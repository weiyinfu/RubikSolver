package cn.weiyinfu.rubik.diamond.obj;

import cn.weiyinfu.rubik.diamond.*;
import cn.weiyinfu.rubik.diamond.finder.DisplaceFinderCube;
import cn.weiyinfu.rubik.two.Mini;

import java.util.*;

public class Cube implements Provider, ColorString.ColorMap {
    protected final DisplaceFinderCube finder;
    protected final int n;

    public Cube(int n) {
        this.n = n;
        this.finder = new DisplaceFinderCube(n);
    }

    @Override
    public int[] newStart() {
        var c = new cn.weiyinfu.rubik.cube.Cube(n);
        return finder.flat(c.colors);
    }

    @Override
    public List<Operation> getOperations() {
        return OperationList.fromMap(finder.operations);
    }

    @Override
    public int[] parseState(String s) {
        return ColorString.string2displace(s, this);
    }

    @Override
    public Map<String, Integer> getColorMap(String s) {
        var ma = new TreeMap<String, Integer>();
        //给定一个颜色列表，为每个颜色进行编号
        ma.put(s.charAt(n * n - 1) + "", 0);
        ma.put(s.charAt(n * n * 2 + n - 1) + "", 2);
        ma.put(s.charAt(n * n * 3) + "", 3);

        //寻找每个颜色的对面
        char[] a = new char[6 * 2 * 2];
        var l = 0;
        for (int i = 0; i < 6; i++) {
            a[l++] = s.charAt(i * n * n);
            a[l++] = s.charAt(i * n * n + n - 1);
            a[l++] = s.charAt(i * n * n + n * n - n);
            a[l++] = s.charAt(i * n * n + n * n - 1);
        }

        Set<String> hadSet = new TreeSet<>(ma.keySet());
        for (var i : Mini.sword) {
            var nowSet = new TreeSet<String>();
            for (var j : i) {
                nowSet.add(a[j] + "");
            }
            var nowHas = new TreeSet<>(nowSet);
            nowHas.removeAll(hadSet);
            var onlyHad = new TreeSet<>(hadSet);
            onlyHad.removeAll(nowSet);
            if (nowHas.size() == 1 && onlyHad.size() == 1) {
                var now = nowHas.first();
                var faceId = ma.get(onlyHad.first());
                ma.put(now, cn.weiyinfu.rubik.cube.Cube.opsite[faceId]);
            }
        }
        return ma;
    }
}
