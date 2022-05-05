package cn.weiyinfu.rubik.diamond;

import java.nio.file.Paths;

public class TableSolverDiamond extends TableSolver {
    public TableSolverDiamond(int n) {
        super(Paths.get(String.format("diamond%s.bin", n)), new Diamond(n));
    }
}
