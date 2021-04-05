package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalContacts;

/**
 * Created by James on 01/08/2018.
 */

public class SchoolContactsRecycler  extends RecyclerView.Adapter<SchoolContactsRecycler.ViewHolder> {


    List<PortalContacts> portalContactsList;
    private Context context;


    public SchoolContactsRecycler(Context context, List<PortalContacts> portalContactsList) {
        this.portalContactsList = portalContactsList;
        this.context = context;
    }

    @Override
    //this inflates each individual layout in the recycleview
    //binds the layout file
    public SchoolContactsRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //you need to get the view and then return it, a new view holder object
        View view = LayoutInflater.from(context).inflate(R.layout.school_contacts_item_layout, parent, false);
        return new SchoolContactsRecycler.ViewHolder(view);
    }

    @Override
    //here we bind the data to each one of the individual list items
    //this is where all the widgets and the actual data is going to be attached to each individual list item
    public void onBindViewHolder(SchoolContactsRecycler.ViewHolder holder, int position) {

        PortalContacts contact = portalContactsList.get(position);




        // String st = String.format("",new Date());
        holder.settingValue.setText(contact.getSettingsValue());
        holder.settingName.setText(contact.getSettingsName());

    }


    @Override
    public int getItemCount() {
        return portalContactsList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView settingName;
        TextView settingValue;

        public ViewHolder(View itemView){
            super(itemView);
            settingName =  itemView.findViewById(R.id.school_contacts_setting_name);
            settingValue =  itemView.findViewById(R.id.school_contacts_setting_value);


        }
    }


}
