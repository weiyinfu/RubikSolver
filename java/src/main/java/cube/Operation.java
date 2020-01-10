package cube;

import java.util.ArrayList;
import java.util.List;
/**
 * 一个操作就是一个（type，cnt）二元组
 * */
public class Operation {
int type;
int cnt;

Operation(int type, int cnt) {
    this.type = type;
    this.cnt = cnt;
}

Operation(String operation) {
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

static List<Operation> parse(String s) {
    List<Operation> a = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
        if (Character.isSpaceChar(s.charAt(i))) {
            i++;
            continue;
        }
        int type = parseChar(s.charAt(i));
        if (type != -1) {
            int cnt = 0;
            for (i++; i < s.length() && Character.isDigit(s.charAt(i)); i++) {
                cnt = cnt * 10 + (s.charAt(i) - '0');
            }
            a.add(new Operation(type, cnt));
        }
    }
    return a;
}

@Override
public String toString() {
    return "后左下".charAt(this.type) + "" + this.cnt;
}

public static void main(String[] args) {
    List<Operation> ops = parse("左2下3 后2后下左");
    for (Operation o : ops) {
        System.out.println(o);
    }
}
}
