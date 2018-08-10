package haha;

import cube.Mini;
import input.Solver;

//两公式求解器
public class TwoFormulaSolver implements Solver {
String swapPosition = "下后下下后左后后左下左后后后左";
String swapState = "后后左后下下后后下左后左左左下左下下";

TwoFormulaSolver() {

}


@Override
public String solve(String input) {
    Mini now=new Mini(input);

    return null;
}

@Override
public int getN() {
    return 2;
}

public static void main(String[] args) {

}

}
