package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.SearchSolver;
import cn.weiyinfu.rubik.diamond.object.Cube;
import cn.weiyinfu.rubik.diamond.object.DiamondSimple;

import java.nio.file.Paths;

public class SearchSolverDiamondSimple extends SearchSolver {
    public SearchSolverDiamondSimple(int n, int maxLayer, int maxDepth) {
        init(Paths.get(String.format("SearchSolverDiamond%s_%s.bin", n, maxLayer)), new DiamondSimple(n), maxLayer, maxDepth, null);
    }

    public static void main(String[] args) {
        var a = new SearchSolverDiamondSimple(2, 7, 3);
        System.out.println(a.table.size());
    }
}
