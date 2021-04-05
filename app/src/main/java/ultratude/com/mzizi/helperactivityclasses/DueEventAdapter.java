package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.DueEvents;

public class DueEventAdapter extends RecyclerView.Adapter<DueEventAdapter.ViewHolder> {

    private List<DueEvents> dueEventsList;
    private Context context;

    public DueEventAdapter(List<DueEvents> dueEventsList, Context context){
        this.dueEventsList = dueEventsList;
        this.context = context;
    }


    @Override
    public DueEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.due_events_item_layout, parent, false);
        return new DueEventAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final DueEventAdapter.ViewHolder holder, final int position) {
        holder.bind(dueEventsList.get(position));
    }


    @Override
    public int getItemCount() {
        return dueEventsList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView dueTitle, dueDate;

        public ViewHolder(View itemView){
            super(itemView);
            dueTitle = itemView.findViewById(R.id.dueTitle);
            dueDate = itemView.findViewById(R.id.dueDate);
        }


        //TODO: For days remaining have the record highlighted in a different color, date should be today
        public void bind(DueEvents dueEvents){
            dueTitle.setText(dueEvents.getDueTitle());
            //TODO: format date
            dueDate.setText(dueEvents.getDueDate());

        }

    }

}