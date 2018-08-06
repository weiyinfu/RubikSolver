package haha;

import java.util.ArrayList;
import java.util.List;

import static haha.TableGenerator.*;

/**
 * 回溯法求解二阶魔方
 */
public class BackTracing {
int limit = 19;
boolean vis[] = new boolean[5040 * 729];
Node input;
List<String> ans = new ArrayList<>();

void run(Node now, String operation) {
    if (now.code == input.code) {
        limit = operation.length();
        ans.add(new StringBuilder(operation).reverse().toString());
        return;
    }
    if (operation.length() >= limit)
        return;

    for (int i = 0; i < 3; i++) {
        Node next = go(i, now);
        if (vis[next.code])
            continue;
        vis[next.code] = true;
        run(next, operation + "后左下".charAt(i));
        vis[next.code] = false;
    }
}

List<String> solve(int[][] positionAndState) {
    ans.clear();
    input = new Node();
    input.pos = positionAndState[0];
    input.state = hashState(positionAndState[1]);
    input.code = hashPosition(input.pos) * 729 + input.state;
    vis[aim.code] = true;
    run(aim, "");
    return ans;
}

List<String> solve(String question) {
    return solve(new Converter(question).a);
}

public static void main(String[] args) {
    BackTracing back = new BackTracing();
    // back.solve(new int[][] { new int[] { 1, 0, 2, 3, 4, 5, 6, 7 }, new
    // int[] { 2,2, 0, 0, 0, 0, 0, 0 } })
    // .forEach(System.out::println);
    back.solve(TableSolver.inputFromFile("haha.txt")).forEach(System.out::println);
}
}
