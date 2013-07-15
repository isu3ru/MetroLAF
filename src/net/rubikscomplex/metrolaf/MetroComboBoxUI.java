/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroComboBoxUI extends BasicComboBoxUI {
    public static final Character M_BUTTONARROW = Character.valueOf((char)0xF036);
    
    public static ComponentUI createUI(JComponent c) {
        return new MetroComboBoxUI();
    }
    
    public MetroComboBoxUI() {
        super();
    }
    
    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
    }
    
    @Override
    public PropertyChangeListener createPropertyChangeListener() {
        return new MetroComboBoxUIPropertyChangeListener();
    }
    
    @Override
    public JButton createArrowButton() {
        JButton b = new JButton(M_BUTTONARROW.toString());
        b.setFont(MetroLookAndFeel.getCBFont());
        b.setBackground(Color.WHITE);
        b.setMargin(new Insets(2, 2, 0, 0));
        return b;
    }
    
    @Override
    public ListCellRenderer createRenderer() {
        return new MetroListCellRenderer();
    }
    
    public class MetroComboBoxUIPropertyChangeListener extends BasicComboBoxUI.PropertyChangeHandler {
        @Override
        public void propertyChange(PropertyChangeEvent e) {
            super.propertyChange(e);
            String pn = e.getPropertyName();
            switch(pn) {
                case "background":
                    Color b = (Color)e.getNewValue();
                    arrowButton.setBackground(b);
                    listBox.setBackground(b);
                    break;
                case "foreground":
                    Color f = (Color)e.getNewValue();
                    arrowButton.setForeground(f);
                    listBox.setForeground(f);
                    break;
            }
        }
    }
    
    public class MetroListCellRenderer extends DefaultListCellRenderer {
        public final Border border = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        
        @Override
        public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean hf) {
            Component c = super.getListCellRendererComponent(l, v, i, is, hf);
            if (c instanceof JComponent) {
                ((JComponent)c).setBorder(border);
            }
            return c;
        }
    }
}
