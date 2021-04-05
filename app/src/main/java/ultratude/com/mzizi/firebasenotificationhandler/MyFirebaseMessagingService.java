package ultratude.com.mzizi.firebasenotificationhandler;

import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.webservice.APIRequest;

import static android.os.Build.VERSION.SDK_INT;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG ="MyFirebaseMessagingS";

    public MyFirebaseMessagingService() {

    }


//https://medium.com/android-school/test-fcm-notification-with-postman-f91ba08aacc3
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /*There are two types of messages data messages and notification messages. Data messages are handled here in
        * onMessageReceived whether the app is in the foreground or background. Data messages are the type traditionally used with GCM.
        * Notification messages are only received here in onMessageReceived when the app is in the foreground.
        * When the app is in the background an automatically generated notification is displayed.
        * When the user taps on teh notification they are returned to the app.
        * Messages containing both notification and data payloads are treated as notification messages.
        * The firebase console always sends notification messages.
        * For more see: https://firebase.google.com/docs/cloud-messaging/concept-options*/

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        //if(remoteMessage.getNotification() != null ) {
        if (remoteMessage.getData().size() > 0) {



            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob(remoteMessage);
            } else {
                // Handle message within 10 seconds
                handleNow();
            }


            // Check if message contains a notification payload.
//
//                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//
//
           }

        //}

        //https://stackoverflow.com/questions/37711082/how-to-handle-notification-when-app-in-background-in-firebase
       // Log.d(TAG, "WHEN IN BACKGROUND");
    }




    @Override
    public void onDeletedMessages() {

    }



    /*Called if InstanceID token is updated. This may occur if the security of the previous token had been compromised.
    * Note that this is called when the InstanceID token is initially generated so this is where you would retrieve the token*/
    //https://stackoverflow.com/questions/50127098/get-new-token-firebase-fcm-for-push-notification
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void scheduleJob(final RemoteMessage remoteMessage){
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
        //Fetch data
        Log.d(TAG, "1) Calling SendRequestFromFirebaseFCM");
        new AsyncTask(){
            @Override
            protected void onPostExecute(Object o) {
                List<AuthenticateUserResponse> responseList = (List<AuthenticateUserResponse>)o;
                if(responseList.size() > 0){

                    Paper.init(getApplicationContext());
                    if(Paper.book().contains("FCMLoggedInYet")) {
                        if (Paper.book().read("FCMLoggedInYet").toString().equalsIgnoreCase("1")) {
                            new SendRequestFromFirebaseFCM(getApplicationContext()).execute(remoteMessage);
                        }
                    }

                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return ParentMziziDatabase.getInstance(getApplicationContext()).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
            }
        }.execute();

    }

    private void handleNow() {

        Log.d(TAG, "Short lived task is done.");
    }

    //persist token to third-party servers
    //Modify this method to associate the user's FCM InstanceID token with any server-side account
    //maintained by your application
    private void sendRegistrationToServer(String token){
        Log.d(TAG, "Token: " + token);
        // TODO: 2020-06-12 Implement this method to send token to your app server
        new AsyncTask(){

            @Override
            protected void onPostExecute(Object o) {
                List<AuthenticateUserResponse> authenticateUserResponses = (List<AuthenticateUserResponse>)o;

                if(authenticateUserResponses.size() > 0){
                    Student student = new Student(authenticateUserResponses.get(0).getUserID(), authenticateUserResponses.get(0).getAppcode());
                    RequestFor.sendRequest(student,getApplicationContext(),"", APIRequest.REGISTER_FCM_TOKEN);
                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return ParentMziziDatabase.getInstance(getApplicationContext()).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
            }
        }.execute();
    }




    private class SendRequestFromFirebaseFCM extends AsyncTask<RemoteMessage, Void, Map<String, Object>> {


        Context context;

        public SendRequestFromFirebaseFCM(Context context) {

            this.context = context;
        }


        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {

            Log.d(TAG, "4) Inside onPostExecute");



            String title = "";//contains the school name
            String message = "";

            List<String> list3 = (List<String>) map.get("Notification");
            if(list3.size() == 2){
                title = list3.get(0);
                message = list3.get(1);
            }


            String studentIDFromNotification = "";
            String organizationFromNotification = "";
            String schoolIDFromNotification = "";

            List<String> list2 = (List<String>) map.get("FromNotification");

            if(list2.size() == 3){
                studentIDFromNotification = list2.get(0);
                schoolIDFromNotification = list2.get(1);
                organizationFromNotification  = list2.get(2);
            }


            Student student = new Student();
            String studentIDFromDB = "";
            String appcodeFromDB = "";
            String organizationFromDB = "";
            String notificationLastIDFromDB = "";
            String schoolIDFromDB = "";

            List<List<String>> listSibling = (List<List<String>>) map.get("FromDBAndSibling");

            for(List<String> list : listSibling) {

                if (list.size() == 5) {
                    studentIDFromDB = list.get(0);
                    appcodeFromDB = list.get(1);
                    organizationFromDB = list.get(2);
                    schoolIDFromDB = list.get(3);
                    notificationLastIDFromDB = list.get(4);


                    student = new Student(studentIDFromDB, appcodeFromDB);
                    student.setOrganizationID(organizationFromDB);
                }


                Log.d(TAG, "5) Compairing results after fetching: " + "list: " + list.size() + " list2: " + list2.size() + " list3: " + list3.size() );
                if(list.size() == 5 && list2.size() == 3 && list3.size() == 2){

                    Log.d(TAG, "6) Compaire Student: " + studentIDFromDB.equalsIgnoreCase(studentIDFromNotification)
                            + " SchoolID: " +schoolIDFromDB.equalsIgnoreCase(schoolIDFromNotification)
                            + " OrganizationID: " + organizationFromDB.equalsIgnoreCase(organizationFromNotification));

                    if(studentIDFromDB.equalsIgnoreCase(studentIDFromNotification) &&
                            schoolIDFromDB.equalsIgnoreCase(schoolIDFromNotification) &&
                            organizationFromDB.equalsIgnoreCase(organizationFromNotification)
                    ){


                        Log.d(TAG, "7) onMessageReceived:  Message Received: \n" +
                                "Title: " + title + "\n" +
                                "Message: " + message + "\n" +
                                "StudentID: " + student.getStudentID());


                        // sendNotification(title, message + " " + student.getStudentID());
                        List<Notification> listNotifications = new ArrayList<>();
                        Notification notification = new Notification();
                        notification.setMsg(message);
                        listNotifications.add(notification);

                        NotificationTopDisplay.showNotificationDisplay(listNotifications, context, Integer.valueOf(studentIDFromDB), "FCM");


                        if(message != null) {

                            if (message.contains("Results") || message.contains("term")
                                    || message.contains("results") || message.contains("Term")) {

                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.RESULTS_8_4_4);
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.RECENT_RESULTS_8_4_4);
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.STUDENT_VISUALIZATION);
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.STUDENT_VISUALIZATION_AVERAGE);


                                //RequestFor.sendRequest(student,context,notificationLastIDFromDB,APIRequest.RESULTS_2_6_6_3);

                            } else if (message.contains("receipt") || message.contains("transaction")
                                    || message.contains("Receipt") || message.contains("Transaction")
                                    || message.contains("credit") || message.contains("credit")
                                    || message.contains("Balance") || message.contains("balance")
                                    || message.contains("SMS Receipt")) {

                               // Log.d(TAG, "Send Request for TRANSACTION and RECENT_TRANSACTION ");

                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.TRANSACTION);
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.RECENT_TRANSACTION);

                            } else if (message.contains("chat") || message.contains("Chat")
                                    || message.contains("replied") || message.contains("Replied")) {

                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.PORTAL_CHAT);

                            } else if (message.contains("event") || message.contains("Event")) {

                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.EVENTS);

                            } else if (message.contains("diary") || message.contains("Diary")) {
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.DETAILED_TODO);
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.TODO);

                            } else if (message.contains("video") || message.contains("Video")) {
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.VIDEO_GALLERY);
                            } else if (message.contains("photo") || message.contains("Photo")) {
                                RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.PHOTO_GALLERY);
                            }

                            // TODO: 2020-06-12 Remember to uncomment this, since its important
                            RequestFor.sendRequest(student, context, notificationLastIDFromDB, APIRequest.FCM_MUST);
                        }

                        break;
                    }
                }



            }






            super.onPostExecute(map);
        }

        @Override
        protected Map<String, Object> doInBackground(RemoteMessage... remoteMessagesList) {
            Map<String, Object> returnMap = new HashMap<>();
            if(remoteMessagesList.length > 0) {

                Log.d(TAG, "2) Inside doInBackground");

                ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);


                List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();


                    // TODO: 2020-06-25 notification for specific studentID
                    List<Notification> notificationList = new NotificationDAO(context).getNotificationsList();


                    List<List<String>> listlist = new ArrayList<>();
                    List<String> list = new ArrayList<>();
                    if (authenticateUserResponse.size() > 0) {
                        list.add(authenticateUserResponse.get(0).getUserID());//0
                        list.add(authenticateUserResponse.get(0).getAppcode());//1
                        list.add(authenticateUserResponse.get(0).getOrganizationID());//2
                        list.add(db.getPortalSiblingsDao().getSchoolIDFromPortalSibling(authenticateUserResponse.get(0).getUserID()));//3
                    }


                    if (notificationList.isEmpty()) {
                        String.valueOf(new Notification().getMsgID());
                        list.add(String.valueOf(new Notification().getMsgID()));//4
                    } else {
                        list.add(String.valueOf(notificationList.get(notificationList.size() - 1).getMsgID()));//4
                    }

                    listlist.add(list);


                    List<String> listSibling = new ArrayList<>();
                    listSibling.clear();
                    for (PortalSiblings sibling : db.getPortalSiblingsDao().getSiblings()) {
                        if (authenticateUserResponse.size() > 0) {
                            String mainStudentID = list.get(0);
                            if (mainStudentID.equalsIgnoreCase(sibling.getStudentID())) {
                            } else {
                                listSibling.add(sibling.getStudentID());
                                listSibling.add(list.get(1));
                                listSibling.add(list.get(2));
                                listSibling.add(list.get(3));
                                listSibling.add(list.get(4));
                                listlist.add(listSibling);
                            }

                        }
                    }


                    returnMap.put("FromDBAndSibling", listlist);


                    List<String> list2 = new ArrayList<>();
                    list2.add(remoteMessagesList[0].getData().get("StudentID"));//0
                    list2.add(remoteMessagesList[0].getData().get("SchoolID"));//1
                    list2.add(remoteMessagesList[0].getData().get("OrganizationID"));//2

                    returnMap.put("FromNotification", list2);

                    List<String> list3 = new ArrayList<>();
//                    list3.add(remoteMessagesList[0].getNotification().getTitle());//0=title
//                    list3.add(remoteMessagesList[0].getNotification().getBody());//notification
                    list3.add(remoteMessagesList[0].getData().get("title"));
                    list3.add(remoteMessagesList[0].getData().get("body"));

                    returnMap.put("Notification", list3);

                    Log.d(TAG, "3) Fetched data from db");


            }

            return returnMap;
        }
    }

}
