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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.helperactivityclasses.UpcomingEventsRecyclerAdapter;
import ultratude.com.mzizi.modelclasses.response.BorrowedBooksResponse;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;

public class BorrowedBooksFragment extends Fragment {

    private ProgressBar borrowedBookProgress;
    private RecyclerView borrowedBooksRecyclerview;
    private LinearLayout borrowedBooksNoContent, borrowedBooksLayout;

    private LinearLayoutManager linearLayoutManager;



    public BorrowedBooksFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "BorrowedBooks Fragment");
        return inflater.inflate(R.layout.borrowed_books_fragment_layout, container, false);
    }

    private UtilityFunctions utilityFunctions;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 0;

            if(isAdded())
                shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);



            borrowedBooksRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
            borrowedBooksRecyclerview.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    borrowedBooksRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            borrowedBookProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            borrowedBookProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    borrowedBookProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            borrowedBookProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            borrowedBooksRecyclerview.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
    
    private void showNoContent(boolean show){
        if(show){
            borrowedBooksLayout.setVisibility(View.GONE);
            borrowedBooksNoContent.setVisibility(View.VISIBLE);
        }else{
            borrowedBooksLayout.setVisibility(View.VISIBLE);
            borrowedBooksNoContent.setVisibility(View.GONE);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        borrowedBookProgress = view.findViewById(R.id.borrowed_books_progress);

        getActivity().setTitle("Borrowed Books");

        utilityFunctions = new UtilityFunctions(getActivity());
        borrowedBooksRecyclerview = view.findViewById(R.id.borrowed_books_recycler);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        borrowedBooksRecyclerview.setLayoutManager(linearLayoutManager);

        borrowedBooksNoContent = view.findViewById(R.id.borrowed_books_noContent);
        borrowedBooksLayout = view.findViewById(R.id.borrowed_book_layout);


        // TODO: 2020-03-28 implement the pull refresh feature 
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pullToRefresh.setRefreshing(false);
            }
        });


        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                showProgress(true);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                
                showProgress(false);
                
                List<BorrowedBooksResponse> borrowedBooksResponseList = (List<BorrowedBooksResponse>) o;
                if(borrowedBooksResponseList != null){
                    if(!borrowedBooksResponseList.isEmpty()){
                        BorrowedBooksFragmentAdapter adapter = new BorrowedBooksFragmentAdapter(getActivity(), borrowedBooksResponseList);
                        borrowedBooksRecyclerview.setAdapter(adapter);
                        borrowedBooksRecyclerview.smoothScrollToPosition(borrowedBooksResponseList.size() -1);
                    
                        showNoContent(false);
                    }else{
                        showNoContent(false);
                    }
                    
                }else{
                   showNoContent(true);
                }

                showProgress(false);

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return db.getBorrowedBooksResponseDAO().getBorrowedBooksResponse(Integer.valueOf(studentid));
            }
        };
        asyncTask.execute();

        super.onViewCreated(view, savedInstanceState);
    }


    private class BorrowedBooksFragmentAdapter extends RecyclerView.Adapter<BorrowedBooksFragmentAdapter.ViewHolder>{


        private List<BorrowedBooksResponse> borrowedBooksResponseList;
        private Context context;

        public BorrowedBooksFragmentAdapter(final Context context, final List<BorrowedBooksResponse> borrowedBooks){
            this.borrowedBooksResponseList = borrowedBooks;
            this.context = context;
        }

        @NonNull
        @Override
        public BorrowedBooksFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.borrowed_books_item_fragment_layout, parent, false);
            return new BorrowedBooksFragmentAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            holder.bind(borrowedBooksResponseList.get(i));
        }

        @Override
        public int getItemCount() {
            return borrowedBooksResponseList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView booktitle_text,bookno_text,category_text,
                type_text, publisher_text,
                date_borrowed_text,
                date_returned_text,
                unit_text;

            public ViewHolder(final View view){
                super(view);

                booktitle_text = view.findViewById(R.id.booktitle_text);
                bookno_text = view.findViewById(R.id.bookno_text);
                category_text = view.findViewById(R.id.category_text);
                type_text = view.findViewById(R.id.type_text);
                publisher_text = view.findViewById(R.id.publisher_text);
                date_borrowed_text = view.findViewById(R.id.date_borrowed_text);
                date_returned_text = view.findViewById(R.id.date_returned_text);
                unit_text = view.findViewById(R.id.unit_text);
            }

            public void bind(final BorrowedBooksResponse response){
                booktitle_text.setText(response.getBookName());
                bookno_text.setText(response.getBookNo());
                category_text.setText(response.getCategoryName());
                type_text.setText(response.getTypeName());
                publisher_text.setText(response.getPublisherName());
                date_borrowed_text.setText(response.getDateBorrowed());
                date_returned_text.setText(response.getDateReturned());
                unit_text.setText(response.getUnitName());
            }


        }
    }

}
