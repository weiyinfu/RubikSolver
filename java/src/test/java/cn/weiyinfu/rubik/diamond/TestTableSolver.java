package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.diamond.object.Cube;
import cn.weiyinfu.rubik.diamond.object.DiamondSimple;
import cn.weiyinfu.rubik.diamond.solvers.TableSolverCube;
import cn.weiyinfu.rubik.diamond.solvers.TableSolverDiamondSimple;
import junit.framework.TestCase;

import java.util.Arrays;

public class TestTableSolver extends TestCase {

    public void testCube2() {
        SolverPlayer p = new SolverPlayer(
                new Cube(2),
                new TableSolverCube(2)
        );
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000, false);
    }

    public void testDiamond3() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new TableSolverDiamondSimple(3));
        System.out.println(p.testSolver(1000));
        p.testSolverMultiTimes(1000, 1000, false);
    }


    public void testTableSolverDiamondOne() {
        SolverPlayer p = new SolverPlayer(
                new DiamondSimple(3),
                new TableSolverDiamondSimple(3)
        );
        var state = p.getShuffleState(1000);
        System.out.println(Arrays.toString(state));
        var ans = p.solve(state);
        System.out.println("Operations:" + ans);
        var des = p.executeOps(state, ans, false);
        System.out.println(Arrays.toString(des));
    }

}
