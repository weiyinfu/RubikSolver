package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.SearchSolver;
import cn.weiyinfu.rubik.diamond.object.CubeStart;

import java.nio.file.Paths;

public class SearchSolverCubeStart extends SearchSolver {
    public SearchSolverCubeStart(int n, int maxLayer, int maxDepth) {
        super(Paths.get(String.format("SearchSolverCubeStart%s_%s.bin", n, maxLayer)), new CubeStart(n), maxLayer, maxDepth);
    }

    public static void main(String[] args) {
        var a = new SearchSolverCubeStart(2, 7, 3);
        System.out.println(a.table.size());
    }
}
