package ultratude.com.staff.webservice.ResponseModels;

/**
 * Created by James on 11/01/2019.
 */

public class VehicleServicing {

    private String vehicleID;
    private String servicedBy;
    private String serviceType;
    private String dateServiced;
    private String mileageBefore;
    private String serviceReport;
    private String nextServiceMileage;
    private String serviceCost;
    private String addedBy;
    private String appCode;
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public VehicleServicing(String vehicleID,
                            String servicedBy,
                            String serviceType,
                            String dateServiced,
                            String mileageBefore,
                            String serviceReport,
                            String nextServiceMileage,
                            String serviceCost,
                            String appCode){

        this.vehicleID = vehicleID;
        this.servicedBy = servicedBy;
        this.serviceType = serviceType;
        this.addedBy = servicedBy;
        this.dateServiced = dateServiced;
        this.mileageBefore = mileageBefore;
        this.serviceReport = serviceReport;
        this.nextServiceMileage = nextServiceMileage;
        this.serviceCost = serviceCost;
        this.appCode = appCode;

    }


    @Override
    public String toString() {
        return "VehicleServicing{" +
                "vehicleID='" + vehicleID + '\'' +
                ", servicedBy='" + servicedBy + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", dateServiced='" + dateServiced + '\'' +
                ", mileageBefore='" + mileageBefore + '\'' +
                ", serviceReport='" + serviceReport + '\'' +
                ", nextServiceMileage='" + nextServiceMileage + '\'' +
                ", serviceCost='" + serviceCost + '\'' +
                ", addedBy='" + addedBy + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }

    public String getServiceType() {
        return serviceType;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getServicedBy() {
        return servicedBy;
    }

    public String getDateServiced() {
        return dateServiced;
    }

    public String getMileageBefore() {
        return mileageBefore;
    }

    public String getServiceReport() {
        return serviceReport;
    }

    public String getNextServiceMileage() {
        return nextServiceMileage;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getAppCode() {
        return appCode;
    }
}
