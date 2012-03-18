/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

import java.io.Serializable;

/**
 *
 * @author Thushan
 */
public class UserDB implements Serializable{

    private String userName;
    private String enPass;
    private Functionality functionality;
    private PrivilegeMode mode;

    public UserDB(){}
    public UserDB(String uname,String pass,Functionality func,PrivilegeMode pri){
        userName=uname;
        enPass=pass;
        functionality=func;
        mode=pri;
    }
    /**
     *
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
     * @return the enPass
     */
    public String getEncryptPass() {
        return enPass;
    }

    /**
     * @param enPass the enPass to set
     */
    public void setEncryptPass(String enPass) {
        this.enPass = enPass;
    }

    /**
     * @return the functionality
     */
    public Functionality getFunctionality() {
        return functionality;
    }

    /**
     * @param functionality the functionality to set
     */
    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }

    /**
     * @return the mode
     */
    public PrivilegeMode getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(PrivilegeMode mode) {
        this.mode = mode;
    }
}
