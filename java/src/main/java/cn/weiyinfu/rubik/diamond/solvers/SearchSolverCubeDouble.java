package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.SearchSolver;
import cn.weiyinfu.rubik.diamond.object.CubeDouble;

import java.nio.file.Paths;

public class SearchSolverCubeDouble extends SearchSolver {
    public SearchSolverCubeDouble(int n, int maxLayer, int maxDepth) {
        init(
                Paths.get(String.format("SearchSolverCubeStartDouble%s_%s.bin", n, maxLayer)),
                new CubeDouble(n),
                maxLayer,
                maxDepth,
                null
        );
    }

    public static void main(String[] args) {
        var a = new SearchSolverCubeDouble(3, 9, 3);
        System.out.println(a.table.size());
    }
}
