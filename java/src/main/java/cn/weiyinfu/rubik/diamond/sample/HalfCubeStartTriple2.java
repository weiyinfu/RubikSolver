package cn.weiyinfu.rubik.diamond.sample;

import cn.weiyinfu.rubik.diamond.HalfSolver;
import cn.weiyinfu.rubik.diamond.object.CubeStartTriple;

import java.nio.file.Paths;

public class HalfCubeStartTriple2 {
    public static void main(String[] args) {
        int n = 2;
        int maxLayer = 17;
        var y = new CubeStartTriple(n);
        System.out.println(y.getOperations().size());
        var x = new HalfSolver(Paths.get(String.format("HalfCubeStartTriple%s_%s.bin", n, maxLayer)), y, maxLayer);
        System.out.println(x.table.size());
    }
}
