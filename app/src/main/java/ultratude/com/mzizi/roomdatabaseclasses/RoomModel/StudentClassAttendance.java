package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by James on 24/04/2019.
 */


@Entity
public class StudentClassAttendance implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
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

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }


    public StudentClassAttendance() {
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
    @NonNull
    public int getId() {
        return id;
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


    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setDateRecorded(String dateRecorded) {
        DateRecorded = dateRecorded;
    }

    public void setTermFor(String termFor) {
        TermFor = termFor;
    }

    public void setYearFor(String yearFor) {
        YearFor = yearFor;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public void setRollcallSessionName(String rollcallSessionName) {
        RollcallSessionName = rollcallSessionName;
    }
}
