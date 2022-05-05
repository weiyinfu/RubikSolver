package cn.weiyinfu.rubik.diamond;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.weiyinfu.rubik.diamond.Linalg.displaceReverse;

public class OperationList {
    static List<Operation> fromMap(Map<String, int[]> ma) {
        return ma.entrySet()
                .stream()
                .map(x -> {
                    var o = new Operation();
                    o.name = x.getKey();
                    o.displace = x.getValue();
                    o.reverseDisplace = displaceReverse(o.displace);
                    return o;
                })
                .sorted(Comparator.comparing(x -> x.name))
                .collect(Collectors.toList());
    }
}
