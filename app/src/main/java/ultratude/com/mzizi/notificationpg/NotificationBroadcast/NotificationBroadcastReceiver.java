package ultratude.com.mzizi.notificationpg.NotificationBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.modelclasses.MessageRequest;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;



/**
 * Created by James on 05/07/2018.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    String studentID, appcode;


    public NotificationBroadcastReceiver(){

    }


    public NotificationBroadcastReceiver(String studentID, String appcode) {
        this.studentID = studentID;
        this.appcode = appcode;
    }


    @Override
    public void onReceive(final Context context, Intent intent) {



        if (internetConnection(context)) {


            //you can now update the billing table
            //start

            //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                                    //request to PortalStudentInfo
            final Student student  = new Student(intent.getExtras().getString("StudentID"), intent.getExtras().getString("appcode"));

            Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
            userCall.enqueue(new Callback<SyncMyAccountResult>() {
                @Override
                public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {

                    SyncMyAccountResult result = response.body();

                    if (result == null) {

                    }else{

                       // Toast.makeText(context, "SyncMyAccount: " + result.toString(), Toast.LENGTH_LONG).show();
                        if(result.getPortalAccount() == "" || result.getPortalPaybill() == null){

                        }else{
                            //first delete the previous
                          //  Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
                            new SyncMyAccountDAO(context).deleteForSyncMyAccountResult(student.getStudentID());
                            result.setStudID(Integer.valueOf(student.getStudentID()));
                            new SyncMyAccountDAO(context).insertSyncMyAccountResult(result);
                        }

                    }




                    //end

                }

                @Override
                public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                   // Toast.makeText(context, "Check your intent connection!", Toast.LENGTH_SHORT).show();
                    call.cancel();

                }
            });


            ///end


            new LastIDNotification(context,studentID,appcode).execute();

        }
    }

    public boolean internetConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }


    private class LastIDNotification extends AsyncTask<Void, Void, List<String> > {

        String studentID, appcode;
        Context context;

        public LastIDNotification(Context context, String studentID, String appcode){
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



            if(list.size()==3){
                String studentID = list.get(0);
                String appcode = list.get(1);
                String notificationLastID = list.get(2);

                //  Toast.makeText(context, "LastID :" + notificationLastID, Toast.LENGTH_LONG).show();

               // Toast.makeText(context, "DataTransferReceiverBroadcast: " + notificationLastID , Toast.LENGTH_LONG).show();

                MessageRequest messageRequest = new MessageRequest(studentID, notificationLastID, appcode);
                Student student = new Student(studentID, appcode);


               // new GetNotificationFromMziziApi(context,student).SendRequest(messageRequest);
               // new GetPortalEventsFromMziziApi(context).SendRequest(student);




                //just for testing
                // Toast.makeText(context, mainActivityWeakReference.get().studentID, Toast.LENGTH_LONG).show();
            }else{


            }







            super.onPostExecute(list);
        }

        @Override
        protected List<String> doInBackground(Void... voids) {

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
            List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

            List<String> list = new ArrayList<>();

            if(authenticateUserResponse.size() > 0){

                List<ultratude.com.mzizi.notificationpg.NotificationModel.Notification> notificationList =  new NotificationDAO(context).getNotificationsList();

                //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
                list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode




                //check this array out of bounce
                if(notificationList.isEmpty()){

                    String.valueOf(new Notification().getMsgID());
                    list.add(String.valueOf(new Notification().getMsgID()));//2notificationid
                    return list;
                }else{

                    list.add(String.valueOf(notificationList.get(notificationList.size() - 1).getMsgID()));
                    return  list;
                }
            }


            return list;


        }
    }
}
