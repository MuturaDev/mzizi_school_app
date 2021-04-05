package ultratude.com.mzizi.uiactivities;

import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.AsyncTask;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.modelclasses.request.StudentChangePasswordRequest;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;



public class SettingsFragment extends Fragment {

    private Switch pin_switch, pattern_switch;
//    private PinView mPinView;
    private static final String ARG_CURRENT_PIN = "current_pin";
    private CardView logout_btn;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ParentMziziDatabase db;
    static FragmentManager fragmentManager;



    private RelativeLayout use_with_fingerprint_layout;

    private String password = "";
    private boolean continueProcess = true;

    private String newPasswordText = "";
    private String confirmPasswordText = "";


    public SettingsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "Settings Fragment");
        return inflater.inflate(R.layout.activity_settings, container, false);
    }


    private Switch lightSwitch, mziziSwitch,fingerprint_switch;




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Settings");
        db = ParentMziziDatabase.getInstance(getContext());
        Paper.init(getActivity());

        ((Button) view.findViewById(R.id.sendFCMDeviceToken)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // sendFCMDeviceToken(view);
            }
        });


        // TODO: 2020-04-26 Fingerprint has errors solve it later... 
        use_with_fingerprint_layout = getActivity().findViewById(R.id.use_with_fingerprint_layout);

        boolean fingerprintHardwareDetected = false;
        boolean fingerprintNotRegister = false;


        List<Boolean> booleanList = UtilityFunctions.fingerprintHardwareDetected(getActivity());
        fingerprintHardwareDetected = booleanList.get(0);
        fingerprintNotRegister = booleanList.get(1);

        TextView fingerprint_text  = getActivity().findViewById(R.id.fingerprint_text);

        if(fingerprintHardwareDetected){
            use_with_fingerprint_layout.setVisibility(View.VISIBLE);

            if(fingerprintNotRegister){
                fingerprint_text.setText("To use with fingerprint, please register a fingerprint");
            }


        }else{
            use_with_fingerprint_layout.setVisibility(View.GONE);
        }



        lightSwitch = getActivity().findViewById(R.id.light_switch);
        mziziSwitch = getActivity().findViewById(R.id.mzizi_ui_switch);
      //  fingerprint_switch = getActivity().findViewById(R.id.fingerprint_switch);
        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchState) {
                Paper.book().write("LightUI", switchState);
                mziziSwitch.setChecked(!switchState);
                Paper.book().write("MziziUI", !switchState);

                if(switchState)
                    ReportAnalytics.reportAnalyticEvent(getContext(), "ThemeSelected", "Light");

            }
        });

        mziziSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean switchState) {
                Paper.book().write("MziziUI", switchState);
                lightSwitch.setChecked(!switchState);
                Paper.book().write("LightUI", !switchState);

                if(switchState)
                    ReportAnalytics.reportAnalyticEvent(getContext(), "ThemeSelected", "Mzizi");
            }
        });

        if(Paper.book().read("LightUI") != null){
            lightSwitch.setChecked((Boolean) Paper.book().read("LightUI"));
        }

        if(Paper.book().read("MziziUI") != null){
            mziziSwitch.setChecked((Boolean) Paper.book().read("MziziUI"));
        }





        //TRY AGAIN LATER
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                }
//            });
//        }


        // TODO: 2020-04-26 Change password had errors solve them 
        logout_btn  = getActivity().findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {


                              final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                              builder.setTitle("Change Password");
                              LayoutInflater inflater = getLayoutInflater();
                              View view = inflater.inflate(R.layout.change_password_dialog_layout, null, false);

                              final TextInputEditText oldPassword = view.findViewById(R.id.oldpassword);
                              final TextInputEditText newPassword = view.findViewById(R.id.newpassword);
                              final TextInputEditText confirmPassword = view.findViewById(R.id.confirmpassword);

                              final TextInputLayout old_password = view.findViewById(R.id.old_password);
                              final TextInputLayout new_password = view.findViewById(R.id.new_password);
                              final TextInputLayout confirm_password = view.findViewById(R.id.confirm_password);


                              //TODO: There is need to have the forgot pin or pattern since users might forget the there is need to fall back
                              oldPassword.addTextChangedListener(new TextWatcher() {
                                  @Override
                                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                  }

                                  @Override
                                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                      // Log.d(getActivity().getPackageName().toUpperCase(),  password+ " " + charSequence.toString());
                                      if (!password.isEmpty()) {
                                          if (password.toCharArray().length == charSequence.length()) {
                                              if (oldPassword.getText().toString().trim().equalsIgnoreCase(password)) {
                                                  old_password.setErrorEnabled(false);
                                                  continueProcess = true;
                                              } else {
                                                  old_password.setErrorEnabled(true);
                                                  oldPassword.requestFocus();
                                                  old_password.setError("Password entered is wrong, please enter the correct password.");
                                                  continueProcess = false;
                                              }
                                          } else if (password.toCharArray().length < charSequence.length()) {
                                              old_password.setErrorEnabled(true);
                                              oldPassword.requestFocus();
                                              old_password.setError("Password entered is wrong, please enter the correct password.");
                                              continueProcess = false;
                                          }
                                      }
                                  }

                                  @Override
                                  public void afterTextChanged(Editable editable) {

                                  }
                              });

                              confirmPassword.addTextChangedListener(new TextWatcher() {
                                  @Override
                                  public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                  }

                                  @Override
                                  public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                      if (!newPassword.getText().toString().isEmpty()) {
                                          if (newPassword.getText().toString().toCharArray().length == charSequence.length()) {
                                              if (newPassword.getText().toString().equalsIgnoreCase(charSequence.toString())) {
                                                  confirm_password.setErrorEnabled(false);
                                                  continueProcess = true;
                                              } else {
                                                  confirm_password.setErrorEnabled(true);
                                                  confirmPassword.requestFocus();
                                                  confirm_password.setError("Password entered does not match with your new password.");
                                                  continueProcess = false;
                                              }
                                          } else if (newPassword.getText().toString().toCharArray().length < charSequence.length()) {
                                              confirm_password.setErrorEnabled(true);
                                              confirmPassword.requestFocus();
                                              confirm_password.setError("Password entered does not match with your new password.");
                                              continueProcess = false;
                                          }

                                      }
                                  }

                                  @Override
                                  public void afterTextChanged(Editable editable) {

                                  }
                              });


                              builder.setView(view);

                              CardView cancelProcess = view.findViewById(R.id.cancel_process);
                              CardView changepassword = view.findViewById(R.id.change_password);

                              builder.setCancelable(false);
                              final AlertDialog alert = builder.create();
                              alert.getWindow().setGravity(Gravity.CENTER);
                              //alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                              alert.show();

                              cancelProcess.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {
                                      alert.cancel();
                                  }
                              });


                              changepassword.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View view) {


                                      if (oldPassword.getText().toString().isEmpty()) {
                                          old_password.setErrorEnabled(true);
                                          oldPassword.requestFocus();
                                          old_password.setError("Enter Old Password.");
                                          continueProcess = false;
                                      } else {
                                          continueProcess = true;
                                      }

                                      if (newPassword.getText().toString().isEmpty()) {
                                          new_password.setErrorEnabled(true);
                                          newPassword.requestFocus();
                                          new_password.setError("Enter New Password.");
                                          continueProcess = false;
                                      } else {
                                          continueProcess = true;
                                      }

                                      if (confirmPassword.getText().toString().isEmpty()) {
                                          confirm_password.setErrorEnabled(true);
                                          confirmPassword.requestFocus();
                                          confirm_password.setError("Confirm new password entered.");
                                          continueProcess = false;
                                      } else {
                                          continueProcess = true;
                                      }

                                      if (continueProcess) {

                                          //TODO: CHECK IF THE PASSWORD ENTERED AND THE CONFIRM PASSWORD ARE OKAY
                                          //TODO:

                                          sendRequestForChangeOfPassword(view, newPassword.getText().toString().trim());


                                          // alert.cancel();
                                      }
                                      //alert.cancel();
                                  }
                              });


