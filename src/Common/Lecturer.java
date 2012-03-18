/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

/**
 *
 * @author Thushan
 */
public class Lecturer extends Person {

    private Qualifications qualification;
    private StudyAreas studyArea;

    /**
     * @return the qualification
     */

    public Lecturer(){
        setPrivilegeMode(PrivilegeMode.Lecturer);
    }

    public Lecturer(String[] nameWithInit,String firstName,int[] dateOfBirth,String address,String telNo,Gender gender,Qualifications quali,StudyAreas area,int imageID){
        super(nameWithInit,firstName,dateOfBirth,address,telNo,gender,imageID);
        setPrivilegeMode(PrivilegeMode.Lecturer);
        qualification=quali;
        studyArea=area;
    }

    public Lecturer(String lastName,String firstName,StudyAreas area){
        super(lastName,firstName);
        this.studyArea=area;
    }

    public Qualifications getQualification() {
        return qualification;
    }

    /**
     * @param qualification the qualification to set
     */
    public void setQualification(Qualifications qualification) {
        this.qualification = qualification;
    }

    /**
     * @return the studyArea
     */
    public StudyAreas getStudyArea() {
        return studyArea;
    }

    /**
     * @param studyArea the studyArea to set
     */
    public void setStudyArea(StudyAreas studyArea) {
        this.studyArea = studyArea;
    }


}
