/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroBorders {
    public static class FrameBorder extends AbstractBorder implements UIResource {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Color foreground = Color.BLUE;
            Color inactiveForeground = Color.GRAY;
            
            g.setColor(foreground);
            
            Window win = SwingUtilities.getWindowAncestor(c);
            if (win != null && !win.isActive()) {
                g.setColor(inactiveForeground);
            }
            
            g.drawRect(x, y, w-1, h-1);
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets ni) {
            ni.set(1, 1, 1, 1);
            return ni;
        }
    }
    
    public static class ButtonBorder extends AbstractBorder implements UIResource {
        protected static Insets borderInsets = new Insets(3, 3, 3, 3);
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            // Empty, we want no button borders
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets ni) {
            ni.set(3, 3, 3, 3);
            return ni;
        }
    }
}
