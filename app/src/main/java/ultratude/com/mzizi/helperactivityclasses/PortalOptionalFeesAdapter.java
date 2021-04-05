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
import ultratude.com.mzizi.modelclasses.PortalOptionalFeeRequest;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalOptionalFeesResponse;


public class PortalOptionalFeesAdapter  extends RecyclerView.Adapter<PortalOptionalFeesAdapter.ViewHolder> {

    private List<PortalOptionalFeesResponse> portalOptionalFeesResponseList;
    private Context context;

    public PortalOptionalFeesAdapter(List<PortalOptionalFeesResponse> portalOptionalFeesResponseList, Context context){
        this.portalOptionalFeesResponseList = portalOptionalFeesResponseList;
        this.context = context;
    }


    @Override
    public PortalOptionalFeesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.optional_fees_item_layout_frag, parent, false);
        return new PortalOptionalFeesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PortalOptionalFeesAdapter.ViewHolder holder, final int position) {
        holder.bind(portalOptionalFeesResponseList.get(position));

    }


    @Override
    public int getItemCount() {
        return portalOptionalFeesResponseList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView term_name,class_name,fee_name,current_status,fee_amount,action_btn_text;
        PortalOptionalFeesResponse portalOptionalFeesResponse;
        LinearLayout horizontal_progress;
        RelativeLayout action_btn;

        public ViewHolder(View itemView){
            super(itemView);

            horizontal_progress = itemView.findViewById(R.id.horizontal_progress);
            term_name = itemView.findViewById(R.id.term_name);
            class_name = itemView.findViewById(R.id.class_name);
            fee_name = itemView.findViewById(R.id.fee_name);
            current_status = itemView.findViewById(R.id.current_status);
            fee_amount = itemView.findViewById(R.id.fee_amount);
            action_btn_text = itemView.findViewById(R.id.action_btn_text);
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

                    PortalOptionalFeeRequest request = new PortalOptionalFeeRequest(
                            portalOptionalFeesResponse.getFeeExemptions_f(),
                            portalOptionalFeesResponse.getFeeExemptions_i(),
                            "",
                            ""

                    );

                }
            });


        }


        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        public void showProgress(final boolean show) {

            // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
            // for very easy animations. If available, use these APIs to fade-in
            // the progress spinner.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

                int shortAnimTime = 0;


                action_btn_text.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        action_btn_text.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
                    }
                });



                horizontal_progress.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        horizontal_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {

                horizontal_progress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

                action_btn_text.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
            }
        }





        public void bind(PortalOptionalFeesResponse portalOptionalFeesResponse){
            this.portalOptionalFeesResponse = portalOptionalFeesResponse;
                term_name.setText(portalOptionalFeesResponse.getTerm());
                class_name.setText(portalOptionalFeesResponse.getClassName());
                fee_name.setText(portalOptionalFeesResponse.getFeeName());
                current_status.setText(portalOptionalFeesResponse.getCurrentStatus());
                fee_amount.setText("Ksh. " + portalOptionalFeesResponse.getFeeAmount() + "/-");
                if(portalOptionalFeesResponse.getIsTakenUrlText().equalsIgnoreCase("Remove Fee"))
                    action_btn.setBackgroundColor(context.getColor(R.color.red));



            action_btn_text.setText(portalOptionalFeesResponse.getIsTakenUrlText());



        }

    }




}