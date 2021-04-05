package ultratude.com.mzizi.notificationpg.DisplayNotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.R;
//import ultratude.com.mzizi.RoomDatabaseClasses.RoomModel.Notification;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.uiactivities.MainActivity;

/**
 * Created by James on 01/09/2018.
 */

public class NotificationTopDisplay {

    static List<Integer> notificationIdHolder = new ArrayList<>();//for deleting purposes

//    static  NotificationManager notifyMgr;
//    static NotificationManager notificationManager;


    private static NotificationManager manager;
    public static boolean showingOnTop = false;
    private static Context mContext;

    private static List<Notification> notificationList = new ArrayList<>();

    private static List<Notification> newNotificationList = new ArrayList<>();

    public static HashMap<Integer, List<Notification>> studentNotificationHolderMap = new HashMap<>();

    public static void showNotificationDisplay(List<Notification> listNotifications , Context context,int notificationID, String from){

        mContext = context;

        if(studentNotificationHolderMap.size() > 0){

            if(studentNotificationHolderMap.get(notificationID) != null) {
                if (studentNotificationHolderMap.get(notificationID).size() > 0) {

                    newNotificationList = studentNotificationHolderMap.get(notificationID);

                    notificationList = listNotifications;
                    //prevents notification doubling

                        if(newNotificationList.size() != notificationList.size()) {
                            newNotificationList.addAll(notificationList);
                        }else if(newNotificationList.size() == 1){
                            newNotificationList.addAll(notificationList);
                        }

                    studentNotificationHolderMap.put(notificationID, newNotificationList);

                    newNotificationList = studentNotificationHolderMap.get(notificationID);

                    new GetAuthenticatedUser(newNotificationList.size(), notificationID).execute(context);

                } else {

                    notificationList = listNotifications;
                    studentNotificationHolderMap.put(notificationID, notificationList);

                    //newNotificationList.clear();
                    newNotificationList = studentNotificationHolderMap.get(notificationID);

                    new GetAuthenticatedUser(newNotificationList.size(), notificationID).execute(context);

                }
            }else{
                notificationList = listNotifications;
                studentNotificationHolderMap.put(notificationID, notificationList);

                //newNotificationList.clear();
                newNotificationList = studentNotificationHolderMap.get(notificationID);

                new GetAuthenticatedUser(newNotificationList.size(), notificationID).execute(context);
            }
        }else{
            notificationList = listNotifications;
            studentNotificationHolderMap.put(notificationID, notificationList);

            //newNotificationList.clear();
            newNotificationList = studentNotificationHolderMap.get(notificationID);

            new GetAuthenticatedUser(newNotificationList.size(), notificationID).execute(context);

        }

    }


    public static void giveContext(Context context){
        mContext = context;
    }






    public static class GetAuthenticatedUser extends AsyncTask<Context, Void, List<String>> {

       static int notificationCount;
       static int notificationID;

       NotificationManager notifyMgr;
       NotificationManager notificationManager;


