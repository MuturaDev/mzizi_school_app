package ultratude.com.staff.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
import ultratude.com.staff.webservice.DataAccessObjects.AssetItemDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DisciplineCaseDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.MarkRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.SchoolsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TripLatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleFuelingDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleServicingDAO;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
import ultratude.com.staff.webservice.ResponseModels.AssetItem;
import ultratude.com.staff.webservice.ResponseModels.DisciplineCase;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;
import ultratude.com.staff.webservice.ResponseModels.TripLatLong;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControlRequest;
import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;
import ultratude.com.staff.webservice.ResponseModels.Schools;
import ultratude.com.staff.webservice.ResponseModels.Student;
import ultratude.com.staff.webservice.ResponseModels.VehicleFueling;
import ultratude.com.staff.webservice.ResponseModels.VehicleServicing;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.UltradataRequest;
import ultratude.com.staff.webservice.ResponseModels.UltraData;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;
import ultratude.com.staff.BuildConfig;

import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;

/**
 * Created by James on 19/10/2018.
 */

public class DataTransferBroadcast extends BroadcastReceiver {

    public static final String ACTION_ALARM="ultratude.com.staff.ACTION_ALARM";


    public DataTransferBroadcast(){

    }


    @Override
    public void onReceive(final Context context, Intent intent) {

        // MessageRequest messageRequest =  new MessageRequest(intent.getExtras().getString("StudentID"),intent.getExtras().getString("NoteLastID"),intent.getExtras().getString("appcode"));

        if(ACTION_ALARM != null)
        if(ACTION_ALARM.equals(intent.getAction())) {
            StaffRequest staffRequest = new StaffRequest(
                    intent.getExtras().getString("StaffID") ,
                    intent.getExtras().getString("SchoolID"),
                    intent.getExtras().getString("OrganizationID"),
                    intent.getExtras().getString("AppCode")

            );
            uploadLocalData(context, staffRequest);
        }

    }

