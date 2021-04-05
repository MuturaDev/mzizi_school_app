//package ultratude.com.mzizi.notificationpg.NotificationBroadcast;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.util.Log;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.paperdb.Paper;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import ultratude.com.mzizi.modelclasses.ParentChatRequest;
//import ultratude.com.mzizi.modelclasses.StudentVisualization;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.GlobalSettingsDAO;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.ParentChatDAO;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
//import ultratude.com.mzizi.modelclasses.GlobalSettingsRequest;
//import ultratude.com.mzizi.modelclasses.MessageRequest;
//import ultratude.com.mzizi.modelclasses.NewCarriculumExam;
//import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
//import ultratude.com.mzizi.modelclasses.NotificationReadTracking;
//import ultratude.com.mzizi.modelclasses.NotificationReadTrackingDAO;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
//import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentUnits;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationAverageResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
//import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
//import ultratude.com.mzizi.webservice.apiconnections.GetNotificationFromMziziApi;
//import ultratude.com.mzizi.webservice.apiconnections.GetPortalEventsFromMziziApi;
//import ultratude.com.staff.BuildConfig;
//import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;
//import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControlRequest;
//import ultratude.com.staff.webservice.RequestModels.PortalGetAttendanceRequest;
//
//import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;
//
///**
// * Created by James on 20/07/2018.
// */
//
//public class NotificationAlarmBroadcast extends BroadcastReceiver {
//
//    public static final String ACTION_ALARM="com.jamiecode.ACTION_ALARM";
//
//
//    public NotificationAlarmBroadcast(){
//
//    }
//
//
//    @Override
//    public void onReceive(final Context context, Intent intent) {
//
//      // MessageRequest messageRequest =  new MessageRequest(intent.getExtras().getString("StudentID"),intent.getExtras().getString("NoteLastID"),intent.getExtras().getString("appcode"));
//        if(ACTION_ALARM.equals(intent.getAction())) {
//            if (internetConnection(context)) {
//
//                //you can now update the billing table
//                //start
//
//                Log.d(context.getPackageName().toUpperCase().toString(), "Service Running");
//
//                //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
//                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//                Paper.init(context);
////                                    //request to PortalStudentInfo
//                Student student  = new Student(intent.getExtras().getString("StudentID"), intent.getExtras().getString("appcode"));
//
//                Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
//                userCall.enqueue(new Callback<SyncMyAccountResult>() {
//                    @Override
//                    public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {
//
//                        SyncMyAccountResult result = response.body();
//
//                        if(result == null){
//
//                        }else{
//                           // Toast.makeText(context, "SyncMyAccount: " + result.toString(), Toast.LENGTH_LONG).show();
//
//                            if(result.getPortalAccount() == "" || result.getPortalPaybill() == null){
//
//                            }else{
//                                //first delete the previous
//                              //  Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
//                                new SyncMyAccountDAO(context).deleteAllSyncMyAccountResult();
//                                new SyncMyAccountDAO(context).insertSyncMyAccountResult(result);
//                            }
//
//
//                        }
//
//
//
//
//                            //end
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {
//
//                       // Toast.makeText(context,"Check your internet connection!", Toast.LENGTH_SHORT).show();
//                        call.cancel();
//
//                    }
//                });
//
//               // Toast.makeText(context, "After loggingout from SyncMyAccount", Toast.LENGTH_LONG).show();
//
//
//                ///end
//
//                //
//               new LastIDNotification(context,student.getStudentID(),student.getAppcode()).execute();
//
//            }
//
//
//        }
//    }
//
//
//    public boolean internetConnection(Context context) {
//        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {
//
//
//            return true;//connected
//
//        }
//        return false;
//    }
//
//
//    private class LastIDNotification extends AsyncTask<Void, Void, List<String>> {
//
//        String studentID, appcode;
//        Context context;
//
//        public LastIDNotification(Context context, String studentID, String appcode){
//            this.studentID = studentID;
//            this.appcode = appcode;
//            this.context = context;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//
//
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(List<String> list) {
////
//            if(list.size()==4){
//                String studentID = list.get(0);
//                String appcode = list.get(1);
//                String organization = list.get(2);
//                String notificationLastID = list.get(3);
//
//
//              //  Toast.makeText(context, "NotificationAlarmBroadcast: " +notificationLastID  , Toast.LENGTH_LONG).show();
//                //Toast.makeText(context, "AlarmBroadcast: " +String.valueOf(list.size())  , Toast.LENGTH_LONG).show();
////System.exit(1);
//
//                //
//                //  Toast.makeText(context, notificationLastID, Toast.LENGTH_LONG).show();
//
//
//                int versionCode = BuildConfig.VERSION_CODE;
//                String versionName = BuildConfig.VERSION_NAME;
//                MziziAppVersionControlRequest request = new MziziAppVersionControlRequest(versionName, versionCode);
//               // sendRequestMziziAppVersionControl(context,request);
//                sendRequestMziziAppVersionControl(context, request);
//                MessageRequest messageRequest = new MessageRequest(studentID, notificationLastID, appcode);
//                Student student = new Student(studentID, appcode);
//                new GetNotificationFromMziziApi(context, student).SendRequest(messageRequest);
//                new GetPortalEventsFromMziziApi(context).SendRequest(student);
//
//                sendRequestForGlobalSettings(student, context,organization, Constants.CHAT_ENABLED);
//                sendRequestForGlobalSettings(student, context,organization, Constants.OPTIONAL_FEE_ENABLED);
//                sendRequestForGlobalSettings(student, context,organization, Constants.TRANSPORT_FEE_ENABLED);
//                sendRequestForGlobalSettings(student, context,organization, Constants.BIO_ACCESS_ENABLED);
//
//                sendRequestForParentChat(student,context,organization);
//                sendRequestForStudentInfo(student,context);
//                sendRequestForNotificationReadTracking(student,context);
//                sendRequestForPortalProgressReport(student, context);
//                sendRequestForStudentAttendanceDetils(student, context,organization);
//                sendRequestForPortalRecentTransactionResponseDetails(student, context);
//                sendRequestForDetailedToDoList(student, context);
//                sendRequestForOptionalFeesDetails(student,context);
//                sendRequestForPortalStudentResultsExtendedDetails(student,context);
//                sendRequestForStudentVisualizationAverageDetails(student,context);
//                sendRequestForStudentVisualizationDetails(student,context);
//                sendRequestForToDoList(student,context);
//                sendRequestPortalTransportRoutesDetails(student,context);
//                sendRequestPortalStudentUnitDetails(student, context);
//
//            }else{
//
//
//            }
//
//
//            //just for testing
//            // Toast.makeText(context, mainActivityWeakReference.get().studentID, Toast.LENGTH_LONG).show();
//
//
//            super.onPostExecute(list);
//        }
//
//        @Override
//        protected List<String> doInBackground(Void... voids) {
//
//            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
//
//            List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//
//            List<ultratude.com.mzizi.notificationpg.NotificationModel.Notification> notificationList =  new NotificationDAO(context).getNotificationsList();
//
//            //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//            List<String> list = new ArrayList<>();
//            if(authenticateUserResponse.size() > 0){
//                list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
//                list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
//                list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
//            }
//
//
//
//
//
//            //check this array out of bounce
//            if(notificationList.isEmpty()){
//
//                String.valueOf(new Notification().getMsgID());
//                list.add(String.valueOf(new Notification().getMsgID()));//3notificationid
//                return list;
//            }else{
//
//                list.add(String.valueOf(notificationList.get(notificationList.size() - 1).getMsgID()));
//                return  list;
//            }
//        }
//    }
//
//
//
//
//    private void sendRequestForParentChat(Student student, final Context context, String organizationID){
//
//        //TODO: You added enquiryTypeID
//        ParentChatRequest parentChat = new ParentChatRequest(
//                student.getStudentID(),//"24528",
//                "",
//                "0",
//                student.getAppcode(),
//                "1"
//        );
//
//
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<ParentChat>> userCall = apiInterface.sendParentChat(parentChat);
//            userCall.enqueue(new Callback<List<ParentChat>>() {
//                @Override
//                public void onResponse(Call<List<ParentChat>> call, final Response<List<ParentChat>> response) {
//                    if(response.code() == 200){
//                       AsyncTask asyncTask = new AsyncTask() {
//                           @Override
//                           protected Object doInBackground(Object[] params) {
//                           ParentChatDAO parentChatDAO = ParentMziziDatabase.getInstance(context.getApplicationContext()).getParentChatDAO();
//                               parentChatDAO.deleteParentChat();
//                               parentChatDAO.insertParentChat(response.body());
//                               return null;
//                           }
//                       };
//                       asyncTask.execute();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<ParentChat>> call, Throwable t) {
//
//                }
//            });
//
//    }
//
//
//
//
//}
//
