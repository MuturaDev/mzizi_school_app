package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity
public class PortalOptionalFeesResponse {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("ClassName")
    private String className;
    @SerializedName("Term")
    private String term;
    @SerializedName("FeeName")
    private String feeName;
    @SerializedName("FeeAmount")
    private String feeAmount;
    @SerializedName("CurrentStatus")
    private String currentStatus;
    @SerializedName("Options")
    private String options;
    @SerializedName("IsTakenUrlText")
    private String isTakenUrlText;
    @SerializedName("FeeExemptions_f")
    private String feeExemptions_f;
    @SerializedName("FeeExemptions_i")
    private String feeExemptions_i;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalOptionalFeesResponse(){}



    @Ignore
    public PortalOptionalFeesResponse(String className, String term, String feeName, String feeAmount, String currentStatus, String options, String isTakenUrlText, String feeExemptions_f, String feeExemptions_i) {
        this.className = className;
        this.term = term;
        this.feeName = feeName;
        this.feeAmount = feeAmount;
        this.currentStatus = currentStatus;
        this.options = options;
        this.isTakenUrlText = isTakenUrlText;
        this.feeExemptions_f = feeExemptions_f;
        this.feeExemptions_i = feeExemptions_i;
    }


    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public String getTerm() {
        return term;
    }

    public String getFeeName() {
        return feeName;
    }

    public String getFeeAmount() {
        return feeAmount;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getOptions() {
        return options;
    }

    public String getIsTakenUrlText() {
        return isTakenUrlText;
    }

    public String getFeeExemptions_f() {
        return feeExemptions_f;
    }

    public String getFeeExemptions_i() {
        return feeExemptions_i;
    }

    @Override
    public String toString() {
        return "PortalOptionalFeesResponse{" +
                "className='" + className + '\'' +
                ", term='" + term + '\'' +
                ", feeName='" + feeName + '\'' +
                ", feeAmount='" + feeAmount + '\'' +
                ", currentStatus='" + currentStatus + '\'' +
                ", options='" + options + '\'' +
                ", isTakenUrlText='" + isTakenUrlText + '\'' +
                ", feeExemptions_f='" + feeExemptions_f + '\'' +
                ", feeExemptions_i='" + feeExemptions_i + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public void setFeeAmount(String feeAmount) {
        this.feeAmount = feeAmount;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setIsTakenUrlText(String isTakenUrlText) {
        this.isTakenUrlText = isTakenUrlText;
    }

    public void setFeeExemptions_f(String feeExemptions_f) {
        this.feeExemptions_f = feeExemptions_f;
    }

    public void setFeeExemptions_i(String feeExemptions_i) {
        this.feeExemptions_i = feeExemptions_i;
    }
}
