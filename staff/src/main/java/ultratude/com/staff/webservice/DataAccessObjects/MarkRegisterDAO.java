package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ultratude.com.staff.chips.MarkAttendanceChip;
import ultratude.com.staff.spinnermodel.AttendanceRollCallSessionSpinner;
import ultratude.com.staff.spinnermodel.AttendanceStatusRadioButton;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.RequestModels.PortalMarkRegisterRequest;

/**
 * Created by James on 11/01/2019.
 */

public class MarkRegisterDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public MarkRegisterDAO(Context mContext){
        this.mContext = mContext;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }


    public long saveMarkRegister(ArrayList<PortalMarkRegisterRequest> portalMarkRegisterRequestList, String classStream){

        Cursor cursor = getMarkRegisterCursor();

        long saved = 0;
        long countAfterSaving = 0;

        long savedOrUpdated = 0;

        if(cursor != null) {


            long countBeforeSaving = Long.valueOf(cursor.getCount());





            try {
                SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {


                    //should not just save, should check if the file exist, date and classstream, session
                    //String classStream,String session, String currentDate
                    if (portalMarkRegisterRequestList.size() > 0) {


                        //IF EXIST(select * from markregister WHERE session = rollcallsession AND classstream = classstream AND dateRecorded = dateRecorded)
                        if (isNewAttendanceRegister(classStream, portalMarkRegisterRequestList.get(0).getRollCallSession(), portalMarkRegisterRequestList.get(0).getDateRecorded())) {

                            //though should be removed
                            // deleteRegister();


                            for (int i = 0; i < portalMarkRegisterRequestList.size(); i++) {

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STUDENT_ID, portalMarkRegisterRequestList.get(i).getStudentID());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STUDENTFULLNAME, portalMarkRegisterRequestList.get(i).getStudentFullName());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STATUS, portalMarkRegisterRequestList.get(i).getStatus());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.DATERECORDED, portalMarkRegisterRequestList.get(i).getDateRecorded());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION, portalMarkRegisterRequestList.get(i).getRollCallSession());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STAFF_ID, portalMarkRegisterRequestList.get(i).getStaffID());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.REMARKS, portalMarkRegisterRequestList.get(i).getRemarks());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.APPCODE, portalMarkRegisterRequestList.get(i).getAppCode());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM, classStream);



                                long id = db.insertOrThrow(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE, null, contentValues);


                            }

                            countAfterSaving = Long.valueOf(getMarkRegisterCursor().getCount());

                            // 27 - 0 or 34 - 5 ;//countAfterSaving will always be greater, 1 - 0, 5 - 4;
                            savedOrUpdated = (countAfterSaving - countBeforeSaving);


                        } else {
                            for (PortalMarkRegisterRequest portalMarkRegisterRequest : portalMarkRegisterRequestList) {

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STUDENT_ID, portalMarkRegisterRequest.getStudentID());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STUDENTFULLNAME, portalMarkRegisterRequest.getStudentFullName());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STATUS, portalMarkRegisterRequest.getStatus());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.DATERECORDED, portalMarkRegisterRequest.getDateRecorded());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION, portalMarkRegisterRequest.getRollCallSession());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STAFF_ID, portalMarkRegisterRequest.getStaffID());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.REMARKS, portalMarkRegisterRequest.getRemarks());
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.APPCODE, portalMarkRegisterRequest.getAppCode());

                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM, classStream);
                                contentValues.put(ConnSQLiteContract.PortalMarkRegister.STUDENT_ID , 0);//SET NOT SENT FOR THE UPDATED


                                ///db.insertOrThrow(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE, null, contentValues);
                                //if you use, daterocored, rollcallsession, classstream, you will notice all recordeds will the updated with the values of the last mark register list student
                                //also use the student id, to update individual records
                                //to check for todays mark register use, todays date (can not be changed since its current date only) , rollcallsession(can be changed), classstream(can be changed)

                                long id = db.update(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE, contentValues,
                                        ConnSQLiteContract.PortalMarkRegister.STUDENT_ID + " = ? " +
                                                "AND "
                                                + ConnSQLiteContract.PortalMarkRegister.DATERECORDED + " = ? " +
                                                "AND "
                                                + ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION + " = ? " +
                                                "AND "
                                                + ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM + " = ? "
                                        , new String[]{portalMarkRegisterRequest.getStudentID(), portalMarkRegisterRequest.getDateRecorded(), portalMarkRegisterRequest.getRollCallSession(), classStream});

