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
import javax.swing.ButtonModel;
import javax.swing.SwingUtilities;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroBorders {
    protected static MetroTheme getCurrentTheme() {
        return MetroLookAndFeel.getCurrentMetroTheme();
    }
    
    public static class FrameBorder extends AbstractBorder implements UIResource {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Color foreground = getCurrentTheme().getFrameBorder();
            Color inactiveForeground = getCurrentTheme().getDisabledTextColor();
            
            g.setColor(foreground);
            
            Window win = SwingUtilities.getWindowAncestor(c);
            if (win != null && !win.isActive()) {
                g.setColor(inactiveForeground);
            }
            
            g.drawRect(x, y, w-1, h-1);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new InsetsUIResource(0, 0, 0, 0));
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
            // Empty, we want empty (invisible) button borders
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new InsetsUIResource(0, 0, 0, 0));
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
    
    public static class RolloverButtonBorder extends ButtonBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            AbstractButton b = (AbstractButton)c;
            ButtonModel m = b.getModel();
            MetroTheme theme = MetroLookAndFeel.getCurrentMetroTheme();
            if (m.isRollover()) {
                g.setColor(theme.getPrimary());
            }
            else {
                g.setColor(theme.getSecondary2());
            }
            g.drawRect(x+1, y+1, w-3, h-3);
        }
    }
    
    public static class ControlBorder extends AbstractBorder implements UIResource {
        protected static Insets bi = new Insets(1, 1, 1, 1);

        @Override 
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            if (c.hasFocus()) {
                g.setColor(MetroLookAndFeel.getCurrentMetroTheme().getControlFocusBorder());
            }else {
                g.setColor(MetroLookAndFeel.getCurrentMetroTheme().getControlBorder());
            }
            g.drawRect(x, y, w-1, h-1);
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return getBorderInsets(c, new InsetsUIResource(0, 0, 0, 0));
        }
        
        @Override
        public Insets getBorderInsets(Component c, Insets ni) {
            ni.set(bi.top, bi.left, bi.bottom, bi.right);
            return ni;
        }
    }
    
    public static class TextFieldBorder extends ControlBorder {
        @Override
        public Insets getBorderInsets(Component c, Insets ni) {
            ni.set(5, 6, 5, 5);
            return ni;
        }
    }
}
