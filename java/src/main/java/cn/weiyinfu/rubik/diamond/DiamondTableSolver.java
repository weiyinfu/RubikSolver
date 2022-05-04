package cn.weiyinfu.rubik.diamond;

import java.nio.file.Paths;
import java.util.Map;

public class DiamondTableSolver {
    DisplaceFinder finder;
    public TableSolver solver;

    public int[] newStart() {
        var a = new int[finder.skeleton.totalFace];
        for (int i = 0; i < a.length; i++) {
            a[i] = i / finder.skeleton.perFace;
        }
        return a;
    }

    public DiamondTableSolver(int n) {
        finder = new DisplaceFinder(n);
        solver = new TableSolver(
                Paths.get(String.format("diamond%s.bin", n)),
                new TableSolver.Provider() {

                    @Override
                    public int[] newStart() {
                        return DiamondTableSolver.this.newStart();
                    }

                    @Override
                    public Map<String, int[]> getOperations() {
                        return finder.getOperations();
                    }
                }
        );
    }
}
