package cn.weiyinfu.rubik.diamond.solvers;

import cn.weiyinfu.rubik.diamond.GreedySolver;
import cn.weiyinfu.rubik.diamond.obj.CubeStart;

import java.nio.file.Paths;
/*
* 贪心搜索：每次贪心选择一个操作
* */
public class GreedySolverCubeStart extends GreedySolver {
    public GreedySolverCubeStart(int n, int maxLayer) {
        super(Paths.get(String.format("GreedySolverCubeStart%s_%s.bin", n, maxLayer)), new CubeStart(n), maxLayer);
    }

    public static void main(String[] args) {
        var a = new GreedySolverCubeStart(2, 7);
        System.out.println(a.table.size());
    }
}
