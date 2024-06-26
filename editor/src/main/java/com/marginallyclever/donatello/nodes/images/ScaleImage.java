package com.marginallyclever.donatello.nodes.images;

import com.marginallyclever.nodegraphcore.DockReceiving;
import com.marginallyclever.nodegraphcore.DockShipping;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.Packet;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Resize an {@link BufferedImage} to the new desired size
 * @author Dan Royer
 * @since 2022-02-23
 */
public class ScaleImage extends Node {
    private final DockReceiving<BufferedImage> image = new DockReceiving<>("image", BufferedImage.class,new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
    private final DockReceiving<Number> width = new DockReceiving<>("width",Number.class,1);
    private final DockReceiving<Number> height = new DockReceiving<>("height",Number.class,1);
    private final DockShipping<BufferedImage> output = new DockShipping<>("output", BufferedImage.class,new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));

    /**
     * Constructor for subclasses to call.
     */
    public ScaleImage() {
        super("ScaleImage");
        addVariable(image);
        addVariable(width);
        addVariable(height);
        addVariable(output);
    }

    @Override
    public void update() {
        if(image.hasPacketWaiting()) image.receive();
        if(width.hasPacketWaiting()) width.receive();
        if(height.hasPacketWaiting()) height.receive();

        int w = Math.max(1,width.getValue().intValue());
        int h = Math.max(1,height.getValue().intValue());
        BufferedImage input = image.getValue();
        BufferedImage result = new BufferedImage(w,h,input.getType());

        AffineTransform at = new AffineTransform();
        at.scale((double)w/(double)input.getWidth(), (double)h/(double)input.getHeight());
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        scaleOp.filter(input, result);
        output.send(new Packet<>(result));
    }
}
