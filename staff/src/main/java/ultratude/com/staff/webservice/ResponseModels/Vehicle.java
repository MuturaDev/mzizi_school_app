package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 12/01/2019.
 */

public class Vehicle {


    @SerializedName("VehicleID")
    private String vehicleID;
    @SerializedName("NumberPlate")
    private String numberPlate;
    @SerializedName("LastMileage")
    private String lastMileage;


    public Vehicle(String vehicleID, String numberPlate, String lastMileage){
        this.vehicleID = vehicleID;
        this.numberPlate = numberPlate;
        this.lastMileage = lastMileage;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleID='" + vehicleID + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", lastMileage='" + lastMileage + '\'' +
                '}';
    }


    public String getVehicleID() {
        return vehicleID;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getLastMileage() {
        return lastMileage;
    }
}
