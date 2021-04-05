package ultratude.com.staff.webservice.ResponseModels;

public class AssetRegisterResponse {

    private String ID;
    private String AssetName;
    private String AssetCode;
    private String AssetSerialNumber;
    private String AssetDescription;
    private String AssetBarcode;
    private String AssetTypeName;
    private String OpenMarketValue;
    private String AssetTypeID;

    public AssetRegisterResponse(String ID, String assetName, String assetCode, String assetSerialNumber, String assetDescription, String assetBarcode, String assetTypeName, String openMarketValue, String assetTypeID) {
        this.ID = ID;
        AssetName = assetName;
        AssetCode = assetCode;
        AssetSerialNumber = assetSerialNumber;
        AssetDescription = assetDescription;
        AssetBarcode = assetBarcode;
        AssetTypeName = assetTypeName;
        OpenMarketValue = openMarketValue;
        AssetTypeID = assetTypeID;
    }

    public String getID() {
        return ID;
    }

    public String getAssetName() {
        return AssetName;
    }

    public String getAssetCode() {
        return AssetCode;
    }

    public String getAssetSerialNumber() {
        return AssetSerialNumber;
    }

    public String getAssetDescription() {
        return AssetDescription;
    }

    public String getAssetBarcode() {
        return AssetBarcode;
    }

    public String getAssetTypeName() {
        return AssetTypeName;
    }

    public String getOpenMarketValue() {
        return OpenMarketValue;
    }

    public String getAssetTypeID() {
        return AssetTypeID;
    }
}
