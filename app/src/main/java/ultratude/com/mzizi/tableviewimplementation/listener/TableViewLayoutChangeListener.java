package ultratude.com.mzizi.tableviewimplementation.listener;

import androidx.annotation.NonNull;
import android.view.View;



import ultratude.com.mzizi.tableviewimplementation.ITableView;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.CellRecyclerView;
import ultratude.com.mzizi.tableviewimplementation.layoutmanager.CellLayoutManager;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by evrencoskun on 21.01.2018.
 */

public class TableViewLayoutChangeListener implements View.OnLayoutChangeListener {
    @NonNull
    private CellRecyclerView mCellRecyclerView;
    @NonNull
    private CellRecyclerView mColumnHeaderRecyclerView;
    @NonNull
    private CellLayoutManager mCellLayoutManager;

    public TableViewLayoutChangeListener(@NonNull ITableView tableView) {
        this.mCellRecyclerView = tableView.getCellRecyclerView();
        this.mColumnHeaderRecyclerView = tableView.getColumnHeaderRecyclerView();
        this.mCellLayoutManager = tableView.getCellLayoutManager();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int
            oldTop, int oldRight, int oldBottom) {

        if (v.isShown() && (right - left) != (oldRight - oldLeft)) {

            // Control who need the remeasure
            if (mColumnHeaderRecyclerView.getWidth() > mCellRecyclerView.getWidth()) {
                // Remeasure all nested CellRow recyclerViews
                mCellLayoutManager.remeasureAllChild();

            } else if (mCellRecyclerView.getWidth() > mColumnHeaderRecyclerView.getWidth()) {

                // It seems Column Header is needed.
                mColumnHeaderRecyclerView.getLayoutParams().width = WRAP_CONTENT;
                mColumnHeaderRecyclerView.requestLayout();
            }
        }
    }
}