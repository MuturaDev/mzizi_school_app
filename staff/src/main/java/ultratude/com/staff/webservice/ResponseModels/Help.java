package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 14/05/2019.
 */

public class Help {

    @SerializedName("ID")
    private String helpID;
    @SerializedName("HelpDescription")
    private String helpText;
    @SerializedName("OperationName")
    private String OperationName;

    @Override
    public String toString() {
        return "Help{" +
                "helpID='" + helpID + '\'' +
                ", helpText='" + helpText + '\'' +
                ", OperationName='" + OperationName + '\'' +
                '}';
    }

    public String getOperationName() {
        return OperationName;
    }

    public void setOperationName(String operationName) {
        OperationName = operationName;
    }

    public String getHelpID() {
        return helpID;
    }

    public void setHelpID(String helpID) {
        this.helpID = helpID;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }
}
