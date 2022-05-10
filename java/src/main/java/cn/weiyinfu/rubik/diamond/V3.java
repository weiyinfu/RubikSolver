package cn.weiyinfu.rubik.diamond;

/*
 * 一个三维向量的一些运算，既可以表示点的位置，又可以表示向量
 * */
public class V3 {
    public double x;
    public double y;
    public double z;

    public V3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    //将向量的长度按照sz进行格式化
    public V3 resize(double sz) {
        return mul(sz / length());
    }

    public V3 sub(V3 v) {
        return add(v.mul(-1));
    }

    public V3 add(V3 v) {
        return new V3(x + v.x, y + v.y, z + v.z);
    }

    public V3 mul(double ratio) {
        return new V3(x * ratio, y * ratio, z * ratio);
    }

    public V3 copy() {
        return new V3(x, y, z);
    }

    @Override
    public String toString() {
        return "V3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}