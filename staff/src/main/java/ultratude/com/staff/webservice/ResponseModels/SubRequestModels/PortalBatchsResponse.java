package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalBatchsResponse {
    @SerializedName("ID")
    private String ID;
    @SerializedName("Name")
    private String Name;
    @SerializedName("WEF")
    private String WEF;

    public PortalBatchsResponse(String ID, String name, String WEF) {
        this.ID = ID;
        Name = name;
        this.WEF = WEF;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    public String getWEF() {
        return WEF;
    }

    @Override
    public String toString() {
        return this.Name;
    }
}
