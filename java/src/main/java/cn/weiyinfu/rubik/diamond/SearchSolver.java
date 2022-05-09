package cn.weiyinfu.rubik.diamond;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SearchSolver extends GreedySolver {
    private final int maxDepth;
    private final int[] target;

    public SearchSolver(Path tablePath, Provider p, int maxLayer, int maxDepth) {
        super(tablePath, p, maxLayer);
        this.maxDepth = maxDepth;
        this.target = provider.newStart();
    }

    class NodeGood {
        int good;
        int[] state;
        Node node;

        NodeGood(int good, int[] a, Node node) {
            this.good = good;
            this.state = a;
            this.node = node;
        }
    }

    List<Integer> search(int[] a, int depth) {
        if (depth >= maxDepth) {
            return null;
        }
        if (Arrays.equals(a, target)) return new ArrayList<>();
        if (depth == maxDepth - 1) {
            //还有最后一次机会
            var nex = Displace.reverse(a);
            var code = calculateHash(nex);
            if (table.containsKey(code)) {
                //如果已经存在，则不用遍历了，直接返回
                return solveSimple(nex);
            }
        }
        List<NodeGood> candidates = new ArrayList<>(table.size());
        for (var i : table.values()) {
            if (i.layer == 0) continue;//如果是原地不动，那是万万不可的
            var x = Displace.mul(a, i.a);
            candidates.add(new NodeGood(howGoodSuffix(x), x, i));
        }
        candidates.sort(Comparator.comparing(x -> -x.good));
//        if (depth == 0) {
//            System.out.println("========");
//            for (int i = 0; i < 5; i++) {
//                System.out.println(Arrays.toString(candidates.get(i).state));
//            }
//        }
        int ind = 0;
        for (var nex : candidates) {
            ind++;
            if (depth == 0 && (System.currentTimeMillis() / 1000) % 2 == 0) {
                System.out.printf("%s/%s\n", ind, candidates.size());
            }
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
