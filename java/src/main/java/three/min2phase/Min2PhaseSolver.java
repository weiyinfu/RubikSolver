package three.min2phase;

import cube.Cube;
import cube.FormulaTransformer;
import cube.Operation;
import cube.OperationList;
import input.Solver;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Min2PhaseSolver implements Solver {
@Override
public String solve(String input) {
    String to = "ULFRBD";
    String from = "wgrboy";
    int[] face = {0, 3, 2, 4, 1, 5};
    char[] a = new char[54];
    for (int i = 0; i < input.length(); i++) {
        a[i] = to.charAt(from.indexOf(input.charAt(i)));
    }
    //交换底面和后面
    for (int i = 0; i < 5; i++) {
        char temp = a[45 + i];
        a[45 + i] = a[53 - i];
        a[53 - i] = temp;
    }
    StringBuilder builder = new StringBuilder(54);
    for (int i = 0; i < 6; i++) {
        builder.append(Arrays.copyOfRange(a, face[i] * 9, face[i] * 9 + 9));
    }
    //十二种操作集：B2 L2 U  D2 R2 D' B  R2 U2 R2 L  B2 D2 F2 U2 R2 B2 F
    String twelveOperation = new Search().solution(builder.toString(), 21, 10000, 0, 0);
    if (twelveOperation.trim().startsWith("Error")) return twelveOperation;
    //需要把十二种操作转化成标准操作（6种操作）
    System.out.println(twelveOperation);
    String[] ops = twelveOperation.split("\\s+");
    String[][] operationMapArray = {
            {"R", "左2左3左3左3"},
            {"U", "下2下3下3下3"},
            {"F", "后2后3后3后3"},
            {"L", "左"},
            {"D", "下"},
            {"B", "后"}};
    Map<String, List<Operation>> operationMap = new TreeMap<>();
    for (String[] operationReplace : operationMapArray) {
        List<Operation> op = new OperationList(operationReplace[1]);
        operationMap.put(operationReplace[0], op);
    }
    Pattern pattern = Pattern.compile("^([LRBFUD]'?)(\\d*)$");
    OperationList operationList = new OperationList();
    for (String s : ops) {
        //发现异常操作，立即返回
        Matcher matcher = pattern.matcher(s);
        if (!matcher.find()) return twelveOperation;
        String op = matcher.group(1);
        int repeat = 1;
        if (matcher.group(2).length() > 0) {
            repeat = Integer.parseInt(matcher.group(2));
        }
        //把逆操作消除掉，只留正操作
        if (op.endsWith("'")) {
            op = op.substring(0, 1);
            repeat *= 3;
            repeat %= 4;
        }
        //把重复次数消掉
        for (int j = 0; j < repeat; j++) {
            operationList.addAll(operationMap.get(op));
        }
    }
    String opStr = operationList.toString();
    opStr = opStr.replaceAll("左3", "D").replaceAll("下3", "R").replaceAll("后3", "RDRRR");
    opStr = FormulaTransformer.eliminateDR(opStr, this.getN());
    operationList = new OperationList(opStr);
    return operationList.toFormatString();
}

@Override
public int getN() {
    return 3;
}

public static void main(String[] args) {
    Solver s = new Min2PhaseSolver();
//    String question = "grbwwygywygwwgbyywogbrrrbggooygbbrowroybybrwgborrowoyo";
    String question="bwoywwrwwybggbygwoyrrrobwoybrwrgrbyggyogygwgogoyggbrbb";
    System.out.println(question.length());
    String ans = s.solve(question);
    System.out.println(ans);
    Cube cube = new Cube(question);
    cube.go(ans);
    System.out.println(cube);
}
}
