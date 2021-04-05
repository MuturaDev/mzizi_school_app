package ultratude.com.staff.expandablelist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 12/05/2019.
 */

public class ChildInfo {

    @SerializedName("LeaveTypeName")
    private String leaveTypeName = "";
    @SerializedName("DaysEntitled")
    private String daysEntitled = "";
    @SerializedName("LeaveBalance")
    private String leaveBalance = "";

    @Override
    public String toString() {
        return "ChildInfo{" +
                "leaveTypeName='" + leaveTypeName + '\'' +
                ", daysEntitled='" + daysEntitled + '\'' +
                ", leaveBalance='" + leaveBalance + '\'' +
                '}';
    }

    public String getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(String leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public String getDaysEntitled() {
        return daysEntitled;
    }

    public void setDaysEntitled(String daysEntitled) {
        this.daysEntitled = daysEntitled;
    }
}