        public static void cancelNotificationDisplay(String deletenotificationID){

            if(deletenotificationID != null){

                if(deletenotificationID.isEmpty()){
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                        NotificationManager notify =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                        notify.cancelAll();

                        newNotificationList.clear();

                    }else{

                        NotificationManager notify =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                        notify.cancelAll();

                        newNotificationList.clear();
                    }
                }else{
                    //delete per deletenotificationID
                    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                        NotificationManager notify =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                        notify.cancel(Integer.valueOf(deletenotificationID));

                        //testing


                        //newNotificationList.clear();
                        studentNotificationHolderMap.remove(Integer.valueOf(deletenotificationID));
                       // newNotificationList.clear();

                        Map<Integer,List<Notification>> mmm = studentNotificationHolderMap;

                    }else{

                        NotificationManager notify =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                        notify.cancel(Integer.valueOf(deletenotificationID));

                        //newNotificationList.clear();
                        studentNotificationHolderMap.remove(Integer.valueOf(deletenotificationID));
                    }

                }
            }else{
                if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                    NotificationManager notify =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    notify.cancelAll();

                    //testing


                    newNotificationList.clear();

                }else{


                    NotificationManager notify =  (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);


                    notify.cancelAll();


                    newNotificationList.clear();
                }
            }

        }



        public GetAuthenticatedUser(int count,int notificationid){
            notificationCount = count;
            notificationID = notificationid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> returnList) {




            Student student = new Student(returnList.get(0), returnList.get(1) );
            String schoolName = returnList.get(2);
            String studentName = returnList.get(3);


            //test
           if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


               // Toast.makeText(mContext, "NewNotificationList: " + newNotificationList.size(), Toast.LENGTH_LONG).show();


                String CHANNEL_ID = "mzizi_school_app_channel_01";
                CharSequence name = "mzizi_school_app_channel";
                String Description = "This channel contains notifications from mzizi school app.";



                notificationManager = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);



                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(R.color.colorPrimary);
                mChannel.enableVibration(true);
                //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mChannel.setShowBadge(true);

                if (notificationManager != null) {

                    notificationManager.createNotificationChannel(mChannel);
                }



                Intent toPIntent = new Intent(mContext, MainActivity.class);
                toPIntent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT,OpenFragments.OPEN_HOME_FRAGMENT);
                toPIntent.putExtra("StudentID", String.valueOf(notificationID));
                toPIntent.putExtra("NewNotificationsMapHolder", studentNotificationHolderMap);
                toPIntent.putExtra("appcode", student.getAppcode());
                toPIntent.putExtra("isLog_In", false);


                TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(toPIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


                    //DO IT THE INBOX STYLE


                //you should test here if there are nay new messsages, if not you dont have to display 0 new messages in your notifications
                NotificationCompat.InboxStyle add =  new NotificationCompat.InboxStyle();
                //android.app.Notification.InboxStyle add =   new android.app.Notification.InboxStyle();
                List<NotificationCompat.InboxStyle> list = new ArrayList<>();

                for(int i = 0; i< newNotificationList.size(); i++){//fill the list
                    list.add(i, add);
                }
                list.add(add);
                for(int j = 0; j<newNotificationList.size(); j++){
                    String notification = newNotificationList.get(j).getMsg();
//                    if(j == 0){
//                        list.get(j).addLine( "-----------" +studentName+"----------");
//                    }
                    list.get(j).addLine(notification);

                }

                add.setSummaryText("+"+newNotificationList.size()+" more");


               Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

              //THIS IS THE END



               //END OF DO IT THE INBOX SYLE

                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID)
                       // .setContentTitle(schoolName)
                        .setContentTitle( studentName +", "+ schoolName)
                        .setContentText((notificationCount==1) ? "You have " + notificationCount+" new notification" :"You have " +  notificationCount+" new notifications")
                       // .setContentText("You have " + notificationCount+ " new school notifications")
                        .setStyle(add)
                       // .setStyle(new NotificationCompat.BigTextStyle().bigText("Notice that the NotificationCompat.Builder constructor requires that you provide a channel ID. This is required for compatibility with Android 8.0 (API level 26) and higher, but is ignored by older versions By default, the notification's text content is truncated to fit one line. If you want your notification to be longer, you can enable an expandable notification by adding a style template with setStyle(). For example, the following code creates a larger text area"))
                        .setSmallIcon(R.mipmap.mzizi_app_icon)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setSound(sounduri)
                        .setShowWhen(true)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(resultPendingIntent)
                        .setColor(mContext.getResources().getColor(R.color.colorPrimary))
                        .addAction(R.mipmap.mzizi_app_icon, "Open", resultPendingIntent);

