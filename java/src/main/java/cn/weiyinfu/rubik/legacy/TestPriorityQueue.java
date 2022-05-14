package cn.weiyinfu.rubik.legacy;

import java.util.PriorityQueue;

public class TestPriorityQueue {
    public static void main(String[] args) {
        PriorityQueue<Integer> a = new PriorityQueue<>();
        for (int i = 10; i >= 0; i--) {
            a.add(i);
            if (a.size() > 5) {
                a.poll();
            }
        }
        while (a.size() > 0) {
            System.out.println(a.poll());
        }
    }
}
