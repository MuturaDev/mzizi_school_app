package ultratude.com.mzizi.uiactivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.backgroundtasks.DeleteThisStudentData;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.modelclasses.OpenFragments;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.webservice.APIRequest;


/**
 * Created by James on 11/09/2018.
 */

    //if the user's billing balance is greater than 0, the user is redirected here
public class SyncMyAccount extends AppCompatActivity {


    private Intent intent;
    private MyAccountAdapter myAccountAdapter;
    private MyAccountAsyncTask myAccountAsyncTask;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView billingBalanceText;
    private LinearLayout refreshClickableLayout;

    private Student student;
    public  AlertDialog.Builder alert;

    private ImageView logout_syncMyAccount;

            //getIntent().getStringExtra("SPLASHSCREEN");//AUTHENTICATE_USER

    private static String CALL_FROM ;

    public ProgressDialog bar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        setContentView(R.layout.my_account_layout);



        bar = new ProgressDialog(this);
        bar.setCancelable(true);
        bar.setIndeterminate(false);
        bar.setMessage("Please wait, fetching data from school...");
        bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        CALL_FROM = getIntent().getStringExtra("CALL_FROM");//AUTHENTICATE_USER   SPLASHSCREEN;

        billingBalanceText = findViewById(R.id.billing_balance_text);
        refreshClickableLayout = findViewById(R.id.refresh_clickable_layout);
        recyclerView = findViewById(R.id.myaccount_recyclerView);
        logout_syncMyAccount = findViewById(R.id.logout_synMyAccount);
        logout_syncMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(SyncMyAccount.this,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        //Toast.makeText(SyncMyAccount.this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.logout_sync_ID:

                                //start

                                alert = new AlertDialog.Builder(SyncMyAccount.this);
                                alert.setCancelable(false);
                                alert.setMessage("Are you sure you want to logout?");
                                alert.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //this should logout the user and move the user to login screen;
                                        SharedPreferences pshare = getSharedPreferences(GetConstants.PREF_KEY_NAME, 0);
                                        //if there is a user still logged in, go to home screen instead, otherwise, go to loginscreen
                                        if (true) {
                                            //clear this session

                                            //DELETE FROM ROOMDATABASE



                                            //this task just retrievs SyncMyAccountResult, Notification
                                           // showProgress(true);//removed because this is not the MainActivity, where its needed
                                            new DeleteThisStudentData(SyncMyAccount.this).execute();
                                            NotificationTopDisplay.giveContext(SyncMyAccount.this);

                                            NotificationTopDisplay.GetAuthenticatedUser.cancelNotificationDisplay(null);
                                            //you should not use this, for it will delete all the data we have
                                            //PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
                                        } else {
                                            try {
                                                throw new Exception("For you to reach this screen, you must have logged in Exception");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }
                                });
                                alert.setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alert.show();

                                //end

