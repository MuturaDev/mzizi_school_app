package ultratude.com.mzizi.helperactivityclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;

import ultratude.com.mzizi.uiactivities.LoginActivity;

/**
 * Created by James on 01/07/2018.
 */

public class BroadCastReceiver extends BroadcastReceiver {

    WeakReference<LoginActivity> loginActivityWeakReference;


    public BroadCastReceiver(){

    }

    public BroadCastReceiver(LoginActivity activity) {
        this.loginActivityWeakReference = new WeakReference<>(activity);
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if(internetConnection(context)){


            loginActivityWeakReference.get().dialog(true);
        }else{
            loginActivityWeakReference.get().dialog(false);
        }



    }

    public boolean internetConnection(Context context){
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }



}
