package cn.weiyinfu.rubik.diamond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static cn.weiyinfu.rubik.diamond.Displace.mul;

public class SearchSolver implements Solver {
    public static class Node implements Serializable {
        public int[] a;
        long hash;
        int layer;//当前是第几层
        int prevOp;

        Node(int[] a, long hash, int layer, int prevOp) {
            this.a = a;
            this.hash = hash;
            this.layer = layer;
            this.prevOp = prevOp;
        }
    }

    class Op {
        List<Integer> valid = new ArrayList<>();
        int[] displace;

        Op(int[] a) {
            displace = a;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == i) {
                    continue;
                }
                valid.add(i);
            }
        }

        Node apply(Node x) {
            int[] a = Arrays.copyOf(x.a, x.a.length);
            var hash = x.hash;
            for (int i = 0; i < valid.size(); i++) {
                int ind = valid.get(i);
                hash ^= zob.zob[ind][a[ind]];
                a[ind] = x.a[displace[ind]];
                hash ^= zob.zob[ind][a[ind]];
            }
            return new Node(a, hash, x.layer + 1, 0);
        }
    }

    class OpGood {
        int good;
        int[] state;
        Node node;

        OpGood(int good, int[] a, Node node) {
            this.good = good;
            this.state = a;//执行完操作之后的状态
            this.node = node;
        }
    }

    final Logger log = LoggerFactory.getLogger(SearchSolver.class);
    private int maxLayer;
    private int maxDepth;
    private int[] width;
    protected int[] startState;
    private long startStateCode;
    int[] identityDisplace;
    protected Provider provider;
    protected List<Operation> operations;
    private Zobrist zob;
    public Map<Long, Node> table;
    public Map<Long, Node> lastStep;

    public SearchSolver() {

    }

    public void init(Path tablePath, Provider p, int maxLayer, int maxDepth, int[] width) {
        this.provider = p;
        this.maxLayer = maxLayer;
        this.startState = provider.newStart();
        this.maxDepth = maxDepth;
        this.operations = provider.getOperations();
        this.width = width;
        if (operations.size() == 0) {
            throw new RuntimeException("operations is empty");
        }
        int displaceSize = startState.length;
        this.zob = new Zobrist(displaceSize, displaceSize);
        this.startStateCode = zob.calculateHash(this.startState);
        this.identityDisplace = Displace.arange(displaceSize);
        if (!Files.exists(tablePath)) {
            var ma = this.buildTables();
            saveTable(ma, tablePath);
        }
        this.table = loadTable(tablePath);
        this.lastStep = new TreeMap<>();
        //为了减少层数，直接对最后一步执行reverse
        for (var i : table.values()) {
            var x = Displace.mul(startState, Displace.inverse(i.a));
            var code = zob.calculateHash(x);
            lastStep.put(code, i);
        }
    }

    static void saveTable(Map<Long, Node> ma, Path tablePath) {
        try (var cout = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(tablePath)));
        ) {
            cout.writeObject(ma);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Map<Long, Node> loadTable(Path tablePath) {
        try (var cin = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(tablePath)))) {
            Map<Long, Node> ma = (Map<Long, Node>) cin.readObject();
            System.out.println("loadTable over:" + ma.size());
            return ma;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    Map<Long, Node> buildTables() {
        var start = new Node(identityDisplace, zob.calculateHash(identityDisplace), 0, 0);
        Queue<Node> q = new ConcurrentLinkedQueue<>();
        q.add(start);
        var visited = new ConcurrentSkipListMap<Long, Node>();
        visited.put(start.hash, start);
        long beginTime = System.currentTimeMillis();
        //此处操作是正向操作
        var dis = operations.stream().map(x -> new Op(x.displace)).collect(Collectors.toList());
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

    //给定置换求正向操作
    List<Integer> solveDisplace(int[] a) {
        var opList = new ArrayList<Integer>();
        while (!Arrays.equals(identityDisplace, a)) {
            var k = zob.calculateHash(a);
            var no = table.get(k);
            if (no == null) {
                throw new RuntimeException("illegal state");
            }
            var op = operations.get(no.prevOp);
            a = mul(a, op.reverseDisplace);
            opList.add(no.prevOp);
        }
        //将操作列表反转
        for (var i = 0; i < opList.size() / 2; i++) {
            var j = opList.size() - 1 - i;
            var temp = opList.get(i);
            opList.set(i, opList.get(j));
            opList.set(j, temp);
        }
        return opList;
    }

    int howGoodPrefix(int[] a) {
        //只评价前缀有多少个符合要求
        var s = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == startState[i]) {
                s++;
            } else {
                break;
            }
        }
        return s;
    }

    int howGoodSuffix(int[] a) {
        //只评价前缀有多少个符合要求
        var s = 0;
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] == startState[i]) {
                s++;
            } else {
                break;
            }
        }
        return s;
    }

    int howGood(int[] a) {
        //判断a到目标状态的距离，这种评价方式梯度不够明确
        var s = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] == startState[i]) {
                s++;
            }
        }
        return s;
    }

    List<Integer> search(int[] a, int depth) {
        var code = zob.calculateHash(a);
        if (code == startStateCode) return new ArrayList<>();
        if (depth >= maxDepth) {
            return null;
        }
        //最后一步的优化，如果发现无解，立马返回
        if (lastStep.containsKey(code)) {
            return solveDisplace(lastStep.get(code).a);
        } else {
            if (depth >= maxDepth - 1) {
                return null;
            }
        }
        List<OpGood> candidates = new ArrayList<>(table.size());
        for (var i : table.values()) {
            if (i.layer == 0) continue;//如果是原地不动，那是万万不可的
            var x = Displace.mul(a, i.a);
            var goodness = howGoodSuffix(x);
            candidates.add(new OpGood(goodness, x, i));
            if (goodness == x.length) {
                break;
            }
        }
        candidates.sort(Comparator.comparing(x -> -x.good));
        if (depth > 0 && width != null && candidates.size() > width[depth - 1]) {
            candidates = candidates.subList(0, width[depth - 1]);
        }

//        var ind = 0;
//        long lastSecond = 0;

        for (var nex : candidates) {
//            ind++;
//            var second = System.currentTimeMillis() / 1000;
//            if (second % 3 == 0 && lastSecond != second && depth == 0) {
//                var ratio = 1.0 * ind / table.size();
//                System.out.printf("depth=%s,ind=%s,table.size=%s,ratio=%s\n", depth, ind, table.size(), ratio);
//                lastSecond = second;
//            }
            var ops = search(nex.state, depth + 1);
            if (ops == null) continue;
            var opList = solveDisplace(nex.node.a);
            opList.addAll(ops);
            return opList;
        }
        return null;
    }

    @Override
    public List<Integer> solve(int[] a) {
        //贪心法搜索答案
        List<Integer> ans;
        ans = search(a, 0);
        if (ans == null) {
            throw new RuntimeException("solve failed");
        }
        return ans;
    }


    public String solve(String s) {
        var a = provider.parseState(s);
        System.out.println("solving" + Arrays.toString(a));
        var opIds = solve(a);
        return OperationList.operation2string(opIds, operations);
    }

}
