package com.marginallyclever.donatello.actions.undoable;

import com.marginallyclever.donatello.AddNodePanel;
import com.marginallyclever.donatello.Donatello;
import com.marginallyclever.donatello.edits.NodeAddEdit;
import com.marginallyclever.nodegraphcore.Graph;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.nodes.custom.NodeType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Launches the "Add Node" dialog.  If the user clicks "Ok" then the selected {@link Node} type is added to the
 * current editor {@link Graph}.
 *
 * @author Dan Royer
 * @since 2022-02-21
 */
public class AddNodeAction extends AbstractAction {
    /**
     * The editor being affected.
     */
    private final Donatello editor;
    private final List<String> nodes;

    /**
     * Constructor for subclasses to call.
     *
     * @param name   the name of this action visible on buttons and menu items.
     * @param editor the editor affected by this Action.
     */
    public AddNodeAction(String name, List<String> nodes, Donatello editor) {
        super(name);
        this.nodes = nodes;
        this.editor = editor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Node n = AddNodePanel.runAsDialog((JFrame) SwingUtilities.getWindowAncestor(editor), getValue(Action.NAME).toString(), nodes);
        if (n != null) {
            ifFirstAddGoal(n);
            n.setPosition(editor.getPaintArea().transformMousePoint(editor.getPopupPoint()));
            editor.addEdit(new NodeAddEdit((String) this.getValue(Action.NAME), editor, n));
        }
    }

    private void ifFirstAddGoal(Node node) {
        String string = this.getValue(Action.NAME).toString();
        if (string.equals("Add Goal") && editor.getGraph().getNodes().stream().noneMatch(n -> n.getLabel().equals(NodeType.GOAL.getName()))) {
            node.setFirst(true);
        }
    }
}
