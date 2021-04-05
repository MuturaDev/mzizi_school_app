package ultratude.com.staff.webservice.RequestModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransportBusTrips implements Serializable {

    @SerializedName("ID")
    private Integer id;
    @SerializedName("Name")
    private String name;

    public TransportBusTrips(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
