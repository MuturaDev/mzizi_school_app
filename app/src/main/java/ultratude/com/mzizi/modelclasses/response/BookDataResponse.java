package ultratude.com.mzizi.modelclasses.response;




import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class BookDataResponse {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("StudentID")
    private String StudentID;
    @SerializedName("SchoolID")
    private String SchoolID;
    @SerializedName("OrganizationID")
    private String OrganizationID;
    @SerializedName("YearID")
    private String YearID;
    @SerializedName("TermID")
    private String TermID;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public BookDataResponse(){}


    @Ignore
    public BookDataResponse(String studentID, String schoolID, String organizationID, String yearID, String termID) {
        StudentID = studentID;
        SchoolID = schoolID;
        OrganizationID = organizationID;
        YearID = yearID;
        TermID = termID;

    }

    public String getStudentID() {
        return StudentID;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public void setSchoolID(String schoolID) {
        SchoolID = schoolID;
    }

    public void setOrganizationID(String organizationID) {
        OrganizationID = organizationID;
    }

    public void setYearID(String yearID) {
        YearID = yearID;
    }

    public int getId() {
        return id;
    }

    public void setTermID(String termID) {
        TermID = termID;
    }

    public String getYearID() {
        return YearID;
    }

    public String getTermID() {
        return TermID;
    }


    @Override
    public String toString() {
        return "BookDataResponse{" +
                "StudentID='" + StudentID + '\'' +
                ", SchoolID='" + SchoolID + '\'' +
                ", OrganizationID='" + OrganizationID + '\'' +
                ", YearID='" + YearID + '\'' +
                ", TermID='" + TermID + '\'' +
                '}';
    }
}
