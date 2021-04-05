package ultratude.com.mzizi.changepasswordfragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andrognito.patternlockview.utils.ResourceUtils;
import com.kevalpatel.passcodeview.PatternView;
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPatternAuthenticator;
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener;
import com.kevalpatel.passcodeview.patternCells.DotPatternCell;
import com.kevalpatel.passcodeview.patternCells.PatternPoint;

import java.util.List;

import io.paperdb.Paper;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.DisplayLayout;
import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.uiactivities.LoginActivity;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.SyncMyAccount;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.Staff;

public class PatternFragment extends Fragment {


    public PatternFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pattern_fragment, container, false);
    }

    private boolean showTimer = false;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO: 2020-04-26 Removed coz it started crashing on android 10 
        Paper.init(getActivity());
        if(Paper.book().contains("PatternString")){
            if(Paper.book().read("PatternString").toString().isEmpty()){
               // pattern_switch.setChecked(false);
                showTimer = true;
                new GetLoginUser().execute();

            }else{
                getView().findViewById(R.id.pattern_fragment_layout).setVisibility(View.VISIBLE);
               // pattern_switch.setChecked(true);
            }

        }else{
           // pattern_switch.setChecked(false);
            showTimer = true;
            new GetLoginUser().execute();
        }
        registerPattern();
        super.onViewCreated(view, savedInstanceState);
    }




    PatternView patternView;
    private void registerPattern(){
        try {


            patternView = getView().findViewById(R.id.pattern_view);

            //TODO:check if it should be activated.
            if (Paper.book().contains("UseWithFingerprint")) {
                if (Paper.book().read("UseWithFingerprint")) {
                    patternView.setIsFingerPrintEnable(true);
                    patternView.setTactileFeedback(true);
                } else {
                    patternView.setIsFingerPrintEnable(false);
                    patternView.setTactileFeedback(false);
                }
            } else {
                patternView.setIsFingerPrintEnable(false);
                patternView.setTactileFeedback(false);
            }

            patternView.setFingerPrintStatusText("Scan your finger to unlock Mzizi School App");
            patternView.setFingerPrintStatusTextColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
            patternView.setFingerPrintStatusTextSize(10f);

            patternView.setTitle("Draw your pattern");
            patternView.setTitleColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
            patternView.setPatternPathColor(ResourceUtils.getColor(getActivity(), R.color.colorPrimary));

            //Set number of pattern counts.
            //REQUIRED
            patternView.setNoOfColumn(3);   //Number of columns
            patternView.setNoOfRows(3);     //Number of rows

//        Set the correct pin code.
//        Display row and column number of the pattern point sequence.
//        REQUIRED
//        final PatternPoint[] correctPattern = new PatternPoint[]{
//                new PatternPoint(0, 0),
//                new PatternPoint(1, 0),
//                new PatternPoint(2, 0),
//                new PatternPoint(2, 1)
//        };


            final PatternPoint[] correctPattern = (PatternPoint[]) Paper.book().read("PatternPointArray");

            patternView.setAuthenticator(new PasscodeViewPatternAuthenticator(correctPattern));

            //Build the desired indicator shape and pass the theme attributes.
            //REQUIRED
            patternView.setPatternCell(new DotPatternCell.Builder(patternView)
                    .setRadius(R.dimen.pattern_cell_radius)
                    .setCellColorResource(R.color.homeText));

            patternView.setAuthenticationListener(new AuthenticationListener() {
                @Override
                public void onAuthenticationSuccessful() {
                    //User authenticated successfully.
                    //Navigate to secure screens.

                    new GetLoginUser().execute();

                }

                @Override
                public void onAuthenticationFailed() {
                    if (patternView.isFingerPrintEnable()) {
//                    startActivity(new Intent(getActivity(), AboutMziziApp.class));
//                    getActivity().finish();
                        // Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        patternView.setTitle("Wrong pattern, try again");
                        if (getActivity() != null)
                            patternView.setTitleColor(ResourceUtils.getColor(getActivity(), R.color.red));
                        //Calls whenever authentication is failed or user is unauthorized.
                        //Do something
                    }

                }
            });

        }catch (IndexOutOfBoundsException ex){
            Log.e(getContext().getPackageName().toUpperCase(), "PatternFragment: Exception " + ex.toString());
        }
    }



    public class GetLoginUser extends AsyncTask<Void, Void, AuthenticateUserResponse> {

        @Override
        protected void onPostExecute(final AuthenticateUserResponse user) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Staff staff = new StaffDao(getActivity()).getUserThatSignedUp();


                    //  Toast.makeText(SplashActivity.this, String.valueOf(user == null) ,Toast.LENGTH_LONG).show();




                    if (user != null) {//that means there is a login user
                        // Toast.makeText(SplashActivity.this, "AuthenticateUserResponse: " + String.valueOf(user.getUserType() + " " + user.getUserID() + " " + user.getLogin_status()), Toast.LENGTH_LONG).show();

                        //hopely this should not occur
                        // if (user.getUserType() != "" && user.getAppcode() != "" && user.getLogin_status() != "" && user.getUserID() != "") {//check this just to make sure.

                        //if there is a user still logged in, go to home screen instead, otherwise, go to loginscreen
                        try {
                            //obviously in order for  you to log out, you must have logged in
                            if (user.getLogin_status().equalsIgnoreCase("SUCCESS")) {


                                if(user.getUserType().equals("STUDENT")){

                                    SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(getActivity()).getFilteredPortalStudentInfoResult();

//                                    AlertDialog.Builder alert = new AlertDialog.Builder(SplashActivity.this);
//                                    alert.setTitle(syncMyAccountResult.getBillingBalance());
//                                    alert.setMessage(user.toString());
//                                    alert.show();

                                    if(syncMyAccountResult.getBillingBalance().equalsIgnoreCase("")){
                                        //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        intent.putExtra("StudentID", user.getUserID());
                                        //false because you dint log in, you just went back, then your your system check if there you had logged
                                        intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                        // intent.putExtra("appcode",user.getAppcode());
                                        intent.putExtra("student", user.getUserID());
                                        intent.putExtra("appcode", user.getAppcode());
                                        intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                        startActivity(intent);
                                        getActivity().finish();
                                    }else{
                                        if(Float.valueOf(syncMyAccountResult.getBillingBalance()) >0f){



                                            //if(500.0 > 0.0){
                                            Intent intent = new Intent(getActivity().getBaseContext(), SyncMyAccount.class);
                                            intent.putExtra("StudentID",user.getUserID());
                                            intent.putExtra("appcode",user.getAppcode());
                                            intent.putExtra("CALL_FROM", "SPLASHSCREEN");
                                            startActivity(intent);
                                            getActivity().finish();

                                        }
                                        else
                                        {



                                            //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            intent.putExtra("StudentID", user.getUserID());
                                            //false because you dint log in, you just went back, then your your system check if there you had logged
                                            intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                            // intent.putExtra("appcode",user.getAppcode());
                                            intent.putExtra("student", user.getUserID());
                                            intent.putExtra("appcode", user.getAppcode());
                                            intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                            startActivity(intent);
                                            getActivity().finish();
                                        }


                                    }



                                }



                            }else if(user.getLogin_status() == ""){

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                                startActivity(intent);
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                            startActivity(intent);
                            getActivity().finish();
                        }

                        //}
                    }else if(staff != null  ){

                        // Staff signup = new StaffDao(SplashActivity.this).getUserThatSignedUp();

                        // Toast.makeText(SplashActivity.this, signup.toString(), Toast.LENGTH_LONG).show();



                        if(staff != null && staff.getStaff_ID() != "" && staff.getStaff_ID() !=  null){
                            Intent intent = new Intent(getActivity(), HomeScreen.class);

                            //false because you dint log in, you just went back, then your your system check if there you had logged
                            intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                            intent.putExtra("appcode",staff.getAppcode());
                            intent.putExtra("staffID", staff.getStaff_ID());
                            intent.putExtra("userType", staff.getUserType());
                            // intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                            startActivity(intent);
                            getActivity().finish();
                        }else{

                            Intent i = new Intent(getActivity(), LoginActivity.class);//should be loginActivity
                            i.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                            i.putExtra("isLog_In", true);
                            startActivity(i);
                            getActivity().finish();

                        }


                    } else {////that means that thre is no login in user, hence the first time the app was launched. should move to login
                        //if first time, call loginActivity
                        Intent i = new Intent(getActivity(), LoginActivity.class);//should be loginActivity
                        i.putExtra(DisplayLayout.DISPLAY_LAYOUT, "DISPLAY_LAYOUT");
                        i.putExtra("isLog_In", true);
                        startActivity(i);
                        getActivity().finish();
                    }
                }
            }, showTimer? 4000:1);

            super.onPostExecute(user);
        }

        @Override
        protected AuthenticateUserResponse doInBackground(Void... params) {

            List<AuthenticateUserResponse> authenticateUserResponseList  = ParentMziziDatabase.getInstance(getActivity()).getAuthenticateUserResponseDao().getAuthenticateUserResponse();


            AuthenticateUserResponse authenticateUserResponse = null;
            if(authenticateUserResponseList.size() > 0){//if this is false, authenticateUserResponse will refer to null
                authenticateUserResponse = authenticateUserResponseList.get(0);
            }



            return authenticateUserResponse;
        }
    }

}
