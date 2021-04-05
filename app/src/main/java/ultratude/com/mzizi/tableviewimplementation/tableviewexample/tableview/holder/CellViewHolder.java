package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import ultratude.com.mzizi.R;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.model.Cell;

/**
 * Created by evrencoskun on 23/10/2017.
 */

public class CellViewHolder extends AbstractViewHolder {
    @NonNull
    private final TextView cell_textview;
    @NonNull
    private final LinearLayout cell_container;

    public CellViewHolder(@NonNull View itemView) {
        super(itemView);
        cell_textview = itemView.findViewById(R.id.cell_data);
        cell_container = itemView.findViewById(R.id.cell_container);
    }

    public void setCell(@Nullable Cell cell) {
        cell_textview.setText(String.valueOf(cell.getData()));

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        cell_textview.requestLayout();
    }
}
