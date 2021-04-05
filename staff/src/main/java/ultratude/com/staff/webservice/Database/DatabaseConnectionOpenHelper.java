package ultratude.com.staff.webservice.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 3/30/2018.
 */

public class DatabaseConnectionOpenHelper extends SQLiteOpenHelper {


    private Context context;
    private static final String DBULTRABUS_NAME = "ultrabusdb.db";
    //JUST UDPATED TO VERSION  FROM 5
    private static final int DBULTRABUS_VERSION = 11;//from 4 app version 22



    private static final String ULTRADATA_QUERY = "CREATE TABLE " +
            ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + "(" +
            ConnSQLiteContract.UltraDataDef._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.UltraDataDef.STUDENTID_BARCODE + " TEXT NOT NULL, " +
            ConnSQLiteContract.UltraDataDef.LATITUDE + " FLOAT NOT NULL, " +
            ConnSQLiteContract.UltraDataDef.LONGITUDE + " FLOAT NOT NULL, " +
            ConnSQLiteContract.UltraDataDef.BUS_ACTIVITY + " TEXT NOT NULL, " +
            ConnSQLiteContract.UltraDataDef.SESSION + " TEXT DEFAULT NULL, " +
            ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE + " TEXT DEFAULT NULL, " +
            ConnSQLiteContract.UltraDataDef.BUSTRIP + " TEXT DEFAULT NULL, " +
            ConnSQLiteContract.UltraDataDef.STATUS_SENT + " TEXT DEFAULT NULL, " +
            ConnSQLiteContract.UltraDataDef.DATE_SCANNED + " TEXT DEFAULT NULL " +
            ");";

   private static  final String ULTRAUSER_QUERY = "CREATE TABLE " +
            ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + "(" +
            ConnSQLiteContract.UltraStaffDef._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.UltraStaffDef.APPCODE + " TEXT NOT NULL, " +
            ConnSQLiteContract.UltraStaffDef.STAFF_ID + " TEXT NOT NULL, " +
            ConnSQLiteContract.UltraStaffDef.USERTYPE + " TEXT NOT NULL, " +
           //update version 5
           ConnSQLiteContract.UltraStaffDef.SCHOOLID + " nvarchar NOT NULL," +
           ConnSQLiteContract.UltraStaffDef.ORGANIZATIONID + " nvarchar NOT NULL," +
           ConnSQLiteContract.UltraStaffDef.PASSWORD + " nvarchar NOT NULL," +
           ConnSQLiteContract.UltraStaffDef.USERNAME + " nvarchar NOT NULL" +
            ");";
    private static final String ULTRA_LAT_LONG = "CREATE TABLE " +
            ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE + "(" +
            ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LONGITUDE + " FLOAT NOT NULL, " +
            ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LATITUDE + " FLOAT NOT  NULL " +
            ");";
    private static final String TRIP_LAT_LONG = "CREATE TABLE " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE + "(" +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LONGITUDE + " FLOAT NOT NULL, " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LATITUDE + " FLOAT NOT  NULL, " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.STAFF_ID + " INTEGER NOT  NULL, " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.DATE_RECORDED + " INTEGER NOT  NULL, " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.VEHICLE_ID + " INTEGER NOT  NULL, " +
            ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.APP_CODE + " INTEGER NOT  NULL " +
            ");";
    private static final String ULTRA_ALLSTUDENTS = "CREATE TABLE " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + "(" +//this table changed from having these columns to having more columns PortalAllTransportStudents
            ConnSQLiteContract.PortalAllTransportStudentsListing.STUDENTFULLNAME + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.REGISTRATIONNUMBER + " TEXT NOT  NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.CLASSSTREAM + " TEXT NOT  NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.SCHOOLNAME + " TEXT NOT  NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.BARCODE + " TEXT PRIMARY KEY NOT NULL, " +

            //added in version 4 from 3
            ConnSQLiteContract.PortalAllTransportStudentsListing.ROUTE_NAME + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.BUS + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.TERM_NAME + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.YEAR_ID + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.MORNING_PICKED + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.EVENING_PICKED + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.FATHER_PHONE + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAllTransportStudentsListing.MOTHER_PHONE + " TEXT NOT NULL " +
            ");";

