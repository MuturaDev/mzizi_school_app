package ultratude.com.staff.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import ultratude.com.staff.constantclasses.DisplayContants;
import ultratude.com.staff.R;
import ultratude.com.staff.spinnerdao.TermSpinnerDAO;
import ultratude.com.staff.spinnerdao.YearSpinnerDAO;
import ultratude.com.staff.spinnermodel.TermSpinner;
import ultratude.com.staff.spinnermodel.YearSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.StudentAttendanceDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;


public class ViewClassAttendance extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static FragmentManager fragmentManager;
    public StudentRequest studentRequest;

    public YearSpinner yearSelected = null;
    public TermSpinner termSelected = null;

    private TextView txt_viewing_results_of_ID;
    private LinearLayout pb_loading_txt_manageDiscipline_progress;
    public GridView grid_view_class_attendance_ID;

    //NO INTERNET
    private RelativeLayout no_internet_connection_layout_ID;
    private LinearLayout inner_view_attendance_layout_ID;


    public Button btn_tryagain_ID;
    private ProgressBar pb_manageDiscipline_progress;
    public void showBtnProgress(boolean show){
        if(show){
            pb_manageDiscipline_progress.setVisibility(View.VISIBLE);
            btn_tryagain_ID.setVisibility(View.GONE);
        }else{
            pb_manageDiscipline_progress.setVisibility(View.GONE);
            btn_tryagain_ID.setVisibility(View.VISIBLE);
        }
    }

    public TextView label_confirmation_message_ID;
    private Spinner sp_term_ID;

    private Spinner sp_year_ID;
    private LinearLayout pb_manageDiscipline_progress_layout;
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

    public TextView no_content_to_display_ID;


    private LinearLayout sp_group_layout;
    private RelativeLayout key_layout;
    // showLabelConfirmationMessage(false);
    //grid_view_class_attendance_ID

    public ViewClassAttendance() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewClassAttendance.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewClassAttendance newInstance(String param1, String param2) {
        ViewClassAttendance fragment = new ViewClassAttendance();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_attendance_layout_staff, container, false);
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.maximize_minimize, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getTitle().equals("maximize")){
            // item.setTooltipText("Minimize");

                //gone
                if(grid_view_class_attendance_ID.getVisibility() == View.VISIBLE){
                    item.setIcon(getResources().getDrawable(R.drawable.minimize_icon));
                    item.setTitle("minimize");

                    sp_group_layout.setVisibility(View.GONE);
                    key_layout.setVisibility(View.GONE);


                }

        }else if(item.getTitle().equals("minimize")) {

            //gone
            if(grid_view_class_attendance_ID.getVisibility() == View.VISIBLE){
                item.setIcon(getResources().getDrawable(R.drawable.maximize_icon));
                item.setTitle("maximize");

                sp_group_layout.setVisibility(View.VISIBLE);
                key_layout.setVisibility(View.VISIBLE);


            }

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        //NO INTERNET CONNECTION
        studentRequest = (StudentRequest) getArguments().getSerializable(DisplayContants.STUDENT_REQUEST);

        no_internet_connection_layout_ID = getActivity().findViewById(R.id.no_internet_connection_layout_ID);

        no_content_to_display_ID = getActivity().findViewById(R.id.no_content_to_display_ID);

        inner_view_attendance_layout_ID = getActivity().findViewById(R.id.inner_view_attendance_layout_ID);

        pb_loading_txt_manageDiscipline_progress = this.getActivity().findViewById(R.id.pb_loading_txt_manageDiscipline_progress);


        btn_tryagain_ID = getActivity().findViewById(R.id.btn_tryagain_ID);

        pb_manageDiscipline_progress = getActivity().findViewById(R.id.pb_manageDiscipline_progress);

        //OTHER
        sp_group_layout = getActivity().findViewById(R.id.sp_group_layout);
        key_layout = getActivity().findViewById(R.id.key_layout);

        pb_manageDiscipline_progress_layout = getActivity().findViewById(R.id.pb_manageDiscipline_progress_layout);

        txt_viewing_results_of_ID = this.getActivity().findViewById(R.id.txt_viewing_results_of_ID);
        StringBuilder b = new StringBuilder();
        b.append("Viewing class attendance of ");
        b.append(new StudentDAO(this.getActivity()).getFullnamesForThisStudentID(String.valueOf(studentRequest.getStudentID())));
        b.append(" ");
        b.append(new StudentDAO(this.getActivity()).getRegistrationNumberForThisStudentID(String.valueOf(studentRequest.getStudentID())));
        txt_viewing_results_of_ID.setText(b.toString());

        grid_view_class_attendance_ID = this.getActivity().findViewById(R.id.grid_view_class_attendance_ID);


        label_confirmation_message_ID = this.getActivity().findViewById(R.id.label_confirmation_message_ID);
        sp_year_ID = this.getActivity().findViewById(R.id.sp_year_ID);
        sp_term_ID = this.getActivity().findViewById(R.id.sp_term_ID);
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


                        ArrayList<TermSpinner> termSpinnerList = new TermSpinnerDAO(getActivity()).loadDataForTermSpinner(ViewClassAttendance.this);
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
//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        //break;
                        termSelected = (TermSpinner) parent.getSelectedItem();
                        //LOAD DATA
                        //Will load the data and display the gridview
                        if(checkYear(yearSelected) && checkTerm(termSelected)){
                            showLabelConfirmationMessage(false);

                            new StudentAttendanceDAO(getActivity()).loadGridviewWithData(ViewClassAttendance.this);
                        }

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                termSelected = null;

            }
        });


        super.onActivityCreated(savedInstanceState);
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

            pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            pb_loading_txt_manageDiscipline_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            grid_view_class_attendance_ID.setVisibility(show ? View.GONE : View.VISIBLE);
            grid_view_class_attendance_ID.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    grid_view_class_attendance_ID.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });



        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            grid_view_class_attendance_ID.setVisibility(show ? View.GONE : View.VISIBLE);
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
