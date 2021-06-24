package ultratude.com.mzizi.uiactivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.HomeItem;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;
import ultratude.com.mzizi.tableviewimplementation.tableviewexample.OrderItemsFragment2;

public class HomeMoreIconsFragment extends Fragment {

    public HomeMoreIconsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_more_icons_fragments_layout, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        addFloatingActionMenuButtons(view);

        super.onViewCreated(view, savedInstanceState);
    }



    private void addFloatingActionMenuButtons(final View view){



        AsyncTask asyncTask  = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {


                GridView gridView = view.findViewById(R.id.gridview);


                final  List<HomeItem> homeItemList = new ArrayList<>();


                Map<String, GlobalSettings> map = (Map<String,GlobalSettings>)o;
                if(map.get(Constants.OPTIONAL_FEE_ENABLED) != null){
                    if(map.get(Constants.OPTIONAL_FEE_ENABLED).getGlobalSettingsValue().equalsIgnoreCase("YES")){
                        HomeItem homeItem1 = new HomeItem(getResources().getDrawable(R.drawable.optional_fee_icon), "Optional Fees", true);
                        homeItemList.add(homeItem1);
                    }
                }

                if(map.get(Constants.TRANSPORT_FEE_ENABLED) != null){
                    if(map.get(Constants.TRANSPORT_FEE_ENABLED).getGlobalSettingsValue().equalsIgnoreCase("YES")){
                        HomeItem homeItem2 = new HomeItem(getResources().getDrawable(R.drawable.transport_route_icon), "Transport Routes", true);
                        homeItemList.add(homeItem2);
                    }
                }

                HomeItem homeItem3 = new HomeItem(getResources().getDrawable(R.drawable.media_gallery_icon), "Media Gallery", true);
                homeItemList.add(homeItem3);


                HomeItem homeItem4 = new HomeItem(getResources().getDrawable(R.drawable.order_items), "Order Items", true);
                homeItemList.add(homeItem4);

                HomeItem homeItem5 = new HomeItem(getResources().getDrawable(R.drawable.parent_portal), "Parent Web Portal", true);
                homeItemList.add(homeItem5);

                HomeItem homeItem6 = new HomeItem(getResources().getDrawable(R.drawable.settings_icon), "Settings", true);
                homeItemList.add(homeItem6);

                HomeItem homeItem7 = new HomeItem(getResources().getDrawable(R.drawable.borrowed_books_icon), "Borrowed Books", true);
                homeItemList.add(homeItem7);

                HomeItem homeItem8 = new HomeItem(getResources().getDrawable(R.drawable.school_trip), "School Trip", true);
                homeItemList.add(homeItem8);

                HomeItem homeItem9 = new HomeItem(getResources().getDrawable(R.drawable.moodle_icon), "Moodle", true);
                homeItemList.add(homeItem9);





                GenericHomeGridUIAdapter adapter2 = new GenericHomeGridUIAdapter(getActivity(),removeUnVisibleItem(homeItemList));
                gridView.setAdapter(adapter2);




//                LinearLayout media_gallery = getActivity().findViewById(R.id.media_gallery_CardID);
//                media_gallery.setOnClickListener(HomeMoreIconsFragment.this);
//                //TODO: GONE FOR NOW BUT WILL BE DONE LATER, WHEN I COMPLETELY UNDERSTAND WHAT I WANT
//                // media_gallery.setVisibility(View.GONE);
//
//                LinearLayout order_items_btn = getActivity().findViewById(R.id.order_items_CardID);
//                order_items_btn.setOnClickListener(HomeMoreIconsFragment.this);
//
//                LinearLayout optionalFee = getActivity().findViewById(R.id.optional_fee_Card);
//                optionalFee.setOnClickListener(HomeMoreIconsFragment.this);
//
//                LinearLayout transportRoutes = getActivity().findViewById(R.id.transport_CardID);
//                transportRoutes.setOnClickListener(HomeMoreIconsFragment.this);
//
//                Map<String, GlobalSettings> map = (Map<String,GlobalSettings>)o;
//                if(map.get(Constants.OPTIONAL_FEE_ENABLED) != null){
//                    if(map.get(Constants.OPTIONAL_FEE_ENABLED).getGlobalSettingsValue().equalsIgnoreCase("YES")){
//                        optionalFee.setVisibility(View.VISIBLE);
//                    }else{
//                        optionalFee.setVisibility(View.GONE);
//                    }
//                }else{
//                    optionalFee.setVisibility(View.GONE);
//                }
//
//                if(map.get(Constants.TRANSPORT_FEE_ENABLED) != null){
//                    if(map.get(Constants.TRANSPORT_FEE_ENABLED).getGlobalSettingsValue().equalsIgnoreCase("YES")){
//                        transportRoutes.setVisibility(View.VISIBLE);
//                    }else{
//                        transportRoutes.setVisibility(View.GONE);
//                    }
//                }else{
//                    transportRoutes.setVisibility(View.GONE);
//                }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                Map<String, Object> map = new HashMap<>();

                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }

                if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.OPTIONAL_FEE_ENABLED,Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.OPTIONAL_FEE_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.OPTIONAL_FEE_ENABLED,Integer.valueOf(studentid)).get(0));
                }

                if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.TRANSPORT_FEE_ENABLED,Integer.valueOf(studentid)).size() > 0){
                    map.put(Constants.TRANSPORT_FEE_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.TRANSPORT_FEE_ENABLED,Integer.valueOf(studentid)).get(0));

                }

                return map;
            }
        };
        asyncTask.execute();


    }


    List<HomeItem> removeUnVisibleItem(List<HomeItem> itemList){
        List<HomeItem> itemList1 = new ArrayList<>();
        for(HomeItem item : itemList){
            if(item.isItemVisible()){
                itemList1.add(item);
            }
        }

        return itemList1;
    }




    private void changeToScreen(String screenName, FragmentManager fragmentManager){

        switch(screenName){

            case "Transport Routes":
                FragTransaction.dislayFragment(TransportRoutesFragment.class,  "", fragmentManager);
                break;
            case "Optional Fees":
                FragTransaction.dislayFragment(OptionalFeesFragment.class,   "", fragmentManager);
                break;

            case "Media Gallery":
                Intent intent3 = new Intent( getActivity(), GalleryActivity.class);
                getActivity().startActivity(intent3);

                break;
            case "Order Items":
                FragTransaction.dislayFragment(OrderItemsFragment2.class, "", fragmentManager);
                break;
            case "Parent Web Portal":
                startActivity(new Intent(getActivity(), MziziParentWebPortal.class));
                break;
            case "Settings":
                FragTransaction.dislayFragment(SettingsFragment.class, "",fragmentManager);
                break;
            case "Borrowed Books":
                FragTransaction.dislayFragment(BorrowedBooksFragment.class, "",fragmentManager);
                break;
            case "School Trip":
                FragTransaction.dislayFragment(SchoolTripFragment.class, "",fragmentManager);
                break;
            case "Moodle" :
                //String url =”selphone://post_detail?post_id=10";

                try{
                    String url = "moodlemobile://link=https://lms.mzizi.co.ke";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }catch (Exception ex){
                   // Toast.makeText(getActivity(), "Sorry, Kindly install the Moodle Mobile App", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Not Found")
                            .setMessage("Moodle App is not available, you can click Install")
                            .setPositiveButton("Install", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.moodle.moodlemobile")));
                                }
                            })
                            .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }

                break;

        }

    }



    private class GenericHomeGridUIAdapter extends BaseAdapter {
        private Context mContext;
        private List<HomeItem>  homeItemList;

        public GenericHomeGridUIAdapter(Context mContext, List<HomeItem> homeitemList){
            this.mContext = mContext;
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

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.home_more_icons_fragments_item_layout, null, false);
            LinearLayout homeItemID = view.findViewById(R.id.item_layout_click);

            homeItemID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String iconHeaderName = homeItemList.get(position).getItemName();

                    changeToScreen(iconHeaderName, ((MainActivity) getActivity()).fragmentManager);

                    //Toast.makeText(getActivity(), "You Clicked on: " + homeItemList.get(position).getItemName(), Toast.LENGTH_SHORT).show();
                }
            });

            ImageView homeicon = view.findViewById(R.id.imageicon);
            Drawable drawable = (homeItemList.get(position)).getItemIcon();
            homeicon.setImageDrawable(drawable);
            TextView hometext = view.findViewById(R.id.hometext);
            hometext.setText(homeItemList.get(position).getItemName());


            return view;
        }
    }
}
