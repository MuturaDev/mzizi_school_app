package ultratude.com.staff.webservice.ResponseModels;

/**
 * Created by James on 11/01/2019.
 */

public class VehicleFueling {


    private String vehicleID;
    private String fueledBy;
    //private String fuelType;
    private String dateFueled;
    private String mileageBefore;
    private String quantity;
    private String unitPrice;
    private String addedBy;
    private String appCode;
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public VehicleFueling(String vehicleID, String fueledBy,/* String fuelType,*/ String dateFueled, String mileageBefore, String quantity, String unitPrice,
                          String appCode){
        this.vehicleID = vehicleID;
        this.fueledBy = fueledBy;
        this.addedBy = fueledBy;
       // this.fuelType = fuelType;
        this.dateFueled = dateFueled;
        this.mileageBefore = mileageBefore;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.appCode = appCode;
    }


    @Override
    public String toString() {
        return "VehicleFueling{" +
                "vehicleID='" + vehicleID + '\'' +
                ", fueledBy='" + fueledBy + '\'' +
//                ", fuelType='" + fuelType + '\'' +
                ", dateFueled='" + dateFueled + '\'' +
                ", mileageBefore='" + mileageBefore + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", addedBy='" + addedBy + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }
//
//    public String getFuelType() {
//        return fuelType;
//    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getFueledBy() {
        return fueledBy;
    }

    public String getDateFueled() {
        return dateFueled;
    }

    public String getMileageBefore() {
        return mileageBefore;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getAppCode() {
        return appCode;
    }
}
