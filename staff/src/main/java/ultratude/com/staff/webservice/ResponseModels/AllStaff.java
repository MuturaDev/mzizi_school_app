package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 24/01/2019.
 */

public class AllStaff  {

    @SerializedName("StaffID")
    private String staffID;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("TitleName")
    private String titleName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("RankName")
    private String rankName;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("SchooID")
    private String schoolID;
    @SerializedName("OrganizationID")
    private String organizationID;
    @SerializedName("DepartmentName")
    private String departmentName;
    @SerializedName("SchoolName")
    private String schoolName;
    @SerializedName("BarCode")
    private String barCode;
    @SerializedName("Email")
    private String email;


    public AllStaff(String staffID, String firstName, String lastName, String titleName,
                    String rankName, String phoneNumber, String schoolID,
                    String organizationID, String departmentName, String schoolName,
                    String barCode,
                    String email){
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.titleName = titleName;
        this.rankName = rankName;
        this.phoneNumber = phoneNumber;
        this.schoolID = schoolID;
        this.organizationID = organizationID;
        this.departmentName = departmentName;
        this.schoolName = schoolName;
        this.barCode = barCode;
        this.email = email;

    }

    @Override
    public String toString() {
        return "AllStaff{" +
                "staffID='" + staffID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", titleName='" + titleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rankName='" + rankName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", organizationID='" + organizationID + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", barCode='" + barCode + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getTitleName() {
        return titleName;
    }

    public String getEmail() {
        return email;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRankName() {
        return rankName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getBarCode() {
        return barCode;
    }
}
