package ultratude.com.staff.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.adapters.CustomAdapter;
import ultratude.com.staff.datepickerfragments.DatePickerFragment_DateFrom;
import ultratude.com.staff.datepickerfragments.DatePickerFragment_DateTo;
import ultratude.com.staff.datepickerfragments.DatePickerFragment_LeaveDurationDates;
import ultratude.com.staff.expandablelist.ChildInfo;
import ultratude.com.staff.expandablelist.GroupInfo;
import ultratude.com.staff.R;
import ultratude.com.staff.spinnermodel.LeaveTypeSpinner;
import ultratude.com.staff.spinnermodel.StaffNameSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LeaveTypeDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.StaffLeaveApplication;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;

/**
 * Created by James on 05/01/2019.
 */

public class ManageLeaveApplicationScreen extends AppCompatActivity implements View.OnClickListener,
        DatePickerFragment_DateTo.DatePickerFragment_DateToInteractionListener,
        DatePickerFragment_DateFrom.DatePickerFragment_DateFromInteractionListener,
        DatePickerFragment_LeaveDurationDates.DatePickerFragment_LeaveDurationDatesInteractionListener {


    private Spinner sp_leaveType_ID, sp_delegateTo_ID;
    private TextInputEditText txt_daysApplied_ID;
    private TextView txt_dateFrom_ID, txt_dateTo_ID;
    private Button btn_save_ID;

    private TextView txt_confirmation_message;
    private ProgressBar pb_progressbar_ID;


    private ImageView img_date_from;//, img_date_to;


    private LeaveTypeSpinner leaveTypeSelected = null;
    private StaffNameSpinner delegateToSelected = null;
    private String relationSelected = null;


    private static  String constructDateFrom = null;

    private ImageView image_leave_type_sp_ID, image_delegate_to_sp_ID;


    private LinkedHashMap<String, GroupInfo> subjects = new LinkedHashMap<>();

    private CustomAdapter listAdapter;
    private ExpandableListView simpleExpandableListview;

    private RelativeLayout no_internet_connection_layout_ID;
    private Button btn_tryagain_ID;
    private LinearLayout inner_leave_application_layout_ID;
   // private ExpandableListView expand_list_view_entitlements_ID;
    private LinearLayout pb_leave_progress_layout;

    private TextInputEditText txt_contact_name_ID,txt_contact_phone_ID,txt_contact_email_ID;
    private Spinner sp_relation_ID;
    private ImageView image_contact_relation_sp_ID;



    private void resetFields(){
        ArrayList<StaffNameSpinner> staffNameSpinnerArrayList = new AllStaffDAO(this).getStaffNameSpinner();
        ArrayAdapter<StaffNameSpinner> delegateAdpater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, staffNameSpinnerArrayList);
        sp_delegateTo_ID.setAdapter(delegateAdpater);

        ArrayList<String> relations = new ArrayList<>();
        relations.add("Select Contact Relation");
        relations.add("Spouse");
        relations.add("Relative");
        relations.add("Sibling");
        ArrayAdapter<String>  relationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, relations);
        sp_relation_ID.setAdapter(relationAdapter);

        ArrayList<LeaveTypeSpinner> leaveTypeSpinnerArrayList = new LeaveTypeDAO(this).getLeaveTypeSpinner();
        ArrayAdapter<LeaveTypeSpinner>  leaveTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, leaveTypeSpinnerArrayList);
        sp_leaveType_ID.setAdapter(leaveTypeAdapter);

        txt_dateFrom_ID.setHint("From");
        txt_dateTo_ID.setHint("To");

    }

    private TextView no_leave_entitlement_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.leave_application_layout);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        no_leave_entitlement_ID = findViewById(R.id.no_leave_entitlement_ID);
        txt_contact_name_ID = findViewById(R.id.txt_contact_name_ID);
        txt_contact_phone_ID = findViewById(R.id.txt_contact_phone_ID);
        txt_contact_email_ID = findViewById(R.id.txt_contact_email_ID);
        sp_relation_ID = findViewById(R.id.sp_relation_ID);
        image_contact_relation_sp_ID = findViewById(R.id.image_contact_relation_sp_ID);
        image_contact_relation_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_relation_ID.performClick();
            }
        });

        ArrayList<String> relations = new ArrayList<>();
        relations.add("Select Contact Relation");
        relations.add("Spouse");
        relations.add("Relative");
        relations.add("Sibling");
        ArrayAdapter<String>  relationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, relations);
        sp_relation_ID.setAdapter(relationAdapter);
        sp_relation_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        relationSelected = null;
                        break;
                    default:
                        relationSelected =  (String) parent.getSelectedItem();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                relationSelected = null;
            }
        });



        txt_daysApplied_ID = findViewById(R.id.txt_daysApplied_ID);

        pb_progressbar_ID = findViewById(R.id.pb_leave_application_progress);
        txt_confirmation_message = findViewById(R.id.txt_confirmation_message);


        btn_save_ID = findViewById(R.id.btn_save_ID);
        btn_save_ID.setOnClickListener(this);

        inner_leave_application_layout_ID = findViewById(R.id.inner_leave_application_layout_ID);
        no_internet_connection_layout_ID = findViewById(R.id.no_internet_connection_layout_ID);
       // expand_list_view_entitlements_ID = findViewById(R.id.expand_list_view_entitlements_ID);
        pb_leave_progress_layout = findViewById(R.id.pb_leave_progress_layout);
        btn_tryagain_ID = findViewById(R.id.btn_tryagain_ID);
        btn_tryagain_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        simpleExpandableListview = findViewById(R.id.expand_list_view_entitlements_ID);

        sendRequest();
        simpleExpandableListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                GroupInfo headerInfo = deptList.get(groupPosition);
