package com.marginallyclever.nodegraphcore.nodes.math;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

/**
 * C=A/B
 */
public class Divide extends Node {
    private final DockReceiving<Number> a = new DockReceiving<>("A", Number.class, 0);
    private final DockReceiving<Number> b = new DockReceiving<>("B", Number.class, 0);
    private final DockShipping<Number> c = new DockShipping<>("output", Number.class, 0);

    /**
     * Constructor for subclasses to call.
     */
    public Divide() {
        super("Divide");
        addVariable(a);
        addVariable(b);
        addVariable(c);
    }

    @Override
    public void update() {
        if (0 == countReceivingConnections()) return;
        if (!a.hasPacketWaiting() && !b.hasPacketWaiting()) return;
        a.receive();
        b.receive();
        double av = a.getValue().doubleValue();
        double bv = b.getValue().doubleValue();
        if (bv == 0) c.send(new Packet<>(Float.NaN));
        else c.send(new Packet<>(av / bv));
    }
}
