package ultratude.com.staff.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.R;

import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.adapters.NewHomeScreenTopItemsAdapter;
import ultratude.com.staff.model.HomeItem;
import ultratude.com.staff.spinnermodel.StudentSpinner;
import ultratude.com.staff.utils.UtilityFunctions;
import ultratude.com.staff.webservice.DataAccessObjects.DisciplineCaseDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.ResponseModels.DisciplineCase;
import ultratude.com.staff.webservice.ResponseModels.Staff;

/**
 * Created by James on 03/01/2019.
 */

public class ManageDisciplineSreen extends AppCompatActivity implements View.OnClickListener {


    private CardView btn_save;
    private EditText txt_offence, txt_penalty;
    private Spinner sp_class, sp_StudentName;
    private TextView label_confrimation_message;

    private ProgressBar pb_manageDiscipline;
    private String classNameValueSelected = null;
    private StudentSpinner studentSelected = null;


    private void resetFields(){

        //ACTUAL DATA
        List<String> classStreamList = new StudentDAO(this).getClassStreamList();
        ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, classStreamList);
        sp_class.setAdapter(classesAdapter);

        //ACTUAL DATA
        ArrayList<StudentSpinner> studentSpinnerArrayList = new StudentDAO(ManageDisciplineSreen.this).getStudentSpinnerList(classNameValueSelected);
        ArrayAdapter<StudentSpinner> studentNamesAdapter = new ArrayAdapter<>(ManageDisciplineSreen.this, android.R.layout.simple_spinner_dropdown_item, studentSpinnerArrayList);
        sp_StudentName.setAdapter(studentNamesAdapter);

