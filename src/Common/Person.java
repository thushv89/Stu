/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

import java.awt.Image;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Thushan
 */
public class Person implements Serializable{

    private String userName;
    private String[] nameWithInit;
    private String firstName;
    private int age;
    private int[] dateOfBirth;
    private String address;
    private String telNo;
    private Gender gender;
    private int imageID;
    private PrivilegeMode privilegeMode;
    private File imgFile;

    public Person(){
        dateOfBirth=new int[3];
    }
    
    public Person(String[] nameWithInit,String firstName,int[] dateOfBirth,String address,String telNo,Gender gender,int imgID){
        dateOfBirth=new int[3];
        this.nameWithInit=nameWithInit;
        this.firstName=firstName;
        this.dateOfBirth=dateOfBirth;
        this.address=address;
        this.telNo=telNo;
        this.gender=gender;
        this.imageID=imgID;
    }

    public Person(String lastName,String firstName){
        nameWithInit=new String[2];
        nameWithInit[1]=lastName;
        this.firstName=firstName;
    }

    public Person(String uname){
        this.userName=uname;
    }
    /**
     * @return the userName
     */

    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the nameWithInit
     */
    public String[] getNameAndInits() {
        return nameWithInit;
    }
    public String getNameWithInit(){
        String name=nameWithInit[0]+" "+nameWithInit[1];
        return name;
    }

    /**
     * @param nameWithInit the nameWithInit to set
     */
    public void setNameWithInit(String[] nameWithInit) {
        this.nameWithInit = nameWithInit;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the dateOfBirth
     */
    public int[] getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(int[] dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDOBString(){
        String dob=dateOfBirth[0]+"-"+dateOfBirth[1]+"-"+dateOfBirth[2];
        return dob;
    }

    public void setDOBFromString(String dob){
        String[] tokens=dob.split("-");
        for(int i=0;i<3;i++){
            dateOfBirth[i]=Integer.parseInt(tokens[i]);
        }
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return the imgLocation
     */
    public int getImageID() {
        return imageID;
    }

    /**
     * @param imgLocation the imgLocation to set
     */
    public void setImageID(int id) {
        this.imageID = id;
    }


    /**
     * @return the telNo
     */
    public String getTelNo() {
        return telNo;
    }

    /**
     * @param telNo the telNo to set
     */
    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    /**
     * @return the privilegeMode
     */
    public PrivilegeMode getPrivilegeMode() {
        return privilegeMode;
    }

    /**
     * @param privilegeMode the privilegeMode to set
     */
    public void setPrivilegeMode(PrivilegeMode privilegeMode) {
        this.privilegeMode = privilegeMode;
    }

    /**
     * @return the imgFile
     */
    public File getImgFile() {
        return imgFile;
    }

    /**
     * @param imgFile the imgFile to set
     */
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
}
