package ultratude.com.mzizi.modelclasses;

import ultratude.com.staff.BuildConfig;

/**
 * Created by James on 05/06/2019.
 */

public class GlobalSettingsRequest {

    private String settingName;
    private String organizationID;
    private String appCode;
    private String AppVersion;


    public GlobalSettingsRequest(String settingName, String organizationID, String appCode) {
        this.settingName = settingName;
        this.organizationID = organizationID;
        this.appCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getSettingName() {
        return settingName;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public String getAppCode() {
        return appCode;
    }
}
