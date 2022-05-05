package cn.weiyinfu.rubik.diamond;

import java.util.List;

public class DiamondSimple extends Diamond {
    DiamondSimple(int n) {
        super(n);
    }

    @Override
    public List<Operation> getOperations() {
        return OperationList.fromMap(finder.getOperationsWithoutCorner());
    }
}
