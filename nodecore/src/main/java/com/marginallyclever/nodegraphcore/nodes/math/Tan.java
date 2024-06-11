package com.marginallyclever.nodegraphcore.nodes.math;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

/**
 * output=tan(A)
 *
 * @author Dan Royer
 * @since 2022-02-01
 */
public class Tan extends Node {
    private final DockReceiving<Number> a = new DockReceiving<>("A", Number.class, 0);
    private final DockShipping<Number> c = new DockShipping<>("output", Number.class, 0);

    /**
     * Constructor for subclasses to call.
     */
    public Tan() {
        super("Tan");
        addVariable(a);
        addVariable(c);
    }

    @Override
    public void update() {
        if (!a.hasPacketWaiting()) return;
        a.receive();
        double av = a.getValue().doubleValue();
        c.send(new Packet<>(Math.tan(av)));
    }
}
