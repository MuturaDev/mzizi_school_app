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
import android.widget.ProgressBar;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.helperactivityclasses.PortalTransportRoutesAdapter;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;


public class TransportRoutesFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ParentMziziDatabase db;

    public RecyclerView recyclerView;
    public ProgressBar diary_progress;
    public SwipeRefreshLayout pullToRefresh;

    static FragmentManager fragmentManager;

    public TransportRoutesFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "TransportRoutes Fragment");
        return inflater.inflate(R.layout.transport_routes_layout_frag, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Transport Routes");

        db = ParentMziziDatabase.getInstance(getContext());

        recyclerView = getActivity().findViewById(R.id.tranport_route_recycler);
        diary_progress = getActivity().findViewById(R.id.transport_route_progress);
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

       new GetPortalTransportRoutesFees(this.getActivity(), TransportRoutesFragment.this).execute();

        super.onActivityCreated(savedInstanceState);
    }


    class GetPortalTransportRoutesFees extends AsyncTask<Void,Void, List<PortalTransportRoutesResponse>> {

        private Context context;
        private TransportRoutesFragment fragment;


        public GetPortalTransportRoutesFees(Context context, TransportRoutesFragment fragment){
            this.context = context;
            this.fragment = fragment;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
            getActivity().findViewById(R.id.transport_routes_layout_noContent).setVisibility(View.INVISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<PortalTransportRoutesResponse> returnResults) {

            //START
            List<PortalTransportRoutesResponse> portalTransportRoutesResponseList = returnResults;

            if(portalTransportRoutesResponseList != null) {

                if (portalTransportRoutesResponseList.size() > 0) {
                    showProgress(false);

                    PortalTransportRoutesAdapter adapter = new PortalTransportRoutesAdapter(portalTransportRoutesResponseList, fragment.getActivity());
                    recyclerView.setAdapter(adapter);

                } else {
                    getActivity().findViewById(R.id.transport_routes_layout_noContent).setVisibility(View.VISIBLE);
                    showProgress(false);
                }
            }else{
                getActivity().findViewById(R.id.transport_routes_layout_noContent).setVisibility(View.VISIBLE);
                showProgress(false);
            }

            //TODO: SHOW WHEN NULL

            super.onPostExecute(returnResults);
        }

        @Override
        protected List<PortalTransportRoutesResponse> doInBackground(Void... voids) {

            ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
            if(studentid == null){
                studentid  = "0";
            }
            return   db.getPortalTransportRoutesResponseDAO().getPortalTransportRoutesResponse(Integer.valueOf(studentid));

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


    public static TransportRoutesFragment newInstance(String param1, String param2, FragmentManager fragmentManager) {
        TransportRoutesFragment.fragmentManager = fragmentManager;

        TransportRoutesFragment fragment = new TransportRoutesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}
