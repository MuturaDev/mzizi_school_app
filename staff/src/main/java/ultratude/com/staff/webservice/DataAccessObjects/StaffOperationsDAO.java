package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.StaffOperation;

/**
 * Created by James on 02/05/2019.
 */

public class StaffOperationsDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public StaffOperationsDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveStaffOperation(List<StaffOperation> staffOperationList){
        Cursor cursor = getStaffOperationsCursor();
        long saved = 0;
        if(cursor != null) {
            long countBeforeSaving = (long) cursor.getCount();

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    for (StaffOperation staffOperation : staffOperationList) {
                        ContentValues contentHolder = new ContentValues();
                        contentHolder.put(ConnSQLiteContract.PortalStaffOperations.STAFFID, staffOperation.getStaffID());
                        contentHolder.put(ConnSQLiteContract.PortalStaffOperations.STAFF_OPERATION, staffOperation.getOperations());

                        db.insert(ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE, null, contentHolder);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

            long countAfterSaving = (long) getStaffOperationsCursor().getCount();

             saved = countAfterSaving - countBeforeSaving;
        }

        return saved;
    }

    private Cursor getStaffOperationsCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
//            db.close();
        }

        return cursor;
    }

    public List<StaffOperation> getStaffOperationsList(){
        List<StaffOperation> staffOperationList = new ArrayList<>();

        Cursor staffOperationCursor = getStaffOperationsCursor();
        try{
            int count = 0;

            while(staffOperationCursor.moveToNext()){
                StaffOperation staffOperation = new StaffOperation(
                        staffOperationCursor.getString(staffOperationCursor.getColumnIndex(ConnSQLiteContract.PortalStaffOperations.STAFFID)),
                        staffOperationCursor.getString(staffOperationCursor.getColumnIndex(ConnSQLiteContract.PortalStaffOperations.STAFF_OPERATION))
                );

                staffOperationList.add(staffOperation);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return staffOperationList;
    }


    public boolean deleteStaffOperations(){

            int affRaw = 0;
            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    affRaw = db.delete(ConnSQLiteContract.PortalStaffOperations.PORTALSTAFFOPERATIONS_TABLE, null, null);

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



}
