package com.marginallyclever.donatello.nodes;

import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

/**
 * Publishes the time in seconds continuously.
 */
public class TimeInSeconds extends Node {
    private final DockShipping<Number> seconds = new DockShipping<>("seconds", Number.class, 0);
    private final double startTime = System.currentTimeMillis();

    public TimeInSeconds() {
        super("TimeInSeconds");
        addVariable(seconds);
    }

    @Override
    public void update() {
        double t = (System.currentTimeMillis()-startTime)/1000.0;
        seconds.send(new Packet<>(t));
    }
}
