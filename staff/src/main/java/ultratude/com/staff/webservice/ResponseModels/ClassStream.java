package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 03/05/2019.
 */

public class ClassStream {

    @SerializedName("CourseID")
    private String courseID;
    @SerializedName("CourseCode")
    private  String courseCode;
    @SerializedName("LevelID")
    private String levelID;
    @SerializedName("LevelName")
    private String levelName;



    public ClassStream(String courseID, String courseCode, String levelID, String LevelName){
        this.courseCode = courseCode;
        this.levelName = LevelName;
        this.courseID = courseID;
        this.levelID = levelID;
    }

    @Override
    public String toString() {
        return "ClassStream{" +
                "courseID='" + courseID + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", levelID='" + levelID + '\'' +
                ", levelName='" + levelName + '\'' +
                '}';
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getLevelID() {
        return levelID;
    }
}
