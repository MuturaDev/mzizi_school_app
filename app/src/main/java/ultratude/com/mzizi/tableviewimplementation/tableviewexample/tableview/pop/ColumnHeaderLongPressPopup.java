package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.pop;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.tableviewimplementation.TableView;
import ultratude.com.mzizi.tableviewimplementation.sort.SortState;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder.ColumnHeaderViewHolder;


/**
 * Created by evrencoskun on 24.12.2017.
 */

public class ColumnHeaderLongPressPopup extends PopupMenu implements PopupMenu
        .OnMenuItemClickListener {
    // Menu Item constants
    private static final int ASCENDING = 1;
    private static final int DESCENDING = 2;
    private static final int HIDE_ROW = 3;
    private static final int SHOW_ROW = 4;
    private static final int SCROLL_ROW = 5;

    @NonNull
    private TableView mTableView;
    private int mXPosition;

    public ColumnHeaderLongPressPopup(@NonNull ColumnHeaderViewHolder viewHolder, @NonNull TableView tableView) {
        super(viewHolder.itemView.getContext(), viewHolder.itemView);
        this.mTableView = tableView;
        this.mXPosition = viewHolder.getAdapterPosition();

        initialize();
    }

    private void initialize() {
        createMenuItem();
        changeMenuItemVisibility();

        this.setOnMenuItemClickListener(this);
    }

    private void createMenuItem() {
        Context context = mTableView.getContext();
        this.getMenu().add(Menu.NONE, ASCENDING, 0, context.getString(R.string.sort_ascending));
        this.getMenu().add(Menu.NONE, DESCENDING, 1, context.getString(R.string.sort_descending));
        this.getMenu().add(Menu.NONE, HIDE_ROW, 2, context.getString(R.string.hiding_row_sample));
        this.getMenu().add(Menu.NONE, SHOW_ROW, 3, context.getString(R.string.showing_row_sample));
        this.getMenu().add(Menu.NONE, SCROLL_ROW, 4, context.getString(R.string.scroll_to_row_position));
        this.getMenu().add(Menu.NONE, SCROLL_ROW, 0, "change width");
        // add new one ...

    }

    private void changeMenuItemVisibility() {
        // Determine which one shouldn't be visible
        SortState sortState = mTableView.getSortingStatus(mXPosition);
        if (sortState == SortState.UNSORTED) {
            // Show others
        } else if (sortState == SortState.DESCENDING) {
            // Hide DESCENDING menu item
            getMenu().getItem(1).setVisible(false);
        } else if (sortState == SortState.ASCENDING) {
            // Hide ASCENDING menu item
            getMenu().getItem(0).setVisible(false);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // Note: item id is index of menu item..

        switch (menuItem.getItemId()) {
            case ASCENDING:
                mTableView.sortColumn(mXPosition, SortState.ASCENDING);

                break;
            case DESCENDING:
                mTableView.sortColumn(mXPosition, SortState.DESCENDING);
                break;
            case HIDE_ROW:
                mTableView.hideRow(5);
                break;
            case SHOW_ROW:
                mTableView.showRow(5);
                break;
            case SCROLL_ROW:
                mTableView.scrollToRowPosition(5);
                break;
        }
        return true;
    }

}
