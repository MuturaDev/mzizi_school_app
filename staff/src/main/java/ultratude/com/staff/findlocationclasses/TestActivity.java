package ultratude.com.staff.findlocationclasses;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class TestActivity extends AppCompatActivity implements   com.google.android.gms.location.LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    //LOCAITON SERVICES
    private GoogleApiClient googleApiClient;
    private LocationServices locationServices;
    private LocationRequest mLocationRequest;
    private static final int REQUEST_LOCATION = 2;
    private Location mLastLocation;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);




        //LOCATION SERVICES
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .build();





    }


    //LOCATION SERVICES
    @Override
    protected void onStart() {
        super.onStart();
        try {
            googleApiClient.connect();
        } catch (RuntimeException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            try {
                googleApiClient.disconnect();
                googleApiClient = null;
            } catch (RuntimeException e) {
                // Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
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

            Toast.makeText(this, "Latitude: " + mLastLocation.getLatitude() + "   " + "Longitude: " + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();
           //we shall just store data in the database
        }
    }
}
