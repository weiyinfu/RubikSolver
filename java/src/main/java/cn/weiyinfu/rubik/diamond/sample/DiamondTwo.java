package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.solvers.TableSolverDiamond;

public class DiamondTwo {
    public static void main(String[] args) {
        var x = new TableSolverDiamond(2);
        System.out.println(x.table.size());
    }
}
