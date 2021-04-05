package ultratude.com.staff.webservice.RequestModels;

public class MziziAppVersionControlRequest {

    private String versionName;
    private int versionCode;
    private String AppModuleName;




    public MziziAppVersionControlRequest(String versionName, int versionCode) {
        this.versionName = versionName;
        this.versionCode = versionCode;
    }

    public String getAppModuleName() {
        return AppModuleName;
    }

    public void setAppModuleName(String appModuleName) {
        AppModuleName = appModuleName;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }
}
