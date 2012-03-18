/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentserver;


import java.io.*;
import java.net.*;
import javax.swing.JTextArea;
import Common.*;

import java.util.ArrayList;
import javax.swing.JOptionPane;

//import
/**
 *
 * @author Thushan
 */
public class ServerThread extends Thread {

    ObjectOutputStream out;
    ObjectInputStream in;
    private final Socket socket;
    private JTextArea text;
    private Boolean connection;

    public ServerThread(Socket socket,JTextArea text,Boolean connection) {
        this.socket = socket;
        this.text=text;
        this.connection=connection;
        start();
    }

    @Override
    public void run() {
        DatabaseEntry entry;
        Object obj;

        try {
            text.append("Initiated connection: "+socket.getInetAddress().getHostName()+"\n");
            
            out = new ObjectOutputStream(
                    socket.getOutputStream());
            in = new ObjectInputStream(
                    socket.getInputStream());

            do{
                obj=in.readObject();
                if(obj instanceof DatabaseEntry)
                    processCommand((DatabaseEntry)obj);
                else if(obj instanceof UserDB)
                    processCommand((UserDB) obj);
                else if (obj instanceof Functionality){
                    if(((Functionality)obj)==Functionality.CHECK_CONNECTION)
                        sendMessage(connection);
                }
    
            }while(!(obj instanceof Exit));
            sendMessage(true);
            
            close();
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null, "Connection error: "+socket.getInetAddress().getHostName()+"\n");
            close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error while writing/reading");
        } catch (ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Uncompatible type");
        }
    }

    private void close(){
        text.append("Closing connection: "+socket.getInetAddress().getHostName()+"\n");
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error while closing connections");
        }
    }
    private void sendMessage(Object obj){
        try {
            out.writeObject(obj);
            out.flush();
            out.reset();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error while sending the message");
        }
    }

    public void processCommand(DatabaseEntry entry){
        if (entry.getFunctionality() == Functionality.SEARCH_SINGLE || entry.getFunctionality() == Functionality.SEARCH_MULTIPLE)
        {
            sendMessage(processSearch(entry));
        }else if(entry.getFunctionality() == Functionality.SORT){
            sendMessage(DatabaseHandler.sort(entry.getProperties(), entry.getDtable()));

        }else if(entry.getFunctionality()==Functionality.UPDATE){
            DatabaseHandler.update(entry.getPerson());
            sendMessage(true);
            text.append("Database updated\n");
        }else if(entry.getFunctionality()==Functionality.DELETELecturer || entry.getFunctionality()==Functionality.DELETEStudent){
            DatabaseHandler.Delete(entry.getPerson().getUserName(), entry.getDtable());
            sendMessage(true);
            text.append("Entries deleted\n");
        }else if(entry.getFunctionality()==Functionality.CHECK_SIMILAR_USERS){
            sendMessage(DatabaseHandler.similarUserName(entry.getPerson().getUserName()));

        }else if(entry.getFunctionality()==Functionality.NEWPROFILE){
            DatabaseHandler.addNewUserProfile(entry.getPerson());
            text.append("New user profile added\n");
            sendMessage(true);
        }else if(entry.getFunctionality()==Functionality.SEARCH_USER){
            sendMessage(DatabaseHandler.searchSingle(Properties.UserName, getPropertyValue(entry.getPerson(),Properties.UserName), entry.getDtable()).get(0));
        }



    }

    public void processCommand(UserDB user){
            if(user.getFunctionality()==Functionality.LOGIN){
                sendMessage(DatabaseHandler.loginAuthentication(user.getUserName(), user.getEncryptPass()));

                text.append("Login Authentication : "+user.getUserName()+"\n");
            }

            else if(user.getFunctionality()==Functionality.NEW){
                DatabaseHandler.addNewUserLogin(user.getUserName(),user.getEncryptPass(),user.getMode());
                sendMessage(true);
                text.append("New user added\n");
            }else if(user.getFunctionality()==Functionality.RETRIEVE_IMAGE){
                sendMessage(DatabaseHandler.getImage(user.getUserName()));

            }
    }


    public static ArrayList<Person> processSearch(DatabaseEntry entry){
        if(entry.getFunctionality()==Functionality.SEARCH_SINGLE){
            return DatabaseHandler.searchSingle(entry.getProperties(),getPropertyValue(entry.getPerson(), entry.getProperties()) , entry.getDtable());
        }

        else if(entry.getFunctionality()==Functionality.SEARCH_MULTIPLE){
            return DatabaseHandler.searchMultiple(entry.getPerson(),entry.getDtable());
        }

        return null;

    }

    private static String getPropertyValue(Person person,Properties prop){
        String value=null;
            if(prop==Properties.LastName)
               value=person.getNameAndInits()[1];
            else if(prop==Properties.FirstName)
                value=person.getFirstName();
            else if(prop==Properties.IndexNo)
                value=((Student) person).getIndexNo();
            else if(prop==Properties.StudyArea)
                value=((Lecturer) person).getStudyArea().toString();
            else if(prop==Properties.Semester)
                value=((Student) person).getSemester()+"";
            else if(prop==Properties.UserName)
                value=person.getUserName()+"";

        return value;
    }
}
