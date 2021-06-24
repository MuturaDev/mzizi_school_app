package ultratude.com.staff.activities.accesscontrolforactivities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.activities.AssetTrackingScreen;
import ultratude.com.staff.activities.DailyTransport;
import ultratude.com.staff.activities.DutyRosterScreen;
import ultratude.com.staff.activities.EnrollActivityScan;
import ultratude.com.staff.activities.ManageAttendanceScreenWebPortal;
import ultratude.com.staff.activities.ManageDisciplineSreen;
import ultratude.com.staff.activities.ManageFleetFuelScreen;
import ultratude.com.staff.activities.ManageFleetServiceScreen;
import ultratude.com.staff.activities.ManageLeaveApplicationScreen;
import ultratude.com.staff.activities.MziziParentWebPortal;
import ultratude.com.staff.activities.MziziVersionControlActivity;
import ultratude.com.staff.activities.RefreshData;
import ultratude.com.staff.activities.StudentEnquiry;
import ultratude.com.staff.activities.TestLayoutActivity;
import ultratude.com.staff.activities.TripTransport;
import ultratude.com.staff.activities.mziziapptutorial.TapToGetHelpActivity;
import ultratude.com.staff.R;

import ultratude.com.staff.adapters.NewHomeScreenBottomItemsAdapter;
import ultratude.com.staff.adapters.NewHomeScreenTopItemsAdapter;
import ultratude.com.staff.apprating.CustomAppRating;
import ultratude.com.staff.model.HomeItem;
import ultratude.com.staff.services.DataTransferBroadcast;
import ultratude.com.staff.services.DataTransferReceiverBroadcast;
import ultratude.com.staff.services.DataTransferService;
import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.MarkRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.SchoolsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportBusTripsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportSessionsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
import ultratude.com.staff.webservice.DataAccessObjects.TripLatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.RequestModels.PortalTransportSessionBusTripsResponse;
import ultratude.com.staff.webservice.RequestModels.TransportBusTrips;
import ultratude.com.staff.webservice.RequestModels.TransportSessions;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;
import ultratude.com.staff.webservice.ResponseModels.Schools;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;
import ultratude.com.staff.webservice.ResponseModels.Student;
import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
import ultratude.com.staff.webservice.ResponseModels.TripLatLong;
import ultratude.com.staff.webservice.ResponseModels.UltraData;
import ultratude.com.staff.webservice.ResponseModels.Vehicle;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffAllStudentsRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;

import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;


/**
 * Created by Admin on 3/21/2018.
 */

public class HomeScreen extends AppCompatActivity {


   public static String CurrentScreenKey = "No Label";

    DataTransferReceiverBroadcast broadcastReceiver;

    Context context;

    Staff staff;

    private TextView txt_StaffName_ID;

//    CardView manage_transport_CardID,
//            apply_leave_CardId,
//            manage_discipline_CardId,
//            manage_attendance_CardId,
//            view_payslip_CardId,
//            student_enquiry_CardId,
//            duty_roster_CardId,
//            manage_fuel_CardId,
//            fleet_service_CardID,
//            enroll_activity_scan_CardID;


    private static boolean refresh_Button = false;

   private  boolean isLogin = false;

    //private LinearLayout pb_homescreen_progress_layout;


//    private void showNameProgress(boolean show){
//
//        if(show){
//            pb_homescreen_progress_layout.setVisibility(View.VISIBLE);
//            txt_StaffName_ID.setVisibility(View.GONE);
//        }else{
//            pb_homescreen_progress_layout.setVisibility(View.GONE);
//            txt_StaffName_ID.setVisibility(View.VISIBLE);
//        }
//
//    }


    private static boolean is_first_launch = false;
    //private Toolbar toolbar;
    private LinearLayout show_tutorial_ID;


    @Override
    protected void onStart() {
        Paper.init(this);
        MziziAppVersionControl mziziAppVersionControl =  Paper.book().read(PAPER_MZIZIVERSIONCONTROL, new MziziAppVersionControl());
        if(mziziAppVersionControl != null){
            if(mziziAppVersionControl.isForceAppUpdateStatus()){
                Intent intent = new Intent(this, MziziVersionControlActivity.class);
                startActivity(intent);
                finish();
            }
        }

        super.onStart();
    }

    LinearLayout parent_portal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.new_home_screen);


            //NEED TO IMPLEMENT THIS IN INFORMATION IN
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
//
//        int ff = 10/0;

        show_tutorial_ID = findViewById(R.id.layout_help);
//        toolbar = findViewById(R.id.toolbar);
//
//        toolbar.inflateMenu(R.menu.staff_menu);
//        toolbar.setTitle("Home");
//       // toolbar.setSubtitle("Select action");
//        //toolbar.setLogo(R.drawable.mzizi_laucher_icon);
//        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.home_icon));
//       // setTitle("jdlfajldjf");
//        setSupportActionBar(toolbar);


        context = HomeScreen.this;

        //RATE THE APP
