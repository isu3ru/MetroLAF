/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.List;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import sun.awt.SunToolkit;

/**
 * TODO: Make font code more robust
 * TODO: Fix menu bar glitching
 * 
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroTitlePane extends JComponent implements PropertyChangeListener {
    public static final int PANE_HEIGHT = 25;
    public static final int CONTENT_HEIGHT = 22;
    
    public static final int CB_BUTTON_WIDTH = 29;
    
    public static final int ICON_HEIGHT = 16;
    public static final int ICON_WIDTH = 16;
    
    protected static final Character M_MINIMIZE = Character.valueOf((char)0xF030);
    protected static final Character M_MAXIMIZE = Character.valueOf((char)0xF031);
    protected static final Character M_RESTORE  = Character.valueOf((char)0xF032);
    protected static final Character M_CLOSE    = Character.valueOf((char)0xF072);
    
    protected JRootPane rootPane;
    protected MetroRootPaneUI rootPaneUI;
    
    protected JLabel titleLabel;
    protected JButton minButton;
    protected JButton maxButton;
    protected JButton closeButton;
    protected JMenuBar menuBar;
    
    protected Image systemIcon;
    
    protected Action minimizeAction;
    protected Action maximizeAction;
    protected Action restoreAction;
    protected Action closeAction;
    
    protected int state;
    
    @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
    public MetroTitlePane(JRootPane root, MetroRootPaneUI ui) {
        rootPane = root;
        rootPaneUI = ui;
        
        closeAction = new CloseAction();
        minimizeAction = new MinimizeAction();
        maximizeAction = new MaximizeAction();
        restoreAction = new RestoreAction();
        
        titleLabel = new JLabel("Test Title");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Must use unicode mappings for Marlett font
        // See http://www.pclviewer.com/de/resources/pclt/pclt_marlett.html
        minButton = new JButton(M_MINIMIZE.toString());
        maxButton = new JButton(M_MAXIMIZE.toString());
        closeButton = new JButton(M_CLOSE.toString());
        
        minButton.setFont(MetroLookAndFeel.getCBFont());
        maxButton.setFont(MetroLookAndFeel.getCBFont());
        closeButton.setFont(MetroLookAndFeel.getCBFont());
        titleLabel.setFont(MetroLookAndFeel.getBodyFont());
        
        Insets i = new Insets(2, 5, 2, 2);
        minButton.setMargin(i);
        maxButton.setMargin(i);
        Insets ci = new Insets(2, 4, 2, 2);
        closeButton.setMargin(ci);
        Dimension cbButtonSize = new Dimension(CB_BUTTON_WIDTH, CONTENT_HEIGHT);
        minButton.setFocusPainted(false);
        maxButton.setFocusPainted(false);
        closeButton.setFocusPainted(false);
        minButton.setAlignmentY(Component.TOP_ALIGNMENT);
        maxButton.setAlignmentY(Component.TOP_ALIGNMENT);
        closeButton.setAlignmentY(Component.TOP_ALIGNMENT);
        minButton.setPreferredSize(cbButtonSize);
        maxButton.setPreferredSize(cbButtonSize);
        closeButton.setPreferredSize(cbButtonSize);
        
        titleLabel.setAlignmentY(Component.TOP_ALIGNMENT);
        Dimension tld = titleLabel.getSize();
        titleLabel.setSize(tld.width, CONTENT_HEIGHT);
        createMenuBar();
        menuBar.setSize(ICON_WIDTH, ICON_HEIGHT);
        menuBar.setAlignmentY(Component.TOP_ALIGNMENT);
        
        // setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setLayout(new MetroTitlePaneLayout());
        add(menuBar);
        add(Box.createHorizontalGlue());
        add(titleLabel);
        add(Box.createHorizontalGlue());
        prepareButtons();
        
        MetroLookAndFeel.getLogger().log(Level.INFO, "Preferred size: {0}", getLayout().preferredLayoutSize(this));
        MetroLookAndFeel.getLogger().log(Level.INFO, "Minimum layout size: {0}", getLayout().minimumLayoutSize(this));
        MetroLookAndFeel.getLogger().log(Level.INFO, "Current size: {0}", getSize());
        MetroLookAndFeel.getLogger().info("MetroTitlePane created.");
        
        state = -1;
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension pd = super.getPreferredSize();
        return new Dimension(pd.width, PANE_HEIGHT);
    }
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, PANE_HEIGHT);
    }
    
    @Override
    public void setVisible(boolean visible) {
        MetroLookAndFeel.getLogger().log(Level.INFO, "setVisible called ({0})...", visible);
        MetroLookAndFeel.getLogger().info(String.format("%d %d", getWidth(), getHeight()));
        super.setVisible(visible);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Frame f = getFrame();
        if (f != null) {
            setState(f.getExtendedState(), false);
        }
        int width = getWidth();
        int height = getHeight();
        String title = getTitle();
        titleLabel.setText(title == null ? "" : title);
        
        /*
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        */
        
        MetroLookAndFeel.getLogger().info(minButton.getSize().toString());
        MetroLookAndFeel.getLogger().info(String.format("Width: %d, Height: %d", width, height));
    }
    
    public void createMenuBar() {
        menuBar = new SystemMenuBar();
        menuBar.setFocusable(false);
        menuBar.setBorderPainted(true);
        menuBar.add(createMenu());
    }
    
    private JMenu createMenu() {
        JMenu menu = new JMenu("__");
        if (rootPane.getWindowDecorationStyle() == JRootPane.FRAME) {
            addMenuItems(menu);
        }
        return menu;
    }
    
    private void addMenuItems(JMenu menu) {
        // Locale locale = rootPane.getLocale();
        JMenuItem mi = menu.add(restoreAction);
        int mnemonic = KeyEvent.VK_R;

        if (mnemonic != -1) {
            mi.setMnemonic(mnemonic);
        }
        
        mi = menu.add(minimizeAction);
        mnemonic = KeyEvent.VK_M;
        if (mnemonic != -1) {
            mi.setMnemonic(mnemonic);
        }

        if (Toolkit.getDefaultToolkit().isFrameStateSupported(
                Frame.MAXIMIZED_BOTH)) {
            mi = menu.add(maximizeAction);
            mnemonic = KeyEvent.VK_X;
            if (mnemonic != -1) {
                mi.setMnemonic(mnemonic);
            }
        }

        menu.add(new JSeparator());

        mi = menu.add(closeAction);
        mnemonic = KeyEvent.VK_C;
        if (mnemonic != -1) {
            mi.setMnemonic(mnemonic);
        }
    }
    
    public void prepareButtons() {
        int ds = rootPane.getWindowDecorationStyle();
        
        closeButton.setAction(closeAction);
        minButton.setAction(minimizeAction);
        maxButton.setAction(maximizeAction);
        
        closeButton.setText(M_CLOSE.toString());
        minButton.setText(M_MINIMIZE.toString());
        maxButton.setText(M_MAXIMIZE.toString());
        
        if (ds == JRootPane.FRAME) {
            add(minButton);
            add(maxButton);
            add(closeButton);
        }
        else if (ds == JRootPane.PLAIN_DIALOG ||
                ds == JRootPane.INFORMATION_DIALOG ||
                ds == JRootPane.ERROR_DIALOG ||
                ds == JRootPane.COLOR_CHOOSER_DIALOG ||
                ds == JRootPane.FILE_CHOOSER_DIALOG ||
                ds == JRootPane.QUESTION_DIALOG ||
                ds == JRootPane.WARNING_DIALOG) {
            add(closeButton);
        }
    }
    
    public String getTitle() {
        Window w = getWindow();
        
        if (w instanceof Frame) {
            return ((Frame)w).getTitle();
        }
        else if (w instanceof Dialog) {
            return ((Dialog)w).getTitle();
        }
        return null;
    }
    
    public void close() {
        Window w = getWindow();
        if (w == null) {
            return;
        }
        w.dispatchEvent(new WindowEvent(w, WindowEvent.WINDOW_CLOSING));
    }
    
    public void minimize() {
        Frame f = getFrame();
        if (f == null) {
            return;
        }
        f.setExtendedState(state | Frame.ICONIFIED);
    }
    
    public void maximize() {
        Frame f = getFrame();
        if (f == null) {
            return;
        }
        f.setExtendedState(state | Frame.MAXIMIZED_BOTH);
    }
    
    public void restore() {
        Frame f = getFrame();
        if (f == null) {
            return;
        }
        
        if ((state & Frame.ICONIFIED) != 0) {
            f.setExtendedState(state & ~Frame.ICONIFIED);
        }
        else {
            f.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
        }
    }
    
    protected void installListeners() {
        Window w = getWindow();
        if (w == null) {
            return;
        }
        w.addWindowListener(new MetroTitlePaneWindowHandler());
        w.addPropertyChangeListener(this);
    }
    
    protected Window getWindow() {
        return SwingUtilities.getWindowAncestor(this);
    }
    
    protected Frame getFrame() {
        Window w = getWindow();
        return w instanceof Frame ? (Frame)w : null;
    }
    
    protected void updateSystemIcon() {
        Window w = getWindow();
        if (w == null) {
            systemIcon = null;
            return;
        }
        List<Image> icons = w.getIconImages();
        if (icons.isEmpty()) {
            systemIcon = null;
        }
        else if (icons.size() == 1) {
            systemIcon = icons.get(0);
        }
        else {
            systemIcon = SunToolkit.getScaledIconImage(icons, ICON_WIDTH, ICON_HEIGHT);
        }
    }
    
    protected void updateMaxButton(Action action, Character c) {
        maxButton.setAction(action);
        maxButton.setText(c.toString());
    }
    
    protected void setState(int state, boolean updateRegardless) {
        Window w = getWindow();
        
        if (w != null && rootPane.getWindowDecorationStyle() == JRootPane.FRAME) {
            if (this.state == state && !updateRegardless) {
                return;
            }
            Frame f = getFrame();
            if (f == null) {
                maximizeAction.setEnabled(false);
                restoreAction.setEnabled(false);
                if (maxButton.getParent() != null) {
                    remove(maxButton);
                    revalidate();
                    repaint();
                }
                return;
            }
            
            if ((state & Frame.MAXIMIZED_BOTH) != 0 && f.isShowing()) {
                rootPane.setBorder(null);
            }
            else if ((state & Frame.MAXIMIZED_BOTH) == 0) {
                rootPaneUI.installBorder(rootPane);
            }
            
            if (f.isResizable()) {
                if ((state & Frame.MAXIMIZED_BOTH) != 0) {
                    updateMaxButton(restoreAction, M_RESTORE);
                    maximizeAction.setEnabled(false);
                    restoreAction.setEnabled(true);
                }
                else {
                    updateMaxButton(maximizeAction, M_MAXIMIZE);
                    maximizeAction.setEnabled(true);
                    restoreAction.setEnabled(false);
                }
                
                if (maxButton.getParent() == null || minButton.getParent() == null) {
                    add(maxButton);
                    add(minButton);
                    revalidate();
                    repaint();
                }
            }
            else {
                maximizeAction.setEnabled(false);
                restoreAction.setEnabled(false);
                if (maxButton.getParent() != null) {
                    remove(maxButton);
                    revalidate();
                    repaint();
                }
            }
        }
        closeAction.setEnabled(true);
        this.state = state;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String pn = e.getPropertyName();
        
        switch(pn) {
            case "resizable":
            case "state":
                Frame frame = getFrame();
                
                if (frame != null) {
                    setState(frame.getExtendedState(), true);
                }
                if (pn.equals("resiazble")) {
                    rootPane.repaint();
                }
            case "title":
                repaint();
            case "componentOrientation":
                revalidate();
                repaint();
            case "iconImage":
                updateSystemIcon();
                revalidate();
                repaint();
        }
    }
    
    @Override
    public void addNotify() {
        super.addNotify();
        
        Window w = getWindow();
        if (w == null) {
            return;
        }
        
        if (w instanceof Frame) {
            setState(((Frame)w).getExtendedState(), false);
        }
        else {
            setState(0, false);
        }
        
        setActive(w.isActive());
        installListeners();
        updateSystemIcon();
    }
    
    protected void setActive(boolean active) {
        Boolean b = Boolean.valueOf(active);
        
        closeButton.putClientProperty("paintActive", b);
        if (rootPane.getWindowDecorationStyle() == JRootPane.FRAME) {
            minButton.putClientProperty("paintActive", b);
            maxButton.putClientProperty("paintActive", b);
        }
        rootPane.repaint();
    }
    
    private class SystemMenuBar extends JMenuBar {
        public static final int ICON_BORDER_WIDTH = 3;
        
        @Override
        public void paint(Graphics g) {
            if (isOpaque()) {
                g.setColor(getBackground());
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            if (systemIcon != null) {
                g.drawImage(systemIcon, ICON_BORDER_WIDTH, ICON_BORDER_WIDTH, ICON_WIDTH, ICON_HEIGHT, null);
            } else {
                Dimension d = getPreferredSize();
                MetroLookAndFeel.getLogger().info(String.format("Painting internal frame icon. %d, %d", d.width, d.height));
                Icon icon = UIManager.getIcon("InternalFrame.icon");

                if (icon != null) {
                    icon.paintIcon(this, g, ICON_BORDER_WIDTH, ICON_BORDER_WIDTH);
                }
            }
        }
        
        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }
        
        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            
            return new Dimension(Math.max(ICON_WIDTH+ICON_BORDER_WIDTH*2, size.width),
                                 Math.max(size.height, ICON_HEIGHT+ICON_BORDER_WIDTH*2));
        }
    }
    
    public class MetroTitlePaneLayout implements LayoutManager {
        public static final int TITLE_Y_OFFSET = 3;
        
        @Override
        public Dimension preferredLayoutSize(Container c) {
            return new Dimension(PANE_HEIGHT, PANE_HEIGHT);
        }
        
        @Override
        public Dimension minimumLayoutSize(Container c) {
            return preferredLayoutSize(c);
        }
        
        @Override
        public void layoutContainer(Container c) {
            int w = getWidth();
            int lx = 0;
            int lw = w;
            int minOffset = 0;
            if (menuBar != null) {
                menuBar.setBounds(0, 0, CONTENT_HEIGHT, CONTENT_HEIGHT);
                lx = CONTENT_HEIGHT;
            }
            if (closeButton != null) {
                closeButton.setBounds(w-CB_BUTTON_WIDTH, 0, CB_BUTTON_WIDTH, CONTENT_HEIGHT);
                lw -= CB_BUTTON_WIDTH;
            }
            if ((maximizeAction.isEnabled() || restoreAction.isEnabled()) && maxButton != null) {
                maxButton.setBounds(w-(CB_BUTTON_WIDTH*2), 0, CB_BUTTON_WIDTH, CONTENT_HEIGHT);
                maxButton.setVisible(true);
                minOffset += CB_BUTTON_WIDTH;
                lw -= CB_BUTTON_WIDTH;
            }
            else {
                maxButton.setVisible(false);
                maxButton.setBounds(0, 0, 0, 0);
            }
            if (minimizeAction.isEnabled() && minButton != null) {
                minButton.setBounds(w-(CB_BUTTON_WIDTH*2)-minOffset, 0, CB_BUTTON_WIDTH, CONTENT_HEIGHT);
                minButton.setVisible(true);
                lw -= CB_BUTTON_WIDTH;
            }
            else {
                minButton.setVisible(false);
                minButton.setBounds(0, 0, 0, 0);
            }
            titleLabel.setBounds(lx, TITLE_Y_OFFSET, lw, CONTENT_HEIGHT-TITLE_Y_OFFSET);
        }
        
        @Override
        public void addLayoutComponent(String name, Component c) {}
        @Override
        public void removeLayoutComponent(Component c) {}
    }

    public class MetroTitlePaneWindowHandler extends WindowAdapter {
        @Override
        public void windowActivated(WindowEvent e) {
            setActive(true);
        }
        
        @Override
        public void windowDeactivated(WindowEvent e) {
            setActive(false);
        }
    }
    
    protected class CloseAction extends AbstractAction {
        public CloseAction() {
            super("Close");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
        }
    }
    
    protected class MinimizeAction extends AbstractAction {
        public MinimizeAction() {
            super("Minimize");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            minimize();
        }
    }
    
    protected class MaximizeAction extends AbstractAction {
        public MaximizeAction() {
            super("Maximize");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            maximize();
        }
    }
    
    protected class RestoreAction extends AbstractAction {
        public RestoreAction() {
            super("Restore");
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            restore();
        }
    }
}
