package com.marginallyclever.donatello.component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ThreeNumberGroupPanel extends JPanel {
    private final ArrayList<JPanel> coordinatePanels;
    private final JButton addButton;
    private final JButton removeButton;
    private final JPanel coordinatesContainer;
    private JScrollPane scrollPane;

    public ThreeNumberGroupPanel() {
        coordinatePanels = new ArrayList<>();
        setLayout(new BorderLayout());

        coordinatesContainer = new JPanel();
        coordinatesContainer.setLayout(new BoxLayout(coordinatesContainer, BoxLayout.Y_AXIS));


        scrollPane = new JScrollPane(coordinatesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addButton = new JButton("Add Coordinate");
        removeButton = new JButton("Remove Coordinate");

        addButton.addActionListener(e -> {
            addCoordinateInputPanel();
        });
        removeButton.addActionListener(e -> {
            removeCoordinateInputPanel();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add the initial coordinate input panel
        for (int i = 0; i < 5; i++) {
            addCoordinateInputPanel();
            initSize();
        }
    }

    private void addCoordinateInputPanel() {
        ThreeNumberInputPanel coordinateInputPanel = new ThreeNumberInputPanel();
        coordinatePanels.add(coordinateInputPanel);
        coordinatesContainer.add(coordinateInputPanel);
        update();
    }

    private void removeCoordinateInputPanel() {
        if (coordinatePanels.size() > 1) {
            JPanel panel = coordinatePanels.remove(coordinatePanels.size() - 1);
            coordinatesContainer.remove(panel);
            update();
        }
    }

    private void initSize() {
        int totalHeight = 0;
        int totalWidth = coordinatePanels.get(0).getWidth();

        for (JPanel panel : coordinatePanels) {
            totalHeight += panel.getHeight();
        }
        Dimension preferredSize = new Dimension(totalWidth, totalHeight);
        scrollPane.setSize(preferredSize);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void update() {
        int totalHeight = 0;
        int totalWidth = coordinatePanels.get(0).getWidth();

        for (JPanel panel : coordinatePanels) {
            totalHeight += panel.getHeight();
        }
        Dimension preferredSize = new Dimension(totalWidth, totalHeight);
        coordinatesContainer.setSize(preferredSize);
        coordinatesContainer.revalidate();
        coordinatesContainer.repaint();
    }
}