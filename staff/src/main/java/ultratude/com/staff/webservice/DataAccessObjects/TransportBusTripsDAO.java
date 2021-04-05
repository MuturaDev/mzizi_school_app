package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.RequestModels.TransportBusTrips;
import ultratude.com.staff.webservice.RequestModels.TransportSessions;

public class TransportBusTripsDAO {

    private Context context;
    private DatabaseConnectionOpenHelper sqhelp;


    public TransportBusTripsDAO(Context context){

        this.context = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }


    public void saveTransportBusTrips(List<TransportBusTrips> sessionsList){

        try{

            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                deleteAllTransportBusTrips();

                for(TransportBusTrips session : sessionsList){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ConnSQLiteContract.PortalTransportBusTrip.ID, session.getId());
                    contentValues.put(ConnSQLiteContract.PortalTransportBusTrip.NAME, session.getName());

                    db.insertOrThrow(ConnSQLiteContract.PortalTransportBusTrip.PORTAL_TRANSPORT_BUSTRIP_TABLE,null,contentValues);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

    }

    public ArrayList<TransportBusTrips> getTransportBusTripsArrayList(){
        ArrayList<TransportBusTrips> returnList = new ArrayList<>();
        try{

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            if(db.isOpen()){
                Cursor cursor = db.query(ConnSQLiteContract.PortalTransportBusTrip.PORTAL_TRANSPORT_BUSTRIP_TABLE, null,null,null,null,null,null);
                while(cursor.moveToNext()){
                    TransportBusTrips session = new TransportBusTrips(
                            cursor.getInt(cursor.getColumnIndex(ConnSQLiteContract.PortalTransportBusTrip.ID)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalTransportBusTrip.NAME))

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

    public void deleteAllTransportBusTrips(){
        try {

            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            if (db.isOpen()) {
                db.delete(ConnSQLiteContract.PortalTransportBusTrip.PORTAL_TRANSPORT_BUSTRIP_TABLE,null,null);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }


}
