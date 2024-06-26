package com.marginallyclever.donatello.nodes.color;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

import java.awt.*;

/**
 * Loads a {@link Color} into the graph.
 * @author Dan Royer
 * @since 2022-03-19
 */
public class LoadColor extends Node {
    private final DockReceiving<Number> r = new DockReceiving<>("r", Number.class, 0);
    private final DockReceiving<Number> g = new DockReceiving<>("g", Number.class, 0);
    private final DockReceiving<Number> b = new DockReceiving<>("b", Number.class, 0);
    private final DockReceiving<Number> a = new DockReceiving<>("a", Number.class, 0);
    private final DockShipping<Color> output = new DockShipping<>("output", Color.class, new Color(0,0,0,0));

    /**
     * Constructor for subclasses to call.
     */
    public LoadColor() {
        super("LoadColor");
        addVariable(r);
        addVariable(g);
        addVariable(b);
        addVariable(a);
        addVariable(output);
    }

    @Override
    public void update() {
        if(r.hasPacketWaiting()) r.receive();
        if(g.hasPacketWaiting()) g.receive();
        if(b.hasPacketWaiting()) b.receive();
        if(a.hasPacketWaiting()) a.receive();

        int rr = r.getValue().intValue();
        int gg = g.getValue().intValue();
        int bb = b.getValue().intValue();
        int aa = a.getValue().intValue();
        output.send(new Packet<>(new Color(rr,gg,bb,aa)));
    }
}
