package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.solvers.TableSolverCube;

public class CubeTwo {
    public static void main(String[] args) {
        var x = new TableSolverCube(2);
        System.out.println(x.table.size());
    }
}
