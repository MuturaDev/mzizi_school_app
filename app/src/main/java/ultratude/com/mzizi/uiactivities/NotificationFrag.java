package ultratude.com.mzizi.uiactivities;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;



import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
import ultratude.com.mzizi.helperactivityclasses.NotificationRecycleViewAdapter;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.modelclasses.MessageRequest;
import ultratude.com.mzizi.modelclasses.NotificationReadTracking;
import ultratude.com.mzizi.modelclasses.NotificationReadTrackingDAO;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotification;
import ultratude.com.mzizi.modelclasses.ReadNotReadNotificationDAO;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.webservice.APIRequest;
//import ultratude.com.mzizi.RoomDatabaseClasses.RoomModel.Notification;

/**
 * Created by Admin on 5/21/2018.
 */

public class NotificationFrag extends Fragment {

   public ProgressBar notification_progress;

    public ParentMziziDatabase db;

    public LinearLayoutManager lm;

    public RecyclerView recyclerView;
    public LinearLayout notification_layout_noContent;

    public NotificationRecycleViewAdapter notificationAdapter;


    //THIS IS WHAT YOU SHOULD ADD

    String message;
    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public NotificationFrag(){

    }

    public static NotificationFrag newInstance(String param1, String param2, FragmentManager fragmentManager) {
        NotificationFrag.fragmentManager = fragmentManager;



        NotificationFrag fragment = new NotificationFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 0;
            if(isAdded()){
                shortAnimTime= getResources().getInteger(android.R.integer.config_shortAnimTime);
            }



            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            notification_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            notification_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    notification_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            notification_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.notification_messages_layout, container, false);

        return view;
    }




    SwipeRefreshLayout pullToRefresh;
    UtilityFunctions utilityFunctions;

    @Override
    //if this code is put in onStart, the layout items will double
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


       getActivity().setTitle("School Notifications");

        utilityFunctions = new UtilityFunctions(getActivity());

//        Toolbar tool = getActivity().findViewById(R.id.toolbar);
//        ( (MainActivity) getActivity()).setSupportActionBar(tool);
//        getActivity().setTitle("2  new notifications");

        //cancel the notification, and when

      //  FragTransaction.setNotificationCount("0");


        //String message = ( getArguments() != null) ? getArguments().getString("message") : "has no message to display";

        //Toast.makeText(getActivity(),message , Toast.LENGTH_LONG).show();

        db = ParentMziziDatabase.getInstance(getContext());


        notification_layout_noContent = getActivity().findViewById(R.id.notification_layout_noContent);



        //notificationAdapter object is created in XXXFrag....
        lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView =  getActivity().findViewById(R.id.direct_messages_recycle);
        //Context context, ArrayList<Integer> picturesUrls, ArrayList<String> text, ArrayList<String> time

        //This is just to look if there are errors.
        recyclerView.setLayoutManager(lm);


        notification_progress = getActivity().findViewById(R.id.notification_progress);


        pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
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

                            RequestFor.sendRequest(((MainActivity)getActivity()).student,getActivity(),"", APIRequest.NOTIFICATION);


                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        return null;
                    }
                };
                //TODO: Test the network first, network connection test
                if(utilityFunctions.internetConnection())
                    asyncTask.execute();

                pullToRefresh.setRefreshing(false);
            }
        });



        //display the notifications in the layout
        if(this.getActivity() != null) {
            if (this.getActivity() instanceof MainActivity) {
                new RetrieveDataNotificationFrag(this.getActivity()).execute();
                //new XFragRetrieveAndDisplayRoomData(this, ((MainActivity) getActivity()), "").execute(GetConstants.GET_NOTIFICATION);
            }
        }


        super.onActivityCreated(savedInstanceState);
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


    public class RetrieveDataNotificationFrag extends AsyncTask<Void,Void, Map<String, Object>>{

        Context context;

        public RetrieveDataNotificationFrag(Context context){
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


            List<Notification> listOfNotification = (List<Notification>) returnResults.get(GetConstants.GET_NOTIFICATION) ;

           // Toast.makeText(context, "ListOfNotification notificationFrag : " + String.valueOf(listOfNotification.size()), Toast.LENGTH_LONG).show();

            if(listOfNotification.size() >=1){


                //assign different notifications markers.
               List<ReadNotReadNotification> notReadNotificationList = new ReadNotReadNotificationDAO(context).getReadNotReadNotification();

                if(notReadNotificationList.size() > 0){
                    //show the notifications are seen, so save them in the table notificationReadTracking

                    AsyncTask asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {

                            final Calendar cal = Calendar.getInstance();
                            final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

                            NotificationReadTracking notificationReadTracking = new NotificationReadTracking(
                                    ((MainActivity) getActivity()).student.getStudentID(),
                                    sdf.format(cal.getTime())
                            );
                            notificationReadTracking.setStudID(Integer.valueOf((((MainActivity) getActivity()).student.getStudentID())));


                            Log.d(context.getPackageName().toUpperCase(), notificationReadTracking.toString());

                           // new NotificationReadTrackingDAO(context).deleteAllNotificationsReadTracking();
                            new NotificationReadTrackingDAO(context).insertNotificationReadTracking(notificationReadTracking);

                            return null;
                        }
                    };
                    asyncTask.execute();
                }

                for(Notification notification : listOfNotification){

                    //set everything true;//read
                    notification.setRead(true);

                    int msgID = notification.getMsgID();

                    for(ReadNotReadNotification readNotification : notReadNotificationList){
                        if(String.valueOf(msgID).equals(readNotification.getMsgID())){
                            notification.setRead(false);
                        }
                    }

                }


                //delete records of new notifications

               new ReadNotReadNotificationDAO(context).deleteForReadNotReadNotification(((MainActivity)getActivity()).student.getStudentID());

                //     Toast.makeText(mainActivityWeakReference.get(), listOfNotification.toString(), Toast.LENGTH_SHORT).show();
                //create the an object of notificationAdapter found in NotificationFrag
               notificationAdapter = new NotificationRecycleViewAdapter(getActivity(), listOfNotification );


                recyclerView.setAdapter(notificationAdapter);
                recyclerView.scrollToPosition(listOfNotification.size() - 1);

//            if(((NotificationFrag) fragWeakReference.get()).isVisible()){
//
//            }else if(!((NotificationFrag) fragWeakReference.get()).isVisible()){
//                FragTransaction.dislayFragment(NotificationFrag.class, mainActivityWeakReference.get().fragmentManager);
//                mainActivityWeakReference.get().floatbutton.setVisibility(View.INVISIBLE);
//
//            }

                // fragWeakReference.get().notificationInstancesList = (List<Notification>) objectCarrier;


                    showProgress(false);
                recyclerView.setVisibility(View.VISIBLE);
                notification_layout_noContent.setVisibility(View.INVISIBLE);

            }else{
                showProgress(false);
                recyclerView.setVisibility(View.INVISIBLE);
                notification_layout_noContent.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(), "No Notifications to show", Toast.LENGTH_SHORT).show();
            }

            super.onPostExecute(returnResults);
        }

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            Map<String, Object>  returnResults;


          //  List<Notification> notificationList = db.getNotificationsDao().getNotifications();

            List<Notification> listNotifications = new ArrayList<>();


                //WITH THE NEW DATES THIS WILL NOT WORK
            Calendar currentDate = Calendar.getInstance();//this is todays date

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

            Date  notificationDateSent;//date notification is sent//this message isnot filterd

            String currentDateString = formatter.format(currentDate.getTime());
            String dateSentString;

            final Date currentd = new Date(currentDateString);//filterdd current date
             Date datesent;///filted date notification sent

            ///DISPLAY DATES
