/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import net.rubikscomplex.metrolaf.MetroBorders.RolloverButtonBorder;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroScrollBarUI extends BasicScrollBarUI {
    public static final Character M_HDECREASE = Character.valueOf((char)0xF033);
    public static final Character M_HINCREASE = Character.valueOf((char)0xF034);
    public static final Character M_VDECREASE = Character.valueOf((char)0xF035);
    public static final Character M_VINCREASE = Character.valueOf((char)0xF036);
    
    protected JScrollBar sb = null;
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroScrollBarUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        sb = (JScrollBar)c;
        super.installUI(c);
    }
    
    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        sb = null;
    }
    
    protected Character getArrowChar(int orientation) {
        switch(orientation) {
            case SwingConstants.NORTH:
                return M_VDECREASE;
            case SwingConstants.SOUTH:
                return M_VINCREASE;
            case SwingConstants.WEST:
                return M_HDECREASE;
            case SwingConstants.EAST:
                return M_HINCREASE;
            default:
                return M_HDECREASE;
        }
    }
    
    protected Insets getInsets(int orientation) {
        switch(orientation) {
            case SwingConstants.NORTH:
                return new Insets(2, 1, 0, 0);
            case SwingConstants.SOUTH:
                return new Insets(-1, 1, 0, 0);
            case SwingConstants.WEST:
                return new Insets(1, 1, 0, 0);
            case SwingConstants.EAST:
                return new Insets(1, -1, 0, 0);
            default:
                return new Insets(0, 0, 0, 0);
        }
    }
    
    @Override
    public JButton createDecreaseButton(int orientation) { 
        JButton b = new JButton(getArrowChar(orientation).toString());
        b.setBorder(new RolloverButtonBorder());
        b.setBackground(MetroLookAndFeel.getCurrentMetroTheme().getSecondary());
        b.setForeground(MetroLookAndFeel.getCurrentMetroTheme().getPrimary());
        b.setPreferredSize(new Dimension(scrollBarWidth, scrollBarWidth));
        b.setFocusable(false);
        b.setMargin(getInsets(orientation));
        b.setFont(MetroLookAndFeel.getCBFont().deriveFont(10.0f));
        return b;
    }
    
    @Override
    public JButton createIncreaseButton(int orientation) {
        JButton b = new JButton(getArrowChar(orientation).toString());
        b.setBorder(new RolloverButtonBorder());
        b.setBackground(MetroLookAndFeel.getCurrentMetroTheme().getSecondary());
        b.setForeground(MetroLookAndFeel.getCurrentMetroTheme().getPrimary());
        b.setPreferredSize(new Dimension(scrollBarWidth, scrollBarWidth));
        b.setFocusable(false);
        b.setMargin(getInsets(orientation));
        b.setFont(MetroLookAndFeel.getCBFont().deriveFont(10.0f));
        return b;
    }
    
    @Override
    public void paintTrack(Graphics g, JComponent c, Rectangle tr) {
        g.setColor(MetroLookAndFeel.getCurrentMetroTheme().getScrollBarBackground());
        if (sb.getOrientation() == JScrollBar.VERTICAL) {
            g.fillRect(tr.x+1, tr.y, tr.width-2, tr.height-1);
        }
        else {
            g.fillRect(tr.x, tr.y+1, tr.width-1, tr.height-2);
        }
    }
    
    @Override
    public void paintThumb(Graphics g, JComponent c, Rectangle tr) {
        MetroTheme theme = MetroLookAndFeel.getCurrentMetroTheme();
        Color fc = null;
        Color bc = null;
        if (isThumbRollover()) {
            fc = theme.getScrollBarBackground();
            bc = theme.getDefaultForeground();
        }
        else {
            fc = theme.getSecondary();
            bc = theme.getSecondary2();
        }
        g.setColor(fc);
        if (sb.getOrientation() == JScrollBar.VERTICAL) {
            g.fillRect(tr.x+1, tr.y, tr.width-3, tr.height-1);
            g.setColor(bc);
            g.drawRect(tr.x+1, tr.y, tr.width-3, tr.height-1);
        }
        else {
            g.fillRect(tr.x, tr.y+1, tr.width-1, tr.height-3);
            g.setColor(bc);
            g.drawRect(tr.x, tr.y+1, tr.width-1, tr.height-3);
        }
    }
}
