package cn.weiyinfu.rubik.ui.samples;

import cn.weiyinfu.rubik.ui.WindowInput;

//四阶魔方求解器
public class FourSolver {
    public static void main(String[] args) {
        new WindowInput(new cn.weiyinfu.rubik.four.FourSolver(), 4);
    }
}
