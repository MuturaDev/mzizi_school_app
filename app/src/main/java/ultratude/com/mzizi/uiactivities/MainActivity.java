package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.adriangl.overlayhelper.OverlayHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.reginald.swiperefresh.CustomSwipeRefreshLayout;

import org.apache.harmony.awt.datatransfer.DataSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.backgroundtasks.DeleteThisStudentData;
import ultratude.com.mzizi.backgroundtasks.NavigationDrawerRetrieveAndDisplayRoomDataTask;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.custom_swip_refresh.MyCustomHeadView;
import ultratude.com.mzizi.firebasejobdispatch.FirebaseJobDispatch;
import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
import ultratude.com.mzizi.floating_view.ChatHeadService;
import ultratude.com.mzizi.floating_view.CustomFloatingViewService;
import ultratude.com.mzizi.helperactivityclasses.BottomSheetRecyclerAdapter;
//import ultratude.com.mzizi.HelperActivityClasses.DataTransferReceiverBroadcast;
import ultratude.com.mzizi.helperactivityclasses.StudentNotificationDialogAdapter;
import ultratude.com.mzizi.modelclasses.DisplayLayout;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.modelclasses.StudentNotification;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
//import ultratude.com.mzizi.notificationpg.NotificationBroadcast.NotificationService;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.Test;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.OrderItemsFragment2;
import ultratude.com.mzizi.webservice.APIRequest;
import ultratude.com.staff.activities.MziziVersionControlActivity;
import ultratude.com.staff.customer_support.activities.CustomerDashboard;
import ultratude.com.staff.customer_support.activities.ProfileFillUpActivity;
import ultratude.com.staff.customer_support.helper.FireBaseReferenceHelper;
import ultratude.com.staff.customer_support.helper.PostUserToFirebase;
import ultratude.com.staff.customer_support.helper.SharedPreferenceHelper;
import ultratude.com.staff.customer_support.helper.ToastHelper;
import ultratude.com.staff.customer_support.helper.UniqueIDGenerator;
import ultratude.com.staff.customer_support.helper.UserRegistration;
import ultratude.com.staff.customer_support.interfaces.UserRegistrationListener;
import ultratude.com.staff.customer_support.models.UserDetail;
import ultratude.com.staff.customer_support.util.Config;
import ultratude.com.staff.customer_support.util.TimeStamp;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;

import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFrag.OnFragmentInteractionListener, UserRegistrationListener {



    public DrawerLayout drawer;
    public Toolbar toolbar;
    private NavigationView navigationView;

    private ActionBarDrawerToggle toggle;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;

    //should be set

    public BottomSheetDialog bottomSheetDialog;

    public RecyclerView recyclerView;
    public RecyclerView recyclerViewSibling;
    public GridLayoutManager gridLayoutManager;
    public BottomSheetRecyclerAdapter bottomrecycler;
    public View sheet;

    public ParentMziziDatabase db;




    public Student student;
    //thise instance are used by GetXInstanceFromRoomDB classes to retrive instnance of there objects
    public PortalStudentInfo portalStudentInfoInstance;



    //main activity
    public TextView studentName,registrationNumber,schoolName, schoolMoto, portalBalance;
    public RelativeLayout other_siblings_layout_clickable;
    public CircleImageView sibling_one;


    public de.hdodenhof.circleimageview.CircleImageView studentProfilePhoto;


    public String studentID;
    public String appcode;



    static int count = 0;

    public static Context context;


    public ProgressBar content_main_progress;
    public ConstraintLayout content_main;

    public OverlayHelper overlayHelper;

    //FLOATING VIEW
    private static final int CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE = 100;

    private static final int CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE = 101;

    @Override
    //@TargetApi(Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(this, false, false);
        } else if (requestCode == CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE) {
            showFloatingView(this, false, true);
        }

        if(requestCode == 9876) {
            overlayHelper.onRequestDrawOverlaysPermissionResult(requestCode);

            showFloatingView();

            //moved to MainActivities onActivityResult
            //launch zoom client from your app
            PackageManager pm = this.getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage("us.zoom.videomeetings");
            if(intent != null){
                startActivity(intent);
            }else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("zoomus://"));
                if(browserIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(browserIntent);
            }
            //impement here
        }

        super.onActivityResult(requestCode,resultCode,data);

    }


    /**
     * FloatingViewの表示
     *
     * @param context                 Context
     * @param isShowOverlayPermission 表示できなかった場合に表示許可の画面を表示するフラグ
     * @param isCustomFloatingView    If true, it launches CustomFloatingViewService.
     */
    @SuppressLint("NewApi")
    public void showFloatingView(Context context, boolean isShowOverlayPermission, boolean isCustomFloatingView) {
        // API22以下かチェック
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startFloatingViewService(this, isCustomFloatingView);
            return;
        }

        // 他のアプリの上に表示できるかチェック
        if (Settings.canDrawOverlays(context)) {
            startFloatingViewService(this, isCustomFloatingView);
            return;
        }

        // オーバレイパーミッションの表示
        if (isShowOverlayPermission) {
            final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            startActivityForResult(intent, isCustomFloatingView ? CUSTOM_OVERLAY_PERMISSION_REQUEST_CODE : CHATHEAD_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }


    /**
     * Start floating view service
     *
     * @param activity             {@link Activity}
     * @param isCustomFloatingView If true, it launches CustomFloatingViewService.
     */
    private static void startFloatingViewService(Activity activity, boolean isCustomFloatingView) {
        // *** You must follow these rules when obtain the cutout(FloatingViewManager.findCutoutSafeArea) ***
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 1. 'windowLayoutInDisplayCutoutMode' do not be set to 'never'
            if (activity.getWindow().getAttributes().layoutInDisplayCutoutMode == WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER) {
                throw new RuntimeException("'windowLayoutInDisplayCutoutMode' do not be set to 'never'");
            }
        }

        // launch service
        final Class<? extends Service> service;
        final String key;
        if (isCustomFloatingView) {
            service = CustomFloatingViewService.class;
            key = CustomFloatingViewService.EXTRA_CUTOUT_SAFE_AREA;
        } else {
            service = ChatHeadService.class;
            key = ChatHeadService.EXTRA_CUTOUT_SAFE_AREA;
        }
        final Intent intent = new Intent(activity, service);
        intent.putExtra(key, FloatingViewManager.findCutoutSafeArea(activity));
        ContextCompat.startForegroundService(activity, intent);
    }

    //FLOATING VIEW


    public void showFloatingView(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final String channelId = getString(R.string.default_floatingview_channel_id);
            final String channelName = getString(R.string.default_floatingview_channel_name);
            final NotificationChannel defaultChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN);
            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(defaultChannel);
            }
        }


