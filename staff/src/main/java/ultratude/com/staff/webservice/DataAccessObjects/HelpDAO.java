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
import ultratude.com.staff.webservice.ResponseModels.Help;

/**
 * Created by James on 14/05/2019.
 */

public class HelpDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqHelp;

    public HelpDAO(Context mContext) {
        this.mContext = mContext;
        sqHelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }

    public long saveHelp(List<Help> helpList) {



        Log.d(mContext.getPackageName().toUpperCase(), helpList.toString());


        Cursor cursor = getAllSupportHelpCursor();
        long saved = 0;

        if(cursor != null) {


            long countBeforeSaving = (long) cursor.getCount();

            try {

                SQLiteDatabase db = sqHelp.getWriteableDatabaseConnection();
                if (db.isOpen()) {

                    deleteAllHelpSupport();

                    for (Help help : helpList) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ConnSQLiteContract.PortalGetMziziAppSupportHelp.ID, help.getHelpID());
                        contentValues.put(ConnSQLiteContract.PortalGetMziziAppSupportHelp.HELP_DESCRIPTION, help.getHelpText());
                        contentValues.put(ConnSQLiteContract.PortalGetMziziAppSupportHelp.OPERATION_NAME, help.getOperationName());

                        db.insert(ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE, null, contentValues);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                sqHelp.closeDatabase();
            }

            long countAfterSaving = (long) getAllSupportHelpCursor().getCount();
             saved = countAfterSaving - countBeforeSaving;

        }
        return saved;

    }


    public Cursor getAllSupportHelpCursor() {
        Cursor cursor = null;

        try {
            SQLiteDatabase db = sqHelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE, null, null, null, null, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cursor;
    }

    public List<Help> getSupportHelpList() {
        List<Help> helpList = new ArrayList<>();

        Cursor helpCursor = getAllSupportHelpCursor();

        Log.d(mContext.getPackageName().toUpperCase(), "Cursor Count: " + String.valueOf(helpCursor.getCount()));

        try {


            while(helpCursor.moveToNext()) {

                Help help = new Help();
                help.setHelpID(helpCursor.getString(helpCursor.getColumnIndex(ConnSQLiteContract.PortalGetMziziAppSupportHelp.ID)));
                help.setHelpText(helpCursor.getString(helpCursor.getColumnIndex(ConnSQLiteContract.PortalGetMziziAppSupportHelp.HELP_DESCRIPTION)));
                help.setOperationName(helpCursor.getString(helpCursor.getColumnIndex(ConnSQLiteContract.PortalGetMziziAppSupportHelp.OPERATION_NAME)));

                helpList.add(help);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sqHelp.closeDatabase();
        }

        return helpList;
    }


    public boolean deleteAllHelpSupport(){
        int affRaw = 0;

        try{

            SQLiteDatabase db = sqHelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                affRaw = db.delete(ConnSQLiteContract.PortalGetMziziAppSupportHelp.PORTALGETMZIZIAPPSUPPORTHELP_TABLE,null,null);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqHelp.closeDatabase();
        }

        if(affRaw>0){
            return true;
        }

        return false;
    }



}
