package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Displace;
import cn.weiyinfu.rubik.diamond.FacePieceMap;
import cn.weiyinfu.rubik.diamond.finder.SkeletonDiamond;

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

    @Override
    public int[] parseState(String colorString) {
        if (n == 2 || n == 3) {
            //给定颜色求置换
            int[] a = super.parseState(colorString);
            var x = super.newStart();
            var ma = new FacePieceMap(x, new SkeletonDiamond(n).getSword());
            return ma.solve(a);
        } else {
            throw new RuntimeException("不太确定是否为一一映射");
        }
    }
}
