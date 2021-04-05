//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
//import ultratude.com.mzizi.modelclasses.GlobalSettingsRequest;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.GlobalSettingsDAO;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
//import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//import ultratude.com.mzizi.webservice.APIRequest;
//
///**
// * Created by James on 20/07/2018.
// */
//
//public class GetPortalSiblingsFromMziziApi extends AsyncTask<List<Object>, Void, String > {
//
//   private WeakReference<Object> weakReference;
//
//    private Student student;
//
//
//    public GetPortalSiblingsFromMziziApi(Object activity, Student student){
//        this.weakReference = new WeakReference<>(activity);
//        this.student = student;
//
//        if(activity instanceof SyncMyAccount){
//            weakReference = new WeakReference<>(activity);
//        }else if(activity instanceof LoginActivity){
//            weakReference = new WeakReference<>(activity);
//
//        }
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(String stringObjectMap) {
//
//        if( stringObjectMap.contains("SUCCESSS")){
//
//          //  Toast.makeText((LoginActivity) weakReference.get(), "Siblings Called", Toast.LENGTH_LONG).show();
//
//        }else if(stringObjectMap.contains("NO_CONTENT_RETURNED")){
//
//           // Toast.makeText(loginActivityWeakReference.get(), "0 Siblings", Toast.LENGTH_LONG).show();
//
//        }else if(stringObjectMap.contains("FAILURE")){
//
//        }
//
//
//        if(weakReference.get() instanceof LoginActivity)
//       // new GetPortalContactsFromMziziApi((LoginActivity) weakReference.get(), student).SendRequest(student);
//
//        if(weakReference.get() instanceof SyncMyAccount)
//          //  new GetPortalContactsFromMziziApi((SyncMyAccount) weakReference.get(), student).SendRequest(student);
//
//
//        super.onPostExecute(stringObjectMap);
//    }
//
//    @Override
//    protected String doInBackground(List<Object>... lists) {
//
//        long id = 0;
//        long statuscode = (long) lists[0].get(0);
//
//        try{
//
//            if(String.valueOf(statuscode).equals("204")){
//
//                return String.valueOf( statuscode) + " NO_CONTENT_RETURNED";
//
//            }
//            if(String.valueOf(statuscode).equals("500")) {
//
//                return String.valueOf(statuscode) + " FAILURE";
//
//            }
//
//            List<PortalSiblings> portalSiblingsList = (List<PortalSiblings>) lists[0].get(1);
//
//
//            for(int i = 0; i <= portalSiblingsList.size(); i++) {
//
//                ParentMziziDatabase db = null;
//
//                if(weakReference.get() instanceof LoginActivity )
//                    db = ParentMziziDatabase.getInstance( (LoginActivity) weakReference.get());
//
//                if(weakReference.get() instanceof  SyncMyAccount)
//                    db = ParentMziziDatabase.getInstance( (SyncMyAccount) weakReference.get());
//
//                try{
//                    id = db.getPortalSiblingsDao().insertMoreSiblings(portalSiblingsList.get(i));
//                }catch(SQLiteException e){
//                    db.getPortalSiblingsDao().deleteSiblings();
//                }
//
//              //  portalSiblingsList.add(portalSiblingsList.get(i));
//
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//        return String.valueOf(statuscode) + " SUCCESS";
//    }
//
//
//
//
//    public void SendRequest(final Student student) {
//
//
//
//        if (weakReference.get() instanceof LoginActivity){
//
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<PortalSiblings>> userCall = apiInterface.getPortalSiblings(student);
//            userCall.enqueue(new Callback<List<PortalSiblings>>() {
//                @Override
//                public void onResponse(Call<List<PortalSiblings>> call, Response<List<PortalSiblings>> response) {
//
//                    List<PortalSiblings> resultsList = response.body();
//
//                    List<Object> listOfItems = new ArrayList<>();
//
//                    //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() , Toast.LENGTH_LONG).show();
//
//                    AsyncTask asyncTask = new AsyncTask() {
//                        @Override
//                        protected Object doInBackground(Object[] params) {
//                            return  ParentMziziDatabase.getInstance(((LoginActivity)weakReference.get())).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//                        }
//
//                        @Override
//                        protected void onPostExecute(Object o) {
//
//                            List<AuthenticateUserResponse> authenticateUserResponseList = (List<AuthenticateUserResponse>)o;
//
//                           // Log.d((LoginActivity) weakReference.get(), ": " + );
//
//                            if(authenticateUserResponseList.size() > 0){
//                                sendRequestForGlobalSettings(student, ((LoginActivity)weakReference.get()),authenticateUserResponseList.get(0).getOrganizationID(), Constants.CHAT_ENABLED);
//
////                                sendRequestForGlobalSettings(student, ((LoginActivity)weakReference.get()),authenticateUserResponseList.get(0).getOrganizationID(), Constants.OPTIONAL_FEE_ENABLED);
////                                sendRequestForGlobalSettings(student, ((LoginActivity)weakReference.get()),authenticateUserResponseList.get(0).getOrganizationID(), Constants.TRANSPORT_FEE_ENABLED);
////                                sendRequestForGlobalSettings(student, ((LoginActivity)weakReference.get()),authenticateUserResponseList.get(0).getOrganizationID(), Constants.BIO_ACCESS_ENABLED);
////                                sendRequestForGlobalSettings(student, ((LoginActivity)weakReference.get()),authenticateUserResponseList.get(0).getOrganizationID(), Constants.PORTAL_URL_ENABLED);
//
//                                AsyncTask asyncTask = new AsyncTask() {
//                                    @Override
//                                    protected void onPostExecute(Object o) {
//
//                                        List<String> list = (List<String>)o;
//                                        if(list.size() > 0){
//
//                                            Student student = new Student(list.get(0), list.get(1));
//                                            student.setOrganizationID(list.get(2));
//                                            Log.d(((LoginActivity)weakReference.get()).getPackageName().toUpperCase(), "Start Dispatch First Request: " + student.toString());
//                                            RequestFor.sendRequest(student, ((LoginActivity)weakReference.get()), "0", APIRequest.ALL);
//
//                                        }
//                                        super.onPostExecute(o);
//                                    }
//
//                                    @Override
//                                    protected Object doInBackground(Object[] objects) {
//                                        ParentMziziDatabase db = ParentMziziDatabase.getInstance(((LoginActivity)weakReference.get()));
//
//                                        List<AuthenticateUserResponse> authenticateUserResponse = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//                                        List<String> list = new ArrayList<>();
//                                        if (authenticateUserResponse.size() > 0) {
//                                            list.add(authenticateUserResponse.get(0).getUserID());//0 = studentid
//                                            list.add(authenticateUserResponse.get(0).getAppcode());//1 = appcode
//                                            list.add(authenticateUserResponse.get(0).getOrganizationID());//2=organizationID
//                                        }
//
//                                        return list;
//                                    }
//                                };
//                                asyncTask.execute();
//
//                               // new FirebaseJobDispatch((LoginActivity)weakReference.get()).startDispatch(student);
//
//                            }
//
//                            super.onPostExecute(o);
//                        }
//                    };
//                    asyncTask.execute();
//
//                    if (response.code() == 200) {
//
//                        if (resultsList != null) {
//
//
//                          //  if (resultsList.size() > 0) {
//
//                                listOfItems.add(Long.valueOf(response.code()));
//                                listOfItems.add(resultsList);
//                                //new GetPortalSiblingsFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//
//                          //  }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalSiblingsFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       /// new GetPortalSiblingsFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                      //  Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<PortalSiblings>> call, Throwable t) {
//                   // Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//
//
//        }
//
//
//
//        if(weakReference.get() instanceof SyncMyAccount) {
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<PortalSiblings>> userCall = apiInterface.getPortalSiblings(student);
//            userCall.enqueue(new Callback<List<PortalSiblings>>() {
//                @Override
//                public void onResponse(Call<List<PortalSiblings>> call, Response<List<PortalSiblings>> response) {
//
//                    List<PortalSiblings> resultsList = response.body();
//
//                    List<Object> listOfItems = new ArrayList<>();
//
//                    //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() , Toast.LENGTH_LONG).show();
//
//                    //SEND REQUEST FOR THE UI
//                    AsyncTask asyncTask = new AsyncTask() {
//                        @Override
//                        protected Object doInBackground(Object[] params) {
//                          return  ParentMziziDatabase.getInstance(((SyncMyAccount)weakReference.get())).getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//                        }
//
//                        @Override
//                        protected void onPostExecute(Object o) {
//
//                            List<AuthenticateUserResponse> authenticateUserResponseList = (List<AuthenticateUserResponse>)o;
//                            if(authenticateUserResponseList.size() < 0){
//                                sendRequestForGlobalSettings(student, ((SyncMyAccount)weakReference.get()),authenticateUserResponseList.get(0).getOrganizationID(),Constants.CHAT_ENABLED );
//
//                            }
//
//                            super.onPostExecute(o);
//                        }
//                    };
//                    asyncTask.execute();
//
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
//                               // new GetPortalSiblingsFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//                          //  }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalSiblingsFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalSiblingsFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                      //  Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<PortalSiblings>> call, Throwable t) {
//                   // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//
//
//
//        }
//
//
//
//    }
//
//
//
//    private void sendRequestForGlobalSettings(Student student, final Context context, String organizationID,final String settingName){
//        GlobalSettingsRequest globalSettingsRequest = new GlobalSettingsRequest(
//                settingName,
//                organizationID,
//                student.getAppcode()
//        );
//
//        //  Toast.makeText(context, "OrganizationID: " + organizationID , Toast.LENGTH_SHORT).show();
//
//
//        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//        Call<GlobalSettings> userCall = apiInterface.getGlobalSettings(globalSettingsRequest);
//        userCall.enqueue(new Callback<GlobalSettings>() {
//            @Override
//            public void onResponse(Call<GlobalSettings> call, final Response<GlobalSettings> response) {
//                if(response.code() == 200){
//                    AsyncTask asyncTask = new AsyncTask() {
//                        @Override
//                        protected Object doInBackground(Object[] params) {
//                            Log.d(context.getPackageName().toUpperCase(), "Request Global SettingsFragment GetPortalSiblingsFromMziziApi: " + response.body().toString());
//                            ParentMziziDatabase db =  ParentMziziDatabase.getInstance(context.getApplicationContext());
//                            GlobalSettingsDAO parentChatDAO = db.getGlobalSettingsDAO();
//                            parentChatDAO.deleteGlobalSetting(settingName, Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//                            GlobalSettings settings = response.body();
//
//                            settings.setGlobalSettingName(settingName);
//                            response.body().setStudID(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//                            parentChatDAO.insertGlobalSettings(response.body());
//
//                            return null;
//                        }
//                    };
//                    asyncTask.execute();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GlobalSettings> call, Throwable t) {
//
//            }
//        });
//
//    }
//
//
//
//
//
//}
