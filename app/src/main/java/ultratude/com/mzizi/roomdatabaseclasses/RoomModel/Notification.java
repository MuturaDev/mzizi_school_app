package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;

/**
 * Created by James on 05/07/2018.
 */

@Entity(tableName = Constants.TABLE_NAME_NOTIFICATION)
public class Notification  implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("MsgID")
    private int msgID = 0;
    @SerializedName("Msg")
    private String msg = "";
    @SerializedName("DateSent")
    private String dateSent = "";//should be changed

    @Ignore
    public Notification(){

    }
        //you should add the boolean either read or not
    public Notification(int msgID, String msg, String dateSent){
        this.msgID = msgID;
        this.msg = msg;
        this.dateSent = dateSent;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
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


}
