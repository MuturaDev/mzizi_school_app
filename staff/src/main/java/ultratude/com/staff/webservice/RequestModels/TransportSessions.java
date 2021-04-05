package ultratude.com.staff.webservice.RequestModels;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransportSessions implements Serializable {

    @SerializedName("ID")
    private Integer id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Code")
    private String code;

    public TransportSessions(Integer id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return name;
    }
}
