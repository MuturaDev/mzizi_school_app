//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//import android.widget.Toast;
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
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//
///**
// * Created by James on 07/07/2018.
// */
//
//public class GetPortalDetailedTransactionFromMziziApi extends AsyncTask<List<Object>, Void, Map<String, Object>> {
//
//    private WeakReference<Object> weakReference;
//    private Student student;
//
//
//    public GetPortalDetailedTransactionFromMziziApi(Object activity, Student student){
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
//
//        //do something
//
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute( Map<String, Object> result) {
//
//
//       // ((LoginActivity) weakReference.get()).bar.cancel();
//        // super.onPostExecute(result);
//
//        String backResults = (String) result.get("dataReturnStatus");
//
//
//        //long backResults1 = (long) result.get("dataReturnStatus");
//
//      List<PortalDetailedTransaction> portalDetailedTransactions = (List<PortalDetailedTransaction>) result.get("TransactData");
//       // Toast.makeText((LoginActivity) weakReference.get(), "GetPortalDetailedTransaction called " + String.valueOf(portalDetailedTransactions.size() + "  " + backResults), Toast.LENGTH_SHORT).show();
//
//
//
//
//
////        if( backResults.equals("SUCCESS")){
////
////            List<PortalDetailedTransaction> portalDetailedTransactions = (List<PortalDetailedTransaction>) result.get("TransactData");
////
////
////        }else if(backResults.equals("NO_CONTENT")){
////
////        }else if(backResults.equals("ERROR_OCCURRED")){
////
////        }
//
//        if(weakReference.get() instanceof LoginActivity)
//            //new GetPortalSiblingsFromMziziApi( (LoginActivity) weakReference.get(), student).SendRequest(student);
//
//        if(weakReference.get() instanceof SyncMyAccount)
//           // new GetPortalSiblingsFromMziziApi( (SyncMyAccount) weakReference.get(), student).SendRequest(student);
//
//        super.onPostExecute(result);
//    }
//
//
//
//    @Override
//    protected Map<String, Object> doInBackground(List<Object>... lists) {
//
//
//
//
//
//        long statuscode = (long) lists[0].get(0);
//
//
//
//        Map<String, Object> result = new HashMap<>();
//
//        try{
//
//
//
//
//            if(String.valueOf(statuscode).equalsIgnoreCase("200")){
//
//                List<PortalDetailedTransaction> transactions  = (List<PortalDetailedTransaction>) lists[0].get(1);
//
//
//                    ParentMziziDatabase db = null;
//                    if(weakReference.get() instanceof LoginActivity )
//                        db = ParentMziziDatabase.getInstance( (LoginActivity) weakReference.get());
//
//                    if(weakReference.get() instanceof SyncMyAccount )
//                        db = ParentMziziDatabase.getInstance( (SyncMyAccount) weakReference.get());
//
//
//                    try{
//                         db.getPortalDetailedTransactionDao().insertPortalDetailedTransaction(transactions);
//                    }catch (SQLiteException e){
//                        db.getPortalDetailedTransactionDao().deletePortalDetailedTransaction(Integer.valueOf(student.getStudentID()));
//                    }
//
//
//
//
//
//
//                result.put("TransactData", transactions);
//                result.put("dataReturnStatus", "SUCCESS");
//                //return result;
////                result.put("statuscode", (statuscode + id));
//
//
//            }else if(String.valueOf(statuscode).equalsIgnoreCase("204")){
//                //no content
//
//                result.put("dataReturnStatus", "NO_CONTENT");
//            }else{
//
//                //error happens
//
//                result.put("dataReturnStatus", "ERROR_OCCURRED");
//            }
////
////
////
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
////        List<PortalDetailedTransaction> transactions  = (List<PortalDetailedTransaction>) lists[0].get(1);
////
////         result.put("TransactData", transactions);
////        result.put("dataReturnStatus", statuscode);
//
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
//            Call<List<PortalDetailedTransaction>> userCall = apiInterface.getPortalDetailedTransaction(student);
//            userCall.enqueue(new Callback<List<PortalDetailedTransaction>>() {
//                @Override
//                public void onResponse(Call<List<PortalDetailedTransaction>> call, Response<List<PortalDetailedTransaction>> response) {
//
//                    List<PortalDetailedTransaction> resultsList = response.body();
//
//                    for(PortalDetailedTransaction res : resultsList){
//                        res.setStudID(Integer.valueOf(student.getStudentID()));
//                    }
//
//                    List<Object> listOfItems = new ArrayList<>();
//
//                    if (response.code() == 200) {
//
//                        if (resultsList != null) {
//
//                                listOfItems.add(Long.valueOf(response.code()));//0
//                                listOfItems.add(resultsList);//1
//                               // new GetPortalDetailedTransactionFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalDetailedTransactionFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalDetailedTransactionFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                        Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<PortalDetailedTransaction>> call, Throwable t) {
//                    Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
//            Call<List<PortalDetailedTransaction>> userCall = apiInterface.getPortalDetailedTransaction(student);
//            userCall.enqueue(new Callback<List<PortalDetailedTransaction>>() {
//                @Override
//                public void onResponse(Call<List<PortalDetailedTransaction>> call, Response<List<PortalDetailedTransaction>> response) {
//
//                    List<PortalDetailedTransaction> resultsList = response.body();
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
//                                listOfItems.add(Long.valueOf(response.code()));
//                                listOfItems.add(resultsList);
//                               // new GetPortalDetailedTransactionFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//                          //  }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalDetailedTransactionFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalDetailedTransactionFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
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
//                public void onFailure(Call<List<PortalDetailedTransaction>> call, Throwable t) {
//                    Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//        }
//    }
//
//
//
//
//
//}
