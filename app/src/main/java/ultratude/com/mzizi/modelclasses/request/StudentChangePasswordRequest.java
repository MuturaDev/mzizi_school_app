package ultratude.com.mzizi.modelclasses.request;

import ultratude.com.mzizi.BuildConfig;

public class StudentChangePasswordRequest {

    private String StudentID;
    private String OrganizationID;
    private String PasswordOverride;
    private String SchoolID;
    private String AppCode;
    private String AppVersion;

    public StudentChangePasswordRequest(String studentID, String organizationID, String passwordOverride, String schoolID, String appCode) {
        StudentID = studentID;
        OrganizationID = organizationID;
        PasswordOverride = passwordOverride;
        SchoolID = schoolID;
        AppCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public String getPasswordOverride() {
        return PasswordOverride;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getAppCode() {
        return AppCode;
    }

    @Override
    public String toString() {
        return "StudentChangePasswordRequest{" +
                "StudentID='" + StudentID + '\'' +
                ", OrganizationID='" + OrganizationID + '\'' +
                ", PasswordOverride='" + PasswordOverride + '\'' +
                ", SchoolID='" + SchoolID + '\'' +
                ", AppCode='" + AppCode + '\'' +
                '}';
    }
}
