package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.spinnermodel.AttendanceRollCallSessionSpinner;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.RollCallSession;

/**
 * Created by James on 23/01/2019.
 */

public class RollCallSessonsDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public RollCallSessonsDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }


    public long saveRollCallSessons(List<RollCallSession> rollCallSessionList){
        long id = 0L;
        long saved = 0;


        Cursor cursor = getRollCallSessionCursor();
        if(cursor != null) {


            long countBeforeSaving = (long) cursor.getCount();


            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    deleteAllRollCallSesson();

                    for (RollCallSession session : rollCallSessionList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetRollCallSessions.SESSIONID, session.getSessionID().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetRollCallSessions.SESSIONNAME, session.getSessionName().trim());


                        id += db.insertOrThrow(ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE, null, contentValues);

                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();

            } finally {
                sqhelp.closeDatabase();
            }

            long countAfterSaving = (long) getRollCallSessionCursor().getCount();

            saved = countAfterSaving - countBeforeSaving;
        }

        return saved;

    }




    public Cursor getRollCallSessionCursor(){
        Cursor cursor = null;
        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
          //  sqhelp.closeDatabase();
//            db.close();
        }
        return cursor;

    }







    //delete each row
    public void deleteOneAttendanceRollCallSessions(int[] ultraDataIDs) {

        SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

        if (db.isOpen()) {
            try {

                for (int i : ultraDataIDs) {
                    String query = "DELETE FROM " + ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE + " WHERE " + ConnSQLiteContract.PortalGetRollCallSessions._ID + " = " + i + ";";
                    db.execSQL(query);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                db.close();
            }
        }
    }


    public int getAttendanceRollCallSpinnerID(String session){

        ArrayList<AttendanceRollCallSessionSpinner> attendanceRollCallSessionSpinnersList = getAttendanceRollCallSessionSpinnerList();

        Log.d(mContext.getPackageName().toUpperCase(), session);

        int returnID = 0;
//        for(AttendanceRollCallSessionSpinner rollSession : attendanceRollCallSessionSpinnersList){
//            if(session.equals(String.valueOf(rollSession.getSessionID()))){
//                returnID = rollSession.getSessionID();
//                Log.d(mContext.getPackageName().toUpperCase(), attendanceRollCallSessionSpinnersList.toString());
//                break;
//            }
//        }

        for(int i = 0; i < attendanceRollCallSessionSpinnersList.size(); i++){

            if(Integer.valueOf(session) == attendanceRollCallSessionSpinnersList.get(i).getSessionID()){
                returnID = attendanceRollCallSessionSpinnersList.get(i).getSessionID();
                Log.d(mContext.getPackageName().toUpperCase(), attendanceRollCallSessionSpinnersList.toString());
                break;
            }
        }

        return returnID;
    }




    public ArrayList<AttendanceRollCallSessionSpinner> getAttendanceRollCallSessionSpinnerList(){
       ArrayList<AttendanceRollCallSessionSpinner> rollCallSessionsList = new ArrayList<>();
        AttendanceRollCallSessionSpinner rollCall2 = new AttendanceRollCallSessionSpinner(
                0,"Select Session"
        );
        rollCallSessionsList.add(rollCall2);

          try {
              SQLiteDatabase db = sqhelp.getWritableDatabase();

              if (db.isOpen()) {

                  Cursor cursor = getRollCallSessionCursor();
                  while (cursor.moveToNext()) {
                      AttendanceRollCallSessionSpinner rollCall = new AttendanceRollCallSessionSpinner(
                              cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalGetRollCallSessions.SESSIONID)),
                              cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetRollCallSessions.SESSIONNAME))
                      );



                      rollCallSessionsList.add(rollCall);
                  }

              }
          }catch (Exception ex){
              ex.printStackTrace();
          }finally {
              sqhelp.closeDatabase();
          }



      return rollCallSessionsList;
    }




    public void deleteAllRollCallSesson(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalGetRollCallSessions.PORTALGETROLLCALLSESSIONS_TABLE, null,null);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }


}
