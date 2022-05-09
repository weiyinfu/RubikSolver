package cn.weiyinfu.rubik.legacy;

import cn.weiyinfu.rubik.diamond.Displace;
import cn.weiyinfu.rubik.diamond.finder.DisplaceFinderDiamond;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
 * 使用string作为去重工具内存消耗太大
 * */
public class DiamondBroadSearch {
    DisplaceFinderDiamond finder;
    int n;

    class Node {
        int[] a;

        Node(int sz) {
            this.a = new int[sz];
        }

        Node apply(int[] displace) {
            Node ans = new Node(this.a.length);
            ans.a = Displace.mul(this.a, displace);
            return ans;
        }

        public String tos() {
            return Arrays.toString(a);
        }
    }

    interface VisitedKeeper {
        boolean add(Node node);

        int size();
    }

    class StringVisitedKeeper implements VisitedKeeper {
        HashSet<String> visited = new HashSet<String>();

        @Override
        public boolean add(Node node) {
            var k = node.tos();
            if (visited.contains(k)) {
                return false;
            }
            visited.add(k);
            return true;
        }

        @Override
        public int size() {
            return visited.size();
        }
    }

    Node newStartNode() {
        Node no = new Node(finder.skeleton.totalFace);
        for (int i = 0; i < no.a.length; i++) {
            no.a[i] = i / finder.skeleton.perFace;
        }
        return no;
    }

    DiamondBroadSearch(int n) {
        this.n = n;
        finder = new DisplaceFinderDiamond(n);
        var start = newStartNode();
        Queue<Node> q = new LinkedList<>();
        q.add(start);
        var visited = new StringVisitedKeeper();
        visited.add(start);
        var operations = finder.getOperations();
        while (!q.isEmpty()) {
            if (visited.size() % 10000 == 0) {
                System.out.printf("visited:%s,queue:%s\n", visited.size(), q.size());
            }
            var it = q.poll();
            if (it == null) {
                continue;
            }
            for (var op : operations.values()) {
                var nex = it.apply(op);
                if (visited.add(nex)) {
                    q.add(nex);
                }
            }
        }
        System.out.println(visited.size());
    }

    public static void main(String[] args) {
        var solver = new DiamondBroadSearch(2);
    }
}
