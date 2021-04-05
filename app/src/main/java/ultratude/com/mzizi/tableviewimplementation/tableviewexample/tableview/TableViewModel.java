package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview;


import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.ListItem;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.Cell;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.ColumnHeader;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.RowHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class TableViewModel {

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;
    public static final int ORDER_QUANTITY_CELL_TYPE = 3;

    // Constant values for icons
    public static final int SAD = 1;
    public static final int HAPPY = 2;
    public static final int BOY = 1;
    public static final int GIRL = 2;

    // Constant size for dummy data sets
    private static final int COLUMN_SIZE = 20;
    private static final int ROW_SIZE = 20;

    // Drawables
    @DrawableRes
    private final int mBoyDrawable;
    @DrawableRes
    private final int mGirlDrawable;
    @DrawableRes
    private final int mHappyDrawable;
    @DrawableRes
    private final int mSadDrawable;

    public TableViewModel() {
        // initialize drawables
        mBoyDrawable = R.drawable.ic_male;
        mGirlDrawable = R.drawable.ic_female;
        mHappyDrawable = R.drawable.ic_happy;
        mSadDrawable = R.drawable.ic_sad;
    }


    public static List<ListItem> getTableData(){
        List<ListItem> returnList = new ArrayList<>();

//        ListItem item = new ListItem(5,5);
//        item.setColumnHeaderList("Category", "Item Name", "Item Cost","Order Quantity", "Units");
//        item.setRowValueList("UNIFORM ACCOUNT", "SHIRT DEMO", "850", "0", "Unit(s)");
//        returnList.add(item);
//
//        ListItem item1 = new ListItem(5,5);
//        item1.setColumnHeaderList("Category", "Item Name", "Item Cost","Order Quantity", "Units");
//        item1.setRowValueList("UNIFORM ACCOUNT", "SOCKS", "950", "0", "Piece(s)");
//        returnList.add(item1);
//
//        ListItem item2 = new ListItem(5,5);
//        item2.setColumnHeaderList("Category", "Item Name", "Item Cost","Order Quantity", "Units");
//        item2.setRowValueList("UNIFORM ACCOUNT", "SWEATER DEMO", "850", "0", "Unit(s)");
//        returnList.add(item2);
//
//        ListItem item3 = new ListItem(5,5);
//        item3.setColumnHeaderList("Category", "Item Name", "Item Cost","Order Quantity", "Units");
//        item3.setRowValueList("UNIFORM ACCOUNT", "SHIRT_DAMACREST", "100", "0", "Pair(s)");
//        returnList.add(item3);
//
//        ListItem item4 = new ListItem(5,5);
//        item4.setColumnHeaderList("Category", "Item Name", "Item Cost","Order Quantity", "Units");
//        item4.setRowValueList("UNIFORM ACCOUNT", "WHITE SOCKS", "250", "0", "Pair(s)");
//        returnList.add(item4);

        return returnList;
    }





    @NonNull
    private List<RowHeader> getSimpleRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        List<ListItem> listItemList = getTableData();
        if(!listItemList.isEmpty()){
            for (int i = 0; i < listItemList.get(0).getRowLength(); i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + Integer.valueOf(i+1));
                list.add(header);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        List<ListItem> listItemList = getTableData();
        if(!listItemList.isEmpty()) {
            for (int i = 0; i < listItemList.get(0).getColumnLength(); i++) {
                String title = listItemList.get(0).getColumnHeaderList()[i];
                //int nRandom = new Random().nextInt();
//                if (nRandom % 4 == 0 || nRandom % 3 == 0 || nRandom == i) {
//                    title = "large column " + i;
//                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        List<ListItem> listItemList = getTableData();
        if(!listItemList.isEmpty()) {
            for (int i = 0; i < listItemList.get(0).getRowLength(); i++) {
                List<Cell> cellList = new ArrayList<>();
                for (int j = 0; j < listItemList.get(0).getColumnLength(); j++) {
                    Object text = listItemList.get(i).getRowValueList()[j];//"cell " + j + " " + i;
                    String header = listItemList.get(i).getColumnHeaderList()[j];

                    if(header.equalsIgnoreCase("Order Quantity") || j == ORDER_QUANTITY_CELL_TYPE){
                       // text = text + "Order Quantity";//to be stripped out
                    }

                    Cell cell;

                    if(header.equalsIgnoreCase("Order Quantity") || j == ORDER_QUANTITY_CELL_TYPE){
                        cell = new Cell(String.valueOf(i), text);
                    }else{
                        cell = new Cell(String.valueOf(i), text);
                    }

                    cellList.add(cell);

//                    final int random = new Random().nextInt();
//                    if (j == 0) {//column0
//                        text = i;
//                    } else if (j == 1) {//colum1
//                        text = random;
//                    } else if (j == MOOD_COLUMN_INDEX) {//column3
//                        text = random % 2 == 0 ? HAPPY : SAD;
//                    } else if (j == GENDER_COLUMN_INDEX) {//column4
//                        text = random % 2 == 0 ? BOY : GIRL;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
                }
                list.add(cellList);
            }
        }

        return list;
    }

    @DrawableRes
    public int getDrawable(int value, boolean isGender) {
        if (isGender) {
            return value == BOY ? mBoyDrawable : mGirlDrawable;
        } else {
            return value == SAD ? mSadDrawable : mHappyDrawable;
        }
    }

    @NonNull
    public List<List<Cell>> getCellList() {
        return getCellListForSortingTest();
    }

    @NonNull
    public List<RowHeader> getRowHeaderList() {
        return getSimpleRowHeaderList();
    }

    @NonNull
    public List<ColumnHeader> getColumnHeaderList() {
        return getRandomColumnHeaderList();
    }
}
