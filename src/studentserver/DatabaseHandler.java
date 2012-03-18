/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package studentserver;

import Common.*;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


/**
 *
 * @author Thushan
 */
public class DatabaseHandler {

    private static String url = null;
    private static Statement stmt = null;
    private static PreparedStatement psmnt = null;
    private static Connection con = null;

    public static boolean linkToDB() {
        try {
            // TODO code application logic here
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306/users";
            con = DriverManager.getConnection(url, "root", "");
            stmt = con.createStatement();
            return true;

        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
        } catch (SQLException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean initiateDB(){
         try {
            // TODO code application logic here
            Class.forName("com.mysql.jdbc.Driver");
            url = "jdbc:mysql://localhost:3306";
            con = DriverManager.getConnection(url, "root", "");
            String db="CREATE DATABASE users";
            stmt = con.createStatement();
            stmt.executeUpdate(db);
            return true;

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean createTables(){
        String commonFields1="UserName varchar(25) , Initials varchar(25) , LastName varchar(25) , FirstName varchar(25) ";
        String commonFields2=", Address varchar(255) , BirthDay varchar(15) , Gender varchar(6) , TelephoneNumber varchar(15) ";
        String stuTable="CREATE table students ("+commonFields1+", IndexNo varchar(10) "+commonFields2+" , Semester int(2) , ImageID tinyint(6))";
        String lecTable="CREATE table lecturers ("+commonFields1+commonFields2+" , Qualifications varchar(15) , StudyArea varchar(15)"+" , ImageID tinyint(6))";
        String loginTable="CREATE table login_details ( UserName varchar(25) , Password varchar(255) , Privilege varchar(15))";
        String imageTable="CREATE table image_table ( ImageID tinyint(6) , ImageType varchar(25) , Image longblob , ImageSize varchar(25) , ImageName varchar(50))";
        try {
            stmt.executeUpdate(stuTable);
            stmt.executeUpdate(lecTable);
            stmt.executeUpdate(loginTable);
            stmt.executeUpdate(imageTable);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

    public static synchronized boolean usersExist(){
        String users = "SELECT * FROM login_details";
        ResultSet rs;
        try {
            rs=stmt.executeQuery(users);
            if(rs.next())
                return true;
            else
                return false;

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }


    public static synchronized  int uploadImage(File image) {
        ResultSet rs;
        

        int numRows = 0;

        int size = (int) (image.length() / 1024);
        String filesize = size + "KB";

        String fileName = image.getName();

        int mid = fileName.lastIndexOf(".");
        String fileExt = fileName.substring(mid + 1, fileName.length());

        int imageID = 1;

        /*String getRows = "SELECT COUNT(*) FROM image_table";
        try {
            rs = stmt.executeQuery(getRows);
            rs.next();
            numRows = rs.getInt(1);
            imageID = numRows + 1;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        imageID=calculateImgID();



        try {
            psmnt = con.prepareStatement("INSERT INTO image_table VALUES (?,?,?,?,?)");
            psmnt.setInt(1, imageID);
            psmnt.setString(2, fileExt);
            psmnt.setString(4, filesize);
            psmnt.setString(5, fileName);
            FileInputStream fis = new FileInputStream(image);
            psmnt.setBinaryStream(3, (InputStream) fis, (int) (image.length()));

            psmnt.executeUpdate();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return imageID;

    }

    //calculate the ID for the Image
    private static synchronized  int calculateImgID(){
        ResultSet rs;
        int imageID=1;
        String getCol = "SELECT ImageID FROM image_table";
        try {
            rs = stmt.executeQuery(getCol);
            while(rs.next()){
                int dbImgID=rs.getInt("ImageID");
                if(dbImgID==imageID)
                    imageID++;
                else
                    break;

               
            }


        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return imageID;
    }

    public static synchronized  PrivilegeMode loginAuthentication(String auname, String apword) {

        String uname = null;
        String pword = null;
        String privilege = null;

        try {

            String search = "SELECT * FROM login_details WHERE UserName= '" + auname + "' AND Password= '" + apword + "'";
            ResultSet rs = stmt.executeQuery(search);

            while (rs.next()) {
                uname = rs.getString("UserName");
                pword = rs.getString("Password");
                privilege = rs.getString("Privilege");
            }

            if (uname != null && pword != null) {
                if (privilege.equals("Administrator")) {
                    return PrivilegeMode.Administrator;
                } else if (privilege.equals("Student")) {
                    return PrivilegeMode.Student;
                } else if (privilege.equals("Lecturer")) {
                    return PrivilegeMode.Lecturer;
                }
            }


        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        return PrivilegeMode.None;
    }

    public static synchronized  void addNewUserProfile(Person person) {

        String insert = null;      
        int imageID=0;
        if(person.getImgFile()!=null){
            imageID = uploadImage(person.getImgFile());
        }

        if (person.getPrivilegeMode() == PrivilegeMode.Student) {

            Student student = (Student) person;
            insert = "INSERT INTO students VALUES ('" + student.getUserName() + "','" + student.getNameAndInits()[0] + "','" + student.getNameAndInits()[1] + "','" + student.getFirstName() + "','" + student.getIndexNo() + "','" + student.getAddress() + "','" + student.getDOBString() + "','" + student.getGender().toString() + "','" + student.getTelNo() + "','" + student.getSemester() + "','" + imageID + "')";

        } else if (person.getPrivilegeMode() == PrivilegeMode.Lecturer) {
            Lecturer lecturer = (Lecturer) person;
            insert = "INSERT INTO lecturers VALUES ('" + lecturer.getUserName() + "','" + lecturer.getNameAndInits()[0] + "','" + lecturer.getNameAndInits()[1] + "','" + lecturer.getFirstName() + "','" + lecturer.getAddress() + "','" + lecturer.getDOBString() + "','" + lecturer.getGender().toString() + "','" + lecturer.getTelNo() + "','" + lecturer.getQualification().toString() + "','" + lecturer.getStudyArea().toString() + "','" + imageID + "')";
        }

        try {
            stmt.executeUpdate(insert);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static synchronized  void addNewUserLogin(String uname, String enpass, PrivilegeMode mode) {

        String insertUser = null;

        insertUser = "INSERT INTO login_details VALUES ('" + uname + "','" + enpass + "','" + mode.toString() + "')";

        try {
            stmt.executeUpdate(insertUser);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        


    }

    public static synchronized  boolean similarUserName(String uname) {
        ResultSet rs = null;
        try {
            String search = "SELECT * FROM login_details WHERE UserName= '" + uname + "'";
            rs = stmt.executeQuery(search);
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //search using multiple fields
    public static synchronized  ArrayList<Person> searchMultiple(Person person, DataTable dtable) {

        //array
        String searchCom = null;
        String searchStr = null;
        String searchStu = null;
        String searchLec = null;

        String[] search = new String[5];
        for (int i = 0; i < 5; i++) {
            search[i] = null;
        }


        //search results
        ArrayList<Person> searchRs = new ArrayList<Person>();
        ResultSet rs;
        int idx = 0;
        //initialize search specification if the field is not empty get it!
        if (!person.getNameAndInits()[1].isEmpty()) {
            search[idx] = "LastName='" + person.getNameAndInits()[1] + "'";
            idx++;
        }
        if (!person.getFirstName().isEmpty()) {
            search[idx] = "FirstName='" + person.getFirstName() + "'";
            idx++;
        }
        if (!person.getTelNo().isEmpty()) {
            search[idx] = "TelephoneNumber='" + person.getTelNo() + "'";
            idx++;
        }
        if (!person.getAddress().isEmpty()) {
            search[idx] = "Address='" + person.getAddress() + "'";
            idx++;
        }
        if(dtable==DataTable.Students){
            if (!((Student) person).getIndexNo().isEmpty()) {
                search[idx] = "IndexNo='" + ((Student) person).getIndexNo() + "'";
                idx++;
            }
        }

        //create the fields and join them with ANDs
        if (idx == 1) {
            searchCom = search[0];
        } else if (idx > 1) {
            for (int i = 0; i < idx - 1; i = i + 2) {
                searchCom = search[i] + " AND " + search[i + 1];
            }
        }




        //create the search statement
        //check only 1 database
        if (dtable == DataTable.Students || dtable == DataTable.Lecturers) {
            searchStr = "SELECT * FROM " + dtable.toString() + " WHERE " + searchCom;
            try {
                rs = stmt.executeQuery(searchStr);

                    while (rs.next()) {
                        searchRs.add(getPerson(rs, dtable));
                    }

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            //check both databases
        } else if (dtable == DataTable.StudentsAndLectureres) {
            searchStu = "SELECT * FROM students WHERE " + searchCom;
            searchLec = "SELECT * FROM lecturers WHERE " + searchCom;

            try {
                rs = stmt.executeQuery(searchStu);
                while (rs.next()) {
                    searchRs.add(getPerson(rs, DataTable.Students));
                }
                searchRs.add(new Person());

                rs = stmt.executeQuery(searchLec);
                while (rs.next()) {
                    searchRs.add(getPerson(rs, DataTable.Lecturers));
                }

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return searchRs;
    }

    //search using single field
    public static synchronized  ArrayList<Person> searchSingle(Properties searchTag, String value, DataTable dtable) {
        ArrayList<Person> searchRs = new ArrayList<Person>();
        String search = null;
        ResultSet rs;

        search = "SELECT * FROM " + dtable.toString() + " WHERE " + searchTag.toString() + "= '" + value + "'";


        try {
            rs = stmt.executeQuery(search);
            
                while (rs.next()) {
                    searchRs.add(getPerson(rs,dtable));
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return searchRs;

    }

    public static synchronized  ArrayList<Person> sort(Properties sort, DataTable dtable) {
        ArrayList<Person> sortRs = new ArrayList<Person>();
        String sortStr = null;
        ResultSet rs;


        sortStr = "SELECT * FROM " + dtable.toString() + " ORDER BY " + sort.toString();

        try {
            rs = stmt.executeQuery(sortStr);
            while (rs.next()) {
                sortRs.add(getPerson(rs, dtable));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sortRs;

    }

    public static synchronized  void Delete(String userName, DataTable dtable) {
        ResultSet rs;
        String delete = null;
        String deleteLogin = null;
        String getImgID;

        getImgID="SELECT ImageID FROM " +dtable.toString()+ " WHERE UserName= '" +userName+"'" ;
        delete = "DELETE FROM " + dtable.toString() + " WHERE UserName= '" + userName + "'";
        deleteLogin = "DELETE FROM login_details WHERE UserName= '" + userName + "'";
 
        try {
            //get the ID of the image
            rs=stmt.executeQuery(getImgID);
            rs.next();

            stmt.executeUpdate("DELETE FROM image_table WHERE ImageID = "+rs.getInt("ImageID"));
            
            stmt.executeUpdate(delete);
            stmt.executeUpdate(deleteLogin);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update set of people
    public static synchronized  void update(String userName, Properties type, String value, DataTable dtable) {
        String update = null;
        String column = null;

        if (type == Properties.Semester && dtable == DataTable.Students) {
            column = "Semester";
        }

        update = "UPDATE " + dtable.toString() + " SET " + column + "= " + value + " WHERE UserName= '" + userName + "'";
        //deleteLogin = "DELETE FROM login_details WHERE UserName= '"+userName +"'";

        try {
            stmt.executeUpdate(update);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update one person
    public static synchronized  void update(Person person){
        String update = null;
        int imageID = 0;

        if(person.getImgFile()!= null && person.getImageID() == 0)
            imageID = uploadImage(person.getImgFile());

        //image existed before the update
        if(person.getImageID()!=0){
            imageID=person.getImageID();
        }


        if (person.getPrivilegeMode() == PrivilegeMode.Student) {

            Student student = (Student) person;
            update = "UPDATE students SET Initials='" + student.getNameAndInits()[0] + "' , LastName='" + student.getNameAndInits()[1] + "', FirstName='" + student.getFirstName() + "', IndexNo='" + student.getIndexNo() + "', Address='" + student.getAddress() + "', BirthDay='" + student.getDOBString() + "', Gender='" + student.getGender().toString() + "', TelephoneNumber='" + student.getTelNo() + "', Semester=" + student.getSemester() + ", ImageID=" + imageID+" WHERE UserName = '"+person.getUserName()+"'";

        } else if (person.getPrivilegeMode() == PrivilegeMode.Lecturer) {
            Lecturer lecturer = (Lecturer) person;
            update = "UPDATE lecturers SET Initials='" +  lecturer.getNameAndInits()[0]  + "' , LastName='"  + lecturer.getNameAndInits()[1] + "', FirstName='" + lecturer.getFirstName() + "', Address='" + lecturer.getAddress() + "', BirthDay='" + lecturer.getDOBString() + "', Gender='" + lecturer.getGender().toString() + "', TelephoneNumber='" + lecturer.getTelNo() + "', Qualifications='" + lecturer.getQualification().toString() + "', StudyArea='" + lecturer.getStudyArea().toString() + "', ImageID=" + imageID+" WHERE UserName = '"+person.getUserName()+"'";
        }
        try {
            stmt.executeUpdate(update);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static synchronized Person getPerson(ResultSet rs, DataTable dtable) {
        Person person = null;

        //for a student
        if (dtable == DataTable.Students) {
            person = new Student();
            try {
                person.setPrivilegeMode(PrivilegeMode.Student);
                ((Student) person).setIndexNo(rs.getString("IndexNo"));
                ((Student) person).setSemester(rs.getInt("Semester"));
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

         //specifically for a lecturer
        } else if (dtable == DataTable.Lecturers) {
            person = new Lecturer();
            try {

                person.setPrivilegeMode(PrivilegeMode.Lecturer);
                //set qualifications
                if (rs.getString("Qualifications").equals("BSc")) {
                    ((Lecturer) person).setQualification(Qualifications.BSc);
                } else if (rs.getString("Qualifications").equals("MSc")) {
                    ((Lecturer) person).setQualification(Qualifications.MSc);
                } else if (rs.getString("Qualifications").equals("Phd")) {
                    ((Lecturer) person).setQualification(Qualifications.Phd);
                }

                //set study area
                if (rs.getString("StudyArea").equals("PROGRAMMING")) {
                    ((Lecturer) person).setStudyArea(StudyAreas.PROGRAMMING);
                } else if (rs.getString("StudyArea").equals("ALOGRITHMS")) {
                    ((Lecturer) person).setStudyArea(StudyAreas.ALGORITHMS);
                } else if (rs.getString("StudyArea").equals("SECURITY")) {
                    ((Lecturer) person).setStudyArea(StudyAreas.SECURITY);
                } else if (rs.getString("StudyArea").equals("NETWORKING")) {
                    ((Lecturer) person).setStudyArea(StudyAreas.NETWORKING);
                } else if (rs.getString("StudyArea").equals("HARDWARE")) {
                    ((Lecturer) person).setStudyArea(StudyAreas.HARDWARE);
                } else if (rs.getString("StudyArea").equals("OTHER")) {
                    ((Lecturer) person).setStudyArea(StudyAreas.OTHER);
                }

            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        //set common attributes
        try {
            person.setUserName(rs.getString("UserName"));
            person.setFirstName(rs.getString("FirstName"));
            person.setNameWithInit(new String[]{rs.getString("Initials"), rs.getString("LastName")});
            
            person.setDOBFromString(rs.getString("BirthDay"));
            person.setAddress(rs.getString("Address"));
            if (rs.getString("Gender").equals("Male")) {
                person.setGender(Gender.Male);
            } else if (rs.getString("Gender").equals("Femal")) {
                person.setGender(Gender.Female);
            }
            person.setTelNo(rs.getString("TelephoneNumber"));
            person.setImageID(rs.getInt("ImageID"));

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return person;
    }

    public static ImageIcon getImage(String uname) {
        ResultSet rs;
        Image image = null;
        int imageID = 0;

        try {
            
            String getStudent = "SELECT * FROM students WHERE UserName='" + uname + "'";
            rs = stmt.executeQuery(getStudent);
            while (rs.next()) {
                imageID = rs.getInt("ImageID");
            }

            String getImage = "SELECT * FROM image_table WHERE ImageID=" + imageID;

            rs = stmt.executeQuery(getImage);
            while (rs.next()) {
                image = ImageIO.read(rs.getBinaryStream("Image"));
            }



        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

       return new ImageIcon(image);

    }
}
