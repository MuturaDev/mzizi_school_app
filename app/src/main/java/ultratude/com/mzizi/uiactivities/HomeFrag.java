package ultratude.com.mzizi.uiactivities;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.os.Handler;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.florent37.viewtooltip.ViewTooltip;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.joaquimley.faboptions.FabOptions;
import com.reginald.swiperefresh.CustomSwipeRefreshLayout;


//MPAndroid

import io.paperdb.Paper;


//IMPORTS FROM THE OTHER FRAGMENT
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import android.widget.ToggleButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.backgroundtasks.XFragRetrieveAndDisplayRoomData;
import ultratude.com.mzizi.chartsfragment.LineChartFragment;
import ultratude.com.mzizi.custom_swip_refresh.MyCustomHeadView;
import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
import ultratude.com.mzizi.helperactivityclasses.DueEventAdapter;
import ultratude.com.mzizi.helperactivityclasses.PortalToDoListResponseAdapter;
import ultratude.com.mzizi.helperactivityclasses.RecentTransactionAdapter;
import ultratude.com.mzizi.helperactivityclasses.StudentRecentResultsAdapter;
import ultratude.com.mzizi.helperactivityclasses.TimeTableRecyclerAdapter;
import ultratude.com.mzizi.modelclasses.DueEvents;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.modelclasses.HomeItem;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.multiplechoicequestions.MultipleChoiceQuestionsFragment;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalDetailedToDoListResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalSiblingsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentDetailedResultsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotification;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotificationDAO;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationCountViewModel;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.OrderItemsFragment2;
import ultratude.com.mzizi.webservice.APIRequest;


import java.util.ArrayList;

import static ultratude.com.mzizi.Utils.UtilityFunctions.getDummyDataForTimeTable;
import static ultratude.com.mzizi.Utils.UtilityFunctions.sortRawTimeTableData;


public class HomeFrag extends Fragment implements View.OnClickListener{

   public HomeFrag(){

    }

    private NotificationCountViewModel mModel;



    //dynamic views
    private CardView directmessageCardId;



    CardView feebalancecard,meanscorecard,upcomingeventscard, graphvisualizationcard;
    ImageButton attendance_CardId;
    public TextView feetext,upcomingeventstext, notification_count_id;
    private ProgressBar attendanceprogresstext;

    String mParam1;
    String mParam2;

    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public ParentMziziDatabase db;

    public TextView currentmeanscoretext_ID;

    private ImageView graph_meanscore_icon;
    private LinearLayout currentmeanscoreLayout;
    private RelativeLayout progress_report_layout;

    ProgressBar homelayout_progress;
    CoordinatorLayout home_layout_container;

    private CardView myExam_CardId;


    ///OTHER
    public RelativeLayout recentMeanScoreExpand,recentTransactionExpand,
            assignmentCommunicationExpand,currentExamVisualizationExpand,
            graphOfAveragePerformanceExpand, myExamExapand;
    private RelativeLayout recentMeanScoreCollapse,recentTransactionCollapse,
            assignmentCommunicationCollapse,currentExamVisualizationCollapse,
            graphOfAveragePerformanceCollapse,myExamCollapse;
    private ImageView expand1,expand2,expand3,expand4,expand5, expand6;
    private ToggleButton toggle1, toggle2, toggle3,toggle4,toggle5,toggle6;


    // private BarChart chart;

    private static boolean isExpnded = false;
    private static boolean isExpnded1 = false;
    private static boolean isExpnded2 = false;
    private static boolean isExpnded3 = false;
    private static boolean isExpnded4 = false;
    private static boolean isExpnded5 = false;

    private TextView progressText;




    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private  void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    //OTHER
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notification_actionbar_menu, menu);
    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
