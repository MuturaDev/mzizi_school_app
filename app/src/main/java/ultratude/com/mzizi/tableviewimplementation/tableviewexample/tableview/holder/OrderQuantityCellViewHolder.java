package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.elegantnumberbutton.ElegantNumberButton;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.TableViewModel;

public class OrderQuantityCellViewHolder extends AbstractViewHolder {
    @NonNull
    public final ElegantNumberButton numberButton;

    public OrderQuantityCellViewHolder(@NonNull View itemView) {
        super(itemView);
        numberButton = itemView.findViewById(R.id.number_button);
    }

}