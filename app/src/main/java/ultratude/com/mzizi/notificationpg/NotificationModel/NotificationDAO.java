package ultratude.com.mzizi.notificationpg.NotificationModel;

//import android.arch.lifecycle.MutableLiveData;
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModel;
//import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.notificationpg.NotificationSQDB.ConnSQLiteOpenHelper;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;

/**
 * Created by James on 01/09/2018.
 */

public class NotificationDAO {


   private Context context;

    private ConnSQLiteOpenHelper openHelper;


    private String mainStudentID;


    public NotificationDAO(Context context){

        openHelper = new ConnSQLiteOpenHelper(context);

        this.context = context;
        ParentMziziDatabase db = ParentMziziDatabase.getInstance(context.getApplicationContext());
        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
        if(studentid == null){
            studentid  = "0";
        }
        mainStudentID = studentid;
    }

//    public NotificationDAO(SQLiteDatabase sqdb, Context context, HomeFrag.NotificationCountViewModel mModel){
//        this.sqdb  = sqdb;
//        this.context = context;
//        this.mModel = mModel;
//
//
//        //Get the ViewModel
//
//    }


    public SQLiteDatabase getWritableSQLiteConnection(){

      return openHelper.getWritableDatabase();

    }

    public SQLiteDatabase getReadableSQLiteConnection(){

        return openHelper.getReadableDatabase();

    }




    public int insertNotifications(List<Notification> notificationList){

        long id = 0;
       SQLiteDatabase sqdb = getWritableSQLiteConnection();



        for(int i = 0; i < notificationList.size(); i++){
            ContentValues insertContent = new ContentValues();
            insertContent.put(LocalDBContract.NotificationTB.MSGID, Integer.valueOf(notificationList.get(i).getMsgID()) );
            insertContent.put(LocalDBContract.NotificationTB.MSG, notificationList.get(i).getMsg());
            insertContent.put(LocalDBContract.NotificationTB.DATESENT, notificationList.get(i).getDateSent());
            insertContent.put(LocalDBContract.NotificationTB.STUDID, notificationList.get(i).getStudID());

            id = sqdb.insert(LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME, null, insertContent);
        }

        //testing
        List<Notification> list = getNotificationsList();


        // notificationList.clear();

//        for(Notification notification : notificationList){
//
//
//
//        }

        sqdb.close();
        return Integer.parseInt(String.valueOf(id));

    }

    public int deleteAllNotifications(){


        return getWritableSQLiteConnection().delete( LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME,
                null,null);
        //sqdb.execSQL(DELETE_NOTIFICATION_QUERY);

    }

    public int deleteOneNotification(int notificationID, String StudentID) {
        //very vulnerable to sql injection
        //String deletQuery = "DELETE FROM " +LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME + " WHERE " + LocalDBContract.NotificationTB.MSGID;
        // getReadableSQLiteConnection().execSQL(deletQuery);

        final String tablename = LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME;
        final String whereClause = LocalDBContract.NotificationTB.MSGID + " = ? " +
                " AND " +
                LocalDBContract.NotificationTB.STUDID + " = ? ";
        final String[] whereArgs = new String[]{String.valueOf(notificationID),StudentID};

        return getReadableSQLiteConnection().delete(tablename,whereClause, whereArgs);
    }




    public int getNotificationLastID(String StudentID){
        //TESTING
       // List<Notification> notificationList = getNotificationsList();

        int lastNotificationID = 0;
        Cursor cursor = getReadableSQLiteConnection().rawQuery("SELECT MAX(" + LocalDBContract.NotificationTB.MSGID + ") AS LastNotificationID FROM " + LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME + " WHERE " + LocalDBContract.NotificationTB.STUDID + " = ? ", new String[]{StudentID});
        if(cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                lastNotificationID = cursor.getInt(cursor.getColumnIndex("LastNotificationID"));
            }
        }

        if(cursor != null)
            cursor.close();

        return lastNotificationID;

    }

    public List<Notification> getNotificationsList(){

        List<Notification> notificationList = new ArrayList<>();
        Notification notification;
        Cursor cursor = getReadableSQLiteConnection().query(LocalDBContract.NotificationTB.NOTIFICAITON_TB_NAME,
                null,
                LocalDBContract.NotificationTB.STUDID + " = ? ",
                new String[]{mainStudentID},null,null,null,null);


        while(cursor.moveToNext()){

             notification  = new Notification();
                notification.setMsgID((cursor.getInt(cursor.getColumnIndex(LocalDBContract.NotificationTB.MSGID))));
                notification.setMsg((cursor.getString(cursor.getColumnIndex(LocalDBContract.NotificationTB.MSG))));
                notification.setDateSent((cursor.getString(cursor.getColumnIndex(LocalDBContract.NotificationTB.DATESENT))));
                notificationList.add(notification);
        }

        cursor.close();

        return notificationList;
    }




}
