package ultratude.com.staff.webservice.ResponseModels;

import java.io.Serializable;

import ultratude.com.staff.BuildConfig;

/**
 * Created by James on 02/02/2019.
 */

public class    StudentRequest implements Serializable {


    private String studentID;
    private String appcode;
    private String organizationID;
    private String AppVersion;

    public StudentRequest(String studentID, String appcode, String organizationID){
        this.studentID = studentID;
        this.appcode = appcode;
        this.organizationID = organizationID;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public String toString() {
        return "StudentRequest{" +
                "studentID='" + studentID + '\'' +
                ", appcode='" + appcode + '\'' +
                ", organizationID='" + organizationID + '\'' +
                '}';
    }

    public String getStudentID() {
        return studentID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getAppcode() {
        return appcode;
    }


}
