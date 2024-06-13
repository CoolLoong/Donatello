package com.marginallyclever.nodegraphcore.nodes.custom.goal;

import com.marginallyclever.nodegraphcore.DockValue;
import com.marginallyclever.nodegraphcore.type.FourNumberArray;

public class RoamingWithLocationGoalNode extends BaseGoalNode {
    public static final String name = "周期坐标移动";

    private final DockValue<FourNumberArray> c = new DockValue<>("坐标/速度", FourNumberArray.class, new FourNumberArray());

    public RoamingWithLocationGoalNode() {
        super("周期坐标移动");
        addVariable(c);
    }
}
