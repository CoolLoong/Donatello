package com.marginallyclever.nodegraphcore.nodes.custom.goal;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.type.FourNumberArray;

public class RoamingWithLocationGoalNode extends BaseGoalNode {
    private final DockReceiving<FourNumberArray> a = new DockReceiving<>("坐标/速度", FourNumberArray.class, new FourNumberArray());

    public RoamingWithLocationGoalNode() {
        super("周期随机移动");
        addVariable(a);
    }

    @Override
    public void update() {
    }
}
