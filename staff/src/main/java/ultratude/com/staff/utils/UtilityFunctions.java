package ultratude.com.staff.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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






}