    //update to version 5
    private static final String SaveVehicleServicing = "CREATE TABLE " +
            ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE + "(" +
            ConnSQLiteContract.PortalSaveVehicleServicing._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.VEHICLE_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.SERVICEDBY + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.SERVICETYPE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.DATESERVICED + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.MILEAGEBEFORE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.SERVICEREPORT + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.NEXTSERVICEMILEAGE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleServicing.SERVICECOST + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalSaveVehicleServicing.ADDEDBY + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalSaveVehicleServicing.APPCODE + " nvarchar NOT NULL" +
            ");";
    private static final String SaveVehicleFueling = "CREATE TABLE " +
            ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE + "(" +
            ConnSQLiteContract.PortalSaveVehicleFueling._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.VEHICLE_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.FUELEDBY + " nvarchar NOT NULL, " +
//            ConnSQLiteContract.PortalSaveVehicleFueling.FUELTYPE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.DATEFUELED + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.MILEAGEBEFORE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.QUANTITY + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.UNITPRICE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.ADDEDBY + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicleFueling.APPCODE + " nvarchar NOT NULL" +
            ");";

    private static final String SaveDisciplineCase = "CREATE TABLE " +
            ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE + "(" +
            ConnSQLiteContract.PortalSaveDisciplineCase._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalSaveDisciplineCase.STUDENT_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveDisciplineCase.OFFENCE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveDisciplineCase.PENAULTY + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveDisciplineCase.REPORTEDBY + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveDisciplineCase.APPCODE + " nvarchar NOT NULL" +
            ");";

    private static final String SaveMarkRegister = "CREATE TABLE " +
            ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE + "(" +
            ConnSQLiteContract.PortalMarkRegister._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalMarkRegister.STUDENT_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.STUDENTFULLNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.STATUS + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.DATERECORDED + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.STAFF_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.REMARKS + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.APPCODE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalMarkRegister.STATUSSENT + "  boolean default 0 " +
            ");";


    private static final String SaveSchools = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE + "(" +
            ConnSQLiteContract.PortalGetSchools._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetSchools.SCHOOL_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetSchools.SCHOOLNAME + " nvarchar NOT NULL " +
            ");";

    private static final String SaveDutyRoster = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE + "(" +
            ConnSQLiteContract.PortalGetDutyRoster._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetDutyRoster.STAFFNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetDutyRoster.YEAR + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetDutyRoster.TERMNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetDutyRoster.DUTYWEEK + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetDutyRoster.CURRENTWEEK + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetDutyRoster.PHONENUMBER + " nvarchar NOT NULL " +
            ");";

    private static final String SaveStudents = "CREATE TABLE " +
            ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE + "(" +
            ConnSQLiteContract.PortalSaveAllStudents._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalSaveAllStudents.STUDENTID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.REGISTRATIONNUMBER + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.SCHOOLNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.SCHOOLID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.ORGANIZATIONID + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalSaveAllStudents.CURRENTTERM + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveAllStudents.CURRENTYEAR + " nvarchar NOT NULL " +

            ");";


    private static final String SaveVehicle = "CREATE TABLE " +
            ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE + "(" +
            ConnSQLiteContract.PortalSaveVehicle._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalSaveVehicle.VEHICLEID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicle.NUMBERPLATE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalSaveVehicle.LASTMILEAGE + " nvarchar NOT NULL " +
            ");";


    private static final String SaveRollCallSession = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE + "(" +
            ConnSQLiteContract.PortalGetRollCallSessions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetRollCallSessions.SESSIONID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetRollCallSessions.SESSIONNAME + " nvarchar NOT NULL " +
            ");";

    private static final String SaveGetAllStaff = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE + "(" +
            ConnSQLiteContract.PortalGetAllStaff._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetAllStaff.STAFFID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.LASTNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.TITLENAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.RANKNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.PHONENUMBER + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.SCHOOLID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.ORGANIZATIONID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.DEPARTMENTNAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetAllStaff.SCHOOLNAME + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalGetAllStaff.EMAIL + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalGetAllStaff.BARCODE + " nvarchar DEFAULT NULL" +
            ");";


    private static final String SaveLeavetType = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE + "(" +
            ConnSQLiteContract.PortalGetLeaveType._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetLeaveType.LEAVETYPEID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetLeaveType.LEAVETYPENAME + " nvarchar NOT NULL " +
            ");";

