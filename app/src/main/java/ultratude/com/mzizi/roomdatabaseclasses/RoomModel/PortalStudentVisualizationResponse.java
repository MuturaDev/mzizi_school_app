package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class PortalStudentVisualizationResponse {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("Period")
    private String Period;
    @SerializedName("UnitName")
    private String UnitName;
    @SerializedName("AverageScore")
    private String AverageScore;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalStudentVisualizationResponse(){}

    @Ignore
    public PortalStudentVisualizationResponse(String Period, String UnitName, String AverageScore){
        this.Period = Period;
        this.UnitName = UnitName;
        this.AverageScore = AverageScore;
    }

    public int getId() {
        return id;
    }

    public String getPeriod() {
        return Period;
    }

    public String getUnitName() {
        return UnitName;
    }

    public String getAverageScore() {
        return AverageScore;
    }

    @Override
    public String toString() {
        return "PortalStudentVisualizationResponse{" +
                "\nPeriod = '" + Period + '\'' +
                "\n  UnitName = '" + UnitName + '\'' +
                "\n  AverageScore = '" + AverageScore + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPeriod(String period) {
        Period = period;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public void setAverageScore(String averageScore) {
        AverageScore = averageScore;
    }
}
