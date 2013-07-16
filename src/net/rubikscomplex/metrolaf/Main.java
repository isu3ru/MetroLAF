/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class Main {
    @SuppressWarnings("NonConstantLogger")
    static Logger mlaf = null;
    
    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Metro LAF Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Object[] items = {"Item 1", "Item 2", "Item 3", "Baluga"};
        JLabel label = new JLabel("Label:");
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        JComboBox cb = new JComboBox(items);
        JLabel label2 = new JLabel("Text field: ");
        MetroTextField tf = new MetroTextField();
        tf.setEmptyPrompt("username");
        JLabel label3 = new JLabel("Pass field: ");
        MetroPasswordField pf = new MetroPasswordField();
        pf.setEmptyPrompt("password");
        JButton button = new JButton("Button");
        button.setMnemonic(KeyEvent.VK_B);
        JPanel p = new JPanel();
        JLabel pl = new JLabel("Panel label.");
        p.setPreferredSize(new Dimension(400, 400));
        p.add(pl);
        JScrollPane sp = new JScrollPane(p);
        sp.getHorizontalScrollBar().setUnitIncrement(20);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        sp.setPreferredSize(new Dimension(200, 200));
        frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        frame.getContentPane().add(label);
        frame.getContentPane().add(cb);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(tf);
        frame.getContentPane().add(label3);
        frame.getContentPane().add(pf);
        frame.getContentPane().add(button);
        frame.getContentPane().add(sp);
        cb.setPreferredSize(new Dimension(200, 25));
        tf.setPreferredSize(new Dimension(200, 25));
        pf.setPreferredSize(new Dimension(200, 25));
        
        
        frame.pack();
        frame.setSize(new Dimension(300, 400));
        frame.setLocation(400, 200);
        for (Component c : frame.getRootPane().getLayeredPane().getComponents()) {
            mlaf.log(Level.INFO, "{0}: {1}", new Object[]{c.getClass().getSimpleName(), c.getSize()});
        }
        frame.setVisible(true);
    }
    
    public static class MLAFFormatter extends Formatter {
        protected final DateFormat df = new SimpleDateFormat("HH:mm:ss:SSS");
        
        public MLAFFormatter() {
        }
        
        @Override
        public String format(LogRecord record) {
            Date date = new Date();
            String[] ct = record.getSourceClassName().split("\\.");
            return String.format("[%s %s] %s: %s", df.format(date), record.getLevel().toString(), ct[ct.length-1], formatMessage(record));
        }
    }
    
    public static class MLAFPrintStreamHandler extends Handler {
        PrintStream ps;
        Formatter f;
        
        public MLAFPrintStreamHandler(PrintStream ps, Formatter f) {
            this.ps = ps;
            this.f = f;
        }
        
        @Override
        public void publish(LogRecord record) {
            ps.println(f.format(record));
        }
        
        @Override 
        public void close() {
            ps.close();
        }
        
        @Override
        public void flush() {
            ps.flush();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            mlaf = MetroLookAndFeel.getLogger();
            mlaf.setLevel(Level.INFO);
            mlaf.addHandler(new MLAFPrintStreamHandler(System.err, new MLAFFormatter()));
            UIManager.setLookAndFeel("net.rubikscomplex.metrolaf.MetroLookAndFeel");
            MetroLookAndFeel.setUIFont(MetroLookAndFeel.getBodyFont());
            mlaf.info("Finished initialising look and feel.");
        }
        catch (SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            mlaf.log(Level.INFO, "Error setting look and feel: {0}", e.getMessage());
            System.exit(1);
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