    private static final String SaveExamHistory = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE + "(" +
            ConnSQLiteContract.PortalGetExamHistory._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetExamHistory.SUBJECT + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.SCORE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.GRADE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.REMARK + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.TOTALMARKS + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.MEANSCORE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.MEANGRADE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.STREAMPOSITION + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.OVERALLPOSITION + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalGetExamHistory.PERIOD + " nvarchar DEFAULT NULL, " +
            ConnSQLiteContract.PortalGetExamHistory.TERMNAME + " nvarchar NOT NULL," +
            ConnSQLiteContract.PortalGetExamHistory.CURRENTYEAR + " nvarchar DEFAULT NULL" +
            ");";

    private static final String GetDisciplinaryCases = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE + "(" +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.OFFENCE_DESCRIPTION + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PENAULTY_DESCRIPTION + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.REPORTEDBY + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.DATE_COMMITED + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.TERM_FOR + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.YEARID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentDisciplinaryCases.STUDENTNAME + " nvarchar NOT NULL " +
            ");";

    private static final String GetStudentAttendance = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE + "(" +
            ConnSQLiteContract.PortalGetStudentAttendance._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetStudentAttendance.STUDENT_NAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.STATUS + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.COURSENAME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.REMARKS + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.DATE_RECORDED + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.TERM_FOR + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.YEAR_FOR + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetStudentAttendance.UNIT_NAME + " nvarchar NOT NULL " +
            ");";

    private static final String GetClassStream = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE + "(" +
            ConnSQLiteContract.PortalGetClassStreams._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetClassStreams.COURSE_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetClassStreams.COURSE_CODE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetClassStreams.LEVEL_ID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetClassStreams.LEVEL_NAME + " nvarchar NOT NULL " +
            ");";


    private static final String GetStaffOperation = "CREATE TABLE " +
            ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE + "(" +
            ConnSQLiteContract.PortalStaffOperations._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalStaffOperations.STAFFID + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalStaffOperations.STAFF_OPERATION + " nvarchar NOT NULL " +
            ");";

    private static final String GetMziziAppSupportHelp = "CREATE TABLE " +
            ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE + "(" +
            ConnSQLiteContract.PortalGetMziziAppSupportHelp._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalGetMziziAppSupportHelp.ID + " INTEGER NOT NULL, " +
            ConnSQLiteContract.PortalGetMziziAppSupportHelp.HELP_DESCRIPTION + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalGetMziziAppSupportHelp.OPERATION_NAME + " nvarchar NOT NULL " +
            ");";

    private static final String GetAssetTracking = "CREATE TABLE " +
            ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE + "(" +
            ConnSQLiteContract.PortalAssetTracking._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalAssetTracking.BARCODE_NUMBER + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalAssetTracking.ASSET_TYPE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalAssetTracking.LATITUDE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalAssetTracking.LONGITUDE + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalAssetTracking.STAFFID  + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalAssetTracking.SCANDATETIME + " nvarchar NOT NULL, " +
            ConnSQLiteContract.PortalAssetTracking.COMMENT + " nvarchar NOT NULL " +
            ");";

    private static final String GetTransportSessions = "CREATE TABLE " +
            ConnSQLiteContract.PortalTransportSessions.PORTAL_TRANSPORT_SESSIONS_TABLE + "(" +
            ConnSQLiteContract.PortalTransportSessions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalTransportSessions.ID + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalTransportSessions.NAME + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalTransportSessions.CODE + " TEXT NOT NULL " +
            ");";

    private static final String GetTransportBusTrip = "CREATE TABLE " +
            ConnSQLiteContract.PortalTransportBusTrip.PORTAL_TRANSPORT_BUSTRIP_TABLE + "(" +
            ConnSQLiteContract.PortalTransportBusTrip._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalTransportBusTrip.ID + " TEXT DEFAULT NULL, " +
            ConnSQLiteContract.PortalTransportBusTrip.NAME + " TEXT DEFAULT NULL " +
            ");";

    private static final String GetAssetRegisterd = "CREATE TABLE " +
            ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE + "(" +
            ConnSQLiteContract.PortalAssetRegister._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ConnSQLiteContract.PortalAssetRegister.ID  + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAssetRegister.ASSETNAME + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAssetRegister.ASSETCODE + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAssetRegister.ASSETSERIALNUMBER + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAssetRegister.ASSETDESCRIPTION + " TEXT NOT NULL, " +
            ConnSQLiteContract.PortalAssetRegister.ASSETBARCODE + " TEXT NOT NULL " +
            ");";




