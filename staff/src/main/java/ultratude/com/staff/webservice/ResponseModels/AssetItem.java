package ultratude.com.staff.webservice.ResponseModels;

public class AssetItem {

    private String barCodeNumber;
    private String assetType;
    private String latitude;
    private String longitude;
    private String staffID;
    private String scanDateTime;
    private String comment;
    private String _ID;

    //for the request
    private String AppVersion;
    private String AppCode;

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public void setAppCode(String appCode) {
        AppCode = appCode;
    }

    public AssetItem(String barCodeNumber, String assetType, String latitude, String longitude, String staffID, String scanDateTime, String comment) {
        this.barCodeNumber = barCodeNumber;
        this.assetType = assetType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.staffID = staffID;
        this.scanDateTime = scanDateTime;
        this.comment = comment;
    }

    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getBarCodeNumber() {
        return barCodeNumber;
    }

    public String getAssetType() {
        return assetType;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getScanDateTime() {
        return scanDateTime;
    }

    public String getComment() {
        return comment;
    }
}
