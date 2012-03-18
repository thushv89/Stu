/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentserver;

import java.util.Locale;

/**
 *
 * @author Thushan
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerForm sForm=new ServerForm();
        sForm.setVisible(true);
        sForm.setLocationRelativeTo(null);
        sForm.initiate();
    }

}
