package cn.weiyinfu.rubik.diamond.finder;

/*
 * 正方体的放置方式：DownAndRight
 * */
public class PutTypeCube extends PutType {
    public PutTypeCube() {
        super(6, new int[][]{
                {5, 1, 0, 3, 2, 4},
                {0, 5, 1, 2, 4, 3}
        });
    }

    public static void main(String[] args) {
        var x = new PutTypeCube();
        System.out.println(x.nodes.length);
    }
}
