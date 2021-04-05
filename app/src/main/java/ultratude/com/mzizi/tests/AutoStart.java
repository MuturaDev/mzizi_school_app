package ultratude.com.mzizi.tests;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import ultratude.com.mzizi.helperactivityclasses.BroadCastReceiver;

public class AutoStart extends BroadCastReceiver {

    public void onReceive(Context context, Intent args){

        Intent intent = new Intent(context, ServiceTest.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
        Log.i("Autostart", "started");

    }

}
