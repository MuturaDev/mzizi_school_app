package ultratude.com.staff.webservice.RequestModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PortalTransportSessionBusTripsResponse implements Serializable {

    @SerializedName("session")
    ArrayList<TransportSessions> sessionsList;
    @SerializedName("trips")
    ArrayList<TransportBusTrips> busTripsList;

    public PortalTransportSessionBusTripsResponse(ArrayList<TransportSessions> sessionsList, ArrayList<TransportBusTrips> busTripsList) {
        this.sessionsList = sessionsList;
        this.busTripsList = busTripsList;
    }

    public ArrayList<TransportSessions> getSessionsList() {
        return sessionsList;
    }

    public ArrayList<TransportBusTrips> getBusTripsList() {
        return busTripsList;
    }
}
