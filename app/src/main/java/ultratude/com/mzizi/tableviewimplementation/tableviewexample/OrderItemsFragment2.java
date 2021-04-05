package ultratude.com.mzizi.tableviewimplementation.tableviewexample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.ListItem;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.tableviewimplementation.TableView;
import ultratude.com.mzizi.tableviewimplementation.filter.Filter;
import ultratude.com.mzizi.tableviewimplementation.pagination.Pagination;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.TableViewAdapter;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.TableViewListener;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.TableViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderItemsFragment2 extends Fragment {
    private Spinner moodFilter, genderFilter;
    private ImageButton previousButton, nextButton;
    private TextView tablePaginationDetails;
    private TableView mTableView;
    private Filter mTableFilter; // This is used for filtering the table.
    private Pagination mPagination; // This is used for paginating the table.


    private boolean mPaginationEnabled = false;

    private TextView checkoutCountText,checkoutCountTextBottomSheet;

    //bottom sheet
    private FloatingActionButton fab;
    private CardView layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    //view in the bottoms sheet
    private CardView back_btn_layout, clear_cat_btn_layout;
    private TextView no_content_text;
    private LinearLayout tableview_section_layout_ID;
    private TableView tableView;

    private RelativeLayout fab_layout;

    private LinearLayout order_items_progress;


    public OrderItemsFragment2() {
        // Required empty public constructor
    }

    private void showNoContent(boolean show){
        if(show){
            no_content_text.setVisibility(View.VISIBLE);

            tableview_section_layout_ID.setVisibility(View.INVISIBLE);

        }else{
            no_content_text.setVisibility(View.INVISIBLE);
            tableview_section_layout_ID.setVisibility(View.VISIBLE);

        }
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



            tableview_section_layout_ID.setVisibility(show ? View.GONE : View.VISIBLE);
            tableview_section_layout_ID.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tableview_section_layout_ID.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });


            order_items_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            order_items_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    order_items_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            order_items_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            tableview_section_layout_ID.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().setTitle("Order Items");

        EditText searchField = layout.findViewById(R.id.query_string);
        searchField.addTextChangedListener(mSearchTextWatcher);

        moodFilter = layout.findViewById(R.id.mood_spinner);
        moodFilter.setOnItemSelectedListener(mItemSelectionListener);

        genderFilter = layout.findViewById(R.id.gender_spinner);
        genderFilter.setOnItemSelectedListener(mItemSelectionListener);

        Spinner itemsPerPage = layout.findViewById(R.id.items_per_page_spinner);

        View tableTestContainer = layout.findViewById(R.id.table_test_container);

        previousButton = layout.findViewById(R.id.previous_button);
        nextButton = layout.findViewById(R.id.next_button);
        EditText pageNumberField = layout.findViewById(R.id.page_number_text);
        tablePaginationDetails = layout.findViewById(R.id.table_details);

        fab_layout = layout.findViewById(R.id.fab_layout);

        order_items_progress = layout.findViewById(R.id.order_items_progress);

        if (mPaginationEnabled) {
            tableTestContainer.setVisibility(View.VISIBLE);
            itemsPerPage.setOnItemSelectedListener(onItemsPerPageSelectedListener);

            previousButton.setOnClickListener(mClickListener);
            nextButton.setOnClickListener(mClickListener);
            pageNumberField.addTextChangedListener(onPageTextChanged);
        } else {
            tableTestContainer.setVisibility(View.GONE);
        }

        // Let's get TableView
        mTableView = layout.findViewById(R.id.tableview);

        checkoutCountText = layout.findViewById(R.id.checkoutCountText);
        checkoutCountTextBottomSheet = layout.findViewById(R.id.checkoutCountTextBottomSheet);

        initializeTableView();

        if (mPaginationEnabled) {
            mTableFilter = new Filter(mTableView); // Create an instance of a Filter and pass the
            // created TableView.

            // Create an instance for the TableView pagination and pass the created TableView.
            mPagination = new Pagination(mTableView);

            // Sets the pagination listener of the TableView pagination to handle
            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
        }

        //BOTTOM SHEET

        no_content_text = layout.findViewById(R.id.bottom_sheet_no_content_text);
        tableview_section_layout_ID = layout.findViewById(R.id.tableview_section_layout_ID);
        tableView = layout.findViewById(R.id.tableview);

        layoutBottomSheet = layout.findViewById(R.id.order_bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        back_btn_layout = layout.findViewById(R.id.back_btn_layout);
        back_btn_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);//3
                fab_layout.setVisibility(View.VISIBLE);

            }
        });
        clear_cat_btn_layout = layout.findViewById(R.id.clear_cat_btn_layout);
        clear_cat_btn_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2020-03-30 DO MORE...Clear bottom sheet's tableview 
                TableViewAdapter.bindData.clear();
                initializeTableView();
                checkoutCountTextBottomSheet.setText("0");
                checkoutCountText.setText("0");

            }
        });

        /**
         * Bottom sheet state change listener.
         * We are changing bottom text when sheet changed state*/
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState){
                    case BottomSheetBehavior.STATE_HIDDEN:
                        fab_layout.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        //show floating action button
                        fab_layout.setVisibility(View.GONE);
                        //label_change_bottom_sheet_state_ID.setText("Close Comment Section");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //show floating action button
                        fab_layout.setVisibility(View.VISIBLE);
                        //label_change_bottom_sheet_state_ID.setText("Show Comment Section");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        List<ListItem>  list = TableViewModel.getTableData();

        TextView noContent = layout.findViewById(R.id.txt_no_content_to_show);
        if(list.size() == 0){
            noContent.setVisibility(View.VISIBLE);
        }else{
            noContent.setVisibility(View.GONE);
        }




        fab = layout.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                fab_layout.setVisibility(View.GONE);

                //if()
                showProgress(false);
                showNoContent(true);

                 List<ListItem>  list = TableViewModel.getTableData();


                Map<Integer, Boolean> bindData = TableViewAdapter.bindData;
                Map<Integer, String> bindDataQuantity =  TableViewAdapter.bindDataQuantity;
                StringBuilder sBuilder = new StringBuilder();

                    if(!list.isEmpty() && bindData.isEmpty()){
                        for(int i = 0; i < bindData.size(); i++) {

                            if (i == 0)
                                sBuilder.append(Arrays.toString(list.get(i).getColumnHeaderList()) + "\n");

                            boolean isChecked = bindData.get(i);
                            if (isChecked) {
                                String[] item = list.get(i).getRowValueList();
                                if (bindDataQuantity.isEmpty())
                                    item[3] = "1";
                                else

                                    if(bindDataQuantity.containsKey(i))
                                        item[3] = bindDataQuantity.get(i);
                                    else
                                        item[3] = "1";


                                sBuilder.append(Arrays.toString(item));

                            }
                        }
                    }

                no_content_text.setText(sBuilder.toString());
            }

        });


        ReportAnalytics.reportScreenChangeAnalytic(getActivity(), "OrderItems Fragment");



        return layout;
    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        // TODO: 2020-03-29 implement for each fragment or activity layout you are implementing the TableView , if there are changes being implemented.

        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel, getActivity(),checkoutCountText,checkoutCountTextBottomSheet);


        mTableView.setAdapter(tableViewAdapter);
        // TODO: 2020-03-29 For handling table events use a difference TableViewListener, ofcourse with a difference name,, according to the class

        mTableView.setTableViewListener(new TableViewListener(mTableView));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        // TODO: 2020-03-29 This is the place to modify
        tableViewAdapter.setAllItems(tableViewModel.getColumnHeaderList(), tableViewModel
                .getRowHeaderList(), tableViewModel.getCellList());





        //This is not working
