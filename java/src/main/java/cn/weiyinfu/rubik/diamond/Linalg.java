package cn.weiyinfu.rubik.diamond;

/*
 * 线性代数运算集合
 * */
public class Linalg {
    //定比分点，ratio从0到1，距离from越来越远
    public static V3 dingBiFenDian(V3 f, V3 t, double ratio) {
        return new V3(
                f.x * (1 - ratio) + t.x * ratio,
                f.y * (1 - ratio) + t.y * ratio,
                f.z * (1 - ratio) + t.z * ratio
        );
    }

    //p点绕着直线f、t旋转alpha角度
    public static V3 turnByAxis(V3 f, V3 t, V3 p, double alpha) {
        //p点绕直线旋转一定度数，得到一个新点
        var n = t.sub(f).resize(1);
        var K = 1 - Math.cos(alpha);
        var M = dot(n, t);

        var mat = new double[][]{
                {n.x * n.x * K + Math.cos(alpha), n.x * n.y * K - n.z * Math.sin(alpha), n.x * n.z * K + n.y * Math.sin(alpha), (t.x - n.x * M) * K + (n.z * t.y - n.y * t.z) * Math.sin(alpha)},
                {n.x * n.y * K + n.z * Math.sin(alpha), n.y * n.y * K + Math.cos(alpha), n.y * n.z * K - n.x * Math.sin(alpha), (t.y - n.y * M) * K + (n.x * t.z - n.z * t.x) * Math.sin(alpha)},
                {n.x * n.z * K - n.y * Math.sin(alpha), n.y * n.z * K + n.x * Math.sin(alpha), n.z * n.z * K + Math.cos(alpha), (t.z - n.z * M) * K + (n.y * t.x - n.x * t.y) * Math.sin(alpha)},
                {0, 0, 0, 1},
        };
        var ans = matmul(mat, new double[]{p.x, p.y, p.z, 1});
        return new V3(ans[0], ans[1], ans[2]);
    }

    //在点f和点t之间插入n个点
    public static V3[] linspace(V3 f, V3 t, int n) {
        V3[] ans = new V3[n];
        if (n == 1) {
            return new V3[]{f};
        }
        for (int i = 0; i < n; i++) {
            ans[i] = dingBiFenDian(f, t, i * 1.0 / (n - 1));
        }
        return ans;
    }

    //给定一堆点，求他们的重心
    public static V3 mean(V3... a) {
        V3 v = new V3(0, 0, 0);
        for (var i : a) {
            v.x += i.x;
            v.y += i.y;
            v.z += i.z;
        }
        v.x /= a.length;
        v.y /= a.length;
        v.z /= a.length;
        return v;
    }

    //矩阵乘以向量
    public static double[] matmul(double[][] matrix, double[] v) {
        var a = new double[v.length];
        for (int i = 0; i < matrix.length; i++) {
            var s = 0.;
            for (int j = 0; j < matrix[i].length; j++) {
                s += matrix[i][j] * v[j];
            }
            a[i] = s;
        }
        return a;
    }

    //矩阵相乘
    public static double[][] matmul(double[][] A, double[][] B) {
        if (A[0].length != B.length) {
            throw new RuntimeException("Matrix not match");
        }
        var a = new double[A.length][B[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                var s = 0.;
                for (int k = 0; k < A[0].length; k++) {
                    s += A[i][k] * B[k][j];
                }
                a[i][j] = s;
            }
        }
        return a;
    }

    //矩阵转置
    public static double[][] transpose(double[][] a) {
        var ans = new double[a[0].length][a.length];
        for (int i = 0; i < ans.length; i++) {
            for (int j = 0; j < ans[i].length; j++) {
                ans[i][j] = a[j][i];
            }
        }
        return ans;
    }

    public static String tos(double[][] a) {
        StringBuilder builder = new StringBuilder();
        for (double[] doubles : a) {
            for (double aDouble : doubles) {
                builder.append(aDouble).append(",");
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    public static double dot(V3 p, V3 q) {
        return p.x * q.x + p.y * q.y + p.z * q.z;
    }

    public static double distance(V3 f, V3 t) {
        var dx = f.x - t.x;
        var dy = f.y - t.y;
        var dz = f.z - t.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    //点p到面ABC的距离
    public static double distanceToFace(V3 A, V3 B, V3 C, V3 p) {
        //点p到面ABC的距离
        var a = ((B.y - A.y) * (C.z - A.z) - (B.z - A.z) * (C.y - A.y));
        var b = ((B.z - A.z) * (C.x - A.x) - (B.x - A.x) * (C.z - A.z));
        var c = ((B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x));
        var v = new V3(a, b, c);
        return Math.abs((dot(v, p) - dot(v, C)) / v.length());
    }

    public static int[] displaceMultiply(int[] A, int[] B) {
        if (A.length != B.length) {
            throw new RuntimeException("A and B is not match");
        }
        int[] ans = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            ans[i] = A[B[i]];
        }
        return ans;
    }

    public static int[] arange(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    public static int[] displacePower(int[] displace, int n) {
        var ans = arange(displace.length);
        for (int i = 0; i < n; i++) {
            ans = displaceMultiply(ans, displace);
        }
        return ans;
    }

    public static int[] displaceReverse(int[] displace) {
        //求置换的逆元
        var a = new int[displace.length];
        for (int i = 0; i < a.length; i++) {
            a[displace[i]] = i;
        }
        return a;
    }
}
