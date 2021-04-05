package ultratude.com.mzizi.modelclasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.notificationpg.NotificationSQDB.ConnSQLiteOpenHelper;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.staff.spinnermodel.TermSpinner;

/**
 * Created by James on 18/05/2019.
 */

public class NewCarriculumExamDAO {

    private Context mContext;
    private ConnSQLiteOpenHelper openHelper;
    private String mainStudentID;

   public NewCarriculumExamDAO(Context mContext){
       this.mContext = mContext;
       openHelper = new ConnSQLiteOpenHelper(mContext);
        ParentMziziDatabase db = ParentMziziDatabase.getInstance(mContext.getApplicationContext());
       String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
       if(studentid == null){
           studentid  = "0";
       }
       mainStudentID = studentid;
   }

    public SQLiteDatabase getWritableSQLiteConnection(){

        return openHelper.getWritableDatabase();

    }

    public SQLiteDatabase getReadableSQLiteConnection(){

        return openHelper.getReadableDatabase();

    }

   public void insertNewCarriculumExamFormat(List<NewCarriculumExam> newCarriculumExamList){


       long id = 0;

       SQLiteDatabase sqdb = getWritableSQLiteConnection();

       try {

           if(newCarriculumExamList.size() > 0)
             deleteNewCarriculumExamFormat(newCarriculumExamList.get(0).getStudID().toString());

           for (NewCarriculumExam exam : newCarriculumExamList) {
               ContentValues insertContent = new ContentValues();
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.STUDENTFULLNAME, exam.getStudentFullName());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.YEAID, exam.getYearID());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.TERM_NAME, exam.getTermName());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC, exam.getExamTypeDesc());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.COURSE_NAME, exam.getCourseName());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.LEVEL_NAME, exam.getLevelName());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.ACTIVITY, exam.getActivity());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.LEARNINGAREA, exam.getLearningArea());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.REMARKS, exam.getRemarks());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.SCORE_DESCRIPTION, exam.getScoreDescription());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.CLASS_TEACHER_COMMENT, exam.getClassTeacherComment());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.HEAD_TEACHER_COMMENT, exam.getHeadTeacherComment());
               insertContent.put(LocalDBContract.NewCurriculumExamFormat.STUDID, exam.getStudID());

               id = sqdb.insert(LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE, null, insertContent);
              // Log.d(mContext.getPackageName().toUpperCase(), "Saved ID: " + String.valueOf(id));

           }

           if (sqdb.isOpen()) {
                sqdb.close();
           }
       }catch (Exception ex){
           ex.printStackTrace();
       }
   }


   private List<Activities> getAllActivities(String examType, String yearID, String termName){
       List<Activities> activitiesList = new ArrayList<>();

       try{
           SQLiteDatabase db = getReadableSQLiteConnection();

           Cursor cursor = db.query(
                   true,
                   LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                   new String[]{LocalDBContract.NewCurriculumExamFormat.ACTIVITY, LocalDBContract.NewCurriculumExamFormat.COURSE_NAME, LocalDBContract.NewCurriculumExamFormat.LEVEL_NAME},
                   LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC + " = ? " +
                   " AND " +
                   LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                   " AND " +
                   LocalDBContract.NewCurriculumExamFormat.TERM_NAME + " = ? " +
                    " AND " +
                   LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "
                   ,new String[]{examType, yearID,termName, mainStudentID},null,null,null,null);
           while (cursor.moveToNext()) {

               Activities activities = new Activities(
                       cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.ACTIVITY)),
                       cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.COURSE_NAME)),
                       cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.LEVEL_NAME))
               );
               activitiesList.add(activities);
           }

       }catch (Exception ex){
           ex.printStackTrace();
       }

       return activitiesList;

   }


   public List<Activities> getPortalResultProgress(String examType, String yearID, String termName){

       List<Activities> activitiesList = getAllActivities(examType, yearID, termName);

       try{

           SQLiteDatabase db = getReadableSQLiteConnection();

           for(Activities activities : activitiesList){

               Cursor progressResult = db.query(
                       true,
                       LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                       new String[]{LocalDBContract.NewCurriculumExamFormat.LEARNINGAREA, LocalDBContract.NewCurriculumExamFormat.SCORE_DESCRIPTION, LocalDBContract.NewCurriculumExamFormat.REMARKS},
                                LocalDBContract.NewCurriculumExamFormat.ACTIVITY + " = ? " +
                               " AND " +
                                LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC + " = ? " +
                               " AND " +
                                LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                               " AND " +
                                LocalDBContract.NewCurriculumExamFormat.TERM_NAME + " = ? " +
                                " AND " +
                                LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "
                       ,new String[]{activities.getActivityName(),examType, yearID,termName,mainStudentID},null,null,null,null);

               List<LearningAreas> learningAreasList = new ArrayList<>();
               while(progressResult.moveToNext()){
                   LearningAreas learningAreas = new LearningAreas(
                           progressResult.getString(progressResult.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.LEARNINGAREA)),
                           progressResult.getString(progressResult.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.SCORE_DESCRIPTION)),
                           progressResult.getString(progressResult.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.REMARKS))
                   );
                   learningAreasList.add(learningAreas);
               }

               activities.setLearningAreaList(learningAreasList);


           }


       }catch (Exception ex){
           ex.printStackTrace();
       }


       Log.d(mContext.getPackageName().toUpperCase(), activitiesList.toString());

       return activitiesList;

   }


   public int deleteAllNewCarriculumExamFormat(){
       int id = 0;
       try {
          id = getWritableSQLiteConnection().delete(LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                  null,
                  null);
       }catch (Exception ex){
           ex.printStackTrace();
       }
       return id;
   }

   public void deleteNewCarriculumExamFormat(String studentID){
       try{
           getWritableSQLiteConnection().delete(LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                   LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? ",
                   new String[]{studentID});
       }catch (Exception ex){
           ex.printStackTrace();
       }
   }





   public Cursor getPortalExamProgressReportExamTypeCursor(String term, String year){
       Cursor cursor = null;
       try{

           SQLiteDatabase db = getReadableSQLiteConnection();
           cursor = db.query(true,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                   new String[]{LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC},
                   LocalDBContract.NewCurriculumExamFormat.TERM_NAME + " = ? " +
                           " AND " +
                           LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                           " AND " +
                           LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "
                   ,new String[]{term,year,mainStudentID},null,null,null,null);

           return cursor;

       }catch (Exception ex){
           ex.printStackTrace();
       }

       return cursor;
   }


   public Cursor getPortalExamProgressReportYearCursor(){
       Cursor cursor = null;
       try{

           SQLiteDatabase db = getReadableSQLiteConnection();
           cursor = db.query(true,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,new String[]{LocalDBContract.NewCurriculumExamFormat.YEAID},
                   LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? " ,
                   new String[]{mainStudentID},null,null,null,null);

           return cursor;

       }catch (Exception ex){
           ex.printStackTrace();
       }

       return cursor;
   }


   public Cursor getPortalExamProgressReportTermCursor(String year){
       Cursor cursor = null;
       try{

           SQLiteDatabase db = getReadableSQLiteConnection();
           cursor = db.query(true,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                   new String[]{LocalDBContract.NewCurriculumExamFormat.TERM_NAME},
                   LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                            " AND " +
                            LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? ",

                   new String[]{year, mainStudentID},null,null,null,null);

           Log.d(mContext.getPackageName().toUpperCase(), "Cursor Count: " +String.valueOf(cursor.getCount()));
           return cursor;

       }catch (Exception ex){
           ex.printStackTrace();
       }

       return cursor;
   }


   private String getHeaderTeacherComment(String year,String term,String exam){
       String returnText = "";
       try{
           SQLiteDatabase db = getReadableSQLiteConnection();
           Cursor commentsCursor = db.query(true,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,new String[]{LocalDBContract.NewCurriculumExamFormat.HEAD_TEACHER_COMMENT},
                   LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                           " AND " +
                            LocalDBContract.NewCurriculumExamFormat.TERM_NAME + " = ? " +
                            " AND " +
                            LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC + " = ? " +
                            " AND " +
                            LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "
                   ,new String[]{year,term,exam, mainStudentID},null,null,null,null);

           if(commentsCursor.moveToFirst()){
               returnText = commentsCursor.getString(commentsCursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.HEAD_TEACHER_COMMENT));
           }
           // Log.d(mContext.getPackageName().toUpperCase(), "Cursor Count: " );
       }catch (Exception ex){
           ex.printStackTrace();
       }

       return returnText;
   }

   private String getGradeFacilitatorComment(String year, String term,String exam){
       String returnText = "";
       try{
           SQLiteDatabase db = getReadableSQLiteConnection();
           Cursor commentsCursor = db.query(true,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,new String[]{LocalDBContract.NewCurriculumExamFormat.CLASS_TEACHER_COMMENT}
                   , LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                           " AND " +
                           LocalDBContract.NewCurriculumExamFormat.TERM_NAME + " = ? " +
                            " AND " +
                           LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC + " = ? " +
                           " AND " +
                           LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "

                   ,new String[]{year,term,exam, mainStudentID},null,null,null,null);

           if(commentsCursor.moveToFirst()){
               returnText = commentsCursor.getString(commentsCursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.CLASS_TEACHER_COMMENT));
           }
          // Log.d(mContext.getPackageName().toUpperCase(), "Cursor Count: " );
       }catch (Exception ex){
           ex.printStackTrace();
       }

       return returnText;
   }


   public CommentSection getNewExamFormatComments(String year, String term,String exam){
       CommentSection commentSection = new CommentSection(getHeaderTeacherComment(year,term,exam), getGradeFacilitatorComment(year,term,exam));
     return commentSection;
   }





   private Cursor getDefaultPortalExamProgressReport(){
       Cursor cursor = null;

       return null;
   }


   public ArrayList<TermSpinner> termToSelect(){
       ArrayList<TermSpinner> termSpinnerList =  new ArrayList<>();

       for(int termID = 0; termID <=3; termID++){
           if(termID == 0){
               TermSpinner termSpinner = new TermSpinner(termID, "Select Term");
               termSpinnerList.add(termSpinner);
           }else{
               TermSpinner termSpinner = new TermSpinner(termID, "Term "+ termID);
               termSpinnerList.add(termSpinner);
           }
       }

       return termSpinnerList;
   }


   public List<Object> getExam(){
       List<Activities> learningAreasList = new ArrayList<>();
       String year = "";
       String term = "";
       String exam = "";


       try{

           SQLiteDatabase db = getReadableSQLiteConnection();
          Cursor cursor = db.query(true,LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                   null,
                           LocalDBContract.NewCurriculumExamFormat.YEAID + " = ? " +
                                    " AND " +
                                    LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "
                   ,new String[]{defaultYear(), mainStudentID},null,null,null,null);

           if(cursor.moveToNext()) {

               year = cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.YEAID));
               term = cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.TERM_NAME));
               exam = cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC));
               learningAreasList = getPortalResultProgress(exam, year, term);

               Log.d(mContext.getPackageName().toUpperCase(), learningAreasList.toString());
           }

       }catch (Exception ex){
           ex.printStackTrace();
       }

       List<Object> list = new ArrayList<>();
       list.add(learningAreasList);// List<Activities> //0
       list.add(exam);//1
       list.add(year);//2
       list.add(term);//3
       return list;
   }


   public String maxYear(ArrayList<Integer> integerArrayList){
       int maxmax = 0;
       if(integerArrayList.size() > 0){

           for(int i = 0; i< integerArrayList.size() -1; i++){
               int max = integerArrayList.get(i);

               if(integerArrayList.get(i+1) > max){
                   max = integerArrayList.get(i+1);
               }
               maxmax = max;

           }
       }

       return String.valueOf(maxmax);


   }


   public String defaultYear(){

       ArrayList<Integer> integerArrayList = new ArrayList<>();

       String returnyear = "0";
       try{
           SQLiteDatabase db = getReadableSQLiteConnection();
//         Cursor  cursor = db.query( LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
//                   new String[]{"MAX("+LocalDBContract.NewCurriculumExamFormat.YEAID+")"},
//                   null
//                   , null, null, null, null, null);
           Cursor cursor = db.rawQuery("SELECT "+ LocalDBContract.NewCurriculumExamFormat.YEAID +" FROM " + LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE +
                   " WHERE " + LocalDBContract.NewCurriculumExamFormat.STUDID + " = " + mainStudentID , null);
          if(cursor.getCount() > 1){
              while(cursor.moveToNext()){
                  integerArrayList.add(cursor.getInt(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.YEAID)));
              }
              maxYear(integerArrayList);
          }else{
              if(cursor.moveToFirst()){
                  returnyear = cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.YEAID));
              }
          }

       }catch (Exception ex){
           ex.printStackTrace();
       }
        returnyear = maxYear(integerArrayList);

       return returnyear;
   }


   public String defaultTerm(){

       String returnTerm = "";
       Cursor cursor = null;
       try{

           ArrayList<TermSpinner> termSpinners = termToSelect();
           for(int i = termSpinners.size() - 1; i<=0; i--) {
               returnTerm = termSpinners.get(i).getTermFor();
               SQLiteDatabase db = getReadableSQLiteConnection();
               cursor = db.query(true, LocalDBContract.NewCurriculumExamFormat.NEWCURRICULUMEXAMFORMAT_TABLE,
                       null,
                       LocalDBContract.NewCurriculumExamFormat.TERM_NAME + " = ? " +
                               " AND " +
                               LocalDBContract.NewCurriculumExamFormat.STUDID + " = ? "
                       , new String[]{termSpinners.get(i).getTermFor(), mainStudentID}, null, null, null, null);

               if(cursor.getCount() > 0)
                   break;
           }

       }catch (Exception ex){
           ex.printStackTrace();
       }

       return returnTerm;
   }



}
