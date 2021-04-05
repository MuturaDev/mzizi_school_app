package ultratude.com.mzizi.uiactivities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;



public class ProfileFragment  extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ParentMziziDatabase db;

    public RecyclerView recyclerView;
    public ProgressBar diary_progress;


    static FragmentManager fragmentManager;


    private TextView name_text,
            school_text,
            admission_no_text,
            course_name_text,
            date_of_birth_text,
            phone_number_text,
            mean_score_text,
            mean_grade_text,
            two_names_text,
            username_text,
            password_text,
            schoolcode_text,
            class_attendance_text;

    private ImageView  profile_image;



    public ProfileFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ReportAnalytics.reportScreenChangeAnalytic(getContext(), "Profile Fragment");
        return inflater.inflate(R.layout.profile_layout_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle("Student Profile");

        db = ParentMziziDatabase.getInstance(getContext());


//        recyclerView = getActivity().findViewById(R.id.profile_recycler);
//        diary_progress = getActivity().findViewById(R.id.profile_progress);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.hasFixedSize();


        personal_information_collapse = getActivity().findViewById(R.id.personal_information_collapse);
        father_details_collapse       = getActivity().findViewById(R.id.father_details_collapse);
        mother_details_collapse       = getActivity().findViewById(R.id.mother_details_collapse);
        guardian_details_collapse     = getActivity().findViewById(R.id.guardian_details_collapse);
        theme_layout_theme_expand = getActivity().findViewById(R.id.theme_layout_theme_expand);



        personal_information_expand = getActivity().findViewById(R.id.personal_information_expand);
        personal_information_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandPersonalInformation();
            }
        });
        father_details_expand = getActivity().findViewById(R.id.father_details_expand);
        father_details_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandFatherDetail();
            }
        });
        mother_details_expand = getActivity().findViewById(R.id.mother_details_expand);
        mother_details_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandMotherDetails();
            }
        });
        guardian_details_expand = getActivity().findViewById(R.id.guardian_details_expand);
        guardian_details_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandGuadianDetails();
            }
        });



        expand1 = getActivity().findViewById(R.id.expand1);
        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandPersonalInformation();
            }
        });
        expand2 = getActivity().findViewById(R.id.expand2);
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandFatherDetail();
            }
        });
        expand3 = getActivity().findViewById(R.id.expand3);
        expand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandMotherDetails();
            }
        });
        expand4 = getActivity().findViewById(R.id.expand4);
        expand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandGuadianDetails();
            }
        });

