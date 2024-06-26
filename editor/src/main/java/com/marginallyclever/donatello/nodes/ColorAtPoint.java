package com.marginallyclever.donatello.nodes;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Calculates the color of a {@link BufferedImage} at a given point.  Does nothing if the requested point is out of bounds.
 * The sampling area is a square <code>1+samplesize</code> pixels on each side.<br/>
 *
 * The sampling is evenly weighted - that is to say the
 * <a href='https://en.wikipedia.org/wiki/Kernel_(image_processing)#Convolution'>convolution matrix</a> is all 1s.
 * @author Dan Royer
 * @since 2022-02-23
 */
public class ColorAtPoint extends Node {
    private final DockReceiving<BufferedImage> image = new DockReceiving<>("image", BufferedImage.class, new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
    private final DockReceiving<Number> cx = new DockReceiving<>("x", Number.class, 0);
    private final DockReceiving<Number> cy = new DockReceiving<>("y", Number.class, 0);
    private final DockReceiving<Number> sampleSize = new DockReceiving<>("sampleSize", Number.class, 0);
    private final DockShipping<Color> output = new DockShipping<>("output", Color.class, new Color(0,0,0,0));

    /**
     * Constructor for subclasses to call.
     */
    public ColorAtPoint() {
        super("ColorAtPoint");
        addVariable(image);
        addVariable(cx);
        addVariable(cy);
        addVariable(sampleSize);
        addVariable(output);
    }

    @Override
    public void update() {
        if(image.hasPacketWaiting()) image.receive();
        if(cx.hasPacketWaiting()) cx.receive();
        if(cy.hasPacketWaiting()) cy.receive();
        if(sampleSize.hasPacketWaiting()) sampleSize.receive();

        BufferedImage src = image.getValue();
        int h = src.getHeight();
        int w = src.getWidth();

        int sample = sampleSize.getValue().intValue();
        int sampleSize = 1 + 2 * sample;
        int startX = cx.getValue().intValue() - sample - 1;
        int startY = cy.getValue().intValue() - sample - 1;
        int endX = startX + sampleSize;
        int endY = startY + sampleSize;
        startX = Math.max(startX,0);
        startY = Math.max(startY,0);
        endX = Math.min(endX,w);
        endY = Math.min(endY,h);

        if(startX!=endX && startY!=endY) {
            int sumCount=0;
            double sumA=0;
            double sumR=0;
            double sumG=0;
            double sumB=0;

            for (int y = startY; y < endY; ++y) {
                for (int x = startX; x < endX; ++x) {
                    int pixel = src.getRGB(x,y);
                    sumA += (pixel >> 24) & 0xff;
                    sumR += (pixel >> 16) & 0xff;
                    sumG += (pixel >> 8) & 0xff;
                    sumB += (pixel) & 0xff;
                    sumCount++;
                }
            }

            sumA /= sumCount;
            sumR /= sumCount;
            sumG /= sumCount;
            sumB /= sumCount;
            output.send(new Packet<>(new Color((int)sumR, (int)sumG, (int)sumB, (int)sumA)));
        }
    }
}
