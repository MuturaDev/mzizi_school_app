package ultratude.com.staff.webservice.ResponseModels;

import java.io.Serializable;

/**
 * Created by James on 20/10/2018.
 */

public class LatLong implements Serializable{

    private double latitude;
    private double longitude;


    public LatLong(){

    }

    @Override
    public String toString() {
        return "LatLong{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
