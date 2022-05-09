package cn.weiyinfu.rubik.diamond;

import java.util.Random;

/**
 * 置换相关操作
 */
public class Displace {

    public static int[] mul(int[] A, int[] B) {
        if (A.length != B.length) {
            throw new RuntimeException(String.format("A and B is not match:A.length=%s,B.length=%s", A.length, B.length));
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

    public static int[] power(int[] displace, int n) {
        var ans = arange(displace.length);
        for (int i = 0; i < n; i++) {
            ans = mul(ans, displace);
        }
        return ans;
    }

    public static int[] reverse(int[] displace) {
        //求置换的逆元
        var a = new int[displace.length];
        for (int i = 0; i < a.length; i++) {
            a[displace[i]] = i;
        }
        return a;
    }

    public static int[] div(int[] a, int[] b) {
        return mul(a, reverse(b));
    }

    public static int[] divFast(int[] a, int[] b) {
        //直接进行映射
        var c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            c[b[i]] = a[i];
        }
        return c;
    }

    static Random r = new Random();

    public static int[] randomDisplace(int n) {
        var a = arange(n);
        for (int i = 0; i < a.length; i++) {
            var ind = i + r.nextInt(a.length - i);
            var temp = a[i];
            a[i] = a[ind];
            a[ind] = temp;
        }
        return a;
    }

}
