/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import net.rubikscomplex.metrolaf.MetroBorders.ButtonBorder;
import net.rubikscomplex.metrolaf.MetroBorders.FrameBorder;
import net.rubikscomplex.metrolaf.MetroBorders.ControlBorder;
import net.rubikscomplex.metrolaf.MetroBorders.TextFieldBorder;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroLookAndFeel extends MetalLookAndFeel {
    @SuppressWarnings("NonConstantLogger")
    protected static Logger logger = null;
    protected static Font bodyFont = null;
    protected static Font cbFont = null;
    protected static MetroTheme currentTheme = new MetroBlueTheme();
    
    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("net.rubikscomplex.metrolaf");
            logger.setUseParentHandlers(false);
        }
        return logger;
    }
    
    public static Font getBodyFont() {
        if (bodyFont == null) {
            bodyFont = new Font("Roboto", Font.PLAIN, 12);
            // bodyFont = new Font("Segoe UI", Font.PLAIN, 12);
        }
        return bodyFont;
    }
    
    public static Font getCBFont() {
        if (cbFont == null) {
            cbFont = new Font("Marlett", Font.PLAIN, 12);
            MetroLookAndFeel.getLogger().info("Found and used Marlett font.");
        }
        return cbFont;
    }
    
    public static MetroTheme getCurrentMetroTheme() {
        return currentTheme;
    }
    
    public static void setCurrentMetroTheme(MetroTheme theme) {
        currentTheme = theme;
    }
    
    @Override
    protected void initClassDefaults(UIDefaults table) {
        MetroLookAndFeel.getLogger().info("initClassDefaults called...");
        super.initClassDefaults(table);
        
        Object[] uiDefaults = {
            "ButtonUI", "net.rubikscomplex.metrolaf.MetroButtonUI",
            "ComboBoxUI", "net.rubikscomplex.metrolaf.MetroComboBoxUI",
            "RootPaneUI", "net.rubikscomplex.metrolaf.MetroRootPaneUI",
            "MenuBarUI", "net.rubikscomplex.metrolaf.MetroMenuBarUIdasdfsd",
            "PopupMenuUI", "net.rubikscomplex.metrolaf.MetroPopupMenuUI",
            "TextFieldUI", "net.rubikscomplex.metrolaf.MetroTextFieldUI",
            "PasswordFieldUI", "net.rubikscomplex.metrolaf.MetroTextFieldUI"
        };
        
        table.putDefaults(uiDefaults);
    }
    
    @Override
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);
        
        MetroTheme theme = getCurrentMetroTheme();
        Object[] uiDefaults = {
            "Button.border", new ButtonBorder(),
            "ComboBox.border", new ControlBorder(),
            "TextField.border", new TextFieldBorder(),
            "PasswordField.border", new TextFieldBorder(),
            "RootPane.frameBorder", new FrameBorder(),
            "Panel.background", theme.getSecondary(),
            "Menu.background", theme.getSecondary(),
            "MenuItem.background", theme.getSecondary(),
            "MenuBar.background", theme.getSecondary(),
            "PopupMenu.background", theme.getSecondary(),
            "Label.background", theme.getSecondary(),
            "Label.foreground", theme.getSecondary2(),
            "Viewport.background", theme.getSecondary(),
            "Desktop.background", theme.getSecondary(),
            "OptionPane.background", theme.getSecondary(),
            "ComboBox.background", theme.getSecondary(),
            "ComboBox.selectionBackground", theme.getButtonHighlightBackground(),
            "ComboBox.selectionForeground", theme.getButtonHighlightForeground(),
            "Button.focus", theme.getPrimary2(),
            "MenuItem.selectionBackground", theme.getButtonHighlightBackground(),
            "MenuItem.selectionForeground", theme.getButtonHighlightForeground(),
            "MenuItem.border", BorderFactory.createEmptyBorder(3, 3, 3, 3),
            "control", theme.getSecondary(),
        };
        table.putDefaults(uiDefaults);
    }
    
    public static void setUIFont(Font f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object k = keys.nextElement();
            Object v = UIManager.getDefaults().get(k);
            if (k.toString().startsWith("Button.")) {
                MetroLookAndFeel.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{k.toString(), v});
            }
            if (v instanceof Color) {
                Color c = (Color)v;
                if (c.getRed() == 163 && c.getGreen() == 184) {
                    // System.err.println("*** "+k.toString());
                    // UIManager.getDefaults().put(k, Color.WHITE);
                }
            }
            // System.err.println(k.toString()+": "+v);
            if (v != null && v instanceof FontUIResource) {
                // MetroLookAndFeel.getLogger().log(Level.INFO, "  Replacing: {0}", k.toString());
                UIManager.getDefaults().put(k, f);
            }
        }
    }
}
