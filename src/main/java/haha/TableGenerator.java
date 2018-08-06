package haha;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Queue;

public class TableGenerator {

	int a[] = new int[5040 * 729];
	static final int[] factorial = { 1, 1, 2, 6, 24, 120, 720 };
	static final int[] pow = { 1, 3, 9, 27, 81, 243, 729 };
	static final Node aim = getInitNode();

	// 位置哈希，全排列散列算法
	static int hashPosition(int[] position) {
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

	// 状态哈希
	static int hashState(int[] state) {
		int ans = 0;
		for (int i = 0; i < 6; i++) {
			ans += pow[i] * state[i];
		}
		return ans;
	}

	static Node go(int op, Node node) {
		int state = node.state;
		int pos[] = new int[7];
		for (int i = 0; i < 7; i++) {
			int p = node.pos[i];
			if ((p & (1 << op)) == 0) {
				if (i < 6) {
					int s = (node.state / (int) Math.pow(3, i)) % 3;
					int ns = (6 - op - s) % 3;
					state = state - (s - ns) * (int) Math.pow(3, i);
				}
				int op1 = (op + 1) % 3, op2 = (op + 2) % 3;
				pos[i] = p & (1 << op) | (1 - ((p >> op1) & 1) << op2) | ((p >> op2) & 1) << op1;
			} else {
				pos[i] = node.pos[i];
			}
		}
		return new Node(pos, state, hashPosition(pos) * 729 + state);
	}

	static Node getInitNode() {
		Node node = new Node();
		for (int i = 0; i < 7; i++) {
			node.pos[i] = i;
		}
		node.state = 0;
		node.code = 0;
		return node;
	}

	void save() {
		try {
			DataOutputStream cout = new DataOutputStream(Files.newOutputStream(Paths.get("table.data")));
			for (int i = 0; i < a.length; i++) {
				cout.writeInt(a[i]);
			}
			cout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TableGenerator() {
		Queue<Node> q = new ArrayDeque<Node>();
		q.add(aim);
		a[0] = 1;
		while (q.isEmpty() == false) {
			Node now = q.poll();
			for (int i = 0; i < 3; i++) {
				Node next = go(i, now);
				if (a[next.code] == 0) {
					q.add(next);
					a[next.code] = a[now.code] * 3 + i;
				}
			}
		}
		save();
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		new TableGenerator();
		System.out.println(System.currentTimeMillis());
	}
}
