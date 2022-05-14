package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Displace;
import cn.weiyinfu.rubik.diamond.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * 双向操作，也就是完备操作集
 * */
public class CubeDouble extends Cube {

    public CubeDouble(int n) {
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
        return b;
    }
}
