package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalGetUnitsResponse {

    @SerializedName("ID")
    private Integer ID;
    @SerializedName("Name")
    private String Name;

    public PortalGetUnitsResponse(Integer ID, String name) {
        this.ID = ID;
        Name = name;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return this.Name;
    }

}
