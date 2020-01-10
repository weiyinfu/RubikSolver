package two;

import cube.Cube;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * 三阶魔方边块中的守恒量
 * 每个边块位置一旦固定，只可能有两种状态：0表示较小的状态，1表示较大的状态
 * 每个边块位置不固定，有三种状态：0表示x轴方向，1表示y轴方向，2表示z轴方向，以红橙两色的朝向为朝向
 */
public class ConstantValue3 {
Set<Integer> visited = new TreeSet<>();

int hash(Cube c) {
//    for (int i = 0; i < 6; i++) {
//        for (int j = 0; j < 3; j++) {
//            for (int )
//        }
//    }
    return 0;
}

ConstantValue3() {
    Queue<Cube> q = new LinkedList<>();
    q.add(new Cube(3));
    while (!q.isEmpty()) {
        Cube c = q.poll();
    }
}

public static void main(String[] args) {
    new ConstantValue3();
}
}
