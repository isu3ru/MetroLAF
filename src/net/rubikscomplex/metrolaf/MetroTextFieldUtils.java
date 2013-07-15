/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import sun.swing.SwingUtilities2;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroTextFieldUtils {
    protected static FocusListener promptFocusListener = new MetroTextFieldFocusListener();
    
    public static void installListeners(JTextField tf) {
        if (tf instanceof MetroPromptTextField) {
            tf.addFocusListener(promptFocusListener);
        }
    }
    
    public static void uninstallListeners(JTextField tf) {
        if (tf instanceof MetroPromptTextField) {
            tf.removeFocusListener(promptFocusListener);
        }
    }
    
    public static void drawEmptyPrompt(Graphics g, JTextField textField, Rectangle vr) {
        if (!(textField instanceof MetroPromptTextField)) {
            return;
        }
        MetroPromptTextField tf = (MetroPromptTextField)textField;
        if (tf.getEmptyPrompt() != null && textField.getText().isEmpty() && textField.isEnabled() && !textField.hasFocus()) {
            MetroLookAndFeel.getLogger().info(String.format("Painting text field background (want to draw empty prompt) %d %d.", vr.x, vr.y));
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(textField.getFont().deriveFont(Font.ITALIC));
            // g.setFont(g.getFont().deriveFont(Font.ITALIC));
            FontMetrics fm = g.getFontMetrics();
            SwingUtilities2.drawString(textField, g, tf.getEmptyPrompt(), vr.x, vr.y+fm.getAscent());
        }
    }
    
    public static class MetroTextFieldFocusListener extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            e.getComponent().revalidate();
            e.getComponent().repaint();
        }
        
        @Override
        public void focusLost(FocusEvent e) {
            e.getComponent().revalidate();
            e.getComponent().repaint();
        }
    }
}
