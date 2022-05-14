package cn.weiyinfu.rubik.diamond;

import java.util.Random;

public class Zobrist {
    final long[][] zob;

    Zobrist(int rows, int cols) {

        zob = new long[rows][cols];
        Random r = new Random(0);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                zob[i][j] = r.nextLong();
            }
        }

    }

    public long calculateHash(int[] a) {
        long s = 0;
        for (int i = 0; i < a.length; i++) {
            s ^= zob[i][a[i]];
        }
        return s;
    }

}
