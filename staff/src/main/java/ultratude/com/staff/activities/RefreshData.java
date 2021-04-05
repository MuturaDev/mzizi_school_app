package ultratude.com.staff.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.BuildConfig;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
import ultratude.com.staff.webservice.DataAccessObjects.AssetRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.ClassStreamDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.HelpDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LeaveTypeDAO;
import ultratude.com.staff.webservice.DataAccessObjects.RollCallSessonsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.SchoolsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportBusTripsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportSessionsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
import ultratude.com.staff.webservice.ResponseModels.AssetRegisterResponse;
import ultratude.com.staff.webservice.ResponseModels.ClassStream;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;
import ultratude.com.staff.webservice.ResponseModels.Help;
import ultratude.com.staff.webservice.ResponseModels.LeaveType;
import ultratude.com.staff.webservice.ResponseModels.RollCallSession;
import ultratude.com.staff.webservice.ResponseModels.Schools;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.StaffOperation;
import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
import ultratude.com.staff.webservice.ResponseModels.Vehicle;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControlRequest;
import ultratude.com.staff.webservice.RequestModels.PortalTransportSessionBusTripsResponse;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;

import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;


/**
 * Created by James on 04/05/2019.
 */

public class RefreshData {



    public RefreshData(Context mContext, APIInterface apiInterface, StaffRequest staffRequest, ProgressDialog alertd){
        sendRequestForAllTransportStudents(mContext, apiInterface, staffRequest);
        sendRequestForAllStudents(mContext, apiInterface,staffRequest);
        sendRequestForAllVehicles(mContext, apiInterface,staffRequest);
        sendRequestForRollCallSessions(mContext, apiInterface, staffRequest);
        sendRequestForLeaveTypes(mContext, apiInterface, staffRequest);
        sendRequestForStaffOperations(mContext, apiInterface, staffRequest);
        sendRequestForSupportHelp(mContext, apiInterface, staffRequest);
        sendRequestForClassStream(mContext, apiInterface, staffRequest);
        sendRequestForALLSchools(mContext, apiInterface,staffRequest);
        sendRequestForDutyRosterList(mContext, apiInterface,staffRequest);
        sendRequestForAllStaff(mContext, apiInterface, staffRequest, alertd);
        sendRequestForTransportSessionsAndBusTrips(mContext,apiInterface,staffRequest);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        MziziAppVersionControlRequest request = new MziziAppVersionControlRequest(versionName, versionCode);
        request.setAppModuleName("STAFF");
        sendRequestMziziAppVersionControl(mContext, apiInterface,request);

        sendRequestForAssetRegister(mContext, apiInterface,staffRequest);
    }

