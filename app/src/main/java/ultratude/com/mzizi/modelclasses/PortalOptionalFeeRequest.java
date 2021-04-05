package ultratude.com.mzizi.modelclasses;

import ultratude.com.mzizi.BuildConfig;

public class PortalOptionalFeeRequest {

    private String feeExemptions_f;
    private String feeExemptions_i;
    private String studentID;
    private String appCode;
    private String AppVersion;

    public PortalOptionalFeeRequest(String feeExemptions_f, String feeExemptions_i, String studentID, String appCode) {
        this.feeExemptions_f = feeExemptions_f;
        this.feeExemptions_i = feeExemptions_i;
        this.studentID = studentID;
        this.appCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }


    public String getFeeExemptions_f() {
        return feeExemptions_f;
    }

    public String getFeeExemptions_i() {
        return feeExemptions_i;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getAppCode() {
        return appCode;
    }

    @Override
    public String toString() {
        return "PortalOptionalFeeRequest{" +
                "feeExemptions_f='" + feeExemptions_f + '\'' +
                ", feeExemptions_i='" + feeExemptions_i + '\'' +
                ", studentID='" + studentID + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }
}
