package ultratude.com.staff.webservice.ResponseModels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 11/01/2019.
 */

public class DutyRoster {


    @SerializedName("StaffName")
    private String staffName;
    @SerializedName("Year")
    private String year;
    @SerializedName("TermName")
    private String termName;
    @SerializedName("DutyWeek")
    private String dutyWeek;
    @SerializedName("CurrentWeek")
    private String currentWeek;
    @SerializedName("PhoneNumber")
    private String phoneNumber;

    public DutyRoster(String staffName, String year, String termName, String dutyWeek, String currentWeek, String phoneNumber ){
        this.staffName = staffName;
        this.year = year;
        this.termName = termName;
        this.dutyWeek = dutyWeek;
        this.currentWeek = currentWeek;
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "DutyRoster{" +
                "staffName='" + staffName + '\'' +
                ", year='" + year + '\'' +
                ", termName='" + termName + '\'' +
                ", dutyWeek='" + dutyWeek + '\'' +
                ", currentWeek='" + currentWeek + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public String getCurrentWeek() {
        return currentWeek;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getYear() {
        return year;
    }

    public String getTermName() {
        return termName;
    }

    public String getDutyWeek() {
        return dutyWeek;
    }
}
