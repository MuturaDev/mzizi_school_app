package ultratude.com.staff.webservice.ResponseModels;

/**
 * Created by James on 11/01/2019.
 */

public class DisciplineCase {



    private String studentID;
    private String offence;
    private String penaulty;
    private String reportedBy;
    private String appCode;
    private String appVersion;


    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public DisciplineCase(String studentID, String offence, String penaulty, String reportedBy, String appCode){
        this.studentID = studentID;
        this.offence = offence;
        this.penaulty  = penaulty;
        this.reportedBy = reportedBy;
        this.appCode = appCode;
    }


    @Override
    public String toString() {
        return "DisciplineCase{" +
                "studentID='" + studentID + '\'' +
                ", offence='" + offence + '\'' +
                ", penaulty='" + penaulty + '\'' +
                ", reportedBy='" + reportedBy + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }

    public String getStudentID() {
        return studentID;
    }

    public String getOffence() {
        return offence;
    }

    public String getPenaulty() {
        return penaulty;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getAppCode() {
        return appCode;
    }
}
