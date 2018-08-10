package haha;

import cube.Mini;

/**
 * 公式工具
 */
public class FormulaUtil {
//化简公式
static String simple(String operation) {
    return new TableSolver().solve(Mini.getInitNode().go(operation).code);
}

public static void main(String[] args) {
    System.out.println(simple("左下后"));
}
}
