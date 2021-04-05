package ultratude.com.mzizi.uiactivities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.modelclasses.Activities;
import ultratude.com.mzizi.modelclasses.CommentSection;
import ultratude.com.mzizi.modelclasses.LearningAreas;
import ultratude.com.mzizi.modelclasses.NewCarriculumExam;
import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.spinnerdao.ExamTypeSpinnerDAO;
import ultratude.com.mzizi.spinnerdao.TermSpinnerDAO;
import ultratude.com.mzizi.spinnerdao.YearSpinnerDAO;
import ultratude.com.staff.spinnermodel.ExamTypeSpinner;
import ultratude.com.staff.spinnermodel.TermSpinner;
import ultratude.com.staff.spinnermodel.YearSpinner;

/**
 * Created by James on 18/05/2019.
 */

public class NewCarricullumActivity extends Fragment {

    static FragmentManager fragmentManager;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ImageView image_sp_exam_type_ID, image_sp_year_ID, image_sp_term_ID;
    private Spinner sp_term_ID, sp_year_ID, sp_exam_type_ID;
    private LinearLayout pb_loading_txt_newCarriculum_progress;
    private RecyclerView recycler_newCarriculum_exam_ID;

    private LinearLayout pb_newCarriculum_progress_layout_exam_type, pb_newCarriculum_progress_layout_year,pb_newCarriculum_progress_layout_term;

    private YearSpinner yearSelected = null;
    private TermSpinner termSelected = null;
    private ExamTypeSpinner examTypeSelected = null;

    private TextView txt_confirmationMessage;
    private TextView txt_confirmationMessage2;

    private TextView txt_no_exam_progress_toshow_ID;


    //COMMENTS BOTTOM SHEET
    public LinearLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private TextView label_change_bottom_sheet_state_ID;
    private LinearLayout btn_change_bottom_sheet_state_ID;

    //comments
    private TextView label_head_teacher_comment,label_grade_facilitator_comment;
    private TextView txt_head_teacher_comment_ID,txt_grade_facilitator_comment;
    private LinearLayout comments_section_layout_ID;
    private LinearLayout pb_loading_txt_manageDiscipline_progress;