//                       //JUST HERE FOR REFERENCE
//                       rootView.findViewById(R.id.show_demo).setOnClickListener(new View.OnClickListener() {
//                           @Override
//                           public void onClick(View v) {
//                               showFloatingView(getActivity(), true, false);
//                           }
//                       });
//                       // カスタマイズデモの表示
//                       rootView.findViewById(R.id.show_customized_demo).setOnClickListener(new View.OnClickListener() {
//                           @Override
//                           public void onClick(View v) {
//                               showFloatingView(getActivity(), true, true);
//                           }
//                       });
//                       // 設定画面の表示
//                       rootView.findViewById(R.id.show_settings).setOnClickListener(new View.OnClickListener() {
//                           @Override
//                           public void onClick(View v) {
//                               final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                               ft.replace(R.id.container, FloatingViewSettingsFragment.newInstance());
//                               ft.addToBackStack(null);
//                               ft.commit();
//                           }
//                       });
        showFloatingView(this, true, false);
    }


    //TODO: REFRESH DATA
    public void onRefreshComplete() {
//        mListAdapter.clear();
//        for (String cheese : result) {
//            mListAdapter.add(cheese);
//        }
//        // return to the first item
//        mListView.setSelection(0);
        // to notify CustomSwipeRefreshLayout that the refreshing is completed
        //FIXME: fixed https://github.com/xyxyLiu/SwipeRefreshLayout/issues/16
        mCustomSwipeRefreshLayout.refreshComplete();

    }

    private void initiateRefresh() {
        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {

                List<String> list = (List<String>)o;
                if (list.size() == 4) {
                    String studentID = list.get(0);
                    String appcode = list.get(1);
                    String organization = list.get(2);
                    String notificationLastID = list.get(3);

                    Student student = new Student(studentID, appcode);
                    student.setOrganizationID(organization);
                    RequestFor.refresh = true;
//                    builder  = new ProgressDialog(MainActivity.this);
//                    builder.setCancelable(false);
//                    builder.setMessage("Please wait, refreshing app's data... will restart shortly");
//                    builder.show();
                    RequestFor.sendRequest(student, MainActivity.this, notificationLastID, APIRequest.ALL);


                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                ParentMziziDatabase db = ParentMziziDatabase.getInstance(MainActivity.this.getApplicationContext());

                List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();


                List<Notification> notificationList = new NotificationDAO(MainActivity.this.getApplicationContext()).getNotificationsList();

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
        };
        asyncTask.execute();
    }

    public TextView txt_check_internet_connection;
    public LinearLayout check_internet_connection_layout;

    public void checkInternetConnection(){

        ConnectivityManager connManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = connManager.getActiveNetworkInfo();
        boolean isWifi = current != null && current.getType() == ConnectivityManager.TYPE_WIFI;

        if(false){//FIXME: MADE FALSE SINCE ITS HAVING ISSUES
            new AsyncTask() {
                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    Boolean returnObject = (Boolean)o;
                    if(!returnObject) {
                        txt_check_internet_connection.setText("You will need internet connection for some services to work");
                        check_internet_connection_layout.setBackgroundResource(R.color.red);
                    }else{
                        txt_check_internet_connection.setText("Pull any part of the view to refresh");
                        check_internet_connection_layout.setBackgroundResource(R.color.colorPrimary);
                    }
                    super.onPostExecute(o);
                }

                @Override
                protected Object doInBackground(Object[] objects) {
                    return UtilityFunctions.isConnected(MainActivity.this);
                }
            }.execute();
        }else{
            Test test = new Test();
            test.setTest(true);
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<Boolean> userCall = apiInterface.testNetworkStability(test);
            userCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.code() == 200) {
                        txt_check_internet_connection.setText("Pull any part of the view to refresh");
                        check_internet_connection_layout.setBackgroundResource(R.color.colorPrimary);
                    }else{
                        txt_check_internet_connection.setText("You will need internet connection for some services to work");
                        check_internet_connection_layout.setBackgroundResource(R.color.red);
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    txt_check_internet_connection.setText("You will need internet connection for some services to work");
                    check_internet_connection_layout.setBackgroundResource(R.color.red);
                }
            });
        }
    }


    /**
     * The CustomSwipeRefreshLayout that detects swipe gestures and
     * triggers callbacks in the app.
     */
    public static CustomSwipeRefreshLayout mCustomSwipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //https://github.com/alhazmy13/Catcho
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();




        setContentView(R.layout.activity_main);

        context  = this;
        FragTransaction.transactionActivity = this;

        //RATE THE APP
        //ReviewManager manager = ReviewManagerFactory.create(context)

        txt_check_internet_connection = findViewById(R.id.txt_check_internet_connection);
        check_internet_connection_layout  = findViewById(R.id.check_internet_connection_layout);





        //REFRESH ANIMATION
        mCustomSwipeRefreshLayout = (CustomSwipeRefreshLayout) findViewById(R.id.swipelayout);
        // Set a custom HeadView. use default HeadView if not provided
        mCustomSwipeRefreshLayout.setCustomHeadview(new MyCustomHeadView(MainActivity.this));


        //FIXME: THE CODE BELOW SOLVE THIS ERROR: java.lang.IllegalArgumentException: Invalid Layer Save Flag - only ALL_SAVE_FLAGS is allowed swip refresh
        mCustomSwipeRefreshLayout.enableTopProgressBar(false);


        //mCustomSwipeRefreshLayout.setRefreshMode(CustomSwipeRefreshLayout.REFRESH_MODE_SWIPE);
        //text = "swipe refresh mode";

        //mCustomSwipeRefreshLayout.setRefreshMode(CustomSwipeRefreshLayout.REFRESH_MODE_PULL);
        //text = "pull refresh mode";

        // mCustomSwipeRefreshLayout.setKeepTopRefreshingHead(true);
        // text = "fixed refreshing head";

        //mCustomSwipeRefreshLayout.setKeepTopRefreshingHead(false);
        // text = "movable refreshing head";

        // set onRefresh listener
        mCustomSwipeRefreshLayout.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // do something here when it starts to refresh
                initiateRefresh();

            }
        });


        overlayHelper = new OverlayHelper(context.getApplicationContext(), new OverlayHelper.OverlayPermissionChangedListener() {
            @Override public void onOverlayPermissionCancelled() {
                Toast.makeText(context, "Draw overlay permissions request canceled", Toast.LENGTH_SHORT).show();
            }

            @Override public void onOverlayPermissionGranted() {
                Toast.makeText(context, "Draw overlay permissions request granted", Toast.LENGTH_SHORT).show();
            }

            @Override public void onOverlayPermissionDenied() {
                Toast.makeText(context, "Draw overlay permissions request denied", Toast.LENGTH_SHORT).show();
            }
        });

        overlayHelper.startWatching();

        //subscribe for an FCM topic
        FirebaseMessaging.getInstance().subscribeToTopic("Parent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //String msg = getString(R.string.msg_subscribed);
                        if (task.isSuccessful()) {
                            Log.d("MyFirebaseMessagingS", "Subscription to Topic Parent, is a success");
                            //   msg = getString(R.string.msg_subscribe_failed);
                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FloatingActionShow.getFloatInstance(MainActivity.this);

        toolbar =  findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.vector_menu_navigation));
        setSupportActionBar(toolbar);


        //the RoomDatabase
        db = ParentMziziDatabase.getInstance(this);

        // floatbutton =  findViewById(R.id.fab);






        //display the home fragment first
        fragmentManager = getSupportFragmentManager();
        //remove all previous fragments
        fragmentTransaction = fragmentManager.beginTransaction();

        content_main = findViewById(R.id.content_main);
        content_main_progress = findViewById(R.id.content_main_progress);





        setTitle("Home");


        if(!getIntent().equals(null)){
            studentID = getIntent().getExtras().getString("StudentID");
            appcode = getIntent().getExtras().getString("appcode");

//            PreferenceStorage.getPrefInstance(this);
//            studentID = PreferenceStorage.getThis("StudentID");
//            appcode = PreferenceStorage.getThis("appcode");
        }


        student = new Student(studentID, appcode);





        //FIXME: This is old code, was replaced with Firebase Job Dispatcher
