//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
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
//import ultratude.com.mzizi.modelclasses.NewCarriculumExam;
//import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//
///**
// * Created by James on 18/05/2019.
// */
//
//public class GetPortalNewCarriculumExamFormatFromMziziApi extends AsyncTask<List<Object>, Void, String>{
//
//    private Student student;
//    private Context context;
//
//    private WeakReference<Object> weakReference;
//    public GetPortalNewCarriculumExamFormatFromMziziApi(Object activity, Student student){
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
//    }
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
//            // Toast.makeText((LoginActivity) weakReference.get(), "Contacts Called", Toast.LENGTH_LONG).show();
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
//        Map<String, String> pro = new HashMap<>();
//        pro.put("StudentID", student.getStudentID());
//        pro.put("appcode", student.getAppcode());
//
//
//        if(weakReference.get() instanceof LoginActivity)
//            new GetPortalStudentDetailedResultsFromMziziApi( (LoginActivity) weakReference.get(), pro).SendRequest(student);
//
//        if(weakReference.get() instanceof SyncMyAccount)
//            new GetPortalStudentDetailedResultsFromMziziApi((SyncMyAccount) weakReference.get(), pro).SendRequest(student);
//
//        super.onPostExecute(stringObjectMap);
//    }
//
//    @Override
//    protected String doInBackground(List<Object>... lists) {
//        long id = 0;
//        long statuscode = (long) lists[0].get(0);
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
//            List<NewCarriculumExam> newCarriculumExamList = (List<NewCarriculumExam>) lists[0].get(1);
//
//            Log.d("ULTRATUDE.COM.APP", "Before Saving: " + newCarriculumExamList.size());
//            new NewCarriculumExamDAO(context).insertNewCarriculumExamFormat(newCarriculumExamList);
//
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
//    public void SendRequest(final Student student){
//        if(weakReference.get() instanceof  LoginActivity){
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<NewCarriculumExam>> userCall = apiInterface.getPortalProgressReport(student);
//            userCall.enqueue(new Callback<List<NewCarriculumExam>>() {
//                @Override
//                public void onResponse(Call<List<NewCarriculumExam>> call, Response<List<NewCarriculumExam>> response) {
//
//                    List<NewCarriculumExam> resultsList = response.body();
//
//                   // Log.d(((LoginActivity) weakReference.get()).getPackageName().toUpperCase(), "Data RECEIVED FROM API: " + resultsList.toString() );
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
//                            // if (resultsList.size() > 0) {
//
//                            listOfItems.add(Long.valueOf(response.code()));
//                            listOfItems.add(resultsList);
//                           // new GetPortalNewCarriculumExamFormatFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//
//                            // }
//
//
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                      //  new GetPortalNewCarriculumExamFormatFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalNewCarriculumExamFormatFromMziziApi((LoginActivity) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                        // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<NewCarriculumExam>> call, Throwable t) {
//                    // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//        }
//
//        if(weakReference.get() instanceof SyncMyAccount){
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<NewCarriculumExam>> userCall = apiInterface.getPortalProgressReport(student);
//            userCall.enqueue(new Callback<List<NewCarriculumExam>>() {
//                @Override
//                public void onResponse(Call<List<NewCarriculumExam>> call, Response<List<NewCarriculumExam>> response) {
//
//                    List<NewCarriculumExam> resultsList = response.body();
//                    Log.d(((SyncMyAccount) weakReference.get()).getPackageName().toUpperCase(), "Data RECEIVED FROM API: " + resultsList.toString() );
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
//                            // if (resultsList.size() > 0) {
//
//                            listOfItems.add(Long.valueOf(response.code()));
//                            listOfItems.add(resultsList);
//                           // new GetPortalNewCarriculumExamFormatFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//                            // }
//                        }
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalNewCarriculumExamFormatFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalNewCarriculumExamFormatFromMziziApi((SyncMyAccount) weakReference.get(), student).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                        // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<List<NewCarriculumExam>> call, Throwable t) {
//                    // Toast.makeText((SyncMyAccount) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                    return;
//                }
//            });
//        }
//    }
//}