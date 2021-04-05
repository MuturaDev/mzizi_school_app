package ultratude.com.staff.customer_support.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import ultratude.com.staff.R;
import ultratude.com.staff.customer_support.adapters.CSFAdapter;
import ultratude.com.staff.customer_support.helper.FireBaseReferenceHelper;
import ultratude.com.staff.customer_support.helper.NetworkHelper;
import ultratude.com.staff.customer_support.helper.ProgressDialogHelper;
import ultratude.com.staff.customer_support.helper.SharedPreferenceHelper;
import ultratude.com.staff.customer_support.helper.ToastHelper;
import ultratude.com.staff.customer_support.models.CommonBean;
import ultratude.com.staff.customer_support.models.FeedbackBean;
import ultratude.com.staff.customer_support.util.Config;

public class FeedbackFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private TextView noContentLabel;
    private RecyclerView recyclerView;
    private CSFAdapter commonAdapter;
    private ArrayList<CommonBean> feedbackList;

    private ProgressDialogHelper progressDialogHelper;
    private ProgressDialog progressDialog;

    private FireBaseReferenceHelper fireBaseReferenceHelper;
    private ProgressBar progressBar;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    public static FeedbackFragment newInstance(String param1) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        feedbackList = new ArrayList<>();
        commonAdapter = new CSFAdapter(getActivity(), feedbackList);

//        progressDialogHelper = new ProgressDialogHelper(getContext(), true, true);
//        progressDialog = progressDialogHelper.getProgressDialog();

        fireBaseReferenceHelper = new FireBaseReferenceHelper(getContext());
        sharedPreferenceHelper = new SharedPreferenceHelper(getContext(), Config.SP_ROOT_NAME);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestions, container, false);

        noContentLabel = (TextView) view.findViewById(R.id.noContentLabel);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager rlm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(rlm);
        recyclerView.setAdapter(commonAdapter);

        progressBar = view.findViewById(R.id.progressBar);

        if (NetworkHelper.isNetworkAvailable(getContext())) {
            showDialog();
            fetchFeedback();
        } else {
            ToastHelper.showToast(getContext(), "Check your internet connection");
        }

        return view;
    }

    private void fetchFeedback() {
        fireBaseReferenceHelper.feedback().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    hideDialog();
                    feedbackList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FeedbackBean feedbackBean = snapshot.getValue(FeedbackBean.class);
                        if (feedbackBean != null) {
                            CommonBean commonBean = new CommonBean();
                            commonBean.setPostTitle(feedbackBean.getFeedbackTitle());
                            commonBean.setPostDescription(feedbackBean.getFeedbackDescription());
                            commonBean.setPostImage(feedbackBean.getFeedbackImage());

                            commonBean.setPostedBy(feedbackBean.getFeedbackFrom());
                            commonBean.setCustomerMobNumber(feedbackBean.getCustomerMobNumber());
                            commonBean.setPostedTime(feedbackBean.getFeedbackTimestamp());
                            commonBean.setPostedUserID(feedbackBean.getUserID());
                            commonBean.setPostStatus(feedbackBean.getFeedbackStatus());
                            commonBean.setPostResponseMessage(feedbackBean.getFeedbackResponseMessage());

                            commonBean.setActionID(feedbackBean.getFeedbackID());
                            commonBean.setActionType(Config.ACTION_TYPE_FEEDBACK);

                            //check the main student
                            String mainStudent =  sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, "0");
                            if(mainStudent.equals(feedbackBean.getUserID()))
                                feedbackList.add(commonBean);
                        }
                    }

                    if (feedbackList.size() > 0) {
                        noContentLabel.setVisibility(View.GONE);
                        Collections.reverse(feedbackList);
                        recyclerView.setVisibility(View.VISIBLE);
                        commonAdapter.notifyDataSetChanged();
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        noContentLabel.setText("No feedback to show");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                hideDialog();
                ToastHelper.showToast(getContext(), "Couldn't load feedback. Please try again");
            }
        });
    }

    private void showDialog() {
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }

        progressBar.setVisibility(View.GONE);
    }

}