//        mTableView.setHasFixedWidth(true);
//
//        for (int i = 0; i < tableViewModel.getCellList().size(); i++) {
//            mTableView.setColumnWidth(i, 200);
//        }
//
//        mTableView.setColumnWidth(0, -2);
//        mTableView.setColumnWidth(1, -2);
//
//        mTableView.setColumnWidth(2, 200);
//        mTableView.setColumnWidth(3, 300);
//        mTableView.setColumnWidth(4, 400);
//        mTableView.setColumnWidth(5, 500);

    }

    public void filterTable(@NonNull String filter) {
        // Sets a filter to the table, this will filter ALL the columns.
        mTableFilter.set(filter);
    }

    public void filterTableForMood(@NonNull String filter) {
        // Sets a filter to the table, this will only filter a specific column.
        // In the example data, this will filter the mood column.
        mTableFilter.set(TableViewModel.MOOD_COLUMN_INDEX, filter);
    }

    public void filterTableForGender(@NonNull String filter) {
        // Sets a filter to the table, this will only filter a specific column.
        // In the example data, this will filter the gender column.
        mTableFilter.set(TableViewModel.GENDER_COLUMN_INDEX, filter);
    }

    // The following four methods below: nextTablePage(), previousTablePage(),
    // goToTablePage(int page) and setTableItemsPerPage(int itemsPerPage)
    // are for controlling the TableView pagination.
    public void nextTablePage() {
        mPagination.nextPage();
    }

    public void previousTablePage() {
        mPagination.previousPage();
    }

    public void goToTablePage(int page) {
        mPagination.goToPage(page);
    }

    public void setTableItemsPerPage(int itemsPerPage) {
        mPagination.setItemsPerPage(itemsPerPage);
    }

    // Handler for the changing of pages in the paginated TableView.
    @NonNull
    private Pagination.OnTableViewPageTurnedListener onTableViewPageTurnedListener = new
            Pagination.OnTableViewPageTurnedListener() {
                @Override
                public void onPageTurned(int numItems, int itemsStart, int itemsEnd) {
                    int currentPage = mPagination.getCurrentPage();
                    int pageCount = mPagination.getPageCount();
                    previousButton.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);

                    if (currentPage == 1 && pageCount == 1) {
                        previousButton.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == 1) {
                        previousButton.setVisibility(View.INVISIBLE);
                    }

                    if (currentPage == pageCount) {
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                    tablePaginationDetails.setText(getString(R.string.table_pagination_details, String
                            .valueOf(currentPage), String.valueOf(itemsStart), String.valueOf(itemsEnd)));

                }
            };

    @NonNull
    private AdapterView.OnItemSelectedListener mItemSelectionListener = new AdapterView
            .OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // 0. index is for empty item of spinner.
            if (position > 0) {

                String filter = Integer.toString(position);

                if (parent == moodFilter) {
                    filterTableForMood(filter);
                } else if (parent == genderFilter) {
                    filterTableForGender(filter);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Left empty intentionally.
        }
    };

    @NonNull
    private TextWatcher mSearchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            filterTable(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @NonNull
    private AdapterView.OnItemSelectedListener onItemsPerPageSelectedListener = new AdapterView
            .OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int itemsPerPage;
            if ("All".equals(parent.getItemAtPosition(position).toString())) {
                itemsPerPage = 0;
            } else {
                itemsPerPage = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            setTableItemsPerPage(itemsPerPage);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @NonNull
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == previousButton) {
                previousTablePage();
            } else if (v == nextButton) {
                nextTablePage();
            }
        }
    };

    @NonNull
    private TextWatcher onPageTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int page;
            if (TextUtils.isEmpty(s)) {
                page = 1;
            } else {
                page = Integer.parseInt(String.valueOf(s));
            }

            goToTablePage(page);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
