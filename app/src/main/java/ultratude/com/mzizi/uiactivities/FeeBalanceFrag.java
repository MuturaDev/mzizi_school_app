package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.andrognito.patternlockview.utils.ResourceUtils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.helperactivityclasses.FeeBalanceRecyclerViewAdapter;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;

/**
 * Created by James on 02/07/2018.
 */


//remember to have an interface, to send information to the activity, from there the activity can do whatever it wanst after
//implementing the interaface and overriding the method.
public class FeeBalanceFrag extends Fragment {



    public LinearLayoutManager linearLayoutManager;
    public RecyclerView recyclerView;
    public SwipeRefreshLayout pullToRefresh;
    public FeeBalanceRecyclerViewAdapter feeBalanceRecyclerViewAdapter;

    public RelativeLayout fee_balance_layout;
    public LinearLayout fee_balance_noContent;
    public TextView current_balance;
    TextView top_header_value;
    public LinearLayout tap_to_refresh_ID;


    public ProgressBar feebalance_progress;

    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    //COMMENTS BOTTOM SHEET
    public LinearLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private TextView label_change_bottom_sheet_state_ID;
    private LinearLayout btn_change_bottom_sheet_state_ID;

    //comments
    private TextView label_head_teacher_comment;




    public FeeBalanceFrag(){

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

              //  Toast.makeText(getActivity(), "greater: " + billingBalance, Toast.LENGTH_LONG).show();

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


        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "FeeTransactions Fragment");

        super.onCreate(savedInstanceState);
    }

     ShimmerFrameLayout shimmer_view_container;



    UtilityFunctions utilityFunctions;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Fee Transactions");

        utilityFunctions = new UtilityFunctions(getActivity());

        current_balance =  getActivity().findViewById(R.id.current_balance);
        top_header_value = getActivity().findViewById(R.id.top_header_value);
        feebalance_progress = getActivity().findViewById(R.id.feebalance_progress);
        fee_balance_layout = getActivity().findViewById(R.id.fee_balance_layout);
        fee_balance_noContent = getActivity().findViewById(R.id.fee_balance_noContent);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = getActivity().findViewById(R.id.fee_balance_recycler);
        tap_to_refresh_ID = getActivity().findViewById(R.id.tap_to_refresh_ID);
        pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        //setting an setOnRefreshListener on the SwipeDownLayout
        shimmer_view_container = getActivity().findViewById(R.id.shimmer_view_container);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1; //Counting how many times user have refreshed the layout

            @Override
            public void onRefresh() {
                //Here you can update your data from internet or from local SQLite data

                AsyncTask asyncTask = new AsyncTask() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        //shimmer_view_container.startShimmerAnimation();
                        showProgress(true);
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        Student student = (Student)o;

                        sendRequestForTransactions(student);

                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        List<AuthenticateUserResponse> studentList = ParentMziziDatabase.getInstance(getActivity()).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
                        if(studentList.size()>0){
                            return new Student(studentList.get(0).getUserID(),studentList.get(0).getAppcode());
                        }

                        return new Student("","");
                    }
                };



                if(utilityFunctions.internetConnection())
                    asyncTask.execute();


                pullToRefresh.setRefreshing(false);
            }
        });


        recyclerView.setLayoutManager(linearLayoutManager);
