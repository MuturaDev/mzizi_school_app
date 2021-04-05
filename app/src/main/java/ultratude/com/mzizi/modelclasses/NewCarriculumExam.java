package ultratude.com.mzizi.modelclasses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 18/05/2019.
 */

public class NewCarriculumExam {

    @SerializedName("StudentFullName")
    private String studentFullName;
    @SerializedName("YearID")
    private String yearID; //not displayed
    @SerializedName("TermName")
    private String termName;//not displayed
    @SerializedName("ExamTypeDesc")
    private String examTypeDesc;//not displayed
    @SerializedName("CourseName")
    private String courseName;
    @SerializedName("LevelName")
    private String levelName;
    @SerializedName("Activity")
    private String activity;//activityName
    @SerializedName("LearningArea")
    private String learningArea;//learningAreaName
    @SerializedName("Remarks")
    private String remarks;
    @SerializedName("ScoreDescription")
    private String scoreDescription;
    @SerializedName("ClassTeacherComment")
    private String classTeacherComment;
    @SerializedName("HeadTeacherComment")
    private String headTeacherComment;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public NewCarriculumExam(String studentFullName, String yearID, String termName, String examTypeDesc, String courseName, String levelName, String activity, String learningArea, String remarks, String scoreDescription, String classTeacherComment, String headTeacherComment) {
        this.studentFullName = studentFullName;
        this.yearID = yearID;
        this.termName = termName;
        this.examTypeDesc = examTypeDesc;
        this.courseName = courseName;
        this.levelName = levelName;
        this.activity = activity;
        this.learningArea = learningArea;
        this.remarks = remarks;
        this.scoreDescription = scoreDescription;
        this.classTeacherComment = classTeacherComment;
        this.headTeacherComment = headTeacherComment;
    }


    @Override
    public String toString() {
        return "NewCarriculumExam{" +
                "studentFullName='" + studentFullName + '\'' +
                ", yearID='" + yearID + '\'' +
                ", termName='" + termName + '\'' +
                ", examTypeDesc='" + examTypeDesc + '\'' +
                ", courseName='" + courseName + '\'' +
                ", levelName='" + levelName + '\'' +
                ", activity='" + activity + '\'' +
                ", learningArea='" + learningArea + '\'' +
                ", remarks='" + remarks + '\'' +
                ", scoreDescription='" + scoreDescription + '\'' +
                ", classTeacherComment='" + classTeacherComment + '\'' +
                ", headTeacherComment='" + headTeacherComment + '\'' +
                '}';
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public String getYearID() {
        return yearID;
    }

    public String getTermName() {
        return termName;
    }

    public String getExamTypeDesc() {
        return examTypeDesc;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getActivity() {
        return activity;
    }

    public String getLearningArea() {
        return learningArea;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getScoreDescription() {
        return scoreDescription;
    }


    public String getClassTeacherComment() {
        return classTeacherComment;
    }

    public String getHeadTeacherComment() {
        return headTeacherComment;
    }
}
