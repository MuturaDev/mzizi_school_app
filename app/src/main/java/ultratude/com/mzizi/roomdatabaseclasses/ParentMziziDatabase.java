package ultratude.com.mzizi.roomdatabaseclasses;


import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ultratude.com.mzizi.modelclasses.response.BookDataResponse;
import ultratude.com.mzizi.modelclasses.response.BorrowedBooksResponse;
import ultratude.com.mzizi.modelclasses.response.OrderItemResponse;
import ultratude.com.mzizi.modelclasses.response.YoutubeVideoGalleryResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.BookDataResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.BorrowedBooksResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.OrderItemResponseDAO;

import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalDetailedToDoListResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalOptionalFeesResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalRecentTransactionResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentResultsExtendedDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentUnitsDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentVisualizationAverageResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentVisualizationResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalToDoListResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalTransportRoutesResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.SwitchSiblingsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.TimeTableResponseDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.YoutubeVideoGalleryResponseDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.AuthenticateUserResponseDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.GlobalSettingsDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.NotificationDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.ParentChatDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalContactsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalDetailedTransactionDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalEventsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentDetailedResultsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalSiblingsDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.StudentClassAttendanceDAO;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.AuthenticateUserResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.ParentChat;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalSiblings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.roomdatabaseclasses.RoomDao.PortalStudentInfoDao;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.Notification;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentInfo;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentUnits;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationAverageResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.roomdatabaseclasses.Util.DateRoomConverter;

/**
 * Created by James on 25/06/2018.
 */

// TODO: 2020-03-27 Need to update the db version before hosting version 35
@Database(entities = {
        AuthenticateUserResponse.class,
        PortalContacts.class,
        PortalStudentInfo.class,
        Notification.class,
        PortalDetailedTransaction.class,
        PortalStudentDetailedResults.class,
        PortalSiblings.class,
        PortalEvents.class,
        StudentClassAttendance.class,
        ParentChat.class,
        GlobalSettings.class,
        PortalDetailedToDoListResponse.class,
        PortalOptionalFeesResponse.class,
        PortalRecentTransactionResponse.class,
        PortalStudentResultsExtended.class,
        PortalStudentVisualizationAverageResponse.class,
        PortalStudentVisualizationResponse.class,
        PortalToDoListResponse.class,
        PortalTransportRoutesResponse.class,
        PortalStudentUnits.class,

        BookDataResponse.class,
        BorrowedBooksResponse.class,
        OrderItemResponse.class,
        //SchoolTripResponse.class,
        YoutubeVideoGalleryResponse.class,
       // OrderItemsHistory.class
        TimeTableResponse.class
        },
        version = 8/*from 6*/, exportSchema = false)
@TypeConverters({DateRoomConverter.class})
public abstract class ParentMziziDatabase extends RoomDatabase {

    public abstract PortalStudentInfoDao getPortalStudentInfoDao();
    public abstract NotificationDao getNotificationsDao();
    public abstract PortalStudentDetailedResultsDao getPortalStudentDetailedResultsDao();
    public abstract PortalDetailedTransactionDao getPortalDetailedTransactionDao();
    public abstract PortalSiblingsDao getPortalSiblingsDao();
    public abstract PortalEventsDao getPortalEventsDao();
    public abstract AuthenticateUserResponseDao getAuthenticateUserResponseDao();
    public abstract PortalContactsDao getPortalContactsDao();
    public abstract StudentClassAttendanceDAO getStudentClassAttendanceDAO();
    public abstract GlobalSettingsDAO getGlobalSettingsDAO();
    public abstract ParentChatDAO getParentChatDAO();

    public abstract PortalRecentTransactionResponseDAO getPortalRecentTransactionResponseDAO();
    public abstract PortalDetailedToDoListResponseDAO getPortalDetailedToDoListResponseDAO();
    public abstract PortalOptionalFeesResponseDAO getPortalOptionalFeesResponseDAO();
    public abstract PortalStudentResultsExtendedDAO getPortalStudentResultsExtendedDAO();
    public abstract PortalStudentVisualizationAverageResponseDAO getPortalStudentVisualizationAverageResponseDAO();
    public abstract PortalStudentVisualizationResponseDAO getPortalStudentVisualizationResponseDAO();
    public abstract PortalToDoListResponseDAO getPortalToDoListResponseDAO();
    public abstract PortalTransportRoutesResponseDAO getPortalTransportRoutesResponseDAO();
    public abstract PortalStudentUnitsDAO getPortalStudentUnitsDAO();

