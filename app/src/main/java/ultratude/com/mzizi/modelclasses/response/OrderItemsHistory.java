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
//public class OrderItemsHistory {
//
//    @PrimaryKey
//    @NonNull
//    @SerializedName("PortalOrderID")
//    private String PortalOrderID;
//    @SerializedName("ItemID")
//    private String ItemID;
//    @SerializedName("ItemName")
//    private String ItemName;
//    @SerializedName("CategoryName")
//    private String CategoryName;
//    @SerializedName("ItemCode")
//    private String ItemCode;
//    @SerializedName("DispensingUnits")
//    private String DispensingUnits;
//    @SerializedName("ItemSellingPrice")
//    private String ItemSellingPrice;
//    @SerializedName("Quantity")
//    private String Quantity;
//    @SerializedName("ShowDeleteLink")
//    private String showDeleteLink;
//    @SerializedName("ItemStatus")
//    private String ItemStatus;
//    @SerializedName("DateAdded")
//    private String DateAdded;
//    @SerializedName("Remarks")
//    private String Remarks;
//    @SerializedName("Total")
//    private String Total;
//
//    public OrderItemsHistory(){}
//
//    @Ignore
//    public OrderItemsHistory(String portalOrderID, String itemID, String itemName, String categoryName, String itemCode, String dispensingUnits, String itemSellingPrice, String quantity, String showDeleteLink, String itemStatus, String dateAdded, String remarks, String total) {
//        PortalOrderID = portalOrderID;
//        ItemID = itemID;
//        ItemName = itemName;
//        CategoryName = categoryName;
//        ItemCode = itemCode;
//        DispensingUnits = dispensingUnits;
//        ItemSellingPrice = itemSellingPrice;
//        Quantity = quantity;
//        this.showDeleteLink = showDeleteLink;
//        ItemStatus = itemStatus;
//        DateAdded = dateAdded;
//        Remarks = remarks;
//        Total = total;
//    }
//
//    public String getPortalOrderID() {
//        return PortalOrderID;
//    }
//
//    public String getItemID() {
//        return ItemID;
//    }
//
//    public String getItemName() {
//        return ItemName;
//    }
//
//    public String getCategoryName() {
//        return CategoryName;
//    }
//
//    public String getItemCode() {
//        return ItemCode;
//    }
//
//    public String getDispensingUnits() {
//        return DispensingUnits;
//    }
//
//    public String getItemSellingPrice() {
//        return ItemSellingPrice;
//    }
//
//    public String getQuantity() {
//        return Quantity;
//    }
//
//    public String getShowDeleteLink() {
//        return showDeleteLink;
//    }
//
//    public String getItemStatus() {
//        return ItemStatus;
//    }
//
//    public String getDateAdded() {
//        return DateAdded;
//    }
//
//    public String getRemarks() {
//        return Remarks;
//    }
//
//    public String getTotal() {
//        return Total;
//    }
//
//    @Override
//    public String toString() {
//        return "OrderItemsHistory{" +
//                "PortalOrderID='" + PortalOrderID + '\'' +
//                ", ItemID='" + ItemID + '\'' +
//                ", ItemName='" + ItemName + '\'' +
//                ", CategoryName='" + CategoryName + '\'' +
//                ", ItemCode='" + ItemCode + '\'' +
//                ", DispensingUnits='" + DispensingUnits + '\'' +
//                ", ItemSellingPrice='" + ItemSellingPrice + '\'' +
//                ", Quantity='" + Quantity + '\'' +
//                ", showDeleteLink='" + showDeleteLink + '\'' +
//                ", ItemStatus='" + ItemStatus + '\'' +
//                ", DateAdded='" + DateAdded + '\'' +
//                ", Remarks='" + Remarks + '\'' +
//                ", Total='" + Total + '\'' +
//                '}';
//    }
//
//
//
//    public void setPortalOrderID(String portalOrderID) {
//        PortalOrderID = portalOrderID;
//    }
//
//    public void setItemID(String itemID) {
//        ItemID = itemID;
//    }
//
//    public void setItemName(String itemName) {
//        ItemName = itemName;
//    }
//
//    public void setCategoryName(String categoryName) {
//        CategoryName = categoryName;
//    }
//
//    public void setItemCode(String itemCode) {
//        ItemCode = itemCode;
//    }
//
//    public void setDispensingUnits(String dispensingUnits) {
//        DispensingUnits = dispensingUnits;
//    }
//
//    public void setItemSellingPrice(String itemSellingPrice) {
//        ItemSellingPrice = itemSellingPrice;
//    }
//
//    public void setQuantity(String quantity) {
//        Quantity = quantity;
//    }
//
//    public void setShowDeleteLink(String showDeleteLink) {
//        this.showDeleteLink = showDeleteLink;
//    }
//
//    public void setItemStatus(String itemStatus) {
//        ItemStatus = itemStatus;
//    }
//
//    public void setDateAdded(String dateAdded) {
//        DateAdded = dateAdded;
//    }
//
//    public void setRemarks(String remarks) {
//        Remarks = remarks;
//    }
//
//    public void setTotal(String total) {
//        Total = total;
//    }
//}
