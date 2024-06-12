package com.marginallyclever.donatello.component;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ThreeNumberInputPanel extends JPanel {
    private final JTextField xField;
    private final JTextField yField;
    private final JTextField zField;

    public ThreeNumberInputPanel() {
        xField = new NumericTextField(5);
        yField = new NumericTextField(5);
        zField = new NumericTextField(5);

        add(xField);
        add(yField);
        add(zField);
    }

    public Double getXValue() {
        return Double.parseDouble(xField.getText());
    }

    public Double getYValue() {
        return Double.parseDouble(yField.getText());
    }

    public Double getZValue() {
        return Double.parseDouble(zField.getText());
    }

    private static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            if (isNumeric(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                return;
            }
            if (isNumeric(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isNumeric(String text) {
            return text.matches("[0-9.]*");
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Coordinate Input Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);
        frame.add(new ThreeNumberInputPanel());
        frame.setVisible(true);
    }
}