package ultratude.com.mzizi.modelclasses;

import ultratude.com.mzizi.BuildConfig;

/**
 * Created by James on 05/06/2019.
 */

public class  ParentChatRequest {

    private String studentID;
    private String Msg;
    private String staffID;
    private String appCode;
    private String enquiryTypeID;
    private  String AppVersion;


    public ParentChatRequest(String studentID, String msg, String staffID, String appCode,String enquiryTypeID) {
        this.studentID = studentID;
        Msg = msg;
        this.staffID = staffID;
        this.appCode = appCode;
        this.enquiryTypeID = enquiryTypeID;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getStudentID() {
        return studentID;
    }

    public String getMsg() {
        return Msg;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getAppCode() {
        return appCode;
    }

    public String getEnquiryTypeID() {
        return enquiryTypeID;
    }

    @Override
    public String toString() {
        return "ParentChatRequest{" +
                "studentID='" + studentID + '\'' +
                ", Msg='" + Msg + '\'' +
                ", staffID='" + staffID + '\'' +
                ", appCode='" + appCode + '\'' +
                ", enquiryTypeID='" + enquiryTypeID + '\'' +
                ", AppVersion='" + AppVersion + '\'' +
                '}';
    }
}
