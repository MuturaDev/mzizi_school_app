package ultratude.com.staff.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import ultratude.com.staff.R;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.adapters.NewHomeScreenTopItemsAdapter;
import ultratude.com.staff.model.HomeItem;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;

public class UtilityFunctions {


    //https://stackoverflow.com/questions/6609414/how-do-i-programmatically-restart-an-android-app
    public static void doRestart(Context c) {
        String TAG = c.getPackageName().toUpperCase();
        try {
            //check if the context is given
            if (c != null) {
                //fetch the packagemanager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e(TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e(TAG, "Was not able to restart application, PM null");
                }
            } else {
                Log.e(TAG, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Was not able to restart application");
        }
    }

    public static void makeACall(Context context, String status, String phoneNumber){

        if(phoneNumber.length() == 0 || phoneNumber.length() == 1){
            Toast.makeText(context, "Sorry, no phone number to place the call to.", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(context, "Making a call to: " + phoneNumber, Toast.LENGTH_LONG).show();

        if(status.equals("WhatsAppCall")){//ERROR: DOES NOT WORK
            try{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                /* sets the desired package for the intent */
                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(context, "Sorry, you must have Whats App Installed", Toast.LENGTH_SHORT).show();
            }
        }else if(status.equals("PhoneCall")){
            try{
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + phoneNumber));
                context.startActivity(intent);
            }catch (Exception ex){
                ex.printStackTrace();
                Toast.makeText(context, "Sorry, you must have Phone App Installed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void copyToClipBoard(Context context, String textToCopy) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(textToCopy);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Phone Number",textToCopy);
            clipboard.setPrimaryClip(clip);
        }

    }



    public static void activateQuickActions(final AppCompatActivity activity, Integer data_capture_count, String screenLabel){

        TextView txt_data_capture_count = activity.findViewById(R.id.txt_data_capture_count);
        LinearLayout layout_captured_data_count = activity.findViewById(R.id.layout_captured_data_count);
        if(data_capture_count <= 0){
            if(layout_captured_data_count != null)
            layout_captured_data_count.setVisibility(View.GONE);
        }else{
            txt_data_capture_count.setVisibility(View.VISIBLE);
            txt_data_capture_count.setText(String.valueOf(data_capture_count));
        }

        LinearLayout layout_sync = activity.findViewById(R.id.layout_sync);
        if(layout_sync != null)
        layout_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Will use new sync feature", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout btn_back = activity.findViewById(R.id.btn_back);
        if(btn_back != null)
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });

        TextView back_label = activity.findViewById(R.id.back_label);
        if(back_label != null)
        back_label.setText(screenLabel);


        //FOR VISIBILITY
        final LinearLayout quick_actions_linearlayout = activity.findViewById(R.id.quick_actions_linearlayout);
        final LinearLayout quick_action_key = activity.findViewById(R.id.quick_action_key);

        //FOR CHANGE IN HEIGHT
        final FrameLayout quick_actions_framelayout = activity.findViewById(R.id.quick_actions_framelayout);
        final RelativeLayout quick_actions_cardview = activity.findViewById(R.id.quick_actions_cardview);

        //FOR ADDING MARGIN TOP
        final ViewGroup scrollview = activity.findViewById(R.id.scrollview);


        final ToggleButton quick_action_hide_show = activity.findViewById(R.id.quick_action_hide_show);
        //toggle3.setChecked(true);
        quick_action_hide_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!isChecked){

                    if(quick_actions_linearlayout != null)
                        quick_actions_linearlayout.setVisibility(View.VISIBLE);

                    if(quick_action_key != null)
                        quick_action_key.setVisibility(View.VISIBLE);


                    CardView.LayoutParams param1 = new CardView.LayoutParams(
                            /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                            /*height*/ (int) activity.getResources().getDimension(R.dimen.quick_action_size_expand_cardview)
                            //,
                            /*weight*/// 1.0f
                    );

                    if(quick_actions_cardview != null)
                        quick_actions_cardview.setLayoutParams(param1);

                    RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
                            /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                            /*height*/ (int) activity.getResources().getDimension(R.dimen.quick_action_size_expand_framelayout)
                            ///*weight*/ 1.0f
                    );

                    if(quick_actions_framelayout != null)
                        quick_actions_framelayout.setLayoutParams(param2);

                    if(scrollview != null) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) scrollview.getLayoutParams();
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        //params.leftMargin = 100;
                        params.topMargin = (int) activity.getResources().getDimension(R.dimen.quick_action_size_expand_cardview);
                    }

                }else{
                    if(quick_actions_linearlayout != null)
                        quick_actions_linearlayout.setVisibility(View.GONE);

                    if(quick_action_key != null)
                        quick_action_key.setVisibility(View.GONE);


                    CardView.LayoutParams param1 = new CardView.LayoutParams(
                            /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                            /*height*/ (int) activity.getResources().getDimension(R.dimen.quick_action_size_collapse_cardview)
                            ///*weight*/ 1.0f
                    );
                    if(quick_actions_cardview != null)
                        quick_actions_cardview.setLayoutParams(param1);

                    RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(
                            /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                            /*height*/ (int) activity.getResources().getDimension(R.dimen.quick_action_size_collapse_framelayout)
                           // /*weight*/ 1.0f
                    );

                    if(quick_actions_framelayout != null)
                        quick_actions_framelayout.setLayoutParams(param2);

                    if(scrollview != null) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) scrollview.getLayoutParams();
                        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        //params.leftMargin = 100;
                        params.topMargin = (int) activity.getResources().getDimension(R.dimen.quick_action_size_collapse_cardview);
                    }

                }
            }
        });

        NewHomeScreenTopItemsAdapter rc_top_items_adapter = new NewHomeScreenTopItemsAdapter(activity, new ArrayList<HomeItem>() );

        if(( activity.findViewById(R.id.rc_top_items_list)) != null)
        ((RecyclerView) activity.findViewById(R.id.rc_top_items_list)).setAdapter(rc_top_items_adapter);

    }

    //https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
    public static  String customFormat(String pattern, double value ) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        String output = myFormatter.format(value);
        return output;
    }


    public static List<DutyRoster> dutyRosterDummyData(){
        List<DutyRoster> dutyRosterList = new ArrayList<>();
        DutyRoster dutyRoster1 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "4",
                "Week 1",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster1);
        DutyRoster dutyRoster2 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "5",
                "Week 2",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster2);
        DutyRoster dutyRoster3 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster3);

        DutyRoster dutyRoster4 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster4);

        DutyRoster dutyRoster5 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster5);

        DutyRoster dutyRoster6 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster6);

        DutyRoster dutyRoster7 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster7);

        DutyRoster dutyRoster8 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster8);

        DutyRoster dutyRoster9 = new DutyRoster(
                "Millie Collins",
                "2019",
                "Term 3",
                "6",
                "Week 3",
                "0712345678"

        );
        dutyRosterList.add(dutyRoster9);

        return dutyRosterList;
    }

}
