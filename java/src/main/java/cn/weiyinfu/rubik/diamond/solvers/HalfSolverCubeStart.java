package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.HalfSolver;
import cn.weiyinfu.rubik.diamond.obj.CubeStart;

import java.nio.file.Paths;

public class HalfSolverCubeStart extends HalfSolver {
    public HalfSolverCubeStart(int n, int maxStep) {
        super(Paths.get(String.format("cubeHalf%s_%s.bin", n, maxStep)), new CubeStart(n), maxStep);
    }

    @Override
    public String solve(String s) {
        throw new RuntimeException("unsupported solve string");
    }

    public static void main(String[] args) {
        var x = new HalfSolverCubeStart(2, 10);
        System.out.println(x.table.size());
        /*
        如果是11，则为105474
        如果是10，则为42642
        总状态数：3674160
         * */
    }
}
