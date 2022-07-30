package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.diamond.object.Cube;
import cn.weiyinfu.rubik.diamond.object.DiamondSimple;
import cn.weiyinfu.rubik.diamond.solvers.SearchSolverCube;
import cn.weiyinfu.rubik.diamond.solvers.SearchSolverDiamondSimple;
import cn.weiyinfu.rubik.diamond.solvers.TableSolverDiamondSimple;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestSearchSolver extends TestCase {

    public void testCubeOne() {
        //SearchSolver测试Cube
        var solver = new SearchSolverCube(2, 7, 3);
        var provider = new Cube(2);
        SolverPlayer p = new SolverPlayer(provider, solver);
        System.out.println(p.testSolver(1000));
    }

    public void testCubeOne2() {
        var solver = new SearchSolverCube(2, 7, 3);
        var provider = new Cube(2);
        var a = new int[]{4, 5, 1, 0, 3, 2, 4, 0, 0, 2, 1, 2, 3, 4, 3, 5, 5, 4, 1, 0, 2, 3, 5, 1};
        var s = Arrays.stream(a).mapToObj(x -> "rgbyow".charAt(x) + "").collect(Collectors.joining());
        System.out.println(s);
        var ans = solver.solve(a);
        System.out.println(ans);
        System.out.println(solver.solve(s));
    }

    public void testCubeMulti() {
        //2,10,2 和2,7,3
        SolverPlayer p = new SolverPlayer(
                new Cube(2),
                new SearchSolverCube(2, 7, 3));
        p.testSolverMultiTimes(1000, 1000, false);
    }

    public void testCubeMulti3Steps() {
        SolverPlayer p = new SolverPlayer(
                new Cube(2),
                new SearchSolverCube(2, 7, 3));
        p.testSolverMultiTimes(1000, 1000, false);
    }

    public void testCube3() {
        SolverPlayer p = new SolverPlayer(
                new Cube(3),
                new SearchSolverCube(3, 5, 3)
        );
        p.testSolverMultiTimes(1000, 1, true);
    }

    public void testCube3Deep() {
        SolverPlayer p = new SolverPlayer(
                new Cube(3),
                new SearchSolverCube(3, 4, 5)
        );
        for (int i = 0; i < 10; i++) {
            System.out.println(i + " " + p.testSolver(4));
        }
    }

    public void testDiamondMulti3Steps() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new SearchSolverDiamondSimple(3, 7, 3)
        );
        p.testSolverMultiTimes(1000, 1000, false);
    }

    public void testDiamondMulti3() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new SearchSolverDiamondSimple(3, 7, 3)
        );
        var a = p.getShuffleState(1000);
        System.out.println(Arrays.toString(a));
        var s = Arrays.stream(a).mapToObj(x -> "rgbyow".charAt(x) + "").collect(Collectors.joining());
        System.out.println(s);
        System.out.println("init state" + Arrays.toString(p.p.newStart()));
        var ans = p.solve(a);
        System.out.println("operations:" + ans);
    }

    public void testSearchSolver() {
        var solver = new SearchSolverDiamondSimple(3, 3, 7);
        var state = "yybyyrygyrbrrrrgrrbbrbbybybggyggggbg";
        System.out.println(solver.table.size());
        var ans = solver.solve(state);
        System.out.println(ans);
    }

}
