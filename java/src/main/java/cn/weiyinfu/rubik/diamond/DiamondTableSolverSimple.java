package cn.weiyinfu.rubik.diamond;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class DiamondTableSolverSimple {
    DisplaceFinder finder;
    public TableSolver solver;
    ArrayList<Integer> corners;//corners的坐标

    public int[] newStart() {
        var a = new int[finder.skeleton.totalFace];
        for (int i = 0; i < a.length; i++) {
            a[i] = i / finder.skeleton.perFace;
        }
        clearCorners(a);
        return a;
    }

    void clearCorners(int[] a) {
        for (int i : corners) {
            a[i] = 0;
        }
    }

    String solve(String state) {
        var a = solver.string2displace(state);
        clearCorners(a);
        var ops = solver.solve(a);
        return solver.opids2String(ops);
    }

    public DiamondTableSolverSimple(int n) {
        finder = new DisplaceFinder(n);
        var perFace = finder.skeleton.perFace;
        corners = new ArrayList<>(9);
        for (int i = 0; i < finder.skeleton.totalFace; i++) {
            int faceId = i / perFace;
            int x = i % perFace;
            if (faceId == 1) {
                //只有1号面是正向的
                if (x == 0 || x == perFace - 1 || x == perFace - n) {
                    corners.add(i);
                }
            } else {
                if (x == 0 || x == n - 1 || x == perFace - 1) {
                    corners.add(i);
                }
            }
        }
        solver = new TableSolver(
                Paths.get(String.format("diamondSimple%s.bin", n)),
                new TableSolver.Provider() {

                    @Override
                    public int[] newStart() {
                        return DiamondTableSolverSimple.this.newStart();
                    }

                    @Override
                    public Map<String, int[]> getOperations() {
                        return finder.getOperationsWithoutCorner();
                    }
                }
        );
    }
}
