package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 13/01/2019.
 */

public class AttendanceRollCallSessionSpinner {

    private Integer sessionID;
    private String sessionName;

    public AttendanceRollCallSessionSpinner(Integer sessionID, String sessionName){
        this.sessionID = sessionID;
        this.sessionName = sessionName;

    }




    @Override
    public String toString() {
        return sessionName;
    }


    public Integer getSessionID() {
        return sessionID;
    }

    public String getSessionName() {
        return sessionName;
    }
}
