package ultratude.com.staff.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.adapters.UltraDataAdapter;
import ultratude.com.staff.R;

import ultratude.com.staff.barcodescanner.CustomViewFinderScannerActivity;
import ultratude.com.staff.spinnermodel.VehicleSpinner;
import ultratude.com.staff.utils.UtilityFunctions;
import ultratude.com.staff.webservice.DataAccessObjects.TransportBusTripsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportSessionsDAO;
import ultratude.com.staff.webservice.DataAccessObjects.TransportStudentsDao;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.Database.ConnSQLiteContract;
import ultratude.com.staff.webservice.ResponseModels.LatLong;
import ultratude.com.staff.webservice.DataAccessObjects.LatLongDAO;
import ultratude.com.staff.webservice.ResponseModels.TransportStudents;
import ultratude.com.staff.webservice.DataAccessObjects.UltraDataDao;
import ultratude.com.staff.webservice.RequestModels.TransportBusTrips;
import ultratude.com.staff.webservice.RequestModels.TransportSessions;



/**
 * Created by Admin on 3/25/2018.
 */

public class DailyTransport extends AppCompatActivity implements View.OnClickListener ,  com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    protected static final String TAG = "TransportLocationService";

    protected GoogleApiClient gac;

    public static final int RC_BARCODE_CAPTURE=9001;
    private static final String LOG_TAG = "ReadBarcodActivity";


    UltraDataAdapter adapter;
    int countNoOfScans;
    static boolean dataIsNotNull = false;

    //private static boolean continueScanning = false;

    //BARCODE
    public static final String BARCODE_KEY = "BARCODE";


    private Bundle savedInstanceState;

    //this should be removed

   private ListView listview;
   private TextView board;
   private TextView alight;
    private LinearLayout btn_board_ID;
    private LinearLayout btn_alight_ID;

   private View view;


  public TextView displayHere;




