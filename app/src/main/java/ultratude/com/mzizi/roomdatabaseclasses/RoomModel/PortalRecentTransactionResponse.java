package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;




import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class PortalRecentTransactionResponse {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("RefNo")
    private String RefNo;
    @SerializedName("TranType")
    private String TranType;
    @SerializedName("TranDate")
    private String TranDate;
    @SerializedName("TranAmount")
    private String TranAmount;
    @SerializedName("BalAmount")
    private String BalAmount;
    @SerializedName("TransType")
    private String TransType;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalRecentTransactionResponse(){}



    @Ignore
    public PortalRecentTransactionResponse(String refNo, String tranType, String tranDate, String tranAmount, String balAmount, String transType) {
        RefNo = refNo;
        TranType = tranType;
        TranDate = tranDate;
        TranAmount = tranAmount;
        BalAmount = balAmount;
        TransType = transType;
    }

    public int getId() {
        return id;
    }

    public String getRefNo() {
        return RefNo;
    }

    public String getTranType() {
        return TranType;
    }

    public String getTranDate() {
        return TranDate;
    }

    public String getTranAmount() {
        return TranAmount;
    }

    public String getBalAmount() {
        return BalAmount;
    }

    public String getTransType(){
        return TransType;
    }

    @Override
    public String toString() {
        return "PortalRecentTransactionResponse{" +
                "RefNo='" + RefNo + '\'' +
                ", TranType='" + TranType + '\'' +
                ", TranDate='" + TranDate + '\'' +
                ", TranAmount='" + TranAmount + '\'' +
                ", BalAmount='" + BalAmount + '\'' +
                ", TransType='" + TransType + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRefNo(String refNo) {
        RefNo = refNo;
    }

    public void setTranType(String tranType) {
        TranType = tranType;
    }

    public void setTranDate(String tranDate) {
        TranDate = tranDate;
    }

    public void setTranAmount(String tranAmount) {
        TranAmount = tranAmount;
    }

    public void setBalAmount(String balAmount) {
        BalAmount = balAmount;
    }

    public void setTransType(String transType) {
        TransType = transType;
    }
}
