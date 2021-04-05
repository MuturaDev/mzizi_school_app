package ultratude.com.mzizi.tableviewimplementation.handler;

import androidx.annotation.NonNull;

import ultratude.com.mzizi.tableviewimplementation.ITableView;

/**
 * Created by evrencoskun on 25.04.2018.
 */

public class ColumnWidthHandler {
    @NonNull
    private ITableView mTableView;

    public ColumnWidthHandler(@NonNull ITableView tableView) {
        mTableView = tableView;
    }

    public void setColumnWidth(int columnPosition, int width) {

        // Firstly set the column header cache bindData
        mTableView.getColumnHeaderLayoutManager().setCacheWidth(columnPosition, width);

        // Set each of cell items that is located on the column position
        mTableView.getCellLayoutManager().setCacheWidth(columnPosition, width);
    }

}
