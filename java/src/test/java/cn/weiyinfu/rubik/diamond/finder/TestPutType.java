package cn.weiyinfu.rubik.diamond.finder;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestPutType extends TestCase {

    public void testPutType() {
        var x = new PutTypeDiamond();
        for (int i = 0; i < x.stateTable.length; i++) {
            System.out.print(Arrays.toString(x.nodes[i].a) + ":");
            for (int j = 0; j < x.stateTable[i].length; j++) {
                System.out.print(x.stateTable[i][j] + ",");
            }
            System.out.println();
        }
    }
}
