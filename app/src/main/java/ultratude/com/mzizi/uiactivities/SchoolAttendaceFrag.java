package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.modelclasses.StudentAttendanceDAO;
import ultratude.com.mzizi.modelclasses.TermSpinner;
import ultratude.com.mzizi.modelclasses.YearSpinner;
import ultratude.com.mzizi.modelclasses.spinnerdao.TermSpinnerDAO;
import ultratude.com.mzizi.modelclasses.spinnerdao.YearSpinnerDAO;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.StudentClassAttendanceDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TermRoom;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.YearRoom;
import ultratude.com.staff.webservice.RequestModels.PortalGetAttendanceRequest;

/**
 * Created by James on 01/08/2018.
 */

public class SchoolAttendaceFrag  extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static FragmentManager fragmentManager;

    public YearSpinner yearSelected = null;
    public TermSpinner termSelected = null;

    private TextView txt_viewing_results_of_ID;
    private LinearLayout pb_loading_txt_manageDiscipline_progress;
    public GridView grid_view_class_attendance_ID;

    public TextView no_content_to_display_ID;

    //NO INTERNET
    private RelativeLayout no_internet_connection_layout_ID;
    private LinearLayout inner_view_attendance_layout_ID;
    public void showNoInternetConnectionLayout(boolean show){

        if (show){
            no_internet_connection_layout_ID.setVisibility(View.VISIBLE);
            inner_view_attendance_layout_ID.setVisibility(View.GONE);
        }else{
            no_internet_connection_layout_ID.setVisibility(View.GONE);
            inner_view_attendance_layout_ID.setVisibility(View.VISIBLE);
        }

    }

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
    public Spinner sp_term_ID;

    public Spinner sp_year_ID;
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


    private LinearLayout sp_group_layout;
    private RelativeLayout key_layout;
    // showLabelConfirmationMessage(false);
    //grid_view_class_attendance_ID


   private static String studentName = "";
    private static String registrationNumber = "";

    public SchoolAttendaceFrag() {
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
    public static SchoolAttendaceFrag newInstance(String param1, String param2) {
        SchoolAttendaceFrag fragment = new SchoolAttendaceFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.view_attendance_layout, container, false);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(ultratude.com.staff.R.menu.maximize_minimize, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //sp_group_layout
        //key_layout
        // showLabelConfirmationMessage(false);//mosty this has already being handled
        //grid_view_class_attendance_ID

        if(item.getTitle().equals("maximize")){
            // item.setTooltipText("Minimize");

            //gone
            if(grid_view_class_attendance_ID.getVisibility() == View.VISIBLE){
                item.setIcon(getResources().getDrawable(ultratude.com.staff.R.drawable.minimize_icon));
                item.setTitle("minimize");

                sp_group_layout.setVisibility(View.GONE);
                key_layout.setVisibility(View.GONE);


            }

        }else if(item.getTitle().equals("minimize")) {

            //gone
            if(grid_view_class_attendance_ID.getVisibility() == View.VISIBLE){
                item.setIcon(getResources().getDrawable(ultratude.com.staff.R.drawable.maximize_icon));
                item.setTitle("maximize");

                sp_group_layout.setVisibility(View.VISIBLE);
                key_layout.setVisibility(View.VISIBLE);


            }

        }

        return super.onOptionsItemSelected(item);
    }

    UtilityFunctions utilityFunctions;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        //NO INTERNET CONNECTION
        this.getActivity().setTitle("Class Attendance");
        utilityFunctions = new UtilityFunctions(getActivity());



        no_content_to_display_ID = getActivity().findViewById(R.id.no_content_to_display_ID);
        no_internet_connection_layout_ID = getActivity().findViewById(R.id.no_internet_connection_layout_ID);

        inner_view_attendance_layout_ID = getActivity().findViewById(R.id.inner_view_attendance_layout_ID);

        pb_loading_txt_manageDiscipline_progress = this.getActivity().findViewById(R.id.pb_loading_txt_manageDiscipline_progress);


        btn_tryagain_ID = getActivity().findViewById(R.id.btn_tryagain_ID);

        pb_manageDiscipline_progress = getActivity().findViewById(R.id.pb_manageDiscipline_progress);

        //OTHER
        sp_group_layout = getActivity().findViewById(R.id.sp_group_layout);
        key_layout = getActivity().findViewById(R.id.key_layout);

        pb_manageDiscipline_progress_layout = getActivity().findViewById(R.id.pb_manageDiscipline_progress_layout);

        txt_viewing_results_of_ID = this.getActivity().findViewById(R.id.txt_viewing_results_of_ID);

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(SchoolAttendaceFrag.this.getActivity());

                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo> info = db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
                return info;
            }

            @Override
            protected void onPostExecute(Object o) {

                List<ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo> portalStudentInfoList = (List<ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo>)o;

                if(portalStudentInfoList.size() > 0) {
                    ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo portalStudentInfo = portalStudentInfoList.get(0);

                    studentName = portalStudentInfo.getStudentFullName();
                    registrationNumber = portalStudentInfo.getRegistrationNumber();
                    txt_viewing_results_of_ID.setText("");
                }

                super.onPostExecute(o);
            }
        };
        asyncTask.execute();





        grid_view_class_attendance_ID = this.getActivity().findViewById(ultratude.com.staff.R.id.grid_view_class_attendance_ID);


        label_confirmation_message_ID = this.getActivity().findViewById(ultratude.com.staff.R.id.label_confirmation_message_ID);
        sp_year_ID = this.getActivity().findViewById(ultratude.com.staff.R.id.sp_year_ID);
        sp_term_ID = this.getActivity().findViewById(ultratude.com.staff.R.id.sp_term_ID);

        final SwipeRefreshLayout pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                AsyncTask asyncTask1 = new AsyncTask() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        showProgress(true);
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        List<String> list = (List<String>) o;

                        if(list.size() ==  3) {
                            String studentID = list.get(0);
                            String appcode = list.get(1);
                            String organization = list.get(2);


                            Student student = new Student(studentID, appcode);

                            sendRequestForStudentAttendanceDetils(student, getActivity(),organization);


                        }



                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());

                        List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();


                        //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();

                        List<String> list = new ArrayList<>();
                        if(authenticateUserResponse.size() > 0){
                            list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
                            list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
                            list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
                        }

                        return list;

                    }
                };

                if(utilityFunctions.internetConnection())
                    asyncTask1.execute();

                pullToRefresh.setRefreshing(false);

            }
        });


        displayRecyclerViewData();




        super.onActivityCreated(savedInstanceState);
    }


    private void sendRequestForStudentAttendanceDetils(final Student student, final Context mContext, String organization){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        PortalGetAttendanceRequest portalGetAttendanceRequest = new PortalGetAttendanceRequest(
                student.getStudentID(),
                "0",
                "0",
                organization,
                student.getAppcode()
//                "24454",
//                "0",
//                "0",
//                "49",
//                "1000"
        );


        Call<List<StudentClassAttendance>> userCall = apiInterface.getStudentClassAttendance(portalGetAttendanceRequest);
        userCall.enqueue(new Callback<List<StudentClassAttendance>>() {
                             @Override
                             public void onResponse(Call<List<StudentClassAttendance>> call, final Response<List<StudentClassAttendance>> response) {
                                 if(response.isSuccessful()){
                                     if(response.code() == 200){
                                         //save to database
                                         AsyncTask asyncTask = new AsyncTask() {

                                             @Override
                                             protected void onPostExecute(Object o) {

                                                 displayRecyclerViewData();

                                                 super.onPostExecute(o);
                                             }

                                             @Override
                                             protected Object doInBackground(Object[] params) {
                                                 ParentMziziDatabase db = ParentMziziDatabase.getInstance(mContext);
                                                 String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                                                 if(studentid == null){
                                                     studentid  = "0";
                                                 }
                                                 db.getStudentClassAttendanceDAO().deleteStudentClassAttendance(Integer.valueOf(studentid));
                                                 for(StudentClassAttendance res : response.body()){
                                                     res.setStudID(Integer.valueOf(studentid));
                                                 }
                                                 db.getStudentClassAttendanceDAO().insertStudentClassAttendance(response.body());
                                                 return null;
                                             }
                                         };
                                         asyncTask.execute();


                                     }
                                 }
                             }

                             @Override
                             public void onFailure(Call<List<StudentClassAttendance>> call, Throwable t) {

                             }
                         }
        );
    }





    public void displayRecyclerViewData(){

        AsyncTask asyncTask1 = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                showProgress(true);
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                ParentMziziDatabase db =  ParentMziziDatabase.getInstance(SchoolAttendaceFrag.this.getActivity());
                StudentClassAttendanceDAO attendanceDAO = db.getStudentClassAttendanceDAO();
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<YearRoom> yearRoom = attendanceDAO.getYearForList(Integer.valueOf(studentid));
                List<TermRoom> termRoom = attendanceDAO.getTermForList(Integer.valueOf(studentid));

                List<Object> list = new ArrayList<>();
                list.add(yearRoom);
                list.add(termRoom);
                return list;
            }

            @Override
            protected void onPostExecute(Object o) {

                showProgress(false);
                List<Object> list = (List<Object>) o;

                if(list.size() > 0){
                    List<YearRoom> yearRoom = ( List<YearRoom>) list.get(0);
                    List<TermRoom> termRoom = (List<TermRoom>) list.get(1);

                    if(yearRoom.size() > 0 && termRoom.size() > 0){


                        YearRoom year = yearRoom.get(0);
                        TermRoom term = termRoom.get(0);
                        //Viewing class attendance of James Mutura, 9961 for
                        //Term 1 2019
                        StringBuilder stringBuilder  = new StringBuilder();
                        stringBuilder.append("Viewing class attendance of ");
                        stringBuilder.append(studentName);
                        stringBuilder.append(" ");
                        stringBuilder.append(registrationNumber);
                        stringBuilder.append(" for ");
                        stringBuilder.append(term.getTerm());
                        stringBuilder.append(" " + year.getYear());
                        txt_viewing_results_of_ID.setText(stringBuilder.toString());


                        YearSpinner yearSpinner = new YearSpinner(
                                1,
                                year.getYear()
                        );
                        TermSpinner termSpinner = new TermSpinner(
                                1,
                                term.getTerm()
                        );
                        new StudentAttendanceDAO(getActivity()).loadGridviewWithData(SchoolAttendaceFrag.this,yearSpinner, termSpinner);


                    }else{
                        no_content_to_display_ID.setVisibility(View.VISIBLE);
                    }
                }

                super.onPostExecute(o);
            }
        };
        asyncTask1.execute();


        //
        new YearSpinnerDAO(getActivity()).loadDataForYearSpinner(this);
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
                        checkTerm(termSelected);
                        new TermSpinnerDAO(getActivity()).loadDataForTermSpinner(SchoolAttendaceFrag.this);
                        no_content_to_display_ID.setVisibility(View.INVISIBLE);
                        break;
                    // Toast.makeText(ManageDisciplineSreen.this, classNameValueSelected, Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yearSelected = null;


            }
        });

        //
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
                            no_content_to_display_ID.setVisibility(View.INVISIBLE);
                            showLabelConfirmationMessage(false);
                            StringBuilder stringBuilder  = new StringBuilder();
                            stringBuilder.append("Viewing class attendance of ");
                            stringBuilder.append(studentName);
                            stringBuilder.append(" ");
                            stringBuilder.append(registrationNumber);
                            stringBuilder.append(" for ");
                            stringBuilder.append(yearSelected.getYearFor());
                            stringBuilder.append(" " + termSelected.getTermFor());
                            txt_viewing_results_of_ID.setText(stringBuilder.toString());
                            new StudentAttendanceDAO(getActivity()).loadGridviewWithData(SchoolAttendaceFrag.this,yearSelected, termSelected);
                        }else{
                            showLabelConfirmationMessage(true);

                        }

