package haha;

import java.util.ArrayDeque;
import java.util.Queue;

//将一个正方体变成"红心白发蓝右臂"的形式
public class DownAndRight {
	// opsite[i] is the opsite face of the ith face
	int opsite[] = { 4, 3, 5, 1, 0, 2 };
	// down operation and right operation
	int f[][] = { { 5, 1, 0, 3, 2, 4 }, { 0, 5, 1, 2, 4, 3 } };
	// 0 means down,1 means right
	int ans[] = new int[24];
	int graph[][] = new int[24][2];

	// 打印图结构
	void initGraph() {
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 2; j++) {
				int next = go(j, i);
				graph[next][j] = i;
			}
		}
	}

	void showGraph() {
		initGraph();
		for (int i = 0; i < 24; i++) {
			System.out.printf("%s %s %s\n", tos(i), tos(graph[i][0]), tos(graph[i][1]));
		}
	}

	int gt(int x, int y) {
		return x >= y ? 1 : 0;
	}

	int go(int op, int state) {
		int x = state % 6, y = state / 6;
		y = y + gt(y, x) + gt(y, opsite[x]);
		return state(f[op][x], f[op][y]);
	}

	int state(int x, int y) {
		y = y - gt(y, x) - gt(y, opsite[x]);
		return x + y * 6;
	}

	String tos(int state) {
		int x = state % 6, y = state / 6;
		y += gt(y, x) + gt(y, opsite[x]);
		return String.format("(%d,%d)", x, y);
	}

	public DownAndRight() {
		Queue<Integer> q = new ArrayDeque<Integer>();
		int initState = state(2, 3);
		ans[initState] = 1;
		q.add(initState);
		while (q.isEmpty() == false) {
			int now = q.poll();
			for (int op = 0; op < 2; op++) {
				int next = go(op, now);
				if (ans[next] == 0) {
					ans[next] = ans[now] << 1 | op;
					q.add(next);
				}
			}
		}
	}

	public static void main(String[] args) {
		new DownAndRight();
	}
}