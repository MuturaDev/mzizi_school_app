//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//
//
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
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;
//
///**
// * Created by James on 20/07/2018.
// */
//
//public class GetPortalEventsFromMziziApi extends AsyncTask<List<Object>, Void,String > {
//
//
//    private Student student;
//    private ParentMziziDatabase db;
//    Context context;
//
//    public GetPortalEventsFromMziziApi(Context context){
//
//        this.context = context;
//        db = ParentMziziDatabase.getInstance(context);
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
//      //  String backResults = (String) stringObjectMap.get("dataReturnStatus");
//     //   Toast.makeText(context, "Success events: " + stringObjectMap, Toast.LENGTH_LONG).show();
//
//
//        if( stringObjectMap.contains("SUCCESSS")){
//
//           // Toast.makeText(context, "Success events: " + stringObjectMap, Toast.LENGTH_LONG).show();
//
//        }else if(stringObjectMap.contains("NO_CONTENT_RETURNED")){
//
//          //  Toast.makeText(context, "0 school events", Toast.LENGTH_LONG).show();
//
//        }else if(stringObjectMap.contains("FAILURE")){
//
//        }
//
//
//
//        super.onPostExecute(stringObjectMap);
//    }
//
//    @Override
//    protected String doInBackground(List<Object>... lists) {
//
//        long statuscode = (long) lists[0].get(0);
//
//        try{
//
//            if(String.valueOf(statuscode).equals("204")){
//
//                return (statuscode) + " NO_CONTENT_RETURNED";
//            }
//
//            if(String.valueOf(statuscode).equals("500")){
//
//                return (statuscode) + " FAILURE";
//            }
//
//
//            List<PortalEvents> portalEventsList = (List<PortalEvents>) lists[0].get(1);
//
//
//            try{
//                db.getPortalEventsDao().deletePortalEvents(Integer.valueOf(student.getStudentID()));
//                db.getPortalEventsDao().insertPortalEventsList(portalEventsList);
//            }catch(SQLiteException e){
//                db.getPortalSiblingsDao().deleteSiblings();
//            }
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//        return statuscode + " SUCCESS";
//    }
//
//
//
//    public void SendRequest(final Student student) {
//
//
//            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//            Call<List<PortalEvents>> userCall = apiInterface.getPortalEvents(student);
//            userCall.enqueue(new Callback<List<PortalEvents>>() {
//                @Override
//                public void onResponse(Call<List<PortalEvents>> call, Response<List<PortalEvents>> response) {
//
//                    List<Object> listOfItems = new ArrayList<>();
//
//                    if (response.code() == 200) {
//
//                            for(PortalEvents res : response.body()){
//                                res.setStudID(Integer.valueOf(student.getStudentID()));
//                            }
//
//                            listOfItems.add(Long.valueOf(response.code()));
//                            listOfItems.add(response.body());
//                          //  new GetPortalEventsFromMziziApi(context).execute(listOfItems);
//
//
//                    } else if (response.code() == 204) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalEventsFromMziziApi(context).execute(listOfItems);
//
//
//
//                    } else if (response.code() == 500) {
//                        listOfItems.add(Long.valueOf(response.code()));
//                       // new GetPortalEventsFromMziziApi(context).execute(listOfItems);
//
//                    } else if (isCancelled()) {
//
//                       // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<List<PortalEvents>> call, Throwable t) {
//                  //  Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
//    }
//
//
//}
