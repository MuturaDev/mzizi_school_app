package ultratude.com.mzizi.uiactivities;

import android.content.Intent;
import android.os.Bundle;



import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.flipkart.youtubeview.activity.YouTubeActivity;
import com.flipkart.youtubeview.models.YouTubePlayerType;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;

import ultratude.com.mzizi.eventbus.MessageEvent;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.crashreport.Catcho;


public class GalleryActivity  extends AppCompatActivity/*extends MediaSliderActivity*/ {


  private DrawerLayout drawer;
    private NavigationView navigationView;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        setContentView(R.layout.content_gallery);

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon4);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


//       drawer = findViewById(R.id.drawer_layout);
//        //NavigationView navigationView = findViewById(R.id.nav_view);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView =  findViewById(R.id.nav_view);
//
//        populateHomeView(navigationView);
//
//         fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (drawer.isDrawerOpen(GravityCompat.END)) {
//                    drawer.closeDrawer(GravityCompat.END);
//                }else{
//                    drawer.openDrawer(GravityCompat.END);
//                }
//
//
//
////
//
//            }
//        });
        ReportAnalytics.reportScreenChangeAnalytic(GalleryActivity.this, "Gallery Activity");

    }






    private void populateHomeView(final NavigationView navigationView){
        final  View view =  navigationView.getHeaderView(0);

        final RelativeLayout image_view_btn = view.findViewById(R.id.image_view_btn);
        final RelativeLayout video_view_btn = view.findViewById(R.id.video_image_view);

        image_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    video_view_btn.setBackground(getDrawable(R.drawable.gallery_bottom_outline));
                    image_view_btn.setBackground(getDrawable(R.drawable.gallery_layout_outline));

                    navigationView.findViewById(R.id.image_layout).setVisibility(View.VISIBLE);
                navigationView.findViewById(R.id.video_layout).setVisibility(View.GONE);


            }
        });


        video_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    image_view_btn.setBackground(getDrawable(R.drawable.gallery_bottom_outline));
                    video_view_btn.setBackground(getDrawable(R.drawable.gallery_layout_outline));

                navigationView.findViewById(R.id.video_layout).setVisibility(View.VISIBLE);
                navigationView.findViewById(R.id.image_layout).setVisibility(View.GONE);


            }
        });



    }





    @Override
    public void onBackPressed() {

//        if (drawer.isDrawerOpen(GravityCompat.END)) {
//            drawer.closeDrawer(GravityCompat.END);
//
////        }if(fab.getVisibility() == View.GONE){
////            fab.setVisibility(View.VISIBLE);
//        }else{
            super.onBackPressed();
        //}

    }




    //This method will be called when the messageevent is posted (in the ui thread for toast)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
//        fab.setVisibility(View.GONE);
//        ArrayList<String> list = new ArrayList<>();
//        list.add(event.response.getVideoLink());
//       // list.add("https://res.cloudinary.com/kartiksaraf/video/upload/v1564516308/github_MediaSliderView/demo_videos/video1_jetay3.mp4");
//        loadMediaSliderView(list,"video",true,false,false,event.response.getName(),"#000000",null,0);
        Intent intent = new Intent(GalleryActivity.this, YouTubeActivity.class);
        intent.putExtra("apiKey", Constants.API_KEY);
       // intent.putExtra("videoId", "3AtDnEC4zak");
        intent.putExtra("videoId", UtilityFunctions.getYoutubeID(event.response.getVideoLink()));
        intent.putExtra("playerType", YouTubePlayerType.STRICT_NATIVE);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
