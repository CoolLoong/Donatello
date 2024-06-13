package com.marginallyclever.donatello.component;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FourNumberInputPanel extends JPanel {
    private final JTextField xField;
    private final JTextField yField;
    private final JTextField zField;
    private final JTextField wField;

    public FourNumberInputPanel() {
        xField = new NumericTextField(5);
        yField = new NumericTextField(5);
        zField = new NumericTextField(5);
        wField = new NumericTextField(5);

        add(xField);
        add(yField);
        add(zField);
        add(wField);
    }

    public Double getXValue() {
        try {
            return Double.parseDouble(xField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setXValue(double v) {
        xField.setText(String.valueOf(v));
    }

    public Double getYValue() {
        try {
            return Double.parseDouble(yField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setYValue(double v) {
        yField.setText(String.valueOf(v));
    }

    public Double getZValue() {
        try {
            return Double.parseDouble(zField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setZValue(double v) {
        zField.setText(String.valueOf(v));
    }

    public Double getWValue() {
        try {
            return Double.parseDouble(wField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void setWValue(double v) {
        wField.setText(String.valueOf(v));
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
        frame.add(new FourNumberInputPanel());
        frame.setVisible(true);
    }
}