package cn.weiyinfu.rubik.diamond.finder;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestSkeleton extends TestCase {
    public void testSkeletonDiamond2() {
        /*
        * 0,22,35,
1,
2,20,
3,
4,9,18,
5,32,
6,
7,10,
8,13,27,
11,
12,23,
14,
15,29,
16,
17,26,31,
19,
21,
24,
25,34,
28,
30,
33,
        * */
        var x = new SkeletonDiamond(3);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println("骨骼点：" + i);
        }
        System.out.println("骨骼点个数" + x.facePoints.size());
        //角块4个，边块6个，面块4个
        for (var i : x.facePoints.values()) {
            System.out.println("小四面体对应的面数" + i.count());
        }
        int faceCount = x.facePoints.values().stream().map(SkeletonDiamond.FacePoint::count).reduce(Integer::sum).get();
        System.out.println("总面数：" + faceCount + " " + x.totalFace);
        for (var i : x.getSword()) {
            System.out.println(Arrays.toString(i));
        }
    }

    public void testSkeletonDiamond() {
        var x = new SkeletonDiamond(1);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println(i);
        }
    }

    public void testSkeletonCube() {
        var x = new SkeletonCube(2);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println(i);
        }
        for (var i : x.facePoints.values()) {
            StringBuilder builder = new StringBuilder();
            for (var j : i.faceId) {
                if (j != -1) {
                    builder.append(j + ",");
                }
            }
            System.out.println(builder.toString());
        }
    }

    public void testSkeletonCube3() {
        /*
        * int[][] thirdSword = {
            {0, 9, 51}, {1, 52}, {2, 29, 53}, {3, 10}, {4},
            {5, 28},
            {6, 11, 18}, {7, 19}, {8, 20, 27},
            {12, 48}, {13}, {14, 21}, {15, 42, 45},
            {16, 39}, {17, 24, 36},
            {22}, {23, 30}, {25, 37}, {26, 33, 38},
            {31}, {32, 50}, {34, 41}, {35, 44, 47},
            {40}, {43, 46}, {49}
    };
        * */
        var x = new SkeletonCube(3);
        for (var i : x.facePoints.values()) {
            StringBuilder builder = new StringBuilder();
            for (var j : i.faceId) {
                if (j != -1) {
                    builder.append(j + ",");
                }
            }
            System.out.println(builder.toString());
        }
    }


}
