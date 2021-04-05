package ultratude.com.staff.activities;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ultratude.com.staff.chips.MarkAttendanceChip;
import ultratude.com.staff.R;
import ultratude.com.staff.spinnermodel.AttendanceRollCallSessionSpinner;
import ultratude.com.staff.spinnermodel.ClassStreamSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.ClassStreamDAO;
import ultratude.com.staff.webservice.DataAccessObjects.MarkRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.RollCallSessonsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;

/**
 * Created by James on 06/01/2019.
 */

public class ManageAttendanceScreen extends AppCompatActivity implements  View.OnClickListener , View.OnLongClickListener {

    //Date, you can select date or if attendace recording for the day is not finished the previous records date and data are displayed

    //CHIP SET
    private  Spinner sp_classStream_ID;
    private Spinner sp_session;

    private TextView label_confirmation_message;

    private RecyclerView recyclerView;
    private TextView label_filterdata_if_select_classAndSession_message;

    private TextView txt_mark_attendance_date_ID;

    private TextView txt_count_number_of_students;

    private ClassStreamSpinner classStreamSelected = null;
    private AttendanceRollCallSessionSpinner sessionSelected = null;

    private ProgressBar pb_attendance_progress;
    private Button btn_save;

    private Calendar currentDate = null;

    private TextView txt_register_status;


    private AttendanceRecyclerAdapter adapter;


    private ArrayList<PortalMarkRegisterRequest> studentPortalMarkRegisterRequestList = new ArrayList<>();
    private ArrayList<String> studentRegistrationNumberList = new ArrayList<>();


    private RecyclerView rl_marked_attendance_ID;

    private LinearLayout filter_input_layout;

    //private final String[] rollCallSessions = {"Morning Session", "Mid Morning Sessions", "After Noon Sessions", "Evening Session", "Night Session", "General Session"};

    //private final String[] status = {"Present", "Absent"};

    private static boolean updating = false;

    private void resetFields(){

        //Dummy  DATA///you should actual data
        ArrayList<AttendanceRollCallSessionSpinner> sessionRB = new RollCallSessonsDAO(ManageAttendanceScreen.this).getAttendanceRollCallSessionSpinnerList();
        ArrayAdapter<AttendanceRollCallSessionSpinner> sessionValuesAdapter = new ArrayAdapter<AttendanceRollCallSessionSpinner>(ManageAttendanceScreen.this, android.R.layout.simple_spinner_dropdown_item, sessionRB);
        sp_session.setAdapter(sessionValuesAdapter);

        txt_count_number_of_students.setText("No. of Students");

        ArrayList<ClassStreamSpinner> classStreamValues = new ClassStreamDAO(ManageAttendanceScreen.this).getClassStreamSpinner();

        final ArrayAdapter<ClassStreamSpinner> classStreamAdapter = new ArrayAdapter<ClassStreamSpinner>(ManageAttendanceScreen.this, android.R.layout.simple_spinner_dropdown_item, classStreamValues);
       sp_classStream_ID.setAdapter(classStreamAdapter);
        label_confirmation_message.setText("");

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getMenuInflater().inflate(R.menu.maximize_minimize, menu);
        return true;
    }


    private static boolean filter_layout_isGone = false;


    public boolean onOptionsItemSelected(MenuItem item) {
      // if (item != null && item.getTitle() != null){
        if(item.getItemId() == R.id.maximize) {


            if (item.getTitle().equals("maximize")) {
                // item.setTooltipText("Minimize");

                if (recyclerView.getVisibility() == View.VISIBLE) {
                    //gone
                    if (filter_input_layout.getVisibility() == View.VISIBLE) {
                        item.setIcon(getResources().getDrawable(R.drawable.minimize_icon));
                        item.setTitle("minimize");

                        filter_input_layout.setVisibility(View.GONE);
                        filter_layout_isGone = true;

                        return true;

//                    StudentEnquiry.filter_layout_isGone = true;
//                    fragment_transaction.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//                    fragment_transaction.requestLayout();
                    }
                }

            } else if (item.getTitle().equals("minimize")) {

                if (recyclerView.getVisibility() == View.VISIBLE) {

                    if (ManageAttendanceScreen.filter_layout_isGone) {
                        item.setIcon(getResources().getDrawable(R.drawable.maximize_icon));
                        item.setTitle("maximize");

                        filter_input_layout.setVisibility(View.VISIBLE);
                        return true;

                    }

                    // if(filter_input_layout.getVisibility() == View.VISIBLE){

//                    ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) fragment_transaction.getLayoutParams();
//
//
//                    //layoutParams = myView.getLayoutParams()
//                    //p.setMargins(left, top, right, bottom);
//                    p.setMargins(((int) TypedValue.applyDimension(
//                            TypedValue.COMPLEX_UNIT_DIP, 0, getResources()
//                                    .getDisplayMetrics())),
//                            ((int) TypedValue.applyDimension(
//                                    TypedValue.COMPLEX_UNIT_DIP, 210, getResources()
//                                            .getDisplayMetrics())),
//                            ((int) TypedValue.applyDimension(
//                                    TypedValue.COMPLEX_UNIT_DIP, 0, getResources()
//                                            .getDisplayMetrics())),
//                            ((int) TypedValue.applyDimension(
//                                    TypedValue.COMPLEX_UNIT_DIP, 0, getResources()
//                                            .getDisplayMetrics())));
//
//                    fragment_transaction.requestLayout();


                    // }
                }

            }
        }// Respond to the action bar's Up/Home button
        else if(item.getItemId() == android.R.id.home){
               onBackPressed();
               return true;

           }
      // }

        return super.onOptionsItemSelected(item);
    }

   // private ChipsInput mChipsInput;

    private List<MarkAttendanceChip> markAttendanceChipList;





    private ImageView image_session_sp_ID, image_classStream_sp_ID;

