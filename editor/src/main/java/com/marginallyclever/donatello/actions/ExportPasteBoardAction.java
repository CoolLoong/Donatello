package com.marginallyclever.donatello.actions;

import com.marginallyclever.donatello.Donatello;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ExportPasteBoardAction extends AbstractAction {
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
    public ExportPasteBoardAction(String name, Donatello editor) {
        super(name);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String string = editor.getGraph().toJSON().toString();
            byte[] encode = Base64.getEncoder().encode(string.getBytes(StandardCharsets.UTF_8));

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(new String(encode, StandardCharsets.UTF_8));
            clipboard.setContents(selection, null);

            editor.setSpecificStatusBarText("导出图数据到粘贴板完成!");
        } catch (Throwable ex) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(editor), ex.getLocalizedMessage());
        }
    }
}