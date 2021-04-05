package ultratude.com.staff.activities.mziziapptutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ultratude.com.staff.R;

/**
 * Created by James on 13/05/2019.
 */

public class SliderAdapter extends PagerAdapter {


    public static boolean isFirstTime = true;
    private Context mContext;
    private LayoutInflater inflater;
    private TutorialActivity tutorialActivityWeakReference;

    //APPLY FOR LEAVE
    private int[] slideDescriptions;

    private String[] slideImages;


    //With this, you can use nother Ids to refer to this
    public String[] headers = {
            "STEP ONE",
            "STEP TWO",
            "STEP THREE",
            "STEP FOUR",
            "STEP FIVE",
            "STEP SIX",
            "STEP SEVEN",
            "STEP EIGHT",
            "STEP NINE",
            "STEP TEN",
            "STEP ELEVENT",
            "STEP THIRTEEN",
            "STEP FOURTEEN",
            "STEP FIFTEEN"
    };


    private String[] sampleImage = {};//{R.drawable.sleep_icon, R.drawable.eat_icon, R.drawable.home_one};


    private SliderAdapter sliderAdapter;
    ArrayList<Integer> images = new ArrayList<>();


    public SliderAdapter(Context context,TutorialActivity tutorialActivity, String[] slideImages, int[] slideDescriptions) {
        this.mContext = context;
        this.tutorialActivityWeakReference = tutorialActivity;

        this.slideDescriptions = slideDescriptions;
        this.slideImages = slideImages;

    }

    @Override
    public int getCount() {
        return slideDescriptions.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    public void displayOneImage(ImageView imageView, int position){
        if(imageView != null){
            imageView.setVisibility(View.GONE);

        }

        try{

            if(sampleImage.length == 0 && slideImages.length > 0){

                InputStream inputStream  = tutorialActivityWeakReference.getAssets().open(slideImages[position]);
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                carouselView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                images.add(R.drawable.home_one);
                images.add(R.drawable.help_message);
                imageView.setImageBitmap(bitmap);
            }
//            else if(sampleImage.length >0 & slideImages.length > 0){
//
//                InputStream inputStream  = tutorialActivityWeakReference.getAssets().open(sampleImage[position]);
//                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//
//                ImageListener imageListener = new ImageListener() {
//                    @Override
//                    public void setImageForPosition(int position, ImageView imageView) {
//                        // imageView.setImageResource(sampleImage[position]);
//                        imageView.setImageBitmap(bitmap);
//                    }
//                };
//
//                carouselView.setImageListener(imageListener);
//                carouselView.setVisibility(View.VISIBLE);
//                imageView.setVisibility(View.GONE);
//            }




        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private CarouselView carouselView;


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.slide_image);
        TextView slideHeading = view.findViewById(R.id.slide_heading);
        TextView slideDescription = view.findViewById(R.id.slide_description);

        carouselView =  view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImage.length);

        //slideImageView.setImageResource(slideImages[position]);
        displayOneImage(slideImageView,position);
        slideHeading.setText(headers[position]);
        slideDescription.setText( tutorialActivityWeakReference.getResources().getString(slideDescriptions[position]).toString());




        if(isFirstTime && position == 0) {
            isFirstTime = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (tutorialActivityWeakReference.tts != null) {
                        tutorialActivityWeakReference.tts.stop();
                        //tts.shutdown();
                    }

                    if (tutorialActivityWeakReference.ttsStatus) {

                        tutorialActivityWeakReference.speakout( tutorialActivityWeakReference.getResources().getString(slideDescriptions[position]).toString());
                        Log.d(tutorialActivityWeakReference.getPackageName().toUpperCase(), "The first thing i said is " +  tutorialActivityWeakReference.getResources().getString(slideDescriptions[position]).toString());
                    } else {
                        //Toast.makeText(mContext, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();

                    }

                }
            }, 1000);


        }
// else{
//
//                if (tutorialActivityWeakReference.tts != null) {
//                    tutorialActivityWeakReference.tts.stop();
//                    //tts.shutdown();
//                }
//
//
//            if (tutorialActivityWeakReference.ttsStatus) {
//                tutorialActivityWeakReference.speakout(slideDescriptions[position].toString());
//            } else {
//                Toast.makeText(mContext, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
//
//            }
//        }

        container.addView(view);

        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout) object);
    }
}