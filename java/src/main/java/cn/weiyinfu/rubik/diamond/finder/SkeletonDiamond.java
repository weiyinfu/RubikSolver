package cn.weiyinfu.rubik.diamond.finder;

import cn.weiyinfu.rubik.diamond.Linalg;
import cn.weiyinfu.rubik.diamond.V3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.weiyinfu.rubik.diamond.Linalg.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SkeletonDiamond extends Skeleton {
    int n;//阶数
    public int totalFace;//总面数
    public int perFace;//每个面的面数
    //四个面的坐标
    V3 left;
    V3 right;
    V3 top;
    V3 back;
    double bottomDis;//每个小立方体的体心到面心的距离
    double smallDiamondHeight;//每个小立方体的高度

    //给定一个面，求这个面所决定的小三角形列表
    List<V3> getTriangles(V3 A, V3 B, V3 C) {
        //将三角形A、B、C分割成一堆点
        List<V3> triangles = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            var lastLayerF = dingBiFenDian(A, B, (i - 1.0) / n);
            var lastLayerT = dingBiFenDian(A, C, (i - 1.0) / n);
            var nextLayerF = dingBiFenDian(A, B, i * 1.0 / n);
            var nextLayerT = dingBiFenDian(A, C, i * 1.0 / n);
            V3[] lastLayer = linspace(lastLayerF, lastLayerT, i);
            V3[] nextLayer = linspace(nextLayerF, nextLayerT, i + 1);
            List<V3> nextLayerTriangles = new ArrayList<>();
            List<V3> lastLayerTriangles = new ArrayList<>();
            for (int j = 0; j < nextLayer.length - 1; j++) {
                var m = mean(nextLayer[j], nextLayer[j + 1], lastLayer[j]);
                nextLayerTriangles.add(m);
            }
            for (int j = 0; j < lastLayer.length - 1; j++) {
                var m = mean(lastLayer[j], lastLayer[j + 1], nextLayer[j + 1]);
                lastLayerTriangles.add(m);
            }
            for (int j = 0; j < lastLayerTriangles.size(); j++) {
                triangles.add(nextLayerTriangles.get(j));
                triangles.add(lastLayerTriangles.get(j));
            }
            triangles.add(nextLayerTriangles.get(nextLayerTriangles.size() - 1));
        }
        return triangles;
    }

    List<V3> reverse(List<V3> a) {
        List<V3> ans = new ArrayList<>();
        for (int i = 0; i < a.size(); i++) {
            ans.add(a.get(a.size() - 1 - i));
        }
        return ans;
    }

    public SkeletonDiamond(int n) {
        this.n = n;
        for (int i = 0; i < n; i++) {
            perFace += i * 2 + 1;
        }
        totalFace = perFace * 4;
        double N = n;
        //以最左边为原点，以最左和最右为X轴建立立体坐标系
        left = new V3(0, 0, 0);
        right = new V3(N, 0, 0);
        //后面那个点的坐标(N/2,back,0)
        back = new V3(N / 2, N * sqrt(3) / 2, 0);
        V3 bottomCenter = mean(left, right, back);
        top = new V3(bottomCenter.x, bottomCenter.y, sqrt(N * N - pow(distance(bottomCenter, left), 2)));
        V3[] fourPoints = {top, left, right, back};
        V3 bodyCenter = mean(fourPoints);
        bottomDis = distance(bottomCenter, bodyCenter) / N;
        smallDiamondHeight = distance(top, bottomCenter) / N;

        this.init();
    }

    Face handle(V3 A, V3 B, V3 C, V3 D, boolean reverseTriangle, int faceIndex) {
        var center = mean(A, B, C);
        var heightVec = D.sub(center).resize(bottomDis);
        var triangles = getTriangles(A, B, C);
        if (reverseTriangle) {
            triangles = reverse(triangles);
        }
        List<V3> faceCenters = triangles.stream().map(Linalg::mean).collect(Collectors.toList());
        return new Face(faceCenters, heightVec);
    }

    @Override
    List<Face> getFaces() {
        var a = new ArrayList<Face>();
        a.add(handle(left, top, back, right, true, 0));
        a.add(handle(top, left, right, back, false, 1));
        a.add(handle(right, back, top, left, true, 2));
        a.add(handle(back, right, left, top, true, 3));
        return a;
    }
}
