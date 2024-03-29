package ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.adapter.ITableAdapter;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder.SelectionState;

/**
 * Created by evrencoskun on 10/06/2017.
 */

public class CellRowRecyclerViewAdapter<C> extends AbstractRecyclerViewAdapter<C> {

    private int mYPosition;
    private ITableAdapter mTableAdapter;

    @NonNull
    private ITableView mTableView;

    public CellRowRecyclerViewAdapter(@NonNull Context context, @NonNull ITableView tableView) {
        super(context, null);
        this.mTableAdapter = tableView.getAdapter();
        this.mTableView = tableView;
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mTableAdapter.onCreateCellViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final AbstractViewHolder holder, final int xPosition) {
        mTableAdapter.onBindCellViewHolder(holder, getItem(xPosition), xPosition, mYPosition);
    }

    public int getYPosition() {
        return mYPosition;
    }

    public void setYPosition(int rowPosition) {
        mYPosition = rowPosition;
    }

    @Override
    public int getItemViewType(int position) {
        return mTableAdapter.getCellItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull AbstractViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);

        SelectionState selectionState = mTableView.getSelectionHandler().getCellSelectionState
                (viewHolder.getAdapterPosition(), mYPosition);

        // Control to ignore selection color
        if (!mTableView.isIgnoreSelectionColors()) {

            // Change the background color of the view considering selected row/cell position.
            if (selectionState == SelectionState.SELECTED) {
                viewHolder.setBackgroundColor(mTableView.getSelectedColor());
            } else {
                viewHolder.setBackgroundColor(mTableView.getUnSelectedColor());
            }
        }

        // Change selection status
        viewHolder.setSelected(selectionState);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull AbstractViewHolder holder) {
        return holder.onFailedToRecycleView();
    }

    @Override
    public void onViewRecycled(@NonNull AbstractViewHolder holder) {
        super.onViewRecycled(holder);
        holder.onViewRecycled();
    }
}
