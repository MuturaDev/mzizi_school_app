package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalTermsResponse {

    @SerializedName("ID")
    private String ID;
    @SerializedName("TermName")
    private String TermName;


    public PortalTermsResponse(String ID, String termName) {
        this.ID = ID;
        TermName = termName;
    }

    public String getID() {
        return ID;
    }

    public String getTermName() {
        return TermName;
    }


    @Override
    public String toString() {
        return this.TermName;
    }
}
