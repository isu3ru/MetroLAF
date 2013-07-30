/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroMenuItemUI extends BasicMenuItemUI {
    protected JMenuItem mi = null;
    protected MetroMenuItemUI() {
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroMenuItemUI();
    }

    @Override
    public void installUI(JComponent c) {
        mi = (JMenuItem)c;
        super.installUI(c);
        Dimension d = mi.getPreferredSize();
        d.height = 24;
        mi.setPreferredSize(d);
    }
    
    @Override
    public void paintBackground(Graphics g, JMenuItem mi, Color bgc) {
        Color oc = g.getColor();
        int w = mi.getWidth();
        int h = mi.getHeight();
        ButtonModel bm = mi.getModel();
                
        MetroLookAndFeel.getLogger().info(String.format("%s %s %s", mi.getText(), bgc, Boolean.valueOf(mi.isArmed())));

        if (!mi.isOpaque()) {
            return;
        }
        
        g.setColor(mi.isArmed() ? bgc : mi.getBackground());
        g.fillRect(0, 0, w, h);
        g.setColor(oc);
    }
}
