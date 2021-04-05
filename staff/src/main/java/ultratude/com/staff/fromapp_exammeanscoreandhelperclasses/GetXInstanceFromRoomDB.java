package ultratude.com.staff.fromapp_exammeanscoreandhelperclasses;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import ultratude.com.staff.activities.StudentEnquiryFragmentsViewer;
import ultratude.com.staff.fragments.ViewExamHistory;
import ultratude.com.staff.webservice.DataAccessObjects.ExamHistoryDAO;
import ultratude.com.staff.webservice.ResponseModels.ExamHistory;


/**
 * Created by James on 07/07/2018.
 */

public class GetXInstanceFromRoomDB extends AsyncTask<String,Void,List<ExamHistory>> {


    private WeakReference<StudentEnquiryFragmentsViewer> studentEnquiryWeakReference;//for just changing the text at the toolbar
    private WeakReference<ViewExamHistory> meanScoreFragWeakReference;

    public GetXInstanceFromRoomDB(StudentEnquiryFragmentsViewer mainActivity , ViewExamHistory frag ){
        this.studentEnquiryWeakReference = new WeakReference<>(mainActivity);
        this.meanScoreFragWeakReference = new WeakReference<ViewExamHistory>(frag);
    }

    @Override
    protected void onPreExecute() {
        //Do nothing
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(List<ExamHistory> examHistoryList) {


//            AlertDialog.Builder d = new AlertDialog.Builder(studentEnquiryWeakReference.get());
//            d.setMessage(studentList.getDateOfAdmission());
//            d.show();

            //list of years from date of admission to current year, we changed this to , list of unique years from the first instance of exams history to the last
            List<String> uniqueYears = new ArrayList<>();

           for(int j= 0; j<examHistoryList.size(); j++){
               String beforeUniqueYear = examHistoryList.get(j).getCurrentYear();
               String afterUniqueYear = "";

               if(uniqueYears.size() == 0){
                   uniqueYears.add(beforeUniqueYear);
               }else{
                   for(int k =0; k<uniqueYears.size(); k++){
                       if(beforeUniqueYear.equals(uniqueYears.get(k))){
                           break;
                       }else{
                           if(k == (uniqueYears.size()-1)){
                               afterUniqueYear  = beforeUniqueYear;
                               uniqueYears.add(afterUniqueYear);
                               break;
                           }else{
                               continue;
                           }
                       }
                   }
               }
           }


           //Create a list to sort
            List<String> sortList = new ArrayList<>();
                sortList.addAll(uniqueYears);

            if(sortList.size() == 0){

                Date date = new Date(System.currentTimeMillis());
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                    //Add one to month {0 - 11}
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                sortList.add(String.valueOf(year));

            }



                //sorting list of years currently with results
                Collections.sort(sortList, Collections.<String>reverseOrder());

                //by now years list is field with data
                final Spinner year_spinner =   (meanScoreFragWeakReference.get()).year_spinner;
                ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(studentEnquiryWeakReference.get(),
                        android.R.layout.simple_spinner_dropdown_item, sortList);
                year_spinner.setAdapter(spinner_adapter);
                 String yearToDisplayResults;//is the default value
                    if(year_spinner !=null)
                       yearToDisplayResults  = year_spinner.getSelectedItem().toString();

                //new XFragRetrieveAndDisplayRoomData((fragWeakReference.get()), studentEnquiryWeakReference.get(), yearToDisplayResults).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS);





                //by default display the results of the current year

                year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                        String yearToDisplayResults = year_spinner.getSelectedItem().toString();
                        //the selectd is what is selected.
                        //Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(), yearToDisplayResults, Toast.LENGTH_LONG).show();
                        // new XFragRetrieveAndDisplayRoomData( (fragWeakReference.get()), new MainActivity(), yearToDisplayResults ).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS);
                        new XFragRetrieveAndDisplayRoomData( (meanScoreFragWeakReference.get()),  studentEnquiryWeakReference.get(), yearToDisplayResults ).execute("");

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView){

                    }
                });







        super.onPostExecute(examHistoryList);
    }

    @Override
    //delete or get istu
    protected  List<ExamHistory> doInBackground(String... strings) {//GetConstants.GET_NOTIFICATION,DeleteConstants.DeleteNotification

            List<ExamHistory> studentDetailedResults = new ExamHistoryDAO(studentEnquiryWeakReference.get()).getExamResultHistory();
       // Log.d(studentEnquiryWeakReference.get().getPackageName().toUpperCase(), "NOT EMPTY: " + studentDetailedResults.toString());

        return studentDetailedResults;
    }

}
