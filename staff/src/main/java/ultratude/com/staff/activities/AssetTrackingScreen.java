package ultratude.com.staff.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ultratude.com.staff.R;
import ultratude.com.staff.barcodescanner.CustomViewFinderScannerActivity;
import ultratude.com.staff.webservice.DataAccessObjects.AssetItemDAO;
import ultratude.com.staff.webservice.DataAccessObjects.AssetRegisterDAO;
import ultratude.com.staff.webservice.DataAccessObjects.LatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.ResponseModels.AssetItem;
import ultratude.com.staff.webservice.ResponseModels.AssetRegisterResponse;
import ultratude.com.staff.webservice.ResponseModels.LatLong;

public class AssetTrackingScreen extends AppCompatActivity implements  com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks ,ToolTipsManager.TipListener{

    //LOCAITON SERVICES
    private GoogleApiClient googleApiClient;
    private LocationServices locationServices;
    private LocationRequest mLocationRequest;
    private static final int REQUEST_LOCATION = 3;
    private Location mLastLocation;

     AlertDialog.Builder builder;
     AlertDialog alert;

    private static final int RC_BARCODE_CAPTURE=9001;

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        //send a location request
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            /// mLastLocation =  LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            //should be currentLocation;
            //locationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest,this);
            locationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,mLocationRequest,this);



        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if(mLastLocation != null){
            LatLong latlong = new LatLong();
            latlong.setLatitude(mLastLocation.getLatitude());
            latlong.setLongitude(mLastLocation.getLongitude());

            //  senddata.setText(latlong.getLatitude() + " : " + latlong.getLongitude());


            //setTitle(latlong.getLatitude() + " : " + latlong.getLongitude());
            //setTitle("Asset Tracking: GPS Active...");

            //AppCompatTextView (id 0) - title
            //AppCompatTextView (id 2) - subtitle
            //https://stackoverflow.com/questions/24344007/get-actionbar-textview-title-in-android/24344024
            Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
            TextView textView = (TextView) toolbar.getChildAt(0);
            textView.setText("Asset Tracking: GPS Active...");
            textView.clearAnimation();
            Animation anim = AnimationUtils.loadAnimation(AssetTrackingScreen.this.getApplicationContext(), R.anim.anim_blink);
            textView.startAnimation(anim);

            //TextView textView = (TextView) toolbar.getChildAt(2);


//


            new LatLongDAO(this).saveLatLong(latlong);
        }
    }

    @Override
    protected void onStart() {
        try {
            googleApiClient.connect();
            // Toast.makeText(DailyTransport.this, String.valueOf("isConnected" +googleApiClient.isConnected() + " " + "isConnecting"+ googleApiClient.isConnecting()), Toast.LENGTH_LONG).show();

        } catch (RuntimeException e) {
            e.printStackTrace();
            // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        super.onStart();
    }

    @Override
    public void onBackPressed() {

        try{
            if(googleApiClient.isConnecting() || googleApiClient.isConnected()){
                Toast.makeText(this, "GPS tracking closing...", Toast.LENGTH_SHORT).show();

                googleApiClient.disconnect();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AssetTrackingScreen.this, "GPS closing complete.", Toast.LENGTH_SHORT).show();
                    }
                }, 1000);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_tracking_layout);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScan();
            }
        });

        ToolTipsManager toolTipsManager = new ToolTipsManager(this);
        //Tooltip
        //https://github.com/tomergoldst/tooltips
        ToolTip.Builder builder = new ToolTip.Builder(this, floatingActionButton, (CoordinatorLayout)findViewById(R.id.asset_tracking_layout), "Tap to scan an asset item", ToolTip.POSITION_LEFT_TO);
       // builder.setAlign(ToolTip.ALIGN_LEFT);
        builder.setBackgroundColor(getResources().getColor(R.color.new_attendance_register));
        builder.setGravity(ToolTip.GRAVITY_RIGHT);
        builder.setTextAppearance(R.style.TooltipTextAppearance); // from `styles.xml`
       // builder.setTypeface(mCustomFontTypeface);
        toolTipsManager.show(builder.build());

        populateCustAdapWithData(null);

        //FindLocation
