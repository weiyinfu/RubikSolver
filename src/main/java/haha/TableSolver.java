package haha;

import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TableSolver {
	RandomAccessFile book;

	static String parseOperation(int x) {
		StringBuilder builder = new StringBuilder();
		while (x > 1) {
			builder.append("后左下".charAt(x % 3));
			x /= 3;
		}
		return builder.toString();
	}

	static int hash(int[][] node) {
		return TableGenerator.hashPosition(node[0]) * 729 + TableGenerator.hashState(node[1]);
	}

	static String inputFromFile(String file) {
		try {
			StringBuilder builder = new StringBuilder();
			Files.readAllLines(Paths.get(file)).forEach(x -> builder.append(x));
			String colors = builder.toString();
			return colors;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String solve(String question) {
		return solve(hash(new Converter(question).a));
	}

	String solve(int code) {
		try {
			book.seek(code << 2);
			int ans = book.readInt();
			return parseOperation(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TableSolver() {
		try {
			Path table = Paths.get("table.data");
			if (!Files.exists(table)) {
				System.out.println("is generating table.data,please wait about 10s");
				new TableGenerator();
			}
			book = new RandomAccessFile("table.data", "r");
			String ans=solve(hash(new int[][] { new int[] { 1, 0, 2, 3, 4, 5, 6, 7 }, new int[] { 0, 0, 0, 0, 0, 0, 0, 0 } }));
			System.out.println(ans);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TableSolver();
	}
}
