package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.R;
//import ultratude.com.mzizi.modelclasses.response.OrderItemsHistory;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.SampleTableViewActivity;

public class OrderItemsFragment extends Fragment {

    private ProgressBar orderItemsProgress;
    private RecyclerView orderItemsRecyclerview;
    private LinearLayout orderItemsNoContent, orderItemsLayout;

    private LinearLayoutManager linearLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_items_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Order Items");

//        orderItemsProgress = view.findViewById(R.id.orderItemsProgress);
//        orderItemsRecyclerview = view.findViewById(R.id.orderItemsRecyclerview);
//        orderItemsNoContent = view.findViewById(R.id.orderItemsNoContent);
//        orderItemsLayout = view.findViewById(R.id.orderItemsLayout);

        Button btn  = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SampleTableViewActivity.class);
                startActivity(intent);
            }
        });

//        linearLayoutManager =
//                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        orderItemsRecyclerview.setLayoutManager(linearLayoutManager);
//
//        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//                pullToRefresh.setRefreshing(false);
//            }
//        });


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



            orderItemsRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
            orderItemsRecyclerview.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    orderItemsRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            orderItemsProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            orderItemsProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    orderItemsProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            orderItemsProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            orderItemsRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void showNoContent(boolean show){
        if(show){
            orderItemsLayout.setVisibility(View.GONE);
            orderItemsNoContent.setVisibility(View.VISIBLE);
        }else{
            orderItemsLayout.setVisibility(View.VISIBLE);
            orderItemsNoContent.setVisibility(View.GONE);
        }
    }


//
//    private class OrderItemsFragmentAdapter extends RecyclerView.Adapter<OrderItemsFragmentAdapter.ViewHolder>{
//
//        private List<OrderItemsHistory> orderItemsHistoryList;
//        private Context context;
//
//        public OrderItemsFragmentAdapter(Context context, List<OrderItemsHistory> orderItemsHistoryList){
//            this.orderItemsHistoryList = orderItemsHistoryList;
//            this.context = context;
//        }
//
//        @NonNull
//        @Override
//        public OrderItemsFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////            View view = LayoutInflater.from(context).inflate(R.layout., parent, false);
////            return new ViewHolder(view);
//            return null;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull OrderItemsFragmentAdapter.ViewHolder holder, int i) {
//            holder.bind(orderItemsHistoryList.get(i));
//        }
//
//        @Override
//        public int getItemCount() {
//            return orderItemsHistoryList.size();
//        }
//
//        public class ViewHolder extends RecyclerView.ViewHolder{
//
//            public ViewHolder(final View view){
//                super(view);
//
//            }
//
////            public void bind(OrderItemsHistory response){
////
////            }
//
//
//        }
//    }


}
