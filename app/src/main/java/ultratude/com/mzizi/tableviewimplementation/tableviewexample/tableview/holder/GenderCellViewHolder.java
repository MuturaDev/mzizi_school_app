package ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.holder;

import androidx.annotation.NonNull;
import android.view.View;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.tableview.TableViewModel;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class GenderCellViewHolder extends MoodCellViewHolder {

    public GenderCellViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data) {
        int gender = (int) data;
        int genderDrawable = gender == TableViewModel.BOY ? R.drawable.ic_male : R.drawable.ic_female;

        cell_image.setImageResource(genderDrawable);
    }
}
