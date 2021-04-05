package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

public class PortalStudentListForMarkAttendanceResponse {

    @SerializedName("Num")
    public String Num;
    @SerializedName("StudentID")
    public String StudentID;
    @SerializedName("RegistrationNumber")
    public String RegistrationNumber;
    @SerializedName("StudentFullName")
    public String StudentFullName;
    @SerializedName("UnitName")
    public String UnitName;
    @SerializedName("Status")
    public String Status;
    @SerializedName("Remarks")
    public String Remarks;
    @SerializedName("LateBy")
    public String LateBy;

    public PortalStudentListForMarkAttendanceResponse(String num, String studentID, String registrationNumber, String studentFullName, String unitName, String status, String remarks, String lateBy) {
        Num = num;
        StudentID = studentID;
        RegistrationNumber = registrationNumber;
        StudentFullName = studentFullName;
        UnitName = unitName;
        Status = status;
        Remarks = remarks;
        LateBy = lateBy;
    }


    public void setStatus(String status) {
        Status = status;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public void setLateBy(String lateBy) {
        LateBy = lateBy;
    }

    public String getNum() {
        return Num;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public String getStudentFullName() {
        return StudentFullName;
    }

    public String getUnitName() {
        return UnitName;
    }

    public String getStatus() {
        return Status;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getLateBy() {
        return LateBy;
    }
}
