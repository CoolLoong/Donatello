package com.marginallyclever.nodegraphcore.nodes.custom.state;

import com.marginallyclever.nodegraphcore.DockValue;

public class DelayStateNode extends BaseStateNode {
    public static final String name = "延时状态";

    private final DockValue<Number> a = new DockValue<>("延时时间", Number.class, 0);

    public DelayStateNode() {
        super(name);
        addVariable(a);
    }

    public Number getValue() {
        return a.getValue();
    }

    @Override
    public String toString() {
        return name + " " + a.getName() + ":" + getValue();
    }
}
