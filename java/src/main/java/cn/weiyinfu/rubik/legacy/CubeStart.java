package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Displace;
import cn.weiyinfu.rubik.diamond.FacePieceMap;
import cn.weiyinfu.rubik.diamond.finder.SkeletonCube;

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
    public int[] parseState(String s) {
        //只考虑二阶魔方
        if (n == 2 || n == 3) {
            //给定颜色求置换
            int[] a = super.parseState(s);
            var ma = new FacePieceMap(super.newStart(), new SkeletonCube(n).getSword());
            a = ma.solve(a);
            return a;
        } else {
            throw new RuntimeException("不太确定N阶魔方是否与Start模式一一映射");
        }
    }
}
