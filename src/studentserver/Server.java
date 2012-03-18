/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentserver;

import Common.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import Common.PrivilegeMode;
import Common.Properties;


/**
 *
 * @author Thushan
 */
public class Server {


    public void initiate(){

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

        return value;
    }


    public static ArrayList<Person> processSort(DatabaseEntry entry){
        return DatabaseHandler.sort(entry.getProperties(), entry.getDtable());
    }
    public static PrivilegeMode loginAuth(String uname,String pass){
        return DatabaseHandler.loginAuthentication(uname, pass);
    }

    public static void processDelete(DatabaseEntry entry){
        DatabaseHandler.Delete(entry.getPerson().getUserName(), DataTable.Students);
    }

    public static void processUpdate(DatabaseEntry entry){
        DatabaseHandler.update(entry.getPerson().getUserName(),entry.getProperties(), getPropertyValue(entry.getPerson(),entry.getProperties()), entry.getDtable());
    }

    public static void addNewUser(Person person,String pass){
        DatabaseHandler.addNewUserLogin(person.getUserName(), pass, PrivilegeMode.None);
    }
    
    public static void addNewUserProfile(Person person){
        DatabaseHandler.addNewUserProfile(person);
    }


}