    public abstract BookDataResponseDAO getBookDataResponseDAO();
    public abstract OrderItemResponseDAO getOrderItemResponseDAO();
    public abstract BorrowedBooksResponseDAO getBorrowedBooksResponseDAO();
    //public abstract OrderItemsHistoryDAO getOrderItemsHistoryDAO();
    //public abstract SchoolTripResponseDAO getSchoolTripReponseDAO();
    public abstract YoutubeVideoGalleryResponseDAO getYoutubeVideoGalleryResponseDAO();

    public abstract SwitchSiblingsDao getSwitchSiblingsDAO();
    public abstract TimeTableResponseDao getTimeTableResponseDAO();

    private static ParentMziziDatabase db;

    // synchronized is use to avoid concurrent access in multithread environment
    public static /*synchronized*/ ParentMziziDatabase getInstance(Context context){
        if(db == null){
            db = buildDatabaseInstance(context);
        }
        return db;
    }

    private static ParentMziziDatabase buildDatabaseInstance(Context context){

        return Room
                .databaseBuilder(context, ParentMziziDatabase.class, Constants.DB_NAME)
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .addMigrations(MIGRATION_5_6)
                .addMigrations(MIGRATION_6_7)
                .addMigrations(MIGRATION_7_8)
                .build();
    }

    public void cleanUpdb(){
        db = null;
    }


//    private static final String CREATE_AUTHENTICATEUSERRESPONSE  = "CREATE TABLE AuthenticateUserResponse " +
//            " ( " +
//            "userID TEXT PRIMARY KEY  NOT NULL, " +
//            "userType TEXT DEFAULT NULL, " +
//            "appcode TEXT DEFAULT NULL, " +
//            "username TEXT DEFAULT NULL, " +
//            "password TEXT DEFAULT NULL, " +
//            "login_status TEXT DEFAULT NULL " +
//            ");";



    //migration
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            // Since we didn't alter the table, there's nothing else to do here.
            //database.execSQL(CREATE_AUTHENTICATEUSERRESPONSE);
            database.execSQL("ALTER TABLE 'AuthenticateUserResponse' ADD schoolID TEXT");
            database.execSQL("ALTER TABLE 'AuthenticateUserResponse' ADD organizationID TEXT");
        }
    };

