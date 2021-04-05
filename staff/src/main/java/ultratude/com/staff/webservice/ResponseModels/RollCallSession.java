package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 23/01/2019.
 */

public class RollCallSession {


    @SerializedName("SessionID")
    private String sessionID;
    @SerializedName("SessionName")
    private String sessionName;

    public RollCallSession(String sessionID, String sessionName){
        this.sessionID = sessionID;
        this.sessionName = sessionName;
    }


    @Override
    public String toString() {
        return "RollCallSession{" +
                "sessionID='" + sessionID + '\'' +
                ", sessionName='" + sessionName + '\'' +
                '}';
    }


    public String getSessionID() {
        return sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }
}
