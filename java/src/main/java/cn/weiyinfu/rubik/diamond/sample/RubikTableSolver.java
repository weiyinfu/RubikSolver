package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.cube.Cube;
import cn.weiyinfu.rubik.diamond.DisplaceFinderCube;
import cn.weiyinfu.rubik.diamond.TableSolver;

import java.nio.file.Paths;
import java.util.Map;

public class RubikTableSolver {
    DisplaceFinderCube finder;
    TableSolver solver;

    RubikTableSolver() {
        finder = new DisplaceFinderCube(2);
        solver = new TableSolver(Paths.get("cube2.bin"), new TableSolver.Provider() {
            @Override
            public int[] newStart() {
                var c = new Cube(2);
                return finder.flat(c.colors);
            }

            @Override
            public Map<String, int[]> getOperations() {
                return finder.operations;
            }
        });
    }

    public static void main(String[] args) {
        var x = new RubikTableSolver();
        System.out.println(x.solver.table.size());
    }
}
