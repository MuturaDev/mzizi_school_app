package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class PortalStudentResultsExtended {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("ExamPeriod")
    private String ExamPeriod;
    @SerializedName("AvgScore")
    private String AvgScore;
    @SerializedName("MeanGrade")
    private String MeanGrade;
    @SerializedName("ExamRemarks")
    private String ExamRemarks;
    @SerializedName("DownloadLink")
    private String DownloadLink;
    @SerializedName("FileURL")
    private String FileUrl;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalStudentResultsExtended(){}

    @Ignore
    public PortalStudentResultsExtended(String examPeriod, String avgScore, String meanGrade, String examRemarks, String downloadLink, String fileUrl) {
        ExamPeriod = examPeriod;
        AvgScore = avgScore;
        MeanGrade = meanGrade;
        ExamRemarks = examRemarks;
        DownloadLink = downloadLink;
        FileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public String getExamPeriod() {
        return ExamPeriod;
    }

    public String getAvgScore() {
        return AvgScore;
    }

    public String getMeanGrade() {
        return MeanGrade;
    }

    public String getExamRemarks() {
        return ExamRemarks;
    }

    public String getDownloadLink() {
        return DownloadLink;
    }

    public String getFileUrl() {
        return FileUrl;
    }


    @Override
    public String toString() {
        return "PortalStudentResultsExtended{" +
                "ExamPeriod='" + ExamPeriod + '\'' +
                ", AvgScore='" + AvgScore + '\'' +
                ", MeanGrade='" + MeanGrade + '\'' +
                ", ExamRemarks='" + ExamRemarks + '\'' +
                ", DownloadLink='" + DownloadLink + '\'' +
                ", FileUrl='" + FileUrl + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExamPeriod(String examPeriod) {
        ExamPeriod = examPeriod;
    }

    public void setAvgScore(String avgScore) {
        AvgScore = avgScore;
    }

    public void setMeanGrade(String meanGrade) {
        MeanGrade = meanGrade;
    }

    public void setExamRemarks(String examRemarks) {
        ExamRemarks = examRemarks;
    }

    public void setDownloadLink(String downloadLink) {
        DownloadLink = downloadLink;
    }

    public void setFileUrl(String fileUrl) {
        FileUrl = fileUrl;
    }
}
