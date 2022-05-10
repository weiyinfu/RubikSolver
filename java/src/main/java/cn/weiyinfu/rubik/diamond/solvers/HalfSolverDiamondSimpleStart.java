package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.HalfSolver;
import cn.weiyinfu.rubik.diamond.object.DiamondSimpleStart;

import java.nio.file.Paths;

public class HalfSolverDiamondSimpleStart extends HalfSolver {
    public HalfSolverDiamondSimpleStart(int n, int maxLayer) {
        super(Paths.get(String.format("diamondHalfSolver%s_%s.bin", n, maxLayer)), new DiamondSimpleStart(n), maxLayer);
    }

    public static void main(String[] args) {
        var x = new HalfSolverDiamondSimpleStart(3, 11);
        System.out.println(x.table.size());
        System.out.println(x.operations.size());
        /*
         * 244577/933120
         * */
    }
}
