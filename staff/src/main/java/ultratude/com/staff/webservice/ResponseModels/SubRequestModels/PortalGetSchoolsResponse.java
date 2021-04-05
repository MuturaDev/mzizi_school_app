package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalGetSchoolsResponse {


    @SerializedName("ID")
    private Integer ID;
    @SerializedName("SchoolName")
    private String SchoolName;

    public PortalGetSchoolsResponse(Integer ID, String schoolName) {
        this.ID = ID;
        SchoolName = schoolName;
    }

    public Integer getID() {
        return ID;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    @Override
    public String toString() {
        return this.SchoolName;
    }
}
