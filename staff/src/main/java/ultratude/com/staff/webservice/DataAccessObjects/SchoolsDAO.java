package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.Schools;

/**
 * Created by James on 11/01/2019.
 */

public class SchoolsDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public SchoolsDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveSchools(List<Schools> schoolsList){
        long id = 0;


            try {
                deleteSchools();

                SQLiteDatabase db = sqhelp.getWritableDatabase();

                if (db.isOpen()) {

                    for (Schools school : schoolsList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetSchools.SCHOOL_ID, school.getSchoolID().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetSchools.SCHOOLNAME, school.getSchoolName().trim());

                        id += db.insertOrThrow(ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE, null, contentValues);

                    }
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

        //return id;

        long id2 = 0;
            if( getSchoolsCursor() !=  null){
                id =  (long) getSchoolsCursor().getCount();
            }


        return id2;
    }


    public Cursor getSchoolsCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
//            db.close();
        }

        return cursor;


    }


    //delete each row
    public void deleteOneSchool(int[] ultraDataIDs) {

            try {

                SQLiteDatabase db = sqhelp.getWritableDatabase();

                if (db.isOpen()) {
                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE + " WHERE " + ConnSQLiteContract.PortalGetSchools._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

    }




    public List<Object> getSchoolsList(){
        List<Schools> schoolsList = new ArrayList<>();

        Cursor dutyRosterCursor = getSchoolsCursor();
        int[] schoolsIDs = new int[dutyRosterCursor.getCount()];

        try{

            int count = 0;
            while(dutyRosterCursor.moveToNext()){

                int id = dutyRosterCursor.getInt(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetSchools._ID));
                schoolsIDs[count] = id;
                count++;

                Schools school = new Schools(
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetSchools.SCHOOL_ID)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalGetSchools.SCHOOLNAME))

                );
                schoolsList.add(school);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(schoolsList);//0 List<DutyRoster>
        list.add(schoolsIDs);//1 int[]

        return list;
    }


    public  void deleteSchools(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalGetSchools.PORTALGETSCHOOLS_TABLE,null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }


}
