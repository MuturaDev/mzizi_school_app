package ultratude.com.mzizi.backgroundtasks;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.modelclasses.DeleteConstants;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.Notification;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.uiactivities.FeeBalanceFrag;
import ultratude.com.mzizi.uiactivities.HomeFrag;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.MeanScoreFrag;
import ultratude.com.mzizi.uiactivities.NotificationFrag;

/**
 * Created by James on 07/07/2018.
 */

public class GetXInstanceFromRoomDB extends AsyncTask<String,Void,Map<String, Object>> {

    //private WeakReference<Object> fragWeakReference;
    private WeakReference<MainActivity> mainActivityWeakReference;//for just changing the text at the toolbar
    private WeakReference<Object> fragWeakReference;

    public GetXInstanceFromRoomDB(MainActivity mainActivity , Object frag ){
        this.mainActivityWeakReference = new WeakReference<>(mainActivity);
        if(frag instanceof FeeBalanceFrag){
            //will constraint the object to be FeeBalanceFrag if it is an instance of it
            fragWeakReference = new WeakReference<>(frag);
        }else if(frag instanceof HomeFrag){
            fragWeakReference = new WeakReference<>(frag);
        }else if(frag instanceof MeanScoreFrag){
            fragWeakReference = new WeakReference<>(frag);
        }else if(frag instanceof NotificationFrag){
            fragWeakReference = new WeakReference<>(frag);
//        }else if(frag instanceof UpcomingEventsFrag){
//            fragWeakReference = new WeakReference<>(frag);
//        }else if(frag instanceof PortalChatsFragment){
//            fragWeakReference = new WeakReference<>(frag);
        }



    }

    @Override
    protected void onPreExecute() {
        //Do nothing
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(Map<String, Object> stringObjectMap) {


        if(stringObjectMap.containsKey(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS)){
            List<PortalStudentDetailedResults> studentResultList = (List<PortalStudentDetailedResults>) stringObjectMap.get(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS);
           // Toast.makeText(mainActivityWeakReference.get(), studentList.getDateOfAdmission(), Toast.LENGTH_LONG).show();



//            AlertDialog.Builder d = new AlertDialog.Builder(mainActivityWeakReference.get());
//            d.setMessage(studentList.getDateOfAdmission());
//            d.show();

            //list of years from date of admission to current year


            List<String> uniqueYears = new ArrayList<>();

           for(int j= 0; j<studentResultList.size(); j++){
               String beforeUniqueYear = studentResultList.get(j).getCurrentYear();
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



                //list of years currently with results
                Collections.sort(sortList, Collections.<String>reverseOrder());

                //by now years list is field with data
                final Spinner year_spinner =   ((MeanScoreFrag) fragWeakReference.get()).year_spinner;
                ArrayAdapter<String> spinner_adapter = new ArrayAdapter<>(mainActivityWeakReference.get(),
                        android.R.layout.simple_spinner_dropdown_item, sortList);
                year_spinner.setAdapter(spinner_adapter);
                 String yearToDisplayResults;//is the default value
                    if(year_spinner !=null)
                       yearToDisplayResults  = year_spinner.getSelectedItem().toString();

                //new XFragRetrieveAndDisplayRoomData((fragWeakReference.get()), mainActivityWeakReference.get(), yearToDisplayResults).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS);

                //by default display the results of the current year

                year_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id){
                        String yearToDisplayResults = year_spinner.getSelectedItem().toString();
                        //the selectd is what is selected.
                        //Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(), yearToDisplayResults, Toast.LENGTH_LONG).show();
                        // new XFragRetrieveAndDisplayRoomData( (fragWeakReference.get()), new MainActivity(), yearToDisplayResults ).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS);
                        new XFragRetrieveAndDisplayRoomData( (fragWeakReference.get()), new MainActivity(), yearToDisplayResults ).execute(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView){

                    }
                });

            }else{

                //do nothing
            }






        super.onPostExecute(stringObjectMap);
    }