    public DatabaseConnectionOpenHelper(Context context){
        super(context,DBULTRABUS_NAME,null,DBULTRABUS_VERSION);
        this.context = context;
    }




    private static DatabaseConnectionOpenHelper instance = null;

    public static DatabaseConnectionOpenHelper getInstance(final Context context){
        if(instance == null){
            synchronized (SQLiteOpenHelper.class){
                if(instance == null){
                    //be sure to call getApplicationContext() to avoid memory leak
                    instance = new DatabaseConnectionOpenHelper(context.getApplicationContext());
                }
            }
        }

        return instance;
    }

    public SQLiteDatabase getWriteableDatabaseConnection(){
        OpenHelperManager.requireConnection();
        return getWritableDatabase();
    }

    public SQLiteDatabase getReadableDatabaseConnection(){
        OpenHelperManager.requireConnection();
        return getReadableDatabase();
    }

    public void closeDatabase(){
        OpenHelperManager.releaseHelper(this);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {//Those that download the app now, without any previous installs the app will have to create these tables

            //this will be created only when:
                /*
                * when the app is freshly installed
                * -this is for new users - for newly installed apps*/

                //this will always have the newVersion

//        db.execSQL(ULTRADATA_QUERY);
//        db.execSQL(ULTRAUSER_QUERY);
//        db.execSQL(ULTRA_LAT_LONG);
//        db.execSQL(ULTRA_ALLSTUDENTS);


        if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE))//RETURN true changed to false
            db.execSQL(ULTRADATA_QUERY);

