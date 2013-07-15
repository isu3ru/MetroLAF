/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroMenuBarUI extends BasicMenuBarUI {
    public static final int MIN_MENU_WIDTH = 125;
    public static final int MENU_ITEM_MARGIN = 5;
    
    protected PropertyChangeListener propertyChangeListener;
    protected ContainerListener containerListener;
    protected JMenuBar menuBar;
    
    public static ComponentUI createUI(JComponent c) {
        MetroLookAndFeel.getLogger().log(Level.INFO, "*** Creating MenuBarUI for: {0}", c.toString());
        return new MetroMenuBarUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        menuBar = (JMenuBar)c;
        MetroLookAndFeel.getLogger().log(Level.INFO, "Installing MenuBarUI for: {0}", c.toString());
        super.installUI(c);
    }
    
    @Override
    public void installListeners() {
        propertyChangeListener = new MetroMenuBarPropertyChangeListener();
        containerListener = new MetroMenuBarContainerListener();
        menuBar.addPropertyChangeListener(propertyChangeListener);
        menuBar.addContainerListener(containerListener);
    }
    
    @Override
    public void uninstallUI(JComponent c) {
        menuBar.removePropertyChangeListener(propertyChangeListener);
        menuBar.removeContainerListener(containerListener);
        propertyChangeListener = null;
        containerListener = null;
        menuBar = null;
    }
    
    public class MetroMenuBarPropertyChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            MetroLookAndFeel.getLogger().info(e.getPropertyName());
        }
    }
    
    public class MetroMenuBarContainerListener extends ContainerAdapter {
        @Override
        public void componentAdded(ContainerEvent e) {
            MetroLookAndFeel.getLogger().info(e.getChild().toString());
            if (e.getChild() instanceof JMenuItem) {
                MetroLookAndFeel.getLogger().info("  Adding new listener to above.");
                ((JMenuItem)e.getChild()).addItemListener(new MetroMenuItemItemListener());
                ((JMenuItem)e.getChild()).addContainerListener(this);
            }
        }
    }
    
    public class MetroMenuItemItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            MetroLookAndFeel.getLogger().log(Level.INFO, "*** {0}: {1}", new Object[]{e.getItemSelectable().toString(), e.getStateChange()});
        }
    }
}
