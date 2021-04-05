package ultratude.com.mzizi.uiactivities;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

import io.paperdb.Paper;
import ultratude.com.mzizi.R;

/**
 * Created by James on 31/07/2018.
 */

public class FloatingActionShow {

    private static Context context;


    private static boolean otherSibling = false;
    private static MainActivity activity;
    private static boolean is_first_launch = false;
    private static boolean allowSchoolChat = false;





    private FloatingActionShow(){

    }

   public static void getFloatInstance(MainActivity mainActivity){
       context = mainActivity.getBaseContext();
       activity = mainActivity;

       is_first_launch = mainActivity.getIntent().getBooleanExtra("isLog_In", false);
    }



    public static void showSchoolChat(boolean show){

//        if(show == true){
//            if(allowSchoolChat== true){
//                floatingBtn.setVisibility(View.VISIBLE);
//
//                if(is_first_launch){
//
//                    is_first_launch = false;
//                }else{
//
//                }
//            }
//        }else if(show == false){
//
//            if(allowSchoolChat == true){
//
//                floatingBtn.setVisibility(View.GONE);
//            }
//        }


        if(show && allowSchoolChat){
//            floatingBtn.setVisibility(View.VISIBLE);
            if(is_first_launch){
                is_first_launch  = false;
            }
        }else{
//            floatingBtn.setVisibility(View.GONE);
        }


        Log.d(context.getPackageName().toUpperCase(), "Show: " +  show + " AllowSchoolChat: " + allowSchoolChat);
    }


    public static void supportsSchoolChat(boolean allow){
        allowSchoolChat = allow;
    }




