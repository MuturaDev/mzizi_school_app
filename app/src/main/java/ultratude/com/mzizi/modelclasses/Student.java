package ultratude.com.mzizi.modelclasses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ultratude.com.mzizi.BuildConfig;

/**
 * Created by James on 26/06/2018.
 */

public class Student implements Serializable{

    @SerializedName("studentID")
    private String studentID;
    @SerializedName("appcode")
    private String appcode;
    @SerializedName("organizationID")
    private String organizationID;
    private String AppVersion;

    public Student(){
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public Student(String studentID, String appcode){
        this.studentID = studentID;
        this.appcode = appcode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public String toString() {
        return "StudentMR{" +
                "studentID='" + studentID + '\'' +
                ", appcode='" + appcode + '\'' +
                '}';
    }

    public String getStudentID() {
        return studentID;
    }

    public void setOrganizationID(String organizationID){
        this.organizationID = organizationID;
    }

    public String getOrganizationID(){
        return organizationID;
    }



    public String getAppcode() {
        return appcode;
    }


}
