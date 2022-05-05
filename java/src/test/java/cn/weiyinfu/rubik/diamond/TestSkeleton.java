package cn.weiyinfu.rubik.diamond;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestSkeleton extends TestCase {
    public void testSkeleton() {
        var x = new Skeleton(3);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println("骨骼点：" + i);
        }
        System.out.println("骨骼点个数" + x.facePoints.size());
        //角块4个，边块6个，面块4个
        for (var i : x.facePoints.values()) {
            System.out.println("小四面体对应的面数" + i.count());
        }
        int faceCount = x.facePoints.values().stream().map(Skeleton.FacePoint::count).reduce(Integer::sum).get();
        System.out.println("总面数：" + faceCount + " " + x.totalFace);
    }

    public void testSkeleton2() {
        var x = new Skeleton(1);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println(i);
        }
    }

    public void testTwelveState() {
        var x = new TwelveState();
        for (int i = 0; i < x.stateTable.length; i++) {
            System.out.print(Arrays.toString(x.nodes[i].a) + ":");
            for (int j = 0; j < x.stateTable[i].length; j++) {
                System.out.print(x.stateTable[i][j] + ",");
            }
            System.out.println();
        }
    }

}
