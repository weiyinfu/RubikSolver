package haha;
/**
 * 二阶魔方的状态
 * */
public class Node {
	int[] pos;
	int code;
	int state;

	Node() {
		pos = new int[7];
	}

	Node(int[] pos, int state, int code) {
		this.pos = pos;
		this.state = state;
		this.code = code;
	}
}
