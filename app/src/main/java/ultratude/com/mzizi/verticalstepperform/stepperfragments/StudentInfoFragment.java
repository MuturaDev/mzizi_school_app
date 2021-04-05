package ultratude.com.mzizi.verticalstepperform.stepperfragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.verticalstepperform.VerticalStepperFormView;
import ultratude.com.mzizi.verticalstepperform.form.steps.AlarmNameStep;
import ultratude.com.mzizi.verticalstepperform.listener.StepperFormListener;

public class StudentInfoFragment extends Fragment implements StepperFormListener {

    //Stepper Form

    private AlarmNameStep  Student_First_Name     ;
    private AlarmNameStep  Student_Second_Name    ;
    private AlarmNameStep  Student_Last_Name      ;
    private AlarmNameStep   Admission_Number      ;
    private AlarmNameStep  Gender                 ;
    private AlarmNameStep          DOB            ;
    private AlarmNameStep  DOA                    ;
    private AlarmNameStep         School_Name     ;
    private AlarmNameStep Current_Class           ;
    private AlarmNameStep         Current_Stream  ;
    private AlarmNameStep Special_Need            ;
    private AlarmNameStep         NEMIS_NO        ;
    private AlarmNameStep Borading_Type           ;
    private AlarmNameStep         Religion        ;
    private AlarmNameStep Former_School           ;
    private AlarmNameStep         Contact_Person  ;
    private AlarmNameStep Former_School_Phone     ;
    private AlarmNameStep         Language_Spoken ;
    private AlarmNameStep Residential_Address     ;
    private AlarmNameStep         Dietary_Requirem;
    private AlarmNameStep Health_Info             ;
    private AlarmNameStep         Nationality     ;
    private AlarmNameStep Immunization_Card       ;
    private AlarmNameStep          Passport_Photo ;

    private ProgressDialog progressDialog;
    private VerticalStepperFormView verticalStepperForm;


    //This for saving
    public static final String   STUDENT_FIRST_NAME       =   "Student_First_Name";
    public static final String   STUDENT_SECOND_NAME     =    "Student_Second_Name";
    public static final String   STUDENT_LAST_NAME       =    "Student_Last_Name";
    public static final String    ADMISSION_NUMBER         =  "Admission_Number";
    public static final String   GENDER                    =  "Gender";
    public static final String    DOB_                =       "DOB";
    public static final String   DOA_                      =  "DOA";
    public static final String   SCHOOL_NAME      =           "School_Name";
    public static final String  CURRENT_CLASS            =    "Current_Class";
    public static final String          CURRENT_STREAM     =  "Current_Stream";
    public static final String  SPECIAL_NEED               =  "Special_Need";
    public static final String          NEMIS_NO_            ="NEMIS_NO";
    public static final String  BORADING_TYPE             =   "Borading_Type";
    public static final String          RELIGION         =    "Religion";
    public static final String  FORMER_SCHOOL            =    "Former_School";
    public static final String          CONTACT_PERSON     =  "Contact_Person";
    public static final String  FORMER_SCHOOL_PHONE        =  "Former_School_Phone";
    public static final String          LANGUAGE_SPOKEN     = "Language_Spoken";
    public static final String  RESIDENTIAL_ADDRESS       =   "Residential_Address";
    public static final String          DIETARY_REQUIREMENT =    "Dietary_Requirement";
    public static final String  HEALTH_INFO              =    "Health_Info";
    public static final String          NATIONALITY        =  "Nationality";
//    public static final String  IMMUNIZATION_CARD        =  "Immunization_Card";
//    public static final String           PASSPORT_PHOTO   = "Passport_Photo";


    public StudentInfoFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_info_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] stepTitles = getResources().getStringArray(R.array.steps_titles_student);

         Student_First_Name        = new AlarmNameStep(stepTitles[0]);
         Student_Second_Name         = new AlarmNameStep(stepTitles[1]);
         Student_Last_Name       = new AlarmNameStep(stepTitles[2]);
          Admission_Number       = new AlarmNameStep(stepTitles[3]);
         Gender                   = new AlarmNameStep(stepTitles[4]);
                 DOB             = new AlarmNameStep(stepTitles[5]);
         DOA                         = new AlarmNameStep(stepTitles[6]);
                School_Name          = new AlarmNameStep(stepTitles[7]);
        Current_Class                 = new AlarmNameStep(stepTitles[8]);
                Current_Stream       = new AlarmNameStep(stepTitles[9]);
        Special_Need             = new AlarmNameStep(stepTitles[10]);
                NEMIS_NO          = new AlarmNameStep(stepTitles[11]);
        Borading_Type            = new AlarmNameStep(stepTitles[12]);
                Religion         = new AlarmNameStep(stepTitles[13]);
        Former_School             = new AlarmNameStep(stepTitles[14]);
                Contact_Person   = new AlarmNameStep(stepTitles[15]);
        Former_School_Phone      = new AlarmNameStep(stepTitles[16]);
                Language_Spoken      = new AlarmNameStep(stepTitles[17]);
        Residential_Address          = new AlarmNameStep(stepTitles[18]);
                Dietary_Requirem      = new AlarmNameStep(stepTitles[19]);
        Health_Info                  = new AlarmNameStep(stepTitles[20]);
                Nationality          = new AlarmNameStep(stepTitles[21]);
        Immunization_Card            = new AlarmNameStep(stepTitles[22]);
                 Passport_Photo  = new AlarmNameStep(stepTitles[23]);





        verticalStepperForm = view.findViewById(R.id.stepper_form);
        verticalStepperForm.setup(this,
                Student_First_Name,
                Student_Second_Name,
                Student_Last_Name,
                Admission_Number,
                Gender,
                DOB,
                DOA,
                School_Name,
                Current_Class,
                Current_Stream,
                Special_Need,
                NEMIS_NO,
                Borading_Type,
                Religion,
                Former_School,
                Contact_Person,
                Former_School_Phone,
                Language_Spoken,
                Residential_Address,
                Dietary_Requirem,
                Health_Info,
                Nationality,
                Immunization_Card,
                Passport_Photo          ).init();

        super.onViewCreated(view, savedInstanceState);
    }


    //    Stepper Form
    @Override
    public void onCompletedForm() {
        final Thread dataSavingThread = saveData();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.form_sending_data_message));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                try {
                    dataSavingThread.interrupt();
                } catch (RuntimeException e) {
                    // No need to do anything here
                } finally {
                    verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
                }
            }
        });
    }

    @Override
    public void onCancelledForm() {
        //showCloseConfirmationDialog();
    }

    private Thread saveData() {

        // Fake data saving effect
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
//                    Intent intent = getIntent();
//                    setResult(RESULT_OK, intent);
//                    intent.putExtra(STATE_NEW_ALARM_ADDED, true);
//                    intent.putExtra(STATE_TITLE, nameStep.getStepData());
//                    intent.putExtra(STATE_DESCRIPTION, descriptionStep.getStepData());
//                    intent.putExtra(STATE_TIME_HOUR, timeStep.getStepData().hour);
//                    intent.putExtra(STATE_TIME_MINUTES, timeStep.getStepData().minutes);
//                    intent.putExtra(STATE_WEEK_DAYS, daysStep.getStepData());

//                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return thread;
    }

