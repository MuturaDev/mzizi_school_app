package ultratude.com.mzizi.uiactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.DisplayLayout;
import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.Staff;


public class SplashActivity extends AppCompatActivity {


    private static final int PRIVATE_MODE = 0;
    private static final String PREF_KEY_NAME = "PUSHARE";
    private SharedPreferences pstore;

    private TextView schoolname, schoolmotto;

    // private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        setContentView(R.layout.splash_layout);
        schoolname = findViewById(R.id.school_name_text);
        schoolmotto = findViewById(R.id.school_motto_text);

       Toolbar toolbar =  findViewById(R.id.toolbar);
       // toolbar.inflateMenu(R.menu.main);

        //toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.vector_menu_navigation));
        setSupportActionBar(toolbar);

        // For Testing the control version
//         Paper.init(this);
//        MziziAppVersionControl responseMziziApp = new MziziAppVersionControl();
//        responseMziziApp.setForceAppUpdateStatus(true);
//        Paper.book().write(PAPER_MZIZIVERSIONCONTROL, responseMziziApp);
//
//        lottieAnimationView = findViewById(R.id.animation_view);
//        lottieAnimationView.playAnimation();
//
//        if (lottieAnimationView.isAnimating()) {
//            lottieAnimationView.cancelAnimation();
//
//        } else {
//            lottieAnimationView.playAnimation();
//
//        }


