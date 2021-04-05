package ultratude.com.staff.webservice.Database;

import android.provider.BaseColumns;

public class ConnSQLiteContract {

    public class UltraDataDef implements BaseColumns{
        public static final String STUDENTID_BARCODE = "StudentID_Barcode";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String BUS_ACTIVITY = "bus_activity";
        public static final String SESSION = "session";
        public static final String VEHICLE_PLATE = "vehicle_plate";
        public static final String BUSTRIP = "busTrip";
        public static final String STATUS_SENT = "status_sent";
        public static final String DATE_SCANNED = "datescanned";

        public static final String ULTRADATA_TABLE="ultradata";
    }
    public class UltraStaffDef implements BaseColumns{
        public static final String STAFF_ID = "staffID";
        public static final String APPCODE = "appcode";
        public static final String USERTYPE = "userType";

        //just updated
        public static final String SCHOOLID = "schoolID";
        public static final String ORGANIZATIONID = "organizationID";

        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";


        public static final String ULTRAUSER_TABLE="Staff";
    }

    public class UpdatedDailyLatitudeAndLongitude implements BaseColumns{
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";

        public static final String UPDATED_LATITUDE_AND_LONGITUDE = "updatedLatitudeAndLongitude";
    }


    public class UpdatedTripLatitudeAndLongitude implements BaseColumns{
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String STAFF_ID = "staffID";
        public static final String DATE_RECORDED = "dateRecorded";
        public static final String VEHICLE_ID = "vehicleID";
        public static final String APP_CODE = "appCode";

        public static final String UPDATED_TRIP_LATITUDE_AND_LONGITUDE = "updatedTripLatitudeAndLongitude";
    }




    public class PortalAllTransportStudentsListing implements BaseColumns{

        public static final String STUDENTFULLNAME = "studentFullName";
        public static final String REGISTRATIONNUMBER = "registrationNumber";
        public static final String CLASSSTREAM = "classStream";
        public static final String SCHOOLNAME = "schoolName";
        public static final String BARCODE = "barcode";

        public static final String ROUTE_NAME = "routeName";
        public static final String BUS = "bus";
        public static final String TERM_NAME = "termName";
        public static final String YEAR_ID = "yearID";
        public static final String MORNING_PICKED = "morningPicked";
        public static final String EVENING_PICKED = "eveningPicked";

        public static final String FATHER_PHONE = "fatherPhone";
        public static final String MOTHER_PHONE  = "motherPhone";

        public static final String PORTALALLSTUDENTSLISTING_TABLE ="portalAllStudentsListing";


    }



    //UPDATES TO VERSION 5

    public class PortalSaveVehicle implements BaseColumns{
        public static final String VEHICLEID = "vehicleID";
        public static final String NUMBERPLATE = "numberPlate";
        public static final String LASTMILEAGE =  "lastMileage";

        public static final String PORTALSAVEVEHICLE_TABLE  = "vehicles";
    }

    public class PortalSaveAllStudents implements BaseColumns{
        public static final String STUDENTID = "studentID";
        public static final String STUDENTFULLNAME = "studentFullName";
        public static final String REGISTRATIONNUMBER = "registrationNumber";
        public static final String CLASSSTREAM = "classStream";
        public static final String SCHOOLNAME = "schoolName";
        public static final String SCHOOLID = "schoolID";
        public static final String ORGANIZATIONID = "organizationID";
        public static final String CURRENTTERM = "currentTerm";
        public static final String CURRENTYEAR = "currentYear";

        public static final String PORTALSAVEALLSTUDENTS_TABLE = "students";

    }



    public class PortalSaveVehicleServicing implements BaseColumns{

        public static final String VEHICLE_ID = "vehicleID";
        public static final String SERVICEDBY = "servicedBy";
        public static final String SERVICETYPE = "serviceType";
        public static final String DATESERVICED = "dateServiced";
        public static final String MILEAGEBEFORE = "mileageBefore";
        public static final String SERVICEREPORT = "serviceReport";
        public static final String NEXTSERVICEMILEAGE = "nextServiceMileage";
        public static final String SERVICECOST = "serviceCost";
        public static final String ADDEDBY  = "addedBy";
        public static final String APPCODE = "appCode";


