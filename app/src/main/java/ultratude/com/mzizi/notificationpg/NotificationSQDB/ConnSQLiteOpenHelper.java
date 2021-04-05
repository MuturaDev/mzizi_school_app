package ultratude.com.mzizi.notificationpg.NotificationSQDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;

/**
 * Created by James on 01/09/2018.
 */

public class    ConnSQLiteOpenHelper extends SQLiteOpenHelper {


    private SQLiteDatabase sqdb;
    private Context context;

    private static  ConnSQLiteOpenHelper mHelper;

    private static final String NOTIFICAITON_DB = "Notification_DB";
    private static final int NDB_VERSION = 7;


    private static final String CREATE_NOTIFICATION_QUERY = "CREATE TABLE " +
            LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME + "(" +
            LocalDBContract.NotificationTB._ID +" INTEGER PRIMARY KEY NOT NULL, " +
            LocalDBContract.NotificationTB.MSGID +" INTEGER NOT NULL, " +
            LocalDBContract.NotificationTB.MSG + " TEXT NOT NULL, " +
            LocalDBContract.NotificationTB.DATESENT + " TEXT NOT NULL," +
            LocalDBContract.NotificationTB.STUDID + " TEXT NOT NULL" +
            ");";
    private static final String CREATE_NOTIFICATION_READ_TRACKING = "CREATE TABLE " +
            LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE + "(" +
            LocalDBContract.NotificationReadTracking._ID +" INTEGER PRIMARY KEY, " +
            LocalDBContract.NotificationReadTracking.STUDENTID + " TEXT NOT NULL, " +
            LocalDBContract.NotificationReadTracking.DATEREAD + " TEXT NOT NULL," +
            LocalDBContract.NotificationReadTracking.STUDID + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_READNOTREADNOTIFICATION = "CREATE TABLE " +
            LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE + "(" +
            LocalDBContract.ReadNotReadNotifications._ID +" INTEGER PRIMARY KEY NOT NULL, " +
            LocalDBContract.ReadNotReadNotifications.MSGID +" INTEGER NOT NULL," +
            LocalDBContract.ReadNotReadNotifications.STUDID + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_SyncMyAccountTB = "CREATE TABLE " +
            LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB + "(" +
            LocalDBContract.SyncMyAccountTB._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LocalDBContract.SyncMyAccountTB.BILLING_BALANCE +" TEXT, " +
            LocalDBContract.SyncMyAccountTB.ORGANIZATION_ID + " TEXT NOT NULL, " +
            LocalDBContract.SyncMyAccountTB.PORTAL_ACCOUNT + " TEXT NOT NULL, " +
            LocalDBContract.SyncMyAccountTB.PORTAL_PAYBILL + "  NOT NULL, " +
            LocalDBContract.SyncMyAccountTB.BALANCE + " TEXT NOT NULL," +
            LocalDBContract.SyncMyAccountTB.STUDID + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_NEWCURRICULUMEXAM_FORMAT = "CREATE TABLE " +
            LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + "(" +
            LocalDBContract.NewCurriculumExamFormat._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LocalDBContract.NewCurriculumExamFormat.STUDENTFULLNAME +" TEXT, " +
            LocalDBContract.NewCurriculumExamFormat.YEAID + " TEXT NOT NULL, " +
            LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC + " TEXT NOT NULL, " +
            LocalDBContract.NewCurriculumExamFormat.TERM_NAME + "  NOT NULL, " +
            LocalDBContract.NewCurriculumExamFormat.COURSE_NAME + " TEXT NOT NULL, " +
            LocalDBContract.NewCurriculumExamFormat.LEVEL_NAME + "  NOT NULL, " +
            LocalDBContract.NewCurriculumExamFormat.ACTIVITY + " TEXT NOT NULL, " +
            LocalDBContract.NewCurriculumExamFormat.LEARNINGAREA + " TEXT  NULL, " +
            LocalDBContract.NewCurriculumExamFormat.REMARKS + " TEXT  NULL, " +
            LocalDBContract.NewCurriculumExamFormat.SCORE_DESCRIPTION + " TEXT  NULL, " +
            LocalDBContract.NewCurriculumExamFormat.CLASS_TEACHER_COMMENT + " TEXT  NULL, " +
            LocalDBContract.NewCurriculumExamFormat.HEAD_TEACHER_COMMENT + " TEXT  NULL," +
            LocalDBContract.NewCurriculumExamFormat.STUDID + " TEXT NOT NULL" +
            ");";