        if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE))//return false change to true
            db.execSQL(ULTRAUSER_QUERY);

        if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
            db.execSQL(ULTRA_LAT_LONG);

        if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
            db.execSQL(ULTRA_ALLSTUDENTS);

        //UPDATE VERSION 5
        if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
            db.execSQL(SaveVehicleServicing);
        if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
            db.execSQL(SaveVehicleFueling);
        if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
            db.execSQL(SaveDisciplineCase);
        if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
            db.execSQL(SaveDutyRoster);
        if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
            db.execSQL(SaveMarkRegister);
        if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
            db.execSQL(SaveSchools);
        if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
            db.execSQL(SaveStudents);
        if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
            db.execSQL(SaveVehicle);
        if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
            db.execSQL(SaveRollCallSession);
        if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
            db.execSQL(SaveGetAllStaff);
        if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
            db.execSQL(SaveLeavetType);
        if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
            db.execSQL(SaveExamHistory);
        if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
            db.execSQL(GetDisciplinaryCases);
        if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
            db.execSQL(GetStudentAttendance);
        if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
            db.execSQL(GetClassStream);
        if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
            db.execSQL(GetStaffOperation);
        if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
            db.execSQL(TRIP_LAT_LONG);
        if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
            db.execSQL(GetMziziAppSupportHelp);
        if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
            db.execSQL(GetAssetTracking);
        if(!tableExists(db, ConnSQLiteContract.PortalTransportBusTrip.PORTAL_TRANSPORT_BUSTRIP_TABLE))
            db.execSQL(GetTransportBusTrip);
        if(!tableExists(db, ConnSQLiteContract.PortalTransportSessions.PORTAL_TRANSPORT_SESSIONS_TABLE))
            db.execSQL(GetTransportSessions);

        if(!tableExists(db, ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE))
            db.execSQL(GetAssetRegisterd);

    }

        //if the database version is changed,or there is presence of changes to the database without considering those changes the app will crash, because the schema of the db changed.
        //those who have previous installs of the app, and are now updating the app, we have to consider the changes made, and have to support the db we have in their app. meaning update to the present schema changes made.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //REMEMBER THE PREVIOUS DBS DONT HAVE THE UPDATES, SO YOU HAVE TO MAKE THE CHANGES.

            //i dont want to drop the table, i want the data to still remain
                //you are now supporting the previous database you had

        /*this method is never called, when you increase the app version
        *   -called when you have successive app upgrades, several of which have database upgrades, you have to check oldVersion
        *   -when the user upgrades from version 1 to version 3, they will get all updates from version 2 to 3. this is to make sure even those that skip database version updates*/



       // Toast.makeText(context, "OnUpgrade is called in newer version", Toast.LENGTH_LONG).show();


        switch (oldVersion){
            case 1:

              //  String changeTableColumn = "ALTER TABLE " + ConnSQLiteContract.PORTAL_ALL_TRANSPORT_STUDENTS.PORTALALLSTUDENTSLISTING_TABLE + " ADD COLUMN " + ""

                //i really have to drop this tables because the user wont have the privous tables
                //tables
//                ULTRADATA_TABLE;
//                ULTRAUSER_TABLE;
//                UPDATED_LATITUDE_AND_LONGITUDE;
//                PORTALALLSTUDENTSLISTING_TABLE;
                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }
                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE))//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);

                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
                     db.execSQL(ULTRA_LAT_LONG);

                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
                    db.execSQL(ULTRA_ALLSTUDENTS);

                //UPDATE VERSION 5
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
                    db.execSQL(SaveVehicleServicing);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
                    db.execSQL(SaveVehicleFueling);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
                    db.execSQL(SaveDisciplineCase);
                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
                    db.execSQL(SaveDutyRoster);
                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
                    db.execSQL(SaveMarkRegister);
                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
                    db.execSQL(SaveSchools);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
                    db.execSQL(SaveStudents);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
                    db.execSQL(SaveVehicle);
                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
                    db.execSQL(SaveRollCallSession);
                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
                    db.execSQL(SaveGetAllStaff);
                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
                    db.execSQL(SaveLeavetType);
                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
                    db.execSQL(SaveExamHistory);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
                    db.execSQL(GetDisciplinaryCases);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
                    db.execSQL(GetStudentAttendance);
                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
                    db.execSQL(GetClassStream);
                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
                    db.execSQL(GetStaffOperation);
                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
                    db.execSQL(TRIP_LAT_LONG);
                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
                db.execSQL(GetMziziAppSupportHelp);
                if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
                    db.execSQL(GetAssetTracking);






            case 2:

                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE))//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);

                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE))//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);

                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
                    db.execSQL(ULTRA_LAT_LONG);

                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
                    db.execSQL(ULTRA_ALLSTUDENTS);



                //UPDATE VERSION 5
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
                    db.execSQL(SaveVehicleServicing);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
                    db.execSQL(SaveVehicleFueling);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
                    db.execSQL(SaveDisciplineCase);
                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
                    db.execSQL(SaveDutyRoster);
                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
                    db.execSQL(SaveMarkRegister);
                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
                    db.execSQL(SaveSchools);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
                    db.execSQL(SaveStudents);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
                    db.execSQL(SaveVehicle);
                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
                    db.execSQL(SaveRollCallSession);
                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
                    db.execSQL(SaveGetAllStaff);
                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
                    db.execSQL(SaveLeavetType);
                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
                    db.execSQL(SaveExamHistory);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
                    db.execSQL(GetDisciplinaryCases);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
                    db.execSQL(GetStudentAttendance);
                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
                    db.execSQL(GetClassStream);
                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
                    db.execSQL(GetStaffOperation);
                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
                    db.execSQL(TRIP_LAT_LONG);
                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
                    db.execSQL(GetMziziAppSupportHelp);
                if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
                    db.execSQL(GetAssetTracking);


            case 3:
                //here we support the database version 3



                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }else{

                   // db.execSQL(ULTRADATA_QUERY);

                        //this will support users of the previous database versions
                    String alter_AddSessionColumn = "ALTER TABLE "+ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE +" ADD  session TEXT  DEFAULT NULL;";
                   // Toast.makeText(context, "Alter", Toast.LENGTH_LONG).show();
                    db.execSQL(alter_AddSessionColumn);
                }
                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE))//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);

                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
                    db.execSQL(ULTRA_LAT_LONG);

                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE)) {//if this table exist, add the new columns, if it does not create the whole table
                    db.execSQL(ULTRA_ALLSTUDENTS);
                }else{//this means the table does exist, but i have to update to the state of the present databsae

//                    //remember that you are changing the previous table of database version 3
//
//                    //this means if this table will have the data from the previous, data i can delete all the data then, ask the user to refresh the data
//

                        // WAS JUST FOR TESTING
//                   String drop_Table = "DROP TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE;
//
//                    db.execSQL(drop_Table);
//                    db.execSQL(ULTRA_ALLSTUDENTS);


                    String query_ADD_ROUTENAME = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.ROUTE_NAME + " TEXT ";
                    String query_ADD_BUS = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.BUS + " TEXT ";
                    String query_ADD_TERMNAME= "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.TERM_NAME + " TEXT ";
                    String query_ADD_YEARID = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.YEAR_ID + " TEXT ";
                    String query_ADD_MORNINGPICKED = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.MORNING_PICKED + " TEXT ";
                    String query_ADD_EVENINGPICKED = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.EVENING_PICKED + " TEXT ";


                    db.execSQL(query_ADD_ROUTENAME);
                    db.execSQL(query_ADD_BUS);
                    db.execSQL(query_ADD_TERMNAME);
                    db.execSQL(query_ADD_YEARID);
                    db.execSQL(query_ADD_MORNINGPICKED);
                    db.execSQL(query_ADD_EVENINGPICKED);


                }

                //UPDATE VERSION 5
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
                    db.execSQL(SaveVehicleServicing);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
                    db.execSQL(SaveVehicleFueling);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
                    db.execSQL(SaveDisciplineCase);
                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
                    db.execSQL(SaveDutyRoster);
                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
                    db.execSQL(SaveMarkRegister);
                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
                    db.execSQL(SaveSchools);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
                    db.execSQL(SaveStudents);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
                    db.execSQL(SaveVehicle);
                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
                    db.execSQL(SaveRollCallSession);
                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
                    db.execSQL(SaveGetAllStaff);
                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
                    db.execSQL(SaveLeavetType);
                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
                    db.execSQL(SaveExamHistory);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
                    db.execSQL(GetDisciplinaryCases);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
                    db.execSQL(GetStudentAttendance);
                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
                    db.execSQL(GetClassStream);
                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
                    db.execSQL(GetStaffOperation);
                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
                    db.execSQL(TRIP_LAT_LONG);
                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
                    db.execSQL(GetMziziAppSupportHelp);
                if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
                    db.execSQL(GetAssetTracking);




            case 4:

                //changes to the schema of the database should now be updated here

                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }


                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE)) {//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);
                }else{//the table does exist, am just making update version 5 database schema

                    String query_UPDATE_ULTRAUSERTABLE_SCHOOLID = "ALTER TABLE " + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + " ADD " + ConnSQLiteContract.UltraStaffDef.SCHOOLID + " nvarchar ";
                    String query_UPDATE_ULTRATUSERTABLE_ORGANIZATIONID = "ALTER TABLE "  + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + " ADD " + ConnSQLiteContract.UltraStaffDef.ORGANIZATIONID + " nvarchar ";

                    db.execSQL(query_UPDATE_ULTRAUSERTABLE_SCHOOLID);
                    db.execSQL(query_UPDATE_ULTRATUSERTABLE_ORGANIZATIONID);



                }

                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
                    db.execSQL(ULTRA_LAT_LONG);

                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
                    db.execSQL(ULTRA_ALLSTUDENTS);

                //UPDATE VERSION 5
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
                    db.execSQL(SaveVehicleServicing);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
                    db.execSQL(SaveVehicleFueling);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
                    db.execSQL(SaveDisciplineCase);
                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
                    db.execSQL(SaveDutyRoster);
                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
                    db.execSQL(SaveMarkRegister);
                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
                    db.execSQL(SaveSchools);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
                    db.execSQL(SaveStudents);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
                    db.execSQL(SaveVehicle);
                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
                    db.execSQL(SaveRollCallSession);
                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
                    db.execSQL(SaveGetAllStaff);
                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
                    db.execSQL(SaveLeavetType);
                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
                    db.execSQL(SaveExamHistory);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
                    db.execSQL(GetDisciplinaryCases);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
                    db.execSQL(GetStudentAttendance);
                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
                    db.execSQL(GetClassStream);
                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
                    db.execSQL(GetStaffOperation);
                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
                    db.execSQL(TRIP_LAT_LONG);
                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
                    db.execSQL(GetMziziAppSupportHelp);
                if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
                    db.execSQL(GetAssetTracking);


            case 5://update to 6, --14/5/2019
                //changes to the schema of the database should now be updated here

                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }


                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE)) {//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);
                }

                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
                    db.execSQL(ULTRA_LAT_LONG);

                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
                    db.execSQL(ULTRA_ALLSTUDENTS);
                else
                {
                    String query_ADD_FATHER_PHONE = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.FATHER_PHONE + " TEXT ";
                    String query_ADD_MOTHER_PHONE = "ALTER TABLE " + ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE + " ADD  " + ConnSQLiteContract.PortalAllTransportStudentsListing.MOTHER_PHONE + " TEXT ";

                    db.execSQL(query_ADD_FATHER_PHONE);
                    db.execSQL(query_ADD_MOTHER_PHONE);
                }

                //UPDATE VERSION 5
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
                    db.execSQL(SaveVehicleServicing);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
                    db.execSQL(SaveVehicleFueling);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
                    db.execSQL(SaveDisciplineCase);
                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
                    db.execSQL(SaveDutyRoster);
                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
                    db.execSQL(SaveMarkRegister);
                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
                    db.execSQL(SaveSchools);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
                    db.execSQL(SaveStudents);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
                    db.execSQL(SaveVehicle);
                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
                    db.execSQL(SaveRollCallSession);
                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
                    db.execSQL(SaveGetAllStaff);
                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
                    db.execSQL(SaveLeavetType);
                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
                    db.execSQL(SaveExamHistory);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
                    db.execSQL(GetDisciplinaryCases);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
                    db.execSQL(GetStudentAttendance);
                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
                    db.execSQL(GetClassStream);
                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
                    db.execSQL(GetStaffOperation);
                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
                    db.execSQL(TRIP_LAT_LONG);
                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
                    db.execSQL(GetMziziAppSupportHelp);
                if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
                    db.execSQL(GetAssetTracking);


            case 6://update to 7, -- 13/1/2021
                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }


                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE)) {//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);
                }

                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
                    db.execSQL(ULTRA_LAT_LONG);

                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
                    db.execSQL(ULTRA_ALLSTUDENTS);

                //UPDATE VERSION 5
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
                    db.execSQL(SaveVehicleServicing);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
                    db.execSQL(SaveVehicleFueling);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
                    db.execSQL(SaveDisciplineCase);
                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
                    db.execSQL(SaveDutyRoster);
                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
                    db.execSQL(SaveMarkRegister);
                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
                    db.execSQL(SaveSchools);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
                    db.execSQL(SaveStudents);
                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
                    db.execSQL(SaveVehicle);
                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
                    db.execSQL(SaveRollCallSession);
                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
                    db.execSQL(SaveGetAllStaff);
                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
                    db.execSQL(SaveLeavetType);
                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
                    db.execSQL(SaveExamHistory);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
                    db.execSQL(GetDisciplinaryCases);
                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
                    db.execSQL(GetStudentAttendance);
                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
                    db.execSQL(GetClassStream);
                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
                    db.execSQL(GetStaffOperation);
                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
                    db.execSQL(TRIP_LAT_LONG);
                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
                    db.execSQL(GetMziziAppSupportHelp);
                if(!tableExists(db, ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE))
                    db.execSQL(GetAssetTracking);


            case 7://update to 8, -- 21/1/2021
                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }else{
                    db.execSQL("ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.BUSTRIP + " TEXT DEFAULT NULL");
                    db.execSQL("ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.STATUS_SENT + " TEXT DEFAULT NULL");
                }


            case 8:

                if(!tableExists(db, ConnSQLiteContract.PortalTransportSessions.PORTAL_TRANSPORT_SESSIONS_TABLE)) {//RETURN true changed to false
                    db.execSQL(GetTransportSessions);
                }

                if(!tableExists(db, ConnSQLiteContract.PortalTransportBusTrip.PORTAL_TRANSPORT_BUSTRIP_TABLE)) {//RETURN true changed to false
                    db.execSQL(GetTransportBusTrip);
                }
            case 9:
                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
                    db.execSQL(ULTRADATA_QUERY);
                }else{
                    db.execSQL("ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.DATE_SCANNED + " TEXT DEFAULT NULL");
                }

                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE)) {//return false change to true
                    db.execSQL(ULTRAUSER_QUERY);
                }else{//the table does exist, am just making update version 5 database schema

                    String query_UPDATE_ULTRAUSERTABLE_USERNAME = "ALTER TABLE " + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + " ADD " + ConnSQLiteContract.UltraStaffDef.USERNAME + " nvarchar DEFAULT NULL ";
                    String query_UPDATE_ULTRATUSERTABLE_PASSWORD = "ALTER TABLE "  + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + " ADD " + ConnSQLiteContract.UltraStaffDef.PASSWORD + " nvarchar DEFAULT NULL ";

                    db.execSQL(query_UPDATE_ULTRAUSERTABLE_USERNAME);
                    db.execSQL(query_UPDATE_ULTRATUSERTABLE_PASSWORD);
                }
            case 10:
                if(!tableExists(db, ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE))
                    db.execSQL(GetAssetRegisterd);


