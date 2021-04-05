package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 24/01/2019.
 */

public class LeaveTypeSpinner {

    public Integer leaveTypeID;
    public String leaveTypeName;

    public LeaveTypeSpinner(Integer leaveTypeID, String leaveTypeName){
        this.leaveTypeID = leaveTypeID;
        this.leaveTypeName = leaveTypeName;
    }

    @Override
    public String toString() {
        return leaveTypeName;
    }

    public Integer getLeaveTypeID() {
        return leaveTypeID;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }
}