                                // do your code
                                return true;
                            default:
                                return false;
                        }


                    }
                });
                popupMenu.inflate(R.menu.popup_menu_logout);
                popupMenu.show();


            }
        });


        student = new Student(getIntent().getStringExtra("StudentID"), getIntent().getStringExtra("appcode"));

        final String studentID = student.getStudentID();
        final String appcode = student.getAppcode();

       // Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + "before", Toast.LENGTH_LONG).show();




        refreshClickableLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //start of onCLick


                if(CALL_FROM.equalsIgnoreCase("SPLASHSCREEN")){

                    //if the call is from the splashscreen, that means you had logged in before.
                    //hence the user just needs to pay, the amount, then refreshes, to be allowed to continue
                    //continue to MainActivity.



                    //send a request to syncMyAccount because this is refresh handler

                    //start


                    //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                                    //request to PortalStudentInfo

                    final ProgressDialog progress = new ProgressDialog(SyncMyAccount.this);
                    progress.setMessage("Please, wait for confirmation...");
                    progress.setCancelable(false);
                    progress.setIndeterminate(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();


                    Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
                    userCall.enqueue(new Callback<SyncMyAccountResult>() {
                        @Override
                        public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {



                            SyncMyAccountResult result = response.body();

                            if(result == null){
                                progress.cancel();

                            }else{

                               // Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + result.toString(), Toast.LENGTH_LONG).show();


                                //first delete the previous
                                new SyncMyAccountDAO(SyncMyAccount.this).deleteForSyncMyAccountResult(student.getStudentID());
                                result.setStudID(Integer.valueOf(student.getStudentID()));
                                new SyncMyAccountDAO(SyncMyAccount.this).insertSyncMyAccountResult(result);

                                if(Float.valueOf(result.getBillingBalance())>0f){//for this the user is redirected to SyncMyAccount
                                    // if(500>0){//should redirect

                                    //am in SyncMyAccount Activity, do i really have to start the activity again, or should i just update the information being displayed

                                    //start of update
                                    billingBalanceText.setText(result.getBillingBalance());

                                    List<String> rightColumnList = new ArrayList<>();
                                    rightColumnList.add(result.getPortalPaybill().replace(" ", ""));
                                    rightColumnList.add(result.getPortalAccount());
                                    rightColumnList.add(result.getBillingBalance());



                                    //hardcoded data
                                    //        rightColumnList.add("45 95 12".replace(" ",""));
                                    //        rightColumnList.add("01-9345");
                                    //        rightColumnList.add("500");//ask where should i get this.

                                    List<String> leftColumnList = new ArrayList<>();
                                    leftColumnList.add("Paybill: ");
                                    leftColumnList.add("A/C No.: ");
                                    leftColumnList.add("Amount: ");

                                    myAccountAdapter = new MyAccountAdapter(SyncMyAccount.this, rightColumnList, leftColumnList );

                                    linearLayoutManager = new LinearLayoutManager(SyncMyAccount.this, LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(myAccountAdapter);

                                    progress.cancel();

                                    //end update

                                    //this should be commented
                                    // Intent intent = new Intent(SyncMyAccount.this, SyncMyAccount.class);
                                    // intent.putExtra("StudentID", student.getUserID());
                                    // intent.putExtra("appcode", student.getAppcode());
                                    // intent.putExtra("CALL_FROM", "AUTHENTICATE_USER");

                                    // startActivity(intent);
                                    // finish();


                                }else {//for this, the user continues

                                    progress.cancel();
                                    //the redirect is from the splashscreen, had logged in before, then redirected to MainActivity

                                    //call the main activity

                                    // Toast.makeText(SyncMyAccount.this, "Thank you for renewing your MziziApp account...", Toast.LENGTH_SHORT).show();

                                    //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount
                                    Intent intent = new Intent(SyncMyAccount.this, MainActivity.class);
                                    intent.putExtra("StudentID", student.getStudentID());
                                    //false because you dint log in, you just went back, then your your system check if there you had logged
                                    intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                    intent.putExtra("appcode",student.getAppcode());
                                    intent.putExtra("student", student.getStudentID());
                                    intent.putExtra("appcode", student.getAppcode());
                                    intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                    startActivity(intent);
                                    finish();






                                }


                            }



                        }

                        @Override
                        public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                            progress.cancel();

                            Toast.makeText(SyncMyAccount.this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
                            call.cancel();

                        }
                    });

                    ///end



                }else if(CALL_FROM.equalsIgnoreCase("SWITCH_SIBLING")){



                    //send a request to syncMyAccount because this is refresh handler

                    //start


                    //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                                    //request to PortalStudentInfo

                    final ProgressDialog progress = new ProgressDialog(SyncMyAccount.this);
                    progress.setMessage("Please, wait for confirmation...");
                    progress.setCancelable(false);
                    progress.setIndeterminate(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();


                    Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
                    userCall.enqueue(new Callback<SyncMyAccountResult>() {
                        @Override
                        public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {


                            response.isSuccessful();
                            response.code();



                            SyncMyAccountResult result = response.body();

                            if(result == null){
                                progress.cancel();

                            }else{

                                // Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + result.toString(), Toast.LENGTH_LONG).show();


                                //first delete the previous
                                new SyncMyAccountDAO(SyncMyAccount.this).deleteForSyncMyAccountResult(student.getStudentID());
                                result.setStudID(Integer.valueOf(student.getStudentID()));
                                new SyncMyAccountDAO(SyncMyAccount.this).insertSyncMyAccountResult(result);

                                if(Float.valueOf(result.getBillingBalance())>0f){//for this the user is redirected to SyncMyAccount
                                    // if(500>0){//should redirect

                                    //am in SyncMyAccount Activity, do i really have to start the activity again, or should i just update the information being displayed

                                    //start of update
                                    billingBalanceText.setText(result.getBillingBalance());

                                    List<String> rightColumnList = new ArrayList<>();
                                    rightColumnList.add(result.getPortalPaybill().replace(" ", ""));
                                    rightColumnList.add(result.getPortalAccount());
                                    rightColumnList.add(result.getBillingBalance());



                                    //hardcoded data
                                    //        rightColumnList.add("45 95 12".replace(" ",""));
                                    //        rightColumnList.add("01-9345");
                                    //        rightColumnList.add("500");//ask where should i get this.

                                    List<String> leftColumnList = new ArrayList<>();
                                    leftColumnList.add("Paybill: ");
                                    leftColumnList.add("A/C No.: ");
                                    leftColumnList.add("Amount: ");

                                    myAccountAdapter = new MyAccountAdapter(SyncMyAccount.this, rightColumnList, leftColumnList );

                                    linearLayoutManager = new LinearLayoutManager(SyncMyAccount.this, LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(myAccountAdapter);

                                    progress.cancel();

                                    //end update

                                    //this should be commented
                                    // Intent intent = new Intent(SyncMyAccount.this, SyncMyAccount.class);
                                    // intent.putExtra("StudentID", student.getUserID());
                                    // intent.putExtra("appcode", student.getAppcode());
                                    // intent.putExtra("CALL_FROM", "AUTHENTICATE_USER");

                                    // startActivity(intent);
                                    // finish();


                                }else {//for this, the user continues

                                    progress.cancel();

                                    RequestFor.login = true;
                                    Object activity  = SyncMyAccount.this;
                                    RequestFor.weakReference  = new WeakReference<>(activity);
                                    RequestFor.sendRequest(student,SyncMyAccount.this,"0", APIRequest.ALL);
                                   // new GetStudentInfoFromMziziApi(SyncMyAccount.this, student).SendRequest(student);
                                }


                            }



                        }

                        @Override
                        public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                            progress.cancel();

                            Toast.makeText(SyncMyAccount.this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
                            call.cancel();

                        }
                    });

                    ///end



                }


                else if(CALL_FROM.equalsIgnoreCase("AUTHENTICATE_USER")){


                    //if from authenticateUser, then the user needs to pay, the amount, then refreshes to make to continue getting the
                    ///rest of the information, because its the users first logged in.

                    //sent




                    //start


                    //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                                    //request to PortalStudentInfo

                    final ProgressDialog progress = new ProgressDialog(SyncMyAccount.this);
                    progress.setMessage("Please, wait for confirmation...");
                    progress.setCancelable(false);
                    progress.setIndeterminate(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();


                    Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
                    userCall.enqueue(new Callback<SyncMyAccountResult>() {
                        @Override
                        public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {

                            progress.cancel();

                            SyncMyAccountResult result = response.body();

                            if(result == null){

                                progress.cancel();
                            }else{


                                //Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + result.toString(), Toast.LENGTH_LONG).show();


                                //first delete the previous
                                new SyncMyAccountDAO(SyncMyAccount.this).deleteForSyncMyAccountResult(student.getStudentID());
                                result.setStudID(Integer.valueOf(student.getStudentID()));
                                new SyncMyAccountDAO(SyncMyAccount.this).insertSyncMyAccountResult(result);

                                //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount
                                if(Float.valueOf(result.getBillingBalance())>0f){//for this the user is redirected to SyncMyAccount
                                    // if(500>0){//should redirect

                                    progress.cancel();

                                    //start of update
                                    billingBalanceText.setText(result.getBillingBalance());

                                    List<String> rightColumnList = new ArrayList<>();
                                    rightColumnList.add(result.getPortalPaybill().replace(" ", ""));
                                    rightColumnList.add(result.getPortalAccount());
                                    rightColumnList.add(result.getBillingBalance());



                                    //hardcoded data
                                    //        rightColumnList.add("45 95 12".replace(" ",""));
                                    //        rightColumnList.add("01-9345");
                                    //        rightColumnList.add("500");//ask where should i get this.

                                    List<String> leftColumnList = new ArrayList<>();
                                    leftColumnList.add("Paybill: ");
                                    leftColumnList.add("A/C No.: ");
                                    leftColumnList.add("Amount: ");

                                    myAccountAdapter = new MyAccountAdapter(SyncMyAccount.this, rightColumnList, leftColumnList );

                                    linearLayoutManager = new LinearLayoutManager(SyncMyAccount.this, LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(myAccountAdapter);

                                    //end update

                                    progress.cancel();


                                    // Intent intent = new Intent(SyncMyAccount.this, SyncMyAccount.class);
                                    // intent.putExtra("StudentID", student.getUserID());
                                    // intent.putExtra("appcode", student.getAppcode());
                                    // intent.putExtra("CALL_FROM", "AUTHENTICATE_USER");

                                    // startActivity(intent);
                                    // finish();


                                }else {//for this, the user continues downlaoding information

                                    progress.cancel();
                                    //the redirect is from the splashscreen, had logged in before, then redirected to continue downloading information to display

                                    //call the main activity

                                    // Toast.makeText(SyncMyAccount.this, "Thank you for renewing your MziziApp account...", Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(SyncMyAccount.this, "Thank you for renewing your MziziApp account...", Toast.LENGTH_SHORT).show();

                                    //call
                                    RequestFor.login = true;
                                    Object activity  = SyncMyAccount.this;
                                    RequestFor.weakReference  = new WeakReference<>(activity);
                                    RequestFor.sendRequest(student,SyncMyAccount.this,"0", APIRequest.ALL);
                                   // new GetStudentInfoFromMziziApi(SyncMyAccount.this, student).SendRequest(student);




                                }




                            }


                        }

                        @Override
                        public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                            progress.cancel();

                            Toast.makeText(SyncMyAccount.this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
                            call.cancel();

                        }
                    });

                    ///end










                }else if(CALL_FROM.equalsIgnoreCase("FRAGMENT")){//this is from other fragments, because every click to view data in each of the fragments, database is checked to make sure the use has billing amout less than, 0
                    //bacause this activity is redirecetd from the fragments, the user needs to pay the amount then refreshes to continue to MainActivity, because the use was locked out.


                    //start

                   // Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + "before", Toast.LENGTH_LONG).show();



                    //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
                    //
                    //                                    //request to PortalStudentInfo

                    final ProgressDialog progress = new ProgressDialog(SyncMyAccount.this);
                    progress.setMessage("Please, wait for confirmation...");
                    progress.setCancelable(false);
                    progress.setIndeterminate(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();


                    Call<SyncMyAccountResult> userCall = apiInterface.getSyncMyAccount(student);
                    userCall.enqueue(new Callback<SyncMyAccountResult>() {
                        @Override
                        public void onResponse(Call<SyncMyAccountResult> call, Response<SyncMyAccountResult> response) {

                            progress.cancel();

                            SyncMyAccountResult result = response.body();
                            response.errorBody();

                            if(result == null){
                               // Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + response.message(), Toast.LENGTH_LONG).show();
                                progress.cancel();
                            }else{


                               // Toast.makeText(SyncMyAccount.this, "FIlterdPortalStudentInfoResult: " + result.toString(), Toast.LENGTH_LONG).show();


                                //first delete the previous
                                new SyncMyAccountDAO(SyncMyAccount.this).deleteForSyncMyAccountResult(student.getStudentID());
                                result.setStudID(Integer.valueOf(student.getStudentID()));
                                new SyncMyAccountDAO(SyncMyAccount.this).insertSyncMyAccountResult(result);

                                if(Float.valueOf(result.getBillingBalance())>0f){//for this the user is redirected to SyncMyAccount
                                    // if(500>0){//should redirect

                                    progress.cancel();

                                    //start of update
                                    billingBalanceText.setText(result.getBillingBalance());

                                    List<String> rightColumnList = new ArrayList<>();
                                    rightColumnList.add(result.getPortalPaybill().replace(" ", ""));
                                    rightColumnList.add(result.getPortalAccount());
                                    rightColumnList.add(result.getBillingBalance());



                                    //hardcoded data
                                    //        rightColumnList.add("45 95 12".replace(" ",""));
                                    //        rightColumnList.add("01-9345");
                                    //        rightColumnList.add("500");//ask where should i get this.

                                    List<String> leftColumnList = new ArrayList<>();
                                    leftColumnList.add("Paybill: ");
                                    leftColumnList.add("A/C No.: ");
                                    leftColumnList.add("Amount: ");

                                    myAccountAdapter = new MyAccountAdapter(SyncMyAccount.this, rightColumnList, leftColumnList );

                                    linearLayoutManager = new LinearLayoutManager(SyncMyAccount.this, LinearLayoutManager.VERTICAL, false);
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(myAccountAdapter);

                                    //end update

                                    progress.cancel();
                                    // Intent intent = new Intent(SyncMyAccount.this, SyncMyAccount.class);
                                    // intent.putExtra("StudentID", student.getUserID());
                                    // intent.putExtra("appcode", student.getAppcode());
                                    // intent.putExtra("CALL_FROM", "AUTHENTICATE_USER");

                                    // startActivity(intent);
                                    // finish();


                                }else {//for this, the user continues

                                    progress.cancel();
                                    //the redirect is from the splashscreen, had logged in before, then redirected to MainActivity

                                    //call the main activity

                                    // Toast.makeText(SyncMyAccount.this, "Thank you for renewing your MziziApp account...", Toast.LENGTH_SHORT).show();

                                    //NOTE:  You should check if his or her balance is greater than 0, then redirected to SyncMyAccount
                                    Intent intent = new Intent(SyncMyAccount.this, MainActivity.class);
                                    intent.putExtra("StudentID", student.getStudentID());
                                    //false because you dint log in, you just went back, then your your system check if there you had logged
                                    intent.putExtra("isLog_In", false);//will check if to call the web service or just retrieve the data from the database
                                    intent.putExtra("appcode",student.getAppcode());
                                    intent.putExtra("student", student.getStudentID());
                                    intent.putExtra("appcode", student.getAppcode());
                                    intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                                    startActivity(intent);
                                    finish();






                                }

                            }



                        }

                        @Override
                        public void onFailure(Call<SyncMyAccountResult> call, Throwable t) {

                            progress.cancel();

                            Toast.makeText(SyncMyAccount.this, "Check your internet connection!", Toast.LENGTH_SHORT).show();
                            call.cancel();

                        }
                    });

                    ///end




                }
                //end of onCLick


            }
        });


        //fill the list before the button is clicked
        SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(this).getFilteredPortalStudentInfoResult();
        billingBalanceText.setText(syncMyAccountResult.getBillingBalance());

        List<String> rightColumnList = new ArrayList<>();
        rightColumnList.add(syncMyAccountResult.getPortalPaybill().replace(" ", ""));
        rightColumnList.add(syncMyAccountResult.getPortalAccount());
        rightColumnList.add(syncMyAccountResult.getBillingBalance());



        //hardcoded data
//        rightColumnList.add("45 95 12".replace(" ",""));
//        rightColumnList.add("01-9345");
//        rightColumnList.add("500");//ask where should i get this.

        List<String> leftColumnList = new ArrayList<>();
        leftColumnList.add("Paybill: ");
        leftColumnList.add("A/C No.: ");
        leftColumnList.add("Amount: ");

        myAccountAdapter = new MyAccountAdapter(this, rightColumnList, leftColumnList );

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myAccountAdapter);
        // recyclerView.scrollToPosition(leftColumnList.size() - 1);

        ReportAnalytics.reportScreenChangeAnalytic(this, "SyncMyAccount Activity");

    }



    private class MyAccountAdapter extends RecyclerView.Adapter<MyAccountAdapter.ViewHolder> {



        private Context context;
        private List<String> rightColumnList;
        private List<String> leftColumnList;



    public MyAccountAdapter(Context context, List<String> rightColumnList, List<String> leftColumnList) {
            this.leftColumnList = leftColumnList;
            this.rightColumnList = rightColumnList;
            this.context = context;
        }

        @Override
        //this inflates each individual layout in the recycleview
        //binds the layout file
        public MyAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //you need to get the view and then return it, a new view holder object
            View view = LayoutInflater.from(context).inflate(R.layout.my_account_layout_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        //here we bind the data to each one of the individual list items
        //this is where all the widgets and the actual data is going to be attached to each individual list item
        public void onBindViewHolder(MyAccountAdapter.ViewHolder holder, final int position) {
            //leftColumn rightcColumn
            //PaybillNo  459512--remember to remove the spaced in between
            //A/c No.    01-9375
            //Amount.    500
            holder.leftColumn.setText(leftColumnList.get(position));//0,1,2,
            holder.rightColumn.setText(rightColumnList.get(position));//0,1,2




        }


        @Override
        public int getItemCount() {
            return leftColumnList.size();
        }

        //this is a class
        //this will take a hold of the views on the item view.
        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView leftColumn, rightColumn;

            public ViewHolder(View itemView){
                super(itemView);
                leftColumn = itemView.findViewById(R.id.left_column);
                rightColumn  = itemView.findViewById(R.id.right_column);

            }
        }


    }
        //she be no good to me, if i held her against her will.
    //i hope the road you take, leads you back to me
    //i set you free even though i love her still
    //the best part of my world

    //this should make a call to the SyncMpesa...if you are using HttpDefaultClient
    private class MyAccountAsyncTask extends AsyncTask<Void, Void, Void>{

        private Context context;

        public MyAccountAsyncTask(Context context){
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }


    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
