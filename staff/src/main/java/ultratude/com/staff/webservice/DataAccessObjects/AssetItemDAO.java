package ultratude.com.staff.webservice.DataAccessObjects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.Database.DatabaseConnectionOpenHelper;
import ultratude.com.staff.webservice.ResponseModels.AssetItem;

public class AssetItemDAO {

    private Context mContext;
    private DatabaseConnectionOpenHelper sqhelp;

    public AssetItemDAO(Context mContext){
        this.mContext = mContext;
        this.sqhelp = DatabaseConnectionOpenHelper.getInstance(mContext);
    }



    public long saveAssetItemDAO(List<AssetItem> assetItemList){
        long id = 0;
        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(assetItemList.size() > 0){
                if(!assetItemList.get(0).getLatitude().isEmpty() &&
                        !assetItemList.get(0).getLongitude().isEmpty()){
                   for(int i=0; i<assetItemList.size(); i++){
                       ContentValues cv = new ContentValues();
                       cv.put(ConnSQLiteContract.PortalAssetTracking.BARCODE_NUMBER, assetItemList.get(i).getBarCodeNumber());
                       cv.put(ConnSQLiteContract.PortalAssetTracking.ASSET_TYPE, assetItemList.get(i).getAssetType());
                       cv.put(ConnSQLiteContract.PortalAssetTracking.LATITUDE, assetItemList.get(i).getLatitude());
                       cv.put(ConnSQLiteContract.PortalAssetTracking.LONGITUDE, assetItemList.get(i).getLongitude());
                       cv.put(ConnSQLiteContract.PortalAssetTracking.STAFFID, assetItemList.get(i).getStaffID());
                       cv.put(ConnSQLiteContract.PortalAssetTracking.SCANDATETIME, assetItemList.get(i).getScanDateTime());
                       cv.put(ConnSQLiteContract.PortalAssetTracking.COMMENT, assetItemList.get(i).getComment());
                       if(db.insert(ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE, null ,cv) > 0){
                           id++;
                       }

                   }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
        return id;//checking if all the records were saved
    }

    public void updateAssetItemDAO(List<AssetItem> assetItemList){
        long id = 0;
        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(assetItemList.size() > 0){
                if(!assetItemList.get(0).getLatitude().isEmpty() &&
                        !assetItemList.get(0).getLongitude().isEmpty()){
                    for(int i=0; i<assetItemList.size(); i++){
                        ContentValues cv = new ContentValues();
                        cv.put(ConnSQLiteContract.PortalAssetTracking.BARCODE_NUMBER, assetItemList.get(i).getBarCodeNumber());
                        cv.put(ConnSQLiteContract.PortalAssetTracking.ASSET_TYPE, assetItemList.get(i).getAssetType());
                        cv.put(ConnSQLiteContract.PortalAssetTracking.LATITUDE, assetItemList.get(i).getLatitude());
                        cv.put(ConnSQLiteContract.PortalAssetTracking.LONGITUDE, assetItemList.get(i).getLongitude());
                        cv.put(ConnSQLiteContract.PortalAssetTracking.STAFFID, assetItemList.get(i).getStaffID());
                        cv.put(ConnSQLiteContract.PortalAssetTracking.SCANDATETIME, assetItemList.get(i).getScanDateTime());
                        cv.put(ConnSQLiteContract.PortalAssetTracking.COMMENT, assetItemList.get(i).getComment());
                        db.update(
                                ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE
                                ,cv,
                                ConnSQLiteContract.PortalAssetTracking._ID + " = ? " ,
                                new String[]{assetItemList.get(i).get_ID()}) ;


                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
       // return id;//checking if all the records were saved
    }

    public Cursor getAllAssetItemsCursor(){
        Cursor cursor = null;
        try{
            SQLiteDatabase db = sqhelp.getReadableDatabaseConnection();
            cursor = db.query(ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE,null,null,null,null,null,null);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return cursor;
    }

    public void deleteOneAssetItem(int[] assetItemsIDs){
        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();
            if(db.isOpen()){
                for(int i : assetItemsIDs){
                    String query = "DELETE FROM " + ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE + " WHERE " + ConnSQLiteContract.PortalAssetTracking._ID + " = " + i + ";";
                    db.execSQL(query);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            sqhelp.closeDatabase();
        }
    }

    public List<Object> getAllAssetItems(){
        List<Object> returnList = new ArrayList<>();

        List<AssetItem> assetItemList = new ArrayList<>();

        Cursor dataCursor = getAllAssetItemsCursor();
        if(dataCursor != null){
            int[] ultraDataIDs = new int[dataCursor.getCount()];

            try{

                int count = 0;
                while(dataCursor.moveToNext()){
                    int id = dataCursor.getInt(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking._ID));
                    ultraDataIDs[count] = id;
                    count++;

                    AssetItem item = new AssetItem(
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.BARCODE_NUMBER)),
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.ASSET_TYPE)),
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.LATITUDE)),
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.LONGITUDE)),
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.STAFFID)),
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.SCANDATETIME)),
                            dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.COMMENT))
                    );
                    item.set_ID(dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking._ID)));
                    assetItemList.add(item);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }

            returnList.add(assetItemList);
            returnList.add(ultraDataIDs);
        }

        return returnList;
    }

    public void deleteAllStaff(){
        try{
            SQLiteDatabase db = sqhelp.getWriteableDatabaseConnection();

            if(db.isOpen()){
                int row = db.delete(ConnSQLiteContract.PortalAssetTracking.PORTALASSETTRACKING_TABLE, null,null);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            sqhelp.closeDatabase();
        }
    }


}