    public void uploadLocalData(final Context context, final StaffRequest staffRequest){


            // Log.d(context.getPackageName().toUpperCase(), "Service is running");

            Paper.init(context);

            if (internetConnection(context)) {
                //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.




                //All this will be sent to another location, when this data will be fetched after a successfull login
//                sendRequestForAllStudents(context, apiInterface,staffRequest);
//                sendRequestForAllVehicles(context, apiInterface,staffRequest);
//                sendRequestForALLSchools(context, apiInterface,staffRequest);
//                sendRequestForDutyRosterList(context, apiInterface,staffRequest);
                //sendRequestForAllStaff(context, apiInterface, staffRequest);

                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                MziziAppVersionControlRequest request = new MziziAppVersionControlRequest(versionName, versionCode);
                sendRequestMziziAppVersionControl(context, apiInterface,request);


                //All this data will be sent to the api
                //first we check if there is a list of anything having any data
                //then send if there is one with data
                //we shall have several if statements not nested if statements

                // List<Object> tripLatLongObjectList = new TripLatLongDAO(context).getTripLatLong();

//                List<TripLatLong> tripLatLongList = (List<TripLatLong>) tripLatLongObjectList.get(0);
//                final int[] tripLatLongIds = (int[]) tripLatLongObjectList.get(1);

//                if(tripLatLongList.size() > 0){
//
//                   TripLatLong tripLatLong =  tripLatLongList.get(tripLatLongList.size() - 1);
//                    Calendar cal = Calendar.getInstance();
//                    SimpleDateFormat  sdf = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
//
//                int minuteDiffernce = getTimeDifference( tripLatLong.getDateRecorded(),sdf.format(cal.getTime()));

//                    Log.d(context.getPackageName().toUpperCase(), "Minute Difference: " + String.valueOf(minuteDiffernce));
//                    if(minuteDiffernce > 1){
//                        new SendNotifications(context).displayNotification("No Internet Connection", "Check your internet settings", "Please enable your mobile data connection or wifi to send data.");
//                    }
//                }


                List<Object> list = new UltraDataDao(context).getUltraDataList();
                if(list.size() > 0) {

                    List<UltraData> ultradataList = (List<UltraData>) list.get(0);
                    final int[] ultraIDs = (int[]) list.get(1);


                    //You have to check if the database has any data to send

                    //one
                    if (ultradataList.size() > 0 && staffRequest != null) {

                        //start
                        List<UltradataRequest> requestdata = new ArrayList<>();
                        //Need to include bus on that route
                        for (UltraData ultradata : ultradataList) {
                            UltradataRequest datarequest = new UltradataRequest();
                            datarequest.setAppcode(staffRequest.getAppCode());
                            datarequest.setBus_Activity(ultradata.getBus_Activity());
                            datarequest.setLatitude(String.valueOf(ultradata.getLatitude()));
                            datarequest.setLongitude(String.valueOf(ultradata.getLongitude()));
                            datarequest.setStudentID_Barcode(ultradata.getBarcodeValue());
                            datarequest.setStaff_ID(staffRequest.getStaffID());
                            datarequest.setSession(ultradata.getBus_Session());
                            datarequest.setBusTrip(ultradata.getBusTrips());
                            datarequest.setVehiclePlate(ultradata.getVehiclePlate());
                            datarequest.setDateScanned(ultradata.getDateScanned());
                            datarequest.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                            requestdata.add(datarequest);

                        }

                        Call<String> userCall = apiInterface.sendUltraData(requestdata);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 if (response.isSuccessful()) {
                                                     if (response.body() != null){
                                                         if (response.body().equals("true")) {
                                                             //now you can delete all data
                                                             AsyncTask asyncTask = new AsyncTask() {
                                                                 @Override
                                                                 protected Object doInBackground(Object[] params) {
                                                                     new UltraDataDao(context).updateRecordToSent(ultraIDs);
                                                                     return null;
                                                                 }
                                                             };
                                                             asyncTask.execute();


                                                         } else if (response.body() == "false") {

                                                             //what do we do now?
                                                         }
                                                 }else{

                                                     }
                                                 } else {
                                                     //you dont have to delete all the data
                                                 }

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }
                        );
                    }
                }

