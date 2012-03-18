/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Common;

import Common.DataTable;
import Common.Functionality;
import Common.Person;
import java.io.Serializable;

/**
 *
 * @author Thushan
 */
public class DatabaseEntry implements Serializable {

    private Functionality functionality;
    private Person person;
    private Properties properties;
    private DataTable dtable;

    public DatabaseEntry(){

    }
    public DatabaseEntry(Functionality func,Person person,Properties prop,DataTable dtable){
        functionality=func;
        this.person=person;
        properties=prop;
        this.dtable=dtable;
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
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return the properties
     */
    public Properties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * @return the dtable
     */
    public DataTable getDtable() {
        return dtable;
    }

    /**
     * @param dtable the dtable to set
     */
    public void setDtable(DataTable dtable) {
        this.dtable = dtable;
    }

}
