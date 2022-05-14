package cn.weiyinfu.rubik.diamond.finder;

import cn.weiyinfu.rubik.diamond.V3;

import java.util.ArrayList;
import java.util.List;

public class SkeletonCube extends Skeleton {
    private final int n;
    V3[][][] eight = new V3[2][2][2];

    public SkeletonCube(int n) {
        this.n = n;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    eight[i][j][k] = new V3(i * n, j * n, k * n);
                }
            }
        }
        init();
    }

    Face add(V3 A, V3 B, V3 C, V3 D, V3 heightVec) {
        /*
         * A----B
         * |    |
         * |    |
         * C----D
         * */
        var dx = B.sub(A).mul(1.0 / n);
        var dy = C.sub(A).mul(1.0 / n);
        var centers = new ArrayList<V3>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                var center = A.add(dy.mul(i + 0.5).add(dx.mul(j + 0.5)));
                centers.add(center);
            }
        }
        return new Face(centers, heightVec);
    }

    @Override
    List<Face> getFaces() {
        var faces = new ArrayList<Face>();
        faces.add(add(eight[0][0][1], eight[0][1][1], eight[1][0][1], eight[1][1][1], new V3(0, 0, -0.5)));
        faces.add(add(eight[0][0][1], eight[1][0][1], eight[0][0][0], eight[1][0][0], new V3(0, 0.5, 0)));
        faces.add(add(eight[1][0][1], eight[1][1][1], eight[1][0][0], eight[1][1][0], new V3(-0.5, 0, 0)));
        faces.add(add(eight[1][1][1], eight[0][1][1], eight[1][1][0], eight[0][1][0], new V3(0, -0.5, 0)));
        faces.add(add(eight[1][0][0], eight[1][1][0], eight[0][0][0], eight[0][1][0], new V3(0, 0, 0.5)));
        faces.add(add(eight[0][0][0], eight[0][1][0], eight[0][0][1], eight[0][1][1], new V3(0.5, 0, 0)));
        return faces;
    }
}
