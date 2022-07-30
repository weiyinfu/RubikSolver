package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.*;
import cn.weiyinfu.rubik.diamond.finder.DisplaceFinderDiamond;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*
 * n阶四面体魔方
 * */
public class Diamond implements ColorString.ColorMap, Provider {
    protected final DisplaceFinderDiamond finder;
    final int n;

    public Diamond(int n) {
        this.n = n;
        this.finder = new DisplaceFinderDiamond(n);
    }

    @Override
    public Map<String, Integer> getColorMap(String s) {
        /*
         * 根据当前的颜色，确定一个颜色映射。
         * 如果以最顶层的那个小角为参考系确定颜色映射，存在的问题是：对于DiamondSimple模式不考虑四个小角，导致必须引入"下2"这个整体旋转才能查表得到。所以不能以最顶层的那个小角为参考系确定颜色映射。
         * 以去掉4个小角之后的最上层颜色确定，找到0，1，2,3所对应的颜色
         * */
        var perFace = finder.skeleton.perFace;
        var colorMap = new TreeMap<String, Integer>();
        colorMap.put(s.charAt((2 * n - 1) - 2) + "", 0);
        colorMap.put(s.charAt(perFace + 2) + "", 1);
        colorMap.put(s.charAt(perFace * 2 + 1) + "", 2);
        //找到对面的那个小块
        for (var i = 0; i < s.length(); i++) {
            var k = s.substring(i, i + 1);
            if (colorMap.containsKey(k)) {
                continue;
            }
            colorMap.put(k, 3);
            break;
        }
        return colorMap;
    }

    @Override
    public int[] newStart() {
        var totalFace = finder.skeleton.totalFace;
        var perFace = finder.skeleton.perFace;
        var a = new int[totalFace];
        for (int i = 0; i < a.length; i++) {
            a[i] = i / perFace;
        }
        return a;
    }

    @Override
    public List<Operation> getOperations() {
        return OperationList.fromMap(finder.getOperations());
    }

    @Override
    public int[] parseState(String colorString) {
        return ColorString.string2displace(colorString, this);
    }
}
