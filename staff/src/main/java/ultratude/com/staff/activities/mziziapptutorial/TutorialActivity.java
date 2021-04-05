package ultratude.com.staff.activities.mziziapptutorial;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.Locale;

import ultratude.com.staff.R;

import ultratude.com.staff.utils.Constants;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout dotsLayout;

    public TextToSpeech tts;
    public boolean ttsStatus = false;


    private TextView[] mDots;

    private Button btnPrevious;
    private Button btnNext;

    private int currentPage = 0;
    private SliderAdapter sliderAdapter;

    private int[] slideDescriptions;
    private String[] slideImages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
       setContentView(R.layout.tutorial_activity_layout);


      int[] arr1 = (int[])  getIntent().getExtras().getSerializable("SlideDescription");
      String[] arr2 = (String[])  getIntent().getExtras().getSerializable("SlideImages");
        if(arr1 != null && arr2 != null){
            slideDescriptions = arr1;
            slideImages = arr2;
        }


        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        ttsStatus = false;
                       // Toast.makeText(TutorialActivity.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                        Log.d(TutorialActivity.this.getPackageName().toUpperCase(), "Error occured. Sorry, you will not be able to use this service.");

                    }else{
                        ttsStatus = true;
                        // helpText.setEnabled(true);
                        //speakout();
                    }
                }else{
                   // Toast.makeText(TutorialActivity.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();

                    ttsStatus = false;
                }
            }
        });

        slideViewPager = findViewById(R.id.slideViewPager);
        dotsLayout = findViewById(R.id.dotsLayout);

        btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(currentPage + 1);
            }
        });
        btnPrevious = findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideViewPager.setCurrentItem(currentPage - 1);
            }
        });


        sliderAdapter = new SliderAdapter(this.getBaseContext(), this,slideImages,slideDescriptions);
        slideViewPager.setAdapter(sliderAdapter);
        slideViewPager.getAdapter().getCount();

        addDotsIndicator(0);

        slideViewPager.addOnPageChangeListener(viewListener);

    }


    public void addDotsIndicator(int position){
        mDots = new TextView[slideDescriptions.length];
        dotsLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDots[i].setTextSize(40);

            dotsLayout.addView(mDots[i]);


        }

        if(mDots.length > 0 ){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
            mDots[position].setTextSize(50);

            //not working
//            final ViewGroup.LayoutParams layoutparams = (ViewGroup.LayoutParams) mDots[position].getLayoutParams();
//            layoutparams.width = 20;
//            layoutparams.height = 20;
            //mDots[position].setLayoutParams(layoutparams);

        }
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int i) {
            addDotsIndicator(i);
            currentPage = i;
            String textDescription = TutorialActivity.this.getResources().getString(slideDescriptions[currentPage]);
            //testing
            if(i==0){
                Log.d(getPackageName().toUpperCase(), String.valueOf("I should have worked"));

                btnNext.setEnabled(true);
                btnPrevious.setEnabled(false);
                btnPrevious.setVisibility(View.INVISIBLE);

                btnNext.setText("Next");
                btnPrevious.setText("");

                if (tts != null) {
                    tts.stop();
                    //tts.shutdown();
                }


                if(ttsStatus){
                    speakout(textDescription);
                    Log.d(getPackageName().toUpperCase(), "I said " + textDescription);

                }else{
                    //Toast.makeText(TutorialActivity.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                    Log.d(TutorialActivity.this.getPackageName().toUpperCase(), "Error occured. Sorry, you will not be able to use this service.");
                }

            }else if(i == mDots.length - 1){

                if (tts != null) {
                    tts.stop();
                    //tts.shutdown();
                }


                if(ttsStatus){
                    speakout(textDescription);
                    Log.d(getPackageName().toUpperCase(), "I said " + textDescription);

                }else{
                   // Toast.makeText(TutorialActivity.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                    Log.d(TutorialActivity.this.getPackageName().toUpperCase(), "Error occured. Sorry, you will not be able to use this service.");

                }

                btnNext.setEnabled(true);
                btnPrevious.setEnabled(true);
                btnPrevious.setVisibility(View.VISIBLE);

                btnNext.setText("Finish");
//                String s = null;
//                s.length();
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                btnPrevious.setText("Back");
            }else{

                if (tts != null) {
                    tts.stop();
                    //tts.shutdown();
                }


                if(ttsStatus){
                    speakout(textDescription);
                    Log.d(getPackageName().toUpperCase(), "I said " + textDescription);

                }else{
                    //Toast.makeText(TutorialActivity.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                    Log.d(TutorialActivity.this.getPackageName().toUpperCase(), "Error occured. Sorry, you will not be able to use this service.");

                }
                btnNext.setEnabled(true);
                btnPrevious.setEnabled(true);
                btnPrevious.setVisibility(View.VISIBLE);

                btnNext.setText("Next");
                btnPrevious.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(getPackageName().toUpperCase(), "Page Scroll: " + String.valueOf(state));

        }
    };


    @Override
    protected void onDestroy() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }


        super.onDestroy();
    }



    public void speakout(String textToRead){
//        StringBuilder stringb = new StringBuilder();
//        stringb.append(TripTransport.this.getResources().getString(R.string.start_trip_instructions));
//        stringb.append(" ");
//        stringb.append(TripTransport.this.getResources().getString(R.string.end_trip_instructions));
//        stringb.append(" ");
//        stringb.append(TripTransport.this.getResources().getString(R.string.how_to_use_app));
        tts.speak(textToRead.toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

}
