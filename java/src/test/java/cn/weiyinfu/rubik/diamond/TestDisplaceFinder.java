package cn.weiyinfu.rubik.diamond;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestDisplaceFinder extends TestCase {

    public void testDisplace() {
        var finder = new DisplaceFinder(2);
        DisplaceFinder.Diamond m = finder.NewStartNode();
        System.out.println(m);
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
            System.out.printf("顶部块：%s,%s,%s\n", x[4], x[9], x[18]);
        }
    }

}
