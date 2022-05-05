package cn.weiyinfu.rubik.diamond;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public void testSolver() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new TableSolverCube(2));
        var state = p.getShuffleState(1000);
        System.out.println(Arrays.toString(state));
        var ans = p.solve(state);
        System.out.println("Operations:" + ans);
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }

    public void testParseState() {
        SolverPlayer p = new SolverPlayer(
                new Cube(3),
                null);
        p.testParseStateMulti(1000);
    }

    public void testParseStateCubeOne() {
        var c = new Cube(3);
        SolverPlayer p = new SolverPlayer(c, null);
        System.out.println("testing");
        System.out.println(p.testParseState());
    }

    public void testParseStateCubeOne2() {
        var a = new int[]{4, 3, 0, 0, 5, 1, 4, 0, 2, 2, 1, 4, 3, 5, 3, 4, 5, 5, 2, 2, 3, 1, 1, 0};
        var x = new Cube(2);
        System.out.println(a.length);
        var s = Arrays.stream(a).mapToObj(i -> (char) (i + 'a') + "").collect(Collectors.joining());
        System.out.println(s);
        var ans = x.parseState(s);
        System.out.println(Arrays.toString(ans));
    }

    public void testParseStateDiamondSimple() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new TableSolverDiamondSimple(3));
        p.testParseStateMulti(1000);
    }

    public void testParseState2() {
        var s = "ccabbcaaaababdddddccdddcaaabbbccdbcb";
        var x = new DiamondSimple(3);
        var ans = x.parseState(s);
        System.out.println(Arrays.toString(ans));
    }
}