   private TextView txt_no_comments_toshow_ID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.new_carricullum_activity_layout, container, false);

        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "ProgressReport Fragment");

        return view;
    }


    private boolean checkYear(YearSpinner yearSpinner){
        if(yearSpinner == null){
            txt_confirmationMessage2.setText("Please specify Year");
            return false;
        }
        return true;
    }
    private boolean checkTerm(TermSpinner termSpinner){
        if(termSpinner == null){
            txt_confirmationMessage2.setText("Please specify Term");
            return false;
        }

        return true;

    }
    private boolean checkExam(ExamTypeSpinner examTypeSpinner){
        if(examTypeSpinner == null){
            txt_confirmationMessage2.setText("Please specify Exam Type");
            return false;
        }

        return true;

    }




    private void sendRequestForPortalProgressReport(final Student student, final Context mContext){
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<List<NewCarriculumExam>> userCall = apiInterface.getPortalProgressReport(student);
        userCall.enqueue(new Callback<List<NewCarriculumExam>>() {
            @Override
            public void onResponse(Call<List<NewCarriculumExam>> call, Response<List<NewCarriculumExam>> response) {

                List<NewCarriculumExam> resultsList = response.body();

                if (response.code() == 200) {

                    if (resultsList != null) {

                        // if (resultsList.size() > 0) {

                        for(NewCarriculumExam res : response.body()){
                            res.setStudID(Integer.valueOf(student.getStudentID()));
                        }
                        new NewCarriculumExamDAO(mContext).insertNewCarriculumExamFormat(resultsList);

                        Log.d(getActivity().getPackageName().toUpperCase(), resultsList.toString());

                        displayRecyclerViewData();
                        pullToRefresh.setRefreshing(false);
                    }

                } else {
                    displayRecyclerViewData();
                    pullToRefresh.setRefreshing(false);
                }

            }

            @Override
            public void onFailure(Call<List<NewCarriculumExam>> call, Throwable t) {
                //  Toast.makeText(mainActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });
    }

    SwipeRefreshLayout pullToRefresh;
    UtilityFunctions utilityFunctions;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Progress Report");

        utilityFunctions = new UtilityFunctions(getActivity());

        //COMMENTS
        //start of comments
        txt_no_comments_toshow_ID = getActivity().findViewById(R.id.txt_no_comments_toshow_ID);
        // comments_layout_ID = findViewById(R.id.comments_layout_ID);
        label_head_teacher_comment = getActivity().findViewById(R.id.label_head_teacher_comment);
        label_grade_facilitator_comment = getActivity().findViewById(R.id.label_grade_facilitator_comment);
        txt_head_teacher_comment_ID = getActivity().findViewById(R.id.txt_head_teacher_comment_ID);
        txt_grade_facilitator_comment = getActivity().findViewById(R.id.txt_grade_facilitator_comment);

        comments_section_layout_ID = getActivity().findViewById(R.id.comments_section_layout_ID);
        pb_loading_txt_manageDiscipline_progress = getActivity().findViewById(R.id.pb_loading_txt_manageDiscipline_progress);

        btn_change_bottom_sheet_state_ID = getActivity().findViewById(R.id.btn_change_bottom_sheet_state_ID);

        layoutBottomSheet = getActivity().findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        label_change_bottom_sheet_state_ID = getActivity().findViewById(R.id.label_change_bottom_sheet_state_ID);


         pullToRefresh = getActivity().findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                AsyncTask asyncTask = new AsyncTask() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        recycler_newCarriculum_exam_ID.setVisibility(View.GONE);
                    }

                    @Override
                    protected void onPostExecute(Object o) {

                        Student student = (Student) o;

                        sendRequestForPortalProgressReport(student, getActivity());


                        super.onPostExecute(o);
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        return ((MainActivity) getActivity()).student;
                    }
                };


                if(utilityFunctions.internetConnection())
                    asyncTask.execute();

                pullToRefresh.setRefreshing(false);

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
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        label_change_bottom_sheet_state_ID.setText("Close Comment Section");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        label_change_bottom_sheet_state_ID.setText("Show Comment Section");
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
        btn_change_bottom_sheet_state_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(MainActivity.this, String.valueOf(sheetBehavior.getState()), Toast.LENGTH_SHORT).show();
                //sheetBehavior.setPeekHeight(200);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                if(sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){

                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //sheetBehavior.setPeekHeight(200);
                    label_change_bottom_sheet_state_ID.setText("Close Comment Section");//4
                }else{
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//3
                    label_change_bottom_sheet_state_ID.setText("Show Comment Section");

                }


                // sheetBehavior.setState(sheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN ? BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_HIDDEN);
            }
        });



        //Spinner progress bars
       // ((MainActivity)getActivity()).layoutBottomSheet.setVisibility(View.VISIBLE);
        pb_newCarriculum_progress_layout_exam_type = this.getActivity().findViewById(R.id.pb_newCarriculum_progress_layout_exam_type);
        pb_newCarriculum_progress_layout_year = getActivity().findViewById(R.id.pb_newCarriculum_progress_layout_year);
        pb_newCarriculum_progress_layout_term = getActivity().findViewById(R.id.pb_newCarriculum_progress_layout_term);

        txt_no_exam_progress_toshow_ID = getActivity().findViewById(R.id.txt_no_exam_progress_toshow_ID);

        pb_loading_txt_newCarriculum_progress = getActivity().findViewById(R.id.pb_loading_txt_newCarriculum_progress);
        recycler_newCarriculum_exam_ID = getActivity().findViewById(R.id.recycler_newCarriculum_exam_ID);

        txt_confirmationMessage = getActivity().findViewById(R.id.txt_confirmationMessage);
        txt_confirmationMessage2 = getActivity().findViewById(R.id.txt_confirmationMessage2);

        image_sp_exam_type_ID = getActivity().findViewById(R.id.image_sp_exam_type_ID);
        image_sp_exam_type_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_exam_type_ID.performClick();
            }
        });
        image_sp_year_ID = getActivity().findViewById(R.id.image_sp_year_ID);
        image_sp_year_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_year_ID.performClick();
            }
        });
        image_sp_term_ID = getActivity().findViewById(R.id.image_sp_term_ID);
        image_sp_term_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_term_ID.performClick();
            }
        });

        sp_term_ID = getActivity().findViewById(R.id.sp_term_ID);
        sp_year_ID = getActivity().findViewById(R.id.sp_year_ID);
        sp_exam_type_ID = getActivity().findViewById(R.id.sp_exam_type_ID);

        final Context mContext = this.getActivity();





        displayRecyclerViewData();


            //populate with data
//        asyncTask1.execute(year);
//        asyncTask2.execute(term,year);
       // asyncTask.execute();

        //sp_year_ID, sp_term_ID, sp_exam_type_ID



