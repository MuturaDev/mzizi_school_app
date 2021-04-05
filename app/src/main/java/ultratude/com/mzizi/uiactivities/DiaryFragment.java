package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adriangl.overlayhelper.OverlayHelper;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.floatingview.FloatingViewManager;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.floating_view.ChatHeadService;
import ultratude.com.mzizi.floating_view.CustomFloatingViewService;
import ultratude.com.mzizi.floating_view.fragment.FloatingViewControlFragment;
import ultratude.com.mzizi.helperactivityclasses.PortalDetailedToDoListResponseAdapter;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.staff.activities.DailyTransport;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by James on 01/08/2018.
 */

public class DiaryFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ParentMziziDatabase db;

    public RecyclerView recyclerView;
    public ProgressBar diary_progress;
    public SwipeRefreshLayout pullToRefresh;
    static FragmentManager fragmentManager;




    public DiaryFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "Diary Fragment");
        return inflater.inflate(R.layout.diary_layout, container, false);
    }






    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {





        getActivity().setTitle("Diary");
        db = ParentMziziDatabase.getInstance(getContext());

        recyclerView = getActivity().findViewById(R.id.diary_recycler);
        diary_progress = getActivity().findViewById(R.id.diary_progress);
        pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pullToRefresh.setRefreshing(false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        new RetrieveDataForDiary(getActivity(), this).execute();

       final Context context = getActivity();




        super.onActivityCreated(savedInstanceState);
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


            recyclerView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    recyclerView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                }
            });


            diary_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    diary_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            diary_progress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            recyclerView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
        }
    }



    public class RetrieveDataForDiary extends AsyncTask<Void,Void, List<PortalDetailedToDoListResponse>> {

        private Context context;
        private DiaryFragment fragment;


        public RetrieveDataForDiary(Context context, DiaryFragment fragment){
            this.context = context;
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<PortalDetailedToDoListResponse> returnResults) {
            //START
            List<PortalDetailedToDoListResponse> portalDetailedToDoListResponseList = returnResults;

            if(portalDetailedToDoListResponseList != null){
                if(portalDetailedToDoListResponseList.size() > 0){
                    int scrollToPosition = 0;

                    List<PortalDetailedToDoListResponse> adapterList = new ArrayList<>();

                    if(DiaryFragment.this.getArguments() != null){

                        if(DiaryFragment.this.getArguments().getString("message").contains("Highlight")){
                            String message = DiaryFragment.this.getArguments().getString("message").replace("Highlight","");
                            for(int i = 0; i < portalDetailedToDoListResponseList.size(); i++){
                                portalDetailedToDoListResponseList.get(i).setTobeHighlited(false);
                                if (portalDetailedToDoListResponseList.get(i).getName().contains(message)) {
                                    scrollToPosition = i;
                                    portalDetailedToDoListResponseList.get(i).setTobeHighlited(true);
                                }

                                adapterList.add(portalDetailedToDoListResponseList.get(i));

                            }

                            PortalDetailedToDoListResponseAdapter portalDetailedToDoListResponseAdapter = new PortalDetailedToDoListResponseAdapter(adapterList, getActivity(), DiaryFragment.this);

                            recyclerView.setAdapter(portalDetailedToDoListResponseAdapter);
                            recyclerView.scrollToPosition(scrollToPosition);

                        }else{

                            for(int i = 0; i< portalDetailedToDoListResponseList.size(); i++) {
                                if (portalDetailedToDoListResponseList.get(i).getName().contains(DiaryFragment.this.getArguments().getString("message").toUpperCase())) {
                                    adapterList.add(portalDetailedToDoListResponseList.get(i));
                                }

                            }

                            PortalDetailedToDoListResponseAdapter portalDetailedToDoListResponseAdapter = new PortalDetailedToDoListResponseAdapter(adapterList, getActivity(),DiaryFragment.this);

                            recyclerView.setAdapter(portalDetailedToDoListResponseAdapter);
                            recyclerView.scrollToPosition(scrollToPosition);
                        }
                    }

                    showProgress(false);

                }else{
                    showProgress(false);
                    // Toast.makeText(context, "No fee transactions to show", Toast.LENGTH_SHORT);
                    //do nothing if there is no data
                }
            }

            //END
            super.onPostExecute(returnResults);
        }

        @Override
        protected List<PortalDetailedToDoListResponse> doInBackground(Void... voids) {
            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }
            return db.getPortalDetailedToDoListResponseDAO().getPPortalDetailedToDoListResponse(Integer.valueOf(studentid));
        }

    }


    public static DiaryFragment newInstance(String param1, String param2, FragmentManager fragmentManager) {
        DiaryFragment.fragmentManager = fragmentManager;

        DiaryFragment fragment = new DiaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




}
