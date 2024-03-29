package ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.adapter.ITableAdapter;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder.SelectionState;
import ultratude.com.mzizi.tableviewimplementation.sort.RowHeaderSortHelper;

import java.util.List;

/**
 * Created by evrencoskun on 10/06/2017.
 */

public class RowHeaderRecyclerViewAdapter<RH> extends AbstractRecyclerViewAdapter<RH> {
    @NonNull
    private ITableAdapter mTableAdapter;
    private ITableView mTableView;
    private RowHeaderSortHelper mRowHeaderSortHelper;

    public RowHeaderRecyclerViewAdapter(@NonNull Context context, @Nullable List<RH> itemList, @NonNull ITableAdapter
            tableAdapter) {
        super(context, itemList);
        this.mTableAdapter = tableAdapter;
        this.mTableView = tableAdapter.getTableView();
    }

    @NonNull
    @Override
    public AbstractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mTableAdapter.onCreateRowHeaderViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractViewHolder holder, int position) {
        mTableAdapter.onBindRowHeaderViewHolder(holder, getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        return mTableAdapter.getRowHeaderItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull AbstractViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);

        SelectionState selectionState = mTableView.getSelectionHandler().getRowSelectionState
                (viewHolder.getAdapterPosition());

        // Control to ignore selection color
        if (!mTableView.isIgnoreSelectionColors()) {

            // Change background color of the view considering it's selected state
            mTableView.getSelectionHandler().changeRowBackgroundColorBySelectionStatus
                    (viewHolder, selectionState);
        }

        // Change selection status
        viewHolder.setSelected(selectionState);
    }

    @NonNull
    public RowHeaderSortHelper getRowHeaderSortHelper() {
        if (mRowHeaderSortHelper == null) {
            // It helps to store sorting state of row headers
            this.mRowHeaderSortHelper = new RowHeaderSortHelper();
        }
        return mRowHeaderSortHelper;
    }
}
