package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.diamond.Linalg;
import cn.weiyinfu.rubik.diamond.V3;
import junit.framework.TestCase;

public class TestLinalg extends TestCase {

    public void testMatmul() {
        double[][] A = {{1, 2, 3}, {4, 5, 6}};
        System.out.println(Linalg.tos(Linalg.transpose(A)));
        var C = Linalg.matmul(A, Linalg.transpose(A));
        System.out.println(Linalg.tos(C));
    }

    public void testDistance() {
        var ans = Linalg.distanceToFace(
                new V3(0, 0, 0),
                new V3(0, 1, 0),
                new V3(1, 2, 0),
                new V3(0, 0, 5)
        );
        System.out.println(ans);
    }

    public void testTurnByAxis() {
        var A = new V3(0, 0, 0);
        var B = new V3(0, 0, 1);
        var C = new V3(1, 0, 0);
        var D = Linalg.turnByAxis(A, B, C, -Math.PI / 2);
        System.out.println(D);
    }
}
