package ultratude.com.mzizi.modelclasses;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ListItem {

    String[] valueArray;
    String[] headerArray;

    int columnLength;
    int rowLength;

    public ListItem(){

    }

    public ListItem(int columnLength, int rowLength){
        this.columnLength = columnLength;
        this.rowLength = rowLength;
    }


    public int getColumnLength() {
        return columnLength;
    }

    public int getRowLength() {
        return rowLength;
    }

    public void setRowValueList(String... values){
        valueArray = values;
    }

    public void setColumnHeaderList(String... headers){
        headerArray = headers;
    }


    public String[] getRowValueList(){
        return valueArray;
    }

    public String[] getColumnHeaderList(){
        return headerArray;
    }

    private boolean checkIfEmpty(String value){
        if(value.isEmpty()){
            return true;
        }else{
            return false;
        }
    }


}
