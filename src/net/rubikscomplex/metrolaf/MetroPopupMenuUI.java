/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroPopupMenuUI extends BasicPopupMenuUI {
    protected JPopupMenu popupMenu = null;
    protected Border pmBorder = null;
    protected ContainerListener containerListener = null;
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroPopupMenuUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        MetroLookAndFeel.getLogger().info("Installing popup menu UI on: " + c.toString());
        popupMenu = (JPopupMenu)c;
        super.installUI(c);
    }
    
    @Override
    public Popup getPopup(JPopupMenu pm, int x, int y) {
        if (pmBorder == null) {
            // pmBorder = pm.getBorder();
            pmBorder = pm.getBorder();
            MetroLookAndFeel.getLogger().info(pmBorder.toString());
            pm.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        }
        MetroLookAndFeel.getLogger().info(pm.getLayout().toString());
        return super.getPopup(pm, x, y);
    }
    
    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        popupMenu = null;
        pmBorder = null;
    }
    
    @Override
    public void installListeners() {
        super.installListeners();
        if (containerListener == null) {
            containerListener = new MetroPopupMenuContainerListener();
        }
        popupMenu.addContainerListener(containerListener);
    }
    
    @Override
    public void uninstallListeners() {
        super.uninstallListeners();
        popupMenu.removeContainerListener(containerListener);
        containerListener = null;
    }
    
    public class MetroPopupMenuContainerListener extends ContainerAdapter {
        @Override
        public void componentAdded(ContainerEvent e) {
            // System.err.println(String.format("*** %s", e.getChild().toString()));
            if (e.getChild() instanceof Container && !(e.getChild() instanceof JMenuItem)) {
                ((Container)e.getChild()).addContainerListener(new MetroPopupMenuContainerListener());
            }
        }
    }
}