//outState.putString("notificationCount", notification_count_id.getText().toString());
        outState.putString("notificationCount", "11");

        super.onSaveInstanceState(outState);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view  = inflater.inflate(R.layout.activity_home_frag2, container, false);
        new XFragRetrieveAndDisplayRoomData(null, ((MainActivity) getActivity()), "").execute(GetConstants.GET_PORTAL_SIBLINGS);
        return view;
    }



    @Override
    public void onStart() {


//        if(recentMeanScoreCollapse.getVisibility() == View.VISIBLE){
//            expand1.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
//        }else{
//            expand1.setBackground(getActivity().getDrawable(R.drawable.up_icon));
//        }
//
//        if(recentTransactionCollapse.getVisibility() == View.VISIBLE){
//            expand2.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
//        }else{
//            expand2.setBackground(getActivity().getDrawable(R.drawable.up_icon));
//        }
//
//        if(assignmentCommunicationCollapse.getVisibility() == View.VISIBLE){
//            expand3.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
//        }else{
//            expand3.setBackground(getActivity().getDrawable(R.drawable.up_icon));
//        }
//
//        if(currentExamVisualizationCollapse.getVisibility() == View.VISIBLE){
//            expand4.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
//        }else{
//            expand4.setBackground(getActivity().getDrawable(R.drawable.up_icon));
//        }
//
//        if(graphOfAveragePerformanceCollapse.getVisibility() == View.VISIBLE){
//            expand5.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
//        }else{
//            expand5.setBackground(getActivity().getDrawable(R.drawable.up_icon));
//        }

        super.onStart();
    }


    private void checkWhatToExapandOrCollapse(){


//        Paper.book().write("Toggle1",true);
//        Paper.book().write("Toggle2",true);
//        Paper.book().write("Toggle3",true);



        if(Paper.book().contains("Toggle1")){
            if(Paper.book().read("Toggle1")){
                isExpnded = false;
                expandMeanScore();
                toggle1.setChecked(true);

            }else{
                isExpnded = true;
                expandMeanScore();
                toggle1.setChecked(false);

            }

        }

        if(Paper.book().contains("Toggle2")){
            if(Paper.book().read("Toggle2")){
                isExpnded1 = false;
                expandTransaction();
                toggle2.setChecked(true);
            }else{
                isExpnded1 = true;
                expandTransaction();
                toggle2.setChecked(false);
            }
        }

        if(Paper.book().contains("Toggle3")){
            if(Paper.book().read("Toggle3")){
                isExpnded2 = false;
                expandAssignment();
                toggle3.setChecked(true);
            }else{
                isExpnded2 = true;
                expandAssignment();
                toggle3.setChecked(false);
            }
        }


        if(Paper.book().contains("Toggle4")){
            if(Paper.book().read("Toggle4")){
                isExpnded3 = false;
                expandCurrentExamV();
                toggle4.setChecked(true);
            }else{
                isExpnded3 = true;
                expandCurrentExamV();
                toggle4.setChecked(false);
            }
        }

        if(Paper.book().contains("Toggle5")){
            if(Paper.book().read("Toggle5")){
                isExpnded4 = false;
                expandGraphAveragePerformance();
                toggle5.setChecked(true);
            }else{
                isExpnded4 = true;
                expandGraphAveragePerformance();
                toggle5.setChecked(false);
            }
        }

    }


    public static int getTimeDifference(String dateStart, String dateStop){

        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

        int returnMinutesDifference = 0;


        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            DateTime dt1 = new DateTime(d1);
            DateTime dt2 = new DateTime(d2);

            //System.out.print(Days.daysBetween(dt1, dt2).getDays() + " days, ");
            //System.out.print(Hours.hoursBetween(dt1, dt2).getHours() % 24 + " hours, ");
            //System.out.print(Minutes.minutesBetween(dt1, dt2).getMinutes() % 60 + " minutes, ");
            returnMinutesDifference = Minutes.minutesBetween(dt1, dt2).getMinutes() % 60;
            //System.out.print(Seconds.secondsBetween(dt1, dt2).getSeconds() % 60 + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnMinutesDifference;

    }

    private void expandMeanScore(){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });

        if(isExpnded ){
            //recentMeanScoreCollapse.setVisibility(View.GONE);
            collapse(recentMeanScoreCollapse);
            if(getActivity() != null) {
                expand1.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
                isExpnded = false;
            }

        }else{
            //recentMeanScoreCollapse.setVisibility(View.VISIBLE);
            expand(recentMeanScoreCollapse);
            if(getActivity() != null) {
                expand1.setBackground(getActivity().getDrawable(R.drawable.up_icon));
                isExpnded = true;
            }
        }
    }

    private void expandTransaction(){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });

        if(isExpnded1){
            collapse(recentTransactionCollapse);
            if (getActivity() != null){
                expand2.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
                isExpnded1  = false;
            }
        }else{
            expand(recentTransactionCollapse);
            if(getActivity() != null) {
                expand2.setBackground(getActivity().getDrawable(R.drawable.up_icon));
                isExpnded1 = true;
            }
        }
    }

    private void expandAssignment(){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });

        if(isExpnded2){
            collapse(assignmentCommunicationCollapse);
            if(getActivity() != null) {
                expand3.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
                isExpnded2 = false;
            }
        }else{
            expand(assignmentCommunicationCollapse);
            if(getActivity() != null) {
                expand3.setBackground(getActivity().getDrawable(R.drawable.up_icon));
                isExpnded2 = true;
            }
        }
    }

    private void expandCurrentExamV(){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_DOWN);
            }
        });

        if(isExpnded3){
            collapse(currentExamVisualizationCollapse);
            if(getActivity() != null) {
                expand4.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
                isExpnded3 = false;
            }

            //  scrollView.scrollTo(0, scrollView.getBottom());

        }else{
            expand(currentExamVisualizationCollapse);
            if(getActivity() != null) {
                expand4.setBackground(getActivity().getDrawable(R.drawable.up_icon));
                isExpnded3 = true;
            }
        }
    }

    private void expandGraphAveragePerformance(){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_DOWN);
            }
        });

        if(isExpnded4){
            collapse(graphOfAveragePerformanceCollapse);
            if(getActivity() != null) {
                expand5.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
                isExpnded4 = false;
            }


        }else{
            expand(graphOfAveragePerformanceCollapse);
            if(getActivity() != null) {
                expand5.setBackground(getActivity().getDrawable(R.drawable.up_icon));
                isExpnded4 = true;
            }

        }
    }

    private void expandMyExams(){
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_DOWN);
            }
        });

        if(isExpnded5){
            collapse(myExamCollapse);
            expand6.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
            isExpnded5 = false;
        }else{
            expand(myExamCollapse);
            if(getActivity() != null){
                expand6.setBackground(getActivity().getDrawable(R.drawable.up_icon));
                isExpnded5 = true;
            }
        }
    }

    private ScrollView scrollView;
    private FloatingActionsMenu actionsMenu;

    GridView     light_academy_home_ID;
    RelativeLayout lightHome, mziziHome;


    private void switchBetweenHomeUI(){

        //testing something
       Log.e(getContext().getPackageName().toUpperCase(), "First Time Run: " + UtilityFunctions.getFirstTimeRun(getContext()));
        Log.e(getContext().getPackageName().toUpperCase(), "Is First Install: " + UtilityFunctions.isFirstInstall(getContext()));
        Log.e(getContext().getPackageName().toUpperCase(), "Is Install From Update: " + UtilityFunctions.isInstallFromUpdate(getContext()));


        //This has to be set within the settings
        boolean lightUI = false;
        boolean mziziUI = true;
        if(Paper.book().read("LightUI") != null){
           lightUI =  Paper.book().read("LightUI");
        }

        if(Paper.book().read("MziziUI") != null){
           mziziUI =  Paper.book().read("MziziUI");
        }


        if(mziziUI){
            Paper.book().write("MziziUI", true);
        }


        if(lightUI){
            lightHome = view.findViewById(R.id.light_home);
            lightHome.setVisibility(View.VISIBLE);
            light_academy_home_ID  = view.findViewById(R.id.light_academy_home_ID);
            light_academy_home_ID.setVisibility(View.VISIBLE);
            lightUI();
        }

        if(mziziUI){
            mziziHome = view.findViewById(R.id.mzizi_home);
            mziziHome.setVisibility(View.VISIBLE);
            mziziUI();
        }

    }

    //The icon ui
    private void lightUI(){


        //TODO: Get data to make a choice on which display buttons to be visible
        //exam, regress report, optional fees and transports routes, siblings,
        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {



                Map<String, Object> map = (Map<String,Object>)o;


                final  List<HomeItem> homeItemList = new ArrayList<>();

                //SECTION 1
                HomeItem homeItem1 = new HomeItem(getResources().getDrawable(R.drawable.exam), "Results", true);
                homeItemList.add(homeItem1);

                if(map.get(Constants.PROGRESS_REPORT_ENABLED) != null){
                    PortalStudentInfo portalStudentInfo = (PortalStudentInfo) map.get(Constants.PROGRESS_REPORT_ENABLED);
                    if(portalStudentInfo.getClassStream().contains("GRADE") || portalStudentInfo.getClassStream().contains("Grade")){
                        HomeItem homeItem2 = new HomeItem(getResources().getDrawable(R.drawable.exam_icon2), "Progress Report", true);
                        homeItemList.add(homeItem2);
                    }
                }

                HomeItem homeItem4 = new HomeItem(getResources().getDrawable(R.drawable.progress_report2), "Performance Trends", true);
                homeItemList.add(homeItem4);

                HomeItem homeItem16 = new HomeItem(getResources().getDrawable(R.drawable.assigment), "Diary", true);
                homeItemList.add(homeItem16);

                //SECTION 2
                HomeItem homeItem3 = new HomeItem(getResources().getDrawable(R.drawable.event_in_calendar), "School Events", true);
                homeItemList.add(homeItem3);

                HomeItem homeItem5 = new HomeItem(getResources().getDrawable(R.drawable.parent_chat), "Notifications", true);
                homeItemList.add(homeItem5);

                HomeItem homeItem15 = new HomeItem(getResources().getDrawable(R.drawable.fee), "Transactions", true);
                homeItemList.add(homeItem15);

                //SECTION 3
                HomeItem homeItem6 = new HomeItem(getResources().getDrawable(R.drawable.class_attendance_icon), "Attendance", true);
                homeItemList.add(homeItem6);

                HomeItem homeItem12 = new HomeItem(getResources().getDrawable(R.drawable.sibling_main), "Profile", true);
                homeItemList.add(homeItem12);

                HomeItem homeItem13 = new HomeItem(getResources().getDrawable(R.drawable.school_contact_info_icon), "School Contact", true);
                homeItemList.add(homeItem13);

                //SECTION 4
//                HomeItem homeItem18 = new HomeItem(getResources().getDrawable(R.drawable.media_gallery_icon), "Video Gallery", true);
//                homeItemList.add(homeItem18);

                HomeItem homeItem14 = new HomeItem(getResources().getDrawable(R.drawable.settings), "Settings", true);
                homeItemList.add(homeItem14);

                HomeItem homeItem7 = new HomeItem(getResources().getDrawable(R.drawable.announcement_notification_icon), "About Mzizi School App", true);
                homeItemList.add(homeItem7);

                HomeItem homeItem8 = new HomeItem(getResources().getDrawable(R.drawable.order_items), "Order Items", true);
                homeItemList.add(homeItem8);

                HomeItem homeItem9 = new HomeItem(getResources().getDrawable(R.drawable.parent_portal), "Parent Web Portal", true);
                homeItemList.add(homeItem9);

                HomeItem homeItem10 = new HomeItem(getResources().getDrawable(R.drawable.settings_icon), "Settings", true);
                homeItemList.add(homeItem10);

                HomeItem homeItem11 = new HomeItem(getResources().getDrawable(R.drawable.borrowed_books_icon), "Borrowed Books", true);
                homeItemList.add(homeItem11);

                HomeItem homeItem122 = new HomeItem(getResources().getDrawable(R.drawable.school_trip), "School Trip", true);
                homeItemList.add(homeItem122);





//                Log.d(getActivity().getPackageName().toUpperCase(), "Optional Fee Activated: " + ((GlobalSettings) bindData.get(Constants.OPTIONAL_FEE_ENABLED)).getGlobalSettingsValue()
//                        + " Transports Routes Activated: " + ((GlobalSettings) bindData.get(Constants.TRANSPORT_FEE_ENABLED)).getGlobalSettingsValue());
//

                if(map.get(Constants.TRANSPORT_FEE_ENABLED) != null){
                    if(((GlobalSettings)map.get(Constants.TRANSPORT_FEE_ENABLED)).getGlobalSettingsValue().equalsIgnoreCase("YES")){
                        // transportRoutes.setVisibility(View.VISIBLE);
                        HomeItem homeItem = new HomeItem(getResources().getDrawable(R.drawable.transport_route_icon), "Transport Routes", false);
                        homeItemList.add(homeItem);
                    }
                }


                if(map.get(Constants.OPTIONAL_FEE_ENABLED) != null){
                    if(((GlobalSettings)map.get(Constants.OPTIONAL_FEE_ENABLED)).getGlobalSettingsValue().equalsIgnoreCase("YES")) {
                        //optionalFee.setVisibility(View.VISIBLE);
                        HomeItem homeItem = new HomeItem(getResources().getDrawable(R.drawable.optional_fee_icon), "Optional Fee", false);
                        homeItemList.add(homeItem);
                    }

                }


                HomeItem homeItem17 = new HomeItem(getResources().getDrawable(R.drawable.ic_happy), "Order Items", false);
                homeItemList.add(homeItem17);


//        HomeItem homeItem19 = new HomeItem(getResources().getDrawable(R.drawable.exam), "Exam", true);
//        homeItemList.add(homeItem19);


                GenericHomeGridUIAdapter adapter = new GenericHomeGridUIAdapter(getActivity(),removeUnVisibleItem(homeItemList));
                light_academy_home_ID.setAdapter(adapter);


                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                Map<String, Object> map = new HashMap<>();

                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }

                if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.OPTIONAL_FEE_ENABLED,Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.OPTIONAL_FEE_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.OPTIONAL_FEE_ENABLED,Integer.valueOf(studentid)).get(0));
                }

                if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.TRANSPORT_FEE_ENABLED,Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.TRANSPORT_FEE_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.TRANSPORT_FEE_ENABLED,Integer.valueOf(studentid)).get(0));

                }


                if(db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.PROGRESS_REPORT_ENABLED, db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid)).get(0));
                }



                return map;
            }
        };

        asyncTask.execute();




    }

    //The collapsible and expandable ui
    private void mziziUI(){

       // FrameLayout home_more_items = view.findViewById(R.id.home_more_items);

        try{
            Class fragmentClass = LineChartFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_line_chart, fragment).commit();

        }catch(Exception ex){
            ex.printStackTrace();
        }

        try {
            Class fragmentClass = HomeMoreIconsFragment.class;
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.home_more_items, fragment).commit();

        }catch(Exception ex){
            ex.printStackTrace();
        }


        toggle1 = view.findViewById(R.id.toggle1);
       // toggle1.setChecked(true);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    Paper.book().write("Toggle1", true);

                }else{
                    Paper.book().write("Toggle1", false);

                }
            }
        });
        toggle2 = view.findViewById(R.id.toggle2);
        //toggle2.setChecked(true);
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    Paper.book().write("Toggle2", true);

                }else{

                    Paper.book().write("Toggle2", false);

                }
            }
        });
        toggle3 = view.findViewById(R.id.toggle3);
        //toggle3.setChecked(true);
        toggle3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    Paper.book().write("Toggle3", true);

                }else{

                    Paper.book().write("Toggle3", false);

                }
            }
        });
        toggle4 = view.findViewById(R.id.toggle4);
        toggle4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    Paper.book().write("Toggle4", true);

                }else{

                    Paper.book().write("Toggle4", false);

                }
            }
        });
        toggle5 = view.findViewById(R.id.toggle5);
        toggle5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    Paper.book().write("Toggle5", true);

                }else{

                    Paper.book().write("Toggle5", false);

                }
            }
        });

        toggle6 = view.findViewById(R.id.toggle6);
        toggle6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    Paper.book().write("Toggle6", true);
                else
                    Paper.book().write("Toggle6", false);

            }
        });




        expand1 = view.findViewById(R.id.expand1);
        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandMeanScore();
            }
        });
        expand2 = view.findViewById(R.id.expand2);
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandTransaction();
            }
        });
        expand3 = view.findViewById(R.id.expand3);
        expand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandAssignment();
            }
        });
        expand4 = view.findViewById(R.id.expand4);
        expand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandCurrentExamV();
            }
        });
        expand5 = view.findViewById(R.id.expand5);
        expand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandGraphAveragePerformance();
            }
        });
        expand6 = view.findViewById(R.id.expand6);
        expand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // expandMyExams();
            }
        });

        myExam_CardId = view.findViewById(R.id.myExam_CardId);
        myExam_CardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragTransaction.dislayFragment(MultipleChoiceQuestionsFragment.class, "",fragmentManager);
            }
        });


        // toggle1, toggle2, toggle3,toggle4,toggle5;

        scrollView = view.findViewById(R.id.scrollview);
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(scrollView.FOCUS_UP);
            }
        });



        CardView  dueEventCard = view.findViewById(R.id.dueEventCard);
        dueEventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DUE EVENTS
               // final   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

