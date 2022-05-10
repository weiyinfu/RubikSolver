package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Displace;

import java.util.Map;

/*
* n阶魔方provider，不同之处在于start局面是从0开始依次编号
* */
public class CubeStart extends Cube {

    public CubeStart(int n) {
        super(n);
    }

    @Override
    public int[] newStart() {
        return Displace.arange(6 * n * n);
    }

    @Override
    public Map<String, Integer> getColorMap(String s) {
        throw new RuntimeException("unsupported");
    }
}