//                ChildInfo detailInfo = headerInfo.getChildInfoList().get(childPosition);
//                Toast.makeText(getApplicationContext(), "Clicked on ::" + headerInfo.getEntitlement() + "/" + detailInfo.getDaysEntitled(), Toast.LENGTH_LONG).show();
                return false;
            }

        });

        simpleExpandableListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                GroupInfo headerInfo = deptList.get(groupPosition);
//                Toast.makeText(getApplicationContext(), "Header is ::" + headerInfo.getEntitlement(),
//                        Toast.LENGTH_LONG).show();
                return false;
            }

        });

        image_delegate_to_sp_ID = findViewById(R.id.image_delegate_to_sp_ID);
        image_leave_type_sp_ID = findViewById(R.id.image_leave_type_sp_ID);


        sp_delegateTo_ID = findViewById(R.id.sp_delegateTo_ID);

         //DUMMY DATA
//        String[] delegateValues  =  {"Select a Delegate", "DIXON  DAVENPORT","REBECCA  DURHAM","HANEY  OSBORN","RIVERA  ODONNELL","BALLARD  WILKERSON", "CLARISSA  ROGERS","ERICA  HULL","RENE  FRANCO"};
//        ArrayAdapter<String> delegateAdpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, delegateValues);


        ArrayList<StaffNameSpinner> staffNameSpinnerArrayList = new AllStaffDAO(this).getStaffNameSpinner();
        ArrayAdapter<StaffNameSpinner> delegateAdpater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, staffNameSpinnerArrayList);
        sp_delegateTo_ID.setAdapter(delegateAdpater);
        image_delegate_to_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_delegateTo_ID.performClick();
            }
        });
        sp_delegateTo_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        delegateToSelected = (StaffNameSpinner) parent.getSelectedItem();;
                        break;
                    default:
                        delegateToSelected =  (StaffNameSpinner) parent.getSelectedItem();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                delegateToSelected = null;
            }
        });



        sp_leaveType_ID = findViewById(R.id.sp_leaveType_ID);
        //DUMMY DATA
