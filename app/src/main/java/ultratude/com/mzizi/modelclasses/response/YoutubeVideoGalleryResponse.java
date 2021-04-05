package ultratude.com.mzizi.modelclasses.response;



import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class YoutubeVideoGalleryResponse {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Body")
    private String Body;
    @SerializedName("VideoLink")
    private String VideoLink;
    @SerializedName("DueDate")
    private String DueDate;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public YoutubeVideoGalleryResponse(){}

    @Ignore
    public YoutubeVideoGalleryResponse(String name, String body, String videoLink, String dueDate) {
        Name = name;
        Body = body;
        VideoLink = videoLink;
        DueDate = dueDate;
    }

    public String getName() {
        return Name;
    }

    public String getBody() {
        return Body;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public String getDueDate() {
        return DueDate;
    }

    @Override
    public String toString() {
        return "YoutubeVideoGalleryResponse{" +
                "Name='" + Name + '\'' +
                ", Body='" + Body + '\'' +
                ", VideoLink='" + VideoLink + '\'' +
                ", DueDate='" + DueDate + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setBody(String body) {
        Body = body;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }
}
