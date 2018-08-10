package cube;

import haha.Converter;

/**
 * 二阶魔方的状态
 */
public class Mini {
public int[] pos;
public int code;
public int state;
static final int[] pow = {1, 3, 9, 27, 81, 243, 729};
static final int[] factorial = {1, 1, 2, 6, 24, 120, 720};

public Mini() {
    pos = new int[7];
}

public Mini(String question) {
    int[][] positionAndState = new Converter(question).a;
    set(positionAndState);
}

public Mini(int[][] positionAndState) {
    set(positionAndState);
}

public Mini(int[] pos, int state, int code) {
    this.pos = pos;
    this.state = state;
    this.code = code;
}

public void set(int[][] positionAndState) {
    this.pos = positionAndState[0];
    this.state = Mini.hashState(positionAndState[1]);
    this.code = Mini.hashPosition(this.pos) * 729 + this.state;
}

public static Mini getInitNode() {
    Mini node = new Mini();
    for (int i = 0; i < 7; i++) {
        node.pos[i] = i;
    }
    node.state = 0;
    node.code = 0;
    return node;
}

// 状态哈希
public static int hashState(int[] state) {
    int ans = 0;
    for (int i = 0; i < 6; i++) {
        ans += pow[i] * state[i];
    }
    return ans;
}

public Mini go(int op, boolean moveAll) {
    int state = this.state;
    int pos[] = new int[7];
    for (int i = 0; i < 7; i++) {
        int p = this.pos[i];
        //只移动op面上的小块
        if (moveAll || (p & (1 << op)) == 0) {
            if (i < 6) {
                int s = (this.state / (int) Math.pow(3, i)) % 3;
                int ns = (6 - op - s) % 3;
                state = state - (s - ns) * (int) Math.pow(3, i);
            }
            int op1 = (op + 1) % 3, op2 = (op + 2) % 3;
            pos[i] = p & (1 << op) | (1 - ((p >> op1) & 1) << op2) | ((p >> op2) & 1) << op1;
        } else {
            pos[i] = this.pos[i];
        }
    }
    return new Mini(pos, state, hashPosition(pos) * 729 + state);
}

public Mini go(int op) {
    return go(op, false);
}

public Mini go(String operation) {
    Mini now = this;
    for (char o : operation.toCharArray()) {
        int op = "左后下".indexOf(o);
        now = now.go(op);
    }
    return now;
}

//整体运动
public Mini bigGo(int op) {
    return go(op, true);
}

// 位置哈希，全排列散列算法
public static int hashPosition(int[] position) {
    int ans = 0;
    for (int i = 0; i < 7; i++) {
        int k = 0;
        for (int j = i + 1; j < 7; j++) {
            if (position[j] < position[i]) {
                k++;
            }
        }
        ans += factorial[6 - i] * k;
    }
    return ans;
}

}
