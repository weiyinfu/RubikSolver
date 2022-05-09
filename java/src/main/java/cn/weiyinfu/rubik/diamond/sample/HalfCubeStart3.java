package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.solvers.HalfSolverCubeStart;

public class HalfCubeStart3 {
    public static void main(String[] args) {
        var x = new HalfSolverCubeStart(3, 9);
        System.out.println(x.table.size());
    }
}
