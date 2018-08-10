package min2phase;

import input.Solver;

import java.util.Arrays;

public class Min2PhaseSolver implements Solver {
String to = "ULFRBD";
String from = "wgrboy";

@Override
public String solve(String input) {
    int[] face = {0, 3, 2, 4, 1, 5};
    char[] a = new char[54];
    for (int i = 0; i < input.length(); i++) {
        a[i] = to.charAt(from.indexOf(input.charAt(i)));
    }
    for (int i = 0; i < 5; i++) {
        char temp = a[45 + i];
        a[45 + i] = a[53 - i];
        a[53 - i] = temp;
    }
    StringBuilder builder = new StringBuilder(54);
    for (int i = 0; i < 6; i++) {
        builder.append(Arrays.copyOfRange(a, face[i] * 9, face[i] * 9 + 9));
    }
    return new Search().solution(builder.toString(), 21, 10000, 0, 0);
}

@Override
public int getN() {
    return 3;
}

public static void main(String[] args) {
    Solver s = new Min2PhaseSolver();
    String question = "grbwwygywygwwgbyywogbrrrbggooygbbrowroybybrwgborrowoyo";
    System.out.println(question.length());
    String ans = s.solve(question);
    System.out.println(ans);
}
}
