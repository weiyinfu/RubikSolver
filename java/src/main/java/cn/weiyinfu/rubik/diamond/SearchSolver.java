package cn.weiyinfu.rubik.diamond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static cn.weiyinfu.rubik.diamond.Displace.mul;

public class SearchSolver implements Solver {
    final Logger log = LoggerFactory.getLogger(SearchSolver.class);
    private final int maxLayer;
    private final int maxDepth;
    private final int[] target;
    private final Provider provider;
    private final List<Operation> operations;
    private final long[][] zob;
    public Map<Long, HalfSolver.Node> table;

    public SearchSolver(Path tablePath, Provider p, int maxLayer, int maxDepth) {
        this.provider = p;
        this.maxLayer = maxLayer;
        this.target = provider.newStart();
        this.maxDepth = maxDepth;
        this.operations = provider.getOperations();
        if (operations.size() == 0) {
            throw new RuntimeException("operations is empty");
        }
        int displaceSize = operations.get(0).displace.length;
        this.zob = new long[displaceSize][displaceSize];
        Random r = new Random(0);
        for (int i = 0; i < displaceSize; i++) {
            for (int j = 0; j < displaceSize; j++) {
                zob[i][j] = r.nextLong();
            }
        }
        if (!Files.exists(tablePath)) {
            var ma = this.buildTables();
            HalfSolver.saveTable(ma, tablePath);
        }
        this.table = HalfSolver.loadTable(tablePath);
    }

    long calculateHash(int[] a) {
        long s = 0;
        for (int i = 0; i < a.length; i++) {
            s ^= zob[i][a[i]];
        }
        return s;
    }

    Map<Long, HalfSolver.Node> buildTables() {
        var startState = provider.newStart();
        var start = new HalfSolver.Node(startState, calculateHash(startState), 0, 0);
        Queue<HalfSolver.Node> q = new ConcurrentLinkedQueue<>();
        q.add(start);
        var visited = new ConcurrentSkipListMap<Long, HalfSolver.Node>();
        visited.put(start.hash, start);
        long beginTime = System.currentTimeMillis();
        //此处操作是正向操作
        var dis = operations.stream().map(x -> new HalfSolver.Op(x.displace, zob)).collect(Collectors.toList());
        var layerCountMap = new TreeMap<Integer, Integer>();
        var lastLayer = 0;
        while (!q.isEmpty()) {
            if (visited.size() % 10000 == 0) {
                log.info(String.format("visited:%s,queue:%s,layer=%s\n", visited.size(), q.size(), lastLayer));
            }
            var it = q.poll();
            if (it == null) {
                continue;
            }
            if (it.layer >= maxLayer) {
                continue;
            }
            lastLayer = it.layer;
            for (var i = 0; i < dis.size(); i++) {
                var op = dis.get(i);
                var nex = op.apply(it);
                nex.prevOp = i;
                if (!visited.containsKey(nex.hash)) {
                    layerCountMap.put(nex.layer, layerCountMap.getOrDefault(nex.layer, 0) + 1);
                    visited.put(nex.hash, nex);
                    q.add(nex);
                }
            }
        }
        long timeUsed = System.currentTimeMillis() - beginTime;
        log.info(String.format("耗时%s秒,tableSize=%s\n", timeUsed / 1000, visited.size()));
        for (var i : layerCountMap.entrySet()) {
            log.info(String.format("%s=>%s\n", i.getKey(), i.getValue()));
        }
        return visited;
    }

    class NodeGood {
        int good;
        int[] state;
        HalfSolver.Node node;

        NodeGood(int good, int[] a, HalfSolver.Node node) {
            this.good = good;
            this.state = a;//执行完操作之后的状态
            this.node = node;
        }
    }

    //给定置换求正向操作
    List<Integer> solveSimple(int[] a) {
        //反转数组
        var opList = new ArrayList<Integer>();
        while (!Arrays.equals(target, a)) {
            var k = calculateHash(a);
            var no = table.get(k);
            if (no == null) {
                throw new RuntimeException("illegal state");
            }
            var op = operations.get(no.prevOp);
            a = mul(a, op.reverseDisplace);
            opList.add(no.prevOp);
        }
        //将操作列表反转
        var ans = new ArrayList<Integer>();
        for (var i = opList.size() - 1; i >= 0; i--) {
            ans.add(opList.get(i));
        }
        return ans;
    }

    List<Integer> search(int[] a, int depth) {
        if (depth >= maxDepth) {
            return null;
        }
        if (Arrays.equals(a, target)) return new ArrayList<>();
        if (depth == maxDepth - 1) {
            /*还有最后一次机会
            最后一次机会一定要成功，所以这里直接选择最佳方案
             */
            var nex = Displace.reverse(a);
            var code = calculateHash(nex);
            if (table.containsKey(code)) {
                //如果已经存在，则不用遍历了，直接返回
                return solveSimple(nex);
            }
            return null;
        }
        List<NodeGood> candidates = new ArrayList<>(table.size());
        for (var i : table.values()) {
            if (i.layer == 0) continue;//如果是原地不动，那是万万不可的
            var x = Displace.mul(a, i.a);
            candidates.add(new NodeGood(GreedySolver.howGoodSuffix(x), x, i));
        }
        candidates.sort(Comparator.comparing(x -> -x.good));
        var ind = 0;
        long lastSecond = 0;
        for (var nex : candidates) {
            ind++;
//            var second = System.currentTimeMillis() / 1000;
//            if (second % 3 == 0 && lastSecond != second) {
//                lastSecond = second;
//                var ratio = 1.0 * ind / table.size();
//                System.out.printf("depth=%s,ind=%s,table.size=%s,ratio=%s\n", depth, ind, table.size(), ratio);
//            }
            var ops = search(nex.state, depth + 1);
            if (ops == null) continue;
            var opList = solveSimple(nex.node.a);
            opList.addAll(ops);
            return opList;
        }
        return null;
    }

    @Override
    public List<Integer> solve(int[] a) {
        //贪心法搜索答案
        return search(a, 0);
    }
}
