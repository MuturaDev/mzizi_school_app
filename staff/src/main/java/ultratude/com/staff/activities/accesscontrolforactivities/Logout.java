package ultratude.com.staff.activities.accesscontrolforactivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import io.paperdb.Paper;
import ultratude.com.staff.services.DataTransferService;
import ultratude.com.staff.webservice.DataAccessObjects.AllStaffDAO;
import ultratude.com.staff.webservice.DataAccessObjects.AllTransportStudentsListDao;
import ultratude.com.staff.webservice.DataAccessObjects.ClassStreamDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DisciplineCaseDAO;
import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.ExamHistoryDAO;
import ultratude.com.staff.webservice.DataAccessObjects.HelpDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LeaveTypeDAO;
import ultratude.com.staff.webservice.DataAccessObjects.MarkRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.RollCallSessonsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.SchoolsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StudentDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
import ultratude.com.staff.webservice.DataAccessObjects.TripLatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleFuelingDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleServicingDAO;

/**
 * Created by James on 06/05/2019.
 */

public class Logout extends AsyncTask<Context,Void, Void>{


    private WeakReference<HomeScreen> homeScreenWeakReference;
    private ProgressDialog pDialog;
    public Logout( HomeScreen activity){
        homeScreenWeakReference = new WeakReference<>(activity);
        Paper.init(homeScreenWeakReference.get());
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(homeScreenWeakReference.get());
        pDialog.setMessage("Please wait, processing your action...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent intent = new Intent(homeScreenWeakReference.get(), DataTransferService.class);
        homeScreenWeakReference.get().stopService(intent);
       homeScreenWeakReference.get().finish();
        pDialog.cancel();
        super.onPostExecute(aVoid);

    }

    @Override
    protected Void doInBackground(Context... params) {
        Context context =  params[0];

        new AllStaffDAO(context).deleteAllStaff();
        new AllTransportStudentsListDao(context).deleteAllStudentsList();
        new ClassStreamDAO(context).deleteClassStreams();;
        new DisciplineCaseDAO(context).deleteAllDisciplineCases();
        new DutyRosterDAO(context).deleteAllDutyRoster();
        new ExamHistoryDAO(context).deleteExamResultsHistory();
        new LatLongDAO(context).deleteLatLong();
        new LeaveTypeDAO(context).deleteLeaveType();
        new TripLatLongDAO(context).deleteAllTripLatLong();
        new MarkRegisterDAO(context).deleteRegister();
        new RollCallSessonsDAO(context).deleteAllRollCallSesson();
        new SchoolsDAO(context).deleteSchools();
        new StaffDao(context).deleteStaff();
        new StaffOperationsDAO(context).deleteStaffOperations();
        new StudentDAO(context).deleteAllStudents();
        new TransportStudentsDao(context).deleteAllTransportStudents();
        new UltraDataDao(context).deleteUltradata();
        new VehicleDAO(context).deleteVehicles();
        new VehicleFuelingDAO(context).deleteVehicleFueling();
        new VehicleServicingDAO(context).deleteVehicleServicing();
        new HelpDAO(context).deleteAllHelpSupport();


        Paper.book().destroy();


        return null;

    }
}
