package cn.weiyinfu.rubik.diamond;

import java.nio.file.Paths;

public class TableSolverDiamondSimple extends TableSolver {
    public TableSolverDiamondSimple(int n) {
        super(Paths.get(String.format("diamondSimple%s.bin", n)), new DiamondSimple(n));
    }
}
