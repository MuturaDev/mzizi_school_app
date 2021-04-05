package ultratude.com.mzizi.backgroundtasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ultratude.com.mzizi.helperactivityclasses.BottomSheetRecyclerAdapter;
import ultratude.com.mzizi.helperactivityclasses.MeanScoreRecycleViewAdapterTest;
import ultratude.com.mzizi.helperactivityclasses.SchoolContactsRecycler;
import ultratude.com.mzizi.helperactivityclasses.UpcomingEventsRecyclerAdapter;
import ultratude.com.mzizi.modelclasses.GetConstants;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.Notification;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.uiactivities.FeeBalanceFrag;
import ultratude.com.mzizi.uiactivities.FloatingActionShow;
import ultratude.com.mzizi.uiactivities.HomeFrag;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.MeanScoreFrag;
import ultratude.com.mzizi.uiactivities.NotificationFrag;
import ultratude.com.mzizi.uiactivities.SchoolContactsFrag;
import ultratude.com.mzizi.uiactivities.UpcomingEventsFrag;

/**
 * Created by James on 07/07/2018.
 */

public class XFragRetrieveAndDisplayRoomData extends AsyncTask<String,Void, Map<String, Object>> {

    private WeakReference<Object> fragWeakReference;
    private WeakReference<MainActivity> mainActivityWeakReference;//for just changing the text at the toolbar


    private String yearToDisplayResults;

    public XFragRetrieveAndDisplayRoomData(Object frag, MainActivity mainActivity , String yearToDisplayResults ){
       this.yearToDisplayResults = yearToDisplayResults;

        this.mainActivityWeakReference = new WeakReference<>(mainActivity);


        if(frag instanceof FeeBalanceFrag){
            //will constraint the object to be FeeBalanceFrag if it is an instance of it
            fragWeakReference = new WeakReference<>(frag);
        }else if(frag instanceof HomeFrag){

            fragWeakReference = new WeakReference<>(frag);
            ((HomeFrag) fragWeakReference.get()).showProgress(false);
        }else if(frag instanceof MeanScoreFrag){
            fragWeakReference = new WeakReference<>(frag);
        }else if(frag instanceof NotificationFrag){
            fragWeakReference = new WeakReference<>(frag);
            ((NotificationFrag) fragWeakReference.get()).showProgress(false);
        }else if(frag instanceof UpcomingEventsFrag){
            fragWeakReference = new WeakReference<>(frag);
//        }else if(frag instanceof PortalChatsFragment){
//            fragWeakReference = new WeakReference<>(frag);
        }else if(frag instanceof SchoolContactsFrag){
            fragWeakReference = new WeakReference<>(frag);
        }

    }

    @Override
    protected void onPreExecute() {



        super.onPreExecute();
    }

