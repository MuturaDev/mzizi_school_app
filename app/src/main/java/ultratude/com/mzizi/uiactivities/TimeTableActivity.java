package ultratude.com.mzizi.uiactivities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.helperactivityclasses.TimeTableRecyclerAdapter;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;

import static ultratude.com.mzizi.Utils.UtilityFunctions.getDummyDataForTimeTable;
import static ultratude.com.mzizi.Utils.UtilityFunctions.sortRawTimeTableData;

public class TimeTableActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_table_view_recyclerview);

        new AsyncTask(){
            @Override
            protected void onPostExecute(Object o) {
        RecyclerView recyclerview_due_events = findViewById(R.id.time_table_recyclerview);

        RelativeLayout top_layout = findViewById(R.id.top_layout);
        top_layout.setVisibility(View.GONE);

        //List<TimeTableResponse> responseList = getDummyDataForTimeTable();
        List<TimeTableResponse> responseList = (List<TimeTableResponse>)o;

        if(responseList.size() > 0){

            List<List<TimeTableResponse>> list = sortRawTimeTableData(responseList);

            int scrollToPosition = 0;

            for(int i = 0; i<list.size(); i++){
                if(list.get(i).size() > 0){
                    scrollToPosition = i;
                    break;
                }
            }
            TimeTableRecyclerAdapter dueEventAdapter = new TimeTableRecyclerAdapter(list, TimeTableActivity.this);
            recyclerview_due_events.setAdapter(dueEventAdapter);
            recyclerview_due_events.scrollToPosition(scrollToPosition);
            findViewById(R.id.scroll_layout).setVisibility(View.VISIBLE);
            findViewById(R.id.txt_no_content).setVisibility(View.GONE);

        }else{
            findViewById(R.id.scroll_layout).setVisibility(View.GONE);
            findViewById(R.id.txt_no_content).setVisibility(View.VISIBLE);
        }


                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(TimeTableActivity.this);
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return db.getTimeTableResponseDAO().getTimeTableResponse(Integer.valueOf(studentid));
            }
        }.execute();


        ReportAnalytics.reportScreenChangeAnalytic(TimeTableActivity.this, "TimeTable Activity");
    }
}
