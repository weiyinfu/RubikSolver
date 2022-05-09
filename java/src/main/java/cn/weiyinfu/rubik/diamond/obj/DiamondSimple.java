package cn.weiyinfu.rubik.diamond.obj;

import cn.weiyinfu.rubik.diamond.Operation;
import cn.weiyinfu.rubik.diamond.OperationList;

import java.util.List;

public class DiamondSimple extends Diamond {
    public DiamondSimple(int n) {
        super(n);
    }

    @Override
    public List<Operation> getOperations() {
        return OperationList.fromMap(finder.getOperationsWithoutCorner());
    }
}
