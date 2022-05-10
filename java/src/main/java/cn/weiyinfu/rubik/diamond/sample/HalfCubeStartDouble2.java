package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.HalfSolver;
import cn.weiyinfu.rubik.diamond.object.CubeStartDouble;

import java.nio.file.Paths;

public class HalfCubeStartDouble2 {
    public static void main(String[] args) {
        int n = 3;
        int maxLayer = 7;
        var y = new CubeStartDouble(n);
        System.out.println(y.getOperations().size());
        var x = new HalfSolver(Paths.get(String.format("HalfCubeStartDoulbe%s_%s.bin", n, maxLayer)), y, maxLayer);
        System.out.println(x.table.size());
    }
}
