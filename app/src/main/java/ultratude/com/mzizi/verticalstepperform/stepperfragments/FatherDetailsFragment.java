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

public class FatherDetailsFragment extends Fragment implements StepperFormListener {

    //Stepper Form
    private AlarmNameStep fatherFirstNameStep;
    private AlarmNameStep fatherMiddleName;
    private AlarmNameStep fatherSecondName;
    private AlarmNameStep phoneNumber;
    private AlarmNameStep emailAddress;
    private AlarmNameStep nationalID;
    private AlarmNameStep occupation;

    private ProgressDialog progressDialog;
    private VerticalStepperFormView verticalStepperForm;


    //This for saving
    public static final String FATHER_FIRST_NAME   =  "fatherFirstNameStep";
    public static final String FATHER_MIDDLE_NAME =   "fatherMiddleName";
    public static final String FATHER_SECOND_NAME=   "fatherSecondName";
    public static final String PHONE_NUMBER =         "phoneNumber";
    public static final String EMAIL_ADDRESS =        "emailAddress";
    public static final String NATIONAL_ID =          "nationalID";

    public FatherDetailsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.father_details_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String[] stepTitles = getResources().getStringArray(R.array.steps_titles_general);

        fatherFirstNameStep = new AlarmNameStep(stepTitles[0]);
        fatherMiddleName = new AlarmNameStep(stepTitles[1]);
        fatherSecondName = new AlarmNameStep(stepTitles[2]);
        phoneNumber = new AlarmNameStep(stepTitles[3]);
        emailAddress = new AlarmNameStep(stepTitles[4]);
        nationalID = new AlarmNameStep(stepTitles[5]);
        occupation = new AlarmNameStep(stepTitles[6]);

        verticalStepperForm = view.findViewById(R.id.stepper_form);
        verticalStepperForm.setup(this, fatherFirstNameStep,
                fatherMiddleName,
                fatherSecondName,
                phoneNumber,
                emailAddress,
                nationalID,
                occupation ).init();

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
