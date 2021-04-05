package ultratude.com.staff.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import ultratude.com.staff.R;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;

/**
 * Created by James on 12/01/2019.
 */

public class DutyRosterRecyclerAdapter  extends RecyclerView.Adapter<DutyRosterRecyclerAdapter.ViewHolder> {

    private List<DutyRoster> dutyRosterList;
    private Context mContext;

    public DutyRosterRecyclerAdapter(Context mContext, List<DutyRoster> dutyRosterList){
        this.mContext = mContext;
        this.dutyRosterList = dutyRosterList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_staffFullName, txt_termName, txt_week, txt_year;
        private LinearLayout layout_make_call;
        private TextView staff_phoneNumber;


        public ViewHolder(View view){
            super(view);

            layout_make_call = view.findViewById(R.id.layout_click_to_make_call_ID);
            staff_phoneNumber = view.findViewById(R.id.txt_staff_phoneNumber_ID);

            txt_staffFullName = view.findViewById(R.id.txt_staffName_ID);
            txt_termName = view.findViewById(R.id.txt_termName_ID);
            txt_week = view.findViewById(R.id.txt_week_ID);
            txt_year = view.findViewById(R.id.txt_year_ID);
        }


        void bind(DutyRoster dutyRoster){
            txt_staffFullName.setText(dutyRoster.getStaffName());
            txt_termName.setText(dutyRoster.getTermName());
            txt_week.setText( "Week "+ dutyRoster.getDutyWeek());
            txt_year.setText(dutyRoster.getYear());

            staff_phoneNumber.setText(dutyRoster.getPhoneNumber());
            layout_make_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //MAKE CALL
                    String callThisPhoneNumber =  staff_phoneNumber.getText().toString();

                    String uri = "tel: " + callThisPhoneNumber.trim() ;

                    try {
                        //Intent intent = new Intent(Intent.ACTION_CALL);//
                        //does not require CALL_PHONE permission
                        Intent intent = new Intent(Intent.ACTION_DIAL);//show the dailer with the number already entered, but allows the user to decide  whether to actually make a call or not
                        intent.setData(Uri.parse(uri));
                        mContext.getApplicationContext().startActivity(intent);
                    }
                    catch (android.content.ActivityNotFoundException e){
                        FancyToast.makeText(mContext, "App Failed", FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                       // Toast.makeText(mContext,"App failed",Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dutyroster_item_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dutyRosterList.get(position));
    }


    @Override
    public int getItemCount() {
        return dutyRosterList.size();
    }
}
