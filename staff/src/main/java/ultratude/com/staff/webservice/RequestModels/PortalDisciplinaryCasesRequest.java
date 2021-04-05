package ultratude.com.staff.webservice.RequestModels;

/**
 * Created by James on 28/04/2019.
 */

public class PortalDisciplinaryCasesRequest {

    private String studentID;
    private String yearID;
    private String termID;
    private String organizationID;
    private String appCode;

    public PortalDisciplinaryCasesRequest(String studentID,
                                      String yearID,
                                      String termID,
                                      String organizationID,
                                      String appCode){
        this.studentID = studentID;
        this.yearID = yearID;
        this.termID = termID;
        this.organizationID = organizationID;
        this.appCode = appCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getYearID() {
        return yearID;
    }

    public String getTermID() {
        return termID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getAppCode() {
        return appCode;
    }

}
