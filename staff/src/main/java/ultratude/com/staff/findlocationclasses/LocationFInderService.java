package ultratude.com.staff.findlocationclasses;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


/**
 * Created by James on 20/10/2018.
 */

public class LocationFInderService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {

        //Toast.makeText(this, "TestActivity Started", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this, TestActivity.class);
        startActivity(i);

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {



        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }










}
