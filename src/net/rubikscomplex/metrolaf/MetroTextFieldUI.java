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
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import sun.swing.SwingUtilities2;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroTextFieldUI extends BasicTextFieldUI {
    protected JTextField textField = null;
    protected FocusListener focusListener = null;
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroTextFieldUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        textField = (JTextField)c;
        // System.err.println("*** Installing textfieldUI for: " + textField.toString());
        super.installUI(c);
    }
    
    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        textField = null;
    }
    
    @Override
    public void installListeners() {
        MetroTextFieldUtils.installListeners(textField);
    }
    
    @Override
    public void uninstallListeners() {
        MetroTextFieldUtils.uninstallListeners(textField);
    }
    
    @Override
    public void paintBackground(Graphics g) {
        super.paintBackground(g);
        // MetroLookAndFeel.getLogger().info("Painting text field background." + textField.toString());
        MetroTextFieldUtils.drawEmptyPrompt(g, textField, getVisibleEditorRect());
    }
    
}