    private TextView txt_replace_sp_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.manage_attendance_layout);


        txt_replace_sp_ID  = findViewById(R.id.txt_replace_sp_ID);
        image_session_sp_ID = findViewById(R.id.image_session_sp_ID);
        image_classStream_sp_ID = findViewById(R.id.image_classStream_sp_ID);


       // mChipsInput  = findViewById(R.id.chips_input);

        rl_marked_attendance_ID = findViewById(R.id.rl_marked_attendance_ID);
        rl_marked_attendance_ID.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ManageAttendanceScreen.this, LinearLayoutManager.HORIZONTAL, false);
        rl_marked_attendance_ID.setLayoutManager(layoutManager);

        //PREVIOUS STORED MARKED REGISTERS
         markAttendanceChipList = new MarkRegisterDAO(this).getStudentMarkRegisterForCurrentDateForChips();

      //  Toast.makeText(this, "Mark " + String.valueOf(markAttendanceChipList.get(0).getStudentPortalMarkRegisterRequestList()), Toast.LENGTH_SHORT).show();

        for(MarkAttendanceChip chip : markAttendanceChipList){
            chip.setSaved(true);
            chip.setDisplay(true);
        }
        ChipAdaptor adapter1 = new ChipAdaptor(ManageAttendanceScreen.this, markAttendanceChipList);
        rl_marked_attendance_ID.setAdapter(adapter1);
       // rl_marked_attendance_ID.getAdapter().notifyItemInserted(markAttendanceChipList.size() - 1);




        filter_input_layout = findViewById(R.id.filter_input_layout);

        txt_register_status = findViewById(R.id.txt_register_status);

        label_confirmation_message = findViewById(R.id.label_confirmation_message);
        label_confirmation_message.setVisibility(View.VISIBLE);
        label_confirmation_message.setText("Select a Session and a Class Stream");

        label_filterdata_if_select_classAndSession_message = findViewById(R.id.label_filterdata_if_select_classAndSession_message);


        txt_count_number_of_students = findViewById(R.id.txt_count_number_of_Students);


        txt_mark_attendance_date_ID = findViewById(R.id.txt_mark_attendance_date_ID);
        final Calendar cal = Calendar.getInstance();

        //if statement to check if there are any previously unfinished marking registers, set the date for that day
        //and display data

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, dd MMM yyyy");
        cal.set(year, month, day);

        if (currentDate == null) {
            currentDate = cal;
        }
        txt_mark_attendance_date_ID.setText(sdf.format(cal.getTime()));


        //you cannot select date
