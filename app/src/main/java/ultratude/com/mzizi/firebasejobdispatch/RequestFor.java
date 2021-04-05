package ultratude.com.mzizi.firebasejobdispatch;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.BuildConfig;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.modelclasses.GlobalSettingsRequest;
import ultratude.com.mzizi.modelclasses.MessageRequest;
import ultratude.com.mzizi.modelclasses.NewCarriculumExam;
import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.modelclasses.NotificationReadTracking;
import ultratude.com.mzizi.modelclasses.NotificationReadTrackingDAO;
import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.modelclasses.ParentChatRequest;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotification;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotificationDAO;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.modelclasses.StudentVisualization;
import ultratude.com.mzizi.modelclasses.response.BorrowedBooksResponse;
import ultratude.com.mzizi.modelclasses.response.OrderItemResponse;
import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.BorrowedBooksResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.GlobalSettingsDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.OrderItemResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.ParentChatDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalDetailedToDoListResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalOptionalFeesResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalRecentTransactionResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentResultsExtendedDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentUnitsDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentVisualizationAverageResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentVisualizationResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalToDoListResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalTransportRoutesResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.StudentClassAttendanceDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.YoutubeVideoGalleryResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentUnits;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationAverageResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.uiactivities.FragTransaction;
import ultratude.com.mzizi.uiactivities.HomeFrag;
import ultratude.com.mzizi.uiactivities.LoginActivity;
import ultratude.com.mzizi.uiactivities.MainActivity;

import ultratude.com.mzizi.uiactivities.SyncMyAccount;
import ultratude.com.mzizi.webservice.APIRequest;

import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControlRequest;
import ultratude.com.staff.webservice.RequestModels.PortalGetAttendanceRequest;

import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;

public class RequestFor {

    public static boolean refresh = false;
    public static boolean login = false;
    public static boolean firebaseJobDispatch = false;
    public static WeakReference<Object> weakReference;




    public static void sendRequest(final Student student, final Context context, final String notificationLastID,final APIRequest apiRequest){

        Paper.init(context);



//        if(APIRequest.ALL == apiRequest){
//
//            //Set alarm
//            //new EventsToNotifyAbout(MainActivity.this).checkForEventsToNotify();
////                EventsToNotifyAbout notifyAbout =  new EventsToNotifyAbout(context);
////                notifyAbout.checkForEventsToNotify();
//
//        }


        new AsyncTask(){
            @Override
            protected void onPostExecute(Object o) {

                if(o == null){
                    RequestFor.sendRequest(student,context,"",APIRequest.PORTAL_SIBLINGS);
                }else{

                    //there are two versions of BuildConfig, staff and parent
                    int versionCode = BuildConfig.VERSION_CODE;
                    String versionName = BuildConfig.VERSION_NAME;

                    String organization = student.getOrganizationID();

                    if(APIRequest.STUDENT_INFO == apiRequest || APIRequest.ALL == apiRequest || APIRequest.FCM_MUST == apiRequest){
                        sendRequestForStudentInfo(student,context);
                    }

                    if(APIRequest.RECENT_RESULTS_8_4_4 == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForPortalStudentResultsExtendedDetails(student,context);
                    }

                    if(APIRequest.TIME_TABLE == apiRequest || APIRequest.ALL == apiRequest || APIRequest.FCM_MUST == apiRequest){
                        sendRequestforTimeTable(student,context);
                    }

                    //receipt/credit /transaction or SMS Receipt/Receipt/Credit/Balance/balance
                    if(APIRequest.RECENT_TRANSACTION == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForPortalRecentTransactionResponseDetails(student, context);
                    }

                    if(APIRequest.OPTIONAL_FEE == apiRequest || APIRequest.ALL == apiRequest){
                        //sendRequestForOptionalFeesDetails(student,context);
                    }

                    if(APIRequest.RESULTS_2_6_6_3 == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForPortalProgressReport(student, context);
                    }

                    //Results/results/term/Term
                    if(APIRequest.RESULTS_8_4_4 == apiRequest || APIRequest.ALL == apiRequest){
                        setRequestForPortalResults(student, context);
                    }

                    //chat/replied
                    if(APIRequest.PORTAL_CHAT == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForParentChat(student,context,organization);
                    }

                    if(APIRequest.NOTIFICATION == apiRequest || APIRequest.ALL == apiRequest || APIRequest.FCM_MUST == apiRequest){

                        //new GetNotificationFromMziziApi(context, student).SendRequest(messageRequest);
                        new AsyncTask(){
                            @Override
                            protected void onPostExecute(Object o) {
                                String lastNotificationID = String.valueOf(o);
                                MessageRequest messageRequest = new MessageRequest(student.getStudentID(), lastNotificationID, student.getAppcode());

                                //testing


                                sendRequestForNotification(student,context,messageRequest);
                                super.onPostExecute(o);
                            }

                            @Override
                            protected Object doInBackground(Object[] objects) {
                                return  new NotificationDAO(context).getNotificationLastID(student.getStudentID());

                            }
                        }.execute();


                    }

                    //event/Event
                    if(APIRequest.EVENTS == apiRequest || APIRequest.ALL == apiRequest){
                        //new GetPortalEventsFromMziziApi(context).SendRequest(student);
                        sendRequestForUpcomingEvents(student, context);
                    }
                    if(APIRequest.CHATS_GS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForGlobalSettings(student, context,organization, Constants.CHAT_ENABLED);
                    }

                    if(APIRequest.OPTIONAL_FEE_GS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForGlobalSettings(student, context,organization, Constants.OPTIONAL_FEE_ENABLED);
                    }

                    if(APIRequest.TRANSPORT_ROUTE_GS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForGlobalSettings(student, context,organization, Constants.TRANSPORT_FEE_ENABLED);
                    }

                    if(APIRequest.BIO_ACCESS_GS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForGlobalSettings(student, context,organization, Constants.BIO_ACCESS_ENABLED);
                    }

                    if(APIRequest.PORTAL_URL_GS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForGlobalSettings(student, context, organization, Constants.PORTAL_URL_ENABLED);
                    }

                    if(APIRequest.SYNC_MY_ACCOUNT == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForSyncMyAccount(student, context, organization);
                    }



                    if(APIRequest.NOTIFICATION_READ_TRACKING == apiRequest || APIRequest.ALL == apiRequest|| APIRequest.FCM_MUST == apiRequest){
                        // TODO: 2020-06-11 Need to be advanced enough to note a student or parent has received a notificaiton
                        sendRequestForNotificationReadTracking(student,context);
                    }

                    if(APIRequest.STUDENT_ATTENDANCE == apiRequest || APIRequest.ALL == apiRequest|| APIRequest.FCM_MUST == apiRequest){
                        sendRequestForStudentAttendanceDetils(student, context,organization);
                    }

                    if(APIRequest.TRANSACTION == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForTransactions(student, context);
                    }

                    //diary
                    if(APIRequest.DETAILED_TODO == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForDetailedToDoList(student, context);
                    }

                    //diary
                    if(APIRequest.TODO == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForToDoList(student,context);
                    }



                    if(APIRequest.STUDENT_VISUALIZATION == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForStudentVisualizationDetails(student,context);
                    }

                    if(APIRequest.STUDENT_VISUALIZATION_AVERAGE == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForStudentVisualizationAverageDetails(student,context);
                    }

                    if(APIRequest.VIDEO_GALLERY == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForPortalYoutubeVideoGallery(student,context);
                    }

                    if(APIRequest.PHOTO_GALLERY == apiRequest || APIRequest.ALL == apiRequest){

                    }

                    if(APIRequest.MZIZI_APP_VERSION == apiRequest || APIRequest.ALL == apiRequest || APIRequest.FCM_MUST == apiRequest){
                       MziziAppVersionControlRequest request = new MziziAppVersionControlRequest(versionName, versionCode);
                        sendRequestMziziAppVersionControl(context, request);
                    }


                    if(APIRequest.TRANSPORT_ROUTES == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestPortalTransportRoutesDetails(student,context);
                    }

                    if(APIRequest.STUDENT_UNITS == apiRequest || APIRequest.ALL == apiRequest){
                        // sendRequestPortalStudentUnitDetails(student, context);
                    }

                    if(APIRequest.PORTAL_BOOK_CENTRE == apiRequest || APIRequest.ALL == apiRequest){
                        // sendRequestForPortalBookCentre(student,context);
                    }

                    if(APIRequest.PORTAL_BORROWED_BOOKS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForPortalBorrowedBooks(student,context);
                    }

                    if(APIRequest.PORTAL_ORDER_ITEMS == apiRequest || APIRequest.ALL == apiRequest){
                         sendRequestForPortalOrderItems(student,context);
                    }

                    if(APIRequest.PORTAL_SCHOOL_TRIP == apiRequest || APIRequest.ALL == apiRequest){
                         // sendRequestForPortalSchoolTrip(student, context);
                    }

                    if(APIRequest.SEND_TEST_REQUEST == apiRequest || APIRequest.ALL == apiRequest){
                        // sendTestRequest(student, context);
                    }

                    if(APIRequest.PORTAL_SIBLINGS == apiRequest || APIRequest.ALL == apiRequest){
                        sendRequestForPortalSibling(student,context);
                    }

                    if(APIRequest.PORTAL_SCHOOL_CONTACTS == apiRequest || APIRequest.ALL == apiRequest){
                        // TODO: 2020-06-29 has issues with the primary key
                        //https://stackoverflow.com/questions/40483827/how-to-set-start-value-for-a-column-which-is-autoincrement-in-android-sqlite
                        sendRequestForPortalContacts(student,context);
                    }

                    if(APIRequest.REGISTER_FCM_TOKEN == apiRequest || APIRequest.ALL == apiRequest){
                        if(firebaseJobDispatch){
                            firebaseJobDispatch = false;
                            sendRequestForStudentFCMDeviceToken(student, context);
                        }


                    }
                }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return ParentMziziDatabase.getInstance(context).getPortalSiblingsDao().getSchoolIDFromPortalSibling(student.getStudentID());
            }
        }.execute();


    }



