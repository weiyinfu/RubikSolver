package cn.weiyinfu.rubik.two;

import java.io.RandomAccessFile;
import java.nio.file.Files;

//读表法
public class TableSolver extends TwoSolver {
    RandomAccessFile book;

    static String parseOperation(int x) {
        StringBuilder builder = new StringBuilder();
        while (x > 1) {
            builder.append(Mini.ops.charAt(x % 3));
            x /= 3;
        }
        return builder.toString();
    }

    public String getAns(String question) {
        return solve(Mini.from(question).code);
    }

    String solve(int code) {
        try {
            //每个操作占用来年各个字节
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
            if (!Files.exists(TableGenerator.BinPath)) {
                System.out.println("is generating table.data,please wait about 10s");
                new TableGenerator();
            }
            book = new RandomAccessFile(TableGenerator.BinPath.toFile(), "r");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
