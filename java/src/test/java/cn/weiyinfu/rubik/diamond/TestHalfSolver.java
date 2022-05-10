package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.diamond.object.CubeStart;
import cn.weiyinfu.rubik.diamond.object.DiamondSimpleStart;
import cn.weiyinfu.rubik.diamond.solvers.HalfSolverCubeStart;
import cn.weiyinfu.rubik.diamond.solvers.HalfSolverDiamondSimpleStart;
import junit.framework.TestCase;

import java.util.Arrays;

public class TestHalfSolver extends TestCase {
    public void testDiamond3() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new HalfSolverDiamondSimpleStart(3, 11)
        );
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testCube2() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new HalfSolverCubeStart(2, 10));
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000);
    }


    public void testCube3() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new HalfSolverDiamondSimpleStart(3, 11));
        var state = p.getShuffleState(1000, Displace.arange(p.operations.get(0).displace.length));
        System.out.println(Arrays.toString(state));
        var ans = p.solve(state);
        System.out.println("Operations:" + ans);
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }

    public void testDiamond3Special() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new HalfSolverDiamondSimpleStart(3, 11));

        var state = new int[]{17, 16, 20, 19, 4, 32, 21, 12, 22, 9, 23, 3, 34, 0, 1, 15, 14, 13, 18, 11, 2, 30, 31, 25, 28, 10, 27, 35, 33, 29, 6, 8, 5, 24, 7, 26};
        var ans = p.solve(state);
        System.out.println("Operations:" + ans);
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }


}
