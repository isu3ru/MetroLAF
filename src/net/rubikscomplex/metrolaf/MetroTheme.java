/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public abstract class MetroTheme {
    protected static final ColorUIResource white = new ColorUIResource(Color.WHITE);
    protected static final ColorUIResource black = new ColorUIResource(Color.BLACK);
    protected static final ColorUIResource lightGrey = new ColorUIResource(242,242,242);
    protected static final ColorUIResource darkGrey = new ColorUIResource(119,119,119);
    protected static final ColorUIResource disabled = new ColorUIResource(152,152,152);
    
    public Color getDefaultForeground() { return black; }
    public Color getDefaultBackground() { return white; }
    
    public abstract Color getButtonBackground();
    public Color getButtonForeground() { return darkGrey; }
    public abstract Color getButtonHighlightBackground();
    public abstract Color getButtonHighlightForeground();
    public abstract Color getButtonPressedBackground();
    public Color getButtonPressedForeground() { return getButtonHighlightForeground(); }
    
    public abstract Color getControlBorder();
    public abstract Color getControlFocusBorder();
    
    public Color getScrollBarBackground() { return lightGrey; }
    
    public abstract Color getFrameBorder();
    
    public Color getPrimary() { return black; }
    public abstract Color getPrimary2();
    
    public Color getSecondary() { return white; }
    public Color getSecondary2() { return disabled; }
    
    public Color getDisabledTextColor() { return disabled; }
}