//        String[] leaveTypeValues = {"Select a Leave Type","Annual Leave"};
//        ArrayAdapter<String>  leaveTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, leaveTypeValues);

        ArrayList<LeaveTypeSpinner> leaveTypeSpinnerArrayList = new LeaveTypeDAO(this).getLeaveTypeSpinner();
        ArrayAdapter<LeaveTypeSpinner>  leaveTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, leaveTypeSpinnerArrayList);
        image_leave_type_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_leaveType_ID.performClick();
            }
        });
        sp_leaveType_ID.setAdapter(leaveTypeAdapter);
        sp_leaveType_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        leaveTypeSelected = (LeaveTypeSpinner) parent.getSelectedItem();
                        break;
                    default:
                        leaveTypeSelected = (LeaveTypeSpinner) parent.getSelectedItem();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                leaveTypeSelected = null;
            }
        });

        txt_dateFrom_ID = findViewById(R.id.txt_dateFrom_ID);
        txt_dateFrom_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBtnProgress(false);

                DialogFragment newFragment = new DatePickerFragment_LeaveDurationDates();
                Bundle b = new Bundle();
                b.putString("Date", "FROM");
                newFragment.setArguments(b);
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        img_date_from = findViewById(R.id.date_image_from_ID);
        img_date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBtnProgress(false);
                DialogFragment newFragment = new DatePickerFragment_LeaveDurationDates();
                Bundle b = new Bundle();
                b.putString("Date", "FROM");
                newFragment.setArguments(b);
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        //img_date_to = findViewById(R.id.date_image_to_ID);
//        img_date_to.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(constructDateFrom == 0L){
//                    showBtnProgress(false);
//                    txt_confirmation_message.setText("Please enter Date From");
//                }else{
//                    DialogFragment newFragment = new DatePickerFragment_LeaveDurationDates();
//                    Bundle b = new Bundle();
//                    b.putString("Date", "TO");
//                    newFragment.setArguments(b);
//                    newFragment.show(getSupportFragmentManager(), "date picker");
//                }
//
//            }
//        });


       txt_dateTo_ID = findViewById(R.id.txt_dateTo_ID);
