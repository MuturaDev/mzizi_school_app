//package ultratude.com.mzizi.modelclasses.response;
//
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.Ignore;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.PrimaryKey;
//import android.support.annotation.NonNull;
//
//import com.google.gson.annotations.SerializedName;
//
//@Entity
//public class SchoolTripResponse {
//
//    @PrimaryKey
//    @NonNull
//    @SerializedName("ID")
//    private String ID;
//    @SerializedName("Name")
//    private String Name;
//    @SerializedName("Description")
//    private String Description;
//    @SerializedName("TermID")
//    private String TermID;
//    @SerializedName("YearID")
//    private String YearID;
//    @SerializedName("AmountCharged")
//    private String AmountCharged;
//    @SerializedName("ExpiryDate")
//    private String ExpiryDate;
//
//    public SchoolTripResponse(){}
//
//    @Ignore
//    public SchoolTripResponse(String ID, String name, String description, String termID, String yearID, String amountCharged, String expiryDate) {
//        this.ID = ID;
//        Name = name;
//        Description = description;
//        TermID = termID;
//        YearID = yearID;
//        AmountCharged = amountCharged;
//        ExpiryDate = expiryDate;
//    }
//
//    public String getID() {
//        return ID;
//    }
//
//    public String getName() {
//        return Name;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public String getTermID() {
//        return TermID;
//    }
//
//    public String getYearID() {
//        return YearID;
//    }
//
//    public String getAmountCharged() {
//        return AmountCharged;
//    }
//
//    public String getExpiryDate() {
//        return ExpiryDate;
//    }
//
//    @Override
//    public String toString() {
//        return "SchoolTripResponse{" +
//                "ID='" + ID + '\'' +
//                ", Name='" + Name + '\'' +
//                ", Description='" + Description + '\'' +
//                ", TermID='" + TermID + '\'' +
//                ", YearID='" + YearID + '\'' +
//                ", AmountCharged='" + AmountCharged + '\'' +
//                ", ExpiryDate='" + ExpiryDate + '\'' +
//                '}';
//    }
//
//    public void setID(String ID) {
//        this.ID = ID;
//    }
//
//    public void setName(String name) {
//        Name = name;
//    }
//
//    public void setDescription(String description) {
//        Description = description;
//    }
//
//    public void setTermID(String termID) {
//        TermID = termID;
//    }
//
//    public void setYearID(String yearID) {
//        YearID = yearID;
//    }
//
//    public void setAmountCharged(String amountCharged) {
//        AmountCharged = amountCharged;
//    }
//
//    public void setExpiryDate(String expiryDate) {
//        ExpiryDate = expiryDate;
//    }
//}
