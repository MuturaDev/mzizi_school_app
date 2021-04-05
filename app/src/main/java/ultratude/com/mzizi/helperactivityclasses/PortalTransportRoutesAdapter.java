package ultratude.com.mzizi.helperactivityclasses;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalTransportRoutesResponse;
import ultratude.com.mzizi.modelclasses.TransportRoutesRequest;

public class PortalTransportRoutesAdapter  extends RecyclerView.Adapter<PortalTransportRoutesAdapter.ViewHolder> {

    private List<PortalTransportRoutesResponse> portalTransportRoutesResponseList;
    private Context context;

    public PortalTransportRoutesAdapter(List<PortalTransportRoutesResponse> portalTransportRoutesResponseList, Context context){
        this.portalTransportRoutesResponseList = portalTransportRoutesResponseList;
        this.context = context;
    }


    @Override
    public PortalTransportRoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.transport_routes_item_layout_frag, parent, false);
        return new PortalTransportRoutesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PortalTransportRoutesAdapter.ViewHolder holder, final int position) {
        holder.bind(portalTransportRoutesResponseList.get(position));

    }


    @Override
    public int getItemCount() {
        return portalTransportRoutesResponseList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView route_name, route_amount,action_btn_text;
        RelativeLayout action_btn;
        LinearLayout tranport_route_progress;



        public ViewHolder(View itemView){
            super(itemView);

            route_name = itemView.findViewById(R.id.route_name);
            route_amount = itemView.findViewById(R.id.route_amount);
            action_btn_text = itemView.findViewById(R.id.action_btn_text);
            tranport_route_progress = itemView.findViewById(R.id.tranport_route_progress_inner);
            action_btn = itemView.findViewById(R.id.action_btn);
            action_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AsyncTask asyncTask = new AsyncTask() {

                        @Override
                        protected void onPreExecute() {
                            showProgress(true);
                            super.onPreExecute();

                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            showProgress(false);
                            action_btn.setBackgroundColor(context.getResources().getColor(R.color.homeText));
                            action_btn_text.setText("Successful Registration");
                            super.onPostExecute(o);
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {

                            try{
                                Thread.sleep(3000);
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }

                            return null;
                        }
                    };
                    asyncTask.execute();

                    TransportRoutesRequest request = new TransportRoutesRequest(
                            "",
                            "",
                            "",
                            "",
                            ""

                    );

                }
            });

        }


        public void bind(PortalTransportRoutesResponse response){

            route_name.setText(response.getDisplayName());
            route_amount.setText("Ksh. " + response.getTranAmount() + "/-");

            if(response.getRouteUrlDescription().equalsIgnoreCase("This is your current route - click to remove"))
                action_btn.setBackgroundColor(context.getColor(R.color.red));

            action_btn_text.setText(response.getRouteUrlDescription());



        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        public void showProgress(final boolean show) {

            // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
            // for very easy animations. If available, use these APIs to fade-in
            // the progress spinner.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

                int shortAnimTime = 0;

                    shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);


                action_btn_text.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        action_btn_text.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                    }
                });



                tranport_route_progress.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        tranport_route_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {

                tranport_route_progress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

                action_btn_text.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        }




    }

}