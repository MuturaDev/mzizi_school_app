//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import ultratude.com.mzizi.firebasejobdispatch.FirebaseJobDispatch;
//import ultratude.com.mzizi.modelclasses.MessageRequest;
//import ultratude.com.mzizi.modelclasses.Student;
////import ultratude.com.mzizi.notificationpg.NotificationBroadcast.NotificationService;
//import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
//import ultratude.com.mzizi.notificationpg.NotificationModel.NotificationDAO;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//import ultratude.com.staff.BuildConfig;
//import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControlRequest;
//
///**
// * Created by James on 26/06/2018.
// */
//
////will retrieve data and will also delete data
//public class GetStudentInfoFromMziziApi extends AsyncTask<List<Object>, Void, Map<String, Object>> {
//
//    private WeakReference<Object> weakReference;
//    Student student;
//
//    public GetStudentInfoFromMziziApi(){
//
//    }
//
//    public GetStudentInfoFromMziziApi( Object activity, Student student){
//        this.weakReference = new WeakReference<>(activity);
//        this.student = student;
//
//        if(activity instanceof SyncMyAccount){
//            weakReference = new WeakReference<>(activity);
//        }else if(activity instanceof LoginActivity){
//            weakReference = new WeakReference<>(activity);
//
//        }
//
//    }
//
//
//
//
//    @Override
//    protected void onPreExecute() {
//
//
//
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute( Map<String, Object> result) {
//
//      //  Toast.makeText((LoginActivity) weakReference.get(), String.valueOf(result.get("dataReturnStatus")), Toast.LENGTH_LONG).show();
//
//            //THIS IS JUST FOR TESTING PURPOSES ONLY
////        if(weakReference.get() instanceof LoginActivity)
////            ((LoginActivity) weakReference.get()).bar.cancel();
////
////        if(weakReference.get() instanceof SyncMyAccount)
////            ((SyncMyAccount) weakReference.get()).bar.cancel();
//
//
//       if(!result.equals(null)){
//           String backResults = (String) result.get("dataReturnStatus");
//
//           //if(weakReference.get() instanceof LoginActivity)
//                //Toast.makeText( (LoginActivity) weakReference.get(), backResults, Toast.LENGTH_LONG).show();
//
//
//
//           if( backResults.equals("SUCCESS")){
//
//               PortalStudentInfo portalStudentInfos = (PortalStudentInfo) result.get("StudentInfo");
//
//
//
////               //JUST FOR TESTING
////               if(weakReference.get() instanceof LoginActivity)
////                   Toast.makeText( (LoginActivity) weakReference.get(),"GetStudentInfo: "+ portalStudentInfos.toString(), Toast.LENGTH_LONG).show();
////
////               if(weakReference.get() instanceof SyncMyAccount)
////                   Toast.makeText( (SyncMyAccount) weakReference.get(),"GetStudentInfo: "+  portalStudentInfos.toString(), Toast.LENGTH_LONG).show();
//
//
//
//           }else if(backResults.equals("NO_CONTENT")){
//
//
//           }else if(backResults.equals("ERROR_OCCURRED")){
//
//           }
//       }
////
////
////
////
//        //fetch this data next, LET IT BE THE LAST THING YOU DO
//        if(weakReference.get() instanceof  LoginActivity) {
//
//            new LastIDNotification((LoginActivity) weakReference.get(), student.getStudentID(), student.getAppcode()).execute();
//           // new GetPortalDetailedTransactionFromMziziApi(weakReference.get(), student).SendRequest(student);//NOTE
//        }
//
//        if(weakReference.get() instanceof SyncMyAccount) {
//            new LastIDNotification((SyncMyAccount) weakReference.get(), student.getStudentID(), student.getAppcode()).execute();
//          //  new GetPortalDetailedTransactionFromMziziApi(weakReference.get(), student).SendRequest(student);
//        }
//        super.onPostExecute(result);
//    }
//
//
//
//
//    @Override
//    protected Map<String, Object>  doInBackground(List<Object>... lists) {
//
//
//
//
//        Map<String, Object> result = new HashMap<>();
//
//        long status = (long) lists[0].get(0);
//        try {
//
//
//
//           if(String.valueOf(status).equals("200")){
//
//
//
//               PortalStudentInfo portalStudentInfo = (PortalStudentInfo) lists[0].get(1);
//
//
//
//
//               ParentMziziDatabase db = null;
//               if(weakReference.get() instanceof LoginActivity) {
//                   portalStudentInfo.setStudID(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//                   db = ParentMziziDatabase.getInstance((LoginActivity) weakReference.get());
//               }
//
//               if(weakReference.get() instanceof SyncMyAccount) {
//                   portalStudentInfo.setStudID(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//                   db = ParentMziziDatabase.getInstance((SyncMyAccount) weakReference.get());
//               }
//
//
//               try{
//                   db.getPortalStudentInfoDao().insertStudent(portalStudentInfo);
//               }catch (SQLiteException e){
//                   db.getPortalStudentInfoDao().deleteStudent(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//               }
//
//               result.put("StudentInfo", portalStudentInfo );
//               result.put("dataReturnStatus", "SUCCESS");
//
//           }else if(String.valueOf(status).equals("204")){
//               result.put("dataReturnStatus", "NO_CONTENT");
//
//           }else{
//               result.put("dataReturnStatus", "ERROR_OCCURRED");
//
//           }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//        return result;
//    }
//
//
//
//
//
//    public void SendRequest(final Student student) {
//
//        if(weakReference.get() instanceof LoginActivity)
//            ((LoginActivity) weakReference.get()).bar.show();
//
//        if(weakReference.get() instanceof SyncMyAccount)
//            ((SyncMyAccount) weakReference.get()).bar.show();
//
//
//
//        if (weakReference.get() instanceof LoginActivity){
//
//
//
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//                Call<PortalStudentInfo> userCall = apiInterface.getStudentInfo(student);
//                userCall.enqueue(new Callback<PortalStudentInfo>() {
//                    @Override
//                    public void onResponse(Call<PortalStudentInfo> call, Response<PortalStudentInfo> response) {
//
//                        PortalStudentInfo portalStudentInfo = response.body();
//
//                       // Log.d(((LoginActivity) weakReference.get()).getPackageName().toUpperCase(), "Student Info: " +  portalStudentInfo.toString());
//
//                        List<Object> listOfItems = new ArrayList<>();
//
//                        //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() , Toast.LENGTH_LONG).show();
//
//
//                        if (response.code() == 200) {
//
//                            if (portalStudentInfo != null) {
//
//
//                              //  if (portalStudentInfo.getStudentFullName() != "") {
//
//                                   // Toast.makeText((LoginActivity) weakReference.get(), student.toString(), Toast.LENGTH_LONG).show();
//
//
//                                    listOfItems.add(Long.valueOf(response.code()));
//                                    listOfItems.add(portalStudentInfo);
//
//
//                                if(weakReference.get() instanceof LoginActivity) {
//                                    //FIXME: This is old code, was replaced with Firebase Job Dispatcher
////                                    Intent d = new Intent((LoginActivity) weakReference.get(), NotificationService.class);
////                                    d.putExtra("StudentID",student.getStudentID());
////                                    d.putExtra("appcode",student.getAppcode());
////                                    ((LoginActivity) weakReference.get()).startService(d);
//                                    new FirebaseJobDispatch((LoginActivity)weakReference.get()).startDispatch(student);
//
//                                }
//
//                                if(weakReference.get() instanceof SyncMyAccount) {
////                                    Intent d = new Intent((SyncMyAccount) weakReference.get(), NotificationService.class);
////                                    d.putExtra("StudentID",student.getStudentID());
////                                    d.putExtra("appcode",student.getAppcode());
////                                    ((SyncMyAccount) weakReference.get()).startService(d);
//
//                                    new FirebaseJobDispatch((SyncMyAccount)weakReference.get()).startDispatch(student);
//
//                                }
//
//
//
//                                  //  new GetStudentInfoFromMziziApi( weakReference.get(), student).execute(listOfItems);
//
//
//                              //  }
//
//
//                            }
//                        } else if (response.code() == 204) {
//                            listOfItems.add(Long.valueOf(response.code()));
//                           // new GetStudentInfoFromMziziApi( weakReference.get(), student).execute(listOfItems);
//
//
//
//                        } else if (response.code() == 500) {
//                            listOfItems.add(Long.valueOf(response.code()));
//                           // new GetStudentInfoFromMziziApi(weakReference.get(), student).execute(listOfItems);
//
//                        } else if (isCancelled()) {
//
//                           // Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<PortalStudentInfo> call, Throwable t) {
//                       // Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                        return;
//                    }
//                });
//
//        }
//
//
//
//        if(weakReference.get() instanceof SyncMyAccount) {
//
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<PortalStudentInfo> userCall = apiInterface.getStudentInfo(student);
//            userCall.enqueue(new Callback<PortalStudentInfo>() {
//                @Override
//                public void onResponse(Call<PortalStudentInfo> call, Response<PortalStudentInfo> response) {
//
//                    PortalStudentInfo portalStudentInfo = response.body();
//
//                    List<Object> listOfItems = new ArrayList<>();
//
//                    //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() , Toast.LENGTH_LONG).show();
//
//
//                    if (response.code() == 200) {
//
//                        if (portalStudentInfo != null) {
//
//
//                           // if (portalStudentInfo.getStudentFullName() != "") {
//
//                                listOfItems.add(Long.valueOf(response.code()));
//                                listOfItems.add(portalStudentInfo);
//                               // new GetStudentInfoFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//                          //  }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetStudentInfoFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetStudentInfoFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                       // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<PortalStudentInfo> call, Throwable t) {
//                   // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//
//        }
//
//
//
//
//    }
//
//
//    private class LastIDNotification extends AsyncTask<Void, Void, List<String>> {
//
//        String studentID, appcode;
//        Context context;
//
//        public LastIDNotification(Context context, String studentID, String appcode){
//            this.studentID = studentID;
//            this.appcode = appcode;
//            this.context = context;
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//
//
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(List<String> list) {
////
//            if(list.size()==4){
//                String studentID = list.get(0);
//                String appcode = list.get(1);
//                String organization = list.get(2);
//                String notificationLastID = list.get(3);
//
//
//                //  Toast.makeText(context, "NotificationAlarmBroadcast: " +notificationLastID  , Toast.LENGTH_LONG).show();
//                //Toast.makeText(context, "AlarmBroadcast: " +String.valueOf(list.size())  , Toast.LENGTH_LONG).show();
////System.exit(1);
//
//                //
//                //  Toast.makeText(context, notificationLastID, Toast.LENGTH_LONG).show();
//
//
//                int versionCode = BuildConfig.VERSION_CODE;
//                String versionName = BuildConfig.VERSION_NAME;
//                MziziAppVersionControlRequest request = new MziziAppVersionControlRequest(versionName, versionCode);
//                // sendRequestMziziAppVersionControl(context,request);
//
//                MessageRequest messageRequest = new MessageRequest(studentID, notificationLastID, appcode);
//                Student student = new Student(studentID, appcode);
//              //  new GetNotificationFromMziziApi(context, student).SendRequest(messageRequest);
//                //new GetPortalEventsFromMziziApi(context).SendRequest(student);
//
//            }
//
//            //just for testing
//            // Toast.makeText(context, mainActivityWeakReference.get().studentID, Toast.LENGTH_LONG).show();
//
//            super.onPostExecute(list);
//        }
//
//        @Override
//        protected List<String> doInBackground(Void... voids) {
//
//
//
//
//
//            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
//
//
//
//
//
//            List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//
//            List<ultratude.com.mzizi.notificationpg.NotificationModel.Notification> notificationList =  new NotificationDAO(context).getNotificationsList();
//
//            //  List<AuthenticateUserResponse> userResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//            List<String> list = new ArrayList<>();
//            if(authenticateUserResponse.size() > 0){
//                list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
//                list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
//                list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
//            }
//
//
//
//
//
//            //check this array out of bounce
//            if(notificationList.isEmpty()){
//
//                String.valueOf(new Notification().getMsgID());
//                list.add(String.valueOf(new Notification().getMsgID()));//3notificationid
//                return list;
//            }else{
//
//                list.add(String.valueOf(notificationList.get(notificationList.size() - 1).getMsgID()));
//                return  list;
//            }
//        }
//    }
//
//}
