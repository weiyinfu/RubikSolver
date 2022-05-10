package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.diamond.object.CubeStart;
import cn.weiyinfu.rubik.diamond.object.DiamondSimpleStart;
import cn.weiyinfu.rubik.diamond.solvers.SearchSolverCubeStart;
import cn.weiyinfu.rubik.diamond.solvers.SearchSolverDiamondSimpleStart;
import junit.framework.TestCase;

import java.util.Arrays;

public class TestSearchSolver extends TestCase {

    public void testCubeOne() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new SearchSolverCubeStart(2, 7, 2));
        var x = p.getShuffleState(1000);
        System.out.println(Arrays.toString(x));
        System.out.println(p.testSolver(x));
    }

    public void testCubeMulti() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new SearchSolverCubeStart(2, 10, 2));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testCubeMulti3Steps() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new SearchSolverCubeStart(2, 7, 3));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testCube3() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(3),
                new SearchSolverCubeStart(3, 5, 3)
        );
        for (int i = 0; i < 10; i++) {
            var a = p.getShuffleState(5);
            System.out.println(Arrays.toString(a));
            System.out.println(i + " " + p.testSolver(a));
        }
    }

    public void testCube3Deep() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(3),
                new SearchSolverCubeStart(3, 4, 5)
        );
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " " + p.testSolver(4));
        }
    }

    public void testDiamondMulti3Steps() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new SearchSolverDiamondSimpleStart(3, 7, 3)
        );
        p.testSolverMultiTimes(1000, 1000);
    }

}
