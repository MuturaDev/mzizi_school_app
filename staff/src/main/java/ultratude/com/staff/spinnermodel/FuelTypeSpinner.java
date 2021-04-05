package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 13/01/2019.
 */

public class FuelTypeSpinner {

    private Integer fuelTypeID;
    private String fuelTypeName;

    public FuelTypeSpinner(Integer fuelTypeID, String fuelTypeName){
        this.fuelTypeID = fuelTypeID;
        this.fuelTypeName = fuelTypeName;
    }

    @Override
    public String toString() {
        return fuelTypeName;
    }

    public Integer getFuelTypeID() {
        return fuelTypeID;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }
}
