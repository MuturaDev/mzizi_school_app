package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;

import com.google.gson.annotations.SerializedName;


/**
 * Created by James on 04/11/2018.
 */


public class AuthenticateResponse {

    @SerializedName("UserType")
    private String userType = "";
    @SerializedName("UserID")
    private String userID = "";

    //justa added
    @SerializedName("SchoolID")
    private String schoolID;
    @SerializedName("OrganizationID")
    private String OrganizationID;


    public AuthenticateResponse(){

    }

    @Override
    public String toString() {
        return "AuthenticateResponse{" +
                "userType='" + userType + '\'' +
                ", userID='" + userID + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", OrganizationID='" + OrganizationID + '\'' +
                '}';
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(String organizationID) {
        OrganizationID = organizationID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
