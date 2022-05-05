package cn.weiyinfu.rubik.diamond;

import cn.weiyinfu.rubik.cube.Cube;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/*
 * 正方体魔方的置换求解器
 * */
public class DisplaceFinderCube {
    public final Map<String, int[]> operations;

    //适用于正方体的置换寻找器
    public DisplaceFinderCube(int n) {
        Cube c = new Cube(n);
        var a = new int[6][n][n];
        var l = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    a[i][j][k] = l++;
                }
            }
        }
        c.colors = a;
        operations = new TreeMap<String, int[]>();
        for (int i = 1; i < n; i++) {
            submit(0, i, String.format("后%s", i), c);
            submit(1, i, String.format("左%s", i), c);
            submit(2, i, String.format("下%s", i), c);
        }
    }

    void submit(int op, int layer, String name, Cube c) {
        var cc = c.copy();
        cc.go(op, layer);
        var displace = flat(cc.colors);
        operations.put(name, displace);
    }

    public int[] flat(int[][][] a) {
        var b = new int[6 * a[0].length * a[0].length];
        var l = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < a[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    b[l++] = a[i][j][k];
                }
            }
        }
        return b;
    }

    public static void main(String[] args) {
        var x = new DisplaceFinderCube(2);
        for (var i : x.operations.entrySet()) {
            System.out.println(i.getKey() + "=>" + Arrays.toString(i.getValue()));
        }
    }
}
