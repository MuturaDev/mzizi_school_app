package ultratude.com.staff.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.staff.R;
import ultratude.com.staff.model.HomeItem;
import ultratude.com.staff.webservice.ResponseModels.StudentDisciplinaryCase;

public class NewHomeScreenTopItemsAdapter extends RecyclerView.Adapter<NewHomeScreenTopItemsAdapter.ViewHolder>{

    private List<HomeItem> homeItemList;
    private Context mContext;

    public NewHomeScreenTopItemsAdapter(Context mContext, List<HomeItem> homeItemList){
            this.mContext = mContext;
            this.homeItemList = homeItemList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_item_notification_count,txt_item_label;
        private ImageView img_item;
        private LinearLayout item_layout_click;

        public ViewHolder(View view){
            super(view);

            txt_item_notification_count = view.findViewById(R.id.txt_item_notification_count);
            img_item = view.findViewById(R.id.img_item);
            item_layout_click = view.findViewById(R.id.item_layout_click);
            txt_item_label = view.findViewById(R.id.txt_item_label);

        }

        public final void bind(HomeItem item){

            //TODO: UNCOMMENT THIS
//            txt_item_notification_count.setText(item.getCountValue());
//            item_layout_click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//            txt_item_label.setText(item.getItemName());
//            img_item.setImageDrawable(item.getItemIcon());


        }
    }


    @Override
    public NewHomeScreenTopItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_home_screen_top_items_layout, parent, false);
        return new NewHomeScreenTopItemsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewHomeScreenTopItemsAdapter.ViewHolder holder, int position) {
        //holder.bind(homeItemList.get(position));
    }


    @Override
    public int getItemCount() {
        return 15;//homeItemList.size();
    }

}
