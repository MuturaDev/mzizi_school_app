package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.TripLatLong;

/**
 * Created by James on 11/05/2019.
 */

public class TripLatLongDAO {



    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;


    public TripLatLongDAO(Context context){

        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }

    public long saveTripLatLong(TripLatLong latLong){

        long id = 0l;

        Log.d(context.getPackageName().toUpperCase(), "Before saving: " + latLong.toString());


        try {
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();


            if (db != null && db.isOpen()) {


                Cursor cursor = db.query(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE, null, null, null, null, null, null, null);

                if (cursor.moveToNext()) {//with data , delet first
                    // Toast.makeText(context, String.valueOf(deleteLatLong()), Toast.LENGTH_LONG).show();


                   // deleteLatLong();
                    ContentValues savelatlong = new ContentValues();
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LATITUDE, latLong.getLatitude());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LONGITUDE, latLong.getLongitude());

                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.STAFF_ID, latLong.getStaffID());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.DATE_RECORDED, latLong.getDateRecorded());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.VEHICLE_ID, latLong.getVehicleID());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.APP_CODE, latLong.getAppCode());


                    id = db.insert(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE, null, savelatlong);

                    Log.d(context.getPackageName().toUpperCase(), "Save Event: " + id);


                    if (id > 0) {

                        //Toast.makeText(context, getLatLong().toString(), Toast.LENGTH_LONG).show();
                    }


                } else {//without data you dont have to delete first


                    ContentValues savelatlong = new ContentValues();

                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LATITUDE, latLong.getLatitude());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LONGITUDE, latLong.getLongitude());

                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.STAFF_ID, latLong.getStaffID());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.DATE_RECORDED, latLong.getDateRecorded());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.VEHICLE_ID, latLong.getVehicleID());
                    savelatlong.put(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.APP_CODE, latLong.getAppCode());
                   // deleteLatLong();
                    id = db.insert(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE, null, savelatlong);

                    Log.d(context.getPackageName().toUpperCase(), "Save Event: " + id);

                    if (id > 0) {
                        //  Toast.makeText(context, getLatLong().toString(), Toast.LENGTH_LONG).show();
                    }

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }


        return id;


    }


    public List<Object> getTripLatLong(){

        List<TripLatLong> tripLatLongList = new ArrayList<>();
        List<Object> returnListObject = new ArrayList<>();

            try{

                SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

                if(db.isOpen()) {

                    Cursor cursor = db.query(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE, null, null, null, null, null, null, null);
                    int[] ids = new int[cursor.getCount()];
                    int count = 0;
                    while (cursor.moveToNext()) {

                        ids[count] = cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude._ID));

                        TripLatLong latlong = new TripLatLong();

                        latlong.setLongitude(cursor.getDouble(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LONGITUDE)));
                        latlong.setLatitude(cursor.getDouble(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.LATITUDE)));

                        latlong.setStaffID(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.STAFF_ID)));
                        latlong.setDateRecorded(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.DATE_RECORDED)));
                        latlong.setVehicleID(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.VEHICLE_ID)));
                        latlong.setAppCode(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.APP_CODE)));

                        tripLatLongList.add(latlong);

                        count++;
                    }

                    returnListObject.add(tripLatLongList);//0
                    returnListObject.add(ids);
                }



            }catch (Exception ex){
                ex.printStackTrace();
                //this closes the connection and other query being request requires this connection
            }finally {
                sqhelp.closeDatabase();
            }


        Log.d(context.getPackageName().toUpperCase(), " Get List Event: " + tripLatLongList.toString());



        return returnListObject;
    }

    public boolean deleteAllTripLatLong(){
        int affRaw = 0;
        try{
            SQLiteDatabase  db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                affRaw = db.delete(ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE,null,null);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        if(affRaw > 0){
            return true;
        }
        return false;
    }

    public void deleteOneTripLatLong(int[] ids){
        try {

            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            if (db.isOpen()) {
                for (int i : ids) {
                    String query = "DELETE FROM " + ConnSQLiteContract.UpdatedTripLatitudeAndLongitude.UPDATED_TRIP_LATITUDE_AND_LONGITUDE + " WHERE " + ConnSQLiteContract.UpdatedTripLatitudeAndLongitude._ID + " = " + i + ";";
                    db.execSQL(query);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            sqhelp.closeDatabase();
        }
    }

}
