package ultratude.com.staff.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import ultratude.com.staff.datepickerfragments.DatePickerFragment_DateFueled;
import ultratude.com.staff.R;
import ultratude.com.staff.spinnermodel.FuelTypeSpinner;
import ultratude.com.staff.spinnermodel.VehicleSpinner;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleFuelingDAO;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.VehicleFueling;

/**
 * Created by James on 03/01/2019.
 */

public class ManageFleetFuelScreen extends AppCompatActivity implements View.OnClickListener, DatePickerFragment_DateFueled.DatePickerFragment_DateFueledInteractionListener {


    private TextInputEditText txt_quantity, txt_mileageBefore, txt_pricePerLitre;
    private TextView txt_dateFueled;
    private Button btn_save;

    private Spinner sp_licensePlate;
    //private Spinner sp_fuelType;

    private ProgressBar pb_manageFuel;
    private TextView txt_confirmationMessage;

    private VehicleSpinner numberPlateSelected = null;
    //private FuelTypeSpinner fuelTypeSelected = null;


    private ImageView date_image;


    private String dateFueled = null;

    private void resetFields(){
        txt_quantity.setText("");
        txt_mileageBefore.setText("");
        txt_pricePerLitre.setText("");
        setSpinnerFields();
        dateFueled = null;
        txt_dateFueled.setText("From");

    }
    private void setSpinnerFields(){
        //DUMMY DATA
        ///String[] numberPlates = { "Select a License Plate", generateVehicleNumberPlate(), generateVehicleNumberPlate(), generateVehicleNumberPlate(), generateVehicleNumberPlate(), generateVehicleNumberPlate()};

        //ACTUAL DATA
        ArrayList<VehicleSpinner> vehicleSpinnerArrayList = new VehicleDAO(this).getVehicleIDWithNumberPlate();
        ArrayAdapter<VehicleSpinner> numberPlatesAdapter = new ArrayAdapter<VehicleSpinner>(this, android.R.layout.simple_spinner_dropdown_item, vehicleSpinnerArrayList);
        sp_licensePlate.setAdapter(numberPlatesAdapter);


//        //ACTUAL DATA
//        ArrayList<FuelTypeSpinner> fuelTypeSpinnerArrayList = new VehicleDAO(this).getFuelTypeList();
//        //DUMMY DATA
//        // final String[] fuelType = {"Select a Fuel Type","Diesel","Petrol"};
//        ArrayAdapter<FuelTypeSpinner> fuelTypesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuelTypeSpinnerArrayList);
//        sp_fuelType.setAdapter(fuelTypesAdapter);


    }

    //private ImageView image_fuel_type_sp_ID;
    private ImageView image_vehicle_plate_sp_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.fuel_screen_layout);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        image_vehicle_plate_sp_ID = findViewById(R.id.image_vehicle_plate_sp_ID);
       // image_fuel_type_sp_ID = findViewById(R.id.image_fuel_type_sp_ID);

        txt_quantity = findViewById(R.id.txt_quantity_ID);
        txt_mileageBefore = findViewById(R.id.txt_mileage_before_ID);
        txt_mileageBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ManageFleetFuelScreen.this, "milage clicked", Toast.LENGTH_SHORT).show();
            }
        });
        txt_dateFueled = findViewById(R.id.txt_datafueled_ID);
        txt_dateFueled.setCursorVisible(false);
        txt_dateFueled.setActivated(false);
        txt_dateFueled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment_DateFueled();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });
        date_image = findViewById(R.id.date_image_ID);
        date_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment_DateFueled();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });


        txt_pricePerLitre = findViewById(R.id.txt_price_per_l_ID);
        sp_licensePlate = findViewById(R.id.sp_license_plate_ID);
        //sp_fuelType = findViewById(R.id.txt_fuel_type_ID);

        //values to show in spinner
        setSpinnerFields();
        image_vehicle_plate_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_licensePlate.performClick();
            }
        });
        sp_licensePlate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_confirmationMessage.setText("");

                switch(position){
                    case 0:
                        numberPlateSelected = null;
                        break;
                    default:
                        numberPlateSelected = (VehicleSpinner)  parent.getSelectedItem();
                      //  Toast.makeText(ManageFleetFuelScreen.this, numberPlateSelected, Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numberPlateSelected = null;
            }
        });


