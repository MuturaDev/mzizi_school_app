package ultratude.com.staff.webservice.ResponseModels;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by James on 07/07/2018.
 */


public class ExamHistory implements Serializable{



    @SerializedName("Subjects")
    private String subjects = "N/A";
    @SerializedName("Score")
    private String score = "N/A";
    @SerializedName("Grade")
    private String grade = "N/A";
    @SerializedName("Remark")
    private String remark = "N/A";
    @SerializedName("TotalMarks")
    private String totalMarks = "N/A";
    @SerializedName("MeanScore")
    private String meanScore = "N/A";
    @SerializedName("MeanGrade")
    private String meanGrade = "N/A";
    @SerializedName("StreamPosition")
    private String streamPosition = "N/A";
    @SerializedName("OverallPosition")
    private String overallPosition = "N/A";
    @SerializedName("Period")
    private String period = "N/A";

    /*"TermName": "Term 1",
        "CurrentYear": "2018"*/
    @SerializedName("TermName")
    private String termName = "N/A";
    @SerializedName("CurrentYear")
    private String currentYear = "N/A";

    //private String dataForStudentID;


    @Override
    public String toString() {
        return "ExamHistory{" +
                "subjects='" + subjects + '\'' +
                ", score='" + score + '\'' +
                ", grade='" + grade + '\'' +
                ", remark='" + remark + '\'' +
                ", totalMarks='" + totalMarks + '\'' +
                ", meanScore='" + meanScore + '\'' +
                ", meanGrade='" + meanGrade + '\'' +
                ", streamPosition='" + streamPosition + '\'' +
                ", overallPosition='" + overallPosition + '\'' +
                ", period='" + period + '\'' +
                ", termName='" + termName + '\'' +
                ", currentYear='" + currentYear + '\'' +
                '}';
    }

    public ExamHistory(){

    }


    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }



    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getMeanScore() {
        return meanScore;
    }

    public void setMeanScore(String meanScore) {
        this.meanScore = meanScore;
    }

    public String getMeanGrade() {
        return meanGrade;
    }

    public void setMeanGrade(String meanGrade) {
        this.meanGrade = meanGrade;
    }

    public String getStreamPosition() {
        return streamPosition;
    }

    public void setStreamPosition(String streamPosition) {
        this.streamPosition = streamPosition;
    }

    public String getOverallPosition() {
        return overallPosition;
    }

    public void setOverallPosition(String overallPosition) {
        this.overallPosition = overallPosition;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
