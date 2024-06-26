package com.marginallyclever.nodegraphcore.nodes.custom.goal;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.nodes.custom.CustomNode;

public abstract class BaseGoalNode extends Node implements CustomNode {
    protected final DockReceiving<Void> from = new DockReceiving<>("From", Void.TYPE, null);
    protected final DockShipping<Void> to = new DockShipping<>("To", Void.TYPE, null);
    
    public BaseGoalNode(String name) {
        super(name);
        this.setLabel("goal");
        addVariable(from);
        addVariable(to);
    }

    public DockReceiving<Void> getFrom() {
        return from;
    }

    public DockShipping<Void> getTo() {
        return to;
    }

    @Override
    public void update() {
    }
}