//    private String board_bus_Session = "";
//    private String board_bus_trip = "";
//    private String board_bus_Activity = "";
//
//    private String alight_bus_Session = "";
//    private String alight_bus_trip = "";
//    private String alight_bus_Activity = "";

    SQLiteDatabase sqdb;



    //LOCAITON SERVICES
    private GoogleApiClient googleApiClient;
    private LocationServices locationServices;
    private LocationRequest mLocationRequest;
    private static final int REQUEST_LOCATION = 2;
    private Location mLastLocation;

    private boolean isLogin = false;

    List<TransportStudents> transportStudentsList;

    private Spinner sp_vehicle_plate_number_ID;
    private ImageView image_vehicle_plate_number_ID;
    private TextView lable_message_ID;
    private VehicleSpinner vehiclePlateValueSelected;

    private File filePath = new File(Environment.getExternalStorageDirectory() + "/mzizi_daily_transport_scans.xls");



    public void buttonCreateExcel(Cursor datacursor){
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Transport Scans");


        //style.setFillPattern(HSSFCellStyle.BIG_SPOTS);

//        HSSFRow hssfRow = hssfSheet.createRow(0);
//        HSSFCell hssfCell = hssfRow.createCell(0);


//        HSSFRow hssfRow = hssfSheet.createRow(0);
//        HSSFCell hssfCell = hssfRow.createCell(0);

        int count = 0;
        while(datacursor.moveToNext()){

            if(count == 0){
                HSSFRow rowzero = hssfSheet.createRow(count);
                rowzero.createCell(0).setCellValue("BARCODE");
                rowzero.createCell(1).setCellValue("LATITUDE");
                rowzero.createCell(2).setCellValue("LONGITUDE");
                rowzero.createCell(3).setCellValue("BUS ACTIVITY");
                rowzero.createCell(4).setCellValue("SESSION");
                rowzero.createCell(5).setCellValue("VEHICLE PLATE");
                rowzero.createCell(6).setCellValue("BUS TRIPS");
                rowzero.createCell(7).setCellValue("DATE SCANNED");
                rowzero.createCell(8).setCellValue("SENT STATUS");
            }

            String session = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.SESSION));
            String busTrip = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.BUSTRIP));

            for(TransportSessions  tSession : transportSessionsArrayList){
                if(tSession.getId() ==  Integer.valueOf(session)){
                    session = (tSession.getName());
                    break;
                }
            }

            for(TransportBusTrips  tSession : transportBusTrips){
                if(tSession.getId() ==  Integer.valueOf(busTrip)){
                   busTrip = (tSession.getName());
                    break;
                }
            }

            String barcodeValue = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STUDENTID_BARCODE));
            Double latitude = datacursor.getDouble(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.LATITUDE));
            Double longitude = datacursor.getDouble(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.LONGITUDE));
            String schoolbus = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.BUS_ACTIVITY));
            String vehiclePlate = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.VEHICLE_PLATE));
            String dateScanned = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.DATE_SCANNED));

            String status = datacursor.getString(datacursor.getColumnIndex(ConnSQLiteContract.UltraDataDef.STATUS_SENT)).equals("true") ? "SENT" : "NOT SENT";

            HSSFRow rowno = hssfSheet.createRow(count + 1);
            rowno.createCell(0).setCellValue(barcodeValue);
            rowno.createCell(1).setCellValue(latitude);
            rowno.createCell(2).setCellValue(longitude);
            rowno.createCell(3).setCellValue(schoolbus);
            rowno.createCell(4).setCellValue(session);
            rowno.createCell(5).setCellValue(vehiclePlate);
            rowno.createCell(6).setCellValue(busTrip);
            rowno.createCell(7).setCellValue(dateScanned);
            rowno.createCell(8).setCellValue(status);

            count++;
        }

        try {
            if (!filePath.exists()){
                filePath.createNewFile();
            }

            FileOutputStream fileOutputStream= new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);

            if (fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }

            //OPEN THE FILE
            //https://medium.com/@ashumaurya_9286/how-to-open-a-excel-file-in-android-programmatically-8295b932b9ef
            Intent intent=new Intent(Intent.ACTION_VIEW);
            Uri apkURI = FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName(),filePath);

            MimeTypeMap myMime = MimeTypeMap.getSingleton();
            String mimeType=myMime.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(apkURI.toString()));//It will return the mimetype

            intent.setDataAndType(apkURI, mimeType);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ArrayList<TransportBusTrips> transportBusTrips = new TransportBusTripsDAO(DailyTransport.this).getTransportBusTripsArrayList();

    ArrayList<TransportSessions> transportSessionsArrayList = new TransportSessionsDAO(DailyTransport.this).getTransportSessionArrayList();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.daily_transport_layout);

        UtilityFunctions.activateQuickActions(this,  0, HomeScreen.CurrentScreenKey);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        ((ImageView) findViewById(R.id.generate_excel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask asyncTask = new AsyncTask() {
                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if(((Cursor) o).getCount() > 0)
                            buttonCreateExcel((Cursor) o);
                        else
                            Toast.makeText(DailyTransport.this, "Can't generate excel file with no scanned records!", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        return new UltraDataDao(DailyTransport.this).getAllUltradataCursor();
                    }
                };
                asyncTask.execute();

            }
        });




        board = findViewById(R.id.board_bus);
        alight = findViewById(R.id.alight_bus);

        Paper.init(this);
        // transportSessions = ((PortalTransportSessionBusTripsResponse) Paper.book().read(PAPER_TRANSPORT_SESSIONS_BUSTRIPS));
        sp_vehicle_plate_number_ID = findViewById(R.id.sp_vehicle_plate_number_ID);
        image_vehicle_plate_number_ID = findViewById(R.id.image_vehicle_plate_number_ID);
        lable_message_ID = findViewById(R.id.lable_message_ID);

        image_vehicle_plate_number_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_vehicle_plate_number_ID.performClick();
            }
        });
        ArrayList<VehicleSpinner> vehicleSpinnerArrayList = new VehicleDAO(this).getVehicleIDWithNumberPlate();
        ArrayAdapter<VehicleSpinner> numberPlatesAdapter = new ArrayAdapter<VehicleSpinner>(this, android.R.layout.simple_spinner_dropdown_item, vehicleSpinnerArrayList);
        sp_vehicle_plate_number_ID.setAdapter(numberPlatesAdapter);

       VehicleSpinner selectedVehicle =  Paper.book().read("Vehicle");

        int countVehicleId = 0;
        if(selectedVehicle != null){

            for(int count = 0; count< vehicleSpinnerArrayList.size(); count++){

                if(vehicleSpinnerArrayList.get(count).getVehicleID() == selectedVehicle.getVehicleID()){
                    countVehicleId = count;
                    break;
                }
            }


           // Log.d(this.getPackageName().toUpperCase(), selectedVehicle.toString());
            sp_vehicle_plate_number_ID.setSelection(countVehicleId);
        }

        //select the previous
        sp_vehicle_plate_number_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                lable_message_ID.setVisibility(View.VISIBLE);
                switch(position){
                    case 0:
                        vehiclePlateValueSelected = null;
                        break;
                    default:
                        vehiclePlateValueSelected =  (VehicleSpinner) parent.getSelectedItem();
                        //  Toast.makeText(ManageFleetFuelScreen.this, numberPlateValueSelected, Toast.LENGTH_SHORT).show();
                        lable_message_ID.setVisibility(View.INVISIBLE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehiclePlateValueSelected = null;
                lable_message_ID.setVisibility(View.INVISIBLE);

            }
        });



        isLogin =   getIntent().getExtras().getBoolean("isLog_In");


        setTitle("Searching GPS Signal...");

        transportStudentsList = new TransportStudentsDao(this).getAllStudents();


        //FindLocation
