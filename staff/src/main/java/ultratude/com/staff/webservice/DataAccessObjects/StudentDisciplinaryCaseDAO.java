package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.StudentDisciplinaryCase;

/**
 * Created by James on 28/04/2019.
 */

public class StudentDisciplinaryCaseDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public StudentDisciplinaryCaseDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);

    }

    public Cursor getStudentDisciplinaryCasesCursor(){
        Cursor cursor = null;

        SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

        try{
            cursor = db.query(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE, null,null,null,null,null,null);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
           // sqhelp.closeDatabase();
        }

        return cursor;
    }

    public long saveStudentDisciplinaryCases(List<StudentDisciplinaryCase> studentDisciplinaryCaseList){
        long id = 0;

        Cursor cursor =  getStudentDisciplinaryCasesCursor();
        long saved = 0;
        if (cursor != null) {


            long countBeforeSaving = (long) cursor.getCount();


            try {
                //deleteStudentDisciplinaryCases
                deleteStudentDisciplinaryCases();

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {


                    for (StudentDisciplinaryCase disciplinaryCase : studentDisciplinaryCaseList) {
                        ContentValues contentValue = new ContentValues();
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.OFFENCE_DESCRIPTION, disciplinaryCase.getOffenceDescription());
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PENAULTY_DESCRIPTION, disciplinaryCase.getPenaultyDescription());
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.REPORTEDBY, disciplinaryCase.getReportedBy());
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.DATE_COMMITED, disciplinaryCase.getDateCommited());
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.TERM_FOR, disciplinaryCase.getTermID());
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.YEARID, disciplinaryCase.getYearID());
                        contentValue.put(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.STUDENTNAME, disciplinaryCase.getStudentName());

                        id += db.insertOrThrow(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE, null, contentValue);
                    }
                }
            } catch (Exception ex) {
                //log
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


            long countAfterSaving = (long) getStudentDisciplinaryCasesCursor().getCount();
             saved = countAfterSaving - countBeforeSaving;
        }

        return saved;
    }



    public void deleteOneStudentDisciplineCases(int[] ultraDataIDs){

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {
                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE + " WHERE " + ConnSQLiteContract.PortalGetStudentDisciplinaryCases._ID + " = " + i + ";";
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


    public List<Object> getStudentDisciplinaryCasesList(){
        List<StudentDisciplinaryCase> studentDisciplinaryCasesList = new ArrayList<>();

        Cursor studentDisciplinaryCasesCursor = getStudentDisciplinaryCasesCursor();
        int[] dutyRosterIDs = new int[studentDisciplinaryCasesCursor.getCount()];

        try{

            int count = 0;
            while(studentDisciplinaryCasesCursor.moveToNext()){

                int id = studentDisciplinaryCasesCursor.getInt(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetDutyRoster._ID));
                dutyRosterIDs[count] = id;
                count++;

                StudentDisciplinaryCase studentDisciplinaryCase = new StudentDisciplinaryCase(
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.OFFENCE_DESCRIPTION)),
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PENAULTY_DESCRIPTION)),
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.REPORTEDBY)),
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.DATE_COMMITED)),
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.TERM_FOR)),
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.YEARID)),
                        studentDisciplinaryCasesCursor.getString(studentDisciplinaryCasesCursor.getColumnIndex(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.STUDENTNAME))

                );
                studentDisciplinaryCasesList.add(studentDisciplinaryCase);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(studentDisciplinaryCasesList);//0 List<DutyRoster>
        list.add(dutyRosterIDs);//1 int[]

        return list;
    }

    private void deleteStudentDisciplinaryCases(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalGetStudentDisciplinaryCases.PORTALGETDISCIPLINARYCASES_TABLE, null,null);
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            sqhelp.closeDatabase();
        }

    }

}
