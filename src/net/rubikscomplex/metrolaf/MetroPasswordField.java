/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import javax.swing.JPasswordField;
import javax.swing.text.Document;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroPasswordField extends JPasswordField implements MetroPromptTextField {
    protected String ep = null;
    
    public MetroPasswordField() {
        super();
        System.err.println("*** UI: " + getUI().toString());
    }
    
    public MetroPasswordField(Document doc, String txt, int column) {
        super(doc, txt, column);
    }
    
    public MetroPasswordField(int columns) {
        super(columns);
    }
    
    public MetroPasswordField(String text) {
        super(text);
    }
    
    public MetroPasswordField(String text, int columns) {
        super(text, columns);
    }
    
    @Override
    public void setEmptyPrompt(String ep) {
        this.ep = ep;
    }
    
    @Override
    public String getEmptyPrompt() {
        return ep;
    }
}