//        String message = ( getArguments() != null) ? getArguments().getString("message") : "Has no message to display!!!!";
//
//        Toast.makeText(getActivity(),message , Toast.LENGTH_LONG).show();

        // tblayout =  getActivity().findViewById(R.id.fee_history_tl);


            //This is removed but dont delete it
       // new XFragRetrieveAndDisplayRoomData(this, ( (MainActivity) getActivity()), "").execute(GetConstants.GET_PORTAL_DETAILED_TRANSACTION);

        showProgress(false);
        new RetrieveDataFeeBalance(getActivity(), this).execute();


        //addHeaders();
        //addData();

        layoutBottomSheet = getActivity().findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        label_change_bottom_sheet_state_ID = getActivity().findViewById(R.id.label_change_bottom_sheet_state_ID);
        btn_change_bottom_sheet_state_ID = getActivity().findViewById(R.id.btn_change_bottom_sheet_state_ID);

        label_head_teacher_comment = getActivity().findViewById(R.id.label_head_teacher_comment);

        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {

                List<PortalStudentInfo> studentInfoList = (List<PortalStudentInfo>)o;
                if(studentInfoList.size()>0){
                    if(studentInfoList.get(0).getPaymentNote() != null) {
                        label_head_teacher_comment.setText(studentInfoList.get(0).getPaymentNote());
                        label_head_teacher_comment.setTextColor(ResourceUtils.getColor(getContext(), R.color.homeText));
                    }
                    else{
                        label_head_teacher_comment.setText("No payment instruction to show");
                        label_head_teacher_comment.setTextColor(ResourceUtils.getColor(getContext(), R.color.confirmation_success));
                    }
                }else{
                    label_head_teacher_comment.setText("No payment instruction to show");
                    label_head_teacher_comment.setTextColor(ResourceUtils.getColor(getContext(), R.color.confirmation_success));
                }
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
            }
        };
        asyncTask.execute();

        /**
         * Bottom sheet state change listener.
         * We are changing bottom text when sheet changed state*/
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        label_change_bottom_sheet_state_ID.setText("Close payment instruction");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        label_change_bottom_sheet_state_ID.setText("Show payment instruction");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        btn_change_bottom_sheet_state_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(MainActivity.this, String.valueOf(sheetBehavior.getState()), Toast.LENGTH_SHORT).show();
                //sheetBehavior.setPeekHeight(200);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){

                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //sheetBehavior.setPeekHeight(200);
                    label_change_bottom_sheet_state_ID.setText("Close payment instruction");//4
                }else{
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//3
                    label_change_bottom_sheet_state_ID.setText("Show payment instruction");

                }


                // sheetBehavior.setState(sheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN ? BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_HIDDEN);
            }
        });



        super.onActivityCreated(savedInstanceState);
    }

    private void sendRequestForTransactions(final Student student){

        Log.d(getActivity().getPackageName().toUpperCase(), "Swip Started");

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalDetailedTransaction>> userCall = apiInterface.getPortalDetailedTransaction(student);
        userCall.enqueue(new Callback<List<PortalDetailedTransaction>>() {
            @Override
            public void onResponse(Call<List<PortalDetailedTransaction>> call, Response<List<PortalDetailedTransaction>> response) {

                List<PortalDetailedTransaction> resultsList = response.body();


                if (response.code() == 200) {

                    ParentMziziDatabase db = ((MainActivity) getActivity()).db;
                    db.getPortalDetailedTransactionDao().deletePortalDetailedTransaction(Integer.valueOf(student.getStudentID()));
                    for(PortalDetailedTransaction res : response.body()){
                        res.setStudID(Integer.valueOf(student.getStudentID()));
                    }
                    db.getPortalDetailedTransactionDao().insertPortalDetailedTransaction(resultsList);

                    new RetrieveDataFeeBalance(getActivity(), FeeBalanceFrag.this).execute();

                }


            }

            @Override
            public void onFailure(Call<List<PortalDetailedTransaction>> call, Throwable t) {
                Toast.makeText(getActivity(), "Couldn't connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
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

            int shortAnimTime = 0;

            if(isAdded())
            shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            current_balance.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            top_header_value.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                }
            });


            feebalance_progress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            feebalance_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    feebalance_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            feebalance_progress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            top_header_value.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            current_balance.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            recyclerView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
    }


    public static FeeBalanceFrag newInstance(String param1, String param2, FragmentManager fragmentManager) {
        FeeBalanceFrag.fragmentManager = fragmentManager;

        FeeBalanceFrag fragment = new FeeBalanceFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fee_balance_layout, container, false);
    }

    private class RetrieveDataFeeBalance extends AsyncTask<Void,Void, Map<String, Object>> {

        private Context context;
        private FeeBalanceFrag fragment;


        public RetrieveDataFeeBalance(Context context, FeeBalanceFrag fragment){
            this.context = context;
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
            //recylerbit
            //showprogress
            // currentBalance


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Map<String, Object> returnResults) {

            //START
            List<PortalDetailedTransaction> transactions =  (List<PortalDetailedTransaction>) returnResults.get(GetConstants.GET_PORTAL_DETAILED_TRANSACTION);
            //reverse the transactions list
            Collections.reverse(transactions);

            if(transactions.size() >=1){

                if(transactions.get(0).getTransType() != null || !TextUtils.isEmpty(transactions.get(0).getTransType())){
                    NumberFormat myFormat = NumberFormat.getInstance();
                    myFormat.setGroupingUsed(true);

                    if(!transactions.get(transactions.size() - 1).getCurrentAmount().equals("")){
                        current_balance.setText("Ksh. " + myFormat.format(Double.parseDouble(transactions.get(transactions.size() - 1).getCurrentAmount())) + "/=");
                    }else{
                        current_balance.setText("Ksh. 0.00");
                    }


                    feeBalanceRecyclerViewAdapter = new FeeBalanceRecyclerViewAdapter(getActivity(), fragment , transactions );


                    recyclerView.setAdapter(feeBalanceRecyclerViewAdapter);
                    //This was removed for the first record is the last transaction made
                    recyclerView.scrollToPosition(transactions.size() - 1);




                    fee_balance_layout.setVisibility(View.VISIBLE);
                    fee_balance_noContent.setVisibility(View.GONE);
                    current_balance.setVisibility(View.VISIBLE);




                }else{
                    showProgress(false);

                    fee_balance_layout.setVisibility(View.INVISIBLE);
                    fee_balance_noContent.setVisibility(View.VISIBLE);
                    current_balance.setVisibility(View.INVISIBLE);
                    tap_to_refresh_ID.setVisibility(View.VISIBLE);
                }

            }else{


                showProgress(false);

                fee_balance_layout.setVisibility(View.INVISIBLE);
                fee_balance_noContent.setVisibility(View.VISIBLE);
                current_balance.setVisibility(View.INVISIBLE);

               // Toast.makeText(context, "No fee transactions to show", Toast.LENGTH_SHORT);
                //do nothing if there is no data
            }

            //END


            super.onPostExecute(returnResults);
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
            Map<String, Object>  returnResults;

            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }

            List<PortalDetailedTransaction> detailedTransactionsList = db.getPortalDetailedTransactionDao().getPortalDetailedTransaction(Integer.valueOf(studentid));
            //checking if there any transactions for this student
            if(detailedTransactionsList.size() < 1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_DETAILED_TRANSACTION, new ArrayList<PortalDetailedTransaction>());
                return returnResults;
            }

            //remember this is returning a list of transactions
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_DETAILED_TRANSACTION, detailedTransactionsList);
            return returnResults;

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