//THIS SECION WAS COMMENTED BEFORE
//                AlertDialog.Builder    alert = new AlertDialog.Builder(getActivity());
//                alert.setCancelable(false);
//                alert.setMessage("Are you sure you want to logout?");
//                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        //this should logout the user and move the user to login screen;
//                        SharedPreferences pshare = getActivity().getSharedPreferences(GetConstants.PREF_KEY_NAME, 0);
//                        //if there is a user still logged in, go to home screen instead, otherwise, go to loginscreen
//                        if (true) {
//                            //clear this session
//
//                            //DELETE FROM ROOMDATABASE
//
//                            //this task just retrievs SyncMyAccountResult, Notification
//                            /// showProgress(true);
//                            new DeleteThisStudentData(getActivity()).execute();
//                            NotificationTopDisplay.giveContext(getActivity());
//
//                            NotificationTopDisplay.GetAuthenticatedUser.cancelNotificationDisplay();
//                            //you should not use this, for it will delete all the data we have
//                            //PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();
//                        } else {
//                            try {
//                                throw new Exception("For you to reach this screen, you must have logged in Exception");
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                });
//                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                alert.show();
                                              //THIS SECTION WAS COMMENTED BEFORE


//            }
//        });

//        //SECURITY
                                              // TODO: 2020-04-26 Before is fingerpint pattern and pin security features that were removed due to errors
