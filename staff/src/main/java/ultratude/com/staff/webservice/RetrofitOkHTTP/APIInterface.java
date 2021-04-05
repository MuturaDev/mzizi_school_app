package ultratude.com.staff.webservice.RetrofitOkHTTP;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import ultratude.com.staff.expandablelist.ChildInfo;
import ultratude.com.staff.webservice.RequestModels.PortalClassStreamTeacherStaffAllocationRequest;
import ultratude.com.staff.webservice.RequestModels.PortalStudentListForMarkAttendanceRequest;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
import ultratude.com.staff.webservice.ResponseModels.AssetItem;
import ultratude.com.staff.webservice.ResponseModels.AssetRegisterResponse;
import ultratude.com.staff.webservice.ResponseModels.ClassStream;
import ultratude.com.staff.webservice.ResponseModels.DisciplineCase;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;
import ultratude.com.staff.webservice.ResponseModels.ExamHistory;
import ultratude.com.staff.webservice.ResponseModels.Help;
import ultratude.com.staff.webservice.ResponseModels.LeaveType;
import ultratude.com.staff.webservice.ResponseModels.PortalClassStreamTeacherStaffAllocationsResponse;
import ultratude.com.staff.webservice.ResponseModels.PortalStudentInfo;
import ultratude.com.staff.webservice.ResponseModels.RollCallSession;
import ultratude.com.staff.webservice.ResponseModels.StaffOperation;
import ultratude.com.staff.webservice.ResponseModels.StudentClassAttendance;
import ultratude.com.staff.webservice.ResponseModels.StudentDisciplinaryCase;
import ultratude.com.staff.webservice.ResponseModels.TripLatLong;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControlRequest;
import ultratude.com.staff.webservice.RequestModels.PortalDisciplinaryCasesRequest;
import ultratude.com.staff.webservice.RequestModels.PortalGetAttendanceRequest;
import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;
import ultratude.com.staff.webservice.ResponseModels.Schools;
import ultratude.com.staff.webservice.ResponseModels.StaffLeaveApplication;
import ultratude.com.staff.webservice.ResponseModels.Student;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;
import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
import ultratude.com.staff.webservice.ResponseModels.Vehicle;
import ultratude.com.staff.webservice.ResponseModels.VehicleFueling;
import ultratude.com.staff.webservice.ResponseModels.VehicleServicing;
import ultratude.com.staff.webservice.ResponseModels.PortalStudentListForMarkAttendanceResponse;
import ultratude.com.staff.webservice.RequestModels.PortalTransportSessionBusTripsResponse;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffAllStudentsRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.StaffRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.Models.UltradataRequest;

/**
 * Created by James on 22/09/2018.
 */





public interface APIInterface {

   // @POST("/api/Ultradata")
   @POST(UrlEndpoints.ULTRABUS_ENDPOINT)

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
    Call<String> sendUltraData(@Body List<UltradataRequest> data);

    @POST(UrlEndpoints.ASSET_ITEMS_ENDPOINT)
    Call<String> sendAssetItemsData(@Body List<AssetItem> data);

//    @POST(MziziURLConstants.FILTERED_STUDENTINFO)
//    Call<FilteredStudentInfoResults> getFilteredStudentInfo(@Body Student student);


    //GET DATA
    @POST(UrlEndpoints.PORTAL_ALL_TRANSPORT_STUDENTS)
    Call<List<TransportStudents>> getAllTransportStudents(@Body StaffAllStudentsRequest request);

    @POST(UrlEndpoints.PORTAL_GETSCHOOLS)
    Call<List<Schools>> getAllSchools(@Body StaffRequest staffRequest);

    @POST(UrlEndpoints.PORTAL_GETDUTYROSTER)
    Call<List<DutyRoster>> getDutyRosterList(@Body StaffRequest staffRequest);

    @POST(UrlEndpoints.PORTAL_GETVEHICLEPLATES)
    Call<List<Vehicle>> getAllVehicles(@Body StaffRequest staffRequest);

    @POST(UrlEndpoints.PORTAL_ALLSTUDENTSLISTING)
    Call<List<Student>> getAllStudents(@Body StaffRequest staffRequest);

   @POST(UrlEndpoints.PORTAL_EXAM_RESULT)
   Call<List<ExamHistory>> getExamResultsHistory(@Body StudentRequest studentRequest);

    @POST(UrlEndpoints.PORTAL_GETALLSTAFF)
    Call<List<AllStaff>> getAllStaff(@Body StaffRequest staffRequest);

