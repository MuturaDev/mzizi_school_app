package ultratude.com.mzizi.tableviewimplementation.handler;

import androidx.annotation.NonNull;


import ultratude.com.mzizi.tableviewimplementation.TableView;
import ultratude.com.mzizi.tableviewimplementation.preference.Preferences;

/**
 * Created by evrencoskun on 3.03.2018.
 */

public class PreferencesHandler {
    @NonNull

    private ScrollHandler scrollHandler;
    @NonNull
    private SelectionHandler selectionHandler;

    public PreferencesHandler(@NonNull TableView tableView) {
        this.scrollHandler = tableView.getScrollHandler();
        this.selectionHandler = tableView.getSelectionHandler();
    }

    @NonNull
    public Preferences savePreferences() {
        Preferences preferences = new Preferences();
        preferences.columnPosition = scrollHandler.getColumnPosition();
        preferences.columnPositionOffset = scrollHandler.getColumnPositionOffset();
        preferences.rowPosition = scrollHandler.getRowPosition();
        preferences.rowPositionOffset = scrollHandler.getRowPositionOffset();
        preferences.selectedColumnPosition = selectionHandler.getSelectedColumnPosition();
        preferences.selectedRowPosition = selectionHandler.getSelectedRowPosition();
        return preferences;
    }

    public void loadPreferences(@NonNull Preferences preferences) {
        scrollHandler.scrollToColumnPosition(preferences.columnPosition, preferences.columnPositionOffset);
        scrollHandler.scrollToRowPosition(preferences.rowPosition, preferences.rowPositionOffset);
        selectionHandler.setSelectedColumnPosition(preferences.selectedColumnPosition);
        selectionHandler.setSelectedRowPosition(preferences.selectedRowPosition);
    }
}
