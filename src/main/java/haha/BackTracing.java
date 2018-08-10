package haha;

import cube.Mini;
import input.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static haha.TableGenerator.aim;

/**
 * 回溯法求解二阶魔方
 */
public class BackTracing implements Solver {
int limit = 19;
boolean vis[] = new boolean[5040 * 729];
Mini input;
List<String> ans = new ArrayList<>();

void run(Mini now, String operation) {
    if (now.code == input.code) {
        limit = operation.length();
        ans.add(new StringBuilder(operation).reverse().toString());
        return;
    }
    if (operation.length() >= limit)
        return;

    for (int i = 0; i < 3; i++) {
        Mini next = now.go(i);
        if (vis[next.code])
            continue;
        vis[next.code] = true;
        run(next, operation + "后左下".charAt(i));
        vis[next.code] = false;
    }
}

List<String> solve(int[][] positionAndState) {
    return go(new Mini(positionAndState));
}

public String solve(String question) {
    return go(new Mini(question)).stream().collect(Collectors.joining("\n"));
}

List<String> go(Mini mini) {
    ans.clear();
    input = mini;
    vis[aim.code] = true;
    run(aim, "");
    return ans;
}


@Override
public int getN() {
    return 2;
}

public static void main(String[] args) {
    BackTracing back = new BackTracing();
    // back.solve(new int[][] { new int[] { 1, 0, 2, 3, 4, 5, 6, 7 }, new
    // int[] { 2,2, 0, 0, 0, 0, 0, 0 } })
    // .forEach(System.out::println);
    String ans = back.solve(TableSolver.inputFromFile("haha.txt"));
    System.out.println(ans);
}
}
