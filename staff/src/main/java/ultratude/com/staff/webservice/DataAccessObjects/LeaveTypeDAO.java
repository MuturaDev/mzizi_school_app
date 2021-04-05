package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.spinnermodel.LeaveTypeSpinner;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.LeaveType;

/**
 * Created by James on 24/01/2019.
 */

public class LeaveTypeDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public LeaveTypeDAO(Context context){
        this.mContext  = context;

        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }


    public long saveLeaveTypes(List<LeaveType> leaveTypeList){
        long id = 0;


        Cursor cursor = getLeaveTypeCursor();

        long saved = 0;
        if(cursor != null) {


            long countBeforeSaving = (long) cursor.getCount();

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {


                    deleteLeaveType();

                    for (LeaveType leaveType : leaveTypeList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetLeaveType.LEAVETYPEID, leaveType.getLeaveTypeID().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetLeaveType.LEAVETYPENAME, leaveType.getLeavetTypeName().trim());


                        id += db.insertOrThrow(ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE, null, contentValues);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

            long countAfterSaving = (long) getLeaveTypeCursor().getCount();

             saved = countAfterSaving - countBeforeSaving;

        }
        return saved;
    }


    public Cursor getLeaveTypeCursor(){
        Cursor cursor = null;
        try{

            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }

        return cursor;
    }


    //delete each row
    public void deleteOneLeaveType(int[] ultraDataIDs) {

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE + " WHERE " + ConnSQLiteContract.PortalGetLeaveType._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

    }


    public ArrayList<LeaveTypeSpinner> getLeaveTypeSpinner(){

        ArrayList<LeaveTypeSpinner> leaveTypeSpinnerArrayList = new ArrayList<>();

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    Cursor cursor = db.query(ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE, null, null, null, null, null, null);
                    while (cursor.moveToNext()) {
                        LeaveTypeSpinner leaveTypeSpinner = new LeaveTypeSpinner(
                                cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalGetLeaveType.LEAVETYPEID)),
                                cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetLeaveType.LEAVETYPENAME))
                        );

                        leaveTypeSpinnerArrayList.add(leaveTypeSpinner);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

        return leaveTypeSpinnerArrayList;
    }






    public void deleteLeaveType(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalGetLeaveType.PORTALGETLEAVETYPE_TABLE,null, null);


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }



}
