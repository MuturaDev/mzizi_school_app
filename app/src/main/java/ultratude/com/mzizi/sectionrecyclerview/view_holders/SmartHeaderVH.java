package ultratude.com.mzizi.sectionrecyclerview.view_holders;

import android.view.View;
import android.widget.TextView;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.sectionrecyclerview.adapters.BaseAdapter;


public class SmartHeaderVH extends HeaderVH {

    private TextView tvItemCnt;

    public SmartHeaderVH(View itemView) {
        super(itemView);
        tvItemCnt = itemView.findViewById(R.id.tv_item_cnt);
    }

    @Override
    public void bind(BaseAdapter adapter, int color) {
        super.bind(adapter, color);
        tvItemCnt.setText(String.valueOf(adapter.getItemCount()));
    }

}