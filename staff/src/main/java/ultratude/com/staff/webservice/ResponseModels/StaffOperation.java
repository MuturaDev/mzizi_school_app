package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 02/05/2019.
 */

public class StaffOperation {
    @SerializedName("StaffID")
    private String staffID;
    @SerializedName("OperationName")
    private String operation;

    public StaffOperation(String staffID, String operations){
        this.staffID = staffID;
        this.operation = operations;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getOperations() {
        return operation;
    }
}
