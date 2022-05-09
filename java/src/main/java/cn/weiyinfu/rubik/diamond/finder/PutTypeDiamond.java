package cn.weiyinfu.rubik.diamond.finder;

public class PutTypeDiamond extends PutType {
    public PutTypeDiamond() {
        super(4, new int[][]{
                {0, 2, 3, 1},
                {3, 1, 0, 2},
                {1, 3, 2, 0},
                {2, 0, 1, 3}
        });
    }
}
