package ultratude.com.mzizi.modelclasses.request;

import ultratude.com.mzizi.BuildConfig;

public class BookDataRequest {

    private String StudentID;
    private String SchoolID;
    private String OrganizationID;
    private String YearID;
    private String TermID;
    private String AppCode;
    private String AppVersion;

    public BookDataRequest(String studentID, String schoolID, String organizationID, String yearID, String termID, String appCode) {
        StudentID = studentID;
        SchoolID = schoolID;
        OrganizationID = organizationID;
        YearID = yearID;
        TermID = termID;
        AppCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public String getYearID() {
        return YearID;
    }

    public String getTermID() {
        return TermID;
    }

    public String getAppCode() {
        return AppCode;
    }

    @Override
    public String toString() {
        return "BookDataRequest{" +
                "StudentID='" + StudentID + '\'' +
                ", SchoolID='" + SchoolID + '\'' +
                ", OrganizationID='" + OrganizationID + '\'' +
                ", YearID='" + YearID + '\'' +
                ", TermID='" + TermID + '\'' +
                ", AppCode='" + AppCode + '\'' +
                '}';
    }
}