    public ConnSQLiteOpenHelper(Context context){
        super(context, NOTIFICAITON_DB,null, NDB_VERSION);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase sqdb){

        if(!tableExists(sqdb,LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))
            sqdb.execSQL(CREATE_NOTIFICATION_QUERY);
//        else
//            sqdb.execSQL(CREATE_NOTIFICATION_QUERY);

        if(!tableExists(sqdb,LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))
            sqdb.execSQL(CREATE_SyncMyAccountTB);
//        else
//            sqdb.execSQL(CREATE_SyncMyAccountTB);



        if(!tableExists(sqdb,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))
            sqdb.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
//        else
//            sqdb.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);



        if(!tableExists(sqdb,LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
            sqdb.execSQL(CREATE_NOTIFICATION_READ_TRACKING);
//        else
//            sqdb.execSQL(CREATE_NOTIFICATION_READ_TRACKING);


        if(!tableExists(sqdb,LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
            sqdb.execSQL(CREATE_READNOTREADNOTIFICATION);
//        else
//            sqdb.execSQL(CREATE_READNOTREADNOTIFICATION);


    }


    public static synchronized ConnSQLiteOpenHelper getInstance(Context context){
        if(mHelper == null){
            mHelper = new ConnSQLiteOpenHelper(context.getApplicationContext());
        }

        return mHelper;
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        //sqdb.execSQL("DROP TABLE IF EXISTS " + LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME);
        //sqdb.execSQL("DROP TABLE IF EXISTS " + LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB);
       // onCreate(sqdb);



        switch (oldVersion){

            case 1:

                if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))//RETURN true changed to false
                    db.execSQL(CREATE_NOTIFICATION_QUERY);

                if(!tableExists(db, LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))//RETURN true changed to false
                    db.execSQL(CREATE_SyncMyAccountTB);

                if(!tableExists(db, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))//RETURN true changed to false
                    db.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
                else
                    if(!isFieldExist(db,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, LocalDBContract.NewCurriculumExamFormat.STUDID))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + " ADD " + LocalDBContract.NewCurriculumExamFormat.STUDID + " INTEGER");

                if(!tableExists(db, LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
                    db.execSQL(CREATE_NOTIFICATION_READ_TRACKING);
                if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
                    db.execSQL(CREATE_READNOTREADNOTIFICATION);



            case 2:

                if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))//RETURN true changed to false
                    db.execSQL(CREATE_NOTIFICATION_QUERY);

                if(!tableExists(db, LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))//RETURN true changed to false
                    db.execSQL(CREATE_SyncMyAccountTB);

                if(!tableExists(db, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))//RETURN true changed to false
                    db.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
                else
                    if(!isFieldExist(db,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, LocalDBContract.NewCurriculumExamFormat.STUDID))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + " ADD " + LocalDBContract.NewCurriculumExamFormat.STUDID + " INTEGER");

                if(!tableExists(db, LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
                    db.execSQL(CREATE_NOTIFICATION_READ_TRACKING);
                if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
                    db.execSQL(CREATE_READNOTREADNOTIFICATION);

            case 3:

                if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))//RETURN true changed to false
                    db.execSQL(CREATE_NOTIFICATION_QUERY);
                else {
                    if(!isFieldExist(db,LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME, "StudID"))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME + " ADD StudID INTEGER");
                }

                if(!tableExists(db, LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))//RETURN true changed to false
                    db.execSQL(CREATE_SyncMyAccountTB);
                else
                    if(!isFieldExist(db,LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB, "StudID"))
                     db.execSQL("ALTER TABLE " + LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB + " ADD StudID INTEGER");

                if(!tableExists(db, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))//RETURN true changed to false
                    db.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
                else
                    if(!isFieldExist(db,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, "StudID"))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + " ADD " + LocalDBContract.NewCurriculumExamFormat.STUDID + " INTEGER");

