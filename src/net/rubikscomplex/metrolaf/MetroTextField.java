/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.rubikscomplex.metrolaf;

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 *
 * @author Chris Davoren <cdavoren@gmail.com>
 */
public class MetroTextField extends JTextField implements MetroPromptTextField {
    protected String ep = null;
    
    public MetroTextField() {
        super();
    }
    
    public MetroTextField(String text) {
        super(text);
    }
    
    public MetroTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }
    
    public MetroTextField(String text, int columns) {
        super(text, columns);
    }
    
    @Override
    public void setEmptyPrompt(String p) {
        ep = p;
    }
    
    @Override
    public String getEmptyPrompt() {
        return ep;
    }
}
