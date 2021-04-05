package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.spinnermodel.ClassStreamSpinner;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.ClassStream;

/**
 * Created by James on 03/05/2019.
 */

public class ClassStreamDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public ClassStreamDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }


    public long saveClassStreamDAO(List<ClassStream> classStreamList){


        Log.d(mContext.getPackageName().toUpperCase(), classStreamList.toString());

        long saved = 0;
        Cursor cursor = null;

        Cursor cursor1 = getClassStreamCursor();

        if(cursor1 != null) {

            long countBeforeSaving = (long) cursor1.getCount();


            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    deleteClassStreams();

                    for (ClassStream classStream : classStreamList) {

                        Log.d(mContext.getPackageName().toUpperCase(), classStream.toString());

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetClassStreams.COURSE_ID, classStream.getCourseID().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetClassStreams.COURSE_CODE, classStream.getCourseCode().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetClassStreams.LEVEL_ID, classStream.getLevelID().trim());
                        contentValues.put(ConnSQLiteContract.PortalGetClassStreams.LEVEL_NAME, classStream.getLevelName().trim());

                        db.insertOrThrow(ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE, null, contentValues);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


            long countAfterSaving = (long) getClassStreamCursor().getCount();

             saved = countAfterSaving - countBeforeSaving;
        }

        return saved;
    }


    private Cursor getClassStreamCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE, null, null, null, null, null, null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
           // sqhelp.closeDatabase();
        }

        return cursor;
    }

    public void deleteClassStreams(){
        Cursor cursor = null;


        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE,null, null);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

    }


    public /*ArrayList<ClassStreamSpinner>*/ int getClassStreamSpinnerID(String classStream){

        ArrayList<ClassStreamSpinner>  classStreamSpinnerArrayList = getClassStreamSpinner();
        Log.d(mContext.getPackageName().toUpperCase(), "Class Streamm Spinner List: " + classStreamSpinnerArrayList.toString());

        ArrayList<ClassStreamSpinner>  classStreams = new ArrayList<>();

        Log.d(mContext.getPackageName().toUpperCase(), classStream);

        ClassStreamSpinner classStreamSpinner = null;
        int returncount = 0;

        for(int returnID = 0; returnID < classStreamSpinnerArrayList.size(); returnID++){

            if(classStreamSpinnerArrayList.get(returnID).getClassStreamName().equalsIgnoreCase(classStream)){
                Log.d(mContext.getPackageName().toUpperCase(), "Class Stream Ids: " + String.valueOf(returnID));
//                classStreamSpinner =  classStreamSpinnerArrayList.get(returnID);
//                classStreams.add(classStreamSpinner);
                returncount = returnID;

                break;
            }
        }

       // Log.d(mContext.getPackageName().toUpperCase(), "The Roll Call selected" + classStreams.toString());

        //return classStreams;
        return returncount;
    }



    public ArrayList<ClassStreamSpinner> getClassStreamSpinner(){

        ArrayList<ClassStreamSpinner> classStreamSpinnerArrayList = new ArrayList<>();
        ClassStreamSpinner leaveTypeSpinner2 = new ClassStreamSpinner(
                //REMEMBER there is level id
                0,"Select Class"
        );
        classStreamSpinnerArrayList.add(leaveTypeSpinner2);

            try{

                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
                if(db.isOpen()){
                    Cursor cursor = db.query(ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE, null,null,null,null,null,null);
                    while(cursor.moveToNext()) {
                        ClassStreamSpinner leaveTypeSpinner = new ClassStreamSpinner(
                                //REMEMBER there is level id
                                cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalGetClassStreams.COURSE_ID)),
                                cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetClassStreams.COURSE_CODE)) + " " + cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetClassStreams.LEVEL_NAME))
                        );

                        classStreamSpinnerArrayList.add(leaveTypeSpinner);
                    }
                }


            }catch(Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }


        return classStreamSpinnerArrayList;
    }

    public void deleteOneClassStream(int[] classStreamIDs){

            try {

                    SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    for (int i : classStreamIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalGetClassStreams.PORTALGETCLASSSTREAMS_TABLE + " WHERE " + ConnSQLiteContract.PortalGetClassStreams._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

    }

}
