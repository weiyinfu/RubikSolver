package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.DiamondTableSolverSimple;

public class ThreeSimple {
    public static void main(String[] args) {
        var x = new DiamondTableSolverSimple(3);
        System.out.println(x.solver.table.size());
    }
}
