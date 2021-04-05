package ultratude.com.mzizi.webservice.apiconnections;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.appcompat.widget.AppCompatEditText;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.firebasejobdispatch.RequestFor;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.modelclasses.UserCredentials;
import ultratude.com.mzizi.retrofitokhttp.APIClient;
import ultratude.com.mzizi.retrofitokhttp.APIInterface;
import ultratude.com.mzizi.retrofitokhttp.Pojo.FilteredStudentInfoResults;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountDAO;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.uiactivities.LoginActivity;
import ultratude.com.mzizi.uiactivities.SyncMyAccount;
import ultratude.com.mzizi.webservice.APIRequest;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
import ultratude.com.staff.webservice.DataAccessObjects.ClassStreamDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.HelpDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LeaveTypeDAO;
import ultratude.com.staff.webservice.DataAccessObjects.RollCallSessonsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.SchoolsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportBusTripsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportSessionsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
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
import ultratude.com.staff.webservice.RequestModels.PortalTransportSessionBusTripsResponse;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;


public class AuthenticateUser {

    private WeakReference<LoginActivity> loginActivityWeakReference;

    private AppCompatEditText passwordtext, usernametext, appcodetext;

    private static ProgressDialog pd;

    public AuthenticateUser(final LoginActivity loginActivity, final AppCompatEditText passwordtext, final AppCompatEditText usernametext, final AppCompatEditText appcodetext) {


        loginActivityWeakReference = new WeakReference<>(loginActivity);

        this.passwordtext = passwordtext;
        this.usernametext = usernametext;
        this.appcodetext = appcodetext;

        final UserCredentials user = new UserCredentials(usernametext.getText().toString(), passwordtext.getText().toString(), appcodetext.getText().toString());


        pd = new ProgressDialog(loginActivityWeakReference.get());
        pd.setCancelable(false);
        pd.setMessage("Please wait for confirmation...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        Call<AuthenticateResponse> userCall = apiInterface.getAuthenticateResponse(user);
        userCall.enqueue(new Callback<AuthenticateResponse>() {
            @Override
            public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {

                final AuthenticateResponse authenticateResponse = response.body();

                if (response.code() == 200) {

                    //  new AuthenticateFromMziziApiTest(loginActivityWeakReference.get(), passwordtext,usernametext,appcodetext).execute(listOfItems);

                    if (authenticateResponse.getUserID().trim().equalsIgnoreCase("0") && authenticateResponse.getUserType().trim().equalsIgnoreCase("") && authenticateResponse.getSchoolID().trim().equalsIgnoreCase("") && authenticateResponse.getOrganizationID().trim().equalsIgnoreCase("")) {
                        pd.cancel();
                        AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
                        alert.setCancelable(false);
                        alert.setTitle("Confirmation");
                        alert.setMessage("Please ensure your username, password and school code are correct");
                        alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // passwordtext.setText(dataFromEndpoint.get("StudentID") +" " + dataFromEndpoint.get("username"));
//                    passwordtext.setText("");
//                    usernametext.setText("");
//
                                dialogInterface.cancel();

                            }
                        });
                        alert.show();
                    } else {


                        if (authenticateResponse.getUserType().equalsIgnoreCase("STUDENT")) {

                            new StudentAsyc(user, authenticateResponse).execute();

                        } else if (authenticateResponse.getUserType().equalsIgnoreCase("STAFF")) {

                            new StaffAsyc(user, authenticateResponse).execute();

                        }
                    }

                } else if (response.code() == 204) {
                    pd.cancel();
                    AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
                    alert.setCancelable(false);
                    alert.setTitle("Confirmation");
                    alert.setMessage("Please ensure your username, password and school code are correct");
                    alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    alert.show();
                } else if (response.code() == 500) {
                    pd.cancel();
                    AlertDialog.Builder alert = new AlertDialog.Builder(loginActivityWeakReference.get());
                    alert.setCancelable(false);
                    alert.setTitle("Confirmation");
                    alert.setMessage("Please ensure your username, password and school code are correct");
                    alert.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    alert.show();
                }

            }

            @Override
            public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                //  Toast.makeText(loginActivityWeakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                call.cancel();
                pd.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                 Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                return;
            }
        });


    }


    private class StudentAsyc extends AsyncTask<Void, Void, Void> {

        private UserCredentials userCredentials;
        private AuthenticateResponse authenticateUserResponse;

        StudentAsyc(UserCredentials userCredentials, AuthenticateResponse authenticateResponse) {
            this.userCredentials = userCredentials;
            this.authenticateUserResponse = authenticateResponse;
        }

        @Override
        protected void onPreExecute() {
            pd.cancel();
            (loginActivityWeakReference.get()).bar.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //should cancel the progressdialog, but i will use it to display if the other information has been finished

            //remember when sending a request you can now include in schoolID and OrganizationID
            final Student student = new Student(authenticateUserResponse.getUserID(), userCredentials.getAppcode());
            student.setOrganizationID(authenticateUserResponse.getOrganizationID());


            // new GetStudentInfoFromMziziApi(loginActivityWeakReference.get(), student).execute(student);

            //END OF TESTING

            //the use is already authenticated, you can make a call to the PortalStudentInfo
            //start
            //CREATE NEW USER --working

            //REMEMBER THIS IS NOT WORKING BECAUSE YOU HAVE NOT HOSTED THE API TO CLOUD
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
            //for testing only
            Student studenttest = new Student(student.getStudentID(), student.getAppcode());
            Call<FilteredStudentInfoResults> userCall = apiInterface.getFilteredStudentInfo(studenttest);
            userCall.enqueue(new Callback<FilteredStudentInfoResults>() {
                @Override
                public void onResponse(Call<FilteredStudentInfoResults> call, Response<FilteredStudentInfoResults> response) {

                    FilteredStudentInfoResults result = response.body();

                    if (result != null) {


                        if (result.getPortalAccount() == "" || result.getPortalPaybill() == null) {

                        } else {

                            //save this information into the database
                            SyncMyAccountResult account = new SyncMyAccountResult();
                            //Balance, BillingBalance, PortalAccount, PaybillNo
                            account.setStudID(Integer.valueOf(student.getStudentID()));
                            account.setBalance(result.getBalance());
                            account.setBillingBalance(result.getBillingBalance());
                            account.setPortalPaybill(result.getPortalPaybill());
                            account.setPortalAccount(result.getPortalAccount());

                            new SyncMyAccountDAO(loginActivityWeakReference.get()).deleteForSyncMyAccountResult(student.getStudentID());

                            //TESTING
                            // String studentID = ParentMziziDatabase.getInstance(loginActivityWeakReference.get().getApplicationContext()).getPortalSiblingsDao().getMainStudentFromSibling();
                            account.setStudID(Integer.valueOf(student.getStudentID()));
                            new SyncMyAccountDAO(loginActivityWeakReference.get()).insertSyncMyAccountResult(account);

                        }


                        if (Float.valueOf(result.getBillingBalance()) > 0f) {//should redirect
                            // if(500>0){//should redirect

                            Intent intent = new Intent(loginActivityWeakReference.get(), SyncMyAccount.class);
                            intent.putExtra("StudentID", student.getStudentID());
                            intent.putExtra("appcode", student.getAppcode());
                            intent.putExtra("isLog_In", (loginActivityWeakReference.get()).getIntent().getBooleanExtra("isLog_In", false));


                            //no need of doing this, just add the data to the database
                            // intent.putExtra("Account", account);

                            intent.putExtra("CALL_FROM", "AUTHENTICATE_USER");

                            loginActivityWeakReference.get().startActivity(intent);
                            loginActivityWeakReference.get().finish();

                        } else {


                            //should move to SyncMyAccount


                            //new GetStudentInfoFromMziziApi(loginActivityWeakReference.get(), student).SendRequest(student);
                        }

                    }


                }

                @Override
                public void onFailure(Call<FilteredStudentInfoResults> call, Throwable t) {
                    // Toast.makeText(loginActivityWeakReference.get(),"Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                    // call.cancel();

                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                    pd.cancel();
                    return;
                }
            });


            Call<List<PortalSiblings>> userCall2 = apiInterface.getPortalSiblings(student);
            userCall2.enqueue(new Callback<List<PortalSiblings>>() {
                @Override
                public void onResponse(Call<List<PortalSiblings>> call, final Response<List<PortalSiblings>> response) {


                    if (response.code() == 200) {

                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected void onPostExecute(Object o) {

                                final List<PortalSiblings> siblingsList = (List<PortalSiblings>) o;

                                if (siblingsList.size() > 0) {
                                    RequestFor.firebaseJobDispatch = true;
                                    RequestFor.sendRequest(new Student("", userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.REGISTER_FCM_TOKEN);


                                    for (int i = 0; i < siblingsList.size(); i++) {
                                        final int count = i;


                                        if (count == 0) {
                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

//                                        new Handler().post(new Runnable() {
//                                            @Override
//                                            public void run() {
                                            Object activity = loginActivityWeakReference.get();
                                            RequestFor.weakReference = new WeakReference<>(activity);
                                            RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);

//                                            }
//                                        });

                                        } else if (count == 1) {

                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);
                                                }
                                            }, 20000);
                                        } else if (count == 2) {

                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);
                                                }
                                            }, 40000);
                                        } else if (count == 3) {

                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);
                                                }
                                            }, 60000);
                                        } else if (count == 4) {

                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);
                                                }
                                            }, 80000);
                                        } else if (count == 5) {

                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);
                                                }
                                            }, 100000);
                                        } else if (count == 6) {

                                            if (count == (siblingsList.size() - 1))
                                                RequestFor.login = true;

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(new Student(siblingsList.get(count).getStudentID(), userCredentials.getAppcode()), loginActivityWeakReference.get(), "", APIRequest.ALL);
                                                }
                                            }, 120000);
                                        }


                                    }
                                } else {
                                    ///get student info, save as sibling, main
                                    sendRequestForStudentInfo(student, loginActivityWeakReference.get());
                                }


                                super.onPostExecute(o);
                            }

                            @Override
                            protected Object doInBackground(Object[] params) {
                                ParentMziziDatabase db = ParentMziziDatabase.getInstance(loginActivityWeakReference.get());
                                db.getPortalSiblingsDao().deleteSiblings();
                                db.getPortalSiblingsDao().insertMoreSiblings(response.body());

                                db.getSwitchSiblingsDAO().setTheMainStudentToThisStudentIDPassed(authenticateUserResponse.getUserID());

                                return db.getPortalSiblingsDao().getSiblings();
                            }
                        };
                        asyncTask.execute();

                    } else {
                        ///get student info, save as sibling, main
                        sendRequestForStudentInfo(student, loginActivityWeakReference.get());
                    }

                }

                @Override
                public void onFailure(Call<List<PortalSiblings>> call, Throwable t) {
                    // Toast.makeText((LoginActivity) weakReference.get(), "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();
                    sendRequestForStudentInfo(student, loginActivityWeakReference.get());

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                    return;
                }
            });

            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            AuthenticateResponse authenticateResponse = authenticateUserResponse;

            AuthenticateUserResponse userResponse = new AuthenticateUserResponse();
            userResponse.setUserID(authenticateResponse.getUserID());
            userResponse.setUserType(authenticateResponse.getUserType());
            userResponse.setAppcode(appcodetext.getText().toString());
            userResponse.setUsername(usernametext.getText().toString());
            userResponse.setPassword(passwordtext.getText().toString());
            userResponse.setSchoolID(authenticateResponse.getSchoolID());
            userResponse.setOrganizationID(authenticateResponse.getOrganizationID());
            userResponse.setLogin_status("SUCCESS");

            loginActivityWeakReference.get().db.getAuthenticateUserResponseDao().deleteAuthenticateUserResponse();

            loginActivityWeakReference.get().db.getAuthenticateUserResponseDao().insertAuthenticateUserResponse(userResponse);

            return null;

        }


        private void sendRequestForStudentInfo(final Student student, final Context context) {

            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

            Call<PortalStudentInfo> userCall = apiInterface.getStudentInfo(student);
            userCall.enqueue(new Callback<PortalStudentInfo>() {
                @Override
                public void onResponse(Call<PortalStudentInfo> call, final Response<PortalStudentInfo> response) {


                    //  Toast.makeText(context, String.valueOf(response.code() == 200 ) + "  " + portalStudentInfo.toString() , Toast.LENGTH_LONG).show();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getStudentFullName() != "") {

                                try {
                                    AsyncTask asyncTask = new AsyncTask() {

                                        @Override
                                        protected void onPostExecute(Object o) {
                                            final PortalStudentInfo studentInfo = response.body();

                                            new AsyncTask() {
                                                @Override
                                                protected void onPostExecute(Object o) {
                                                    RequestFor.login = true;

                                                    Object activity = loginActivityWeakReference.get();
                                                    RequestFor.weakReference = new WeakReference<>(activity);
                                                    RequestFor.sendRequest(student, loginActivityWeakReference.get(), "", APIRequest.ALL);

                                                    super.onPostExecute(o);
                                                }

                                                @Override
                                                protected Object doInBackground(Object[] objects) {
                                                    PortalSiblings sibling = new PortalSiblings();
                                                    sibling.setStudentID(student.getStudentID());
                                                    sibling.setRegistrationNumber(studentInfo.getRegistrationNumber());
                                                    sibling.setCourseName(studentInfo.getClassStream());
                                                    sibling.setStudentFullName(studentInfo.getTwoNames());


                                                    sibling.setUsername(userCredentials.getUserName());
                                                    sibling.setDefaultPassword(userCredentials.getPassword());
                                                    sibling.setSchoolID(authenticateUserResponse.getSchoolID());
                                                    sibling.setOrganizationID(authenticateUserResponse.getOrganizationID());

                                                    ParentMziziDatabase db = ParentMziziDatabase.getInstance(loginActivityWeakReference.get());
                                                    db.getPortalSiblingsDao().deleteSiblings();
                                                    db.getPortalSiblingsDao().insertSibling(sibling);

                                                    db.getSwitchSiblingsDAO().setTheMainStudentToThisStudentIDPassed(authenticateUserResponse.getUserID());

                                                    //testing
//                                                    List<PortalSiblings> list = db.getPortalSiblingsDao().getSiblings();
//
//                                                    List<AuthenticateUserResponse> list2 = db.getAuthenticateUserResponseDao().getAuthenticateUserResponse();
//
//                                                    String student = db.getPortalSiblingsDao().getMainStudentFromSibling();

                                                    return null;
                                                }
                                            }.execute();

                                            super.onPostExecute(o);
                                        }

                                        @Override
                                        protected Object doInBackground(Object[] objects) {

                                            response.body().setStudID(Integer.valueOf(student.getStudentID()));
                                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);

                                            db.getPortalStudentInfoDao().deleteStudent(Integer.valueOf(student.getStudentID()));

                                            List<PortalStudentInfo> portalStudentInfoList = db.getPortalStudentInfoDao().checkIfStudentWithThisRegistrationNumberExist(response.body().getRegistrationNumber());
                                            if(portalStudentInfoList.size() > 0){
                                                for(PortalStudentInfo info : portalStudentInfoList){
                                                    if(info.getRegistrationNumber().equalsIgnoreCase(response.body().getRegistrationNumber())){
                                                        db.getPortalStudentInfoDao().deleteAllStudents();
                                                    }
                                                }
                                            }
                                            db.getPortalStudentInfoDao().insertStudent(response.body());
                                            return null;
                                        }
                                    };
                                    asyncTask.execute();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }


                        }
                    }

                }

                @Override
                public void onFailure(Call<PortalStudentInfo> call, Throwable t) {
                    // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                    return;
                }
            });
        }
    }

    private class StaffAsyc extends AsyncTask<Void, Void, Staff> {

        private UserCredentials userCredentials;
        private AuthenticateResponse authenticateUserResponse;


        StaffAsyc(UserCredentials userCredentials, AuthenticateResponse authenticateResponse) {
            this.userCredentials = userCredentials;
            this.authenticateUserResponse = authenticateResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Staff staff) {
            //should cancel the progressdialog, but i will use it to display if the other information has been finished

            //start
            ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface = ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient.getClient().create(ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface.class);//used to instantiate the APIClient.
            //APIInterface apiInterface1 = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.
//
            StaffRequest staffRequest = new StaffRequest(
                    staff.getStaff_ID(),
                    staff.getSchoolID(),
                    staff.getOrganizationID(),
                    staff.getAppcode()
            );

            //all get request
            sendRequestForAllTransportStudents(loginActivityWeakReference.get(), apiInterface, staffRequest, staff);
            sendRequestForAllStudents(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForAllVehicles(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForRollCallSessions(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForLeaveTypes(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForStaffOperations(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForSupportHelp(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForClassStream(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForALLSchools(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForDutyRosterList(loginActivityWeakReference.get(), apiInterface, staffRequest);
            sendRequestForAllStaff(loginActivityWeakReference.get(), apiInterface, staffRequest, staff);
            sendRequestForTransportSessionsAndBusTrips(loginActivityWeakReference.get(),apiInterface,staffRequest);

            //end
            super.onPostExecute(staff);
        }

        @Override
        protected Staff doInBackground(Void... voids) {

            AuthenticateResponse authenticateResponse = authenticateUserResponse;

            //save user credentials
            Staff staff = new Staff();
            staff.setAppcode(userCredentials.getAppcode());
            staff.setStaff_ID(authenticateResponse.getUserID());
            staff.setUserType(authenticateResponse.getUserType());
            staff.setSchoolID(authenticateResponse.getSchoolID());
            staff.setOrganizationID(authenticateResponse.getOrganizationID());

            staff.setAppcode(appcodetext.getText().toString());
            staff.setUsername(usernametext.getText().toString());
            staff.setPassword(passwordtext.getText().toString());

            try {
                new StaffDao(loginActivityWeakReference.get()).saveUserToDB(staff);

            } catch (SQLiteException e) {

                new StaffDao(loginActivityWeakReference.get()).deleteStaff();
            }

            return staff;
        }


        private void sendRequestForAllTransportStudents(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest, final Staff innerStaff) {
            //for testing only
            //You can also add schoolID and organizationID
            Call<List<TransportStudents>> userCall = apiInterface.getPortalAllTransportStudents(staffRequest);
            userCall.enqueue(new Callback<List<TransportStudents>>() {
                @Override
                public void onResponse(Call<List<TransportStudents>> call, final Response<List<TransportStudents>> response) {

                    if (response.code() == 200) {

                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected Object doInBackground(Object[] objects) {
                                return new TransportStudentsDao(mContext).saveAllStudents(null, response.body());
                            }
                        };
                        asyncTask.execute();

                    }

                }

                @Override
                public void onFailure(Call<List<TransportStudents>> call, Throwable t) {
                    // Toast.makeText(loginActivityWeakReference.get(),"Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                    call.cancel();

                                            /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                             Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                    pd.cancel();
                }
            });
        }

        private void sendRequestForTransportSessionsAndBusTrips(final Context mContext,ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest ){
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
                public void onFailure(Call<PortalTransportSessionBusTripsResponse> call, Throwable t) {}
            });
        }



        private void sendRequestForAllStaff(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest, final Staff innerstaff) {


            Call<List<AllStaff>> userCall = apiInterface.getAllStaff(staffRequest);
            userCall.enqueue(new Callback<List<AllStaff>>() {
                @Override
                public void onResponse(Call<List<AllStaff>> call, final Response<List<AllStaff>> response) {


                    if (response.isSuccessful()) {

                        // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                        if (response.code() == 200) {

                            AsyncTask asyncTask = new AsyncTask() {
                                @Override
                                protected Object doInBackground(Object[] params) {
                                    long id = new AllStaffDAO(mContext).saveAllStaffDAO(((List<AllStaff>) params[0]));
                                    //Log.d(loginActivityWeakReference.get().getPackageName().toUpperCase(), ((List<AllStaff>) params[0]).toString());
                                    return null;
                                }
                            };
                            asyncTask.execute(response.body());

                        }

                        // Toast.makeText(mContext, "Passed: " + String.valueOf(response.body().size()) + " Saved: " + id, Toast.LENGTH_LONG).show();

                    }


                    //This is put to the last part of the request
                    Intent intent = new Intent(loginActivityWeakReference.get(), HomeScreen.class);
                    //false because you dint log in, you just went back, then your your system check if there you had logged
                    intent.putExtra("isLog_In", true);//will check if to call the web service or just retrieve the data from the database
                    intent.putExtra("appcode", innerstaff.getAppcode());
                    intent.putExtra("staffID", innerstaff.getStaff_ID());
                    intent.putExtra("userType", innerstaff.getUserType());
                    intent.putExtra("SchoolID", innerstaff.getSchoolID());
                    intent.putExtra("OrganizationID", innerstaff.getOrganizationID());

                    // intent.putExtra(OpenFragments.OPEN_HOME_FRAGMENT, "HomeFragment");
                    loginActivityWeakReference.get().startActivity(intent);
                    loginActivityWeakReference.get().finish();


                    pd.cancel();


                }

                @Override
                public void onFailure(Call<List<AllStaff>> call, Throwable t) {
                    // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again!", Toast.LENGTH_SHORT).show();
                    call.cancel();

                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                   Couldnt connect to server. Make sure your phone has an internet connection and try again.*/
                }
            });

        }

        //All this will be taken to the login location
        private void sendRequestForAllStudents(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {


            Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> userCall = apiInterface.getAllStudents(staffRequest);
            userCall.enqueue(new Callback<List<ultratude.com.staff.webservice.ResponseModels.Student>>() {
                                 @Override
                                 public void onResponse(Call<List<ultratude.com.staff.webservice.ResponseModels.Student>> call, final Response<List<ultratude.com.staff.webservice.ResponseModels.Student>> response) {
                                     if (response.isSuccessful()) {

                                         // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                                         if (response.code() == 200) {

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

                                     } else {
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

        private void sendRequestForAllVehicles(final Context mContext, final ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {


            Call<List<Vehicle>> userCall = apiInterface.getAllVehicles(staffRequest);
            userCall.enqueue(new Callback<List<Vehicle>>() {
                                 @Override
                                 public void onResponse(Call<List<Vehicle>> call, final Response<List<Vehicle>> response) {
                                     if (response.isSuccessful()) {


                                         // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                         if (response.code() == 200) {

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

                                     } else {
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

        private void sendRequestForALLSchools(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {


            Call<List<Schools>> userCall = apiInterface.getAllSchools(staffRequest);
            userCall.enqueue(new Callback<List<Schools>>() {
                                 @Override
                                 public void onResponse(Call<List<Schools>> call, final Response<List<Schools>> response) {
                                     if (response.isSuccessful()) {

                                         // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();

                                         if (response.code() == 200) {

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

                                     } else {
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

        private void sendRequestForDutyRosterList(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {

            Call<List<DutyRoster>> userCall = apiInterface.getDutyRosterList(staffRequest);
            userCall.enqueue(new Callback<List<DutyRoster>>() {
                                 @Override
                                 public void onResponse(Call<List<DutyRoster>> call, final Response<List<DutyRoster>> response) {
                                     if (response.isSuccessful()) {

                                         // Toast.makeText(context, String.valueOf(response.body()), Toast.LENGTH_LONG).show();
                                         if (response.code() == 200) {
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

                                     } else {
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

        private void sendRequestForRollCallSessions(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {
            Call<List<RollCallSession>> userCall = apiInterface.getRollCallSessions(staffRequest);
            userCall.enqueue(new Callback<List<RollCallSession>>() {
                @Override
                public void onResponse(Call<List<RollCallSession>> call, Response<List<RollCallSession>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

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

        private void sendRequestForLeaveTypes(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {
            Call<List<LeaveType>> userCall = apiInterface.getLeaveTypes(staffRequest);
            userCall.enqueue(new Callback<List<LeaveType>>() {
                @Override
                public void onResponse(Call<List<LeaveType>> call, Response<List<LeaveType>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

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

        private void sendRequestForClassStream(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {
            Call<List<ClassStream>> userCall = apiInterface.getClassStreamTeacherStaffAllocation(staffRequest);
            userCall.enqueue(new Callback<List<ClassStream>>() {
                @Override
                public void onResponse(Call<List<ClassStream>> call, final Response<List<ClassStream>> response) {

                    // Toast.makeText(mContext, response.body().toString(), Toast.LENGTH_SHORT).show();

                    //   Log.d(loginActivityWeakReference.get().getPackageName().toUpperCase(),response.body().toString());

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            AsyncTask asyncTask = new AsyncTask() {
                                @Override
                                protected Object doInBackground(Object[] params) {
                                    long id = new ClassStreamDAO(mContext).saveClassStreamDAO(((List<ClassStream>) params[0]));
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

        private void sendRequestForStaffOperations(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {
            Call<List<StaffOperation>> userCall = apiInterface.getStaffOperations(staffRequest);
            userCall.enqueue(new Callback<List<StaffOperation>>() {
                @Override
                public void onResponse(Call<List<StaffOperation>> call, final Response<List<StaffOperation>> response) {

                    if (response.isSuccessful()) {
                        if (response.code() == 200) {

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

        private void sendRequestForSupportHelp(final Context mContext, ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface apiInterface, StaffRequest staffRequest) {
            Call<List<Help>> userCall = apiInterface.getSupportHelp(staffRequest);
            userCall.enqueue(new Callback<List<Help>>() {
                @Override
                public void onResponse(Call<List<Help>> call, Response<List<Help>> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            AsyncTask asyncTask = new AsyncTask() {
                                @Override
                                protected Object doInBackground(Object[] params) {
                                    long id = new HelpDAO(mContext).saveHelp(((List<Help>) params[0]));
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

    }
}




