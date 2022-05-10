package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Operation;
import cn.weiyinfu.rubik.diamond.OperationList;

import java.util.List;
/*
* n阶四面体魔方，不考虑四个角的状态
* */
public class DiamondSimple extends Diamond {
    public DiamondSimple(int n) {
        super(n);
    }

    @Override
    public List<Operation> getOperations() {
        return OperationList.fromMap(finder.getOperationsWithoutCorner());
    }
}