//    private void finishIfPossible() {
//        if(verticalStepperForm.isAnyStepCompleted()) {
//            showCloseConfirmationDialog();
//        } else {
//            finish();
//        }
//    }

//    private void showCloseConfirmationDialog() {
//        new DiscardAlarmConfirmationFragment().show(getActivity().getSupportFragmentManager(), null);
//    }

//    private void dismissDialogIfNecessary() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//        progressDialog = null;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home) {
//            finishIfPossible();
//            return true;
//        }
//
//        return false;
//    }

//    @Override
//    public void onClick(DialogInterface dialogInterface, int which) {
//        switch (which) {
//
//            // "Discard" button of the Discard Alarm dialog
//            case -1:
//                finish();
//                break;
//
//            // "Cancel" button of the Discard Alarm dialog
//            case -2:
//                verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
//                break;
//        }
//    }

//    @Override
//    public void onBackPressed(){
//        finishIfPossible();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        dismissDialogIfNecessary();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        dismissDialogIfNecessary();
//    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//
//        savedInstanceState.putString(STATE_TITLE, nameStep.getStepData());
//        savedInstanceState.putString(STATE_DESCRIPTION, descriptionStep.getStepData());
//        savedInstanceState.putInt(STATE_TIME_HOUR, timeStep.getStepData().hour);
//        savedInstanceState.putInt(STATE_TIME_MINUTES, timeStep.getStepData().minutes);
//        savedInstanceState.putBooleanArray(STATE_WEEK_DAYS, daysStep.getStepData());
//
//        // IMPORTANT: The call to super method must be here at the end
//        super.onSaveInstanceState(savedInstanceState);
//    }

//    @Override
//    public void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        if(savedInstanceState.containsKey(STATE_TITLE)) {
//            String title = savedInstanceState.getString(STATE_TITLE);
//            nameStep.restoreStepData(title);
//        }
//
//        if(savedInstanceState.containsKey(STATE_DESCRIPTION)) {
//            String description = savedInstanceState.getString(STATE_DESCRIPTION);
//            descriptionStep.restoreStepData(description);
//        }
//
//        if(savedInstanceState.containsKey(STATE_TIME_HOUR)
//                && savedInstanceState.containsKey(STATE_TIME_MINUTES)) {
//            int hour = savedInstanceState.getInt(STATE_TIME_HOUR);
//            int minutes = savedInstanceState.getInt(STATE_TIME_MINUTES);
//            AlarmTimeStep.TimeHolder time = new AlarmTimeStep.TimeHolder(hour, minutes);
//            timeStep.restoreStepData(time);
//        }
//
//        if(savedInstanceState.containsKey(STATE_WEEK_DAYS)) {
//            boolean[] alarmDays = savedInstanceState.getBooleanArray(STATE_WEEK_DAYS);
//            daysStep.restoreStepData(alarmDays);
//        }
//
//        // IMPORTANT: The call to super method must be here at the end
//        super.onRestoreInstanceState(savedInstanceState);
//    }

//    public static class DiscardAlarmConfirmationFragment extends DialogFragment {
//        @Override
//        @NonNull
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//            if (getActivity() == null) {
//                throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
//            }
//
//            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
//            builder.setTitle(R.string.form_discard_question)
//                    .setMessage(R.string.form_info_will_be_lost)
//                    .setPositiveButton(R.string.form_discard, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    })
//                    .setNegativeButton(R.string.form_discard_cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
//                        }
//                    })
//                    .setCancelable(false);
//            Dialog dialog = builder.create();
//            dialog.setCanceledOnTouchOutside(false);
//
//            return dialog;
//        }
//    }

}
