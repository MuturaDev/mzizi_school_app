package ultratude.com.staff.fromapp_exammeanscoreandhelperclasses;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.activities.StudentEnquiryFragmentsViewer;
import ultratude.com.staff.fragments.ViewExamHistory;
import ultratude.com.staff.webservice.DataAccessObjects.ExamHistoryDAO;
import ultratude.com.staff.webservice.ResponseModels.ExamHistory;

/**
 * Created by James on 07/07/2018.
 */

public class XFragRetrieveAndDisplayRoomData extends AsyncTask<String,Void, List<ExamHistory>> {

    private WeakReference<ViewExamHistory> meanScoreFragWeakReference;
    private WeakReference<StudentEnquiryFragmentsViewer> studentEnquiryWeakReference;//for just changing the text at the toolbar


    private String yearToDisplayResults;

    public XFragRetrieveAndDisplayRoomData(ViewExamHistory frag, StudentEnquiryFragmentsViewer mainActivity , String yearToDisplayResults ){
       this.yearToDisplayResults = yearToDisplayResults;

        this.studentEnquiryWeakReference = new WeakReference<>(mainActivity);
        this.meanScoreFragWeakReference = new WeakReference<ViewExamHistory>(frag);




    }

    @Override
    protected void onPreExecute() {



        super.onPreExecute();
    }