//        AppRating.Builder(this)
//                .setMinimumLaunchTimes(5)
//                .setMinimumDays(7)
//                .setMinimumLaunchTimesToShowAgain(5)
//                .setMinimumDaysToShowAgain(10)
//                .setRatingThreshold(RatingThreshold.FOUR)
//                .showIfMeetsConditions()

        parent_portal = findViewById(R.id.parent_portal);
        parent_portal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreen.this, MziziParentWebPortal.class));
            }
        });


        isLogin  = getIntent().getExtras().getBoolean("isLog_In");
        is_first_launch = isLogin;
       //if(is_first_launch){
        if(true){

           show_tutorial_ID.setVisibility(View.VISIBLE);
//            final Display display = getWindowManager().getDefaultDisplay();
//
//            final Drawable droid = ContextCompat.getDrawable(context, R.drawable.mzizi_laucher_icon);
//
//            //FOR A SEQUENCE OF TAPS
//
//
//            // Tell our droid buddy where we want him to appear
//            final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
//            // Using deprecated methods makes you look way cool
//            droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);
//
//            //this is really cool, just for underling here you want, just like in html
//            final SpannableString sassyDesc = new SpannableString("Introduction Tutorial.");
//            sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "".length(), sassyDesc.length(), 0);
//
//            // We have a sequence of targets, so lets build it!
//            final TapTargetSequence sequence = new TapTargetSequence(this)
//                    .targets(
//                            // This tap target will target the back button, we just need to pass its containing toolbar
//
//
//                            TapTarget.forToolbarNavigationIcon(toolbar, "Menu", sassyDesc).id(1),  //in out case we dont have a navigation view set
//                            // Likewise, this tap target will target the search button
//                            TapTarget.forToolbarMenuItem(toolbar, R.id.find_help_ID, "Find Help", "Tap to find get the Introduction Tutorial.")
//                                    .cancelable(false)
//                                    .drawShadow(true)
//                                    .outerCircleColor(R.color.colorAccent)
//                                    .dimColor(R.color.white_transparency)
//                                    .id(2)
//                            // You can also target the overflow button in your toolbar
//                            //TapTarget.forToolbarOverflow(toolbar, "Logout", "Click to logout.").id(3)
//
//                            //In our case we are not pointing to any spacific location in on the screen. done programmatically.
//                                               /* ,*/
//                            // This tap target will target our droid buddy at the given target rect
//                            //                       TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
//                            //                                .cancelable(false)
//                            //                                .icon(droid)
//                            //                                .id(4)
//                    )
//                    .listener(new TapTargetSequence.Listener() {
//                        // This listener will tell us when interesting(tm) events happen in regards
//                        // to the sequence
//                        @Override
//                        public void onSequenceFinish() {
//                            //for our case just use a alert the user, that he or she is educated
//                            //((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
//                        }
//
//                        @Override
//                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
//                            //Log.d("TapTargetView", "Clicked on " + lastTarget.id());
//                        }
//
//                        @Override
//                        public void onSequenceCanceled(TapTarget lastTarget) {
//                            this.onSequenceFinish();
//                        }
//                    });
//
//
//            sequence.start();
//
          is_first_launch = false;
       }



        Staff signup = new StaffDao(context).getUserThatSignedUp();
        staff = signup;

        txt_StaffName_ID = findViewById(R.id.txt_StaffName_ID);

       // pb_homescreen_progress_layout = findViewById(R.id.pb_homescreen_progress_layout);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
               //showNameProgress(true);
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {

                try{
                    Thread.sleep(5000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

               return new AllStaffDAO(HomeScreen.this).getNameOfLoggedInStaff();
            }

            @Override
            protected void onPostExecute(Object o) {
                //showNameProgress(false);
                txt_StaffName_ID.setText(((String) o));
                super.onPostExecute(o);
            }
        };
        asyncTask.execute();

            //i dont see the essence of this, because i wont unregister it and will have to call it when the serveice starts...but not now,, let me see its effect


        //REMOVED DUE TO MANY REQUEST,
        Intent intent = new Intent(this, DataTransferService.class);
        intent.putExtra("STAFF", signup);
   startService(intent);


       // new StaffDao(this).deleteStaff();
        //new StaffDao(this).saveUserToDB(staff);

//        manage_transport_CardID = (CardView) findViewById(R.id.manage_transport_CardID);
//        apply_leave_CardId= (CardView) findViewById(R.id.apply_leave_CardId);
//        apply_leave_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });
//        manage_discipline_CardId=  findViewById(R.id.manage_discipline_CardId);
//        manage_discipline_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
//        manage_attendance_CardId= (CardView) findViewById(R.id.manage_attendance_CardId);
//        manage_attendance_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });
//
//        view_payslip_CardId= (CardView) findViewById(R.id.view_payslip_CardId);
//        view_payslip_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
//
//        student_enquiry_CardId= (CardView) findViewById(R.id.student_enquiry_CardId);
//        student_enquiry_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//               // Toast.makeText(HomeScreen.this, "Phase two of development", Toast.LENGTH_SHORT).show();
//
//
//
//
//
//            }
//        });
//        duty_roster_CardId= (CardView) findViewById(R.id.duty_roster_CardId);
//        duty_roster_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
//
//
//
//            }
//        });
//        manage_fuel_CardId = (CardView) findViewById(R.id.cv_manageFuel_ID);
//        manage_fuel_CardId.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });
//
//        fleet_service_CardID = findViewById(R.id.fleet_service_CardID);
//        fleet_service_CardID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
//
//        fleet_service_CardID = findViewById(R.id.assets_scan_CardID);
//        fleet_service_CardID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
//
//
//
//        manage_transport_CardID.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//
//
//                    Intent intent = new Intent(HomeScreen.this, ManageTransportScreen.class);
//                    intent.putExtra("isLog_In", isLogin);
//                    startActivity(intent);
//                    isLogin = false;
//
//            }
//        });
//
//
//        enroll_activity_scan_CardID = findViewById(R.id.enroll_activity_scan_CardID);
//        enroll_activity_scan_CardID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });


        (findViewById(R.id.layout_sync)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        findViewById(R.id.layout_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert;
                alert = new AlertDialog.Builder(HomeScreen.this);
                alert.setCancelable(false);
                alert.setMessage("Are you sure you want to logout?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogouter, int which) {

                        List<Object> list = new UltraDataDao(context).getUltraDataList();
                        if (list.size() > 0) {
                            List<Object> tripObjectList = new TripLatLongDAO(context).getTripLatLong();

                            List<UltraData> ultraDataList = (List<UltraData>) list.get(0);
                            List<TripLatLong> tripLatLongList = (List<TripLatLong>) tripObjectList.get(0);


                            //remember that you have to delete all the data when logging out


                            //TODO: MUST/SHOULD CHECK FOR ALL DATA SAVED LOCALLY AND NEED TO BE UPLOADED NOT JUST SCANNED RECORDS
                            //here we check conditions
                            if (ultraDataList.size() != 0 && tripLatLongList.size() != 0) {
                                dialogouter.cancel();
                                AlertDialog.Builder alertInner = new AlertDialog.Builder(context);
                                alertInner.setTitle("Denied");
                                alertInner.setCancelable(false);
                                alertInner.setMessage("Sorry, please wait until all your scanned records are sent");
                                alertInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialoginner, int which) {
                                        dialoginner.cancel();
                                    }
                                });

                                alertInner.show();
                            } else {//You can now delete all your data
                                dialogouter.cancel();

                                new Logout(HomeScreen.this).execute(HomeScreen.this);

                            }
                        }


                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogouter, int which) {
                        dialogouter.cancel();
                    }
                });
                alert.show();
            }
        });

        findViewById(R.id.layout_sync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnection(HomeScreen.this)){
                    StaffRequest staffRequest = new StaffRequest(
                            staff.getStaff_ID(),
                            staff.getSchoolID(),
                            staff.getOrganizationID(),
                            staff.getAppcode()
                    );
                    new DataTransferBroadcast().uploadLocalData(HomeScreen.this, staffRequest);
                    Snackbar.make(findViewById(R.id.home_layout), "Uploading records stored locally...", Snackbar.LENGTH_LONG).show();
                }else {
                    AlertDialog.Builder noInternetConnectionAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                    noInternetConnectionAlert.setView(view);
                    noInternetConnectionAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    noInternetConnectionAlert.show();

                }
