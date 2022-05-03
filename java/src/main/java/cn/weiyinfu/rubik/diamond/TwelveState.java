package cn.weiyinfu.rubik.diamond;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/*
把四面体放在地上有12种状态（谁着地4种*谁是正面3种），这12种状态
构成一个图，每个结点有4条边

其实它的结构就是一个正八面体，也就是正方体的对偶图。

六面体有24种状态，24个结点，每个结点有4个邻居，其实就是一个

面的表示：左面0，中间1，右面2，下面3
操作的表示：左面0，中间1，右面2，下面3，操作指的是旋转面而不是旋转尖
 */
public class TwelveState {
    //四种操作：影响状态，0左面，1正面，2右面，3下面，左手定则
    final int[][] ops = {
            {0, 2, 3, 1},
            {3, 1, 0, 2},
            {1, 3, 2, 0},
            {2, 0, 1, 3}
    };
    public int[][] stateTable;
    public Node[] nodes;

    public class Node {
        public int[] a = new int[4];//0,1,2,3


        Node apply(int op) {
            int[] displace = ops[op];
            int[] a = new int[this.a.length];
            for (int i = 0; i < a.length; i++) {
                a[i] = this.a[displace[i]];
            }
            Node no = new Node();
            no.a = a;
            return no;
        }

        String tos() {
            return Arrays.toString(a);
        }
    }

    class NodeWrapper {
        private final int id;
        private final Node node;

        NodeWrapper(int id, Node node) {
            this.id = id;
            this.node = node;
        }
    }

    public TwelveState() {
        Queue<Node> q = new LinkedList<>();
        Node initNode = new Node();
        initNode.a = new int[]{0, 1, 2, 3};
        q.add(initNode);
        HashMap<String, NodeWrapper> visited = new HashMap<>();
        visited.put(initNode.tos(), new NodeWrapper(0, initNode));
        while (!q.isEmpty()) {
            Node x = q.poll();
            for (int i = 0; i < ops.length; i++) {
                Node nex = x.apply(i);
                if (visited.containsKey(nex.tos())) {
                    continue;
                }
                visited.put(nex.tos(), new NodeWrapper(visited.size(), nex));
                q.add(nex);
            }
        }

        stateTable = new int[visited.size()][ops.length];
        nodes = new Node[visited.size()];
        for (var i : visited.values()) {
            nodes[i.id] = i.node;
            for (int op = 0; op < ops.length; op++) {
                var nex = i.node.apply(op);
                stateTable[i.id][op] = visited.get(nex.tos()).id;
            }
        }
    }
}
