package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.TableSolver;
import cn.weiyinfu.rubik.diamond.object.CubeDouble;

import java.nio.file.Paths;

public class TableSolverCubeStartDouble2 {
    public static void main(String[] args) {
        int n = 2;
        int maxLayer = 6;
        var y = new CubeDouble(n);
        System.out.println(y.getOperations().size());
        var x = new TableSolver(Paths.get(String.format("TableCubeStartDoulbe%s_%s.bin", n, maxLayer)), y);
        System.out.println(x.table.size());
    }
}
