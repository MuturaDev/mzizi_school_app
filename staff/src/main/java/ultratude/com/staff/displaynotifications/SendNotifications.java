package ultratude.com.staff.displaynotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;


import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.activities.TripTransport;
import ultratude.com.staff.R;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by James on 16/05/2019.
 */

public class SendNotifications {

    static List<Integer> notificationIdHolder = new ArrayList<>();//for deleting purposes
    private NotificationManager notifyMgr;
    private  NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 234;

    private Context mContext;




    public SendNotifications(Context context){
        this.mContext = context;

    }



    public void displayNotification(String title, String message, String subMessage){

        if(SDK_INT>= Build.VERSION_CODES.O) {

            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";

            notificationManager = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(mChannel);
            }


            Intent toPIntent = new Intent(mContext, TripTransport.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
            stackBuilder.addParentStack(TripTransport.class);
            stackBuilder.addNextIntent(toPIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.InboxStyle add = new NotificationCompat.InboxStyle();
            add.addLine(title);
            add.setSummaryText("GPS open...");

            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.InboxStyle d = new NotificationCompat.InboxStyle().addLine("");

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(message)
                    //.setSubText(subMessage)
                    // .setContentText("You have " + notificationCount+ " new school notifications")
                    .setStyle(add)
                    // .setStyle(new NotificationCompat.BigTextStyle().bigText("Notice that the NotificationCompat.Builder constructor requires that you provide a channel ID. This is required for compatibility with Android 8.0 (API level 26) and higher, but is ignored by older versions By default, the notification's text content is truncated to fit one line. If you want your notification to be longer, you can enable an expandable notification by adding a style template with setStyle(). For example, the following code creates a larger text area"))
                    .setSmallIcon(R.drawable.my_location_icon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .setSound(sounduri)
                    .setShowWhen(true)
                    .setWhen(System.currentTimeMillis())
                    .setColor(mContext.getResources().getColor(android.R.color.holo_red_dark))
                    .addAction(R.drawable.mzizi_laucher_icon, "Open", resultPendingIntent);
//                        .addAction(R.mipmap.ic_launcher, "More", resultPendingIntent)
//                        .addAction(R.mipmap.ic_launcher, "And more", resultPendingIntent);

            if (notificationManager != null) {

                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }


        }else{



            final  String GROUP_KEY_NOTIFY = "group_key_notify";

            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            Intent toPIntent = new Intent(mContext, TripTransport.class);
            // toPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                toPIntent.putExtra(OpenFragments.OPEN_NOTIFICATION_FRAGMENT,OpenFragments.OPEN_NOTIFICATION_FRAGMENT);
//                toPIntent.putExtra("StudentID", student.getStudentID());
//                toPIntent.putExtra("appcode", student.getAppcode());
//                toPIntent.putExtra("isLog_In", false);

            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1251, toPIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            int summaryNotificaitonId = 9961;
            NotificationCompat.Builder builderSummary =
                    new NotificationCompat.Builder(mContext, String.valueOf(summaryNotificaitonId))
                            .setSmallIcon(R.drawable.my_location_icon)
                            .setContentTitle(title)
                            .setContentText(message)
                            //.setSubText(subMessage)
                            .setSound(sounduri)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setAutoCancel(false)
                            .setOngoing(true)
                            .setGroup(GROUP_KEY_NOTIFY)
                            .setContentIntent(pendingIntent)
                            .setGroupSummary(true);


            int count_notification_id = 0;

            notifyMgr = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            int notificationId = 100;
            NotificationCompat.Builder builder;


            int countToNotifySoundUri = 0;


            builder= new NotificationCompat.Builder(mContext, String.valueOf(count_notification_id))
                    .setSmallIcon(R.drawable.my_location_icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    //.setSubText(subMessage)
                    //The notification will be cleared if the application is uninstalled or by calling Cancel or CancelAll:
                    .setAutoCancel(false)
                    .setOngoing(true)//The setOngoing(true) call achieves this, and setAutoCancel(false) stops the notification from going away when the user taps the notification.
                    .setContentIntent(pendingIntent)
                    .setGroup(GROUP_KEY_NOTIFY);

            if(countToNotifySoundUri == 1) {
                builder.setSound(sounduri);
            }

            notifyMgr.notify(notificationId, builder.build());

            count_notification_id++;


            Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(1000);

            notificationIdHolder.add(summaryNotificaitonId);
            notifyMgr.notify(summaryNotificaitonId, builderSummary.build());


        }
    }


    public static void cancelNotificationDisplay(TripTransport transport){


        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
//                if(notificationManager != null)
//                notificationManager.cancel(NOTIFICATION_ID);

            // NotificationTopDisplay.notificationManager.cancelAll();

            NotificationManager notify =  (NotificationManager) transport.getSystemService(Context.NOTIFICATION_SERVICE);
            notify.cancelAll();

            //newNotificationList.clear();

        }else{

//                for(Integer integer : notificationIdHolder){
//                    if(notifyMgr != null)
//                    notifyMgr.cancel(integer);
//                }

            NotificationManager notify =  (NotificationManager) transport.getSystemService(Context.NOTIFICATION_SERVICE);


            notify.cancelAll();


            // newNotificationList.clear();
        }


    }


}