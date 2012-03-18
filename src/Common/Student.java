/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

/**
 *
 * @author Thushan
 */
public class Student extends Person{

    private String indexNo;
    private int currSem;

    public Student(){
        setPrivilegeMode(PrivilegeMode.Student);
    }
    public Student(String[] nameWithInit,String firstName,String indNo,int[] dateOfBirth,String address,String telNo,Gender gender,int imageID){
        super(nameWithInit,firstName,dateOfBirth,address,telNo,gender,imageID);
        indexNo=indNo;
        setPrivilegeMode(PrivilegeMode.Student);
    }

    public Student(String lastName,String firstName,String indexNo,int semester){
        super(lastName,firstName);
        this.indexNo=indexNo;
        this.currSem=semester;
    }
    /**
     * @return the indexNo
     */
    public String getIndexNo() {
        return indexNo;
    }

    /**
     * @param indexNo the indexNo to set
     */
    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    /**
     * @return the currSem
     */
    public int getSemester() {
        return currSem;
    }

    /**
     * @param currSem the currSem to set
     */
    public void setSemester(int currSem) {
        this.currSem = currSem;
    }
}
