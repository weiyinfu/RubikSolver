package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.DiamondTableSolver;

public class Three {
    public static void main(String[] args) {
        var x = new DiamondTableSolver(3);
        System.out.println(x.solver.table.size());
    }
}
