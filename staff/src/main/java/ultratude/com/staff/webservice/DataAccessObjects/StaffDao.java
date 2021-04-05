package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;

/**
 * Created by Admin on 3/21/2018.
 */

public class StaffDao {



    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;


    public StaffDao( Context context){

        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }

    public long saveUserToDB(Staff user){

        //Toast.makeText(context, user.toString(), Toast.LENGTH_LONG).show();

        long id = 0;

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {


                    ContentValues signUp = new ContentValues();
                    signUp.put(ConnSQLiteContract.UltraStaffDef.APPCODE, user.getAppcode());
                    signUp.put(ConnSQLiteContract.UltraStaffDef.STAFF_ID, user.getStaff_ID());
                    signUp.put(ConnSQLiteContract.UltraStaffDef.USERTYPE, user.getUserType());
                    signUp.put(ConnSQLiteContract.UltraStaffDef.SCHOOLID, user.getSchoolID());
                    signUp.put(ConnSQLiteContract.UltraStaffDef.ORGANIZATIONID, user.getOrganizationID());

                    signUp.put(ConnSQLiteContract.UltraStaffDef.USERNAME, user.getUsername());
                    signUp.put(ConnSQLiteContract.UltraStaffDef.PASSWORD, user.getPassword());


                    id = db.insert(ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE, null, signUp);
                    // Toast.makeText(context, getUserThatSignedUp().toString(), Toast.LENGTH_LONG).show();

                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

        return id;

    }



    public boolean deleteStaff(){
        long id = 0;


            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    id = db.delete(ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE, null, null);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


        if(id>0){
            return true;
        }else{
            return false;
        }

    }


    public Staff getUserThatSignedUp(){


            try {

                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
                Staff staff = new Staff();

                if (db.isOpen()) {

                    Cursor cursor = db.query(ConnSQLiteContract.UltraStaffDef.ULTRAUSER_TABLE, null, null, null, null, null, null, null);

                    if (cursor.moveToFirst()) {

                        staff.setAppcode(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.APPCODE)));
                        staff.setStaff_ID(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.STAFF_ID)));
                        staff.setUserType(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.USERTYPE)));
                        staff.setSchoolID(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.SCHOOLID)));
                        staff.setOrganizationID(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.ORGANIZATIONID)));
                        staff.setUsername(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.USERNAME)));
                        staff.setPassword(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraStaffDef.PASSWORD)));

                        return staff;
                    }


                }
            }catch (Exception ex){
                ex.printStackTrace();

            }finally {
                sqhelp.closeDatabase();
            }

        return null;

    }

}
