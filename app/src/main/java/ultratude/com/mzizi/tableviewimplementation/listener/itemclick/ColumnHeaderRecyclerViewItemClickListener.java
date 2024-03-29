package ultratude.com.mzizi.tableviewimplementation.listener.itemclick;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MotionEvent;
import android.view.View;

import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.CellRecyclerView;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;



/**
 * Created by evrencoskun on 26/09/2017.
 */

public class ColumnHeaderRecyclerViewItemClickListener extends AbstractItemClickListener {

    public ColumnHeaderRecyclerViewItemClickListener(@NonNull CellRecyclerView recyclerView, @NonNull ITableView
            tableView) {
        super(recyclerView, tableView);

    }

    @Override
    protected boolean clickAction(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        // Get interacted view from x,y coordinate.
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null) {
            // Find the view holder
            AbstractViewHolder holder = (AbstractViewHolder) mRecyclerView.getChildViewHolder
                    (childView);

            int column = holder.getAdapterPosition();

            // Control to ignore selection color
            if (!mTableView.isIgnoreSelectionColors()) {
                mSelectionHandler.setSelectedColumnPosition(holder, column);
            }

            // Call ITableView listener for item click
            getTableViewListener().onColumnHeaderClicked(holder, column);

            return true;
        }
        return false;
    }

    protected void longPressAction(@NonNull MotionEvent e) {
        // Consume the action for the time when the recyclerView is scrolling.
        if (mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
            return;
        }

        // Get interacted view from x,y coordinate.
        View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());

        if (child != null) {
            // Find the view holder
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(child);

            // Call ITableView listener for long click
            getTableViewListener().onColumnHeaderLongPressed(holder, holder.getAdapterPosition());
        }
    }

    @Override
    protected boolean doubleClickAction(MotionEvent e) {
        // Get interacted view from x,y coordinate.
        View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());

        if (childView != null) {
            // Find the view holder
            AbstractViewHolder holder = (AbstractViewHolder) mRecyclerView.getChildViewHolder
                    (childView);

            int column = holder.getAdapterPosition();

            // Control to ignore selection color
            if (!mTableView.isIgnoreSelectionColors()) {
                mSelectionHandler.setSelectedColumnPosition(holder, column);
            }

            // Call ITableView listener for item click
            getTableViewListener().onColumnHeaderDoubleClicked(holder, column);

            return true;
        }
        return false;
    }
}