//        pin_switch = getActivity().findViewById(R.id.pin_switch);
//        pattern_switch = getActivity().findViewById(R.id.pattern_switch);
//        if(Paper.book().contains("PatternString")){
//            if(Paper.book().read("PatternString").toString().isEmpty()){
//                pattern_switch.setChecked(false);
//            }else{
//                pattern_switch.setChecked(true);
//            }
//        }else{
//            pattern_switch.setChecked(false);
//        }
//
//        LinearLayout pattern_view_layout = getActivity().findViewById(R.id.pattern_view_layout);
//        if(pattern_switch.isChecked()){
//
//            pattern_view_layout.setVisibility(View.VISIBLE);
//            // UtilityFunctions.expand(pattern_view_layout);
//        }else{
//            // UtilityFunctions.collapse(pattern_view_layout);
//            pattern_view_layout.setVisibility(View.GONE);
//        }


//        if(Paper.book().contains("UseWithFingerprint")){
//            if(Paper.book().read("UseWithFingerprint")){
//                fingerprint_switch.setChecked(true);
//            }else{
//                fingerprint_switch.setChecked(false);
//            }
//        }else{
//            fingerprint_switch.setChecked(false);
//        }
//
//        fingerprint_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if(isChecked){
//                  //  EasyLock.setPassword(getActivity(), SettingsFragment.class);
//                    Paper.book().write("UseWithFingerprint",true);
//                }else{
//                    Paper.book().write("UseWithFingerprint", false);
//                }
//            }
//        });


