/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroPasswordFieldUI extends BasicPasswordFieldUI {
    protected JPasswordField passField = null;
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroPasswordFieldUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        passField = (JPasswordField)c;
        super.installUI(c);
    }
    
    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        passField = null;
    }
    
    @Override
    public void installListeners() {
        super.installListeners();
        MetroTextFieldUtils.installListeners(passField);
    }
    
    @Override
    public void uninstallListeners() {
        super.uninstallListeners();
        MetroTextFieldUtils.uninstallListeners(passField);
    }
    
    @Override
    public void paintBackground(Graphics g) {
        super.paintBackground(g);
        MetroTextFieldUtils.drawEmptyPrompt(g, passField, getVisibleEditorRect());
    }
}
