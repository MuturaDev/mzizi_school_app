package ultratude.com.staff.webservice.RetrofitOkHTTP;

/**
 * Created by James on 15/10/2018.
 */

public class UrlEndpoints {

        //REMEMBER YOU HAVE TO UNCOMMENT THIS
  //public static final String BASE_URL = "https://android.mzizi.co.ke";
 // public static final String BASE_URL = "https://taapi.mzizi.co.ke";


  //FIXME: REMOVE THIS AFTER TESTING
   public static final String BASE_URL = "https://androidv2.mzizi.co.ke";//"https://75d1cbb01032.ngrok.io";//

   public static final String ULTRABUS_ENDPOINT = BASE_URL +"/api/Ultrabus";


   // public static final String PORTAL_ALL_TRANSPORT_STUDENTS = BASE_URL +"/api/PORTAL_ALL_TRANSPORT_STUDENTS";
   public static final String PORTAL_ALL_TRANSPORT_STUDENTS = BASE_URL +"/api/PortalAllTransportStudents";

    //GET DATA
    public static final String PORTAL_GETALLSTAFF = BASE_URL + "/api/PortalRptGetAllStaff";
    public static final String PORTAL_GETSCHOOLS = BASE_URL + "/api/PortalGetSchools";
    public static final String PORTAL_GETDUTYROSTER = BASE_URL + "/api/PortalGetDutyRoster";
    public static final String PORTAL_GETVEHICLEPLATES = BASE_URL + "/api/PortalGetVehiclePlates";
    public static final String PORTAL_ALLSTUDENTSLISTING = BASE_URL + "/api/PortalAllStudentsListing";
    public static final String PORTAL_STUDENTDISCIPLINARYCASES = BASE_URL + "/api/PortalStudentDisciplinaryCases";
    public static final String PORTAL_STUDENTATTENDANCE = BASE_URL + "/api/PortalStudentAttendance";
    public static final String PORTALSTUDENTINFO_URL = BASE_URL +"/api/PortalStudentInfo";
    public static final String PORTAL_ROLLCALLSESSIONS = BASE_URL + "/api/PortalRollCallSession";
    public static final String PORTAL_CLASSSTREAMTEACHERSTAFFALLOCATIONS = BASE_URL;// = BASE_URL + "/api/PortalClassStreamTeacherStaffAllocations";

    public static final String PORTAL_CUSTOM_FILTERS_FOR_MARKREGISTER = BASE_URL + "/api/PortalClassStreamTeacherStaffAllocations";


    public static final String PORTAL_GETLEAVETYPES = BASE_URL + "/api/PortalGetLeaveTypes";
    public static final String PORTAL_GETSTAFFOPERATION = BASE_URL + "/api/PortalStaffOperations";
    public static final String PORTAL_GETCHILDINFO = BASE_URL + "/api/PortalGetStaffLeaveBalances";
    public static final String PORTAL_GETMZIZIAPPSUPPORTHELPCONTROLLER = BASE_URL + "/api/PortalGetMziziAppSupportHelp";



        //SAVE DATA
    public static final String PORTAL_MARKREGISTER = BASE_URL + "/api/PortalMarkRegister";
    public static final String PORTAL_SAVEDISCIPLINE  = BASE_URL + "/api/PortalSaveDisciplineCase";
    public static final String PORTAL_SAVEVEHICLEFUELING = BASE_URL + "/api/PortalSaveVehicleFueling";
    public static final String PORTAL_SAVEVEHICLESERVICING = BASE_URL + "/api/PortalSaveVehicleServicing";
    public static final String PORTAL_SAVETRIPLATLONG = BASE_URL + "/api/PortalVehicleTracking";
    public static final String ASSET_ITEMS_ENDPOINT =  BASE_URL +
            "/api/AssetTrackingManager";


    //This is for, student enquiry
    public static final String PORTAL_EXAM_RESULT = BASE_URL + "/api/PortalStudentDetailedResults";
    //public static final String PORTAL_EXAM_RESULTS = BASE_URL+"/api/PortalStudentDetailedResults";

    public static final String PORTAL_SAVE_LEAVE_APPLICATION = BASE_URL + "/api/PortalSaveLeaveApplication";


    public static final String PORTAL_MZIZIAPP_VERSION_CONTROL = BASE_URL + "/api/MziziAppVersion";

    public static final String PORTAL_TRANSPORT_SESSIONS = BASE_URL + "/api/PortalTransportSessionsAndBusTrips";

     public static final String PORTAL_ASSET_REGISTER = BASE_URL + "/api/PortalAssetRegister";

     public static final String PORTAL_STUDENTLIST_FOR_MARKATTENDANCE = BASE_URL + "/api/PortalStudentListForMarkAttendance";

 public static final String PORTAL_GLOBALSETTINGS = BASE_URL + "/api/PortalGetGlobalSettings";



}
