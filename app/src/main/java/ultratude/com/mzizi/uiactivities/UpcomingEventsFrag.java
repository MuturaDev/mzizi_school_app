package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.loadingview.LoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.helperactivityclasses.UpcomingEventsRecyclerAdapter;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;


/**
 * Created by James on 02/07/2018.
 */

public class UpcomingEventsFrag extends Fragment {


    public ProgressBar upcoming_events_progress;

    public RecyclerView recyclerView;
    public LinearLayout upcoming_events_noContent;
    public LinearLayout upcoming_events_layout;

    public LinearLayoutManager linearLayoutManager;
    public UpcomingEventsRecyclerAdapter upcomingEventsRecyclerAdapter;

    public ParentMziziDatabase db;

    ////THIS IS WHAT WE ARE ADDING

    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    public UpcomingEventsFrag(){


    }

    public static UpcomingEventsFrag newInstance(String param1, String param2, FragmentManager fragmentManager) {
        UpcomingEventsFrag.fragmentManager = fragmentManager;

        UpcomingEventsFrag fragment = new UpcomingEventsFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.upcoming_events_layout, container, false);
    }

    UtilityFunctions utilityFunctions;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        upcoming_events_progress = getActivity().findViewById(R.id.upcoming_events_progress);


        getActivity().setTitle("Upcoming Events");
        utilityFunctions = new UtilityFunctions(getActivity());


        db = ParentMziziDatabase.getInstance(getContext());


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recyclerView =  getActivity().findViewById(R.id.upcoming_events_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);
        upcoming_events_noContent = getActivity().findViewById(R.id.upcoming_events_noContent);
        upcoming_events_layout = getActivity().findViewById(R.id.upcoming_events_layout);