//                //build ui with information
//                LayoutInflater inflater = getLayoutInflater();
//                View view = inflater.inflate(R.layout.time_table_view, null, false);

//                RecyclerView recyclerview_due_events =  view.findViewById(R.id.recyclerview_due_events);
//                recyclerview_due_events.setHasFixedSize(true);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                recyclerview_due_events.setLayoutManager(linearLayoutManager);
//
//                LinearLayout no_content_layout = view.findViewById(R.id.no_content_layout);

//                //TODO: Fetch data from the database now, below is the dummy data
//                //TODO: Show events that are within this week

//                List<DueEvents> dueEventsList = new ArrayList<>();
////                DueEvents events = new DueEvents("9 NOV 2019", "HOLIDAY BIBLE CAMP");
////                dueEventsList.add(events);
////                DueEvents events1 = new DueEvents("10 NOV 2019", "SCHOOL CLOSING");
////                dueEventsList.add(events1);
////                DueEvents events2 = new DueEvents("11 NOV 2019", "KCPE DATES");
////                dueEventsList.add(events2);
////                DueEvents events3 = new DueEvents("12 NOV 2019", "PARENTS VISITING");
////                dueEventsList.add(events3);
//
//                if(dueEventsList.size() > 0) {
//
//                    DueEventAdapter dueEventAdapter = new DueEventAdapter(dueEventsList, getActivity());
//                    recyclerview_due_events.setAdapter(dueEventAdapter);
//                    recyclerview_due_events.setVisibility(View.VISIBLE);
//                    no_content_layout.setVisibility(View.GONE);
//                }else{
//                    no_content_layout.setVisibility(View.VISIBLE);
//                    recyclerview_due_events.setVisibility(View.GONE);
//                }


               new AsyncTask(){
                   @Override
                   protected void onPostExecute(Object o) {
                       //TIME TABLE
                       final   AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                       //build ui with information
                       LayoutInflater inflater = getLayoutInflater();
     //                View view = inflater.inflate(R.layout.time_table_view, null, false);
                       View view = inflater.inflate(R.layout.time_table_view_recyclerview, null, false);

                       RecyclerView recyclerview_due_events = view.findViewById(R.id.time_table_recyclerview);
                       RelativeLayout top_layout = view.findViewById(R.id.top_layout);
                       top_layout.setVisibility(View.VISIBLE);

                      //List<TimeTableResponse> responseList = getDummyDataForTimeTable();

                       List<TimeTableResponse> responseList = (List<TimeTableResponse>)o;

                       ImageButton exitBtn = view.findViewById(R.id.exitBtn);
                       ImageView full_screen = view.findViewById(R.id.full_screen);

                       if(responseList.size() > 0){

                           List<List<TimeTableResponse>> list = sortRawTimeTableData(responseList);

                           int scrollToPosition = 0;

                           for(int i = 0; i<list.size(); i++){
                               if(list.get(i).size() > 0){
                                   scrollToPosition = i;
                                   break;
                               }
                           }
                           TimeTableRecyclerAdapter dueEventAdapter = new TimeTableRecyclerAdapter(list, getActivity());
                           recyclerview_due_events.setAdapter(dueEventAdapter);
                           recyclerview_due_events.smoothScrollToPosition(scrollToPosition);
                           view.findViewById(R.id.scroll_layout).setVisibility(View.VISIBLE);
                           view.findViewById(R.id.txt_no_content).setVisibility(View.GONE);

                       }else{
                           full_screen.setVisibility(View.GONE);
                           view.findViewById(R.id.scroll_layout).setVisibility(View.GONE);
                           view.findViewById(R.id.txt_no_content).setVisibility(View.VISIBLE);
                       }


                       builder.setView(view);

                       builder.setCancelable(true);
                       final AlertDialog alert = builder.create();
                       alert.getWindow().setGravity(Gravity.BOTTOM);
                       alert.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                       alert.show();

                       full_screen.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               startActivity(new Intent(getActivity(), TimeTableActivity.class));
                           }
                       });

                       exitBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               alert.cancel();
                           }
                       });
                       super.onPostExecute(o);
                   }

                   @Override
                   protected Object doInBackground(Object[] objects) {
                       ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity().getApplicationContext());
                       String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                       if(studentid == null){
                           studentid  = "0";
                       }
                       return db.getTimeTableResponseDAO().getTimeTableResponse(Integer.valueOf(studentid));
                   }
               }.execute();
            }
        });

        recentMeanScoreExpand = view.findViewById(R.id.recentMeanScoreExpand);
        recentMeanScoreCollapse = view.findViewById(R.id.recentMeanScoreCollapse);
        recentMeanScoreExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandMeanScore();
            }
        });
        //Transit between different home bars

        recentTransactionExpand = view.findViewById(R.id.recentTransactionExpand);
        recentTransactionCollapse = view.findViewById(R.id.recentTransactionCollapse);
        recentTransactionExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandTransaction();
            }
        });
        assignmentCommunicationExpand = view.findViewById(R.id.assignmentCommunicationExpand);




        assignmentCommunicationCollapse = view.findViewById(R.id.assignmentCommunicationCollapse);
        assignmentCommunicationExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               expandAssignment();
