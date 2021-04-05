package ultratude.com.mzizi.webservice.mziziapiconstantsurl;

import io.paperdb.Paper;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.staff.webservice.RetrofitOkHTTP.UrlEndpoints;

/**
 * Created by James on 07/07/2018.
 */

public class MziziURLConstants {




    public static final String BASE_URL = UrlEndpoints.BASE_URL;//"https://androidv2.mzizi.co.ke";//this one is off

    public static final String PORTAL_ALL_STUDENT_LISTING = BASE_URL + "/api/PortalAllTransportStudentsListing";

    //remeber not to interchange this
    public static final String PORTALSTUDENTINFO_URL = BASE_URL +"/api/PortalStudentInfo";


    //https://b8070c9b.ngrok.io/api/UserCredentials
    public static final String FILTERED_STUDENTINFO = "/api/FilteredStudentInfo";
    public static final String SYNC_MYACCOUNT = "/api/SyncMyAccount";

    public static final String NOTIFICAITON_URL = BASE_URL +"/api/PortalNotifications";
    public static final String PORTALDETAILEDTRANSACTION_URL = BASE_URL +"/api/PortalDetailedTransaction";
    public static final String PORTALSTUDENTDETAILEDRESULTS_URL = BASE_URL +"/api/PortalStudentDetailedResults";


        //for testing
   // public static final String AUTHENTICATION_URL = "https://android.mzizi.co.ke/api/UserCredentials";
    public static final String AUTHENTICATION_URL = BASE_URL +"/api/UserCredentials";//APPCODE:03-14701  STAFF_ID:00014701 APPCODE:1000



    public static final String PORTALSIBLINGS_URL = BASE_URL +"/api/PortalSiblings";
    public static final String PORTALEVENTS_URL = BASE_URL +"/api/PortalEvents";


    public static final String PORTAL_CONTACTS_URL = BASE_URL +"/api/PortalContacts";


   public static final String PORTAL_GETPROGRESSREPORT = BASE_URL +  "/api/PortalGetProgressReport";


  public static final String PORTAL_NOTIFICATION_READTRACKING = BASE_URL + "/api/PortalNotificationsReadTracking";

  public static final String PARENT_PORTALSTUDENTATTENDANCE = BASE_URL + "/api/ParentPortalStudentAttendance";

  public static final String NETWORK_TESTING = BASE_URL + "/api/NetworkTesting";

    public static final String PORTAL_PARENTCHAT = BASE_URL + "/api/PortalParentChat";
    public static final String PORTAL_GLOBALSETTINGS = BASE_URL + "/api/PortalGetGlobalSettings";

    public static final String PORTAL_RECENT_TRANSACTION = BASE_URL + "/api/PortalRecentTransactions";

    public static final String PORTAL_DETAILED_TODO_LIST = BASE_URL + "/api/PortalDetailedToDoList";
    public static final String PORTAL_OPTIONAL_FEE = BASE_URL + "/api/PortalOptionalFees";
  public static final String PORTAL_STUDENT_RESULT_EXTENDED = BASE_URL + "/api/PortalStudentResults";
  public static final String PORTAL_STUDENT_VISUALIZATION_AVERAGE = BASE_URL + "/api/PortalStudentVisualizationAverage";
  public static final String PORTAL_STUDENT_VISUALIZATION = BASE_URL + "/api/PortalStudentVisualization";
  public static final String PORTAL_TODO_LIST = BASE_URL + "/api/PortalToDoList";
  public static final String PORTAL_TRANSPORT_ROUTES = BASE_URL + "/api/PortalGetTransportRoutes";

   public static final String PORTAL_MANAGEMENT_OPTIONAL_FEE = BASE_URL  + "/api/ManageOptionalFee";
   public static final String  PORTAL_MANAGEMENT_TRANSPORT_ROUTES = BASE_URL  + "/api/ManageRouteSelection";

   public static final String PORTAL_PORTAL_STUDENT_UNITS = BASE_URL + "/api/PortalStudentUnits";

   public static final String PORTAL_BORROWEDBOOKS = BASE_URL + "/api/PortalBorrowedBooks";

   public static final String PORTAL_BOOKCENTRE = BASE_URL + "/api/PortalBookCentre";

   public static final String PORTAL_ORDERITEMS = BASE_URL + "/api/PortalOrderItems";

  // public static final String PORTAL_SCHOOLTRIPS = BASE_URL + "/api/PortalOrderItems";

   public static final String PORTAL_YOUTUBEVIDEOGALLERY = BASE_URL + "/api/PortalYoutubeVideoGallery";

   public static final String PORTAL_STUDENTCHANGEPASSWORD = BASE_URL + "/api/PortalStudentChangePassword";



   public static final String PORTAL_DEVICEFCMTOKEN =  BASE_URL + "/api/PortalDeviceFCMToken";

   // public static final String PORTAL_DEVICEFCMTOKEN_SIMON = "/api/PortalDeviceFCMToken";

    public static final String PORTAL_TIME_TABLE = "/api/StudentTimeTable";




}
