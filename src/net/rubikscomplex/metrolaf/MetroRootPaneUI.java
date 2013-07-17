/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRootPaneUI;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroRootPaneUI extends BasicRootPaneUI {
    private static final String[] borderKeys = new String[] {
        null, "RootPane.frameBorder", "RootPane.plainDialogBorder",
        "RootPane.informationDialogBorder",
        "RootPane.errorDialogBorder", "RootPane.colorChooserDialogBorder",
        "RootPane.fileChooserDialogBorder", "RootPane.questionDialogBorder",
        "RootPane.warningDialogBorder"
    };

    private static final int[] cursorMapping = new int[]
    { Cursor.NW_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.N_RESIZE_CURSOR,
             Cursor.NE_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
      Cursor.NW_RESIZE_CURSOR, 0, 0, 0, Cursor.NE_RESIZE_CURSOR,
      Cursor.W_RESIZE_CURSOR, 0, 0, 0, Cursor.E_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR, 0, 0, 0, Cursor.SE_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR, Cursor.SW_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR,
             Cursor.SE_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };
    
    protected JComponent titlePane = null;
    protected JRootPane root = null;
    protected Window window = null;
    protected MouseInputListener mouseInputListener = null;
    protected Cursor lastCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    protected LayoutManager savedOldLayout = null;
    protected LayoutManager layoutManager = null;
    
    public static ComponentUI createUI(JComponent c) {
        MetroLookAndFeel.getLogger().info("Creating new MetroRootPaneUI...");
        return new MetroRootPaneUI();
    }
    
    @Override
    public void installUI(JComponent c) {
        MetroLookAndFeel.getLogger().info("installUI called...");
        super.installUI(c);
        root = (JRootPane)c;
        if (!isLookAndFeelDecorated(root)) {
            installClientDecorations(root);
        }
    }
    
    @Override 
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        uninstallClientDecorations(root);
        mouseInputListener = null;
        root = null;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        MetroLookAndFeel.getLogger().log(Level.INFO, "Property changed called: {0}", e.getPropertyName());
        super.propertyChange(e);
        String propertyName = e.getPropertyName();
        if (propertyName == null) {
            return;
        }
        switch (propertyName) {
            case "windowDecorationStyle":
                JRootPane rootPane = (JRootPane)e.getSource();
                uninstallClientDecorations(rootPane);
                if (!isLookAndFeelDecorated(root)) {
                    installClientDecorations(rootPane);
                }
                break;
            case "ancestor":
                uninstallWindowListeners(root);
                if (((JRootPane)e.getSource()).getWindowDecorationStyle() != JRootPane.NONE) {
                    installWindowListeners(root, root.getParent());
                }
                break;
        }
    }
    
    protected boolean isLookAndFeelDecorated(JRootPane root) {
        return (root.getWindowDecorationStyle() == JRootPane.NONE);
    }
    
    protected JComponent createTitlePane(JRootPane root) {
        return new MetroTitlePane(root, this);
    }
    
    protected JRootPane getRootPane() {
        return root;
    }
    
    protected JComponent getTitlePane() {
        return titlePane;
    } 
    
    protected void setTitlePane(JRootPane root, JComponent titlePane) {
        JLayeredPane layeredPane = root.getLayeredPane();
        JComponent oldTitlePane = getTitlePane();
        
        for (Component c : layeredPane.getComponents()) {
            if (!(c instanceof JPanel) && !(c instanceof MetroTitlePane)) {
                layeredPane.remove(c);
            }
        }

        if (oldTitlePane != null) {
            oldTitlePane.setVisible(false);
            layeredPane.remove(oldTitlePane);
        }
        if (titlePane != null) {
            layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
            layeredPane.setComponentZOrder(titlePane, 0);
            titlePane.setVisible(true);
        }
        this.titlePane = titlePane;
    }
    
    protected void installWindowListeners(JRootPane root, Component parent) {
        if (parent instanceof Window) {
            window = (Window)parent;
        }
        else {
            window = SwingUtilities.getWindowAncestor(parent);
        }
        
        if (window == null) {
            return;
        }
        if (window instanceof Dialog) {
            root.setBackground(MetroLookAndFeel.getCurrentMetroTheme().getDefaultBackground());
        }
        if (mouseInputListener == null) {
            mouseInputListener = new MouseInputHandler();
        }
        window.addMouseListener(mouseInputListener);
        window.addMouseMotionListener(mouseInputListener);
    }
    
    protected void uninstallWindowListeners(JRootPane root) {
        if (window != null) {
            window.removeMouseListener(mouseInputListener);
            window.removeMouseMotionListener(mouseInputListener);
        }
    }
    
    protected void installClientDecorations(JRootPane root) {
        MetroLookAndFeel.getLogger().info("installClientDecorations called...");
        installBorder(root);

        JComponent title = createTitlePane(root);
        setTitlePane(root, title);
        installWindowListeners(root, root.getParent());
        installLayout(root);
        if (window != null) {
            root.revalidate();
            root.repaint();
        }
    }
    
    protected void uninstallClientDecorations(JRootPane root) {
        uninstallBorder(root);
        uninstallWindowListeners(root);
        setTitlePane(root, null);
        uninstallLayout(root);
        if (!isLookAndFeelDecorated(root)) {
            root.revalidate();
            root.repaint();
        }
        if (window != null) {
            window.setCursor(Cursor.getDefaultCursor());
        }
        window = null;
    }
    
    protected void installLayout(JRootPane root) {
        if (layoutManager == null) {
            layoutManager = new MetroRootPaneLayout();
        }
        savedOldLayout = root.getLayout();
        root.setLayout(layoutManager);
    }
    
    protected void uninstallLayout(JRootPane root) {
        if (savedOldLayout != null) {
            root.setLayout(savedOldLayout);
            savedOldLayout = null;
        }
    }
    
    protected void installBorder(JRootPane root) {
        int ds = root.getWindowDecorationStyle();
        
        if (ds == JRootPane.NONE) {
            LookAndFeel.uninstallBorder(root);
        }
        else {
            LookAndFeel.installBorder(root, borderKeys[ds]);
        }
    }
    
    protected void uninstallBorder(JRootPane root) {
        LookAndFeel.uninstallBorder(root);
    }
    
    protected enum Size {
        PREFERRED,
        MINIMUM,
        MAXIMUM;
    }
    
    public class MetroRootPaneLayout implements LayoutManager2 {
        
        protected Dimension getCPDimensions(JRootPane root, Size s) {
            if (root.getContentPane() != null) {
                switch(s) {
                    case PREFERRED:
                        return root.getContentPane().getPreferredSize();
                    case MINIMUM:
                        return root.getContentPane().getMinimumSize();
                    case MAXIMUM:
                        return root.getContentPane().getMaximumSize();
                }
            }
            return root.getSize();
        }
        
        protected Dimension getTPDimensions(JRootPane root, JComponent titlePane, Size s) {
            if (!isLookAndFeelDecorated(root) && titlePane != null) {
                switch(s) {
                    case PREFERRED:
                        return titlePane.getPreferredSize();
                    case MINIMUM:
                        return titlePane.getMinimumSize();
                    case MAXIMUM:
                        return titlePane.getMaximumSize();
                }
            }
            return new Dimension(0, 0);
        }
        
        protected int getInsetWidth(JRootPane root) {
            return root.getInsets().left + root.getInsets().right;
        }
        
        protected int getInsetHeight(JRootPane root) {
            return root.getInsets().top + root.getInsets().bottom;
        }
        
        protected int max3(int a, int b, int c) {
            return Math.max(a, Math.max(b, c));
        }
        
        protected Dimension calcDimensions(JRootPane root, Size s) {
            Dimension cpd = getCPDimensions(root, s);
            Dimension tpd = getTPDimensions(root, ((MetroRootPaneUI)root.getUI()).getTitlePane(), s);
            return new Dimension(
                    Math.max(cpd.width, tpd.width) + getInsetWidth(root),
                    cpd.height + tpd.height + getInsetHeight(root));
        }
        
        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return calcDimensions((JRootPane)parent, Size.PREFERRED);
        }
        
        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return calcDimensions((JRootPane)parent, Size.MINIMUM);
        }
        
        @Override
        public Dimension maximumLayoutSize(Container parent) {
            return calcDimensions((JRootPane)parent, Size.MAXIMUM);
        }
        
        @Override
        public void layoutContainer(Container parent) {
            JRootPane root = (JRootPane)parent;
            Insets i = root.getInsets();
            int width = root.getWidth() - getInsetWidth(root);
            int height = root.getHeight() - getInsetHeight(root);
            int nextY = 0;
            
            if ((window instanceof Frame && ((Frame)window).getExtendedState() != Frame.MAXIMIZED_BOTH) || (window instanceof Dialog)) {
                nextY += 3;
            }
            
            if (root.getLayeredPane() != null) {
                root.getLayeredPane().setBounds(i.left, i.top, width, height);
            }
            if (root.getGlassPane() != null) {
                root.getGlassPane().setBounds(i.left, i.top, width, height);
            }
            if (!isLookAndFeelDecorated(root)) {
                JComponent titlePane = ((MetroRootPaneUI)root.getUI()).getTitlePane();
                if (titlePane != null) {
                    titlePane.setBounds(0, nextY, width, MetroTitlePane.PANE_HEIGHT);
                    nextY += MetroTitlePane.PANE_HEIGHT;
                }
            }
            if (root.getContentPane() != null) {
                // root.getContentPane().setFont(MetroLookAndFeel.getBodyFont());
                root.getContentPane().setBounds(0, nextY, width, height < nextY ? 0 : height - nextY);
            }
        }
        
        @Override
        public void addLayoutComponent(String name, Component c) {}
        @Override
        public void removeLayoutComponent(Component c) {}
        @Override
        public void addLayoutComponent(Component c, Object constratins) {}
        @Override
        public float getLayoutAlignmentX(Container target) { return 0.0f; }
        @Override
        public float getLayoutAlignmentY(Container target) { return 0.0f; }
        @Override
        public void invalidateLayout(Container target) {}
    }
    
    /**
     * The amount of space (in pixels) that the cursor is changed on.
     */
    private static final int CORNER_DRAG_WIDTH = 16;

    /**
     * Region from edges that dragging is active from.
     */
    private static final int BORDER_DRAG_THICKNESS = 5;

    private class MouseInputHandler implements MouseInputListener {
        /**
         * Set to true if the drag operation is moving the window.
         */
        private boolean isMovingWindow;

        /**
         * Used to determine the corner the resize is occurring from.
         */
        private int dragCursor;

        /**
         * X location the mouse went down on for a drag operation.
         */
        private int dragOffsetX;

        /**
         * Y location the mouse went down on for a drag operation.
         */
        private int dragOffsetY;

        /**
         * Width of the window when the drag started.
         */
        private int dragWidth;

        /**
         * Height of the window when the drag started.
         */
        private int dragHeight;

        @Override
        @SuppressWarnings({"null", "ConstantConditions"})
        public void mousePressed(MouseEvent ev) {
            JRootPane rootPane = getRootPane();

            if (isLookAndFeelDecorated(rootPane)) {
                return;
            }
            Point dragWindowOffset = ev.getPoint();
            Window w = (Window)ev.getSource();
            if (w != null) {
                w.toFront();
            }
            Point convertedDragWindowOffset = SwingUtilities.convertPoint(
                           w, dragWindowOffset, getTitlePane());

            Frame f = null;
            Dialog d = null;

            if (w instanceof Frame) {
                f = (Frame)w;
            } else if (w instanceof Dialog) {
                d = (Dialog)w;
            }

            int frameState = (f != null) ? f.getExtendedState() : 0;
            
            if (getTitlePane() != null &&
                        getTitlePane().contains(convertedDragWindowOffset)) {
                if ((f != null && ((frameState & Frame.MAXIMIZED_BOTH) == 0)
                        || (d != null))
                        && dragWindowOffset.y >= BORDER_DRAG_THICKNESS
                        && dragWindowOffset.x >= BORDER_DRAG_THICKNESS
                        && dragWindowOffset.x < w.getWidth()
                            - BORDER_DRAG_THICKNESS) {
                    isMovingWindow = true;
                    dragOffsetX = dragWindowOffset.x;
                    dragOffsetY = dragWindowOffset.y;
                }
            }
            else if (f != null && f.isResizable()
                    && ((frameState & Frame.MAXIMIZED_BOTH) == 0)
                    || (d != null && d.isResizable())) {
                dragOffsetX = dragWindowOffset.x;
                dragOffsetY = dragWindowOffset.y;
                dragWidth = w.getWidth();
                dragHeight = w.getHeight();
                dragCursor = getCursor(calculateCorner(
                             w, dragWindowOffset.x, dragWindowOffset.y));
            }
        }

        @Override
        public void mouseReleased(MouseEvent ev) {
            if (dragCursor != 0 && window != null && !window.isValid()) {
                // Some Window systems validate as you resize, others won't,
                // thus the check for validity before repainting.
                window.validate();
                getRootPane().repaint();
            }
            isMovingWindow = false;
            dragCursor = 0;
        }

        @Override
        public void mouseMoved(MouseEvent ev) {
            JRootPane root = getRootPane();

            if (root.getWindowDecorationStyle() == JRootPane.NONE) {
                return;
            }

            Window w = (Window)ev.getSource();

            Frame f = null;
            Dialog d = null;

            if (w instanceof Frame) {
                f = (Frame)w;
            } else if (w instanceof Dialog) {
                d = (Dialog)w;
            }

            // Update the cursor
            int cursor = getCursor(calculateCorner(w, ev.getX(), ev.getY()));

            if (cursor != 0 && ((f != null && (f.isResizable() &&
                    (f.getExtendedState() & Frame.MAXIMIZED_BOTH) == 0))
                    || (d != null && d.isResizable()))) {
                w.setCursor(Cursor.getPredefinedCursor(cursor));
            }
            else {
                w.setCursor(Cursor.getDefaultCursor());
            }
        }

        private void adjust(Rectangle bounds, Dimension min, int deltaX,
                            int deltaY, int deltaWidth, int deltaHeight) {
            bounds.x += deltaX;
            bounds.y += deltaY;
            bounds.width += deltaWidth;
            bounds.height += deltaHeight;
            if (min != null) {
                if (bounds.width < min.width) {
                    int correction = min.width - bounds.width;
                    if (deltaX != 0) {
                        bounds.x -= correction;
                    }
                    bounds.width = min.width;
                }
                if (bounds.height < min.height) {
                    int correction = min.height - bounds.height;
                    if (deltaY != 0) {
                        bounds.y -= correction;
                    }
                    bounds.height = min.height;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent ev) {
            Window w = (Window)ev.getSource();
            Point pt = ev.getPoint();

            if (isMovingWindow) {
                Point eventLocationOnScreen = ev.getLocationOnScreen();
                w.setLocation(eventLocationOnScreen.x - dragOffsetX,
                              eventLocationOnScreen.y - dragOffsetY);
            }
            else if (dragCursor != 0) {
                Rectangle r = w.getBounds();
                Rectangle startBounds = new Rectangle(r);
                Dimension min = w.getMinimumSize();

                switch (dragCursor) {
                case Cursor.E_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, pt.x + (dragWidth - dragOffsetX) -
                           r.width, 0);
                    break;
                case Cursor.S_RESIZE_CURSOR:
                    adjust(r, min, 0, 0, 0, pt.y + (dragHeight - dragOffsetY) -
                           r.height);
                    break;
                case Cursor.N_RESIZE_CURSOR:
                    adjust(r, min, 0, pt.y -dragOffsetY, 0,
                           -(pt.y - dragOffsetY));
                    break;
                case Cursor.W_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, 0,
                           -(pt.x - dragOffsetX), 0);
                    break;
                case Cursor.NE_RESIZE_CURSOR:
                    adjust(r, min, 0, pt.y - dragOffsetY,
                           pt.x + (dragWidth - dragOffsetX) - r.width,
                           -(pt.y - dragOffsetY));
                    break;
                case Cursor.SE_RESIZE_CURSOR:
                    adjust(r, min, 0, 0,
                           pt.x + (dragWidth - dragOffsetX) - r.width,
                           pt.y + (dragHeight - dragOffsetY) -
                           r.height);
                    break;
                case Cursor.NW_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX,
                           pt.y - dragOffsetY,
                           -(pt.x - dragOffsetX),
                           -(pt.y - dragOffsetY));
                    break;
                case Cursor.SW_RESIZE_CURSOR:
                    adjust(r, min, pt.x - dragOffsetX, 0,
                           -(pt.x - dragOffsetX),
                           pt.y + (dragHeight - dragOffsetY) - r.height);
                    break;
                default:
                    break;
                }
                if (!r.equals(startBounds)) {
                    w.setBounds(r);
                    // Defer repaint/validate on mouseReleased unless dynamic
                    // layout is active.
                    if (Toolkit.getDefaultToolkit().isDynamicLayoutActive()) {
                        w.validate();
                        getRootPane().repaint();
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent ev) {
            Window w = (Window)ev.getSource();
            lastCursor = w.getCursor();
            mouseMoved(ev);
        }

        @Override
        public void mouseExited(MouseEvent ev) {
            Window w = (Window)ev.getSource();
            w.setCursor(lastCursor);
        }

        @Override
        public void mouseClicked(MouseEvent ev) {
            Window w = (Window)ev.getSource();
            Frame f;

            if (w instanceof Frame) {
                f = (Frame)w;
            } else {
                return;
            }

            Point convertedPoint = SwingUtilities.convertPoint(
                           w, ev.getPoint(), getTitlePane());

            int state = f.getExtendedState();
            if (getTitlePane() != null &&
                    getTitlePane().contains(convertedPoint)) {
                if ((ev.getClickCount() % 2) == 0 &&
                        ((ev.getModifiers() & InputEvent.BUTTON1_MASK) != 0)) {
                    if (f.isResizable()) {
                        if ((state & Frame.MAXIMIZED_BOTH) != 0) {
                            f.setExtendedState(state & ~Frame.MAXIMIZED_BOTH);
                        }
                        else {
                            f.setExtendedState(state | Frame.MAXIMIZED_BOTH);
                        }
                    }
                }
            }
        }

        /**
         * Returns the corner that contains the point <code>x</code>,
         * <code>y</code>, or -1 if the position doesn't match a corner.
         */
        private int calculateCorner(Window w, int x, int y) {
            Insets insets = w.getInsets();
            int xPosition = calculatePosition(x - insets.left,
                    w.getWidth() - insets.left - insets.right);
            int yPosition = calculatePosition(y - insets.top,
                    w.getHeight() - insets.top - insets.bottom);

            if (xPosition == -1 || yPosition == -1) {
                return -1;
            }
            return yPosition * 5 + xPosition;
        }

        /**
         * Returns the Cursor to render for the specified corner. This returns
         * 0 if the corner doesn't map to a valid Cursor
         */
        private int getCursor(int corner) {
            if (corner == -1) {
                return 0;
            }
            return cursorMapping[corner];
        }

        /**
         * Returns an integer indicating the position of <code>spot</code>
         * in <code>width</code>. The return value will be:
         * 0 if < BORDER_DRAG_THICKNESS
         * 1 if < CORNER_DRAG_WIDTH
         * 2 if >= CORNER_DRAG_WIDTH && < width - BORDER_DRAG_THICKNESS
         * 3 if >= width - CORNER_DRAG_WIDTH
         * 4 if >= width - BORDER_DRAG_THICKNESS
         * 5 otherwise
         */
        private int calculatePosition(int spot, int width) {
            if (spot < BORDER_DRAG_THICKNESS) {
                return 0;
            }
            if (spot < CORNER_DRAG_WIDTH) {
                return 1;
            }
            if (spot >= (width - BORDER_DRAG_THICKNESS)) {
                return 4;
            }
            if (spot >= (width - CORNER_DRAG_WIDTH)) {
                return 3;
            }
            return 2;
        }
    }
}