//                        .addAction(R.mipmap.ic_launcher, "More", resultPendingIntent)
//                        .addAction(R.mipmap.ic_launcher, "And more", resultPendingIntent);


                if (notificationManager != null)
                    notificationManager.notify(notificationID, builder.build());





            }else{//other bundled notification

                //
                final  String GROUP_KEY_NOTIFY = "mzizi_school_app_notify";

                Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Intent toPIntent = new Intent(mContext, MainActivity.class);
                toPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               toPIntent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT,OpenFragments.OPEN_HOME_FRAGMENT);
               toPIntent.putExtra("StudentID", String.valueOf(notificationID));
               toPIntent.putExtra("NewNotificationsMapHolder", studentNotificationHolderMap);
               toPIntent.putExtra("appcode", student.getAppcode());
               toPIntent.putExtra("isLog_In", false);

               PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1251, toPIntent, PendingIntent.FLAG_ONE_SHOT);

                int summaryNotificaitonId = notificationID;
                NotificationCompat.Builder builderSummary =
                        new NotificationCompat.Builder(mContext, String.valueOf(summaryNotificaitonId))
                                .setSmallIcon(R.mipmap.mzizi_app_icon)
                                .setContentTitle(schoolName)
                                .setSound(sounduri)
                                .setContentText((notificationCount==1) ? "You have " + notificationCount+" new notification." :"You have " +  notificationCount+" new notifications")
                                .setGroup(GROUP_KEY_NOTIFY)
                                .setContentIntent(pendingIntent)
                                .setGroupSummary(true);


                int count_notification_id = 0;

                notifyMgr = (NotificationManager) mContext.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                int notificationId = 100;
                NotificationCompat.Builder builder;


                int countToNotifySoundUri = 0;

                for(Notification notification : newNotificationList){

                        countToNotifySoundUri++;

                    notificationId += count_notification_id;
                    notificationIdHolder.add(notificationId);




                           builder= new NotificationCompat.Builder(mContext, String.valueOf(count_notification_id))
                                    .setSmallIcon(R.mipmap.mzizi_app_icon)
                                   .setContentTitle(studentName)
                                    .setContentText(notification.getMsg())
                                    .setContentIntent(pendingIntent)
                                    .setGroup(GROUP_KEY_NOTIFY);

                    if(countToNotifySoundUri == 1) {
                        builder.setSound(sounduri);
                    }

                    notifyMgr.notify(notificationId, builder.build());

                    count_notification_id++;
                }

                Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                 v.vibrate(1000);

                notificationIdHolder.add(summaryNotificaitonId);

                if(builderSummary.build() != null)
                    notifyMgr.notify(notificationID, builderSummary.build());


            }

            //END

            super.onPostExecute(returnList);
        }

        @Override
        protected List<String> doInBackground(Context... lists) {

            mContext = lists[0];

            Context context = lists[0];


            List<String> list = new ArrayList<>();

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context.getApplicationContext());



            List<AuthenticateUserResponse> authenticateUserResponseList =  db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
            List<PortalStudentInfo> studentInfo = db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(notificationID));
            for(AuthenticateUserResponse l : authenticateUserResponseList){
                //for testing purposes
                list.add(l.getUserID());//0
                list.add(l.getAppcode());//1
                if(studentInfo.size()>=1){
                    list.add(studentInfo.get(studentInfo.size() - 1).getSchoolName());
                }else{
                    list.add("School Notifications");
                }

            }

            List<PortalSiblings> siblingsList =  db.getPortalSiblingsDao().getSiblings();
            for(PortalSiblings sibling : siblingsList){
                if(sibling.getStudentID().equalsIgnoreCase(String.valueOf(notificationID)))
                    list.add(titleCaseConversion(sibling.getStudentFullName()));
            }


            return list;
        }
    }




    private static String titleCaseConversion(String inputString)
    {
        if ((inputString).isEmpty()) {
            return "";
        }

        if (inputString.length() == 1) {
            return inputString.toUpperCase();
        }

        StringBuffer resultPlaceHolder = new StringBuffer(inputString.length());



        int count = 1;
        for(String stringPart : inputString.split(" "))
        {
            if(count >2){
                break;
            }
            if (stringPart.length() > 1)
                resultPlaceHolder.append(stringPart.substring(0, 1)
                        .toUpperCase())
                        .append(stringPart.substring(1)
                                .toLowerCase());
            else
                resultPlaceHolder.append(stringPart.toUpperCase());

            resultPlaceHolder.append(" ");
            count++;
        }
        return resultPlaceHolder.toString().trim();

    }



}
