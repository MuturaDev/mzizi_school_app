package ultratude.com.mzizi.uiactivities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.helperfragments.AutoLoginFragment;
import ultratude.com.mzizi.modelclasses.DisplayLayout;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.retrofitokhttp.Pojo.Test;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.UserCredentials;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.webservice.apiconnections.AuthenticateUser;

import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.staff.activities.AssetTrackingScreen;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;

/**
 * Created by Admin on 5/21/2018.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

   private TextInputLayout passwordlayout;
   private TextInputLayout usernamelayout;
   private TextInputLayout appcodelayout;
   public AppCompatEditText passwordtext;
   public AppCompatEditText usernametext;
   public AppCompatEditText appcodetext;


    ProgressBar login_progress;
    ScrollView login_form;
    private ProgressBar pb_login_progress;
    private TextView label_wait_ID;



    Button submit;


    public ProgressDialog bar;
    public ParentMziziDatabase db;

    public boolean is_first_lanuch = false;


    public String displayLayout = "";


    private ImageView qr_code;
    private TextView qr_code_label;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        //test if you should dipslay the layout
        is_first_lanuch = getIntent().getBooleanExtra("isLog_In", false);

       // Toast.makeText(this, "isLogin LoginActivity." + getIntent().getBooleanExtra("isLog_In", false), Toast.LENGTH_SHORT).show();

        // TODO: 01/08/2020 REMEMBER TO REMOVE THIS 
//        int i = 0;
//        i = i/0;
        
        bar = new ProgressDialog(this);
        bar.setCancelable(false);
        bar.setIndeterminate(false);
        bar.setMessage("Please wait, fetching data from school...");
        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        db = ParentMziziDatabase.getInstance(this);

        if(getIntent().getExtras().containsKey(DisplayLayout.DISPLAY_LAYOUT)){

            displayLayout = DisplayLayout.DISPLAY_LAYOUT;

            setContentView(R.layout.login_layout_test);

            submit  = findViewById(R.id.loginbtn);
            submit.setOnClickListener(this);
            pb_login_progress = findViewById(R.id.pb_login_progress);
            passwordlayout =  findViewById(R.id.password);
            label_wait_ID = findViewById(R.id.label_wait_ID);

            usernamelayout =  findViewById(R.id.username);
            appcodelayout = findViewById(R.id.appCode);

            usernametext =  findViewById(R.id.usernametext);
            passwordtext =  findViewById(R.id.passwordtext);
            appcodetext = findViewById(R.id.appCodtext);


            if(isInternetAccessibleAsyncTask()){
                dialog(true);
            }else{
                dialog(false);
            }



            /////////////////////////////////////////START OF BARCODE SECTION///////////////////////////////////////

            qr_code_label = findViewById(R.id.qr_code_label);
            qr_code_label.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performQRClick();
                }
            });
            qr_code = findViewById(R.id.qr_code);
            qr_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    performQRClick();
                }
            });



        }else if(getIntent().getExtras().containsKey(DisplayLayout.DO_NOT_DISPLAY_LAYOUT)){

            displayLayout = DisplayLayout.DO_NOT_DISPLAY_LAYOUT;

            String studentID = getIntent().getExtras().getString("StudentID");
            String appcode = getIntent().getExtras().getString("appcode");

            Log.d(LoginActivity.this.getPackageName().toUpperCase(), "Sibling Student: " + studentID + " " + appcode);


            //authenticateUserResponse

                //this is called when, you switch siblings,
            new AuthenticateSiblingResponseInsertToDB().execute(studentID, appcode,
                    getIntent().getExtras().getString("SchoolID"),
                    getIntent().getExtras().getString("OrganizationID"),
                    getIntent().getExtras().getString("Username"),
                    getIntent().getExtras().getString("Password"));

        }else if(getIntent().getExtras().containsKey(DisplayLayout.DO_NOT_DISPLAY_LAYOUT_FROM_STAFF)){

            displayLayout = DisplayLayout.DO_NOT_DISPLAY_LAYOUT_FROM_STAFF;

            String studentID = getIntent().getExtras().getString("StudentID");
            String appcode = getIntent().getExtras().getString("appcode");

            new AuthenticateSiblingResponseInsertToDB().execute(studentID, appcode
                    );
        }



        Paper.init(this);
        Paper.book().write("FCMLoggedInYet", "0");
    }

    private FragmentManager fragmentManager;
    AutoLoginFragment autoLoginFragment;

    public void handleAutoLogin(String encryptedQRCode){

        //Toast.makeText(this, encryptedQRCode, Toast.LENGTH_SHORT).show();

        if( autoLoginFragment != null)
            autoLoginFragment.dismiss();

        if(TextUtils.isEmpty(encryptedQRCode)){
            FancyToast.makeText(this, "Invalid QR Code", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            return;
        }

        if(!encryptedQRCode.contains("xy") && !encryptedQRCode.contains("yx")){
            FancyToast.makeText(this, "Invalid QR Code", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            return;
        }

        Map<String,String> credentials = new HashMap<>();

        try {
            credentials = UtilityFunctions.decryptPassword(encryptedQRCode);
        }catch (Exception ex){
            FancyToast.makeText(this, "Invalid QR Code", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
            return;
        }

//        Toast.makeText(this, new UserCredentials(credentials.get("Username"), credentials.get("Password"), credentials.get("SchoolCode")).toString(), Toast.LENGTH_SHORT).show();

        //login
        login(new UserCredentials(credentials.get("Username"), credentials.get("Password"), credentials.get("SchoolCode")), null);

    }


    private void performQRClick(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View view = inflater.inflate(R.layout.auto_login_fragment_layout,null,false);
//        builder.setView(view);
//
//
//        builder.setView(view);
//        builder.setCancelable(true);
//        alert = builder.create();
//        alert.getWindow().setGravity(Gravity.BOTTOM);
//        //alert.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
//        alert.show();

        requestMultiPermission();

//        fragmentManager = getSupportFragmentManager();
//        AutoLoginFragment fragment = AutoLoginFragment.createFor(null);
//        fragment.show(fragmentManager, "fragment_edit_name");


        //Request for camera permission
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            login_form.setVisibility(show ? View.GONE : View.VISIBLE);
            login_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            login_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            login_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            login_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////REQUEST PERMISSION//////////////////////////////////////////////////////////////
    private void requestMultiPermission(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report){
                        //check if all permissions are granted
                        if(report.areAllPermissionsGranted()){

                            fragmentManager = getSupportFragmentManager();
                             autoLoginFragment = AutoLoginFragment.createFor(null);
                            autoLoginFragment.show(fragmentManager, "fragment_edit_name");

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




    ///////////////////////////////////////////////////////////////////////////////END OF REQUEST PERMISSION//////////////////////////////////////////////////////////////



    //called from the broadcast, onReceive
    public  void dialog(boolean value){
        if(value){
                //do nothing as discussed
        }else{


//            Toast toast =    Toast.makeText(this, "   Check your internet connection!  ", Toast.LENGTH_LONG);
//            View v =  toast.getView();
//            v.setBackground(getResources().getDrawable(R.drawable.snack_bg_red));
//            toast.show();
            FancyToast.makeText(this, "Ooops, check your internet connection.", FancyToast.LENGTH_SHORT, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false ).show();
        }
    }




    public boolean isInternetAccessibleAsyncTask(){
        final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi  = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((mobile.isConnected()) || wifi.isConnected()) {

            return true;//connected
        }
        return false;
    }



//    public void isInternetAccessibleAsynTask(){
//        if(isInternetAccessibleAsyncTask()){
//            new AsyncTask<Void, Void,Boolean>(){
//                protected Boolean doInBackground(Void... params) {
//                         try{
//                                final HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
//                                        urlc.setRequestProperty("Staff-Agent", "Test");
//                                        urlc.setRequestProperty("Connection", "close");
//                                        urlc.setConnectTimeout(1500);
//                                        urlc.connect();
//                                return (urlc.getResponseCode() == 200);
//                        }catch (Exception e){
//                           // Toast.makeText(LoginActivity.this, "Coundn't check internet connection", Toast.LENGTH_LONG).show();
//
//                        }
//
//
//
//                    return false;
//                }
//                protected void onPostExecute(Boolean result) {
//
//                    if(result == true){
//                        dialog(true);
//                    }else{
//                        dialog(false);
//                    }
//
//                }
//
//            };
//
//    }else{
//        Toast.makeText(LoginActivity.this, "Internet not available", Toast.LENGTH_LONG).show();
//
//    }

   // }

    private boolean checkUsername(String username){
        if(username.isEmpty()){
            usernamelayout.setErrorEnabled(true);
            usernametext.requestFocus();
            usernamelayout.setError("Enter Username");
            return false;
        }
        return true;
    }
    public boolean checkPassword(String password){
        if(password.isEmpty()){
            passwordlayout.setErrorEnabled(true);
            passwordtext.requestFocus();
            passwordlayout.setError("Enter password");
            return false;
        }
        return true;
    }
    public boolean checkAppCode(String appcode) {
        if (appcode.isEmpty()) {
            appcodelayout.setErrorEnabled(true);
            appcodetext.requestFocus();
            appcodelayout.setError("Enter SchoolCode");
            return false;
        }
        return true;
    }



    private void login(final UserCredentials credentials, final  View view){
        if(isInternetAccessibleAsyncTask()){//REMEMBER TO UNCOMMENT
            submit.setEnabled(false);
            submit.setVisibility(View.INVISIBLE);
            pb_login_progress.setVisibility(View.VISIBLE);
            label_wait_ID.setVisibility(View.VISIBLE);


            Test test = new Test();
            test.setTest(true);

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


            Call<Boolean> userCall = apiInterface.testNetworkStability(test);
            userCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                    //Log.d(mContext.getPackageName().toUpperCase(), "Network Testing: " + String.valueOf(response.body()));
                    if (response.code() == 200) {

                        if (response.body()) {

                            authenticate(credentials, view);
                            submit.setEnabled(true);
                            submit.setVisibility(View.VISIBLE);
                            pb_login_progress.setVisibility(View.GONE);
                            label_wait_ID.setVisibility(View.GONE);


                        }else{
                            FancyToast.makeText(LoginActivity.this, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
                            submit.setVisibility(View.VISIBLE);
                            pb_login_progress.setVisibility(View.GONE);
                            label_wait_ID.setVisibility(View.GONE);
                        }

                    } else {
                        FancyToast.makeText(LoginActivity.this, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
                        submit.setEnabled(true);
                        submit.setVisibility(View.VISIBLE);
                        pb_login_progress.setVisibility(View.GONE);
                        label_wait_ID.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    //call.cancel();
                    FancyToast.makeText(LoginActivity.this, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
                    submit.setEnabled(true);
                    submit.setVisibility(View.VISIBLE);
                    pb_login_progress.setVisibility(View.GONE);
                    label_wait_ID.setVisibility(View.GONE);

                }
            });

        }else{
            FancyToast.makeText(LoginActivity.this, "Oops Check your internet connection!", FancyToast.LENGTH_LONG, FancyToast.ERROR, R.mipmap.mzizi_app_icon,false).show();
            submit.setEnabled(true);
            submit.setVisibility(View.VISIBLE);
            pb_login_progress.setVisibility(View.GONE);
            label_wait_ID.setVisibility(View.GONE);

        }
    }




    @Override
    public void onClick(final View v) {
        String username = usernametext.getText().toString().trim();
        String password = passwordtext.getText().toString().trim();
        String appcode = appcodetext.getText().toString().trim();

        if(checkUsername(username) && checkPassword(password) && checkAppCode(appcode)){
            final  UserCredentials credentials = new UserCredentials(username, password, appcode);

            if(v.getId() == R.id.loginbtn){
                login(credentials, v);
            }
        }

    }

    private boolean isUsernamePasswordNumber(UserCredentials credentials){
        boolean valueIsNumber = true;
//        try {
//            new Integer(credentials.getPassword());
//
//        }catch(IllegalArgumentException e){
//          valueIsNumber = false;
//        }

        return valueIsNumber;
    }

    public void authenticate(UserCredentials credentials, View v){


        if(credentials.getUserName().isEmpty()){
            usernamelayout.setErrorEnabled(true);
            usernametext.requestFocus();
            usernamelayout.setError("Enter Username");
        }
        else if(credentials.getPassword().isEmpty()){
            passwordlayout.setErrorEnabled(true);
            passwordtext.requestFocus();
            passwordlayout.setError("Enter password");
        }
        else if(credentials.getAppcode().isEmpty()){
                appcodelayout.setErrorEnabled(true);
                appcodetext.requestFocus();
                appcodelayout.setError("Enter SchoolCode");
//        }else if(credentials.getUserName().length() > 100){
//            usernamelayout.setErrorEnabled(true);
//            usernametext.requestFocus();
//            usernamelayout.setError("Username should not have more than 100 characters");
        }else if(!isUsernamePasswordNumber(credentials)){
            passwordlayout.setErrorEnabled(true);
            passwordlayout.requestFocus();
            passwordlayout.setError("Password should be a Number");
        }
        else{

            //Call an AsyncTask to handle authenication
            List<Object> list = new ArrayList<>();
            list.add(credentials);
            list.add(getApplicationContext());

            //this should call, for a test of working internet connection


           // Toast.makeText(this, credentials.toString(), Toast.LENGTH_LONG).show();

            if(v == null){
                usernametext.setText(credentials.getUserName());
                passwordtext.setText(credentials.getPassword());
                appcodetext.setText(credentials.getAppcode());
            }

            new AuthenticateUser(this, passwordtext, usernametext, appcodetext);


            if(v != null){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }



        }
    }


    //this helps alot, when the app is closed, bload cast are not fired
//    private void unregisterNetworkBroadcastReceiver(){
//        try{
//            unregisterReceiver(broadcast);
//        }catch(IllegalArgumentException e){
//            e.printStackTrace();
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterNetworkBroadcastReceiver();
//    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        //prompt the user if he she wants to exit or not
        if(doubleBackToExitPressedOnce){
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "BACK again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


    }


    private class AuthenticateSiblingResponseInsertToDB extends AsyncTask<String, Void, List<String>>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<String> returnList) {


            //WHEN YOU DDONT DISPLAY THE LAYOUT

            //remember this inforamtion is coming from siblings
            //StudentID and
            //This intent will come with the student id

            // login_progress = findViewById(R.id.login_progress);
            // login_form = findViewById(R.id.login_form);


            final Student student = new Student(returnList.get(0), returnList.get(1));

            // Toast.makeText(LoginActivity.this, "Your loggin  to: "+ student.getUserID()  + " And " +student.getAppcode() , Toast.LENGTH_LONG).show();

//            showProgress(true);
//            showProgress(false);


            //should now call the GetStudentInfoFromMziziApi, using OkHTTP..remember two for siblings


            //here is the place to send the request
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                                    //request to PortalStudentInfo
            bar.setMessage("Please wait for confirmation...");

            Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
            userCall.enqueue(new Callback<SyncMyAccountResult>() {
                @Override
                public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {

                    SyncMyAccountResult result = response.body();
//                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
//                    alert.setMessage(result.toString());
//                    alert.show();

                    if(result == null){

                    }else{
                        bar.dismiss();
                        // Toast.makeText(mainActivityWeakReference.get(), "SyncMyAccount: " + result.toString(), Toast.LENGTH_LONG).show();


                        //first delete the previous
                        new SyncMyAccountDAO(LoginActivity.this).deleteForSyncMyAccountResult(student.getStudentID());
                        result.setStudID(Integer.valueOf(student.getStudentID()));
                        new SyncMyAccountDAO(LoginActivity.this).insertSyncMyAccountResult(result);

                        //then do the rest

                        if(Float.valueOf(result.getBillingBalance())> 0f){

                            bar.dismiss();


                            Intent intent = new Intent(LoginActivity.this, SyncMyAccount.class);
                            intent.putExtra("StudentID", student.getStudentID());
                            intent.putExtra("appcode", student.getAppcode());
                            intent.putExtra("CALL_FROM", "SWITCH_SIBLING");//AUTHENTICATE_USER

                            startActivity(intent);
                            finish();


                        }else{

                            new AuthenticateUser(LoginActivity.this,passwordtext,usernametext,appcodetext);
                            //new GetStudentInfoFromMziziApi(LoginActivity.this, student).SendRequest(student);

                        }

                    }
                    //end

                }

                @Override
                public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                   // Toast.makeText(mainActivityWeakReference.get(), "Check your internet connection!", Toast.LENGTH_SHORT).show();
                    call.cancel();

                }
            });


            //End





            super.onPostExecute(returnList);
        }

        @Override
        protected List<String> doInBackground(String... lists) {

            String studentID = lists[0];
            String schoolcode = lists[1];


            List<String> list = new ArrayList<>();
//            list.add(studentID);
//            list.add(schoolcode);

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(LoginActivity.this.getApplicationContext());
            db.getAuthenticateUserResponseDao().deleteAuthenticateUserResponse();

            AuthenticateUserResponse student = new AuthenticateUserResponse();
            student.setAppcode(schoolcode);
            student.setUserID(studentID);
            student.setUserType("STUDENT");
            student.setLogin_status("SUCCESS");

            student.setSchoolID(lists[2]);
            student.setOrganizationID(lists[3]);
            student.setUsername(lists[4]);
            student.setPassword(lists[5]);




            db.getAuthenticateUserResponseDao().insertAuthenticateUserResponse(student);

           List<AuthenticateUserResponse> authenticateUserResponseList =  db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

            for(AuthenticateUserResponse l : authenticateUserResponseList){
                //for testing purposes
                list.add(l.getUserID());
                list.add(l.getAppcode());
            }


            return list;
        }
    }


}
