package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
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
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalDetailedTransaction;
import ultratude.com.mzizi.uiactivities.FeeBalanceFrag;

/**
 * Created by James on 24/07/2018.
 */

public class FeeBalanceRecyclerViewAdapter extends RecyclerView.Adapter<FeeBalanceRecyclerViewAdapter.ViewHolder>{




    private List<PortalDetailedTransaction> portalEventsList;
    private Context context;


    private FeeBalanceFrag fragmentFeeBalance;

//    private static class TransTypeEnum{
//        enum TransTypeDescriptions{
//            RECEIPT, INVOICE
//        }
//    }




    public FeeBalanceRecyclerViewAdapter(Context context, FeeBalanceFrag fragment, List<PortalDetailedTransaction> portalEventsList) {
        this.portalEventsList = portalEventsList;
        this.context = context;
        fragmentFeeBalance = fragment;



    }

    @Override
    //this inflates each individual layout in the recycleview
    //binds the layout file
    public FeeBalanceRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //you need to get the view and then return it, a new view holder object
        View view = LayoutInflater.from(context).inflate(R.layout.fee_balance_item_layout, parent, false);
        return new FeeBalanceRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    //here we bind the data to each one of the individual list items
    //this is where all the widgets and the actual data is going to be attached to each individual list item
    public void onBindViewHolder(FeeBalanceRecyclerViewAdapter.ViewHolder holder, int position) {
        PortalDetailedTransaction portalDetailedTransaction = portalEventsList.get(position);

        holder.bind(portalDetailedTransaction);
    }


    @Override
    public int getItemCount() {
        return portalEventsList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView tranType_refNo_textID,transType_refNo_txtID, postingDate_textID, narration_textID, amount_textID, runningBalance_textID,label_receipt_or_invoice_ID;

        public ViewHolder(View itemView){
            super(itemView);


            tranType_refNo_textID = itemView.findViewById(R.id.tranType_refNo_textID);
            transType_refNo_txtID = itemView.findViewById(R.id.transType_refNo_txtID);//REFNO
            postingDate_textID = itemView.findViewById(R.id.postingDate_textID);
            narration_textID = itemView.findViewById(R.id.narration_textID);
            amount_textID = itemView.findViewById(R.id.amount_textID);
            runningBalance_textID = itemView.findViewById(R.id.runningBalance_textID);
            label_receipt_or_invoice_ID = itemView.findViewById(R.id.label_receipt_or_invoice_ID);

        }

        void bind(PortalDetailedTransaction portalDetailedTransaction){
                NumberFormat myFormat = NumberFormat.getInstance();
                myFormat.setGroupingUsed(true);

               // tranType_refNo_textID.setText(portalDetailedTransaction.getTranType());


//                String transText = (portalDetailedTransaction.getTransType().equalsIgnoreCase("2") ) ? "RECEIPT" : (portalDetailedTransaction.getTransType().equalsIgnoreCase("0") ) ? "INVOICE" : "";
//                // Toast.makeText(context, transText.toString(), Toast.LENGTH_LONG).show();
//                transType_refNo_txtID.setText(transText);
                transType_refNo_txtID.setText(portalDetailedTransaction.getTranType());
                //transType_refNo_txtID.setText(portalDetailedTransaction.getTransType());
               Drawable  drawable = ((portalDetailedTransaction.getTransType().equalsIgnoreCase("2")) ?  context.getResources().getDrawable(R.drawable.transtype_green_bg) : (portalDetailedTransaction.getTransType().equalsIgnoreCase("0")) ?  context.getResources().getDrawable(R.drawable.transtype_orange_bg) : null);
                if(drawable != null) {
                    transType_refNo_txtID.setBackgroundDrawable(drawable);
                }

                String textLabel = ((portalDetailedTransaction.getTransType().equalsIgnoreCase("2")) ?  "Receipt Date" : (portalDetailedTransaction.getTransType().equalsIgnoreCase("0")) ? "Invoice Date" : "");

            if(textLabel != null){
                label_receipt_or_invoice_ID.setText(textLabel);
            }

                //postingDate_textID.setText(portalDetailedTransaction.getDatePosted().replace("12:00:00", "").replace("AM","").replace("PM", ""));

                postingDate_textID.setText(UtilityFunctions.convertDateToMziziDisplayDate(portalDetailedTransaction.getDatePosted()));


                narration_textID.setText(portalDetailedTransaction.getRefNo());

                amount_textID.setText(myFormat.format(Double.parseDouble(portalDetailedTransaction.getTranAmount())));
                runningBalance_textID.setText(myFormat.format(Double.parseDouble(portalDetailedTransaction.getBalAmount())));


           fragmentFeeBalance.showProgress(false);




        }
    }







}
