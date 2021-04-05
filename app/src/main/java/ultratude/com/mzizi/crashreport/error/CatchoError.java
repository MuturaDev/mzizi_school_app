package ultratude.com.mzizi.crashreport.error;



import java.io.Serializable;

/**
 * Created by alhazmy13 on 2/13/17.
 */

public class CatchoError implements Serializable {
    private String error;
    private DeviceInformation deviceInformation;
    private Firmware firmware;
    private String appVersion;
    private String user;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public DeviceInformation getDeviceInformation() {
        return deviceInformation;
    }

    void setDeviceInformation(DeviceInformation deviceInformation) {
        this.deviceInformation = deviceInformation;
    }

    public Firmware getFirmware() {
        return firmware;
    }

    void setFirmware(Firmware firmware) {
        this.firmware = firmware;
    }

    @Override
    public String toString() {
        return "************ CAUSE OF ERROR ************\n" +
                error +
                "\n************ DEVICE INFORMATION ***********\n" +
                deviceInformation +
                "\n************ FIRMWARE ************\n" +
                firmware +
                "\n************ APPVERSION ************\n" +
                appVersion+
                 "\n************ USER ************\n" +
                user;
    }

}