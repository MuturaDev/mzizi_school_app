package ultratude.com.mzizi.tableviewimplementation.handler;


import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.layoutmanager.CellLayoutManager;
import ultratude.com.mzizi.tableviewimplementation.layoutmanager.ColumnHeaderLayoutManager;
import ultratude.com.mzizi.tableviewimplementation.layoutmanager.ColumnLayoutManager;

/**
 * Created by evrencoskun on 13.01.2018.
 */

public class ScrollHandler {
    @NonNull
    private ITableView mTableView;
    @NonNull
    private CellLayoutManager mCellLayoutManager;
    @NonNull
    private LinearLayoutManager mRowHeaderLayoutManager;
    @NonNull
    private ColumnHeaderLayoutManager mColumnHeaderLayoutManager;

    public ScrollHandler(@NonNull ITableView tableView) {
        this.mTableView = tableView;
        this.mCellLayoutManager = tableView.getCellLayoutManager();
        this.mRowHeaderLayoutManager = tableView.getRowHeaderLayoutManager();
        this.mColumnHeaderLayoutManager = tableView.getColumnHeaderLayoutManager();
    }

    public void scrollToColumnPosition(int columnPosition) {
        // TableView is not on screen yet.
        if (!((View) mTableView).isShown()) {
            // Change default value of the listener
            mTableView.getHorizontalRecyclerViewListener().setScrollPosition(columnPosition);
        }

        // Column Header should be scrolled firstly because of fitting column width process.
        scrollColumnHeader(columnPosition, 0);
        scrollCellHorizontally(columnPosition, 0);
    }

    public void scrollToColumnPosition(int columnPosition, int offset) {
        // TableView is not on screen yet.
        if (!((View) mTableView).isShown()) {
            // Change default value of the listener
            mTableView.getHorizontalRecyclerViewListener().setScrollPosition(columnPosition);
            mTableView.getHorizontalRecyclerViewListener().setScrollPositionOffset(offset);
        }

        // Column Header should be scrolled firstly because of fitting column width process.
        scrollColumnHeader(columnPosition, offset);
        scrollCellHorizontally(columnPosition, offset);
    }

    public void scrollToRowPosition(int rowPosition) {
        mRowHeaderLayoutManager.scrollToPosition(rowPosition);
        mCellLayoutManager.scrollToPosition(rowPosition);
    }

    public void scrollToRowPosition(int rowPosition, int offset) {
        mRowHeaderLayoutManager.scrollToPositionWithOffset(rowPosition, offset);
        mCellLayoutManager.scrollToPositionWithOffset(rowPosition, offset);
    }

    private void scrollCellHorizontally(int columnPosition, int offset) {
        CellLayoutManager cellLayoutManager = mTableView.getCellLayoutManager();

        for (int i = cellLayoutManager.findFirstVisibleItemPosition(); i < cellLayoutManager
                .findLastVisibleItemPosition() + 1; i++) {

            RecyclerView cellRowRecyclerView = (RecyclerView) cellLayoutManager
                    .findViewByPosition(i);

            if (cellRowRecyclerView != null) {
                ColumnLayoutManager columnLayoutManager = (ColumnLayoutManager)
                        cellRowRecyclerView.getLayoutManager();

                columnLayoutManager.scrollToPositionWithOffset(columnPosition, offset);
            }

        }
    }

    private void scrollColumnHeader(int columnPosition, int offset) {
        mTableView.getColumnHeaderLayoutManager().scrollToPositionWithOffset(columnPosition,
                offset);
    }

    public int getColumnPosition() {
        return mColumnHeaderLayoutManager.findFirstVisibleItemPosition();
    }

    public int getColumnPositionOffset() {
        View child = mColumnHeaderLayoutManager.findViewByPosition(mColumnHeaderLayoutManager
                .findFirstVisibleItemPosition());
        if (child != null) {
            return child.getLeft();
        }
        return 0;
    }

    public int getRowPosition() {
        return mRowHeaderLayoutManager.findFirstVisibleItemPosition();
    }

    public int getRowPositionOffset() {
        View child = mRowHeaderLayoutManager.findViewByPosition(mRowHeaderLayoutManager
                .findFirstVisibleItemPosition());
        if (child != null) {
            return child.getLeft();
        }
        return 0;
    }
}
