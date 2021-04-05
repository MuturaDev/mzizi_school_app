package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 12/01/2019.
 */

public class Student {

    @SerializedName("StudentFullName")
    private String studentFullName;
    @SerializedName("StudentID")
    private String studentID;
    @SerializedName("RegistrationNumber")
    private String registrationNumber;
    @SerializedName("ClassStream")
    private String classStream;
    @SerializedName("SchoolName")
    private String schoolName;
    @SerializedName("SchoolID")
    private String schoolID;
    @SerializedName("OrganizationID")
    private String organizationID;
    @SerializedName("CurrentTerm")
    private String currentTerm;
    @SerializedName("CurrentYear")
    private String currentYear;

    public Student(String studentFullName,
                   String studentID,
                   String registrationNumber,
                   String classStream,
                   String schoolName,
                   String schoolID,
                   String organizationID,
                   String currentTerm,
                   String currentYear){

        this.studentFullName = studentFullName;
        this.studentID = studentID;
        this.registrationNumber = registrationNumber;
        this.classStream = classStream;
        this.schoolName = schoolName;
        this.schoolID = schoolID;
        this.organizationID = organizationID;
        this.currentTerm = currentTerm;
        this.currentYear = currentYear;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentFullName='" + studentFullName + '\'' +
                ", studentID='" + studentID + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", classStream='" + classStream + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolID='" + schoolID + '\'' +
                ", organizationID='" + organizationID + '\'' +
                ", currentTerm='" + currentTerm + '\'' +
                ", currentYear='" + currentYear + '\'' +
                '}';
    }


    public String getStudentFullName() {
        return studentFullName;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getClassStream() {
        return classStream;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolID() {
        return schoolID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getCurrentTerm() {
        return currentTerm;
    }

    public String getCurrentYear() {
        return currentYear;
    }
}
