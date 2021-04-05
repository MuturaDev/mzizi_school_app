package ultratude.com.staff.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.activities.StudentEnquiryFragmentsViewer;
import ultratude.com.staff.constantclasses.DisplayContants;
import ultratude.com.staff.fromapp_exammeanscoreandhelperclasses.GetXInstanceFromRoomDB;
import ultratude.com.staff.fromapp_exammeanscoreandhelperclasses.MeanScoreRecycleViewAdapterTest;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.DataAccessObjects.ExamHistoryDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.ResponseModels.ExamHistory;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;


/**
 * Created by James on 02/07/2018.
 */

public class ViewExamHistory extends Fragment {

    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public LinearLayoutManager linearLayoutManager;

    public RecyclerView recyclerView;
    public LinearLayout meanscore_layout_noContent;
    public LinearLayout layoutabove;

    private TextView txt_viewing_results_of_ID;

    //public MeanScoreRecycleViewAdapter meanScoreRecycleViewAdapter;
    public MeanScoreRecycleViewAdapterTest meanScoreRecycleViewAdapterTest;

    public LinearLayout meanscore_progress;

    public   Spinner year_spinner;

    private static boolean is_first_launch = false;
    private static int countAssignements = 1;

    //NO INTENET CONNECTION
    private RelativeLayout no_internet_connection_layout_ID;
    private LinearLayout inner_meanscore_recyler_layout2;
    private Button btn_tryagain_ID;
    private ProgressBar pb_manageDiscipline_progress;

    private StudentRequest studentRequest;

//    public void showNoInternetConnectionLayout(boolean show){
//
//        if (show){
//            no_internet_connection_layout_ID.setVisibility(View.VISIBLE);
//            inner_meanscore_recyler_layout2.setVisibility(View.GONE);
//        }else{
//            no_internet_connection_layout_ID.setVisibility(View.GONE);
//            inner_meanscore_recyler_layout2.setVisibility(View.VISIBLE);
//        }
//
//
//    }
//    public void showBtnProgress(boolean show){
//        if(show){
//            pb_manageDiscipline_progress.setVisibility(View.VISIBLE);
//            btn_tryagain_ID.setVisibility(View.GONE);
//        }else{
//            pb_manageDiscipline_progress.setVisibility(View.GONE);
//            btn_tryagain_ID.setVisibility(View.VISIBLE);
//        }
//    }

    public ViewExamHistory(){


    }

    public static ViewExamHistory newInstance(String param1, String param2, FragmentManager fragmentManager) {
        ViewExamHistory.fragmentManager = fragmentManager;

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
            is_first_launch = false;
        return inflater.inflate(R.layout.meanscore_recycler_layout2, container, false);
    }



    @Override
    //if this code is put in onStart, the layout items will double
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        //NO INTE
        studentRequest = (StudentRequest) getArguments().getSerializable(DisplayContants.STUDENT_REQUEST);

        no_internet_connection_layout_ID = getActivity().findViewById(R.id.no_internet_connection_layout_ID);

        inner_meanscore_recyler_layout2 = getActivity().findViewById(R.id.inner_meanscore_recyler_layout2);

        btn_tryagain_ID = getActivity().findViewById(R.id.btn_tryagain_ID);

        pb_manageDiscipline_progress = getActivity().findViewById(R.id.pb_manageDiscipline_progress);
        //should also be put in
        //getActivity().setTitle("Current Mean Score");

        // String message = ( getArguments() != null) ? getArguments().getString("message") : "Has no message to display!!!";

        //Toast.makeText(getActivity(),message , Toast.LENGTH_LONG).show();
        txt_viewing_results_of_ID = this.getActivity().findViewById(R.id.txt_viewing_results_of_ID);
        StringBuilder b = new StringBuilder();
        b.append("Viewing exam result of ");
        b.append(new StudentDAO(this.getActivity()).getFullnamesForThisStudentID(String.valueOf(studentRequest.getStudentID())));
        b.append(" ");
        b.append(new StudentDAO(this.getActivity()).getRegistrationNumberForThisStudentID(String.valueOf(studentRequest.getStudentID())));
        txt_viewing_results_of_ID.setText(b.toString());



