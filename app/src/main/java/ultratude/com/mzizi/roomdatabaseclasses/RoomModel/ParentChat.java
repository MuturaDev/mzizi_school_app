package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Created by James on 05/06/2019.
 */

@Entity
public class ParentChat {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("Msg")
    private String msg;
    @SerializedName("DatePosted")
    private String datePosted;
    @SerializedName("Actor")
    private String actor;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public ParentChat() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
