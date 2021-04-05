package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder;


import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.hanks.library.AnimateCheckBox;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;


/**
 * Created by evrencoskun on 23/10/2017.
 */

public class RowHeaderViewHolder extends AbstractViewHolder {
    @NonNull
    public final TextView row_header_textview;
    public final AnimateCheckBox anim_checkbox;

    public RowHeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        anim_checkbox = itemView.findViewById(R.id.anim_check_box);
        anim_checkbox.setUncheckStatus();
       row_header_textview = itemView.findViewById(R.id.row_header_textview);
    }
}