        new GetLoginUser().execute();



    }

    String getSchoolName(String schoolNameClass) {
        String schoolName = schoolNameClass;
        if (schoolNameClass.contains(",")) {
            String[] strings = schoolNameClass.split(",");
            schoolName = strings[1].trim();
        }

        return schoolName;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //animate the image at the splash screen layout

        findViewById(R.id.splashimage).startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim));
        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {

                List<Object> returnList = ((List<Object>) o);
                if (returnList.size() > 0) {

                    PortalStudentInfo portalStudentInfo = (PortalStudentInfo) returnList.get(0);

                    schoolmotto.setText(portalStudentInfo.getSchoolMotto());
                    schoolname.setText(getSchoolName(portalStudentInfo.getSchoolName()));

                    schoolname.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim_slide_left));
                    schoolmotto.startAnimation(AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim_slide_right));
                } else {
                    schoolmotto.setVisibility(View.GONE);
                    schoolname.setVisibility(View.GONE);
                }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                //get data from different sources
                List<Object> returnList = new ArrayList<>();
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(SplashActivity.this);
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if (studentid == null) {
                    studentid = "0";
                }
                List<PortalStudentInfo> studentInfo = db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid == null ? "0" : studentid));

                if (studentInfo != null) {
                    if (studentInfo.size() > 0) {

                        returnList.add(studentInfo.get(0));
                    }
                }

                return returnList;
            }
        };
        asyncTask.execute();


    }


    private void registerAnalyticEvent(){

        ReportAnalytics.reportScreenChangeAnalytic(this, "Splash Activity");

        ReportAnalytics.reportAnalyticEvent(SplashActivity.this, "Open_Time", new SimpleDateFormat("hh:mm aa").format(Calendar.getInstance().getTime()));

    }


    public class GetLoginUser extends AsyncTask<Void, Void, AuthenticateUserResponse> {

        @Override
        protected void onPostExecute(final AuthenticateUserResponse user) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    new AsyncTask() {
                        @Override
                        protected void onPostExecute(Object o) {
                            Map map = (Map) o;

                            if (!map.containsKey("STUDENT") && !map.containsKey("STAFF")) {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                startActivity(intent);
                                SplashActivity.this.finish();
                                return;
                            }
                            if ((boolean) map.get("STUDENT") || (boolean) map.get("STAFF")) {

                                Staff staff = new StaffDao(SplashActivity.this).getUserThatSignedUp();

                                //  Toast.makeText(SplashActivity.this, String.valueOf(user == null) ,Toast.LENGTH_LONG).show();

                                if (user != null) {//that means there is a login user
                                    // Toast.makeText(SplashActivity.this, "AuthenticateUserResponse: " + String.valueOf(user.getUserType() + " " + user.getUserID() + " " + user.getLogin_status()), Toast.LENGTH_LONG).show();

                                    //hopely this should not occur
                                    // if (user.getUserType() != "" && user.getAppcode() != "" && user.getLogin_status() != "" && user.getUserID() != "") {//check this just to make sure.

                                    //if there is a user still logged in, go to home screen instead, otherwise, go to loginscreen
                                    try {
                                        //obviously in order for  you to log out, you must have logged in
                                        if (user.getLogin_status().equalsIgnoreCase("SUCCESS")) {


                                            if (user.getUserType().equals("STUDENT")) {

                                                SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(SplashActivity.this).getFilteredPortalStudentInfoResult();

//                                    AlertDialog.Builder alert = new AlertDialog.Builder(SplashActivity.this);
//                                    alert.setTitle(syncMyAccountResult.getBillingBalance());
//                                    alert.setMessage(user.toString());
//                                    alert.show();

                                                if (syncMyAccountResult.getBillingBalance().equalsIgnoreCase("")) {
                                                    //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount

                                                    registerAnalyticEvent();

                                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                                    intent.putExtra("StudentID", user.getUserID());
                                                    //false because you dint log in, you just went back, then your your system check if there you had logged
                                                    intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                                    // intent.putExtra("appcode",user.getAppcode());
                                                    intent.putExtra("student", user.getUserID());
                                                    intent.putExtra("appcode", user.getAppcode());
                                                    intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                                    startActivity(intent);
                                                    SplashActivity.this.finish();
                                                } else {
                                                    if (Float.valueOf(syncMyAccountResult.getBillingBalance()) > 0f) {


                                                        //if(500.0 > 0.0){
                                                        Intent intent = new Intent(SplashActivity.this.getBaseContext(), SyncMyAccount.class);
                                                        intent.putExtra("StudentID", user.getUserID());
                                                        intent.putExtra("appcode", user.getAppcode());
                                                        intent.putExtra("CALL_FROM", "SPLASHSCREEN");
                                                        startActivity(intent);
                                                        SplashActivity.this.finish();

                                                    } else {

                                                        registerAnalyticEvent();

                                                        //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount
                                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                                        intent.putExtra("StudentID", user.getUserID());
                                                        //false because you dint log in, you just went back, then your your system check if there you had logged
                                                        intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                                        // intent.putExtra("appcode",user.getAppcode());
                                                        intent.putExtra("student", user.getUserID());
                                                        intent.putExtra("appcode", user.getAppcode());
                                                        intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                                        startActivity(intent);
                                                        SplashActivity.this.finish();
                                                    }


                                                }


                                            }


                                        } else if (user.getLogin_status() == "") {

                                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                            intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                            startActivity(intent);
                                        } else {
                                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                            intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                            startActivity(intent);
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                        intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                        startActivity(intent);
                                        SplashActivity.this.finish();
                                    }

                                    //}
                                } else if (staff != null) {

                                    // Staff signup = new StaffDao(SplashActivity.this).getUserThatSignedUp();

                                    // Toast.makeText(SplashActivity.this, signup.toString(), Toast.LENGTH_LONG).show();


                                    if (staff != null && staff.getStaff_ID() != "" && staff.getStaff_ID() != null) {
                                        Intent intent = new Intent(SplashActivity.this, HomeScreen.class);

                                        //false because you dint log in, you just went back, then your your system check if there you had logged
                                        intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                        intent.putExtra("appcode", staff.getAppcode());
                                        intent.putExtra("staffID", staff.getStaff_ID());
                                        intent.putExtra("userType", staff.getUserType());
                                        // intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                        startActivity(intent);
                                        SplashActivity.this.finish();
                                    } else {

                                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);//should be loginActivity
                                        i.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                        i.putExtra("isLog_In", true);
                                        startActivity(i);
                                        SplashActivity.this.finish();

                                    }


                                } else {////that means that thre is no login in user, hence the first time the app was launched. should move to login
                                    //if first time, call loginActivity
                                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);//should be loginActivity
                                    i.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                    i.putExtra("isLog_In", true);
                                    startActivity(i);
                                    SplashActivity.this.finish();
                                }

                            } else {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                startActivity(intent);
                                SplashActivity.this.finish();
                            }
                            super.onPostExecute(o);
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(SplashActivity.this);
                            Log.d(SplashActivity.this.getPackageName().toUpperCase(), "Is Logged in: " + db.getAuthenticateUserResponseDao().getAuthenticateUserResponse().size());
                            Map<String, Boolean> map = new HashMap<>();
                            map.put("STUDENT", true);
                            map.put("STAFF", true);
                            if (db.getAuthenticateUserResponseDao().getAuthenticateUserResponse().size() <= 0) {
                                map.put("STUDENT", false);
                            }

                            if (db.getPortalSiblingsDao().getSiblings().size() <= 0) {
                                map.put("STUDENT", false);
                            }


                            Staff staff = new StaffDao(SplashActivity.this).getUserThatSignedUp();
                            if (staff == null)
                                map.put("STAFF", false);


                            // Log.d(SplashActivity.this.getPackageName().toUpperCase(), "Is Logged in");

                            return map;
                        }
                    }.execute();

                }
            }, 4000);

            super.onPostExecute(user);
        }

        @Override
        protected AuthenticateUserResponse doInBackground(Void... params) {

            List<AuthenticateUserResponse> authenticateUserResponseList = ParentMziziDatabase.getInstance(SplashActivity.this).getAuthenticateUserResponseDao().getAuthenticateUserResponse();

            //Log.d(SplashActivity.this.getPackageName().toUpperCase(), "Is Logged in: " + authenticateUserResponseList.size());

            AuthenticateUserResponse authenticateUserResponse = null;
            if (authenticateUserResponseList.size() > 0) {//if this is false, authenticateUserResponse will refer to null
                authenticateUserResponse = authenticateUserResponseList.get(0);
            }


            return authenticateUserResponse;
        }
    }


}
