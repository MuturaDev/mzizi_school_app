package ultratude.com.mzizi.eventbus;

import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;

public class MessageEvent {

    public final YoutubeVideoGalleryResponse response;

    public MessageEvent(YoutubeVideoGalleryResponse response){
        this.response  = response;
    }
}
