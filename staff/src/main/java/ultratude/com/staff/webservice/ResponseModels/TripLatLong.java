package ultratude.com.staff.webservice.ResponseModels;

import java.io.Serializable;

/**
 * Created by James on 11/05/2019.
 */

public class TripLatLong implements Serializable {

    private double latitude;
    private double longitude;
    private String staffID;
    private String dateRecorded;//at the other end we have dateRecorded and dateReceived
    private String vehicleID;
    private String appCode;
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public String toString() {
        return "TripLatLong{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", staffID='" + staffID + '\'' +
                ", dateRecorded='" + dateRecorded + '\'' +
                ", vehicleID='" + vehicleID + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDateRecorded() {
        return dateRecorded;
    }

    public void setDateRecorded(String dateRecorded) {
        this.dateRecorded = dateRecorded;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
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
