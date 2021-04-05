package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.spinnermodel.AttendanceRollCallSessionSpinner;
import ultratude.com.staff.spinnermodel.ClassStreamSpinner;
import ultratude.com.staff.spinnermodel.StudentSpinner;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.Student;

/**
 * Created by James on 12/01/2019.
 */

public class StudentDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public StudentDAO(Context context){
        this.mContext = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }


    public long saveStudent(List<Student> studentList){
        long id = 0;

        Cursor cursor = null;

        SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

        if(db.isOpen()){
            try{

                deleteAllStudents();

                for(Student saveStudent : studentList){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.STUDENTID, saveStudent.getStudentID().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME, saveStudent.getStudentFullName().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.REGISTRATIONNUMBER, saveStudent.getRegistrationNumber().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM, saveStudent.getClassStream().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.SCHOOLNAME, saveStudent.getSchoolName().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.SCHOOLID, saveStudent.getSchoolID().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.ORGANIZATIONID, saveStudent.getOrganizationID().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.CURRENTTERM, saveStudent.getCurrentTerm().trim());
                    contentValues.put(ConnSQLiteContract.PortalSaveAllStudents.CURRENTYEAR, saveStudent.getCurrentYear().trim());


                    id += db.insertOrThrow(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE, null, contentValues);

                }

            }catch(Exception e){
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }
        }


        //return id;
        long id2 = 0;

        Cursor cursor1 = getStudentCursor();
        if(cursor1 != null){
            id2 = (long) getStudentCursor().getCount();
        }

        return id2;
    }


    public Cursor getStudentCursor(){
        Cursor cursor = null;
        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
//            db.close();
            //sqhelp.closeDatabase();
        }

        return cursor;


    }


    //delete each row
    public void deleteOneStudent(int[] ultraDataIDs) {

        DatabaseConnectionOpenHelper sqhelp = new DatabaseConnectionOpenHelper(mContext.getApplicationContext());

            try {

                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

                if (db.isOpen()) {
                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE + " WHERE " + ConnSQLiteContract.PortalSaveAllStudents._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
                sqhelp.closeDatabase();
            }

    }


    public ArrayList<PortalMarkRegisterRequest> getDummyStudentMarkRegisterList(String classStream){

        Student student1 = new Student(
                "PARRISH  BATTLE",
                "12483",
                "12483",
                "Pre-Unit",
                "Mzizi Primary School",
                "3",
                "1",
                "3",
                "2019"
        );

        Student student2 = new Student(
                "JEANNINE  CAMPOS",
                "12488",
                "12488",
                "Pre-Unit",
                "Mzizi Primary School",
                "3",
                "1",
                "3",
                "2019"
        );

        Student student3 = new Student(
                "TAMMI  WISE",
                "12499",
                "12499",
                "Class 1 RED",
                "Mzizi Primary School",
                "3",
                "1",
                "3",
                "2019"
        );



        Student student4 = new Student(
                "BARNETT  PORTER",
                "12502",
                "12502",
                "Class 1 RED",
                "Mzizi Primary School",
                "3",
                "1",
                "3",
                "2019"
        );


        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);


        Staff staff = new StaffDao(mContext).getUserThatSignedUp();
        ArrayList<PortalMarkRegisterRequest> list = new ArrayList<>();

        for(Student student : studentList){

            if(student.getClassStream().equalsIgnoreCase(classStream)){
                PortalMarkRegisterRequest markStudentRegister = new PortalMarkRegisterRequest(
                        student.getStudentID(),
                        student.getStudentFullName(),
                        "",//status
                        "",//dateRecorded
                        "",//rollCallSession
                        staff.getStaff_ID(),
                        "",//remarks
                        staff.getAppcode()
                );
                list.add(markStudentRegister);
            }


        }

        return list;
    }

    public String getFullnamesForThisStudentID(String studentID){
        String fullNames = "";

            try {
                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

                if(db.isOpen()) {

                    Cursor cursor = db.query(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE,
                            new String[]{ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME},
                            ConnSQLiteContract.PortalSaveAllStudents.STUDENTID + " = ? ",
                            new String[]{studentID},
                            null,
                            null,
                            null);
                    if (cursor.moveToFirst()) {
                        fullNames = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }



        return fullNames;

    }


    public String getRegistrationNumberForThisStudentID(String studentID){
        String registrationNumber = "";

            try {
                SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

                if(db.isOpen()) {

                    Cursor cursor = db.query(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE,
                            new String[]{ConnSQLiteContract.PortalSaveAllStudents.REGISTRATIONNUMBER},
                            ConnSQLiteContract.PortalSaveAllStudents.STUDENTID + " = ? ",
                            new String[]{studentID},
                            null,
                            null,
                            null);
                    if (cursor.moveToFirst()) {
                        registrationNumber = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.REGISTRATIONNUMBER));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                sqhelp.closeDatabase();
            }

        return registrationNumber;
    }


    public List<Object> getStudentsList(){
        List<Student> studentList = new ArrayList<>();

        Cursor studentCursor = getStudentCursor();
        int[] studentIDs = new int[studentCursor.getCount()];

        try{

            int count = 0;
            while(studentCursor.moveToNext()){

                int id = studentCursor.getInt(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents._ID));
                studentIDs[count] = id;
                count++;

                Student student = new Student(
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTID)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.REGISTRATIONNUMBER)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.SCHOOLNAME)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.SCHOOLID)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.ORGANIZATIONID)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.CURRENTTERM)),
                        studentCursor.getString(studentCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.CURRENTYEAR))
                );
                studentList.add(student);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        List<Object> list = new ArrayList<>();
        list.add(studentList);//0 List<Student>
        list.add(studentIDs);//1 int[]

        return list;
    }






    public List<Object>  getStudentMarkRegister(ClassStreamSpinner classStream, AttendanceRollCallSessionSpinner session, String currentDate){



        ArrayList<PortalMarkRegisterRequest> studentPortalMarkRegisterRequestList = new ArrayList<>();

        ArrayList<String> registrationNumbersList = new ArrayList<>();


        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            String groupBy = null;
            String having = null;
            String orderBy = ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME;

            Cursor attendanceCursor  = db.query(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE
                    ,new String[]{ConnSQLiteContract.PortalSaveAllStudents.STUDENTID, ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME}
                    , ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM + " = ? "
                    ,new String[]{classStream.getClassStreamName()}, groupBy, having, orderBy);


            Staff staff = new StaffDao(mContext).getUserThatSignedUp();

           // Toast.makeText(mContext, "Cursor: " + String.valueOf(attendanceCursor.getCount())+ " Staff: " + staff.toString(), Toast.LENGTH_LONG).show();


            while(attendanceCursor.moveToNext()){
                String studentFUllName = attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME));
                int studentID = attendanceCursor.getInt(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTID));

                registrationNumbersList.add(getRegistrationNumberForThisStudentID(String.valueOf(studentID)));

                PortalMarkRegisterRequest markStudentRegister = new PortalMarkRegisterRequest(
                       String.valueOf(studentID),
                        studentFUllName,
                        String.valueOf(new MarkRegisterDAO(mContext).getAttendanceStatusIDRadioButtonList("Present")),//status
                        currentDate,//dateRecorded
                        String.valueOf(session.getSessionID()),//rollCallSession
                        staff.getStaff_ID(),
                        "",//remarks
                        staff.getAppcode()
                );
                studentPortalMarkRegisterRequestList.add(markStudentRegister);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }


        List<Object> list = new ArrayList<>();
        list.add(studentPortalMarkRegisterRequestList);//ArrayList<PortalMarkRegisterRequest>
        list.add(registrationNumbersList);//ArrayList<String>
        return list;
    }



    public ArrayList<StudentSpinner> getStudentSpinnerList(String classStream){


        ArrayList<StudentSpinner> studentSpinnerList = new ArrayList<>();
        StudentSpinner defaultStudent = new StudentSpinner(Integer.valueOf(0), "Select a Student");
        studentSpinnerList.add(defaultStudent);//0


        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            Cursor cursor = db.query(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE
                    , new String[]{ConnSQLiteContract.PortalSaveAllStudents.STUDENTID, ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME}
                    ,ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM + " = ? "
                    ,new String[]{classStream}
                    ,null
                    ,null
                    , ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME);

            //Toast.makeText(mContext,"Count: " + String.valueOf( cursor.getCount()), Toast.LENGTH_LONG).show();

            while(cursor.moveToNext()){



                    String studentFUllName = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME));
                    String studentID = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.STUDENTID));

                   // Toast.makeText(mContext, "Name:  " + studentFUllName+ "  ID :" + studentID, Toast.LENGTH_LONG).show();

                    StudentSpinner student = new StudentSpinner(Integer.valueOf(studentID), studentFUllName);
                    studentSpinnerList.add(student);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
        return studentSpinnerList;
    }



    public List<String> getClassStreamList(){


        List<String> classStreamList = new ArrayList<>();
        classStreamList.add("Select a Class");//0

        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            Cursor cursor = db.query(true,ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE, new String[]{ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM}, null, null, null, null, null,null);
            while(cursor.moveToNext()){
                String classStream  = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalSaveAllStudents.CLASSSTREAM));
                classStreamList.add(classStream);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return classStreamList;
    }




    public void deleteAllStudents(){

        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            db.delete(ConnSQLiteContract.PortalSaveAllStudents.PORTALSAVEALLSTUDENTS_TABLE, null,null);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

    }
}





