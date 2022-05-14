package cn.weiyinfu.rubik.legacy;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Start模式与颜色模式的互相转换，sword表示每个小块所对应的下标，color表示平面图中每个小面的颜色
 */
public class FacePieceMap {
    private final int[][] sword;
    Piece[] pieces;

    class Piece {
        int index;
        int color;
        String pieceKey;

        Piece(int index, int color) {
            this.index = index;
            this.color = color;
        }
    }

    public FacePieceMap(int[] color, int[][] sword) {
        this.sword = sword;
        pieces = new Piece[color.length];
        for (int i = 0; i < pieces.length; i++) {
            pieces[i] = new Piece(i, color[i]);
        }
        for (int[] ints : sword) {
            var pieceKey = getColorKey(color, ints);
            for (int j : ints) {
                pieces[j].pieceKey = pieceKey;
            }
        }
    }

    int getIndex(String pieceKey, int color) {
        //根据三种颜色找到下标
        for (var i : pieces) {
            if (i.color == color && i.pieceKey.equals(pieceKey)) {
                return i.index;
            }
        }
        throw new RuntimeException("cannot find piece");
    }

    String getColorKey(int[] obj, int[] piece) {
        return Arrays.stream(piece).mapToObj(x -> Integer.toString(obj[x])).sorted().collect(Collectors.joining(","));
    }

    public int[] solve(int[] a) {
        var ans = new int[a.length];
        for (int[] small : sword) {
            var pieceKey = getColorKey(a, small);
            for (int j : small) {
                ans[j] = getIndex(pieceKey, a[j]);
            }
        }
        return ans;
    }
}
