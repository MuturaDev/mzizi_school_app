package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.paperdb.Paper;
import ultratude.com.staff.spinnermodel.VehicleSpinner;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.LatLong;
import ultratude.com.staff.webservice.ResponseModels.UltraData;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;

public class UltraDataDao {

    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;
    public UltraDataDao(Context context){
        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
        Paper.init(context);

    }

    public Cursor getAllUltradataCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

          cursor = db.query(ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
           // sqhelp.closeDatabase();

//            db.close();
        }

        return cursor;


    }


    public Cursor getAllUltradataDisplayCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // sqhelp.closeDatabase();

//            db.close();
        }

        return cursor;
    }



    public  List<Object> getUltraDataList(){

        List<UltraData> datalist = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Cursor datacursor =   getAllUltradataCursor();
        if(datacursor != null) {
            int[] ultradataIDS = new int[datacursor.getCount()];


            try {

//
                int count = 0;
                while (datacursor.moveToNext()) {
                    UltraData ultradta = new UltraData();
                    //this should be added
                    int id = datacursor.getInt(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef._ID));
                    ultradataIDS[count] = id;
                    count++;

                    String barcodeValue = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STUDENTID_BARCODE));
                    Double latitude = datacursor.getDouble(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.LATITUDE));
                    Double longitude = datacursor.getDouble(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.LONGITUDE));
                    String schoolbus = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.BUS_ACTIVITY));
                    String session = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.SESSION));
                    String vehiclePlate = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE));
                    String busTrip = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.BUSTRIP));
                    String dateScanned = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.DATE_SCANNED));
                    boolean sentStatus = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STATUS_SENT)).equals("true");

                    ultradta.setBarcodeValue(barcodeValue);
                    ultradta.setLatitude(latitude);
                    ultradta.setLongitude(longitude);
                    ultradta.setBus_Activity(schoolbus);
                    ultradta.setBus_Session(session);
                    ultradta.setVehiclePlate(vehiclePlate);
                    ultradta.setBusTrips(busTrip);
                    ultradta.setDateScanned(dateScanned);

                    //prevents sent records from being sent again
                    if(!sentStatus)
                        datalist.add(ultradta);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



            list.add(datalist);//0 List<UltraData>
            list.add(ultradataIDS);//1 int[]
        }


        return list;

    }






    public void addUltradata(String barcode, LatLong currentLocation, String schoolBus, String busSession, VehicleSpinner vehicleSpinner, String busTrip){

            try {

                SQLiteDatabase  db = sqhelp.getWriteableDatabaseConnection();
                if(currentLocation.getLongitude() != 0.0 && currentLocation.getLatitude() != 0.0 && db.isOpen()) {

                    ContentValues cv = new ContentValues();
                    cv.put(ConnSQLiteContract.UltraDataDef.STUDENTID_BARCODE, barcode);
                    //check the difference in time stamp
                    cv.put(ConnSQLiteContract.UltraDataDef.LATITUDE, Double.valueOf(currentLocation.getLatitude()));
                    cv.put(ConnSQLiteContract.UltraDataDef.LONGITUDE, Double.valueOf(currentLocation.getLongitude()));
                    cv.put(ConnSQLiteContract.UltraDataDef.BUS_ACTIVITY, schoolBus);
                    cv.put(ConnSQLiteContract.UltraDataDef.SESSION, busSession);
                    cv.put(ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE, vehicleSpinner.getNumberPlate());
                    cv.put(ConnSQLiteContract.UltraDataDef.BUSTRIP, busTrip);
                    cv.put(ConnSQLiteContract.UltraDataDef.STATUS_SENT, "false");
                    cv.put(ConnSQLiteContract.UltraDataDef.DATE_SCANNED, new SimpleDateFormat("dd MMM YYYY HH:mm:ss").format(Calendar.getInstance().getTime()));

                    Log.d(context.getPackageName().toUpperCase(), "Before adding: " + String.valueOf(currentLocation.getLatitude()) + " " + String.valueOf(currentLocation.getLongitude()) + " " + schoolBus + " " + busSession + " " + vehicleSpinner.getNumberPlate());
                    Log.d(context.getPackageName().toUpperCase(), "Checking if the table exist: " + String.valueOf(tableExists(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE)));

                    if(!existsColumnInTable(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE)){
                        String query_UPDATE_ULTRATUDATA_VEHICLEPLATE = "ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE + " nvarchar ";
                        db.execSQL(query_UPDATE_ULTRATUDATA_VEHICLEPLATE);
                        Log.d(context.getPackageName().toUpperCase(), "Added the Column VehiclePlate");
                    }else{
                        Log.d(context.getPackageName().toUpperCase(), "The VehiclePlate column does exist");
                    }

                    if(!existsColumnInTable(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, ConnSQLiteContract.UltraDataDef.BUSTRIP)){
                        String query_UPDATE_ULTRATUDATA_VEHICLEPLATE = "ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.BUSTRIP + " TEXT DEFAULT NULL ";
                        db.execSQL(query_UPDATE_ULTRATUDATA_VEHICLEPLATE);
                        //Log.d(context.getPackageName().toUpperCase(), "Added the Column VehiclePlate");
                    }else{
                       // Log.d(context.getPackageName().toUpperCase(), "The VehiclePlate column does exist");
                    }

                    if(!existsColumnInTable(db, ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, ConnSQLiteContract.UltraDataDef.BUSTRIP)){
                        String query_UPDATE_ULTRATUDATA_VEHICLEPLATE = "ALTER TABLE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " ADD " + ConnSQLiteContract.UltraDataDef.DATE_SCANNED + " TEXT DEFAULT NULL ";
                        db.execSQL(query_UPDATE_ULTRATUDATA_VEHICLEPLATE);
                        //Log.d(context.getPackageName().toUpperCase(), "Added the Column VehiclePlate");
                    }else{
                        // Log.d(context.getPackageName().toUpperCase(), "The VehiclePlate column does exist");
                    }

                    long id = db.insert(ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, null, cv);
                    Log.d(context.getPackageName().toUpperCase(), "Added Tranport Records: " + String.valueOf(id));

                    //
                    if (id > 0) {
                       // Toast.makeText(context, "VehiclePlate added to plate.", Toast.LENGTH_SHORT).show();
                        Paper.book().destroy();
                        Paper.book().write("Vehicle", vehicleSpinner);

                    }
                }

            }catch(Exception e){

                //having erros because of the latitude and longitude having null values
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }




    }


    public void deleteOneRecord(int[] ultraDateIDS){

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    for (int i : ultraDateIDS) {
                        String query = "DELETE FROM " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " WHERE " + ConnSQLiteContract.UltraDataDef._ID + " = " + i + " AND " + ConnSQLiteContract.UltraDataDef.STATUS_SENT + " = 'true';";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
               sqhelp.closeDatabase();
            }
    }

    public void updateRecordToSent(int[] ultratDateIDs){
        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()) {
                for (int i : ultratDateIDs) {
                    String query = "UPDATE " + ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE + " SET " + ConnSQLiteContract.UltraDataDef.STATUS_SENT + " = 'true' WHERE " + ConnSQLiteContract.UltraDataDef._ID + " = " + i + ";";
                    db.execSQL(query);
                }

                //delete records order than 7 days
                Cursor datacursor = getAllUltradataCursor();
                List<Integer> listIDS = new ArrayList<>();
                while (datacursor.moveToNext()) {
                    String dateScanned = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.DATE_SCANNED));
                    if (!TextUtils.isEmpty(dateScanned)) {
                        int ID = datacursor.getInt(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef._ID));
                        String currentDate = new SimpleDateFormat("dd MMM YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
                        if(daysBetweenDates(currentDate, dateScanned) >= 7){
                            listIDS.add(ID);
                        }
                    }
                }

                //convert to array
                if(listIDS.size() > 0) {
                    int[] ids = new int[listIDS.size()];
                    for (int i = 0; i < listIDS.size(); i++) {
                        ids[i] = listIDS.get(i);
                    }
                    deleteOneRecord(ids);
                }
            }



        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }

    public int daysBetweenDates(String date1, String date2) {
        String firstdate = date1;

        String seconddate = date2;

        // Formatter
        DateTimeFormatter dateStringFormat = DateTimeFormat
                .forPattern("dd MMM YYYY HH:mm:ss");
        DateTime firstTime = dateStringFormat.parseDateTime(firstdate);
        DateTime secondTime = dateStringFormat.parseDateTime(seconddate);
        int days = Days.daysBetween(firstTime.withTimeAtStartOfDay(),
                secondTime.withTimeAtStartOfDay()).getDays();
        return days;
    }


    public void deleteUltradata(){

            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {
                    int affRaw = db.delete(ConnSQLiteContract.UltraDataDef.ULTRADATA_TABLE, null, null);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

    }


    private boolean tableExists(SQLiteDatabase db, String tableName)
    {
        if (tableName == null || db == null || !db.isOpen())
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (!cursor.moveToFirst())
        {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }


    private boolean existsColumnInTable(SQLiteDatabase inDatabase, String inTable, String columnToCheck) {
        Cursor mCursor = null;
        try {
            // Query 1 row
            mCursor = inDatabase.rawQuery("SELECT * FROM " + inTable + " LIMIT 0", null);

            // getColumnIndex() gives us the index (0 to ...) of the column - otherwise we get a -1
            if (mCursor.getColumnIndex(columnToCheck) != -1)
                return true;
            else
                return false;

        } catch (Exception Exp) {
            // Something went wrong. Missing the database? The table?
          //  Log.d("... - existsColumnInTable", "When checking whether a column exists in the table, an error occurred: " + Exp.getMessage());
            return false;
        } finally {
            if (mCursor != null) mCursor.close();
        }
    }

}
