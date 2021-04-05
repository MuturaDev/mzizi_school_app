package ultratude.com.mzizi.retrofitokhttp.Pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by James on 02/10/2018.
 */

public class FilteredStudentInfoResults {

    @SerializedName("Balance")
    private String balance = "";
    @SerializedName("PortalAccount")
    private String portalAccount = "";
    @SerializedName("BillingBalance")
    private String billingBalance = "";
    @SerializedName("PortalPaybill")
    private String portalPaybill = "";
    @SerializedName("OrganizationID")
    private String organizationID = "";



    public FilteredStudentInfoResults(){

    }


    @Override
    public String toString() {
        return "SyncMyAccountResult{" +
                ", balance='" + balance + '\'' +
                ", billingBalance='" + billingBalance + '\'' +
                ", portalAccount='" + portalAccount + '\'' +
                ", portalPaybill='" + portalPaybill + '\'' +
                ", organizationID='" + organizationID + '\'' +
                '}';
    }


    public String getBillingBalance() {
        return billingBalance;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBillingBalance(String billingBalance) {
        this.billingBalance = billingBalance;
    }

    public String getPortalAccount() {
        return portalAccount;
    }

    public void setPortalAccount(String portalAccount) {
        this.portalAccount = portalAccount;
    }

    public String getPortalPaybill() {
        return portalPaybill;
    }

    public void setPortalPaybill(String portalPaybill) {
        this.portalPaybill = portalPaybill;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }
}

