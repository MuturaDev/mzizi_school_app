package ultratude.com.staff.spinnermodel;

import java.io.Serializable;

/**
 * Created by James on 13/01/2019.
 */

public class VehicleSpinner implements Serializable{


    private Integer vehicleID;
    private String numberPlate;


    public VehicleSpinner(Integer vehicleID, String numberPlate){
        this.vehicleID = vehicleID;
        this.numberPlate = numberPlate;
    }


    @Override
    public String toString() {
        return numberPlate;
    }


    public Integer getVehicleID() {
        return vehicleID;
    }

    public String getNumberPlate() {
        return numberPlate;
    }
}