//                //have not reached there
//                //added vehicle plate to ultrata data
//                //these are the updates
//                if(!tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)) {//RETURN true changed to false
//                    db.execSQL(ULTRADATA_QUERY);
//                }else{
//                    String query_UPDATE_ULTRATUDATA_VEHICLEPLATE = "ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE + " nvarchar ";
//                    db.execSQL(query_UPDATE_ULTRATUDATA_VEHICLEPLATE);
//                }
//
//
//                if(!tableExists(db, ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE)) {//return false change to true
//                    db.execSQL(ULTRAUSER_QUERY);
//                }else{//the table does exist, am just making update version 5 database schema
//
//                    String query_UPDATE_ULTRAUSERTABLE_SCHOOLID = "ALTER TABLE " + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + " ADD " + ConnSQLiteContract.UltraStaffDef.SCHOOLID + " nvarchar ";
//                    String query_UPDATE_ULTRATUSERTABLE_ORGANIZATIONID = "ALTER TABLE "  + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE + " ADD " + ConnSQLiteContract.UltraStaffDef.ORGANIZATIONID + " nvarchar ";
//
//                    db.execSQL(query_UPDATE_ULTRAUSERTABLE_SCHOOLID);
//                    db.execSQL(query_UPDATE_ULTRATUSERTABLE_ORGANIZATIONID);
//
//                }
//
//                if(!tableExists(db, ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE))
//                    db.execSQL(ULTRA_LAT_LONG);
//
//                if(!tableExists(db, ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE))
//                    db.execSQL(ULTRA_ALLSTUDENTS);
//
//                //UPDATE VERSION 5
//                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE))
//                    db.execSQL(SaveVehicleServicing);
//                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE))
//                    db.execSQL(SaveVehicleFueling);
//                if(!tableExists(db, ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE))
//                    db.execSQL(SaveDisciplineCase);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE))
//                    db.execSQL(SaveDutyRoster);
//                if(!tableExists(db, ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE))
//                    db.execSQL(SaveMarkRegister);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE))
//                    db.execSQL(SaveSchools);
//                if(!tableExists(db, ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE))
//                    db.execSQL(SaveStudents);
//                if(!tableExists(db, ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE))
//                    db.execSQL(SaveVehicle);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE))
//                    db.execSQL(SaveRollCallSession);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE))
//                    db.execSQL(SaveGetAllStaff);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE))
//                    db.execSQL(SaveLeavetType);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE))
//                    db.execSQL(SaveExamHistory);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE))
//                    db.execSQL(GetDisciplinaryCases);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetStudentAttendance.PORTALGETSTUDENTATTENDANCE_TABLE))
//                    db.execSQL(GetStudentAttendance);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE))
//                    db.execSQL(GetClassStream);
//                if(!tableExists(db, ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE))
//                    db.execSQL(GetStaffOperation);
//                if(!tableExists(db, ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE))
//                    db.execSQL(TRIP_LAT_LONG);
//                if(!tableExists(db, ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE))
//                    db.execSQL(GetMziziAppSupportHelp);



                //current version for now of the database

                //NO BREAKS;

                if (db != null && !db.isOpen())
                    db.close();

        }

            //dont do this coz it will coz problems
//       db.execSQL("DROP TABLE IF EXISTS " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE);
//        db.execSQL("DROP TABLE IF EXISTS " + ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE);

    }



    private boolean tableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }










}
