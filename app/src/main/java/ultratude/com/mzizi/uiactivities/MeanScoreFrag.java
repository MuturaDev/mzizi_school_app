package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.backgroundtasks.GetXInstanceFromRoomDB;
import ultratude.com.mzizi.helperactivityclasses.MeanScoreRecycleViewAdapterTest;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;

/**
 * Created by James on 02/07/2018.
 */

public class MeanScoreFrag extends Fragment {

    public ParentMziziDatabase db;
    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public LinearLayoutManager linearLayoutManager;

    public RecyclerView recyclerView;
    public LinearLayout meanscore_layout_noContent;
    public LinearLayout layoutabove;


    //public MeanScoreRecycleViewAdapter meanScoreRecycleViewAdapter;
    public MeanScoreRecycleViewAdapterTest meanScoreRecycleViewAdapterTest;

    public ProgressBar meanscore_progress;

    public   Spinner year_spinner;

    private static boolean is_first_launch = false;
    private static int countAssignements = 1;


    public MeanScoreFrag(){

    }

    public static MeanScoreFrag newInstance(String param1, String param2, FragmentManager fragmentManager) {
        MeanScoreFrag.fragmentManager = fragmentManager;

        MeanScoreFrag fragment = new MeanScoreFrag();
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
        return inflater.inflate(R.layout.meanscore_recycler_layout, container, false);
    }



    UtilityFunctions utilityFunctions;

    @Override
    //if this code is put in onStart, the layout items will double
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Exam Scores");

        utilityFunctions = new UtilityFunctions(getActivity());

        // String message = ( getArguments() != null) ? getArguments().getString("message") : "Has no message to display!!!";

        //Toast.makeText(getActivity(),message , Toast.LENGTH_LONG).show();

        year_spinner = getActivity().findViewById(R.id.year_spinner_ID);


        meanscore_progress = getActivity().findViewById(R.id.meanscore_progress);

       meanscore_layout_noContent = getActivity().findViewById(R.id.meanscore_layout_noContent);
        layoutabove = getActivity().findViewById(R.id.layoutabove);

        // tblayout =  getActivity().findViewById(R.id.upcoming_events_table);

        db = ParentMziziDatabase.getInstance(getContext());

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = getActivity().findViewById(R.id.meanscore_recycle);
        recyclerView.setLayoutManager(linearLayoutManager);


        if(countAssignements<=1){
            is_first_launch = getActivity().getIntent().getBooleanExtra("isLog_In", false);
        }else{
            is_first_launch = false;
        }

        countAssignements++;


       final SwipeRefreshLayout pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                AsyncTask asyncTask = new AsyncTask() {

                    @Override
                    protected void onPreExecute() {
                        showProgress(true);
                        super.onPreExecute();
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        Student student = (Student) o;

                        setRequestForPortalResults(student);

                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        return ((MainActivity) getActivity()).student;
                    }
                };

                if(utilityFunctions.internetConnection())
                    asyncTask.execute();

                pullToRefresh.setRefreshing(false);

            }
        });



        if(is_first_launch){
            //if first time, call loginActivity

            //ShowCaseView

            // You don't always need a sequence, and for that there's a single time tap target
            final SpannableString spannedDesc = new SpannableString("The view will change to results of the year you selected.");
            spannedDesc.setSpan(new UnderlineSpan(), 0, spannedDesc.length(), 0);

            TapTargetView.showFor(getActivity(), TapTarget.forView(getActivity().findViewById(R.id.year_spinner_ID), "Click to select year.", spannedDesc)
                    .cancelable(false)
                    .drawShadow(true)
                    .dimColor(R.color.black)
                    // .titleTextDimen(R.dimen.title_text_size)
                    .tintTarget(false), new TapTargetView.Listener() {
                @Override
                public void onTargetClick(TapTargetView view) {
                    super.onTargetClick(view);
                    // .. which evidently starts the sequence we defined earlier
                    // sequence.start();
                }

                @Override
                public void onOuterCircleClick(TapTargetView view) {
                    super.onOuterCircleClick(view);
                   // Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTargetDismissed(TapTargetView view, boolean userInitiated) {


                   // Log.d("TapTargetViewSample", "You dismissed me :(");
                }
            });


            //ShowCaseView

            }

            //START OF TESTING...
//        if(countAssignements<=1){
//            is_first_launch = getActivity().getIntent().getBooleanExtra("isLog_In", false);
//            //is_first_launch = true;
//        }else{
//            is_first_launch = false;
//        }
//
//        countAssignements++;


       // Toast.makeText(getActivity(), "is_first_launch: "  + is_first_launch, Toast.LENGTH_SHORT).show();

        //END OF TESTING....



        showProgress(true);
        new GetXInstanceFromRoomDB( (MainActivity) getActivity(), MeanScoreFrag.this).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,"");


        super.onActivityCreated(savedInstanceState);
    }


    private void setRequestForPortalResults(final Student student){

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalStudentDetailedResults>> userCall = apiInterface.getPortalStudentDetailes(student);
        userCall.enqueue(new Callback<List<PortalStudentDetailedResults>>() {
            @Override
            public void onResponse(Call<List<PortalStudentDetailedResults>> call,final Response<List<PortalStudentDetailedResults>> response) {


                if (response.code() == 200) {
                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] objects) {
                            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                            if(studentid == null){
                                studentid  = "0";
                            }
                            db.getPortalStudentDetailedResultsDao().deletePortalStudentDetialedResults(Integer.valueOf(studentid));

                            for(PortalStudentDetailedResults res : response.body()){
                                res.setStudID(Integer.valueOf(student.getStudentID()));
                            }

                            db.getPortalStudentDetailedResultsDao().insertPortalStudentDetialedResults(response.body());
                            return null;
                        }
                    };
                    asyncTask.execute();

                    new GetXInstanceFromRoomDB( (MainActivity) getActivity(), MeanScoreFrag.this).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,"");


                } else{
                    new GetXInstanceFromRoomDB( (MainActivity) getActivity(), MeanScoreFrag.this).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,"");

                }

            }

            @Override
            public void onFailure(Call<List<PortalStudentDetailedResults>> call, Throwable t) {
                Toast.makeText(getActivity(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
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





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

       // Toast.makeText(getActivity(), "OnCreate", Toast.LENGTH_LONG).show();

        //to the student id, you have to create a back ground task

        SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(getActivity()).getFilteredPortalStudentInfoResult();
        String billingBalance = syncMyAccountResult.getBillingBalance();
        if(billingBalance.equals("")){//equal to an empty string
            //do nothing
        }else if(!billingBalance.equals("")){//not equal to an empty string

            if(Float.valueOf(billingBalance) > 0f){

               // Toast.makeText(getActivity(), "greater: " + billingBalance, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(( getActivity().getBaseContext()), SyncMyAccount.class);
                intent.putExtra("StudentID", ( (MainActivity) getActivity()).student.getStudentID());
                intent.putExtra("appcode", ( (MainActivity) getActivity()).student.getAppcode());
                intent.putExtra("CALL_FROM", "FRAGMENT");//AUTHENTICATE_USER

                getActivity().startActivity(intent);
                getActivity().finish();

            }else{//If the balance is less than 0.0f, no need to

               // Toast.makeText(getActivity(), "lesser: " + billingBalance, Toast.LENGTH_LONG).show();
            }

            ReportAnalytics.reportScreenChangeAnalytic(getContext(), "8.4.4 Results Fragment");


        }




        super.onCreate(savedInstanceState);
    }
















}
