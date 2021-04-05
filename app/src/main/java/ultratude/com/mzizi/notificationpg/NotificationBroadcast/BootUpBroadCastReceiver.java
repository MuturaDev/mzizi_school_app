package ultratude.com.mzizi.notificationpg.NotificationBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by James on 20/07/2018.
 */

public class BootUpBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

      //  Toast.makeText(context, "BOOT UP 1", Toast.LENGTH_LONG);

        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){

         //   Toast.makeText(context, "BOOT UP 2", Toast.LENGTH_LONG);

//            Intent pushIntent = new Intent(context, NotificationService.class);
//            context.startService(pushIntent);
           // new FirebaseJobDispatch(this.).startDispatch(student);



//            //play music
//            final MediaPlayer player = MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
//            player.start();



        }

        //The code below does not work
//        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
//
//           // Toast.makeText(context, "Boot up", Toast.LENGTH_LONG).show();
//
//            //set a notification to notify
//
//            //play music
//            final MediaPlayer player = MediaPlayer.create(context, SettingsFragment.System.DEFAULT_RINGTONE_URI);
//            player.start();
//
////            context.startService(new Intent(context, DataTransferService.class));
//        }


    }
}
