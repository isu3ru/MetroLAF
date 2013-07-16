/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroProgressBarUI extends BasicProgressBarUI {
    public static ComponentUI createUI(JComponent c) {
        return new MetroProgressBarUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        ((JProgressBar)c).setBorderPainted(false);
    }
}
