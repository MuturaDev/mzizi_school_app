package ultratude.com.mzizi.firebasejobdispatch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.webservice.APIRequest;


public class MziziJobService extends JobService {

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {

        Student student = new Student(job.getExtras().getString("StudentID"), job.getExtras().getString("appcode"));
        Log.d(this.getPackageName().toUpperCase(), "Job Service is Started: " + student.toString());
        startRequest(this.getApplicationContext(), job);


        return false;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        Log.d(this.getPackageName().toUpperCase(), "Job Service is Stopped");
        return false;
    }


    private void startRequest(final Context context, final JobParameters job) {
        if (internetConnection(context)) {
            Student student = new Student(job.getExtras().getString("StudentID"), job.getExtras().getString("appcode"));
            new LastIDNotification(context, student.getStudentID(), student.getAppcode()).execute();

        }
    }


    private boolean internetConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }

    private class LastIDNotification extends AsyncTask<Void, Void, List<String>> {

        String studentID, appcode;
        Context context;

        public LastIDNotification(Context context, String studentID, String appcode) {
            this.studentID = studentID;
            this.appcode = appcode;
            this.context = context;
        }


        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> list) {
//
            if (list.size() == 4) {
                String studentID = list.get(0);
                String appcode = list.get(1);
                String organization = list.get(2);
                String notificationLastID = list.get(3);

                Student student = new Student(studentID, appcode);
                student.setOrganizationID(organization);

                RequestFor.firebaseJobDispatch = true;
                RequestFor.sendRequest(student, context, notificationLastID, APIRequest.ALL);


            }

            //just for testing
            // Toast.makeText(context, mainActivityWeakReference.get().studentID, Toast.LENGTH_LONG).show();


            super.onPostExecute(list);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);

            List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();


            List<Notification> notificationList = new NotificationDAO(context).getNotificationsList();

            //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

            List<String> list = new ArrayList<>();
            if (authenticateUserResponse.size() > 0) {
                list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
                list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
                list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
            }


            //check this array out of bounce
            if (notificationList.isEmpty()) {

                String.valueOf(new Notification().getMsgID());
                list.add(String.valueOf(new Notification().getMsgID()));//3notificationid
                return list;
            } else {

                list.add(String.valueOf(notificationList.get(notificationList.size() - 1).getMsgID()));
                return list;
            }
        }
    }
}





