package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.modelclasses.DisplayLayout;
import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.modelclasses.NotificationReadTrackingDAO;
import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotificationDAO;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
//import ultratude.com.mzizi.notificationpg.NotificationBroadcast.NotificationService;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.Test;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.uiactivities.LoginActivity;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.SyncMyAccount;

/**
 * Created by Admin on 6/3/2018.
 */

public class BottomSheetRecyclerAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<PortalSiblings> siblings;
    private BottomSheetItemListener itemListener;

    BottomSheetDialog bottomSheet;

    private static int countSiblingClicks = 1;


    public BottomSheetRecyclerAdapter(Context mContext, List<PortalSiblings> siblings, BottomSheetDialog bottomSheet) {
        this.mContext = mContext;
        this.siblings = siblings;
        this.bottomSheet = bottomSheet;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_item, parent, false);
        return new BottomSheetHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PortalSiblings sibling = siblings.get(position);
        BottomSheetHolder bsheetHolder = (BottomSheetHolder) holder;
        bsheetHolder.bind(sibling);
        bsheetHolder.siblingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                new AsyncTask(){
                    @Override
                    protected void onPostExecute(Object o) {

                        String StudentID = (String)o;

                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("StudentID",StudentID);
                        intent.putExtra("appcode", ((MainActivity)mContext).student.getAppcode());

                        intent.putExtra("isLog_In",((MainActivity) mContext).getIntent().getBooleanExtra("isLog_In", false));//true because you logged in
                        intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                        (mContext).startActivity(intent);
                        ((MainActivity) mContext).finish();
                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        ParentMziziDatabase db =  ParentMziziDatabase.getInstance(mContext);
                        //testing
//                        List<PortalSiblings> fromportalSiblingsList = db.getPortalSiblingsDao().getSiblings();
//                        String from = db.getPortalSiblingsDao().getMainStudentFromSibling();

                        db.getSwitchSiblingsDAO().setTheMainStudentToThisStudentIDPassed(sibling.getStudentID());
                        List<AuthenticateUserResponse> authenticateUser =  db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                        //testing
//                        List<PortalSiblings> toportalSiblingsList = db.getPortalSiblingsDao().getSiblings();
//                       String to = db.getPortalSiblingsDao().getMainStudentFromSibling();

                        return authenticateUser.get(0).getUserID();
                    }
                }.execute();




//                TextView name =  (TextView) v.findViewById(R.id.sibling_name);
//                Toast.makeText(mContext, "Move to \"" + name.getText().toString()+"\" Profile Account", Toast.LENGTH_SHORT).show();

                //finish the activity, then

