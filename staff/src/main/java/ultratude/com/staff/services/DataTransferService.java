package ultratude.com.staff.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import android.util.Log;

import androidx.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ultratude.com.staff.displaynotifications.SendNotifications;
import ultratude.com.staff.webservice.DataAccessObjects.TripLatLongDAO;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.TripLatLong;


/**
 * Created by James on 20/07/2018.
 */

public class DataTransferService extends Service {


    DataTransferReceiverBroadcast broadcastReceiver;
    PendingIntent alarmPIntent;
    AlarmManager alarmManager;


    @Override
    public void onCreate() {

        broadcastReceiver = new DataTransferReceiverBroadcast();
        // broadcastReceiver = new DataTransferReceiverBroadcast(intent.getExtras().getString("StudentID"), intent.getExtras().getString("appcode"));
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        super.onCreate();


    }


    @Override
    public void onDestroy() {
       // Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        //unregister the broadcast
       unregisterNetworkBroadcastReceiver();
//        HomeScreen home = new HomeScreen();
//        home.unregisterNetworkBroadcastReceiver();


        //Cancel the alarm
        if (!(alarmManager == null)) {
            alarmManager.cancel(alarmPIntent);
            alarmManager = null;

        } else {
            alarmManager = null;
        }


        //  Toast.makeText(getApplicationContext(), "Service Stopped ", Toast.LENGTH_LONG).show();


        super.onDestroy();
    }


    //this helps alot, when the app is closed, bload cast are not fired
    private void unregisterNetworkBroadcastReceiver() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static int getTimeDifference(String dateStart, String dateStop){

        SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");

        int returnMinutesDifference = 0;


        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            //System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
            //System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            //System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
            returnMinutesDifference = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
            //System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnMinutesDifference;


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        //NOT SCHEDULE
        List<Object> tripLatLongObjectList = new TripLatLongDAO(this).getTripLatLong();
        List<TripLatLong> tripLatLongList = (List<TripLatLong>) tripLatLongObjectList.get(0);
        final int[] tripLatLongIds = (int[]) tripLatLongObjectList.get(1);


        int diff = 0;

        if(tripLatLongList.size() > 0){

            TripLatLong tripLatLong =  tripLatLongList.get(tripLatLongList.size() - 1);
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

            int minuteDiffernce = getTimeDifference( tripLatLong.getDateRecorded(),sdf.format(cal.getTime()));
            diff = minuteDiffernce;
            // Log.d(context.getPackageName().toUpperCase(), "Minute Difference: " + String.valueOf(minuteDiffernce));
            if(minuteDiffernce > 1){
                new SendNotifications(this).displayNotification("No internet connection to send data.", "Please enable your mobile data connection or wifi", "now");
            }
        }

        Log.d(this.getPackageName().toUpperCase(), "Checking for tracking data: " + String.valueOf(tripLatLongList.size()) + " Minute Diff: " + String.valueOf(diff));




        if(intent != null){
            Staff staff =  (Staff) intent.getExtras().getSerializable("STAFF");
            if(staff != null){
                //SCHEDULE INTERVAL NOTIFICATION FINDER
                final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


                    Intent intentToPending = new Intent(getApplicationContext(), DataTransferBroadcast.class);
                    intentToPending.setAction(DataTransferBroadcast.ACTION_ALARM);
                    // Toast.makeText(this, "DataTransferService: " + staff.toString(), Toast.LENGTH_LONG).show();

                    intentToPending.putExtra("StaffID", staff.getStaff_ID());
                    intentToPending.putExtra("AppCode", staff.getAppcode());
                    intentToPending.putExtra("OrganizationID", staff.getOrganizationID());
                    intentToPending.putExtra("SchoolID", staff.getSchoolID());

                    alarmPIntent = PendingIntent.getBroadcast(getApplicationContext(), 9961, intentToPending, 0);
                    alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

                    //set an interval
                    Calendar triggerAt = Calendar.getInstance();
                    //triggerAt.add(Calendar.SECOND, 2000);
                    long triggerAtTimeInMillis = triggerAt.getTimeInMillis();
                    long intervalMillis = 1000;


                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTimeInMillis, intervalMillis, alarmPIntent);


                }
            }
        }



        return START_STICKY;
    }


}