    @Override
    protected void onPostExecute( List<ExamHistory> examHistoryList) {
            if((  meanScoreFragWeakReference.get()).isAdded())
                (  meanScoreFragWeakReference.get()).showProgress(false);


        Log.d(meanScoreFragWeakReference.get().getActivity().getPackageName().toUpperCase(), "Exam list empty?: " + examHistoryList.size());

            if(examHistoryList.size()>0){


                //you can send two list, the list of headers, and the list of data to display
                //list of headers
                //headerResultsList



                //start

                //the data stored here is stored depending on the year selected

                List<ExamHistory> filteredByYearDetailedResults = new ArrayList<>();


//            for(int i= 0; i<detailedResults.size(); i++){
//                detailedResults.get(i).getCurrentYear();
//
//                if(detailedResults.get(i).getCurrentYear() == yearToDisplayResults){
//                    filteredDetailedResults.add(detailedResults.get(i));
//                }
//            }

                //use either

                for(int k = 0; k< examHistoryList.size(); k++){
                    String currentYear = examHistoryList.get(k).getCurrentYear();
                    if(!yearToDisplayResults.equals("")){
                        //remember hard coded

                        //Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(),"Date Of Admission: " + yearToDisplayResults, Toast.LENGTH_LONG).show();
                        if(currentYear.equals(yearToDisplayResults)){
                            //those results of this specific year are stored in filteredDetailedResults
                            filteredByYearDetailedResults.add(examHistoryList.get(k));
                        }
                    }

                }




                List<String> uniquePeriodHeaders = new ArrayList<>();
                for(int j = 0; j<filteredByYearDetailedResults.size(); j++){

                    String beforeUniquePeriod = filteredByYearDetailedResults.get(j).getPeriod();
                    String afterUniquePeriod = "";

                    if(uniquePeriodHeaders.size() == 0){//at first its less is of size 0

                        uniquePeriodHeaders.add(beforeUniquePeriod);//meaning uniquePeriodHeaders will have the size of 1
                        //break;//if it adds no need to continue with this loop because it added the first item, and if continued i wont be having a second item in uniquePeriodHeaders

                    }else{

                        for(int k =0; k< uniquePeriodHeaders.size(); k++){
                            if(beforeUniquePeriod.equals(uniquePeriodHeaders.get(k))){
                                //dont add, coz its already there
                                //just have to continue with this loop
                                //this means this item does exist in uniquePeriodHeaders
                                break;//break from this statement so that i can fetch another item in filteredByYearDetailedResults

                            }else{
                                //if it has not looped to the last item of uniquePeriodHeaders continue with the second interation
                                if(k==(uniquePeriodHeaders.size()-1)){
                                    afterUniquePeriod = beforeUniquePeriod;
                                    uniquePeriodHeaders.add(afterUniquePeriod);
                                    break;
                                    //after it has added another item to uniquePeriodHeaders, i exit the loop to go and fetch another item
                                }else{
                                    continue;
                                }
                            }
                        }

                    }

                }//end of loop, all the uniquePeriodHeaders

                //  Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(), String.valueOf(uniquePeriodHeaders.size()), Toast.LENGTH_LONG).show();


                //Headers results list
                //so this headerResultsList, will container  different results instances of resultsSpecificToPeriod
                List<List<ExamHistory>> headerResultList = new ArrayList<>();//This will contain resultsSpecificToPeriod

                //this is not working
                //this will container, different instances of PortalStudentInfo, specific to period


                for(int z = 0; z< uniquePeriodHeaders.size(); z++){


                    List<ExamHistory> resultsSpecificToPeriod = new ArrayList<>();

                    for(int i = 0; i< filteredByYearDetailedResults.size(); i++){//will loop through every result, and store them in resultsSpecificToPeriod



                        if(uniquePeriodHeaders.get(z).equals(filteredByYearDetailedResults.get(i).getPeriod())){//you have to loop


                            resultsSpecificToPeriod.add(filteredByYearDetailedResults.get(i));

                        }



                    }//after its through, this instance is stored in headerResultsList, it will contain all the results

                    headerResultList.add(resultsSpecificToPeriod);//will store the different instance of resultsSpecificToPeriod



                }






                //these are the two things you should sent to MeanScoreRecycleViewAdapter
                //headerResultsList, for now will have resultsSpecificToPeriod
                // uniquePeriodHeaders list, for now will have all the unuique periods

                //create the an object of notificationAdapter found in NotificationFrag


                //for testing only

                //ALL TEST ARE CORRECT

//            List<List<ExamHistory>> listHolder = new ArrayList<>();
//
//            List<ExamHistory>  resultsSpecificToPeriod = new ArrayList<>();
//            for(int i = 0; i< filteredByYearDetailedResults.size(); i++){//will loop through every result, and store them in resultsSpecificToPeriod
//
//
//                if("CLASS 8 BRILLIANT TERM 1 2018 MID TERM EXAM".equals(filteredByYearDetailedResults.get(i).getPeriod())){//you have to loop
//
//                    resultsSpecificToPeriod.add(filteredByYearDetailedResults.get(i));
//
//                }
//
//
//            }
//
//            listHolder.add(resultsSpecificToPeriod);

                //Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(),"ResultSpecificToPeriod: "+ String.valueOf(listHolder.get(0).size()) + "  " + String.valueOf(filteredByYearDetailedResults.size()), Toast.LENGTH_LONG).show();


                //  Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(), "HeadersList: " + String.valueOf(uniquePeriodHeaders.size() )+ "   " + "DataList: " + String.valueOf(headerResultList.size() + "  " + "InnerDataList: "+ String.valueOf(headerResultList.get(11).get(0).getPeriod())), Toast.LENGTH_LONG).show();
//
                //this test is correct
//            AlertDialog.Builder alert = new AlertDialog.Builder(((ViewExamHistory) fragWeakReference.get()).getActivity());
//            StringBuilder b = new StringBuilder();
//            for(String list : uniquePeriodHeaders){
//                b.append(list);
//                b.append("\n");
//            }
//            alert.setMessage(b.toString());
//            alert.show();


                //end of testing



                // ((ViewExamHistory)  fragWeakReference.get()).meanScoreRecycleViewAdapter = new MeanScoreRecycleViewAdapter(((ViewExamHistory) fragWeakReference.get()),  uniquePeriodHeaders ,headerResultList);
                (  meanScoreFragWeakReference.get()).meanScoreRecycleViewAdapterTest = new MeanScoreRecycleViewAdapterTest(( meanScoreFragWeakReference.get()),  uniquePeriodHeaders ,headerResultList);


                ( meanScoreFragWeakReference.get()).recyclerView.setAdapter((  meanScoreFragWeakReference.get()).meanScoreRecycleViewAdapterTest);
                ( meanScoreFragWeakReference.get()).recyclerView.scrollToPosition(0);

                (  meanScoreFragWeakReference.get()).meanscore_layout_noContent.setVisibility(View.INVISIBLE);
                (  meanScoreFragWeakReference.get()).layoutabove.setVisibility(View.VISIBLE);
                (  meanScoreFragWeakReference.get()).recyclerView.setVisibility(View.VISIBLE);

                if((  meanScoreFragWeakReference.get()).isAdded())
                    (  meanScoreFragWeakReference.get()).showProgress(false);

                

            }else{

                (  meanScoreFragWeakReference.get()).meanscore_layout_noContent.setVisibility(View.VISIBLE);
                (  meanScoreFragWeakReference.get()).layoutabove.setVisibility(View.VISIBLE);//SHOULD BE CHANGED IN...
                (  meanScoreFragWeakReference.get()).recyclerView.setVisibility(View.INVISIBLE);


                if((  meanScoreFragWeakReference.get()).isAdded())
                    (  meanScoreFragWeakReference.get()).showProgress(false);

            }









        super.onPostExecute(examHistoryList);
    }



    @Override
    protected  List<ExamHistory> doInBackground(String... strings) {






            List<ExamHistory> studentDetailedResults = new ExamHistoryDAO(studentEnquiryWeakReference.get()).getExamResultHistory();






        return studentDetailedResults;//will not contain any of the above keys
    }

}
