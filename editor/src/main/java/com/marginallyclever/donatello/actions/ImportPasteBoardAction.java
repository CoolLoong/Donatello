package com.marginallyclever.donatello.actions;

import com.marginallyclever.donatello.Donatello;
import com.marginallyclever.donatello.edits.GraphPasteEdit;
import com.marginallyclever.nodegraphcore.Graph;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ImportPasteBoardAction extends AbstractAction {
    /**
     * The editor being affected.
     */
    private final Donatello editor;

    /**
     * Constructor for subclasses to call.
     *
     * @param name   the name of this action visible on buttons and menu items.
     * @param editor the editor affected by this Action.
     */
    public ImportPasteBoardAction(String name, Donatello editor) {
        super(name);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            String result = null;
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
                byte[] decode = Base64.getDecoder().decode(result);
                String json = new String(decode, StandardCharsets.UTF_8);
                Graph newModel = new Graph();
                try {
                    newModel.parseJSON(new JSONObject(json));
                    editor.addEdit(new GraphPasteEdit((String) this.getValue(Action.NAME), editor, newModel));
                    editor.setSpecificStatusBarText("成功导入图数据从粘贴板!");
                } catch (Throwable e1) {
                    JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(editor), e1.getLocalizedMessage());
                }
            } catch (UnsupportedFlavorException ex) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(editor), ex.getLocalizedMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(editor), ex.getLocalizedMessage());
            }
        }
    }
}