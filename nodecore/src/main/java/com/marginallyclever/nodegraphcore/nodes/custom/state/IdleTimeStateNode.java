package com.marginallyclever.nodegraphcore.nodes.custom.state;

import com.marginallyclever.nodegraphcore.DockValue;

public class IdleTimeStateNode extends BaseStateNode {
    public static final String name = "空闲时间状态";

    private final DockValue<String> a = new DockValue<>("表达式", String.class, "");

    public IdleTimeStateNode() {
        super(name);
        addVariable(a);
    }

    public String getValue() {
        return a.getValue();
    }
}