//        //start the service and remember to send data
//        Intent d = new Intent(this, NotificationService.class);
//        d.putExtra("StudentID",studentID);
//        d.putExtra("appcode",appcode);
//        startService(d);

        new FirebaseJobDispatch(this.getApplicationContext()).startDispatch(student);




        //Bottom sheet
        sheet = getLayoutInflater().inflate(R.layout.home_bottom_sheet_layout, null, true);

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(sheet);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);


        recyclerView =  sheet.findViewById(R.id.recyclerviewbottomsheet);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(MainActivity.this,2);

        recyclerView.setLayoutManager(gridLayoutManager);






        //Was just for tests
        // Toast.makeText(this, student +" "+ appcode, Toast.LENGTH_LONG ).show();

        boolean isLogin = false;

        if(getIntent() != null){
            isLogin = getIntent().getExtras().getBoolean("isLog_In");
        }

        if(isLogin == true){
            //THE FIRST TIME YOU LOGIN IN, YOU HAVE TO RETRIEVE A STUDENT,
            //WHEN YOU LOG OUT, ALL THE INFORMATION ABOUT THE STUDENT IS DELETED AND YOU HAVE TO LOG IN AGAIN, WITH THE STAFF_ID YOU WANT TO LOG IN WITH.
            //WHEN YOU EXIT THE APP, AND GO BACK AGAIN, TEH APP WILL CHECK IF THERE IS SyncMyAccountResult in the ROOMDATABASE.
            //IF THERE IS, YOU WILL DISPLAY THE INFORMAITON


            //HOW FREQUENT WIL YOU REFRESH THE INFORMATION...
            /*YOU CAN PLACE A BUTTON TO REFRESH
             * -YOU CAN HAVE A BROADCAST TO FIREUP IF THERE IS INTERNET AND REQUEST FOR UPDATES.
             * -YOU CAN HAVE AN ENDPOINT AT MZIZI THAT REQUESTED WHEN THERE IS AVAILABILITY OF INTERNET, TO CHECK FOR UPDATES.
             *   -THIS ACTION METHOD WILL FUNCTION LIKE THIS:
             *       -set true for a updateStatus or column in a database or .....*/



            //we can have a refresh button in actionbar;




            //String studentID, int msgID,String appcode
            // new GetXInstanceFromRoomDB(MainActivity.this).execute(new ExamHistory());

            // Toast.makeText(this, "MainActivity: " + portalStudentInfoInstance , Toast.LENGTH_LONG).show();


        }else{
            //do nothing
        }






        //FloatingActionButton
        //remember this you have to change





        //Drawable
        //find our drawer view
        drawer =  findViewById(R.id.drawer_layout);
        //NOTE: make sure you pass in a valid toolbar reference.
        //ActionBarDrawToggle() does not require it and  will not render the hamburger icon without it
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        populateHomeView(navigationView);

        //populate its view

        new NavigationDrawerRetrieveAndDisplayRoomDataTask(MainActivity.this).execute();
        //showcase();


        Paper.init(this);
        if(Paper.book().contains("FCMLoggedInYet")) {
            Paper.book().write("FCMLoggedInYet", "1");
        }


        if(getIntent() != null){
            if(getIntent().getExtras() != null){
                if(getIntent().getExtras().containsKey("OpenNotifications")){
                    FragTransaction.dislayFragment(NotificationFrag.class,"", fragmentManager);
                    FloatingActionShow.showFloat(false);
                    FloatingActionShow.showSchoolChat(false);
                }
            }
        }

        if(getIntent() == null){
            //do nothing
            //this should not happen

        }else{


            if(getIntent().getExtras().containsKey(OpenFragments.OPEN_HOME_FRAGMENT)){

                // Toast.makeText(this, getIntent().getExtras().getString(OpenFragments.OPEN_HOME_FRAGMENT), Toast.LENGTH_LONG).show();

                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                FragTransaction.dislayFragment(HomeFrag.class,"", fragmentManager);


                //floatbutton.setVisibility(View.VISIBLE);



//                Fragment fragment = HomeFrag.newInstance(null, null, fragmentManager);
//                fragmentTransaction.add(R.id.fragment_transaction, fragment,"HomeFrag" ).commit();
            }//else if(getIntent().getExtras().containsKey(OpenFragments.OPEN_NOTIFICATION_FRAGMENT))


            final  String StudentID = getIntent().getExtras().getString("StudentID");
            if(getIntent().getExtras().getSerializable("NewNotificationsMapHolder") != null){


                new AsyncTask(){

                    @Override
                    protected void onPostExecute(Object o) {
                        final   AlertDialog.Builder builder = new AlertDialog.Builder(context);

                        //build ui with information
                        LayoutInflater inflater = getLayoutInflater();
                        View view = inflater.inflate(R.layout.choose_student_to_view_notifications_layout, null, false);


                        RecyclerView recyclerview_due_events =  view.findViewById(R.id.rv_student_notification);
                        recyclerview_due_events.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        recyclerview_due_events.setLayoutManager(linearLayoutManager);

                        CardView exitBtn = view.findViewById(R.id.cancel_btn);

                        HashMap<Integer, List<Notification>> map = NotificationTopDisplay.studentNotificationHolderMap;

                        //if this is used... the map does not update for notifications removed.
                        // HashMap<Integer, List<Notification>> map =  (HashMap<Integer, List<Notification>>) getIntent().getExtras().getSerializable("NewNotificationsMapHolder");

                        List<StudentNotification> studentNotificationList = new ArrayList<>();

                        for(PortalSiblings res : (List<PortalSiblings>)o){
                            if(map.containsKey(Integer.valueOf(res.getStudentID()))){

                                int count =  map.get(Integer.valueOf(res.getStudentID())).size();

                                studentNotificationList.add(new StudentNotification(
                                        res.getStudentID(),
                                        res.getStudentFullName(),
                                        res.getCourseName(),
                                        res.getRegistrationNumber(),
                                        String.valueOf(count)));
                            }
                        }


//
                        if(studentNotificationList.size() > 0) {

                            StudentNotificationDialogAdapter dueEventAdapter = new StudentNotificationDialogAdapter(studentNotificationList, context,fragmentManager,exitBtn);
                            recyclerview_due_events.setAdapter(dueEventAdapter);
                            recyclerview_due_events.setVisibility(View.VISIBLE);

                        }else{
                            NotificationTopDisplay.giveContext(context);
                            NotificationTopDisplay.GetAuthenticatedUser.cancelNotificationDisplay(StudentID);

                            FragTransaction.dislayFragment(NotificationFrag.class,"", fragmentManager);
                            FloatingActionShow.showFloat(false);
                            FloatingActionShow.showSchoolChat(false);
                        }

                        builder.setView(view);



                        builder.setCancelable(true);
                        final AlertDialog alert = builder.create();
                        alert.setCancelable(true);
                        alert.getWindow().setGravity(Gravity.BOTTOM);
                        alert.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.homeText)));
                        alert.show();

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
                        return ParentMziziDatabase.getInstance(context).getPortalSiblingsDao().getSiblings();
                    }
                }.execute();


            }
        }


        new AsyncTask(){
            @Override
            protected void onPostExecute(Object o) {
                boolean pass = (boolean)o;
                if(!pass) {
                    logout();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                    startActivity(intent);
                    finish();
                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(MainActivity.this);
                if(db.getAuthenticateUserResponseDao().getAuthenticateUserResponse().size() <= 0){
                    return false;
                }

                if(db.getPortalSiblingsDao().getSiblings().size() <= 0){
                    return false;
                }

                return true;
            }
        }.execute();
    }






    @Override
    protected void onResume() {
        // if(floatbutton.getVisibility() == View.VISIBLE)
        //showcase();



        super.onResume();
    }




    @Override
    protected void onNewIntent(Intent intent) {
//        FloatingActionShow.showFloat(false);
//        FloatingActionShow.showSchoolChat(false);

        FragTransaction.removeFragments(fragmentManager);
        FragTransaction.dislayFragment(NotificationFrag.class,"", fragmentManager);


        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {

        //VERSION CHECKING
        Paper.init(this);
        MziziAppVersionControl mziziAppVersionControl =  Paper.book().read(PAPER_MZIZIVERSIONCONTROL, new MziziAppVersionControl());
        if(mziziAppVersionControl != null){
            if(mziziAppVersionControl.isForceAppUpdateStatus()){
                Intent intent = new Intent(this, MziziVersionControlActivity.class);
                startActivity(intent);
                finish();


            }
        }



//        LiveData<List<Notification>>  m =    db.getNotificationsDao().getLiveNotifications();
//        m.observe(new MainActivity(), new Observer<List<Notification>>() {
//            @Override
//            public void onChanged(@Nullable List<Notification> notifications) {
//
//                Toast.makeText(MainActivity.this, notifications.size(), Toast.LENGTH_LONG).show();
//            }
//        });


//        if(getIntent().getExtras().containsKey(OpenFragments.OPEN_NOTIFICATION_FRAGMENT)){
//
//            //do nothing because it was removed and replaced with a new activity been created.
//            //this was to service the notifications icon clicked at the action bar, which startes
//            //this activity, and if its OPEN_NOTIFICATION_FRAGMENT, then it opens notification fragment
//            //this way caused other problems because we could not control when the actionbar notifications are clicked for view.
//            //problems such as, you had to click several times to exit, because it had the previous instance
//
//            //atleast with the new acivity you can finish the previous MainActivity.
//            FragTransaction.dislayFragment(NotificationFrag.class, fragmentManager);
//
//
//
//        }



        super.onStart();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            content_main.setVisibility(show ? View.GONE : View.VISIBLE);
            content_main.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    content_main.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            content_main_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            content_main_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    content_main_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            content_main_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            content_main.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    AsyncTask siblingAsynctTask = new AsyncTask() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {

            Map<String, Object> returnResults = (Map<String, Object>)o;

            if(returnResults.containsKey(GetConstants.GET_PORTAL_SIBLINGS)) {

                List<PortalSiblings> portalSiblingsList = (List<PortalSiblings>) returnResults.get(GetConstants.GET_PORTAL_SIBLINGS);
                AuthenticateUserResponse authenticatedUser = (AuthenticateUserResponse) returnResults.get("authenticatedUser");


                if ((portalSiblingsList.size() > 0)) {

                    //show the floatingActionButton
                    FloatingActionShow.otherSiblings(true);
                    FloatingActionShow.showFloat(true);

                    List<PortalSiblings> siblings = new ArrayList<>();
                    for (int k = 0; k < portalSiblingsList.size(); k++) {
                        if (portalSiblingsList.get(k).getStudentID().equals(authenticatedUser.getUserID())) {
                            //do nothing, this student is already logged in

                        } else {
                            siblings.add(portalSiblingsList.get(k));
                        }
                    }


                    bottomrecycler = new BottomSheetRecyclerAdapter(context, siblings,bottomSheetDialog);
                    recyclerViewSibling.setAdapter(bottomrecycler);


                } else {
                    FloatingActionShow.otherSiblings(false);
                    FloatingActionShow.showFloat(false);
                    sibling_one.setVisibility(View.GONE);
                    //do nothing

                    //Toast.makeText(mainActivityWeakReference.get(), "No data  to display", Toast.LENGTH_LONG).show();
                }
            }



            super.onPostExecute(o);
        }

        @Override
        protected Object doInBackground(Object[] objects) {


            Map<String, Object> returnResults = new HashMap<>();

            List<PortalSiblings> portalSiblingsList  = db.getPortalSiblingsDao().getSiblings();
            List<AuthenticateUserResponse> authenticateUser = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();


            if(portalSiblingsList.size() <1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_SIBLINGS, new ArrayList<PortalSiblings>());
                return returnResults;
            }

            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_SIBLINGS, portalSiblingsList);//0
            try{
                returnResults.put("authenticatedUser", authenticateUser.get(0));// 1

            }catch(ArrayIndexOutOfBoundsException e){
                ///do nothing
            }

            return returnResults;
        }
    };


    public void populateHomeView(final NavigationView navigationView){

        final  View view =  navigationView.getHeaderView(0);



        final LinearLayout profile_layout = view.findViewById(R.id.profile_layout);
        final LinearLayout siblingLayout = view.findViewById(R.id.sibling_list_layout);


        sibling_one = view.findViewById(R.id.sibling_one);


        //studentName,registrationNumber;schoolName;
        //schoolLogo;studentProfilePhoto;
        studentName = view.findViewById(R.id.student_nameID);

        registrationNumber = view.findViewById(R.id.registration_numberID);
        schoolName = view.findViewById(R.id.school_nameID);
        schoolMoto = view.findViewById(R.id.school_moto_text);
        portalBalance = view.findViewById(R.id.portal_balance);
        LinearLayout headerLayout = view.findViewById(R.id.header_layout);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                drawer.closeDrawer(GravityCompat.START);
                FragTransaction.dislayFragment(ProfileFragment.class,   "", fragmentManager);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);

            }
        });
        //commented as discussed
        // schoolLogo= view.findViewById(R.id.school_logoID);
        studentProfilePhoto = view.findViewById(R.id.student_profile_photoID);

        recyclerViewSibling =  view.findViewById(R.id.recyclerviewbottomsheet);
        recyclerViewSibling.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewSibling.setLayoutManager(linearLayoutManager);

        siblingAsynctTask.execute();

        // TODO: 2020-03-26 THIS SHOULD BE REPLACED WITH GLIDE TO FETCH THE IMAGE AND CACHE IT.
        other_siblings_layout_clickable = view.findViewById(R.id.other_siblings_layout_clickable);
        other_siblings_layout_clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(profile_layout.getVisibility() == View.VISIBLE){
                    siblingLayout.setVisibility(View.VISIBLE);
                    sibling_one.setImageResource(R.drawable.sibling_main);
                    profile_layout.setVisibility(View.GONE);
                }else{
                    sibling_one.setImageResource(R.drawable.switch_siblings);
                    siblingLayout.setVisibility(View.GONE);
                    profile_layout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        AsyncTask asyncTask10 = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return ParentMziziDatabase.getInstance(context).getGlobalSettingsDAO().getGlobalSettings(Constants.CHAT_ENABLED,Integer.valueOf(studentid));
            }

            @Override
            protected void onPostExecute(Object o) {

                List<GlobalSettings>  list = (List<GlobalSettings>)o;
                //Toast.makeText(HomeFrag.this.getActivity(), "GlobalSettingsValue: " + String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                if(list.size() > 0){

                    GlobalSettings globalSettings = list.get(0);
                    if(globalSettings.getGlobalSettingsValue().contains("YES")){
//                        FloatingActionShow.supportsSchoolChat(true);
//                        FloatingActionShow.showSchoolChat(true);

                        //This will be taken in the oncreate menu
                        getMenuInflater().inflate(R.menu.main, menu);

                    }else if(globalSettings.getGlobalSettingsValue().contains("NO")){
//                        FloatingActionShow.supportsSchoolChat(false);
//                        FloatingActionShow.showSchoolChat(false);

                        //Here use another menu but with the same ids
                        getMenuInflater().inflate(R.menu.main_without_chat, menu);

                    }else{
                        getMenuInflater().inflate(R.menu.main,menu);
//                        FloatingActionShow.supportsSchoolChat(false);
//                        FloatingActionShow.showSchoolChat(false);
                    }
                }else{
                    getMenuInflater().inflate(R.menu.main_without_chat,menu);
//                        FloatingActionShow.supportsSchoolChat(false);
//                        FloatingActionShow.showSchoolChat(false);
                }
                super.onPostExecute(o);
            }
        };
        asyncTask10.execute();

        // showcase();
        return true;
    }
    public boolean internetConnection(){
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

            return true;//connected

        }
        return false;
    }


    private void logout(){
        //this should logout the user and move the user to login screen;
        SharedPreferences pshare = getSharedPreferences(GetConstants.PREF_KEY_NAME, 0);
        //if there is a user still logged in, go to home screen instead, otherwise, go to loginscreen
        if (true) {
            //clear this session

            //DELETE FROM ROOMDATABASE

            //this task just retrievs SyncMyAccountResult, Notification
            /// showProgress(true);
            new DeleteThisStudentData(MainActivity.this).execute();
            NotificationTopDisplay.giveContext(getApplicationContext());

            NotificationTopDisplay.GetAuthenticatedUser.cancelNotificationDisplay(null);
            //you should not use this, for it will delete all the data we havef
            //PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
        } else {
            try {
                throw new Exception("For you to reach this screen, you must have logged in Exception");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int itemId = item.getItemId();


        switch(itemId) {
            case R.id.logout_id:

                AlertDialog.Builder    alert = new AlertDialog.Builder(this);
                alert.setCancelable(false);
                alert.setMessage("Are you sure you want to logout?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        logout();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();

                break;
            case R.id.floating_chat_ID:
                FragTransaction.dislayFragment(PortalChatsFragment.class, "",fragmentManager);
                break;



        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;



        switch(id){
            //handle actions
            case R.id.nav_home:
//                fragmentClass = HomeFrag.class;
//                //highlight the selected item has been done by NavigationView
//                item.setChecked(true);
//                //setActionBar title
//                setTitle(item.getTitle());

                FragTransaction.dislayFragment(HomeFrag.class,"", fragmentManager);
                FloatingActionShow.showFloat(true);
                FloatingActionShow.showSchoolChat(true);
                //floatbutton.setVisibility(View.VISIBLE);

                break;
            case R.id.nav_fee:

//                fragmentClass = FeeBalanceFrag.class;
//                //highlight the selected item has been done by NavigationView
//                item.setChecked(true);
//                //setActionBar title
//                setTitle(item.getTitle());

                FragTransaction.dislayFragment(FeeBalanceFrag.class,"", fragmentManager);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
//                floatbutton.setVisibility(View.INVISIBLE);




                break;
            case R.id.nav_score://its working
//                fragmentClass = ViewExamHistory.class;
//                //highlight the selected item has been done by NavigationView
//                item.setChecked(true);
//                //setActionBar title
//                // setTitle(item.getTitle());
//                setTitle("Results History");


                // TODO: 2020-03-26 CBC RESULTS SHOULD BE A PRIORITY
                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] params) {
                        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                        if(studentid == null){
                            studentid  = "0";
                        }
                        List<PortalStudentDetailedResults> studentDetailedResults = db.getPortalStudentDetailedResultsDao().getPortalStudentDetailedResults(Integer.valueOf(studentid));
                        return studentDetailedResults;
                    }

                    @Override
                    protected void onPostExecute(Object o) {

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

//                FragTransaction.dislayFragment(MeanScoreFrag.class,"", fragmentManager);
//              //  floatbutton.setVisibility(View.INVISIBLE);
//                FloatingActionShow.showFloat(false);

                break;
            case R.id.nav_events://its working
//                fragmentClass = UpcomingEventsFrag.class;
//                //highlight the selected item has been done by NavigationView
//                item.setChecked(true);
//                //setActionBar title
//                setTitle(item.getTitle());

                FragTransaction.dislayFragment(UpcomingEventsFrag.class,"", fragmentManager);
                // floatbutton.setVisibility(View.INVISIBLE);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);

                break;

            case R.id.nav_notifications:

//                fragmentClass = NotificationFrag.class;
//                //highlight the selected item has been done by NavigationView
//                item.setChecked(true);
//                //setActionBar title
//                setTitle(item.getTitle());

                FragTransaction.dislayFragment(NotificationFrag.class,"", fragmentManager);
                // floatbutton.setVisibility(View.INVISIBLE);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case R.id.contacts_navid:

                FragTransaction.dislayFragment(SchoolContactsFrag.class,"", fragmentManager);
                // floatbutton.setVisibility(View.INVISIBLE);
                FloatingActionShow.showFloat(false);
                FloatingActionShow.showSchoolChat(false);
                break;
            case R.id.nav_tutorial:

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("StudentID", student.getStudentID());
                intent.putExtra("appcode", student.getAppcode());
                String displayLayout = DisplayLayout.DISPLAY_LAYOUT;
                intent.putExtra("DisplayLayout",displayLayout);

                intent.putExtra("isLog_In",true);//true because you logged in
                intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                startActivity(intent);
                finish();

                break;

            case R.id.nav_about:
                Intent intent1 = new Intent(MainActivity.this, AboutMziziApp.class);
                startActivity(intent1);
                break;
            case R.id.nav_feedback:
//                Intent intent2 = new Intent(MainActivity.this, CustomerDashboard.class);
//                startActivity(intent2);
                sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

                new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(getApplicationContext());
                        return  db.getPortalSiblingsDao().getMainStudentFromSibling();
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        if(o != null)
                            if(!TextUtils.isEmpty((String)o)){
                                UserRegistration userRegistration = new UserRegistration(MainActivity.this);
                                userRegistration.setUserRegistrationListener(MainActivity.this);
                                userRegistration.isUserRegistered((String)o);
                            }
                        super.onPostExecute(o);
                    }
                }.execute();
                break;

        }
        //after every click you must close the navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////START CUSTOMER SUPPORT//////////////////////////////////////////////////////////////////////////
    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    public void registrationStatus(final boolean isUserRegistered) {

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getApplicationContext());
                return  db.getPortalSiblingsDao().getMainStudentFromSiblingInformation();
            }

            @Override
            protected void onPostExecute(Object o) {
                if(o != null)
                    if(((List<PortalSiblings>)o).size() > 0) {
                        PortalSiblings user = ((List<PortalSiblings>) o).get(0);
                        new PostUserToFirebase(MainActivity.this,isUserRegistered,user.getUsername(),user.getDefaultPassword(),student.getAppcode());

                    }
                super.onPostExecute(o);
            }
        }.execute();

    }



    ///////////////////////////////////////////////////////////////////////////////////////END CUSTOMER SUPPORT//////////////////////////////////////////////////////////////////////////

    @Override
    public void onFragmentInteraction(String mystring) {
        //so do something
//        Fragment fragment = null;
//        Class fragmentClass = FragmentHome.class;
//
//        try{
//            fragment = (Fragment) fragmentClass.newInstance();
//            Bundle b = new Bundle();
//            b.putString("message", mystring);
//
//            fragment.setArguments(b);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        FragmentTransaction t = fragmentManager.beginTransaction();
//        t.replace(R.id.fragment_transaction, fragment).addToBackStack(null).commit();


        // Toast.makeText(this, "onFragmentInteraction: JUST FOR TESTS", Toast.LENGTH_LONG).show();
    }





    @Override
    protected void onDestroy() {


        for( Fragment fragment : fragmentManager.getFragments()){

            if(fragment.isVisible()){
                count++;
                fragmentManager.beginTransaction().remove(fragment);
            }
        }

        if(overlayHelper != null)
            overlayHelper.stopWatching();


        super.onDestroy();
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        //its not working
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

            // floatbutton.setVisibility(View.VISIBLE);
            FloatingActionShow.showFloat(false);
            FloatingActionShow.showSchoolChat(false);


        }else {

            for (Fragment fragment : fragmentManager.getFragments()) {


                if (fragment instanceof FeeBalanceFrag) {
                    backToHome();
                    break;
                } else if (fragment instanceof HomeFrag) {

                    //prompt the user if he she wants to exit or not
                    //check if the sheet is expanded, if yes, collapse
                    //if no, just exit, if back is clicked twice

                    if (doubleBackToExitPressedOnce) {
                        //                moveTaskToBack(true);
                        finish();
                        // System.exit(0);
                        // moveTaskToBack(true);

                        return;
                    }

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, "Back again to exit!" , Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                    //super.onBackPressed();
                    break;
                } else if (fragment instanceof MeanScoreFrag) {
                    backToHome();
                    break;
                } else if (fragment instanceof NotificationFrag) {
                    backToHome();

                    break;
                } else if (fragment instanceof UpcomingEventsFrag) {
                    backToHome();
                    break;

//        }else if(frag instanceof PortalChatsFragment){
//            fragWeakReference = new WeakReference<>(frag);
                }else if (fragment instanceof SchoolAttendaceFrag) {
                    backToHome();
                    break;
                }else if (fragment instanceof SchoolContactsFrag) {
                    backToHome();
                    break;
                }else if (fragment instanceof NewCarricullumActivity) {
                    backToHome();
                    break;
                }else if(fragment instanceof DiaryFragment){
                    backToHome();
                }else if(fragment instanceof OptionalFeesFragment){
                    backToHome();
                }else if(fragment instanceof TransportRoutesFragment){
                    backToHome();
                }else if(fragment instanceof SettingsFragment){
                    backToHome();
                }else if(fragment instanceof  ProfileFragment){
                    backToHome();
                }else if(fragment instanceof  AssignmentFileUploadFragment){
                    FragTransaction.dislayFragment(DiaryFragment.class, "", fragmentManager);
                    //floatbutton.setVisibility(View.VISIBLE);
                    FloatingActionShow.showFloat(true);
                    FloatingActionShow.showSchoolChat(true);
                }else if(fragment instanceof PortalChatsFragment){
                    backToHome();
                }else if(fragment instanceof OrderItemsFragment2){
                    backToHome();
                }else if(fragment instanceof BorrowedBooksFragment){
                    backToHome();
                }else if(fragment instanceof SchoolTripFragment){
                    backToHome();
                }

            }
        }

    }


    public  void backToHome(){
        FragTransaction.dislayFragment(HomeFrag.class, "", fragmentManager);
        //floatbutton.setVisibility(View.VISIBLE);
        FloatingActionShow.showFloat(true);
        FloatingActionShow.showSchoolChat(true);
    }


