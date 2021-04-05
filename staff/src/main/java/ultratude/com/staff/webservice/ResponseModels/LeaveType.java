package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 24/01/2019.
 */

public class LeaveType {

    @SerializedName("LeaveTypeID")
    private String leaveTypeID;
    @SerializedName("LeaveTypeName")
    private String leavetTypeName;

    public LeaveType(String leaveTypeID, String leavetTypeName){
        this.leaveTypeID = leaveTypeID;
        this.leavetTypeName = leavetTypeName;
    }

    @Override
    public String toString() {
        return "LeaveType{" +
                "classStreamID='" + leaveTypeID + '\'' +
                ", leavetTypeName='" + leavetTypeName + '\'' +
                '}';
    }


    public String getLeaveTypeID() {
        return leaveTypeID;
    }

    public String getLeavetTypeName() {
        return leavetTypeName;
    }
}
