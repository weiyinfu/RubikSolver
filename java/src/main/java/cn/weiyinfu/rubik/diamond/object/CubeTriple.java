package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Displace;
import cn.weiyinfu.rubik.diamond.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * 三倍操作集，在完备操作集的基础上加上180度旋转操作
 */
public class CubeTriple extends Cube {

    public CubeTriple(int n) {
        super(n);
    }

    @Override
    public List<Operation> getOperations() {
        var a = super.getOperations();
        var b = new ArrayList<>(a);
        for (var i : a) {
            var o = new Operation();
            o.displace = i.reverseDisplace;
            o.reverseDisplace = i.displace;
            o.name = i.name + "^";
            b.add(o);
        }
        for (var i : a) {
            var o = new Operation();
            o.displace = Displace.mul(i.displace, i.displace);
            o.reverseDisplace = Displace.inverse(o.displace);
            o.name = i.name + "-";
            b.add(o);
        }
        return b;
    }
}
