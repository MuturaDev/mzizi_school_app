package ultratude.com.staff.webservice.RequestModels;

/**
 * Created by James on 11/01/2019.
 */

public class PortalMarkRegisterRequest {


    private String studentID;
    private String studentFullName;
    private String status;
    private String dateRecorded;
    private String rollCallSession;
    private String staffID;
    private String remarks;
    private String appCode;
    private String appVersion;

    private String UnitID;
    private String ChargeTypeID;
    private String lateBy;
    private String LessonPeriodID;
    private String ListID;
    private String EndpointVersion;


    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public String getChargeTypeID() {
        return ChargeTypeID;
    }

    public void setChargeTypeID(String chargeTypeID) {
        ChargeTypeID = chargeTypeID;
    }

    public String getLateBy() {
        return lateBy;
    }

    public void setLateBy(String lateBy) {
        this.lateBy = lateBy;
    }

    public String getLessonPeriodID() {
        return LessonPeriodID;
    }

    public void setLessonPeriodID(String lessonPeriodID) {
        LessonPeriodID = lessonPeriodID;
    }

    public String getListID() {
        return ListID;
    }

    public void setListID(String listID) {
        ListID = listID;
    }

    public String getEndpointVersion() {
        return EndpointVersion;
    }

    public void setEndpointVersion(String endpointVersion) {
        EndpointVersion = endpointVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public PortalMarkRegisterRequest(String studentID,
                                     String studentFullName,
                                     String status,
                                     String dateRecorded,
                                     String rollCallSession,
                                     String staffID,
                                     String remarks,
                                     String appCode) {

        this.studentID = studentID;
        this.studentFullName = studentFullName;
        this.status = status;
        this.dateRecorded = dateRecorded;
        this.rollCallSession  = rollCallSession;
        this.staffID = staffID;
        this.remarks = remarks;
        this.appCode = appCode;
    }




    public String getStudentID() {
        return studentID;
    }


    @Override
    public String toString() {
        return "PortalMarkRegisterRequest{" +
                "studentID='" + studentID + '\'' +
                ", studentFullName='" + studentFullName + '\'' +
                ", status='" + status + '\'' +
                ", dateRecorded='" + dateRecorded + '\'' +
                ", rollCallSession='" + rollCallSession + '\'' +
                ", staffID='" + staffID + '\'' +
                ", remarks='" + remarks + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setDateRecorded(String dateRecorded){
        this.dateRecorded = dateRecorded;
    }

    public void setRollCallSession(String session){
        this.rollCallSession = session;
    }

    public void setRemarks(String remark){
        this.remarks = remark;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public String getStatus() {
        return status;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public String getRollCallSession() {
        return rollCallSession;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getAppCode() {
        return appCode;
    }
}
