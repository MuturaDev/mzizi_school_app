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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;


public class SchoolTripFragment extends Fragment {

    private ProgressBar schoolTripProgress;
    private RecyclerView schoolTripRecyclerview;
    private LinearLayout schoolTripNoContent, schoolTripLayout;

    private LinearLayoutManager linearLayoutManager;



    public SchoolTripFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "School Trips Fragment");
        return inflater.inflate(R.layout.school_trip_fragment_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("School Trips");

        schoolTripProgress = view.findViewById(R.id.school_trip_progress);
        schoolTripRecyclerview = view.findViewById(R.id.school_trip_recycler);
        schoolTripNoContent = view.findViewById(R.id.school_trips_noContent);
        schoolTripLayout = view.findViewById(R.id.school_trip_layout);


        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        schoolTripRecyclerview.setLayoutManager(linearLayoutManager);

        // TODO: 2020-03-28 implement the pull refresh feature
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pullToRefresh.setRefreshing(false);
            }
        });

        showProgress(false);
        showNoContent(true);

//
//        AsyncTask asyncTask = new AsyncTask() {
//
//            @Override
//            protected void onPreExecute() {
//                showProgress(true);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected void onPostExecute(Object o) {
//                showProgress(false);
//
//                List<SchoolTripResponse> schoolTripResponseList  = (List<SchoolTripResponse>)o;
//                if(schoolTripResponseList != null){
//                    if(!schoolTripResponseList.isEmpty()){
//                        SchoolTripFragmentAdapter adapter = new SchoolTripFragmentAdapter(schoolTripResponseList, getActivity());
//                        schoolTripRecyclerview.setAdapter(adapter);showNoContent(false);
//                    }else{
//                        showNoContent(true);
//                    }
//                }else{
//                    showNoContent(true);
//                }
//
//                super.onPostExecute(o);
//            }
//
//            @Override
//            protected Object doInBackground(Object[] objects) {
//                return ParentMziziDatabase.getInstance(getActivity()).getSchoolTripReponseDAO().getSchoolTripResponse();
//            }
//        };
//        asyncTask.execute();


        super.onViewCreated(view, savedInstanceState);
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



            schoolTripRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
            schoolTripRecyclerview.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    schoolTripRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            schoolTripProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            schoolTripProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    schoolTripProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            schoolTripProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            schoolTripRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showNoContent(boolean show){
        if(show){
            schoolTripLayout.setVisibility(View.GONE);
            schoolTripNoContent.setVisibility(View.VISIBLE);
        }else{
            schoolTripLayout.setVisibility(View.VISIBLE);
            schoolTripNoContent.setVisibility(View.GONE);
        }
    }

//
//    private class SchoolTripFragmentAdapter extends RecyclerView.Adapter<SchoolTripFragmentAdapter.ViewHolder>{
//
//        private List<SchoolTripResponse> schoolTripResponseList;
//        private Context context;
//
//        public SchoolTripFragmentAdapter(final List<SchoolTripResponse> schoolTripResponseList,final Context context){
//            this.schoolTripResponseList = schoolTripResponseList;
//            this.context = context;
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int i) {
//            View view = LayoutInflater.from(context).inflate(R.layout.school_trip_fragment_item_layout, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
//            holder.bind(schoolTripResponseList.get(i));
//        }
//
//        @Override
//        public int getItemCount() {
//            return schoolTripResponseList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder{
//
//            private TextView
//            school_trip_name,
//            description_text,
//            term_name,
//            year_name,
//            trip_price;
//
//            private AnimateCheckBox anim_check_box;
//
//            public ViewHolder(final View view){
//                super(view);
//
//                anim_check_box = view.findViewById(R.id.anim_check_box);
//                school_trip_name = view.findViewById(R.id.school_trip_name);
//                description_text = view.findViewById(R.id.description_text);
//                term_name = view.findViewById(R.id.term_name);
//                year_name = view.findViewById(R.id.year_name);
//                trip_price = view.findViewById(R.id.trip_price);
//            }
//
//            public void bind(final SchoolTripResponse response){
//
//                anim_check_box.setUncheckStatus();
//                school_trip_name.setText(response.getName());
//                description_text.setText(response.getDescription());
//                term_name.setText(response.getTermID());
//                year_name.setText(response.getYearID());
//
//                // TODO: 2020-03-28 Format the amount
//                trip_price.setText(response.getAmountCharged());
//            }
//
//        }
//    }


}
