package com.marginallyclever.nodegraphcore.nodes.custom.goal;

import com.marginallyclever.nodegraphcore.Node;

public abstract class BaseGoalNode extends Node {
    public BaseGoalNode(String name) {
        super(name);
        this.setLabel("goal");
    }
}
