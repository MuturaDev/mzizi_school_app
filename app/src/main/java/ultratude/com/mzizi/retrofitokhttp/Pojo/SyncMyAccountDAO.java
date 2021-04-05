package ultratude.com.mzizi.retrofitokhttp.Pojo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ultratude.com.mzizi.notificationpg.NotificationSQDB.ConnSQLiteOpenHelper;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;

/**
 * Created by James on 24/09/2018.
 */

public class SyncMyAccountDAO {

    private Context context;

    private ConnSQLiteOpenHelper openHelper;

    private String mainStudentID;

    public SyncMyAccountDAO(Context context){
        openHelper = new ConnSQLiteOpenHelper(context);
        this.context = context;
        ParentMziziDatabase db = ParentMziziDatabase.getInstance(context.getApplicationContext());
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




    public int insertSyncMyAccountResult(SyncMyAccountResult syncMyAccountResults){
        long id = 0;
        if(syncMyAccountResults != null){

            SQLiteDatabase sqdb = getWritableSQLiteConnection();
            ContentValues insertContent = new ContentValues();

            insertContent.put(LocalDBContract.SyncMyAccountTB.BALANCE, String.valueOf(syncMyAccountResults.getBalance()));
            insertContent.put(LocalDBContract.SyncMyAccountTB.BILLING_BALANCE, String.valueOf(syncMyAccountResults.getBillingBalance()));
            insertContent.put(LocalDBContract.SyncMyAccountTB.ORGANIZATION_ID, String.valueOf(syncMyAccountResults.getOrganizationID()));
            insertContent.put(LocalDBContract.SyncMyAccountTB.PORTAL_ACCOUNT, String.valueOf(syncMyAccountResults.getPortalAccount()));
            insertContent.put(LocalDBContract.SyncMyAccountTB.PORTAL_PAYBILL, String.valueOf(syncMyAccountResults.getPortalPaybill()));
            insertContent.put(LocalDBContract.SyncMyAccountTB.STUDID, syncMyAccountResults.getStudID());
            sqdb.insert(LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB, null, insertContent);

//
            sqdb.close();
        }


        return Integer.parseInt(String.valueOf(id));

    }

    public int deleteAllSyncMyAccountResult(){

        SQLiteDatabase sqdb = getWritableSQLiteConnection();

        int rowAff =  sqdb.delete( LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB,
                null,null);
        sqdb.close();

        return rowAff;
        //sqdb.execSQL(DELETE_NOTIFICATION_QUERY);

    }


    public int deleteForSyncMyAccountResult(String StudentID){
        SQLiteDatabase sqdb = getWritableSQLiteConnection();

        int rowAff =  sqdb.delete( LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB,
                LocalDBContract.SyncMyAccountTB.STUDID + " = ? ",
                new String[]{StudentID});
        sqdb.close();

        return rowAff;
        //sqdb.execSQL(DELETE_NOTIFICATION_QUERY);
    }


    public SyncMyAccountResult getFilteredPortalStudentInfoResult(){

        SyncMyAccountResult syncMyAccountResult = new SyncMyAccountResult();
        Cursor cursor = getReadableSQLiteConnection().query(LocalDBContract.SyncMyAccountTB.SYNC_MYACCOUNTTB,
                null,
                LocalDBContract.SyncMyAccountTB.STUDID + " = ? ",
                new String[]{mainStudentID},
                null,null,null,null);

        while(cursor.moveToNext()){

            syncMyAccountResult = new SyncMyAccountResult();
            syncMyAccountResult.setBalance((cursor.getString(cursor.getColumnIndex(LocalDBContract.SyncMyAccountTB.BALANCE))));
            syncMyAccountResult.setBillingBalance((cursor.getString(cursor.getColumnIndex(LocalDBContract.SyncMyAccountTB.BILLING_BALANCE))));
            syncMyAccountResult.setOrganizationID((cursor.getString(cursor.getColumnIndex(LocalDBContract.SyncMyAccountTB.ORGANIZATION_ID))));
            syncMyAccountResult.setPortalAccount((cursor.getString(cursor.getColumnIndex(LocalDBContract.SyncMyAccountTB.PORTAL_ACCOUNT))));
            syncMyAccountResult.setPortalPaybill((cursor.getString(cursor.getColumnIndex(LocalDBContract.SyncMyAccountTB.PORTAL_PAYBILL))));

        }

        cursor.close();

        return syncMyAccountResult;
    }
}
