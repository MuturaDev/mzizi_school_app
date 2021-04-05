package ultratude.com.mzizi.modelclasses;

import ultratude.com.mzizi.BuildConfig;

/**
 * Created by James on 21/05/2019.
 */

public class NotificationReadTracking {

    private String studentID;
    private String dateOpened;
    private String appCode;

    private Integer StudID;
    private String AppVersion;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public NotificationReadTracking(String studentID, String dateOpened) {
        this.studentID = studentID;
        this.dateOpened = dateOpened;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public String toString() {
        return "NotificationReadTracking{" +
                "studentID='" + studentID + '\'' +
                ", dateOpened='" + dateOpened + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getDateOpened() {
        return dateOpened;
    }
}
