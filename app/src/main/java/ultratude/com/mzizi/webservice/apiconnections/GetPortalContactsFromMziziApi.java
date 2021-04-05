//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//import android.widget.Toast;
//
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//
///**
// * Created by James on 01/08/2018.
// */
//
//public class GetPortalContactsFromMziziApi extends AsyncTask<List<Object>, Void,String > {
//
//
//    private Student student;
//    //private ParentMziziDatabase db;
//    private Context context;
//
//    WeakReference<Object> weakReference;
//
//    public GetPortalContactsFromMziziApi(Object activity, Student student){
//
//        //this.context = context;
//        this.weakReference = new WeakReference<>(activity);
//        this.student = student;
//        //db = ParentMziziDatabase.getInstance();
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
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(String stringObjectMap) {
//
//
//        //  String backResults = (String) stringObjectMap.get("dataReturnStatus");
//
//
//        if( stringObjectMap.contains("SUCCESSS")){
//
//
//           // Toast.makeText((LoginActivity) weakReference.get(), "Contacts Called", Toast.LENGTH_LONG).show();
//
//        }else if(stringObjectMap.contains("NO_CONTENT_RETURNED")){
//
//
//
//        }else if(stringObjectMap.contains("FAILURE")){
//
//        }
//
//
//
//        if(weakReference.get() instanceof LoginActivity)
//           // new GetPortalNewCarriculumExamFormatFromMziziApi((LoginActivity) weakReference.get(), student).SendRequest(student);
//
//        if(weakReference.get() instanceof SyncMyAccount)
//          //  new GetPortalNewCarriculumExamFormatFromMziziApi((SyncMyAccount) weakReference.get(), student).SendRequest(student);
//        super.onPostExecute(stringObjectMap);
//    }
//
//    @Override
//    protected String doInBackground(List<Object>... lists) {
//
//        long id = 0;
//        long statuscode = (long) lists[0].get(0);
//
//
//        try{
//
//            if(String.valueOf(statuscode).equals("204")){
//                return String.valueOf( statuscode) + " NO_CONTENT_RETURNED";
//            }
//
//            if(String.valueOf(statuscode).equals("500")){
//                return String.valueOf( statuscode) + " FAILURE";
//            }
//
//
//
//            List<PortalContacts> portalContactsList = (List<PortalContacts>) lists[0].get(1);
//
//
//            for(int i = 0; i <= portalContactsList.size(); i++) {
//
//                ParentMziziDatabase db = null;
//                if(weakReference.get() instanceof LoginActivity)
//                     db= ParentMziziDatabase.getInstance((LoginActivity) weakReference.get());
//
//                if(weakReference.get() instanceof SyncMyAccount)
//                    db = ParentMziziDatabase.getInstance((SyncMyAccount) weakReference.get());
//
//
//                db.getPortalContactsDao().deletePortalContacts(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//
//                try{
//                    for(PortalContacts contacts : portalContactsList){
//                        contacts.setStudID(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//                    }
//                    db.getPortalContactsDao().insertPortalContacts(portalContactsList);
//
//                }catch(SQLiteException e){
//                    db.getPortalContactsDao().deletePortalContacts(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
//
//                }
//
//
//            }
//
//
//
//
//
//
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//
//
//        return String.valueOf(statuscode) + " SUCCESS";
//    }
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
//            Call<List<PortalContacts>> userCall = apiInterface.getPortalContacts(student);
//            userCall.enqueue(new Callback<List<PortalContacts>>() {
//                @Override
//                public void onResponse(Call<List<PortalContacts>> call, Response<List<PortalContacts>> response) {
//
//                    List<PortalContacts> resultsList = response.body();
//
//                    for(PortalContacts res : resultsList){
//                        res.setStudID(Integer.valueOf(student.getStudentID()));
//                    }
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
//                               // new GetPortalContactsFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//
//                           // }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalContactsFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                      //  new GetPortalContactsFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                        //Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<PortalContacts>> call, Throwable t) {
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
//            Call<List<PortalContacts>> userCall = apiInterface.getPortalContacts(student);
//            userCall.enqueue(new Callback<List<PortalContacts>>() {
//                @Override
//                public void onResponse(Call<List<PortalContacts>> call, Response<List<PortalContacts>> response) {
//
//                    List<PortalContacts> resultsList = response.body();
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
//                              //  new GetPortalContactsFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//                           // }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalContactsFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalContactsFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                        Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<PortalContacts>> call, Throwable t) {
//                    Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
//}
