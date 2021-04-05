package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 24/04/2019.
 */

public class StudentClassAttendance {


    @SerializedName("StudentName")
    private String StudentName;
    @SerializedName("Status")
    private String Status;
    @SerializedName("CourseName")
    private String CourseName;
    @SerializedName("Remarks")
    private String Remarks;
    @SerializedName("DateRecorded")
    private String DateRecorded;
    @SerializedName("TermFor")
    private String TermFor;
    @SerializedName("YearFor")
    private String YearFor;
    @SerializedName("UnitName")
    private String UnitName;
    @SerializedName("RollcallSessionName")
    private String RollcallSessionName;



    public StudentClassAttendance(String StudentName, String Status, String CourseName, String Remarks, String DateRecorded, String TermFor, String YearFor, String UnitName, String rollcallSessionName){

        this.StudentName = StudentName;
        this.Status = Status;
        this.CourseName = CourseName;
        this.Remarks = Remarks;
        this.DateRecorded = DateRecorded;
        this.TermFor = TermFor;
        this.YearFor = YearFor;
        this.UnitName = UnitName;
        this.RollcallSessionName = rollcallSessionName;
    }

    @Override
    public String toString() {
        return "StudentClassAttendance{" +
                "StudentName='" + StudentName + '\'' +
                ", Status='" + Status + '\'' +
                ", CourseName='" + CourseName + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", DateRecorded='" + DateRecorded + '\'' +
                ", TermFor='" + TermFor + '\'' +
                ", YearFor='" + YearFor + '\'' +
                ", UnitName='" + UnitName + '\'' +
                ", RollcallSessionName='" + RollcallSessionName + '\'' +
                '}';
    }

    public String getRollcallSessionName() {
        return RollcallSessionName;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getStatus() {
        return Status;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getDateRecorded() {
        return DateRecorded;
    }

    public String getTermFor() {
        return TermFor;
    }

    public String getYearFor() {
        return YearFor;
    }

    public String getUnitName() {
        return UnitName;
    }
}