//LOCATION SERVICES
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();

    }

    @Override
    public void onTipDismissed(View view, int anchorViewId, boolean byUser) {
//        Log.d(TAG, "tip near anchor view " + anchorViewId + " dismissed");
//
//        if (anchorViewId == R.id.text_view) {
//            // Do something when a tip near view with id "R.id.text_view" has been dismissed
//        }
    }


    private void startScan() {

        startActivityForResult(new Intent(AssetTrackingScreen.this, CustomViewFinderScannerActivity.class), RC_BARCODE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 2
        if(requestCode == RC_BARCODE_CAPTURE)
        {
            if(data != null) {
                String message = data.getStringExtra("BARCODE");
                LatLong latLong = new LatLongDAO(AssetTrackingScreen.this).getLatLong();
                onBarcodeCapture(message, latLong);

            }
        }
    }

    private void onBarcodeCapture(final String barcode, final LatLong latLong){

        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.asset_register_item_dialog,null,false);
        final TextView txt_barcode_number = view.findViewById(R.id.txt_barcode_number);
        txt_barcode_number.setText(barcode);

        //final Spinner sp_asset_type = view.findViewById(R.id.sp_asset_type);
        final TextView txt_assset_name = view.findViewById(R.id.txt_assset_name);
        final TextView txt_assset_serialnumber = view.findViewById(R.id.txt_assset_serialnumber);
        final TextView txt_assset_description = view.findViewById(R.id.txt_assset_description);
        final TextView txt_asset_type = view.findViewById(R.id.txt_asset_type);

        final String[] assetType = {""};

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPostExecute(Object o) {
                AssetRegisterResponse assetRegisterResponse  = (AssetRegisterResponse)o;

                txt_assset_name.setText(assetRegisterResponse.getAssetName());
                txt_assset_serialnumber.setText(assetRegisterResponse.getAssetSerialNumber());
                txt_assset_description.setText(assetRegisterResponse.getAssetDescription());
                txt_asset_type.setText(assetRegisterResponse.getAssetTypeName());

                assetType[0] = assetRegisterResponse.getAssetTypeID();

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                return new AssetRegisterDAO(AssetTrackingScreen.this).getAssetRegisterItemWithBarcode(barcode);
            }
        };
        asyncTask.execute();

        final EditText multitxt_comment = view.findViewById(R.id.multitxt_comment);
        final Button btn_save = view.findViewById(R.id.btn_save);
        final Button btn_cancel = view.findViewById(R.id.btn_cancel);
        builder.setView(view);

        builder.setCancelable(true);
        alert = builder.create();
        alert.getWindow().setGravity(Gravity.BOTTOM);
        //alert.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        alert.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String barcodeNumber = barcode;
                //String assetType = sp_asset_type.getSelectedItem().toString();
                String comment = multitxt_comment.getText().toString().trim();

