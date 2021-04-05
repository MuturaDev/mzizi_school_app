package ultratude.com.staff.webservice.ResponseModels.SubRequestModels;

import com.google.gson.annotations.SerializedName;

public class PortalRollCallSessionResponse {

    @SerializedName("SessionID")
    private Integer SessionID;
    @SerializedName("SessionName")
    private String SessionName;

    public PortalRollCallSessionResponse(Integer sessionID, String sessionName) {
        SessionID = sessionID;
        SessionName = sessionName;
    }

    public Integer getSessionID() {
        return SessionID;
    }

    public String getSessionName() {
        return SessionName;
    }

    @Override
    public String toString() {
        return this.SessionName;
    }
}
