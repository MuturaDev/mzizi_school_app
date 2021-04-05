package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalGetStreamResponse {

    @SerializedName("ID")
    private Integer ID;
    @SerializedName("LevelName")
    private String LevelName;
    @SerializedName("StartDate")
    private String StartDate;

    public PortalGetStreamResponse(Integer ID, String levelName, String startDate) {
        this.ID = ID;
        LevelName = levelName;
        StartDate = startDate;
    }

    public Integer getID() {
        return ID;
    }

    public String getLevelName() {
        return LevelName;
    }

    public String getStartDate() {
        return StartDate;
    }

    @Override
    public String toString() {
        return this.LevelName;
    }
}
