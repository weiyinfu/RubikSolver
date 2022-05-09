package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.TableSolver;
import cn.weiyinfu.rubik.diamond.obj.Cube;

import java.nio.file.Paths;

public class TableSolverCube extends TableSolver {
    public TableSolverCube(int n) {
        super(Paths.get(String.format("cube%s.bin", n)), new Cube(n));
    }

    public static void main(String[] args) {
        var x = new TableSolverCube(2);
        System.out.println(x.table.size());
    }
}
