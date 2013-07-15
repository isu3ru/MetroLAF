/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import sun.swing.SwingUtilities2;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroButtonUI extends BasicButtonUI {
    protected Color focusColor;
    protected Color selectColor;
    protected Color disabledTextColor;
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroButtonUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        // MetroLookAndFeel.getLogger().info("installUI called... (" + c.toString() + ")");
        if (c instanceof JMenuItem) {
            MetroLookAndFeel.getLogger().info("*** ButtonUI called for JMenuItem.");
        }
    }
    
    protected Color getSelectColor() {
        selectColor = UIManager.getColor(getPropertyPrefix() + "select");
        return selectColor;
    }
    
    protected Color getDisabledTextColor() {
        disabledTextColor = UIManager.getColor(getPropertyPrefix() + "disabledText");
        return disabledTextColor;
    }
    
    protected Color getFocusColor() {
        focusColor = UIManager.getColor(getPropertyPrefix() + "focus");
        return focusColor;
    }
    
    @Override
    public void update(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton)c;
        if (b.isContentAreaFilled() && c.isEnabled()) {
            ButtonModel m = b.getModel();
            if ((!m.isArmed() && !m.isPressed()) || m.isRollover()) {
                Color bg = b.getBackground();
                if (m.isRollover()) {
                    bg = bg.darker();
                }
                g.setColor(bg);
                g.fillRect(0, 0, b.getWidth(), b.getHeight());
                paint(g, c);
                return;
            }
        }
        super.update(g, c);
    }
    
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        if (b.isContentAreaFilled()) {
            Dimension d = b.getSize();
            g.setColor(getSelectColor());
            g.fillRect(0, 0, d.width, d.height);
        }
    }
    
    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle vr, Rectangle tr, Rectangle ir) {
        Rectangle fr = new Rectangle();
        String t = b.getText();
        boolean isIcon = b.getIcon() != null;
        
        if (t != null && t.isEmpty()) {
            if (!isIcon) {
                fr.setBounds(tr);
            }
            else {
                fr.setBounds(ir.union(tr));
            }
        }
        else if (isIcon) {
            fr.setBounds(ir);
        }
        
        g.setColor(getFocusColor());
        g.drawRect(fr.x-1, fr.y-1, fr.width+1, fr.height+1);
    }
    
    @Override
    protected void paintText(Graphics g, AbstractButton b, Rectangle tr, String t) {
        ButtonModel m = b.getModel();
        FontMetrics fm = SwingUtilities2.getFontMetrics(b, g);
        Insets i = b.getMargin();
        int mi = b.getDisplayedMnemonicIndex();
        
        if (m.isEnabled()) {
            g.setColor(b.getForeground());
        }
        else {
            g.setColor(getDisabledTextColor());
        }
        SwingUtilities2.drawStringUnderlineCharAt(b, g, t, mi, tr.x, tr.y+fm.getAscent());
    }
}
