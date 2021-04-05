package ultratude.com.mzizi.videoimagefragments.videoimageutil;

import android.os.Bundle;

import java.util.ArrayList;

import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;

public class VideoSliderDemo extends MediaSliderActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        ArrayList<String> list = new ArrayList<>();
        list.add("https://res.cloudinary.com/kartiksaraf/video/upload/v1564516308/github_MediaSliderView/demo_videos/video1_jetay3.mp4");
        list.add("https://res.cloudinary.com/kartiksaraf/video/upload/v1564516308/github_MediaSliderView/demo_videos/video2_sn3sek.mp4");
        list.add("https://res.cloudinary.com/kartiksaraf/video/upload/v1564516308/github_MediaSliderView/demo_videos/video3_jcrsb3.mp4");
        loadMediaSliderView(list,"video",true,false,false,"Video-Slider","#000000",null,0);
    }



}
