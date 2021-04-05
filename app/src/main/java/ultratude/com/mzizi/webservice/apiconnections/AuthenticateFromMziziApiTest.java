//package ultratude.com.mzizi.webservice.apiconnections;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.sqlite.SQLiteException;
//import android.os.AsyncTask;
//
//import android.util.Log;
//
//
//import androidx.appcompat.widget.AppCompatEditText;
//
//import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import ultratude.com.mzizi.modelclasses.Student;
//import ultratude.com.mzizi.modelclasses.UserCredentials;
////import ultratude.com.mzizi.notificationpg.NotificationBroadcast.NotificationService;
//import ultratude.com.mzizi.retrofitokhttp.APIInterface;
//import ultratude.com.mzizi.retrofitokhttp.APIClient;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.FilteredStudentInfoResults;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
//import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateResponse;
//import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
//import ultratude.com.mzizi.uiactivities.LoginActivity;
//import ultratude.com.mzizi.uiactivities.SyncMyAccount;
//import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
//import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.ClassStreamDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.HelpDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.LeaveTypeDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.RollCallSessonsDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.SchoolsDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
//import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
//import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
//import ultratude.com.staff.webservice.ResponseModels.AllStaff;
//import ultratude.com.staff.webservice.ResponseModels.ClassStream;
//import ultratude.com.staff.webservice.ResponseModels.DutyRoster;
//import ultratude.com.staff.webservice.ResponseModels.Help;
//import ultratude.com.staff.webservice.ResponseModels.LeaveType;
//import ultratude.com.staff.webservice.ResponseModels.RollCallSession;
//import ultratude.com.staff.webservice.ResponseModels.Schools;
//import ultratude.com.staff.webservice.ResponseModels.Staff;
//import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
//import ultratude.com.staff.webservice.ResponseModels.StaffOperation;
//import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
//import ultratude.com.staff.webservice.ResponseModels.Vehicle;
//import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;
//
///**
// * Created by James on 25/07/2018.
// */
//
//public class AuthenticateFromMziziApiTest   extends AsyncTask<List<Object>, Void,  List<Object>> {
//
//
//    WeakReference<LoginActivity> loginActivityWeakReference;
//
//    private AppCompatEditText passwordtext, usernametext, appcodetext;
//
//
//
//    public AuthenticateFromMziziApiTest(
//            LoginActivity loginActivity, AppCompatEditText  passwordtext,AppCompatEditText usernametext, AppCompatEditText appcodetext){
//
//        loginActivityWeakReference = new WeakReference<>(loginActivity);
//
//        this.passwordtext = passwordtext;
//        this.usernametext = usernametext;
//        this.appcodetext  = appcodetext;
//    }
//
//
//   static ProgressDialog pd;
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
//    protected void onPostExecute(/*Map<String, String>*/ List<Object> response) {
//        //should cancel the progressdialog, but i will use it to display if the other information has been finished
//
//        //start the service and remember to send data
//
//        String dataFromEndpoint = "";
//
//        if(response.size()>0){
//
//
//           //Toast.makeText(loginActivityWeakReference.get(), String.valueOf((String) response.get(0)), Toast.LENGTH_LONG).show();
//
//
//
//            dataFromEndpoint = (String) response.get(0);
//
//          // Toast.makeText(loginActivityWeakReference.get(), String.valueOf(dataFromEndpoint), Toast.LENGTH_LONG).show();
//
//
//            if(dataFromEndpoint.contains("NO_CONTENT_RETURNED")){
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
//                alert.setCancelable(false);
//                alert.setTitle("Confirmation");
//                alert.setMessage("Please ensure your username, password and school code are correct");
//                alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // passwordtext.setText(dataFromEndpoint.get("StudentID") +" " + dataFromEndpoint.get("username"));
////                    passwordtext.setText("");
////                    usernametext.setText("");
////
//                        dialogInterface.cancel();
//
//                    }
//                });
//                alert.show();
//
//                pd.cancel();
//
//            }else if(dataFromEndpoint.contains("FAILURE")){
//
//                pd.cancel();
//                // PreferenceStorage.storeThis("password", " ");//will be used in the preference
//
//
//                AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
//                alert.setCancelable(false);
//                alert.setTitle("Confirmation");
//                alert.setMessage("Please ensure your username, password and school code are correct");
//                alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // passwordtext.setText(dataFromEndpoint.get("StudentID") +" " + dataFromEndpoint.get("username"));
////                    passwordtext.setText("");
////                    usernametext.setText("");
////
//                        dialogInterface.cancel();
//
//                    }
//                });
//                alert.show();
//
//            }else if(dataFromEndpoint.contains("EMPTY_CREDENTIALS")){
//
//                pd.cancel();
//                AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
//                alert.setCancelable(false);
//                alert.setTitle("Confirmation");
//                alert.setMessage("Please ensure your username, password and school code are correct.");
//                alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // passwordtext.setText(dataFromEndpoint.get("StudentID") +" " + dataFromEndpoint.get("username"));
//
//                        dialogInterface.cancel();
//                        //as discussed let there passwords remain
////                    passwordtext.setText("");
////                    usernametext.setText("");
////                    appcodetext.setText("");
//                    }
//                });
//                alert.show();
//            }
//
//
//            AuthenticateUserResponse user = null;
//            Staff staff  = null;
//
//
//            if(response.size() == 2) {
//
////
//                //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf( response.get(1) instanceof AuthenticateUserResponse) + "  " + String.valueOf(response.get(1) instanceof Staff), Toast.LENGTH_LONG).show();
//
//                if (response.get(1) instanceof AuthenticateUserResponse) {
//
//                    user = (AuthenticateUserResponse) response.get(1);
//                    // Toast.makeText(loginActivityWeakReference.get(), user.toString(), Toast.LENGTH_LONG).show();
//
//                } else if (response.get(1) instanceof Staff) {
//                    staff = (Staff) response.get(1);
//                    // Toast.makeText(loginActivityWeakReference.get(), staff.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//
//
//             // Toast.makeText(loginActivityWeakReference.get(), String.valueOf(dataFromEndpoint), Toast.LENGTH_LONG).show();
//
//
//             if(response.size() == 2){
//
//                    if(dataFromEndpoint.contains("SUCCESS")){
//
//                        if(response.get(1) instanceof AuthenticateUserResponse){
//                            //Toast.makeText(loginActivityWeakReference.get(), "Statuscode: " +  dataFromEndpoint.get("statuscode" + " \nLoginStatus: " + dataFromEndpoint.get("loginStatus")), Toast.LENGTH_LONG).show();
//
//                            if(user != null){
//
//                                if(user.getUserType().equalsIgnoreCase("STUDENT")){
//
//                                    //remember when sending a request you can now include in schoolID and OrganizationID
//                                    final Student student = new Student(user.getUserID(), user.getAppcode());
//
//
//
//                                    // new GetStudentInfoFromMziziApi(loginActivityWeakReference.get(), student).execute(student);
//
//                                    //END OF TESTING
//
//                                    //the use is already authenticated, you can make a call to the PortalStudentInfo
//                                    //start
//                                    //CREATE NEW USER --working
//
//                                    //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
//                                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
////
////                                    //request to PortalStudentInfo
//
//
//
//                                    //for testing only
//                                    Student studenttest = new Student(student.getStudentID(), student.getAppcode());
//                                    Call<FilteredStudentInfoResults> userCall = apiInterface.getFilteredStudentInfo(studenttest);
//                                    userCall.enqueue(new Callback<FilteredStudentInfoResults>() {
//                                        @Override
//                                        public void onResponse(Call<FilteredStudentInfoResults> call, Response<FilteredStudentInfoResults> response) {
//
//                                            FilteredStudentInfoResults result = response.body();
//                                            pd.cancel();
//
//                                            if(result != null){
//
//
//                                                //FOR TESTING
////                                    AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
////                                    alert.setMessage(result.toString());
////                                    alert.show();
//
//
//
//
//                                                //first delete the previous
//                                                //new SyncMyAccountDAO(loginActivityWeakReference.get()).deleteAllSyncMyAccountResult();
//                                                //new SyncMyAccountDAO(loginActivityWeakReference.get()).insertSyncMyAccountResult(result);
//
//                                                if(result.getPortalAccount() == "" || result.getPortalPaybill() == null){
//
//                                                }else{
//
//                                                    //save this information into the database
//                                                    SyncMyAccountResult account = new SyncMyAccountResult();
//                                                    //Balance, BillingBalance, PortalAccount, PaybillNo
//                                                    account.setBalance(result.getBalance());
//                                                    account.setBillingBalance(result.getBillingBalance());
//                                                    account.setPortalPaybill(result.getPortalPaybill());
//                                                    account.setPortalAccount(result.getPortalAccount());
//
//
//                                                    new SyncMyAccountDAO(loginActivityWeakReference.get()).deleteForSyncMyAccountResult(student.getStudentID());
//                                                    account.setStudID(Integer.valueOf(student.getStudentID()));
//                                                    new SyncMyAccountDAO(loginActivityWeakReference.get()).insertSyncMyAccountResult(account);
//
//                                                }
//
//
//
//
//                                                if(Float.valueOf(result.getBillingBalance())>0f){//should redirect
//                                                    // if(500>0){//should redirect
//
//                                                    Intent intent = new Intent(loginActivityWeakReference.get(), SyncMyAccount.class);
//                                                    intent.putExtra("StudentID", student.getStudentID());
//                                                    intent.putExtra("appcode", student.getAppcode());
//                                                    intent.putExtra("isLog_In", ((LoginActivity) loginActivityWeakReference.get()).getIntent().getBooleanExtra("isLog_In", false));
//
//
//
//
//                                                    //no need of doing this, just add the data to the database
//                                                    // intent.putExtra("Account", account);
//
//                                                    intent.putExtra("CALL_FROM", "AUTHENTICATE_USER");
//
//                                                    loginActivityWeakReference.get().startActivity(intent);
//                                                    loginActivityWeakReference.get().finish();
//
//
//
//
//                                                }else{
//
//
//                                                  //  Toast.makeText(loginActivityWeakReference.get(), student.toString(), Toast.LENGTH_LONG).show();
//
//                                                    //start
//
//                                                    //end
//                                                    //if statement
//
//                                                   // new GetStudentInfoFromMziziApi(loginActivityWeakReference.get(), student).SendRequest(student);
//
//
//
//                                                    //new GetPortalDetailedTransactionFromMziziApi(loginActivityWeakReference.get()).execute(student);
//
//                                                    //   new GetPortalEventsFromMziziApi(loginActivityWeakReference.get()).execute(student);
//
//
//                                                    //                new GetPortalSiblingsFromMziziApi(loginActivityWeakReference.get()).execute(student);
//                                                    //
//                                                    //                Map<String, String> pro = new HashMap<>();
//                                                    //                pro.put("StudentID", dataFromEndpoint.get("StudentID"));
//                                                    //                pro.put("appcode", dataFromEndpoint.get("appcode"));
//                                                    //                new GetPortalStudentDetailedResultsFromMziziApi(loginActivityWeakReference.get(), pro).execute(student);
//
//                                                    //end
//
//                                                }
//
//
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<FilteredStudentInfoResults> call, Throwable t) {
//                                           // Toast.makeText(loginActivityWeakReference.get(),"Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                                           // call.cancel();
//
//                                            /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                             Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                                            pd.cancel();
//                                            return;
//                                        }
//                                    });
//
//
//
//
//
//
//
//
//
//
//
////            }else{
////                Intent intent = new Intent(loginActivityWeakReference.get(), StaffMainActivity.class);
////                loginActivityWeakReference.get().startActivity(intent);
//
//                                    //this user will ony work if allowd
//                                }else{
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
//                                    alert.setCancelable(false);
//                                    alert.setTitle("Confirmation");
//                                    alert.setMessage("Check your internet connection!");
//                                    alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            // passwordtext.setText(dataFromEndpoint.get("StudentID") +" " + dataFromEndpoint.get("username"));
////                    passwordtext.setText("");
////                    usernametext.setText("");
////
//                                            dialogInterface.cancel();
//                                            pd.cancel();
//
//                                        }
//                                    });
//                                    alert.show();
//                                }
//
//
//                            }
//                        }
//
//
//                    }
//
//
//
//
//                    //remove
//
//
//                    if(dataFromEndpoint.contains("SUCCESS")){
//
//                        if(response.get(1) instanceof Staff){
//                           // Toast.makeText(loginActivityWeakReference.get(), staff.toString(), Toast.LENGTH_LONG).show();
//
//                            if(staff != null){
//
//                                if(staff.getUserType().equalsIgnoreCase("STAFF")){
//
//
//
//                                    //send all request here
//
//
//
//
//                                   final Staff innerstaff = staff;
//
//                                    //start
//                                    ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface = ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient.getClient().create(ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface.class);//used to instantiate the APIClient.
//                                    //APIInterface apiInterface1 = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
////
//
//                                    StaffRequest staffRequest = new StaffRequest(
//                                            staff.getStaff_ID(),
//                                            staff.getSchoolID(),
//                                            staff.getOrganizationID(),
//                                            staff.getAppcode()
//                                    );
//
//                                    //all get request
//                                    sendRequestForAllTransportStudents(loginActivityWeakReference.get(), apiInterface, staffRequest, staff);
//                                    sendRequestForAllStudents(loginActivityWeakReference.get(), apiInterface,staffRequest);
//                                    sendRequestForAllVehicles(loginActivityWeakReference.get(), apiInterface,staffRequest);
//                                    sendRequestForRollCallSessions(loginActivityWeakReference.get(), apiInterface, staffRequest);
//                                    sendRequestForLeaveTypes(loginActivityWeakReference.get(), apiInterface, staffRequest);
//                                    sendRequestForStaffOperations(loginActivityWeakReference.get(), apiInterface, staffRequest);
//                                    sendRequestForSupportHelp(loginActivityWeakReference.get(), apiInterface, staffRequest);
//                                    sendRequestForClassStream(loginActivityWeakReference.get(), apiInterface, staffRequest);
//                                    sendRequestForALLSchools(loginActivityWeakReference.get(), apiInterface,staffRequest);
//                                    sendRequestForDutyRosterList(loginActivityWeakReference.get(), apiInterface,staffRequest);
//                                    sendRequestForAllStaff(loginActivityWeakReference.get(), apiInterface, staffRequest, staff);
//
//
//
//                                    //end
//
//
//                                }else{
//                                    AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
//                                    alert.setCancelable(false);
//                                    alert.setTitle("Confirmation");
//                                    alert.setMessage("Check your internet connection!");
//                                    alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            // passwordtext.setText(dataFromEndpoint.get("StudentID") +" " + dataFromEndpoint.get("username"));
////                    passwordtext.setText("");
////                    usernametext.setText("");
////
//                                            dialogInterface.cancel();
//                                            pd.cancel();
//
//                                        }
//                                    });
//                                    alert.show();
//                                }
//
//
//                            }
//                        }
//
//
//                    }
//
//
//
//
//            }
//
//
//            //
//             // Toast.makeText( loginActivityWeakReference.get(), "StatusCode: ("  +dataFromEndpoint + ")"  , Toast.LENGTH_LONG).show();
//
//
//        }
//
//        super.onPostExecute(response);
//    }
//
//
//
//
//    @Override
//    protected  List<Object>  /*String*/ doInBackground(List<Object>... lists) {
//
//
//
//
//
//        //values to assign results
//        String userID = "";
//        String userType = "";
//        String appcode = "";
//        String schoolID = "";
//        String organizationID = "";
//
//       final long statuscode = (long) lists[0].get(0);
//        List<Object> responseResultMap  = new ArrayList<>();
//
//
//        try{
//
//
//
//
//            if(String.valueOf(statuscode).trim().equalsIgnoreCase("204")){
//                //dont save any data in the database
//
//                //responseResultMap.put("loginStatus", "NO_CONTENT_RETURNED");
//                //responseResultMap.put("statuscode", String.valueOf(statuscode));
//
//                 //return responseResultMap;
//                // PreferenceStorage.storeThis("loginStatus", "FAILURE" );//will be used in the preference
//                String dataFromEndpoint =  String.valueOf( statuscode) + " NO_CONTENT_RETURNED";
//                 responseResultMap.add( dataFromEndpoint);//0
//                 return responseResultMap;//0
//
//            }
//
//            if(String.valueOf(statuscode).trim().equalsIgnoreCase("500") || String.valueOf(statuscode).trim().equalsIgnoreCase("204") ){
//
//               // responseResultMap.put("loginStatus", "FAILURE");
//               // responseResultMap.put("statuscode", String.valueOf(statuscode));
//
//                 //return responseResultMap;
//                //PreferenceStorage.storeThis("loginStatus", "FAILURE" );//will be used in the preference
//
//
//                String dataFromEndpoint = String.valueOf( statuscode) + " FAILURE";
//                responseResultMap.add(dataFromEndpoint);
//
//                return responseResultMap;//0
//            }
//
//            //start
//
//
//            //outside
//            AuthenticateResponse authenticateResponse = (AuthenticateResponse) lists[0].get(1);///from retrofit
//
//
//            if(authenticateResponse.getUserID().trim().equalsIgnoreCase("0") && authenticateResponse.getUserType().trim().equalsIgnoreCase("") && authenticateResponse.getSchoolID().trim().equalsIgnoreCase("") && authenticateResponse.getOrganizationID().trim().equalsIgnoreCase("")){
//               // responseResultMap.put("loginStatus", "EMPTY_CREDENTIALS");
//
////                return responseResultMap;
//                //PreferenceStorage.storeThis("loginStatus", "FAILURE" );//will be used in the preference
//                String dataFromEndPoint = String.valueOf( statuscode) + " EMPTY_CREDENTIALS " +authenticateResponse.getUserID() + " " + authenticateResponse.getUserType() ;
//
//                responseResultMap.add(dataFromEndPoint);
//
//                return responseResultMap;//0
//            }
//
//
//            UserCredentials user = (UserCredentials) lists[0].get(2);//credential that the user logged in with
//
//                //credentials that user logged in with
//            String username = user.getUserName();
//            String password = user.getPassword();
//            appcode = user.getAppcode();
//
//
//                //from retrofit
//            userID = authenticateResponse.getUserID();
//            userType = authenticateResponse.getUserType();
//            schoolID  = authenticateResponse.getSchoolID();
//             organizationID = authenticateResponse.getOrganizationID();
//
//
//            AuthenticateUserResponse userResponse = new AuthenticateUserResponse();
//                userResponse.setUserID(authenticateResponse.getUserID());
//                userResponse.setUserType(authenticateResponse.getUserType());
//                userResponse.setAppcode(appcodetext.getText().toString());
//                userResponse.setUsername(usernametext.getText().toString());
//                userResponse.setPassword(passwordtext.getText().toString());
//                userResponse.setSchoolID(schoolID);
//                userResponse.setOrganizationID(organizationID);
//                userResponse.setLogin_status("SUCCESS");
//
//
//            if(authenticateResponse.getUserType().equalsIgnoreCase("STUDENT")){
//                try{
//
//                    //SHOULD BE UNCOMMENTED
//                    loginActivityWeakReference.get().db.getAuthenticateUserResponseDao().insertAuthenticateUserResponse(userResponse);
//
//                }catch(SQLiteException e){
//
//                    loginActivityWeakReference.get().db.getAuthenticateUserResponseDao().deleteAuthenticateUserResponse();
//                    String endFromEndpoint =  String.valueOf(( "500" + " FAILURE"));//this will cause the user to be prompt to enter their credentials again
//                    responseResultMap.add(endFromEndpoint);
//
//                    return responseResultMap;//0
//
//                }
//
//
//            }else if(authenticateResponse.getUserType().equalsIgnoreCase("STAFF")){
//
//                try{
//
//                    Staff staff = new Staff();
//                    staff.setAppcode(appcode);
//                    staff.setStaff_ID(authenticateResponse.getUserID());
//                    staff.setUserType(authenticateResponse.getUserType());
//                    staff.setSchoolID(schoolID);
//                    staff.setOrganizationID(organizationID);
//
//                    new StaffDao(loginActivityWeakReference.get()).saveUserToDB(staff);
//
//                }catch(SQLiteException e){
//
//                    new StaffDao(loginActivityWeakReference.get()).deleteStaff();
//                    String endFromEndpoint =  String.valueOf(( "500" + " FAILURE"));//this will cause the user to be prompt to enter their credentials again
//                    responseResultMap.add(endFromEndpoint);
//
//                    return responseResultMap;//0
//
//                }
//            }
//
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//
//        if(userType.equalsIgnoreCase("STUDENT")){
//
//            //  return responseResultMap;
//            String dataFromEndpoint = String.valueOf(statuscode) + " SUCCESS";
//            responseResultMap.add(dataFromEndpoint);//0
//            List<AuthenticateUserResponse> userLogin = loginActivityWeakReference.get().db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//            if(userLogin.size()>0){
//
//                responseResultMap.add(userLogin.get(0));//1
//            }
//
//        }else if(userType.equalsIgnoreCase("STAFF")){
//
//            //  return responseResultMap;
//            String dataFromEndpoint = String.valueOf(statuscode) + " SUCCESS";
//            //String dataFromEndpoint = String.valueOf(userType) + " SUCCESS";
//           // Staff staff =  new StaffDao(loginActivityWeakReference.get()).getUserThatSignedUp();
//            Staff staff = new Staff();
//            staff.setAppcode(appcode);
//            staff.setUserType(userType);
//            staff.setStaff_ID(userID);
//            staff.setSchoolID(schoolID);
//            staff.setOrganizationID(organizationID);
//
//           // String dataFromEndpoint = String.valueOf(staff == null) + " SUCCESS";
//
//            responseResultMap.add(dataFromEndpoint);//0
//
//
//                responseResultMap.add(staff);//1
//
//
//        }
//
//
//
//
//
//
//        return responseResultMap;
//
//
//////        //test
////        List<Object> list3 = new ArrayList<>();
////        list3.add(statuscode);
////
////        return list3;
//
//    }
//
//
//    public class SaveAllTransportStudentsAsyncTask extends AsyncTask<List<TransportStudents>, Void, String>{
//
//        private Context context;
//        private Staff innerstaff;
//        private ProgressDialog pd;
//
//
//    //    new SaveAllTransportStudentsAsyncTask(loginActivityWeakReference.get(), innerstaff, pd).execute(result);
//
//        public SaveAllTransportStudentsAsyncTask(Context context, Staff innerStaff, ProgressDialog pd){
//            this.context = context;
//            this.innerstaff = innerStaff;
//            this.pd = pd;
//
//        }
//
//
//
//        @Override
//        protected void onPostExecute(String results) {
//
//          //  Toast.makeText(context, "StudentList: " + results , Toast.LENGTH_LONG).show();
//
//
//            //new StaffDao(loginActivityWeakReference.get()).saveUserToDB(innerstaff);
//
//
//
//
//            super.onPostExecute(results);
//        }
//
//        @Override
//        protected String doInBackground(List<TransportStudents>... params) {
//
//           final List<TransportStudents> studentList = params[0];
//
//            if(studentList.size() > 0){
//                return String.valueOf( new TransportStudentsDao(context).saveAllStudents(null,studentList));
//
//            }
//
//            return String.valueOf(studentList.size());
//
//
//           // return String.valueOf(studentList.size());
//
//        }
//    }
//
//
//    public void SendRequest(final List<Object> list) {
//
//        final UserCredentials user = (UserCredentials) list.get(0);//credentials user logged in with
//
//
//        pd = new ProgressDialog(loginActivityWeakReference.get());
//        pd.setCancelable(false);
//        pd.setMessage("Please wait for confirmation...");
//        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        pd.show();
//
//
//        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
//        Call<AuthenticateResponse> userCall = apiInterface.getAuthenticateResponse(user);
//        userCall.enqueue(new Callback<AuthenticateResponse>() {
//            @Override
//            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
//
//                AuthenticateResponse resultsList = response.body();
//
//                List<Object> listOfItems = new ArrayList<>();
//
//                // Toast.makeText(loginActivityWeakReference.get(), "AuthResponse Body: " + String.valueOf(resultsList == null ) + "  " + resultsList.toString() , Toast.LENGTH_LONG).show();
//
//                if (response.code() == 200) {
//
//                    if (resultsList != null) {
//
//
//                            listOfItems.add(Long.valueOf(response.code())); //0
//                            listOfItems.add(resultsList);//from retrofit 1
//                            listOfItems.add(user);//credentials the user logged in with 2
//
//                        Log.d(loginActivityWeakReference.get().getPackageName().toUpperCase(), "Passowrd: " + passwordtext.getText().toString() + " Username: " + usernametext.getText().toString() + " AppCode: " + appcodetext.getText().toString());
//
//                            new AuthenticateFromMziziApiTest(loginActivityWeakReference.get(), passwordtext,usernametext,appcodetext).execute(listOfItems);
//
//                    }
//                } else if (response.code() == 204) {
//                    listOfItems.add(Long.valueOf(response.code()));
//                    new AuthenticateFromMziziApiTest(loginActivityWeakReference.get(), passwordtext,usernametext,appcodetext).execute(listOfItems);
//
//                } else if (response.code() == 500) {
//                    listOfItems.add(Long.valueOf(response.code()));
//                    new AuthenticateFromMziziApiTest(loginActivityWeakReference.get(), passwordtext,usernametext,appcodetext).execute(listOfItems);
//
//                } else if (isCancelled()) {
//                    pd.cancel();
//                 //   Toast.makeText(loginActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
//              //  Toast.makeText(loginActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                call.cancel();
//                pd.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                 Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                return;
//            }
//        });
//
//    }
//
//
//    private void sendRequestForAllTransportStudents(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest, final Staff innerStaff){
//        //for testing only
//        //You can also add schoolID and organizationID
//        Call<List<TransportStudents>> userCall = apiInterface.getPortalAllTransportStudents(staffRequest);
//        userCall.enqueue(new Callback<List<TransportStudents>>() {
//            @Override
//            public void onResponse(Call<List<TransportStudents>> call, Response<List<TransportStudents>> response) {
//
//                List<TransportStudents> list = response.body();
//
//                //  Toast.makeText(loginActivityWeakReference.get(), String.valueOf(response.code() == 200 ) + "  " + list.size() + " " + response.body().get(0).toString() , Toast.LENGTH_LONG).show();
//
//
//                List<TransportStudents> result = new ArrayList<TransportStudents>();
//
//                if(response.code() == 200){
//
//
//
//                    result = response.body();
//
//                    //Toast.makeText(loginActivityWeakReference.get(), String.valueOf(result.get(0).toString()), Toast.LENGTH_LONG).show();
//
//
//
//
//                    if(result != null){
//
//                        if(result.size()> 0){
//
//
//                            // if(result.get(0).getStudentFullName() != ""){
//
//                            // Toast.makeText(loginActivityWeakReference.get(), String.valueOf(result.get(0).toString()), Toast.LENGTH_LONG).show();
////
//                            //Call PortalAllTransportStudentsListing
//
//                            new SaveAllTransportStudentsAsyncTask(loginActivityWeakReference.get(), innerStaff, pd).execute(result);
//
//
////
////
//                            // }
//                        }
//
//                    }
//                }
//
//
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<TransportStudents>> call, Throwable t) {
//               // Toast.makeText(loginActivityWeakReference.get(),"Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                call.cancel();
//
//                                            /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                             Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//                pd.cancel();
//            }
//        });
//    }
//
//    private void sendRequestForAllStaff(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest, final Staff innerstaff){
//
//
//        Call<List<AllStaff>> userCall = apiInterface.getAllStaff(staffRequest);
//        userCall.enqueue(new Callback<List<AllStaff>>() {
//            @Override
//            public void onResponse(Call<List<AllStaff>> call, final Response<List<AllStaff>> response) {
//
//
//                if(response.isSuccessful()){
//
//                    // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//                    if(response.code() == 200) {
//
//                        AsyncTask asyncTask = new AsyncTask() {
//                            @Override
//                            protected Object doInBackground(Object[] params) {
//                                long id = new AllStaffDAO(mContext).saveAllStaffDAO(((List<AllStaff>) params[0]));
//                                //Log.d(loginActivityWeakReference.get().getPackageName().toUpperCase(), ((List<AllStaff>) params[0]).toString());
//                                return null;
//                            }
//                        };
//                        asyncTask.execute(response.body());
//
//                    }
//
//                   // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();
//
//                }
//
//
//                //This is put to the last part of the request
//                Intent intent = new Intent(loginActivityWeakReference.get(), HomeScreen.class);
//                //false because you dint log in, you just went back, then your your system check if there you had logged
//                intent.putExtra("isLog_In", true);//will check if to call the web service or just retrieve the data from the database
//                intent.putExtra("appcode",innerstaff.getAppcode());
//                intent.putExtra("staffID", innerstaff.getStaff_ID());
//                intent.putExtra("userType", innerstaff.getUserType());
//                intent.putExtra("SchoolID",  innerstaff.getSchoolID());
//                intent.putExtra("OrganizationID", innerstaff.getOrganizationID());
//
//                // intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
//                loginActivityWeakReference.get().startActivity(intent);
//                loginActivityWeakReference.get().finish();
//
//
//
//
//                pd.cancel();
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<List<AllStaff>> call, Throwable t) {
//               // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
//                call.cancel();
//
//                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
//                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
//            }
//        });
//
//    }
//
//    //All this will be taken to the login location
//    private void sendRequestForAllStudents(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//
//
//        Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> userCall = apiInterface.getAllStudents(staffRequest);
//        userCall.enqueue(new Callback<List<ultratude.com.staff.webservice.ResponseModels.Student>>() {
//                             @Override
//                             public void onResponse(Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> call, final Response<List<ultratude.com.staff.webservice.ResponseModels.Student>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//                                     if(response.code() == 200) {
//
//                                         AsyncTask asyncTask = new AsyncTask() {
//                                             @Override
//                                             protected Object doInBackground(Object[] params) {
//                                                 long id = new StudentDAO(mContext).saveStudent((((List<ultratude.com.staff.webservice.ResponseModels.Student>) params[0])));
//                                                 return null;
//                                             }
//                                         };
//                                         asyncTask.execute(response.body());
//
//                                     }
//
//                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();
//
//                                 }else{
//                                     //you dont have to delete all the data
//                                 }
//
//                             }
//
//                             @Override
//                             public void onFailure(Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> call, Throwable t) {
//
//                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//
//    }
//
//    private void sendRequestForAllVehicles(final Context mContext, final ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//
//
//
//        Call<List<Vehicle>> userCall = apiInterface.getAllVehicles(staffRequest);
//        userCall.enqueue(new Callback<List<Vehicle>>() {
//                             @Override
//                             public void onResponse(Call<List<Vehicle>> call, final Response<List<Vehicle>> response) {
//                                 if(response.isSuccessful()){
//
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//
//                                     if(response.code() == 200) {
//
//                                         AsyncTask asyncTask = new AsyncTask() {
//                                             @Override
//                                             protected Object doInBackground(Object[] params) {
//                                                 long id = new VehicleDAO(mContext).saveVehicle(((List<Vehicle>) params[0]));
//
//                                                 return null;
//                                             }
//                                         };
//                                         asyncTask.execute(response.body());
//                                     }
//
//                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();
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
//                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//    }
//
//    private void sendRequestForALLSchools(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//
//
//
//        Call<List<Schools>> userCall = apiInterface.getAllSchools(staffRequest);
//        userCall.enqueue(new Callback<List<Schools>>() {
//                             @Override
//                             public void onResponse(Call<List<Schools>> call, final Response<List<Schools>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//
//                                     if(response.code() == 200) {
//
//                                         AsyncTask asyncTask = new AsyncTask() {
//                                             @Override
//                                             protected Object doInBackground(Object[] params) {
//                                                 long id = new SchoolsDAO(mContext).saveSchools(((List<Schools>) params[0]));
//
//                                                 return null;
//                                             }
//                                         };
//                                         asyncTask.execute(response.body());
//
//                                     }
//
//                                     //  Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();
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
//                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//
//    }
//
//    private void sendRequestForDutyRosterList(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//
//        Call<List<DutyRoster>> userCall = apiInterface.getDutyRosterList(staffRequest);
//        userCall.enqueue(new Callback<List<DutyRoster>>() {
//                             @Override
//                             public void onResponse(Call<List<DutyRoster>> call, final Response<List<DutyRoster>> response) {
//                                 if(response.isSuccessful()){
//
//                                     // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
//                                     if(response.code() == 200) {
//                                         AsyncTask asyncTask = new AsyncTask() {
//                                             @Override
//                                             protected Object doInBackground(Object[] params) {
//                                                 long id = new DutyRosterDAO(mContext).saveDutyRoster(((List<DutyRoster>) params[0]));
//
//                                                 return null;
//                                             }
//                                         };
//                                         asyncTask.execute(response.body());
//                                     }
//
//                                     // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();
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
//                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again." + t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                             }
//                         }
//
//
//        );
//    }
//
//    private void sendRequestForRollCallSessions(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//        Call<List<RollCallSession>> userCall = apiInterface.getRollCallSessions(staffRequest);
//        userCall.enqueue(new Callback<List<RollCallSession>>() {
//            @Override
//            public void onResponse(Call<List<RollCallSession>> call, Response<List<RollCallSession>> response) {
//
//                if(response.isSuccessful()){
//                    if(response.code() == 200) {
//
//                        AsyncTask asyncTask = new AsyncTask() {
//                            @Override
//                            protected Object doInBackground(Object[] params) {
//
//                                long id = new RollCallSessonsDAO(mContext).saveRollCallSessons(((List<RollCallSession>) params[0]));
//
//
//                                return null;
//                            }
//                        };
//                        asyncTask.execute(response.body());
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<RollCallSession>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void sendRequestForLeaveTypes(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//        Call<List<LeaveType>> userCall = apiInterface.getLeaveTypes(staffRequest);
//        userCall.enqueue(new Callback<List<LeaveType>>() {
//            @Override
//            public void onResponse(Call<List<LeaveType>> call, Response<List<LeaveType>> response) {
//
//                if(response.isSuccessful()){
//                    if(response.code() == 200) {
//
//                        AsyncTask asyncTask = new AsyncTask() {
//                            @Override
//                            protected Object doInBackground(Object[] params) {
//                                long id = new LeaveTypeDAO(mContext).saveLeaveTypes(((List<LeaveType>) params[0]));
//                                return null;
//                            }
//                        };
//                        asyncTask.execute(response.body());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<LeaveType>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void sendRequestForClassStream(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//        Call<List<ClassStream>> userCall = apiInterface.getClassStreamTeacherStaffAllocation(staffRequest);
//        userCall.enqueue(new Callback<List<ClassStream>>() {
//            @Override
//            public void onResponse(Call<List<ClassStream>> call, final Response<List<ClassStream>> response) {
//
//               // Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();
//
//            //   Log.d(loginActivityWeakReference.get().getPackageName().toUpperCase(),response.body().toString());
//
//                if(response.isSuccessful()){
//                    if(response.code() == 200) {
//                        AsyncTask asyncTask = new AsyncTask() {
//                            @Override
//                            protected Object doInBackground(Object[] params) {
//                                long id = new ClassStreamDAO(mContext).saveClassStreamDAO(((List<ClassStream>) params[0]));
//                                return null;
//                            }
//                        };
//
//                        asyncTask.execute(response.body());
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ClassStream>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void sendRequestForStaffOperations(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//        Call<List<StaffOperation>> userCall = apiInterface.getStaffOperations(staffRequest);
//        userCall.enqueue(new Callback<List<StaffOperation>>() {
//            @Override
//            public void onResponse(Call<List<StaffOperation>> call, final Response<List<StaffOperation>> response) {
//
//                if(response.isSuccessful()){
//                    if(response.code() == 200) {
//
//                        AsyncTask asyncTask = new AsyncTask() {
//                            @Override
//                            protected Object doInBackground(Object[] params) {
//                                long id = new StaffOperationsDAO(mContext).saveStaffOperation(((List<StaffOperation>) params[0]));
//                                return null;
//                            }
//                        };
//                        asyncTask.execute(response.body());
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<StaffOperation>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void sendRequestForSupportHelp(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest){
//        Call<List<Help>> userCall = apiInterface.getSupportHelp(staffRequest);
//        userCall.enqueue(new Callback<List<Help>>() {
//            @Override
//            public void onResponse(Call<List<Help>> call, Response<List<Help>> response) {
//                if(response.isSuccessful()){
//                    if(response.code() == 200){
//                        AsyncTask asyncTask = new AsyncTask() {
//                            @Override
//                            protected Object doInBackground(Object[] params) {
//                                long id = new HelpDAO(mContext).saveHelp(((List<Help>) params[0]));
//                                return null;
//                            }
//                        };
//                        asyncTask.execute(response.body());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Help>> call, Throwable t) {
//
//            }
//        });
//    }
//
//
//}
