package ultratude.com.staff.activities.mziziapptutorial;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import ultratude.com.staff.activities.accesscontrolforactivities.HelpSupportMap;
import ultratude.com.staff.R;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.ApplyLeaveHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.DailyTransportHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.DiscplinaryCaseHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.DutyRosterHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.EnqAttendanceHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.EnqDisciplinaryCasesHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.EnqExamHistoryHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.FleetServiceHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.FuelConsumptionHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.MarkClassAttendanceHelpSupport;
import ultratude.com.staff.activities.mziziapptutorial.TutorialPagesDAO.TripTransportHelpSupport;

import ultratude.com.staff.webservice.DataAccessObjects.HelpDAO;
import ultratude.com.staff.webservice.ResponseModels.Help;

/**
 * Created by James on 14/05/2019.
 */

public class TapToGetHelpActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private LinearLayout pb_loading_txt_manageDiscipline_progress;

    private ImageView btn_cancel_ID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.tap_to_get_help_layout);


        btn_cancel_ID = findViewById(R.id.btn_cancel_ID);
        btn_cancel_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        pb_loading_txt_manageDiscipline_progress = findViewById(R.id.pb_loading_txt_manageDiscipline_progress);

        recyclerView = findViewById(R.id.recycler_tap_to_get_help_ID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);




        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress(true);
            }

            @Override
            protected Object doInBackground(Object[] params) {

                return new HelpDAO(TapToGetHelpActivity.this).getSupportHelpList();
            }

            @Override
            protected void onPostExecute(Object o) {
                showProgress(false);
               // FancyToast.makeText(TapToGetHelpActivity.this,((List<Help>) o).toString(), FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(TapToGetHelpActivity.this,new HelpSupportMap(TapToGetHelpActivity.this).requestAccessToThisHelpSupport(((List<Help>) o)) );
                recyclerView.setAdapter(adapter);

                super.onPostExecute(o);
            }
        };

        asyncTask.execute();

    }



    private void showProgress(boolean show){
        if(show){
            recyclerView.setVisibility(View.INVISIBLE);
            pb_loading_txt_manageDiscipline_progress.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            pb_loading_txt_manageDiscipline_progress.setVisibility(View.INVISIBLE);
        }
    }



    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


        private List<Help> helpList;
        private Context mContext;


        public RecyclerViewAdapter(Context context, List<Help> helpList){
            this.mContext = context;
            this.helpList = helpList;
        }

        public class ViewHolder extends  RecyclerView.ViewHolder{

            private  TextView helpText;

            public ViewHolder(View view){
                super(view);

                helpText = view.findViewById(R.id.txt_help_text_ID);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Help help =  helpList.get(getAdapterPosition());

                        Intent intent = new Intent(TapToGetHelpActivity.this, TutorialActivity.class);

                        boolean findATrue = true;
                        ApplyLeaveHelpSupport applyLeaveHelpSupport = new ApplyLeaveHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(applyLeaveHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",applyLeaveHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", applyLeaveHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        DiscplinaryCaseHelpSupport discplinaryCaseHelpSupport = new DiscplinaryCaseHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(discplinaryCaseHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",discplinaryCaseHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", discplinaryCaseHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        DutyRosterHelpSupport dutyRosterHelpSupport = new DutyRosterHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(dutyRosterHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",dutyRosterHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", dutyRosterHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        FuelConsumptionHelpSupport fuelConsumptionHelpSupport = new FuelConsumptionHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(fuelConsumptionHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",fuelConsumptionHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", fuelConsumptionHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        FleetServiceHelpSupport fleetServiceHelpSupport = new FleetServiceHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(fleetServiceHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",fleetServiceHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", fleetServiceHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        MarkClassAttendanceHelpSupport markClassAttendanceHelpSupport = new MarkClassAttendanceHelpSupport();
                       // Log.d(mContext.getPackageName().toUpperCase(), markClassAttendanceHelpSupport.getHelpText()[0] + "\n" + help.getHelpText() );

                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(markClassAttendanceHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",markClassAttendanceHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", markClassAttendanceHelpSupport.getSlideImages());
                            findATrue = false;
                        }

                        EnqAttendanceHelpSupport enqAttendanceHelpSupport = new EnqAttendanceHelpSupport();
                        Log.d(mContext.getPackageName().toUpperCase(), mContext.getResources().getString(enqAttendanceHelpSupport.getHelpText()[0]) + "\n" + help.getHelpText() );

                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(enqAttendanceHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",enqAttendanceHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", enqAttendanceHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        EnqDisciplinaryCasesHelpSupport enqDisciplinaryCasesHelpSupport = new EnqDisciplinaryCasesHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(enqDisciplinaryCasesHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",enqDisciplinaryCasesHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", enqDisciplinaryCasesHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        EnqExamHistoryHelpSupport enqExamHistoryHelpSupport  = new EnqExamHistoryHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(enqExamHistoryHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",enqExamHistoryHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", enqExamHistoryHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        DailyTransportHelpSupport dailyTransportHelpSupport = new DailyTransportHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(dailyTransportHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",dailyTransportHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", dailyTransportHelpSupport.getSlideImages());
                            findATrue = false;
                        }
                        TripTransportHelpSupport tripTransportHelpSupport = new TripTransportHelpSupport();
                        if(help.getHelpText().equalsIgnoreCase(mContext.getResources().getString(tripTransportHelpSupport.getHelpText()[0]))){
                            intent.putExtra("SlideDescription",tripTransportHelpSupport.getSlideDescription());
                            intent.putExtra("SlideImages", tripTransportHelpSupport.getSlideImages());
                            findATrue = false;
                        }


                        if(findATrue){
                            FancyToast.makeText(mContext,"Comming Soon", FancyToast.LENGTH_LONG, FancyToast.INFO,true).show();
                        }else{
                            startActivity(intent);
                        }



                        //Toast.makeText(mContext, "Every item is clickable " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            void bind(Help help){
                helpText.setText(help.getHelpText());
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tap_to_get_help_item_layout, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(helpList.get(position));
        }

        @Override
        public int getItemCount() {
            return helpList.size();
        }



    }


    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    private ItemClickListener onItemClickListener;

    public void setItemClickListener(ItemClickListener clickListener){
        onItemClickListener = clickListener;
    }





}