    @Override
    protected void onPostExecute( Map<String, Object> returnResults) {




        //you pass in what you want, notifications, studentinfo
        if(returnResults.containsKey(GetConstants.GET_NOTIFICATION)){//rememeber this is returning a list
//
//
//            List<Notification> listOfNotification = (List<Notification>) returnResults.get(GetConstants.GET_NOTIFICATION) ;
//
//
//            if(listOfNotification.size() >=1){
//                //     Toast.makeText(mainActivityWeakReference.get(), listOfNotification.toString(), Toast.LENGTH_SHORT).show();
//                //create the an object of notificationAdapter found in NotificationFrag
//                ((NotificationFrag)  fragWeakReference.get()).notificationAdapter = new NotificationRecycleViewAdapter(((NotificationFrag) fragWeakReference.get()).getActivity(), listOfNotification );
//
//
//                ((NotificationFrag) fragWeakReference.get()).recyclerView.setAdapter(((NotificationFrag)  fragWeakReference.get()).notificationAdapter);
//                ((NotificationFrag) fragWeakReference.get()).recyclerView.scrollToPosition(listOfNotification.size() - 1);
//
////            if(((NotificationFrag) fragWeakReference.get()).isVisible()){
////
////            }else if(!((NotificationFrag) fragWeakReference.get()).isVisible()){
////                FragTransaction.dislayFragment(NotificationFrag.class, mainActivityWeakReference.get().fragmentManager);
////                mainActivityWeakReference.get().floatbutton.setVisibility(View.INVISIBLE);
////
////            }
//
//                // fragWeakReference.get().notificationInstancesList = (List<Notification>) objectCarrier;
//
//                if(((NotificationFrag) fragWeakReference.get()).isVisible()) {
//                    ((NotificationFrag) fragWeakReference.get()).showProgress(false);
//                }else{
//                    return;
//                }
//            }else{
//                Toast.makeText(((NotificationFrag)  fragWeakReference.get()).getActivity(), "No Notifications to show", Toast.LENGTH_SHORT).show();
//            }



        }else if(returnResults.containsKey(GetConstants.GET_PORTAL_DETAILED_TRANSACTION)){//remember this is returning a list

//            if(((FeeBalanceFrag)  fragWeakReference.get()).isVisible())
//            ((FeeBalanceFrag)  fragWeakReference.get()).showProgress(false);
//
//            List<PortalDetailedTransaction> transactions =  (List<PortalDetailedTransaction>) returnResults.get(GetConstants.GET_PORTAL_DETAILED_TRANSACTION);
//
//            if(transactions.size() >=1){
//
//                NumberFormat myFormat = NumberFormat.getInstance();
//                myFormat.setGroupingUsed(true);
//
//                 if(!transactions.get(transactions.size() - 1).getCurrentAmount().equals("")){
//                     ((FeeBalanceFrag)  fragWeakReference.get()).current_balance.setText("Current Balance is: " + myFormat.format(Double.parseDouble(transactions.get(transactions.size() - 1).getCurrentAmount())));
//                 }else{
//                     ((FeeBalanceFrag)  fragWeakReference.get()).current_balance.setText("");
//                 }
//
//
//
//                ((FeeBalanceFrag)  fragWeakReference.get()).feeBalanceRecyclerViewAdapter = new FeeBalanceRecyclerViewAdapter(((FeeBalanceFrag) fragWeakReference.get()).getActivity(), transactions );
//
//                ((FeeBalanceFrag) fragWeakReference.get()).recyclerView.setAdapter(((FeeBalanceFrag)  fragWeakReference.get()).feeBalanceRecyclerViewAdapter);
//                ((FeeBalanceFrag) fragWeakReference.get()).recyclerView.scrollToPosition(transactions.size() - 1);



                //this is was hard coded, just uncomment it for future use, maybe

                //views store
//                List<TextView[]> holdRows = new ArrayList<>();
//
//                TextView[] rowone = new TextView[] {
//                        ( (FeeBalanceFrag) fragWeakReference.get()).transport_narration,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).transport_amount,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).transport_balance
//                };
//                holdRows.add(rowone);
//
//                TextView[] rowtwo = new TextView[] {
//                        ( (FeeBalanceFrag) fragWeakReference.get()).tuition_narration,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).tuition_amount,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).tuition_balance
//                };
//                holdRows.add(rowtwo);
//                TextView[] rowthree = new TextView[] {
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt_narration,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt_amount,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt_balance
//                };
//
//                holdRows.add(rowthree);
//                TextView[] rowfour = new TextView[] {
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt2_narration,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt2_amount,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt2_balance
//                };
//                holdRows.add(rowfour);
//                TextView[] rowfive = new TextView[] {
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt3_narration,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt3_amount,
//                        ( (FeeBalanceFrag) fragWeakReference.get()).receipt3_balance
//                };
//                holdRows.add(rowfive);
//
//
//
//
//                for(int i = 0; i < transactions.size(); i++){
//
//                    for(int j =0; j<=i; j++ ){
//                        int k = 0;
//                        while(k < holdRows.get(j).length){
//
//                            TextView[] d =  holdRows.get(i);
//                            if(k == 0){
//
//                                (d[k]).setText( transactions.get(i).getRefNo());
//                                //transactions.get(i).getRefNo().replace(".0000", "")
//
//                            }else if(k == 1){
//
//                                NumberFormat myFormat = NumberFormat.getInstance();
//                                myFormat.setGroupingUsed(true);
//
//                                (d[k]).setText( myFormat.format(Double.parseDouble(transactions.get(i).getTranAmount())));
//
//                                //(d[k]).setText(String.format("%,.2f", Double.parseDouble()) );
//                                //transactions.get(i).getTranAmount()
//                            }else if(k == 2){
//
//                                NumberFormat myFormat = NumberFormat.getInstance();
//                                myFormat.setGroupingUsed(true);
//
//                                (d[k]).setText( myFormat.format(Double.parseDouble(transactions.get(i).getBalAmount())));
//
//                                // (d[k]).setText(String.format("%,.2f", Double.parseDouble(transactions.get(i).getBalAmount())) );
//
//                                //transactions.get(i).getBalAmount()
//                            }else if(i == (transactions.size() - 1)){
//                                // ( (FeeBalanceFrag) fragWeakReference.get()).current_balance.setText("Current Balance is Ksh. "+(transactions.get(i).getCurrentAmount()));
//
//
//                                // ( (FeeBalanceFrag) fragWeakReference.get()).current_balance.setText("Current Balance is Ksh. "+(transactions.get(transactions.size() - 1)).getCurrentAmount());
//                            }
//                            k++;
//                        }
//
//                    }
//                }







//            }else{
//
//
//                //Toast.makeText(mainActivityWeakReference.get(), "No fee transactions to show", Toast.LENGTH_SHORT);
//                //do nothing if there is no data
//            }



            // fragWeakReference.get().portalDetailedTransactionInstancesList = (List<PortalDetailedTransaction>) objectCarrier;
        }else if(returnResults.containsKey(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS)){//remember this is return ing a list

            if(((MeanScoreFrag)  fragWeakReference.get()).isAdded())
                ((MeanScoreFrag)  fragWeakReference.get()).showProgress(false);



            List<PortalStudentDetailedResults> detailedResults = (List<PortalStudentDetailedResults>) returnResults.get(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS) ;


            if(detailedResults.size()>0){


                //you can send two list, the list of headers, and the list of data to display
                //list of headers
                //headerResultsList



                //start

                //the data stored here is stored depending on the year selected

                List<PortalStudentDetailedResults> filteredByYearDetailedResults = new ArrayList<>();


//            for(int i= 0; i<detailedResults.size(); i++){
//                detailedResults.get(i).getCurrentYear();
//
//                if(detailedResults.get(i).getCurrentYear() == yearToDisplayResults){
//                    filteredDetailedResults.add(detailedResults.get(i));
//                }
//            }

                //use either

                for(int k = 0; k< detailedResults.size(); k++){
                    String currentYear = detailedResults.get(k).getCurrentYear();
                    if(!yearToDisplayResults.equals("")){
                        //remember hard coded

                        //Toast.makeText(((ViewExamHistory) fragWeakReference.get()).getActivity(),"Date Of Admission: " + yearToDisplayResults, Toast.LENGTH_LONG).show();
                        if(currentYear.equals(yearToDisplayResults)){
                            //those results of this specific year are stored in filteredDetailedResults
                            filteredByYearDetailedResults.add(detailedResults.get(k));
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
                List<List<PortalStudentDetailedResults>> headerResultList = new ArrayList<>();//This will contain resultsSpecificToPeriod

                //this is not working
                //this will container, different instances of PortalStudentInfo, specific to period


                for(int z = 0; z< uniquePeriodHeaders.size(); z++){


                    List<PortalStudentDetailedResults> resultsSpecificToPeriod = new ArrayList<>();

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
                ((MeanScoreFrag)  fragWeakReference.get()).meanScoreRecycleViewAdapterTest = new MeanScoreRecycleViewAdapterTest(((MeanScoreFrag) fragWeakReference.get()),  uniquePeriodHeaders ,headerResultList);


                ((MeanScoreFrag) fragWeakReference.get()).recyclerView.setAdapter(((MeanScoreFrag)  fragWeakReference.get()).meanScoreRecycleViewAdapterTest);
                ((MeanScoreFrag) fragWeakReference.get()).recyclerView.scrollToPosition(0);

                ((MeanScoreFrag)  fragWeakReference.get()).meanscore_layout_noContent.setVisibility(View.INVISIBLE);
                ((MeanScoreFrag)  fragWeakReference.get()).layoutabove.setVisibility(View.VISIBLE);
                ((MeanScoreFrag)  fragWeakReference.get()).recyclerView.setVisibility(View.VISIBLE);

                if(((MeanScoreFrag)  fragWeakReference.get()).isAdded())
                    ((MeanScoreFrag)  fragWeakReference.get()).showProgress(false);



            }else{

                ((MeanScoreFrag)  fragWeakReference.get()).meanscore_layout_noContent.setVisibility(View.VISIBLE);
                ((MeanScoreFrag)  fragWeakReference.get()).layoutabove.setVisibility(View.VISIBLE);//SHOULD BE CHANGED IN...
                ((MeanScoreFrag)  fragWeakReference.get()).recyclerView.setVisibility(View.INVISIBLE);


                if(((MeanScoreFrag)  fragWeakReference.get()).isAdded())
                    ((MeanScoreFrag)  fragWeakReference.get()).showProgress(false);

            }

        }else if(returnResults.containsKey(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT)){

            PortalStudentInfo studentInfo =  (PortalStudentInfo) returnResults.get(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT);




            if(!studentInfo.getRegistrationNumber().equals(" ")){



                NumberFormat myFormatOne = NumberFormat.getInstance();
                myFormatOne.setGroupingUsed(true);

                if(!(( fragWeakReference.get()) == null)){
                    ((HomeFrag) fragWeakReference.get()).feetext.setText((studentInfo.getBalance() == "") ? "" : myFormatOne.format(Double.valueOf(( studentInfo).getBalance())));
                    ((HomeFrag) fragWeakReference.get()).currentmeanscoretext_ID.setText((studentInfo).getMeanScore()+"% ( "+ (studentInfo).getMeanGrade() + " )");
                    ((HomeFrag) fragWeakReference.get()).upcomingeventstext.setText("( " +(studentInfo).getEvents()+ " )");
                    //   mainActivityWeakReference.get().setTitle(((SyncMyAccountResult) objectCarrier).getSchoolName().toUpperCase());

                }




            }




        }else if(returnResults.containsKey(GetConstants.GET_PORTAL_EVENTS)){
            if(((UpcomingEventsFrag) fragWeakReference.get()).isVisible()){
                ((UpcomingEventsFrag) fragWeakReference.get()).showProgress(false);
            }


           List<PortalEvents>  portalEventsList1 = (List<PortalEvents>) returnResults.get(GetConstants.GET_PORTAL_EVENTS) ;

        //    Toast.makeText(((UpcomingEventsFrag) fragWeakReference.get()).getActivity(), "Success events: " + String.valueOf(portalEventsList1.get(0).getDescription()), Toast.LENGTH_LONG).show();


            //


            if(!portalEventsList1.isEmpty()){
                //HARD CODED DATA
               // Toast.makeText(((UpcomingEventsFrag) fragWeakReference.get()).getActivity(), portalEventsList1.get(1).getEventsTitle() , Toast.LENGTH_LONG).show();

//            List<PortalEvents> portalEventsList = new ArrayList<>();
//            PortalEvents portalEvents = new PortalEvents();
//            portalEvents.setEventsTitle("Form 1 Admission");
//            portalEvents.setVenue("School Play Ground");
//            portalEvents.setStartDate("5/1/2019");
//            portalEvents.setEndDate("30/1/2019");
//
//            portalEvents.setStartTime("07:00 AM");
//            portalEvents.setEndTime("6:00 PM");
//            portalEvents.setDescription("NOTE: You should be at the admission desk as early as possible.\n" +
//                    "DON'T FORGET TO COME WITH YOUR ADMISSION LETTER\n" +
//                    "\n" +
//                    "School Fees should be paid on the various bank acounts provided on the admission letter.\n" +
//                    "NO STUDENT WILL BE ADMITTED WITHOUT FULLY PAYING ALL HIS OR HER SCHOOL FEE!");
//
//            portalEventsList.add(portalEvents);
          //  portalEventsList.add(portalEvents);



                ((UpcomingEventsFrag)  fragWeakReference.get()).upcomingEventsRecyclerAdapter = new UpcomingEventsRecyclerAdapter(((UpcomingEventsFrag) fragWeakReference.get()).getActivity(), portalEventsList1 );


                ((UpcomingEventsFrag) fragWeakReference.get()).recyclerView.setAdapter(((UpcomingEventsFrag)  fragWeakReference.get()).upcomingEventsRecyclerAdapter );
                ((UpcomingEventsFrag) fragWeakReference.get()).recyclerView.scrollToPosition(portalEventsList1.size() - 1);
            }else{
                Toast.makeText( ((UpcomingEventsFrag) fragWeakReference.get()).getActivity(), "No upcoming events to show", Toast.LENGTH_SHORT).show();
            }





        }else if(returnResults.containsKey(GetConstants.GET_PORTAL_SIBLINGS)){

            List<PortalSiblings>  portalSiblingsList = (List<PortalSiblings>) returnResults.get(GetConstants.GET_PORTAL_SIBLINGS);
            AuthenticateUserResponse authenticatedUser = (AuthenticateUserResponse) returnResults.get("authenticatedUser");


            //CHANGET THIS TO A LOCATION WHERE IT WORKS FAST
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    ParentMziziDatabase db = ParentMziziDatabase.getInstance(mainActivityWeakReference.get());
                    String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                    if(studentid == null){
                        studentid  = "0";
                    }
                    return db.getGlobalSettingsDAO().getGlobalSettings(Constants.CHAT_ENABLED,Integer.valueOf(studentid));

                }

                @Override
                protected void onPostExecute(Object o) {


                    List<GlobalSettings>  list = (List<GlobalSettings>)o;
                   //Toast.makeText(mainActivityWeakReference.get(), "GlobalSettingsValue: " + String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                    if(list.size() > 0){

                        GlobalSettings globalSettings = list.get(0);
                        if(globalSettings.getGlobalSettingsValue().contains("YES")){
                            FloatingActionShow.supportsSchoolChat(true);
                            FloatingActionShow.showSchoolChat(true);
                            Log.d(mainActivityWeakReference.get().getPackageName().toUpperCase(), "Global SettingsFragment: " + globalSettings.getGlobalSettingsValue().contains("YES"));

                        }else if(globalSettings.getGlobalSettingsValue().contains("NO")){
                            FloatingActionShow.supportsSchoolChat(false);
                            FloatingActionShow.showSchoolChat(false);
                            Log.d(mainActivityWeakReference.get().getPackageName().toUpperCase(), "Global SettingsFragment: " + globalSettings.getGlobalSettingsValue().contains("YES"));

                        }else{
                            FloatingActionShow.supportsSchoolChat(false);
                            FloatingActionShow.showSchoolChat(false);
                            Log.d(mainActivityWeakReference.get().getPackageName().toUpperCase(), "Global SettingsFragment: " + globalSettings.getGlobalSettingsValue().contains("YES"));


                        }
                    }
                    super.onPostExecute(o);
                }
            };
            asyncTask.execute();




          // Toast.makeText(mainActivityWeakReference.get(), "Siblings: " + portalSiblingsList.toArray().toString(), Toast.LENGTH_LONG).show();

            //HARDCODED DATA, remember to comment
//        List<PortalSiblings> siblings = new ArrayList<>();
//
//            PortalSiblings siblingone = new PortalSiblings();
//            siblingone.setStudentFullName("John Kimathi");
//            siblingone.setUserID("1533");
//
//
//
//        siblings.add(siblingone);
////
//







          // Toast.makeText(mainActivityWeakReference.get(), String.valueOf(portalSiblingsList.size()), Toast.LENGTH_LONG ).show();


            if((portalSiblingsList.size() >0)){

                //show the floatingActionButton
               FloatingActionShow.otherSiblings(true);
                FloatingActionShow.showFloat(true);

                List<PortalSiblings> siblings = new ArrayList<>();
                for(int k =0; k< portalSiblingsList.size(); k++){
                    if(portalSiblingsList.get(k).getStudentID().equals(authenticatedUser.getUserID())){
                        //do nothing, this student is already logged in

                    }else{
                        siblings.add(portalSiblingsList.get(k));
                    }
                }


                mainActivityWeakReference.get().bottomrecycler = new BottomSheetRecyclerAdapter(mainActivityWeakReference.get(), siblings,mainActivityWeakReference.get().bottomSheetDialog);
                mainActivityWeakReference.get().recyclerView.setAdapter(mainActivityWeakReference.get().bottomrecycler);


            }else{
                FloatingActionShow.otherSiblings(false);
                FloatingActionShow.showFloat(false);
                //do nothing

                //Toast.makeText(mainActivityWeakReference.get(), "No data  to display", Toast.LENGTH_LONG).show();
            }


        }else if(returnResults.containsKey(GetConstants.GET_PORTAL_CONTACTS)){






            List<PortalContacts> portalContactsList =  (List<PortalContacts>) returnResults.get(GetConstants.GET_PORTAL_CONTACTS);

            if(!portalContactsList.isEmpty()) {

                if(!((SchoolContactsFrag) fragWeakReference.get()).getActivity().equals(null)){
                    ((SchoolContactsFrag) fragWeakReference.get()).schoolContactsRecycler = new SchoolContactsRecycler(((SchoolContactsFrag) fragWeakReference.get()).getActivity(), portalContactsList);

                    ((SchoolContactsFrag) fragWeakReference.get()).recyclerView.setAdapter(((SchoolContactsFrag) fragWeakReference.get()).schoolContactsRecycler);
                    ((SchoolContactsFrag) fragWeakReference.get()).recyclerView.scrollToPosition(portalContactsList.size() - 1);
                }



            }else{
                Toast.makeText( ((SchoolContactsFrag) fragWeakReference.get()).getActivity(), "No school contacts to show", Toast.LENGTH_SHORT).show();
            }


            } else{
            Toast.makeText(new MainActivity(), "An error occured when retrieving data from the database", Toast.LENGTH_SHORT).show();
        }







        super.onPostExecute(returnResults);
    }



    @Override
    protected  Map<String, Object> doInBackground(String... strings) {
        String identifier =  strings[0];//dictates what you want
        Map<String, Object>  returnResults = new HashMap<>();


        ParentMziziDatabase db = ParentMziziDatabase.getInstance(mainActivityWeakReference.get());

        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
        if(studentid == null){
            studentid = "0";
        }

        if(identifier.equals(GetConstants.GET_NOTIFICATION)){

            List<Notification> notificationList = db.getNotificationsDao().getNotifications();

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

        }else if(identifier.equals(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT)){
//            List<SyncMyAccountResult> studentList = ( (HomeFrag) fragWeakReference.get()).db.getPortalStudentInfoDao().getPortalStudentsInfo();
//            if(studentList.size() <1){
//
//                returnResults = new HashMap<>();
//                returnResults.put(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT, new SyncMyAccountResult());
//                return returnResults;
//
//            }
//            returnResults = new HashMap<>();
//            returnResults.put(GetConstants.GET_PORTAL_STUDENT_INFO_CONSTANT, studentList.get(0));
//            return returnResults;


        }else if(identifier.equals(GetConstants.GET_PORTAL_DETAILED_TRANSACTION)){
//            List<PortalDetailedTransaction> detailedTransactionsList = ( (FeeBalanceFrag) fragWeakReference.get()).db.getPortalDetailedTransactionDao().getPortalDetailedTransaction();
//            //checking if there any transactions for this student
//            if(detailedTransactionsList.size() < 1){
//
//                returnResults = new HashMap<>();
//                returnResults.put(GetConstants.GET_PORTAL_DETAILED_TRANSACTION, new ArrayList<PortalDetailedTransaction>());
//                return returnResults;
//
//
//            }
//
//            //remember this is returning a list of transactions
//            returnResults = new HashMap<>();
//            returnResults.put(GetConstants.GET_PORTAL_DETAILED_TRANSACTION, detailedTransactionsList);
//            return returnResults;

        }else if(identifier.equals(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS)){


            List<PortalStudentDetailedResults> studentDetailedResults = db.getPortalStudentDetailedResultsDao().getPortalStudentDetailedResults(Integer.valueOf(studentid));

            if(studentDetailedResults.size() < 1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,new ArrayList<PortalStudentDetailedResults>() );
                return returnResults;


            }
            //remember this is returning a list of transactions
            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_STUDENT_DETAILED_RESULTS,studentDetailedResults);
            return returnResults;


        }else if(identifier.equals(GetConstants.GET_PORTAL_EVENTS)){

            List<PortalEvents> portalEventsList = db.getPortalEventsDao().getPortalEvents(Integer.valueOf(studentid));



            if(portalEventsList.size() < 1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_EVENTS, new ArrayList<PortalEvents>());
                return returnResults;
            }

            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_EVENTS, portalEventsList);
            return returnResults;
        }else if(identifier.equals(GetConstants.GET_PORTAL_SIBLINGS)){
            List<PortalSiblings> portalSiblingsList  = db.getPortalSiblingsDao().getSiblings();
            List<AuthenticateUserResponse> authenticateUser = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();


            if(portalSiblingsList.size() <1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_SIBLINGS, new ArrayList<PortalSiblings>());
                return returnResults;
            }

            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_SIBLINGS, portalSiblingsList);//0
            try{
                returnResults.put("authenticatedUser", authenticateUser.get(0));// 1

            }catch(ArrayIndexOutOfBoundsException e){
                ///do nothing
            }
        }else if(identifier.equals(GetConstants.GET_PORTAL_CONTACTS)){

            List<PortalContacts> portalContactsList  = db.getPortalContactsDao().getPortalContacts(Integer.valueOf(studentid));

            if(portalContactsList.size() <1){
                returnResults = new HashMap<>();
                returnResults.put(GetConstants.GET_PORTAL_CONTACTS, new ArrayList<PortalContacts>());
                return returnResults;
            }

            returnResults = new HashMap<>();
            returnResults.put(GetConstants.GET_PORTAL_CONTACTS, portalContactsList);

        }

        return returnResults;//will not contain any of the above keys
    }

}