//                ViewTooltip
//                        .on(v)
//                        .autoHide(true, 1000)
//                        .corner(30)
//                        .position(ViewTooltip.Position.TOP)
//                        .text("Pull to refresh data")
//                        .show();

//                ViewTooltip
//                        .on(getActivity(),v)
//                        .color(Color.BLACK)
//                        .position(ViewTooltip.Position.TOP)
//                        .text("bottomRight bottomRight bottomRight")
//                        .show();
            }
        });
        currentExamVisualizationExpand = view.findViewById(R.id.currentExamVisualizationExpand);
        currentExamVisualizationCollapse = view.findViewById(R.id.currentExamVisualizationCollapse);
        currentExamVisualizationExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expandCurrentExamV();
            }
        });
        graphOfAveragePerformanceExpand = view.findViewById(R.id.graphOfAveragePerformanceExpand);
        graphOfAveragePerformanceCollapse = view.findViewById(R.id.graphOfAveragePerformanceCollapse);
        graphOfAveragePerformanceExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandGraphAveragePerformance();
            }
        });
        myExamExapand = view.findViewById(R.id.myExamExpand);
        myExamCollapse = view.findViewById(R.id.myExamCollapse);
        myExamExapand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //expandMyExams();
            }
        });

       // UtilityFunctions.checkIfStudentIfNotMakeWidgetInvisible(recentTransactionCollapse,recentTransactionExpand,feebalanceicon,feebalancelayout);

        //Preview Transitions
        //THIS DID NOT WORK BECOUSE ITS ON THE UI THREAD
        scheduleCollapseAndExpand();

        //RECENT RESULTS
        populateRecentTransaction();

        //RECENT TRANSACTION
        populateRecentMeanScore();

        //ASSINGMENT/COMMUNICATION
        populateAssingmentCommunication();

        //populateMyExam();

        checkWhatToExapandOrCollapse();

        graphvisualizationcard = view.findViewById(R.id.gragh_visualization_CardId);
        graphvisualizationcard.setOnClickListener(this);




    }


    List<HomeItem> removeUnVisibleItem(List<HomeItem> itemList){
        List<HomeItem> itemList1 = new ArrayList<>();
        for(HomeItem item : itemList){
            if(item.isItemVisible()){
                itemList1.add(item);
            }
        }

        return itemList1;
    }


    //ADAPTER
    private class GenericHomeGridUIAdapter extends BaseAdapter {
        private Context mContext;
        private List<HomeItem>  homeItemList;

        public GenericHomeGridUIAdapter(Context mContext, List<HomeItem> homeitemList){
            this.mContext = mContext;
            this.homeItemList = homeitemList;
        }

        @Override
        public int getCount() {
            return homeItemList.size();
        }

        @Override
        public Object getItem(int position) {
            return homeItemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View contentView, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.light_interface_item_layout, null, false);
            CardView homeItemID = view.findViewById(R.id.home_item_ID);

            homeItemID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String iconHeaderName = homeItemList.get(position).getItemName();

                    changeToScreen(iconHeaderName);

                    //Toast.makeText(getActivity(), "You Clicked on: " + homeItemList.get(position).getItemName(), Toast.LENGTH_SHORT).show();
                }
            });

            ImageView homeicon = view.findViewById(R.id.imageicon);
            Drawable drawable = (homeItemList.get(position)).getItemIcon();
            homeicon.setImageDrawable(drawable);
            TextView hometext = view.findViewById(R.id.hometext);
            hometext.setText(homeItemList.get(position).getItemName());


            return view;
        }
    }

    private ImageView feebalanceicon;
    private LinearLayout feebalancelayout;







    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Paper.init(getActivity());

        switchBetweenHomeUI();


