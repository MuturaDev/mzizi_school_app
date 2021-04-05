package ultratude.com.staff.customer_support.models;

/**
 * Created by Prajwal on 31/03/17.
 */

public class UserDetail {
    private String userName;
    private String userPassword;
    private String userSchoolCode;
    private String createdAt;
    private String profileCompleted;




    public UserDetail() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSchoolCode() {
        return userSchoolCode;
    }

    public void setUserSchoolCode(String userSchoolCode) {
        this.userSchoolCode = userSchoolCode;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(String profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
}
