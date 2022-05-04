package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.DiamondTableSolver;

public class Two {
    public static void main(String[] args) {
        var x = new DiamondTableSolver(2);
        System.out.println(x.solver.table.size());
    }
}
