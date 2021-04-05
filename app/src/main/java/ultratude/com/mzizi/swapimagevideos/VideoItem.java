package ultratude.com.mzizi.swapimagevideos;

public class VideoItem {

    private int videoID;
    private String videoUrl;

    public VideoItem(int videoID, String videoUrl) {
        this.videoID = videoID;
        this.videoUrl = videoUrl;
    }

    public int getVideoID() {
        return videoID;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
