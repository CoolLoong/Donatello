package com.marginallyclever.donatello.actions.undoable;

import com.marginallyclever.donatello.Donatello;
import com.marginallyclever.donatello.edits.GraphPasteEdit;
import com.marginallyclever.nodegraphcore.Graph;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Launches a "select file to open" dialog and attempts to load the {@link Graph} from disk.
 * @author Dan Royer
 * @since 2022-02-21
 */
public class LoadGraphAction extends AbstractAction {
    /**
     * The editor being affected.
     */
    private final Donatello editor;

    /**
     * The file chooser remembers the last path.
     */
    private final JFileChooser fc = new JFileChooser();

    /**
     * Constructor for subclasses to call.
     * @param name the name of this action visible on buttons and menu items.
     * @param editor the editor affected by this Action.
     */
    public LoadGraphAction(String name, Donatello editor) {
        super(name);
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fc.setFileFilter(Donatello.FILE_FILTER);
        if (fc.showOpenDialog(SwingUtilities.getWindowAncestor(editor)) == JFileChooser.APPROVE_OPTION) {
            try {
                Graph graph = loadGraphFromFile(fc.getSelectedFile().getAbsolutePath());
                editor.addEdit(new GraphPasteEdit((String)this.getValue(Action.NAME),editor,graph));
            } catch(IOException e1) {
                JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(editor),e1.getLocalizedMessage());
                e1.printStackTrace();
            }
        }
    }

    private Graph loadGraphFromFile(String absolutePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = reader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        Graph newModel = new Graph();
        try {
            newModel.parseJSON(new JSONObject(responseStrBuilder.toString()));
        } catch(IllegalArgumentException e1) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(editor),e1.getLocalizedMessage());
        }
        //newModel.setAllDirty();

        return newModel;
    }
}
