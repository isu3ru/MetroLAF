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
import javax.swing.AbstractButton;
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
            Color foreground = Color.BLUE.darker();
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
        protected static Insets bi = new Insets(3, 3, 3, 3);
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            // Empty, we want no button borders
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new Insets(0, 0, 0, 0));
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets ni) {
            Insets ci = new Insets(0, 0, 0, 0);
            if (c instanceof AbstractButton) {
                ci = ((AbstractButton)c).getMargin();
            }
            ni.set(bi.top+ci.top, bi.left+ci.left, bi.bottom+ci.bottom, bi.right+ci.right);
            return ni;
        }
    }
    
    public static class TextFieldBorder extends AbstractBorder implements UIResource {
        protected static Insets bi = new Insets(5, 6, 5, 6);

        @Override 
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect(x, y, w-1, h-1);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new Insets(0, 0, 0, 0));
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets ni) {
            ni.set(bi.top, bi.left, bi.bottom, bi.right);
            return ni;
        }
    }
}
