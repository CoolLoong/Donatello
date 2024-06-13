package com.marginallyclever.nodegraphcore.nodes.custom;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;

public interface CustomNode {
    DockReceiving<Void> getFrom();

    DockShipping<Void> getTo();
}
