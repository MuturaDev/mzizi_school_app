package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.VehicleServicing;

/**
 * Created by James on 11/01/2019.
 */

public class VehicleServicingDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public VehicleServicingDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveVehicleServicing(VehicleServicing vehicleServicing){
        long id = 0;

        Cursor cursor =  getVehicleServicingCursor();

        long saved = 0;

        if(cursor != null) {

            long countBeforeSaving = (long) cursor.getCount();


            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.VEHICLE_ID, vehicleServicing.getVehicleID());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICEDBY, vehicleServicing.getServicedBy());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICETYPE, vehicleServicing.getServiceType());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.DATESERVICED, vehicleServicing.getDateServiced());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.MILEAGEBEFORE, vehicleServicing.getMileageBefore());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICEREPORT, vehicleServicing.getServiceReport());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.NEXTSERVICEMILEAGE, vehicleServicing.getNextServiceMileage());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICECOST, vehicleServicing.getServiceCost());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.ADDEDBY, vehicleServicing.getAddedBy());
                    contentValues.put(ConnSQLiteContract.PortalSaveVehicleServicing.APPCODE, vehicleServicing.getAppCode());

                    id = db.insertOrThrow(ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE, null, contentValues);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


            long countAfterSaving = (long) getVehicleServicingCursor().getCount();

            saved = (countAfterSaving - countBeforeSaving);
        }

        return saved;
    }


    public Cursor getVehicleServicingCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
//            db.close();
        }

        return cursor;


    }


    //delete each row
    public void deleteOneVehicleServicingCursor(int[] ultraDataIDs) {

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {
                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE + " WHERE " + ConnSQLiteContract.PortalSaveVehicleServicing._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

    }




    public List<Object> getVehicleServicingList() {
        List<VehicleServicing> vehicleServicingList = new ArrayList<>();

        Cursor vehicleServicingCursor = getVehicleServicingCursor();
        int[] vehicleServicingIDs = new int[vehicleServicingCursor.getCount()];

        try {

            int count = 0;
            while (vehicleServicingCursor.moveToNext()) {

                int id = vehicleServicingCursor.getInt(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing._ID));
                vehicleServicingIDs[count] = id;
                count++;

                VehicleServicing vehicleServicing = new VehicleServicing(
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.VEHICLE_ID)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICEDBY)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICETYPE)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.DATESERVICED)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.MILEAGEBEFORE)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICEREPORT)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.NEXTSERVICEMILEAGE)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.SERVICECOST)),
                        vehicleServicingCursor.getString(vehicleServicingCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicleServicing.APPCODE))
                );
                vehicleServicingList.add(vehicleServicing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(vehicleServicingList);//0 List<DutyRoster>
        list.add(vehicleServicingIDs);//1 int[]

        return list;

    }


    public void deleteVehicleServicing(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalSaveVehicleServicing.PORTALSAVEVEHICLESERVICING_TABLE, null, null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }
    }


}
