package ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder;

import androidx.annotation.NonNull;
import android.view.View;



import ultratude.com.mzizi.tableviewimplementation.sort.SortState;

/**
 * Created by evrencoskun on 16.12.2017.
 */

public class AbstractSorterViewHolder extends AbstractViewHolder {
    @NonNull
    private SortState mSortState = SortState.UNSORTED;

    public AbstractSorterViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void onSortingStatusChanged(@NonNull SortState pSortState) {
        this.mSortState = pSortState;
    }

    @NonNull
    public SortState getSortState() {
        return mSortState;
    }
}