   public static void showFloat (final boolean show ){

      // Toast.makeText(context, "Is First Login: " + is_first_launch, Toast.LENGTH_SHORT).show();
    //   Toast.makeText(context, "Show: : othersibling " + show + " " + otherSibling, Toast.LENGTH_SHORT).show();


//       true and true; =true
//                        true and false; = false;
//                        false and true; = false;
//       false and false; = true


            //not true and false
        if(show == true){//should be true and true

            if(otherSibling == true){

//                floatButton.setVisibility(View.VISIBLE);




                //show the tutorial
                //start from the floatbutton
                //and start the sequence of the queue from the floatbutton
                //check if its the first time to launch the application


                //check if its the first boot up
                //input: true and false = false and false
                //input:true and true = false and true
                if(is_first_launch){
                    //if first time, call loginActivity

                    //START OF SHOW CASE VIEW
                    final Display display = activity.getWindowManager().getDefaultDisplay();

                    final Drawable droid  = ContextCompat.getDrawable(context, R.drawable.profile);

                    //FOR A SEQUENCE OF TAPS

                    // Tell our droid buddy where we want him to appear
                    final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
                    // Using deprecated methods makes you look way cool
                    droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

                    //this is really cool, just for underling here you want, just like in html
                    final SpannableString menu = new SpannableString("Tap to view student profile and other menu items");
                    menu.setSpan(new StyleSpan(Typeface.ITALIC), menu.length() - "".length(), menu.length(), 0);

                    // We have a sequence of targets, so lets build it!
                    final TapTargetSequence sequence = new TapTargetSequence(activity)
                            .targets(
                                    // This tap target will target the back button, we just need to pass its containing toolbar


                                    TapTarget.forToolbarNavigationIcon(activity.toolbar, "Menu", menu).id(1),  //in out case we dont have a navigation view set
                                    // Likewise, this tap target will target the search button
                                    //this was commented because the refresh button was removed
//                                    TapTarget.forToolbarMenuItem(activity.toolbar, R.id.refresh_btn, "Refresh", "Refresh to updated information.")
//                                            .cancelable(false)
//                                            .drawShadow(true)
//                                            .id(2),

                                    // You can also target the overflow button in your toolbar
                                    TapTarget.forToolbarOverflow(activity.toolbar,"Settings","Tap to view Settings. \nYou can secure the MziziApp using pattern or fingerprint and pin. \nView your account credentials \nView Student Biodata.  \nLogout.").id(2)

                                    //In our case we are not pointing to any spacific location in on the screen. done programmatically.
                                               /* ,*/
                                    // This tap target will target our droid buddy at the given target rect
                                    //                       TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                    //                                .cancelable(false)
                                    //                                .icon(droid)
                                    //                                .id(4)
                            )
                            .listener(new TapTargetSequence.Listener() {
                                // This listener will tell us when interesting(tm) events happen in regards
                                // to the sequence
                                @Override
                                public void onSequenceFinish() {
                                    //for our case just use a alert the user, that he or she is educated
                                    //((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                                }

                                @Override
                                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                                    //Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                                }

                                @Override
                                public void onSequenceCanceled(TapTarget lastTarget) {
                                    this.onSequenceFinish();

                                }
                            });

                    //FOR SINGLE TAPS

                    //you have to change if its visible


                    // You don't always need a sequence, and for that there's a single time tap target
                    activity.drawer.openDrawer(GravityCompat.START);
                    final SpannableString switchSibling = new SpannableString("Tap, then select a sibling you want to sign in to.");
                    switchSibling.setSpan(new UnderlineSpan(), switchSibling.length() - "".length(), switchSibling.length(), 0);
                   //TODO: The error is from here
                    TapTargetView.showFor(activity, TapTarget.forView(activity.other_siblings_layout_clickable, "Switch sibling accounts", switchSibling)
                            .cancelable(false)
                            .drawShadow(true)
                            // .titleTextDimen(R.dimen.title_text_size)
                            .tintTarget(false), new TapTargetView.Listener() {
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);
                            // .. which evidently starts the sequence we defined earlier
                            activity.drawer.closeDrawer(GravityCompat.START);
                            if(allowSchoolChat){

                               // final SpannableString spannableString3 = new SpannableString()

                                final SpannableString schoolchat = new SpannableString("Tap, to communicate with the school.");
                                schoolchat.setSpan(new UnderlineSpan(), schoolchat.length() - "".length(), schoolchat.length(), 0);
                                TapTargetView.showFor(activity, TapTarget.forView(activity.findViewById(R.id.floating_chat_ID), "School Chat", schoolchat)
                                        .cancelable(false)
                                        .drawShadow(true)
                                        // .titleTextDimen(R.dimen.title_text_size)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        // .. which evidently starts the sequence we defined earlier


                                        if(Paper.book().read("MziziUI") != null){
                                            if(Paper.book().read("MziziUI")){
                                                final SpannableString expandableVisible = new SpannableString("Tap on the eye icon, to make the section viewable permanently.");
                                                expandableVisible.setSpan(new UnderlineSpan(), expandableVisible.length() - "".length(), expandableVisible.length(), 0);
                                                TapTargetView.showFor(activity, TapTarget.forView(activity.findViewById(R.id.assignmentCommunicationExpand), "Sticky section", expandableVisible)
                                                                .cancelable(false)
                                                                .drawShadow(true)
                                                                .tintTarget(false),
                                                        new TapTargetView.Listener(){
                                                            @Override
                                                            public void onTargetClick(TapTargetView view) {
                                                                super.onTargetClick(view);
                                                                // sequence.start();
                                                            }

                                                            @Override
                                                            public void onOuterCircleClick(TapTargetView view) {
                                                                super.onOuterCircleClick(view);
                                                            }

                                                            @Override
                                                            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                                                                super.onTargetDismissed(view, userInitiated);
                                                            }
                                                        }
                                                );
                                            }
                                        }
                                    }

                                    @Override
                                    public void onOuterCircleClick(TapTargetView view) {
                                        super.onOuterCircleClick(view);

                                        //Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                                        // Log.d("TapTargetViewSample", "You dismissed me :(");
                                    }
                                });
                            }else{
                                sequence.start();
                            }

                        }

                        @Override
                        public void onOuterCircleClick(TapTargetView view) {
                            super.onOuterCircleClick(view);

                            //Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                            // Log.d("TapTargetViewSample", "You dismissed me :(");
                        }
                    });



                    //END OF SHOW CASE VIEW


                    is_first_launch = false;

