package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.LatLong;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;

/**
 * Created by James on 20/10/2018.
 */

public class LatLongDAO {


    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;


    public LatLongDAO(Context context){

        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }

    public long saveLatLong(LatLong latLong){

        long id = 0l;


        try {
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();


            if (db != null && db.isOpen()) {


                Cursor cursor = db.query(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE, null, null, null, null, null, null, null);

                if (cursor.moveToNext()) {//with data , delet first
                    // Toast.makeText(context, String.valueOf(deleteLatLong()), Toast.LENGTH_LONG).show();


                        deleteLatLong();
                        ContentValues savelatlong = new ContentValues();
                        savelatlong.put(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LATITUDE, latLong.getLatitude());
                        savelatlong.put(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LONGITUDE, latLong.getLongitude());

                        id = db.insert(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE, null, savelatlong);

                        if (id > 0) {

                            //Toast.makeText(context, getLatLong().toString(), Toast.LENGTH_LONG).show();
                        }


                } else {//without data you dont have to delete first


                        ContentValues savelatlong = new ContentValues();

                        savelatlong.put(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LATITUDE, latLong.getLatitude());
                        savelatlong.put(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LONGITUDE, latLong.getLongitude());
                        deleteLatLong();
                        id = db.insert(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE, null, savelatlong);

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


    public LatLong getLatLong(){
        LatLong latlong = new LatLong();
        SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

        if(db.isOpen()){
            try{

                Cursor cursor =   db.query(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE, null, null,null,null,null,null,null);

                if(cursor.moveToLast()){//the the last know location

                    latlong.setLongitude(cursor.getDouble(cursor.getColumnIndex(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LONGITUDE)));
                    latlong.setLatitude(cursor.getDouble(cursor.getColumnIndex(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.LATITUDE)));

                }

            }catch (Exception ex){
                ex.printStackTrace();
                //this closes the connection and other query being request requires this connection
            }finally {
                sqhelp.closeDatabase();
            }
        }

        return latlong;
    }

    public boolean deleteLatLong(){
        int affRaw = 0;
        try{
            SQLiteDatabase  db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                affRaw = db.delete(ConnSQLiteContract.UpdatedDailyLatitudeAndLongitude.UPDATED_LATITUDE_AND_LONGITUDE,null,null);

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
}
