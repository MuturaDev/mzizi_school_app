package ultratude.com.mzizi.backgroundtasks;

import android.content.Intent;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import ultratude.com.mzizi.modelclasses.DisplayLayout;
import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.modelclasses.NotificationReadTrackingDAO;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotificationDAO;
//import ultratude.com.mzizi.notificationpg.NotificationBroadcast.NotificationService;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.uiactivities.LoginActivity;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.SyncMyAccount;

/**
 * Created by James on 16/07/2018.
 */

public class DeleteThisStudentData extends AsyncTask<Void,Void, Map<String, String>>{

    WeakReference<Object> weakReference;


    public DeleteThisStudentData(Object activity){

        if(activity instanceof MainActivity)
            this.weakReference = new WeakReference<>(activity);

        if(activity instanceof SyncMyAccount)
            this.weakReference = new WeakReference<>(activity);



    }




    @Override
    protected void onPreExecute() {



        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Map<String, String> isDeleted) {

        //when you delete data what should happen


      // mainActivityWeakReference.get().fragmentManager.beginTransaction().remove(new HomeFrag());
        //remember also to delete details of this student.





            //3,1,5,6 for test
      // Toast.makeText(mainActivityWeakReference.get(),
        //      "\nDeletegetAuthenticateUserResponseDao: "  + isDeleted.get("DeletegetAuthenticateUserResponseDao") , Toast.LENGTH_LONG).show();



           // mainActivityWeakReference.get().alert.create().cancel();
//            //move back to login

        if(weakReference.get() instanceof MainActivity){

            new FirebaseJobDispatcher(new GooglePlayDriver(((MainActivity)  weakReference.get()))).cancelAll();


            //((MainActivity) weakReference.get()).stopService(new Intent( (MainActivity) weakReference.get(), NotificationService.class));
            ((MainActivity)  weakReference.get()).showProgress(false);//appears here becoze MainActivity uses it
//
            Intent intent = new Intent((MainActivity) weakReference.get(), LoginActivity.class);
            intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
            ((MainActivity) weakReference.get()).startActivity(intent);
            ((MainActivity) weakReference.get()).finish();


        }else if(weakReference.get() instanceof SyncMyAccount){

            new FirebaseJobDispatcher(new GooglePlayDriver(((SyncMyAccount) weakReference.get()))).cancelAll();


           // ((SyncMyAccount) weakReference.get()).stopService(new Intent( (SyncMyAccount) weakReference.get(), NotificationService.class));
           // ((MainActivity)  weakReference.get()).showProgress(false);//SyncMyAccount is not using this
//
            Intent intent = new Intent((SyncMyAccount) weakReference.get(), LoginActivity.class);
            intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
            ((SyncMyAccount) weakReference.get()).startActivity(intent);
            ((SyncMyAccount) weakReference.get()).finish();

        }






        //you can cancel the progress here


        super.onPostExecute(isDeleted);
    }

