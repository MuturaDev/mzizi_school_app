package ultratude.com.mzizi.modelclasses;


import ultratude.com.mzizi.BuildConfig;

public class TransportRoutesRequest {


    private String studentID;
    private String routeID_r;
    private String termID_t;
    private String yearID_y;
    private String appCode;
    private String AppVersion;


    public TransportRoutesRequest(String studentID, String routeID_r, String termID_t, String yearID_y, String appCode) {
        this.studentID = studentID;
        this.routeID_r = routeID_r;
        this.termID_t = termID_t;
        this.yearID_y = yearID_y;
        this.appCode = appCode;
        AppVersion = String.valueOf(BuildConfig.VERSION_CODE);
    }

    public String getStudentID() {
        return studentID;
    }

    public String getRouteID_r() {
        return routeID_r;
    }

    public String getTermID_t() {
        return termID_t;
    }

    public String getYearID_y() {
        return yearID_y;
    }

    public String getAppCode() {
        return appCode;
    }

    @Override
    public String toString() {
        return "TransportRoutesRequest{" +
                "studentID='" + studentID + '\'' +
                ", routeID_r='" + routeID_r + '\'' +
                ", termID_t='" + termID_t + '\'' +
                ", yearID_y='" + yearID_y + '\'' +
                ", appCode='" + appCode + '\'' +
                '}';
    }
}
