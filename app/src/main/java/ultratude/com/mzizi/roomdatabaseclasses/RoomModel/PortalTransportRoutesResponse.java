package ultratude.com.mzizi.roomdatabaseclasses.RoomModel;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class PortalTransportRoutesResponse {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @SerializedName("DisplayName")
    private String displayName;
    @SerializedName("TermID")
    private String termID;
    @SerializedName("YearID")
    private String yearID;
    @SerializedName("TranAmount")
    private String tranAmount;
    @SerializedName("RouteUrl")
    private String routeUrl;
    @SerializedName("RouteUrlDescription")
    private String routeUrlDescription;
    @SerializedName("RoutesID_r")
    private String routesID_r;
    @SerializedName("TermID_t")
    private String termID_t;
    @SerializedName("YearID_y")
    private String yearID_y;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public PortalTransportRoutesResponse(){}

    @Ignore
    public PortalTransportRoutesResponse(String displayName, String termID, String yearID, String tranAmount, String routeUrl, String routeUrlDescription, String routesID_r, String termID_t, String yearID_y) {
        this.displayName = displayName;
        this.termID = termID;
        this.yearID = yearID;
        this.tranAmount = tranAmount;
        this.routeUrl = routeUrl;
        this.routeUrlDescription = routeUrlDescription;
        this.routesID_r = routesID_r;
        this.termID_t = termID_t;
        this.yearID_y = yearID_y;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTermID() {
        return termID;
    }

    public String getYearID() {
        return yearID;
    }

    public String getTranAmount() {
        return tranAmount;
    }

    public String getRouteUrl() {
        return routeUrl;
    }

    public String getRouteUrlDescription() {
        return routeUrlDescription;
    }

    public String getRoutesID_r() {
        return routesID_r;
    }

    public String getTermID_t() {
        return termID_t;
    }

    public String getYearID_y() {
        return yearID_y;
    }


    @Override
    public String toString() {
        return "PortalTransportRoutesResponse{" +
                "displayName='" + displayName + '\'' +
                ", termID='" + termID + '\'' +
                ", yearID='" + yearID + '\'' +
                ", tranAmount='" + tranAmount + '\'' +
                ", routeUrl='" + routeUrl + '\'' +
                ", routeUrlDescription='" + routeUrlDescription + '\'' +
                ", routesID_r='" + routesID_r + '\'' +
                ", termID_t='" + termID_t + '\'' +
                ", yearID_y='" + yearID_y + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setTermID(String termID) {
        this.termID = termID;
    }

    public void setYearID(String yearID) {
        this.yearID = yearID;
    }

    public void setTranAmount(String tranAmount) {
        this.tranAmount = tranAmount;
    }

    public void setRouteUrl(String routeUrl) {
        this.routeUrl = routeUrl;
    }

    public void setRouteUrlDescription(String routeUrlDescription) {
        this.routeUrlDescription = routeUrlDescription;
    }

    public void setRoutesID_r(String routesID_r) {
        this.routesID_r = routesID_r;
    }

    public void setTermID_t(String termID_t) {
        this.termID_t = termID_t;
    }

    public void setYearID_y(String yearID_y) {
        this.yearID_y = yearID_y;
    }
}