//any new tables you have to create then after the first version and not use room entity
  private static final String CREATE_STUDENTCLASSATTENDANCE  = "CREATE TABLE StudentClassAttendance " +
             " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "StudentName TEXT DEFAULT NULL, " +
            "Status TEXT DEFAULT NULL, " +
            "CourseName TEXT DEFAULT NULL, " +
            "Remarks TEXT DEFAULT NULL, " +
            "DateRecorded TEXT DEFAULT NULL, " +
            "TermFor TEXT DEFAULT NULL, " +
            "YearFor TEXT DEFAULT NULL, " +
            "UnitName TEXT DEFAULT NULL, " +
            "RollcallSessionName TEXT DEFAULT NULL " +
            ");";

    private static final String CREATE_PARENTCHAT = "CREATE TABLE ParentChat " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "msg TEXT DEFAULT NULL, " +
            "datePosted TEXT DEFAULT NULL, " +
            "actor TEXT DEFAULT NULL " +
            ");";
    private static final String CREATE_GLOBALSETTINGS = "CREATE TABLE GlobalSettings " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "globalSettingsValue TEXT DEFAULT NULL " +
            ");";


    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL("ALTER TABLE 'PortalDetailedTransaction' ADD tranType TEXT");
            database.execSQL("ALTER TABLE 'PortalDetailedTransaction' ADD transType TEXT");
            database.execSQL("ALTER TABLE 'PortalDetailedTransaction' ADD datePosted TEXT");

            database.execSQL("ALTER TABLE 'AuthenticateUserResponse' ADD schoolID TEXT");
            database.execSQL("ALTER TABLE 'AuthenticateUserResponse' ADD organizationID TEXT");

            database.execSQL(CREATE_STUDENTCLASSATTENDANCE);
            database.execSQL(CREATE_PARENTCHAT);
            database.execSQL(CREATE_GLOBALSETTINGS);

            database.execSQL("ALTER TABLE 'PortalStudentDetailedResults' ADD ctComments TEXT");
            database.execSQL("ALTER TABLE 'PortalStudentDetailedResults' ADD hmComments TEXT");

        }
    };

    private static final String CREATE_PortalRecentTransactionResponse = "CREATE TABLE PortalRecentTransactionResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "RefNo TEXT DEFAULT NULL, " +
            "TranType TEXT DEFAULT NULL, " +
            "TranDate TEXT DEFAULT NULL, " +
            "TranAmount TEXT DEFAULT NULL, " +
            "BalAmount TEXT DEFAULT NULL, " +
            "TransType TEXT DEFAULT NULL " +
            ");";

    private static final String CREATE_PortalDetailedToDoListResponse = "CREATE TABLE PortalDetailedToDoListResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "Name TEXT DEFAULT NULL, " +
            "ToDoAge TEXT DEFAULT NULL, " +
            "DueDate TEXT DEFAULT NULL, " +
            "DocPath TEXT DEFAULT NULL, " +
            "Body TEXT DEFAULT NULL, " +
            "UnitName TEXT DEFAULT NULL, " +

            "TeacherMemoID TEXT DEFAULT NULL, " +
            "IsFeedbackRequired TEXT DEFAULT NULL, " +
            "IsDocPathAvailable TEXT DEFAULT NULL, " +
            "IsVideoLinkAvailable TEXT DEFAULT NULL, " +
            "IsDocPathExtraAvailable TEXT DEFAULT NULL, " +
            "IsAudioLinkAvailable TEXT DEFAULT NULL, " +
            "DocPathExtra TEXT DEFAULT NULL, " +
            "VideoLink TEXT DEFAULT NULL, " +
            "AudioLink TEXT DEFAULT NULL, " +
            "TimeOffSet TEXT DEFAULT NULL, " +
            "PublishDate TEXT DEFAULT NULL, " +
            "StartTime TEXT DEFAULT NULL, " +
            "EndTime TEXT DEFAULT NULL," +
            "SubmitStatus TEXT DEFAULT NULL" +
            ");";


    private static final String CREATE_PortalOptionalFeesResponse = "CREATE TABLE PortalOptionalFeesResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "className TEXT DEFAULT NULL, " +
            "term TEXT DEFAULT NULL, " +
            "feeName TEXT DEFAULT NULL, " +
            "feeAmount TEXT DEFAULT NULL, " +
            "currentStatus TEXT DEFAULT NULL, " +
            "options TEXT DEFAULT NULL, " +
            "isTakenUrlText TEXT DEFAULT NULL, " +
            "feeExemptions_f TEXT DEFAULT NULL, " +
            "feeExemptions_i TEXT DEFAULT NULL " +
            ");";



    private static final String CREATE_PortalStudentResultsExtended = "CREATE TABLE PortalStudentResultsExtended " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "ExamPeriod TEXT DEFAULT NULL, " +
            "AvgScore TEXT DEFAULT NULL, " +
            "MeanGrade TEXT DEFAULT NULL, " +
            "ExamRemarks TEXT DEFAULT NULL, " +
            "DownloadLink TEXT DEFAULT NULL, " +
            "FileUrl TEXT DEFAULT NULL " +
            ");";



    private static final String CREATE_PortalStudentVisualizationAverageResponse = "CREATE TABLE PortalStudentVisualizationAverageResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "Period TEXT DEFAULT NULL, " +
            "AverageScore TEXT DEFAULT NULL, " +
            "MeanGrade TEXT DEFAULT NULL " +
            ");";



    private static final String CREATE_PortalStudentVisualizationResponse = "CREATE TABLE PortalStudentVisualizationResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "Period TEXT DEFAULT NULL, " +
            "UnitName TEXT DEFAULT NULL, " +
            "AverageScore TEXT DEFAULT NULL " +
            ");";


    private static final String CREATE_PortalToDoListResponse = "CREATE TABLE PortalToDoListResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "DiaryEntryTypeID TEXT DEFAULT NULL, " +
            "DiaryEntryType TEXT DEFAULT NULL, " +
            "DiaryEntriesCount TEXT DEFAULT NULL " +
            ");";

    private static final String CREATE_PortalTransportRoutesResponse = "CREATE TABLE PortalTransportRoutesResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "displayName TEXT DEFAULT NULL, " +
            "termID TEXT DEFAULT NULL, " +
            "yearID TEXT DEFAULT NULL, " +
            "tranAmount TEXT DEFAULT NULL, " +
            "routeUrl TEXT DEFAULT NULL, " +
            "routeUrlDescription TEXT DEFAULT NULL, " +
            "routesID_r TEXT DEFAULT NULL, " +
            "termID_t TEXT DEFAULT NULL, " +
            "yearID_y TEXT DEFAULT NULL " +
            ");";

    private static final String CREATE_portalstudentunit = "CREATE TABLE PortalStudentUnits " +
            " ( " +
            "UnitID INTEGER PRIMARY KEY  NOT NULL, " +
            "UnitName TEXT DEFAULT NULL " +
            ");";


    private static final String CREATE_BookDataResponse = "CREATE TABLE BookDataResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "StudentID TEXT DEFAULT NULL, " +
            "SchoolID TEXT DEFAULT NULL, " +
            "OrganizationID TEXT DEFAULT NULL, " +
            "YearID TEXT DEFAULT NULL, " +
            "TermID TEXT DEFAULT NULL " +
            ");";

    private static final String CREATE_BorrowedBooksResponse = "CREATE TABLE BorrowedBooksResponse " +
            " ( " +
            "BookNo TEXT PRIMARY KEY NOT NULL, " +
            "BookName TEXT DEFAULT NULL, " +
            "CategoryName TEXT DEFAULT NULL, " +
            "PublisherName TEXT DEFAULT NULL, " +
            "TypeName TEXT DEFAULT NULL, " +
            "UnitName TEXT DEFAULT NULL, " +
            "DateBorrowed TEXT DEFAULT NULL, " +
            "DateReturned TEXT DEFAULT NULL " +
            ");";

    private static final String CREATE_OrderItemsResponse = "CREATE TABLE OrderItemResponse " +
            " ( " +
            "ItemID TEXT PRIMARY KEY NOT NULL, " +
            "ItemName TEXT DEFAULT NULL, " +
            "CategoryName TEXT DEFAULT NULL, "+
            "ItemCode TEXT DEFAULT NULL, " +
            "DispensingUnits TEXT DEFAULT NULL, " +
            "ItemSellingPrice TEXT DEFAULT NULL, " +
            "Quantity TEXT DEFAULT NULL " +
            ");";