                if(!tableExists(db, LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
                    db.execSQL(CREATE_NOTIFICATION_READ_TRACKING);
                else
                    if(!isFieldExist(db,LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE, "StudID"))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE + " ADD StudID INTEGER");

                if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
                    db.execSQL(CREATE_READNOTREADNOTIFICATION);
                else
                    if(!isFieldExist(db,LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE, "StudID"))
                         db.execSQL("ALTER TABLE " + LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE + " ADD StudID INTEGER");

            case 4:
                if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))//RETURN true changed to false
                    db.execSQL(CREATE_NOTIFICATION_QUERY);
                else {

                    if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME)) {
                        db.execSQL("DROP TABLE " + LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME);
                        db.execSQL(CREATE_NOTIFICATION_QUERY);
                    }
                }

                if(!tableExists(db, LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))//RETURN true changed to false
                    db.execSQL(CREATE_SyncMyAccountTB);

                if(!tableExists(db, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))//RETURN true changed to false
                    db.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
                else
                    if(!isFieldExist(db,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, LocalDBContract.NewCurriculumExamFormat.STUDID))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + " ADD " + LocalDBContract.NewCurriculumExamFormat.STUDID + " INTEGER");

                if(!tableExists(db, LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
                    db.execSQL(CREATE_NOTIFICATION_READ_TRACKING);

                if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
                    db.execSQL(CREATE_READNOTREADNOTIFICATION);

            case 5:
                if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))//RETURN true changed to false
                    db.execSQL(CREATE_NOTIFICATION_QUERY);

                if(!tableExists(db, LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))//RETURN true changed to false
                    db.execSQL(CREATE_SyncMyAccountTB);

                if(!tableExists(db, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))//RETURN true changed to false
                    db.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
                else
                    if(!isFieldExist(db,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, LocalDBContract.NewCurriculumExamFormat.STUDID))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + " ADD " + LocalDBContract.NewCurriculumExamFormat.STUDID + " INTEGER");

                if(!tableExists(db, LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
                    db.execSQL(CREATE_NOTIFICATION_READ_TRACKING);

                if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
                    db.execSQL(CREATE_READNOTREADNOTIFICATION);
                else{
                    if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE)) {//RETURN true changed to false
                        db.execSQL("DROP TABLE " + LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE);
                        db.execSQL(CREATE_READNOTREADNOTIFICATION);
                    }
                }

            case 6:
                if(!tableExists(db, LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME))//RETURN true changed to false
                    db.execSQL(CREATE_NOTIFICATION_QUERY);

                if(!tableExists(db, LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB))//RETURN true changed to false
                    db.execSQL(CREATE_SyncMyAccountTB);

                if(!tableExists(db, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE))//RETURN true changed to false
                    db.execSQL(CREATE_NEWCURRICULUMEXAM_FORMAT);
                else
                    if(!isFieldExist(db,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, LocalDBContract.NewCurriculumExamFormat.STUDID))
                        db.execSQL("ALTER TABLE " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE + " ADD " + LocalDBContract.NewCurriculumExamFormat.STUDID + " INTEGER");

                if(!tableExists(db, LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE))
                    db.execSQL(CREATE_NOTIFICATION_READ_TRACKING);

                if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE))
                    db.execSQL(CREATE_READNOTREADNOTIFICATION);
                else{
                    if(!tableExists(db, LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE)) {//RETURN true changed to false
                        db.execSQL("DROP TABLE " + LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE);
                        db.execSQL(CREATE_READNOTREADNOTIFICATION);
                    }
                }
        }








//        try{
//            for(int i = oldVersion; i< newVersion; ++i){
//                String migrationName = String.format("from_%d_to_%d.sql", i, (i+1));
//                readAndExecuteSQLScript(sqdb, context, migrationName);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }




    }


//    private   void readAndExecuteSQLScript(SQLiteDatabase sqdb,Context context, String fileName){
//        if(TextUtils.isEmpty(fileName)){
//            return;
//        }
//
//        AssetManager assetManager = context.getAssets();
//        BufferedReader reader = null;
//
//        try{
//            InputStream inputStream = assetManager.open(fileName);
//            InputStreamReader instream = new InputStreamReader(inputStream);
//            reader = new BufferedReader(instream);
//            executeSQLScript(sqdb, reader);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            if(reader != null){
//                try{
//                    reader.close();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//
//    private void executeSQLScript(SQLiteDatabase sqdb, BufferedReader reader) throws IOException{
//        String line;
//        StringBuilder statement = new StringBuilder();
//        while((line = reader.readLine()) != null){
//            statement.append(line);
//            statement.append("\n");
//            if(line.endsWith(";")){
//                sqdb.execSQL(statement.toString());
//                statement = new StringBuilder();
//            }
//        }
//    }


    public boolean isFieldExist(SQLiteDatabase db,String tableName, String fieldName)
    {
        boolean isExist = false;

        Cursor res = db.rawQuery("PRAGMA table_info("+tableName+")",null);
        res.moveToFirst();
        do {
            String currentColumn = res.getString(1);
            if (currentColumn.equals(fieldName)) {
                isExist = true;
            }
        } while (res.moveToNext());
        return isExist;
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