//        txt_mark_attendance_date_ID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = new DatePickerFragment_AttendanceDate();
//                newFragment.show(getSupportFragmentManager(), "date picker");
//            }
//        });


        recyclerView = findViewById(R.id.attendance_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager1);


        sp_classStream_ID = findViewById(R.id.sp_classStream_ID);
        sp_session = findViewById(R.id.sp_session_ID);




        image_classStream_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_classStream_ID.performClick();
            }
        });
        sp_classStream_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:

                        classStreamSelected = null;
                        break;
                    default:
                        classStreamSelected = (ClassStreamSpinner) parent.getSelectedItem();

                        //set the other spinner


                        //BEFORE YOU SHOULD CHECK THE DATABASE MARKREGISTER TABLE FOR DATA, FOR A SPEICIFIC DATE AND CLASSSTREAM, FOR THE PRESENT DAY,
                        ///IF THERE IS NO ROCORDS FOR MARK REGISTER DATA FOR THE PRESENT DAY, SHOW NEW STUDENTS FOR THIS, HAVING ALL THEIR, RECORDS MARKED PRSENT BUT NOT SAVED,
                        //WILL BE SAVED LATER.

                        //DUMMY DATA
                        //studentPortalMarkRegisterRequestList = new StudentDAO(ManageAttendanceScreen.this).getDummyStudentMarkRegisterList(classStreamSelected);
                        //ACTUAL DATA

                        // Toast.makeText(ManageAttendanceScreen.this,String.valueOf(sessionSelected.getSessionName()), Toast.LENGTH_LONG).show();

                        if (checkSession(sessionSelected)) {

                            //so deletes every things before displaying again
                            studentPortalMarkRegisterRequestList.clear();
                            studentRegistrationNumberList.clear();


                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

                            //TEST
                            // ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestArrayList =  new MarkRegisterDAO(ManageAttendanceScreen.this).getStudentMarkRegisterForCurrentDateTest(classStreamSelected.toString(), sessionSelected, sdf.format(currentDate.getTime()));

                            //you have to send the currentdate, classstream, session
                            List<Object> markResult = new MarkRegisterDAO(ManageAttendanceScreen.this).getStudentMarkRegisterForCurrentDate(classStreamSelected.toString(), sessionSelected, sdf.format(currentDate.getTime()));
                            ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestArrayList = (ArrayList<PortalMarkRegisterRequest>) markResult.get(0);
                            ArrayList<String> studentRegistrationNumbers = (ArrayList<String>) markResult.get(1);
                            if (portalMarkRegisterRequestArrayList.size() > 0) {


                                studentPortalMarkRegisterRequestList = portalMarkRegisterRequestArrayList;
                                studentRegistrationNumberList = studentRegistrationNumbers;
                                txt_register_status.setVisibility(View.VISIBLE);

                                txt_register_status.setTextColor(getResources().getColor(R.color.update_attendance_register));
                                txt_register_status.setText("Updating attendance register");


                                //will just show which part it is on the horizontal list





                            } else {



                                //populateMarkedAttendance();

//                                for(MarkAttendanceChip chip : markAttendanceChipList){
//
//                                    if(chip.getCourseLevelName().equalsIgnoreCase(classStreamSelected.getClassStreamName())){
//                                        Log.d(getPackageName().toUpperCase(), markAttendanceChipList.toString());
//                                        mChipsInput.addChip(chip);
//                                        break;
//                                    }
//                                }


                                List<Object> markResult2 = new StudentDAO(ManageAttendanceScreen.this).getStudentMarkRegister(classStreamSelected, sessionSelected, sdf.format(currentDate.getTime()));
                                ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestArrayList2 = (ArrayList<PortalMarkRegisterRequest>) markResult2.get(0);

                                ArrayList<String> studentRegistrationNumbers2 = (ArrayList<String>) markResult2.get(1);


                                studentPortalMarkRegisterRequestList = portalMarkRegisterRequestArrayList2;
                                studentRegistrationNumberList = studentRegistrationNumbers2;

                                txt_register_status.setVisibility(View.VISIBLE);
                                txt_register_status.setTextColor(getResources().getColor(R.color.new_attendance_register));

                                txt_register_status.setText("New attendance register");


                                markAttendanceChipList = new ArrayList<MarkAttendanceChip>();
                                MarkAttendanceChip markchip = new MarkAttendanceChip();
                                markchip.setCourseLevelName(classStreamSelected.getClassStreamName());
                                markchip.setRollCallSession(sessionSelected.getSessionName());
                                markchip.setSaved(false);
                                markchip.setDisplay(true);
                                markchip.setStudentPortalMarkRegisterRequestList(studentPortalMarkRegisterRequestList);
                                markchip.setStudentRegistrationNumberList(studentRegistrationNumberList);
                                markAttendanceChipList.add(markchip);

                                for(MarkAttendanceChip chip : markAttendanceChipList){

                                    if(chip.getCourseLevelName().equalsIgnoreCase(classStreamSelected.getClassStreamName())){

                                        //update
                                        int itemCount =    rl_marked_attendance_ID.getAdapter().getItemCount();


                                        if(itemCount == 0){

                                            chip.setSaved(false);
                                            chip.setDisplay(true);

                                            ChipAdaptor adapter = new ChipAdaptor(ManageAttendanceScreen.this, markAttendanceChipList);
                                            rl_marked_attendance_ID.setAdapter(adapter);

                                        }else{

                                            chip.setSaved(false);
                                            chip.setDisplay(true);

                                            ChipAdaptor adapter = new ChipAdaptor(ManageAttendanceScreen.this, markAttendanceChipList);
                                            rl_marked_attendance_ID.setAdapter(adapter);
                                            rl_marked_attendance_ID.getAdapter().notifyItemInserted(markAttendanceChipList.size() - 1);
                                            rl_marked_attendance_ID.getAdapter().notifyDataSetChanged();


                                        }


                                        break;
                                    }
                                }

                            }

                            StringBuilder s = new StringBuilder();
                            s.append(classStreamSelected.toString() + " has ");
                            s.append(String.valueOf(studentPortalMarkRegisterRequestList.size()) + " Students");


                            txt_count_number_of_students.setText(s.toString());
                            adapter = new AttendanceRecyclerAdapter(ManageAttendanceScreen.this, recyclerView);
                            recyclerView.setAdapter(adapter);
                            label_filterdata_if_select_classAndSession_message.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                classStreamSelected = null;
            }
        });



        //Dummy  DATA///you should actual data
        ArrayList<AttendanceRollCallSessionSpinner> sessionRB = new RollCallSessonsDAO(ManageAttendanceScreen.this).getAttendanceRollCallSessionSpinnerList();
        ArrayAdapter<AttendanceRollCallSessionSpinner> sessionValuesAdapter = new ArrayAdapter<AttendanceRollCallSessionSpinner>(ManageAttendanceScreen.this, android.R.layout.simple_spinner_dropdown_item, sessionRB);
        sp_session.setAdapter(sessionValuesAdapter);
        image_session_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_session.performClick();
            }
        });
        sp_session.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:
                        sessionSelected = null;
                        break;

                    default:
                        sessionSelected = (AttendanceRollCallSessionSpinner) parent.getSelectedItem();
                        //DUMMY DATA
                        //String[] classStreamValues = {"Select a Class Stream", "Pre-Unit BLUE", "Pre-Unit", "Class 1 RED", "Class 1 RED"};

                        //ACTUAL DATA
                        //List<String> classStreamValues = new StudentDAO(ManageAttendanceScreen.this).getClassStreamList();
                        ArrayList<ClassStreamSpinner> classStreamValues = new ClassStreamDAO(ManageAttendanceScreen.this).getClassStreamSpinner();
                        final ArrayAdapter<ClassStreamSpinner> classStreamAdapter = new ArrayAdapter<ClassStreamSpinner>(ManageAttendanceScreen.this, android.R.layout.simple_spinner_dropdown_item, classStreamValues);
                        sp_classStream_ID.setAdapter(classStreamAdapter);
                        label_confirmation_message.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sessionSelected = null;
            }
        });



        btn_save = findViewById(R.id.btn_save_ID);
        btn_save.setOnClickListener(this);
        btn_save.setOnLongClickListener(this);
        pb_attendance_progress = findViewById(R.id.pb_attendance_progress);


    }


    private boolean checkSession(AttendanceRollCallSessionSpinner session) {
        label_confirmation_message.setText("");
        if (session == null) {
            label_confirmation_message.setVisibility(View.VISIBLE);
            label_confirmation_message.setText("Please select a session");
            return false;
        }

        return true;
    }


    private void showProgress(boolean show) {
        if (show) {
            pb_attendance_progress.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.GONE);
            label_confirmation_message.setVisibility(View.INVISIBLE);
        } else {
            pb_attendance_progress.setVisibility(View.GONE);
            btn_save.setVisibility(View.VISIBLE);
            label_confirmation_message.setVisibility(View.VISIBLE);
        }

    }


    //ATTENDANCERECYCLERADAPTER
    public class AttendanceRecyclerAdapter extends RecyclerView.Adapter<AttendanceRecyclerAdapter.ViewHolder> {

        // private  ArrayList<PortalMarkRegisterRequest> studentPortalMarkRegisterRequestList;
        private Context mContext;

        private RecyclerView recyclerView;

//       private String dateRecorded = null;

        public AttendanceRecyclerAdapter(Context mContext, /*Calendar cal,*/ RecyclerView recyclerView) {
            this.mContext = mContext;

            this.recyclerView = recyclerView;

//            if (dateRecorded == null) {
//                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
//                dateRecorded = sdf.format(cal.getTime());
//            }


        }


        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView label_confirmation_message_viewHolder;
            private TextInputEditText txt_studentName_ID, txt_reason_ID;
            private RadioButton rb_present_ID, rb_absent_ID;
            private RadioGroup radioGroup;


            private LinearLayout reason_layout_ID;


            ViewHolder(View itemView) {
                super(itemView);

                reason_layout_ID = itemView.findViewById(R.id.reason_layout_ID);

                txt_reason_ID = itemView.findViewById(R.id.txt_reason_ID);


                label_confirmation_message_viewHolder = itemView.findViewById(R.id.label_confirmation_message);
                txt_studentName_ID = itemView.findViewById(R.id.txt_studentName_ID);


                rb_absent_ID = itemView.findViewById(R.id.rb_absent_ID);

                rb_present_ID = itemView.findViewById(R.id.rb_present_ID);

                txt_reason_ID.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        final PortalMarkRegisterRequest student = studentPortalMarkRegisterRequestList.get(getAdapterPosition());
                        student.setRemarks(s.toString());
                    }
                });


                radioGroup = itemView.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        if (checkedId == R.id.rb_absent_ID) {


                            //do something.
                            reason_layout_ID.setVisibility(View.VISIBLE);

                            // txt_reason_ID.setError("Please enter Reason");

                            //i have to set, status, dateRecorded, rollCallSession, remarks
                            final PortalMarkRegisterRequest student = studentPortalMarkRegisterRequestList.get(getAdapterPosition());

                            if (student.getRemarks().equalsIgnoreCase("")) {//old register will never have empty string remarks, but a new register,
                                //when absent is selected will have an empty string.
                                txt_reason_ID.setError("Please enter Reason");
                            }

                            // Toast.makeText(ManageAttendanceScreen.this, "Name: "+student.getStudentFullName()+" Student status: " + String.valueOf(student.getStatus().trim()), Toast.LENGTH_LONG).show();


                            if (rb_absent_ID.isChecked()) {


                                //we can check if the previous students who are marked absent, if their, remarks have being added

                                //status
                                student.setStatus(String.valueOf(new MarkRegisterDAO(mContext).getAttendanceStatusIDRadioButtonList(rb_absent_ID.getText().toString().trim())));//for present
                                //remarks
                                student.setRemarks(txt_reason_ID.getText().toString().trim());

                                // Toast.makeText(ManageAttendanceScreen.this, "Name: "+student.getStudentFullName()+" Student status: " + String.valueOf(student.getStatus().trim()) + " Session: " + student.getRollCallSession(), Toast.LENGTH_LONG).show();

                            }


                        } else if (checkedId == R.id.rb_present_ID) {

                            reason_layout_ID.setVisibility(View.GONE);

                            //do something
                            PortalMarkRegisterRequest student = studentPortalMarkRegisterRequestList.get(getAdapterPosition());


                            if (rb_present_ID.isChecked()) {

                                // Toast.makeText(ManageAttendanceScreen.this, "Name: "+student.getStudentFullName()+" Student status: " + String.valueOf(student.getStatus().trim()), Toast.LENGTH_LONG).show();

                                //status
                                student.setStatus(String.valueOf(new MarkRegisterDAO(mContext).getAttendanceStatusIDRadioButtonList(rb_present_ID.getText().toString().trim())));//for present
                                //remarks
                                student.setRemarks("");
                                //deteRecorded


                                //  Toast.makeText(ManageAttendanceScreen.this, "Name: "+student.getStudentFullName()+" Student status: " + String.valueOf(student.getStatus().trim()) + " Session: " + student.getRollCallSession(), Toast.LENGTH_LONG).show();

                            }


                        }
                    }
                });

            }


        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_attendance_item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // holder.txt_studentName_ID.setText(studentNamesList.get(position).toString());
            PortalMarkRegisterRequest student = studentPortalMarkRegisterRequestList.get(position);

            //TESTING
