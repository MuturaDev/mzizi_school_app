package ultratude.com.mzizi.modelclasses;

import android.graphics.drawable.Drawable;

public class HomeItem {

    Drawable itemIcon;
    String itemName;
    boolean itemVisible;


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

    @Override
    public String toString() {
        return "HomeItem{" +
                "itemIcon=" + itemIcon +
                ", itemName='" + itemName + '\'' +
                ", itemVisible=" + itemVisible +
                '}';
    }
}
