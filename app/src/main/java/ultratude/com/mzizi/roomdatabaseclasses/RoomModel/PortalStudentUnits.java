package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class PortalStudentUnits {


    @PrimaryKey
    @SerializedName("UnitID")
    @NonNull
    private Integer UnitID;
    @SerializedName("UnitName")
    private String UnitName;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalStudentUnits(){}



    @Ignore
    public PortalStudentUnits(Integer unitID, String unitName) {
        UnitID = unitID;
        UnitName = unitName;
    }

    public Integer getUnitID() {
        return UnitID;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitID(@NonNull Integer unitID) {
        UnitID = unitID;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    @Override
    public String toString() {
        return getUnitName();
    }
}