//        txt_dateTo_ID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(constructDateFrom == 0L){
//                    showBtnProgress(false);
//                    txt_confirmation_message.setText("Please enter Date From");
//                }else{
//                    DialogFragment newFragment = new DatePickerFragment_LeaveDurationDates();
//                    Bundle b = new Bundle();
//                    b.putString("Date", "TO");
//                    newFragment.setArguments(b);
//                    newFragment.show(getSupportFragmentManager(), "date picker");
//                }
//
//            }
//        });



    }

    @Override
    public void onClick(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        saveDetailes();
    }


    private void saveDetailes(){

        String dateFrom = txt_dateFrom_ID.getText().toString();
        String dateTo = txt_dateTo_ID.getText().toString();
        String daysApplied = txt_daysApplied_ID.getText().toString();
        String contactName = txt_contact_name_ID.getText().toString();
        String contactPhone = txt_contact_phone_ID.getText().toString();
        String contactEmail = txt_contact_email_ID.getText().toString();
        String contactRelation = relationSelected;


        Log.d(this.getPackageName().toUpperCase(), String.valueOf( checkContactName(contactName) && checkContactPhone(contactPhone) && checkEmail(contactEmail) &&checkRelation(contactRelation) && checkLeaveType(leaveTypeSelected) && checkDelegateTo(delegateToSelected) && checkDateFrom(dateFrom) && checkDateTo(dateTo) && checkDaysApplied(daysApplied)));

        if( checkContactName(contactName) && checkContactPhone(contactPhone) && checkEmail(contactEmail) &&checkRelation(contactRelation) && checkLeaveType(leaveTypeSelected) && checkDelegateTo(delegateToSelected) && checkDateFrom(dateFrom) && checkDateTo(dateTo) && checkDaysApplied(daysApplied)){

            AllStaff delegate = new AllStaffDAO(this).getStaffWithID(String.valueOf(delegateToSelected.getStaffID()));
            Staff staff = new StaffDao(this).getUserThatSignedUp();

            StaffLeaveApplication applyLeave = new StaffLeaveApplication(
                    staff.getStaff_ID(),
                    String.valueOf(leaveTypeSelected.getLeaveTypeID()),
                    daysApplied.trim(),
                    constructDateFrom,
                    delegate.getStaffID(),
                    delegate.getPhoneNumber(),
                    delegate.getEmail(),
                    contactName,
                    contactPhone,
                    contactEmail,
                    contactRelation,
                    staff.getAppcode()
            );


         //  FancyToast.makeText(this,"Thanks all fields are verified.Please do continue...",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();




           // new SaveDetailsToDB(ManageLeaveApplicationScreen.this).execute(applyLeave);
            final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

                sendRequestToApplyForLeave(this, apiInterface,applyLeave);

            }else{
//                AlertDialog.Builder alert = new AlertDialog.Builder(this);
//                alert.setTitle("Error");
//                alert.setMessage("Couldnt connect to server. Make sure your phone has an internet connection and try again!");
//                alert.setCancelable(true);
//                alert.show();

//                AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(ManageLeaveApplicationScreen.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View view  = inflater.inflate(R.layout.access_denied_layout,null, false);
//                accessDeniedAlert.setView(view);
//                accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                accessDeniedAlert.show();
                AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(ManageLeaveApplicationScreen.this);
                LayoutInflater inflater = getLayoutInflater();
                View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                accessDeniedAlert.setView(view);
                accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.cancel();
                        sendRequest();
                    }
                });
                accessDeniedAlert.setNegativeButton("Home", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        onBackPressed();
                    }
                });
                accessDeniedAlert.show();
            }
        }

    }


    private void sendRequestToApplyForLeave(final Context mContext, APIInterface apiInterface, StaffLeaveApplication applyLeave){

        showBtnProgress(true);

        txt_confirmation_message.setVisibility(View.VISIBLE);
        txt_confirmation_message.setText("Please wait for confirmation...");


        Call<String> userCall = apiInterface.saveLeaveApplication(applyLeave);
        userCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

               // Toast.makeText(mContext, response.body(), Toast.LENGTH_SHORT).show();
                showBtnProgress(false);


                if(response.isSuccessful()){
                    if(response.code() == 200){
                        resetFields();
                        FancyToast.makeText(ManageLeaveApplicationScreen.this, response.body().toString(), FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    }else if(response.code() == 201){
                        txt_confirmation_message.setVisibility(View.VISIBLE);
                        txt_confirmation_message.setText(response.body());
                    }

                }else{

                   // showBtnProgress(false);
                    txt_confirmation_message.setText(response.body());


                    //you dont have to delete all the data
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
              //  Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();

                showBtnProgress(false);

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
            }
        });
    }





    private boolean checkDaysApplied(String daysApplied){
        if(TextUtils.isEmpty(daysApplied)){
            txt_confirmation_message.setText("");
            txt_daysApplied_ID.setError("Please enter Days Applied");
            return false;
        }

        if(!TextUtils.isDigitsOnly(daysApplied)){
            txt_confirmation_message.setText("");
            txt_daysApplied_ID.setError("Please specify a number");
            return false;
        }

        return true;
    }

    private boolean checkContactName(String name){
        if(TextUtils.isEmpty(name)){
            txt_confirmation_message.setText("");
            txt_contact_name_ID.setError("Please enter Contact Name.");
            return false;
        }

        if(TextUtils.isDigitsOnly(name)){
            txt_confirmation_message.setText("");
            txt_contact_name_ID.setError("Please specify a valid input.");
            return false;
        }
        return true;
    }

    private boolean checkContactPhone(String contactPhone){
        if(TextUtils.isEmpty(contactPhone)){
            txt_confirmation_message.setText("");
            txt_contact_phone_ID.setError("Please enter Contact Phone.");
            return false;
        }

        if(!TextUtils.isDigitsOnly(contactPhone)){
            txt_confirmation_message.setText("");
            txt_contact_phone_ID.setError("Please specify a valid input.");
            return false;
        }

        if(contactPhone.length() != 10){
            txt_confirmation_message.setText("");
            txt_contact_phone_ID.setError("Please specify a valid Phone No.\ne.g 07********");
            return false;
        }

        return true;
    }
    private boolean checkEmail(String email){
        if(TextUtils.isEmpty(email)){
            txt_confirmation_message.setText("");
            txt_contact_email_ID.setError("Please enter Contact Email.");
            return false;
        }

        if(!email.contains("@") && !email.contains(".com")){
            txt_confirmation_message.setText("");
            txt_contact_email_ID.setError("Please specify a valid email.\n e.g abc@gmail.com/abc@yahoo.com");
            return false;
        }

        if(TextUtils.isDigitsOnly(email)){
            txt_confirmation_message.setText("");
            txt_contact_email_ID.setError("Please specify a valid input.");
            return false;
        }
        return true;
    }
    private boolean checkRelation(String relation){
        if(TextUtils.isEmpty(relation)){
            txt_confirmation_message.setText("");
            txt_confirmation_message.setText("Please enter Contact Relation");
            return false;
        }
        if(TextUtils.isDigitsOnly(relation)){
            txt_confirmation_message.setText("");
            txt_confirmation_message.setText("Please specify a valid input.");
            return false;
        }

        return true;

    }

    private boolean checkDateTo(String dateTo){
        if(TextUtils.isEmpty(dateTo)){
            showBtnProgress(false);
            txt_confirmation_message.setText("Please enter Date To");
            return false;
        }

        return true;
    }

    private boolean checkDateFrom(String dateFrom){
        if(TextUtils.isEmpty(dateFrom)){
            showBtnProgress(false);
            txt_confirmation_message.setText("Please enter Date From");
            return false;
        }
        return true;
    }

    private boolean checkDelegateTo(StaffNameSpinner delegateTo){
        if(delegateTo == null){
            showBtnProgress(false);
            txt_confirmation_message.setText("Please select a Delegate");
            return false;

        }

        return true;
    }

    private boolean checkLeaveType(LeaveTypeSpinner leaveType){
        if(leaveType == null){
            showBtnProgress(false);
            txt_confirmation_message.setText("Please select a Leave Type");
            return false;
        }

        return true;
    }


    private void showBtnProgress(boolean show){

        txt_confirmation_message.setText("");
        if (show) {
            pb_progressbar_ID.setVisibility(View.VISIBLE);
            btn_save_ID.setVisibility(View.GONE);
            txt_confirmation_message.setVisibility(View.INVISIBLE);
        }else{
            pb_progressbar_ID.setVisibility(View.GONE);
            btn_save_ID.setVisibility(View.VISIBLE);
            txt_confirmation_message.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onDatePickerFragment_LeaveDurationDatesInteraction(String dateToSaveToDB, long millisDate , String leaveDurationDates) {


        if(checkDaysApplied(txt_daysApplied_ID.getText().toString().trim())){



            Date datePassed = new Date(millisDate);
            Calendar calendarDatePassed = Calendar.getInstance();
            calendarDatePassed.setTime(datePassed);
            Calendar currentDate = Calendar.getInstance();
            boolean dateBefore = calendarDatePassed.before(currentDate);

           // Toast.makeText(this, String.valueOf("before "+ dateBefore), Toast.LENGTH_SHORT).show();

            if(dateBefore){
                showBtnProgress(false);
                txt_dateFrom_ID.setText("From");
                txt_confirmation_message.setText("Please enter a later date.");
            }else{

                //set datefrom, to save to db
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyyy");
                constructDateFrom = sdf2.format(calendarDatePassed.getTime());

                //set datefrom, to display in textview
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
                txt_dateFrom_ID.setText(sdf.format(calendarDatePassed.getTime()));


                //set dateTo, to display in textview
                if(checkDaysApplied(txt_daysApplied_ID.getText().toString())) {
                    int daysApplied = Integer.valueOf(txt_daysApplied_ID.getText().toString());
                    calendarDatePassed.add(Calendar.DAY_OF_MONTH, daysApplied + 1);
                    txt_dateTo_ID.setText(sdf.format(calendarDatePassed.getTime()));
                }


            }


        }


    }

    @Override
    public void onDatePickerFragment_DateToInteraction(int year, int month, int day) {

    }



    @Override
    public void onDatePickerFragment_DateFromInteraction(int year, int month, int day) {

    }

    private class SaveDetailsToDB extends AsyncTask<String, Void, String> {

        private Context mContext;

        public SaveDetailsToDB(Context context){
            this.mContext = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showBtnProgress(true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            //THIS SHOULD BE A ALERT DIALOG
            showBtnProgress(false);
            txt_confirmation_message.setText("Detailed Saved Successfully");

        }

        @Override
        protected String doInBackground(String... params) {
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//

//
//
        private ArrayList<GroupInfo> addChildToGroup(String department, ArrayList<ChildInfo> productArrayList) {

             ArrayList<GroupInfo> deptList = new ArrayList<>();

            GroupInfo headerInfo = subjects.get(department);

            if (headerInfo == null) {

                headerInfo = new GroupInfo();
                headerInfo.setEntitlement(department);
                headerInfo.setChildInfoList(productArrayList);
               // subjects.put(department, headerInfo);
                deptList.add(headerInfo);
            }


            return deptList;
        }
//
//
        private void expandAll() {
            int count = listAdapter.getGroupCount();
            for (int i = 0; i < count; i++) {
                simpleExpandableListview.expandGroup(i);
            }
        }
//        private void collapseAll() {
//            int count = listAdapter.getGroupCount();
//            for (int i = 0; i < count; i++) {
//                simpleExpandableListview.collapseGroup(i);
//            }
//        }
//        public void expandCollapse(View view) {
//            if (isExpand) {
//                collapseAll();
//                isExpand = false;
//            } else {
//                expandAll();
//                isExpand = true;
//            }
//        }
//public void showNoInternetConnectionLayout(boolean show){
//
//    if (show){
//        no_internet_connection_layout_ID.setVisibility(View.VISIBLE);
//        inner_leave_application_layout_ID.setVisibility(View.GONE);
//    }else{
//        no_internet_connection_layout_ID.setVisibility(View.GONE);
//        inner_leave_application_layout_ID.setVisibility(View.VISIBLE);
//    }
//
//
//}



    private void sendRequest(){

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        final ConnectivityManager cm = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

            sendRequestForStaffLeaveEntitlements(ManageLeaveApplicationScreen.this, apiInterface);

        }else{

//
//           // showNoInternetConnectionLayout(true);
//            //btn_tryagain_ID.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // reSendRequest();
//                }
//            });

            AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(ManageLeaveApplicationScreen.this);
            LayoutInflater inflater = getLayoutInflater();
            View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
            accessDeniedAlert.setView(view);
            accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog.cancel();
                    sendRequest();
                }
            });
            accessDeniedAlert.setNegativeButton("Home", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    onBackPressed();
                }
            });
            accessDeniedAlert.show();


        }
    }


    private void sendRequestForStaffLeaveEntitlements(final Context mContext, APIInterface apiInterface){

        //here is the problem
        //showBtnProgress(true);

//        PortalDisciplinaryCasesRequest portalDisciplinaryCasesRequest = new PortalDisciplinaryCasesRequest(
////                studentRequest.getStudentID(),
////                String.valueOf(yearSelected.getYearID()),
////                String.valueOf(termSelected.getTermID()),
////                studentRequest.getOrganizationID(),
////                studentRequest.getAppcode()
//                "22265",
//                "2018",
//                "3",
//                "45",
//                "1000"
//        );

        Staff staff = new StaffDao(mContext).getUserThatSignedUp();
        pb_leave_progress_layout.setVisibility(View.VISIBLE);
        simpleExpandableListview.setVisibility(View.GONE);

       StaffRequest request = new StaffRequest(staff.getStaff_ID(), staff.getSchoolID(),staff.getOrganizationID(), staff.getAppcode());
       // StaffRequest request = new StaffRequest("353","30","45","1000");

        Call<ArrayList<ChildInfo>> userCall = apiInterface.getChildList(request);
        userCall.enqueue(new Callback<ArrayList<ChildInfo>>() {
                             @Override
                             public void onResponse(Call<ArrayList<ChildInfo>> call, Response<ArrayList<ChildInfo>> response) {
                                 if(response.isSuccessful()){

                                     if(response.code() == 200){
                                         //this should be within after sending request for the data
                                         listAdapter = new CustomAdapter(ManageLeaveApplicationScreen.this, addChildToGroup("Your Leave Entitlements", response.body()));
                                         simpleExpandableListview.setAdapter(listAdapter);
                                         expandAll();
                                         pb_leave_progress_layout.setVisibility(View.INVISIBLE);
                                         simpleExpandableListview.setVisibility(View.VISIBLE);
                                     }else{
                                         no_leave_entitlement_ID.setVisibility(View.VISIBLE);
                                         pb_leave_progress_layout.setVisibility(View.INVISIBLE);
                                         simpleExpandableListview.setVisibility(View.INVISIBLE);
                                     }



                                 }

                             }

                             @Override
                             public void onFailure(Call<ArrayList<ChildInfo>> call, Throwable t) {
                                 pb_leave_progress_layout.setVisibility(View.INVISIBLE);
                                 simpleExpandableListview.setVisibility(View.INVISIBLE);
                                 //showBtnProgress(false);
                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();

                             }
                         }
        );
    }

}

