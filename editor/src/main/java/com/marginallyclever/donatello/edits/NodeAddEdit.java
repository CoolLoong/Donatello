package com.marginallyclever.donatello.edits;

import com.marginallyclever.donatello.Donatello;
import com.marginallyclever.nodegraphcore.Node;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class NodeAddEdit extends SignificantUndoableEdit {
    private final String name;
    private final Donatello editor;
    private final Node node;

    public NodeAddEdit(String name, Donatello editor, Node node) {
        super();
        this.name = name;
        this.editor = editor;
        this.node = node;
        doIt();
    }

    @Override
    public String getPresentationName() {
        return name;
    }

    public void doIt() {
        editor.lockClock();
        try {
            editor.getGraph().add(node);
            editor.repaint(node.getRectangle());
        }
        finally {
            editor.unlockClock();
        }
    }

    @Override
    public void undo() throws CannotUndoException {
        editor.lockClock();
        try {
            editor.getGraph().remove(node);
        }
        finally {
            editor.unlockClock();
        }

        editor.repaint(node.getRectangle());

        super.undo();
    }

    @Override
    public void redo() throws CannotRedoException {
        doIt();
        super.redo();
    }
}
