package com.marginallyclever.donatello;

import com.marginallyclever.donatello.search.SearchBar;
import com.marginallyclever.donatello.search.SearchListener;
import com.marginallyclever.nodegraphcore.Node;
import com.marginallyclever.nodegraphcore.NodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Swing UI allowing a user to create a new {@link Node}.  The choice of {@link Node} is retrieved from the
 * {@link NodeFactory#getNames()} registration list.
 *
 * @author Dan Royer
 * @since 2022-02-11
 */
public class AddNodePanel extends JPanel implements SearchListener {
    private static final Logger logger = LoggerFactory.getLogger(AddNodePanel.class);

    /**
     * The database of names in the list model.
     */
    private final List<String> nodes;

    /**
     * list model controls the contents of the list.  This is needed to add/remove as the search field is changed.
     */
    private final DefaultListModel<String> listModel = new DefaultListModel<>();

    /**
     * the list of names from the {@link NodeFactory}.
     */
    private final JList<String> myList = new JList<>(listModel);

    private final SearchBar searchBar = new SearchBar();

    /**
     * Constructor for subclasses to call.
     */
    public AddNodePanel(List<String> nodes) {
        super(new BorderLayout());
        this.nodes = nodes;

        myList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        JScrollPane listScroller = new JScrollPane(myList);
        listScroller.setPreferredSize(new Dimension(250, 250));

        this.add(listScroller, BorderLayout.CENTER);
        this.add(searchBar, BorderLayout.NORTH);

        searchBar.addSearchListener(this);
        searchFor("", false, false);
    }

    /**
     * Filter the default list model based on the search query.
     * From <a href='https://stackoverflow.com/questions/15824733/option-to-ignore-case-with-contains-method'>Stack Overflow</a>
     */
    public void searchFor(String query, boolean caseSensitive, boolean regularExpression) {
        if (!regularExpression && !caseSensitive) {
            query = query.toLowerCase();
        }

        listModel.clear();

        for (String s : nodes) {
            String t = (!regularExpression && caseSensitive) ? s : s.toLowerCase();
            boolean found = (regularExpression) ? t.matches(query) : t.contains(query);
            if (found) {
                listModel.addElement(s);
            }
        }
    }

    /**
     * Runs the panel as a dialog.
     *
     * @param frame the parent frame.
     * @return the node created, or null.
     */
    public static Node runAsDialog(Frame frame, String title, List<String> nodes) {
        JDialog dialog = new JDialog(frame, title, Dialog.ModalityType.DOCUMENT_MODAL);

        final AtomicReference<Node> result = new AtomicReference<>();

        final AddNodePanel panel = new AddNodePanel(nodes);
        panel.myList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() < 2) return;
                try {
                    if (panel.myList.getSelectedIndex() != -1) {
                        result.set(NodeFactory.createNode(panel.myList.getSelectedValue()));
                    }
                } catch (IllegalArgumentException e1) {
                    logger.error("", e1);
                }
                dialog.dispose();
            }
        });

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);

        return result.get();
    }

    /**
     * main entry point.  Good for independent test.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) throws Exception {
        NodeFactory.loadRegistries();

        JFrame frame = new JFrame(AddNodePanel.class.getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new AddNodePanel(Arrays.asList(NodeFactory.getNames())));
        frame.pack();
        frame.setVisible(true);
    }
}
