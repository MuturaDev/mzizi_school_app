package ultratude.com.mzizi.notificationpg.DisplayNotification;

//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

/**
 * Created by James on 04/09/2018.
 */

public class NotificationCountLiveData {


    private static NotificationCountViewModel model;
    private static Context mContext;



    public static void getViewModel(NotificationCountViewModel mModel, Context context){//should be called with the activitycreated method fo the fragment
        model = mModel;
        mContext = context;
    }


    public static void getNotificationCountUpdate(String notificationCount){//will be called in the background thread of the getnotificaitonFromMZIZIAPI, for periodic updates

       // Toast.makeText(mContext, "getNotificationCountUpdate: " + notificationCount, Toast.LENGTH_SHORT).show();
            //THIS IS THE ONLY THING I NEED IN THE BACK GROUD THREAD

                //set the updated data
                //post the updated instance of the List<String> because that is what we are observing
                //this is  needed
             //   model.getCurrentNotificationCount().postValue(notificationCount);//executing the method directly calls the onchanged callback

    }









}
