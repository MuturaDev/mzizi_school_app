package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 24/01/2019.
 */

public class StaffNameSpinner {

    private Integer staffID;
    private String staffName;

    public StaffNameSpinner(Integer staffID, String staffName){
        this.staffID = staffID;
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return staffName;
    }


    public Integer getStaffID() {
        return staffID;
    }

    public String getStaffName() {
        return staffName;
    }
}
