/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

/**
 *
 * @author Dav
 */
public class MetroSeparatorUI extends BasicSeparatorUI {
    public static ComponentUI createUI(JComponent c) {
        MetroLookAndFeel.getLogger().info("Creating MetroPopupMenuSeparatorUI...");
        return new MetroSeparatorUI();
    }
    
    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension d = super.getPreferredSize(c);
        MetroLookAndFeel.getLogger().info(d.toString());
        d.width = Math.min(d.width, 1);
        d.height = Math.min(d.height, 1);
        return d;
    }
}
