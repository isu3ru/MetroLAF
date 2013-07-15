/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
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

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroLookAndFeel extends MetalLookAndFeel {
    @SuppressWarnings("NonConstantLogger")
    protected static Logger logger = null;
    protected static Font bodyFont = null;
    protected static Font cbFont = null;
    
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
    
    @Override
    protected void initClassDefaults(UIDefaults table) {
        MetroLookAndFeel.getLogger().info("initClassDefaults called...");
        super.initClassDefaults(table);
        
        Object[] uiDefaults = {
            "ButtonUI", "net.rubikscomplex.metrolaf.MetroButtonUI",
            "ComboBoxUI", "net.rubikscomplex.metrolaf.MetroComboBoxUI",
            "RootPaneUI", "net.rubikscomplex.metrolaf.MetroRootPaneUI",
            "MenuBarUI", "net.rubikscomplex.metrolaf.MetroMenuBarUIdasdfsd",
        };
        
        table.putDefaults(uiDefaults);
    }
    
    @Override
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);
        // Remove default Metal button border
        Object[] uiDefaults = {
            "Button.border", new ButtonBorder(),
            "ComboBox.border", BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            "RootPane.frameBorder", new FrameBorder(),
            "Panel.background", Color.WHITE,
            "Menu.background", Color.WHITE,
            "MenuItem.background", Color.WHITE,
            "MenuBar.background", Color.WHITE,
            "PopupMenu.background", Color.WHITE,
            "Label.background", Color.WHITE,
            "Viewport.background", Color.WHITE,
            "Desktop.background", Color.WHITE,
            "OptionPane.background", Color.WHITE,
            "ComboBox.background", Color.WHITE,
            "ComboBox.selectionBackground", Color.BLUE,
            "ComboBox.selectionForeground", Color.WHITE,
            "Button.focus", Color.BLUE,
            "MenuItem.selectionBackground", Color.BLUE,
            "MenuItem.selectionForeground", Color.WHITE,
            "MenuItem.border", BorderFactory.createEmptyBorder(3, 3, 3, 3),
            "control", Color.WHITE,
        };
        table.putDefaults(uiDefaults);
    }
    
    public static void setUIFont(Font f) {
        // System.err.println(UIManager.getDefaults().get("TabbedPane.font"));
        /*
        UIManager.getDefaults().put("Panel.background", Color.WHITE);
        UIManager.getDefaults().put("Menu.background", Color.WHITE);
        UIManager.getDefaults().put("MenuItem.background", Color.WHITE);
        UIManager.getDefaults().put("MenuBar.background", Color.WHITE);
        UIManager.getDefaults().put("PopupMenu.background", Color.WHITE);
        */
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object k = keys.nextElement();
            Object v = UIManager.getDefaults().get(k);
            if (k.toString().endsWith(".background")) {
                // MetroLookAndFeel.getLogger().log(Level.INFO, "{0}: {1}", new Object[]{k.toString(), v});
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
