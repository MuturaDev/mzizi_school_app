package ultratude.com.staff.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ultratude.com.staff.R;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.model.HomeItem;

public class NewHomeScreenBottomItemsAdapter extends BaseAdapter {
    private HomeScreen myActivity;
    private List<HomeItem> homeItemList;

    public NewHomeScreenBottomItemsAdapter(HomeScreen myActivity, List<HomeItem> homeitemList){
        this.myActivity = myActivity;
        this.homeItemList = homeitemList;
    }

    @Override
    public int getCount() {
        return homeItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.new_home_screen_bottom_item_layout, null, false);
        LinearLayout homeItemID = view.findViewById(R.id.item_layout_click);


        //TODO: UNCOMMENT THIS
        homeItemID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String iconHeaderName = homeItemList.get(position).getItemName();

                myActivity.changeScreen(iconHeaderName, null);

               // Toast.makeText(myActivity, "You Clicked on: " + iconHeaderName, Toast.LENGTH_SHORT).show();
            }
        });

        ImageView homeicon = view.findViewById(R.id.imageicon);
        Drawable drawable = (homeItemList.get(position)).getItemIcon();
        homeicon.setImageDrawable(drawable);
        TextView hometext = view.findViewById(R.id.hometext);
        hometext.setText(homeItemList.get(position).getItemName());

        TextView count_data_captured = view.findViewById(R.id.count_data_captured);
        if(homeItemList.get(position).getCountValue() != null){
            if(homeItemList.get(position).getCountValue() > 0) {
                count_data_captured.setText(String.valueOf(homeItemList.get(position).getCountValue()));
                count_data_captured.setVisibility(View.VISIBLE);
            }else{
                count_data_captured.setVisibility(View.GONE);
            }
        }else{
            count_data_captured.setVisibility(View.GONE);
        }


        return view;
    }
}