        txt_offence.setText("");
        txt_penalty.setText("");


    }


    private ImageView image_studentname_sp_ID,image_class_sp_ID;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.discipline_layout);

        UtilityFunctions.activateQuickActions(this,  0, HomeScreen.CurrentScreenKey);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        image_class_sp_ID = findViewById(R.id.image_class_sp_ID);
        image_studentname_sp_ID = findViewById(R.id.image_studentname_sp_ID);


        btn_save = findViewById(R.id.btn_save_ID);
        txt_offence = findViewById(R.id.multitxt_offence_ID);
        txt_penalty = findViewById(R.id.multitext_penalty_ID);
        sp_class = findViewById(R.id.sp_class_stream_ID);
        sp_StudentName = findViewById(R.id.sp_student_name_ID);
        label_confrimation_message =  findViewById(R.id.label_confirmation_message);
        pb_manageDiscipline = findViewById(R.id.pb_manageDiscipline_progress);

        btn_save.setOnClickListener(this);


        //DUMMY DATA
        // final String[] classStreamList = {"Select a Class", "Pre-Unit BLUE", "Pre-Unit", "Class 1 RED", "Class 1 RED"};

        //ACTUAL DATA
        List<String> classStreamList = new StudentDAO(this).getClassStreamList();
        ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, classStreamList);
        sp_class.setAdapter(classesAdapter);
        image_class_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_class.performClick();
            }
        });
        sp_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if(classes[0].equals(parent.getSelectedItem().toString())){
//                    classNameValueSelected = "";
//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();
//
//                }
//                else{
//                    classNameValueSelected = parent.getSelectedItem().toString();
//                    Toast.makeText(ManageDisciplineSreen.this, classNameValueSelected, Toast.LENGTH_SHORT).show();
//                }
                switch(position){

                    case 0:
                        //break;
                        classNameValueSelected = null;
                        break;
                    default:
                        //break;
                        classNameValueSelected = parent.getSelectedItem().toString();
                        if(classNameValueSelected != null){
                            //DUMMY DATA
                            //final String[] studentSpinnerArrayList =  {"Select a StudentName", "DIXON  DAVENPORT","REBECCA  DURHAM","HANEY  OSBORN","RIVERA  ODONNELL","BALLARD  WILKERSON", "CLARISSA  ROGERS","ERICA  HULL","RENE  FRANCO"};
                            //ACTUAL DATA
                            ArrayList<StudentSpinner> studentSpinnerArrayList = new StudentDAO(ManageDisciplineSreen.this).getStudentSpinnerList(classNameValueSelected);
                            ArrayAdapter<StudentSpinner> studentNamesAdapter = new ArrayAdapter<>(ManageDisciplineSreen.this, android.R.layout.simple_spinner_dropdown_item, studentSpinnerArrayList);
                            sp_StudentName.setAdapter(studentNamesAdapter);
                        }
                        break;
                       // Toast.makeText(ManageDisciplineSreen.this, classNameValueSelected, Toast.LENGTH_SHORT).show();

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classNameValueSelected = null;
            }
        });


        image_studentname_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_StudentName.performClick();
            }
        });
        sp_StudentName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if(studentsNames[0].equals(parent.getSelectedItem().toString())){
//                    studentSelected = "";
//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();
//
//                }else{
//                    studentSelected = parent.getSelectedItem().toString();
//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();
//                }
                switch(position){

                    case 0:
                        //break;
                        studentSelected = null;
//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //break;
                        studentSelected = (StudentSpinner) parent.getSelectedItem();
//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                studentSelected = null;
            }
        });






    }


    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        saveDetails();
    }




    private void saveDetails(){

        String offence = txt_offence.getText().toString();
        String penalty = txt_penalty.getText().toString();


        //remove keyboard from display



        if(checkClassName(classNameValueSelected) && checkStudentName(studentSelected)&& checkOffence(offence) && checkPenalty(penalty)){

            Staff staff = new StaffDao(ManageDisciplineSreen.this).getUserThatSignedUp();
            String staffID = staff.getStaff_ID();
            DisciplineCase disciplineCase = new DisciplineCase(
                String.valueOf(studentSelected.getStudentID()),
                    offence,
                    penalty,
                    staff.getStaff_ID(),
                    staff.getAppcode()


            );

           // Toast.makeText(this, disciplineCase.toString(), Toast.LENGTH_SHORT).show();


           new SaveDetailsToDB(ManageDisciplineSreen.this).execute(disciplineCase);
        }

    }


    private void showProgress(boolean show){
        if (show) {
            pb_manageDiscipline.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.GONE);
            label_confrimation_message.setVisibility(View.INVISIBLE);
        }else{
            pb_manageDiscipline.setVisibility(View.GONE);
            btn_save.setVisibility(View.VISIBLE);
            label_confrimation_message.setVisibility(View.VISIBLE);
        }

    }


    private boolean checkClassName(String className ){

        if(className == null){
            showProgress(false);
            label_confrimation_message.setText("Please select a Class Stream");
            return false;
        }

        return true;
    }


    private boolean checkStudentName(StudentSpinner student){
        if(student == null){
            showProgress(false);
            label_confrimation_message.setText("Please select the Student Name");
            return false;
        }

        return true;
    }


    private boolean checkOffence(String offence)
    {
        label_confrimation_message.setText("");


        if(TextUtils.isEmpty(offence)){
            showProgress(false);
            txt_offence.setError("Please specify the Offence");
            return false;
        }


        if(TextUtils.isDigitsOnly(offence)){
            showProgress(false);
            txt_offence.setError("Please specify a valid input for offence");
            return false;
        }

        return true;
    }

    private boolean checkPenalty(String penalty){
        label_confrimation_message.setText("");


        if(TextUtils.isEmpty(penalty)){
            showProgress(false);
            txt_penalty.setError("Please specify Penalty");
            return false;
        }

        if(TextUtils.isDigitsOnly(penalty)){
            showProgress(false);
            txt_penalty.setError("Please specify valid input for offence");
            return false;
        }

        return true;
    }



    private class SaveDetailsToDB extends AsyncTask<DisciplineCase, Void, Long> {

        private Context mContext;

        public SaveDetailsToDB(Context context){
            this.mContext = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress(true);
        }

        @Override
        protected void onPostExecute(Long s) {
            super.onPostExecute(s);


            //THIS SHOULD BE A ALERT DIALOG
            showProgress(false);

            if(s > 0){

//                label_confrimation_message.setTextColor(getColor(R.color.confirmation_success));
//                label_confrimation_message.setText("Disciplinary case saved successfully");

                FancyToast.makeText(ManageDisciplineSreen.this, "Disciplinary case saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();


                resetFields();
            }else{
                FancyToast.makeText(ManageDisciplineSreen.this, "Failed to save. Please repeate the process.", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
               // label_confrimation_message.setText("Failed to save." + s);

            }


        }

        @Override
        protected Long doInBackground(DisciplineCase... params) {

           long id = 0l;

            try{
                Thread.sleep(2000);

                id = new DisciplineCaseDAO(mContext).saveDisciplineCase(params[0]);

            }catch (Exception e){
                e.printStackTrace();
            }

            return id;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               // NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