//            StringBuilder s = new StringBuilder();
//            s.append("status:"+student.getStatus() + " = ");
//            s.append("date:"+student.getDateRecorded() + " = ");
//            s.append("remark:"+student.getRemarks() + " = ");
//            s.append("session:"+student.getRollCallSession() + "");
//            holder.label_confirmation_message_viewHolder.setText(s.toString());

            holder.txt_reason_ID.setText(student.getRemarks());

            String registrationNumber = studentRegistrationNumberList.get(position);
            holder.txt_studentName_ID.setText(student.getStudentFullName() + "  (" + registrationNumber + ")");
            //if the data is gotten from the database
            if (student.getStatus().equals("1")) {
                holder.rb_present_ID.setChecked(true);
            } else if (student.getStatus().equals("2")) {
                holder.rb_absent_ID.setChecked(true);
            }


        }


        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }


        @Override
        public int getItemCount() {
            return studentPortalMarkRegisterRequestList.size();
        }
    }


    private boolean checkReason(String reason, int absenteeStudentWithNoRemarkCount, String studentName) {


        if (reason.equals("")) {
            recyclerView.setVisibility(View.VISIBLE);
            label_filterdata_if_select_classAndSession_message.setVisibility(View.INVISIBLE);
            StringBuilder s = new StringBuilder();
            s.append(studentName);
            s.append("\n");
            s.append("Please specify reason for absence...or");
            s.append("\n");
            s.append("You can, hold down the save button, to save.");
            label_confirmation_message.setVisibility(View.VISIBLE);
            label_confirmation_message.setText(s.toString());
            recyclerView.scrollToPosition(absenteeStudentWithNoRemarkCount);

            return false;
        }

        return true;
    }




    @Override
    public void onClick(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        boolean hasAllStudentsPresent = false;

        int absenteeStudentWithRemarkCount = 0;
        int presentStudentCount = 0;


        Log.d(this.getPackageName().toUpperCase(), "New Register: " + String.valueOf(!updating &&  txt_register_status.getText().toString().equalsIgnoreCase("New attendance register")) + " Individual: " + updating + " " + txt_register_status.getText().toString().equalsIgnoreCase("New attendance register"));
        Log.d(this.getPackageName().toUpperCase(),  "Update Register: " + String.valueOf(updating == false && txt_register_status.getText().toString().equalsIgnoreCase("Updating attendance register")) + " Individual: " + updating + " " + txt_register_status.getText().toString().equalsIgnoreCase("Updating attendance register"));

        if(!updating &&  txt_register_status.getText().toString().contains("New attendance register")) {
            if ((label_filterdata_if_select_classAndSession_message.getVisibility() != View.VISIBLE) && classStreamSelected != null && studentPortalMarkRegisterRequestList.size() > 0) {

                if (checkSession(sessionSelected)) {
                    for (int i = 0; i < this.studentPortalMarkRegisterRequestList.size(); i++) {
                        PortalMarkRegisterRequest markedStudent = this.studentPortalMarkRegisterRequestList.get(i);
                        if (markedStudent.getStatus().equals("2")) {//absent student
                            //for each absent student with no remark, break out of the loop and scroll to that student
                            if (checkReason(markedStudent.getRemarks(), i, markedStudent.getStudentFullName())) {
                                absenteeStudentWithRemarkCount++;
                            }

                        } else {

                            presentStudentCount++;

                            if (this.studentPortalMarkRegisterRequestList.size() == presentStudentCount) {
                                hasAllStudentsPresent = true;

                            }

                        }
                    }

                    //There are two scenerios, when the all students are present AND when some students are present and others are absent

                    //if present is 24 and studentPortalMarkRegisterRequestList is 24 and absenteeStudentWithRemarkCount is 0
                    //all students cannot be absent, that is when presentStudentCount is greater than 0 and when absenteeStudentWithRemarkCount is 24
                    //should handle some present and others absent
                    if (absenteeStudentWithRemarkCount > 0 && presentStudentCount > 0) {
                        if ((absenteeStudentWithRemarkCount + presentStudentCount) == (this.studentPortalMarkRegisterRequestList.size())) {
                            //  Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                            new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, classStreamSelected.toString());
                           // updating = true;
                        }
                    }

                    //should handle all students present
                    if (hasAllStudentsPresent) {
                        //  Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                        new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, classStreamSelected.toString());
                        //updating = true;
                    }
                }


            } else if (label_filterdata_if_select_classAndSession_message.getVisibility() == View.VISIBLE) {
                //showing congratulation
            } else {
                label_confirmation_message.setVisibility(View.VISIBLE);
                label_confirmation_message.setText("Select a Class Stream and a Session");

            }

        }else if(updating && txt_register_status.getText().toString().contains("Updating attendance register")){

            if ((label_filterdata_if_select_classAndSession_message.getVisibility() != View.VISIBLE) /*&& classStreamSelected != null*/ && studentPortalMarkRegisterRequestList.size() > 0) {

                if (checkSession(sessionSelected)) {
                    for (int i = 0; i < this.studentPortalMarkRegisterRequestList.size(); i++) {
                        PortalMarkRegisterRequest markedStudent = this.studentPortalMarkRegisterRequestList.get(i);
                        if (markedStudent.getStatus().equals("2")) {//absent student
                            //for each absent student with no remark, break out of the loop and scroll to that student
                            if (checkReason(markedStudent.getRemarks(), i, markedStudent.getStudentFullName())) {
                                absenteeStudentWithRemarkCount++;
                            }

                        } else {

                            presentStudentCount++;

                            if (this.studentPortalMarkRegisterRequestList.size() == presentStudentCount) {
                                hasAllStudentsPresent = true;

                            }

                        }
                    }

                    //There are two scenerios, when the all students are present AND when some students are present and others are absent

                    //if present is 24 and studentPortalMarkRegisterRequestList is 24 and absenteeStudentWithRemarkCount is 0
                    //all students cannot be absent, that is when presentStudentCount is greater than 0 and when absenteeStudentWithRemarkCount is 24
                    //should handle some present and others absent
                    if (absenteeStudentWithRemarkCount > 0 && presentStudentCount > 0) {
                        if ((absenteeStudentWithRemarkCount + presentStudentCount) == (this.studentPortalMarkRegisterRequestList.size())) {
                            //  Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                            new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, txt_replace_sp_ID.getText().toString());
                            updating = false;
                        }
                    }

                    //should handle all students present
                    if (hasAllStudentsPresent) {
                        //  Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                        new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, txt_replace_sp_ID.getText().toString());
                        updating = false;
                    }
                }


            } else if (label_filterdata_if_select_classAndSession_message.getVisibility() == View.VISIBLE) {
                //showing congratulation
            } else {
                label_confirmation_message.setVisibility(View.VISIBLE);
                label_confirmation_message.setText("Select a Class Stream and a Session");

            }

            txt_replace_sp_ID.setVisibility(View.INVISIBLE);
            sp_classStream_ID.setVisibility(View.VISIBLE);
            txt_replace_sp_ID.setEnabled(true);
            sp_session.setEnabled(true);
        }

//        String reason = txt_reason_ID.getText().toString();
//        String studentName = txt_studentName_ID.getText().toString();
//
//        if(checkReason(reason)){
//            new AttendanceRecyclerAdapter.ViewHolder.MarkStudentRegister(mContext).execute(studentName, reason, String.valueOf(getAdapterPosition()));
//        }
    }






    @Override
    public boolean onLongClick(View v) {
        boolean hasAllStudentsPresent = false;

        int absenteeStudentWithRemarkCount = 0;
        int presentStudentCount = 0;


        if(!updating){
            //saving after selecting the drop downs
            if ((label_filterdata_if_select_classAndSession_message.getVisibility() != View.VISIBLE) && classStreamSelected != null && studentPortalMarkRegisterRequestList.size() > 0 ) {

                if (checkSession(sessionSelected)) {
                    for (int i = 0; i < this.studentPortalMarkRegisterRequestList.size(); i++) {
                        PortalMarkRegisterRequest markedStudent = this.studentPortalMarkRegisterRequestList.get(i);
                        if (markedStudent.getStatus().equals("2")) {//absent student
                            //for each absent student with no remark, break out of the loop and scroll to that student
                            //but in this case we just save without the remark for long clicks
                            if (true) {
                                absenteeStudentWithRemarkCount++;
                            }

                        } else {

                            presentStudentCount++;

                            if (this.studentPortalMarkRegisterRequestList.size() == presentStudentCount) {
                                hasAllStudentsPresent = true;

                            }

                        }
                    }

                    //There are two scenerios, when the all students are present AND when some students are present and others are absent

                    //if present is 24 and studentPortalMarkRegisterRequestList is 24 and absenteeStudentWithRemarkCount is 0
                    //all students cannot be absent, that is when presentStudentCount is greater than 0 and when absenteeStudentWithRemarkCount is 24
                    //should handle some present and others absent
                    if (absenteeStudentWithRemarkCount > 0 && presentStudentCount > 0) {
                        if ((absenteeStudentWithRemarkCount + presentStudentCount) == (this.studentPortalMarkRegisterRequestList.size())) {
                            // Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                            new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, classStreamSelected.toString());
                           // updating = true;
                        }
                    }

                    //should handle all students present
                    if (hasAllStudentsPresent) {
                        // Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                        new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, classStreamSelected.toString());
                        //updating = true;
                    }
                }


            } else if (label_filterdata_if_select_classAndSession_message.getVisibility() == View.VISIBLE) {
                //showing congratulation
            } else {
                //there are no possible scenerials of this, but
                // new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, classStreamSelected.toString());
                //label_confirmation_message.setVisibility(View.VISIBLE);
                //label_confirmation_message.setText("Select a Class Stream and a Session");

            }
        }else if(updating && txt_register_status.getText().toString().equalsIgnoreCase("Updating attendance register")){

            //collect data from teh textview replace and spinner session
            if ((label_filterdata_if_select_classAndSession_message.getVisibility() != View.VISIBLE) /*&& classStreamSelected != null*/ && studentPortalMarkRegisterRequestList.size() > 0 ) {

                if (checkSession(sessionSelected)) {
                    for (int i = 0; i < this.studentPortalMarkRegisterRequestList.size(); i++) {
                        PortalMarkRegisterRequest markedStudent = this.studentPortalMarkRegisterRequestList.get(i);
                        if (markedStudent.getStatus().equals("2")) {//absent student
                            //for each absent student with no remark, break out of the loop and scroll to that student
                            //but in this case we just save without the remark for long clicks
                            if (true) {
                                absenteeStudentWithRemarkCount++;
                            }

                        } else {

                            presentStudentCount++;

                            if (this.studentPortalMarkRegisterRequestList.size() == presentStudentCount) {
                                hasAllStudentsPresent = true;

                            }

                        }
                    }

                    //There are two scenerios, when the all students are present AND when some students are present and others are absent

                    //if present is 24 and studentPortalMarkRegisterRequestList is 24 and absenteeStudentWithRemarkCount is 0
                    //all students cannot be absent, that is when presentStudentCount is greater than 0 and when absenteeStudentWithRemarkCount is 24
                    //should handle some present and others absent
                    if (absenteeStudentWithRemarkCount > 0 && presentStudentCount > 0) {
                        if ((absenteeStudentWithRemarkCount + presentStudentCount) == (this.studentPortalMarkRegisterRequestList.size())) {
                            // Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                            new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, txt_replace_sp_ID.getText().toString());
                            updating = false;
                        }
                    }

                    //should handle all students present
                    if (hasAllStudentsPresent) {
                        // Toast.makeText(this, "MarkRegisterCount: " + String.valueOf(this.studentPortalMarkRegisterRequestList.size()), Toast.LENGTH_SHORT).show();
                        new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, txt_replace_sp_ID.getText().toString());
                      updating = false;
                    }
                }


            } else if (label_filterdata_if_select_classAndSession_message.getVisibility() == View.VISIBLE) {
                //showing congratulation
            } else {
                //there are no possible scenerials of this, but
                // new MarkStudentRegister(this).execute(this.studentPortalMarkRegisterRequestList, classStreamSelected.toString());
                //label_confirmation_message.setVisibility(View.VISIBLE);
                //label_confirmation_message.setText("Select a Class Stream and a Session");

            }

            txt_replace_sp_ID.setVisibility(View.INVISIBLE);
            sp_classStream_ID.setVisibility(View.VISIBLE);
            txt_replace_sp_ID.setEnabled(true);
            sp_session.setEnabled(true);


        }


