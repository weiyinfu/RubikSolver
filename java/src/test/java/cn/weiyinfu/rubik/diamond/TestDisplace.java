package cn.weiyinfu.rubik.diamond;

import junit.framework.TestCase;

import java.util.Arrays;

public class TestDisplace extends TestCase {
    public void testDiv() {
        var x = Displace.randomDisplace(5);
        var y = Displace.randomDisplace(5);
        var z = Displace.mul(x, y);
        var xx = Displace.div(z, y);
        var xxx = Displace.divFast(z, y);
        System.out.println(Arrays.toString(x));
        System.out.println(Arrays.toString(xx));
        System.out.println(Arrays.toString(xxx));
    }
}
