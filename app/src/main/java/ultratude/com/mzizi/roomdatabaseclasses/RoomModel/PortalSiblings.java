package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 20/07/2018.
 */
@Entity
public class PortalSiblings {


    //SIBLINGS, /api/PortalSiblings
    @PrimaryKey
    @NonNull
    @SerializedName("StudentID")
    private String studentID = "";
    @SerializedName("RegistrationNumber")
    private String registrationNumber = "";
    @SerializedName("StudentFullName")
    private String studentFullName = "";
    @SerializedName("CourseName")
    private String courseName = "";
    @SerializedName("StudentStatus")
    private String studentStatus = "";
    @SerializedName("Username")
    private String Username;
    @SerializedName("DefaultPassword")
    private String DefaultPassword;
    @SerializedName("SchoolID")
    private String SchoolID;
    @SerializedName("OrganizationID")
    private String OrganizationID;

    private int isMain;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDefaultPassword() {
        return DefaultPassword;
    }

    public void setDefaultPassword(String defaultPassword) {
        DefaultPassword = defaultPassword;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(String schoolID) {
        SchoolID = schoolID;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(String organizationID) {
        OrganizationID = organizationID;
    }

    public PortalSiblings(){
    }

    public int getIsMain() {
        return isMain;
    }

    public void setIsMain(int isMain) {
        this.isMain = isMain;
    }

    @NonNull
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(@NonNull String studentID) {
        this.studentID = studentID;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }


}
