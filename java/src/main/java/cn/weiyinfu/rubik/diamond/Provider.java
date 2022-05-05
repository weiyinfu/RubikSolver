package cn.weiyinfu.rubik.diamond;

import java.util.List;
import java.util.Map;

public interface Provider {
    int[] newStart();

    List<Operation> getOperations();

    int[] parseState(String colorString);
}