//    @Override
//    public void onBackPressed() {
//
//        //its not working
//
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//
//            floatbutton.setVisibility(View.VISIBLE);
//
//
//        }else if(!fragmentManager.findFragmentByTag("HomeFrag").isVisible()){
//
//        }else{
//
//            for( Fragment fragment : fragmentManager.getFragments()){
//
//                if(fragment.isVisible()){
//                    count++;
//                    fragmentManager.beginTransaction().remove(fragment);
//                }
//            }
//
//            FragTransaction.dislayFragment(HomeFrag.class, fragmentManager);
//
//        }
//
//    }



//    public void showcase(){
////
//        TapTargetView.showFor(this,
//                // `this` is an Activity
//                TapTarget.forToolbarMenuItem(  toolbar, R.id.logout_id, "This is a target", "We have the best targets, believe me")
//                        // All options below are optional
//                        .outerCircleColor(R.color.blue)      // Specify a color for the outer circle
//                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
//                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
//                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
//                        .titleTextColor(R.color.white)      // Specify the color of the title text
//                        .descriptionTextSize(15)            // Specify the size (in sp) of the description text
//                        .descriptionTextColor(R.color.red)  // Specify the color of the description text
//                        .textColor(R.color.white)            // Specify a color for both the title and description text
//                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
//                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
//                        .drawShadow(true)                   // Whether to draw a drop shadow or not
//                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
//                        .tintTarget(true)                   // Whether to tint the target view's color
//                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
//                        //.icon()                     // Specify a custom drawable to draw as the target
//                        .targetRadius(60),                  // Specify the target radius (in dp)
//                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
//                    @Override
//                    public void onTargetClick(TapTargetView view) {
//                        super.onTargetClick(view);      // This call is optional
//                        //Toast.makeText(MainActivity.this, "onTargetClick", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//
//
//
//    }



}