//            Intent intent = new Intent(DataTransferBroadcast.ACTION_ALARM);
//            sendBroadcast(intent);
            }
        });


        newSetUp();


    }



   // /this helps alot, when the app is closed, bload cast are not fired
        public  void unregisterNetworkBroadcastReceiver(){

          //  Toast.makeText(this, "home uregister", Toast.LENGTH_LONG).show();
        try{
            unregisterReceiver(broadcastReceiver);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
    }


    private void requestMultiPermissionManageTransportScreen(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report){
                        //check if all permissions are granted
                        if(report.areAllPermissionsGranted()){

                            long studentSize = new TransportStudentsDao(HomeScreen.this).getAllStudents().size();
                            ArrayList<TransportBusTrips> transportBusTrips = new TransportBusTripsDAO(HomeScreen.this).getTransportBusTripsArrayList();

                            ArrayList<TransportSessions> transportSessionsArrayList = new TransportSessionsDAO(HomeScreen.this).getTransportSessionArrayList();

                            if(studentSize > 0 && transportBusTrips.size() > 0 && transportSessionsArrayList.size() > 0){
                                //should starte Activity
                                Intent intent = new Intent(HomeScreen.this, DailyTransport.class);
                                intent.putExtra("isLog_In", isLogin);
                                startActivity(intent);
                                isLogin = false;

                            }else{
//                               // Snackbar.make(findViewById(R.id.home_layout), "", Snackbar.LENGTH_LONG).setAction("Refresh student list", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {

                                //start of refresh

                                if(internetConnection(HomeScreen.this)){

                                    final ProgressDialog alertd = new ProgressDialog(HomeScreen.this);
                                    alertd.setCancelable(false);
                                    alertd.setMessage("Refreshing student list...");
                                    alertd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    alertd.show();

                                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.


                                    //for testing only
                                    final StaffAllStudentsRequest request = new StaffAllStudentsRequest();
                                    request.setAppCode(staff.getAppcode());
                                    request.setStaffID(staff.getStaff_ID());

                                    //GET TRANSPORT STUDENTS
                                    Call<List<TransportStudents>> userCall = apiInterface.getAllTransportStudents(request);
                                    userCall.enqueue(new Callback<List<TransportStudents>>() {
                                        @Override
                                        public void onResponse(Call<List<TransportStudents>> call, Response<List<TransportStudents>> response) {

                                            List<TransportStudents> list = response.body();

                                            //  Toast.makeText(HomeScreen.this, String.valueOf(list.get(0).toString()), Toast.LENGTH_LONG).show();

                                            List<TransportStudents> result =  new ArrayList<TransportStudents>();

                                            if(response.code() == 200){

                                                result = response.body();

                                                //  Toast.makeText(HomeScreen.this, String.valueOf(result.size()), Toast.LENGTH_LONG).show();


                                                if(result != null){

                                                    new SaveAllStudentsAsyncTask(HomeScreen.this, alertd).execute(result);
                                                }
                                            }else if(response.code() == 204){
                                                new SaveAllStudentsAsyncTask(HomeScreen.this, alertd).execute(result);

                                            }

                                            alertd.cancel();

                                        }

                                        @Override
                                        public void onFailure(Call<List<TransportStudents>> call, Throwable t) {

                                        }
                                    });

                                    StaffRequest staffRequest = new StaffRequest(
                                            staff.getStaff_ID() ,
                                            staff.getSchoolID(),
                                            staff.getOrganizationID(),
                                            staff.getAppcode()

                                    );


                                    //GET TRANSPORT SESSIONS AND BUSTRIPS
                                    Call<PortalTransportSessionBusTripsResponse> userCall1 = apiInterface.getTransportSessionAndBusTrips(staffRequest);
                                    userCall1.enqueue(new Callback<PortalTransportSessionBusTripsResponse>() {
                                        @Override
                                        public void onResponse(Call<PortalTransportSessionBusTripsResponse> call, Response<PortalTransportSessionBusTripsResponse> response) {
                                            if(response.isSuccessful()){
                                                if(response.code() == 200){
                                                    new TransportBusTripsDAO(HomeScreen.this).saveTransportBusTrips(response.body().getBusTripsList());
                                                    new TransportSessionsDAO(HomeScreen.this).saveTransportSessions(response.body().getSessionsList());
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<PortalTransportSessionBusTripsResponse> call, Throwable t) {

                                        }
                                    });





                                }else{
                                    Toast.makeText(HomeScreen.this,"Check your internet connection!", Toast.LENGTH_SHORT).show();

                                }

                                //start of refresh


//                                    }
//                                }).show();
                            }



                            // Toast.makeText(getApplicationContext(), "All permission are granted!", Toast.LENGTH_SHORT).show();
                        }else{
                            showSettingsDialog();
                        }

                        //check for permanent denial of any permission
                        if(report.isAnyPermissionPermanentlyDenied()){
                            //show alert dialog navigating to showSettingsDialog
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest>permissions, PermissionToken token){
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener(){
            @Override
            public void onError(DexterError error){

                //Toast.makeText(getApplicationContext(), "Errror occured!", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }


    private void requestMultiPermission(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE
                )
                .withListener(new MultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report){
                        //check if all permissions are granted
                        if(report.areAllPermissionsGranted()){

                                //should starte Activity
                                Intent i = new Intent(HomeScreen.this, AssetTrackingScreen.class);
                                i.putExtra("string", "string");
                                startActivity(i);
                                isLogin = false;

                        }else{
                            showSettingsDialog();
                        }

                        //check for permanent denial of any permission
                        if(report.isAnyPermissionPermanentlyDenied()){
                            //show alert dialog navigating to showSettingsDialog
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest>permissions, PermissionToken token){
                        token.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener(){
            @Override
            public void onError(DexterError error){

                //Toast.makeText(getApplicationContext(), "Errror occured!", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    private void showSettingsDialog(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings");
        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
                openSettings();


            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void openSettings(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.staff_menu, menu);

        return true;
    } 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int itemId = item.getItemId();

        final AlertDialog.Builder alert;


        if(itemId == R.id.logout_id) {
        //migrated

        }else if(itemId == R.id.upload_btn) {
            //migrated


        }else if(itemId == R.id.refresh_btn ){
///TODO: TO BE MIGRATED.
            //should be removed
            //deleteMarkRegister();

            refresh_Button = true;

            if(internetConnection(this)){



                //This is what i should change
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

                StaffRequest staffRequest = new StaffRequest(
                        staff.getStaff_ID() ,
                        staff.getSchoolID(),
                        staff.getOrganizationID(),
                        staff.getAppcode()

                );

//                final ProgressDialog alertd = new ProgressDialog(this);
//                alertd.setCancelable(false);
//                alertd.setTitle("Please wait.");
//                alertd.setMessage("Loading...");
//                alertd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                alertd.show();

//                sendRequestForAllStudents(context, apiInterface,staffRequest);
//                sendRequestForAllVehicles(context, apiInterface,staffRequest);
//                sendRequestForALLSchools(context, apiInterface,staffRequest);
//                sendRequestForDutyRosterList(context, apiInterface,staffRequest);
//                sendRequestForAllStaff(context, apiInterface, staffRequest);
//                sendRequestForAllTransportStudents(context, apiInterface, staffRequest, staff);
//                sendRequestForAllStaff(context, apiInterface, staffRequest, staff, alertd);
                new RefreshData(context, apiInterface,staffRequest ,null);

                Snackbar.make( findViewById(R.id.home_layout), "Loading records...", Snackbar.LENGTH_LONG).show();

            }else{

                AlertDialog.Builder noInternetConnectionAlert = new AlertDialog.Builder(HomeScreen.this);
                LayoutInflater inflater = getLayoutInflater();
                View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                noInternetConnectionAlert.setView(view);
                noInternetConnectionAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                noInternetConnectionAlert.show();
              //  Toast.makeText(HomeScreen.this,"Check your internet connection!", Toast.LENGTH_SHORT).show();

            }

       }else if(itemId == R.id.find_help_ID){
          // FancyToast.makeText(HomeScreen.this, "Tutorial", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show();
            Intent intent = new Intent(HomeScreen.this, TapToGetHelpActivity.class);
            startActivity(intent);
           // finish();
        }

        return super.onOptionsItemSelected(item);
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







    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


                    if (doubleBackToExitPressedOnce) {
                        //                moveTaskToBack(true);
                        new LatLongDAO(HomeScreen.this).deleteLatLong();

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


    }






    private void deleteMarkRegister(){
        new MarkRegisterDAO(this).deleteRegister();
    }



    //All this will be taken to the login location
    private void sendRequestForAllStudents(final Context mContext, APIInterface apiInterface, StaffRequest staffRequest){


        Call<List<Student>> userCall = apiInterface.getAllStudents(staffRequest);
        userCall.enqueue(new Callback<List<Student>>() {
                             @Override
                             public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new StudentDAO(mContext).saveStudent(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<Student>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }

        );

    }


    private void sendRequestForAllVehicles(final Context mContext,APIInterface apiInterface,StaffRequest staffRequest){

        Call<List<Vehicle>> userCall = apiInterface.getAllVehicles(staffRequest);
        userCall.enqueue(new Callback<List<Vehicle>>() {
                             @Override
                             public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new VehicleDAO(mContext).saveVehicle(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<Vehicle>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }
        );
    }


    private void sendRequestForALLSchools(final Context mContext,APIInterface apiInterface, StaffRequest staffRequest){



        Call<List<Schools>> userCall = apiInterface.getAllSchools(staffRequest);
        userCall.enqueue(new Callback<List<Schools>>() {
                             @Override
                             public void onResponse(Call<List<Schools>> call, Response<List<Schools>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new SchoolsDAO(mContext).saveSchools(response.body());

                                     //  Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<Schools>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );

    }

    private void sendRequestForDutyRosterList(final Context mContext,APIInterface apiInterface, StaffRequest staffRequest){



        Call<List<DutyRoster>> userCall = apiInterface.getDutyRosterList(staffRequest);
        userCall.enqueue(new Callback<List<DutyRoster>>() {
                             @Override
                             public void onResponse(Call<List<DutyRoster>> call, Response<List<DutyRoster>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new DutyRosterDAO(mContext).saveDutyRoster(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<DutyRoster>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );
    }



    private void sendRequestForAllStaff(final Context mContext, APIInterface apiInterface, StaffRequest staffRequest){


        Call<List<AllStaff>> userCall = apiInterface.getAllStaff(staffRequest);
        userCall.enqueue(new Callback<List<AllStaff>>() {
            @Override
            public void onResponse(Call<List<AllStaff>> call, Response<List<AllStaff>> response) {


                if(response.isSuccessful()){

                    // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                    long id =  new AllStaffDAO(mContext).saveAllStaffDAO(response.body());

                   // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                }else{
                    //you dont have to delete all the data
                }


            }

            @Override
            public void onFailure(Call<List<AllStaff>> call, Throwable t) {
                //  Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
            }
        });


    }

    private void sendRequestForAllTransportStudents(final Context mContext, APIInterface apiInterface, StaffRequest staffRequest, final Staff innerStaff){
        //for testing only
        //You can also add schoolID and organizationID
        Call<List<TransportStudents>> userCall = apiInterface.getPortalAllTransportStudents(staffRequest);
        userCall.enqueue(new Callback<List<TransportStudents>>() {
            @Override
            public void onResponse(Call<List<TransportStudents>> call, Response<List<TransportStudents>> response) {

                List<TransportStudents> list = response.body();

                //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() + " " + response.body().get(0).toString() , Toast.LENGTH_LONG).show();


                List<TransportStudents> result = new ArrayList<TransportStudents>();

                if(response.code() == 200){



                    result = response.body();

                    //Toast.makeText(loginActivityWeakReference.get(), String.valueOf(result.get(0).toString()), Toast.LENGTH_LONG).show();




                    if(result != null){

                        if(result.size()> 0){


                            // if(result.get(0).getStudentFullName() != ""){

                            // Toast.makeText(loginActivityWeakReference.get(), String.valueOf(result.get(0).toString()), Toast.LENGTH_LONG).show();
//
                            //Call PortalAllTransportStudentsListing

                            new SaveAllTransportStudentsAsyncTask(HomeScreen.this, innerStaff).execute(result);


//
//
                            // }
                        }

                    }
                }





            }

            @Override
            public void onFailure(Call<List<TransportStudents>> call, Throwable t) {
                // Toast.makeText(loginActivityWeakReference.get(),"Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                call.cancel();

                                            /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                             Couldnt connect to server. Make sure your phone has an internet connection and try again.*/

            }
        });
    }

    private void sendRequestForAllStaff(final Context mContext, APIInterface apiInterface, StaffRequest staffRequest, final Staff innerstaff, final ProgressDialog alertDialog){


        Call<List<AllStaff>> userCall = apiInterface.getAllStaff(staffRequest);
        userCall.enqueue(new Callback<List<AllStaff>>() {
            @Override
            public void onResponse(Call<List<AllStaff>> call, Response<List<AllStaff>> response) {


                if(response.isSuccessful()){

                    // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                    long id =  new AllStaffDAO(mContext).saveAllStaffDAO(response.body());

                    alertDialog.cancel();
                   // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                }else{
                    alertDialog.cancel();
                    //you dont have to delete all the data
                }

            }

            @Override
            public void onFailure(Call<List<AllStaff>> call, Throwable t) {
                // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                call.cancel();
                alertDialog.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
            }
        });

    }



    public class SaveAllTransportStudentsAsyncTask extends AsyncTask<List<TransportStudents>, Void, String>{

        private Context context;
        private Staff innerstaff;
        private ProgressDialog pd;


        //    new SaveAllTransportStudentsAsyncTask(loginActivityWeakReference.get(), innerstaff, pd).execute(result);

        public SaveAllTransportStudentsAsyncTask(Context context, Staff innerStaff){
            this.context = context;
            this.innerstaff = innerStaff;


        }



        @Override
        protected void onPostExecute(String results) {

            //  Toast.makeText(context, "StudentList: " + results , Toast.LENGTH_LONG).show();


            //new StaffDao(loginActivityWeakReference.get()).saveUserToDB(innerstaff);




            super.onPostExecute(results);
        }

        @Override
        protected String doInBackground(List<TransportStudents>... params) {

            final List<TransportStudents> studentList = params[0];

            if(studentList.size() > 0){
                return String.valueOf( new TransportStudentsDao(context).saveAllStudents(null,studentList));

            }

            return String.valueOf(studentList.size());


            // return String.valueOf(studentList.size());

        }
    }



    ///////////////////////////////////////////////////////NEW SETUP//////////////////////////////////////////////////

    private GridView gv_bottom_items_list;
    private RecyclerView rc_top_items_list;

    private void newSetUp(){

        gv_bottom_items_list = findViewById(R.id.gv_bottom_items_list);
        rc_top_items_list = findViewById(R.id.rc_top_items_list);



        List<HomeItem> gv_homeitem_list = new ArrayList<>();

        HomeItem view_payslip = new HomeItem(getResources().getDrawable(R.drawable.ic_paycheque_white), "View \nPayslip", true);
        gv_homeitem_list.add(view_payslip);

        HomeItem duty_roster = new HomeItem(getResources().getDrawable(R.drawable.ic_duty_roster_white), "Duty \nRoster", true);
        gv_homeitem_list.add(duty_roster);

        HomeItem apply_leave = new HomeItem(getResources().getDrawable(R.drawable.ic_leaving_queue_white), "Apply \nLeave", true);
        gv_homeitem_list.add(apply_leave);


        HomeItem my_Calendar = new HomeItem(getResources().getDrawable(R.drawable.ic_timeline_week_white), "My \nCalendar", true);
        gv_homeitem_list.add(my_Calendar);

        HomeItem internal_requisition = new HomeItem(getResources().getDrawable(R.drawable.ic_product_white), "Internal \nRequisition", true);
        gv_homeitem_list.add(internal_requisition);

        HomeItem receive_storeitems = new HomeItem(getResources().getDrawable(R.drawable.ic_grocery_shelf_white), "Receive \nStore Items", true);
        gv_homeitem_list.add(receive_storeitems);

        HomeItem daily_incident_report = new HomeItem(getResources().getDrawable(R.drawable.ic_results_white), "Daily \nIncident Report", true);
        gv_homeitem_list.add(daily_incident_report);

        HomeItem medical_tracking = new HomeItem(getResources().getDrawable(R.drawable.ic_recovery_white), "Medical \nTracking", true);
        gv_homeitem_list.add(medical_tracking);


        HomeItem disciplinary_case = new HomeItem(getResources().getDrawable(R.drawable.ic_police_fine_white), "Disciplinary \nCase", true);
        gv_homeitem_list.add(disciplinary_case);

        HomeItem mark_class_attendance = new HomeItem(getResources().getDrawable(R.drawable.ic_attendance_white), "Mark \nAttendance", true);
        mark_class_attendance.setCountValue(4);
        gv_homeitem_list.add(mark_class_attendance);

        HomeItem student_enquiry = new HomeItem(getResources().getDrawable(R.drawable.ic_questions_white), "Student \nEnquiry", true);
        gv_homeitem_list.add(student_enquiry);

        HomeItem school_transport = new HomeItem(getResources().getDrawable(R.drawable.ic_students_white), "Daily \nTransport", true);
        mark_class_attendance.setCountValue(300);
        gv_homeitem_list.add(school_transport);

        HomeItem trip_transport = new HomeItem(getResources().getDrawable(R.drawable.ic_trip_transport_white), "Trip \nTransport", true);
        gv_homeitem_list.add(trip_transport);

        HomeItem fleet_service = new HomeItem(getResources().getDrawable(R.drawable.ic_service_white), "Fleet \nService", true);
        gv_homeitem_list.add(fleet_service);

        HomeItem fuel_consumption = new HomeItem(getResources().getDrawable(R.drawable.ic_gasstation_white), "Fuel \nConsumption", true);
        gv_homeitem_list.add(fuel_consumption);

        HomeItem enroll_activity = new HomeItem(getResources().getDrawable(R.drawable.ic_enroll_activity_white), "Enroll \nActivity", true);
        gv_homeitem_list.add(enroll_activity);

        HomeItem asset_tracking = new HomeItem(getResources().getDrawable(R.drawable.ic_scan_white), "Asset \nTracking", true);
        gv_homeitem_list.add(asset_tracking);


        NewHomeScreenBottomItemsAdapter gv_bottom_items_adaper = new NewHomeScreenBottomItemsAdapter(this,gv_homeitem_list);
        gv_bottom_items_list.setAdapter(gv_bottom_items_adaper);

        NewHomeScreenTopItemsAdapter rc_top_items_adapter = new NewHomeScreenTopItemsAdapter(this, new ArrayList<HomeItem>() );
        rc_top_items_list.setAdapter(rc_top_items_adapter);

    }


    //
   public void changeScreen(String screenKey, Object... message){
       CurrentScreenKey = screenKey.replace("\n"," ");
        switch (screenKey){
            case "View \nPayslip":// ScreenNameEnums screenName = ScreenNameEnums.VIEW_PAYSLIP;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.VIEW_PAYSLIP)){

                    startActivity(new Intent(HomeScreen.this, CustomAppRating.class));
                    //Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
                }else{

                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Duty \nRoster"://ScreenNameEnums screenName = ScreenNameEnums.DUTY_ROSTER;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.DUTY_ROSTER)){

                    Intent i = new Intent(HomeScreen.this, DutyRosterScreen.class);
                    i.putExtra("string", "string");
                    startActivity(i);

                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Apply \nLeave"://ScreenNameEnums screenName = ScreenNameEnums.APPLY_LEAVE;


                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.APPLY_LEAVE)){
                    // Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(HomeScreen.this, ManageLeaveApplicationScreen.class);
                    i.putExtra("string", "string");
                    startActivity(i);
                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "My \nCalendar":
                startActivity(new Intent(HomeScreen.this, TestLayoutActivity.class));
                break;
            case "Internal \nRequisition":
                startActivity(new Intent(HomeScreen.this, TestLayoutActivity.class));
                break;
            case "Receive \nStore Item":
                startActivity(new Intent(HomeScreen.this, TestLayoutActivity.class));
                break;
            case "Daily \nTransport"://ScreenNameEnums screenName = ScreenNameEnums.DAILTY_TRANSPORT;
                //This will be removed from here


                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.DAILTY_TRANSPORT)){

                    //check if the gps is enabled, and the wifi connection two,
                    final LocationManager lmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                    if ((lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {

                        //if not prompt the user to connect it, and transfer
                        //him to the settings activity, (ask him to swap from the top of his screen and enable wifi and gps.
                        //if yes, just continue.

                        //request permission
                        //requestCameraPermission();
                        requestMultiPermissionManageTransportScreen();


                    } else {

                        androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(HomeScreen.this);
                        alert.setCancelable(false);
                        alert.setTitle("GPS settings");
                        alert.setMessage("GPS is not enabled. Do you want to go to settings menu?");

                        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                            }
                        });
                        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        alert.show();

                        //if(!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        //      return;
                        // }

                    }

                }else{
                    androidx.appcompat.app.AlertDialog.Builder accessDeniedAlert = new androidx.appcompat.app.AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Medical \nTracking":
                startActivity(new Intent(HomeScreen.this, TestLayoutActivity.class));
                break;
            case "Disciplinary \nCase"://ScreenNameEnums screenName = ScreenNameEnums.MANAGE_DISCPLINARY;


                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.MANAGE_DISCPLINARY)){
                    // Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HomeScreen.this, ManageDisciplineSreen.class);
                    i.putExtra("string", "string");
                    startActivity(i);
                    //finish();

                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Mark \nAttendance"://ScreenNameEnums screenName = ScreenNameEnums.MANAGE_ATTENDANCE;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.MANAGE_ATTENDANCE)){
                    // Toast.makeText(HomeScreen.this, "Coming soon", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(HomeScreen.this, ManageAttendanceScreenWebPortal.class);
                    i.putExtra("string", "string");
                    startActivity(i);
                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Student \nEnquiry":// ScreenNameEnums screenName = ScreenNameEnums.STUDENT_ENQUIRY;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.STUDENT_ENQUIRY)){

                    Intent i = new Intent(HomeScreen.this, StudentEnquiry.class);
                    i.putExtra("string", "string");
                    startActivity(i);

                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Daily \nIncident Report":
                startActivity(new Intent(HomeScreen.this, TestLayoutActivity.class));
                break;
            case "Trip \nTransport"://ScreenNameEnums screenName = ScreenNameEnums.TRIP_TRANSPORT;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.TRIP_TRANSPORT)){
                    Intent intent = new Intent(HomeScreen.this, TripTransport.class);
                    startActivity(intent);

                }else{
                    androidx.appcompat.app.AlertDialog.Builder accessDeniedAlert = new androidx.appcompat.app.AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Fleet \nService"://ScreenNameEnums screenName = ScreenNameEnums.MANAGE_FLEET_SERVICE;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.MANAGE_FLEET_SERVICE)){

                    Intent i = new Intent(HomeScreen.this, ManageFleetServiceScreen.class);
                    i.putExtra("string", "string");
                    startActivity(i);

                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            case "Enroll \nActivity":
                startActivity(new Intent(HomeScreen.this, TestLayoutActivity.class));
                break;
            case "Asset \nTracking":
//check if the gps is enabled, and the wifi connection two,
                final LocationManager lmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if ((lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {

                    //if not prompt the user to connect it, and transfer
                    //him to the settings activity, (ask him to swap from the top of his screen and enable wifi and gps.
                    //if yes, just continue.

                    //request permission
                    //requestCameraPermission();
                    requestMultiPermission();


                } else {

                    androidx.appcompat.app.AlertDialog.Builder alert = new androidx.appcompat.app.AlertDialog.Builder(HomeScreen.this);
                    alert.setCancelable(false);
                    alert.setTitle("GPS settings");
                    alert.setMessage("GPS is not enabled. Do you want to go to settings menu?");

                    alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alert.show();

                    //if(!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    //      return;
                    // }

                }
                break;

            case "Fuel \nConsumption"://ScreenNameEnums screenName = ScreenNameEnums.MANAGE_FLEET_FUEL;

                if(new ScreenDAO(HomeScreen.this).requestAccessToThisScreen(new StaffOperationsDAO(HomeScreen.this).getStaffOperationsList(), ScreenNameEnums.MANAGE_FLEET_FUEL)){

                    Intent i = new Intent(HomeScreen.this, ManageFleetFuelScreen.class);
                    i.putExtra("string", "string");
                    startActivity(i);
                    //finish();

                }else{
                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(HomeScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
                    accessDeniedAlert.setView(view);
                    accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    accessDeniedAlert.show();
                }
                break;
            default:
                Toast.makeText(HomeScreen.this, "No Screen specified for that key", Toast.LENGTH_SHORT).show();
                break;


        }
    }



    public class SaveAllStudentsAsyncTask extends AsyncTask<List<TransportStudents>, Void, String> {

        private Context context;
        private ProgressDialog alert;


        //    new SaveAllStudentsAsyncTask(loginActivityWeakReference.get(), innerstaff, pd).execute(result);

        public SaveAllStudentsAsyncTask(Context context, ProgressDialog alert){
            this.context = context;
            this.alert = alert;
        }


        @Override
        protected void onPostExecute(String results) {
            alert.cancel();

            if(results.equals("0")){
                Snackbar.make(findViewById(R.id.home_layout), "Sorry, you can't proceed to scanning, without a record of all students ", Snackbar.LENGTH_LONG).show();
            }else if(refresh_Button){
                refresh_Button = false;
                Toast.makeText(context, "upto date" /*+ results*/ , Toast.LENGTH_LONG).show();
            }else{



                //check if the gps is enabled, and the wifi connection two,
                final LocationManager lmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                if ((lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {


                    //if not prompt the user to connect it, and transfer
                    //him to the settings activity, (ask him to swap from the top of his screen and enable wifi and gps.
                    //if yes, just continue.

                    //request permission
                    //requestCameraPermission();
                    requestMultiPermission();


                } else {

                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(HomeScreen.this);
                    alert.setCancelable(false);
                    alert.setTitle("GPS settings");
                    alert.setMessage("GPS is not enabled. Do you want to go to settings menu?");

                    alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        }
                    });
                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alert.show();

                    //if(!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    //      return;
                    // }

                }

            }



            super.onPostExecute(results);
        }





        @Override
        protected String doInBackground(List<TransportStudents>... params) {

            final List<TransportStudents> studentList = params[0];

            if(studentList.size() > 0){
                return String.valueOf(new TransportStudentsDao(HomeScreen.this).saveAllStudents(null,studentList));

            }

            return String.valueOf(studentList.size());
            // return String.valueOf(studentList.size());

        }
    }








}
