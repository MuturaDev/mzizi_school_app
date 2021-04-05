package ultratude.com.staff.webservice.RequestModels;

public class PortalStudentListForMarkAttendanceRequest {

    private String EndpointVersion;
    private String ClassID;
    private String StreamID;
    private String DateRecorded;
    private String RollcallSession;
    private String StudySessionID;
    private String UnitID;
    private String ActivityID;
    private String StudentListID;
    private String ASCPeriodID;

    private String AppCode;
    private String AppVersion;


    public PortalStudentListForMarkAttendanceRequest(String classID, String streamID, String dateRecorded, String rollcallSession, String studySessionID, String unitID, String activityID, String studentListID, String ASCPeriodID, String appCode) {
        ClassID = classID;
        StreamID = streamID;
        DateRecorded = dateRecorded;
        RollcallSession = rollcallSession;
        StudySessionID = studySessionID;
        UnitID = unitID;
        ActivityID = activityID;
        StudentListID = studentListID;
        this.ASCPeriodID = ASCPeriodID;
        AppCode = appCode;
    }

    public void setEndpointVersion(String endpointVersion) {
        EndpointVersion = endpointVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getEndpointVersion() {
        return EndpointVersion;
    }

    public String getClassID() {
        return ClassID;
    }

    public String getStreamID() {
        return StreamID;
    }

    public String getDateRecorded() {
        return DateRecorded;
    }

    public String getRollcallSession() {
        return RollcallSession;
    }

    public String getStudySessionID() {
        return StudySessionID;
    }

    public String getUnitID() {
        return UnitID;
    }

    public String getActivityID() {
        return ActivityID;
    }

    public String getStudentListID() {
        return StudentListID;
    }

    public String getASCPeriodID() {
        return ASCPeriodID;
    }

    public String getAppCode() {
        return AppCode;
    }

    public String getAppVersion() {
        return AppVersion;
    }


    public void setClassID(String classID) {
        ClassID = classID;
    }

    public void setStreamID(String streamID) {
        StreamID = streamID;
    }

    public void setDateRecorded(String dateRecorded) {
        DateRecorded = dateRecorded;
    }

    public void setRollcallSession(String rollcallSession) {
        RollcallSession = rollcallSession;
    }

    public void setStudySessionID(String studySessionID) {
        StudySessionID = studySessionID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public void setActivityID(String activityID) {
        ActivityID = activityID;
    }

    public void setStudentListID(String studentListID) {
        StudentListID = studentListID;
    }

    public void setASCPeriodID(String ASCPeriodID) {
        this.ASCPeriodID = ASCPeriodID;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }
}
