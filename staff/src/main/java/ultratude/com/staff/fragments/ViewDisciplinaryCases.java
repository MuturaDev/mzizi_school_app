package ultratude.com.staff.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.adapters.ViewDisciplinaryCasesAdapter;
import ultratude.com.staff.constantclasses.DisplayContants;
import ultratude.com.staff.R;
import ultratude.com.staff.spinnerdao.TermSpinnerDAO;
import ultratude.com.staff.spinnerdao.YearSpinnerDAO;
import ultratude.com.staff.spinnermodel.TermSpinner;
import ultratude.com.staff.spinnermodel.YearSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.ResponseModels.StudentDisciplinaryCase;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;
import ultratude.com.staff.webservice.RequestModels.PortalDisciplinaryCasesRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;

/**
 * Created by James on 28/04/2019.
 */

public class ViewDisciplinaryCases extends Fragment {

    public static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recycler_disciplinary_Cases_ID;


    private LinearLayout pb_loading_txt_manageDiscipline_progress;


    public TextView label_confirmation_message_ID;

    private TextView txt_viewing_results_of_ID;

    private YearSpinner yearSelected = null;

    private TermSpinner termSelected = null;

    private Spinner sp_year_ID, sp_term_ID;
    private LinearLayout pb_manageDiscipline_progress_layout;

    private TextView no_content_to_display_ID;


    //Spinner
    public void showSpinnerProgress(boolean show){
        if(show){
            pb_manageDiscipline_progress_layout.setVisibility(View.VISIBLE);
            sp_year_ID.setVisibility(View.GONE);
        }else{
            pb_manageDiscipline_progress_layout.setVisibility(View.GONE);
            sp_year_ID.setVisibility(View.VISIBLE);
        }
    }

    //NO INTENET CONNECTION
    private RelativeLayout no_internet_connection_layout_ID;
    private LinearLayout inner_view_disciplinary_cases_layout_ID;
    private Button btn_tryagain_ID;
    private ProgressBar pb_manageDiscipline_progress;

    private StudentRequest studentRequest;




    public ViewDisciplinaryCases() {


    }

