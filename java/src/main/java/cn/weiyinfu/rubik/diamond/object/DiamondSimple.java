package cn.weiyinfu.rubik.diamond.object;

import cn.weiyinfu.rubik.diamond.Operation;
import cn.weiyinfu.rubik.diamond.OperationList;

import java.util.Arrays;
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

    int[] removeCorner(int[] a) {
        //把四面体魔方去掉四个角，将四个角的状态置为0
        for (var i = 0; i < 4; i++) {
            if (i == 1) {
                var base = finder.skeleton.perFace * i;
                a[base] = 0;
                a[base + finder.skeleton.perFace - 1] = 0;
                a[base + finder.skeleton.perFace - (2 * n - 1)] = 0;
            } else {
                var base = finder.skeleton.perFace * i;
                a[base] = 0;
                a[base + (2 * n - 1) - 1] = 0;
                a[base + finder.skeleton.perFace - 1] = 0;
            }
        }

        return a;
    }

    @Override
    public int[] parseState(String colorString) {
        return removeCorner(super.parseState(colorString));
    }

    @Override
    public int[] newStart() {
        return removeCorner(super.newStart());
    }
}
