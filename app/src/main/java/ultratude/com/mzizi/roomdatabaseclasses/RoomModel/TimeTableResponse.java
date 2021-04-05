package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class TimeTableResponse {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("ClassRoom")
    private String ClassRoom;
    @SerializedName("Class")
    private String Class_;
    @SerializedName("Subject")
    private String Subject;
    @SerializedName("Teacher")
    private String Teacher;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("EndTime")
    private String EndTime;
    @SerializedName("Day")
    private String Day;
    @SerializedName("UnitColor")
    private String UnitColor;

    private int StudID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudID() {
        return StudID;
    }

    public void setStudID(int studID) {
        StudID = studID;
    }

    @Ignore
    public TimeTableResponse(String classRoom, String class_, String subject, String teacher, String startTime, String endTime, String day, String unitColor) {
        ClassRoom = classRoom;
        Class_ = class_;
        Subject = subject;
        Teacher = teacher;
        StartTime = startTime;
        EndTime = endTime;
        Day = day;
        UnitColor = unitColor;
    }


    public TimeTableResponse() {
    }

    public String getClassRoom() {
        return ClassRoom;
    }

    public void setClassRoom(String classRoom) {
        ClassRoom = classRoom;
    }

    public String getClass_() {
        return Class_;
    }

    public void setClass_(String class_) {
        Class_ = class_;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTeacher() {
        return Teacher;
    }

    public void setTeacher(String teacher) {
        Teacher = teacher;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getUnitColor() {
        return UnitColor;
    }

    public void setUnitColor(String unitColor) {
        UnitColor = unitColor;
    }
}
