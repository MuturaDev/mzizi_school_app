package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;


import ultratude.com.mzizi.R;
import ultratude.com.mzizi.tableviewimplementation.adapter.recyclerview.holder.AbstractViewHolder;

import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.TableViewModel;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class MoodCellViewHolder extends AbstractViewHolder {
    @NonNull
    public final ImageView cell_image;

    public MoodCellViewHolder(@NonNull View itemView) {
        super(itemView);
        cell_image = itemView.findViewById(R.id.cell_image);
    }

    public void setData(Object data) {
        int mood = (int) data;
        int moodDrawable = mood == TableViewModel.HAPPY ? R.drawable.ic_happy : R.drawable.ic_sad;

        cell_image.setImageResource(moodDrawable);
    }
}