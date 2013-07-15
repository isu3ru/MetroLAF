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
public class MetroBlueTheme extends MetroTheme {
    protected static final ColorUIResource controlBorder = new ColorUIResource(171,171,171);
    protected static final ColorUIResource frameBorder = new ColorUIResource(43,87,154);
    protected static final ColorUIResource focusBorder = new ColorUIResource(163,189,227);
    
    protected static final ColorUIResource textHighlightBackground = new ColorUIResource(136,195,255);
    protected static final ColorUIResource buttonBackground = new ColorUIResource(120,120,120);
    protected static final ColorUIResource controlHighlightBackground = new ColorUIResource(213,225,242);
    protected static final ColorUIResource controlHighlightForeground = new ColorUIResource(62,109,181);
    protected static final ColorUIResource controlPressedBackground = focusBorder;
    
    protected static final ColorUIResource darkPrimary = new ColorUIResource(25,71,138);
    
    @Override
    public Color getButtonBackground() { return buttonBackground; }
    @Override
    public Color getButtonHighlightBackground() { return controlHighlightBackground; }
    @Override
    public Color getButtonHighlightForeground() { return controlHighlightForeground; }
    @Override
    public Color getButtonPressedBackground() { return controlPressedBackground; }
    @Override
    public Color getControlBorder() { return controlBorder; }
    @Override
    public Color getControlFocusBorder() { return focusBorder; }
    @Override
    public Color getFrameBorder() { return frameBorder; }
    @Override
    public Color getPrimary2() { return darkPrimary;  }
}
