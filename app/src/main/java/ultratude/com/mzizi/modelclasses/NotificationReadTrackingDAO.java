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

public class NotificationReadTrackingDAO {

    private Context mContext;
    private ConnSQLiteOpenHelper openHelper;
    private String mainStudentID;

    public NotificationReadTrackingDAO(Context mContext){
        this.mContext = mContext;
        this.openHelper = new ConnSQLiteOpenHelper(mContext.getApplicationContext());

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

    public void insertNotificationReadTracking(NotificationReadTracking notificationReadTracking){

        long id = 0;
        SQLiteDatabase db = getWritableSQLiteConnection();
        if(db.isOpen()) {
            try {
                notificationReadTracking.setStudID(Integer.valueOf(mainStudentID));

                ContentValues insertRead = new ContentValues();
                insertRead.put(LocalDBContract.NotificationReadTracking.STUDENTID, notificationReadTracking.getStudentID());
                insertRead.put(LocalDBContract.NotificationReadTracking.DATEREAD, notificationReadTracking.getDateOpened());
                insertRead.put(LocalDBContract.NotificationReadTracking.STUDID, notificationReadTracking.getStudID());

                id = db.insert(LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE, null, insertRead);
                Log.d(mContext.getPackageName().toUpperCase(), "ReadTracking: " + String.valueOf(id));

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }
    }

    public List<Object> getNotificationReadTracking(){
        List<Object> returnList = new ArrayList<>();
        List<NotificationReadTracking> notificationReadTrackingList = new ArrayList<>();

        SQLiteDatabase db = getReadableSQLiteConnection();
        if(db.isOpen()) {

            try {
                Cursor cursor = db.query(LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE,
                        null, LocalDBContract.NotificationReadTracking.STUDID + " = ? ",
                        new String[]{mainStudentID}, null, null, null, null);
                int[] notificationReadIDs = new int[cursor.getCount()];
                int count = 0;
                while (cursor.moveToNext()) {

                    int id = cursor.getInt(cursor.getColumnIndex(LocalDBContract.NotificationReadTracking._ID));
                    notificationReadIDs[count] = id;
                    count++;

                    NotificationReadTracking notificationReadTracking = new NotificationReadTracking(
                            cursor.getString(cursor.getColumnIndex(LocalDBContract.NotificationReadTracking.STUDENTID)),
                            cursor.getString(cursor.getColumnIndex(LocalDBContract.NotificationReadTracking.DATEREAD))
                    );

                    notificationReadTrackingList.add(notificationReadTracking);
                    Log.d(mContext.getPackageName().toUpperCase(), "ReadTracking: " + notificationReadTracking.toString());
                }

                returnList.add(notificationReadTrackingList);//List<NotificationReadTracking> 0
                returnList.add(notificationReadIDs);//int[] 1

                if (!cursor.isClosed()) {
                    cursor.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }

        return returnList;
    }


    public void deleteOneNotificationReadTracking(int[] Ids, String StudentID){
        SQLiteDatabase db = getWritableSQLiteConnection();
        if(db.isOpen()) {
            try {


                if (db.isOpen()) {

                    for (int i : Ids) {
                        String query = "DELETE FROM " + LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE +
                                " WHERE " + LocalDBContract.NotificationReadTracking._ID + " = " + i +
                                " AND " + LocalDBContract.NotificationReadTracking.STUDID + " = " + StudentID + ";";
                        db.execSQL(query);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }
    }

    public int deleteAllNotificationsReadTracking(){
        SQLiteDatabase db = getWritableSQLiteConnection();
        int id = 0;
        try{
            if(db.isOpen()){
              id = db.delete(LocalDBContract.NotificationReadTracking.NOTIFICATIONREADTRACKING_TABLE,
                      null,null);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return id;
    }

}
