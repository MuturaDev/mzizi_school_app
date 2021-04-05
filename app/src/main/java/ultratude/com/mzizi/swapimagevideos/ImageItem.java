package ultratude.com.mzizi.swapimagevideos;

public class ImageItem {

    private String imageurl;
    private int albumid;

    public ImageItem(String imageurl, int albumid) {
        this.imageurl = imageurl;
        this.albumid = albumid;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getAlbumid() {
        return albumid;
    }
}