//                            Log.d(mContext.getPackageName().toUpperCase(), "MarkRegisterListForCurrentDate: " + "ID: " + id + "\nNAME: " + portalMarkRegisterRequest.getStudentFullName()
//                                    + "\nDateRecorded: " + portalMarkRegisterRequest.getDateRecorded() + "\nRollCallSession: " + portalMarkRegisterRequest.getRollCallSession() +
//                                    "\nClassStream: " + classStream);

                            }

                            //count after updating
                            countAfterSaving = 0;//not saving but updating
                            // 27 - 0 or 34 - 5 ;//countAfterSaving will always be greater, 1 - 0, 5 -
                            //this count before can contained data saved before, if not sent to the api
                            //savedOrUpdated = (countAfterSaving - countBeforeSaving);//meaning savedOrUpdated will be equal to countBeforeSaving
                            //instead we use a number to identify when updated,,, then you prompt the user, <Date> register is successfully updated
                            savedOrUpdated = -2;//updated


                        }
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }


        }

        return savedOrUpdated;
    }



    private boolean isNewAttendanceRegister(String classStream,String session, String currentDate){

        try{

            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();


            String groupBy = null;
            String having = null;
            String orderBy = ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME;


            Cursor attendanceCursor  = db.query(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE
                    , null
                    , ConnSQLiteContract.PortalMarkRegister.DATERECORDED + " = ? " +
                            "AND "
                            + ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION + " = ? " +
                            "AND "
                            + ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM + " = ? "
                    ,new String[]{currentDate, session/*String.valueOf(session.getSessionID())*/, classStream}, groupBy, having, orderBy);


            if(attendanceCursor.getCount() > 0){//that means there exist data with those candidate keys
                return false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            sqhelp.closeDatabase();
        }


        return true;
    }


    public Cursor getMarkRegisterCursor(){

        Cursor cursor = null;
        try{
            SQLiteDatabase  db = sqhelp.getReadableDatabaseConnection();

            cursor = db.query(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE, null, null, null, null, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
//            db.close();
        }

        return cursor;

    }


    //delete each row
    //this will change to deleteOneMarkRegisterMarkedAsSent()//1 for true and 0 for false
    public void deleteOneMarkRegister(int[] ultraDataIDs) {

            try {
                SQLiteDatabase db = sqhelp.getWritableDatabase();

                if (db.isOpen()) {

                    for (int i : ultraDataIDs) {
                        String query = "DELETE FROM " + ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE + " WHERE " + ConnSQLiteContract.PortalMarkRegister._ID + " = " + i + ";";
                        db.execSQL(query);
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            } finally {
                sqhelp.closeDatabase();
            }

    }


    class StreamSession{
        private String sesion;
        private String classStream;

        public StreamSession(String session , String classStream){
            this.sesion = session;
            this.classStream = classStream;
        }

        public String getSesion() {
            return sesion;
        }

        @Override
        public String toString() {
            return "StreamSession{" +
                    "sesion='" + sesion + '\'' +
                    ", classStream='" + classStream + '\'' +
                    '}';
        }

        public String getClassStream() {
            return classStream;
        }
    }


    public List<StreamSession> getMarkRegisterSessionWhereClassStream(){
        Calendar cal  =  Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");

        List<String> streamList = getMarkRegisterStream(sdf.format(cal.getTime()));

        List<StreamSession> streamSessionList = new ArrayList<>();

        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            for(String stream : streamList){
                Cursor markedAttendance = db.query(true,ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE,
                        new String[]{ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION},
                        ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM + " = ? ",
                        new String[]{stream},
                        null,
                        null,null,null);

                while(markedAttendance.moveToNext()){
                    StreamSession streamSession = new StreamSession(
                            markedAttendance.getString(markedAttendance.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION)),
                            stream
                    );

                    streamSessionList.add(streamSession);

                }
            }

           // Log.d(mContext.getPackageName().toUpperCase(), streamSessionList.toString());

            return streamSessionList;


        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }

        return streamSessionList;
    }


    public List<String> getMarkRegisterStream(String date){

        List<String> streamList = new ArrayList<>();

        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            Cursor markedAttendance = db.query(true,ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE,
                    new String[]{ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM},
                    ConnSQLiteContract.PortalMarkRegister.DATERECORDED + " = ? ",
                    new String[]{date},
                    null,
                    null,null,null);

            while(markedAttendance.moveToNext()){
                streamList.add(markedAttendance.getString(markedAttendance.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM)));
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            //sqhelp.closeDatabase();
        }


      //  Log.d(mContext.getPackageName().toUpperCase(), streamList.toString());
        return streamList;
    }


    public List<MarkAttendanceChip> getStudentMarkRegisterForCurrentDateForChips(){

        List<MarkAttendanceChip> markAttendanceChipList = new ArrayList<>();


        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            Calendar cal  =  Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");


         // List<String> streamList =  getMarkRegisterStream(sdf.format(cal.getTime()));
            List<StreamSession> streamList = getMarkRegisterSessionWhereClassStream();


            for(StreamSession stream : streamList){

                ArrayList<Object> data = getStudentMarkRegisterForStream(stream, sdf.format(cal.getTime()));

                MarkAttendanceChip markAttendanceChip = new MarkAttendanceChip();
                markAttendanceChip.setRollCallSession(stream.getSesion());
                markAttendanceChip.setCourseLevelName(stream.getClassStream());
                markAttendanceChip.setStudentPortalMarkRegisterRequestList((ArrayList<PortalMarkRegisterRequest>)  data.get(0));
                markAttendanceChip.setStudentRegistrationNumberList((ArrayList<String>) data.get(1));

                markAttendanceChipList.add(markAttendanceChip);
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

      //  Log.d(mContext.getPackageName().toUpperCase(), markAttendanceChipList.toString());
        return markAttendanceChipList;
    }


    public ArrayList<Object> getStudentMarkRegisterForStream(final StreamSession stream, final String date){
        ArrayList<PortalMarkRegisterRequest> markedRegisterList = new ArrayList<>();

        ArrayList<String> registrationNumbersList = new ArrayList<>();

        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();


            String groupBy = null;
            String having = null;
            String orderBy = ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME;

            Cursor attendanceCursor  = db.query(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE
                    , null
                    , ConnSQLiteContract.PortalMarkRegister.DATERECORDED + " = ? " +
                            "AND "
                            + ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION + " = ? " +
                            "AND "
                            + ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM + " = ? "
                    ,new String[]{date, String.valueOf(stream.getSesion()), stream.getClassStream()}, groupBy, having, orderBy);


            int count = 0;
            while(attendanceCursor.moveToNext()){

                String studentID = attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STUDENT_ID));
                registrationNumbersList.add(new StudentDAO(mContext).getRegistrationNumberForThisStudentID(studentID));
                PortalMarkRegisterRequest portalMarkRegisterRequest = new PortalMarkRegisterRequest(
                        studentID,
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STUDENTFULLNAME)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STATUS)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.DATERECORDED)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STAFF_ID)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.REMARKS)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.APPCODE))
                );
                markedRegisterList.add(portalMarkRegisterRequest);



            }


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }


        ArrayList<Object> list = new ArrayList<>();
        list.add(markedRegisterList);//ArrayList<PortalMarkRegisterRequest>
        list.add(registrationNumbersList);//ArrayList<String>


        return list;
    }




    public List<Object>  getStudentMarkRegisterForCurrentDate(String classStream,AttendanceRollCallSessionSpinner session, String currentDate){
        ArrayList<PortalMarkRegisterRequest> markedRegisterList = new ArrayList<>();

        ArrayList<String> registrationNumbersList = new ArrayList<>();

        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();


            String groupBy = null;
            String having = null;
            String orderBy = ConnSQLiteContract.PortalSaveAllStudents.STUDENTFULLNAME;

            Cursor attendanceCursor  = db.query(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE
                    , null
                    , ConnSQLiteContract.PortalMarkRegister.DATERECORDED + " = ? " +
                            "AND "
                            + ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION + " = ? " +
                            "AND "
                            + ConnSQLiteContract.PortalMarkRegister.CLASSSTREAM + " = ? "
                    ,new String[]{currentDate, String.valueOf(session.getSessionID()), classStream}, groupBy, having, orderBy);


            int count = 0;
            while(attendanceCursor.moveToNext()){

                String studentID = attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STUDENT_ID));
                registrationNumbersList.add(new StudentDAO(mContext).getRegistrationNumberForThisStudentID(studentID));
                PortalMarkRegisterRequest portalMarkRegisterRequest = new PortalMarkRegisterRequest(
                        studentID,
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STUDENTFULLNAME)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STATUS)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.DATERECORDED)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STAFF_ID)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.REMARKS)),
                        attendanceCursor.getString(attendanceCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.APPCODE))
                );
                markedRegisterList.add(portalMarkRegisterRequest);



            }


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }


        List<Object> list = new ArrayList<>();
        list.add(markedRegisterList);//ArrayList<PortalMarkRegisterRequest>
        list.add(registrationNumbersList);//ArrayList<String>


        return list;
    }


    public List<Object> getMarkRegisterList(){
        List<PortalMarkRegisterRequest> portalMarkRegisterRequestList = new ArrayList<>();

        Cursor dutyRosterCursor = getMarkRegisterCursor();
        int[] dutyRosterIDs = new int[dutyRosterCursor.getCount()];

        try{

            int count = 0;
            while(dutyRosterCursor.moveToNext()){

                int id = dutyRosterCursor.getInt(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister._ID));
                dutyRosterIDs[count] = id;
                count++;

                PortalMarkRegisterRequest portalMarkRegisterRequest = new PortalMarkRegisterRequest(
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STUDENT_ID)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STUDENTFULLNAME)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STATUS)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.DATERECORDED)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.ROLLCALLSESSION)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.STAFF_ID)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.REMARKS)),
                        dutyRosterCursor.getString(dutyRosterCursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.APPCODE))
                );
                portalMarkRegisterRequestList.add(portalMarkRegisterRequest);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        List<Object> list = new ArrayList<>();
        list.add(portalMarkRegisterRequestList);//0 List<DutyRoster>
        list.add(dutyRosterIDs);//1 int[]

        return list;
    }


    public ArrayList<AttendanceStatusRadioButton> getAttendanceStatusesRadioButtonList(){
        // --0:ALL, 1:PRESENT, 2:ABSENT
        AttendanceStatusRadioButton attendanceStatusRadioButton1 = new AttendanceStatusRadioButton(1,"Present");
        AttendanceStatusRadioButton attendanceStatusRadioButton2 = new AttendanceStatusRadioButton(2,"Absent");


        ArrayList<AttendanceStatusRadioButton> list = new ArrayList<>();
        list.add(attendanceStatusRadioButton1);
        list.add(attendanceStatusRadioButton2);

        return list;
    }



    public long getAttendanceStatusIDRadioButtonList(String statusName){

        long id = 0;

        for(AttendanceStatusRadioButton status : getAttendanceStatusesRadioButtonList()){
            if(status.getStatusName().equalsIgnoreCase(statusName)){
                id = status.getStatusID();
                break;
            }
        }

        return id;
    }


    //YOU SHOULD FETCH THIS DATA FROM THE SERVICE
