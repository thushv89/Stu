/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentclient;

import Common.Functionality;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Thushan
 */
public class LoadWorker extends SwingWorker<Object, Object>{

    JDialog dialog;
    JLabel statusLbl;
    public LoadWorker(JDialog dialog,JLabel lbl){
        this.dialog=dialog;
        statusLbl=lbl;
    }

    @Override
    protected Object doInBackground() throws Exception {
        

        ServerSend.initiateConnection();
        FrameHandler.initializeFrames();
        statusLbl.setText("Initializing...");

        if((Boolean)ServerSend.sendMessage(Functionality.CHECK_CONNECTION)){
            statusLbl.setText("Connected to the server");
            FrameHandler.showLoginForm();
        }
        else
            JOptionPane.showMessageDialog(null,"Problem with the connection. Please try again later");
        
        dialog.dispose();
        return null;
        
    }

    //private boolean connectToWeb(){

    //}



}