        public static final String PORTALSAVEVEHICLESERVICING_TABLE = "vehicleServicing";
    }


    public class PortalSaveVehicleFueling implements BaseColumns{
        public static final String VEHICLE_ID = "vehicleID";
        public static final String FUELEDBY = "fueledBy";
//        public static final String FUELTYPE = "fuelType";
        public static final String DATEFUELED = "dateFueled";
        public static final String MILEAGEBEFORE = "mileageBefore";
        public static final String QUANTITY = "quantity";
        public static final String UNITPRICE = "unitPrice";
        public static final String ADDEDBY = "addedBy";
        public static final String APPCODE = "appCode";


        public static final String PORTALSAVEVEHICLEFUELING_TABLE = "vehicleFueling";
    }



    public class PortalSaveDisciplineCase implements BaseColumns{
        public static final String STUDENT_ID = "studentID";
        public static final String OFFENCE = "offence";
        public static final String PENAULTY = "penaulty";
        public static final String REPORTEDBY = "reportedBy";
        public static final String APPCODE = "appCode";


        public static final String PORTALSAVEDISCIPLINECASE_TABLE = "desciplineCase";
    }


    public class PortalMarkRegister implements  BaseColumns{
        public static final String STUDENT_ID = "studentID";
        public static final String STUDENTFULLNAME = "studentFUllName";
        public static final String STATUS = "status";
        public static final String DATERECORDED = "dateRecorded";
        public static final String ROLLCALLSESSION  = "rollCallSession";
        public static final String STAFF_ID = "staffID";
        public static final String REMARKS = "remarks";
        public static final String APPCODE = "appcode";

        public static final String CLASSSTREAM = "classStream";
        public static final String STATUSSENT = "statusSent";


        public static final String PORTALMARKREGISTER_TABLE = "markRegister";
    }


    public class PortalGetSchools implements BaseColumns{
        public static final String SCHOOL_ID = "schoolID";
        public static final String SCHOOLNAME = "schoolName";


        public static final String PORTALGETSCHOOLS_TABLE = "schools";
    }



    public class PortalGetDutyRoster implements BaseColumns{
        public static final String STAFFNAME = "staffName";
        public static final String YEAR = "year";
        public static final String TERMNAME = "termName";
        public static final String DUTYWEEK = "dutyWeek";
        public static final String CURRENTWEEK = "currentWeek";
        public static final String PHONENUMBER = "phoneNumber";


        public static final String PORTALGETDUTYROSTER_TABLE = "dutyRoster";

    }


    public class PortalGetAllStaff implements BaseColumns{
        public static final String STAFFID = "staffID";
        public static final String FIRSTNAME = "firstName";
        public static final String LASTNAME = "lastName";
        public static final String TITLENAME = "titleName";
        public static final String RANKNAME = "rankName";
        public static final String PHONENUMBER = "phoneNumber";
        public static final String SCHOOLID = "schoolID";
        public static final String ORGANIZATIONID = "organizationID";
        public static final String DEPARTMENTNAME = "departmentName";
        public static final String SCHOOLNAME = "schoolName";
        public static final String BARCODE = "barCode";
        public static final String EMAIL = "email";


        public static final String PORTALGETALLSTAFF_TABLE  = "allStaff";
    }



    public class PortalGetRollCallSessions implements BaseColumns{
        public static final String SESSIONID = "sessionID";
        public static final String SESSIONNAME = "sessionName";

        public static final String PORTALGETROLLCALLSESSIONS_TABLE ="rollCallSession";
    }


    public class PortalGetLeaveType implements BaseColumns{
        public static final String LEAVETYPEID = "leaveTypeID";
        public static final String LEAVETYPENAME = "leavetTypeName";

        public static final String PORTALGETLEAVETYPE_TABLE = "leavetType";
    }


