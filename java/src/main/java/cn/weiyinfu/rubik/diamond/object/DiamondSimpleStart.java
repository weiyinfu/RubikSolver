package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Displace;

/*
 * n阶四面体魔方：初始状态使用从0开始依次编号，不再使用颜色
 * */
public class DiamondSimpleStart extends DiamondSimple {
    public DiamondSimpleStart(int n) {
        super(n);
    }

    @Override
    public int[] newStart() {
        return Displace.arange(finder.skeleton.totalFace);
    }
}
