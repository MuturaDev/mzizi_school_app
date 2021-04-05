package ultratude.com.mzizi.modelclasses.request;

public class StudentChangePassword {

    private String StudentID;
    private String OrganizationID;
    private String PasswordOverride;
    private String SchoolID;
    private String AppCode;

    public StudentChangePassword(String studentID, String organizationID, String passwordOverride, String schoolID, String appCode) {
        StudentID = studentID;
        OrganizationID = organizationID;
        PasswordOverride = passwordOverride;
        SchoolID = schoolID;
        AppCode = appCode;
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
        return "StudentChangePassword{" +
                "StudentID='" + StudentID + '\'' +
                ", OrganizationID='" + OrganizationID + '\'' +
                ", PasswordOverride='" + PasswordOverride + '\'' +
                ", SchoolID='" + SchoolID + '\'' +
                ", AppCode='" + AppCode + '\'' +
                '}';
    }
}
