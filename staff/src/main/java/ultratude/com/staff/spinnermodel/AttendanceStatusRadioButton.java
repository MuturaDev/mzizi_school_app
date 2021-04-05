package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 13/01/2019.
 */

public class AttendanceStatusRadioButton {

    private Integer statusID;
    private String statusName;


    public AttendanceStatusRadioButton(Integer statusID, String statusName){
        this.statusID = statusID;
        this.statusName = statusName;
    }


    @Override
    public String toString() {
        return statusName;
    }

    public Integer getStatusID() {
        return statusID;
    }

    public String getStatusName() {
        return statusName;
    }
}
