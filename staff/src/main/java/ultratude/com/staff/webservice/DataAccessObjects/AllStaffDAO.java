package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.spinnermodel.StaffNameSpinner;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.AllStaff;
import ultratude.com.staff.webservice.ResponseModels.Staff;

/**
 * Created by James on 24/01/2019.
 */

public class AllStaffDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public AllStaffDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }


    public long saveAllStaffDAO(List<AllStaff> allStaffArrayList){
        long saved  = 0;
        Cursor cursor = getAllStaffCursor();

        if(cursor != null) {
            long countBeforeSaving = Long.valueOf(cursor.getCount());


            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    //though should be removed
                    deleteAllStaff();


                    for (AllStaff allStaff : allStaffArrayList) {

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.STAFFID, allStaff.getStaffID());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME, allStaff.getFirstName());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.LASTNAME, allStaff.getLastName());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.TITLENAME, allStaff.getTitleName());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.RANKNAME, allStaff.getRankName());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.PHONENUMBER, allStaff.getPhoneNumber());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.SCHOOLID, allStaff.getSchoolID());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.ORGANIZATIONID, allStaff.getOrganizationID());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.DEPARTMENTNAME, allStaff.getDepartmentName());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.SCHOOLNAME, allStaff.getSchoolName());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.EMAIL, allStaff.getEmail());
                        contentValues.put(ConnSQLiteContract.PortalGetAllStaff.BARCODE, allStaff.getBarCode());

                        db.insertOrThrow(ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE, null, contentValues);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

            long countAfterSaving = Long.valueOf(getAllStaffCursor().getCount());

            // 27 - 0 or 34 - 5 ;//countAfterSaving will always be greater, 1 - 0, 5 - 4;
            saved = (countAfterSaving - countBeforeSaving);
        }

        return saved;
    }


    public Cursor getAllStaffCursor(){
        Cursor cursor = null;
        try{

           SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }

        return cursor;


    }


    //delete each row
    public void deleteOneStaff(int[] ultraDataIDs) {

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {
                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE + " WHERE " + ConnSQLiteContract.PortalGetAllStaff._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            } finally {
                sqhelp.closeDatabase();
            }

    }


    public String getNameOfLoggedInStaff(){
        Staff staff = new StaffDao(mContext).getUserThatSignedUp();

        StringBuilder stringB = new StringBuilder();


            try{

                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
                if(db.isOpen()) {

                    Cursor cursor = db.query(ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE,
                            new String[]{ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME, ConnSQLiteContract.PortalGetAllStaff.LASTNAME, ConnSQLiteContract.PortalGetAllStaff.TITLENAME},
                            ConnSQLiteContract.PortalGetAllStaff.STAFFID + " = ? ",
                            new String[]{staff.getStaff_ID()},
                            null,
                            null,
                            null);
                    if (cursor.moveToNext()) {
                        stringB.append(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.TITLENAME)));
                        stringB.append(" ");

                        Log.d(mContext.getPackageName().toUpperCase(), String.valueOf(TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME)))));

                        if(TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME)))){
                            stringB.append(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.LASTNAME)));
                        }else {
                            stringB.append(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME)));
                        }
//                    stringB.append(" ");
//                    stringB.append(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.LASTNAME)));
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }


        return stringB.toString();

    }


    public AllStaff getStaffWithID(String staffID){

        AllStaff allStaff = null;

            try{
                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

                if(db.isOpen()) {

                    Cursor allStaffCursor = db.query(ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE,
                            null,
                            ConnSQLiteContract.PortalGetAllStaff.STAFFID + " = ? ",
                            new String[]{staffID},
                            null,
                            null,
                            null);
                    if (allStaffCursor.moveToNext()) {

                        allStaff = new AllStaff(
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.STAFFID)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.LASTNAME)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.TITLENAME)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.RANKNAME)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.PHONENUMBER)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.SCHOOLID)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.ORGANIZATIONID)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.DEPARTMENTNAME)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.SCHOOLNAME)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.BARCODE)),
                                allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.EMAIL))

                        );


                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }


        return allStaff;
    }

    public List<Object> getAllStaffList(){
        List<AllStaff> allStaffArrayList = new ArrayList<>();

        Cursor allStaffCursor = getAllStaffCursor();
        int[] dutyRosterIDs = new int[allStaffCursor.getCount()];

        try{

            int count = 0;
            while(allStaffCursor.moveToNext()){

                int id = allStaffCursor.getInt(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff._ID));
                dutyRosterIDs[count] = id;
                count++;

                AllStaff allStaff = new AllStaff(
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.STAFFID)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.LASTNAME)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.TITLENAME)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.RANKNAME)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.PHONENUMBER)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.SCHOOLID)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.ORGANIZATIONID)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.DEPARTMENTNAME)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.SCHOOLNAME)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.BARCODE)),
                        allStaffCursor.getString(allStaffCursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.EMAIL))

                );
                allStaffArrayList.add(allStaff);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(allStaffArrayList);//0 List<DutyRoster>
        list.add(dutyRosterIDs);//1 int[]

        return list;
    }









    public ArrayList<StaffNameSpinner> getStaffNameSpinner(){

        ArrayList<StaffNameSpinner> staffNameSpinnerArrayList = new ArrayList<>();

            try{
                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
                if(db.isOpen()) {
                    Cursor cursor = db.query(ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE,
                            new String[]{ConnSQLiteContract.PortalGetAllStaff.STAFFID, ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME, ConnSQLiteContract.PortalGetAllStaff.LASTNAME},
                            null,
                            null,
                            null,
                            null,
                            null);
                    while (cursor.moveToNext()) {

                        StringBuilder stringB = new StringBuilder();
                        stringB.append(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.FIRSTNAME)));
                        stringB.append(" ");
                        stringB.append(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.LASTNAME)));

                        StaffNameSpinner staffNameSpinner = new StaffNameSpinner(
                                cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalGetAllStaff.STAFFID)),
                                stringB.toString()
                        );

                        staffNameSpinnerArrayList.add(staffNameSpinner);

                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }




        return staffNameSpinnerArrayList;

    }



    public long deleteAllStaff(){

        long id = 0;


        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            id =   db.delete(ConnSQLiteContract.PortalGetAllStaff.PORTALGETALLSTAFF_TABLE, null,null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return id;

    }
}
