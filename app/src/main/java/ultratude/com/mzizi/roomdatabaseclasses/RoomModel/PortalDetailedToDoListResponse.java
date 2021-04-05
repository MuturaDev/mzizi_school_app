package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class PortalDetailedToDoListResponse implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("Name")
    private String Name;
    @SerializedName("ToDoAge")
    private String ToDoAge;
    @SerializedName("DueDate")
    private String DueDate;
    @SerializedName("DocPath")
    private String DocPath;
    @SerializedName("Body")
    private String Body;
    @SerializedName("UnitName")
    private String UnitName;
    @SerializedName("TeacherMemoID")
    private String TeacherMemoID;
    @SerializedName("IsFeedbackRequired")
    private String IsFeedbackRequired;
    @Ignore
    private boolean tobeHighlited;

    @SerializedName("IsDocPathAvailable")
    private String IsDocPathAvailable;
    @SerializedName("IsVideoLinkAvailable")
    private String IsVideoLinkAvailable;
    @SerializedName("IsDocPathExtraAvailable")
    private String IsDocPathExtraAvailable;
    @SerializedName("IsAudioLinkAvailable")
    private String IsAudioLinkAvailable;
    @SerializedName("DocPathExtra")
    private String DocPathExtra;
    @SerializedName("VideoLink")
    private String VideoLink;
    @SerializedName("AudioLink")
    private String AudioLink;
    @SerializedName("TimeOffSet")
    private String TimeOffSet;
    @SerializedName("PublishDate")
    private String PublishDate;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("EndTime")
    private String EndTime;
    @SerializedName("SubmitStatus")
    private String SubmitStatus;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalDetailedToDoListResponse(){}



    @Ignore
    public PortalDetailedToDoListResponse(int id, String name, String toDoAge, String dueDate, String docPath, String body, String unitName, String teacherMemoID, String isFeedbackRequired, boolean tobeHighlited, String isDocPathAvailable, String isVideoLinkAvailable, String isDocPathExtraAvailable, String isAudioLinkAvailable, String docPathExtra, String videoLink, String audioLink, String timeOffSet, String publishDate, String startTime, String endTime, String SubmitStatus) {
        this.id = id;
        Name = name;
        ToDoAge = toDoAge;
        DueDate = dueDate;
        DocPath = docPath;
        Body = body;
        UnitName = unitName;
        TeacherMemoID = teacherMemoID;
        IsFeedbackRequired = isFeedbackRequired;
        this.tobeHighlited = tobeHighlited;
        IsDocPathAvailable = isDocPathAvailable;
        IsVideoLinkAvailable = isVideoLinkAvailable;
        IsDocPathExtraAvailable = isDocPathExtraAvailable;
        IsAudioLinkAvailable = isAudioLinkAvailable;
        DocPathExtra = docPathExtra;
        VideoLink = videoLink;
        AudioLink = audioLink;
        TimeOffSet = timeOffSet;
        PublishDate = publishDate;
        StartTime = startTime;
        EndTime = endTime;
        SubmitStatus = SubmitStatus;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getToDoAge() {
        return ToDoAge;
    }

    public void setToDoAge(String toDoAge) {
        ToDoAge = toDoAge;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getDocPath() {
        return DocPath;
    }

    public void setDocPath(String docPath) {
        DocPath = docPath;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getUnitName() {
        return UnitName;
    }

    public void setUnitName(String unitName) {
        UnitName = unitName;
    }

    public String getTeacherMemoID() {
        return TeacherMemoID;
    }

    public void setTeacherMemoID(String teacherMemoID) {
        TeacherMemoID = teacherMemoID;
    }

    public String getIsFeedbackRequired() {
        return IsFeedbackRequired;
    }

    public void setIsFeedbackRequired(String isFeedbackRequired) {
        IsFeedbackRequired = isFeedbackRequired;
    }

    public boolean isTobeHighlited() {
        return tobeHighlited;
    }

    public void setTobeHighlited(boolean tobeHighlited) {
        this.tobeHighlited = tobeHighlited;
    }

    public String getIsDocPathAvailable() {
        return IsDocPathAvailable;
    }

    public void setIsDocPathAvailable(String isDocPathAvailable) {
        IsDocPathAvailable = isDocPathAvailable;
    }

    public String getIsVideoLinkAvailable() {
        return IsVideoLinkAvailable;
    }

    public void setIsVideoLinkAvailable(String isVideoLinkAvailable) {
        IsVideoLinkAvailable = isVideoLinkAvailable;
    }

    public String getIsDocPathExtraAvailable() {
        return IsDocPathExtraAvailable;
    }

    public void setIsDocPathExtraAvailable(String isDocPathExtraAvailable) {
        IsDocPathExtraAvailable = isDocPathExtraAvailable;
    }

    public String getIsAudioLinkAvailable() {
        return IsAudioLinkAvailable;
    }

    public void setIsAudioLinkAvailable(String isAudioLinkAvailable) {
        IsAudioLinkAvailable = isAudioLinkAvailable;
    }

    public String getDocPathExtra() {
        return DocPathExtra;
    }

    public void setDocPathExtra(String docPathExtra) {
        DocPathExtra = docPathExtra;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }

    public String getAudioLink() {
        return AudioLink;
    }

    public void setAudioLink(String audioLink) {
        AudioLink = audioLink;
    }

    public String getTimeOffSet() {
        return TimeOffSet;
    }

    public void setTimeOffSet(String timeOffSet) {
        TimeOffSet = timeOffSet;
    }

    public String getPublishDate() {
        return PublishDate;
    }

    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getSubmitStatus() {
        return SubmitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        SubmitStatus = submitStatus;
    }
}
