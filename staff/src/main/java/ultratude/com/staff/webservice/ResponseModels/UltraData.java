package ultratude.com.staff.webservice.ResponseModels;

public class UltraData {

    //Private String Numberplate
    private String barcodeValue;
    private Double latitude;
    private Double longitude;
    private String bus_Activity;
    private String bus_Session;
    private String vehiclePlate;

    private String busTrips;//TODO: CHECK BUS TRIPS TO , busTripID
    private String dateScanned;

    public String getDateScanned() {
        return dateScanned;
    }

    public void setDateScanned(String dateScanned) {
        this.dateScanned = dateScanned;
    }

    public UltraData(){

    }

    public String getBusTrips() {
        return busTrips;
    }

    public void setBusTrips(String busTrips) {
        this.busTrips = busTrips;
    }

    @Override
    public String toString() {
        return "UltraData{" +
                "barcodeValue='" + barcodeValue + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", bus_Activity='" + bus_Activity + '\'' +
                ", bus_Session='" + bus_Session + '\'' +
                ", vehiclePlate='" + vehiclePlate + '\'' +
                '}';
    }

    public String getBus_Session() {
        return bus_Session;
    }

    public void setBus_Session(String bus_Session) {
        this.bus_Session = bus_Session;
    }

    public String getBarcodeValue() {
        return barcodeValue;
    }

    public void setBarcodeValue(String barcodeValue) {
        this.barcodeValue = barcodeValue;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBus_Activity() {
        return bus_Activity;
    }

    public void setBus_Activity(String bus_Activity) {
        this.bus_Activity = bus_Activity;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }
}
