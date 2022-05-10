package cn.weiyinfu.rubik.ui.samples;

import cn.weiyinfu.rubik.three.min2phase.Min2PhaseSolver;
import cn.weiyinfu.rubik.ui.WindowInput;

/**
 * 三阶魔方两阶段法魔方求解器
 */
public class Min2Phase {
    public static void main(String[] args) {
        new WindowInput(new Min2PhaseSolver());
    }
}
