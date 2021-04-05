package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;

/**
 * Created by James on 25/06/2018.
 */

@Entity(tableName = Constants.TABLE_NAME_PORTALSTUDENTINFO)
public class PortalStudentInfo implements Serializable{


    @SerializedName("StudentFullName")
    private String studentFullName = "";
    @PrimaryKey
    @NonNull
    @SerializedName("RegistrationNumber")
    private String registrationNumber = " ";
    @SerializedName("Email")
    private String email = "";
    @SerializedName("DOB")
    private String DOB = "";
    @SerializedName("DateOfAdmission")
    private String dateOfAdmission = " ";
    @SerializedName("TwoNames")
    private String twoNames = "";
    @SerializedName("Balance")
    private String balance = "";
    @SerializedName("MeanGrade")
    private String meanGrade = "";
    @SerializedName("MeanScore")
    private String meanScore = "";
    @SerializedName("Attendance")
    private String attendance = "";
    @SerializedName("Events")
    private String events = "";
    @SerializedName("PhotoUrl")
    private String photoUrl = "";
    @SerializedName("CopyrightYear")
    private String copyRightYear = "";
    @SerializedName("ClassStream")
    private String classStream = "";
    @SerializedName("SchoolName")
    private String schoolName = "";
    @SerializedName("SchoolMotto")
    private String schoolMotto = "";
    @SerializedName("BillingBalance")
    private String billingBalance = "";
    @SerializedName("PortalAccount")
    private String portalAccount = "";
    //private String dataForStudentID;

    @SerializedName("ShowGraphSection")
    private String showGraphSection;
    @SerializedName("ContinuousAssessments")
    private String continuousAssessments;
    @SerializedName("ShowPaymentNoteSection")
    private String showPaymentNoteSection;
    @SerializedName("Assingments")
    private String assignments;
    @SerializedName("PaymentNote")
    private String paymentNote;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }





    public PortalStudentInfo(){

    }


    @Override
    public String toString() {
        return "PortalStudentInfo{" +
                "studentFullName='" + studentFullName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", email='" + email + '\'' +
                ", DOB='" + DOB + '\'' +
                ", dateOfAdmission='" + dateOfAdmission + '\'' +
                ", twoNames='" + twoNames + '\'' +
                ", balance='" + balance + '\'' +
                ", meanGrade='" + meanGrade + '\'' +
                ", meanScore='" + meanScore + '\'' +
                ", attendance='" + attendance + '\'' +
                ", events='" + events + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", copyRightYear='" + copyRightYear + '\'' +
                ", classStream='" + classStream + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", schoolMotto='" + schoolMotto + '\'' +
                ", billingBalance='" + billingBalance + '\'' +

                ", portalAccount='" + portalAccount + '\'' +
                ", showGraphSection='" + showGraphSection + '\'' +
                ", continuousAssessments='" + continuousAssessments + '\'' +
                ", showPaymentNoteSection='" + showPaymentNoteSection + '\'' +
                ", assignments='" + assignments + '\'' +
                ", paymentNote='" + paymentNote + '\'' +
                '}';
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public void setRegistrationNumber(@NonNull String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setDateOfAdmission(String dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public void setTwoNames(String twoNames) {
        this.twoNames = twoNames;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setMeanGrade(String meanGrade) {
        this.meanGrade = meanGrade;
    }

    public void setMeanScore(String meanScore) {
        this.meanScore = meanScore;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setCopyRightYear(String copyRightYear) {
        this.copyRightYear = copyRightYear;
    }

    public void setClassStream(String classStream) {
        this.classStream = classStream;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setSchoolMotto(String schoolMotto) {
        this.schoolMotto = schoolMotto;
    }

    public void setBillingBalance(String billingBalance) {
        this.billingBalance = billingBalance;
    }

    public void setPortalAccount(String portalAccount) {
        this.portalAccount = portalAccount;
    }
//getters

    public String getStudentFullName() {
        return studentFullName;
    }

    @NonNull
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getDOB() {
        return DOB;
    }

    public String getDateOfAdmission() {
        return dateOfAdmission;
    }

    public String getTwoNames() {
        return twoNames;
    }

    public String getBalance() {
        return balance;
    }

    public String getMeanGrade() {
        return meanGrade;
    }

    public String getMeanScore() {
        return meanScore;
    }

    public String getAttendance() {
        return attendance;
    }

    public String getEvents() {
        return events;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getCopyRightYear() {
        return copyRightYear;
    }

    public String getClassStream() {
        return classStream;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getSchoolMotto() {
        return schoolMotto;
    }

    public String getBillingBalance() {
        return billingBalance;
    }

    public String getPortalAccount() {
        return portalAccount;
    }


    public String getShowGraphSection() {
        return showGraphSection;
    }

    public void setShowGraphSection(String showGraphSection) {
        this.showGraphSection = showGraphSection;
    }

    public String getContinuousAssessments() {
        return continuousAssessments;
    }

    public void setContinuousAssessments(String continuousAssessments) {
        this.continuousAssessments = continuousAssessments;
    }

    public String getShowPaymentNoteSection() {
        return showPaymentNoteSection;
    }

    public void setShowPaymentNoteSection(String showPaymentNoteSection) {
        this.showPaymentNoteSection = showPaymentNoteSection;
    }

    public String getAssignments() {
        return assignments;
    }

    public void setAssignments(String assignments) {
        this.assignments = assignments;
    }

    public String getPaymentNote() {
        return paymentNote;
    }

    public void setPaymentNote(String paymentNote) {
        this.paymentNote = paymentNote;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof PortalStudentInfo)) return false;

        PortalStudentInfo student = (PortalStudentInfo) obj;

        if(registrationNumber != student.registrationNumber) return false;

        return studentFullName != null ? studentFullName.equals(student.studentFullName) : student.studentFullName == null;
    }


}
