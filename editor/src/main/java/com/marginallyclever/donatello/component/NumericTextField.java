package com.marginallyclever.donatello.component;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class NumericTextField extends JTextField {

    public NumericTextField(int columns) {
        super(columns);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new NumericDocumentFilter());
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

        private boolean isNumeric(String text) {
            return text.matches("[-0-9.]*");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Numeric Text Field Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("坐标: ");
        frame.add(label);

        NumericTextField xField = new NumericTextField(5);
        NumericTextField yField = new NumericTextField(5);
        NumericTextField zField = new NumericTextField(5);

        frame.add(xField);
        frame.add(yField);
        frame.add(zField);

        frame.setVisible(true);
    }
}