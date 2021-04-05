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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.helperactivityclasses.SchoolContactsRecycler;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;

/**
 * Created by James on 31/07/2018.
 */

public class SchoolContactsFrag extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public ParentMziziDatabase db;

    public LinearLayoutManager linearLayoutManager;

    public RecyclerView recyclerView;
    public LinearLayout school_contact_layout;
    public LinearLayout school_contact_noContent;
    public SchoolContactsRecycler schoolContactsRecycler;

    public ProgressBar school_contacts_progressbar;



    public SchoolContactsFrag(){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getActivity().setTitle("School Contacts");

        school_contacts_progressbar = getActivity().findViewById(R.id.school_contacts_progress);

        db = ParentMziziDatabase.getInstance(getContext());


        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView = getActivity().findViewById(R.id.school_contacts_recycler);
        school_contact_noContent = getActivity().findViewById(R.id.school_contacts_noContent);
        school_contact_layout = getActivity().findViewById(R.id.school_contact_layout);



        recyclerView.setLayoutManager(linearLayoutManager);




       new RetrieveDataSchoolContactsFrag(this.getActivity()).execute();
        //new XFragRetrieveAndDisplayRoomData(this, ( (MainActivity) getActivity()), "").execute(GetConstants.GET_PORTAL_CONTACTS);


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


            school_contacts_progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
            school_contacts_progressbar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    school_contacts_progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            school_contacts_progressbar.setVisibility(show ? View.VISIBLE : View.GONE);
            recyclerView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            //return inflater.inflate(R.layout.school_contacts, container, false);

            return inflater.inflate(R.layout.school_contacts, container, false);



        }


        public class RetrieveDataSchoolContactsFrag extends AsyncTask<Void,Void, Map<String, Object>> {

            public Context context;

            public RetrieveDataSchoolContactsFrag(Context context) {
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

                List<PortalContacts> portalContactsList = (List<PortalContacts>) returnResults.get(GetConstants.GET_PORTAL_CONTACTS);

                if (!portalContactsList.isEmpty()) {


                    schoolContactsRecycler = new SchoolContactsRecycler(getActivity(), portalContactsList);

                    recyclerView.setAdapter(schoolContactsRecycler);
                    recyclerView.scrollToPosition(portalContactsList.size() - 1);

                    school_contact_noContent.setVisibility(View.INVISIBLE);
                    school_contact_layout.setVisibility(View.VISIBLE);


                } else {

                    school_contact_noContent.setVisibility(View.VISIBLE);
                    school_contact_layout.setVisibility(View.INVISIBLE);
                 //   Toast.makeText(getActivity(), "No school contacts to show", Toast.LENGTH_SHORT).show();
                }


                super.onPostExecute(returnResults);
            }

            @Override
            protected Map<String, Object> doInBackground(Void... voids) {

                ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                Map<String, Object> returnResults;
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<PortalContacts> portalContactsList = db.getPortalContactsDao().getPortalContacts(Integer.valueOf(studentid));

                if (portalContactsList.size() < 1) {
                    returnResults = new HashMap<>();
                    returnResults.put(GetConstants.GET_PORTAL_CONTACTS, new ArrayList<PortalContacts>());
                    return returnResults;
                }

                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_CONTACTS, portalContactsList);

                return returnResults;//will not contain any of the above keys
            }
        }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

      //  Toast.makeText(getActivity(), "OnCreate", Toast.LENGTH_LONG).show();

        //to the student id, you have to create a back ground task

        SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(getActivity()).getFilteredPortalStudentInfoResult();
        String billingBalance = syncMyAccountResult.getBillingBalance();
        if(billingBalance.equals("")){//equal to an empty string
            //do nothing
        }else if(!billingBalance.equals("")){//not equal to an empty string

            if(Float.valueOf(billingBalance) > 0f){

//                Toast.makeText(getActivity(), "greater: " + billingBalance, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(( getActivity().getBaseContext()), SyncMyAccount.class);
                intent.putExtra("StudentID", ( (MainActivity) getActivity()).student.getStudentID());
                intent.putExtra("appcode", ( (MainActivity) getActivity()).student.getAppcode());
                intent.putExtra("CALL_FROM", "FRAGMENT");//AUTHENTICATE_USER

                getActivity().startActivity(intent);
                getActivity().finish();

            }else{//If the balance is less than 0.0f, no need to

               // Toast.makeText(getActivity(), "lesser: " + billingBalance, Toast.LENGTH_LONG).show();
            }

            ReportAnalytics.reportScreenChangeAnalytic(getContext(), "SchoolContacts Fragment");

        }




        super.onCreate(savedInstanceState);
    }










}
