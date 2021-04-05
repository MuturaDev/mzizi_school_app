package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;

/**
 * Created by James on 11/01/2019.
 */

public class DutyRosterDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public DutyRosterDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }


    public long saveDutyRoster(List<DutyRoster> dutyRosterList){
        long id = 0L;

        Cursor cursor = getDutyRosterCursor();
        long saved = 0;
        if(cursor != null) {

            long countBeforeSaving = (long) cursor.getCount();


            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    deleteDutyRoster();

                    for (DutyRoster dutyRoster : dutyRosterList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetDutyRoster.STAFFNAME, dutyRoster.getStaffName().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetDutyRoster.YEAR, dutyRoster.getYear().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetDutyRoster.TERMNAME, dutyRoster.getTermName().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetDutyRoster.DUTYWEEK, dutyRoster.getDutyWeek().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetDutyRoster.CURRENTWEEK, dutyRoster.getCurrentWeek().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetDutyRoster.PHONENUMBER, dutyRoster.getPhoneNumber().trim());

                        id += db.insertOrThrow(ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE, null, contentValues);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


            long countAfterSaving = (long) getDutyRosterCursor().getCount();

             saved = countAfterSaving - countBeforeSaving;
        }

        return saved;

    }


    public String getDutyRosterCurrentWeek(){

        String currentWeek = "-1";


        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            Cursor cursor = db.query(true, ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE, new String[]{ConnSQLiteContract.PortalGetDutyRoster.CURRENTWEEK}, null,null,null,null,null,null);

            if(cursor.moveToFirst()){
                currentWeek = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.CURRENTWEEK));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return currentWeek;
    }



    public Cursor getDutyRosterCursor(){
        Cursor cursor = null;

        try{ SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }

        return cursor;


    }


    //delete each row
    public void deleteOneDutyRoster(int[] IDs) {


            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    for (int i : IDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE + " WHERE " + ConnSQLiteContract.PortalGetDutyRoster._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

    }

    public boolean  deleteAllDutyRoster(){


            int affRaw = 0;
            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    affRaw = db.delete(ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE, null, null);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

            if(affRaw > 0){
                return true;
            }


        return false;
    }




    public List<Object> getDutyRosterList(){
        List<DutyRoster> dutyRosterList = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        long saved = 0;
        Cursor dutyRosterCursor = getDutyRosterCursor();
        if(dutyRosterCursor != null) {
            int[] dutyRosterIDs = new int[dutyRosterCursor.getCount()];

            try {

                int count = 0;
                while (dutyRosterCursor.moveToNext()) {

                    int id = dutyRosterCursor.getInt(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster._ID));
                    dutyRosterIDs[count] = id;
                    count++;

                    DutyRoster dutyRoster = new DutyRoster(
                            dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.STAFFNAME)),
                            dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.YEAR)),
                            dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.TERMNAME)),
                            dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.DUTYWEEK)),
                            dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.CURRENTWEEK)),
                            dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster.PHONENUMBER))

                    );
                    dutyRosterList.add(dutyRoster);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            list.add(dutyRosterList);//0 List<DutyRoster>
            list.add(dutyRosterIDs);//1 int[]
        }

        return list;
    }




    private void deleteDutyRoster(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalGetDutyRoster.PORTALGETDUTYROSTER_TABLE, null,null);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }


}
