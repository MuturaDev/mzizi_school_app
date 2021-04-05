package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalRecentTransactionResponse;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.ViewHolder> {

    private List<PortalRecentTransactionResponse> recentTransactionResponseList;
    private Context context;

    public RecentTransactionAdapter(List<PortalRecentTransactionResponse> recentTransactionResponseList, Context context){
        this.recentTransactionResponseList = recentTransactionResponseList;
        this.context = context;
    }


    @Override
    public RecentTransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_recent_transaction_item, parent, false);
        return new RecentTransactionAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecentTransactionAdapter.ViewHolder holder, final int position) {
        holder.bind(recentTransactionResponseList.get(position));
    }


    @Override
    public int getItemCount() {
        return recentTransactionResponseList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView date_text,item_text,amount_text,runningBalance_text;

        public ViewHolder(View itemView){
            super(itemView);
            date_text = itemView.findViewById(R.id.date_text);
            item_text = itemView.findViewById(R.id.item_text);
            amount_text = itemView.findViewById(R.id.amount_text);
            runningBalance_text = itemView.findViewById(R.id.runningBalance_text);

        }

       public void bind(PortalRecentTransactionResponse recentTransaction){

           NumberFormat myFormat = NumberFormat.getInstance();
           myFormat.setGroupingUsed(true);
           item_text.setText(recentTransaction.getRefNo());

           if(!recentTransaction.getTransType().isEmpty()){
               Drawable drawable = ((recentTransaction.getTransType().equalsIgnoreCase("2")) ?  context.getResources().getDrawable(R.drawable.transtype_green_bg) : (recentTransaction.getTransType().equalsIgnoreCase("0")) ?  context.getResources().getDrawable(R.drawable.transtype_orange_bg) : null);
               if(drawable != null) {
                   runningBalance_text.setBackgroundDrawable(drawable);
               }
           }else{
               Drawable drawable1 = ((recentTransaction.getTranType().contains("INV")) ? context.getResources().getDrawable(R.drawable.transtype_orange_bg) : context.getResources().getDrawable(R.drawable.transtype_green_bg));
               if(drawable1 != null){
                   runningBalance_text.setBackgroundDrawable(drawable1);
               }
           }


          // date_text.setText(recentTransaction.getTranDate().replace("12:00:00", "").replace("AM","").replace("PM", ""));
            date_text.setText(UtilityFunctions.convertDateToMziziDisplayDate(recentTransaction.getTranDate()));

           amount_text.setText(myFormat.format(Double.parseDouble(recentTransaction.getTranAmount())));
           runningBalance_text.setText(myFormat.format(Double.parseDouble(recentTransaction.getBalAmount())));

        }

    }

}
