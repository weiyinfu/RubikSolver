package cn.weiyinfu.rubik.diamond;

import java.util.Map;
import java.util.TreeMap;

public class ColorString {
    interface ColorMap {
        Map<String, Integer> getColorMap(String s);
    }

    class SimpleColorMap implements ColorMap {

        @Override
        public Map<String, Integer> getColorMap(String s) {
            Map<String, Integer> ma = new TreeMap<>();
            for (char i : s.toCharArray()) {
                var k = Character.toString(i);
                if (!ma.containsKey(k)) {
                    ma.put(k, ma.size());
                }
            }
            return ma;
        }
    }

    public static int[] string2displace(String s, ColorMap colorMap) {
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        var ma = colorMap.getColorMap(s);
        var a = new int[s.length()];
        for (int i = 0; i < a.length; i++) {
            a[i] = ma.get(s.charAt(i) + "");
        }
        return a;
    }
}
