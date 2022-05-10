package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.SearchSolver;
import cn.weiyinfu.rubik.diamond.object.CubeStart;
import cn.weiyinfu.rubik.diamond.object.DiamondSimpleStart;

import java.nio.file.Paths;

public class SearchSolverDiamondSimpleStart extends SearchSolver {
    public SearchSolverDiamondSimpleStart(int n, int maxLayer, int maxDepth) {
        super(
                Paths.get(String.format("SearchSolverDiamondSimpleStart%s_%s.bin", n, maxLayer)),
                new DiamondSimpleStart(n),
                maxLayer,
                maxDepth
        );
    }

    public static void main(String[] args) {
        var a = new SearchSolverDiamondSimpleStart(3, 7, 3);
        System.out.println(a.table.size());
    }
}
