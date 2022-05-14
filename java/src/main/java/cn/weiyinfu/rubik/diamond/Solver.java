package cn.weiyinfu.rubik.diamond;

import java.util.List;

public interface Solver extends cn.weiyinfu.rubik.cube.Solver {
    List<Integer> solve(int[] a);
}