//    public ArrayList<AttendanceRollCallSessionSpinner> getAttendanceRollCallSessionSpinnerList(){
//
//         final String[] rollCallSessions = {"Select a session","Morning Session", "Mid Morning Sessions", "After Noon Sessions", "Evening Session", "Night Session", "General Session"};
//
//        ArrayList<AttendanceRollCallSessionSpinner> list = new ArrayList<>();
//        for(int i = 0; i< rollCallSessions.length; i++){
//            AttendanceRollCallSessionSpinner rollcallSession = new AttendanceRollCallSessionSpinner(i, rollCallSessions[i]);
//            list.add(rollcallSession);
//
//        }
//
//        return list;
//    }



    public long deleteRegister(){

        long id = 0;

        try{

            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            id =   db.delete(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE, null,null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {

            sqhelp.closeDatabase();
        }

        return id;

    }



    private int[] getIDsOfRecordsWithDateTimeBeforeToday(){


        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            Cursor cursor = db.query(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE
                    , new String[]{ConnSQLiteContract.PortalMarkRegister.DATERECORDED, ConnSQLiteContract.PortalMarkRegister._ID}
                    , null

                    ,null, null, null, null);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            //current date
            Date currentDate = new Date();
            if(cursor != null){
                int[] ids = new int[cursor.getCount()];
                int count = 0;
                while(cursor.moveToNext()){

                    String dates = cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister.DATERECORDED));
                    int id = cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalMarkRegister._ID));

                    //date supplied
                    Date suppliedDate = new Date(dates);
                    //current date is after supplied date
                    if(currentDate.after(suppliedDate)){

                        ids[count] = id;
                        count++;
                    }

                }

                return  ids;
            }


        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
           // sqhelp.closeDatabase();
        }

        return null;
    }



    //the delete method will delete using two creterias, date and marked as sent
    //IDS ARE FOR THE RECORDS SENT
    public void markAsSent(int[] ultraDataIDs) {

        try {
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            if (db.isOpen()) {

                //DELETE RECORDS OF PRVIOUS DATE
                int[] idsToDelete = getIDsOfRecordsWithDateTimeBeforeToday();
                   if(idsToDelete != null){
                       for(int i : idsToDelete){
                           String query = "DELETE FROM " + ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE + " WHERE " + ConnSQLiteContract.PortalMarkRegister._ID + " = " + i + " AND "+ ConnSQLiteContract.PortalMarkRegister.STATUSSENT + " = " + 1 +  ";";
                           db.execSQL(query);
                       }

                   }

                for (int i : ultraDataIDs) {

                    //MARK AS SENT
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ConnSQLiteContract.PortalMarkRegister.STATUSSENT, 1);//true mark sent
                    long id = db.update(ConnSQLiteContract.PortalMarkRegister.PORTALMARKREGISTER_TABLE, contentValues,
                            ConnSQLiteContract.PortalMarkRegister._ID + " = ? "

                            , new String[]{String.valueOf(i)});
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            sqhelp.closeDatabase();
        }

    }
}
