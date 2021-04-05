package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 28/04/2019.
 */

public class StudentDisciplinaryCase {


    @SerializedName("OffenceDescription")
    private String offenceDescription;
    @SerializedName("PenaultyDescription")
    private String penaultyDescription;
    @SerializedName("ReportedBy")
    private String reportedBy;
    @SerializedName("DateCommited")
    private String dateCommited;
    @SerializedName("TermID")
    private String termID;
    @SerializedName("YearID")
    private String yearID;
    @SerializedName("StudentFullName")
    private String studentName;


    public StudentDisciplinaryCase(String offenceDescription,
                                   String penaultyDescription,
                                   String reportedBy,
                                   String dateCommited,
                                   String termID,
                                   String yearID,
                                   String studentName){

        this.offenceDescription = offenceDescription;
        this.penaultyDescription = penaultyDescription;
        this.reportedBy = reportedBy;
        this.dateCommited = dateCommited;
        this.termID = termID;
        this.yearID = yearID;
        this.studentName = studentName;
    }

    public String getOffenceDescription() {
        return offenceDescription;
    }

    public String getPenaultyDescription() {
        return penaultyDescription;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public String getDateCommited() {
        return dateCommited;
    }

    public String getTermID() {
        return termID;
    }

    public String getYearID() {
        return yearID;
    }

    public String getStudentName() {
        return studentName;
    }
}
