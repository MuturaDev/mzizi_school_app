package ultratude.com.mzizi.modelclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.notificationpg.NotificationSQDB.ConnSQLiteOpenHelper;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;

/**
 * Created by James on 21/05/2019.
 */

public class ReadNotReadNotificationDAO {

    private Context mContext;
    private ConnSQLiteOpenHelper openHelper;

    private String mainStudentID;

    public ReadNotReadNotificationDAO(Context mContext) {
        this.mContext = mContext;
        this.openHelper = new ConnSQLiteOpenHelper(mContext);
        ParentMziziDatabase db = ParentMziziDatabase.getInstance(mContext.getApplicationContext());
        String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
        if(studentid == null){
            studentid  = "0";
        }
        mainStudentID = studentid;
    }

    public SQLiteDatabase getWritableSQLiteConnection(){
        return openHelper.getWritableDatabase();

    }

    public SQLiteDatabase getReadableSQLiteConnection(){

        return openHelper.getReadableDatabase();

    }

    public void inserNotificationReadTracking(List<ReadNotReadNotification> newNotification){


        long id = 0;
        SQLiteDatabase db = getWritableSQLiteConnection();
        try{
            for(ReadNotReadNotification note : newNotification){
               // note.setStudID(Integer.valueOf(mainStudentID));
                ContentValues insertRead = new ContentValues();
                insertRead.put(LocalDBContract.ReadNotReadNotifications.MSGID, note.getMsgID());
                insertRead.put(LocalDBContract.ReadNotReadNotifications.STUDID, note.getStudID());
                id = db.insert(LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE, null, insertRead);
                Log.d(mContext.getPackageName().toUpperCase(), "After saving ReadNotRead"+ String.valueOf(note));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            db.close();
        }
    }


    //for testing
    public List<ReadNotReadNotification> getForSpcificStudentReadNotReadNotification(String studentid){
        SQLiteDatabase db = getWritableSQLiteConnection();
        List<ReadNotReadNotification> readNotReadNotificationList = new ArrayList<>();
        if(db.isOpen()) {

            try {
                Cursor cursor = db.query(LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE, null,
                        LocalDBContract.ReadNotReadNotifications.STUDID  + " = ? ",
                        new String[]{studentid}, null, null, null, null);
                while (cursor.moveToNext()) {
                    ReadNotReadNotification readNotification = new ReadNotReadNotification(
                            cursor.getString(cursor.getColumnIndex(LocalDBContract.ReadNotReadNotifications.MSGID))
                    );
                    readNotification.setStudID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(LocalDBContract.ReadNotReadNotifications.STUDID))));
                    readNotReadNotificationList.add(readNotification);
                }

                if (!cursor.isClosed()) {
                    cursor.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }

        return readNotReadNotificationList;
    }


    public List<ReadNotReadNotification> getReadNotReadNotification(){
        SQLiteDatabase db = getWritableSQLiteConnection();
        List<ReadNotReadNotification> readNotReadNotificationList = new ArrayList<>();
        if(db.isOpen()) {

            try {
                Cursor cursor = db.query(LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE, null,
                        LocalDBContract.ReadNotReadNotifications.STUDID  + " = ? ",
                        new String[]{mainStudentID}, null, null, null, null);
                while (cursor.moveToNext()) {
                    ReadNotReadNotification readNotification = new ReadNotReadNotification(
                            cursor.getString(cursor.getColumnIndex(LocalDBContract.ReadNotReadNotifications.MSGID))
                    );
                    readNotification.setStudID(Integer.valueOf(cursor.getString(cursor.getColumnIndex(LocalDBContract.ReadNotReadNotifications.STUDID))));
                    readNotReadNotificationList.add(readNotification);
                }

                if (!cursor.isClosed()) {
                    cursor.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }

        return readNotReadNotificationList;
    }


    public int deleteReadNotReadNotification(){
        int id = 0;
        try{
            SQLiteDatabase db = getWritableSQLiteConnection();
           id = db.delete(LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE,
                   null,
                   null);
            Log.d(mContext.getPackageName().toUpperCase(), String.valueOf(id));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return id;
    }

    public int deleteForReadNotReadNotification(String StudentID){
        int id = 0;
        try{
            SQLiteDatabase db = getWritableSQLiteConnection();
            id = db.delete(LocalDBContract.ReadNotReadNotifications.READNOTREADNOTIFICATIONS_TABLE,
                    LocalDBContract.ReadNotReadNotifications.STUDID + " = ? ",
                    new String[]{StudentID});
            Log.d(mContext.getPackageName().toUpperCase(), String.valueOf(id));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return id;
    }



}
