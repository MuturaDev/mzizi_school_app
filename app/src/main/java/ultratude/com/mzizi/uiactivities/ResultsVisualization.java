package ultratude.com.mzizi.uiactivities;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentUnits;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;


public class ResultsVisualization extends AppCompatActivity {


    private Fragment fragment1,fragment2,fragment3;

    private Spinner spinner_units;
    private LinearLayout pb_results_visualization_progress_layout_year;

    private UtilityFunctions utilityFunctions;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon4);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }




        setContentView(R.layout.results_visualization_fragment_layout);
        utilityFunctions = new UtilityFunctions(this);
        pb_results_visualization_progress_layout_year = findViewById(R.id.pb_results_visualization_progress_layout_year);

        spinner_units = findViewById(R.id.sp_units_ID);
        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPreExecute() {
                pb_results_visualization_progress_layout_year.setVisibility(View.VISIBLE);
                spinner_units.setVisibility(View.GONE);
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {

                //ArrayList<PortalStudentVisualizationResponse> resposneList = (ArrayList<PortalStudentVisualizationResponse>)o;

                List<PortalStudentVisualizationResponse> resposneList  = utilityFunctions.portalStudentVisualizationResponsesDummyData();
                List<String> subjects = utilityFunctions.getXAxisLabelsLine(resposneList);

                if(resposneList != null){
                    if(resposneList.size() > 0) {

                        List<PortalStudentUnits> unitList = new ArrayList<>();
                        //
                        for(int i=0; i<subjects.size(); i++){
                            PortalStudentUnits unit = new PortalStudentUnits(
                                    i,
                                    subjects.get(i)
                            );
                            unitList.add(unit);
                        }


                        ArrayAdapter<PortalStudentUnits> examTypeSpinnerArrayAdapter = new ArrayAdapter<>(ResultsVisualization.this, android.R.layout.simple_spinner_dropdown_item, unitList);
                        spinner_units.setAdapter(examTypeSpinnerArrayAdapter);
                    }else{
                        PortalStudentUnits studentUnits = new PortalStudentUnits(0,"ALL SUBJECTS");
                        ArrayList<PortalStudentUnits> list = new ArrayList<>();
                        list.add(studentUnits);

                        ArrayAdapter<PortalStudentUnits> examTypeSpinnerArrayAdapter = new ArrayAdapter<>(ResultsVisualization.this, android.R.layout.simple_spinner_dropdown_item, list);
                        spinner_units.setAdapter(examTypeSpinnerArrayAdapter);
                    }
                }else{
                    PortalStudentUnits studentUnits = new PortalStudentUnits(0,"ALL SUBJECTS");
                    ArrayList<PortalStudentUnits> list = new ArrayList<>();
                    list.add(studentUnits);

                    ArrayAdapter<PortalStudentUnits> examTypeSpinnerArrayAdapter = new ArrayAdapter<>(ResultsVisualization.this, android.R.layout.simple_spinner_dropdown_item, list);
                    spinner_units.setAdapter(examTypeSpinnerArrayAdapter);
                }

                pb_results_visualization_progress_layout_year.setVisibility(View.GONE);
                spinner_units.setVisibility(View.VISIBLE);
                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                //Funny thing this is not the case
               ParentMziziDatabase db = ParentMziziDatabase.getInstance(ResultsVisualization.this);
//                return db.getPortalStudentUnitsDAO().getPortalStudentUnits();
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return db.getPortalStudentVisualizationResponseDAO().getPortalStudentVisualizationResponse(Integer.valueOf(studentid));
            }
        };
        asyncTask.execute();

        spinner_units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
//                    case 0:
//                        break;
                    default:
                        PortalStudentUnits item = (PortalStudentUnits) parent.getSelectedItem();
                        Bundle bundle = new Bundle();
                        bundle.putString("UNIT",item.getUnitName());

                        fragment1 = getSupportFragmentManager().findFragmentById(R.id.fragment1);
                        fragment1.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

//                        if(fragment1 == null){
//                            fragmentTransaction.hide(fragment1);
//                        }

                        fragmentTransaction.commit();


                        fragment2 = getSupportFragmentManager().findFragmentById(R.id.fragment2);
                        fragment2.setArguments(bundle);
                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();

//                        if(fragment2 == null){
//                            fragmentTransaction1.hide(fragment2);
//                        }

                        fragmentTransaction1.commit();

//                        fragment3 = getSupportFragmentManager().findFragmentById(R.id.fragment3);
//                        fragment3.setArguments(bundle);
//                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
//
//                        if(fragment3 != null){
//                            fragmentTransaction2.hide(fragment3);
//                        }
//
//                        fragmentTransaction2.commit();

                       // Toast.makeText(ResultsVisualization.this, item.getUnitName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ReportAnalytics.reportScreenChangeAnalytic(ResultsVisualization.this, "ResultsVisualization Activity");
    }

}