//                    Toast.makeText(ManageDisciplineSreen.this,studentSelected , Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                termSelected = null;

            }
        });




    }

    private boolean checkYear(YearSpinner year){
        if(year == null){
            showLabelConfirmationMessage(true);
            label_confirmation_message_ID.setText("Please specify the Year");
            return false;
        }
            return true;
    }

    private boolean checkTerm(TermSpinner term){
        if(term == null){
            showLabelConfirmationMessage(true);
            label_confirmation_message_ID.setText("Please specify the Term");
        }

        return true;
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
           // int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
//            pb_loading_txt_manageDiscipline_progress.animate().setDuration(shortAnimTime).alpha(
//                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    pb_loading_txt_manageDiscipline_progress.setVisibility(show ? View.VISIBLE : View.GONE);
//                }
//            });

            grid_view_class_attendance_ID.setVisibility(show ? View.GONE : View.VISIBLE);
//            grid_view_class_attendance_ID.animate().setDuration(shortAnimTime).alpha(
//                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    grid_view_class_attendance_ID.setVisibility(show ? View.GONE : View.VISIBLE);
//                }
//            });


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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
       // Toast.makeText(getActivity(), "OnCreate", Toast.LENGTH_LONG).show();

        //to the student id, you have to create a back ground task

        SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(getActivity()).getFilteredPortalStudentInfoResult();
        String billingBalance = syncMyAccountResult.getBillingBalance();
        if(billingBalance.equals("")){//equal to an empty string
            //do nothing
        }else if(!billingBalance.equals("")){//not equal to an empty string

            if(Float.valueOf(billingBalance) > 0f){

                //Toast.makeText(getActivity(), "greater: " + billingBalance, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(( getActivity().getBaseContext()), SyncMyAccount.class);
                intent.putExtra("StudentID", ( (MainActivity) getActivity()).student.getStudentID());
                intent.putExtra("appcode", ( (MainActivity) getActivity()).student.getAppcode());
                intent.putExtra("CALL_FROM", "FRAGMENT");//AUTHENTICATE_USER

                getActivity().startActivity(intent);
                getActivity().finish();

            }else{//If the balance is less than 0.0f, no need to

               // Toast.makeText(getActivity(), "lesser: " + billingBalance, Toast.LENGTH_LONG).show();
            }


        }


        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "SchoolAttendance Fragment");

        super.onCreate(savedInstanceState);
    }


}