                //one//assets
                List<Object> assetItemsList = new AssetItemDAO(context).getAllAssetItems();
                if(assetItemsList.size() > 0) {
                    List<AssetItem> assetItems = (List<AssetItem>) assetItemsList.get(0);
                    if (assetItems.size() > 0) {
                        final int[] assetItemsIDs = (int[]) assetItemsList.get(1);

                        if (assetItems.size() > 0 && staffRequest != null) {
                            for (AssetItem item : assetItems) {
                                item.setAppCode(staffRequest.getAppCode());
                                item.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                            }
                        }

                        Call<String> assetItemsCall = apiInterface.sendAssetItemsData(assetItems);
                        assetItemsCall.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.isSuccessful()) {
                                    if(response.body() != null)
                                    if (response.body().equalsIgnoreCase("true")) {
                                        AsyncTask asyncTask = new AsyncTask() {
                                            @Override
                                            protected Object doInBackground(Object[] objects) {
                                                new AssetItemDAO(context).deleteOneAssetItem(assetItemsIDs);
                                                // List<AssetItem> list = (List<AssetItem>) new AssetItemDAO(context).getAllAssetItems().get(0);
                                                return null;
                                            }
                                        };
                                        asyncTask.execute();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });

                    }
                }



                //two
                List<Object> disciplineCaseObjectList =   new DisciplineCaseDAO(context).getDisciplinaryCasesList();
                if(disciplineCaseObjectList.size() > 0) {
                    List<DisciplineCase> disciplineCaseList = (List<DisciplineCase>) disciplineCaseObjectList.get(0);

                    //TODO: ADD APP VERSION
                    for(DisciplineCase disciplineCase : disciplineCaseList){
                        disciplineCase.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                    }

                    final int[] disciplinaryCaseIds = (int[]) disciplineCaseObjectList.get(1);
                    if (disciplineCaseList != null && disciplineCaseList.size() > 0) {
                        Call<String> userCall = apiInterface.saveDisciplineCase(disciplineCaseList);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 if (response.isSuccessful()) {
                                                     // Toast.makeText(context, "DisciplinaryCaseSaved: " + response.body(), Toast.LENGTH_SHORT).show();
                                                     if(response.body() != null)
                                                     if (response.body().equals("true")) {
                                                         AsyncTask asynctask = new AsyncTask() {
                                                             @Override
                                                             protected Object doInBackground(Object[] params) {
                                                                 new DisciplineCaseDAO(context).deleteOneDisciplineCase(disciplinaryCaseIds);
                                                                 return null;
                                                             }
                                                         };
                                                         asynctask.execute();
                                                     }

                                                 } else {
                                                     //you dont have to delete all the data
                                                 }

                                                 // Log.d(context.getPackageName().toUpperCase(), response.body());

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }
                        );
                    }
                }

                //three
                List<Object> vehiclerFuelingObjectList =  new VehicleFuelingDAO(context).getVehicleFuelingList();
               if(vehiclerFuelingObjectList.size() > 0) {
                    List<VehicleFueling> vehiclerFuelingList = (List<VehicleFueling>) vehiclerFuelingObjectList.get(0);

                    //TODO: ADD APP VERSION
                    for(VehicleFueling fueling : vehiclerFuelingList){
                        fueling.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                    }
                    final int[] vehicleFuelingIds = (int[]) vehiclerFuelingObjectList.get(1);
                    if (vehiclerFuelingList != null && vehiclerFuelingList.size() > 0) {
                        Call<String> userCall = apiInterface.saveVehicleFueling(vehiclerFuelingList);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 if (response.isSuccessful()) {
                                                     // Toast.makeText(context, "VehicleFuelingSaved: " + response.body(), Toast.LENGTH_SHORT).show();
                                                     if(response.code() == 200){
                                                         if(response.body() != null)
                                                        if (response.body().equals("true")) {
                                                            AsyncTask asyncTask = new AsyncTask() {
                                                                @Override
                                                                protected Object doInBackground(Object[] params) {
                                                                    new VehicleFuelingDAO(context).deleteOneVehicleFuelingCursor(vehicleFuelingIds);
                                                                    return null;
                                                                }
                                                            };
                                                            asyncTask.execute();
                                                        }
                                                    }

                                                 } else {
                                                     //you dont have to delete all the data
                                                 }

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }
                        );
                    }
                }

                //four
                List<Object> vehicleServicingObjectList =  new VehicleServicingDAO(context).getVehicleServicingList();
                if(vehicleServicingObjectList.size() > 0) {
                    List<VehicleServicing> vehicleServicinglist = (List<VehicleServicing>) vehicleServicingObjectList.get(0);
                    //TODO: ADD APP VERSION
                    for(VehicleServicing service : vehicleServicinglist){
                        service.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                    }

                    final int[] vehicleServicingIds = (int[]) vehicleServicingObjectList.get(1);

                    if (vehicleServicinglist != null && vehicleServicinglist.size() > 0) {
                        Call<String> userCall = apiInterface.saveVehicleServicing(vehicleServicinglist);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 if (response.isSuccessful()) {
                                                     //  Toast.makeText(context, "VehicleServicingSaved: " + response.body(), Toast.LENGTH_SHORT).show();
                                                     if(response.body() != null)
                                                     if (response.body().equals("true")) {
                                                         AsyncTask asyncTask = new AsyncTask() {
                                                             @Override
                                                             protected Object doInBackground(Object[] params) {
                                                                 new VehicleServicingDAO(context).deleteOneVehicleServicingCursor(vehicleServicingIds);
                                                                 return null;
                                                             }
                                                         };
                                                         asyncTask.execute();
                                                     }

                                                 } else {
                                                     //you dont have to delete all the data
                                                 }

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }
                        );
                    }
                }



                //DONT
                //five
                List<Object> markRegisterObjectList = new MarkRegisterDAO(context).getMarkRegisterList();
                if(markRegisterObjectList.size() > 0) {
                    List<PortalMarkRegisterRequest> portalMarkRegisterRequestList = (List<PortalMarkRegisterRequest>) markRegisterObjectList.get(0);
                    //TODO: ADD APP VERSION
                    for(PortalMarkRegisterRequest register : portalMarkRegisterRequestList){
                        register.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                    }
                    final int[] markRegisterIds = (int[]) markRegisterObjectList.get(1);
                    if (portalMarkRegisterRequestList != null && portalMarkRegisterRequestList.size() > 0) {
                        Call<String> userCall = apiInterface.saveMarkRegister(portalMarkRegisterRequestList);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 if (response.isSuccessful()) {
                                                     //   Toast.makeText(context, "MarkRegisterSaved: " + response.body(), Toast.LENGTH_SHORT).show();
                                                     if(response.body() != null)
                                                     if (response.body().equals("true")) {
                                                         AsyncTask asyncTask = new AsyncTask() {
                                                             @Override
                                                             protected Object doInBackground(Object[] params) {

                                                                 //Mark Register delete but only for records za kitambo
                                                                 // new MarkRegisterDAO(context).markAsSent(markRegisterIds);
                                                                 new MarkRegisterDAO(context).deleteOneMarkRegister(markRegisterIds);
                                                                 return null;
                                                             }
                                                         };
                                                         asyncTask.execute();
                                                     }

                                                 } else {
                                                     //you dont have to delete all the data
                                                 }

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }
                        );
                    }
                }


                List<Object> tripLatLongObjectList = new TripLatLongDAO(context).getTripLatLong();
                if(tripLatLongObjectList.size() > 0){
                    List<TripLatLong> tripLatLongList = (List<TripLatLong>) tripLatLongObjectList.get(0);
                    //TODO: ADD APP VERSION
                    for(TripLatLong latlong : tripLatLongList){
                        latlong.setAppVersion(String.valueOf(BuildConfig.VERSION_CODE));
                    }
                    final int[] tripLatLongIds = (int[]) tripLatLongObjectList.get(1);

                    if(tripLatLongList != null && tripLatLongList.size() > 0){

                        Call<String> userCall = apiInterface.saveTripLatLong(tripLatLongList);
                        userCall.enqueue(new Callback<String>() {
                                             @Override
                                             public void onResponse(Call<String> call, Response<String> response) {
                                                 //
                                                 if(response.isSuccessful()){
                                                     if(response.body() != null)
                                                     if(response.body().equals("true")){
                                                         AsyncTask asynctask = new AsyncTask() {
                                                             @Override
                                                             protected Object doInBackground(Object[] params) {
                                                                 new TripLatLongDAO(context).deleteOneTripLatLong(tripLatLongIds);
                                                                 return null;
                                                             }

                                                             @Override
                                                             protected void onPostExecute(Object o) {
                                                                 if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
                                                                     //                if(notificationManager != null)
                                                                     //                notificationManager.cancel(NOTIFICATION_ID);

                                                                     // NotificationTopDisplay.notificationManager.cancelAll();

                                                                     NotificationManager notify = (NotificationManager)  context.getSystemService(Context.NOTIFICATION_SERVICE);
                                                                     notify.cancelAll();

                                                                     //newNotificationList.clear();

                                                                 }else{

                                                                     //                for(Integer integer : notificationIdHolder){
                                                                     //                    if(notifyMgr != null)
                                                                     //                    notifyMgr.cancel(integer);
                                                                     //                }

                                                                     NotificationManager notify =  (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


                                                                     notify.cancelAll();


                                                                     // newNotificationList.clear();
                                                                 }

                                                                 super.onPostExecute(o);
                                                             }
                                                         };
                                                         asynctask.execute();
                                                     }

                                                 }else{
                                                     //you dont have to delete all the data
                                                 }

                                             }

                                             @Override
                                             public void onFailure(Call<String> call, Throwable t) {

                                                 // Log.d(context.getPackageName().toUpperCase(), "Error when sending trip data: " + t.getMessage());
                                                 // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }
                        );
                    }
                }


            }

    }

    public boolean internetConnection(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {


            return true;//connected

        }
        return false;
    }




    //All this will be taken to the login location
    private void sendRequestForAllStudents(final Context mContext, APIInterface apiInterface, StaffRequest staffRequest){


        Call<List<Student>> userCall = apiInterface.getAllStudents(staffRequest);
        userCall.enqueue(new Callback<List<Student>>() {
                             @Override
                             public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new StudentDAO(mContext).saveStudent(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<Student>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );

    }


    private void sendRequestMziziAppVersionControl(final Context mContext, APIInterface apiInterface, final MziziAppVersionControlRequest request){


        request.setAppModuleName("STAFF");
        Call<MziziAppVersionControl> userCall = apiInterface.forceMziziAppUpdate(request);
        userCall.enqueue(new Callback<MziziAppVersionControl>() {
                             @Override
                             public void onResponse(Call<MziziAppVersionControl> call, Response<MziziAppVersionControl> response) {
                                 if(response.isSuccessful()){

                                     if(response.code() == 200){
                                         MziziAppVersionControl responseMziziApp = response.body();

                                         Log.d(mContext.getPackageName().toUpperCase(), "200?: " + (response.body()).toString());


                                         if(Paper.book().contains(PAPER_MZIZIVERSIONCONTROL)){
                                             Paper.book().delete(PAPER_MZIZIVERSIONCONTROL);
                                             Paper.book().write(PAPER_MZIZIVERSIONCONTROL, responseMziziApp);
                                             Log.d(mContext.getPackageName().toUpperCase(), "UPDATE: " + Paper.book().read(PAPER_MZIZIVERSIONCONTROL, responseMziziApp));
                                         }else{

                                             Paper.book().write(PAPER_MZIZIVERSIONCONTROL, responseMziziApp);
                                             Log.d(mContext.getPackageName().toUpperCase(), "CREATE: " + Paper.book().read(PAPER_MZIZIVERSIONCONTROL, responseMziziApp));

                                         }
                                     }
                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                    // long id =  new VehicleDAO(mContext).saveVehicle(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<MziziAppVersionControl> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );
    }


    private void sendRequestForALLSchools(final Context mContext,APIInterface apiInterface, StaffRequest staffRequest){



        Call<List<Schools>> userCall = apiInterface.getAllSchools(staffRequest);
        userCall.enqueue(new Callback<List<Schools>>() {
                             @Override
                             public void onResponse(Call<List<Schools>> call, Response<List<Schools>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new SchoolsDAO(mContext).saveSchools(response.body());

                                     //  Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<Schools>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );

    }

    private void sendRequestForDutyRosterList(final Context mContext,APIInterface apiInterface, StaffRequest staffRequest){



        Call<List<DutyRoster>> userCall = apiInterface.getDutyRosterList(staffRequest);
        userCall.enqueue(new Callback<List<DutyRoster>>() {
                             @Override
                             public void onResponse(Call<List<DutyRoster>> call, Response<List<DutyRoster>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                     long id =  new DutyRosterDAO(mContext).saveDutyRoster(response.body());

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<DutyRoster>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );
    }



    private void sendRequestForAllStaff(final Context mContext, APIInterface apiInterface, StaffRequest staffRequest){


        Call<List<AllStaff>> userCall = apiInterface.getAllStaff(staffRequest);
        userCall.enqueue(new Callback<List<AllStaff>>() {
            @Override
            public void onResponse(Call<List<AllStaff>> call, Response<List<AllStaff>> response) {


                if(response.isSuccessful()){

                    // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                    long id =  new AllStaffDAO(mContext).saveAllStaffDAO(response.body());

                  //  Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                }else{
                    //you dont have to delete all the data
                }


            }

            @Override
            public void onFailure(Call<List<AllStaff>> call, Throwable t) {
                //  Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
            }
        });

    }











}

