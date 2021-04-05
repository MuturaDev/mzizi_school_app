package ultratude.com.staff.webservice.ResponseModels;

/**
 * Created by James on 13/01/2019.
 */

public class StaffLeaveApplication {


    private String staffID;
    private String leaveTypeID;
    private String daysApplied;
    private String wEF;
    private String delegatedStaffID;
    private String phoneNo;
    private String email;

    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String contactRelation;

    private String appCode;




    public StaffLeaveApplication(String staffID, String leaveTypeID, String daysApplied, String wEF, String delegatedStaffID, String phoneNo, String email, String contactName, String contactPhone, String contactEmail, String contactRelation, String appCode) {
        this.staffID = staffID;
        this.leaveTypeID = leaveTypeID;
        this.daysApplied = daysApplied;
        this.wEF = wEF;
        this.delegatedStaffID = delegatedStaffID;
        this.phoneNo = phoneNo;
        this.email = email;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactRelation = contactRelation;
        this.appCode = appCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    @Override
    public String toString() {
        return "StaffLeaveApplication{" +
                "staffID='" + staffID + '\'' +
                ", leaveTypeID='" + leaveTypeID + '\'' +
                ", daysApplied='" + daysApplied + '\'' +
                ", wEF='" + wEF + '\'' +
                ", delegatedStaffID='" + delegatedStaffID + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactRelation='" + contactRelation + '\'' +
                '}';
    }

    public String getStaffID() {
        return staffID;
    }

    public String getLeaveTypeID() {
        return leaveTypeID;
    }

    public String getDaysApplied() {
        return daysApplied;
    }

    public String getwEF() {
        return wEF;
    }

    public String getDelegatedStaffID() {
        return delegatedStaffID;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }
}
