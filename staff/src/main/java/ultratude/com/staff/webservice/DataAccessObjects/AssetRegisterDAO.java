package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.AssetRegisterResponse;

public class AssetRegisterDAO {


    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public AssetRegisterDAO(Context context){
        this.mContext = context;
        sqhelp = DatabaseConnectionOpenHelper.getInstance(context);
    }

    public void saveAssetRegister(List<AssetRegisterResponse> assetRegisterResponseList){
        try{

            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                deleteAllAssetRegisterArrayList();

                for(AssetRegisterResponse register : assetRegisterResponseList){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ID, register.getID());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETNAME, register.getAssetName());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETCODE, register.getAssetCode());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETSERIALNUMBER, register.getAssetSerialNumber());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETDESCRIPTION, register.getAssetDescription());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETBARCODE, register.getAssetCode());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETTYPE, register.getAssetTypeName());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.ASSETTYPEID, register.getAssetTypeID());
                    contentValues.put(ConnSQLiteContract.PortalAssetRegister.OPENMARKETPRICE, register.getOpenMarketValue());

                    db.insertOrThrow(ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE, null, contentValues);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }


    public ArrayList<AssetRegisterResponse> getAssetRegisterArrayList(){
        ArrayList<AssetRegisterResponse> returnList = new ArrayList<>();
        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            if(db.isOpen()){
                Cursor cursor = db.query(ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE,null,null,null,null,null,null);
                while(cursor.moveToNext()){
                    AssetRegisterResponse register = new AssetRegisterResponse(
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ID)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETNAME)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETCODE)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETSERIALNUMBER)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETDESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETBARCODE)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETTYPE)),

                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETTYPEID)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.OPENMARKETPRICE))
                            );
                    returnList.add(register);
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }finally {
            sqhelp.closeDatabase();
        }

        return returnList;
    }


    public AssetRegisterResponse getAssetRegisterItemWithBarcode(String barcode){
        AssetRegisterResponse register = null;
        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();

            if(db.isOpen()){
                Cursor cursor = db.query(
                        true,
                        ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE,
                        null,
                        ConnSQLiteContract.PortalAssetRegister.ASSETBARCODE + " = ? ",
                        new String[]{barcode},
                        null,
                        null,
                        null,
                        null
                );

                while(cursor.moveToFirst()){
                     register = new AssetRegisterResponse(
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ID)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETNAME)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETCODE)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETSERIALNUMBER)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETDESCRIPTION)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETBARCODE)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETTYPE)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.ASSETTYPEID)),
                            cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.PortalAssetRegister.OPENMARKETPRICE))
                    );
                }

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }

        return register;
    }



    public void deleteAllAssetRegisterArrayList(){
        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            if(db.isOpen()){
                db.delete(ConnSQLiteContract.PortalAssetRegister.PORTAL_ASSETREGISTER_TABLE,null,null);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }

}
