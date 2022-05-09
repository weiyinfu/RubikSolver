package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.solvers.TableSolverDiamondSimple;
//933120
public class DiamondThreeSimple {
    public static void main(String[] args) {
        var x = new TableSolverDiamondSimple(3);
        System.out.println(x.table.size());
    }
}
