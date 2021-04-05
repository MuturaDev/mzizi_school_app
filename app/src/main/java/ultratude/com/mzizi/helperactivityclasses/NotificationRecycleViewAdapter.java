package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.notificationpg.NotificationModel.Notification;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.uiactivities.PortalChatsFragment;
import ultratude.com.mzizi.uiactivities.DiaryFragment;
import ultratude.com.mzizi.uiactivities.FeeBalanceFrag;
import ultratude.com.mzizi.uiactivities.FloatingActionShow;
import ultratude.com.mzizi.uiactivities.FragTransaction;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.MeanScoreFrag;
import ultratude.com.mzizi.uiactivities.UpcomingEventsFrag;
//import ultratude.com.mzizi.RoomDatabaseClasses.RoomModel.Notification;

/**
 * Created by Admin on 5/19/2018.
 */

public class NotificationRecycleViewAdapter extends RecyclerView.Adapter<NotificationRecycleViewAdapter.ViewHolder> {


    List<Notification> notificationList;
    private Context context;


    public NotificationRecycleViewAdapter(Context context, List<Notification> notificationList) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @Override
    //this inflates each individual layout in the recycleview
    //binds the layout file
    public NotificationRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //you need to get the view and then return it, a new view holder object
        View view = LayoutInflater.from(context).inflate(R.layout.notification_messages_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //here we bind the data to each one of the individual list items
    //this is where all the widgets and the actual data is going to be attached to each individual list item
    public void onBindViewHolder(final NotificationRecycleViewAdapter.ViewHolder holder, final int position) {
//        Glide.with(context)
//                .asBitmap()
//                .load(picturesUrls.get(position))
//                .into(holder.senderPicture)


        if(notificationList.get(position).getMsg().contains("event") || notificationList.get(position).getMsg().contains("Event")){
            holder.view_text_id.startAnimation(holder.anim );
            holder.view_text_id.setVisibility(View.VISIBLE);
        }else if(notificationList.get(position).getMsg().contains("Results") || notificationList.get(position).getMsg().contains("term") ||
                notificationList.get(position).getMsg().contains("results") || notificationList.get(position).getMsg().contains("Term")){
            holder.view_text_id.startAnimation(holder.anim );
            holder.view_text_id.setVisibility(View.VISIBLE);
        } else if (notificationList.get(position).getMsg().contains("receipt") || notificationList.get(position).getMsg().contains("transaction") ||
                notificationList.get(position).getMsg().contains("Receipt") || notificationList.get(position).getMsg().contains("Transaction")) {
            holder.view_text_id.startAnimation(holder.anim );
            holder.view_text_id.setVisibility(View.VISIBLE);
        }else if(notificationList.get(position).getMsg().contains("Chat") || notificationList.get(position).getMsg().contains("chat")){
            holder.view_text_id.startAnimation(holder.anim );
            holder.view_text_id.setVisibility(View.VISIBLE);
        }else if(notificationList.get(position).getMsg().contains("New diary entry")){
            holder.view_text_id.startAnimation(holder.anim);
            holder.view_text_id.setVisibility(View.VISIBLE);
        }


        if(notificationList.get(position).isRead()){
            holder.txt_status_notification_read_not_read_ID.setBackground(context.getResources().getDrawable(R.drawable.notification_read_bg));
        }else{
            holder.txt_status_notification_read_not_read_ID.setBackground(context.getResources().getDrawable(R.drawable.notification_not_read_bg));
        }
        //this is where you should do the changes.
        holder.senderPicture.setImageResource(R.drawable.message_from_icon);
        holder.senderText.setText(notificationList.get(position).getMsg());

        // String st = String.format("",new Date());
        holder.timetag.setText(notificationList.get(position).getDateSent());
        holder.senderText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                if(notificationList.get(position).getMsg().contains("event") || notificationList.get(position).getMsg().contains("Event")){


                    FragTransaction.dislayFragment(UpcomingEventsFrag.class,"",  ((MainActivity) context).fragmentManager);
                    //( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
                   // FragTransaction.setNotificationCount(notification_count_id.getText().toString());
                    FloatingActionShow.showFloat(false);
                    FloatingActionShow.showSchoolChat(false);

                }else if(notificationList.get(position).getMsg().contains("Results") || notificationList.get(position).getMsg().contains("term") ||
                        notificationList.get(position).getMsg().contains("results") || notificationList.get(position).getMsg().contains("Term")){
                    FragTransaction.dislayFragment(MeanScoreFrag.class,"", ((MainActivity) context).fragmentManager);
                    //( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
                    //FragTransaction.setNotificationCount(notification_count_id.getText().toString());
                    FloatingActionShow.showFloat(false);
                    FloatingActionShow.showSchoolChat(false);
                } else if (notificationList.get(position).getMsg().contains("receipt") || notificationList.get(position).getMsg().contains("transaction") ||
                        notificationList.get(position).getMsg().contains("Receipt") || notificationList.get(position).getMsg().contains("Transaction") ||
                        notificationList.get(position).getMsg().contains("Balance") || notificationList.get(position).getMsg().contains("balance")){
                    FragTransaction.dislayFragment(FeeBalanceFrag.class,"", ((MainActivity) context).fragmentManager);
                    //( (MainActivity) getActivity()).floatbutton.setVisibility(View.INVISIBLE);
                    //FragTransaction.setNotificationCount(notification_count_id.getText().toString());
                    FloatingActionShow.showFloat(false);
                    FloatingActionShow.showSchoolChat(false);

                }else if(notificationList.get(position).getMsg().contains("Chat") || notificationList.get(position).getMsg().contains("chat")){
//                    Intent intent = new Intent(((MainActivity) context), PortalChatsFragment.class);
//                    ((MainActivity) context).startActivity(intent);
                    FragTransaction.dislayFragment(PortalChatsFragment.class, "",((MainActivity)context).fragmentManager);
                }else if(notificationList.get(position).getMsg().contains("New diary entry:")){
                    FragTransaction.dislayFragment(DiaryFragment.class, notificationList.get(position).getMsg().replace("New diary entry:", "") + "Highlight", ((MainActivity) context).fragmentManager);
                }

             //   Toast.makeText(context, "This message is clicked", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{
        //should be an circleimageview
        ImageView senderPicture;

        TextView senderText;
        TextView timetag;
        TextView view_text_id;
        private Animation anim;

        private TextView txt_status_notification_read_not_read_ID;

        public ViewHolder(View itemView){
            super(itemView);
            senderPicture =  itemView.findViewById(R.id.sendPicture);
            txt_status_notification_read_not_read_ID = itemView.findViewById(R.id.txt_status_notification_read_not_read_ID);
            senderText =  itemView.findViewById(R.id.senderText);
            timetag = itemView.findViewById(R.id.timetag);
            view_text_id = itemView.findViewById(R.id.view_text_id);
            anim = AnimationUtils.loadAnimation(context, R.anim.anim_blink);

        }
    }


}
