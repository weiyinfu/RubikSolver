package cn.weiyinfu.rubik.diamond;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static cn.weiyinfu.rubik.diamond.Linalg.displaceMultiply;
import static cn.weiyinfu.rubik.diamond.Linalg.displaceReverse;

/*
 * 使用zobrist函数作为哈希函数，存在极小的概率发生冲突导致问题求解错误
 * */
public class TableSolver {
    Logger log = LoggerFactory.getLogger(TableSolver.class);
    public final List<Operation> operations;
    long[][] zob;
    public Map<Long, Integer> table;
    Provider provider;

    public class Operation {
        public String name;
        public int[] displace;
        public int[] reverseDisplace;
    }

    class Node {
        public int[] a;
        long hash;
        int layer;//当前是第几层

        Node(int[] a, long hash, int layer) {
            this.a = a;
            this.hash = hash;
            this.layer = layer;
        }
    }

    class Displace {
        List<Integer> valid = new ArrayList<>();
        int[] displace;

        Displace(int[] a) {
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
                hash ^= zob[ind][a[ind]];
                a[ind] = x.a[displace[ind]];
                hash ^= zob[ind][a[ind]];
            }
            return new Node(a, hash, x.layer + 1);
        }
    }

    public interface Provider {
        int[] newStart();

        Map<String, int[]> getOperations();
    }

    long calculateHash(int[] a) {
        long s = 0;
        for (int i = 0; i < a.length; i++) {
            s ^= zob[i][a[i]];
        }
        return s;
    }

    Map<Long, Integer> buildTables() {
        var startState = provider.newStart();
        var start = new Node(startState, calculateHash(startState), 0);
        Queue<Node> q = new ConcurrentLinkedQueue<>();
        q.add(start);
        var visited = new TreeMap<Long, Integer>();
        visited.put(start.hash, 0);
        long beginTime = System.currentTimeMillis();
        var dis = operations.stream().map(x -> new Displace(x.reverseDisplace)).collect(Collectors.toList());
        var layerCountMap = new TreeMap<Integer, Integer>();
        while (!q.isEmpty()) {
            if (visited.size() % 10000 == 0) {
                log.info(String.format("visited:%s,queue:%s\n", visited.size(), q.size()));
            }
            var it = q.poll();
            if (it == null) {
                continue;
            }
            for (var i = 0; i < dis.size(); i++) {
                var op = dis.get(i);
                var nex = op.apply(it);
                if (!visited.containsKey(nex.hash)) {
                    layerCountMap.put(nex.layer, layerCountMap.getOrDefault(nex.layer, 0) + 1);
                    visited.put(nex.hash, i);
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

    void saveTable(Map<Long, Integer> ma, Path tablePath) {
        try (var o = Files.newOutputStream(tablePath);
             var cout = new DataOutputStream(o);
        ) {
            for (var i : ma.entrySet()) {
                var k = i.getKey();
                var v = i.getValue();
                cout.writeLong(k);
                cout.writeByte(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Map<Long, Integer> loadTable(Path tablePath) {
        try (var cin = new DataInputStream(Files.newInputStream(tablePath))) {
            var ma = new TreeMap<Long, Integer>();
            while (true) {
                try {
                    var k = cin.readLong();
                    var v = cin.readByte();
                    ma.put(k, (int) v);
                } catch (IOException e) {
                    break;
                }
            }
            log.info("加载成功" + ma.size());
            return ma;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int[] string2displace(String s) {
        s = s.replaceAll("[^a-zA-Z]", "");
        Map<Character, Integer> ma = new TreeMap<>();
        for (char i : s.toCharArray()) {
            if (!ma.containsKey(i)) {
                ma.put(i, ma.size());
            }
        }
        var a = new int[s.length()];
        for (int i = 0; i < a.length; i++) {
            a[i] = ma.get(s.charAt(i));
        }
        return a;
    }

    public String opids2String(List<Integer> opIds) {
        return opIds.stream()
                .map(x -> operations.get(x).name)
                .collect(Collectors.joining(","));

    }

    public String solve(String s) {
        var a = string2displace(s);
        var opIds = solve(a);
        return opids2String(opIds);
    }

    public List<Integer> solve(int[] a) {
        var opList = new ArrayList<Integer>();
        var target = provider.newStart();
        while (!Arrays.equals(target, a)) {
            var k = calculateHash(a);
            var opId = table.get(k);
            if (opId == null) {
                throw new RuntimeException("illegal state");
            }
            var op = operations.get(opId);
            a = displaceMultiply(a, op.displace);
            opList.add(opId);
        }
        return opList;
    }

    public TableSolver(Path tablePath, Provider p) {
        this.provider = p;
        operations = provider.getOperations()
                .entrySet()
                .stream()
                .map(x -> {
                    var o = new Operation();
                    o.name = x.getKey();
                    o.displace = x.getValue();
                    o.reverseDisplace = displaceReverse(o.displace);
                    return o;
                })
                .sorted(Comparator.comparing(x -> x.name))
                .collect(Collectors.toList());
        if (operations.size() == 0) {
            throw new RuntimeException("operations is empty");
        }
        int displaceSize = operations.get(0).displace.length;
        zob = new long[displaceSize][displaceSize];
        Random r = new Random(0);
        for (int i = 0; i < displaceSize; i++) {
            for (int j = 0; j < displaceSize; j++) {
                zob[i][j] = r.nextLong();
            }
        }
        if (!Files.exists(tablePath)) {
            var ma = this.buildTables();
            this.saveTable(ma, tablePath);
        }
        this.table = this.loadTable(tablePath);
    }
}