//        registerPin();
//        pin_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                LinearLayout pin_view = getActivity().findViewById(R.id.pin_view_layout);
//                if(isChecked){
//                   // EasyLock.setPassword(getActivity(), SettingsFragment.class);
//                    pin_view.setVisibility(View.VISIBLE);
//                }else{
//                    pin_view.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        registerPattern();
//        pattern_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//               final LinearLayout pattern_view_layout = getActivity().findViewById(R.id.pattern_view_layout);
//                if(isChecked){
//
//                    pattern_view_layout.setVisibility(View.VISIBLE);
//                    // UtilityFunctions.expand(pattern_view_layout);
//                }else{
//                    // UtilityFunctions.collapse(pattern_view_layout);
//                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//                    alert.setTitle("Confirmation");
//                    alert.setMessage("Are you sure you want to disable the security pattern used for access?");
//                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            Paper.book().delete("PatternString");
//                            pattern_view_layout.setVisibility(View.GONE);
//                            dialogInterface.dismiss();
//                            fingerprint_switch.setChecked(false);
//                        }
//                    });
//                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                            pattern_switch.setChecked(true);
//                        }
//                    });
//                    alert.show();
//
//
//                }
//            }
//        });


                      final Context context = SettingsFragment.this.getActivity();

                      ///MY ACCOUNT DETAILS
                      AsyncTask asyncTask = new AsyncTask() {


                          @Override
                          protected void onPostExecute(Object o) {

                              Map<String, String> map = (Map<String, String>) o;


                              //TODO: FOR SIBLINGS FIND OUT WHAT SIMON DOES TO GET THE SIBLINGS PASSWORD AND USERNAME
                              StringBuilder hashedPassword = new StringBuilder();

                              if (map.get("Password") != null) {
                                  password = map.get("Password");
                                  char[] array = map.get("Password").toCharArray();
                                  Log.d(getActivity().getPackageName().toUpperCase(), String.valueOf(array == null));
                                  for (int i = 0; i < (array != null ? array.length : 0); i++) {

                                      hashedPassword.append("*");
                                  }
                                  ((TextView) getActivity().findViewById(R.id.username_text)).setText(map.get("UserName").isEmpty() ? "-" : map.get("UserName"));
                                  ((TextView) getActivity().findViewById(R.id.password_text)).setText(hashedPassword.toString());
                                  ((TextView) getActivity().findViewById(R.id.schoolCode_text)).setText(map.get("SchoolCode"));
                              } else {
                                  ((TextView) getActivity().findViewById(R.id.username_text)).setText("__");
                                  ((TextView) getActivity().findViewById(R.id.password_text)).setText("__");
                                  ((TextView) getActivity().findViewById(R.id.schoolCode_text)).setText("__");

                                  getActivity().findViewById(R.id.account_layout_header).setVisibility(View.VISIBLE);
                                  getActivity().findViewById(R.id.myaccountCollapse).setVisibility(View.VISIBLE);
                              }

                              super.onPostExecute(o);
                          }

                          @Override
                          protected Object doInBackground(Object[] objects) {
                              return UtilityFunctions.getMyAccountCredentials(context);
                          }
                      };
                      asyncTask.execute();


                }
                           });


        super.onViewCreated(view, savedInstanceState);
    }







    //private PinLockView mPinLockView;
    private TextView pin_text;



 //   private void registerPin(){

//        pin_text = getActivity().findViewById(R.id.pin_text);
//        mPinLockView =  getActivity().findViewById(R.id.pin_lock_view);
//        mPinLockView.setPinLength(4);
//      // mPinLockView.setTextColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
//        mPinLockView.setTextSize(30);
//        mPinLockView.setButtonSize(60);
//        mPinLockView.setDeleteButtonSize(60);
//        mPinLockView.setShowDeleteButton(true);
//       mPinLockView.setDeleteButtonPressedColor(ResourceUtils.getColor(getActivity(), R.color.colorPrimary));
//
//
//
//       // mPinLockView.attachIndicatorDots(mIndicatorDots);
//        mPinLockView.setPinLockListener(new PinLockListener() {
//            @Override
//            public void onComplete(String pin) {
//                Log.d(getActivity().getPackageName().toUpperCase(), "Pin complete: " + pin);
//            }
//
//            @Override
//            public void onEmpty() {
//               Log.d(getActivity().getPackageName().toUpperCase(), "Pin empty");
//            }
//
//            @Override
//            public void onPinChange(int pinLength, String intermediatePin) {
//                if(pinLength == mPinLockView.getPinLength()){
//
//                }
//                pin_text.setText(intermediatePin);
//               // Log.d(getActivity().getPackageName().toUpperCase(), "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
//            }
//        });
//        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
//        //mPinLockView.enableLayoutShuffling();
//
//        mPinLockView.setPinLength(4);
//        mPinLockView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));


   // }

