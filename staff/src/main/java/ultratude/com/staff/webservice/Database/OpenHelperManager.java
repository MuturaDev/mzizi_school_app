package ultratude.com.staff.webservice.Database;

import android.annotation.SuppressLint;

/**
 * Created by James on 06/05/2019.
 */

public class OpenHelperManager {
    @SuppressLint("StaticFieldLeak")
    private static boolean isClosed = false;
    private static int instanceCount = 0;


    public static synchronized  void releaseHelper(DatabaseConnectionOpenHelper openHelper){

        instanceCount--;
        //String.format("releasing helper %s, instance count = %s", helper, instanceCount)
        if(instanceCount <=0){
            if(openHelper != null){
                openHelper.close();
                isClosed = true;
            }
            if(instanceCount <0){
                //String.format("too many calls to release helper, instance count = %s", instanceCount)
            }
        }

    }

    public static synchronized  void requireConnection(){
        isClosed = false;
        instanceCount++;
    }

    public static boolean isIsClosed(){
        return isClosed;
    }

}
