package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.helperactivityclasses.PortalOptionalFeesAdapter;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;

public class OptionalFeesFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ParentMziziDatabase db;

    public RecyclerView recyclerView;
    public ProgressBar diary_progress;
    public SwipeRefreshLayout pullToRefresh;

    static FragmentManager fragmentManager;

    public OptionalFeesFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "OptionalFees Fragment");
        return inflater.inflate(R.layout.optional_fees_layout_frag, container, false);
    }

    LinearLayout optional_fee_layout_noContent;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Optional Fee");

        db = ParentMziziDatabase.getInstance(getContext());

        recyclerView = getActivity().findViewById(R.id.optional_recycler);
        optional_fee_layout_noContent =  getActivity().findViewById(R.id.optional_fee_layout_noContent);
        diary_progress = getActivity().findViewById(R.id.optional_progress);
        pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pullToRefresh.setRefreshing(false);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.hasFixedSize();


        new GetPortalOptionalFees(this.getActivity(), OptionalFeesFragment.this).execute();

        super.onActivityCreated(savedInstanceState);
    }

    class GetPortalOptionalFees extends AsyncTask<Void,Void, List<PortalOptionalFeesResponse>> {

        private Context context;
        private OptionalFeesFragment fragment;


        public GetPortalOptionalFees(Context context, OptionalFeesFragment fragment){
            this.context = context;
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
            optional_fee_layout_noContent.setVisibility(View.INVISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<PortalOptionalFeesResponse> returnResults) {

            //START
            List<PortalOptionalFeesResponse> portalDetailedToDoListResponseList = returnResults;

            if(portalDetailedToDoListResponseList != null){
                if(portalDetailedToDoListResponseList.size() > 0){
                    showProgress(false);

                    PortalOptionalFeesAdapter adapter = new PortalOptionalFeesAdapter(portalDetailedToDoListResponseList, fragment.getActivity());
                    recyclerView.setAdapter(adapter);

                }
                else{
                    showProgress(false);
                    optional_fee_layout_noContent.setVisibility(View.VISIBLE);
                }

            }else{
                optional_fee_layout_noContent.setVisibility(View.VISIBLE);
                showProgress(false);
            }




            super.onPostExecute(returnResults);
        }

        @Override
        protected List<PortalOptionalFeesResponse> doInBackground(Void... voids) {
            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }
           return db.getPortalOptionalFeesResponseDAO().getPortalOptionalFeesResponse(Integer.valueOf(studentid));
        }

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {

        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            int shortAnimTime = 0;

            if (isAdded())
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


    public static OptionalFeesFragment newInstance(String param1, String param2, FragmentManager fragmentManager) {
        OptionalFeesFragment.fragmentManager = fragmentManager;

        OptionalFeesFragment fragment = new OptionalFeesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}