    public static ViewExamHistory newInstance(String param1, String param2, FragmentManager fragmentManager) {
        ViewDisciplinaryCases.fragmentManager = fragmentManager;

        ViewExamHistory fragment = new ViewExamHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.disciplinary_cases_layout, container, false);
    }


    @Override
    //if this code is put in onStart, the layout items will double
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        //NO INTERNET CONNECTION
        studentRequest = (StudentRequest) getArguments().getSerializable(DisplayContants.STUDENT_REQUEST);

        no_internet_connection_layout_ID = getActivity().findViewById(R.id.no_internet_connection_layout_ID);

        inner_view_disciplinary_cases_layout_ID = getActivity().findViewById(R.id.inner_view_disciplinary_cases_layout_ID);

        btn_tryagain_ID = getActivity().findViewById(R.id.btn_tryagain_ID);

        no_content_to_display_ID = getActivity().findViewById(R.id.no_content_to_display_ID);

        pb_manageDiscipline_progress = getActivity().findViewById(R.id.pb_manageDiscipline_progress);

        //OTHER

        pb_manageDiscipline_progress_layout = getActivity().findViewById(R.id.pb_manageDiscipline_progress_layout);

        recycler_disciplinary_Cases_ID = this.getActivity().findViewById(R.id.recycler_disciplinary_Cases_ID);

        pb_loading_txt_manageDiscipline_progress = this.getActivity().findViewById(R.id.pb_loading_txt_manageDiscipline_progress);

        label_confirmation_message_ID = this.getActivity().findViewById(R.id.label_confirmation_message_ID);
        sp_year_ID = this.getActivity().findViewById(R.id.sp_year_ID);
        sp_term_ID = this.getActivity().findViewById(R.id.sp_term_ID);

        txt_viewing_results_of_ID = this.getActivity().findViewById(R.id.txt_viewing_results_of_ID);
        StringBuilder b = new StringBuilder();
        b.append("Viewing disciplinary cases of ");
        b.append(new StudentDAO(this.getActivity()).getFullnamesForThisStudentID(String.valueOf(studentRequest.getStudentID())));
        b.append(" ");
        b.append(new StudentDAO(this.getActivity()).getRegistrationNumberForThisStudentID(String.valueOf(studentRequest.getStudentID())));
        txt_viewing_results_of_ID.setText(b.toString());


       new YearSpinnerDAO(getActivity()).loadDataForYearSpinner(studentRequest, sp_year_ID,this);
        sp_year_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){

                    case 0:
                        //break;
                        yearSelected = null;


                        break;
                    default:

                        //break;
                        yearSelected = (YearSpinner) parent.getSelectedItem();

                            ArrayList<TermSpinner> termSpinnerList = new TermSpinnerDAO(getActivity()).loadDataForTermSpinner(ViewDisciplinaryCases.this);
                            ArrayAdapter<TermSpinner> termSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, termSpinnerList);
                            sp_term_ID.setAdapter(termSpinnerArrayAdapter);

                        break;
                    // Toast.makeText(ManageDisciplineSreen.this, classNameValueSelected, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yearSelected = null;

            }
        });


        sp_term_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){

                    case 0:
                        //break;
                        termSelected = null;

                        break;
                    default:
                        //break;
                        termSelected = (TermSpinner) parent.getSelectedItem();

                        if(checkYear(yearSelected) && checkTerm(termSelected)){
                            showLabelConfirmationMessage(false);
                            sendRequest();


                        }




//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                termSelected = null;

            }
        });



        super.onActivityCreated(savedInstanceState);
    }






    private void sendRequest(){

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        final ConnectivityManager cm = (ConnectivityManager) this.getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

            sendRequestForStudentDisciplinaryCases(getActivity(), apiInterface);

        }else{


//            showNoInternetConnectionLayout(true);
//            btn_tryagain_ID.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    reSendRequest();
//                }
//            });

            AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(this.getActivity());
            LayoutInflater inflater = this.getActivity().getLayoutInflater();
            View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
            accessDeniedAlert.setView(view);
            accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    //sendRequest();
                    //attendance.getActivity().btn_attendance_ID.performClick();
                    //new StudentAttendanceDAO(attendance.getActivity()).loadGridviewWithData(attendance);
                    sendRequest();
                }
            });
            accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ViewDisciplinaryCases.this.getActivity().onBackPressed();
                }
            });
            accessDeniedAlert.show();


        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showLabelConfirmationMessage(final boolean show){
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            label_confirmation_message_ID.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            label_confirmation_message_ID.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    label_confirmation_message_ID.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
                }
            });



        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            label_confirmation_message_ID.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        }
    }




    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            recycler_disciplinary_Cases_ID.setVisibility(show ? View.GONE : View.VISIBLE);
            recycler_disciplinary_Cases_ID.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recycler_disciplinary_Cases_ID.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            pb_loading_txt_manageDiscipline_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            recycler_disciplinary_Cases_ID.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onPause() {

        showProgress(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {

        showProgress(false);

        super.onDestroy();
    }


    private void sendRequestForStudentDisciplinaryCases(final Context mContext, APIInterface apiInterface){

        showProgress(true);
        no_content_to_display_ID.setVisibility(View.INVISIBLE);

        PortalDisciplinaryCasesRequest portalDisciplinaryCasesRequest = new PortalDisciplinaryCasesRequest(
                studentRequest.getStudentID(),
                String.valueOf(yearSelected.getYearID()),
                String.valueOf(termSelected.getTermID()),
                studentRequest.getOrganizationID(),
                studentRequest.getAppcode()
        );


        Call<List<StudentDisciplinaryCase>> userCall = apiInterface.getStudentDisciplinaryCases(portalDisciplinaryCasesRequest);
        userCall.enqueue(new Callback<List<StudentDisciplinaryCase>>() {
                             @Override
                             public void onResponse(Call<List<StudentDisciplinaryCase>> call, Response<List<StudentDisciplinaryCase>> response) {
                                 if(response.isSuccessful()){

                                     showProgress(false);

                                     if(response.code() == 200){

                                         recycler_disciplinary_Cases_ID.setHasFixedSize(true);
                                         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                         recycler_disciplinary_Cases_ID.setLayoutManager(layoutManager);

                                         ViewDisciplinaryCasesAdapter adapter = new ViewDisciplinaryCasesAdapter(getActivity(), response.body());
                                         recycler_disciplinary_Cases_ID.setAdapter(adapter);
                                         //display the recycler
                                         //cancel the progress

                                     }else{
                                         no_content_to_display_ID.setVisibility(View.VISIBLE);
                                     }


                                 }else{

                                     no_content_to_display_ID.setVisibility(View.VISIBLE);
                                     showProgress(false);
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<StudentDisciplinaryCase>> call, Throwable t) {
                                showProgress(false);
                                 AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(ViewDisciplinaryCases.this.getActivity());
                                 LayoutInflater inflater = ViewDisciplinaryCases.this.getActivity().getLayoutInflater();
                                 View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                                 accessDeniedAlert.setView(view);
                                 accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                         sendRequest();
                                     }
                                 });
                                 accessDeniedAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                         // onBackPressed();
                                         ViewDisciplinaryCases.this.getActivity().onBackPressed();
                                     }
                                 });
                                 accessDeniedAlert.show();
                                 //showBtnProgress(false);
                                // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();

                             }
                         }
        );
    }



    private boolean checkTerm(TermSpinner term){
        if(term == null){
            label_confirmation_message_ID.setText("Please select Term");
            showLabelConfirmationMessage(true);
            return false;
        }

        return true;
    }

    private boolean checkYear(YearSpinner year){
        if(year == null){
            label_confirmation_message_ID.setText("Please select Year");
            showLabelConfirmationMessage(true);
            return false;
        }

        return true;
    }


}
