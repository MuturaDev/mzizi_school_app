package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ultratude.com.mzizi.R;

/**
 * Created by Admin on 5/31/2018.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    //i will loop through the SharedPreference or list containing the pass and just sent message



    private ArrayList<Integer> picturesUrls = new ArrayList<>();
    private ArrayList<String> text = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();


    private Context context;


    public ChatRecyclerViewAdapter(Context context, ArrayList<Object> list) {

        if(list != null){

        }
        this.context = context;
    }

    @Override
    //this inflates each individual layout in the recycleview
    //binds the layout file
    public ChatRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //you need to get the view and then return it, a new view holder object
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //here we bind the data to each one of the individual list items
    //this is where all the widgets and the actual data is going to be attached to each individual list item
    public void onBindViewHolder(ChatRecyclerViewAdapter.ViewHolder holder, int position) {
//        Glide.with(context)
//                .asBitmap()
//                .load(picturesUrls.get(position))
//                .into(holder.senderPicture)

        holder.senderPicture.setImageResource(picturesUrls.get(position));
        holder.senderText.setText(text.get(position));

        // String st = String.format("",new Date());
        holder.timetag.setText(time.get(position));
        holder.senderText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(context, "This message is clicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return text.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{
        //should be an circleimageview
        ImageView senderPicture;

        TextView senderText;
        TextView timetag;

        public ViewHolder(View itemView){
            super(itemView);
            senderPicture = (ImageView) itemView.findViewById(R.id.sendPicture);

            senderText = (TextView) itemView.findViewById(R.id.senderText);
            timetag = (TextView) itemView.findViewById(R.id.timetag);

        }
    }


}