//                if (internetConnection()) {
//
//                    if (countSiblingClicks == 1) {
//                        countSiblingClicks++;
//                        Test test = new Test();
//                        test.setTest(true);
//
//                        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
//
//                        Call<Boolean> userCall = apiInterface.testNetworkStability(test);
//                        userCall.enqueue(new Callback<Boolean>() {
//                            @Override
//                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//
//
//                                Log.d(mContext.getPackageName().toUpperCase(), "Network Testing: " + String.valueOf(response.body()));
//                                if (response.code() == 200) {
//
//                                    if (response.body()) {
//                                        new DeleteOtherSiblingsData((MainActivity) mContext, siblings.get(position)).execute();
//                                    }
//
//                                } else {
//                                    FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<Boolean> call, Throwable t) {
//                                //call.cancel();
//                                FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
//
//                            }
//                        });
//                    }
//
//
//                } else {
//                    FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
//
//                }
            }

        });


    }


    public boolean internetConnection() {
        final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }


    @Override
    public int getItemCount() {
        return siblings.size();
    }

    class BottomSheetHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView siblingName;
        CardView siblingCard;
        TextView regNumber, courseName;


        BottomSheetHolder(View itemView) {
            super(itemView);
            siblingCard = itemView.findViewById(R.id.sibling_cardid);
            siblingCard.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.sibling_image);
            siblingName = itemView.findViewById(R.id.sibling_name);
            regNumber = itemView.findViewById(R.id.reg_num);
            courseName = itemView.findViewById(R.id.course_name);

        }

        @Override
        public void onClick(View v) {
            if (itemListener != null) {
                itemListener.onBottomItemClick();
            }
        }

        void bind(PortalSiblings sibling) {
            imageView.setImageResource(R.drawable.student_registration);
            siblingName.setText("Name: " + sibling.getStudentFullName());
            regNumber.setText("Reg: " + sibling.getRegistrationNumber());
            courseName.setText("Course: " + sibling.getCourseName());

        }


    }

    //a user defined interface
    public interface BottomSheetItemListener {
        void onBottomItemClick();
    }


    private class DeleteOtherSiblingsData extends AsyncTask<Void, Void, Map<String, String>> {

        WeakReference<MainActivity> mainActivityWeakReference;
        PortalSiblings sibling;


        public DeleteOtherSiblingsData(MainActivity mainActivity, PortalSiblings sibling) {
            this.mainActivityWeakReference = new WeakReference<>(mainActivity);
            this.sibling = sibling;
        }


        @Override
        protected void onPreExecute() {


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final Map<String, String> isDeleted) {

            if (internetConnection()) {

//                mainActivityWeakReference.get().stopService(new Intent(mainActivityWeakReference.get(), NotificationService.class));
//                mainActivityWeakReference.get().finish();
                //FIXME: Replacement
                new FirebaseJobDispatcher(new GooglePlayDriver(mContext)).cancelAll();

                NotificationTopDisplay.giveContext(mainActivityWeakReference.get());
                NotificationTopDisplay.GetAuthenticatedUser.cancelNotificationDisplay(null);


                //delete all data of this student

                //will show what will happen
//                bottomSheet.hide();

                countSiblingClicks = 1;
                //send a request
                Intent intent = new Intent((mContext), LoginActivity.class);
                intent.putExtra(DisplayLayout.DO_NOT_DISPLAY_LAYOUT, "DO_NOT_DISPLAY_LAYOUT");
                intent.putExtra("StudentID", sibling.getStudentID());
                intent.putExtra("appcode", isDeleted.get("AppCode"));
                intent.putExtra("SchoolID",isDeleted.get("SchoolID"));
                intent.putExtra("OrganizationID",isDeleted.get("OrganizationID"));
                intent.putExtra("UserID",isDeleted.get("UserID"));
                intent.putExtra("Username",isDeleted.get("Username"));
                intent.putExtra("Password",isDeleted.get("Password"));
                (mContext).startActivity(intent);

            } else {
                FancyToast.makeText(mContext, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
            }

            super.onPostExecute(isDeleted);
        }

        public boolean internetConnection() {
            final ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


                return true;//connected

            }
            return false;
        }

        @Override
        protected Map<String, String> doInBackground(Void... strings) {
//
//            // PreferenceStorage.deleteAll();
////            PreferenceStorage.deleteThis("StudentID");
////            PreferenceStorage.deleteThis("appcode");
//
//            Map<String, String> isDeleted = new HashMap<>();
//
//            List<AuthenticateUserResponse> authenticatedUser = mainActivityWeakReference.get().db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//            try {
//                isDeleted.put("AppCode", authenticatedUser.get(0).getAppcode());
//                isDeleted.put("SchoolID", authenticatedUser.get(0).getSchoolID());
//                isDeleted.put("OrganizationID", authenticatedUser.get(0).getOrganizationID());
//
//                isDeleted.put("Username", authenticatedUser.get(0).getUsername());
//                isDeleted.put("Password", authenticatedUser.get(0).getPassword());
//
//            } catch (IndexOutOfBoundsException e) {
//                ///do nothing
//            }
////
//
//            int rowno = mainActivityWeakReference.get().db.getNotificationsDao().deleteNotifications();
//            int rowno1 = new NotificationDAO(mContext).deleteAllNotifications();
//
//            new SyncMyAccountDAO(mainActivityWeakReference.get()).deleteAllSyncMyAccountResult();
//
//            int rowno2 = mainActivityWeakReference.get().db.getPortalStudentInfoDao().deleteStudent();
//            int rowno3 = mainActivityWeakReference.get().db.getPortalDetailedTransactionDao().deletePortalDetailedTransaction();
//            int rowno4 = mainActivityWeakReference.get().db.getPortalStudentDetailedResultsDao().deletePortalStudentDetialedResults();
//            int rowno5 = mainActivityWeakReference.get().db.getPortalEventsDao().deletePortalEvents();
//            int rowno6 = mainActivityWeakReference.get().db.getPortalSiblingsDao().deleteSiblings();
//
//            //removed to have two records for each sibling, if switched for now
//            int rowno7 = mainActivityWeakReference.get().db.getAuthenticateUserResponseDao().deleteAuthenticateUserResponse();
//
//            int rowno8 = mainActivityWeakReference.get().db.getPortalContactsDao().deletePortalContacts();
//            int rowno9 = new NewCarriculumExamDAO(mainActivityWeakReference.get()).deleteAllNewCarriculumExamFormat();
//            int rowno10 = new ReadNotReadNotificationDAO(mainActivityWeakReference.get()).deleteReadNotReadNotification();
//            int rowno11 = new NotificationReadTrackingDAO(mainActivityWeakReference.get()).deleteAllNotificationsReadTracking();
//            int rownno12 = mainActivityWeakReference.get().db.getStudentClassAttendanceDAO().deleteStudentClassAttendance();
//            int rownno13 = mainActivityWeakReference.get().db.getParentChatDAO().deleteParentChat();
//            int rownno14 = mainActivityWeakReference.get().db.getGlobalSettingsDAO().deleteGlobalSettings();
////TODO: put this rowno for this one, good for debugging
//           int aa = mainActivityWeakReference.get().db.getPortalToDoListResponseDAO().deletePortalToDoListResponse();
//            int bb =  mainActivityWeakReference.get().db.getPortalDetailedToDoListResponseDAO().deletePortalDetailedToDoListResponse();
//            int cc = mainActivityWeakReference.get().db.getPortalRecentTransactionResponseDAO().deletePortalRecentTransactionResponse();
//           int dd = mainActivityWeakReference.get().db.getPortalStudentVisualizationAverageResponseDAO().deletePortalStudentVisualizationAverageResponse();
//            int ee =  mainActivityWeakReference.get().db.getPortalStudentVisualizationResponseDAO().deletePortalStudentVisualizationResponse();
//
//            int ff = mainActivityWeakReference.get().db.getPortalStudentResultsExtendedDAO().deletePortalStudentResultsExtended();
//
//            int gg = mainActivityWeakReference.get().db.getYoutubeVideoGalleryResponseDAO().deleteYoutubeVideoGalleryResponse();
//
//
//
//            return isDeleted;

            return null;

        }
    }


}