//LOCATION SERVICES
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();


        countNoOfScans = 0;

            //This should be removed from here, to but it hasnt finished its work
      // sqdb = new DatabaseConnectionOpenHelper(this).getSQLiteDb();


        displayHere = (TextView) findViewById(R.id.display_here);

        listview = (ListView) findViewById(R.id.location_barcode_scanned);



        Cursor cursor = new UltraDataDao(this).getAllUltradataDisplayCursor();
        if(cursor.moveToNext()){

            displayHere.setVisibility(View.INVISIBLE);
            populateCustAdapWithData();

        }else{
            displayHere.setVisibility(View.VISIBLE);
        }


        btn_board_ID =  findViewById(R.id.btn_board_ID);
        btn_board_ID.setOnClickListener(this);
        btn_alight_ID =  findViewById(R.id.btn_alight_ID);
        btn_alight_ID.setOnClickListener(this);

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
                       Toast.makeText(DailyTransport.this, "GPS closing complete.", Toast.LENGTH_SHORT).show();
                   }
               }, 1000);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        super.onBackPressed();
    }

    private void addItemsToSQdb(String barcode, LatLong latLong, String busActivity, String busSession, VehicleSpinner vehiclePlateValueSelected,String busTrip){

       // Toast.makeText(this, busSession , Toast.LENGTH_LONG).show();
        new UltraDataDao(this).addUltradata(barcode,latLong, busActivity, busSession,vehiclePlateValueSelected,busTrip);
        //below is the text showing where output will appear, before displaying the custom adapter, render it invisible
        listview.setVisibility(View.VISIBLE);
        displayHere.setVisibility(View.INVISIBLE);
       populateCustAdapWithData();



    }


    public void populateCustAdapWithData(){

        try {



                //Since this will take alot of time to change i will leave it like this for now
             Cursor cursor = new UltraDataDao(this).getAllUltradataCursor();
                if(cursor.getCount()>0){

                    adapter = new UltraDataAdapter(this, cursor);
                    adapter.changeCursor(cursor);
                    listview.setAdapter(adapter);
                    listview.setVisibility(View.VISIBLE);

                    listview.setSelection(cursor.getCount());

                }


               // cursor.moveToLast();
               // board.setText(cursor.getString(cursor.getColumnIndex(ConnSQLiteContract.UltraDataDef._ID)));

        }catch(Exception e){
           e.printStackTrace();
           // board.setText(e.getMessage().toString());
        }


    }

    @Override
    public void onClick(View view){

            //this solved the issue of DailyTransport.this.onClick();
        myOnClick(view, false,null,0,0);


    }

    //TODO: HAVE THE DUPLICATE DIALOGS HERE
    private void scanConfirmationDialog(){

    }





        //this method will be called by onclick and onActivityResult methods
    private void myOnClick(View view, boolean continueScanning, String busActivity, int session, final int busTrip){

        Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
        final TextView titleTextView = (TextView) findViewById(R.id.back_label);

            //BOARDIING
        if(view.getId() == R.id.btn_board_ID) {
            this.view = view;


            if (titleTextView.getText().toString().equals("Daily Transport: GPS Active...")) {

               // Toast.makeText(this, "searching", Toast.LENGTH_SHORT).show();

                if (vehiclePlateValueSelected != null) {

                    if (continueScanning) {
                        startScan(
                                busActivity,
                                session,
                                busTrip
                                );
                    } else {

                       // Toast.makeText(this, "Should ask to scan: " + String.valueOf(true), Toast.LENGTH_SHORT).show();

                        final AlertDialog.Builder build = new AlertDialog.Builder(DailyTransport.this);
                        build.setIcon(R.drawable.reminder_icon);
                        build.setTitle("Boarding?");
                        build.setCancelable(false);
                        build.setMessage("Are you sure you want to proceed with scanning the action \"Boarding\"?");
                        //TODO: ADD LAYOUT FOR SESSIONS
                        final View sessionDialogView = getLayoutInflater().inflate(R.layout.daily_transport_sessions_dialog, null,false);
                        final Spinner sp_sessions = sessionDialogView.findViewById(R.id.sp_session_ID);


                        ArrayAdapter<TransportSessions> transportSessionsAdapter = new ArrayAdapter<>(DailyTransport.this, android.R.layout.simple_spinner_dropdown_item, transportSessionsArrayList);
                        sp_sessions.setAdapter(transportSessionsAdapter);

                        final Spinner sp_busTrips = sessionDialogView.findViewById(R.id.sp_bus_trips_ID);
                        ArrayAdapter<TransportBusTrips> transportBusTripsArrayAdapter = new ArrayAdapter<>(DailyTransport.this, android.R.layout.simple_spinner_dropdown_item, transportBusTrips);
                        sp_busTrips.setAdapter(transportBusTripsArrayAdapter);

                        final Button btn_save = sessionDialogView.findViewById(R.id.btn_save);
                        final Button btn_cancel = sessionDialogView.findViewById(R.id.btn_cancel);


                        build.setView(sessionDialogView);


                        build.setCancelable(false);
                        final AlertDialog alert = build.create();
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

                                if(sp_sessions.getSelectedItemId() == 0) {
                                    ((TextView) sessionDialogView.findViewById(R.id.message_confirmation)).setText("Select Session");
                                    return;
                                }

                                if(sp_busTrips.getSelectedItemId() == 0) {
                                    ((TextView) sessionDialogView.findViewById(R.id.message_confirmation)).setText("Select Trip");
                                    return;
                                }

                               alert.dismiss();
                                int session = Integer.parseInt(String.valueOf(sp_sessions.getSelectedItemId()));
                                int busTrips = Integer.parseInt(String.valueOf(sp_busTrips.getSelectedItemId()));
                               startScan(board.getText().toString(),session,busTrips);
                            }
                        });


                    }

                } else {
                    lable_message_ID.setVisibility(View.VISIBLE);
                }


            } else {

               // Toast.makeText(this, "Should ask to scan: " + String.valueOf(true), Toast.LENGTH_SHORT).show();


                AlertDialog.Builder build = new AlertDialog.Builder(DailyTransport.this);
                build.setIcon(R.drawable.reminder_icon);
                build.setTitle("Make sure your GPS is on");
                build.setMessage("Please wait. It might take some time to find your current location.");
                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        // Toast.makeText(DailyTransport.this, String.valueOf("isConnected" +googleApiClient.isConnected() + " " + "isConnecting"+ googleApiClient.isConnecting()), Toast.LENGTH_LONG).show();


                    }
                });
                build.show();

            }

            //ALIGHTING
        }else if(view.getId() == R.id.btn_alight_ID) {

            this.view = view;

            if (titleTextView.getText().toString().equals("Daily Transport: GPS Active...")) {

                if(vehiclePlateValueSelected != null) {

                    if (continueScanning) {

                        //if you need to change to the other scanning thirdparty library for scanning you can place the code here
                        startScan(
                                busActivity,
                                session,
                                busTrip
                        );
                    } else {


                        AlertDialog.Builder build = new AlertDialog.Builder(DailyTransport.this);
                        build.setIcon(R.drawable.reminder_icon);
                        build.setTitle("Alighting?");
                        build.setMessage("Are you sure you want to proceed with scanning the action \"Alighting\"?");
                        //TODO: ADD LAYOUT FOR SESSIONS
                        final View sessionDialogView = getLayoutInflater().inflate(R.layout.daily_transport_sessions_dialog, null,false);
                        final Spinner sp_sessions = sessionDialogView.findViewById(R.id.sp_session_ID);

                        ArrayAdapter<TransportSessions> transportSessionsAdapter = new ArrayAdapter<>(DailyTransport.this, android.R.layout.simple_spinner_dropdown_item, transportSessionsArrayList);
                        sp_sessions.setAdapter(transportSessionsAdapter);

                        final Spinner sp_busTrips = sessionDialogView.findViewById(R.id.sp_bus_trips_ID);

                        ArrayAdapter<TransportBusTrips> transportBusTripsArrayAdapter = new ArrayAdapter<>(DailyTransport.this, android.R.layout.simple_spinner_dropdown_item, transportBusTrips);
                        sp_busTrips.setAdapter(transportBusTripsArrayAdapter);

                        final Button btn_save = sessionDialogView.findViewById(R.id.btn_save);
                        final Button btn_cancel = sessionDialogView.findViewById(R.id.btn_cancel);

                        build.setView(sessionDialogView);
                        build.setCancelable(false);
                        final AlertDialog alert = build.create();
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
                                if(sp_sessions.getSelectedItemId() == 0) {
                                    ((TextView) sessionDialogView.findViewById(R.id.message_confirmation)).setText("Select Session");
                                    return;
                                }

                                if(sp_busTrips.getSelectedItemId() == 0) {
                                    ((TextView) sessionDialogView.findViewById(R.id.message_confirmation)).setText("Select Trip");
                                    return;
                                }

                                alert.cancel();
                                int session = Integer.parseInt(String.valueOf(sp_sessions.getSelectedItemId()));
                                int busTrips = Integer.parseInt(String.valueOf(sp_busTrips.getSelectedItemId()));
                                startScan(alight.getText().toString(),session,busTrips);
                            }
                        });


                    }
                }else{
                    lable_message_ID.setVisibility(View.VISIBLE);
                }
            } else {
                AlertDialog.Builder build = new AlertDialog.Builder(DailyTransport.this);
                build.setIcon(R.drawable.reminder_icon);
                build.setTitle("Make sure your GPS is on");
                build.setMessage("Please wait. It might take some time to find your current location.");
                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                build.show();

            }

        }


    }



    //request permission


        /**
         * Called when an activity you launched exits, giving you the requestCode
         * you started it with, the resultCode it returned, and any additional
         * data from it.  The <var>resultCode</var> will be
         * {@link #RESULT_CANCELED} if the activity explicitly returned that,
         * didn't return any result, or crashed during its operation.
         * <p/>
         * <p>You will receive this call immediately before onResume() when your
         * activity is re-starting.
         * <p/>
         *
         * @param requestCode The integer request code originally supplied to
         *                    startActivityForResult(), allowing you to identify who this
         *                    result came from.
         * @param resultCode  The integer result code returned by the child activity
         *                    through its setResult().
         * @param data        An Intent, which can return result data to the caller
         *                    (various data can be attached to Intent "extras").
         * @see #startActivityForResult
         * @see #createPendingResult
         * @see #setResult(int)
         */


        //this part of continue is having problems with android 9.0 pie

       @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == RC_BARCODE_CAPTURE){
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                if(resultCode == CommonStatusCodes.SUCCESS){
//                    if(data!=null){
//                        dataIsNotNull = true;
//
//                    }else{
//                        builder.setTitle(R.string.barcode_failure);
//
//                    }
//                    builder.setCancelable(false);
//
//                    builder.setPositiveButton("Continue scanning", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//
//                            //this is causing crash
//                          // DailyTransport.this.onClick(view);
//                                //this solved the issue
//                            myOnClick(DailyTransport.this.view, true);
//                        }
//                    });
//                    builder.setNegativeButton("Stop scanning", new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog, int what){
//                            //continueScanning = false;
//
//
//                            dialog.cancel();
//
//
//                        }
//                    }).show();
//                }else{
//                    builder.setMessage((String.format(getString(R.string.barcode_error),
//                            CommonStatusCodes.getStatusCodeString(resultCode))));
//                    builder.setCancelable(false);
//                    builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    }).show();
//
//                }




                if(data != null) {
                    LatLong latLong = new LatLongDAO(DailyTransport.this).getLatLong();
                    String barcode = data.getStringExtra("BARCODE");
                    int session = data.getIntExtra("Bus_Session", 0);
                    String busActivity = data.getStringExtra("Bus_Activity");
                    int busTrips = data.getIntExtra("Bus_Trip", 0);
                    onBarcodeCapture(
                            barcode,
                            latLong,
                            session,
                            busTrips,
                            busActivity);



                }
            }
        }

    //LOCATION SERVICES
    //we test if this conneciton is still on after onActivityResults is called

    @Override
    protected void onDestroy() {

        if (googleApiClient.isConnected()) {
            try {
                googleApiClient.disconnect();
                googleApiClient = null;
            } catch (RuntimeException e) {
                e.printStackTrace();
                // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        super.onDestroy();
    }



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

           // Toolbar toolbar = (Toolbar) findViewById(R.id.action_bar);
           //final TextView titleTextView = (TextView) toolbar.getChildAt(0);
            final TextView titleTextView = findViewById(R.id.back_label);
            titleTextView.setText( "Daily Transport" + ": GPS Active...");
            titleTextView.clearAnimation();
            Animation anim = AnimationUtils.loadAnimation(DailyTransport.this.getApplicationContext(), R.anim.anim_blink);
            titleTextView.startAnimation(anim);

            new LatLongDAO(this).saveLatLong(latlong);
        }
    }




    private void onBarcodeCapture(String barcode, LatLong latLong, final int session, final int busTrip, final String busActivity){


        String studentFullName = "";
        String registrationNo = "";
        String classStream  = "";
        String bus = "";
        String morningPicked = "";
        String eveningPicked = "";
        String routeName = "";
        String termName = "";
        String yearID = "";
        String schoolName = "";
        String fatherPhone = "";
        String motherPhone = "";


        StringBuilder sb = new StringBuilder();


        for(int i = 0; i< transportStudentsList.size(); i++){
            String scannedBarcode = barcode;
            String storedBarcode = transportStudentsList.get(i).getBarcode();



            sb.append(storedBarcode);
            sb.append(",\n");


            if(scannedBarcode.equalsIgnoreCase(storedBarcode)){

                studentFullName = transportStudentsList.get(i).getStudentFullName();
                registrationNo = transportStudentsList.get(i).getRegistrationNumber();
                classStream = transportStudentsList.get(i).getClassStream();
                bus = transportStudentsList.get(i).getBus();
                morningPicked = transportStudentsList.get(i).getMorningPicked();
                eveningPicked = transportStudentsList.get(i).getEveningPicked();
                routeName = transportStudentsList.get(i).getRouteName();
                termName = transportStudentsList.get(i).getTermName();
                yearID = transportStudentsList.get(i).getYearID();
                schoolName = transportStudentsList.get(i).getSchoolName();
                fatherPhone = transportStudentsList.get(i).getFatherPhone();
                motherPhone = transportStudentsList.get(i).getMotherPhone();


              //  setTitle(String.valueOf(storedBarcode + "  " + scannedBarcode));

                break;
            }

        }


       // Toast.makeText(this, String.valueOf(transportStudentsList.size()), Toast.LENGTH_LONG).show();



        if(studentFullName != ""){
        //if(true){

            //Toast.makeText(this, String.valueOf(googleApiClient.isConnected()), Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
//                    LatLong latLong = (LatLong) data.getExtras().get(BarcodeCaptureActivity.LocationObject);
            if(latLong != null && barcode != null){


                //HERE IS WHERE WE SAVE DATA
                //TODO: REMEMBER TO UNDO THIS
                addItemsToSQdb(barcode,latLong, busActivity, String.valueOf(session), vehiclePlateValueSelected,String.valueOf(busTrip));

                countNoOfScans++;
               builder.setTitle(schoolName);

                //ontranport
                //the bus the student is assigned to
                //show if eligible for morning or afternoon tranport

                //not legible to afternoon tranport


//                builder.setMessage(   "Barcode: " + barcode.rawValue +
//                                    "\nReg No  : " + registrationNo +
//                                    "\nClass   : " + classStream  +
//                                    "\nStatus  : " + bus_Activity);

                //STUDENT NOT ON TRANPORT FOR THE CURRENT TERM


                StringBuilder stringBuilder = new StringBuilder();
//                stringBuilder.append("        Route Name      : \n" + routeName);
//                stringBuilder.append("\n------------------------");
//                stringBuilder.append("\nStatus    : " + bus_Activity);
//                stringBuilder.append("\nReg No.   : " + registrationNo);
//                stringBuilder.append("\nClass     : " + classStream);
//                stringBuilder.append("\nBus       : " + bus );
//                stringBuilder.append("\n------------------------");
//
//                if(morningPicked.equals("YES") || eveningPicked.equals("YES")){
//                    stringBuilder.append("\nMorning Picked  : " + morningPicked );
//
//                    stringBuilder.append("\nEvening Picked  : " + eveningPicked);
//
//                }else {
//                    stringBuilder.append("\nThis StudentMR is \"NOT\" on Tranport for the Current Term, " + termName + " " + yearID );
//                }




                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.transport_dialog_after_scan_layout, null, false);
                TextView route_Name, status,busTrips,txt_session, studentName,regNo,class_Stream,_bus, morning_Picked, evening_Picked, not_on_transport_ID;
                RelativeLayout not_on_transport_layout, evening_layout_ID,morning_layout_ID;
                evening_layout_ID = view.findViewById(R.id.evening_layout_ID);
                not_on_transport_layout = view.findViewById(R.id.not_on_transport_layout);
                morning_layout_ID = view.findViewById(R.id.morning_layout_ID);
                final Button btn_father_phone = view.findViewById(R.id.btn_father_phonenumber);
                btn_father_phone.setText("Call: " + fatherPhone);


                final RadioGroup rdGroup = view.findViewById(R.id.radio_group);
                final String finalFatherPhone = fatherPhone;
                btn_father_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(rdGroup.getCheckedRadioButtonId() == R.id.rdbPhoneCall){
                            UtilityFunctions.makeACall(DailyTransport.this, "PhoneCall", finalFatherPhone);
                        }else if(rdGroup.getCheckedRadioButtonId() == R.id.rdbWhatsCall){
                            UtilityFunctions.makeACall(DailyTransport.this, "WhatsAppCall", finalFatherPhone);
                        }

                    }
                });
                btn_father_phone.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        UtilityFunctions.copyToClipBoard(DailyTransport.this, finalFatherPhone);
                        return true;
                    }
                });

                final Button btn_mother_phone = view.findViewById(R.id.btn_mother_phonenumber);
                btn_mother_phone.setText("Call: " + motherPhone);
                final String finalMotherPhone = motherPhone;
                btn_mother_phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rdGroup.getCheckedRadioButtonId() == R.id.rdbPhoneCall){
                            UtilityFunctions.makeACall(DailyTransport.this, "PhoneCall", finalMotherPhone);
                        }else if(rdGroup.getCheckedRadioButtonId() == R.id.rdbWhatsCall){
                            UtilityFunctions.makeACall(DailyTransport.this, "WhatsAppCall",finalMotherPhone );
                        }
                    }
                });

                btn_mother_phone.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        UtilityFunctions.copyToClipBoard(DailyTransport.this, finalMotherPhone);
                        return true;
                    }
                });


                    route_Name = view.findViewById(R.id.route_name_ID);
                    if(!routeName.equals("")){
                        route_Name.setText(routeName);
                    }else{
                        route_Name.setText("No Route Identified");
                        route_Name.setTextColor(getResources().getColor(R.color.no_route));
                    }
                    status = view.findViewById(R.id.status_ID);
                    busTrips = view.findViewById(R.id.bus_trip_ID);
                    txt_session = view.findViewById(R.id.bus_session_ID);



                    for(TransportSessions  tSession : transportSessionsArrayList){
                        if(tSession.getId() ==  (session)){
                            txt_session.setText(tSession.getName());
                            break;
                        }
                    }


                    for(TransportBusTrips  tSession : transportBusTrips){
                        if(tSession.getId() ==  busTrip){
                            busTrips.setText(tSession.getName());
                            break;
                        }
                    }


                    status.setText(busActivity);
                    studentName = view.findViewById(R.id.student_name_ID);
                    studentName.setText(studentFullName);
                    regNo = view.findViewById(R.id.reg_no_ID);
                    regNo.setText(registrationNo);
                    class_Stream = view.findViewById(R.id.class_ID);
                    class_Stream.setText(classStream);
                    _bus = view.findViewById(R.id.bus_ID);

                    _bus.setText(bus);

                 if(morningPicked.equals("YES") || eveningPicked.equals("YES")){
                     morning_layout_ID.setVisibility(View.VISIBLE);
                     evening_layout_ID.setVisibility(View.VISIBLE);
                     not_on_transport_layout.setVisibility(View.GONE);

                    morning_Picked = view.findViewById(R.id.morning_picked_ID);
                    morning_Picked.setText("\t" + morningPicked);
                    evening_Picked = view.findViewById(R.id.evening_picked_ID);
                    evening_Picked.setText("\t"+eveningPicked);

                }else {

                    not_on_transport_layout.setVisibility(View.VISIBLE);
                     morning_layout_ID.setVisibility(View.GONE);
                     evening_layout_ID.setVisibility(View.GONE);

                    stringBuilder.append("\nThis Student (" + barcode + ") is \"NOT\" on transport for the Current Term, " + termName + " " + yearID );
                    not_on_transport_ID = view.findViewById(R.id.not_on_transport_ID);
                    not_on_transport_ID.setText(stringBuilder);
                }

                builder.setView(view);

            }

            builder.setCancelable(false);

            builder.setPositiveButton("Continue scan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //this is causing crash
                    // DailyTransport.this.onClick(view);
                    //this solved the issue
                    myOnClick(DailyTransport.this.view, true,busActivity,session,(busTrip));
                }
            });
            builder.setNegativeButton("Stop scan", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int what){
                    //continueScanning = false;
                    dialog.cancel();
                }
            }).show();

        }else{

            if(latLong != null && barcode != null) {

                addItemsToSQdb(barcode, latLong, busActivity, String.valueOf(session), vehiclePlateValueSelected,String.valueOf(busTrip));

                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.barcode_success);//Student () not on transport
                alert.setMessage("Student (" + barcode+ " ) not on transport");
                alert.setCancelable(false);
                alert.setPositiveButton("Continue scan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //this is causing crash
                        // DailyTransport.this.onClick(view);
                        //this solved the issue
                        myOnClick(DailyTransport.this.view, true,busActivity,(session),(busTrip));
                    }
                });
                alert.setNegativeButton("Stop scan", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int what){
                        //continueScanning = false;
                        dialog.cancel();
                    }
                }).show();
            }


        }


    }







    //BARCODE SCANNING
    //this is your onActivityResults... so everything in onActivityResults should be display here
    private void startScan(String activity,int session,int busTrip) {

        //FIXME: had issues with crashing after scanning several times, and the preview for scanning turned dark
        //FIXED IT BY USING ZXING BARCODE SCANNING
        /**
         * Build a new MaterialBarcodeScanner
         */
//        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
//                .withActivity(DailyTransport.this)
//                .withEnableAutoFocus(true)
//                .withBleepEnabled(true)
//                .withBackfacingCamera()
//                .withCenterTracker()
//                .withText("Scanning, will take a few sec...")
//                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
//                    @Override
//                    public void onResult(Barcode barcode) {
//
//                        //SO THE CODE PLACED IN OnActivityResult should be placed here
//
//
//                        LatLong latLong = new LatLongDAO(DailyTransport.this).getLatLong();
//
//                        barcodeResult = barcode;
//
//                        onBarcodeCapture(barcode, latLong);
//                       // result.setText(barcode.rawValue);
//                    }
//                })
//                .build();
//        materialBarcodeScanner.startScan();

        Intent intent = new Intent(DailyTransport.this, CustomViewFinderScannerActivity.class);
        intent.putExtra("Bus_Activity", activity);
        intent.putExtra("Bus_Session", session);
        intent.putExtra("Bus_Trip", busTrip);

        startActivityForResult(intent, RC_BARCODE_CAPTURE);

    }




    //this is important too
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable(BARCODE_KEY, barcodeResult);
//        super.onSaveInstanceState(outState);
//    }

    //this is important too
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        //FOR location
//
//
//
//        if (requestCode != MaterialBarcodeScanner.RC_HANDLE_CAMERA_PERM) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            return;
//        }
//        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            //startScan();
//            return;
//        }
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        };
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
//        builder.setTitle("Error")
//                .setMessage(R.string.no_camera_permission)
//                .setPositiveButton(android.R.string.ok, listener)
//                .show();
//    }



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







}