//                if(assetType.equals("Select Asset Type")){
//                    Toast.makeText(AssetTrackingScreen.this, "Select Asset Type", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                alert.dismiss();

                //TODO: ADD TO DATABASE
                List<AssetItem> saveList = new ArrayList<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM YYYY HH:mm:ss");
                AssetItem assetItem = new AssetItem(
                        barcodeNumber,
                        assetType[0],
                        String.valueOf(latLong.getLatitude()),
                        String.valueOf(latLong.getLongitude()),
                        new StaffDao(AssetTrackingScreen.this).getUserThatSignedUp().getStaff_ID(),
                        simpleDateFormat.format(Calendar.getInstance().getTime()),
                        comment
                );
                saveList.add(assetItem);

                new AssetItemDAO(AssetTrackingScreen.this).saveAssetItemDAO(saveList);


               // populateCustAdapWithData(null);
                startScan();
            }
        });
        //builder.show();//throws an error,

    }

    public void populateCustAdapWithData(Integer position){

        try {

            //Since this will take alot of time to change i will leave it like this for now
            Cursor cursor = new AssetItemDAO(this).getAllAssetItemsCursor();
            if(cursor.getCount()>0){

                AssetTrackingAdapter adapter = new AssetTrackingAdapter(this, cursor);
                adapter.changeCursor(cursor);
                adapter.notifyDataSetChanged();
                ((ListView)findViewById(R.id.listview)).setAdapter(adapter);
                ((ListView)findViewById(R.id.listview)).setVisibility(View.VISIBLE);
                ((TextView)findViewById(R.id.scanned_label)).setVisibility(View.GONE);
                if(position == null){
                    ((ListView)findViewById(R.id.listview)).setSelection(cursor.getCount());
                }else
                    ((ListView)findViewById(R.id.listview)).setSelection(position);
            }else{
                ((TextView)findViewById(R.id.scanned_label)).setVisibility(View.VISIBLE);
            }


            // cursor.moveToLast();
            // board.setText(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraDataDef._ID)));

        }catch(Exception e){
            e.printStackTrace();
            // board.setText(e.getMessage().toString());
        }


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class AssetTrackingAdapter extends CursorAdapter {

        public AssetTrackingAdapter(Context context, Cursor cursor){
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.asset_tracking_item_layout,parent,false);
        }

        @Override
        public void bindView(View view, final Context context, Cursor dataCursor) {

           final AssetItem item = new AssetItem(
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.BARCODE_NUMBER)),
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.ASSET_TYPE)),
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.LATITUDE)),
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.LONGITUDE)),
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.STAFFID)),
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.SCANDATETIME)),
                    dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking.COMMENT))
            );
           item.set_ID(dataCursor.getString(dataCursor.getColumnIndex(ConnSQLiteContract.PortalAssetTracking._ID)));

            TextView txtBarcodeNumber = view.findViewById(R.id.txt_asset_barcode_number);
            txtBarcodeNumber.setText(item.getBarCodeNumber());
            TextView txtAssetType = view.findViewById(R.id.txt_asset_type);
            txtAssetType.setText("Asset Type: " + item.getAssetType());
            TextView txtComment = view.findViewById(R.id.txt_comment);
            txtComment.setText(item.getComment());

            LinearLayout asset_tracking_item = view.findViewById(R.id.asset_tracking_item);
            asset_tracking_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    builder = new AlertDialog.Builder(AssetTrackingScreen.this);
                    LayoutInflater inflater = getLayoutInflater();
                    final View view = inflater.inflate(R.layout.asset_item_scan_dialog,null,false);
                    final TextView txt_barcode_number = view.findViewById(R.id.txt_barcode_number);
                    txt_barcode_number.setText(item.getBarCodeNumber());
                    final Spinner sp_asset_type = view.findViewById(R.id.sp_asset_type);
                    String[] values = getResources().getStringArray(R.array.asset_tracking_scan);
                    for(int i=0; i<values.length; i++){
                        if(values[i].equalsIgnoreCase(item.getAssetType())){
                            sp_asset_type.setSelection(i);
                            break;
                        }
                    }

                    final EditText multitxt_comment = view.findViewById(R.id.multitxt_comment);
                    multitxt_comment.setText(item.getComment());
                    final Button btn_save = view.findViewById(R.id.btn_save);
                    btn_save.setText("Update");
                    final Button btn_cancel = view.findViewById(R.id.btn_cancel);
                    builder.setView(view);

                    builder.setCancelable(true);
                    alert = builder.create();
                    alert.getWindow().setGravity(Gravity.BOTTOM);
                    //alert.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                    alert.show();

                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });

                    btn_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            String assetType = sp_asset_type.getSelectedItem().toString();
                            String comment = multitxt_comment.getText().toString().trim();

                            if(assetType.equals("Select Asset Type")){
                                Toast.makeText(AssetTrackingScreen.this, "Select Asset Type", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            alert.dismiss();

                            //TODO: UPDATE TO DATABASE
                            List<AssetItem> saveList = new ArrayList<>();
                            saveList.clear();
                            AssetItem assetItem = new AssetItem(
                                    item.getBarCodeNumber(),
                                    assetType,//can be updated
                                    item.getLatitude(),
                                    item.getLongitude(),
                                    item.getStaffID(),
                                    item.getScanDateTime(),
                                    comment//can be updated
                            );
                            assetItem.set_ID(item.get_ID());
                            saveList.add(assetItem);

                            new AssetItemDAO(AssetTrackingScreen.this).updateAssetItemDAO(saveList);

                            populateCustAdapWithData(getCursor().getPosition());

                        }
                    });
                    //builder.show();throws an error

                }
            });




        }
    }




}