//    private static final String CREATE_OrderItemsHistory = "CREATE TABLE OrderItemsHistory " +
//            " ( " +
//            "PortalOrderID TEXT PRIMARY KEY NOT NULL, " +
//            "ItemID TEXT DEFAULT NULL, " +
//            "ItemName TEXT DEFAULT NULL, " +
//            "CategoryName TEXT DEFAULT NULL, " +
//            "ItemCode TEXT DEFAULT NULL, " +
//            "DispensingUnits TEXT DEFAULT NULL, " +
//            "ItemSellingPrice TEXT DEFAULT NULL, " +
//            "Quantity TEXT DEFAULT NULL, " +
//            "ShowDeleteLink TEXT DEFAULT NULL, " +
//            "ItemStatus TEXT DEFAULT NULL, " +
//            "DateAdded TEXT DEFAULT NULL, " +
//            "Remarks TEXT DEFAULT NULL, " +
//            "Total TEXT DEFAULT NULL " +
//            ");";

//    private static final String CREATE_SchoolTripResponse = "CREATE TABLE SchoolTripResponse " +
//            " ( " +
//            "ID TEXT PRIMARY KEY NOT NULL, " +
//            "Name TEXT DEFAULT NULL, " +
//            "Description TEXT DEFAULT NULL, " +
//            "TermID TEXT DEFAULT NULL, " +
//            "YearID TEXT DEFAULT NULL, " +
//            "AmountCharged TEXT DEFAULT NULL, " +
//            "ExpiryDate TEXT DEFAULT NULL " +
//            ");";


    private static final String CREATE_YoutubeVideoGalleryResponse = "CREATE TABLE YoutubeVideoGalleryResponse " +
            " ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            "Name TEXT DEFAULT NULL, " +
            "Body TEXT DEFAULT NULL, " +
            "VideoLink TEXT DEFAULT NULL, " +
            "DueDate TEXT DEFAULT NULL " +
            ");";









    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(CREATE_PortalRecentTransactionResponse);
            database.execSQL(CREATE_PortalDetailedToDoListResponse);
            database.execSQL(CREATE_PortalOptionalFeesResponse);
            database.execSQL(CREATE_PortalStudentResultsExtended);
            database.execSQL(CREATE_PortalStudentVisualizationAverageResponse);
            database.execSQL(CREATE_PortalStudentVisualizationResponse);
            database.execSQL(CREATE_PortalToDoListResponse);
            database.execSQL(CREATE_PortalTransportRoutesResponse);
            database.execSQL(CREATE_portalstudentunit);

            database.execSQL(CREATE_BookDataResponse);
            database.execSQL(CREATE_BorrowedBooksResponse);
            database.execSQL(CREATE_OrderItemsResponse);