    @Override
    //delete or get istu
    protected  Map<String, Object> doInBackground(String... strings) {//GetConstants.GET_NOTIFICATION,DeleteConstants.DeleteNotification

        String identifierGet =  strings[0];//dictates what you want
        String identifierDelete = strings[1];//dictates what you want

        Map<String, Object>  returnResults = new HashMap<>();

        String studentid = mainActivityWeakReference.get().db.getPortalSiblingsDao().getMainStudentFromSibling();
        if(studentid == null){
            studentid  = "0";
        }

        if(identifierGet.equals(GetConstants.GET_NOTIFICATION)){
            if(identifierDelete.equals(DeleteConstants.DELETENOTIFICATION)){
                mainActivityWeakReference.get().db.getNotificationsDao().getNotifications();
                returnResults = new HashMap<>();
                returnResults.put("deleteStatus", true);
                return returnResults;
            }

            List<Notification> notificationList = mainActivityWeakReference.get().db.getNotificationsDao().getNotifications();
            //checking if there are any notification
            if(notificationList.size()<1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_NOTIFICATION, new ArrayList<Notification>() );
                return returnResults;
            }
            //remember that this returns a list of notifications
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_NOTIFICATION, notificationList );
            return returnResults;

        }else if(identifierGet.equals(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT)){

            if(identifierDelete.equals(DeleteConstants.DELETEPORTALSTUDENTINFO_CONSTANT)){

                mainActivityWeakReference.get().db.getPortalStudentInfoDao().deleteStudent(Integer.valueOf(studentid));
                returnResults = new HashMap<>();
                returnResults.put("deleteStatus", true);
                return returnResults;
            }

            List<PortalStudentInfo> studentList = (mainActivityWeakReference.get()).db.getPortalStudentInfoDao().getPortalStudentsInfo(Integer.valueOf(studentid));
            if(studentList.size() <1){

                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT, new PortalStudentInfo());
                return returnResults;

            }
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT, studentList.get(0));
            return returnResults;


        }else if(identifierGet.equals(GetConstants.GET_PORTAL_DETAILED_TRANSACTION)){

            if(identifierDelete.equals(DeleteConstants.DELETEPORTALDETAILEDTRANSACTION)){

                mainActivityWeakReference.get().db.getPortalDetailedTransactionDao().deletePortalDetailedTransaction(Integer.valueOf(studentid));
                returnResults = new HashMap<>();
                returnResults.put("deleteStatus", true);
                return returnResults;

            }

            List<PortalDetailedTransaction> detailedTransactionsList = (mainActivityWeakReference.get()).db.getPortalDetailedTransactionDao().getPortalDetailedTransaction(Integer.valueOf(studentid));
            //checking if there any transactions for this student
            if(detailedTransactionsList.size() < 1){

                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_DETAILED_TRANSACTION, new ArrayList<PortalDetailedTransaction>());
                return returnResults;


            }

            //remember this is returning a list of transactions
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_DETAILED_TRANSACTION, detailedTransactionsList);
            return returnResults;

        }else if(identifierGet.equals(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS)){

            if(identifierDelete.equals(DeleteConstants.DELETEPORTALSTUDENTDETAILEDRESULTS)){

                mainActivityWeakReference.get().db.getPortalStudentDetailedResultsDao().deletePortalStudentDetialedResults(Integer.valueOf(studentid));
                returnResults = new HashMap<>();
                returnResults.put("deleteStatus", true);
                return returnResults;

            }

            List<PortalStudentDetailedResults> studentDetailedResults = (mainActivityWeakReference.get()).db.getPortalStudentDetailedResultsDao().getPortalStudentDetailedResults(Integer.valueOf(studentid));

            if(studentDetailedResults.size() < 1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,new ArrayList<PortalStudentDetailedResults>() );
                return returnResults;


            }
            //remember this is returning a list of transactions
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,studentDetailedResults);
            return returnResults;


        }

        return returnResults;//will not contain any of the above keys
    }

}