//        sp_year_ID.setSelection(new YearSpinnerDAO(mContext).getYearSpinnerPosition(year));
//        sp_term_ID.setSelection(new TermSpinnerDAO(mContext).getTermSpinnerPosition(year,term));
//        sp_exam_type_ID.setSelection(new ExamTypeSpinnerDAO(mContext).getExamSpinnerPosition(term,year,exam));

//
       // Log.d(mContext.getPackageName().toUpperCase(), sp_exam_type_ID.getAdapter().getItem(new ExamTypeSpinnerDAO(mContext).getExamSpinnerPosition(term,year,exam)).toString());//String.valueOf(new YearSpinnerDAO(mContext).getYearSpinnerPosition(year)) + " " + String.valueOf(new TermSpinnerDAO(mContext).getTermSpinnerPosition(year,term)) + " " + String.valueOf(new ExamTypeSpinnerDAO(mContext).getExamSpinnerPosition(term,year,exam)));


        super.onActivityCreated(savedInstanceState);
    }


    public void displayRecyclerViewData(){

        //EXAMTYPE
        //another for the same job
        //populating exam1

        //populating exam2

        // asyncTask2.execute();
        sp_exam_type_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch(position){
                    case 0:
                        examTypeSelected = null;
                        break;
                    default:
                        examTypeSelected = (ExamTypeSpinner)  parent.getSelectedItem();
                        //START

                        if(validateExamTypeSpinner(examTypeSelected)  && validateYearSpinner(yearSelected) && validateTermSpinner(termSelected)) {
                            txt_confirmationMessage.setText("Viewing " + examTypeSelected.getExamTypeName() + " for " + termSelected.getTermFor() + " " + yearSelected);
                           // txt_confirmationMessage.setTextColor(getActivity().getResources().getColor(R.color.homeText));

                            AsyncTask asyncTask3 = new AsyncTask() {
                                @Override
                                protected void onPreExecute() {
                                    pb_loading_txt_newCarriculum_progress.setVisibility(View.VISIBLE);
                                    super.onPreExecute();
                                }

                                @Override
                                protected Object doInBackground(Object[] params) {
                                    List<Activities> activitiesList = new NewCarriculumExamDAO(NewCarricullumActivity.this.getActivity()).getPortalResultProgress(examTypeSelected.getExamTypeName(), yearSelected.getYearFor(),termSelected.getTermFor());

                                    return activitiesList;
                                }

                                @Override
                                protected void onPostExecute(Object o) {

                                    pb_loading_txt_newCarriculum_progress.setVisibility(View.INVISIBLE);

                                    List<Activities> activitiesList = ((List<Activities>)o);

                                    if(activitiesList.size() > 0){
                                        recycler_newCarriculum_exam_ID.setHasFixedSize(true);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                                        recycler_newCarriculum_exam_ID.setLayoutManager(layoutManager);


                                        DisplayActivityRecycler adapter = new DisplayActivityRecycler(getActivity(), ((List<Activities>)o)/*response.body()*/);
                                        recycler_newCarriculum_exam_ID.setAdapter(adapter);
                                        recycler_newCarriculum_exam_ID.setVisibility(View.VISIBLE);
                                    }else{

                                    }


                                    super.onPostExecute(o);
                                }
                            };
                            asyncTask3.execute();


                            //GETcOMMENTS FOR THE SPECIFIED EXAM
                            String year = yearSelected.getYearFor();
                            String term = termSelected.getTermFor();
                            String exam = examTypeSelected.getExamTypeName();

                            CommentSection commentSection =  new NewCarriculumExamDAO(NewCarricullumActivity.this.getActivity()).getNewExamFormatComments(year,term,exam);

                            pb_loading_txt_manageDiscipline_progress.setVisibility(View.INVISIBLE);

                            if(commentSection != null || !(commentSection.getGradeFacilitatorComments().equals("") && commentSection.getHeadTeacherComments().equals("")) ) {

                                if (!commentSection.getGradeFacilitatorComments().equals("")) {
                                    label_grade_facilitator_comment.setVisibility(View.VISIBLE);
                                    txt_grade_facilitator_comment.setText(commentSection.getGradeFacilitatorComments());
                                } else {
                                    label_grade_facilitator_comment.setVisibility(View.GONE);
                                    txt_grade_facilitator_comment.setVisibility(View.GONE);
                                }


                                if (!commentSection.getHeadTeacherComments().equals("")) {

                                    txt_head_teacher_comment_ID.setText(commentSection.getHeadTeacherComments());
                                    label_head_teacher_comment.setVisibility(View.VISIBLE);
                                } else {
                                    txt_head_teacher_comment_ID.setVisibility(View.GONE);
                                    label_head_teacher_comment.setVisibility(View.GONE);
                                }


                                comments_section_layout_ID.setVisibility(View.VISIBLE);
                            }else{
                                txt_no_comments_toshow_ID.setVisibility(View.VISIBLE);
                            }
//                            asyncTask5.execute(yearSelected.getYearFor(),termSelected.getTermFor());


                        }

                        //END
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                examTypeSelected = null;
            }
        });

        NewCarriculumExamDAO newCarriculumExamDAO = new NewCarriculumExamDAO(NewCarricullumActivity.this.getActivity());

        String i =  newCarriculumExamDAO.defaultYear();
        // Toast.makeText(mContext, String.valueOf(i), Toast.LENGTH_SHORT).show();
        Log.d(NewCarricullumActivity.this.getActivity().getPackageName().toUpperCase(), "Show" + String.valueOf(i) );

        //TERM
        //populating term


        // asyncTask1.execute();
        sp_term_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //txt_confirmationMessage.setText("");

                switch(position){
                    case 0:
                        termSelected = null;
                        break;
                    default:
                        termSelected = (TermSpinner)  parent.getSelectedItem();
                        //  Toast.makeText(ManageFleetFuelScreen.this, yearSelected, Toast.LENGTH_SHORT).show();
                        //results will be displayed here, after the exam type , year, term are selected.

                        //POPULATE EXAM

                        pb_newCarriculum_progress_layout_exam_type.setVisibility(View.VISIBLE);
                        ArrayList<ExamTypeSpinner> examTypeSpinnerArrayList = new ExamTypeSpinnerDAO(getActivity()).getExamTypeSpinner(termSelected.getTermFor(), yearSelected.getYearFor());
                        ArrayAdapter<ExamTypeSpinner> examTypeSpinnerArrayAdapter = new ArrayAdapter<ExamTypeSpinner>(getActivity(), android.R.layout.simple_spinner_dropdown_item, examTypeSpinnerArrayList);
                        sp_exam_type_ID.setAdapter(examTypeSpinnerArrayAdapter);
                        sp_exam_type_ID.setVisibility(View.VISIBLE);
                        pb_newCarriculum_progress_layout_exam_type.setVisibility(View.GONE);

                        examTypeSelected = null;
                        if(validateExamTypeSpinner(examTypeSelected)){
                            //do nothing
                        }

                        //START

                        //END
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                termSelected = null;
            }
        });



        //YEAR
        //populatingyear
        final AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                pb_newCarriculum_progress_layout_term.setVisibility(View.VISIBLE);
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                ArrayList<YearSpinner> yearSpinnerArrayList = new YearSpinnerDAO(getActivity()).getYearSpinner();
                return yearSpinnerArrayList;
            }

            @Override
            protected void onPostExecute(Object o) {

                if(((ArrayList<YearSpinner>)o).size() > 0) {

                    pb_newCarriculum_progress_layout_term.setVisibility(View.INVISIBLE);
                    ArrayAdapter<YearSpinner> yearSpinnerArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, ((ArrayList<YearSpinner>) o));
                    sp_year_ID.setAdapter(yearSpinnerArrayAdapter);
                    sp_year_ID.setVisibility(View.VISIBLE);
                }else{
                    txt_no_exam_progress_toshow_ID.setVisibility(View.VISIBLE);
                }




                super.onPostExecute(o);
            }
        };
        asyncTask.execute();
        sp_year_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // txt_confirmationMessage.setText("");

                switch(position){
                    case 0:
                        yearSelected = null;
                        break;
                    default:
                        yearSelected = (YearSpinner)  parent.getSelectedItem();

                        //POPULATE TERM
                        pb_newCarriculum_progress_layout_year.setVisibility(View.VISIBLE);
                        ArrayList<TermSpinner> termSpinnerArrayList = new TermSpinnerDAO(getActivity()).getTermSpinner(yearSelected.getYearFor());
                        ArrayAdapter<TermSpinner> termSpinnerArrayAdapter = new ArrayAdapter<TermSpinner>(getActivity(), android.R.layout.simple_spinner_dropdown_item, termSpinnerArrayList);
                        sp_term_ID.setAdapter(termSpinnerArrayAdapter);
                        sp_term_ID.setVisibility(View.VISIBLE);
                        pb_newCarriculum_progress_layout_year.setVisibility(View.GONE);

                        termSelected = null;
                        if(validateTermSpinner(termSelected)){

                        }

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                yearSelected = null;
            }
        });

        //asyncTask5 and asyncTask7 are all the same only that they do share

        final  AsyncTask asyncTask7 = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {

                String year = (String) params[0];
                String term = (String) params[1];
                String exam = (String) params[2];

                CommentSection commentSection =  new NewCarriculumExamDAO(NewCarricullumActivity.this.getActivity()).getNewExamFormatComments(year,term,exam);
                return commentSection;
            }

            @Override
            protected void onPostExecute(Object o) {

                CommentSection commentSection  = (CommentSection) o;
                pb_loading_txt_manageDiscipline_progress.setVisibility(View.INVISIBLE);

                if(commentSection != null || !(commentSection.getGradeFacilitatorComments().equals("") && commentSection.getHeadTeacherComments().equals("")) ) {

                    if (!commentSection.getGradeFacilitatorComments().equals("")) {
                        label_grade_facilitator_comment.setVisibility(View.VISIBLE);
                        txt_grade_facilitator_comment.setText(commentSection.getGradeFacilitatorComments());
                    } else {
                        label_grade_facilitator_comment.setVisibility(View.GONE);
                        txt_grade_facilitator_comment.setVisibility(View.GONE);
                    }


                    if (!commentSection.getHeadTeacherComments().equals("")) {

                        txt_head_teacher_comment_ID.setText(commentSection.getHeadTeacherComments());
                        label_head_teacher_comment.setVisibility(View.VISIBLE);
                    } else {
                        txt_head_teacher_comment_ID.setVisibility(View.GONE);
                        label_head_teacher_comment.setVisibility(View.GONE);
                    }


                    comments_section_layout_ID.setVisibility(View.VISIBLE);
                }else{
                    txt_no_comments_toshow_ID.setVisibility(View.VISIBLE);
                }



                super.onPostExecute(o);
            }
        };

        //this asynctTask5 will execution will be supplied with data


        List<Object> list = new NewCarriculumExamDAO(NewCarricullumActivity.this.getActivity()).getExam();
        List<Activities> activitiesList = (List<Activities>) list.get(0);
        String exam  = (String) list.get(1);
        String year = (String) list.get(2);
        String term = (String) list.get(3);



        if(activitiesList.size() > 0){
            txt_confirmationMessage.setText("Viewing " + exam + " for " + term+ " " + year);
            txt_confirmationMessage.setTextColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
            asyncTask7.execute(year,term,exam);
            recycler_newCarriculum_exam_ID.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recycler_newCarriculum_exam_ID.setLayoutManager(layoutManager);


            DisplayActivityRecycler adapter = new DisplayActivityRecycler(getActivity(), activitiesList/*response.body()*/);
            recycler_newCarriculum_exam_ID.setAdapter(adapter);
            recycler_newCarriculum_exam_ID.setVisibility(View.VISIBLE);
        }else{
            txt_confirmationMessage.setVisibility(View.INVISIBLE);

        }

    }


    private boolean validateExamTypeSpinner(ExamTypeSpinner selected){
        txt_confirmationMessage2.setText("");
        if(selected == null){
            txt_confirmationMessage2.setText("Please select Exam Type");
            return false;

        }

        return true;
    }
    private boolean validateYearSpinner( YearSpinner selected){
        txt_confirmationMessage2.setText("");
        if(selected == null){
            txt_confirmationMessage2.setText("Please select Year");
            return false;
        }
        return true;
    }

    private boolean validateTermSpinner( TermSpinner selected){
        txt_confirmationMessage2.setText("");
        if(selected == null){
            txt_confirmationMessage2.setText("Please select Term");
            return false;
        }
        return true;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */

    public static NewCarricullumActivity newInstance(String param1, String param2, FragmentManager fragmentManager){
        NewCarricullumActivity.fragmentManager = fragmentManager;

        NewCarricullumActivity fragment = new NewCarricullumActivity();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    private static int callOnce = 0;
    private class DisplayActivityRecycler extends RecyclerView.Adapter<DisplayActivityRecycler.ViewHolder>{


        private Context mContext;
        private List<Activities> activitiesList;

        public DisplayActivityRecycler(Context mContext, List<Activities> activitiesList){
            this.mContext = mContext;
            this.activitiesList = activitiesList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView txt_classstream_level_name_ID;
            private TextView txt_activity_name_ID;
            private LinearLayout pb_loading_txt_learningarea_progress;
            private RecyclerView recycler_learningarea_exam_ID;

            public ViewHolder(View view){
                super(view);

                pb_loading_txt_learningarea_progress = view.findViewById(R.id.pb_loading_txt_learningarea_progress);
                recycler_learningarea_exam_ID = view.findViewById(R.id.recycler_learningarea_exam_ID);
                txt_activity_name_ID = view.findViewById(R.id.txt_activity_name_ID);
                txt_classstream_level_name_ID = view.findViewById(R.id.txt_classstream_level_name_ID);
                pb_loading_txt_learningarea_progress.setVisibility(View.VISIBLE);


            }

            public final void bind(final Activities activities){
                txt_activity_name_ID.setText(activities.getActivityName());
                txt_classstream_level_name_ID.setText(activities.getCourseName() + " " + activities.getLevelName());

                //now here you will call the display learning recyclerview
              //  Log.d(mContext.getPackageName().toUpperCase(), "LearningArea List being displayed: " + activities.getLearningAreaList().toString());


                AsyncTask asyncTask4 = new AsyncTask() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object[] params) {

                        try{
                            //you can remove this, if you see its not important to load
                            Thread.sleep(500);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        return ((List<LearningAreas>) params[0]);
                    }



                    @Override
                    protected void onPostExecute(Object o) {


                        pb_loading_txt_learningarea_progress.setVisibility(View.INVISIBLE);
                            recycler_learningarea_exam_ID.setHasFixedSize(true);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            recycler_learningarea_exam_ID.setLayoutManager(layoutManager);

                            DisplayLearningAreaRecycler adapter = new DisplayLearningAreaRecycler(getActivity(), ((List<LearningAreas>) o)/*response.body()*/);
                            recycler_learningarea_exam_ID.setAdapter(adapter);


                            recycler_learningarea_exam_ID.setVisibility(View.VISIBLE);

                        super.onPostExecute(o);
                    }
                };
                asyncTask4.execute(activities.getLearningAreaList());


            }
        }

        @Override
        public DisplayActivityRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_carricullum_activity_item_layout, parent, false);
            return new DisplayActivityRecycler.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DisplayActivityRecycler.ViewHolder holder, int position) {
            holder.bind(activitiesList.get(position));
        }

        @Override
        public int getItemCount() {
            return activitiesList.size();
        }
    }


    private class DisplayLearningAreaRecycler extends RecyclerView.Adapter<DisplayLearningAreaRecycler.ViewHolder>{
        private Context mContext;
        private List<LearningAreas> learningAreasList;

        public DisplayLearningAreaRecycler(Context mContext, List<LearningAreas> learningAreasList){
            this.mContext = mContext;
            this.learningAreasList = learningAreasList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{

            private TextView txt_learning_area_ID;
            private TextView txt_attainment_ID;
            private TextView txt_facilitator_comment_ID;

            //LABEL
            private TextView label_learning_area;

            public ViewHolder(View view){
                super(view);

                txt_learning_area_ID = view.findViewById(R.id.txt_learning_area_ID);
                txt_attainment_ID = view.findViewById(R.id.txt_attainment_ID);
                txt_facilitator_comment_ID = view.findViewById(R.id.txt_facilitator_comment_ID);
                label_learning_area = view.findViewById(R.id.label_learning_area);

            }

            public final void bind(LearningAreas learningAreas){
                if(learningAreas.getLearningAreaName() != null){
                    txt_learning_area_ID.setText(learningAreas.getLearningAreaName());

                }else{
                    txt_learning_area_ID.setVisibility(View.INVISIBLE);
                    label_learning_area.setVisibility(View.INVISIBLE);
                }

                txt_attainment_ID.setText(learningAreas.getScoreDescription());
                txt_facilitator_comment_ID.setText(learningAreas.getRemarks());


            }
        }

        @Override
        public DisplayLearningAreaRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_carriculum_learning_item_layout, parent, false);
            return new DisplayLearningAreaRecycler.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DisplayLearningAreaRecycler.ViewHolder holder, int position) {
             holder.bind(learningAreasList.get(position));
        }

        @Override
        public int getItemCount() {

             return learningAreasList.size();
        }

    }


    @Override
    public void onDestroy() {

       // ((MainActivity) getActivity()).layoutBottomSheet.setVisibility(View.GONE);
        super.onDestroy();
    }
}
