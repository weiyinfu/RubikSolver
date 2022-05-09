package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.TableSolver;
import cn.weiyinfu.rubik.diamond.obj.DiamondSimple;

import java.nio.file.Paths;

public class TableSolverDiamondSimple extends TableSolver {
    public TableSolverDiamondSimple(int n) {
        super(Paths.get(String.format("diamondSimple%s.bin", n)), new DiamondSimple(n));
    }
}
