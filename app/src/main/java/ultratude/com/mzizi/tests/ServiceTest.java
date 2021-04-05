package ultratude.com.mzizi.tests;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import ultratude.com.mzizi.uiactivities.BootUpTest;

public class ServiceTest extends Service {

    private static final String TAG = "MyService";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onDestroy() {
//        Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
//        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onStart(Intent intent, int startid)
    {
//        Intent intents = new Intent(getBaseContext(), BootUpTest.class);
//        intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intents);
//        Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
//        Log.d(TAG, "onStart");
    }

}

