package cn.weiyinfu.rubik.diamond;

public class Diamond {
    public int n;//四面体魔方的阶数
    public int[][] faces;

    /**
     * 四个面：正面（0），左面（1），右面（2），下面（3）
     * 四个角：上面（0），左面（1），右面（2），后面（3）
     * 旋转的时候使用左手定则，大拇指指向角的方向
     * 操作种数：n-1+n*(n-1),n-1表示底部操作，n*(n-1)表示n个角，每个角有n-1层可以动。
     */
    public Diamond(int n) {
        this.n = n;
        var faceCount = 0;
        for (int i = 0; i < n; i++) {
            faceCount += (i * 2) + 1;
        }
        faces = new int[n][faceCount];
    }

    void op(int point, int layer) {
        //移动以point角为中心的layer层

    }
    void down(int layer){
        //移动底部的几层
    }
}
