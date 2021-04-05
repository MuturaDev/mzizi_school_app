package ultratude.com.staff.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.UltraData;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.UltradataRequest;


/**
 * Created by James on 05/07/2018.
 */

public class DataTransferReceiverBroadcast extends BroadcastReceiver {




    public DataTransferReceiverBroadcast(){

    }




    @Override
    public void onReceive(final Context context, Intent intent) {

        // MessageRequest messageRequest =  new MessageRequest(intent.getExtras().getString("StudentID"),intent.getExtras().getString("NoteLastID"),intent.getExtras().getString("appcode"));


        Log.d(context.getPackageName().toUpperCase(), "Service started ");



        if (internetConnection(context)) {

              // Toast.makeText(context, "DataTransferReceiverBroadcast", Toast.LENGTH_LONG).show();

//                Intent intent2 = new Intent(context, DataTransferService.class);
//                context.startActivity(intent2);

                // Toast.makeText(context, "DataTransferBroadcast", Toast.LENGTH_LONG).show();




                //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.


//

//                                    //request to PortalStudentInfo
                List<Object> list = new UltraDataDao(context).getUltraDataList();
                if(list.size() > 0) {


                    List<UltraData> ultradataList = (List<UltraData>) list.get(0);
                    final int[] ultraIDs = (int[]) list.get(1);

                    Staff staff = new StaffDao(context).getUserThatSignedUp();
                    //Toast.makeText(context, ultradataList.get(0).getLatitude() + "   " + ultradataList.get(10).getLatitude(), Toast.LENGTH_LONG).show();

                    //Toast.makeText(context, staff.toString(), Toast.LENGTH_LONG).show();
                    //you have to check if the database has any data to send

                    if (ultradataList.size() > 0 && staff != null) {

                        //start

                        // public String StudentID_Barcode;
//                public String latitude ;
//                public String longitude ;
//                public String bus_Activity;
//                public String Appcode ;


                        List<UltradataRequest> requestdata = new ArrayList<>();


                        //  Toast.makeText(context, String.valueOf(ultradataList.toString()), Toast.LENGTH_LONG ).show();
                        for (UltraData ultradata : ultradataList) {
                            UltradataRequest datarequest = new UltradataRequest();
                            datarequest.setAppcode(staff.getAppcode());
                            datarequest.setBus_Activity(ultradata.getBus_Activity());
                            datarequest.setLatitude(String.valueOf(ultradata.getLatitude()));
                            datarequest.setLongitude(String.valueOf(ultradata.getLongitude()));
                            datarequest.setStudentID_Barcode(ultradata.getBarcodeValue());
                            datarequest.setStaff_ID(staff.getStaff_ID());
                            datarequest.setSession(ultradata.getBus_Session());

                            requestdata.add(datarequest);
                        }


                        Call<String> userCall = apiInterface.sendUltraData(requestdata);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 if (response.isSuccessful()) {

                                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                                     if (response.body() == "true") {


                                                         //now you can delete all data
                                                         new UltraDataDao(context).updateRecordToSent(ultraIDs);

                                                     } else if (response.body() == "false") {

                                                     } else {

                                                     }
                                                 } else {
                                                     //you dont have to delete all the data
                                                 }

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();

                                             }
                                         }


                        );


                        //end


                    } else {
                        //no need to send ata
                    }
                }


            }



    }



//    private void sendRequestForAllStudents(final Context mContext, APIInterface apiInterface){
//        Staff staff = new StaffDao(mContext).getUserThatSignedUp();
//
//
//        Call<List<Student>> userCall = apiInterface.getAllStudents(staff);
//        userCall.enqueue(new Callback<List<Student>>() {
//                             @Override
//                             public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//
//                                    long id =  new StudentDAO(mContext).saveStudent(response.body());
//
//                                     Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_SHORT).show();
//
//                                 }else{
//                                     //you dont have to delete all the data
//                                 }
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<List<Student>> call, Throwable t) {
//
//                                 Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//
//    }
//
//
//    private void sendRequestForAllVehicles(final Context mContext,APIInterface apiInterface){
//
//        Staff staff = new StaffDao(mContext).getUserThatSignedUp();
//
//
//        Call<List<Vehicle>> userCall = apiInterface.getAllVehicles(staff);
//        userCall.enqueue(new Callback<List<Vehicle>>() {
//                             @Override
//                             public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//
//                                     long id =  new VehicleDAO(mContext).saveVehicle(response.body());
//
//                                     Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_SHORT).show();
//
//                                 }else{
//                                     //you dont have to delete all the data
//                                 }
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<List<Vehicle>> call, Throwable t) {
//
//                                 Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//    }
//
//
//    private void sendRequestForALLSchools(final Context mContext,APIInterface apiInterface){
//
//        Staff staff = new StaffDao(mContext).getUserThatSignedUp();
//
//
//        Call<List<Schools>> userCall = apiInterface.getAllSchools(staff);
//        userCall.enqueue(new Callback<List<Schools>>() {
//                             @Override
//                             public void onResponse(Call<List<Schools>> call, Response<List<Schools>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//
//                                     long id =  new SchoolsDAO(mContext).saveSchools(response.body());
//
//                                     Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_SHORT).show();
//
//                                 }else{
//                                     //you dont have to delete all the data
//                                 }
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<List<Schools>> call, Throwable t) {
//
//                                 Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//
//    }
//
//    private void sendRequestForDutyRosterList(final Context mContext,APIInterface apiInterface){
//
//        Staff staff = new StaffDao(mContext).getUserThatSignedUp();
//
//
//        Call<List<DutyRoster>> userCall = apiInterface.getDutyRosterList(staff);
//        userCall.enqueue(new Callback<List<DutyRoster>>() {
//                             @Override
//                             public void onResponse(Call<List<DutyRoster>> call, Response<List<DutyRoster>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//
//                                     long id =  new DutyRosterDAO(mContext).saveDutyRoster(response.body());
//
//                                     Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_SHORT).show();
//
//                                 }else{
//                                     //you dont have to delete all the data
//                                 }
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<List<DutyRoster>> call, Throwable t) {
//
//                                 Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
  //  }



    public boolean internetConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }











}