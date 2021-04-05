package ultratude.com.staff.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.google.android.material.snackbar.Snackbar;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import com.akhgupta.easylocation.EasyLocationAppCompatActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.com.goncalves.pugnotification.interfaces.ImageLoader;
import br.com.goncalves.pugnotification.interfaces.OnImageLoadingCompleted;
import io.paperdb.Paper;
import ultratude.com.staff.R;

import ultratude.com.staff.spinnermodel.VehicleSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.TripLatLongDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.TripLatLong;
import ultratude.com.staff.displaynotifications.SendNotifications;

import static ultratude.com.staff.displaynotifications.SendNotifications.cancelNotificationDisplay;

/**
 * Created by James on 11/05/2019.
 */

public class TripTransport extends EasyLocationAppCompatActivity  implements ImageLoader {

    private Button requestLocationUpdatesButton,stopLocationUpdatesButton;
    private TextView txt_lat_long_ID;
    private ImageView image_btn_read_text_out_loud_ID;
    private TextToSpeech tts;
    private boolean ttsStatus = false;

    private static int count = 0;

    private Spinner sp_vehicle_plate_number_ID;
    private ImageView image_vehicle_plate_number_ID;

    private TextView lable_message_ID;
    private TextView gps_report_info_ID;


    private void showLableGpsReportInfo(boolean show){
        if(show){
            lable_message_ID.setVisibility(View.INVISIBLE);
        }else{
            lable_message_ID.setVisibility(View.VISIBLE);
        }
    }

    private VehicleSpinner vehiclePlateValueSelected;
    private Staff staff;
    private Calendar cal;
    private SimpleDateFormat sdf;

    private static boolean showOnce = true;


    private TextView txt_last_location_time_ID,txt_last_location_latlong_ID;
    private LinearLayout ll_last_known_location_ID, ll_gps_on;
    private Animation anim;

    private static boolean tripEnd = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.trip_transport_layout);

        txt_last_location_latlong_ID = findViewById(R.id.txt_last_location_latlong_ID);
        txt_last_location_time_ID = findViewById(R.id.txt_last_location_time_ID);
        ll_last_known_location_ID = findViewById(R.id.ll_last_known_location_ID);
        ll_gps_on = findViewById(R.id.ll_gps_on);



        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Paper.init(this);


         staff = new StaffDao(TripTransport.this).getUserThatSignedUp();

        lable_message_ID = findViewById(R.id.lable_message_ID);
        gps_report_info_ID = findViewById(R.id.gps_report_info_ID);


        Paper.init(this);
        sp_vehicle_plate_number_ID = findViewById(R.id.sp_vehicle_plate_number_ID);
        image_vehicle_plate_number_ID = findViewById(R.id.image_vehicle_plate_number_ID);


        image_vehicle_plate_number_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_vehicle_plate_number_ID.performClick();
            }
        });
        ArrayList<VehicleSpinner> vehicleSpinnerArrayList = new VehicleDAO(this).getVehicleIDWithNumberPlate();
        ArrayAdapter<VehicleSpinner> numberPlatesAdapter = new ArrayAdapter<VehicleSpinner>(this, android.R.layout.simple_spinner_dropdown_item, vehicleSpinnerArrayList);
        sp_vehicle_plate_number_ID.setAdapter(numberPlatesAdapter);

        final VehicleSpinner selectedVehicle =  Paper.book().read("TripVehicle");

        int countVehicleId = 0;
        if(selectedVehicle != null){

            for(int count = 0; count< vehicleSpinnerArrayList.size(); count++){

                if(vehicleSpinnerArrayList.get(count).getVehicleID() == selectedVehicle.getVehicleID()){
                    countVehicleId = count;
                    break;
                }
            }


            Log.d(this.getPackageName().toUpperCase(), selectedVehicle.toString());
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


        //requestSingleLocationButton = findViewById(R.id.requestSingleLocationButton);
        requestLocationUpdatesButton  = findViewById(R.id.requestLocationUpdatesButton);
        stopLocationUpdatesButton = findViewById(R.id.stopLocationUpdatesButton);
        txt_lat_long_ID = findViewById(R.id.txt_lat_long_ID);
        image_btn_read_text_out_loud_ID = findViewById(R.id.image_btn_read_text_out_loud_ID);
        image_btn_read_text_out_loud_ID.setEnabled(true);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = tts.setLanguage(Locale.US);

                    if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        ttsStatus = false;
                       // Toast.makeText(TripTransport.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                        FancyToast.makeText(TripTransport.this,"Error occured. Sorry, you will not be able to use this service.", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }else{
                        ttsStatus = true;
                        image_btn_read_text_out_loud_ID.setEnabled(true);
                        //speakout();
                    }
                }else{
                    //Toast.makeText(TripTransport.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                    Log.d(TripTransport.this.getPackageName().toUpperCase(), "Error occured. Sorry, you will not be able to use this service.");

                    ttsStatus = false;
                }
            }
        });
        image_btn_read_text_out_loud_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tts != null){
                    tts.stop();
                    //tts.shutdown();
                }

                if(ttsStatus){
                    speakout();
                }else{
                   // Toast.makeText(TripTransport.this, "Error occured. Sorry, you will not be able to use this service.", Toast.LENGTH_SHORT).show();
                    Log.d(TripTransport.this.getPackageName().toUpperCase(), "Error occured. Sorry, you will not be able to use this service.");

                }

            }
        });