//            System.out.println(currentDateString);
//            System.out.println(dateSentString);



           for(Notification notification :  new NotificationDAO(context).getNotificationsList()){
                    //"9/2/2018 1:55:56 PM"
               notificationDateSent = new Date(notification.getDateSent());//date you should get form every notification
               dateSentString = formatter.format(notificationDateSent);//time sent
               datesent = new Date(dateSentString);

               long diff = Math.abs(currentd.getTime() - datesent.getTime());

               final long days = diff / (24 * 60 * 60 * 1000);

               Log.d(getActivity().getPackageName().toUpperCase(),"Days between: " + days);

               if(days >= 14){//notifications with days greater than or equal to 14 will be deleted
                   //System.out.println("Delete this message in the datebase");
                   new NotificationDAO(context).deleteOneNotification(notification.getMsgID(), ((MainActivity) getActivity()).student.getStudentID());

               }else{

                   listNotifications.add(notification);
                   //System.out.println("Don't delete this message, you can now add it to list for  display");
               }

           }

           // List<Notification> notificationList =

            //checking if there are any notification
            if(listNotifications.size()<1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_NOTIFICATION, new ArrayList<Notification>() );
                return returnResults;
            }
            //remember that this returns a list of notifications
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_NOTIFICATION, listNotifications );
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

               // Toast.makeText(getActivity(), "greater: " + billingBalance, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(( getActivity().getBaseContext()), SyncMyAccount.class);
                intent.putExtra("StudentID", ( (MainActivity) getActivity()).student.getStudentID());
                intent.putExtra("appcode", ( (MainActivity) getActivity()).student.getAppcode());
                intent.putExtra("CALL_FROM", "FRAGMENT");//AUTHENTICATE_USER

                getActivity().startActivity(intent);
                getActivity().finish();

            }else{//If the balance is less than 0.0f, no need to

              //  Toast.makeText(getActivity(), "lesser: " + billingBalance, Toast.LENGTH_LONG).show();
            }




        }


        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "Notification Fragment");

        super.onCreate(savedInstanceState);
    }







}