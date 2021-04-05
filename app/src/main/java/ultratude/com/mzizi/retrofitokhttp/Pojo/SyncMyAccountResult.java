package ultratude.com.mzizi.retrofitokhttp.Pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by James on 25/06/2018.
 */


public class SyncMyAccountResult implements Serializable{


    @SerializedName("Balance")
    private String balance = "";
    @SerializedName("BillingBalance")
    private String billingBalance = "";
    @SerializedName("PortalAccount")
    private String portalAccount = "";
    @SerializedName("PaybillNo")
    private String portalPaybill = "";
    @SerializedName("OrganizationID")
    private String organizationID = "";

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public SyncMyAccountResult(){

    }

    @Override
    public String toString() {
        return "SyncMyAccountResult{" +
                "balance='" + balance + '\'' +
                ", billingBalance='" + billingBalance + '\'' +
                ", portalAccount='" + portalAccount + '\'' +
                ", portalPaybill='" + portalPaybill + '\'' +
                ", organizationID='" + organizationID + '\'' +
                '}';
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBillingBalance() {
        return billingBalance;
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
