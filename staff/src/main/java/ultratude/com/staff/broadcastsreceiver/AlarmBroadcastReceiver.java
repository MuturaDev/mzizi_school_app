package ultratude.com.staff.broadcastsreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

import ultratude.com.staff.activities.DailyTransport;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;


public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_ALARM="ultratude.com.staff.ACTION_ALARM";
    public  SQLiteDatabase sqdb = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        //sqdb = new DatabaseConnectionOpenHelper(context).getSQLiteDb();
        Cursor cursor = new UltraDataDao(context).getAllUltradataCursor();

        if(ACTION_ALARM.equals(intent.getAction())){
            Uri sounduri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Intent toPIntent = new Intent(context, DailyTransport.class);
            PendingIntent pintent = PendingIntent.getActivity(context, 9961,toPIntent, 0);

            Notification notification = new Notification.Builder(context)
                    .setContentTitle("UltraBus")
                    .setContentText("Click to Send Data")
                    .setSmallIcon(R.drawable.bus_splash)
                    .setContentIntent(pintent)
                    .setSound(sounduri)
                    .build();
            NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            if(cursor.moveToNext()){
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
               v.vibrate(2000);
//                long[] pat = new long[]{200,300,500};
//                v.vibrate(pat,1);
                mManager.notify(9961, notification);
            }else{
                mManager.cancel(9961);
            }
        }
    }


}