    private void sendRequestForAssetRegister(final Context mContext, APIInterface apiInterface, final StaffRequest request){
        Call<List<AssetRegisterResponse>> userCall = apiInterface.getAssetRegisterResponse(request);
        userCall.enqueue(new Callback<List<AssetRegisterResponse>>() {
            @Override
            public void onResponse(Call<List<AssetRegisterResponse>> call, Response<List<AssetRegisterResponse>> response) {
                if(response.isSuccessful()){
                    if(response.code() == 200){
                        List<AssetRegisterResponse> responseRegister = response.body();
                        new AssetRegisterDAO(mContext).saveAssetRegister(responseRegister);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AssetRegisterResponse>> call, Throwable t) {

            }
        });
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

                                     MziziAppVersionControl mziziAppVersionControl =  Paper.book().read(PAPER_MZIZIVERSIONCONTROL, new MziziAppVersionControl());
                                     if(mziziAppVersionControl != null){
                                         if(mziziAppVersionControl.isForceAppUpdateStatus()){
                                             Intent intent = new Intent(mContext, MziziVersionControlActivity.class);
                                             mContext.startActivity(intent);
                                             ((HomeScreen)mContext).finish();
                                         }
                                     }

                                 }

                             }

                             @Override
                             public void onFailure(Call<MziziAppVersionControl> call, Throwable t) {
                             }
                         }


        );
    }


    public static void sendRequestForTransportSessionsAndBusTrips(final Context mContext,ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest ){


        Call<PortalTransportSessionBusTripsResponse> userCall = apiInterface.getTransportSessionAndBusTrips(staffRequest);
        userCall.enqueue(new Callback<PortalTransportSessionBusTripsResponse>() {
            @Override
            public void onResponse(Call<PortalTransportSessionBusTripsResponse> call, Response<PortalTransportSessionBusTripsResponse> response) {
                if(response.isSuccessful()){
                    if(response.code() == 200){
                        new TransportBusTripsDAO(mContext).saveTransportBusTrips(response.body().getBusTripsList());
                        new TransportSessionsDAO(mContext).saveTransportSessions(response.body().getSessionsList());
                    }
                }
            }

            @Override
            public void onFailure(Call<PortalTransportSessionBusTripsResponse> call, Throwable t) {

            }
        });
    }


    public void sendRequestForSupportHelp(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
        Call<List<Help>> userCall = apiInterface.getSupportHelp(staffRequest);
        userCall.enqueue(new Callback<List<Help>>() {
            @Override
            public void onResponse(Call<List<Help>> call, Response<List<Help>> response) {
                if(response.isSuccessful()){
                    if(response.code() == 200){
                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {
                                long id = new HelpDAO(mContext).saveHelp(((List<Help>) params[0]));
                                Log.d(mContext.getPackageName().toUpperCase(), String.valueOf(id));

                                int count =     new HelpDAO(mContext).getAllSupportHelpCursor().getCount();

                                return null;
                            }
                        };
                        asyncTask.execute(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Help>> call, Throwable t) {

            }
        });
    }




    private void sendRequestForAllTransportStudents(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
        //for testing only
        //You can also add schoolID and organizationID
        Call<List<TransportStudents>> userCall = apiInterface.getPortalAllTransportStudents(staffRequest);
        userCall.enqueue(new Callback<List<TransportStudents>>() {
            @Override
            public void onResponse(Call<List<TransportStudents>> call, Response<List<TransportStudents>> response) {

                List<TransportStudents> list = response.body();

                //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() + " " + response.body().get(0).toString() , Toast.LENGTH_LONG).show();


                List<TransportStudents> result = new ArrayList<TransportStudents>();

                if(response.code() == 200){



                    result = response.body();

                    //Toast.makeText(loginActivityWeakReference.get(), String.valueOf(result.get(0).toString()), Toast.LENGTH_LONG).show();




                    if(result != null){

                        if(result.size()> 0){


                            // if(result.get(0).getStudentFullName() != ""){

                            // Toast.makeText(loginActivityWeakReference.get(), String.valueOf(result.get(0).toString()), Toast.LENGTH_LONG).show();
//
                            //Call PortalAllTransportStudentsListing

                            new SaveAllTransportStudentsAsyncTask(mContext).execute(result);

                        }

                    }
                }



            }

            @Override
            public void onFailure(Call<List<TransportStudents>> call, Throwable t) {
                // Toast.makeText(loginActivityWeakReference.get(),"Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }

    private void sendRequestForAllStaff(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest, final ProgressDialog alertd){


        Call<List<AllStaff>> userCall = apiInterface.getAllStaff(staffRequest);
        userCall.enqueue(new Callback<List<AllStaff>>() {
            @Override
            public void onResponse(Call<List<AllStaff>> call, Response<List<AllStaff>> response) {


                if(response.isSuccessful()){

                    // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                    if(response.code() == 200) {

                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {
                                long id = new AllStaffDAO(mContext).saveAllStaffDAO(((List<AllStaff>) params[0]));
                                return null;
                            }
                        };
                        asyncTask.execute(response.body());
                    }

                    // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                }

                if(alertd != null)
                    alertd.cancel();


            }

            @Override
            public void onFailure(Call<List<AllStaff>> call, Throwable t) {
                // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                call.cancel();
                if(alertd != null)
                    alertd.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
            }
        });

    }

    //All this will be taken to the login location
    private void sendRequestForAllStudents(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){


        Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> userCall = apiInterface.getAllStudents(staffRequest);
        userCall.enqueue(new Callback<List<ultratude.com.staff.webservice.ResponseModels.Student>>() {
                             @Override
                             public void onResponse(Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> call, Response<List<ultratude.com.staff.webservice.ResponseModels.Student>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                                     if(response.code() == 200) {
                                         AsyncTask asyncTask = new AsyncTask() {
                                             @Override
                                             protected Object doInBackground(Object[] params) {
                                                 long id = new StudentDAO(mContext).saveStudent((((List<ultratude.com.staff.webservice.ResponseModels.Student>) params[0])));
                                                 return null;
                                             }
                                         };
                                         asyncTask.execute(response.body());
                                     }

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );

    }

    private void sendRequestForAllVehicles(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){



        Call<List<Vehicle>> userCall = apiInterface.getAllVehicles(staffRequest);
        userCall.enqueue(new Callback<List<Vehicle>>() {
                             @Override
                             public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                                     if(response.code() == 200) {

                                         AsyncTask asyncTask = new AsyncTask() {
                                             @Override
                                             protected Object doInBackground(Object[] params) {
                                                 long id = new VehicleDAO(mContext).saveVehicle(((List<Vehicle>) params[0]));

                                                 return null;
                                             }
                                         };
                                         asyncTask.execute(response.body());
                                     }

                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                                 }else{
                                     //you dont have to delete all the data
                                 }

                             }

                             @Override
                             public void onFailure(Call<List<Vehicle>> call, Throwable t) {

                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();

                             }
                         }


        );
    }

    private void sendRequestForALLSchools(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){



        Call<List<Schools>> userCall = apiInterface.getAllSchools(staffRequest);
        userCall.enqueue(new Callback<List<Schools>>() {
                             @Override
                             public void onResponse(Call<List<Schools>> call, Response<List<Schools>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                                     if(response.code() == 200) {

                                         AsyncTask asyncTask = new AsyncTask() {
                                             @Override
                                             protected Object doInBackground(Object[] params) {
                                                 long id = new SchoolsDAO(mContext).saveSchools(((List<Schools>) params[0]));

                                                 return null;
                                             }
                                         };
                                         asyncTask.execute(response.body());
                                     }

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

    private void sendRequestForDutyRosterList(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){

        Call<List<DutyRoster>> userCall = apiInterface.getDutyRosterList(staffRequest);
        userCall.enqueue(new Callback<List<DutyRoster>>() {
                             @Override
                             public void onResponse(Call<List<DutyRoster>> call, Response<List<DutyRoster>> response) {
                                 if(response.isSuccessful()){

                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                                     if(response.code() == 200) {
                                         AsyncTask asyncTask = new AsyncTask() {
                                             @Override
                                             protected Object doInBackground(Object[] params) {
                                                 long id = new DutyRosterDAO(mContext).saveDutyRoster(((List<DutyRoster>) params[0]));

                                                 return null;
                                             }
                                         };
                                         asyncTask.execute(response.body());
                                     }

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

    private void sendRequestForRollCallSessions(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
        Call<List<RollCallSession>> userCall = apiInterface.getRollCallSessions(staffRequest);
        userCall.enqueue(new Callback<List<RollCallSession>>() {
            @Override
            public void onResponse(Call<List<RollCallSession>> call, Response<List<RollCallSession>> response) {

                if(response.isSuccessful()){
                    if(response.code() == 200) {
                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {

                                long id = new RollCallSessonsDAO(mContext).saveRollCallSessons(((List<RollCallSession>) params[0]));


                                return null;
                            }
                        };
                        asyncTask.execute(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RollCallSession>> call, Throwable t) {

            }
        });
    }

    private void sendRequestForLeaveTypes(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
        Call<List<LeaveType>> userCall = apiInterface.getLeaveTypes(staffRequest);
        userCall.enqueue(new Callback<List<LeaveType>>() {
            @Override
            public void onResponse(Call<List<LeaveType>> call, Response<List<LeaveType>> response) {

                if(response.isSuccessful()){
                    if(response.code() == 200) {

                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {
                                long id = new LeaveTypeDAO(mContext).saveLeaveTypes(((List<LeaveType>) params[0]));
                                return null;
                            }
                        };
                        asyncTask.execute(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LeaveType>> call, Throwable t) {

            }
        });
    }

    private void sendRequestForClassStream(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
        Call<List<ClassStream>> userCall = apiInterface.getClassStreamTeacherStaffAllocation(staffRequest);
        userCall.enqueue(new Callback<List<ClassStream>>() {
            @Override
            public void onResponse(Call<List<ClassStream>> call, Response<List<ClassStream>> response) {

                if(response.isSuccessful()){

                    if(response.code() == 200) {
                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {
                                long id = new ClassStreamDAO(mContext).saveClassStreamDAO(((List<ClassStream>) params[0]));
                               // Log.d(mContext.getPackageName().toUpperCase(), String.valueOf(id));
                                return null;
                            }
                        };

                        asyncTask.execute(response.body());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ClassStream>> call, Throwable t) {

            }
        });
    }

    private void sendRequestForStaffOperations(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
        Call<List<StaffOperation>> userCall = apiInterface.getStaffOperations(staffRequest);
        userCall.enqueue(new Callback<List<StaffOperation>>() {
            @Override
            public void onResponse(Call<List<StaffOperation>> call, Response<List<StaffOperation>> response) {

                if(response.isSuccessful()){
                    if(response.code() == 200) {
                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] params) {
                                long id = new StaffOperationsDAO(mContext).saveStaffOperation(((List<StaffOperation>) params[0]));
                                return null;
                            }
                        };
                        asyncTask.execute(response.body());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<StaffOperation>> call, Throwable t) {

            }
        });
    }


    public class SaveAllTransportStudentsAsyncTask extends AsyncTask<List<TransportStudents>, Void, String> {

        private Context context;
        private Staff innerstaff;
        private ProgressDialog pd;


        //    new SaveAllTransportStudentsAsyncTask(loginActivityWeakReference.get(), innerstaff, pd).execute(result);

        public SaveAllTransportStudentsAsyncTask(Context context){
            this.context = context;

        }



        @Override
        protected void onPostExecute(String results) {

            //  Toast.makeText(context, "StudentList: " + results , Toast.LENGTH_LONG).show();


            //new StaffDao(loginActivityWeakReference.get()).saveUserToDB(innerstaff);




            super.onPostExecute(results);
        }

        @Override
        protected String doInBackground(List<TransportStudents>... params) {

            final List<TransportStudents> studentList = params[0];

            if(studentList.size() > 0){
                return String.valueOf( new TransportStudentsDao(context).saveAllStudents(null,studentList));

            }

            return String.valueOf(studentList.size());


            // return String.valueOf(studentList.size());

        }
    }



}
