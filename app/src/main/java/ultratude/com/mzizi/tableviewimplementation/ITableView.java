package ultratude.com.mzizi.tableviewimplementation;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;


import ultratude.com.mzizi.tableviewimplementation.adapter.AbstractTableAdapter;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.CellRecyclerView;
import ultratude.com.mzizi.tableviewimplementation.filter.Filter;
import ultratude.com.mzizi.tableviewimplementation.handler.ColumnSortHandler;
import ultratude.com.mzizi.tableviewimplementation.handler.FilterHandler;
import ultratude.com.mzizi.tableviewimplementation.handler.ScrollHandler;
import ultratude.com.mzizi.tableviewimplementation.handler.SelectionHandler;
import ultratude.com.mzizi.tableviewimplementation.handler.VisibilityHandler;
import ultratude.com.mzizi.tableviewimplementation.layoutmanager.CellLayoutManager;
import ultratude.com.mzizi.tableviewimplementation.layoutmanager.ColumnHeaderLayoutManager;
import ultratude.com.mzizi.tableviewimplementation.listener.ITableViewListener;
import ultratude.com.mzizi.tableviewimplementation.listener.scroll.HorizontalRecyclerViewListener;
import ultratude.com.mzizi.tableviewimplementation.listener.scroll.VerticalRecyclerViewListener;
import ultratude.com.mzizi.tableviewimplementation.sort.SortState;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by evrencoskun on 19/06/2017.
 */

public interface ITableView {

    void addView(View child, ViewGroup.LayoutParams params);

    boolean hasFixedWidth();

    boolean isIgnoreSelectionColors();

    boolean isShowHorizontalSeparators();

    boolean isShowVerticalSeparators();

    boolean isAllowClickInsideCell();

    boolean isSortable();

    @NonNull
    Context getContext();

    @NonNull
    CellRecyclerView getCellRecyclerView();

    @NonNull
    CellRecyclerView getColumnHeaderRecyclerView();

    @NonNull
    CellRecyclerView getRowHeaderRecyclerView();

    @NonNull
    ColumnHeaderLayoutManager getColumnHeaderLayoutManager();

    @NonNull
    CellLayoutManager getCellLayoutManager();

    @NonNull
    LinearLayoutManager getRowHeaderLayoutManager();

    @NonNull
    HorizontalRecyclerViewListener getHorizontalRecyclerViewListener();

    @NonNull
    VerticalRecyclerViewListener getVerticalRecyclerViewListener();

    @Nullable
    ITableViewListener getTableViewListener();

    @NonNull
    SelectionHandler getSelectionHandler();

    @Nullable
    ColumnSortHandler getColumnSortHandler();

    @NonNull
    VisibilityHandler getVisibilityHandler();

    @NonNull
    DividerItemDecoration getHorizontalItemDecoration();

    @NonNull
    DividerItemDecoration getVerticalItemDecoration();

    @NonNull
    SortState getSortingStatus(int column);

    @Nullable
    SortState getRowHeaderSortingStatus();

    void scrollToColumnPosition(int column);

    void scrollToColumnPosition(int column, int offset);

    void scrollToRowPosition(int row);

    void scrollToRowPosition(int row, int offset);

    void showRow(int row);

    void hideRow(int row);

    boolean isRowVisible(int row);

    void showAllHiddenRows();

    void clearHiddenRowList();

    void showColumn(int column);

    void hideColumn(int column);

    boolean isColumnVisible(int column);

    void showAllHiddenColumns();

    void clearHiddenColumnList();

    int getShadowColor();

    int getSelectedColor();

    int getUnSelectedColor();

    int getSeparatorColor();

    void sortColumn(int columnPosition, @NonNull SortState sortState);

    void sortRowHeader(@NonNull SortState sortState);

    void remeasureColumnWidth(int column);

    int getRowHeaderWidth();

    void setRowHeaderWidth(int rowHeaderWidth);

    @Nullable
    AbstractTableAdapter getAdapter();

    /**
     * Filters the whole table using the provided Filter object which supports multiple filters.
     *
     * @param filter The filter object.
     */
    void filter(@NonNull Filter filter);

    /**
     * Retrieves the FilterHandler of the TableView.
     *
     * @return The FilterHandler of the TableView.
     */
    @Nullable
    FilterHandler getFilterHandler();

    /**
     * Retrieves the ScrollHandler of the TableView.
     *
     * @return The ScrollHandler of the TableView.
     */
    @NonNull
    ScrollHandler getScrollHandler();
}
