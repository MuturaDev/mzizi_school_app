package ultratude.com.mzizi.tableviewimplementation.filter;

import androidx.annotation.NonNull;

import ultratude.com.mzizi.tableviewimplementation.ITableView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class used to store multiple filters for the TableView filtering feature.
 */
public class Filter {

    /**
     * List of filter items to be used for filtering.
     */
    @NonNull
    private List<FilterItem> filterItems;

    /**
     * The TableView instance used in this scope.
     */
    @NonNull
    private ITableView tableView;

    /**
     * @param tableView The TableView to be filtered.
     */
    public Filter(@NonNull ITableView tableView) {
        this.tableView = tableView;
        this.filterItems = new ArrayList<>();
    }

    /**
     * Adds new filter item to the list. This should be used strictly once
     * for filtering the whole table.
     *
     * @param filter the filter string.
     */
    public void set(@NonNull String filter) {
        set(-1, filter);
    }

    /**
     * Adds new filter item to the list. The filter will be used on the
     * specified column.
     *
     * @param column the target column for filtering.
     * @param filter the filter string.
     */
    public void set(int column, @NonNull String filter) {
        final FilterItem filterItem = new FilterItem(
                column == -1 ? FilterType.ALL : FilterType.COLUMN,
                column,
                filter
        );

        if (isAlreadyFiltering(column, filterItem)) {
            if (filter.isEmpty()) {
                remove(column, filterItem);
            } else {
                update(column, filterItem);
            }
        } else if (!filter.isEmpty()) {
            add(filterItem);
        }
    }

    /**
     * Adds new filter item to the list of this class.
     *
     * @param filterItem The filter item to be added to the list.
     */
    private void add(@NonNull FilterItem filterItem) {
        filterItems.add(filterItem);
        tableView.filter(this);
    }

    /**
     * Removes a filter item from the list of this class.
     *
     * @param column     The column to be checked for removing the filter item.
     * @param filterItem The filter item to be removed.
     */
    private void remove(int column, @NonNull FilterItem filterItem) {
        // This would remove a FilterItem from the Filter list when the filter is cleared.
        // Used Iterator for removing instead of canonical loop to prevent ConcurrentModificationException.
        for (Iterator<FilterItem> filterItemIterator = filterItems.iterator(); filterItemIterator.hasNext(); ) {
            final FilterItem item = filterItemIterator.next();
            if (column == -1 && item.getFilterType().equals(filterItem.getFilterType())) {
                filterItemIterator.remove();
                break;
            } else if (item.getColumn() == filterItem.getColumn()) {
                filterItemIterator.remove();
                break;
            }
        }
        tableView.filter(this);
    }

    /**
     * Updates a filter item from the list of this class.
     *
     * @param column     The column in which filter item will be updated.
     * @param filterItem The updated filter item.
     */
    private void update(int column, @NonNull FilterItem filterItem) {
        // This would update an existing FilterItem from the Filter list.
        // Used Iterator for updating instead of canonical loop to prevent ConcurrentModificationException.
        for (Iterator<FilterItem> filterItemIterator = filterItems.iterator(); filterItemIterator.hasNext(); ) {
            final FilterItem item = filterItemIterator.next();
            if (column == -1 && item.getFilterType().equals(filterItem.getFilterType())) {
                filterItems.set(filterItems.indexOf(item), filterItem);
                break;
            } else if (item.getColumn() == filterItem.getColumn()) {
                filterItems.set(filterItems.indexOf(item), filterItem);
                break;
            }
        }
        tableView.filter(this);
    }

    /**
     * Method to check if a filter item is already added based on the column to be filtered.
     *
     * @param column     The column to be checked if the list is already filtering.
     * @param filterItem The filter item to be checked.
     * @return True if a filter item for a specific column or for ALL is already in the list.
     */
    private boolean isAlreadyFiltering(int column, @NonNull FilterItem filterItem) {
        // This would determine if Filter is already filtering ALL or a specified COLUMN.
        for (FilterItem item : filterItems) {
            if (column == -1 && item.getFilterType().equals(filterItem.getFilterType())) {
                return true;
            } else if (item.getColumn() == filterItem.getColumn()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the list of filter items.
     *
     * @return The list of filter items.
     */
    @NonNull
    public List<FilterItem> getFilterItems() {
        return this.filterItems;
    }
}
