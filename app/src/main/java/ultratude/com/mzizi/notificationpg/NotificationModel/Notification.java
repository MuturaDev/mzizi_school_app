package ultratude.com.mzizi.notificationpg.NotificationModel;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by James on 01/09/2018.
 */

public class Notification implements Serializable{

    private List<Notification> notifications = null;

    @SerializedName("MsgID")
    private int msgID = 0;
    @SerializedName("Msg")
    private String msg = "";
    @SerializedName("DateSent")
    private String dateSent = "";//should be changed
    private boolean read;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public Notification(){

    }


    @Override
    public String toString() {
        return "Notification{" +
                "notifications=" + notifications +
                ", msgID=" + msgID +
                ", msg='" + msg + '\'' +
                ", dateSent='" + dateSent + '\'' +
                ", read=" + read +
                '}';
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getMsgID() {
        return msgID;
    }

    public String getMsg() {
        return msg;
    }

    public String getDateSent() {
        return dateSent;
    }


    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }
}
