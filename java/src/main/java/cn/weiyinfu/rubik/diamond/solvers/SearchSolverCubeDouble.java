package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.SearchSolver;
import cn.weiyinfu.rubik.diamond.object.CubeStartDouble;

import java.nio.file.Paths;

public class SearchSolverCubeStartDouble extends SearchSolver {
    public SearchSolverCubeStartDouble(int n, int maxLayer, int maxDepth) {
        super(
                Paths.get(String.format("SearchSolverCubeStartDouble%s_%s.bin", n, maxLayer)),
                new CubeStartDouble(n),
                maxLayer,
                maxDepth
        );
    }

    public static void main(String[] args) {
        var a = new SearchSolverCubeStartDouble(3, 9, 3);
        System.out.println(a.table.size());
    }
}
