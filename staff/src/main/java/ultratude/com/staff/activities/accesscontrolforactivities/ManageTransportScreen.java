//package ultratude.com.staff.activities.accesscontrolforactivities;
//
//import android.Manifest;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.LocationManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.Settings;
//
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.ActionBar;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.material.snackbar.Snackbar;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.MultiplePermissionsReport;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.DexterError;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.PermissionRequestErrorListener;
//import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import ultratude.com.staff.activities.DailyTransport;
//import ultratude.com.staff.activities.TripTransport;
//import ultratude.com.staff.R;
////import ultratude.com.mzizi.crashreport.Catcho;
//import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
//import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.TransportBusTripsDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.TransportSessionsDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
//import ultratude.com.staff.webservice.ResponseModels.Staff;
//import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
//import ultratude.com.staff.webservice.RequestModels.PortalTransportSessionBusTripsResponse;
//import ultratude.com.staff.webservice.RequestModels.TransportBusTrips;
//import ultratude.com.staff.webservice.RequestModels.TransportSessions;
//import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
//import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
//import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffAllStudentsRequest;
//import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;
//
///**
// * Created by James on 11/05/2019.
// */
//
//public class ManageTransportScreen extends AppCompatActivity {
//
//    private static boolean refresh_Button = false;
//
//    private RelativeLayout btn_daily_tranport_ID, btn_trip_transport_cases_ID;
//    private  boolean isLogin = false;
//
//    private Staff staff;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        Catcho.Builder(this)
////                .recipients(Constants.CRASH_REPORT_EMAIL)
////                .build();
//        setContentView(R.layout.manage_transport_screen_layout);
//
//        ActionBar actionBar = (ActionBar) getSupportActionBar();
//        if(actionBar != null){
//            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//
//        isLogin  = getIntent().getExtras().getBoolean("isLog_In");
//        Staff signup = new StaffDao(this).getUserThatSignedUp();
//        staff = signup;
//
//        btn_daily_tranport_ID = findViewById(R.id.btn_daily_tranport_ID);
//        btn_trip_transport_cases_ID = findViewById(R.id.btn_trip_transport_cases_ID);
//
//        btn_daily_tranport_ID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//
//
//            }
//        });
//
//        btn_trip_transport_cases_ID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//
//            }
//        });
//
//    }
//
//
//    private void requestMultiPermission(){
//        Dexter.withActivity(this)
//                .withPermissions(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.CALL_PHONE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE
//                )
//                .withListener(new MultiplePermissionsListener(){
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report){
//                        //check if all permissions are granted
//                        if(report.areAllPermissionsGranted()){
//
//                            long studentSize = new TransportStudentsDao(ManageTransportScreen.this).getAllStudents().size();
//                            ArrayList<TransportBusTrips> transportBusTrips = new TransportBusTripsDAO(ManageTransportScreen.this).getTransportBusTripsArrayList();
//
//                            ArrayList<TransportSessions> transportSessionsArrayList = new TransportSessionsDAO(ManageTransportScreen.this).getTransportSessionArrayList();
//
//                            if(studentSize > 0 && transportBusTrips.size() > 0 && transportSessionsArrayList.size() > 0){
//                                //should starte Activity
//                                Intent intent = new Intent(ManageTransportScreen.this, DailyTransport.class);
//                               intent.putExtra("isLog_In", isLogin);
//                                startActivity(intent);
//                                isLogin = false;
//
//                            }else{
////                               // Snackbar.make(findViewById(R.id.home_layout), "", Snackbar.LENGTH_LONG).setAction("Refresh student list", new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
//
//                                //start of refresh
//
//                                if(internetConnection(ManageTransportScreen.this)){
//
//                                    final ProgressDialog alertd = new ProgressDialog(ManageTransportScreen.this);
//                                    alertd.setCancelable(false);
//                                    alertd.setMessage("Refreshing student list...");
//                                    alertd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                                    alertd.show();
//
//                                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//
//                                    //for testing only
//                                   final StaffAllStudentsRequest request = new StaffAllStudentsRequest();
//                                    request.setAppCode(staff.getAppcode());
//                                    request.setStaffID(staff.getStaff_ID());
//
//                                    //GET TRANSPORT STUDENTS
//                                    Call<List<TransportStudents>> userCall = apiInterface.getAllTransportStudents(request);
//                                    userCall.enqueue(new Callback<List<TransportStudents>>() {
//                                        @Override
//                                        public void onResponse(Call<List<TransportStudents>> call, Response<List<TransportStudents>> response) {
//
//                                            List<TransportStudents> list = response.body();
//
//                                            //  Toast.makeText(HomeScreen.this, String.valueOf(list.get(0).toString()), Toast.LENGTH_LONG).show();
//
//                                            List<TransportStudents> result =  new ArrayList<TransportStudents>();
//
//                                            if(response.code() == 200){
//
//                                                result = response.body();
//
//                                                //  Toast.makeText(HomeScreen.this, String.valueOf(result.size()), Toast.LENGTH_LONG).show();
//
//
//                                                if(result != null){
//
//                                                    new SaveAllStudentsAsyncTask(ManageTransportScreen.this, alertd).execute(result);
//                                                }
//                                            }else if(response.code() == 204){
//                                                new SaveAllStudentsAsyncTask(ManageTransportScreen.this, alertd).execute(result);
//
//                                            }
//
//                                            alertd.cancel();
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<List<TransportStudents>> call, Throwable t) {
//
//                                        }
//                                    });
//
//                                    StaffRequest staffRequest = new StaffRequest(
//                                            staff.getStaff_ID() ,
//                                            staff.getSchoolID(),
//                                            staff.getOrganizationID(),
//                                            staff.getAppcode()
//
//                                    );
//
//
//                                    //GET TRANSPORT SESSIONS AND BUSTRIPS
//                                    Call<PortalTransportSessionBusTripsResponse> userCall1 = apiInterface.getTransportSessionAndBusTrips(staffRequest);
//                                    userCall1.enqueue(new Callback<PortalTransportSessionBusTripsResponse>() {
//                                        @Override
//                                        public void onResponse(Call<PortalTransportSessionBusTripsResponse> call, Response<PortalTransportSessionBusTripsResponse> response) {
//                                            if(response.isSuccessful()){
//                                                if(response.code() == 200){
//                                                    new TransportBusTripsDAO(ManageTransportScreen.this).saveTransportBusTrips(response.body().getBusTripsList());
//                                                    new TransportSessionsDAO(ManageTransportScreen.this).saveTransportSessions(response.body().getSessionsList());
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<PortalTransportSessionBusTripsResponse> call, Throwable t) {
//
//                                        }
//                                    });
//
//
//
//
//
//                                }else{
//                                    Toast.makeText(ManageTransportScreen.this,"Check your internet connection!", Toast.LENGTH_SHORT).show();
//
//                                }
//
//                                //start of refresh
//
//
////                                    }
////                                }).show();
//                            }
//
//
//
//                            // Toast.makeText(getApplicationContext(), "All permission are granted!", Toast.LENGTH_SHORT).show();
//                        }else{
//                            showSettingsDialog();
//                        }
//
//                        //check for permanent denial of any permission
//                        if(report.isAnyPermissionPermanentlyDenied()){
//                            //show alert dialog navigating to showSettingsDialog
//                            showSettingsDialog();
//                        }
//                    }
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest>permissions, PermissionToken token){
//                        token.continuePermissionRequest();
//                    }
//                }).withErrorListener(new PermissionRequestErrorListener(){
//            @Override
//            public void onError(DexterError error){
//
//                //Toast.makeText(getApplicationContext(), "Errror occured!", Toast.LENGTH_SHORT).show();
//            }
//        }).onSameThread().check();
//    }
//
//
//
//
//
//    private void openSettings(){
//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 101);
//    }
//
//
//    private void showSettingsDialog(){
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//        builder.setTitle("Need Permissions");
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings");
//        builder.setPositiveButton("SETTINGS", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which){
//                dialog.cancel();
//                openSettings();
//
//
//            }
//        });
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which){
//                dialog.cancel();
//            }
//        });
//        builder.show();
//    }
//
//
//
//    public class SaveAllStudentsAsyncTask extends AsyncTask<List<TransportStudents>, Void, String> {
//
//        private Context context;
//        private ProgressDialog alert;
//
//
//        //    new SaveAllStudentsAsyncTask(loginActivityWeakReference.get(), innerstaff, pd).execute(result);
//
//        public SaveAllStudentsAsyncTask(Context context, ProgressDialog alert){
//            this.context = context;
//            this.alert = alert;
//        }
//
//
//        @Override
//        protected void onPostExecute(String results) {
//            alert.cancel();
//
//            if(results.equals("0")){
//                Snackbar.make(findViewById(R.id.manage_transport_screen_layout_ID), "Sorry, you can't proceed to scanning, without a record of all students ", Snackbar.LENGTH_LONG).show();
//            }else if(refresh_Button){
//                refresh_Button = false;
//                Toast.makeText(context, "upto date" /*+ results*/ , Toast.LENGTH_LONG).show();
//            }else{
//
//
//
//                //check if the gps is enabled, and the wifi connection two,
//                final LocationManager lmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//                if ((lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER))) {
//
//
//                    //if not prompt the user to connect it, and transfer
//                    //him to the settings activity, (ask him to swap from the top of his screen and enable wifi and gps.
//                    //if yes, just continue.
//
//                    //request permission
//                    //requestCameraPermission();
//                    requestMultiPermission();
//
//
//                } else {
//
//                    android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(ManageTransportScreen.this);
//                    alert.setCancelable(false);
//                    alert.setTitle("GPS settings");
//                    alert.setMessage("GPS is not enabled. Do you want to go to settings menu?");
//
//                    alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//
//                        }
//                    });
//                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    alert.show();
//
//                    //if(!lmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//                    //      return;
//                    // }
//
//                }
//
//            }
//
//
//
//            super.onPostExecute(results);
//        }
//
//
//
//
//
//        @Override
//        protected String doInBackground(List<TransportStudents>... params) {
//
//            final List<TransportStudents> studentList = params[0];
//
//            if(studentList.size() > 0){
//                return String.valueOf(new TransportStudentsDao(ManageTransportScreen.this).saveAllStudents(null,studentList));
//
//            }
//
//            return String.valueOf(studentList.size());
//            // return String.valueOf(studentList.size());
//
//        }
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//
//}
