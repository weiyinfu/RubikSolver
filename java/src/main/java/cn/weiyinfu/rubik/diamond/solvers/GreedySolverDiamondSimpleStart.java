package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.GreedySolver;
import cn.weiyinfu.rubik.diamond.object.DiamondSimpleStart;

import java.nio.file.Paths;

public class GreedySolverDiamondSimpleStart extends GreedySolver {
    public GreedySolverDiamondSimpleStart(int n, int maxLayer) {
        super(
                Paths.get(String.format("GreedySolverDiamondSimpleStart%s_%s.bin", n, maxLayer)),
                new DiamondSimpleStart(n),
                maxLayer
        );
    }
}