//            database.execSQL(CREATE_OrderItemsHistory);
//            database.execSQL(CREATE_SchoolTripResponse);
            database.execSQL(CREATE_YoutubeVideoGalleryResponse);

            database.execSQL("ALTER TABLE '" + Constants.TABLE_NAME_PORTALSTUDENTINFO + "' ADD showGraphSection TEXT");
            database.execSQL("ALTER TABLE '" + Constants.TABLE_NAME_PORTALSTUDENTINFO + "' ADD continuousAssessments TEXT");
            database.execSQL("ALTER TABLE '" + Constants.TABLE_NAME_PORTALSTUDENTINFO + "' ADD showPaymentNoteSection TEXT");
            database.execSQL("ALTER TABLE '" + Constants.TABLE_NAME_PORTALSTUDENTINFO + "' ADD assignments TEXT");
            database.execSQL("ALTER TABLE '" + Constants.TABLE_NAME_PORTALSTUDENTINFO + "' ADD paymentNote TEXT");
            database.execSQL("ALTER TABLE 'GlobalSettings' ADD globalSettingName TEXT");

        }
    };



    static final Migration MIGRATION_4_5 = new Migration(4,5){

        /*You can:
            create new table as the one you are trying to change,
            copy all data,
            drop old table,
            rename the new one.*/

        final String CREATE_PortalToDoListResponseFornew = "CREATE TABLE PortalToDoListResponse2 " +
                " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "DiaryEntryTypeID TEXT DEFAULT NULL, " +
                "DiaryEntryType TEXT DEFAULT NULL, " +
                "DiaryEntriesCount TEXT DEFAULT NULL " +
                ");";


        final String INSERT_todo_old_to_new = "INSERT INTO 'PortalToDoListResponse2' (DiaryEntryTypeID,DiaryEntryType,DiaryEntriesCount)" +
                " SELECT DiaryEntryTypeID,DiaryEntryType,DiaryEntriesCount FROM PortalToDoListResponse;";

        final String DROP_todo = "DROP TABLE 'PortalToDoListResponse'";

        final String ALTER_TODO = "ALTER TABLE PortalToDoListResponse2 RENAME TO PortalToDoListResponse;";
        //final String CREATE_PortalToDoListResponseForold = CREATE_PortalToDoListResponse;

//        final String INSERT_todo_new_to_old = "INSERT INTO 'PortalToDoListResponse' (DiaryEntryTypeID,DiaryEntryType,DiaryEntriesCount)" +
//                " SELECT DiaryEntryTypeID,DiaryEntryType,DiaryEntriesCount FROM PortalToDoListResponse2;";
//
//        final String DROP_todo2 = "DROP TABLE 'PortalToDoListResponse2'";


        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL(CREATE_PortalToDoListResponseFornew);
            database.execSQL(INSERT_todo_old_to_new);
            database.execSQL(DROP_todo);
            database.execSQL(ALTER_TODO);
        }
    };


    static final Migration MIGRATION_5_6 = new Migration(5,6) {

        final String CREATE_PortalSiblings2 = "CREATE TABLE PortalSiblings2 " +
                " ( " +
                "studentID TEXT PRIMARY KEY NOT NULL," +
                "registrationNumber TEXT DEFAULT NULL, " +
                "studentFullName TEXT DEFAULT NULL, "+
                "courseName TEXT DEFAULT NULL, " +
                "studentStatus TEXT DEFAULT NULL, " +
                "isMain INTEGER NOT NULL " +
                ");";


        final String INSERT_sibling_old_to_new = "INSERT INTO 'PortalSiblings2' (studentID,registrationNumber,studentFullName,courseName,studentStatus)" +
                " SELECT studentID,registrationNumber,studentFullName,courseName,studentStatus FROM PortalSiblings;";

        final String DROP_sibling = "DROP TABLE 'PortalSiblings'";

        final String ALTER_PortalSibling = "ALTER TABLE PortalSiblings2 RENAME TO PortalSiblings";



        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //this did not work had many errors
            //database.execSQL("ALTER TABLE 'PortalSiblings' ADD isMain INTEGER");
            database.execSQL(CREATE_PortalSiblings2);
            database.execSQL(INSERT_sibling_old_to_new);
            database.execSQL(DROP_sibling);
            database.execSQL(ALTER_PortalSibling);


            database.execSQL("ALTER TABLE 'PortalContacts' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE '"+Constants.TABLE_NAME_PORTALSTUDENTINFO+"' ADD StudID INTEGER");
           // database.execSQL("ALTER TABLE '"+Constants.TABLE_NAME_NOTIFICATION+"' ADD Studid INTEGER");
            database.execSQL("ALTER TABLE 'PortalDetailedTransaction' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalStudentDetailedResults' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalEvents' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'StudentClassAttendance' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'ParentChat' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'GlobalSettings' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalDetailedToDoListResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalOptionalFeesResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalRecentTransactionResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalStudentResultsExtended' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalStudentVisualizationAverageResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalStudentVisualizationResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalToDoListResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalTransportRoutesResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'PortalStudentUnits' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'BookDataResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'BorrowedBooksResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'OrderItemResponse' ADD StudID INTEGER");
            database.execSQL("ALTER TABLE 'YoutubeVideoGalleryResponse' ADD StudID INTEGER");


        }
    };


    static final Migration MIGRATION_6_7 = new Migration(6,7) {

        final String CREATE_PortalContacts = "CREATE TABLE PortalContacts2 " +
                " ( " +
                "idp INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "id TEXT DEFAULT NULL, " +
                "settingsName TEXT DEFAULT NULL, "+
                "settingsValue TEXT DEFAULT NULL, " +
                "StudID INTEGER DEFAULT NULL " +
                ");";


        final String INSERT_todo_old_to_new = "INSERT INTO 'PortalContacts2' (id,settingsName,settingsValue)" +
                " SELECT id,settingsName,settingsValue FROM PortalContacts;";

        final String DROP_todo = "DROP TABLE 'PortalContacts'";

        final String ALTER_PortalContacts = "ALTER TABLE PortalContacts2 RENAME TO PortalContacts";


        final String CREATE_TIME_TABLE = "CREATE TABLE TimeTableResponse " +
                " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "ClassRoom TEXT DEFAULT NULL, " +
                "Class_ TEXT DEFAULT NULL, " +
                "Subject TEXT DEFAULT NULL, " +
                "Teacher TEXT DEFAULT NULL, " +
                "StartTime TEXT DEFAULT NULL, " +
                "EndTime TEXT DEFAULT NULL, " +
                "Day TEXT DEFAULT NULL, " +
                "UnitColor TEXT DEFAULT NULL, " +
                "StudID INTEGER NOT NULL " +
                ");";


        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            database.execSQL(CREATE_PortalContacts);
            database.execSQL(INSERT_todo_old_to_new);
            database.execSQL(DROP_todo);
            database.execSQL(ALTER_PortalContacts);

            database.execSQL(CREATE_TIME_TABLE);

        }
    };


    static final Migration MIGRATION_7_8 = new Migration(7,8) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE 'PortalSiblings' ADD Username TEXT DEFAULT NULL");
            database.execSQL("ALTER TABLE 'PortalSiblings' ADD DefaultPassword TEXT DEFAULT NULL");
            database.execSQL("ALTER TABLE 'PortalSiblings' ADD SchoolID TEXT DEFAULT NULL");
            database.execSQL("ALTER TABLE 'PortalSiblings' ADD OrganizationID TEXT DEFAULT NULL");
        }
    };






}
