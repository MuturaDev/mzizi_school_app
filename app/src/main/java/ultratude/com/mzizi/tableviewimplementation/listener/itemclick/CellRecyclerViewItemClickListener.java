package ultratude.com.mzizi.tableviewimplementation.listener.itemclick;


import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.CellRecyclerView;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.CellRowRecyclerViewAdapter;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;


/**
 * Created by evrencoskun on 26/09/2017.
 */

public class CellRecyclerViewItemClickListener extends AbstractItemClickListener {
    @NonNull
    private CellRecyclerView mCellRecyclerView;

    public CellRecyclerViewItemClickListener(@NonNull CellRecyclerView recyclerView, @NonNull ITableView tableView) {
        super(recyclerView, tableView);
        this.mCellRecyclerView = tableView.getCellRecyclerView();
    }

    @Override
    protected boolean clickAction(@NonNull RecyclerView view, @NonNull MotionEvent e) {
        // Get interacted view from x,y coordinate.
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if (childView != null) {
            // Find the view holder
            AbstractViewHolder holder = (AbstractViewHolder) mRecyclerView.getChildViewHolder
                    (childView);

            // Get y position from adapter
            CellRowRecyclerViewAdapter adapter = (CellRowRecyclerViewAdapter) mRecyclerView
                    .getAdapter();

            int column = holder.getAdapterPosition();
            int row = adapter.getYPosition();

            // Control to ignore selection color
            if (!mTableView.isIgnoreSelectionColors()) {
                mSelectionHandler.setSelectedCellPositions(holder, column, row);
            }

            // Call ITableView listener for item click
            getTableViewListener().onCellClicked(holder, column, row);

            return true;
        }
        return false;
    }

    @Override
    protected void longPressAction(@NonNull MotionEvent e) {
        // Consume the action for the time when either the cell row recyclerView or
        // the cell recyclerView is scrolling.
        if ((mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) ||
                (mCellRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE)) {
            return;
        }

        // Get interacted view from x,y coordinate.
        View child = mRecyclerView.findChildViewUnder(e.getX(), e.getY());

        if (child != null) {
            // Find the view holder
            RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(child);

            // Get y position from adapter
            CellRowRecyclerViewAdapter adapter = (CellRowRecyclerViewAdapter) mRecyclerView
                    .getAdapter();

            // Call ITableView listener for long click
            getTableViewListener().onCellLongPressed(holder, holder.getAdapterPosition(), adapter
                    .getYPosition());
        }
    }

    @Override
    protected boolean doubleClickAction(MotionEvent e) {
        // Get interacted view from x,y coordinate.
//        View childView = mCellRecyclerView.findChildViewUnder(e.getX(), e.getY());
//
//        if (childView != null) {
//            // Find the view holder
//            AbstractViewHolder holder = (AbstractViewHolder) mRecyclerView.getChildViewHolder
//                    (childView);
//
//            // Get y position from adapter
//            CellRowRecyclerViewAdapter adapter = (CellRowRecyclerViewAdapter) mRecyclerView
//                    .getAdapter();
//
//            int column = holder.getAdapterPosition();
//            int row = adapter.getYPosition();
//
//            // Control to ignore selection color
//            if (!mTableView.isIgnoreSelectionColors()) {
//                mSelectionHandler.setSelectedCellPositions(holder, column, row);
//            }
//
//            // Call ITableView listener for item click
//            getTableViewListener().onCellDoubleClicked(holder, column, row);
//
//            return true;
//        }
        return false;
    }
}
