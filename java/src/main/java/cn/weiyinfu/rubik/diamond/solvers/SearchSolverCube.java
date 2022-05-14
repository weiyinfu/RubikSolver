package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.SearchSolver;
import cn.weiyinfu.rubik.diamond.object.Cube;

import java.nio.file.Paths;
import java.util.Arrays;

public class SearchSolverCube extends SearchSolver {
    public SearchSolverCube(int n, int maxLayer, int maxDepth) {
        int[] width = new int[maxDepth];
        Arrays.fill(width, Integer.MAX_VALUE);
        init(Paths.get(String.format("SearchSolverCube%s_%s.bin", n, maxLayer)), new Cube(n), maxLayer, width.length, width);
    }

    public static void main(String[] args) {
        var a = new SearchSolverCube(2, 7, 3);
        System.out.println(a.table.size());
    }
}
