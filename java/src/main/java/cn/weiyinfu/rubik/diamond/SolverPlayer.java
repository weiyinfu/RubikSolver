package cn.weiyinfu.rubik.diamond;

import java.util.*;

/*
 * 给定一个求解器，测试这个求解器是否正确
 * */
public class SolverPlayer {

    private final Solver solver;
    private final Provider p;
    List<Operation> operations;

    SolverPlayer(Provider p, Solver solver) {
        this.p = p;
        this.solver = solver;
        operations = p.getOperations();
    }

    int[] getShuffleState(int n) {
        return getShuffleState(n, p.newStart());
    }

    int[] getShuffleState(int n, int[] start) {
        Random r = new Random();
        List<Integer> opList = new ArrayList<>(n);
        for (var i = 0; i < n; i++) {
            var ind = r.nextInt(operations.size());
            opList.add(ind);
        }
        return executeOps(start, opList, false);
    }

    int[] executeOps(int[] a, List<Integer> ops, boolean reverse) {
        for (int op : ops) {
            var o = operations.get(op);
            var displace = reverse ? o.reverseDisplace : o.displace;
            a = Displace.mul(a, displace);
        }
        return a;
    }

    List<Integer> solve(int[] a) {
        //给定一个状态求解法
        return solver.solve(a);
    }

    boolean testSolver(int[] a) {
        var ops = solve(a);
        var des = executeOps(a, ops, false);
        var start = p.newStart();
        return Arrays.equals(start, des);
    }

    boolean testSolver(int shuffleCount) {
        var randomState = getShuffleState(shuffleCount);
        return testSolver(randomState);
    }

    void testSolverMultiTimes(int shuffleCount, int n, boolean verbose) {
        var right = 0;

        for (int i = 0; i < n; i++) {
            try {

                var res = testSolver(shuffleCount);
                if (res) {
                    right++;
                }
                if (verbose) {
                    System.out.println(i + ":" + res);
                }
            } catch (Exception e) {

            }
        }
        System.out.println(String.format("正确率：%s/%s", right, n));
    }

    public boolean testParseState() {
        var a = getShuffleState(1000);
        var aSet = new TreeSet<Integer>();
        for (var i : a) {
            aSet.add(i);
        }
        if (aSet.size() > 26) {
            throw new RuntimeException("too many colors");
        }

        var ids = new ArrayList<>(aSet);
        //数组下标置换
        var dis = Displace.randomDisplace(ids.size());
        var colors = new StringBuilder();
        for (int j : a) {
            colors.append((char) (dis[ids.get(j)] + 'a'));
        }
        var recovered = p.parseState(colors.toString());
        return Arrays.equals(a, recovered);
    }

    public void testParseStateMulti(int n) {
        var right = 0;
        for (int i = 0; i < n; i++) {
            var res = testParseState();
            if (res) {
                right++;
            }
            System.out.println(i + "=>" + res);
        }
        System.out.printf("right/total=%s/%s", right, n);
    }
}
