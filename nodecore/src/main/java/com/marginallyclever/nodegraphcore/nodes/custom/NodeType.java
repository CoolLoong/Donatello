package com.marginallyclever.nodegraphcore.nodes.custom;

public enum NodeType {
    GOAL("goal"),
    STATE("state");

    String name;

    NodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
