package ultratude.com.staff.chips;

import java.io.Serializable;
import java.util.ArrayList;

import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;

/**
 * Created by James on 04/05/2019.
 */

public class MarkAttendanceChip implements Serializable{


    private String courseLevelName;
    private String rollCallSession;
    private boolean saved;
    private boolean display;
    private ArrayList<PortalMarkRegisterRequest> studentPortalMarkRegisterRequestList;
    private ArrayList<String> studentRegistrationNumberList;

    public MarkAttendanceChip(){

    }


    public MarkAttendanceChip(String courseLevelName, String rollCallSession, boolean saved, boolean display, ArrayList<PortalMarkRegisterRequest> studentPortalMarkRegisterRequestList,ArrayList<String> studentRegistrationNumberList){
        this.courseLevelName = courseLevelName;
        this.rollCallSession = rollCallSession;
        this.saved = saved;
        this.display = display;
        this.studentRegistrationNumberList = studentRegistrationNumberList;
        this.studentPortalMarkRegisterRequestList = studentPortalMarkRegisterRequestList;
       // this.avatarUri = avatarUri;
    }

    @Override
    public String toString() {
        return "MarkAttendanceChip{" +
                "courseLevelName='" + courseLevelName + '\'' +
                ", rollCallSession='" + rollCallSession + '\'' +
                ", saved=" + saved +
                ", display=" + display +
                ", studentPortalMarkRegisterRequestList=" + studentPortalMarkRegisterRequestList +
                ", studentRegistrationNumberList=" + studentRegistrationNumberList +
                '}';
    }

    public String getRollCallSession() {
        return rollCallSession;
    }

    public void setRollCallSession(String rollCallSession) {
        this.rollCallSession = rollCallSession;
    }

    public ArrayList<PortalMarkRegisterRequest> getStudentPortalMarkRegisterRequestList() {
        return studentPortalMarkRegisterRequestList;
    }

    public ArrayList<String> getStudentRegistrationNumberList() {
        return studentRegistrationNumberList;
    }

    public void setStudentPortalMarkRegisterRequestList(ArrayList<PortalMarkRegisterRequest> studentPortalMarkRegisterRequestList) {
        this.studentPortalMarkRegisterRequestList = studentPortalMarkRegisterRequestList;
    }

    public void setStudentRegistrationNumberList(ArrayList<String> studentRegistrationNumberList) {
        this.studentRegistrationNumberList = studentRegistrationNumberList;
    }

    public boolean isDisplay() {
        return display;
    }

    public boolean isSaved() {
        return saved;
    }

    public String getCourseLevelName() {
        return courseLevelName;
    }


    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }


    public void setCourseLevelName(String courseLevelName) {
        this.courseLevelName = courseLevelName;
    }


    //    public Uri getAvatarUri() {
//        return avatarUri;
//    }
}
