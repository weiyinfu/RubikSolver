package cn.weiyinfu.rubik.diamond.obj;

import cn.weiyinfu.rubik.diamond.Displace;

import java.util.Map;

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
