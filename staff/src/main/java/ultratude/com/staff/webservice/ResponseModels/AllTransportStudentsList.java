package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 22/12/2018.
 */

public class AllTransportStudentsList {

    @SerializedName("StudentFullName")
    private String studentFullName;
    @SerializedName("RegistrationNumber")
    private String registrationNumber;
    @SerializedName("ClassStream")
    private String classStream;
    @SerializedName("SchoolName")
    private String schoolName;
    @SerializedName("SchoolID")
    private String schoolID;
    @SerializedName("Barcode")
    private String barCode;
    @SerializedName("OrganizationID")
    private String organizationID;
    @SerializedName("CurrentTerm")
    private String currentTerm;
    @SerializedName("CurrentYear")
    private String currentYear;


    public AllTransportStudentsList(){

    }


    @Override
    public String toString() {
        return "AllTransportStudentsListDao{" +
                "studentFullName='" + studentFullName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", classStream='" + classStream + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", barCode='" + barCode + '\'' +
                ", organizationID='" + organizationID + '\'' +
                ", currentTerm='" + currentTerm + '\'' +
                ", currentYear='" + currentYear + '\'' +
                '}';
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getClassStream() {
        return classStream;
    }

    public void setClassStream(String classStream) {
        this.classStream = classStream;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(String currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }
}
