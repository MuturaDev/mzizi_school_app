package ultratude.com.staff.webservice.RetrofitOkHTTP.Models;

/**
 * Created by James on 23/09/2018.
 */


public class UltradataRequest {
    public String StudentID_Barcode;
    public String latitude ;
    public String longitude ;
    public String bus_Activity;
    public String session;
    public String busTrip;
    public String dateScanned;

    public String getDateScanned() {
        return dateScanned;
    }

    public void setDateScanned(String dateScanned) {
        this.dateScanned = dateScanned;
    }

    public String getBusTrip() {
        return busTrip;
    }

    public void setBusTrip(String busTrip) {
        this.busTrip = busTrip;
    }

    public String staff_ID;
    public String Appcode ;
    public String vehiclePlate;

    public String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getStudentID_Barcode() {
        return StudentID_Barcode;
    }

    public void setStudentID_Barcode(String studentID_Barcode) {
        StudentID_Barcode = studentID_Barcode;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBus_Activity() {
        return bus_Activity;
    }

    public void setBus_Activity(String bus_Activity) {
        this.bus_Activity = bus_Activity;
    }

    public String getAppcode() {
        return Appcode;
    }

    public void setAppcode(String appcode) {
        Appcode = appcode;
    }
}
