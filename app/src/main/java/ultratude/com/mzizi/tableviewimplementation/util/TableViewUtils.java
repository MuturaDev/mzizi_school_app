package ultratude.com.mzizi.tableviewimplementation.util;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;


/**
 * Created by evrencoskun on 18/09/2017.
 */

public class TableViewUtils {

    /**
     * Helps to force width value before calling requestLayout by the system.
     */
    public static void setWidth(@NonNull View view, int width) {
        // Change width value from params
        ((RecyclerView.LayoutParams) view.getLayoutParams()).width = width;

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View
                .MeasureSpec.EXACTLY);
        view.measure(widthMeasureSpec, heightMeasureSpec);

        view.requestLayout();
    }

}
