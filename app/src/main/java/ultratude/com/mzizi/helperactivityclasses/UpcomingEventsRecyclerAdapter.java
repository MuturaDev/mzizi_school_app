package ultratude.com.mzizi.helperactivityclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.github.loadingview.LoadingView;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalEvents;

/**
 * Created by James on 21/07/2018.
 */

public class UpcomingEventsRecyclerAdapter extends RecyclerView.Adapter<UpcomingEventsRecyclerAdapter.ViewHolder>{



    List<PortalEvents> portalEventsList;
    private Context context;


    public UpcomingEventsRecyclerAdapter(Context context, List<PortalEvents> portalEvents) {
        this.portalEventsList = portalEvents;
        this.context = context;
    }

    @Override
    //this inflates each individual layout in the recycleview
    //binds the layout file
    public UpcomingEventsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //you need to get the view and then return it, a new view holder object
        View view = LayoutInflater.from(context).inflate(R.layout.upcoming_events_item_layout, parent, false);
        return new UpcomingEventsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    //here we bind the data to each one of the individual list items
    //this is where all the widgets and the actual data is going to be attached to each individual list item
    public void onBindViewHolder(UpcomingEventsRecyclerAdapter.ViewHolder holder, int position) {
        PortalEvents portalEvents = portalEventsList.get(position);

        holder.bind(portalEvents);


    }


    @Override
    public int getItemCount() {
        return portalEventsList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title_text, startdate_text, enddate_text,starttime_text,endtime_text,description_text,venue_text;
        private CardView add_to_calendar;
        private ImageView imageView;
        private TextView textView;
        private View view;

        public ViewHolder(final View itemView){
            super(itemView);

            title_text =  itemView.findViewById(R.id.title_text);
            startdate_text =  itemView.findViewById(R.id.startdate_text);
            enddate_text =  itemView.findViewById(R.id.enddate_text);
            starttime_text =  itemView.findViewById(R.id.starttime_text);
            endtime_text =  itemView.findViewById(R.id.endtime_text);
            venue_text = itemView.findViewById(R.id.venue_text);
            description_text =  itemView.findViewById(R.id.description_text);
            add_to_calendar = itemView.findViewById(R.id.add_to_calendar);
            imageView =  itemView.findViewById(R.id.icon);
             textView = itemView.findViewById(R.id.add_to_calendar_text);
            add_to_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(textView.getText().equals("Added to Calendar")) {
                        FancyToast.makeText(context, "Already added to Calendar!", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();
                    }else {



                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        dialog.setCancelable(false);
                       view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_layout, null, false);
                        LoadingView loading = view.findViewById(R.id.loadingView);

                        loading.start();
                        dialog.setView(view);
                        final AlertDialog alert = dialog.create();
                        // alert.getWindow().setGravity(Gravity.BOTTOM);
                        // alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        AsyncTask asyncTask = new AsyncTask() {
                            @Override
                            protected void onPreExecute() {
                                alert.show();

                                super.onPreExecute();
                            }

                            @Override
                            protected void onPostExecute(Object o) {

                                imageView.setImageDrawable(context.getDrawable(R.drawable.event_in_calendar));
//                                LottieAnimationView lottie = view.findViewById(R.id.animation_view);
//                                lottie.playAnimation();
//                                lottie.setVisibility(View.VISIBLE);
                                view.findViewById(R.id.image).setVisibility(View.VISIBLE);
                                view.findViewById(R.id.loadingView).setVisibility(View.GONE);
                               TextView textView1 =  view.findViewById(R.id.dialog_text);
                               textView1.setText( title_text.getText().toString() + " event was added to your Calendar successfully");
                                textView.setText("Added to Calendar");

                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       alert.cancel();
                                   }
                               }, 2000);

                                super.onPostExecute(o);
                            }

                            @Override
                            protected Object doInBackground(Object[] objects) {

                                try {
                                    Thread.sleep(10000);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                                return null;
                            }
                        };
                        asyncTask.execute();
                    }

                }
            });



        }


        void bind(PortalEvents portalEvents){
            venue_text.setText(portalEvents.getVenue());
            title_text.setText(portalEvents.getEventsTitle());
            startdate_text.setText(portalEvents.getStartDate());
            enddate_text.setText(portalEvents.getEndDate());
            starttime_text.setText(portalEvents.getStartTime());
            endtime_text.setText(portalEvents.getEndTime());
            description_text.setText(portalEvents.getDescription());

        }
    }




}
