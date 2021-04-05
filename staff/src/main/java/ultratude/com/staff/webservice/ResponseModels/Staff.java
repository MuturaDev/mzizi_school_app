package ultratude.com.staff.webservice.ResponseModels;

import java.io.Serializable;

/**
 * Created by Admin on 3/21/2018.
 */

public class Staff implements Serializable {

    private String staff_ID;
    private String appcode;
    private String userType;

    //just added
    private String schoolID;
    private String organizationID;

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staff_ID='" + staff_ID + '\'' +
                ", appcode='" + appcode + '\'' +
                ", userType='" + userType + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", organizationID='" + organizationID + '\'' +
                '}';
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }


}
