/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Thushan
 */
public class ServerSend {

    private static Socket requestSocket;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;

    public static void initiateConnection() {
        String ipAddr=JOptionPane.showInputDialog("Please enter the IP address of the Server");
        try {
            //Create a socket
            requestSocket = new Socket(ipAddr, 4444);
            //Initiate streams and connect accordingly
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());



        } catch (UnknownHostException ex) {

        } catch (IOException ex) {

        }


    }

    public static Object sendMessage(Object obj) {

        try {
            out.writeObject(obj);
            out.flush();
           // while(in.readObject()== null){
           //System.out.println("null");
            return in.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}
