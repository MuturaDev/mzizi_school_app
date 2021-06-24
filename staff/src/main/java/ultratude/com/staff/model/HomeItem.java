package ultratude.com.staff.model;


import android.graphics.drawable.Drawable;

public class HomeItem {

    Drawable itemIcon;
    String itemName;
    boolean itemVisible;
    Integer countValue = 0;


    public HomeItem(Drawable itemIcon, String itemName, boolean itemVisible) {
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.itemVisible = itemVisible;
    }


    public Drawable getItemIcon() {
        return itemIcon;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isItemVisible() {
        return itemVisible;
    }

    public void setCountValue(Integer countValue) {
        this.countValue = countValue;
    }

    public Integer getCountValue() {
        return countValue;
    }

    @Override
    public String toString() {
        return "HomeItem{" +
                "itemIcon=" + itemIcon +
                ", itemName='" + itemName + '\'' +
                ", itemVisible=" + itemVisible +
                '}';
    }
}
