package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalToDoListResponse;
import ultratude.com.mzizi.uiactivities.AssignmentFileUploadFragment;
import ultratude.com.mzizi.uiactivities.DiaryFragment;
import ultratude.com.mzizi.uiactivities.FragTransaction;
import ultratude.com.mzizi.uiactivities.MainActivity;

public class PortalToDoListResponseAdapter  extends RecyclerView.Adapter<PortalToDoListResponseAdapter.ViewHolder> {

    private List<PortalToDoListResponse> portalToDoListResponseList;
    private Context context;

    public PortalToDoListResponseAdapter(List<PortalToDoListResponse> portalToDoListResponseList, Context context){
        this.portalToDoListResponseList = portalToDoListResponseList;
        this.context = context;
    }


    @Override
    public PortalToDoListResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_assignment_communication_item, parent, false);
        return new PortalToDoListResponseAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final PortalToDoListResponseAdapter.ViewHolder holder, final int position) {
        holder.bind(portalToDoListResponseList.get(position));
        holder.home_assingment_communication_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Diary Entry type is passed.
                FragTransaction.dislayFragment(DiaryFragment.class,holder.assignment_name.getText().toString(), ((MainActivity)context).fragmentManager);
                holder.layout.setBackgroundColor(ResourceUtils.getColor(context, R.color.graph_color));

//                try {
//                    Class fragmentClass = AssignmentFileUploadFragment.class;
//                    Fragment fragment = (Fragment) fragmentClass.newInstance();
////                    Bundle b = new Bundle();
////                    b.putSerializable("message", "");
////                    fragment.setArguments(b);
//                    //insert the fragment by replacing any existing
//                    FragmentTransaction fragmentTransaction = ((MainActivity) context).fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_transaction, fragment).commit();
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return portalToDoListResponseList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        private  TextView assignment_name, assignment_dueDate;
        private RelativeLayout home_assingment_communication_item;
        public LinearLayout layout;

        public ViewHolder(View itemView){
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            assignment_name = itemView.findViewById(R.id.assignment_name);
            assignment_dueDate = itemView.findViewById(R.id.assingment_dueDate);
            home_assingment_communication_item = itemView.findViewById(R.id.home_assingment_communication_item);

        }


        public void bind(PortalToDoListResponse toDoListResponse){
            assignment_name.setText(toDoListResponse.getDiaryEntryType().contains("ANNOUNCEMENTS") ? "Announcements" : toDoListResponse.getDiaryEntryType());
            assignment_dueDate.setText(toDoListResponse.getDiaryEntriesCount());
        }

    }

}
