package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.diamond.obj.Cube;
import cn.weiyinfu.rubik.diamond.obj.CubeStart;
import cn.weiyinfu.rubik.diamond.obj.DiamondSimple;
import cn.weiyinfu.rubik.diamond.obj.DiamondSimpleStart;
import cn.weiyinfu.rubik.diamond.solvers.*;
import junit.framework.TestCase;

import java.util.Arrays;

public class TestTableSolver extends TestCase {

    public void testTableSolverCube() {
        SolverPlayer p = new SolverPlayer(
                new Cube(2),
                new TableSolverCube(2));
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testTableSolverDiamond() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new TableSolverDiamondSimple(3));
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000);
    }


    public void testHalfSolverDiamond() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new HalfSolverDiamondSimpleStart(3, 11));
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testHalfSolverCube() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new HalfSolverCubeStart(2, 10));
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testGreedySolverCube() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new GreedySolverCubeStart(2, 8));
        System.out.println(p.testSolver(7));
        p.testSolverMultiTimes(1000, 1000);
    }

    public void testGreedySolverCubeOne() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new GreedySolverCubeStart(2, 10));
        var x = p.getShuffleState(15);
        System.out.println(Arrays.toString(x));
        System.out.println(p.testSolver(x));
    }

    public void testSearchSolverCubeOne() {
        SolverPlayer p = new SolverPlayer(
                new CubeStart(2),
                new SearchSolverCubeStart(2, 7, 2));
        var x = p.getShuffleState(9);
        System.out.println(Arrays.toString(x));
        System.out.println(p.testSolver(x));
    }

    public void testTableSolverDiamondOne() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new TableSolverDiamondSimple(3));
        var state = p.getShuffleState(1000);
        System.out.println(Arrays.toString(state));
        var ans = p.solve(state);
        System.out.println("Operations:" + ans);
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }

    public void testHalfSolverDiamondSimpleStart() {
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

    public void testHalfDiamondOne() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new HalfSolverDiamondSimpleStart(3, 11));

        var state = new int[]{17, 16, 20, 19, 4, 32, 21, 12, 22, 9, 23, 3, 34, 0, 1, 15, 14, 13, 18, 11, 2, 30, 31, 25, 28, 10, 27, 35, 33, 29, 6, 8, 5, 24, 7, 26};
        var ans = p.solve(state);
        System.out.println("Operations:" + ans);
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }

    public void testGreedyDiamondOne() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimpleStart(3),
                new GreedySolverDiamondSimpleStart(3, 8));

        var state = new int[]{17, 16, 20, 19, 4, 32, 21, 12, 22, 9, 23, 3, 34, 0, 1, 15, 14, 13, 18, 11, 2, 30, 31, 25, 28, 10, 27, 35, 33, 29, 6, 8, 5, 24, 7, 26};
        var ans = p.solve(state);
        System.out.println("Operations:" + ans + " " + ans.size());
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }
}
