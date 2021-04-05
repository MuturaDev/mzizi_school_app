package ultratude.com.staff.webservice.DataAccessObjects;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;

/**
 * Created by James on 22/12/2018.
 */

public class AllTransportStudentsListDao {

    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;


    public AllTransportStudentsListDao(Context context){
        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }
    public String saveAllStudentsList(ProgressDialog alertd , List<TransportStudents> studentList){

        long id = 0l;//this is no 1 after zero
        int count = 0;
        Cursor cursor = null;

            try{

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if(db.isOpen()) {
                    //after updating the app in playstore the this delete is not deleting all rows before adding new records

                    //this line messed me up, so dont use if before insert coz it will keep on deleting rows in the database
                    deleteAllStudentsList();

                    for (int i = 0; i < studentList.size(); i++) {

                        count++;

                        ContentValues savelatlong = new ContentValues();

                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.STUDENTFULLNAME, (studentList.get(i).getStudentFullName() == null) ? "" : studentList.get(i).getStudentFullName());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.REGISTRATIONNUMBER, (studentList.get(i).getRegistrationNumber() == null) ? "" : studentList.get(i).getRegistrationNumber());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.CLASSSTREAM, (studentList.get(i).getClassStream() == null) ? "" : studentList.get(i).getClassStream());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.SCHOOLNAME, (studentList.get(i).getSchoolName() == null) ? "" : studentList.get(i).getSchoolName());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.BARCODE, (studentList.get(i).getBarcode() == null) ? "" : studentList.get(i).getBarcode());

                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.ROUTE_NAME, (studentList.get(i).getRouteName() == null) ? "" : studentList.get(i).getRouteName());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.BUS, (studentList.get(i).getBus() == null) ? "" : studentList.get(i).getBus());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.TERM_NAME, (studentList.get(i).getTermName() == null) ? "" : studentList.get(i).getTermName());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.YEAR_ID, (studentList.get(i).getYearID() == null) ? "" : studentList.get(i).getYearID());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.MORNING_PICKED, (studentList.get(i).getMorningPicked() == null) ? "" : studentList.get(i).getMorningPicked());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.EVENING_PICKED, (studentList.get(i).getEveningPicked() == null) ? "" : studentList.get(i).getEveningPicked());

                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.FATHER_PHONE, (studentList.get(i).getFatherPhone() == null) ? "" : studentList.get(i).getFatherPhone());
                        savelatlong.put(ConnSQLiteContract.PortalAllTransportStudentsListing.MOTHER_PHONE, (studentList.get(i).getMotherPhone() == null) ? "" : studentList.get(i).getMotherPhone());

                        id = db.insert(ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE, null, savelatlong);

                    }

                    //studentList.clear();

//            if(alertd != null){
//                alertd.cancel();
//            }

                    cursor = db.query(ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE, null, null, null, null, null, null, null);

                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }



        return String.valueOf(id);


    }


    public List<TransportStudents> getAllStudentsList(){
        List<TransportStudents> transportStudentsList = new ArrayList<>();


            try{

                SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();
                if(db.isOpen()) {
                    Cursor cursor = db.query(ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE, null, null, null, null, null, null, null);

                    // Toast.makeText(context, "Cursor count: " + cursor.getCount(), Toast.LENGTH_LONG).show();

                    while (cursor.moveToNext()) {


                        TransportStudents transportStudents = new TransportStudents();
                        transportStudents.setStudentFullName(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.STUDENTFULLNAME)));
                        transportStudents.setSchoolName(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.SCHOOLNAME)));
                        transportStudents.setClassStream(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.CLASSSTREAM)));
                        transportStudents.setRegistrationNumber(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.REGISTRATIONNUMBER)));
                        transportStudents.setBarcode(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.BARCODE))));


                        transportStudents.setRouteName(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.ROUTE_NAME)));
                        transportStudents.setBus(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.BUS)));
                        transportStudents.setTermName(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.TERM_NAME)));
                        transportStudents.setYearID(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.YEAR_ID)));
                        transportStudents.setMorningPicked(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.MORNING_PICKED))));
                        transportStudents.setEveningPicked(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.EVENING_PICKED))));

                        transportStudents.setFatherPhone(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.FATHER_PHONE))));
                        transportStudents.setMotherPhone(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAllTransportStudentsListing.MOTHER_PHONE))));


                        transportStudentsList.add(transportStudents);
                        // Toast.makeText(context,transportStudents.toString(), Toast.LENGTH_LONG).show();
                    }
                }


            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }




        // Toast.makeText(context, String.valueOf(transportStudentsList.get(0).getBarcode()), Toast.LENGTH_LONG).show();

        return transportStudentsList;

    }





    public boolean deleteAllStudentsList(){


            int affRaw = 0;
            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {
                    affRaw = db.delete(ConnSQLiteContract.PortalAllTransportStudentsListing.PORTALALLSTUDENTSLISTING_TABLE, null, null);
                }

            }finally{
                sqhelp.closeDatabase();
            }


            if(affRaw > 0){
                return true;
            }


        return false;
    }
}
