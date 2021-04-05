package ultratude.com.mzizi.retrofitokhttp;



import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import ultratude.com.mzizi.modelclasses.PortalOptionalFeeRequest;
import ultratude.com.mzizi.modelclasses.StudentVisualization;
import ultratude.com.mzizi.modelclasses.TransportRoutesRequest;
import ultratude.com.mzizi.modelclasses.request.BookDataRequest;
import ultratude.com.mzizi.modelclasses.request.StudentChangePasswordRequest;
import ultratude.com.mzizi.modelclasses.response.BookDataResponse;
import ultratude.com.mzizi.modelclasses.response.BorrowedBooksResponse;
import ultratude.com.mzizi.modelclasses.response.OrderItemResponse;
import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.modelclasses.GlobalSettingsRequest;
import ultratude.com.mzizi.modelclasses.MessageRequest;
import ultratude.com.mzizi.modelclasses.NewCarriculumExam;
import ultratude.com.mzizi.modelclasses.NotificationReadTracking;
import ultratude.com.mzizi.modelclasses.ParentChatRequest;
import ultratude.com.mzizi.modelclasses.Student;
import ultratude.com.mzizi.modelclasses.UserCredentials;
import ultratude.com.mzizi.retrofitokhttp.Pojo.FilteredStudentInfoResults;
import ultratude.com.mzizi.retrofitokhttp.Pojo.SyncMyAccountResult;
import ultratude.com.mzizi.retrofitokhttp.Pojo.Test;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentUnits;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationAverageResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;
import ultratude.com.mzizi.uploadfiles.FileUploadPayload;
import ultratude.com.mzizi.webservice.mziziapiconstantsurl.MziziURLConstants;
import ultratude.com.staff.webservice.ResponseModels.AllTransportStudentsList;

import ultratude.com.staff.webservice.RequestModels.PortalGetAttendanceRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffAllStudentsRequest;

/**
 * Created by James on 22/09/2018.
 */


//the POJO class are wrapped into a typed Retrofit Call Class.
//method parameters:
/*
 * @Body() -send java object as request body
 * @Url() -use dynamic URLs.
 * @Query() - we can simply add a method parameter using @Query() and a query parameter name, describing the type. To URL encode a query use the form.
 *           @Query(value = "auth_token",encoded = true) String auth_token
 * @Field() - send data as form-urlencoded.This requires a @FormUrlEncoded annotation attached with the method.
 *       -works only with post
 *       -NOTE:  requires mandatory paramter. In cases when @Field is optional, we can use @Query instead and pass a null value.*/


public interface APIInterface {

     @POST(MziziURLConstants.AUTHENTICATION_URL)
     Call<AuthenticateResponse> getAuthenticateResponse(@Body UserCredentials user);

   // @POST("/api/FilteredPortalStudentInfo")
    @POST(MziziURLConstants.SYNC_MYACCOUNT)
    Call<SyncMyAccountResult> getSyncMyAccount(@Body Student student);

    @POST(MziziURLConstants.NOTIFICAITON_URL)
    Call<List<ultratude.com.mzizi.notificationpg.NotificationModel.Notification>> getNotifications(@Body MessageRequest messageRequest);

    @POST(MziziURLConstants.FILTERED_STUDENTINFO)
    Call<FilteredStudentInfoResults> getFilteredStudentInfo(@Body Student student);

        //SHOULD BE CHANGED TO TransportStudents
    @POST(MziziURLConstants.PORTAL_ALL_STUDENT_LISTING)
    Call<List<AllTransportStudentsList>> getPortalAllStudentListing(@Body StaffAllStudentsRequest request);

    @POST(MziziURLConstants.PORTALSTUDENTINFO_URL)
    Call<PortalStudentInfo> getStudentInfo(@Body Student student);


    @POST(MziziURLConstants.PORTALSTUDENTDETAILEDRESULTS_URL)
    Call<List<PortalStudentDetailedResults>> getPortalStudentDetailes(@Body Student student);


    @POST(MziziURLConstants.PORTALSIBLINGS_URL)
    Call<List<PortalSiblings>> getPortalSiblings(@Body Student student);


    @POST(MziziURLConstants.PORTALEVENTS_URL)
    Call<List<PortalEvents>> getPortalEvents(@Body Student student);


    @POST(MziziURLConstants.PORTALDETAILEDTRANSACTION_URL)
    Call<List<PortalDetailedTransaction>> getPortalDetailedTransaction(@Body Student student);

    @POST(MziziURLConstants.PORTAL_CONTACTS_URL)
    Call<List<PortalContacts>> getPortalContacts(@Body Student student);
    @POST(MziziURLConstants.PORTAL_GETPROGRESSREPORT)
    Call<List<NewCarriculumExam>> getPortalProgressReport(@Body Student student);

    @POST(MziziURLConstants.PORTAL_NOTIFICATION_READTRACKING)
    Call<String> getNotificationReadTracking(@Body List<NotificationReadTracking> notificationReadTracking);

    @POST(MziziURLConstants.PARENT_PORTALSTUDENTATTENDANCE)
    Call<List<StudentClassAttendance>> getStudentClassAttendance(@Body PortalGetAttendanceRequest portalGetAttendanceRequest);

