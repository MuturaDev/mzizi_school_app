package ultratude.com.staff.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.adapters.NewHomeScreenTopItemsAdapter;
import ultratude.com.staff.datepickerfragments.DatePickeRFragment_DateServiced;
import ultratude.com.staff.R;
import ultratude.com.staff.model.HomeItem;
import ultratude.com.staff.spinnermodel.ServiceTypeSpinner;
import ultratude.com.staff.spinnermodel.VehicleSpinner;
import ultratude.com.staff.utils.UtilityFunctions;
import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleDAO;
import ultratude.com.staff.webservice.DataAccessObjects.VehicleServicingDAO;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.VehicleServicing;

/**
 * Created by James on 05/01/2019.
 */

public class ManageFleetServiceScreen extends AppCompatActivity implements View.OnClickListener ,
        DatePickeRFragment_DateServiced.DatePickerFragment_DateServiedInteractionListener {


    private EditText txt_mileagebefore_ID,
                                txt_nextservicemileage_ID,
                                txt_servicecost_ID,
                                txt_serviceReport_ID;
    private TextView txt_dateserviced_ID;
    private Spinner sp_vehicle_plate_ID, sp_Service_type_ID;


    private CardView btn_save_ID;
    private ProgressBar pb_fleetService_ID;
    private TextView txt_confirmation_message;


    private ImageView date_image;

    private  String dateServiced = "";


    private VehicleSpinner vehiclePlateValueSelected = null;
    private ServiceTypeSpinner  serviceTypeValueSelected = null;


    private void resetFields(){
        txt_mileagebefore_ID.setText("");
        txt_nextservicemileage_ID.setText("");
        txt_servicecost_ID.setText("");
        txt_serviceReport_ID.setText("");
        txt_dateserviced_ID.setText("");
        setSpinnerFields();
    }
    private void setSpinnerFields(){
        //DUMMY DATA
//        String[] numberPlates = { "Select a Vehicle Plate", generateVehicleNumberPlate(), generateVehicleNumberPlate(), generateVehicleNumberPlate(), generateVehicleNumberPlate(), generateVehicleNumberPlate()};
//        ArrayAdapter<String> numberPlatesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, numberPlates);

        //ACTUAL
        ArrayList<VehicleSpinner> vehicleSpinnerArrayList = new VehicleDAO(this).getVehicleIDWithNumberPlate();
        ArrayAdapter<VehicleSpinner> numberPlatesAdapter = new ArrayAdapter<VehicleSpinner>(this, android.R.layout.simple_spinner_dropdown_item, vehicleSpinnerArrayList);

        sp_vehicle_plate_ID.setAdapter(numberPlatesAdapter);


        //DUMMY DATA
        //String[] serviceTypeValues = { "Select a Service Type"," Major Service", "Ultimate Service", "Safety Service", "Premium Service"};
        //ArrayAdapter<String> serviceTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, serviceTypeValues);
        //ACTUAL DATA
        ArrayList<ServiceTypeSpinner> serviceTypeValues = new VehicleDAO(this).getServiceTypeList();

        ArrayAdapter<ServiceTypeSpinner> serviceTypeAdapter = new ArrayAdapter<ServiceTypeSpinner>(this, android.R.layout.simple_spinner_dropdown_item, serviceTypeValues);

        sp_Service_type_ID.setAdapter(serviceTypeAdapter);
    }



    private ImageView image_vehicle_plate_sp_ID, image_service_type_sp_ID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.fleet_service_layout);

        UtilityFunctions.activateQuickActions(this,  0, HomeScreen.CurrentScreenKey);



        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        image_service_type_sp_ID = findViewById(R.id.image_service_type_sp_ID);
        image_vehicle_plate_sp_ID = findViewById(R.id.image_vehicle_plate_sp_ID);

        btn_save_ID = findViewById(R.id.btn_save_ID);
        btn_save_ID.setOnClickListener(this);
        pb_fleetService_ID = findViewById(R.id.pb_fleet_service_progress);
        txt_confirmation_message = findViewById(R.id.txt_confirmation_message);

        txt_mileagebefore_ID = findViewById(R.id.txt_mileage_before_ID);
        txt_nextservicemileage_ID = findViewById(R.id.txt_nextservice_mileage_ID);
        txt_dateserviced_ID = findViewById(R.id.txt_dataserviced_ID);
        txt_dateserviced_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickeRFragment_DateServiced();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });
        date_image = findViewById(R.id.date_image_dateserviced);
        date_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickeRFragment_DateServiced();
                newFragment.show(getSupportFragmentManager(), "date picker");
            }
        });


        txt_servicecost_ID  = findViewById(R.id.txt_servicecost_ID);
        txt_servicecost_ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString().trim().replaceAll("[Ksh,.]", "").replace(" ","");
                if(!TextUtils.isEmpty(input))
                if(!s.toString().trim().equals(current)){
                    txt_servicecost_ID.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Ksh,.]", "").trim();


                    double parsed = Double.parseDouble(cleanString);
                    //NOTE: THIS CANNNOT DELETE, also adds a dolla sign that is difficult to remove
                    //String formatted = NumberFormat.getCurrencyInstance().format((parsed));
                    String formatted =  UtilityFunctions.customFormat("Ksh ###,###.###", parsed);

                    current = formatted;
                    txt_servicecost_ID.setText(formatted.replace("$", "Ksh"));
                    txt_servicecost_ID.setSelection(formatted.length());

                    txt_servicecost_ID.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s.toString())){
//                    try {
//                       double serviceCost =  Double.parseDouble(String.valueOf(s.toString().replace("Ksh ", "").replace(",","").trim()));
//                       txt_servicecost_ID.setText(UtilityFunctions.customFormat("Ksh ###,###.###", serviceCost));
//                    }catch (Exception e){
//                        System.out.println(s.toString());
//                    }
                }



            }
        });

        final StringBuilder addPostfix = new StringBuilder();


        txt_serviceReport_ID = findViewById(R.id.txt_service_report_ID);

        sp_vehicle_plate_ID = findViewById(R.id.sp_vehicle_plate_ID);
        sp_Service_type_ID = findViewById(R.id.sp_service_type_ID);

        //Load data to spinners
        setSpinnerFields();
        image_vehicle_plate_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_vehicle_plate_ID.performClick();
            }
        });
        sp_vehicle_plate_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                txt_confirmation_message.setText("");
                switch(position){
                    case 0:
                        vehiclePlateValueSelected = null;
                        break;
                    default:
                        vehiclePlateValueSelected =  (VehicleSpinner) parent.getSelectedItem();
                        //  Toast.makeText(ManageFleetFuelScreen.this, numberPlateValueSelected, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vehiclePlateValueSelected = null;
            }
        });

        image_service_type_sp_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_Service_type_ID.performClick();
            }
        });
        sp_Service_type_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_confirmation_message.setText("");

                switch(position){
                    case 0:
                        serviceTypeValueSelected = null;
                        break;
                    default:
                        serviceTypeValueSelected =  (ServiceTypeSpinner) parent.getSelectedItem();
                        //  Toast.makeText(ManageFleetFuelScreen.this, numberPlateValueSelected, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                serviceTypeValueSelected = null;
            }
        });

    }


    private void showProgress(boolean show){

       // txt_confirmation_message.setText("");
        if (show) {
            pb_fleetService_ID.setVisibility(View.VISIBLE);
            btn_save_ID.setVisibility(View.GONE);
            txt_confirmation_message.setVisibility(View.INVISIBLE);
        }else{
            pb_fleetService_ID.setVisibility(View.GONE);
            btn_save_ID.setVisibility(View.VISIBLE);
            txt_confirmation_message.setVisibility(View.VISIBLE);
        }

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
    public void onClick(View v)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        saveDetails();
    }


    private void saveDetails(){

        String mileageBefore = txt_mileagebefore_ID.getText().toString();
        String nextServiceMileage = txt_nextservicemileage_ID.getText().toString();
        String dateServiced = this.dateServiced;
        String serviceCost = txt_servicecost_ID.getText().toString();
        String serviceReport = txt_serviceReport_ID.getText().toString();


        if(checkVehiclePlate(vehiclePlateValueSelected) &&
                checkServiceType(serviceTypeValueSelected) &&
                checkMileageBefore(mileageBefore) &&
                checkNextServiceMilage(nextServiceMileage) &&
                checkDateServiced(txt_dateserviced_ID.getText().toString()) &&
                checkServiceCost(serviceCost.replaceAll("[Ksh,]", "")) &&
                checkServiceReport(serviceReport)){

            Staff staff = new StaffDao(ManageFleetServiceScreen.this).getUserThatSignedUp();
            String staffID = staff.getStaff_ID();


            VehicleServicing vehicleServicing = new VehicleServicing(
                    String.valueOf(vehiclePlateValueSelected.getVehicleID()),
                    staffID,
                    String.valueOf(serviceTypeValueSelected.getServiceTypeID()),
                    dateServiced,
                    mileageBefore,
                    serviceReport,
                    nextServiceMileage,
                    serviceCost,
                    staff.getAppcode()

            );

                    //TESTING
//            AlertDialog.Builder alert = new AlertDialog.Builder(this);
//            alert.setMessage(vehicleServicing.toString());
//            alert.show();




            new SaveDetailsToDB(ManageFleetServiceScreen.this).execute(vehicleServicing);
        }
    }


    private boolean checkVehiclePlate(VehicleSpinner vehiclePlate){
        if (vehiclePlate == null) {
            showProgress(false);
            txt_confirmation_message.setText("Please specify Vehicle Plate");
            return false;
        }
        return true;
    }
    private boolean checkServiceType(ServiceTypeSpinner serviceType){
        if(serviceType == null){
            showProgress(false);
            txt_confirmation_message.setText("Please specify Service Type");
            return false;
        }

        return true;
    }
    private boolean checkMileageBefore(String mileageBefore){
        txt_confirmation_message.setText("");

        if(TextUtils.isEmpty(mileageBefore)){
            showProgress(false);

            txt_mileagebefore_ID.setError("Please specify Mileage Before");
            return false;
        }


//        if(!TextUtils.isDigitsOnly((mileageBefore))){
//            showProgress(false);
//
//            txt_mileagebefore_ID.setError("Please enter a valid input");
//            return false;
//        }

        return true;
    }
    private boolean checkNextServiceMilage(String nextServiceMileage){
        txt_confirmation_message.setText("");
        if(TextUtils.isEmpty(nextServiceMileage)){
            showProgress(false);
            txt_nextservicemileage_ID.setError("Please specify Next Service Mileage");
            return false;
        }

//        if(!TextUtils.isDigitsOnly(nextServiceMileage)){
//            showProgress(false);
//            txt_nextservicemileage_ID.setError("Please enter a valid input.");
//            return false;
//        }

        return true;
    }
    private boolean checkDateServiced(String dateServiced){


        if(dateServiced.equals("Date Serviced")){
            showProgress(false);
            txt_confirmation_message.setText("Please specify Date Serviced");
            return false;
        }

        return true;
    }
    private boolean checkServiceCost(String serviceCost){
        txt_confirmation_message.setText("");

        if(TextUtils.isEmpty(serviceCost)){
            showProgress(false);
            txt_servicecost_ID.setError("Please specify Service Cost");
            return false;
        }


        if(!TextUtils.isDigitsOnly(serviceCost)){
            showProgress(false);
            txt_servicecost_ID.setError("Please specify a valid input.");
            return false;
        }

        return true;
    }
    private boolean checkServiceReport(String serviceReport){
        txt_confirmation_message.setText("");

        if(TextUtils.isEmpty(serviceReport)){
            showProgress(false);
            txt_serviceReport_ID.setError("Please specify a Service Report");
            return false;
        }

        if(TextUtils.isDigitsOnly(serviceReport)){
            showProgress(false);
            txt_serviceReport_ID.setError("Please specify valid input");
            return false;
        }

        return true;
    }



    private class SaveDetailsToDB extends AsyncTask<VehicleServicing, Void, Long>{

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
            showProgress(false);

            //THIS SHOULD BE A ALERT DIALOG
            if(s > 0){
//                txt_confirmation_message.setTextColor(getColor(R.color.confirmation_success));
//                txt_confirmation_message.setText("");

                FancyToast.makeText(ManageFleetServiceScreen.this, "Vehicle Service details saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();

                resetFields();
            }else{
                txt_confirmation_message.setText("Failed to save." + s);

                FancyToast.makeText(ManageFleetServiceScreen.this, "This is an Error Toast", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

            }


        }

        @Override
        protected Long doInBackground(VehicleServicing... params) {

            long id = 0L;
            try{
                Thread.sleep(2000);
              id =   new VehicleServicingDAO(mContext).saveVehicleServicing(params[0]);

            }catch (Exception e){
                e.printStackTrace();
            }

            return id;
        }
    }



    @Override
    public void onDatePickerFragment_DateServiedInteraction(String dateToBeSaveToDB ,long milli ) {
        //Toast.makeText(this, "Selected date is " + year + " / " + (month + 1) + " / " + day, Toast.LENGTH_LONG).show();




        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");


        Date date = new Date(milli);
        dateServiced = dateToBeSaveToDB;

        txt_dateserviced_ID.setText(sdf.format(date.getTime()));
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
