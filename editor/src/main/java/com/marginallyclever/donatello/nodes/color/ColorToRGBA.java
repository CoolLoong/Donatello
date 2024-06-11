package com.marginallyclever.donatello.nodes.color;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

import java.awt.*;

/**
 * Separates a color into its four component RGBA channels.  Each channel is a value 0...1.
 * @author Dan Royer
 * @since 2022-03-19
 */
public class ColorToRGBA extends Node {
    private final DockReceiving<Color> color = new DockReceiving<>("color", Color.class, new Color(0,0,0,0));
    private final DockShipping<Number> red = new DockShipping<>("red", Number.class, 0);
    private final DockShipping<Number> green = new DockShipping<>("green", Number.class, 0);
    private final DockShipping<Number> blue = new DockShipping<>("blue", Number.class, 0);
    private final DockShipping<Number> alpha = new DockShipping<>("alpha", Number.class, 0);

    /**
     * Constructor for subclasses to call.
     */
    public ColorToRGBA() {
        super("ColorToRGBA");
        addVariable(color);
        addVariable(red  );
        addVariable(green);
        addVariable(blue );
        addVariable(alpha);
    }

    @Override
    public void update() {
        if(color.hasPacketWaiting()) color.receive();

        Color c = color.getValue();
        red  .send(new Packet<>(c.getRed()  /255.0));
        green.send(new Packet<>(c.getGreen()/255.0));
        blue .send(new Packet<>(c.getBlue() /255.0));
        alpha.send(new Packet<>(c.getAlpha()/255.0));
    }
}