//        showProgress(true);
        //new XFragRetrieveAndDisplayRoomData(this, new MainActivity(), "").execute(GetConstants.GET_PORTAL_EVENTS);
        final SwipeRefreshLayout pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncTask asyncTask = new AsyncTask() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        showProgress(true);
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);

                        Student student  = (Student)o;
                        portalSendRequestForEvents(student);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        return ((MainActivity)getActivity()).student;
                    }
                };

                if(utilityFunctions.internetConnection())
                    asyncTask.execute();

                pullToRefresh.setRefreshing(false);
            }
        });



        new RetrieveDataUpcomingEventsFrag(this.getActivity()).execute();

        super.onActivityCreated(savedInstanceState);
    }


    private void portalSendRequestForEvents(final Student student){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<PortalEvents>> userCall = apiInterface.getPortalEvents(student);
        userCall.enqueue(new Callback<List<PortalEvents>>() {
            @Override
            public void onResponse(Call<List<PortalEvents>> call, Response<List<PortalEvents>> response) {

                if(response.body() != null){
                    if(response.body().size() > 0){
                        final List<PortalEvents> resultsList = response.body();


                        List<Object> listOfItems = new ArrayList<>();

                        AsyncTask asyncTask3 = new AsyncTask() {

                            @Override
                            protected void onPostExecute(Object o) {
                                super.onPostExecute(o);
                                new RetrieveDataUpcomingEventsFrag(getActivity()).execute();
                            }

                            @Override
                            protected Object doInBackground(Object[] objects) {
                                List<PortalEvents> portalEventsList = resultsList;

                                for(PortalEvents res : portalEventsList){
                                    res.setStudID(Integer.valueOf(student.getStudentID()));
                                }

                                try{
                                    db.getPortalEventsDao().deletePortalEvents(Integer.valueOf(student.getStudentID()));
                                    db.getPortalEventsDao().insertPortalEventsList(portalEventsList);
                                }catch(SQLiteException e){
                                    db.getPortalSiblingsDao().deleteSiblings();
                                }

                                return portalEventsList;

                            }
                        };

                        if (response.code() == 200) {

                            if (resultsList != null) {


                                listOfItems.add(Long.valueOf(response.code()));
                                listOfItems.add(resultsList);


                                asyncTask3.execute();

                            }
                        }else{
                            asyncTask3.execute();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<PortalEvents>> call, Throwable t) {
                //  Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });




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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 0;

            if(isAdded())
            shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            upcoming_events_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            upcoming_events_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    upcoming_events_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            upcoming_events_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }







    @Override
    public void onStart() {

        super.onStart();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }



    public class RetrieveDataUpcomingEventsFrag extends AsyncTask<Void,Void, Map<String, Object>> {

        private Context context;

        public RetrieveDataUpcomingEventsFrag(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Map<String, Object> returnResults) {

            showProgress(false);



            List<PortalEvents> portalEventsList1 = (List<PortalEvents>) returnResults.get(GetConstants.GET_PORTAL_EVENTS) ;

            //    Toast.makeText(((UpcomingEventsFrag) fragWeakReference.get()).getActivity(), "Success events: " + String.valueOf(portalEventsList1.get(0).getDescription()), Toast.LENGTH_LONG).show();


            //


            if(!portalEventsList1.isEmpty()){
                //HARD CODED DATA
                // Toast.makeText(((UpcomingEventsFrag) fragWeakReference.get()).getActivity(), portalEventsList1.get(1).getEventsTitle() , Toast.LENGTH_LONG).show();

//            List<PortalEvents> portalEventsList = new ArrayList<>();
//            PortalEvents portalEvents = new PortalEvents();
//            portalEvents.setEventsTitle("Form 1 Admission");
//            portalEvents.setVenue("School Play Ground");
//            portalEvents.setStartDate("5/1/2019");
//            portalEvents.setEndDate("30/1/2019");
//
//            portalEvents.setStartTime("07:00 AM");
//            portalEvents.setEndTime("6:00 PM");
//            portalEvents.setDescription("NOTE: You should be at the admission desk as early as possible.\n" +
//                    "DON'T FORGET TO COME WITH YOUR ADMISSION LETTER\n" +
//                    "\n" +
//                    "School Fees should be paid on the various bank acounts provided on the admission letter.\n" +
//                    "NO STUDENT WILL BE ADMITTED WITHOUT FULLY PAYING ALL HIS OR HER SCHOOL FEE!");
//
//            portalEventsList.add(portalEvents);
                //  portalEventsList.add(portalEvents);



                upcomingEventsRecyclerAdapter = new UpcomingEventsRecyclerAdapter(getActivity(), portalEventsList1 );


                recyclerView.setAdapter(upcomingEventsRecyclerAdapter );
                recyclerView.scrollToPosition(portalEventsList1.size() - 1);

                upcoming_events_layout.setVisibility(View.VISIBLE);
                upcoming_events_noContent.setVisibility(View.GONE);
            }else{

                upcoming_events_layout.setVisibility(View.GONE);
                upcoming_events_noContent.setVisibility(View.VISIBLE);
               // Toast.makeText( getActivity(), "No upcoming events to show", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(returnResults);
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            Map<String, Object>  returnResults;

            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }

            List<PortalEvents> portalEventsList = db.getPortalEventsDao().getPortalEvents(Integer.valueOf(studentid));

            if(portalEventsList.size() < 1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_EVENTS, new ArrayList<PortalEvents>());
                return returnResults;
            }

            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_EVENTS, portalEventsList);
            return returnResults;
        }
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

                Intent intent = new Intent(( getActivity().getBaseContext()), SyncMyAccount.class);
                intent.putExtra("StudentID", ( (MainActivity) getActivity()).student.getStudentID());
                intent.putExtra("appcode", ( (MainActivity) getActivity()).student.getAppcode());
                intent.putExtra("CALL_FROM", "FRAGMENT");//AUTHENTICATE_USER

                getActivity().startActivity(intent);
                getActivity().finish();

            }else{//If the balance is less than 0.0f, no need to

            }


        }

        super.onCreate(savedInstanceState);
    }

}
