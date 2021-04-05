//package ultratude.com.mzizi.webservice.apiconnections;
//
//
//import android.content.Context;
//
//import android.os.AsyncTask;
//
//import android.util.Log;
//import android.widget.Toast;
//
//
//import org.json.JSONArray;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
//import ultratude.com.mzizi.modelclasses.MessageRequest;
//import ultratude.com.mzizi.modelclasses.ParentChatRequest;
//import ultratude.com.mzizi.modelclasses.ReadNotReadNotification;
//import ultratude.com.mzizi.modelclasses.ReadNotReadNotificationDAO;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
//
//import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
//import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.ParentChatDAO;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
//import ultratude.com.mzizi.webservice.APIRequest;
//
///**
// * Created by James on 05/07/2018.
// */
//
//public class GetNotificationFromMziziApi extends AsyncTask<List<Object>, Void, List<Object>>{
//
//
//
//   // WeakReference<MainActivity> mainActivityWeakReference;
//    //mainactivity, notifactionNotify, GetNotificationFromMziziApi;
//
//    Context context;
//    Student student;
//
//
//    //the RoomDatabase
//
//   // NotificationFrag notificationFrag;
//
//    public GetNotificationFromMziziApi(){
//
//    }
//
//    public GetNotificationFromMziziApi( Context context, Student student){
//
//       // this.mainActivityWeakReference = new WeakReference<>(mainActivity);
//
//        this.context = context;
//        this.student = student;
//
//
//       // Paper.init(context);
//
////
////        notificationFrag = new NotificationFrag();
//    }
//
//
//
//
//    @Override
//    protected void onPreExecute() {
//
//
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute( List<Object>   holder) {
//
//
//        //START
//
//       // Toast.makeText(context, "Notification Size"+ holder, Toast.LENGTH_LONG).show();
//
//
//
//
//        if((holder.size()>0)){
//
//            List<Notification> listNotifications = (List<Notification>) holder.get(0);
//
//
//        if(listNotifications.size()>0 ){
//                //HERE you update the notification list
//
//
//
//            SyncMyAccountResult syncMyAccountResult = new SyncMyAccountDAO(context).getFilteredPortalStudentInfoResult();
//            String billingBalance = syncMyAccountResult.getBillingBalance();
//            if(billingBalance.equals("")){//equal to an empty string
//                //do nothing
//            }else{//not equal to an empty string
//
//                if(Float.valueOf(billingBalance) > 0f){//dont show notifications
//
//                    //DO NOTHING
//
//                }else{//If the balance is less than 0.0f, show notifications
//
//
//                    //Toast.makeText(context, "billingBalance: " + billingBalance, Toast.LENGTH_LONG).show();
////                    Paper.book().delete("NewNotification");
////                    Paper.book().write("NewNotification", listNotifications);
//
//                    Log.d(context.getPackageName().toUpperCase(), "Should send notification: " + listNotifications.toString());
//                    NotificationTopDisplay.showNotificationDisplay(listNotifications, context,Integer.valueOf(student.getStudentID()),"NotificationFromMziziApi");
//
//                    List<ReadNotReadNotification> readNotReadNotificationList = new ArrayList<>();
//                    for(Notification notification : listNotifications){
//                        ReadNotReadNotification readNotification = new ReadNotReadNotification(
//                                String.valueOf(notification.getMsgID())
//                        );
//                        readNotReadNotificationList.add(readNotification);
//                    }
//                    //save any new notifications,, will be deleted after they are viewed from the notifications area,
//                    //this table will be used to identify new notifications and the old notifications to assign them different markers
//                    new ReadNotReadNotificationDAO(context).inserNotificationReadTracking(readNotReadNotificationList);
//
//                    //if you have five messages? all the same
//
//                    int countOne= 1;
//                    int countTwo = 1;
//
//                    //if one of the message has one of these then send the request once
//                    for(int i =0; i<listNotifications.size(); i++){
//                       String message =  listNotifications.get(i).getMsg();
//
//                        if(message.contains("Results") || message.contains("term") || message.contains("results") || message.contains("Term") ){
//                            if(countOne <=1) {
//                                //Toast.makeText(context, "send for results", Toast.LENGTH_SHORT).show();
//                                RequestFor.sendRequest(student, context,"", APIRequest.RESULTS_8_4_4);
//                                //sendRequestForPortalResults(student);
//                            }else{
//                                //dont send request
//                            }
//
//                            countOne++;
//
//
//                        } else if (message.contains("receipt") || message.contains("transaction") || message.contains("Receipt") || message.contains("Transaction") ) {
//                            if(countTwo<=1) {
//
//                                //Toast.makeText(context, "send for transaction"+ String.valueOf(listNotifications.size()), Toast.LENGTH_SHORT).show();
//                                RequestFor.sendRequest(student, context,"", APIRequest.TRANSACTION);
//
//                            }else{
//
//                            }
//
//                            countTwo++;
//
//                        }else if(message.contains("chat") || message.contains("Chat")){
//                            if(countTwo <= 1){
//                                RequestFor.sendRequest(student, context,"", APIRequest.PORTAL_CHAT);
//                               // sendRequestForParentChat(student,context);
//                                countTwo++;
//                            }
//                        }else if(message.contains("event") || message.contains("Event")){
//                            if(countTwo <=1){
//                                RequestFor.sendRequest(student, context,"", APIRequest.EVENTS);
//                               // sendRequestForEvents(student, context);
//                                countTwo++;
//                            }
//                        }
//
//
//                    }
//
//                }
//
//            }
//
//
//
//
//
//                // Toast.makeText(context, "Is NotificationFrag visible: " + new NotificationFrag().isVisible(), Toast.LENGTH_LONG).show();
//
//
//                    //there is no need to cancel the notification if the parent hasn't seen them
////                new Handler().postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        NotificationTopDisplay.cancelNotificationDisplay();
////                    }
////                }, 7000);
//
////                   if(FragTransaction.getFragmentIsVisible("NotificationFragment", ( (MainActivity) context.getApplicationContext()).fragmentManager)){
////                       try{
////                           int counter = 0;
////
////
////                           for(int j = 0; j<listNotifications.size(); j++){//3 messages
////                               String notification = listNotifications.get(j).getMsg();
////
////
////                               //within each iterate you should add the new message
////
////                               //my messagesstore is the RoomDatabase, note that. you should also note its already updated, since you called GetNotificationFromMziziApi, which is caled once
////                               //int newMessagePosition = messagestore.size() - 1;
////                               int c = j;//i want to increment the value of c, not j, coz j will couse problems. should only be added by j++, and j is for accessing the messages so it must be zero based.
////                               int newNotificationPosition/*should reach to 12*/ = (oldNotificationCount-1) + (c+1);//9+1=10, 9+2=11, 9+3=12
////                               //mMessageAdapter is,
////                               //notify recycler view insert one new data
////                               // mMessageAdapter.notifyItemChanged(newMessagePosition, sentmessage);
////                               ((NotificationFrag)  FragTransaction.getFragment("NotificationFragment", ( (MainActivity) context.getApplicationContext()).fragmentManager)).notificationAdapter.notifyItemChanged(newNotificationPosition, notification);
////                               //mMessageRecycler is,
////                               ((NotificationFrag)  FragTransaction.getFragment("NotificationFragment",( (MainActivity) context.getApplicationContext()).fragmentManager)).recyclerView.scrollToPosition(newNotificationPosition);
////                               //Scroll recyclerview to the last message
////                               // if((newNotificationPosition +1) == newNotificationCount ){
////                               // mMessageRecycler.scrollToPosition(newMessagePosition);
////                               // }
////
////                           }
////
////                       }catch(Exception e){
////                            e.printStackTrace();
////                        }
////
////                   }
//
//
//            }else{
//                //do nothing, no need to notify
//
//            }
//
//
//
////        }else if(backResults.equals("NO_CONTENT")){
////
////            //do nothing
////
////        }else if(backResults.equals("ERROR_OCCURRED")){
////
////            //do nothing
////
////        }
//
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//        super.onPostExecute( holder);
//    }
//
//
//    @Override
//    protected  List<Object>   doInBackground(List<Object>... lists) {
//
//        long added = 0;
//
//        long statuscode = 0;
//
//        List<Notification> listNotifications = new ArrayList<>() ;//used to add to the database
//
//        List<Notification> listNotificationsToDisplay = new ArrayList<>();
//
//        Notification message ;
//        List<Object> holder = new ArrayList<>();
//
//
//        try{
//
//
//
//            if(String.valueOf(statuscode).trim().equals("204")){
//
//                //return listNotifications;
//
//                holder.add(listNotifications);
//                // holder.add(String.valueOf(statuscode));
//               // return holder;
//
//                //return String.valueOf(statuscode + " StudentID " + requestDetail.getStudentID() +" appcode" + requestDetail.getAppcode() + " ");
//
//            }else if(String.valueOf(statuscode).trim().equals("500")){
//                //  return  listNotifications;
//                // return String.valueOf(statuscode + " StudentID " + requestDetail.getStudentID() +" appcode" + requestDetail.getAppcode() + " ");
//
//                holder.add(listNotifications);
//                //return holder;
//            }
//
//
//
//
//
//
//            //int msgID, String msg, String dateReceived
//            /* "MsgID": 6,
//                "Msg": "You have a new message 6",
//                "DateSent": "05/07/2018 04:43:54"*/
//
//            //This stupid thing made me wast my time, dont uncomment it.
////            jsonResponse.getJSONObject(1);
////            jsonResponse.length();
//
//
//
//            //filtering notification according to dates
//
//
//            //WITH THE NEW DATES THIS WILL NOT WORK
//
//            // List<Notification> listNotifications = new ArrayList<>();
//
//            //Current Date
//            Calendar currentDate = Calendar.getInstance();//this is todays date
//            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
//
//            String currentDateString = formatter.format(currentDate.getTime());
//            String dateSentString;
//
//            final Date currentd = new Date(currentDateString);//filterdd current date
//            Date datesent;///filted date notification sent
//
//
//
//
//            //Notification Date/Previous date
//            Date notificationDateSent;//date notification is sent//this message isnot filterd
//
//            // end filtering notificaitons accroding to dates.
//
//            List<Notification> list = (List<Notification>) lists[0].get(1);//used to show notifications
//
//
//            for(int i = 0; i <list.size(); i++) {
//
//                if(i==0){
//                    listNotifications.clear();
//                    listNotificationsToDisplay.clear();
//
//                }
//
//
//                message = new Notification();
//                message.setMsgID(list.get(i).getMsgID());
//                message.setMsg(list.get(i).getMsg());
//                message.setDateSent(list.get(i).getDateSent());
//
//                //  id = jsonResponse.length();
//
//
//                //will not be use for now
////                //ROOM DATABASE
////                id = db.getNotificationsDao().insertNotifications(message);
//
//
//                //id = jsonResponse.length();
//                // if(id==jsonResponse.length()){
//
//
//
//                //filter by the number of days
//
//                //"9/2/2018 1:55:56 PM"/ WORKS FOR BOTH DATES FORMARTS
//                notificationDateSent = new Date(message.getDateSent());//date you should get form every notification
//                dateSentString = formatter.format(notificationDateSent);//time sent
//                datesent = new Date(dateSentString);
//
//                long diff = Math.abs(currentd.getTime() - datesent.getTime());
//
//                final long days = diff / (24 * 60 * 60 * 1000);
//
//                //System.out.println("Days between: " + days);
//
//                if(days >= 30){//notifications with days greater than or equal to 14 will be deleted
//                    //System.out.println("Delete this message in the datebase");
//                    // new NotificationDAO(context).deleteOneNotification(message.getMsgID());
//
//                    //here you dont delete the message, you just dont save it in the database
//
//
//                }else{
//
//                    //you now save the data that was sent within 7 days before today
//
//                    //System.out.println("Don't delete this message, you can now add it to list for  display");
//                    listNotifications.add(message);
//                    listNotificationsToDisplay.add(message);
//
//                }
//
//
//                //end of filter
//
//                // mModel.getCurrentCount().postValue(String.valueOf(notificationList.size()));
//
//                //  }
//
//                //   }
//            }
//
//
//            //Inser to the SQLite database
//
//            added =  new NotificationDAO(context).insertNotifications(listNotifications);
//
//
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//            holder.add(listNotificationsToDisplay);
//
//
//        return holder;
//        //return String.valueOf(listNotifications.size());
//        // return String.valueOf(added);
////        return String.valueOf(textmessage);
//        // return String.valueOf(statuscode + " \nStudentID " + requestDetail.getStudentID() +" \nappcode" + requestDetail.getAppcode() + " \nLastMessageID" + message.getMsgID() );
//
//
//    }
//
//
//    public void SendRequest(final MessageRequest messageRequest) {
//
//      // Toast.makeText(context, messageRequest.toString(), Toast.LENGTH_LONG).show();
//
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<Notification>> userCall = apiInterface.getNotifications(messageRequest);
//            userCall.enqueue(new Callback<List<Notification>>() {
//                @Override
//                public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
//
//                    List<Notification> resultsList = response.body();
//
//                    List<Object> listOfItems = new ArrayList<>();
//
//                    //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() , Toast.LENGTH_LONG).show();
//
//
//                    if (response.code() == 200) {
//
//                        if (resultsList != null) {
//
//
//                           // if (resultsList.size() > 0) {
//
//                                listOfItems.add(Long.valueOf(response.code()));
//                                listOfItems.add(resultsList);
//                                //new GetNotificationFromMziziApi(context, student).execute(listOfItems);
//
//
//                         //   }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                        //new GetNotificationFromMziziApi(context, student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                        // GetNotificationFromMziziApi(context, student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                      //  Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<Notification>> call, Throwable t) {
//                  //  Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//
//    }
//
//
//
//}