                    //settings.edit().putBoolean("my_first_time", false).commit();
                }else{
                    //do nothing
                }

            }


                //input: true and false //
                //input:true and true //
                //input: false and true //
                //input : false
        }else if(show == false){//should be false and false

//            if(otherSibling== true) {
//                floatButton.setVisibility(View.GONE);
//
//            }


                //show the tutorial
                //start from the floatbutton
                //and start the sequence of the queue outside the float button
                //check if its the first time to launch the application

                if(is_first_launch) {
                    //if first time, call loginActivity

                    //START OF SHOW CASE VIEW
                    final Display display = activity.getWindowManager().getDefaultDisplay();

                    final Drawable droid = ContextCompat.getDrawable(context, R.drawable.profile);

                    //FOR A SEQUENCE OF TAPS

                    // Tell our droid buddy where we want him to appear
                    final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
                    // Using deprecated methods makes you look way cool
                    droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

                    //this is really cool, just for underling here you want, just like in html
                    final SpannableString sassyDesc = new SpannableString("Tap to view student profile and other menu items");
                    sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "".length(), sassyDesc.length(), 0);

                    // We have a sequence of targets, so lets build it!
                    final TapTargetSequence sequence = new TapTargetSequence(activity)
                            .targets(
                                    // This tap target will target the back button, we just need to pass its containing toolbar


//                                    TapTarget.forToolbarNavigationIcon(activity.toolbar, "Menu", sassyDesc).id(1),  //in out case we dont have a navigation view set
//                                    // Likewise, this tap target will target the search button
//                                    TapTarget.forToolbarMenuItem(activity.toolbar, R.id.refresh_btn, "Refresh", "Refresh to updated information.")
//                                            .cancelable(false)
//                                            .drawShadow(true)
//                                            .id(2),
                                    // You can also target the overflow button in your toolbar
                                    TapTarget.forToolbarOverflow(activity.toolbar, "Settings", "Tap to view Settings. \nYou can secure the MziziApp using pattern or fingerprint and pin. \nView your account credentials \nView Student Biodata.  \nLogout.").id(1)

                                    //In our case we are not pointing to any spacific location in on the screen. done programmatically.
                                               /* ,*/
                                    // This tap target will target our droid buddy at the given target rect
                                    //                       TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                    //                                .cancelable(false)
                                    //                                .icon(droid)
                                    //                                .id(4)
                            )
                            .listener(new TapTargetSequence.Listener() {
                                // This listener will tell us when interesting(tm) events happen in regards
                                // to the sequence
                                @Override
                                public void onSequenceFinish() {
                                    //for our case just use a alert the user, that he or she is educated
                                    //((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                                    if(show ==  false){

                                        if(allowSchoolChat){

                                            //TODO: ADD NUMBER OF MESSAGES THE USER IS ALLOWED TO SEND TO THE SCHOOL ABOUT 5 AT MOST
                                            final SpannableString spannedDesc2 = new SpannableString("Tap, to communicate with the school.");
                                            spannedDesc2.setSpan(new UnderlineSpan(), spannedDesc2.length() - "".length(), spannedDesc2.length(), 0);
                                            TapTargetView.showFor(activity, TapTarget.forToolbarMenuItem(activity.toolbar,R.id.floating_chat_ID, "School Chat", spannedDesc2)
                                                    .cancelable(false)
                                                    .drawShadow(true)
                                                    // .titleTextDimen(R.dimen.title_text_size)
                                                    .tintTarget(false), new TapTargetView.Listener() {
                                                @Override
                                                public void onTargetClick(TapTargetView view) {
                                                    super.onTargetClick(view);
                                                    // .. which evidently starts the sequence we defined earlier
                                                    // sequence.start();
                                                }

                                                @Override
                                                public void onOuterCircleClick(TapTargetView view) {
                                                    super.onOuterCircleClick(view);

                                                    //Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                                                    // Log.d("TapTargetViewSample", "You dismissed me :(");
                                                }
                                            });
                                        }


                                    }
                                }

                                @Override
                                public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                                    //Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                                }

                                @Override
                                public void onSequenceCanceled(TapTarget lastTarget) {

                                    this.onSequenceFinish();
                                }
                            });


                    sequence.start();

                    is_first_launch = false;

                }else{


            }

        }

    }


   public static void otherSiblings(boolean other){
        otherSibling = other;
    }



}
