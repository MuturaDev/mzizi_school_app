package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 11/01/2019.
 */

public class Schools {

    @SerializedName("SchoolID")
    private String schoolID;
    @SerializedName("SchoolName")
    private String schoolName;


    public Schools(String schoolID, String schoolName){
        this.schoolID = schoolID;
        this.schoolName = schoolName;
    }


    public String getSchoolID() {
        return schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }
}