    @POST(MziziURLConstants.NETWORK_TESTING)
    Call<Boolean> testNetworkStability(@Body Test test);

    @POST(MziziURLConstants.PORTAL_GLOBALSETTINGS)
    Call<GlobalSettings> getGlobalSettings(@Body GlobalSettingsRequest globalSettingsRequest);

    @POST(MziziURLConstants.PORTAL_PARENTCHAT)
    Call<List<ParentChat>> sendParentChat(@Body ParentChatRequest parentChatRequest);

    @POST(MziziURLConstants.PORTAL_RECENT_TRANSACTION)
    Call<List<PortalRecentTransactionResponse>> getRecentTransaction(@Body Student student);

      @POST(MziziURLConstants.PORTAL_OPTIONAL_FEE)
      Call<List<PortalOptionalFeesResponse>> getOptionalFees(@Body Student student);


      @POST(MziziURLConstants.PORTAL_DETAILED_TODO_LIST)
      Call<List<PortalDetailedToDoListResponse>> getDetailedToDoList(@Body Student student);


      @POST(MziziURLConstants.PORTAL_STUDENT_RESULT_EXTENDED)
      Call<List<PortalStudentResultsExtended>> getStudentResultsExtended(@Body Student student);


      @POST(MziziURLConstants.PORTAL_STUDENT_VISUALIZATION_AVERAGE)
      Call<List<PortalStudentVisualizationAverageResponse>> getStudentVisualizationAverage(@Body Student student);

      @POST(MziziURLConstants.PORTAL_STUDENT_VISUALIZATION)
      Call<List<PortalStudentVisualizationResponse>> getStudentVisualization(@Body StudentVisualization student);

      @POST(MziziURLConstants.PORTAL_TODO_LIST)
      Call<List<PortalToDoListResponse>> getToDoList(@Body Student student);

      @POST(MziziURLConstants.PORTAL_TRANSPORT_ROUTES)
      Call<List<PortalTransportRoutesResponse>> getTransportRoutes(@Body Student student);
      @POST(MziziURLConstants.PORTAL_MANAGEMENT_OPTIONAL_FEE)
      Call<String> manageOptionalFee(@Body PortalOptionalFeeRequest request);

      @POST(MziziURLConstants.PORTAL_MANAGEMENT_TRANSPORT_ROUTES)
      Call<String> manageTransportRoutes(@Body TransportRoutesRequest request);

      @POST(MziziURLConstants.PORTAL_PORTAL_STUDENT_UNITS)
      Call<List<PortalStudentUnits>> getPortalStudentUnits(@Body Student request);

      @POST(MziziURLConstants.PORTAL_BORROWEDBOOKS)
        Call<List<BorrowedBooksResponse>> requestBorrowedBooksResponse(@Body Student request);

      @POST(MziziURLConstants.PORTAL_BOOKCENTRE)
        Call<List<BookDataResponse>> requestBookDataResponse(@Body BookDataRequest request);


      //@POST(MziziURLConstants.)

      @POST(MziziURLConstants.PORTAL_ORDERITEMS)
        Call<List<OrderItemResponse>> requestOrderitemsResponse(@Body Student student);

    //  @POST(MziziURLConstants.PORTAL_SCHOOLTRIPS)
    //    Call<List<SchoolTripResponse>> requestSchoolTripResponse(@Body Student request);

      @POST(MziziURLConstants.PORTAL_YOUTUBEVIDEOGALLERY)
        Call<List<YoutubeVideoGalleryResponse>> requestYoutubeVideoGalleryResponse(@Body Student request);


      @POST(MziziURLConstants.PORTAL_STUDENTCHANGEPASSWORD)
      Call<Boolean> requestStudentChangePassword(@Body StudentChangePasswordRequest request);


    //  @POST(MziziURLConstants.PORTAL_FILEUPLOAD)
    //    Call<Object> uploadFileToAspWebService(@Body FileUploadPayload request);
    //
    //  @Multipart
    //  @POST(MziziURLConstants.PORTAL_FILEUPLOAD)
    //  Call<Object> upload(@Part("photo") MultipartBody.Part photo, Callback<String> callback);
    //


        @POST("PortalWebServices/PortalAppFileUpload.asmx/FileUpload")
        @Multipart
        Call<ResponseBody> uploadFile(//@Part("title") RequestBody title,
                                      @Part MultipartBody.Part imageFile,
                                      @Part("fileUploadPayload") FileUploadPayload request
        );



        @POST(MziziURLConstants.PORTAL_DEVICEFCMTOKEN)
        Call<ResponseBody> requestDeviceFCMToken(@Body Map request);

    //    //to be removed
//        @POST(MziziURLConstants.PORTAL_DEVICEFCMTOKEN_SIMON)
//        Call<ResponseBody> requestDeviceFCMTokenSimon(@Body Map request);


        @POST(MziziURLConstants.PORTAL_TIME_TABLE)
        Call<List<TimeTableResponse>> requestTimeTable(@Body Map request);









}
