//package ultratude.com.mzizi.notificationpg.NotificationBroadcast;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//
//
///**
// * Created by James on 20/07/2018.
// */
//
//public class NotificationService extends Service {
//
//
//    NotificationBroadcastReceiver broadcastReceiver;
//    PendingIntent alarmPIntent;
//    AlarmManager alarmManager;
//
//    Student student;
//
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        Log.d(getApplicationContext().getPackageName().toUpperCase().toString(), "Service Started");
//
//    }
//
//
//
//
//    @Override
//    public void onDestroy() {
//       // Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
//        //unregister the broadcast
//        unregisterNetworkBroadcastReceiver();
//
//        //Cancel the alarm
//        if(!(alarmManager == null)) {
//            alarmManager.cancel(alarmPIntent);
//            alarmManager = null;
//            student = null;
//        }else{
//            alarmManager = null;
//        }
//
//      //  Toast.makeText(getApplicationContext(), "Service Stopped ", Toast.LENGTH_LONG).show();
//        Log.d(getApplicationContext().getPackageName().toUpperCase().toString(), "Service Destroyed");
//        super.onDestroy();
//    }
//
//
//
//    //this helps alot, when the app is closed, bload cast are not fired
//    private void unregisterNetworkBroadcastReceiver(){
//        try{
//            unregisterReceiver(broadcastReceiver);
//        }catch(IllegalArgumentException e){
//            e.printStackTrace();
//        }
//    }
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    @Override
//    public int onStartCommand(Intent intent,  int flags, int startId) {
//
//        new SetAlarmForNotification(this).execute();
//
//
//        return START_STICKY;
//    }
//
//
//
//    private class SetAlarmForNotification extends AsyncTask<Void, Void, List<String>> {
//
//
//        Context context;
//
//        public SetAlarmForNotification(Context context){
//
//            this.context = context;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//
//
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(List<String> list) {
//
//
//            //Toast.makeText(context, String.valueOf(list.size()) + " " + String.valueOf(list.get(0)), Toast.LENGTH_SHORT).show();
//
//            if(list.size() > 0){
//                String studentID = list.get(0);
//                String appcode = list.get(1);
//
//                if(studentID != null && appcode != null){
//                    if(!studentID.equals("") && !appcode.equals("")){
//
//                        //now you can set the alarm for notification
//                        final IntentFilter theFilter = new IntentFilter();
//                        //to show that there is availability of internet, if it becomes available
//                        //MainActivity activity, NotificationFrag notificationFrag
//
//                        //MessageRequest messageRequest = new MessageRequest(intent.getExtras().getString("StudentID"),notificationLastID , intent.getExtras().getString("appcode"));
//
//                        broadcastReceiver = new NotificationBroadcastReceiver(studentID,appcode);
//
//
//                        // broadcastReceiver = new DataTransferReceiverBroadcast(intent.getExtras().getString("StudentID"), intent.getExtras().getString("appcode"));
//                        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//                        //NOT SCHEDULE
//
//                        //SCHEDULE INTERVAL NOTIFICATION FINDER
//                        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//                        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//                        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {
//
//
//
////                            Intent intentToPending = new Intent(getApplicationContext(), NotificationAlarmBroadcast.class);
////                            intentToPending.setAction(NotificationAlarmBroadcast.ACTION_ALARM);
////                            //intent.getExtras().getString("StudentID"),intent.getExtras().getString("notificationLastID") , intent.getExtras().getString("appcode")
////                            intentToPending.putExtra("StudentID",studentID);
////                            // intentToPending.putExtra("NoteLastID", intent.getExtras().getString("notificationLastID"));
////                            intentToPending.putExtra("appcode",appcode);
////                            //intentToPending.putExtra("MainIntance", mainActivity);
//
//                            alarmPIntent = PendingIntent.getBroadcast(getApplicationContext(),9961,intentToPending,0);
//                            alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//
//                            //set an interval
//                            Calendar triggerAt = Calendar.getInstance();
//                            //triggerAt.add(Calendar.SECOND, 2000);
//                            long triggerAtTimeInMillis  = triggerAt.getTimeInMillis();
//                            long intervalMillis = 1000;
//
//
//                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTimeInMillis, intervalMillis,alarmPIntent);
//                            // alarmManager.set(AlarmManager.RTC_WAKEUP,afterThirtyMinutes, alarmPIntent);
//                            //the notification's action will send the user to Transport activity, and the he or she calls the webservice async task
//
//                            //Toast.makeText(context, "SERVICE, alarm started", Toast.LENGTH_SHORT).show();
//
//                            // alarmManager.getNextAlarmClock().getTriggerTime();
//
//                        }
//
//                    }
//                }
//          }
//
//
//            super.onPostExecute(list);
//        }
//
//        @Override
//        protected List<String> doInBackground(Void... voids) {
//
//
//            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
//
//
//
//
//            List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//
//           // List<ultratude.com.mzizi.NotificationPG.NotificationModel.Notification> notificationList =  new NotificationDAO(context).getNotificationsList();
//
//            //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//            List<String> list = new ArrayList<>();
//            if(!authenticateUserResponse.isEmpty()){
//                list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
//                list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
//            }
//
//           // list.add(String.valueOf(authenticateUserResponse.size()));
//
//                return list;
//
//        }
//    }
//
//
//
//
//
//}
