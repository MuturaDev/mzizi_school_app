package ultratude.com.mzizi.tableviewimplementation.sort;


import androidx.annotation.NonNull;

public abstract class ColumnSortStateChangedListener {

    /**
     * Dispatches sorting changes on a column to listeners.
     *
     * @param column    Column to be sorted.
     * @param sortState SortState of the column to be sorted.
     */
    public void onColumnSortStatusChanged(int column, @NonNull SortState sortState) {
    }

    /**
     * Dispatches sorting changes to the row header column to listeners.
     *
     * @param sortState SortState of the row header column.
     */
    public void onRowHeaderSortStatusChanged(@NonNull SortState sortState) {
    }
}
