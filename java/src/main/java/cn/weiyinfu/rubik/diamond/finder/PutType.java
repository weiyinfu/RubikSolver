package cn.weiyinfu.rubik.diamond.finder;

import cn.weiyinfu.rubik.diamond.Displace;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/*
放置方式类

把四面体放在地上有12种状态（谁着地4种*谁是正面3种），这12种状态
构成一个图，每个结点有4条边

其实它的结构就是一个正八面体，也就是正方体的对偶图。

六面体有24种状态，24个结点，每个结点有4个邻居，其实就是一个

面的表示：左面0，中间1，右面2，下面3
操作的表示：左面0，中间1，右面2，下面3，操作指的是旋转面而不是旋转尖
 */
public class PutType {
    private final int[][] ops;
    //四种操作：影响状态，0左面，1正面，2右面，3下面，左手定则
    public int[][] stateTable;
    public Node[] nodes;

    public class Node {
        public int[] a;
        int id;

        Node(int[] a) {
            this.a = a;
        }

        Node apply(int op) {
            return new Node(Displace.mul(a, ops[op]));
        }

        public String tos() {
            return Arrays.toString(a);
        }
    }

    public PutType(int n, int[][] ops) {
        this.ops = ops;
        Queue<Node> q = new LinkedList<>();
        Node initNode = new Node(Displace.arange(n));
        initNode.id = 0;
        q.add(initNode);
        HashMap<String, Node> visited = new HashMap<>();
        visited.put(initNode.tos(), initNode);
        while (!q.isEmpty()) {
            Node x = q.poll();
            for (int i = 0; i < ops.length; i++) {
                Node nex = x.apply(i);
                if (visited.containsKey(nex.tos())) {
                    continue;
                }
                nex.id = visited.size();
                visited.put(nex.tos(), nex);
                q.add(nex);
            }
        }

        stateTable = new int[visited.size()][ops.length];
        nodes = new Node[visited.size()];
        for (var i : visited.values()) {
            nodes[i.id] = i;
            for (int op = 0; op < ops.length; op++) {
                var nex = i.apply(op);
                stateTable[i.id][op] = visited.get(nex.tos()).id;
            }
        }
    }
}
