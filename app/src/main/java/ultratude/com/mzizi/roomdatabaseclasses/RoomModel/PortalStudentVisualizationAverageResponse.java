package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;



@Entity
public class    PortalStudentVisualizationAverageResponse {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("Period")
    private String Period;
    @SerializedName("AverageScore")
    private String AverageScore;
    @SerializedName("MeanGrade")
    private String MeanGrade;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalStudentVisualizationAverageResponse(){}

    @Ignore
    public PortalStudentVisualizationAverageResponse(String Period, String AverageScore, String MeanGrade){
        this.Period = Period;
        this.AverageScore = AverageScore;
        this.MeanGrade = MeanGrade;
    }

    @NonNull
    public int getId(){
        return id;
    }

    public String getPeriod() {
        return Period;
    }

    public String getAverageScore() {
        return AverageScore;
    }

    public String getMeanGrade() {
        return MeanGrade;
    }

    @Override
    public String toString() {
        return "PortalStudentVisualizationAverageResponse{" +
                "Period='" + Period + '\'' +
                ", AverageScore='" + AverageScore + '\'' +
                ", MeanGrade='" + MeanGrade + '\'' +
                '}';
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public void setAverageScore(String averageScore) {
        AverageScore = averageScore;
    }

    public void setMeanGrade(String meanGrade) {
        MeanGrade = meanGrade;
    }
}
