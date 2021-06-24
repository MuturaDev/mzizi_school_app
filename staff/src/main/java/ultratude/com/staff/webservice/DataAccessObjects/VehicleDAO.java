package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.spinnermodel.FuelTypeSpinner;
import ultratude.com.staff.spinnermodel.ServiceTypeSpinner;
import ultratude.com.staff.spinnermodel.VehicleSpinner;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.Vehicle;

/**
 * Created by James on 12/01/2019.
 */

public class VehicleDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;


    public VehicleDAO(Context mContext){
        this.mContext = mContext;
       sqhelp  = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveVehicle(List<Vehicle> vehicleList){
        long id = 0;


            try{
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if(db.isOpen()) {


                    deleteVehicles();

                    for (Vehicle vehicle : vehicleList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicle.VEHICLEID, vehicle.getVehicleID().trim());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicle.NUMBERPLATE, vehicle.getNumberPlate().trim());
                        contentValues.put(ConnSQLiteContract.PortalSaveVehicle.LASTMILEAGE, vehicle.getLastMileage().trim());


                        id += db.insertOrThrow(ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE, null, contentValues);

                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }


        //return id;

        long afterSaveCount = 0;

            if(getVehicleCursor() != null){
               afterSaveCount = (long) getVehicleCursor().getCount();
            }

        return afterSaveCount;
    }


    public  int getLastMileage(VehicleSpinner vehicleSpinner){
        int returnValue = 0;

        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            Cursor cursor =   db.query(ConnSQLiteContract.PortalSaveVehicleFueling.PORTALSAVEVEHICLEFUELING_TABLE,
                    new String[]{ConnSQLiteContract.PortalSaveVehicle.LASTMILEAGE},
                    ConnSQLiteContract.PortalSaveVehicle.NUMBERPLATE + " = ? ",
                    new String[]{vehicleSpinner.getNumberPlate()},
                    null,null,null);
            if(cursor.moveToNext()){
                 returnValue = cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle.LASTMILEAGE));
            }

            return returnValue;

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return returnValue;
    }


    public Cursor getVehicleCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE, null, null, null, null, null, null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            db.close();
           // sqhelp.closeDatabase();

        }

        return cursor;


    }


    //delete each row
    public void deleteOneVehicle(int[] ultraDataIDs) {

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE + " WHERE " + ConnSQLiteContract.PortalSaveVehicle._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
               sqhelp.closeDatabase();
            }

    }




    public List<Object> getVehicleList(){
        List<Vehicle> vehicleList = new ArrayList<>();

        Cursor vehicleCursor = getVehicleCursor();
        int[] vehicleIDs = new int[vehicleCursor.getCount()];

        try{

            int count = 0;
            while(vehicleCursor.moveToNext()){

                int id = vehicleCursor.getInt(vehicleCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle._ID));
                vehicleIDs[count] = id;
                count++;

                Vehicle vehicle = new Vehicle(
                        vehicleCursor.getString(vehicleCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle.VEHICLEID)),
                        vehicleCursor.getString(vehicleCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle.NUMBERPLATE)),
                        vehicleCursor.getString(vehicleCursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle.LASTMILEAGE))

                );
                vehicleList.add(vehicle);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(vehicleList);//0 List<DutyRoster>
        list.add(vehicleIDs);//1 int[]

        return list;
    }







    public ArrayList<VehicleSpinner> getVehicleIDWithNumberPlate(){

        ArrayList<VehicleSpinner> vehicleSpinnerArrayList = new ArrayList<>();
        VehicleSpinner vehicle = new VehicleSpinner(0, "Select Vehicle");
        vehicleSpinnerArrayList.add(vehicle);//0

        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            String table = ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE;
            String[] columns = {ConnSQLiteContract.PortalSaveVehicle.VEHICLEID, ConnSQLiteContract.PortalSaveVehicle.NUMBERPLATE};
            String selection = null;
            String[] selectionArgs = null;
            String groupBy = null;
            String having = null;
            String orderBy = null;

            Cursor cursor = db.query(table, columns, selection,selectionArgs, groupBy, having, orderBy);


            while(cursor.moveToNext()){
                String numberPlate = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle.NUMBERPLATE));
                int vehicleID = cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveVehicle.VEHICLEID));

                VehicleSpinner vehicleSpinner = new VehicleSpinner(vehicleID, numberPlate);

                vehicleSpinnerArrayList.add(vehicleSpinner);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return vehicleSpinnerArrayList;
    }



    public  ArrayList<ServiceTypeSpinner> getServiceTypeList(){
        ArrayList<ServiceTypeSpinner> spinnerArrayList = new ArrayList<>();

        ServiceTypeSpinner serviceTypeSpinner0 = new ServiceTypeSpinner(0, "Select Type");
        spinnerArrayList.add(serviceTypeSpinner0);

        ServiceTypeSpinner serviceTypeValues1 = new ServiceTypeSpinner(1,"Major Service");
        spinnerArrayList.add(serviceTypeValues1);

        ServiceTypeSpinner serviceTypeValues2 = new ServiceTypeSpinner(2,"Minor Service");
        spinnerArrayList.add(serviceTypeValues2);


        return spinnerArrayList;
    }


    public ArrayList<FuelTypeSpinner> getFuelTypeList(){
        FuelTypeSpinner fuelTypeSpinner0 = new FuelTypeSpinner(3, "Select a Fule Type");
        FuelTypeSpinner fuelTypeSpinner1 = new FuelTypeSpinner(1, "Diesel");
        FuelTypeSpinner fuelTypeSpinner2 = new FuelTypeSpinner(2, "Petrol");
        ArrayList<FuelTypeSpinner> fuelTypeSpinnerArrayList = new ArrayList<>();
        fuelTypeSpinnerArrayList.add(fuelTypeSpinner0);
        fuelTypeSpinnerArrayList.add(fuelTypeSpinner1);
        fuelTypeSpinnerArrayList.add(fuelTypeSpinner2);


        return fuelTypeSpinnerArrayList;
    }





    public void deleteVehicles(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            db.delete(ConnSQLiteContract.PortalSaveVehicle.PORTALSAVEVEHICLE_TABLE, null, null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }
}


