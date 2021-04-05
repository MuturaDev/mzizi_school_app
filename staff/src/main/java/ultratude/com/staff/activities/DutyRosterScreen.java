package ultratude.com.staff.activities;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import ultratude.com.staff.adapters.DutyRosterRecyclerAdapter;
import ultratude.com.staff.R;

import ultratude.com.staff.webservice.DataAccessObjects.DutyRosterDAO;
import ultratude.com.staff.webservice.ResponseModels.DutyRoster;

/**
 * Created by James on 12/01/2019.
 */

public class DutyRosterScreen extends AppCompatActivity {


    private RecyclerView recyclerView;
    private TextView txt_todays_date_ID;

    private TextView txt_currentWeek_ID;

    private LinearLayout pb_loading_txt_manageDiscipline_progress;
    private TextView no_content_to_display_ID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.dutyroster_layout);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        pb_loading_txt_manageDiscipline_progress = findViewById(R.id.pb_loading_txt_manageDiscipline_progress);
        no_content_to_display_ID = findViewById(R.id.no_content_to_display_ID);

        txt_todays_date_ID = findViewById(R.id.txt_todays_date_ID);
        recyclerView = findViewById(R.id.duty_roster_recycler);

        txt_currentWeek_ID =findViewById(R.id.txt_currentWeek_ID);



        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
        txt_todays_date_ID.setText(sdf.format(cal.getTime()));


        String currentWeek =  "Week "+ new DutyRosterDAO(this).getDutyRosterCurrentWeek();
        txt_currentWeek_ID.setText(currentWeek);



        List<DutyRoster> dutyRosterList = (List<DutyRoster>) new DutyRosterDAO(this).getDutyRosterList().get(0);

            //TESTING
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//       // alert.setMessage(dutyRosterList.get(0).toString());
//        alert.setMessage(currentWeek);
//        alert.show();







        //List<Object> objectList = new DutyRosterDAO(this).getDutyRosterList();
        //List<DutyRoster> dutyRosterList = (List<DutyRoster>) objectList.get(0);

        //SHOULD BE CHANGED TO GET DATA FROM TEHD DATABASE
        //Dummy DATA
//        List<DutyRoster> dutyRosterList = new ArrayList<>();
//        DutyRoster dutyRoster1 = new DutyRoster(
//                "Millie Collins",
//                "2019",
//                "Term 3",
//                "4"
//
//        );
//        dutyRosterList.add(dutyRoster1);
//        DutyRoster dutyRoster2 = new DutyRoster(
//                "Millie Collins",
//                "2019",
//                "Term 3",
//                "5"
//
//        );
//        dutyRosterList.add(dutyRoster1);
//        DutyRoster dutyRoster3 = new DutyRoster(
//                "Millie Collins",
//                "2019",
//                "Term 3",
//                "6"
//
//        );
//        dutyRosterList.add(dutyRoster1);

        //ACTUAL DATA
        List<Object> list = new DutyRosterDAO(this).getDutyRosterList();
        List<DutyRoster> dutyRosterArrayList = (List<DutyRoster>) list.get(0);


        if(dutyRosterArrayList.size() > 0){
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            DutyRosterRecyclerAdapter adapter = new DutyRosterRecyclerAdapter(this, dutyRosterArrayList);
            recyclerView.setAdapter(adapter);


            //so exactly on the progress that is visible, so make those need visible and leave the rest alone, unless affected meaning changed.
            recyclerView.setVisibility(View.VISIBLE);
            pb_loading_txt_manageDiscipline_progress.setVisibility(View.INVISIBLE);

        }else{
            no_content_to_display_ID.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            pb_loading_txt_manageDiscipline_progress.setVisibility(View.INVISIBLE);
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
