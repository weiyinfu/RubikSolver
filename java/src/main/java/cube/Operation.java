package cube;

/**
 * 一个操作就是一个（type，cnt）二元组
 */
public class Operation {
int type;
int cnt;

Operation(int type, int cnt) {
    this.type = type;
    this.cnt = cnt;
}

public Operation(String operation) {
    operation = operation.trim();
    type = "后左下".indexOf(operation.charAt(0));
    if (operation.length() == 1) {
        cnt = 1;
    } else {
        cnt = Integer.parseInt(operation.substring(1));
    }
}

static int parseChar(char c) {
    if ("后左下".indexOf(c) != -1) return "后左下".indexOf(c);
    if ("bld".indexOf(Character.toLowerCase(c)) != -1) return "bld".indexOf(Character.toLowerCase(c));
    return -1;
}

@Override
public String toString() {
    String op = String.valueOf("后左下".charAt(this.type));
    if (this.cnt == 1) return op;
    else
        return op + "" + this.cnt;
}


}
