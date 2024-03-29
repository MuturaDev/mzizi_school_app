package ultratude.com.mzizi.tableviewimplementation.sort;



import androidx.annotation.NonNull;

import java.util.Comparator;
import java.util.List;

/**
 * Created by evrencoskun on 25.11.2017.
 */

public class ColumnSortComparator extends AbstractSortComparator implements Comparator<List<ISortableModel>> {

    private int mXPosition;

    public ColumnSortComparator(int xPosition, @NonNull SortState sortState) {
        this.mXPosition = xPosition;
        this.mSortState = sortState;
    }

    @Override
    public int compare(List<ISortableModel> t1, List<ISortableModel> t2) {
        Object o1 = t1.get(mXPosition).getContent();
        Object o2 = t2.get(mXPosition).getContent();

        if (mSortState == SortState.DESCENDING) {
            return compareContent(o2, o1);
        } else {
            // Default sorting process is ASCENDING
            return compareContent(o1, o2);
        }
    }

}
