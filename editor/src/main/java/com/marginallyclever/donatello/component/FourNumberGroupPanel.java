package com.marginallyclever.donatello.component;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FourNumberGroupPanel extends JPanel {
    private final List<FourNumberInputPanel> inputPanels;
    private final JButton addButton;
    private final JButton removeButton;
    private final JPanel coordinatesContainer;
    private JScrollPane scrollPane;

    public FourNumberGroupPanel() {
        inputPanels = new ArrayList<>();
        setLayout(new BorderLayout());

        coordinatesContainer = new JPanel();
        coordinatesContainer.setLayout(new BoxLayout(coordinatesContainer, BoxLayout.Y_AXIS));


        scrollPane = new JScrollPane(coordinatesContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addButton = new JButton("Add Coordinate");
        removeButton = new JButton("Remove Coordinate");

        addButton.addActionListener(e -> {
            addInputPanel();
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
            addInputPanel();
        }
        initSize();
    }

    public void addInputPanel() {
        FourNumberInputPanel coordinateInputPanel = new FourNumberInputPanel();
        inputPanels.add(coordinateInputPanel);
        coordinatesContainer.add(coordinateInputPanel);
        update();
    }

    public void addInputPanel(FourNumberInputPanel coordinateInputPanel) {
        inputPanels.add(coordinateInputPanel);
        coordinatesContainer.add(coordinateInputPanel);
        update();
    }

    public void clear() {
        inputPanels.clear();
        coordinatesContainer.removeAll();
    }

    public void removeCoordinateInputPanel() {
        if (inputPanels.size() > 1) {
            JPanel panel = inputPanels.remove(inputPanels.size() - 1);
            coordinatesContainer.remove(panel);
            update();
        }
    }

    private void initSize() {
        int totalHeight = 0;
        int totalWidth = (int) inputPanels.get(0).getMinimumSize().getWidth();

        for (JPanel panel : inputPanels) {
            totalHeight += (int) panel.getMinimumSize().getHeight();
        }
        totalHeight += 20;
        Dimension preferredSize = new Dimension(totalWidth, totalHeight);
        scrollPane.setPreferredSize(preferredSize);
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void update() {
        int totalHeight = 0;
        int totalWidth = inputPanels.get(0).getWidth();

        for (JPanel panel : inputPanels) {
            totalHeight += panel.getHeight();
        }
        Dimension preferredSize = new Dimension(totalWidth, totalHeight);
        coordinatesContainer.setSize(preferredSize);
        coordinatesContainer.revalidate();
        coordinatesContainer.repaint();
    }

    public List<FourNumberInputPanel> getInputPanels() {
        return inputPanels;
    }
}