//        requestSingleLocationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                LocationRequest locationRequest = new LocationRequest()
//                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
//                        .setInterval(5000)
//                        .setFastestInterval(5000);
//                EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
//                        .setLocationRequest(locationRequest)
//                        .setFallBackToLastLocationTime(3000)
//
//                        .build();
//                requestSingleLocationFix(easyLocationRequest);
//            }
//        });

        //true = start trip // false = trip not started
       // Paper.book().write("TripStatus", false);


        requestLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(vehiclePlateValueSelected != null){

                    LocationRequest locationRequest = new LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(1000);
                    //.setFastestInterval(1000);
                    EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                            .setLocationRequest(locationRequest)
                            .setFallBackToLastLocationTime(3000)
                            .build();
                    requestLocationUpdates(easyLocationRequest);


                    txt_lat_long_ID.setText("Searching GPS Signal...");



                }else{
                    lable_message_ID.setVisibility(View.VISIBLE);
                    lable_message_ID.setText("Select vehicle plate");
                }

            }
        });




        stopLocationUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();

                tripEnd = true;

                showOnce = true;
                gps_report_info_ID.setVisibility(View.INVISIBLE);
                AlertDialog.Builder alert = new AlertDialog.Builder(TripTransport.this);
                alert.setCancelable(false);
                alert.setTitle("GPS settings");
                alert.setMessage("Disable GPS. Do you want to go to settings menu?");
                alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Paper.book().delete("TripStatus");
                        Paper.book().write("TripStatus", false);
                        Log.d(TripTransport.this.getPackageName().toUpperCase(), "Stop TripStatus update: " + String.valueOf(Paper.book().read("TripStatus")));

                        txt_lat_long_ID.setText("Please select an action");
                        txt_lat_long_ID.setVisibility(View.VISIBLE);
                        ll_gps_on.setVisibility(View.GONE);
                        ll_gps_on.clearAnimation();
                        ll_last_known_location_ID.setVisibility(View.INVISIBLE);
                        cancelNotificationDisplay(TripTransport.this);
                        Snackbar.make(findViewById(R.id.activity_main),"Arrived to destination. Gps tracking clossing...", Snackbar.LENGTH_LONG).show();
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));


                    }
                });
                alert.setNeutralButton("End tracking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Paper.book().delete("TripStatus");
                        Paper.book().write("TripStatus", false);
                        txt_lat_long_ID.setText("Please select an action");
                        txt_lat_long_ID.setVisibility(View.VISIBLE);
                        ll_last_known_location_ID.setVisibility(View.INVISIBLE);
                        ll_gps_on.setVisibility(View.GONE);
                        ll_gps_on.clearAnimation();
                        cancelNotificationDisplay(TripTransport.this);
                        Snackbar.make(findViewById(R.id.activity_main),"Arrived to destination. Gps tracking clossing...", Snackbar.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("Continue tracking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                alert.show();
            }
        });



        //check if the database has something to do with gps lat and long
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                List<Object> objectList =   new TripLatLongDAO(TripTransport.this).getTripLatLong();
                return objectList;
            }

            @Override
            protected void onPostExecute(Object o) {

                boolean tripstatus = Paper.book().read("TripStatus", false);
                if(tripstatus){

                    //this handles situations when you come back from the app and locations services are not activated and you have to activiated them again
                    LocationRequest locationRequest = new LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(1000);//but should be changed to 1000 in production
                    //.setFastestInterval(1000);
                    EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                            .setLocationRequest(locationRequest)
                            .setFallBackToLastLocationTime(3000)
                            .build();
                    requestLocationUpdates(easyLocationRequest);

                    List<Object> objectList = (List<Object>) o;
                    if(objectList.size() > 0){

                        txt_lat_long_ID.setVisibility(View.INVISIBLE);
                        anim = AnimationUtils.loadAnimation(TripTransport.this.getApplicationContext(), R.anim.anim_blink);
                        ll_gps_on.startAnimation(anim);
                        ll_gps_on.setVisibility(View.VISIBLE);

                        List<TripLatLong> tripLatLongList = (List<TripLatLong>) objectList.get(0);
                        if(tripLatLongList.size() > 0) {
                            TripLatLong tripLatLong = tripLatLongList.get(tripLatLongList.size() - 1);
                            ll_last_known_location_ID.setVisibility(View.VISIBLE);
                            txt_last_location_latlong_ID.setText(tripLatLong.getLatitude() + " : " + tripLatLong.getLongitude());
                            Date date = new Date(tripLatLong.getDateRecorded());
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                            txt_last_location_time_ID.setText(sdf.format(date));
                            gps_report_info_ID.setVisibility(View.VISIBLE);
                        }
                    }
                }


                super.onPostExecute(o);
            }
        };
        asyncTask.execute();

    }

    //keep a strong reference to keep it frombeing garbage collected inside into method
    private Target viewTarget;

    private static Target getViewTarget(final OnImageLoadingCompleted onCompleted){

        Target target = new Target(){
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                onCompleted.imageLoadingCompleted(bitmap);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }

        };

        return target;
    }


    @Override
    public void load(String uri, OnImageLoadingCompleted onCompleted) {
        viewTarget = getViewTarget(onCompleted);
        Picasso.with(TripTransport.this).load(uri).into(viewTarget);
    }

    @Override
    public void load(int imageResId, OnImageLoadingCompleted onCompleted) {
        viewTarget = getViewTarget(onCompleted);
        Picasso.with(TripTransport.this).load(imageResId).into(viewTarget);
    }

    @Override
    public void onLocationPermissionGranted() {

        showToast("Permission granted...You can continue.");

    }
    
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationPermissionDenied() {
        showToast("Permission denied!");
    }

    @Override
    public void onLocationReceived(final Location location) {


        //but should be updated from the live database
       // txt_lat_long_ID.setText(/*location.getProvider() + " : " +*/ location.getLatitude() + " : " + location.getLongitude());
       //txt_lat_long_ID.setTextColor(getResources().getColor(R.color.dark_blue));
//        showToast(location.getLatitude() + " : " + location.getLongitude());



        if(showOnce){

            txt_lat_long_ID.setVisibility(View.INVISIBLE);
            anim = AnimationUtils.loadAnimation(TripTransport.this.getApplicationContext(), R.anim.anim_blink);
            ll_gps_on.startAnimation(anim);
            ll_gps_on.setVisibility(View.VISIBLE);

            showOnce = false;
            Paper.book().write("TripVehicle", vehiclePlateValueSelected);
            Paper.book().delete("TripStatus");
            Paper.book().write("TripStatus", true);

            Log.d(TripTransport.this.getPackageName().toUpperCase(), "Start TripStatus update: " + String.valueOf(Paper.book().read("TripStatus")));
            new SendNotifications(TripTransport.this).displayNotification("MziziApp Vehicle Tracking","GPS enabled, tracking the bus in progress","now");
            showLableGpsReportInfo(true);
            Snackbar.make(findViewById(R.id.activity_main), "Departing from school. Gps tracking opening...", Snackbar.LENGTH_LONG).show();
            gps_report_info_ID.setVisibility(View.VISIBLE);
        }


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {

                TripLatLong triplat = new TripLatLong();
                triplat.setLatitude(location.getLatitude());
                triplat.setLongitude(location.getLongitude());
                triplat.setStaffID(staff.getStaff_ID());
                cal = Calendar.getInstance();
                //sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
                sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
                triplat.setDateRecorded(sdf.format(cal.getTime()));

                triplat.setVehicleID(String.valueOf(vehiclePlateValueSelected.getVehicleID()));
                triplat.setAppCode(staff.getAppcode());
                new TripLatLongDAO(TripTransport.this).saveTripLatLong(triplat);

                Log.d(TripTransport.this.getPackageName().toUpperCase(), triplat.toString());

                return triplat;
            }

            @Override
            protected void onPostExecute(Object o) {
                TripLatLong tripLatLong = (TripLatLong) o;
                ll_last_known_location_ID.setVisibility(View.VISIBLE);
                txt_last_location_latlong_ID.setText(tripLatLong.getLatitude() + " : " + tripLatLong.getLongitude());
                Date date = new Date(tripLatLong.getDateRecorded());
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                txt_last_location_time_ID.setText(sdf.format(date));

                super.onPostExecute(o);
            }
        };
        asyncTask.execute();

    }

//    @Override
//    public void noLocationReceived() {
//        showToast("No location received");
//    }


    @Override
    public void onLocationProviderEnabled() {
        /*showToast("Location services are now ON");*/
    }

    @Override
    public void onLocationProviderDisabled() {
        /*showToast("Location services are still Off");*/
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


    @Override
    protected void onDestroy() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
    }

    private void speakout(){
        StringBuilder stringb = new StringBuilder();
        stringb.append(TripTransport.this.getResources().getString(R.string.start_trip_instructions));
        stringb.append(" ");
        stringb.append(TripTransport.this.getResources().getString(R.string.end_trip_instructions));
        stringb.append(" ");
        stringb.append(TripTransport.this.getResources().getString(R.string.how_to_use_app));
       // stringb.append(" ");
       // stringb.append(TripTransport.this.getResources().getString(R.string.inform_for_notification));

        tts.speak(stringb.toString(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(TripTransport.this);
        alert.setTitle("Exit");
        alert.setMessage("Are you sure you want to exit? Press your devices's home button instead.");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                //onPause();
                finish();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();

    }









}
