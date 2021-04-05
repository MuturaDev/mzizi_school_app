package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


/**
 * Created by James on 05/06/2019.
 */

@Entity
public class GlobalSettings {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("GlobalSettingValue")
    private String globalSettingsValue;
    @SerializedName("GlobalSettingName")
    private String globalSettingName;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public GlobalSettings() {
    }



    @Override
    public String toString() {
        return "GlobalSettings{" +
                "id=" + id +
                ", globalSettingsValue='" + globalSettingsValue + '\'' +
                ", globalSettingName='" + globalSettingName + '\'' +
                '}';
    }

    public String getGlobalSettingName() {
        return globalSettingName;
    }

    public void setGlobalSettingName(String globalSettingName) {
        this.globalSettingName = globalSettingName;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getGlobalSettingsValue() {
        return globalSettingsValue;
    }

    public void setGlobalSettingsValue(String globalSettingsValue) {
        this.globalSettingsValue = globalSettingsValue;
    }
}
