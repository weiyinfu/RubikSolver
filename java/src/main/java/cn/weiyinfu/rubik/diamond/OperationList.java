package cn.weiyinfu.rubik.diamond;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OperationList {
    public static List<Operation> fromMap(Map<String, int[]> ma) {
        return ma.entrySet()
                .stream()
                .map(x -> {
                    var o = new Operation();
                    o.name = x.getKey();
                    o.displace = x.getValue();
                    o.reverseDisplace = Displace.inverse(o.displace);
                    return o;
                })
                .sorted(Comparator.comparing(x -> x.name))
                .collect(Collectors.toList());
    }

    public static String operation2string(List<Integer> opIds, List<Operation> operations) {
        return opIds.stream()
                .map(x -> operations.get(x).name)
                .collect(Collectors.joining(","));

    }
}