//        FabOptions fabOptions = (FabOptions) view.findViewById(R.id.fab_options);
//        fabOptions.setButtonsMenu(R.menu.home_frag_bottom_menu);
//        fabOptions.setOnClickListener(this);




        progressText = view.findViewById(R.id.progress_text);

        progress_report_layout = view.findViewById(R.id.progress_report_layout);

        TextView term_information = view.findViewById(R.id.term_information);
        List<String> list =  UtilityFunctions.checkTermYearSchoolDates();
        if(list != null){
            if(list.size() > 0)
                term_information.setText("Term Info: " + list.get(0) + ", " + list.get(1));

             Log.d(getActivity().getPackageName().toUpperCase(),"Asset Json: " +  new UtilityFunctions(getActivity()).portalStudentVisualizationResponsesFromJsonLocalFiles().toString());
        }

        //OTHER

        getActivity().setTitle("Dashboard");

        home_layout_container = view.findViewById(R.id.home_layout_container);
        homelayout_progress = view.findViewById(R.id.homelayout_progress);

        currentmeanscoretext_ID = view.findViewById(R.id.currentmeanscoretext_ID);

        graph_meanscore_icon = view.findViewById(R.id.graph_meanscore_icon);
        graph_meanscore_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToScreen("Results");
            }
        });
        currentmeanscoreLayout = view.findViewById(R.id.currentmeanscoreLayout);
        currentmeanscoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToScreen("Results");
            }
        });

        feetext = view.findViewById(R.id.feetext_ID);
        upcomingeventstext = view.findViewById(R.id.upcomingeventstext_ID);
        attendanceprogresstext = view.findViewById(R.id.attendanceprogress_ID);
        notification_count_id = view.findViewById(R.id.notification_count_ID);


        //should be decleared here to prevent nulls
        no_content_text_meanscore = view.findViewById(R.id.no_content_text_meanscore);
        no_content_text_transaction = view.findViewById(R.id.no_content_text_transaction);

        AsyncTask asyncTask3 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                //List<Notification> list  =   new NotificationDAO(HomeFrag.this.getActivity()).getNotificationsList();

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
//                List<Notification> parentChatList = (List<Notification>)o;
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
//
//                int count = 0;
//
//                if(parentChatList != null){
//
//                   // Toast.makeText(HomeFrag.this.getActivity(), String.valueOf(parentChatList.get(0).getDateSent()), Toast.LENGTH_SHORT).show();
//                    Log.d(HomeFrag.this.getActivity().getPackageName().toUpperCase(),String.valueOf(parentChatList.get(0).getDateSent()));
//
//                    for(Notification notification :  parentChatList){
//                        String notificationDate = notification.getDateSent();
//                        String message = notification.getMsg().trim();
//
//                        if(message.contains("Chat") || message.contains("chat")){
//                            int dateDiff = getTimeDifference(notificationDate,sdf.format(calendar));
//                            if(dateDiff <= 1){
//                                count++;
//                            }
//                        }
//                    }
//                }

//


                super.onPostExecute(o);
            }
        };
        asyncTask3.execute();

        db = ParentMziziDatabase.getInstance(getContext());


        //Toast.makeText(getActivity(), "savedInstanceState: " + savedInstanceState == null ? "10" : savedInstanceState.getString("notificationCount"), Toast.LENGTH_SHORT).show();


        AsyncTask asyncTask2 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                List<String> termYear = UtilityFunctions.checkTermYearSchoolDates();

                if(termYear != null) {
                    String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                    if(studentid == null){
                        studentid  = "0";
                    }
                    List<StudentClassAttendance> studentClassAttendance =  db.getStudentClassAttendanceDAO().getStudentClassAttendance(termYear.get(1), termYear.get(0),Integer.valueOf(studentid));
                   return studentClassAttendance;
                }

                return new ArrayList<>();
            }

            @Override
            protected void onPostExecute(Object o) {



                List<StudentClassAttendance> list = (List<StudentClassAttendance>)o;
                if(list.size() > 0) {
                    double count = 0.0;

                    //For current data, what percentage of present attendance do we have
                    for (StudentClassAttendance studentClassAttendance : list) {
                        String attendanceStatus = studentClassAttendance.getStatus();
                        if (attendanceStatus.equalsIgnoreCase("PRESENT")) {
                            count++;
                        }
                    }

                    double percentCount = (count * 100.0) / list.size();

                    DecimalFormat df = new DecimalFormat("#");
                 //   Log.d(getActivity().getPackageName().toUpperCase(), "ProgressText " + percentCount);
                    //TODO: The best thing is to have the percentage calculated on the dates of each term...
                    // to get the attendance for that specific term.
                    //TODO:if the attendance for that term does not have data, then show the term previous to that
                    progressText.setText(df.format(Math.ceil(percentCount)) + "%" + " " + "Term 1" );
                    attendanceprogresstext.setProgress(Integer.parseInt(df.format(Math.ceil(percentCount))));
                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(attendanceprogresstext, "progress", 0, Integer.parseInt(df.format(Math.ceil(percentCount))));
                    progressAnimator.setDuration(1000);
                    progressAnimator.setInterpolator(new DecelerateInterpolator());
                    progressAnimator.start();
                }else{
                    attendanceprogresstext.setProgress(100);
                    progressText.setText("100%");
                    ObjectAnimator progressAnimator = ObjectAnimator.ofInt(attendanceprogresstext, "progress", 0, 100);
                    progressAnimator.setDuration(1000);
                    progressAnimator.setInterpolator(new DecelerateInterpolator());
                    progressAnimator.start();
                }
                super.onPostExecute(o);
            }
        };
        asyncTask2.execute();


        //while in the back ground problems occured
        List<ReadNotReadNotification> notReadNotificationList = new ReadNotReadNotificationDAO(HomeFrag.this.getActivity()).getReadNotReadNotification();
        notification_count_id.setText("(" + String.valueOf(notReadNotificationList.size()) + ")");
//        if(notReadNotificationList.size() > 0) {
//            notification_count_id.setTextColor(ResourceUtils.getColor(getActivity(), R.color.red));
//        }else{
//            notification_count_id.setTextColor(ResourceUtils.getColor(getActivity(), R.color.white));
//        }




        // notification_count_id.setText(FragTransaction.notificationCount);//should show the saved instance state
        // notification_count_id.setText( (PreferenceStorage.getThis("notificationCount").equals("")) ? "0" : PreferenceStorage.getThis("notificationCount"));

//        mModel = ViewModelProviders.of(this).get(HomeFrag.NotificationCountViewModel.class);
//        new NotificationDAO(new DatabaseConnectionOpenHelper(getActivity().getApplicationContext()).getWritableSQLiteConnection(),getActivity().getApplicationContext(),mModel);
//
//
//
//        if(new HomeFrag().isVisible()){
//            //notify  for the new messages
//            //create the observer which updates the UI
//            final Observer<String> nameObserver = new Observer<String>() {
//                @Override
//                public void onChanged(@Nullable String s) {
//                    //update the UI, in this case, a TextView
//                    notification_count_id.setText(s);
//                }
//            };
//
//            mModel.getCurrentCount().observe(this, nameObserver);
//        }



        //feebalancecard,meanscorecard,upcomingeventscard,attendancecard;
        feebalancecard = view.findViewById(R.id.feebalance_CardId);
        feebalancecard.setOnClickListener(this);
        meanscorecard = view.findViewById(R.id.meanscore_CardId);
        meanscorecard.setOnClickListener(this);
        upcomingeventscard = view.findViewById(R.id.upcomingevent_CardId);
        upcomingeventscard.setOnClickListener(this);


