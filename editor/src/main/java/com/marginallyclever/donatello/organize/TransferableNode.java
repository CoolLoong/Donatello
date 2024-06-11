package com.marginallyclever.donatello.organize;

import com.marginallyclever.nodegraphcore.Node;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class TransferableNode implements Transferable {
    protected static DataFlavor nodeFlavor = new DataFlavor(Node.class, "A Node Object");
    protected static DataFlavor[] supportedFlavors = { nodeFlavor };

    Node node;

    public TransferableNode(Node node) {
        this.node = node;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return supportedFlavors;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(nodeFlavor) || flavor.equals(DataFlavor.stringFlavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(nodeFlavor))
            return node;
        else if (flavor.equals(DataFlavor.stringFlavor))
            return node.toString();
        else
            throw new UnsupportedFlavorException(flavor);
    }
}