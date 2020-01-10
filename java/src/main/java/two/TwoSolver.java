package two;

import input.Solver;

public abstract class TwoSolver implements Solver {
abstract String getAns(String input);

@Override
public String solve(String input) {
    String validateResult = Validator.validate(Mini.from(input).extend().a);
    if (validateResult != null) return validateResult;
    return getAns(input);
}

@Override
public int getN() {
    return 2;
}

}
