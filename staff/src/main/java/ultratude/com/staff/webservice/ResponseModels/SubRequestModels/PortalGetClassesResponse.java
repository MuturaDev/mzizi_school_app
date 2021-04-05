package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalGetClassesResponse {

    @SerializedName("ID")
    private Integer ID;
    @SerializedName("CourseName")
    private String CourseName;
    @SerializedName("Position")
    private String Position;
    @SerializedName("SchoolID")
    private String SchoolID;
    @SerializedName("CourseCode")
    private String CourseCode;
    @SerializedName("SchoolName")
    private String SchoolName;


    public PortalGetClassesResponse(Integer ID, String courseName, String position, String schoolID, String courseCode, String schoolName) {
        this.ID = ID;
        CourseName = courseName;
        Position = position;
        SchoolID = schoolID;
        CourseCode = courseCode;
        SchoolName = schoolName;
    }

    public Integer getID() {
        return ID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getPosition() {
        return Position;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    @Override
    public String toString() {
        return this.CourseName;
    }
}
