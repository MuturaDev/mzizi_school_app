package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by James on 07/07/2018.
 */

@Entity
public class PortalDetailedTransaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("RefNo")
    private String refNo = "N/A";
    @SerializedName("TranAmount")
    private String tranAmount  = "N/A";
    @SerializedName("BalAmount")
    private String balAmount= "N/A";
    @SerializedName("CurrentAmount")
    private String currentAmount= "N/A";
    //private String dataForStudentID= "N/A";


    //So new user of this app, will have this added collumns, but i will have to increase the database version and migrate from 2 to 3 (add the below columns)
    @SerializedName("TranType")
    private String tranType;
    @SerializedName("TransType")
    private String transType;
    @SerializedName("DatePosted")
    private String datePosted;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    @Ignore
    public PortalDetailedTransaction(){

    }


    public PortalDetailedTransaction(String refNo,
                                     String tranAmount,
                                     String balAmount,
                                     String currentAmount,
                                     String tranType,
                                     String transType,
                                     String datePosted){
        this.refNo = refNo;
        this.tranAmount = tranAmount;
        this.balAmount  = balAmount;
        this.currentAmount = currentAmount;
        this.tranType = tranType;
        this.transType = transType;
        this.datePosted = datePosted;
    }

    public String getTranType() {
        return tranType;
    }

    public String getTransType() {
        return transType;
    }

    public String getDatePosted() {
        return datePosted;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getRefNo() {
        return refNo;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public String getBalAmount() {
        return balAmount;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    @Override
    public String toString() {
        return "PortalDetailedTransaction{" +
                "id=" + id +
                ", refNo='" + refNo + '\'' +
                ", tranAmount='" + tranAmount + '\'' +
                ", balAmount='" + balAmount + '\'' +
                ", currentAmount='" + currentAmount + '\'' +
                ", tranType='" + tranType + '\'' +
                ", transType='" + transType + '\'' +
                ", datePosted='" + datePosted + '\'' +
                '}';
    }
}