//        membershipCardId = (CardView) view.findViewById(R.id.membership_CardId);
//        membershipCardId.setOnClickListener(this);
        directmessageCardId = (CardView) view.findViewById(R.id.directmessages_CardId);
        directmessageCardId.setOnClickListener(this);
//        contactsCardsId =  view.findViewById(R.id.schoolContacts_CardId);
//        contactsCardsId.setOnClickListener(this);

        attendance_CardId = view.findViewById(R.id.attendance_CardId);
        attendance_CardId.setOnClickListener(this);

        //check if its the first boot up
//        SharedPreferences settings  = getSharedPreferences(MziziURLConstants.PREFERENCE_NAME, 0);
//        if(settings.getBoolean("my_first_time", true)){
        //if first time




        //new HomeFragRetrieveAndDisplayRoomDataTask(new HomeFrag()).execute();


        // settings.edit().putBoolean("my_first_time", false).commit();

//        }else{
//            final SharedPreferences pstore = getSharedPreferences(MziziURLConstants.STUD_PREF_KEY_NAME, 0);
//
//            if(pstore.getString("pref_studentID", null).equals(1)){
//                //get data and display
//                //you will call this each time the user is back to the app, if he/she had requested a user before
//                //if not, you have to request for the information again
//
//                //to use this first, we can obtain a SyncMyAccountResult object from the database to find if there is a user
//                //SyncMyAccountResult student = new SyncMyAccountResult(,,,,);
//                //String password = student.getPassword();
//                /*if(!password.isEmpty()){
//
//                *   new NavigationDrawerRetrieveAndDisplayRoomDataTask(ultratude.com.mzizi.uiactivities.HomeActivity.this).execute();
//                *   }*/
//
//            }else {
//
//                new NavigationDrawerRetrieveAndDisplayRoomDataTask(HomeFrag.this).execute();
//            }
//
//        }


        //this is just a test to send information to the MainActivity from this Fragment via interface.
//        btnfour = view.findViewById(R.id.btnfour);
//        btnfour.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onButtonPressed("Call Fragment One with this message");
//            }
//        });


        fragmentManager = getActivity().getSupportFragmentManager();
        super.onStart();


        //THIS SHOULD BE REMOVED

        //LIVE DATA FOR NOTIFICATION COUNT
        //get the view model
        // mModel  = ViewModelProviders.of(this).get(ultratude.com.mzizi.NotificationPG.DisplayNotification.NotificationCountViewModel.class);


        //create the observer which updates the ui
//        final Observer<String> notificationCountObserver = new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable final String count) {
//
//                int total = Integer.valueOf((notification_count_id.getText().toString().equals("") ) || (notification_count_id.getText().toString().equals(" ") ) ? "0" : notification_count_id.getText().toString()) + Integer.valueOf(count);
//
//                notification_count_id.setText(String.valueOf(total));//set to zero for test only
//            }
//        };


//        //Observe live data passing in this activity as the lifecycleOwner and the observer.
//        mModel.getCurrentNotificationCount().observe(this, notificationCountObserver); //you need to have this in the back ground
//        NotificationCountLiveData.getViewModel(mModel, getActivity());

        feebalanceicon = view.findViewById(R.id.feebalanceicon);
        feebalanceicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToScreen("Transactions");
            }
        });
        feebalancelayout = view.findViewById(R.id.feebalancelayout);
        feebalancelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToScreen("Transactions");
            }
        });



        new RetrieveDataHomeFrag(getActivity()).execute();

        //XFragRetrieveAndDisplayRoomData is removed and retrieval data is done in every fragment
        // showProgress(true);
        // new XFragRetrieveAndDisplayRoomData(this, new MainActivity(),"").execute(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT);
        //replaced with the code above
        //new HomeFragRetrieveAndDisplayRoomDataTask(this).execute();

        super.onViewCreated(view, savedInstanceState);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//