    @Override
    protected Map<String, String> doInBackground(Void... strings) {


      //PreferenceStorage.deleteAll();

        int rowno=0, rowno1=0, rowno2=0, rowno3=0, rowno4=0, rowno5=0, rowno6=0, rowno7=0, rowno8=0, rowno9 = 0, rowno10=0, rowno11=0,rownno12=0,rownno13= 0,rownno14 =0, rowno15;

        if(weakReference.get() instanceof MainActivity){




            ParentMziziDatabase db = ParentMziziDatabase.getInstance( (MainActivity) weakReference.get());
           rowno =db.getNotificationsDao().deleteNotifications();
          rowno1 = new NotificationDAO(( (MainActivity) weakReference.get()).getApplicationContext()).deleteAllNotifications();


           rowno9 = new SyncMyAccountDAO((MainActivity) weakReference.get()).deleteAllSyncMyAccountResult();


            rowno2 = db.getPortalStudentInfoDao().deleteAllStudents();
             rowno3 =  db.getPortalDetailedTransactionDao().deleteAllPortalDetailedTransaction();
            rowno4 = db.getPortalStudentDetailedResultsDao().deleteAllPortalStudentDetiledResults();
            rowno5 = db.getPortalEventsDao().deleteAllPortalEvents();
             rowno6 = db.getPortalSiblingsDao().deleteSiblings();
             rowno7 = db.getAuthenticateUserResponseDao().deleteAuthenticateUserResponse();//
             rowno8 = db.getPortalContactsDao().deleteAllPortalContacts();

            rowno9 = new NewCarriculumExamDAO((MainActivity) weakReference.get()).deleteAllNewCarriculumExamFormat();
            rowno10 = new ReadNotReadNotificationDAO((MainActivity) weakReference.get()).deleteReadNotReadNotification();
            rowno11 = new NotificationReadTrackingDAO((MainActivity) weakReference.get()).deleteAllNotificationsReadTracking();

            rownno12 = db.getStudentClassAttendanceDAO().deleteAllStudentClassAttendance();
            rownno13 = db.getParentChatDAO().deleteAllParentChat();
            rownno14 = db.getGlobalSettingsDAO().deleteAllGlobalSettings();

            rowno15 = db.getPortalToDoListResponseDAO().deleteAllPortalToDoListResponse();
            db.getPortalDetailedToDoListResponseDAO().deleteAllPortalDetailedToDoListResponse();
         int dd =   db.getPortalRecentTransactionResponseDAO().deleteAllPortalRecentTransactionResponse();
            db.getPortalStudentVisualizationAverageResponseDAO().deleteAllPortalStudentVisualizationAverageResponse();
            db.getPortalStudentVisualizationResponseDAO().deleteAllPortalStudentVisualizationResponse();

            int ff = db.getPortalStudentResultsExtendedDAO().deleteAllPortalStudentResultsExtended();
            int gg = db.getYoutubeVideoGalleryResponseDAO().deleteAllYoutubeVideoGalleryResponse();

            int hh = db.getTimeTableResponseDAO().deleteAllTimeTableResponse();




//        Map<String, String> isDeleted = new HashMap<>();
//        isDeleted.put("deleteMessage", String.valueOf(rowno));
//        isDeleted.put("deleteStudent", String.valueOf(rowno2));
//        isDeleted.put("deletePortalDetailedTransaction", String.valueOf(rowno3));
//        isDeleted.put("DeletePortalStudentDetailedResults", String.valueOf(rowno4));
//        isDeleted.put("DeletePortalEvents", String.valueOf(rowno5));
//        isDeleted.put("DeletePortalSiblings",String.valueOf(rowno6));
//        isDeleted.put("DeleteAuthenticateUserResponse", String.valueOf(rowno7));
//        isDeleted.put("DeletePortalContacts", String.valueOf(rowno8));


        }else if(weakReference.get() instanceof SyncMyAccount){

            ParentMziziDatabase db = ParentMziziDatabase.getInstance( (SyncMyAccount) weakReference.get());
            rowno =db.getNotificationsDao().deleteNotifications();
            rowno1 = new NotificationDAO(( (SyncMyAccount) weakReference.get()).getApplicationContext()).deleteAllNotifications();

            rowno9 = new SyncMyAccountDAO((SyncMyAccount) weakReference.get()).deleteAllSyncMyAccountResult();

            rowno2 = db.getPortalStudentInfoDao().deleteAllStudents();
            rowno3 =  db.getPortalDetailedTransactionDao().deleteAllPortalDetailedTransaction();
            rowno4 = db.getPortalStudentDetailedResultsDao().deleteAllPortalStudentDetiledResults();
            rowno5 = db.getPortalEventsDao().deleteAllPortalEvents();
            rowno6 = db.getPortalSiblingsDao().deleteSiblings();
            rowno7 = db.getAuthenticateUserResponseDao().deleteAuthenticateUserResponse();//
            rowno8 = db.getPortalContactsDao().deleteAllPortalContacts();
             rowno9 = new NewCarriculumExamDAO((SyncMyAccount) weakReference.get()).deleteAllNewCarriculumExamFormat();
             rowno10 = new ReadNotReadNotificationDAO((SyncMyAccount) weakReference.get()).deleteReadNotReadNotification();
             rowno11 = new NotificationReadTrackingDAO((SyncMyAccount) weakReference.get()).deleteAllNotificationsReadTracking();
            rownno12 = db.getStudentClassAttendanceDAO().deleteAllStudentClassAttendance();
            rownno13 = db.getParentChatDAO().deleteAllParentChat();
            rownno14 = db.getGlobalSettingsDAO().deleteAllGlobalSettings();

            rowno15 = db.getPortalToDoListResponseDAO().deleteAllPortalToDoListResponse();
            db.getPortalDetailedToDoListResponseDAO().deleteAllPortalDetailedToDoListResponse();
          int cc =  db.getPortalRecentTransactionResponseDAO().deleteAllPortalRecentTransactionResponse();
            db.getPortalStudentVisualizationAverageResponseDAO().deleteAllPortalStudentVisualizationAverageResponse();
            db.getPortalStudentVisualizationResponseDAO().deleteAllPortalStudentVisualizationResponse();

            int ff = db.getPortalStudentResultsExtendedDAO().deleteAllPortalStudentResultsExtended();

            int gg = db.getYoutubeVideoGalleryResponseDAO().deleteAllYoutubeVideoGalleryResponse();

            int hh = db.getTimeTableResponseDAO().deleteAllTimeTableResponse();




//        Map<String, String> isDeleted = new HashMap<>();
//        isDeleted.put("deleteMessage", String.valueOf(rowno));
//        isDeleted.put("deleteStudent", String.valueOf(rowno2));
//        isDeleted.put("deletePortalDetailedTransaction", String.valueOf(rowno3));
//        isDeleted.put("DeletePortalStudentDetailedResults", String.valueOf(rowno4));
//        isDeleted.put("DeletePortalEvents", String.valueOf(rowno5));
//        isDeleted.put("DeletePortalSiblings",String.valueOf(rowno6));
//        isDeleted.put("DeleteAuthenticateUserResponse", String.valueOf(rowno7));
//        isDeleted.put("DeletePortalContacts", String.valueOf(rowno8));


        }




        Map<String, String> isDeleted = new HashMap<>();
        isDeleted.put("DeletegetAuthenticateUserResponseDao", String.valueOf(rowno7));


        return isDeleted;

    }
}
