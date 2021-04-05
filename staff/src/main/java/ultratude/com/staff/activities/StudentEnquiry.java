package ultratude.com.staff.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.constantclasses.DisplayContants;
import ultratude.com.staff.R;

import ultratude.com.staff.spinnermodel.StudentSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;

/**
 * Created by James on 28/04/2019.
 */

public class StudentEnquiry extends AppCompatActivity {


    private Spinner sp_class_stream_ID,sp_student_name_ID;
    private TextView label_confirmation_message_ID;

    private LinearLayout layout_selected_studentName;
    private TextView txt_selected_studentName;

    private RelativeLayout btn_exam_history_ID, btn_attendance_ID,btn_disciplinary_cases_ID;

    private String classStreamSelected = null;
    private StudentSpinner studentNameSelected = null;

    private Animation anim;



    private void bounce(View view){
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_text);
        view.startAnimation(anim);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.student_enquiry_layout);



        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sp_class_stream_ID = findViewById(R.id.sp_class_stream_ID);
        sp_student_name_ID = findViewById(R.id.sp_student_name_ID);

        btn_disciplinary_cases_ID = findViewById(R.id.btn_disciplinary_cases_ID);
        btn_attendance_ID = findViewById(R.id.btn_attendance_ID);
        btn_exam_history_ID = findViewById(R.id.btn_exam_history_ID);

        label_confirmation_message_ID = findViewById(R.id.label_confirmation_message_ID);

        layout_selected_studentName = findViewById(R.id.layout_selected_studentName);


        txt_selected_studentName = findViewById(R.id.txt_selected_studentName);


        btn_exam_history_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkClassStream(classStreamSelected) && checkStudentName(studentNameSelected)){

                }else{
                    bounce(label_confirmation_message_ID);
                }


                if (classStreamSelected != null && studentNameSelected != null) {


                    final ConnectivityManager cm = (ConnectivityManager) StudentEnquiry.this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

                        Staff staff = new StaffDao(StudentEnquiry.this).getUserThatSignedUp();

                        StudentRequest studentRequest = new StudentRequest(
                                String.valueOf(studentNameSelected.getStudentID()),
                                staff.getAppcode(),
                                staff.getOrganizationID()
                        );

                        Intent intent = new Intent(StudentEnquiry.this, StudentEnquiryFragmentsViewer.class);
                        intent.putExtra(DisplayContants.STUDENT_REQUEST, studentRequest);
                        intent.putExtra(DisplayContants.DISPLAY, DisplayContants.DISPLAY_EXAM_HISTORY);
                        startActivity(intent);




                    } else {
                      AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(StudentEnquiry.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                        accessDeniedAlert.setView(view);
                        accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                               btn_exam_history_ID.performClick();
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
        });

        btn_attendance_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkClassStream(classStreamSelected) && checkStudentName(studentNameSelected)){

                }else{
                    bounce(label_confirmation_message_ID);
                }

                if (classStreamSelected != null && studentNameSelected != null) {

                    final ConnectivityManager cm = (ConnectivityManager) StudentEnquiry.this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


                        Staff staff = new StaffDao(StudentEnquiry.this).getUserThatSignedUp();

                        StudentRequest studentRequest = new StudentRequest(
                                String.valueOf(studentNameSelected.getStudentID()),
                                staff.getAppcode(),
                                staff.getOrganizationID()
                        );

                        Intent intent = new Intent(StudentEnquiry.this, StudentEnquiryFragmentsViewer.class);
                        intent.putExtra(DisplayContants.STUDENT_REQUEST, studentRequest);
                        intent.putExtra(DisplayContants.DISPLAY, DisplayContants.DISPLAY_CLASS_ATTENDANCE);

                        startActivity(intent);




                    } else {
                        AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(StudentEnquiry.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                        accessDeniedAlert.setView(view);
                        accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                //sendRequest();
                                btn_attendance_ID.performClick();
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
        });

        btn_disciplinary_cases_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkClassStream(classStreamSelected) && checkStudentName(studentNameSelected)){

                }else{
                    bounce(label_confirmation_message_ID);
                }

                if (classStreamSelected != null && studentNameSelected != null) {
                    final ConnectivityManager cm = (ConnectivityManager) StudentEnquiry.this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

                        Staff staff = new StaffDao(StudentEnquiry.this).getUserThatSignedUp();

                        StudentRequest studentRequest = new StudentRequest(
                                String.valueOf(studentNameSelected.getStudentID()),
                                staff.getAppcode(),
                                staff.getOrganizationID()
                        );

                        Intent intent = new Intent(StudentEnquiry.this, StudentEnquiryFragmentsViewer.class);
                        intent.putExtra(DisplayContants.STUDENT_REQUEST, studentRequest);
                        intent.putExtra(DisplayContants.DISPLAY, DisplayContants.DISPLAY_DISCIPLINARY_CASES);

                        startActivity(intent);


                    } else {
                        AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(StudentEnquiry.this);
                        LayoutInflater inflater = getLayoutInflater();
                        View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                        accessDeniedAlert.setView(view);
                        accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                btn_disciplinary_cases_ID.performClick();
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
        });



        //CLASS STREAM is just to filter the number of students
        //ACTUAL DATA

        List<String> classStreamList = new StudentDAO(this).getClassStreamList();
        ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, classStreamList);
        sp_class_stream_ID.setAdapter(classesAdapter);
        sp_class_stream_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){

                    case 0:
                        classStreamSelected = null;
                        break;
                    default:

                        classStreamSelected = parent.getSelectedItem().toString();
                        if(classStreamSelected != null){
                            ArrayList<StudentSpinner> studentSpinnerArrayList = new StudentDAO(StudentEnquiry.this).getStudentSpinnerList(classStreamSelected);
                            ArrayAdapter<StudentSpinner> studentNamesAdapter = new ArrayAdapter<>(StudentEnquiry.this, android.R.layout.simple_spinner_dropdown_item, studentSpinnerArrayList);
                            sp_student_name_ID.setAdapter(studentNamesAdapter);
                        }
                        break;
                    // Toast.makeText(ManageDisciplineSreen.this, classNameValueSelected, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classStreamSelected = null;

            }
        });


        sp_student_name_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//
                switch(position){

                    case 0:
                        studentNameSelected = null;
                        break;
                    default:
                        //break;
                        studentNameSelected = (StudentSpinner) parent.getSelectedItem();

                        if(checkClassStream(classStreamSelected) && checkStudentName(studentNameSelected)){
                            label_confirmation_message_ID.setText("What would you like to enquire about");
                            label_confirmation_message_ID.setTextColor(StudentEnquiry.this.getResources().getColor(R.color.black));
                            txt_selected_studentName.setText(studentNameSelected.getStudentFullName());
                        }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                studentNameSelected = null;
                //label_confirmation_message_ID.setText("Please specify the Student Name");
                //showLabelConfirmationMessage(true);
            }
        });



    }






    private boolean checkClassStream(String classStreamSelected){
        if(classStreamSelected == null){
            label_confirmation_message_ID.setText("Please select Class Stream");
            label_confirmation_message_ID.setTextColor(StudentEnquiry.this.getResources().getColor(R.color.confirmation_failure));
            txt_selected_studentName.setText("");
            return false;
        }

        return true;
    }

    private boolean checkStudentName(StudentSpinner studentName){
        if(studentName ==  null){
            label_confirmation_message_ID.setText("Please select Student Name");
            label_confirmation_message_ID.setTextColor(StudentEnquiry.this.getResources().getColor(R.color.confirmation_failure));
            txt_selected_studentName.setText("");

            return false;
        }

        return true;
    }




//    private void setButtonsClickable(boolean show){
//        if(show){
//            showLabelConfirmationMessage(false);
//            btn_disciplinary_cases_ID.setClickable(true);
//            btn_exam_history_ID.setClickable(true);
//            btn_attendance_ID.setClickable(true);
//        }else{
//            btn_disciplinary_cases_ID.setClickable(false);
//            btn_exam_history_ID.setClickable(false);
//            btn_attendance_ID.setClickable(false);
//        }
//    }





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



}
