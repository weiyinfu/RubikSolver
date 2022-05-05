package cn.weiyinfu.rubik.diamond;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Diamond implements ColorString.ColorMap, Provider {
    protected final DisplaceFinder finder;
    private final int n;

    Diamond(int n) {
        this.n = n;
        this.finder = new DisplaceFinder(n);
    }

    @Override
    public Map<String, Integer> getColorMap(String s) {
        var perFace = finder.skeleton.perFace;
        var colorMap = new TreeMap<String, Integer>();
        colorMap.put(s.charAt(2 * n - 2) + "", 0);
        colorMap.put(s.charAt(perFace) + "", 1);
        colorMap.put(s.charAt(perFace * 2) + "", 2);
        //找到对面的那个小块
        for (var i = 0; i < s.length(); i++) {
            var k = s.substring(i, i + 1);
            if (colorMap.containsKey(k)) {
                continue;
            }
            colorMap.put(k, 3);
            break;
        }
        return colorMap;
    }

    @Override
    public int[] newStart() {
        var totalFace = finder.skeleton.totalFace;
        var perFace = finder.skeleton.perFace;
        var a = new int[totalFace];
        for (int i = 0; i < a.length; i++) {
            a[i] = i / perFace;
        }
        return a;
    }

    @Override
    public List<Operation> getOperations() {
        return OperationList.fromMap(finder.getOperations());
    }

    @Override
    public int[] parseState(String colorString) {
        return ColorString.string2displace(colorString, this);
    }
}