//        image_fuel_type_sp_ID.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sp_fuelType.performClick();
//            }
//        });
//        sp_fuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                txt_confirmationMessage.setText("");
//
//                switch(position){
//                    case 0:
//                        fuelTypeSelected = null;
//                        break;
//                    default :
//                      fuelTypeSelected = (FuelTypeSpinner)  parent.getSelectedItem();
//                        //Toast.makeText(ManageFleetFuelScreen.this, fuelTypeSelected, Toast.LENGTH_SHORT).show();
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                fuelTypeSelected = null;
//            }
//        });


        pb_manageFuel = findViewById(R.id.pb_managefuel_progress);
        txt_confirmationMessage = findViewById(R.id.txt_confirmation_message);

        btn_save = findViewById(R.id.btn_save_ID);
        btn_save.setOnClickListener(this);


    }

    private String generateVehicleNumberPlate(){
        StringBuilder s = new StringBuilder();
        s.append("K");
        for (int i = 0; i < 2; i++) {
            char ch = (char) (Math.random() * 3 + 'A');//should be 26
            s.append(ch);
        }
        s.append(" ");
        for (int i = 0; i < 3; i++) {
            char digit1 = (char) (Math.random() * 10 + '0');
            s.append(digit1);
        }
        for (int i = 0; i < 1; i++) {
            char ch = (char) (Math.random() * 26 + 'A');//should be 26
            s.append(ch);
        }
       // System.out.println("Random vehicle plate number: " + s);
        return s.toString();
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        saveDetails();
    }

    private void saveDetails(){


        String quantity = txt_quantity.getText().toString().trim();
        String mileageBefore = txt_mileageBefore.getText().toString().trim();

        String pricePerLitre=txt_pricePerLitre.getText().toString().trim();


        if(checkNumberPlateValueSelected(numberPlateSelected) /*&& checkFuelTypeValueSelected(fuelTypeSelected)*/ && checkQuantity(quantity) && checkMileageBefore(mileageBefore) && checkDateFueled(dateFueled) && checkPricePerLitre(pricePerLitre)){

            Staff staff = new StaffDao(ManageFleetFuelScreen.this).getUserThatSignedUp();


            VehicleFueling vehicle = new VehicleFueling(
                    String.valueOf(numberPlateSelected.getVehicleID()),
                    staff.getStaff_ID(),
//                    String.valueOf(fuelTypeSelected.getFuelTypeID()),
                    dateFueled,
                    mileageBefore,
                    quantity,
                    pricePerLitre,
                    staff.getAppcode()


            );

            //TESTING
//            AlertDialog.Builder  alert = new AlertDialog.Builder(this);
//            alert.setMessage(vehicle.toString());
//            alert.show();

            new SaveDetailsToDB(ManageFleetFuelScreen.this).execute(vehicle);
        }

    }

    private void showProgress(boolean show){
        txt_confirmationMessage.setText("");
        if (show) {
            pb_manageFuel.setVisibility(View.VISIBLE);
            btn_save.setVisibility(View.GONE);
            txt_confirmationMessage.setVisibility(View.INVISIBLE);
        }else{
            pb_manageFuel.setVisibility(View.GONE);
            btn_save.setVisibility(View.VISIBLE);
            txt_confirmationMessage.setVisibility(View.VISIBLE);
        }

    }

    private boolean checkNumberPlateValueSelected(VehicleSpinner vehicle){

        if(vehicle == null){
            showProgress(false);
            txt_confirmationMessage.setText("Please select Vehicle Plate.");
            return false;
        }

        return true;
    }

    private boolean checkFuelTypeValueSelected(FuelTypeSpinner fuelType){

        if(fuelType == null){
            showProgress(false);
            txt_confirmationMessage.setText("Please select Fuel Type.");
            return false;
        }

        return true;
    }



    private boolean checkQuantity(String quantity){
        txt_confirmationMessage.setText("");
        if(TextUtils.isEmpty(quantity)){
            showProgress(false);
            txt_quantity.setError("Please enter quantity.");
            return false;
        }


        if(!TextUtils.isDigitsOnly(quantity)){
            showProgress(false);
            txt_quantity.setError("Please enter valid input.");
            return false;
        }

        return true;
    }
    private boolean checkMileageBefore(String mileageBefore){
        txt_confirmationMessage.setText("");

        int lastMileageValue = new VehicleDAO(this).getLastMileage(numberPlateSelected);

        if(TextUtils.isEmpty(mileageBefore)){
            showProgress(false);
            txt_mileageBefore.setError("Please specify Mileage before.");
            return false;
        }else if(Integer.valueOf(mileageBefore) <= lastMileageValue){
            Log.d(this.getPackageName().toUpperCase(), String.valueOf(Integer.valueOf(mileageBefore) <= lastMileageValue));
            showProgress(false);
            txt_mileageBefore.setError(String.format("Please enter a value greater than " + lastMileageValue + "."));
            return false;
        }

//        if(!TextUtils.isDigitsOnly(mileageBefore)){
//            showProgress(false);
//            txt_mileageBefore.setError("Please enter valid input");
//            return false;
//        }

        return true;
    }
    private boolean checkDateFueled(String dateFueled){

        if(dateFueled == null){
            showProgress(false);
            txt_confirmationMessage.setText("Please specify Date Fueled.");
            return false;
        }

        return true;
    }
    private boolean checkPricePerLitre(String pricePerLitre){
        txt_confirmationMessage.setText("");
        if(TextUtils.isEmpty(pricePerLitre)){
            showProgress(false);
            txt_pricePerLitre.setError("Please specify Price per litre.");
            return false;
        }


        if(!TextUtils.isDigitsOnly(pricePerLitre)){
            showProgress(false);
            txt_pricePerLitre.setError("Please specify valid input.");
            return false;
        }

        return true;
    }




    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onDatePickerFragment_DateFueledInteraction(String dateToBeSaveToDB ,long milli) {
       // Toast.makeText(this, "Selected date is " + year + " / " + (month + 1) + " / " + day, Toast.LENGTH_LONG).show();

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");

        Date date = new Date(milli);
        dateFueled = dateToBeSaveToDB;
        txt_dateFueled.setText(sdf.format(date.getTime()));
    }



    private class SaveDetailsToDB extends AsyncTask<VehicleFueling, Void, Long> {

        private Context mContext;

        public SaveDetailsToDB(Context context){
            this.mContext = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress(true);
        }

        @Override
        protected void onPostExecute(Long s) {
            super.onPostExecute(s);


            if(s > 0){
                //THIS SHOULD BE A ALERT DIALOG
                showProgress(false);
                //txt_confirmationMessage.setTextColor(getResources().getColor(R.color.confirmation_success));
                //txt_confirmationMessage.setText("Fuel consumption details saved successfully");

                FancyToast.makeText(ManageFleetFuelScreen.this, "Fuel consumption details saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();


                resetFields();
            }else{
                //txt_confirmationMessage.setText("Failed to save. ");
                FancyToast.makeText(ManageFleetFuelScreen.this, "Failed to save. Please repeate the process.", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            }

        }

        @Override
        protected Long doInBackground(VehicleFueling... params) {
            long id = 0l;
            try{
                Thread.sleep(2000);
                id = new VehicleFuelingDAO(mContext).saveVehicleFuelingDAO(params[0]);

            }catch (Exception e){
                e.printStackTrace();
            }

            return id;
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
}
