package com.marginallyclever.donatello.contextsensitivetools;

import com.marginallyclever.donatello.Donatello;
import com.marginallyclever.donatello.UnicodeIcon;
import com.marginallyclever.donatello.edits.ConnectionAddEdit;
import com.marginallyclever.donatello.edits.ConnectionRemoveEdit;
import com.marginallyclever.donatello.graphview.GraphViewPanel;
import com.marginallyclever.nodegraphcore.Connection;
import com.marginallyclever.nodegraphcore.ConnectionPointInfo;
import com.marginallyclever.nodegraphcore.Dock;
import com.marginallyclever.nodegraphcore.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ConnectionEditTool extends ContextSensitiveTool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionEditTool.class);
    private static final Color CONNECTION_POINT_COLOR_SELECTED = Color.RED;
    private static final Color CONNECTION_BEING_EDITED = Color.RED;
    private static final double NEARBY_CONNECTION_DISTANCE_MAX = 10;

    private final Donatello editor;

    /**
     * for tracking relative motion, useful for relative moves like dragging.
     */
    private final Point mousePreviousPosition = new Point();

    /**
     * To create a {@link Connection} the user has to select two {@link Dock} connection points.
     * This is where the first is stored until the user completes the connection or cancels the action.
     */
    private final Connection connectionBeingCreated = new Connection();

    /**
     * The last connection point found
     */
    private ConnectionPointInfo lastConnectionPoint = null;

    private final String addName;
    private final String removeName;

    public ConnectionEditTool(Donatello editor, String addName, String removeName) {
        super();
        this.editor=editor;
        this.addName = addName;
        this.removeName = removeName;
    }

    @Override
    public String getName() {
        return "Connect";
    }

    @Override
    public Icon getSmallIcon() {
        return new UnicodeIcon("🔌");
    }

    @Override
    public boolean isCorrectContext(Point p) {
        selectOneNearbyConnectionPoint(p);
        return lastConnectionPoint!=null;
    }

    public KeyStroke getAcceleratorKey() {
        return KeyStroke.getKeyStroke(KeyEvent.VK_C,0);
    }

    @Override
    public void paint(Graphics g) {
        paintConnectionBeingMade(g);
        highlightNearbyConnectionPoint(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        onClickConnectionPoint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        repaintConnectionInProgress(e.getPoint());

        mousePreviousPosition.setLocation(editor.getPaintArea().transformMousePoint(e.getPoint()));
        selectOneNearbyConnectionPoint(editor.getPaintArea().transformMousePoint(e.getPoint()));

        showConnectionPointToolTip();
    }

    private void showConnectionPointToolTip() {
        GraphViewPanel paintArea = editor.getPaintArea();
        String toolTipName = (lastConnectionPoint!=null) ? lastConnectionPoint.getVariable().getTypeName() : null;
        paintArea.setToolTipText(toolTipName);
    }

    /**
     * Searches for a nearby {@link Dock} connection point and, if found, remembers it.
     * @param p the center of the search area.
     */
    private void selectOneNearbyConnectionPoint(Point p) {
        ConnectionPointInfo info = editor.getGraph().getNearestConnectionPoint(p,NEARBY_CONNECTION_DISTANCE_MAX);
        setLastConnectionPoint(info);
    }

    /**
     * Remembers a connection point as described by a {@link ConnectionPointInfo}.
     * @param info the {@link ConnectionPointInfo}
     */
    private void setLastConnectionPoint(ConnectionPointInfo info) {
        if(info!=lastConnectionPoint && lastConnectionPoint!=null) {
            repaintConnectionPoint(lastConnectionPoint.getPoint());
        }

        lastConnectionPoint = info;

        if(info!=null) repaintConnectionPoint(info.getPoint());
    }

    private void repaintConnectionInProgress(Point point) {
        Rectangle r = connectionBeingCreated.getBounds();
        r.add(point);
        r.add(mousePreviousPosition);
        r.grow((int)NEARBY_CONNECTION_DISTANCE_MAX,(int)NEARBY_CONNECTION_DISTANCE_MAX);
        editor.repaint();
    }

    private void repaintConnectionPoint(Point p) {
        Rectangle r = new Rectangle(p);
        r.grow((int) NEARBY_CONNECTION_DISTANCE_MAX, (int) NEARBY_CONNECTION_DISTANCE_MAX);
        editor.repaint();
    }

    /**
     * Paints a connection as it is being made
     * @param g the {@link Graphics} context
     */
    private void paintConnectionBeingMade(Graphics g) {
        if(connectionBeingCreated.isInputValid() || connectionBeingCreated.isOutputValid()) {
            g.setColor(CONNECTION_BEING_EDITED);
            GraphViewPanel paintArea = editor.getPaintArea();
            paintArea.setLineWidth(g,3);

            Point a,b;
            if(connectionBeingCreated.isInputValid()) {
                a = connectionBeingCreated.getInPosition();
                b = mousePreviousPosition;
                paintArea.paintConnectionAtPoint(g,a);
            } else {
                a = mousePreviousPosition;
                b = connectionBeingCreated.getOutPosition();
                paintArea.paintConnectionAtPoint(g,b);
            }
            paintArea.paintBezierBetweenTwoPoints(g,a,b);

            paintArea.setLineWidth(g,1);
        }
    }

    /**
     * Paints the connection point under the cursor
     * @param g the {@link Graphics} context
     */
    private void highlightNearbyConnectionPoint(Graphics g) {
        if(lastConnectionPoint !=null) {
            g.setColor(CONNECTION_POINT_COLOR_SELECTED);
            GraphViewPanel paintArea = editor.getPaintArea();
            paintArea.setLineWidth(g,2);
            paintArea.paintVariableConnectionPoints(g,lastConnectionPoint.getVariable());
            paintArea.setLineWidth(g,1);
        }
    }

    /**
     * What to do when a user clicks on a connection point.
     */
    private void onClickConnectionPoint() {
        if(lastConnectionPoint == null) {
            connectionBeingCreated.disconnectAll();
            setActive(false);
            return;
        }

        // check that the end node is not the same as the start node.
        //if(!connectionBeingCreated.isConnectedTo(lastConnectionPoint.node))
        {
            if (lastConnectionPoint.getFlags() == ConnectionPointInfo.IN) {
                // the output of a connection goes to the input of a node.
                connectionBeingCreated.setOutput(lastConnectionPoint.getNode(), lastConnectionPoint.getDockIndex());
            } else {
                //the output of a node goes to the input of a connection.
                connectionBeingCreated.setInput(lastConnectionPoint.getNode(), lastConnectionPoint.getDockIndex());
            }

            setActive(true);
            Rectangle r = connectionBeingCreated.getBounds();
            r.grow((int)NEARBY_CONNECTION_DISTANCE_MAX,(int)NEARBY_CONNECTION_DISTANCE_MAX);
            editor.repaint(r);
        }

        if(connectionBeingCreated.isInputValid() && connectionBeingCreated.isOutputValid() ) {
            if(connectionBeingCreated.isValidDataType()) {
                Graph graph = editor.getGraph();
                Connection match = graph.getMatchingConnection(connectionBeingCreated);
                if(match!=null) {
                    editor.addEdit(new ConnectionRemoveEdit(removeName,editor,match));
                } else {
                    editor.addEdit(new ConnectionAddEdit(addName,editor,new Connection(connectionBeingCreated)));
                }
            } else {
                // if any of the tests failed
                Dock<?> vIn = connectionBeingCreated.getInVariable();
                Dock<?> vOut = connectionBeingCreated.getOutVariable();
                String nameIn = (vIn==null) ? "null" : vIn.getTypeName();
                String nameOut = (vOut==null) ? "null" : vOut.getTypeName();
                logger.warn("Invalid types {}, {}",nameOut,nameIn);
            }

            Rectangle r = connectionBeingCreated.getBounds();
            r.grow((int)NEARBY_CONNECTION_DISTANCE_MAX,(int)NEARBY_CONNECTION_DISTANCE_MAX);
            editor.repaint(r);
            // either way, restart.
            connectionBeingCreated.disconnectAll();
            setActive(false);
        }
    }

    public ConnectionPointInfo getLastConnectionPoint() {
        return lastConnectionPoint;
    }

    public void restart() {
        connectionBeingCreated.disconnectAll();
    }

    @Override
    public void attachMouseAdapter() {
        super.attachMouseAdapter();
        GraphViewPanel paintArea = editor.getPaintArea();
        paintArea.addMouseMotionListener(this);
        paintArea.addMouseListener(this);
    }

    @Override
    public void detachMouseAdapter() {
        super.detachMouseAdapter();
        GraphViewPanel paintArea = editor.getPaintArea();
        paintArea.removeMouseMotionListener(this);
        paintArea.removeMouseListener(this);
    }
}
