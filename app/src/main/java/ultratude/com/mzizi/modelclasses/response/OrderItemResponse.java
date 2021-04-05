package ultratude.com.mzizi.modelclasses.response;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class OrderItemResponse {

    @PrimaryKey
    @NonNull
    @SerializedName("ItemID")
    private String ItemID;
    @SerializedName("ItemName")
    private String ItemName;
    @SerializedName("CategoryName")
    private String CategoryName;
    @SerializedName("ItemCode")
    private String ItemCode;
    @SerializedName("DispensingUnits")
    private String DispensingUnits;
    @SerializedName("ItemSellingPrice")
    private String ItemSellingPrice;
    @SerializedName("Quantity")
    private String Quantity;

    private Integer StudID;

    public Integer getStudID() {
        return StudID;
    }

    public void setStudID(Integer studID) {
        StudID = studID;
    }

    public OrderItemResponse(){}

    @Ignore
    public OrderItemResponse(String itemID, String itemName, String categoryName, String itemCode, String dispensingUnits, String itemSellingPrice, String quantity) {
        ItemID = itemID;
        ItemName = itemName;
        CategoryName = categoryName;
        ItemCode = itemCode;
        DispensingUnits = dispensingUnits;
        ItemSellingPrice = itemSellingPrice;
        Quantity = quantity;
    }

    public String getItemID() {
        return ItemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public String getDispensingUnits() {
        return DispensingUnits;
    }

    public String getItemSellingPrice() {
        return ItemSellingPrice;
    }

    public String getQuantity() {
        return Quantity;
    }

    @Override
    public String toString() {
        return "OrderItemResponse{" +
                "ItemID='" + ItemID + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", DispensingUnits='" + DispensingUnits + '\'' +
                ", ItemSellingPrice='" + ItemSellingPrice + '\'' +
                ", Quantity='" + Quantity + '\'' +
                '}';
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public void setDispensingUnits(String dispensingUnits) {
        DispensingUnits = dispensingUnits;
    }

    public void setItemSellingPrice(String itemSellingPrice) {
        ItemSellingPrice = itemSellingPrice;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