    public class PortalGetExamHistory implements BaseColumns{
        public static final String SUBJECT = "subject";
        public static final String SCORE = "score";
        public static final String GRADE = "grade";
        public static final String REMARK = "remark";
        public static final String TOTALMARKS = "totalMarks";
        public static final String MEANSCORE = "meanscore";
        public static final String MEANGRADE = "meanGrade";
        public static final String STREAMPOSITION = "streamPosition";
        public static final String OVERALLPOSITION = "overallPosition";
        public static final String PERIOD = "period";
        public static final String TERMNAME = "termName";
        public static final String CURRENTYEAR = "currentYear";


        public static final String PORTALGETEXAMHISTORY_TABLE= "examHistory";
    }


    public class PortalGetStudentDisciplinaryCases implements  BaseColumns{
        public static final String OFFENCE_DESCRIPTION = "offenceDescription";
        public static final String PENAULTY_DESCRIPTION = "penaultyDescription";
        public static final String REPORTEDBY = "reportedBy";
        public static final String DATE_COMMITED = "dateCommited";
        public static final String TERM_FOR = "termFor";
        public static final String YEARID = "yearID";
        public static final String STUDENTNAME = "studentName";

        public static final String PORTALGETDISCIPLINARYCASES_TABLE = "disciplnaryCases";
    }


    public class PortalGetStudentAttendance implements  BaseColumns{
        public static final String STUDENT_NAME = "studentName";
        public static final String STATUS = "status";
        public static final String COURSENAME = "courseName";
        public static final String REMARKS = "remarks";
        public static final String DATE_RECORDED = "dateRecorded";
        public static final String TERM_FOR = "termFor";
        public static final String YEAR_FOR = "yearFor";
        public static final String UNIT_NAME = "unitName";

        public static final String PORTALGETSTUDENTATTENDANCE_TABLE = "studentAttendance";
    }


    public class PortalGetClassStreams implements  BaseColumns{
        public static final String COURSE_ID = "courseID";
        public static final String COURSE_CODE = "courseCode";
        public static final String LEVEL_ID = "levelID";
        public static final String LEVEL_NAME = "levelName";


        public static final String PORTALGETCLASSSTREAMS_TABLE = "classStreams";
    }


    public class PortalStaffOperations implements BaseColumns{
        public static final String STAFFID = "staffID";
        public static final String STAFF_OPERATION = "staffOperation";

        public static final String PORTALSTAFFOPERATIONS_TABLE = "staffOPeration";
    }

    public class PortalGetMziziAppSupportHelp implements BaseColumns{
        public static final String ID = "id";
        public static final String HELP_DESCRIPTION = "helpDescription";

        public static final String OPERATION_NAME = "OperationName";

        public static final String PORTALGETMZIZIAPPSUPPORTHELP_TABLE = "mziziAppSupportHelp";
    }

    public class PortalAssetTracking implements BaseColumns{
        public static final String BARCODE_NUMBER = "barcodeNumber";
        public static final String ASSET_TYPE = "assetType";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String STAFFID = "staffID";
        public static final String SCANDATETIME = "scanDateTime";
        public static final String COMMENT = "comment";

        public static final String PORTALASSETTRACKING_TABLE = "assetTracking";
    }

    public class PortalTransportSessions implements BaseColumns{
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CODE = "code";

        public static final String PORTAL_TRANSPORT_SESSIONS_TABLE = "transportSessions";

    }

    public class PortalTransportBusTrip implements BaseColumns{
        public static final String ID = "id";
        public static final String NAME = "name";

        public static final String PORTAL_TRANSPORT_BUSTRIP_TABLE = "transportBustrip";
    }

    public class PortalAssetRegister implements BaseColumns{
        public static final String ID = "id";
        public static final String ASSETNAME = "assetName";
        public static final String ASSETCODE = "assetCode";
        public static final String ASSETSERIALNUMBER = "assetSerialNumber";
        public static final String ASSETDESCRIPTION = "assetDescription";
        public static final String ASSETBARCODE = "assetBarcode";
        public static final String ASSETTYPE = "assetType";
        public static final String ASSETTYPEID = "assetTypeID";
        public static final String OPENMARKETPRICE = "openMarketPrice";

        public static final String PORTAL_ASSETREGISTER_TABLE = "assetRegister";
    }


}