//-



//        String reason = txt_reason_ID.getText().toString();
//        String studentName = txt_studentName_ID.getText().toString();
//
//        if(checkReason(reason)){
//            new AttendanceRecyclerAdapter.ViewHolder.MarkStudentRegister(mContext).execute(studentName, reason, String.valueOf(getAdapterPosition()));
//        }

        return true;
    }


    //MARKSTUDENTREGISTER
    public class MarkStudentRegister extends AsyncTask<Object, Void, Long> {

        private Context mContext;


        public MarkStudentRegister(Context context){
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

            showProgress(false);
            sp_classStream_ID.setEnabled(true);
            image_classStream_sp_ID.setEnabled(true);
            sp_session.setEnabled(true);
            image_session_sp_ID.setEnabled(true);

            //remove this from recycler
            //removeAt(adapterPosition);

            if((studentPortalMarkRegisterRequestList.size() == s)){

                label_filterdata_if_select_classAndSession_message.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
                label_confirmation_message.setVisibility(View.INVISIBLE);
                    label_filterdata_if_select_classAndSession_message.setTextColor(getColor(R.color.confirmation_success));
                label_filterdata_if_select_classAndSession_message.setText("Congratulations, You've marked, "+sdf.format(currentDate.getTime())+"'s register");

                recyclerView.setVisibility(View.INVISIBLE);
                txt_register_status.setVisibility(View.INVISIBLE);

                markAttendanceChipList = new MarkRegisterDAO(ManageAttendanceScreen.this).getStudentMarkRegisterForCurrentDateForChips();

                //  Toast.makeText(this, "Mark " + String.valueOf(markAttendanceChipList.get(0).getStudentPortalMarkRegisterRequestList()), Toast.LENGTH_SHORT).show();

                for(MarkAttendanceChip chip : markAttendanceChipList){
                    chip.setSaved(true);
                    chip.setDisplay(true);
                }
                ChipAdaptor adapter1 = new ChipAdaptor(ManageAttendanceScreen.this, markAttendanceChipList);
                rl_marked_attendance_ID.setAdapter(adapter1);
               // rl_marked_attendance_ID.notify();

                resetFields();
            }else if(s == -2){

                label_filterdata_if_select_classAndSession_message.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
                label_confirmation_message.setVisibility(View.INVISIBLE);
                label_filterdata_if_select_classAndSession_message.setTextColor(getColor(R.color.confirmation_success));
                label_filterdata_if_select_classAndSession_message.setText("Today's, " + sdf.format(currentDate.getTime()) + " register is successfully updated");
                recyclerView.setVisibility(View.INVISIBLE);
                txt_register_status.setVisibility(View.INVISIBLE);
                resetFields();

            }else{
                //Toast.makeText(mContext, "Passed: " + studentPortalMarkRegisterRequestList.size() + "  Saved: " + s, Toast.LENGTH_SHORT).show();
                label_confirmation_message.setVisibility(View.VISIBLE);
                label_confirmation_message.setText("Failed to save register");
            }





        }

//        private void removeAt(int position){
//            //should have a way that you will return to where you were left, that dates
//
//            //will not be removing, students from list
////            studentNamesList.remove(position);
////            notifyItemRemoved(getAdapterPosition());
////            notifyItemRangeChanged(getAdapterPosition(),studentNamesList.size());
//        }

        @Override
        protected Long doInBackground(Object... params) {

            long id =0;

            try{
                Thread.sleep(2000);
                //then remove from recyclerview
               // Log.d(mContext.getPackageName().toUpperCase(), "BeforeSaveStudentMarkRegisterListCount: " + String.valueOf(((ArrayList<PortalMarkRegisterRequest>) params[0]).size()));
               id =  new MarkRegisterDAO(mContext).saveMarkRegister((ArrayList<PortalMarkRegisterRequest>) params[0], (String) params[1]);



            }catch(Exception e){
                e.printStackTrace();
            }



           return id;
            //return Long.valueOf(new MarkRegisterDAO(mContext).deleteRegister());
        }
    }

    //private Chip getChip




    public class ChipAdaptor extends RecyclerView.Adapter<ChipAdaptor.ViewHolder> {

        private List<MarkAttendanceChip> markAttendanceChipList;
        private Context mContext;

        public ChipAdaptor(Context mContext, List<MarkAttendanceChip> markAttendanceChipList){
            this.mContext = mContext;
            this.markAttendanceChipList = markAttendanceChipList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView txt_classStream_ID;
            private LinearLayout ll_classStream_session_ID;
            private TextView txt_session_ID;

            public ViewHolder(View view){
                super(view);
                txt_classStream_ID = view.findViewById(R.id.txt_classStream_ID);
                ll_classStream_session_ID = view.findViewById(R.id.ll_classStream_session_ID);
                txt_session_ID = view.findViewById(R.id.txt_session_ID);

            }

            void bind(final MarkAttendanceChip markAttendanceChip) {

                //isDisplay // isSaved
                //true / false ///chip not saved
                //false / true
                //false / false
                //true /true //chip saved

                //GET SESSION NAME
                ArrayList<AttendanceRollCallSessionSpinner> sessionRB = new RollCallSessonsDAO(ManageAttendanceScreen.this).getAttendanceRollCallSessionSpinnerList();
              Log.d(mContext.getPackageName().toUpperCase(), sessionRB.toString());
                String sessionName  = "";
                for(AttendanceRollCallSessionSpinner session : sessionRB){
                    if(TextUtils.isDigitsOnly(markAttendanceChip.getRollCallSession())){
                        if(session.getSessionID() == Integer.valueOf(markAttendanceChip.getRollCallSession())){
                            sessionName = session.getSessionName();
                            break;
                        }
                    }else{
                        if(session.getSessionName().equalsIgnoreCase(markAttendanceChip.getRollCallSession())){
                            sessionName = session.getSessionName();
                            break;
                        }
                    }

                }
                if (markAttendanceChip.isDisplay() && markAttendanceChip.isSaved()) {

                    txt_classStream_ID.setText(markAttendanceChip.getCourseLevelName());
                    txt_session_ID.setText(sessionName);
                    ll_classStream_session_ID.setBackground(mContext.getResources().getDrawable(R.drawable.chip_bg_solid));
                    txt_classStream_ID.setTextColor(mContext.getResources().getColor(R.color.chip_saved));
                    txt_session_ID.setTextColor(mContext.getResources().getColor(R.color.chip_saved));

                    txt_classStream_ID.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //select this for update


                            //so deletes every things before displaying again
                            //studentPortalMarkRegisterRequestList.clear();
                            //studentRegistrationNumberList.clear();


                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

                            //TEST
                            // ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestArrayList =  new MarkRegisterDAO(ManageAttendanceScreen.this).getStudentMarkRegisterForCurrentDateTest(classStreamSelected.toString(), sessionSelected, sdf.format(currentDate.getTime()));
                            //you have to send the currentdate, classstream, session
                            //   List<Object> markResult = new MarkRegisterDAO(ManageAttendanceScreen.this).getStudentMarkRegisterForCurrentDate(classStreamSelected.toString(), sessionSelected, sdf.format(currentDate.getTime()));
                            ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestArrayList = markAttendanceChip.getStudentPortalMarkRegisterRequestList();//markResult.get(0);
                            ArrayList<String> studentRegistrationNumbers = markAttendanceChip.getStudentRegistrationNumberList();



                            if (portalMarkRegisterRequestArrayList.size() > 0) {

                                //get the id of the session
                                resetFields();
                                updating = true;
                                txt_replace_sp_ID.setText(markAttendanceChip.getCourseLevelName());
                                txt_replace_sp_ID.setVisibility(View.VISIBLE);
                                txt_replace_sp_ID.setEnabled(false);
                                image_classStream_sp_ID.setEnabled(false);
                                sp_classStream_ID.setVisibility(View.INVISIBLE);

                                //sp_classStream_ID.setSelection(new ClassStreamDAO(mContext).getClassStreamSpinnerID(markAttendanceChip.getCourseLevelName()));
                               /// sp_classStream_ID.setAdapter(null);

//                                ClassStreamSpinner classStreamSpinner = new ClassStreamSpinner(new ClassStreamDAO(mContext).getClassStreamSpinnerID(markAttendanceChip.getCourseLevelName()), markAttendanceChip.getCourseLevelName());
//                                ArrayList<ClassStreamSpinner> classStreamValues = new ArrayList<>();//new ClassStreamDAO(ManageAttendanceScreen.this).getClassStreamSpinner();
//                                classStreamValues.add(classStreamSpinner);
//                                Log.d(ManageAttendanceScreen.this.getPackageName().toUpperCase(),"New Adapter values: " +  classStreamValues.toString());
//                                final ArrayAdapter<ClassStreamSpinner> classStreamAdapter = new ArrayAdapter<ClassStreamSpinner>(ManageAttendanceScreen.this, android.R.layout.simple_spinner_dropdown_item, classStreamValues);
//                                sp_classStream_ID.setAdapter(classStreamAdapter);
                                //Log.d(ManageAttendanceScreen.this.getPackageName().toUpperCase(), "Adapter count: " + String.valueOf(sp_classStream_ID.getAdapter().getCount()));

                               // Log.d(ManageAttendanceScreen.this.getPackageName().toUpperCase(), "Class Stream Adapter ID" +String.valueOf( new ClassStreamDAO(mContext).getClassStreamSpinnerID("Select Class")));




                                ArrayList<AttendanceRollCallSessionSpinner> sessionRB = new RollCallSessonsDAO(ManageAttendanceScreen.this).getAttendanceRollCallSessionSpinnerList();
//                                ArrayAdapter<AttendanceRollCallSessionSpinner> sessionValuesAdapter = new ArrayAdapter<AttendanceRollCallSessionSpinner>(ManageAttendanceScreen.this, android.R.layout.simple_spinner_dropdown_item, sessionRB);
//                                sp_session.setAdapter(sessionValuesAdapter);
//
                                sp_session.setSelection(new RollCallSessonsDAO(mContext).getAttendanceRollCallSpinnerID(markAttendanceChip.getRollCallSession()));
                                sp_session.setEnabled(false);
                                image_session_sp_ID.setEnabled(false);


                                studentPortalMarkRegisterRequestList = portalMarkRegisterRequestArrayList;
                                studentRegistrationNumberList = studentRegistrationNumbers;
                                txt_register_status.setVisibility(View.VISIBLE);

                                txt_register_status.setTextColor(getResources().getColor(R.color.update_attendance_register));
                                txt_register_status.setText("Updating attendance register");
                                label_confirmation_message.setVisibility(View.INVISIBLE);

                            } else {
//
//
//                                List<Object> markResult2 = new StudentDAO(ManageAttendanceScreen.this).getStudentMarkRegister(classStreamSelected, sessionSelected, sdf.format(currentDate.getTime()));
//                                ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestArrayList2 = (ArrayList<PortalMarkRegisterRequest>) markResult2.get(0);
//
//                                ArrayList<String> studentRegistrationNumbers2 = (ArrayList<String>) markResult2.get(1);
//
//
//                                studentPortalMarkRegisterRequestList = portalMarkRegisterRequestArrayList2;
//                                studentRegistrationNumberList = studentRegistrationNumbers2;
//
//                                txt_register_status.setVisibility(View.VISIBLE);
//                                txt_register_status.setTextColor(getResources().getColor(R.color.new_attendance_register));
//
//                                txt_register_status.setText("New attendance register");
//
                            }

                            StringBuilder s = new StringBuilder();
                            s.append(markAttendanceChip.getCourseLevelName() + " has ");
                            s.append(String.valueOf(studentPortalMarkRegisterRequestList.size()) + " Students");


                            txt_count_number_of_students.setText(s.toString());
                            adapter = new AttendanceRecyclerAdapter(ManageAttendanceScreen.this, recyclerView);
                            recyclerView.setAdapter(adapter);
                            label_filterdata_if_select_classAndSession_message.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                        }

                    });

                } else /*if(markAttendanceChip.isDisplay() && markAttendanceChip.isSaved())*/ {

                    txt_classStream_ID.setText(markAttendanceChip.getCourseLevelName());
                    txt_session_ID.setText(sessionName);
                    ll_classStream_session_ID.setBackground(mContext.getResources().getDrawable(R.drawable.chip_bg_outline));
                    txt_classStream_ID.setTextColor(mContext.getResources().getColor(R.color.chip_not_saved));
                    txt_session_ID.setTextColor(mContext.getResources().getColor(R.color.chip_not_saved));

                }



            }
        }


        @Override
        public ChipAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chip_dialog_fragment_layout, parent, false);
            return new ChipAdaptor.ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ChipAdaptor.ViewHolder holder, int position) {
            holder.bind(markAttendanceChipList.get(position));
        }


        @Override
        public int getItemCount() {
            return markAttendanceChipList.size();
        }
    }




}
