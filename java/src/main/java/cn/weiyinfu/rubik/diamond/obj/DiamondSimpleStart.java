package cn.weiyinfu.rubik.diamond.obj;

import cn.weiyinfu.rubik.diamond.Displace;

public class DiamondSimpleStart extends DiamondSimple {
    public DiamondSimpleStart(int n) {
        super(n);
    }

    @Override
    public int[] newStart() {
        return Displace.arange(finder.skeleton.totalFace);
    }
}
