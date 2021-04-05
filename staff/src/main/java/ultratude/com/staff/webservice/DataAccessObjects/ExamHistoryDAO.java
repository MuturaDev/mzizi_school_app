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
import ultratude.com.staff.webservice.ResponseModels.ExamHistory;

/**
 * Created by James on 25/01/2019.
 */

public class ExamHistoryDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;


    public ExamHistoryDAO(Context mContext){
        this.mContext = mContext;

        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveExamResultHistory(List<ExamHistory> examHistoryList){

        long id = 0l;//this is no 1 after zero
        int count = 0;



        deleteExamResultsHistory();

        Cursor cursor = getExamHistoryCursor();


        long saved = 0;

        if(cursor != null){

            long countBeforeSaving = (long) cursor.getCount();

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {

                    //after updating the app in playstore the this delete is not deleting all rows before adding new records

                    //this line messed me up, so dont use if before insert coz it will keep on deleting rows in the database

                    for (ExamHistory exam : examHistoryList) {

                        ContentValues savelatlong = new ContentValues();

                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.SUBJECT, exam.getSubjects());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.SCORE, exam.getScore());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.GRADE, exam.getGrade());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.REMARK, exam.getRemark());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.TOTALMARKS, exam.getTotalMarks());

                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.MEANSCORE, exam.getMeanScore());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.MEANGRADE, exam.getMeanGrade());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.STREAMPOSITION, exam.getStreamPosition());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.OVERALLPOSITION, exam.getOverallPosition());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.PERIOD, exam.getPeriod());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.TERMNAME, exam.getTermName());
                        savelatlong.put(ConnSQLiteContract.PortalGetExamHistory.CURRENTYEAR, exam.getCurrentYear());

                        id = db.insert(ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE, null, savelatlong);

                        // Log.d(mContext.getPackageName().toUpperCase(), "Saved :" + id);


                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
                // db.close();
            }


            long countAfterSaving = (long) getExamHistoryCursor().getCount();

             saved = countAfterSaving - countBeforeSaving;
        }


        return saved;

    }


    public Cursor getExamHistoryCursor(){
        Cursor cursor = null;

        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            db.close();
        }

        return cursor;


    }




        //WILL NOT BE USING THIS, SINCE WE ARE NOT SAVING THE DATA IN THE DATABASE, WE ARE JUST VIEWING THE DATA
    public List<ExamHistory> getExamResultHistory(){
        List<ExamHistory> examHistoryArrayList = new ArrayList<>();

            try {

                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
                if (db.isOpen()) {
                    Cursor cursor = db.query(ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE, null, null, null, null, null, null, null);

                    while (cursor.moveToNext()) {

                        ExamHistory exam = new ExamHistory();
                        exam.setSubjects(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.SUBJECT)));
                        exam.setScore(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.SCORE)));
                        exam.setGrade(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.GRADE)));
                        exam.setRemark(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.REMARK)));
                        exam.setTotalMarks(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.TOTALMARKS)));

                        exam.setMeanScore(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.MEANSCORE)));
                        exam.setMeanGrade(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.MEANGRADE)));
                        exam.setStreamPosition(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.STREAMPOSITION)));
                        exam.setOverallPosition(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.STREAMPOSITION)));
                        exam.setPeriod(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.PERIOD))));
                        exam.setTermName(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.TERMNAME))));
                        exam.setCurrentYear(String.valueOf(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalGetExamHistory.CURRENTYEAR))));

                        examHistoryArrayList.add(exam);
                        // Toast.makeText(context,exam.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }finally {
                //this closes the connection and other query being request requires this connection
                sqhelp.closeDatabase();
            }

        // Toast.makeText(context, String.valueOf(transportStudentsList.get(0).getBarcode()), Toast.LENGTH_LONG).show();
        return examHistoryArrayList;

    }








    public boolean deleteExamResultsHistory(){


            int affRaw = 0;
            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {
                    affRaw = db.delete(ConnSQLiteContract.PortalGetExamHistory.PORTALGETEXAMHISTORY_TABLE, null, null);
                    Log.d(mContext.getPackageName().toUpperCase(),"Exam record deleted: " + String.valueOf(affRaw));
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
