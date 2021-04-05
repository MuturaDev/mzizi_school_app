package ultratude.com.mzizi.modelclasses;


import ultratude.com.mzizi.BuildConfig;

public class StudentVisualization {

    private String StudentID;
    private String UnitID;
    private String AppCode;
    private String AppVersion;

    public StudentVisualization(String studentID, String unitID, String appCode) {
        StudentID = studentID;
        UnitID = unitID;
        AppCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getUnitID() {
        return UnitID;
    }

    public String getAppCode() {
        return AppCode;
    }

    @Override
    public String toString() {
        return "StudentVisualization{" +
                "StudentID='" + StudentID + '\'' +
                ", UnitID='" + UnitID + '\'' +
                ", AppCode='" + AppCode + '\'' +
                '}';
    }
}
