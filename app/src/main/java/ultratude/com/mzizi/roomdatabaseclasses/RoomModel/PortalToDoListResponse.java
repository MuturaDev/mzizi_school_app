package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class PortalToDoListResponse {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("DiaryEntryTypeID")
    private String DiaryEntryTypeID;
    @SerializedName("DiaryEntryType")
    private String DiaryEntryType;
    @SerializedName("DiaryEntriesCount")
    private String DiaryEntriesCount;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalToDoListResponse(){}

    @Ignore
    public PortalToDoListResponse(@NonNull String diaryEntryTypeID, String diaryEntryType, String diaryEntriesCount) {
        DiaryEntryTypeID = diaryEntryTypeID;
        DiaryEntryType = diaryEntryType;
        DiaryEntriesCount = diaryEntriesCount;
    }


    public int getId() {
        return id;
    }

    @NonNull
    public String getDiaryEntryTypeID() {
        return DiaryEntryTypeID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaryEntryType() {
        return DiaryEntryType;
    }

    public void setDiaryEntryTypeID(@NonNull String diaryEntryTypeID) {
        DiaryEntryTypeID = diaryEntryTypeID;
    }

    public void setDiaryEntryType(String diaryEntryType) {
        DiaryEntryType = diaryEntryType;
    }

    public void setDiaryEntriesCount(String diaryEntriesCount) {
        DiaryEntriesCount = diaryEntriesCount;
    }

    public String getDiaryEntriesCount() {
        return DiaryEntriesCount;
    }

    @Override
    public String toString() {
        return "PortalToDoListResponse{" +
                "id=" + id +
                ", DiaryEntryTypeID='" + DiaryEntryTypeID + '\'' +
                ", DiaryEntryType='" + DiaryEntryType + '\'' +
                ", DiaryEntriesCount='" + DiaryEntriesCount + '\'' +
                '}';
    }
}
