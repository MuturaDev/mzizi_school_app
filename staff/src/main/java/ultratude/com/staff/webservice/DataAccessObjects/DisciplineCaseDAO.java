package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.DisciplineCase;

/**
 * Created by James on 11/01/2019.
 */

public class DisciplineCaseDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;


    public DisciplineCaseDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveDisciplineCase(DisciplineCase disciplineCase){


        long countBeforeSaving = 0;

        Cursor cursor2 = getAllDisciplineCasesCursor();
        long saved = 0;
        if(cursor2 != null) {

            countBeforeSaving = (long) cursor2.getCount();

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {
                    ContentValues contentHolder = new ContentValues();
                    contentHolder.put(ConnSQLiteContract.PortalSaveDisciplineCase.STUDENT_ID, disciplineCase.getStudentID());
                    contentHolder.put(ConnSQLiteContract.PortalSaveDisciplineCase.OFFENCE, disciplineCase.getOffence());
                    contentHolder.put(ConnSQLiteContract.PortalSaveDisciplineCase.PENAULTY, disciplineCase.getPenaulty());
                    contentHolder.put(ConnSQLiteContract.PortalSaveDisciplineCase.REPORTEDBY, disciplineCase.getReportedBy());
                    contentHolder.put(ConnSQLiteContract.PortalSaveDisciplineCase.APPCODE, disciplineCase.getAppCode());

                    db.insert(ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE, null, contentHolder);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

            long countAfterSaving = (long) getAllDisciplineCasesCursor().getCount();

             saved = countAfterSaving - countBeforeSaving;
        }

        return saved;
    }

    public List<Object> getDisciplinaryCasesList(){
        List<DisciplineCase> disciplineCasesList = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        Cursor disciplinaryCase = getAllDisciplineCasesCursor();
        if(disciplinaryCase!= null) {


            int[] studentIDs = new int[disciplinaryCase.getCount()];

            try {

                int count = 0;
                while (disciplinaryCase.moveToNext()) {

                    int id = disciplinaryCase.getInt(disciplinaryCase.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase._ID));
                    studentIDs[count] = id;
                    count++;

                    DisciplineCase student = new DisciplineCase(
                            disciplinaryCase.getString(disciplinaryCase.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase.STUDENT_ID)),
                            disciplinaryCase.getString(disciplinaryCase.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase.OFFENCE)),
                            disciplinaryCase.getString(disciplinaryCase.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase.PENAULTY)),
                            disciplinaryCase.getString(disciplinaryCase.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase.REPORTEDBY)),
                            disciplinaryCase.getString(disciplinaryCase.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase.APPCODE))

                    );
                    disciplineCasesList.add(student);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


            list.add(disciplineCasesList);//0 List<Student>
            list.add(studentIDs);//1 int[]
        }





        return list;
    }



    public Cursor getAllDisciplineCasesCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }

        return cursor;

    }



    public int[] getDisciplineCaseIDs(){

        int[] ids = null;
        try{

            Cursor datacursor =   getAllDisciplineCasesCursor();
            ids = new int[datacursor.getCount()];
            int count = 0;
            while (datacursor.moveToNext()) {

                int id = datacursor.getInt(datacursor.getColumnIndex(ConnSQLiteContract.PortalSaveDisciplineCase._ID));
                ids[count] = id;
                count++;

            }

            //Toast.makeText(context, String.valueOf(Arrays.toString(ids)), Toast.LENGTH_LONG).show();

            //  Toast.makeText(context, datalist.get(0).getLongitude() + "   " + datalist.get(15).getLongitude(), Toast.LENGTH_LONG).show();

        }catch (Exception e){
            e.printStackTrace();
        }

        return ids;


    }



        //delete each row
    public void deleteOneDisciplineCase(int[] ultraDataIDs){


            try{

                SQLiteDatabase  db = sqhelp.getWriteableDatabaseConnection();
                if(db.isOpen()) {

                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE + " WHERE " + ConnSQLiteContract.PortalSaveDisciplineCase._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

    }


        //delete all rows
    public boolean deleteAllDisciplineCases(){


            int affRaw = 0;
            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    affRaw = db.delete(ConnSQLiteContract.PortalSaveDisciplineCase.PORTALSAVEDISCIPLINECASE_TABLE, null, null);
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
               sqhelp.closeDatabase();
            }

            if(affRaw > 0){
                return true;
            }

        return false;

    }
}
