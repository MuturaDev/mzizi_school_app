package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 20/07/2018.
 */
@Entity
public class PortalEvents {


    //EVENTS, /api/PortalEvents
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("EventsTitle")
    private String eventsTitle;
    @SerializedName("Venue")
    private String venue ;
    @SerializedName("StartDate")
    private String startDate;
    @SerializedName("EndDate")
    private String endDate;
    @SerializedName("StartTime")
    private String startTime;
    @SerializedName("EndTime")
    private String endTime;
    @SerializedName("Description")
    private String description;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }


    public PortalEvents(){

    }

    @Ignore
    public PortalEvents(int id, String eventsTitle, String venue, String startDate, String endDate, String startTime, String endTime, String description) {
        this.id = id;
        this.eventsTitle = eventsTitle;
        this.venue = venue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getEventsTitle() {
        return eventsTitle;
    }

    public void setEventsTitle(String eventsTitle) {
        this.eventsTitle = eventsTitle;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
