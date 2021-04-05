package ultratude.com.staff.webservice.RetrofitOkHTTP.Models;

import ultratude.com.staff.BuildConfig;

/**
 * Created by James on 24/10/2018.
 */

public class StaffAllStudentsRequest{
    public String staffID;
    public String appCode;
    private String AppVersion;

    public StaffAllStudentsRequest(){
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