        year_spinner = getActivity().findViewById(R.id.year_spinner_ID);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{String.valueOf(cal.get(Calendar.YEAR))});
        year_spinner.setAdapter(arrayAdapter);


        meanscore_progress = getActivity().findViewById(R.id.meanscore_progress);

       meanscore_layout_noContent = getActivity().findViewById(R.id.meanscore_layout_noContent);
        layoutabove = getActivity().findViewById(R.id.layoutabove);

        // tblayout =  getActivity().findViewById(R.id.upcoming_events_table);



        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = getActivity().findViewById(R.id.meanscore_recycle);
        recyclerView.setLayoutManager(linearLayoutManager);


        if(countAssignements<=1){
            is_first_launch = getActivity().getIntent().getBooleanExtra("isLog_In", false);
        }else{
            is_first_launch = false;
        }

        countAssignements++;


        //HERE WE SHALL SEND A REQUEST TO GET THE EXAM DATA AND USE IT
        sendRequest();


        super.onActivityCreated(savedInstanceState);
    }


    private void sendRequest(){
       final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        final ConnectivityManager cm = (ConnectivityManager) this.getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {
            showProgress(true);
            sendRequestForExamResults(getActivity(), apiInterface,studentRequest);
        }else{

            AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(ViewExamHistory.this.getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
            accessDeniedAlert.setView(view);
            accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    sendRequest();
                   // btn_exam_history_ID.performClick();

                }
            });
            accessDeniedAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ViewExamHistory.this.getActivity().onBackPressed();
                }
            });
            accessDeniedAlert.show();
        }
    }

    private void sendRequestForExamResults(final Context mContext, APIInterface apiInterface,StudentRequest studentEnquiry){

       // sendRequestForExamResults(StudentEnquiry.this, apiInterface,studentRequest);
        //DUMMY DATA
        //no need for the organization id
//        StudentRequest dummyStudentEnquiry = new StudentRequest(
//                "24548","1008",""
//        );

        /*(studentEnquiry);*///(
        Call<List<ExamHistory>> userCall = apiInterface.getExamResultsHistory(studentEnquiry);//should be studentEnquiry
        userCall.enqueue(new Callback<List<ExamHistory>>() {
            @Override
            public void onResponse(Call<List<ExamHistory>> call, final Response<List<ExamHistory>> response) {

                AsyncTask asyncTask2 = new AsyncTask() {
                    @Override
                    protected Long doInBackground(Object[] params) {
                        new ExamHistoryDAO(getActivity()).deleteExamResultsHistory();
                        return null;
                    }
                };
                asyncTask2.execute();
                if(response.isSuccessful()){

                   if(response.code() == 200){
                       AsyncTask asyncTask = new AsyncTask() {
                           @Override
                           protected Long doInBackground(Object[] params) {

                               long id =  new ExamHistoryDAO(getActivity()).saveExamResultHistory(response.body());
                               return id;
                           }

                           @Override
                           protected void onPostExecute(Object o) {

                                   if(response.body() != null){
                                       if(response.body().size() == ((Long)o)){

                                           new GetXInstanceFromRoomDB( (StudentEnquiryFragmentsViewer) getActivity(), ViewExamHistory.this).execute("");

                                       }
                                   }

                               showProgress(false);

                               super.onPostExecute(o);
                           }
                       };
                       asyncTask.execute(response.body());
                   }else{
                       Log.d(mContext.getPackageName().toUpperCase(), "It should view");
                       meanscore_layout_noContent.setVisibility(View.VISIBLE);
                       showProgress(false);
                   }

                }


            }

            @Override
            public void onFailure(Call<List<ExamHistory>> call, Throwable t) {
                showProgress(false);
                AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(ViewExamHistory.this.getActivity());
                LayoutInflater inflater = ViewExamHistory.this.getActivity().getLayoutInflater();
                View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                accessDeniedAlert.setView(view);
                accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sendRequest();
                    }
                });
                accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ViewExamHistory.this.getActivity().onBackPressed();
                    }
                });
                accessDeniedAlert.show();
                call.cancel();
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            meanscore_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            meanscore_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    meanscore_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            meanscore_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
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


}
