package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.RequestModels.TransportSessions;

public class TransportSessionsDAO {


    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;


    public TransportSessionsDAO(Context context){

        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }


    public void saveTransportSessions(List<TransportSessions> sessionsList){

        try{

            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                deleteAllTranportSessions();

                for(TransportSessions session : sessionsList){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ConnSQLiteContract.PortalTransportSessions.ID, session.getId());
                    contentValues.put(ConnSQLiteContract.PortalTransportSessions.NAME, session.getName());
                    contentValues.put(ConnSQLiteContract.PortalTransportSessions.CODE, session.getCode());

                    db.insertOrThrow(ConnSQLiteContract.PortalTransportSessions.PORTAL_TRANSPORT_SESSIONS_TABLE,null,contentValues);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

    }

    public ArrayList<TransportSessions> getTransportSessionArrayList(){
        ArrayList<TransportSessions> returnList = new ArrayList<>();
        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            if(db.isOpen()){
                Cursor cursor = db.query(ConnSQLiteContract.PortalTransportSessions.PORTAL_TRANSPORT_SESSIONS_TABLE, null,null,null,null,null,null);
                while(cursor.moveToNext()){

                    TransportSessions session = new TransportSessions(
                            cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalTransportSessions.ID)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalTransportSessions.NAME)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalTransportSessions.CODE))
                    );
                    returnList.add(session);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return returnList;
    }

    public void deleteAllTranportSessions(){
        try {

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            if (db.isOpen()) {
                db.delete(ConnSQLiteContract.PortalTransportSessions.PORTAL_TRANSPORT_SESSIONS_TABLE,null,null);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }

}