   @POST(UrlEndpoints.PORTAL_STUDENTDISCIPLINARYCASES)
   Call<List<StudentDisciplinaryCase>> getStudentDisciplinaryCases(@Body PortalDisciplinaryCasesRequest portalDisciplinaryCasesRequest);

   @POST(UrlEndpoints.PORTAL_STUDENTATTENDANCE)
   Call<List<StudentClassAttendance>> getStudentClassAttendance(@Body PortalGetAttendanceRequest portalGetAttendanceRequest);

    @POST(UrlEndpoints.PORTALSTUDENTINFO_URL)
    Call<PortalStudentInfo> getStudentInfo(@Body StudentRequest student);

    @POST(UrlEndpoints.PORTAL_ROLLCALLSESSIONS)
    Call<List<RollCallSession>> getRollCallSessions(@Body StaffRequest student);

    @POST(UrlEndpoints.PORTAL_CLASSSTREAMTEACHERSTAFFALLOCATIONS)
    Call<List<ClassStream>> getClassStreamTeacherStaffAllocation(@Body StaffRequest student);

    @POST(UrlEndpoints.PORTAL_CUSTOM_FILTERS_FOR_MARKREGISTER)
    Call<PortalClassStreamTeacherStaffAllocationsResponse> getCustomFiltersForMarkRegister(@Body PortalClassStreamTeacherStaffAllocationRequest request);

    @POST(UrlEndpoints.PORTAL_GETLEAVETYPES)
    Call<List<LeaveType>> getLeaveTypes(@Body StaffRequest student);
   @POST(UrlEndpoints.PORTAL_GETSTAFFOPERATION)
   Call<List<StaffOperation>> getStaffOperations(@Body StaffRequest student);
    @POST(UrlEndpoints.PORTAL_ALL_TRANSPORT_STUDENTS)
    Call<List<TransportStudents>> getPortalAllTransportStudents(@Body StaffRequest request);
    @POST(UrlEndpoints.PORTAL_GETCHILDINFO)
    Call<ArrayList<ChildInfo>> getChildList(@Body StaffRequest request);
    @POST(UrlEndpoints.PORTAL_GETMZIZIAPPSUPPORTHELPCONTROLLER)
    Call<List<Help>> getSupportHelp(@Body StaffRequest request);


    //SAVE DATA


    @POST(UrlEndpoints.PORTAL_SAVE_LEAVE_APPLICATION)
    Call<String> saveLeaveApplication(@Body StaffLeaveApplication staffLeaveApplication);

    @POST(UrlEndpoints.PORTAL_MARKREGISTER)
    Call<String> saveMarkRegister(@Body List<PortalMarkRegisterRequest> portalMarkRegisterRequestList);

    @POST(UrlEndpoints.PORTAL_MARKREGISTER)
    Call<String> saveMarkedRegister(@Body List<PortalMarkRegisterRequest> request);

    @POST(UrlEndpoints.PORTAL_SAVEDISCIPLINE)
    Call<String> saveDisciplineCase(@Body List<DisciplineCase> disciplineCasesList);

    @POST(UrlEndpoints.PORTAL_SAVEVEHICLEFUELING)
    Call<String> saveVehicleFueling(@Body List<VehicleFueling> vehicleFuelingList);

    @POST(UrlEndpoints.PORTAL_SAVEVEHICLESERVICING)
    Call<String> saveVehicleServicing(@Body List<VehicleServicing> vehicleServicingList);

    @POST(UrlEndpoints.PORTAL_SAVETRIPLATLONG)
    Call<String> saveTripLatLong(@Body List<TripLatLong> tripLatLongList);


    @POST(UrlEndpoints.PORTAL_MZIZIAPP_VERSION_CONTROL)
    Call<MziziAppVersionControl> forceMziziAppUpdate(@Body MziziAppVersionControlRequest request);

    @POST(UrlEndpoints.PORTAL_TRANSPORT_SESSIONS)
    Call<PortalTransportSessionBusTripsResponse> getTransportSessionAndBusTrips(@Body StaffRequest request);


    @POST(UrlEndpoints.PORTAL_ASSET_REGISTER)
   Call<List<AssetRegisterResponse>> getAssetRegisterResponse(@Body StaffRequest request);

    @POST(UrlEndpoints.PORTAL_STUDENTLIST_FOR_MARKATTENDANCE)
   Call<List<PortalStudentListForMarkAttendanceResponse>> getPortalStudentListForMarkAttendance(@Body PortalStudentListForMarkAttendanceRequest request);


 @POST(UrlEndpoints.PORTAL_GLOBALSETTINGS)
 Call<Map<String,String>> getGlobalSettings(@Body Map<String,String> globalSettingsRequest);




}
