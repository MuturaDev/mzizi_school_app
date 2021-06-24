package ultratude.com.staff.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.annotations.SerializedName;
import com.shashank.sony.fancytoastlib.FancyToast;

import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.utils.UtilityFunctions;
import ultratude.com.staff.webservice.ResponseModels.SubRequestModels.*;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.goncalves.pugnotification.notification.Progress;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.BuildConfig;
import ultratude.com.staff.R;
import ultratude.com.staff.datepickerfragments.DatePickeRFragment_DateServiced;
import ultratude.com.staff.spinnermodel.AttendanceRollCallSessionSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.MarkRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.RollCallSessonsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.RequestModels.PortalClassStreamTeacherStaffAllocationRequest;
import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;
import ultratude.com.staff.webservice.RequestModels.PortalStudentListForMarkAttendanceRequest;
import ultratude.com.staff.webservice.ResponseModels.PortalClassStreamTeacherStaffAllocationsResponse;
import ultratude.com.staff.webservice.ResponseModels.PortalStudentListForMarkAttendanceResponse;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;

public class ManageAttendanceScreenWebPortal extends AppCompatActivity implements
        DatePickeRFragment_DateServiced.DatePickerFragment_DateServiedInteractionListener {


    private FloatingActionButton floatingActionButton;
    private BottomSheetDialog bottomSheetDialog;
    private ProgressBar pb_attendance_progress;

    private Spinner sp_school_ID, sp_batch_ID;
    private Spinner sp_class_ID, sp_term_id_ID;
    private Spinner sp_stream_ID, sp_unit_ID;
    private Spinner sp_lesson_period, sp_student_list_ID;
    private Spinner sp_session_ID, sp_study_session_ID;
    private Spinner sp_activity_ID;

    private ProgressBar pg_school_ID, pg_batch_ID;
    private ProgressBar pg_class_ID, pg_term_id_ID;
    private ProgressBar pg_stream_ID, pg_unit_ID;
    private ProgressBar pg_lesson_period, pg_student_list_ID;
    private ProgressBar pg_session_ID, pg_study_session_ID;
    private ProgressBar pg_activity_ID;

    private ImageView image_school_sp_ID,image_batch_sp_ID;
    private ImageView image_term_sp_ID,image_class_sp_ID;
    private ImageView image_stream_sp_ID,image_unit_sp_ID;
    private ImageView image_lesson_period_sp_ID,image_student_list_sp_ID;
    private ImageView image_session_sp_ID,image_study_session_sp_ID;
    private ImageView image_activity_sp_ID;

    private PortalStudentListForMarkAttendanceRequest request2;
    private PortalClassStreamTeacherStaffAllocationRequest request;

    private TextView date_btn;
    private ImageView date_icon;
    private Date attendanceDate = null;

    @Override
    public void onDatePickerFragment_DateServiedInteraction(String dateToBeSaveToDB, long milli) {
        //Toast.makeText(this, "Selected date is " + year + " / " + (month + 1) + " / " + day, Toast.LENGTH_LONG).show();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
        attendanceDate = new Date(milli);
        //dateServiced = dateToBeSaveToDB;

        date_btn.setText(sdf.format(attendanceDate.getTime()));

        //sendRequestForFilters(request,sp_activity_ID);

        //on date selected get the students to mark attendance
        request2.setClassID(validateSpinnerValueReturnString(sp_class_ID));
        request2.setStreamID(validateSpinnerValueReturnString(sp_stream_ID));
        //TODO 23 FEB 2021: REPLACE THIS AS THE ACTUAL DATE
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MMM-dd");
        if (attendanceDate != null)
            request2.setDateRecorded(sdf2.format(attendanceDate.getTime()));
        request2.setRollcallSession(validateSpinnerValueReturnString(sp_session_ID));
        request2.setStudySessionID(validateSpinnerValueReturnString(sp_study_session_ID));
        request2.setUnitID(validateSpinnerValueReturnString(sp_unit_ID));
        request2.setActivityID(validateSpinnerValueReturnString(sp_activity_ID));
        request2.setStudentListID(validateSpinnerValueReturnString(sp_student_list_ID));
        request2.setASCPeriodID(validateSpinnerValueReturnString(sp_lesson_period));
        populateRecyclerView(request2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getMenuInflater().inflate(R.menu.upload_attendance_data_menu, menu);
        return true;
    }


    @Override
    public void onBackPressed() {

        if (bottomSheetDialog.isShowing()) {
            bottomSheetDialog.show();
            onDismissDialog(true);
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(ManageAttendanceScreenWebPortal.this);
            alert.setTitle("Confirm Exit");
            alert.setMessage("Are you sure you want to exit Mark Register");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.upload_attendance_btn) {

            //onDismissDialog(false);

            AlertDialog.Builder alert = new AlertDialog.Builder(ManageAttendanceScreenWebPortal.this);
            alert.setTitle("Upload Confirmation");
            alert.setMessage("Are you sure you want to upload marked attendance records");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog progressDialog = new ProgressDialog(ManageAttendanceScreenWebPortal.this);
                    progressDialog.setTitle("Student Attendance");
                    progressDialog.setMessage("Uploading attendance details...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();


                    List<PortalMarkRegisterRequest> markedAttendanceList = new ArrayList<>();
                    for (PortalStudentListForMarkAttendanceResponse attendanceResponse : attendanceList) {
                        PortalMarkRegisterRequest markRequest = new PortalMarkRegisterRequest(
                                attendanceResponse.getStudentID(),
                                attendanceResponse.getStudentFullName(),
                                attendanceResponse.getStatus(),
                                request2.getDateRecorded(),
                                request2.getRollcallSession(),
                                staff.getStaff_ID(),
                                attendanceResponse.getRemarks(),
                                staff.getAppcode()

                        );


                        markRequest.setUnitID(request2.getUnitID());
                        markRequest.setChargeTypeID(request2.getActivityID());
                        markRequest.setLateBy(attendanceResponse.getLateBy());
                        markRequest.setLessonPeriodID(request2.getASCPeriodID());
                        markRequest.setListID(request2.getStudentListID());
                        markRequest.setEndpointVersion(request2.getEndpointVersion());
                        markRequest.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));

                        markedAttendanceList.add(markRequest);

                    }

                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
                    Call<String> apiCall2 = apiInterface.saveMarkRegister(markedAttendanceList);
                    apiCall2.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                            if (response.code() == 200) {
                                ((TextView) bottomSheetView.findViewById(R.id.label_filterdata_if_select_classAndSession_message)).setVisibility(View.VISIBLE);

                                String message = "Congratulations, You've marked, " + new SimpleDateFormat("EEE, dd MMM yyyy").format(attendanceDate.getTime()) + "'s register";
                                ((TextView) bottomSheetView.findViewById(R.id.label_filterdata_if_select_classAndSession_message)).setTextColor(getResources().getColor(R.color.confirmation_success));


                            } else if (response.code() == 500) {
                                FancyToast.makeText(ManageAttendanceScreenWebPortal.this, response != null ? response.body() != null ? response.body() : "" : "", FancyToast.SUCCESS, FancyToast.LENGTH_LONG, true).show();
                            }

                            progressDialog.cancel();


                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.cancel();
                            FancyToast.makeText(ManageAttendanceScreenWebPortal.this, "Error message: " + t.getMessage(), FancyToast.ERROR, FancyToast.LENGTH_LONG, true).show();
                        }
                    });
                }
            });
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();


        }
        return super.onOptionsItemSelected(item);
    }


    private boolean checkIfSpinnerIsEmpty(Spinner spinner) {
        if (spinner != null && spinner.getSelectedItem() != null) {

            if (spinner.getSelectedItem() instanceof PortalGetSchoolsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetSchoolsResponse) spinner.getSelectedItem()).getSchoolName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetClassesResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetClassesResponse) spinner.getSelectedItem()).getSchoolName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetStreamResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetStreamResponse) spinner.getSelectedItem()).getLevelName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetUnitsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetUnitsResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetStudySessionsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetStudySessionsResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetChargeTypeNamesResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetChargeTypeNamesResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetLessonPeriods) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetLessonPeriods) spinner.getSelectedItem()).getName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetStudentList) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetStudentList) spinner.getSelectedItem()).getName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalRollCallSessionResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalRollCallSessionResponse) spinner.getSelectedItem()).getSessionName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalTermsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalTermsResponse) spinner.getSelectedItem()).getTermName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            if (spinner.getSelectedItem() instanceof PortalBatchsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalBatchsResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return false;
                } else {
                    return true;
                }
            }
            
        } else {
            return false;
        }

        return true;
    }


    private Staff staff;
    private View bottomSheetView;
    private RecyclerView recyclerView;

    private int dismissCount = 0;
    private int maxTimesForDismis = 5;

    private void onDismissDialog(boolean finishActivity) {


        if (checkIfSpinnerIsEmpty(sp_school_ID) || checkIfSpinnerIsEmpty(sp_class_ID) || attendanceDate == null) {

            String school = checkIfSpinnerIsEmpty(sp_school_ID) ? "Select School" : "";
            String classs = checkIfSpinnerIsEmpty(sp_class_ID) ? "Select Class" : "";
            String allfilters = checkIfSpinnerIsEmpty(sp_school_ID) && checkIfSpinnerIsEmpty(sp_class_ID) && attendanceDate == null ? "Select School, Class and Date filters" : "";
            String attendanceDateString = attendanceDate == null ? "Select Date" : "";

            if (!TextUtils.isEmpty(allfilters))
                FancyToast.makeText(ManageAttendanceScreenWebPortal.this, allfilters, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
            else if (!TextUtils.isEmpty(school))
                FancyToast.makeText(ManageAttendanceScreenWebPortal.this, school, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

            if (!TextUtils.isEmpty(classs))
                FancyToast.makeText(ManageAttendanceScreenWebPortal.this, classs, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

            if (!TextUtils.isEmpty(attendanceDateString))
                FancyToast.makeText(ManageAttendanceScreenWebPortal.this, attendanceDateString, FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();

            if (bottomSheetDialog != null)
                bottomSheetDialog.show();

            if (finishActivity) {
                dismissCount++;
                if (maxTimesForDismis == dismissCount) {
                    finish();
                    return;
                }
            }


            return;
        } else {
            populateRecyclerView(request2);
            bottomSheetDialog.dismiss();
        }
    }

    boolean btnsaveClicked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_attendance_layout_webportal);

        UtilityFunctions.activateQuickActions(this,  0, HomeScreen.CurrentScreenKey);

        floatingActionButton = findViewById(R.id.fab);
        pb_attendance_progress = findViewById(R.id.pb_attendance_progress);
        pb_attendance_progress.setVisibility(View.VISIBLE);

        setTitle("Mark Register");

        recyclerView = findViewById(R.id.mark_register_rv);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    InputMethodManager imm = (InputMethodManager) recyclerView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


        bottomSheetDialog = new BottomSheetDialog(ManageAttendanceScreenWebPortal.this, R.style.BottomSheetTheme);
        bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.manage_attendance_layout_filters_bottom_sheet,
                (ViewGroup) findViewById(R.id.bottom_sheet_layout));
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);


        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                onDismissDialog(true);
            }
        });

        Button btn_save_ID = bottomSheetView.findViewById(R.id.btn_save_ID);
        btn_save_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDismissDialog(false);
            }
        });

        date_btn = bottomSheetView.findViewById(R.id.txt_mark_attendance_date_ID);
        date_icon = bottomSheetView.findViewById(R.id.imageview);
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickeRFragment_DateServiced();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });
        date_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickeRFragment_DateServiced();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });

        sp_school_ID = bottomSheetView.findViewById(R.id.sp_school_ID);
        pg_school_ID = bottomSheetView.findViewById(R.id.pg_school_sp);

        sp_batch_ID = bottomSheetView.findViewById(R.id.sp_batch_ID);
        pg_batch_ID = bottomSheetView.findViewById(R.id.pg_batch_sp);

        sp_class_ID = bottomSheetView.findViewById(R.id.sp_class_ID);
        pg_class_ID = bottomSheetView.findViewById(R.id.pg_class_sp);

        sp_term_id_ID = bottomSheetView.findViewById(R.id.sp_term_ID);
        pg_term_id_ID = bottomSheetView.findViewById(R.id.pg_term_sp);

        sp_stream_ID = bottomSheetView.findViewById(R.id.sp_stream_ID);
        pg_stream_ID = bottomSheetView.findViewById(R.id.pg_stream_sp);

        sp_unit_ID = bottomSheetView.findViewById(R.id.sp_unit_ID);
        pg_unit_ID = bottomSheetView.findViewById(R.id.pg_unit_sp);

        sp_lesson_period = bottomSheetView.findViewById(R.id.sp_lesson_period_ID);
        pg_lesson_period = bottomSheetView.findViewById(R.id.pg_lesson_period_sp);

        sp_student_list_ID = bottomSheetView.findViewById(R.id.sp_student_list_ID);
        pg_student_list_ID = bottomSheetView.findViewById(R.id.pg_student_list_sp);

        sp_session_ID = bottomSheetView.findViewById(R.id.sp_session_ID);
        pg_session_ID = bottomSheetView.findViewById(R.id.pg_session_sp);

        sp_study_session_ID = bottomSheetView.findViewById(R.id.sp_study_session_ID);
        pg_study_session_ID = bottomSheetView.findViewById(R.id.pg_study_session_sp);

        sp_activity_ID = bottomSheetView.findViewById(R.id.sp_activity_ID);
        pg_activity_ID = bottomSheetView.findViewById(R.id.pg_activity_sp);

        image_school_sp_ID = bottomSheetView.findViewById(R.id.image_school_sp_ID);
        image_school_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_school_ID.performClick();
            }
        });
        image_batch_sp_ID = bottomSheetView.findViewById(R.id.image_batch_sp_ID);
        image_batch_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_batch_ID.performClick();
            }
        });
        image_term_sp_ID = bottomSheetView.findViewById(R.id.image_term_sp_ID);
        image_term_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_term_id_ID.performClick();
            }
        });
        image_class_sp_ID = bottomSheetView.findViewById(R.id.image_class_sp_ID);
        image_class_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_class_ID.performClick();
            }
        });
        image_stream_sp_ID =  bottomSheetView.findViewById(R.id.image_stream_sp_ID);
        image_stream_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_stream_ID.performClick();
            }
        });
        image_unit_sp_ID = bottomSheetView.findViewById(R.id.image_unit_sp_ID);
        image_unit_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_unit_ID.performClick();
            }
        });
        image_lesson_period_sp_ID = bottomSheetView.findViewById(R.id.image_lesson_period_sp_ID);
        image_lesson_period_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_lesson_period.performClick();
            }
        });
        image_student_list_sp_ID = bottomSheetView.findViewById(R.id.image_student_list_sp_ID);
        image_student_list_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_student_list_ID.performClick();
            }
        });
        image_session_sp_ID = bottomSheetView.findViewById(R.id.image_session_sp_ID);
        image_session_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_session_ID.performClick();
            }
        });
        image_study_session_sp_ID = bottomSheetView.findViewById(R.id.image_study_session_sp_ID);
        image_study_session_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_study_session_ID.performClick();
            }
        });
        image_activity_sp_ID = bottomSheetView.findViewById(R.id.image_activity_sp_ID);
        image_activity_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_activity_ID.performClick();
            }
        });


        staff = new StaffDao(ManageAttendanceScreenWebPortal.this).getUserThatSignedUp();

        request2 = new PortalStudentListForMarkAttendanceRequest(
//                "21",
//                "",
//                "2015-07-14",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "",
//                "1010"


                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                staff.getAppcode()
        );
        request2.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
        request2.setEndpointVersion("2");

        request = new PortalClassStreamTeacherStaffAllocationRequest(
//                "",
//                "",
//                "",//staff.getStaff_ID();
//                "21",
//                "47",
//                "34",
//                "2",
//                "2015",
//                "138",
//                "138",
//                "",
//                "",
//                "",
//                "",
//                String.valueOf(BuildConfig.VERSION_CODE),
//                "1010",
//                "2",
//                ""

                staff.getOrganizationID(),
                "",
                staff.getSchoolID(),
                "",
                "",
                "",
                "",
                "",
                "",
                staff.getStaff_ID(),
                "",
                "",
                "",
                "",
                String.valueOf(BuildConfig.VERSION_CODE),
                staff.getAppcode(),
                "2",
                ""

        );

        sendRequestForFilters(request, null, null);
        populateRecyclerView(request2);


        sp_school_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_school_ID.getSelectedItemId() != 0 || !sp_school_ID.getSelectedItem().toString().contains("Select")) {
                    request.setSchoolID(String.valueOf(((PortalGetSchoolsResponse) sp_school_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_school_ID, pg_school_ID);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_batch_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_batch_ID.getSelectedItemId() != 0 || !sp_batch_ID.getSelectedItem().toString().contains("Select")) {
                    request.setBatchID(String.valueOf(((PortalBatchsResponse) sp_batch_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_batch_ID, pg_batch_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_class_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_class_ID.getSelectedItemId() != 0 || !sp_class_ID.getSelectedItem().toString().contains("Select")) {
                    request.setClassID(String.valueOf(((PortalGetClassesResponse) sp_class_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_class_ID, pg_class_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_term_id_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_term_id_ID.getSelectedItemId() != 0 || !sp_term_id_ID.getSelectedItem().toString().contains("Select")) {
                    request.setTermID(String.valueOf(((PortalTermsResponse) sp_term_id_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_term_id_ID, pg_term_id_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_stream_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_stream_ID.getSelectedItemId() != 0 || !sp_stream_ID.getSelectedItem().toString().contains("Select")) {
                    request.setStreamID(String.valueOf(((PortalGetStreamResponse) sp_stream_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_stream_ID, pg_stream_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_unit_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_unit_ID.getSelectedItemId() != 0 || !sp_unit_ID.getSelectedItem().toString().contains("Select")) {
                    request.setUnitID(String.valueOf(((PortalGetUnitsResponse) sp_unit_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_unit_ID, pg_unit_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_lesson_period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_lesson_period.getSelectedItemId() != 0 || !sp_lesson_period.getSelectedItem().toString().contains("Select")) {
                    request.setLessonPeriod(String.valueOf(((PortalGetLessonPeriods) sp_lesson_period.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_lesson_period, pg_lesson_period);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_student_list_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_student_list_ID.getSelectedItemId() != 0 || !sp_student_list_ID.getSelectedItem().toString().contains("Select")) {
                    request.setStudentListID(String.valueOf(((PortalGetStudentList) sp_student_list_ID.getSelectedItem()).getID()));
                    sendRequestForFilters(request, sp_student_list_ID, pg_student_list_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_session_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_session_ID.getSelectedItemId() != 0 || !sp_session_ID.getSelectedItem().toString().contains("Select")) {
                    request.setRollCallSession(String.valueOf(((PortalRollCallSessionResponse) sp_session_ID.getSelectedItem()).getSessionID()));
                    sendRequestForFilters(request, sp_session_ID, pg_session_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_study_session_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_study_session_ID.getSelectedItemId() != 0 || !sp_study_session_ID.getSelectedItem().toString().contains("Select")) {
//                    request.set(String.valueOf(sp_study_session_ID.getSelectedItemId()));
//                    sendRequestForFilters(request,sp_study_session_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_activity_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (sp_activity_ID.getSelectedItemId() != 0 || !sp_activity_ID.getSelectedItem().toString().contains("Select")) {
//                    request.set(String.valueOf(sp_activity_ID.getSelectedItemId()));
//                    sendRequestForFilters(request,sp_activity_ID);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //get items within the bottom sheet.
        // bottomSheetView.findViewById()

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.show();
            }
        });


        //open everytime i the activity is open
        bottomSheetDialog.show();
    }

    private void showNoContentView(boolean show) {
        ((TextView) findViewById(R.id.txt_no_content)).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void populateRecyclerView(PortalStudentListForMarkAttendanceRequest request) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
        Call<List<PortalStudentListForMarkAttendanceResponse>> apiCall2 = apiInterface.getPortalStudentListForMarkAttendance(request);
        apiCall2.enqueue(new Callback<List<PortalStudentListForMarkAttendanceResponse>>() {
            @Override
            public void onResponse(Call<List<PortalStudentListForMarkAttendanceResponse>> call, Response<List<PortalStudentListForMarkAttendanceResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.code() == 200) {

                        recyclerView.setHasFixedSize(true);

                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(ManageAttendanceScreenWebPortal.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager1);

                        attendanceList = response.body();
                        ManageAttendanceAdapter markRegisterAdapter = new ManageAttendanceAdapter(ManageAttendanceScreenWebPortal.this);
                        recyclerView.setAdapter(markRegisterAdapter);
                        markRegisterAdapter.notifyDataSetChanged();

                        showNoContentView(false);
                    } else {
                        showNoContentView(true);
                    }
                } else {
                    showNoContentView(true);
                }

                pb_attendance_progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<PortalStudentListForMarkAttendanceResponse>> call, Throwable t) {
                showNoContentView(false);
            }
        });
    }


    private void sendRequestForFilters(final PortalClassStreamTeacherStaffAllocationRequest request, final Spinner spinner, final ProgressBar progress) {
        showSpinnerProgress(progress, true);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
        Call<PortalClassStreamTeacherStaffAllocationsResponse> apiCall2 = apiInterface.getCustomFiltersForMarkRegister(request);
        apiCall2.enqueue(new Callback<PortalClassStreamTeacherStaffAllocationsResponse>() {
            @Override
            public void onResponse(Call<PortalClassStreamTeacherStaffAllocationsResponse> call, Response<PortalClassStreamTeacherStaffAllocationsResponse> response) {

                if(response.body() == null){

                }else{
                    //if(sp_school_ID.getSelectedItem() != null)

                        if (/*sp_school_ID.getSelectedItemId() == 0 || sp_school_ID.getSelectedItem().toString().contains("Select") ||*/ spinner == null) {


                            sp_school_ID.setAdapter(response.body().getGetSchools() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetSchools()));
                            sp_batch_ID.setAdapter(response.body().getGetBatchsResponse() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetBatchsResponse()));
                            sp_class_ID.setAdapter(response.body().getGetClasses() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetClasses()));
                            sp_term_id_ID.setAdapter(response.body().getGetTermsResponse() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetTermsResponse()));
                            sp_stream_ID.setAdapter(response.body().getGetStreams() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStreams()));
                            sp_unit_ID.setAdapter(response.body().getGetUnits() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetUnits()));
                            sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                            sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                            sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                            sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                            sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

                            showSpinnerProgress(progress, false);
                            //showSpinnerProgress(null, false);
                        } else {
                            populateDropDowns(spinner, progress, response);
                        }
                }
//

            }

            @Override
            public void onFailure(Call<PortalClassStreamTeacherStaffAllocationsResponse> call, Throwable t) {

            }
        });

    }

    private String validateSpinnerValueReturnString(Spinner spinner) {

        if (spinner.getSelectedItem() != null) {

            if (spinner.getSelectedItem() instanceof PortalGetSchoolsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetSchoolsResponse) spinner.getSelectedItem()).getSchoolName().contains("Select")) {
                    return String.valueOf(((PortalGetSchoolsResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetClassesResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetClassesResponse) spinner.getSelectedItem()).getSchoolName().contains("Select")) {
                    return String.valueOf(((PortalGetClassesResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetStreamResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetStreamResponse) spinner.getSelectedItem()).getLevelName().contains("Select")) {
                    return String.valueOf(((PortalGetStreamResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetUnitsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetUnitsResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return String.valueOf(((PortalGetUnitsResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetStudySessionsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetStudySessionsResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return String.valueOf(((PortalGetStudySessionsResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetChargeTypeNamesResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetChargeTypeNamesResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return String.valueOf(((PortalGetChargeTypeNamesResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetLessonPeriods) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetLessonPeriods) spinner.getSelectedItem()).getName().contains("Select")) {
                    return String.valueOf(((PortalGetLessonPeriods) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalGetStudentList) {
                if (spinner.getSelectedItemId() != 0 || !((PortalGetStudentList) spinner.getSelectedItem()).getName().contains("Select")) {
                    return String.valueOf(((PortalGetStudentList) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalRollCallSessionResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalRollCallSessionResponse) spinner.getSelectedItem()).getSessionName().contains("Select")) {
                    return String.valueOf(((PortalRollCallSessionResponse) spinner.getSelectedItem()).getSessionID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalTermsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalTermsResponse) spinner.getSelectedItem()).getTermName().contains("Select")) {
                    return String.valueOf(((PortalTermsResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }
            if (spinner.getSelectedItem() instanceof PortalBatchsResponse) {
                if (spinner.getSelectedItemId() != 0 || !((PortalBatchsResponse) spinner.getSelectedItem()).getName().contains("Select")) {
                    return String.valueOf(((PortalBatchsResponse) spinner.getSelectedItem()).getID());
                } else {
                    return "0";
                }
            }


        } else {
            return "0";
        }

        return "0";
    }


        private void populateDropDowns (Spinner spinner, ProgressBar
        progressBar, Response < PortalClassStreamTeacherStaffAllocationsResponse > response){
        
        
        


            showSpinnerProgress(progressBar, false);

            if (spinner.getId() == sp_school_ID.getId()) {
                sp_batch_ID.setAdapter(response.body().getGetBatchsResponse() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetBatchsResponse()));
                sp_class_ID.setAdapter(response.body().getGetClasses() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetClasses()));
                sp_term_id_ID.setAdapter(response.body().getGetTermsResponse() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetTermsResponse()));
                sp_stream_ID.setAdapter(response.body().getGetStreams() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStreams()));
                sp_unit_ID.setAdapter(response.body().getGetUnits() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetUnits()));
                sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

            } else if (spinner.getId() == sp_batch_ID.getId()) {
                sp_class_ID.setAdapter(response.body().getGetClasses() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetClasses()));
                sp_term_id_ID.setAdapter(response.body().getGetTermsResponse() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetTermsResponse()));
                sp_stream_ID.setAdapter(response.body().getGetStreams() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStreams()));
                sp_unit_ID.setAdapter(response.body().getGetUnits() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetUnits()));
                sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

            } else if (spinner.getId() == sp_class_ID.getId()) {
                sp_term_id_ID.setAdapter(response.body().getGetTermsResponse() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetTermsResponse()));
                sp_stream_ID.setAdapter(response.body().getGetStreams() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStreams()));
                sp_unit_ID.setAdapter(response.body().getGetUnits() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetUnits()));
                sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

            } else if (spinner.getId() == sp_term_id_ID.getId()) {
                sp_stream_ID.setAdapter(response.body().getGetStreams() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStreams()));
                sp_unit_ID.setAdapter(response.body().getGetUnits() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetUnits()));
                sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

            } else if (spinner.getId() == sp_stream_ID.getId()) {
                sp_unit_ID.setAdapter(response.body().getGetUnits() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetUnits()));
                sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

            } else if (spinner.getId() == sp_unit_ID.getId()) {
                sp_lesson_period.setAdapter(response.body().getGetLessonsPeriods() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetLessonsPeriods()));
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));

            } else if (spinner.getId() == sp_lesson_period.getId()) {
                sp_student_list_ID.setAdapter(response.body().getGetStudentList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudentList()));
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));
            } else if (spinner.getId() == sp_student_list_ID.getId()) {
                sp_session_ID.setAdapter(response.body().getGetRollCallSessionsList() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetRollCallSessionsList()));
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));
            } else if (spinner.getId() == sp_study_session_ID.getId()) {
                sp_study_session_ID.setAdapter(response.body().getGetStudySessions() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetStudySessions()));
                sp_activity_ID.setAdapter(response.body().getGetActivities() == null ? new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, new ArrayList()) :new ArrayAdapter<>(ManageAttendanceScreenWebPortal.this, android.R.layout.simple_spinner_dropdown_item, response.body().getGetActivities()));
            } else if (spinner.getId() == sp_activity_ID.getId()) {

            }
        }


        private void showSpinnerProgress (ProgressBar progressBar,boolean visibility){
            if (progressBar != null)
                progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);

            else {
                showSpinnerProgress(pg_school_ID, visibility);
                showSpinnerProgress(pg_batch_ID, visibility);
                showSpinnerProgress(pg_class_ID, visibility);
                showSpinnerProgress(pg_term_id_ID, visibility);
                showSpinnerProgress(pg_stream_ID, visibility);
                showSpinnerProgress(pg_unit_ID, visibility);
                showSpinnerProgress(pg_lesson_period, visibility);
                showSpinnerProgress(pg_student_list_ID, visibility);
                showSpinnerProgress(pg_session_ID, visibility);
                showSpinnerProgress(pg_study_session_ID, visibility);
                showSpinnerProgress(pg_activity_ID, visibility);
            }
        }


        private static List<PortalStudentListForMarkAttendanceResponse> attendanceList;

        public class ManageAttendanceAdapter extends RecyclerView.Adapter<ManageAttendanceAdapter.ViewHolder> {

            private Context mContext;

            public ManageAttendanceAdapter(Context mContext) {
                this.mContext = mContext;
            }


            public class ViewHolder extends RecyclerView.ViewHolder {

                private TextView label_confirmation_message_viewHolder, label_student_number;
                private TextInputEditText txt_studentName_ID;
                private EditText   txt_reason_ID;
                private RadioButton rb_present_ID, rb_absent_ID;
                private EditText edit_late_by;
                private RadioGroup radioGroup;

                private LinearLayout manage_attendance_layout_ID;

                private LinearLayout reason_layout_ID;

                public ViewHolder(View itemView) {
                    super(itemView);

                    reason_layout_ID = itemView.findViewById(R.id.reason_layout_ID);
                    txt_reason_ID = itemView.findViewById(R.id.txt_reason_ID);
                    label_confirmation_message_viewHolder = itemView.findViewById(R.id.label_confirmation_message);
                    txt_studentName_ID = itemView.findViewById(R.id.txt_studentName_ID);
                    rb_absent_ID = itemView.findViewById(R.id.rb_absent_ID);
                    rb_present_ID = itemView.findViewById(R.id.rb_present_ID);
                    label_student_number = itemView.findViewById(R.id.label_student_number);

                    radioGroup = itemView.findViewById(R.id.radioGroup);

                    edit_late_by = itemView.findViewById(R.id.edit_late_by);


                }

                public final void bind(final PortalStudentListForMarkAttendanceResponse response) {

                    label_student_number.setText(response.getNum());
                    txt_studentName_ID.setText(response.getStudentFullName() + " (" + response.getRegistrationNumber() + ")");
                    if (response.getStatus().equals("1")) {
                        rb_present_ID.setChecked(true);
                        rb_absent_ID.setChecked(false);
                        reason_layout_ID.setVisibility(View.GONE);
                    } else if (response.getStatus().equals("2")) {
                        rb_present_ID.setChecked(false);
                        rb_absent_ID.setChecked(true);
                        reason_layout_ID.setVisibility(View.VISIBLE);
                    }
                    edit_late_by.setText(response.getLateBy());
                    txt_reason_ID.setText(response.getRemarks());

                    final int position = getAdapterPosition();
                    if (position >= 0) {
                        final PortalStudentListForMarkAttendanceResponse markeregister = attendanceList.get(position);

                        txt_reason_ID.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                                if (!TextUtils.isEmpty(s))
                                    if (markeregister.getNum().equals(response.getNum())) {
                                        markeregister.setRemarks(txt_reason_ID.getText().toString().trim());
                                    }

                            }
                        });

                        edit_late_by.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean hasFocus) {
                                if (hasFocus) {
                                    //Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
                                    if (edit_late_by.getText().toString().equals("0"))
                                        edit_late_by.setText("");

                                } else {
                                    //Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                                }
                            }
                        });


                        edit_late_by.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if (!TextUtils.isEmpty(s))
                                    if (markeregister.getNum().equals(response.getNum())) {
                                        markeregister.setLateBy(edit_late_by.getText().toString().trim());
                                    }

                            }
                        });


                        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                if (checkedId == R.id.rb_absent_ID) {
                                    //do something.
                                    reason_layout_ID.setVisibility(View.VISIBLE);
                                } else if (checkedId == R.id.rb_present_ID) {
                                    txt_reason_ID.setText("");
                                    reason_layout_ID.setVisibility(View.GONE);
                                }


                                if (markeregister.getNum().equals(response.getNum())) {
                                    markeregister.setStatus(rb_absent_ID.isChecked() ? "2" : rb_present_ID.isChecked() ? "1" : "2");
                                }
                            }
                        });

                        manage_attendance_layout_ID = itemView.findViewById(R.id.manage_attendance_layout_ID);
                        manage_attendance_layout_ID.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }

                }

            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_attendance_item_layout_webportal, parent, false);
                return new ViewHolder(view);
            }


            @Override
            public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                holder.bind(attendanceList.get(position));
                holder.setIsRecyclable(false);

            }

            @Override
            public int getItemCount() {
                return attendanceList.size();
            }
        }

}

