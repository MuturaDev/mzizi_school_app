package ultratude.com.mzizi.helperactivityclasses;

import android.os.AsyncTask;

/**
 * Created by James on 07/10/2018.
 */

public class UtilityClasses {


    //the inner classes will only be used to send request to test internet connection


    private class LoginActivityInternetConnection extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected Boolean doInBackground(Void... params) {




            return null;
        }
    }
}
