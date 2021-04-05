package ultratude.com.mzizi.tableviewimplementation.sort;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by evrencoskun on 24.11.2017.
 */

public interface ISortableModel {
    @NonNull
    String getId();

    @Nullable
    Object getContent();
}
