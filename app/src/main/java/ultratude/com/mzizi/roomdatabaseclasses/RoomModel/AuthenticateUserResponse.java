package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by James on 25/07/2018.
 */
@Entity
public class AuthenticateUserResponse {

    @PrimaryKey
    @NonNull
    private String userID;
    private String userType;
    private String appcode;

    private String username;
    private String password;
    private String login_status;

    private String schoolID;
    private String organizationID;


    public AuthenticateUserResponse(){

    }

    @Override
    public String toString() {
        return "AuthenticateUserResponse{" +
                "userID='" + userID + '\'' +
                ", userType='" + userType + '\'' +
                ", appcode='" + appcode + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", organizationID='" + organizationID + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", login_status='" + login_status + '\'' +
                '}';
    }


    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }


    @NonNull
    public String getUserID() {
        return userID;
    }

    public void setUserID(@NonNull String userID) {
        this.userID = userID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }



}