//
//
//        super.onActivityCreated(savedInstanceState);
//    }



    public void updateNotificationCount(int count){
//        if(isVisible())
        //notification_count_id.setText(count);
    }

    private int timerMax = 15000;


    private void meanscoreColEx() {
        //the error ocurring after using TIMER is,
        //Only the original thread that created a view hierarchy can touch its views.


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                collapse(graphOfAveragePerformanceCollapse);
                if(Paper.book().contains("Toggle3")) {
                    if (Paper.book().read("Toggle3")) {
                        isExpnded4 = false;
                    }else{
                        isExpnded4 = true;
                    }
                }else{
                    isExpnded4 = true;
                }


                expandGraphAveragePerformance();


                scrollView.post(new Runnable() {
                    public void run() {
                        scrollView.fullScroll(scrollView.FOCUS_UP);
                    }
                });



                expand(recentMeanScoreCollapse);
                isExpnded = false;
                expandMeanScore();


                recentranColEx();
            }
        }, timerMax);//6000

    }

    private void recentranColEx(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                collapse(recentMeanScoreCollapse);
                if(Paper.book().contains("Toggle1")) {
                    if (Paper.book().read("Toggle1")) {
                        isExpnded = false;
                    }else{
                        isExpnded = true;
                    }
                }else{
                    isExpnded = true;
                }

                expandMeanScore();

                        scrollView.post(new Runnable() {
                            public void run() {
                                scrollView.fullScroll(scrollView.FOCUS_UP);
                            }
                        });

                        expand(recentTransactionCollapse);
                isExpnded1 = false;
                expandTransaction();


                assignCommColEx();

            }
        }, timerMax);
    }

    private void assignCommColEx(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                        collapse(recentTransactionCollapse);
                if(Paper.book().contains("Toggle2")) {
                    if (Paper.book().read("Toggle2")) {
                        isExpnded1 = false;
                    }else{
                        isExpnded1 = true;
                    }
                }else{
                    isExpnded1 = true;
                }

                expandTransaction();


                        expand(assignmentCommunicationCollapse);
                isExpnded2 = false;
                expandAssignment();
                        examVisualColEx();

            }
        }, timerMax);
    }

    private void examVisualColEx(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                        collapse(assignmentCommunicationCollapse);
                if(Paper.book().contains("Toggle3")) {
                    if (Paper.book().read("Toggle3")) {
                        isExpnded2 = false;
                    }else{
                        isExpnded2 = true;
                    }
                }else{
                    isExpnded2 = true;
                }

                expandAssignment();

                        scrollView.post(new Runnable() {
                            public void run() {
                                scrollView.fullScroll(scrollView.FOCUS_DOWN);
                            }
                        });

                        expand(currentExamVisualizationCollapse);
                       isExpnded3 = false;
                        expandCurrentExamV();

                        avgPerfColEx();

            }
        }, timerMax);
    }

    private void avgPerfColEx(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                        collapse(currentExamVisualizationCollapse);
                if(Paper.book().contains("Toggle4")) {
                    if (Paper.book().read("Toggle4")) {
                        isExpnded3 = false;
                    }else{
                        isExpnded3 = true;
                    }
                }else{
                    isExpnded3 = true;
                }

               expandCurrentExamV();


                        scrollView.post(new Runnable() {
                            public void run() {
                                scrollView.fullScroll(scrollView.FOCUS_DOWN);
                            }
                        });

                        expand(graphOfAveragePerformanceCollapse);
                isExpnded4 = false;
                expandGraphAveragePerformance();

                        meanscoreColEx();

            }
        }, timerMax);
    }


    private void myExamColEx(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Paper.book().contains("Toggle5")){
                    if(Paper.book().read("Toggle5")){
                        isExpnded5 = false;
                    }else{
                        isExpnded5 = true;
                    }
                }else{
                    isExpnded5 = true;
                }

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(scrollView.FOCUS_DOWN);
                    }
                });

                isExpnded5 = false;



            }
        }, timerMax);
    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            home_layout_container.setVisibility(show ? View.GONE : View.VISIBLE);
            home_layout_container.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    home_layout_container.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            homelayout_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            homelayout_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    homelayout_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            homelayout_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            home_layout_container.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */

    public static HomeFrag newInstance(String param1, String param2, FragmentManager fragmentManager){
        HomeFrag.fragmentManager = fragmentManager;

        HomeFrag fragment = new HomeFrag();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //This is called when you click the cards in the activity
    @Override
    public void onClick(View view) {



        //create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;


        switch (view.getId()){

            case R.id.feebalance_CardId:

              changeToScreen("Transactions");

                break;
            case R.id.upcomingevent_CardId:
              changeToScreen("School Events");
                break;
            case R.id.gragh_visualization_CardId:
               changeToScreen("Performance Trends");
                break;
            case R.id.meanscore_CardId:
              changeToScreen("Results");
                break;

            case R.id.directmessages_CardId:

                changeToScreen("Notifications");

                break;
//            case R.id.schoolContacts_CardId:
//
//                //you can go back
//                FragTransaction.dislayFragment(SchoolContactsFrag.class,"", fragmentManager);
//                FloatingActionShow.showFloat(false);
//                FloatingActionShow.showSchoolChat(false);
//                // FragTransaction.setNotificationCount(notification_count_id.getText().toString());
//                // ( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
//                //FloatingActionShow.showFloat(false);
//
//                break;
            case R.id.attendance_CardId:
                changeToScreen("Attendance");
                break;
            case R.id.media_gallery_CardID:
                changeToScreen("Video Gallery");
                break;
            case R.id.order_items_CardID:
                changeToScreen("Order Items");
                break;
            case R.id.transport_CardID:
              changeToScreen("Transport Routes");
                break;
            case R.id.optional_fee_Card:
               changeToScreen("Optional Fee");
                break;
            case R.id.parent_web_portal_CardID:
                changeToScreen("Parent Web Portal");
                break;
            case R.id.settings_id:
                changeToScreen("Settings");

                break;


        }

    }


    @Override
    public void onPause() {

        showProgress(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {

        showProgress(false);



        super.onDestroy();
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String mystring);
    }



    private class RetrieveDataHomeFrag extends AsyncTask<Void,Void, Map<String, Object>>{

        Context context;

        public RetrieveDataHomeFrag(Context context){
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            showProgress(true);
            super.onPreExecute();
        }



        @Override
        protected void onPostExecute(Map<String, Object> returnResults) {

            //do everything in notificationdao, just pass in MainActivity context, model, view

            if(isAdded()){
                showProgress(false);
            }


           final PortalStudentInfo studentInfo =  (PortalStudentInfo) returnResults.get(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT);

            if(!studentInfo.getRegistrationNumber().equals(" ")){

               final NumberFormat myFormatOne = NumberFormat.getInstance();
                myFormatOne.setGroupingUsed(true);


                //FIXME: CHANGE TO THE BELOW CODE, CHECKING GRADE AND NOT IF THE BALANCE THERE IS A VALUE
               // feetext.setText((studentInfo.getBalance() == "") ? "" : myFormatOne.format(Double.valueOf(( studentInfo).getBalance())));

                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPostExecute(Object o) {
                        if(((List<PortalStudentDetailedResults>) o).size() > 0){
                            feetext.setText( myFormatOne.format(Double.valueOf(studentInfo.getBalance())));
                            currentmeanscoretext_ID.setText((studentInfo).getMeanScore()+"% ( "+ (studentInfo).getMeanGrade() + " )");
                        }else{
                            feetext.setText(myFormatOne.format(Double.valueOf(studentInfo.getBalance())));
                            graph_meanscore_icon.setImageDrawable(getResources().getDrawable(R.drawable.progress_report3));
                            currentmeanscoreLayout.setVisibility(View.GONE);
                            progress_report_layout.setVisibility(View.VISIBLE);

                        }

                        upcomingeventstext.setText("( " +(studentInfo).getEvents()+ " )");

                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {

                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                        PortalStudentDetailedResultsDao dao = db.getPortalStudentDetailedResultsDao();
                        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                        return dao.getPortalStudentDetailedResults(Integer.valueOf(studentid));
                    }
                };
                asyncTask.execute();


                //   mainActivityWeakReference.get().setTitle(((SyncMyAccountResult) objectCarrier).getSchoolName().toUpperCase());
                // attendanceprogresstext.setText(studentInfo.getAttendance() +" %");

            }
            super.onPostExecute(returnResults);
        }


        @Override
        protected Map<String, Object> doInBackground(Void... voids) {

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
            Map<String, Object>  returnResults = new HashMap<>();
            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }

            List<PortalStudentInfo> studentList = db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
            if(studentList.size() <1){

                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT, new PortalStudentInfo());
                return returnResults;

            }
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT, studentList.get(0));
            return returnResults;

            ///return null;
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {



        //   Toast.makeText(getActivity(), "OnCreate", Toast.LENGTH_LONG).show();

        //Check if the argumenets passed are null
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //to the student id, you have to create a back ground task

        SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(getActivity()).getFilteredPortalStudentInfoResult();
        String billingBalance = syncMyAccountResult.getBillingBalance();
        if(billingBalance.equals("")){//equal to an empty string
            //do nothing
        }else if(!billingBalance.equals("")){//not equal to an empty string

            if(Float.valueOf(billingBalance) > 0f){

                //  Toast.makeText(getActivity(), "greater: " + billingBalance, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(( getActivity().getBaseContext()), SyncMyAccount.class);
                intent.putExtra("StudentID", ( (MainActivity) getActivity()).student.getStudentID());
                intent.putExtra("appcode", ( (MainActivity) getActivity()).student.getAppcode());
                intent.putExtra("CALL_FROM", "FRAGMENT");//AUTHENTICATE_USER

                getActivity().startActivity(intent);
                getActivity().finish();

            }else{//If the balance is less than 0.0f, no need to

                // Toast.makeText(getActivity(), "lesser: " + billingBalance, Toast.LENGTH_LONG).show();
            }
        }

        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "HomeFrag Fragment");


        //mFirebaseAnalytics.setCurrentScreen(getActivity(), "Home Screen", null);

        super.onCreate(savedInstanceState);
    }



    //TODO: CANCEL THE PREVIOUS EVENT
    private void scheduleCollapseAndExpand(){
        meanscoreColEx();
    }

    private TextView no_content_text_meanscore;

    private void populateRecentMeanScore(){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                if(getView() == null)
                    return;


                RecyclerView recyclerview_due_events = view.findViewById(R.id.recylerview_recent_mean_score);
                List<PortalStudentResultsExtended> resultsList = (List<PortalStudentResultsExtended>) o;
                if(!resultsList.isEmpty()) {

                    recyclerview_due_events.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerview_due_events.setLayoutManager(linearLayoutManager);

                    //TODO: Fetch data from the database, add the download link


                    StudentRecentResultsAdapter dueEventAdapter = new StudentRecentResultsAdapter(resultsList, getActivity());
                    recyclerview_due_events.setAdapter(dueEventAdapter);
                    recyclerview_due_events.setVisibility(View.VISIBLE);
                    no_content_text_meanscore.setVisibility(View.GONE);


                }else{
                    recyclerview_due_events.setVisibility(View.GONE);
                    no_content_text_meanscore.setVisibility(View.VISIBLE);


                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return   db.getPortalStudentResultsExtendedDAO().getPortalStudentResultsExtended(Integer.valueOf(studentid));
            }
        };
        asyncTask.execute();
    }

    private TextView no_content_text_transaction;

    private void populateRecentTransaction(){

        AsyncTask asyncTask = new AsyncTask() {



            @Override
            protected void onPostExecute(Object o) {

                if(getView() == null)
                    return;

                if(getActivity() != null) {


                    RecyclerView recyclerview_recent_transaction = view.findViewById(R.id.recyclerview_recent_transaction);
                    List<PortalRecentTransactionResponse> resultsList2 = (List<PortalRecentTransactionResponse>) o;
                    if (!resultsList2.isEmpty()) {


                        recyclerview_recent_transaction.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
                        recyclerview_recent_transaction.setLayoutManager(linearLayoutManager2);

                        RecentTransactionAdapter dueEventAdapter1 = new RecentTransactionAdapter(resultsList2, getActivity());
                        recyclerview_recent_transaction.setAdapter(dueEventAdapter1);
                        recyclerview_recent_transaction.setVisibility(View.VISIBLE);
                        no_content_text_transaction.setVisibility(View.GONE);
                    } else {
                        no_content_text_transaction.setVisibility(View.VISIBLE);
                        recyclerview_recent_transaction.setVisibility(View.GONE);
                    }
                }


                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());

                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }

                return  db.getPortalRecentTransactionResponseDAO().getPortalRecentTransactionResponse(Integer.valueOf(studentid));

            }
        };
        asyncTask.execute();

    }

    private void populateAssingmentCommunication(){

        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {

                TextView no_content_text = view.findViewById(R.id.no_content_text);
                RecyclerView recyclerview_assignementCommunication = view.findViewById(R.id.recyclerview_assignementCommunication);
                List<PortalToDoListResponse> resultsList = (List<PortalToDoListResponse>) o;
                if(!resultsList.isEmpty()) {

                    recyclerview_assignementCommunication.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerview_assignementCommunication.setLayoutManager(linearLayoutManager);
                    PortalToDoListResponseAdapter portalToDoListResponseAdapter = new PortalToDoListResponseAdapter(resultsList, getActivity());
                    recyclerview_assignementCommunication.setAdapter(portalToDoListResponseAdapter);
                    recyclerview_assignementCommunication.setVisibility(View.VISIBLE);
                    no_content_text.setVisibility(View.GONE);
                }else{
                    recyclerview_assignementCommunication.setVisibility(View.GONE);
                    no_content_text.setVisibility(View.VISIBLE);
                }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                    ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                  return   db.getPortalToDoListResponseDAO().getPortalToDoListResponse(Integer.valueOf(studentid));

            }
        };
        asyncTask.execute();

    }




    private void changeToScreen(String screenName){

        switch(screenName){
            case "Progress Report":
                FragTransaction.dislayFragment(NewCarricullumActivity.class,"", fragmentManager);
                // floatbutton.setVisibility(View.INVISIBLE);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case "School Events":
                FragTransaction.dislayFragment(UpcomingEventsFrag.class,"", fragmentManager);
                //( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
                //FragTransaction.setNotificationCount(notification_count_id.getText().toString());
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case "Performance Trends":
                Intent intent = new Intent((getActivity()), ResultsVisualization.class);
                startActivity(intent);
                break;
            case "Notifications":
                FragTransaction.dislayFragment(NotificationFrag.class,"", fragmentManager);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case "Attendance":
                FragTransaction.dislayFragment(SchoolAttendaceFrag.class,   progressText.getText().toString(), fragmentManager);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case "About Mzizi School App":
                Intent intent1 = new Intent(getActivity(), AboutMziziApp.class);
                startActivity(intent1);
                break;
            case "Transport Routes":
                FragTransaction.dislayFragment(TransportRoutesFragment.class,   progressText.getText().toString(), fragmentManager);
                break;
            case "Optional Fee":
                FragTransaction.dislayFragment(OptionalFeesFragment.class,   progressText.getText().toString(), fragmentManager);
                break;
            case "Profile":
                    FragTransaction.dislayFragment(ProfileFragment.class, "", fragmentManager);
                break;
            case "School Contact":
                FragTransaction.dislayFragment(SchoolContactsFrag.class,"", fragmentManager);
                // floatbutton.setVisibility(View.INVISIBLE);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case "Video Gallery":
                Intent intent3 = new Intent( getActivity(), GalleryActivity.class);
                getActivity().startActivity(intent3);

                break;
            case "Settings":
                FragTransaction.dislayFragment(SettingsFragment.class, "",fragmentManager);
                break;
            case "Transactions":
                FragTransaction.dislayFragment(FeeBalanceFrag.class,"", fragmentManager);
                // FragTransaction.setNotificationCount(notification_count_id.getText().toString());
                // ( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case "Assignment/Communication":
                FragTransaction.dislayFragment(DiaryFragment.class,"", fragmentManager);

                //Testing Section Header Recycler
//                Intent intent2 = new Intent(getActivity(), SectionRecyclerExample.class);
//                startActivity(intent2);
                break;
            case "Diary":
                FragTransaction.dislayFragment(DiaryFragment.class,"", fragmentManager);

                //Testing Section Header Recycler
//                Intent intent2 = new Intent(getActivity(), SectionRecyclerExample.class);
//                startActivity(intent2);
                break;
            case "Results":
                //check if there are results
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        String studentid = ((MainActivity)HomeFrag.this.getActivity()).db.getPortalSiblingsDao().getMainStudentFromSibling();
                        if(studentid == null){
                            studentid  = "0";
                        }
                        List<PortalStudentDetailedResults> studentDetailedResults = ((MainActivity)HomeFrag.this.getActivity()).db.getPortalStudentDetailedResultsDao().getPortalStudentDetailedResults(Integer.valueOf(studentid));

                        return studentDetailedResults;
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        Log.d(getActivity().getPackageName().toUpperCase(), "Has Detailed Results: " +((List<PortalStudentDetailedResults>) o).size() );

                        if(((List<PortalStudentDetailedResults>) o).size() > 0){

                            FragTransaction.dislayFragment(MeanScoreFrag.class,"", fragmentManager);
                            //( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
                            // FragTransaction.setNotificationCount(notification_count_id.getText().toString());
                            FloatingActionShow.showFloat(false);
                            FloatingActionShow.showSchoolChat(false);

                        }else{
                            FragTransaction.dislayFragment(NewCarricullumActivity.class,"", fragmentManager);
                            // floatbutton.setVisibility(View.INVISIBLE);
                            FloatingActionShow.showFloat(false);
                            FloatingActionShow.showSchoolChat(false);
                        }

                        super.onPostExecute(o);
                    }
                };
                asyncTask.execute();
                break;
            case "Order Items":
                    getActivity().setTitle("Order Items");
                 FragTransaction.dislayFragment(OrderItemsFragment2.class, "", fragmentManager);
                 break;
            case "Parent Web Portal":
                startActivity(new Intent(getActivity(), MziziParentWebPortal.class));
                break;

            case "Parent Chat":

                FragTransaction.dislayFragment(PortalChatsFragment.class, "",fragmentManager);

                break;
            case "Borrowed Books":
                FragTransaction.dislayFragment(BorrowedBooksFragment.class, "",fragmentManager);
                break;
            case "School Trip":
                FragTransaction.dislayFragment(SchoolTripFragment.class, "",fragmentManager);
                break;


        }

    }




}