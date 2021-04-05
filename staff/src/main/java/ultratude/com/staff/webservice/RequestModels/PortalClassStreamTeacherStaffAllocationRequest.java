package ultratude.com.staff.webservice.RequestModels;

public class PortalClassStreamTeacherStaffAllocationRequest {

    private String OrganizationID;

    private String SchoolID;

    private String StaffSchoolID;

    private String ClassID;

    private String StreamID;

    private String BatchID;

    private String TermID;

    private String YearID;

    private String StaffID;

    private String LoggedOnStaffID;

    private String UnitID;

    private String LessonPeriod;

    private String StudentListID;

    private String DayName;

    private String AppVersion;

    private String AppCode;

    private String EndpointVersion;

    private String RollCallSession;


    public PortalClassStreamTeacherStaffAllocationRequest(String organizationID, String schoolID, String staffSchoolID, String classID, String streamID, String batchID, String termID, String yearID, String staffID, String loggedOnStaffID, String unitID, String lessonPeriod, String studentListID, String dayName, String appVersion, String appCode, String endpointVersion, String rollCallSession) {
        OrganizationID = organizationID;
        SchoolID = schoolID;
        StaffSchoolID = staffSchoolID;
        ClassID = classID;
        StreamID = streamID;
        BatchID = batchID;
        TermID = termID;
        YearID = yearID;
        StaffID = staffID;
        LoggedOnStaffID = loggedOnStaffID;
        UnitID = unitID;
        LessonPeriod = lessonPeriod;
        StudentListID = studentListID;
        DayName = dayName;
        AppVersion = appVersion;
        AppCode = appCode;
        EndpointVersion = endpointVersion;
        RollCallSession = rollCallSession;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public void setOrganizationID(String organizationID) {
        OrganizationID = organizationID;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(String schoolID) {
        SchoolID = schoolID;
    }

    public String getStaffSchoolID() {
        return StaffSchoolID;
    }

    public void setStaffSchoolID(String staffSchoolID) {
        StaffSchoolID = staffSchoolID;
    }

    public String getClassID() {
        return ClassID;
    }

    public void setClassID(String classID) {
        ClassID = classID;
    }

    public String getStreamID() {
        return StreamID;
    }

    public void setStreamID(String streamID) {
        StreamID = streamID;
    }

    public String getBatchID() {
        return BatchID;
    }

    public void setBatchID(String batchID) {
        BatchID = batchID;
    }

    public String getTermID() {
        return TermID;
    }

    public void setTermID(String termID) {
        TermID = termID;
    }

    public String getYearID() {
        return YearID;
    }

    public void setYearID(String yearID) {
        YearID = yearID;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getLoggedOnStaffID() {
        return LoggedOnStaffID;
    }

    public void setLoggedOnStaffID(String loggedOnStaffID) {
        LoggedOnStaffID = loggedOnStaffID;
    }

    public String getUnitID() {
        return UnitID;
    }

    public void setUnitID(String unitID) {
        UnitID = unitID;
    }

    public String getLessonPeriod() {
        return LessonPeriod;
    }

    public void setLessonPeriod(String lessonPeriod) {
        LessonPeriod = lessonPeriod;
    }

    public String getStudentListID() {
        return StudentListID;
    }

    public void setStudentListID(String studentListID) {
        StudentListID = studentListID;
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getAppCode() {
        return AppCode;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public String getEndpointVersion() {
        return EndpointVersion;
    }

    public void setEndpointVersion(String endpointVersion) {
        EndpointVersion = endpointVersion;
    }

    public String getRollCallSession() {
        return RollCallSession;
    }

    public void setRollCallSession(String rollCallSession) {
        RollCallSession = rollCallSession;
    }
}