package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.VehicleFueling;

/**
 * Created by James on 11/01/2019.
 */

public class VehicleFuelingDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public VehicleFuelingDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }


    public long saveVehicleFuelingDAO(VehicleFueling fueling){
        long id = 0;
        long saved = 0;
        Cursor cursor = getVehicleFuelingCursor();
        if(cursor != null) {


            if (cursor != null) {

                long countBeforeSaving = (long) cursor.getCount();

                try {
                    SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                    if (db.isOpen()) {

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.VEHICLE_ID, fueling.getVehicleID());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.FUELEDBY, fueling.getFueledBy());
//                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.FUELTYPE, fueling.getFuelType());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.DATEFUELED, fueling.getDateFueled());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.MILEAGEBEFORE, fueling.getMileageBefore());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.QUANTITY, fueling.getQuantity());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.UNITPRICE, fueling.getUnitPrice());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.ADDEDBY, fueling.getAddedBy());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicleFueling.APPCODE, fueling.getAppCode());

                        id = db.insertOrThrow(ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE, null, contentValues);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    sqhelp.closeDatabase();
                }

                long countAfterSaving = (long) getVehicleFuelingCursor().getCount();


                saved = (countAfterSaving - countBeforeSaving);
            }
        }

        return saved;
    }



    public Cursor getVehicleFuelingCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            db.close();
           // sqhelp.closeDatabase();

        }

        return cursor;


    }


    //delete each row
    public void deleteOneVehicleFuelingCursor(int[] ultraDataIDs) {


            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE + " WHERE " + ConnSQLiteContract.UltraDataDef._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

    }




    public List<Object> getVehicleFuelingList(){
        List<VehicleFueling> vehicleFuelingList = new ArrayList<>();

        Cursor vehicleFuelingCursor = getVehicleFuelingCursor();
        int[] vehicleFuelingIDs = new int[vehicleFuelingCursor.getCount()];

        try{

            int count = 0;
            while(vehicleFuelingCursor.moveToNext()){

                int id = vehicleFuelingCursor.getInt(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling._ID));
                vehicleFuelingIDs[count] = id;
                count++;

                VehicleFueling vehicleFueling = new VehicleFueling(
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.VEHICLE_ID)),
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.FUELEDBY)),
//                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.FUELTYPE)),
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.DATEFUELED)),
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.MILEAGEBEFORE)),
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.QUANTITY)),
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.UNITPRICE)),
                        vehicleFuelingCursor.getString(vehicleFuelingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleFueling.APPCODE))
                );
                vehicleFuelingList.add(vehicleFueling);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(vehicleFuelingList);//0 List<DutyRoster>
        list.add(vehicleFuelingIDs);//1 int[]

        return list;
    }


    public void deleteVehicleFueling(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE, null, null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }
}
