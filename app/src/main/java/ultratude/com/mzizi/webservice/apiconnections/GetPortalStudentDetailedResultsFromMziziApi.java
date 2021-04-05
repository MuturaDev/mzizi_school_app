//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.content.Intent;
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//import android.util.Log;
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
//import ultratude.com.mzizi.modelclasses.OpenFragments;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.MainActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//
///**
// * Created by James on 07/07/2018.
// */
//
//public class GetPortalStudentDetailedResultsFromMziziApi extends AsyncTask<List<Object>, Void,  Map<String, Object>> {
//
//    WeakReference<Object> weakReference;
//
//    Map<String, String> ob;
//
//    public GetPortalStudentDetailedResultsFromMziziApi(Object activity, Map<String, String> ob){
//        this.weakReference = new WeakReference<>(activity);
//
//        this.ob  = ob;
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
//        //now fetching results
//
//
//
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(  Map<String, Object> result) {
//
//
//
//
//        String backResults = (String) result.get("dataReturnStatus");
//
//
//       // if( backResults.equals("SUCCESSS")){
//
//            List<PortalStudentDetailedResults> portalEventsList = (List<PortalStudentDetailedResults>) result.get("ResultsData");
//
//      //  Toast.makeText((LoginActivity) weakReference.get(), "Results Called", Toast.LENGTH_LONG).show();
//
//            if(weakReference.get() instanceof LoginActivity)
//                ((LoginActivity) weakReference.get()).bar.cancel();
//            if(weakReference.get() instanceof SyncMyAccount)
//                ((SyncMyAccount) weakReference.get()).bar.cancel();
//
//            //REMEMBER YOU SHOULD NOT DELETE AL THE DATA
//
//            //if all the statuses are right lets move to login
//        if(weakReference.get() instanceof SyncMyAccount){
//            Intent intent = new Intent((SyncMyAccount) weakReference.get(), MainActivity.class);
//            intent.putExtra("StudentID", ob.get("StudentID"));
//            intent.putExtra("appcode", ob.get("appcode"));
//
//
//
//            intent.putExtra("isLog_In",((SyncMyAccount) weakReference.get()).getIntent().getBooleanExtra("isLog_In", false));//true because you logged in
//            intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
//            ((SyncMyAccount) weakReference.get()).startActivity(intent);
//            ((SyncMyAccount) weakReference.get()).finish();
//        }
//
//        if(weakReference.get() instanceof LoginActivity){
//            Intent intent = new Intent((LoginActivity) weakReference.get(), MainActivity.class);
//            intent.putExtra("StudentID", ob.get("StudentID"));
//            intent.putExtra("appcode", ob.get("appcode"));
//
//            intent.putExtra("DisplayLayout",  ((LoginActivity) weakReference.get()).displayLayout);
//
//
//            intent.putExtra("isLog_In",((LoginActivity) weakReference.get()).getIntent().getBooleanExtra("isLog_In", false));//true because you logged in
//            intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
//            ((LoginActivity) weakReference.get()).startActivity(intent);
//            ((LoginActivity) weakReference.get()).finish();
//        }
//
//
//
////        }else if(backResults.equals("NO_CONTENT")){
////
////        }else if(backResults.equals("ERROR_OCCURRED")){
////
////        }
//
//
//
//
//
//
//
//        super.onPostExecute(result);
//
//
//    }
//
//    @Override
//    protected Map<String, Object> doInBackground(List<Object>... lists) {
//
//
//        long id = 0;
//        final long statuscode = (long) lists[0].get(0) ;
//
//        Map<String, Object> result = new HashMap<>();
//
//
//        try{
//
//
//            if(String.valueOf(statuscode).equals("200")){
//
//                List<PortalStudentDetailedResults> portalSiblingsList = (List<PortalStudentDetailedResults>) lists[0].get(1);
//
//
//                ParentMziziDatabase db = null;
//                if(weakReference.get() instanceof LoginActivity)
//                    db = ParentMziziDatabase.getInstance((LoginActivity)weakReference.get());
//
//                if(weakReference.get() instanceof SyncMyAccount)
//                    db = ParentMziziDatabase.getInstance((SyncMyAccount)weakReference.get());
//
//
//                db.getPortalStudentDetailedResultsDao().deletePortalStudentDetialedResults(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling()));
////                for(PortalStudentDetailedResults res : portalSiblingsList){
////                    res.setStudID(Integer.valueOf(Integer.valueOf(db.getPortalSiblingsDao().getMainStudentFromSibling())));
////                }
//                db.getPortalStudentDetailedResultsDao().insertPortalStudentDetialedResults(portalSiblingsList);
//
//                result.put("dataReturnStatus", "SUCCESS");
//                result.put("ResultsData", portalSiblingsList);
//
//            }else if(String.valueOf(statuscode).equals("204")){
//
//                result.put("dataReturnStatus", "NO_CONTENT");
//
//            }else{
//
//                result.put("dataReturnStatus", "ERROR_OCCURRED");
//
//            }
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
//
//        // return new Long(id);
//        return result;
//
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
//            Call<List<PortalStudentDetailedResults>> userCall = apiInterface.getPortalStudentDetailes(student);
//            userCall.enqueue(new Callback<List<PortalStudentDetailedResults>>() {
//                @Override
//                public void onResponse(Call<List<PortalStudentDetailedResults>> call, Response<List<PortalStudentDetailedResults>> response) {
//
//                    List<PortalStudentDetailedResults> resultsList = response.body();
//
//                    for(PortalStudentDetailedResults res : resultsList){
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
//                                new GetPortalStudentDetailedResultsFromMziziApi((LoginActivity) weakReference.get(), ob).execute(listOfItems);
//
//
//                          //  }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                        new GetPortalStudentDetailedResultsFromMziziApi((LoginActivity) weakReference.get(), ob).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                        new GetPortalStudentDetailedResultsFromMziziApi((LoginActivity) weakReference.get(), ob).execute(listOfItems);
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
//                public void onFailure(Call<List<PortalStudentDetailedResults>> call, Throwable t) {
//                   // Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
//        if(weakReference.get() instanceof SyncMyAccount) {
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<PortalStudentDetailedResults>> userCall = apiInterface.getPortalStudentDetailes(student);
//            userCall.enqueue(new Callback<List<PortalStudentDetailedResults>>() {
//                @Override
//                public void onResponse(Call<List<PortalStudentDetailedResults>> call, Response<List<PortalStudentDetailedResults>> response) {
//
//                    List<PortalStudentDetailedResults> resultsList = response.body();
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
//                          //  if (resultsList.size() > 0) {
//
//                                listOfItems.add(response.code());
//                                listOfItems.add(resultsList);
//                                new GetPortalStudentDetailedResultsFromMziziApi((SyncMyAccount) weakReference.get(), ob).execute(listOfItems);
//
//
//                          //  }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(response.code());
//                        new GetPortalStudentDetailedResultsFromMziziApi((SyncMyAccount) weakReference.get(), ob).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(response.code());
//                        new GetPortalStudentDetailedResultsFromMziziApi((SyncMyAccount) weakReference.get(), ob).execute(listOfItems);
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
//                public void onFailure(Call<List<PortalStudentDetailedResults>> call, Throwable t) {
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
//}
