package ultratude.com.staff.webservice.RetrofitOkHTTP.Models;

import java.io.Serializable;

import ultratude.com.staff.BuildConfig;

/**
 * Created by James on 16/01/2019.
 */

public class StaffRequest  implements Serializable{

    private String staffID;
    private String schoolID;
    private String organizationID;
    private String appCode;
    private String AppVersion;





    public StaffRequest(String staffID, String schoolID, String organizationID, String appCode){
        this.staffID = staffID;
        this.schoolID = schoolID;
        this.organizationID = organizationID;
        this.appCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }


    @Override
    public String toString() {
        return "StaffRequest{" +
                "staffID='" + staffID + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", organizationID='" + organizationID + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }

    public String getStaffID() {
        return staffID;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getAppCode() {
        return appCode;
    }
}
