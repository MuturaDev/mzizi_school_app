package ultratude.com.mzizi.verticalstepperform.form.steps;

import android.content.Context;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.andrognito.patternlockview.utils.ResourceUtils;
import com.google.android.material.textfield.TextInputEditText;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.verticalstepperform.Step;


public class AlarmNameStep extends Step<String> {

    private static final int MIN_CHARACTERS_ALARM_NAME = 1;


    private ImageView imageView;

    private String unformattedErrorString;



    private TextInputEditText alarmNameEditText;




    public AlarmNameStep(String title) {
        this(title, "");
    }

    public AlarmNameStep(String title, String subtitle) {
        super(title, subtitle);
    }

    private void noteFieldsThatCantBeEdited(){
        if (    getTitle().equalsIgnoreCase("Student First Name") ||
                getTitle().equalsIgnoreCase("Student Second Name") ||
                getTitle().equalsIgnoreCase("Student Last Name") ||
                getTitle().equalsIgnoreCase("Admission Number") ||
                getTitle().equalsIgnoreCase("Gender") ||
                getTitle().equalsIgnoreCase("DOB") ||
                getTitle().equalsIgnoreCase("Date of Admission") ||
                getTitle().equalsIgnoreCase("School Name") ||
                getTitle().equalsIgnoreCase("Current Class") ||
                getTitle().equalsIgnoreCase("Current Stream") ||
                getTitle().equalsIgnoreCase("Special Need") ||
                getTitle().equalsIgnoreCase("NEMIS NO") ||
                getTitle().equalsIgnoreCase("Boarding Type") ||
                getTitle().equalsIgnoreCase("Religion") ||
                getTitle().equalsIgnoreCase("Former School") ||
                getTitle().equalsIgnoreCase("Contact Person") ||
                getTitle().equalsIgnoreCase("Former School Phone") ||
                getTitle().equalsIgnoreCase("Language Spoken") ||
                getTitle().equalsIgnoreCase("Residential Address") ||
                getTitle().equalsIgnoreCase("Dietary Requirement") ||
                getTitle().equalsIgnoreCase("Health Info") ||
                getTitle().equalsIgnoreCase("Nationality") ||
                getTitle().equalsIgnoreCase("Immunization Card") ||
                getTitle().equalsIgnoreCase("Passport Photo")
             ) {

            alarmNameEditText.setEnabled(false);



        }
    }

    private void noteSpecifyKeyboardInputType(){
        if(getTitle() == "Email Address")
            alarmNameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        if(getTitle() == "Phone Number")
            alarmNameEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        if(getTitle() == "National ID")
            alarmNameEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(getTitle() == "Contact Person")
            alarmNameEditText.setInputType(InputType.TYPE_CLASS_PHONE);
        if(getTitle() == "Former School Phone")
            alarmNameEditText.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    private void noteRequestFocusKeyboardAppear(){
        if(getStepDataAsHumanReadableString().isEmpty()) {
            alarmNameEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(alarmNameEditText, InputMethodManager.SHOW_FORCED);
        }
    }

    //TODO: TO CLEARED OUT LATER
    private void clearForUpdate(){

//        getFormView().openStep(5, true);
//
//        for(int i = 0; i < getFormView().getTotalNumberOfSteps() - 1; i++){
//
//
////
////
////                getFormView().getOpenStep().get(i).getStepInstance().isCompleted();
//
//
//            //getFormView().formCompleted = false;
//           // getFormView().goToStep(i, true);
////            getFormView().getOpenStepPosition();
////            getFormView().getOpenStep().markAsCompleted(true);
//
//            //getFormView().markOpenStepAsCompletedOrUncompleted(true);
//        }

    }

    //TODO: here we access the data and display it for each step OR SOME OTHER PLACE
    @NonNull
    @Override
    protected View createStepContentLayout() {


        // We create this step view programmatically
        alarmNameEditText = new TextInputEditText(getContext());

        LinearLayout.LayoutParams  alarmNameEditTextParam =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alarmNameEditText.setLayoutParams(alarmNameEditTextParam);


        //alarmNameEditText.setHint(R.string.form_hint_title);
        alarmNameEditText.setHint(getTitle());
        alarmNameEditText.setSingleLine(true);
        alarmNameEditText.setTextColor(getContext().getResources().getColor(R.color.homeText));

        //TODO: REMOVE THIS, ITS JUST FOR TESTING
        alarmNameEditText.setText("James");


        alarmNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                markAsCompletedOrUncompleted(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        alarmNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                getFormView().goToNextStep(true);
                return false;
            }
        });

        unformattedErrorString = getContext().getResources().getString(R.string.error_alarm_name_min_characters);

        imageView = new ImageView(getContext());


        noteFieldsThatCantBeEdited();
        noteRequestFocusKeyboardAppear();
        noteSpecifyKeyboardInputType();
        clearForUpdate();


        return alarmNameEditText;
    }

    @Override
    protected void onStepOpened(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepClosed(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
        // No need to do anything here
    }

    @Override
    public String getStepData() {
        Editable text = alarmNameEditText.getText();
        if (text != null) {
            return text.toString();
        }

        return "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        String name = getStepData();
        return name == null || name.isEmpty()
                ? getContext().getString(R.string.form_empty_field)
                : name;
    }

    @Override
    public void restoreStepData(String data) {
        if (alarmNameEditText != null) {
            alarmNameEditText.setText(data);
        }
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        if (stepData.length() < MIN_CHARACTERS_ALARM_NAME) {
            String titleError = String.format(unformattedErrorString, MIN_CHARACTERS_ALARM_NAME);
            return new IsDataValid(false, titleError);
        } else {
            return new IsDataValid(true);
        }
    }
}