//    private PatternLockView mPatternLockView;
//
//    private CardView clearBtn, nextBtn;
//    private TextView nextBtnText,draw_pattern_info,clearBtnText;
//
//    private String patternString = "";
//
//    private void enableNextBtn(boolean enable){
//        if(enable){
//            nextBtn.setEnabled(enable);
//            getActivity().findViewById(R.id.next_btn_layout).setBackgroundColor(ResourceUtils.getColor(getActivity(), R.color.colorPrimary));
//
//        }else{
//            nextBtn.setEnabled(false);
//            getActivity().findViewById(R.id.next_btn_layout).setBackgroundColor(ResourceUtils.getColor(getActivity(), android.R.color.darker_gray));
//
//        }
//    }
//    private void enableClearBtn(boolean enable){
//        if(enable){
//            clearBtn.setEnabled(enable);
//            getActivity().findViewById(R.id.clear_btn_layout).setBackgroundColor(ResourceUtils.getColor(getActivity(), R.color.colorPrimary));
//
//        }else{
//            clearBtn.setEnabled(false);
//            getActivity().findViewById(R.id.clear_btn_layout).setBackgroundColor(ResourceUtils.getColor(getActivity(), android.R.color.darker_gray));
//
//        }
//    }





//    //TODO: CHECK IF THE PAPER HAS ANY KEYS SAVED, IF NOT INPUT THE PREVIOUS PATTERN TO CHANGE PATTERN
//    private void registerPattern(){
//
//
//        clearBtn = getActivity().findViewById(R.id.clear_btn);
//        enableClearBtn(false);
//        nextBtn = getActivity().findViewById(R.id.next_btn);
//        enableNextBtn(false);
//        nextBtnText = getActivity().findViewById(R.id.next_btn_text);
//        clearBtnText = getActivity().findViewById(R.id.clear_btn_text);
//
//
//
//        draw_pattern_info = getActivity().findViewById(R.id.draw_pattern_info);
//
//        mPatternLockView =  getView().findViewById(R.id.patter_lock_view);
//
//        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(getActivity(), R.dimen.pattern_lock_path_width));
//
//        mPatternLockView.setAspectRatioEnabled(true);
//        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
//        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
//        mPatternLockView.setDotAnimationDuration(150);
//        mPatternLockView.setPathEndAnimationDuration(100);
//        mPatternLockView.setNormalStateColor(ResourceUtils.getColor(getActivity(), R.color.colorPrimary));
//        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
//        //mPatternLockView.setWrongStateColor(ResourceUtils.getColor(getActivity(), R.color.red));
//        mPatternLockView.setInStealthMode(false);
//        mPatternLockView.setTactileFeedbackEnabled(true);
//        mPatternLockView.setInputEnabled(true);
//        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
//            @Override
//            public void onStarted() {
//
//                Log.d(getActivity().getPackageName().toUpperCase(), "Pattern drawing started");
//            }
//
//            @Override
//            public void onProgress(List<PatternLockView.Dot> progressPattern) {
//                Log.d(getActivity().getPackageName().toUpperCase(), "Pattern progress: " +
//                        PatternLockUtils.patternToString(mPatternLockView, progressPattern));
//            }
//
//
//
//            @Override
//            public void onComplete(List<PatternLockView.Dot> pattern) {
//                Log.d(getActivity().getPackageName().toUpperCase(), "Pattern complete: " +
//                        PatternLockUtils.patternToString(mPatternLockView, pattern));
//
//
//
//
//
//                draw_pattern_info.setText("");
//                draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
//
//                if(nextBtnText.getText().equals("Next")){
//                    draw_pattern_info.setText("Pattern recorded");
//                    enableNextBtn(true);
//                    enableClearBtn(true);
//                    patternString = PatternLockUtils.patternToString(mPatternLockView,pattern);
//
//                }else if(nextBtnText.getText().equals("Confirm")){
//                    //TODO: register the pattern, Secure storage will be implemented later
//
//                    enableNextBtn(true);
//
//
//
//                        if(PatternLockUtils.patternToString(mPatternLockView, pattern).equals(patternString)){
//
//
//                        }else{
//                            clearBtnText.setText("Cancel");
//                            draw_pattern_info.setText("Wrong pattern");
//                            draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.red));
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mPatternLockView.clearPattern();
//                                }
//                            }, 2000);
//
//                        }
//                }
//
//
//            }
//
//            @Override
//            public void onCleared() {
//                Log.d(getActivity().getPackageName().toUpperCase(), "Pattern has been cleared");
//            }
//        });
//
//
//
//        clearBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if(clearBtnText.getText().equals("Cancel")){
//                    Paper.book().delete("PatternString");
//                    draw_pattern_info.setText("Draw an unlock pattern");
//                    draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
//                    nextBtnText.setText("Next");
//                    clearBtnText.setText("Clear");
//                    enableClearBtn(false);
//                    enableNextBtn(false);
//                    patternString = "";
//                }else if(clearBtnText.getText().equals("Clear")){
//                    draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.homeText));
//                    draw_pattern_info.setText("Draw an unlock pattern");
//                    mPatternLockView.clearPattern();
//                }
//            }
//        });
//
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(nextBtnText.getText().equals("Next")){
//
//                    draw_pattern_info.setText("Draw pattern again to confirm");
//                    nextBtnText.setText("Confirm");
//                    enableNextBtn(false);
//
//                    //Save
//                    Paper.book().write("PatternString", patternString);
//
//                    //Clear
//                    mPatternLockView.clearPattern();
//
//                }else if(nextBtnText.getText().equals("Confirm")){
//                    //TODO: register the pattern, Secure storage will be implemented later
//
//                    if(Paper.book().contains("PatternString")){
//                        if(Paper.book().read("PatternString").equals(patternString)){
//                            Paper.book().write("PatternString",patternString);
//
//                            draw_pattern_info.setText("Pattern is a match");
//                            draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.transType_green));
//                            mPatternLockView.setInputEnabled(false);
//
//
//                            Snackbar.make(getActivity().findViewById(R.id.settings_layout_id),"Successfully registered your pattern", Snackbar.LENGTH_SHORT).show();
//
//                            PatternPoint[] PpArray = UtilityFunctions.convertStringToCharThenNumberDigits(patternString);
//                            Paper.book().write("PatternPointArray", PpArray);
//
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //TODO: Exit app then start...
//                                    (getActivity()).finish();
//                                    Intent intent = new Intent(getActivity(), SplashActivity.class);
//                                    startActivity(intent);
//                                }
//                            }, 2000);
//                        }else{
//                            draw_pattern_info.setText("Wrong pattern");
//                            draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.red));
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mPatternLockView.clearPattern();
//                                }
//                            }, 2000);
//
//                        }
//                    }else{
//                        draw_pattern_info.setText("Wrong pattern");
//                        draw_pattern_info.setTextColor(ResourceUtils.getColor(getActivity(), R.color.red));
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                mPatternLockView.clearPattern();
//                            }
//                        }, 2000);
//                    }
//
//                }
//            }
//        });
//
//
//   }


    public static SettingsFragment newInstance(String param1, String param2, FragmentManager fragmentManager) {
        SettingsFragment.fragmentManager = fragmentManager;

        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    private void sendRequestForChangeOfPassword(View view, final String overridePassword){

        final ProgressBar pb_login_progress = view.findViewById(R.id.pb_change_passord_progress);

        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                pb_login_progress.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {

                List<String> list = (List<String>) o;

                if (list.size() == 4) {

                    String studentID = list.get(0);
                    String appcode = list.get(1);
                    String organization = list.get(2);
                    String schoolID = list.get(3);

                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

                    StudentChangePasswordRequest studentChangePassword = new StudentChangePasswordRequest(studentID,organization,overridePassword,schoolID, appcode);


                    Call<Boolean> userCall = apiInterface.requestStudentChangePassword(studentChangePassword);
                    userCall.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, final Response<Boolean> response) {
                            if (response.code() == 200) {
                                pb_login_progress.setVisibility(View.GONE);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                    alert.setCancelable(false);
                                    alert.setTitle("Success");
                                    alert.setMessage("Password changed successfully.");
                                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    alert.show();
                            }else if(response.code() == 500){
                                pb_login_progress.setVisibility(View.GONE);
                                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                                alert.setCancelable(false);
                                alert.setTitle("Failure");
                                alert.setMessage("Password change operation unsuccessful.");
                                alert.setPositiveButton("try again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                alert.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            pb_login_progress.setVisibility(View.GONE);
                        }
                    });
                }


                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                    ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());

                    List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                    List<String> list = new ArrayList<>();
                    if(authenticateUserResponse.size() > 0){
                        list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
                        list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
                        list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
                        list.add(db.getPortalSiblingsDao().getSchoolIDFromPortalSibling(authenticateUserResponse.get(0).getUserID()));//3
                    }


                    return list;
                }
        };
        asyncTask.execute();


    }

    public void sendFCMDeviceToken(View view){

        final ProgressDialog alertprogress = new ProgressDialog(getActivity());
        alertprogress.setMessage("Please wait... sending fcm token.");
        alertprogress.setCancelable(false);


        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                alertprogress.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
           String refreshToken =  FirebaseInstanceId.getInstance().getToken();
                Log.d("MyFirebaseMessagingS", "Get: Refresh Token: " + refreshToken);
//                if(refreshToken != null) {
//                    if(!refreshToken.isEmpty()) {
//                        Log.d("MyFirebaseMessagingS", "Get: Refresh Token: " + refreshToken);
//
//                        List<String> list = (List<String>) o;
//
//                        if (list.size() == 4) {
//
//                            String studentID = list.get(0);
//                            String appcode = list.get(1);
//                            String organization = list.get(2);
//                            String schoolID = list.get(3);
//
//                            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                            DeviceFCMToken request = new DeviceFCMToken(studentID, schoolID, organization, refreshToken, appcode);
//                            Call<ResponseBody> userCall = apiInterface.requestDeviceFCMToken(request);
//                            userCall.enqueue(new Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
//                                    alertprogress.cancel();
//                                    if (response.code() == 200) {
//
//                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                                        alert.setCancelable(false);
//                                        alert.setTitle("Success");
//                                        alert.setMessage("Device FCM Token submitted successfully.");
//                                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.cancel();
//                                            }
//                                        });
//                                        alert.show();
//                                    } else {
//                                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
//                                        alert.setCancelable(false);
//                                        alert.setTitle("Failure");
//                                        alert.setMessage("Device FCM Token not submitted unsuccessful.");
//                                        alert.setPositiveButton("try again", new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(DialogInterface dialogInterface, int i) {
//                                                dialogInterface.cancel();
//                                            }
//                                        });
//                                        alert.show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                    alertprogress.cancel();
//                                }
//                            });
//                        }
//
//                    }else{
//                        Toast.makeText(getActivity(), "Token not available", Toast.LENGTH_SHORT).show();
//                    }
//
//                }else{
//                    Toast.makeText(getActivity(), "Token not available", Toast.LENGTH_SHORT).show();
//                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {

                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());

                List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                List<String> list = new ArrayList<>();
                if(authenticateUserResponse.size() > 0){
                    list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
                    list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
                    list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
                    list.add(db.getPortalSiblingsDao().getSchoolIDFromPortalSibling(authenticateUserResponse.get(0).getUserID()));//3
                }


                return list;
            }
        };
        asyncTask.execute();

    }


    public class DeviceFCMToken{
        private String StudentID;
        private String SchoolID;
        private String OrganizationID;
        private String DeviceFCMToken;
        private String AppCode;

        public DeviceFCMToken(String studentID, String schoolID, String organizationID, String deviceFCMToken, String appCode) {
            StudentID = studentID;
            SchoolID = schoolID;
            OrganizationID = organizationID;
            DeviceFCMToken = deviceFCMToken;
            AppCode = appCode;
        }

        public String getStudentID() {
            return StudentID;
        }

        public String getSchoolID() {
            return SchoolID;
        }

        public String getOrganizationID() {
            return OrganizationID;
        }

        public String getDeviceFCMToken() {
            return DeviceFCMToken;
        }

        public String getAppCode() {
            return AppCode;
        }

        @Override
        public String toString() {
            return "DeviceFCMToken{" +
                    "StudentID='" + StudentID + '\'' +
                    ", SchoolID='" + SchoolID + '\'' +
                    ", OrganizationID='" + OrganizationID + '\'' +
                    ", DeviceFCMToken='" + DeviceFCMToken + '\'' +
                    ", AppCode='" + AppCode + '\'' +
                    '}';
        }
    }

}
