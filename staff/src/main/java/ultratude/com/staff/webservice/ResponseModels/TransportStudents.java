package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by James on 24/10/2018.
 */

public class TransportStudents implements Serializable {

    @SerializedName("StudentFullName")
    private String StudentFullName = "";
    @SerializedName("RegistrationNumber")
    private String registrationNumber= "";
    @SerializedName("ClassStream")
    private String classStream= "";
    @SerializedName("SchoolName")
    private String schoolName= "";
    @SerializedName("BarCode")
    private String barcode= "";

    @SerializedName("RouteName")
    private String routeName = null;
    @SerializedName("Bus")
    private String bus = "";
    @SerializedName("TermName")
    private String termName = "";
    @SerializedName("YearID")
    private String yearID = "";
    @SerializedName("MorningPicked")
    private String morningPicked = "";
    @SerializedName("EveningPicked")
    private String eveningPicked = "";
    @SerializedName("FatherPhone")
    private String fatherPhone;
    @SerializedName("MotherPhone")
    private String motherPhone;

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public TransportStudents(){

    }


    @Override
    public String toString() {
        return "TransportStudents{" +
                "StudentFullName='" + StudentFullName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", classStream='" + classStream + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", barcode='" + barcode + '\'' +
                ", routeName='" + routeName + '\'' +
                ", bus='" + bus + '\'' +
                ", termName='" + termName + '\'' +
                ", yearID='" + yearID + '\'' +
                ", morningPicked='" + morningPicked + '\'' +
                ", eveningPicked='" + eveningPicked + '\'' +
                '}';
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public String getYearID() {
        return yearID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public String getMorningPicked() {
        return morningPicked;
    }

    public void setMorningPicked(String morningPicked) {
        this.morningPicked = morningPicked;
    }

    public String getEveningPicked() {
        return eveningPicked;
    }

    public void setEveningPicked(String eveningPicked) {
        this.eveningPicked = eveningPicked;
    }

    public String getStudentFullName() {
        return StudentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