    private static void sendRequestForSyncMyAccount(final Student student,final Context context, final String OrganizationID){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
        Paper.init(context);
//                                    //request to PortalStudentInfo
      //  Student student = new Student(job.getExtras().getString("StudentID"), job.getExtras().getString("appcode"));

        Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
        userCall.enqueue(new Callback<SyncMyAccountResult>() {
            @Override
            public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {

                SyncMyAccountResult result = response.body();

                if (result == null) {

                } else {
                    // Toast.makeText(context, "SyncMyAccount: " + result.toString(), Toast.LENGTH_LONG).show();

                    if (result.getPortalAccount() == "" || result.getPortalPaybill() == null) {

                    } else {
                        //first delete the previous
                        //  Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
                        SyncMyAccountDAO dao = new SyncMyAccountDAO(context);
                        dao.deleteForSyncMyAccountResult(student.getStudentID());
                        result.setStudID(Integer.valueOf(student.getStudentID()));
                        dao.insertSyncMyAccountResult(result);
                    }

                }
                //end
            }

            @Override
            public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                // Toast.makeText(context,"Check your internet connection!", Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        });
    }




    private static void sendRequestMziziAppVersionControl(final Context mContext,MziziAppVersionControlRequest request){
        ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface2 = ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient.getClient().create(ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface.class);//used to instantiate the APIClient.
        request.setAppModuleName("PARENT_STUDENT");
        Call<MziziAppVersionControl> userCall = apiInterface2.forceMziziAppUpdate(request);
        userCall.enqueue(new Callback<MziziAppVersionControl>() {
                             @Override
                             public void onResponse(Call<MziziAppVersionControl> call, Response<MziziAppVersionControl> response) {
                                 if(response.isSuccessful()){

                                     MziziAppVersionControl responseMziziApp = response.body();

                                     if(Paper.book().contains(PAPER_MZIZIVERSIONCONTROL)){
                                         //Paper.book().delete(PAPER_MZIZIVERSIONCONTROL);
                                         Paper.book().write(PAPER_MZIZIVERSIONCONTROL, responseMziziApp);
                                        // Log.d(mContext.getPackageName().toUpperCase(), "UPDATE: " + Paper.book().read(PAPER_MZIZIVERSIONCONTROL, new MziziAppVersionControl()).toString());
                                     }else{
                                         Paper.book().write(PAPER_MZIZIVERSIONCONTROL, responseMziziApp);
                                        // Log.d(mContext.getPackageName().toUpperCase(), "CREATE: " + Paper.book().read(PAPER_MZIZIVERSIONCONTROL, new MziziAppVersionControl()).toString());

                                     }
                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     // long id =  new VehicleDAO(mContext).saveVehicle(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<MziziAppVersionControl> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );
    }




    private static void sendRequestForStudentAttendanceDetils(final Student student, final Context mContext, String organization){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        final PortalGetAttendanceRequest portalGetAttendanceRequest = new PortalGetAttendanceRequest(
                student.getStudentID(),
                "0",
                "0",
                organization,
                student.getAppcode()
//                "24454",
//                "0",
//                "0",
//                "49",
//                "1000"
        );


        Call<List<StudentClassAttendance>> userCall = apiInterface.getStudentClassAttendance(portalGetAttendanceRequest);
        userCall.enqueue(new Callback<List<StudentClassAttendance>>() {
                             @Override
                             public void onResponse(Call<List<StudentClassAttendance>> call, final Response<List<StudentClassAttendance>> response) {
                                 if(response.isSuccessful()){
                                     if(response.code() == 200){


                                         //save to database
                                         AsyncTask asyncTask = new AsyncTask() {
                                             @Override
                                             protected Object doInBackground(Object[] params) {
                                                 for(StudentClassAttendance res : response.body()){
                                                     res.setStudID(Integer.valueOf(student.getStudentID()));
                                                 }
                                                 ParentMziziDatabase db = ParentMziziDatabase.getInstance(mContext);
                                                 StudentClassAttendanceDAO dao = db.getStudentClassAttendanceDAO();
                                                 dao.deleteStudentClassAttendance(Integer.valueOf(student.getStudentID()));
                                                 dao.insertStudentClassAttendance(response.body());

                                                 return null;
                                             }
                                         };
                                         asyncTask.execute();
                                     }
                                 }
                             }

                             @Override
                             public void onFailure(Call<List<StudentClassAttendance>> call, Throwable t) {

                             }
                         }
        );
    }
    private static void sendRequestForPortalProgressReport(final Student student, final Context mContext){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<NewCarriculumExam>> userCall = apiInterface.getPortalProgressReport(student);
        userCall.enqueue(new Callback<List<NewCarriculumExam>>() {
            @Override
            public void onResponse(Call<List<NewCarriculumExam>> call, final Response<List<NewCarriculumExam>> response) {

                if (response.code() == 200) {

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(NewCarriculumExam res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            new NewCarriculumExamDAO(mContext).insertNewCarriculumExamFormat(response.body());

                            return null;
                        }
                    };
                    asyncTask.execute();

                }
            }

            @Override
            public void onFailure(Call<List<NewCarriculumExam>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    //TODO: TO HAVE SEVERAL OF THIS TO CAPTURE ALL THE GLOBAL SETTINGS REQUIRED
    private static void sendRequestForGlobalSettings(final Student student, final Context context, String organizationID, final String settingName){
        final GlobalSettingsRequest globalSettingsRequest = new GlobalSettingsRequest(
                settingName,
                organizationID,
                student.getAppcode()
        );

        //  Toast.makeText(context, "OrganizationID: " + organizationID , Toast.LENGTH_SHORT).show();


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<GlobalSettings> userCall = apiInterface.getGlobalSettings(globalSettingsRequest);
        userCall.enqueue(new Callback<GlobalSettings>() {
            @Override
            public void onResponse(Call<GlobalSettings> call, final Response<GlobalSettings> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                           // Log.d(context.getPackageName().toUpperCase(), "Request Global SettingsFragment: " + response.body().toString());

                            response.body().setStudID(Integer.valueOf(student.getStudentID()));
                            ParentMziziDatabase db =ParentMziziDatabase.getInstance(context.getApplicationContext());
                            GlobalSettingsDAO parentChatDAO = db.getGlobalSettingsDAO();
                            parentChatDAO.deleteGlobalSetting(settingName,Integer.valueOf(student.getStudentID()));
                            GlobalSettings settings = response.body();

                            settings.setGlobalSettingName(settingName);
                            parentChatDAO.insertGlobalSettings(response.body());

                            if(settingName.equals(Constants.PORTAL_URL_ENABLED)){
                                Paper.book().write(Constants.PORTAL_URL_ENABLED, settings.getGlobalSettingsValue());
                            }

                            return null;
                        }
                    };
                    asyncTask.execute();
                }
            }

            @Override
            public void onFailure(Call<GlobalSettings> call, Throwable t) {

            }
        });

    }
    private static void sendRequestForNotificationReadTracking(final Student student, final Context context){

        final List<Object> noteReadTracking =  new NotificationReadTrackingDAO(context).getNotificationReadTracking();
        if(noteReadTracking.size() > 0) {
            List<NotificationReadTracking> notificationReadTrackingList = (List<NotificationReadTracking>) noteReadTracking.get(0);
            for (NotificationReadTracking note : notificationReadTrackingList) {
                note.setAppCode(student.getAppcode());
            }
            final int[] Ids = (int[]) noteReadTracking.get(1);

            if (notificationReadTrackingList.size() > 0) {
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

                Call<String> userCall = apiInterface.getNotificationReadTracking(notificationReadTrackingList);
                userCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200) {
                            if (response.body().equals("true"))
                                new NotificationReadTrackingDAO(context).deleteOneNotificationReadTracking(Ids, student.getStudentID());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        }
    }
    private static void sendRequestForStudentInfo(final Student student, final Context context){

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<PortalStudentInfo> userCall = apiInterface.getStudentInfo(student);
        userCall.enqueue(new Callback<PortalStudentInfo>() {
            @Override
            public void onResponse(Call<PortalStudentInfo> call, final Response<PortalStudentInfo> response) {

                //  Toast.makeText(context, String.valueOf(response.code() == 200 ) + "  " + portalStudentInfo.toString() , Toast.LENGTH_LONG).show();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getStudentFullName() != "") {

                            try {
                                AsyncTask asyncTask = new AsyncTask() {
                                    @Override
                                    protected Object doInBackground(Object[] objects) {
                                        response.body().setStudID(Integer.valueOf(student.getStudentID()));
                                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);

                                        db.getPortalStudentInfoDao().deleteStudent(Integer.valueOf(student.getStudentID()));

                                        List<PortalStudentInfo> portalStudentInfoList = db.getPortalStudentInfoDao().checkIfStudentWithThisRegistrationNumberExist(response.body().getRegistrationNumber());
                                        if(portalStudentInfoList.size() > 0){
                                            for(PortalStudentInfo info : portalStudentInfoList){
                                                if(info.getRegistrationNumber().equalsIgnoreCase(response.body().getRegistrationNumber())){
                                                    db.getPortalStudentInfoDao().deleteAllStudents();
                                                }
                                            }
                                        }
                                        db.getPortalStudentInfoDao().insertStudent(response.body());
                                        return null;
                                    }
                                };
                                asyncTask.execute();

                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }


                    }
                }

            }

            @Override
            public void onFailure(Call<PortalStudentInfo> call, Throwable t) {
                // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });

    }
    private static void sendRequestForPortalRecentTransactionResponseDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalRecentTransactionResponse>> userCall = apiInterface.getRecentTransaction(student);
        userCall.enqueue(new Callback<List<PortalRecentTransactionResponse>>() {
            @Override
            public void onResponse(Call<List<PortalRecentTransactionResponse>> call, final Response<List<PortalRecentTransactionResponse>> response) {

                //FIXME: NULL POINTER
                //Log.d(context.getPackageName().toUpperCase(), "Recent Transaction: " + resultsList.toString());

                if (response.code() == 200) {
                   // Log.d(context.getPackageName().toUpperCase(), resultsList.toString());

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalRecentTransactionResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }

                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalRecentTransactionResponseDAO dao = db.getPortalRecentTransactionResponseDAO();
                            dao.deletePortalRecentTransactionResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalRecentTransactionResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();



                }

            }

            @Override
            public void onFailure(Call<List<PortalRecentTransactionResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void  sendRequestForDetailedToDoList(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalDetailedToDoListResponse>> userCall = apiInterface.getDetailedToDoList(student);
        userCall.enqueue(new Callback<List<PortalDetailedToDoListResponse>>() {
            @Override
            public void onResponse(Call<List<PortalDetailedToDoListResponse>> call, final Response<List<PortalDetailedToDoListResponse>> response) {



                if (response.code() == 200) {
                   // Log.d(context.getPackageName().toUpperCase(), resultsList.toString());
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalDetailedToDoListResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalDetailedToDoListResponseDAO dao = db.getPortalDetailedToDoListResponseDAO();
                            dao.deletePortalDetailedToDoListResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalDetailedToDoListResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();


                }


            }

            @Override
            public void onFailure(Call<List<PortalDetailedToDoListResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void  sendRequestForOptionalFeesDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalOptionalFeesResponse>> userCall = apiInterface.getOptionalFees(student);
        userCall.enqueue(new Callback<List<PortalOptionalFeesResponse>>() {
            @Override
            public void onResponse(Call<List<PortalOptionalFeesResponse>> call, final Response<List<PortalOptionalFeesResponse>> response) {



                if (response.code() == 200) {
                  //  Log.d(context.getPackageName().toUpperCase(), resultsList.toString());
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalOptionalFeesResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalOptionalFeesResponseDAO dao = db.getPortalOptionalFeesResponseDAO();
                            dao.deletePortalOptionalFeesResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalOptionalFeesResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();


                }

            }

            @Override
            public void onFailure(Call<List<PortalOptionalFeesResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void   sendRequestForPortalStudentResultsExtendedDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalStudentResultsExtended>> userCall = apiInterface.getStudentResultsExtended(student);
        userCall.enqueue(new Callback<List<PortalStudentResultsExtended>>() {
            @Override
            public void onResponse(Call<List<PortalStudentResultsExtended>> call, final Response<List<PortalStudentResultsExtended>> response) {



               // Log.d(context.getPackageName().toUpperCase(), "Request For Extended Results: " +  resultsList.size());

                if (response.code() == 200) {

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalStudentResultsExtended res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalStudentResultsExtendedDAO dao = db.getPortalStudentResultsExtendedDAO();
                            dao.deletePortalStudentResultsExtended(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalStudentResultsExtended(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();


                }


            }

            @Override
            public void onFailure(Call<List<PortalStudentResultsExtended>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
               // call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }




    private static void  sendRequestForStudentVisualizationAverageDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        //  StudentVisualization visualization = new StudentVisualization(student.getStudentID(),"0", student.getAppcode());

        Call<List<PortalStudentVisualizationAverageResponse>> userCall = apiInterface.getStudentVisualizationAverage(student);
        userCall.enqueue(new Callback<List<PortalStudentVisualizationAverageResponse>>() {
            @Override
            public void onResponse(Call<List<PortalStudentVisualizationAverageResponse>> call, final Response<List<PortalStudentVisualizationAverageResponse>> response) {


                if (response.code() == 200) {

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalStudentVisualizationAverageResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalStudentVisualizationAverageResponseDAO dao = db.getPortalStudentVisualizationAverageResponseDAO();
                            dao.deletePortalStudentVisualizationAverageResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalStudentVisualizationAverageResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();
                  //  Log.d(context.getPackageName().toUpperCase(), "VisualizationAverageReponse" + resultsList.toString());


                }

            }

            @Override
            public void onFailure(Call<List<PortalStudentVisualizationAverageResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void  sendRequestForStudentVisualizationDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
        StudentVisualization visualization = new StudentVisualization(student.getStudentID(),"0", student.getAppcode());

       // Log.d(context.getPackageName().toUpperCase(), "VisualizationResponse Request " + visualization.toString());

        Call<List<PortalStudentVisualizationResponse>> userCall = apiInterface.getStudentVisualization(visualization);
        userCall.enqueue(new Callback<List<PortalStudentVisualizationResponse>>() {
            @Override
            public void onResponse(Call<List<PortalStudentVisualizationResponse>> call,final Response<List<PortalStudentVisualizationResponse>> response) {



               // Log.d(context.getPackageName().toUpperCase(), "VisualizationResponse" + response.body());

                if (response.code() == 200) {

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalStudentVisualizationResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalStudentVisualizationResponseDAO dao = db.getPortalStudentVisualizationResponseDAO();
                            dao.deletePortalStudentVisualizationResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalStudentVisualizationResponse(response.body());

                            return null;
                        }
                    };
                    asyncTask.execute();

                }


            }

            @Override
            public void onFailure(Call<List<PortalStudentVisualizationResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void  sendRequestForToDoList(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalToDoListResponse>> userCall = apiInterface.getToDoList(student);
        userCall.enqueue(new Callback<List<PortalToDoListResponse>>() {
            @Override
            public void onResponse(Call<List<PortalToDoListResponse>> call, final Response<List<PortalToDoListResponse>> response) {


                if (response.code() == 200) {
                  // Log.d("MyFirebaseMessagingS",  "RequestResponse: "  + resultsList.toString());

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalToDoListResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalToDoListResponseDAO dao = db.getPortalToDoListResponseDAO();
                            if(dao.getPortalToDoListResponse(Integer.valueOf(student.getStudentID())) != null) {
                                // Log.d("MyFirebaseMessagingS", "FromDB: " + dao.getPortalToDoListResponse().toString());
                                dao.deletePortalToDoListResponse(Integer.valueOf(student.getStudentID()));
                            }
                            dao.insertPortalToDoListResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();

                }

            }

            @Override
            public void onFailure(Call<List<PortalToDoListResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void   sendRequestPortalTransportRoutesDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalTransportRoutesResponse>> userCall = apiInterface.getTransportRoutes(student);
        userCall.enqueue(new Callback<List<PortalTransportRoutesResponse>>() {
            @Override
            public void onResponse(Call<List<PortalTransportRoutesResponse>> call, final Response<List<PortalTransportRoutesResponse>> response) {



                if (response.code() == 200) {
                   // Log.d(context.getPackageName().toUpperCase(), resultsList.toString());

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalTransportRoutesResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }

                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalTransportRoutesResponseDAO dao = db.getPortalTransportRoutesResponseDAO();
                            dao.deletePortalTransportRoutesResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalTransportRoutesResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();



                }

            }

            @Override
            public void onFailure(Call<List<PortalTransportRoutesResponse>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }
    private static void sendRequestPortalStudentUnitDetails(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalStudentUnits>> userCall = apiInterface.getPortalStudentUnits(student);
        userCall.enqueue(new Callback<List<PortalStudentUnits>>() {
            @Override
            public void onResponse(Call<List<PortalStudentUnits>> call, final Response<List<PortalStudentUnits>> response) {



                if (response.code() == 200) {
                   // Log.d(context.getPackageName().toUpperCase(), resultsList.toString());

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalStudentUnits res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }

                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            PortalStudentUnitsDAO dao = db.getPortalStudentUnitsDAO();
                            dao.deletePortalStudentUnits(Integer.valueOf(student.getStudentID()));
                            dao.insertPortalStudentUnits(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();




                }


            }

            @Override
            public void onFailure(Call<List<PortalStudentUnits>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }


    //this is the updated one,,,event the activity involved.
    //but need to make it in
    private static void sendRequestForParentChat(final Student student, final Context context, String organizationID){

        ParentChatRequest parentChat = new ParentChatRequest(
                student.getStudentID(),//"24528",
                "",
                "0",
                student.getAppcode(),
                "1"
        );



        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<ParentChat>> userCall = apiInterface.sendParentChat(parentChat);
        userCall.enqueue(new Callback<List<ParentChat>>() {
            @Override
            public void onResponse(Call<List<ParentChat>> call, final Response<List<ParentChat>> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            for(ParentChat res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            ParentChatDAO parentChatDAO = db.getParentChatDAO();
                            parentChatDAO.deleteParentChat(Integer.valueOf(student.getStudentID()));
                            parentChatDAO.insertParentChat(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();
                }
            }

            @Override
            public void onFailure(Call<List<ParentChat>> call, Throwable t) {

            }
        });

    }
    // TODO: 2020-03-28 To be implemented later, when need to start, Simon Instructions
    private static void sendRequestForPortalBookCentre(Student student, Context context){

    }

    private static void sendRequestForPortalBorrowedBooks(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<BorrowedBooksResponse>> userCall = apiInterface.requestBorrowedBooksResponse(student);
        userCall.enqueue(new Callback<List<BorrowedBooksResponse>>() {
            @Override
            public void onResponse(Call<List<BorrowedBooksResponse>> call, final Response<List<BorrowedBooksResponse>> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            for(BorrowedBooksResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            BorrowedBooksResponseDAO dao = db.getBorrowedBooksResponseDAO();
                            dao.deleteBorrowedBooksResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertBorrowedBooksResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();
                }
            }

            @Override
            public void onFailure(Call<List<BorrowedBooksResponse>> call, Throwable t) {

            }
        });

    }

    private static void sendRequestForPortalOrderItems(final Student student , final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<OrderItemResponse>> userCall = apiInterface.requestOrderitemsResponse(student);
        userCall.enqueue(new Callback<List<OrderItemResponse>>() {
            @Override
            public void onResponse(Call<List<OrderItemResponse>> call, final Response<List<OrderItemResponse>> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            for(OrderItemResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            OrderItemResponseDAO dao = db.getOrderItemResponseDAO();
                            dao.deleteOrderItemResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertOrderItemResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItemResponse>> call, Throwable t) {

            }
        });
    }

    // TODO: 2020-03-29 Send Order Item History
    private void sendRequestForPortalOrderItemHistory(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<OrderItemResponse>> userCall = apiInterface.requestOrderitemsResponse(student);
        userCall.enqueue(new Callback<List<OrderItemResponse>>() {
            @Override
            public void onResponse(Call<List<OrderItemResponse>> call, final Response<List<OrderItemResponse>> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            OrderItemResponseDAO dao = ParentMziziDatabase.getInstance(context).getOrderItemResponseDAO();
                            dao.deleteOrderItemResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertOrderItemResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItemResponse>> call, Throwable t) {

            }
        });
    }

//    private static void sendRequestForPortalSchoolTrip(final Student student, final Context context){
//        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//        Call<List<SchoolTripResponse>> userCall = apiInterface.requestSchoolTripResponse(student);
//        userCall.enqueue(new Callback<List<SchoolTripResponse>>() {
//            @Override
//            public void onResponse(Call<List<SchoolTripResponse>> call, final Response<List<SchoolTripResponse>> response) {
//                if(response.code() == 200){
//                    AsyncTask asyncTask = new AsyncTask() {
//                        @Override
//                        protected Object doInBackground(Object[] params) {
//                            SchoolTripResponseDAO dao = ParentMziziDatabase.getInstance(context).getSchoolTripReponseDAO();
//                            dao.deleteSchoolTripResponse();
//                            dao.insertSchoolTripResponse(response.body());
//                            return null;
//                        }
//                    };
//                    asyncTask.execute();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<SchoolTripResponse>> call, Throwable t) {
//
//            }
//        });
//    }

    private static void sendRequestForPortalYoutubeVideoGallery(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<YoutubeVideoGalleryResponse>> userCall = apiInterface.requestYoutubeVideoGalleryResponse(student);
        userCall.enqueue(new Callback<List<YoutubeVideoGalleryResponse>>() {
            @Override
            public void onResponse(Call<List<YoutubeVideoGalleryResponse>> call, final Response<List<YoutubeVideoGalleryResponse>> response) {
                if(response.code() == 200){
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            for(YoutubeVideoGalleryResponse res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            YoutubeVideoGalleryResponseDAO dao = db.getYoutubeVideoGalleryResponseDAO();
                            dao.deleteYoutubeVideoGalleryResponse(Integer.valueOf(student.getStudentID()));
                            dao.insertYoutubeVideoGalleryResponse(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();
                }

                if(refresh){
                    refresh = false;
                    ((MainActivity)context).onRefreshComplete();
                    //The code below was removed since refresh will occur in all pages
                   // FragTransaction.dislayFragment(HomeFrag.class, "", ((MainActivity) context).fragmentManager);

//                    UtilityFunctions.doRestart(context);
//                    System.exit(0);
                }
//                    UtilityFunctions.doRestart(context);



            }

            @Override
            public void onFailure(Call<List<YoutubeVideoGalleryResponse>> call, Throwable t) {
                if(refresh){
                    refresh = false;
                    if(context instanceof MainActivity) {
                        ((MainActivity) context).onRefreshComplete();
                       // FragTransaction.dislayFragment(HomeFrag.class, "", ((MainActivity) context).fragmentManager);
                        Intent intent = new Intent(( context), MainActivity.class);
                        intent.putExtra("StudentID", student.getStudentID());
                        intent.putExtra("appcode", student.getAppcode());
                        intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT,OpenFragments.OPEN_HOME_FRAGMENT);
                        intent.putExtra("isLog_In",((MainActivity) context).getIntent().getBooleanExtra("isLog_In", false));//true because you logged in
                        (context).startActivity(intent);
                        ((MainActivity) context).finish();
                    }else{
                        UtilityFunctions.doRestart(context);
                        System.exit(0);
                    }

//
                }
            }
        });
    }


    private static void setRequestForPortalResults(final Student student, final Context context){

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalStudentDetailedResults>> userCall = apiInterface.getPortalStudentDetailes(student);
        userCall.enqueue(new Callback<List<PortalStudentDetailedResults>>() {
            @Override
            public void onResponse(Call<List<PortalStudentDetailedResults>> call, final Response<List<PortalStudentDetailedResults>> response) {

                if (response.code() == 200) {
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalStudentDetailedResults res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            db.getPortalStudentDetailedResultsDao().deletePortalStudentDetialedResults(Integer.valueOf(student.getStudentID()));
                            db.getPortalStudentDetailedResultsDao().insertPortalStudentDetialedResults(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();

                }

            }


            @Override
            public void onFailure(Call<List<PortalStudentDetailedResults>> call, Throwable t) {
                                                  /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });

    }



    private static void sendTestRequest(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<PortalStudentVisualizationResponse>> userCall = apiInterface.getStudentVisualization(new StudentVisualization("1391","0", "1010"));
        userCall.enqueue(new Callback<List<PortalStudentVisualizationResponse>>() {
            @Override
            public void onResponse(Call<List<PortalStudentVisualizationResponse>> call, Response<List<PortalStudentVisualizationResponse>> response) {

               // Log.d(context.getPackageName().toUpperCase(), "Request Testing: " + response.body().toString());

                if(response.isSuccessful()){
                    if(response.code() == 200){

                    }
                }
            }

            @Override
            public void onFailure(Call<List<PortalStudentVisualizationResponse>> call, Throwable t) {

            }
        });
    }


    private static void sendRequestForTransactions(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalDetailedTransaction>> userCall = apiInterface.getPortalDetailedTransaction(student);
        userCall.enqueue(new Callback<List<PortalDetailedTransaction>>() {
            @Override
            public void onResponse(Call<List<PortalDetailedTransaction>> call, final Response<List<PortalDetailedTransaction>> response) {

                if (response.code() == 200) {
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            for(PortalDetailedTransaction res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            db.getPortalDetailedTransactionDao().deletePortalDetailedTransaction(Integer.valueOf(student.getStudentID()));
                            db.getPortalDetailedTransactionDao().insertPortalDetailedTransaction(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();

                }

            }

            @Override
            public void onFailure(Call<List<PortalDetailedTransaction>> call, Throwable t) {

            }
        });
    }


    public class DeviceFCMToken{
        private String StudentID;
        private String SchoolID;
        private String OrganizationID;
        private String DeviceFCMToken;
        private String AppCode;

        public DeviceFCMToken(String studentID, String schoolID, String organizationID, String deviceFCMToken, String appCode) {
            StudentID = studentID;
            SchoolID = schoolID;
            OrganizationID = organizationID;
            DeviceFCMToken = deviceFCMToken;
            AppCode = appCode;
        }

        public String getStudentID() {
            return StudentID;
        }

        public String getSchoolID() {
            return SchoolID;
        }

        public String getOrganizationID() {
            return OrganizationID;
        }

        public String getDeviceFCMToken() {
            return DeviceFCMToken;
        }

        public String getAppCode() {
            return AppCode;
        }

        @Override
        public String toString() {
            return "DeviceFCMToken{" +
                    "StudentID='" + StudentID + '\'' +
                    ", SchoolID='" + SchoolID + '\'' +
                    ", OrganizationID='" + OrganizationID + '\'' +
                    ", DeviceFCMToken='" + DeviceFCMToken + '\'' +
                    ", AppCode='" + AppCode + '\'' +
                    '}';
        }
    }


    private static void sendRequestForStudentFCMDeviceToken(final Student student, final Context context){

        //TO BE REMOVED AFTER SIMON TESTING
//        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//        Map hashMap = new HashMap();
//        hashMap.put("DeviceFCMToken", FirebaseInstanceId.getInstance().getToken());
//
//        Call<ResponseBody> userCall = apiInterface.requestDeviceFCMTokenSimon(hashMap);
//        userCall.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {}
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {}
//        });
        //TO BE REMOVED AFTER SIMON TESTING

            final AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected void onPostExecute(final Object oo) {

                    List<String> list = (List<String>) oo;

                    if (list.size() > 0) {

                        new AsyncTask(){

                            @Override
                            protected void onPostExecute(Object o) {

                                final List<AuthenticateUserResponse> responseList = (List<AuthenticateUserResponse>)o;
                                if(responseList.size() > 0){

                                    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (task.isSuccessful()) {
                                                final  String refreshToken = task.getResult().getToken();
                                                List<PortalSiblings> siblingsList = (List<PortalSiblings>) oo;
                                                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

                                                for (PortalSiblings siblings : siblingsList) {

                                                    Map hashMap = new HashMap();
                                                    hashMap.put("StudentID",siblings.getStudentID());
                                                    hashMap.put("SchoolID", siblings.getSchoolID());
                                                    hashMap.put("OrganizationID",siblings.getOrganizationID());
                                                    hashMap.put("DeviceFCMToken",refreshToken);
                                                    hashMap.put("AppCode",responseList.get(0).getAppcode());
                                                    hashMap.put("AppVersion", String.valueOf(BuildConfig.VERSION_CODE));
                                                    Call<ResponseBody> userCall = apiInterface.requestDeviceFCMToken(hashMap);
                                                    userCall.enqueue(new Callback<ResponseBody>() {
                                                        @Override
                                                        public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                                                            ResponseBody res = response.body();
                                                            Log.d(context.getPackageName().toUpperCase(), "T: " +  refreshToken);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<ResponseBody> call, Throwable t) {}
                                                    });
                                                }

                                            }
                                        }
                                    });

                                }
                                super.onPostExecute(o);
                            }

                            @Override
                            protected Object doInBackground(Object[] objects) {
                                return ParentMziziDatabase.getInstance(context).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
                            }
                        }.execute();

                        super.onPostExecute(oo);
                    }
                }

                @Override
                protected Object doInBackground (Object[]objects){
                    ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                    return db.getPortalSiblingsDao().getSiblings();
                }
            };
            asyncTask.execute();

    }





    ///converting
    private static void sendRequestForNotification(final Student student, final Context context, final MessageRequest messageRequest){
        //testing

       // messageRequest.setMsgID("0");

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
        Call<List<Notification>> userCall = apiInterface.getNotifications(messageRequest);
        userCall.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, final Response<List<Notification>> response) {

                if (response.code() == 200) {

                        AsyncTask asyncTask = new AsyncTask() {

                            @Override
                            protected void onPostExecute(Object o) {
                                List<Notification> notificationList = (List<Notification>)o;
                                if(notificationList.size() > 0){

                                    SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(context).getFilteredPortalStudentInfoResult();
                                    String billingBalance = syncMyAccountResult.getBillingBalance();
                                    if(billingBalance.equals("")){//equal to an empty string
                                        //do nothing
                                    }else {//not equal to an empty string

                                        if(Float.valueOf(billingBalance) > 0f){//dont show notifications
                                            //DO NOTHING
                                        }else {//If the balance is less than 0.0f, show notifications

                                                                //Toast.makeText(context, "billingBalance: " + billingBalance, Toast.LENGTH_LONG).show();
                    //                    Paper.book().delete("NewNotification");
                    //                    Paper.book().write("NewNotification", listNotifications);

                                            //Log.d(context.getPackageName().toUpperCase(), "Should send notification: " + notificationList.toString());
                                            NotificationTopDisplay.showNotificationDisplay(notificationList, context,Integer.valueOf(student.getStudentID()),"NotificationFromMziziApi");

                                            List<ReadNotReadNotification> readNotReadNotificationList = new ArrayList<>();
                                            for(Notification notification : notificationList){
                                                ReadNotReadNotification readNotification = new ReadNotReadNotification(
                                                        String.valueOf(notification.getMsgID())
                                                );
                                                readNotReadNotificationList.add(readNotification);
                                            }

                                            for(ReadNotReadNotification readnot : readNotReadNotificationList){
                                                readnot.setStudID(Integer.valueOf(student.getStudentID()));
                                            }
                                            //save any new notifications,, will be deleted after they are viewed from the notifications area,
                                            //this table will be used to identify new notifications and the old notifications to assign them different markers
                                            new ReadNotReadNotificationDAO(context).inserNotificationReadTracking(readNotReadNotificationList);


                                            //if you have five messages? all the same

                                            int countOne= 1;
                                            int countTwo = 1;

                                            //if one of the message has one of these then send the request once
                                            //this seems not to work anymore
                                            for(int i =0; i<notificationList.size(); i++){
                                                String message =  notificationList.get(i).getMsg();

                                                if(message.contains("Results") || message.contains("term") || message.contains("results") || message.contains("Term") ){
                                                    if(countOne <=1) {
                                                        //Toast.makeText(context, "send for results", Toast.LENGTH_SHORT).show();
                                                        RequestFor.sendRequest(student, context,"", APIRequest.RESULTS_8_4_4);
                                                        //sendRequestForPortalResults(student);
                                                    }else{
                                                        //dont send request
                                                    }

                                                    countOne++;


                                                } else if (message.contains("receipt") || message.contains("transaction") || message.contains("Receipt") || message.contains("Transaction") ) {
                                                    if(countTwo<=1) {

                                                        //Toast.makeText(context, "send for transaction"+ String.valueOf(listNotifications.size()), Toast.LENGTH_SHORT).show();
                                                        RequestFor.sendRequest(student, context,"", APIRequest.TRANSACTION);

                                                    }else{

                                                    }

                                                    countTwo++;

                                                }else if(message.contains("chat") || message.contains("Chat")){
                                                    if(countTwo <= 1){
                                                        RequestFor.sendRequest(student, context,"", APIRequest.PORTAL_CHAT);
                                                        // sendRequestForParentChat(student,context);
                                                        countTwo++;
                                                    }
                                                }else if(message.contains("event") || message.contains("Event")){
                                                    if(countTwo <=1){
                                                        RequestFor.sendRequest(student, context,"", APIRequest.EVENTS);
                                                        // sendRequestForEvents(student, context);
                                                        countTwo++;
                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                                super.onPostExecute(o);
                            }

                            @Override
                            protected Object doInBackground(Object[] objects) {

                                //Current Date
                                Calendar currentDate = Calendar.getInstance();//this is todays date
                                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

                                String currentDateString = formatter.format(currentDate.getTime());
                                String dateSentString;

                                final Date currentd = new Date(currentDateString);//filterdd current date
                                Date datesent;///filted date notification sent

                                //Notification Date/Previous date
                                Date notificationDateSent;//date notification is sent//this message isnot filterd

                                // end filtering notificaitons accroding to dates.


                                List<Notification> listNotificationsToDisplay = new ArrayList<>();

                               for(int i = 0; i < response.body().size(); i++){

                                   if(i == 0){
                                       listNotificationsToDisplay.clear();
                                   }

                                   Notification message = response.body().get(i);
                                   message.setStudID(Integer.valueOf(student.getStudentID()));
                                   message.setMsgID(response.body().get(i).getMsgID());
                                   message.setMsg(response.body().get(i).getMsg());
                                   message.setDateSent(response.body().get(i).getDateSent());

                                   //filter by the number of days

                                   //"9/2/2018 1:55:56 PM"/ WORKS FOR BOTH DATES FORMARTS
                                   notificationDateSent = new Date(message.getDateSent());//date you should get form every notification
                                   dateSentString = formatter.format(notificationDateSent);//time sent
                                   datesent = new Date(dateSentString);

                                   long diff = Math.abs(currentd.getTime() - datesent.getTime());

                                   final long days = diff / (24 * 60 * 60 * 1000);

                                   //System.out.println("Days between: " + days);

                                   if(days >= 14){//notifications with days greater than or equal to 14 will be deleted
                                       //System.out.println("Delete this message in the datebase");
                                       // new NotificationDAO(context).deleteOneNotification(message.getMsgID());

                                       //here you dont delete the message, you just dont save it in the database


                                   }else{

                                       //you now save the data that was sent within 7 days before today

                                       //System.out.println("Don't delete this message, you can now add it to list for  display");
                                       listNotificationsToDisplay.add(message);

                                   }
                               }

                               for(Notification res : listNotificationsToDisplay){
                                   res.setStudID(Integer.valueOf(student.getStudentID()));
                               }


                                new NotificationDAO(context).insertNotifications(listNotificationsToDisplay);

                               return listNotificationsToDisplay;
                            }
                        };
                        asyncTask.execute();
                }


            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                //  Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
                  /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }

    private static void sendRequestForUpcomingEvents(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalEvents>> userCall = apiInterface.getPortalEvents(student);
        userCall.enqueue(new Callback<List<PortalEvents>>() {
            @Override
            public void onResponse(Call<List<PortalEvents>> call, final Response<List<PortalEvents>> response) {

                if (response.code() == 200) {

                   AsyncTask asyncTask = new AsyncTask() {
                       @Override
                       protected Object doInBackground(Object[] objects) {
                           ParentMziziDatabase db = ParentMziziDatabase.getInstance(context.getApplicationContext());
                           for(PortalEvents res : response.body()){
                               res.setStudID(Integer.valueOf(student.getStudentID()));
                           }
                           db.getPortalEventsDao().deletePortalEvents(Integer.valueOf(student.getStudentID()));
                           for(PortalEvents res : response.body()){
                               res.setStudID(Integer.valueOf(student.getStudentID()));
                           }
                           db.getPortalEventsDao().insertPortalEventsList(response.body());

                           return response.body();
                       }
                   };
                    asyncTask.execute();


                }
            }

            @Override
            public void onFailure(Call<List<PortalEvents>> call, Throwable t) {
                //  Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }

    private static void sendRequestForPortalContacts(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalContacts>> userCall = apiInterface.getPortalContacts(student);
        userCall.enqueue(new Callback<List<PortalContacts>>() {
            @Override
            public void onResponse(Call<List<PortalContacts>> call, final Response<List<PortalContacts>> response) {

                if (response.code() == 200) {
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);

                            db.getPortalContactsDao().deletePortalContacts(Integer.valueOf(student.getStudentID()));

                            for(PortalContacts contacts : response.body()){
                                contacts.setStudID(Integer.valueOf(student.getStudentID()));
                            }
                            db.getPortalContactsDao().insertPortalContacts(response.body());

                            return null;
                        }
                    };
                    asyncTask.execute();
                }

                moveToMainActivity(student,context);

            }

            @Override
            public void onFailure(Call<List<PortalContacts>> call, Throwable t) {
              //  Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
                moveToMainActivity(student,context);

            }
        });
    }

    private static void moveToMainActivity(final Student student, final Context context){
        if(login){
            login = false;


           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {

                   new AsyncTask(){
                       @Override
                       protected void onPostExecute(Object o) {

                           String StudentID = (String)o;
                           if(weakReference.get() instanceof LoginActivity)
                               ((LoginActivity) weakReference.get()).bar.cancel();
                           if(weakReference.get() instanceof SyncMyAccount)
                               ((SyncMyAccount) weakReference.get()).bar.cancel();

                           //REMEMBER YOU SHOULD NOT DELETE AL THE DATA

                           //if all the statuses are right lets move to login
                           if(weakReference.get() instanceof SyncMyAccount){
                               Intent intent = new Intent((SyncMyAccount) weakReference.get(), MainActivity.class);
                               intent.putExtra("StudentID",StudentID);
                               intent.putExtra("appcode", student.getAppcode());



                               intent.putExtra("isLog_In",((SyncMyAccount) weakReference.get()).getIntent().getBooleanExtra("isLog_In", false));//true because you logged in
                               intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                               ((SyncMyAccount) weakReference.get()).startActivity(intent);
                               ((SyncMyAccount) weakReference.get()).finish();
                           }

                           if(weakReference.get() instanceof LoginActivity){
                               Intent intent = new Intent((LoginActivity) weakReference.get(), MainActivity.class);
                               intent.putExtra("StudentID",StudentID);
                               intent.putExtra("appcode", student.getAppcode());

                               intent.putExtra("DisplayLayout",  ((LoginActivity) weakReference.get()).displayLayout);


                               intent.putExtra("isLog_In",((LoginActivity) weakReference.get()).getIntent().getBooleanExtra("isLog_In", false));//true because you logged in
                               intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                               ((LoginActivity) weakReference.get()).startActivity(intent);
                               ((LoginActivity) weakReference.get()).finish();
                           }
                           super.onPostExecute(o);
                       }

                       @Override
                       protected Object doInBackground(Object[] objects) {
                           ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                           //testing
                           List<PortalSiblings> list = db.getPortalSiblingsDao().getSiblings();

                           List<AuthenticateUserResponse> list2 = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                           String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                           if(studentid == null){
                               studentid  = "0";
                           }

                           return studentid;
                       }
                   }.execute();

               }
           }, 80000);

        }
    }



    private static void sendRequestForPortalSibling(final Student student, final Context context){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalSiblings>> userCall = apiInterface.getPortalSiblings(student);
        userCall.enqueue(new Callback<List<PortalSiblings>>() {
            @Override
            public void onResponse(Call<List<PortalSiblings>> call, final Response<List<PortalSiblings>> response) {


                if (response.code() == 200) {

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                            db.getPortalSiblingsDao().deleteSiblings();
                            db.getPortalSiblingsDao().insertMoreSiblings(response.body());

                            //testing
                           // List<PortalSiblings> list = db.getPortalSiblingsDao().getSiblings();

                            List<AuthenticateUserResponse> list2 = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                            //String StudentID = db.getPortalSiblingsDao().getMainStudentFromSibling();

                            db.getSwitchSiblingsDAO().setTheMainStudentToThisStudentIDPassed(list2.get(0).getUserID());

                           // String StudentID = db.getPortalSiblingsDao().getMainStudentFromSibling();

                            return null;
                        }
                    };
                    asyncTask.execute();

                }

            }

            @Override
            public void onFailure(Call<List<PortalSiblings>> call, Throwable t) {
                // Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }


    private static void sendRequestforTimeTable(final Student student, final Context context){

        new AsyncTask(){

            @Override
            protected void onPostExecute(final Object o) {

              final   List<AuthenticateUserResponse> list = (List<AuthenticateUserResponse>)o;
                if(list.size() > 0){

                    new AsyncTask(){
                        @Override
                        protected void onPostExecute(Object oo) {

                            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
                            Map<String,String> request = new HashMap<>();
                            request.put("StudentID", student.getStudentID());
                            request.put("SchoolID",oo.toString());
                            request.put("TermID","");
                            request.put("YearID",String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
                            request.put("AppCode", student.getAppcode());
                            request.put("AppVersion",  String.valueOf(BuildConfig.VERSION_CODE));

                            Call<List<TimeTableResponse>> userCall = apiInterface.requestTimeTable(request);
                            userCall.enqueue(new Callback<List<TimeTableResponse>>() {
                                @Override
                                public void onResponse(Call<List<TimeTableResponse>> call, final Response<List<TimeTableResponse>> response) {

                                    if (response.code() == 200) {
                                        AsyncTask asyncTask = new AsyncTask() {
                                            @Override
                                            protected Object doInBackground(Object[] objects) {
                                                ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);

                                                db.getTimeTableResponseDAO().deleteTimeTableResponse(Integer.valueOf(student.getStudentID()));

                                                for(TimeTableResponse res : response.body()){
                                                    res.setStudID(Integer.valueOf(student.getStudentID()));
                                                }
                                                db.getTimeTableResponseDAO().insertTimeTableResponse(response.body());

                                                return null;
                                            }
                                        };
                                        asyncTask.execute();
                                    }

                                    moveToMainActivity(student,context);

                                }

                                @Override
                                public void onFailure(Call<List<TimeTableResponse>> call, Throwable t) {
                                    //  Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    call.cancel();
                                    moveToMainActivity(student,context);

                                }
                            });

                            super.onPostExecute(oo);
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            return ParentMziziDatabase.getInstance(context).getPortalSiblingsDao().getSchoolIDFromPortalSibling(student.getStudentID());
                        }
                    }.execute();



                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return ParentMziziDatabase.getInstance(context).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
            }
        }.execute();


    }





}


