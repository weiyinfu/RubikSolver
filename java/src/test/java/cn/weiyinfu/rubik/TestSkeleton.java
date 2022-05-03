package cn.weiyinfu.rubik;

import cn.weiyinfu.rubik.diamond.DisplaceFinder;
import cn.weiyinfu.rubik.diamond.Skeleton;
import cn.weiyinfu.rubik.diamond.TwelveState;
import junit.framework.TestCase;

import java.util.Arrays;

public class TestSkeleton extends TestCase {
    public void testSkeleton() {
        var x = new Skeleton(3);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println(i);
        }
        System.out.println(x.facePoints.size());
        //角块4个，边块6个，面块4个
        for (var i : x.facePoints.values()) {
            System.out.println(i.count());
        }
        int faceCount = x.facePoints.values().stream().map(Skeleton.FacePoint::count).reduce(Integer::sum).get();
        System.out.println(faceCount + " " + x.totalFace);
    }

    public void testSkeleton2() {
        var x = new Skeleton(1);
        System.out.println(x.a.size());
        for (var i : x.a) {
            System.out.println(i);
        }
    }

    public void testDisplace() {
        var finder = new DisplaceFinder(2);
        DisplaceFinder.Diamond m = finder.NewStartNode();
        System.out.println(m);
//        System.out.println("skeleton:" + m.getSkeleton());
//        System.out.println("colors:" + Arrays.toString(m.getFaces()));
        System.out.println(Arrays.toString(finder.left(1)));
    }

    public void testRotate2() {
        var finder = new DisplaceFinder(3);
        DisplaceFinder.Diamond m = finder.NewStartNode();
        System.out.println(m);
        //测试左面
        m = m.rotate(2, 2, true);
        System.out.println(m);
    }

    public void testRotate() {
        var finder = new DisplaceFinder(2);
        DisplaceFinder.Diamond m = finder.NewStartNode();
        System.out.println(m);
        System.out.println("skeleton:" + m.getSkeleton());
        for (int i = 0; i < 3; i++) {
            DisplaceFinder.Diamond nex = m.rotate(1, 1, true);
            System.out.println(nex);
            m = nex;
        }
    }

    public void testPrintDisplace() {
        DisplaceFinder f = new DisplaceFinder(3);
        var operations = f.getOperations();
        for (var i : operations.entrySet()) {
            var x = i.getValue();
            System.out.printf("%s=>%s\n", i.getKey(), Arrays.toString(i.getValue()));
            System.out.printf("%s,%s,%s\n", x[4], x[9], x[18]);
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
