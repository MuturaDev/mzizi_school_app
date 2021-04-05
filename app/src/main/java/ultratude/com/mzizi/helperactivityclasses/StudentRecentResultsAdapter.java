package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.GlobalSettings;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentResultsExtended;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;

public class StudentRecentResultsAdapter extends RecyclerView.Adapter<StudentRecentResultsAdapter.ViewHolder> {

    private List<PortalStudentResultsExtended> resultsExtendedList;
    private Context context;

    public StudentRecentResultsAdapter(List<PortalStudentResultsExtended> resultsExtendedList, Context context){
        this.resultsExtendedList = resultsExtendedList;
        this.context = context;
    }


    @Override
    public StudentRecentResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_recent_mean_score_item, parent, false);
        return new StudentRecentResultsAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StudentRecentResultsAdapter.ViewHolder holder, final int position) {
            holder.bind(resultsExtendedList.get(position));
    }


    @Override
    public int getItemCount() {
        return resultsExtendedList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView exam_period, avg_score, mean_grade, open_btn;

        public ViewHolder(View itemView){
            super(itemView);
            exam_period = itemView.findViewById(R.id.exam_period);
            avg_score = itemView.findViewById(R.id.avg_score);
            mean_grade = itemView.findViewById(R.id.mean_grade);
            open_btn = itemView.findViewById(R.id.open_btn);
//            open_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    AsyncTask asyncTask2 = new AsyncTask() {
//                        @Override
//                        protected void onPostExecute(Object o) {
//                            Map<String, Object> map = (Map<String,Object>)o;
//
//                            if(map.get(Constants.PORTAL_URL_ENABLED) != null){
//                                String baseulr = ((GlobalSettings)map.get(Constants.PORTAL_URL_ENABLED)).getGlobalSettingsValue();
//
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( baseulr + "/portal/" + resultsExtendedList.get(getAdapterPosition()).getFileUrl()));
//                                context.startActivity(browserIntent);
//
//                            }
//
//                            super.onPostExecute(o);
//                        }
//
//                        @Override
//                        protected Object doInBackground(Object[] objects) {
//                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
//                            Map<String, Object> map = new HashMap<>();
//
//                            if(db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED).size() > 0){
//                                map.put(Constants.PORTAL_URL_ENABLED,db.getGlobalSettingsDAO().getGlobalSettings(Constants.PORTAL_URL_ENABLED).get(0));
//                            }
//
//                            return map;
//                        }
//                    };
//                    asyncTask2.execute();
//                }
//            });
        }

        public void bind(PortalStudentResultsExtended results){
            exam_period.setText(results.getExamPeriod());
            avg_score.setText(results.getAvgScore() + " %");
            mean_grade.setText(results.getMeanGrade());
            open_btn.setText(results.getDownloadLink());
        }

    }

}