//        scrollView = getActivity().findViewById(R.id.scrollview);
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.fullScroll(scrollView.FOCUS_UP);
//            }
//        });
//
//        final LinearLayout hide_after_scroll_down =  getActivity().findViewById(R.id.hide_after_scroll_down);
//        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                if (scrollView.getChildAt(0).getBottom()
//                        <= (scrollView.getHeight() + scrollView.getScrollY())) {
//                    //scroll view is at bottom
//                    collapse(hide_after_scroll_down);
//
//                }
//            }
//        });

        profile_image = getView().findViewById(R.id.profile_image);

        AsyncTask asyncTask2 = new AsyncTask() {
            @Override
            protected void onPostExecute(Object o) {
               final Map<String, Object> map = (Map<String,Object>)o;

                if(map.get(Constants.PORTAL_URL_ENABLED) != null){
                    String baseulr = ((GlobalSettings)map.get(Constants.PORTAL_URL_ENABLED)).getGlobalSettingsValue();

                    if(map.get("StudentInfo") != null){

                        PortalStudentInfo studentInfo = (PortalStudentInfo) map.get("StudentInfo");
                        //https://stackoverflow.com/questions/48151918/how-to-cache-images-in-glide
                            Glide.with(getActivity())
                                    .load(baseulr + studentInfo.getPhotoUrl())//https://demo.mzizi.co.ke/
                                    .centerCrop()
                                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                    .error(((MainActivity)getActivity()).studentProfilePhoto.getDrawable())
                                    .into(profile_image);
                    }

                }



                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                Map<String, Object> map = new HashMap<>();
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED,Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.PORTAL_URL_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED,Integer.valueOf(studentid)).get(0));
                }


                    List<PortalStudentInfo> studentList = ((MainActivity) getActivity()).db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
                    if(studentList.size() <1){
                        map.put("StudentInfo",new PortalStudentInfo());
                        return map;
                    }

                    map.put("StudentInfo",studentList.get(0));


                return map;
            }
        };
        asyncTask2.execute();

        name_text = getView().findViewById(R.id.name_text);
        school_text = getView().findViewById(R.id.school_text);
        admission_no_text = getView().findViewById(R.id.admission_no_text);
        course_name_text = getView().findViewById(R.id.course_name_text);
        date_of_birth_text = getView().findViewById(R.id.date_of_birth_text);
        phone_number_text = getView().findViewById(R.id.phone_number_text);
        mean_score_text = getView().findViewById(R.id.mean_score_text);
        mean_grade_text = getView().findViewById(R.id.mean_grade_text);
        two_names_text = getView().findViewById(R.id.two_names_text);
        username_text = getView().findViewById(R.id.username_text);
        password_text = getView().findViewById(R.id.password_text);
        schoolcode_text = getView().findViewById(R.id.schoolcode_text);
        class_attendance_text = getView().findViewById(R.id.class_attendance_text);


        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {

                List<Object> objectList = (List<Object>) o;

                if(objectList != null){
                    if(objectList.size() == 3){
                        List<PortalStudentInfo> studentinfoList = (List<PortalStudentInfo>) objectList.get(0);
                        if(studentinfoList != null){
                            if(studentinfoList.size() > 0){
                                PortalStudentInfo studentInfo = studentinfoList.get(0);

                                name_text.setText(studentInfo.getStudentFullName());
                                school_text.setText(studentInfo.getSchoolName());
                                //admission_no_text.setText(UtilityFunctions.convertDateToMziziDisplayDate(studentInfo.getDateOfAdmission()));
                                admission_no_text.setText(studentInfo.getRegistrationNumber());
                                course_name_text.setText(studentInfo.getClassStream());
                                date_of_birth_text.setText(UtilityFunctions.convertDateToMziziDisplayDate(studentInfo.getDOB()));
                                phone_number_text.setText("N/A");
                                mean_score_text.setText(studentInfo.getMeanScore());
                                mean_grade_text.setText(studentInfo.getMeanGrade());
                                two_names_text.setText(studentInfo.getTwoNames());
                                two_names_text.setVisibility(View.INVISIBLE);


                                Map<String, String> map = (Map<String, String>) objectList.get(2);

                                //TODO: FOR SIBLINGS FIND OUT WHAT SIMON DOES TO GET THE SIBLINGS PASSWORD AND USERNAME
                                StringBuilder hashedPassword = new StringBuilder();

                                if(map.get("Password") != null){
                                    char[] array = map.get("Password").toCharArray();
                                    Log.d(getActivity().getPackageName().toUpperCase(), String.valueOf(array == null));
                                    for(int i = 0; i < (array != null? array.length: 0); i++){

                                        hashedPassword.append("*");
                                    }
                                    username_text.setText(map.get("UserName").isEmpty() ?  "-" : map.get("UserName"));
                                    password_text.setText(hashedPassword.toString());
                                    schoolcode_text.setText(map.get("SchoolCode"));
                                }else
                                {
                                    username_text.setText( "__");
                                    password_text.setText("__");
                                    schoolcode_text.setText("__");


                                }


                                List<StudentClassAttendance> list = (List<StudentClassAttendance>)objectList.get(1);
                                if(list.size() > 0) {
                                    double count = 0.0;
                                    for (StudentClassAttendance studentClassAttendance : list) {
                                        String attendanceStatus = studentClassAttendance.getStatus();
                                        if (attendanceStatus.equalsIgnoreCase("PRESENT")) {
                                            count++;
                                        }
                                    }

                                    double percentCount = (count * 100.0) / list.size();

                                    DecimalFormat df = new DecimalFormat("#");
                                    //   Log.d(getActivity().getPackageName().toUpperCase(), "ProgressText " + percentCount);
                                    //TODO: The best thing is to have the percentage calculated on the dates of each term...
                                    // to get the attendance for that specific term.
                                    class_attendance_text.setText(df.format(Math.ceil(percentCount)) + "%" + " " + "Term 1");

                                }else{

                                    class_attendance_text.setText("100%");

                                }

                            }
                        }

                    }
                }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                List<Object> returnList = new ArrayList<>();
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
               List<PortalStudentInfo> studentinfoList =  db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
               returnList.add(studentinfoList);//Student Info..0

               List<String> termYear = UtilityFunctions.checkTermYearSchoolDates();

               if(termYear != null) {

                 List<StudentClassAttendance> studentClassAttendance =  db.getStudentClassAttendanceDAO().getStudentClassAttendance(termYear.get(1), termYear.get(0),Integer.valueOf(studentid));
                   returnList.add(studentClassAttendance);//1
               }

               returnList.add(UtilityFunctions.getMyAccountCredentials(getActivity()));//2

                return returnList;
            }
        };
        asyncTask.execute();


        super.onViewCreated(view, savedInstanceState);
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


    public static ProfileFragment newInstance(String param1, String param2, FragmentManager fragmentManager) {
        ProfileFragment.fragmentManager = fragmentManager;

        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private RelativeLayout personal_information_collapse, father_details_collapse,mother_details_collapse,guardian_details_collapse,theme_layout_theme_collapse;
    private RelativeLayout personal_information_expand, father_details_expand, mother_details_expand, guardian_details_expand,theme_layout_theme_expand;



    private void expandPersonalInformation(){
        closeAnyExpandedView();
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.fullScroll(scrollView.FOCUS_UP);
//            }
//        });

        if(isExpnded && personal_information_collapse.getVisibility() == View.VISIBLE){
            //recentMeanScoreCollapse.setVisibility(View.GONE);
            collapse(personal_information_collapse);
            expand1.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
            isExpnded  = false;

        }else{
            //recentMeanScoreCollapse.setVisibility(View.VISIBLE);
            expand(personal_information_collapse);
            expand1.setBackground(getActivity().getDrawable(R.drawable.up_icon));
            isExpnded  = true;
        }
    }

    private void expandFatherDetail(){
        closeAnyExpandedView();
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.fullScroll(scrollView.FOCUS_UP);
//            }
//        });

        if(isExpnded1 && father_details_collapse.getVisibility() == View.VISIBLE){
            collapse(father_details_collapse);
            expand2.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
            isExpnded1  = false;
        }else{
            expand(father_details_collapse);
            expand2.setBackground(getActivity().getDrawable(R.drawable.up_icon));
            isExpnded1  = true;
        }
    }

    private void expandMotherDetails(){
        closeAnyExpandedView();
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.fullScroll(scrollView.FOCUS_UP);
//            }
//        });

        if(isExpnded2 && mother_details_collapse.getVisibility() == View.VISIBLE){
            collapse(mother_details_collapse);
            expand3.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
            isExpnded2  = false;
        }else{
            expand(mother_details_collapse);
            expand3.setBackground(getActivity().getDrawable(R.drawable.up_icon));
            isExpnded2  = true;
        }
    }

    private void expandGuadianDetails(){
        closeAnyExpandedView();
//        scrollView.post(new Runnable() {
//            public void run() {
//                scrollView.fullScroll(scrollView.FOCUS_DOWN);
//            }
//        });

        if(isExpnded3 && guardian_details_collapse.getVisibility() == View.VISIBLE){
            collapse(guardian_details_collapse);
            expand4.setBackground(getActivity().getDrawable(R.drawable.expand_icon));
            isExpnded3  = false;

            //  scrollView.scrollTo(0, scrollView.getBottom());

        }else{
            expand(guardian_details_collapse);
            expand4.setBackground(getActivity().getDrawable(R.drawable.up_icon));
            isExpnded3  = true;
        }
    }

    private void closeAnyExpandedView(){

        if( personal_information_collapse.getVisibility() == View.VISIBLE ){
            collapse(personal_information_collapse);
        }

        if( father_details_collapse.getVisibility()  == View.VISIBLE){
            collapse(father_details_collapse);
        }

        if(mother_details_collapse.getVisibility()  == View.VISIBLE){
            collapse(mother_details_collapse);
        }

        if(guardian_details_collapse.getVisibility() == View.VISIBLE){
            collapse(guardian_details_collapse);
        }
    }


    //private ScrollView scrollView;
    private ImageView expand1,expand2,expand3,expand4,expandTheme;
    private static boolean isExpnded = false;
    private static boolean isExpnded1 = false;
    private static boolean isExpnded2 = false;
    private static boolean isExpnded3 = false;
    private static boolean isExpandedTheme = false;

    public static void expand(final View v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private  void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

}
