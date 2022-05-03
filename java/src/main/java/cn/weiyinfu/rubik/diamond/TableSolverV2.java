package cn.weiyinfu.rubik.diamond;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static cn.weiyinfu.rubik.diamond.Linalg.displaceMultiply;
import static cn.weiyinfu.rubik.diamond.Linalg.displacePower;

/*
 * 使用zobrist函数作为哈希函数
 * */
public class TableSolverV2 {
    class Operation {
        String name;
        int[] displace;
        int[] reverseDisplace;
    }

    private final List<Operation> operations;
    private final Path tablePath;
    DisplaceFinder finder;
    long[][] zob;
    Map<Long, Integer> table;

    class Node {
        int[] a;
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

    long calculateHash(int[] a) {
        long s = 0;
        for (int i = 0; i < a.length; i++) {
            s ^= zob[i][a[i]];
        }
        return s;
    }

    Node newStartNode() {
        var a = new int[finder.skeleton.totalFace];
        for (int i = 0; i < a.length; i++) {
            a[i] = i / finder.skeleton.perFace;
        }
        return new Node(a, calculateHash(a), 0);
    }

    Map<Long, Integer> buildTables() {
        var start = newStartNode();
        Queue<Node> q = new ConcurrentLinkedQueue<>();
        q.add(start);
        var visited = new TreeMap<Long, Integer>();
        visited.put(start.hash, 0);
        long beginTime = System.currentTimeMillis();
        var dis = operations.stream().map(x -> new Displace(x.reverseDisplace)).collect(Collectors.toList());
        var layerCountMap = new TreeMap<Integer, Integer>();
        while (!q.isEmpty()) {
            if (visited.size() % 10000 == 0) {
                System.out.printf("visited:%s,queue:%s\n", visited.size(), q.size());
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
        System.out.printf("耗时%s秒\n", timeUsed / 1000);
        System.out.println(visited.size());
        for (var i : layerCountMap.entrySet()) {
            System.out.printf("%s=>%s\n", i.getKey(), i.getValue());
        }
        return visited;
    }

    void saveTable(Map<Long, Integer> ma) {
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

    Map<Long, Integer> loadTable() {
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
            System.out.println("加载成功" + ma.size());
            return ma;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public String solve(int[] a) {
        var target = newStartNode();
        var opList = new ArrayList<String>();
        while (!Arrays.equals(target.a, a)) {
            var k = calculateHash(a);
            var opId = table.get(k);
            if (opId == null) {
                throw new RuntimeException("illegal state");
            }
            var op = operations.get(opId);
            a = displaceMultiply(a, op.displace);
            opList.add(op.name);
        }
        return opList.toString();
    }

    TableSolverV2(int n, boolean withoutCorner) {
        finder = new DisplaceFinder(n);
        zob = new long[finder.skeleton.totalFace][finder.skeleton.totalFace];
        Random r = new Random(0);
        for (int i = 0; i < finder.skeleton.totalFace; i++) {
            for (int j = 0; j < finder.skeleton.totalFace; j++) {
                zob[i][j] = r.nextLong();
            }
        }
        Map<String, int[]> opList;
        if (withoutCorner) {
            opList = finder.getOperationsWithoutCorner();
        } else {
            opList = finder.getOperations();
        }
        operations = opList
                .entrySet()
                .stream()
                .map(x -> {
                    var o = new Operation();
                    o.name = x.getKey();
                    o.displace = x.getValue();
                    o.reverseDisplace = displacePower(o.displace, 2);
                    return o;
                })
                .sorted(Comparator.comparing(x -> x.name))
                .collect(Collectors.toList());

        tablePath = Paths.get("diamond" + n + ".bin");
        if (!Files.exists(tablePath)) {
            var ma = this.buildTables();
            this.saveTable(ma);
        }
        this.table = this.loadTable();
    }

    public static void main(String[] args) {
        var solver = new TableSolverV2(3, true);
    }
}
