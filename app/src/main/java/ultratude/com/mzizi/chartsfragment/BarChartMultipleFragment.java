package ultratude.com.mzizi.chartsfragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.ChartsCustom.MyMarkerView;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;


public class BarChartMultipleFragment extends Fragment implements OnChartValueSelectedListener {

    private BarChart barchart1;
    private TextView y_axis_title, x_axis_title;

    //CHART
    private Typeface tfLight,tfRegular;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.barchart_multiple_fragment_layout, container, false);
    }

    private UtilityFunctions utilityFunctions;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        utilityFunctions = new UtilityFunctions(getActivity());
        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        y_axis_title = view.findViewById(R.id.y_axis_title);
        y_axis_title.setTypeface(tfLight);
        x_axis_title = view.findViewById(R.id.x_axis_title);
        x_axis_title.setTypeface(tfLight);


        BarChartMultiSet();

        //BarChartMultiSetTesting();

        super.onViewCreated(view, savedInstanceState);
    }

    private void BarChartMultiSetTesting(){
        barchart1 = getView().findViewById(R.id.chart);
                        barchart1.setOnChartValueSelectedListener(BarChartMultipleFragment.this);
                        barchart1.getDescription().setEnabled(false);

                        barchart1.setDrawBorders(true);

                        // scaling can now only be done on x- and y-axis separately
                        barchart1.setPinchZoom(false);

                        barchart1.setDrawBarShadow(false);

                        barchart1.setDrawGridBackground(false);

                        // create a custom MarkerView (extend MarkerView) and specify the layout
                        // to use for it
                        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
                        mv.setChartView(barchart1); // For bounds control
                        barchart1.setMarker(mv); // Set the marker to the chart




                        Legend l = barchart1.getLegend();
                        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        l.setDrawInside(false);
                        l.setTypeface(tfLight);
                        l.setYOffset(0f);
                        l.setXOffset(10f);
                        l.setYEntrySpace(0f);
                        l.setTextSize(8f);


                        XAxis xAxis = barchart1.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setTypeface(tfLight);
                        xAxis.setGranularity(1f);
                        //todo: set dynamically
                        //xAxis.setAxisMaximum(100);
                        xAxis.setAxisMinimum(0f);
                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getFormattedValue(float value) {
                                return String.valueOf((int) value);
                            }
                        });

                        YAxis leftAxis = barchart1.getAxisLeft();
                        leftAxis.setTypeface(tfLight);
                        leftAxis.setValueFormatter(new LargeValueFormatter());
                        leftAxis.setDrawGridLines(false);
                        leftAxis.setSpaceTop(35f);
//                        leftAxis.setAxisMaximum(100f);
                        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

                        barchart1.getAxisRight().setEnabled(false);

                        //DATA


                        float groupSpace = 0.08f;
                        float barSpace = 0.03f; // x4 DataSet
                        float barWidth = 0.2f; // x4 DataSet
                        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"

                        int groupCount =4;//seekBarX.getProgress() + 1
                        int startYear = 1980;
                        int endYear = startYear + groupCount;
                        ArrayList<BarEntry> values1 = new ArrayList<>();
                        ArrayList<BarEntry> values2 = new ArrayList<>();
                        ArrayList<BarEntry> values3 = new ArrayList<>();
                        ArrayList<BarEntry> values4 = new ArrayList<>();



                        float randomMultiplier = 3 * 100000f;//seekBarY.getProgress() * 100000f;

//                        for(int i = 0; i<resultList.size(); i++){
//
//                        }

                        for (int i = startYear; i < endYear; i++) {
                            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
                        }

                        BarDataSet set1, set2, set3, set4;


                        if (barchart1.getData() != null && barchart1.getData().getDataSetCount() > 0) {

                            set1 = (BarDataSet) barchart1.getData().getDataSetByIndex(0);
                            set2 = (BarDataSet) barchart1.getData().getDataSetByIndex(1);
                            set3 = (BarDataSet) barchart1.getData().getDataSetByIndex(2);
                            set4 = (BarDataSet) barchart1.getData().getDataSetByIndex(3);
                            set1.setValues(values1);
                            set2.setValues(values2);
                            set3.setValues(values3);
                            set4.setValues(values4);



                            barchart1.getData().notifyDataChanged();
                            barchart1.notifyDataSetChanged();

                        } else {
                            // create 4 DataSets


                            set1 = new BarDataSet(values1, "Company A");
                            //set1.setColor(Color.rgb(104, 241, 175));
                            set2 = new BarDataSet(values2, "Company B");
                            //set2.setColor(Color.rgb(164, 228, 251));
                            set3 = new BarDataSet(values3, "Company C");
                            //set3.setColor(Color.rgb(242, 247, 158));
                            set4 = new BarDataSet(values4, "Company D");
                            // set4.setColor(Color.rgb(255, 102, 0));



                            set1.setColor(Color.rgb(26, 79, 249));
                            set2.setColor(Color.rgb(27, 71, 210));
                            set3.setColor(Color.rgb(22, 59, 175));
                            set4.setColor(Color.rgb(19, 49, 146));

                            com.github.mikephil.charting.data.BarData data = new com.github.mikephil.charting.data.BarData(set1, set2, set3, set4);
                            data.setValueFormatter(new LargeValueFormatter());
                            data.setValueTypeface(tfLight);

                            barchart1.setData(data);
                        }

                        // specify the width each bar should have
                        barchart1.getBarData().setBarWidth(barWidth);

                        // restrict the x-axis range
                        barchart1.getXAxis().setAxisMinimum(startYear);

                        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
                        barchart1.getXAxis().setAxisMaximum(startYear + barchart1.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
                        barchart1.groupBars(startYear, groupSpace, barSpace);
                        barchart1.invalidate();



                        barchart1.getData().setHighlightEnabled(barchart1.getData().isHighlightEnabled());
                        barchart1.setPinchZoom(true);
                        barchart1.setAutoScaleMinMaxEnabled(barchart1.isAutoScaleMinMaxEnabled());
                        barchart1.notifyDataSetChanged();
                        barchart1.animateXY(2000, 2000);
                        barchart1.invalidate();
    }


    private void BarChartMultiSet(){

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                List<PortalStudentVisualizationResponse> resultList = (List<PortalStudentVisualizationResponse>)o;

               // Log.d(getContext().getPackageName().toUpperCase(), "VisualizationResponse Chart Fragment: " + resultList2.toString());


                Bundle unit = getArguments();
                String subject = "";
                if(unit != null){
                   subject = unit.getString("UNIT");
                }
               // Log.d(getActivity().getPackageName().toUpperCase(), "BAR SUBJECT: " + subject);
               // List<PortalStudentVisualizationResponse> resultList = utilityFunctions.getListForThisSubject(subject,utilityFunctions.portalStudentVisualizationResponsesDummyData() );


                if(resultList != null){
                    if(resultList.size() > 0){

                        List<String> termYearDatesKey = utilityFunctions.getUniqueTermYearDates(resultList);
                        List<List<PortalStudentVisualizationResponse>> subjectGroups = utilityFunctions.getUniqueSubjects(resultList);

                        barchart1 = getView().findViewById(R.id.chart);
                        barchart1.setOnChartValueSelectedListener(BarChartMultipleFragment.this);
                        barchart1.getDescription().setEnabled(false);

                        barchart1.setDrawBorders(true);

                        // scaling can now only be done on x- and y-axis separately
                        barchart1.setPinchZoom(false);

                        barchart1.setDrawBarShadow(false);

                        barchart1.setDrawGridBackground(true);


                        // create a custom MarkerView (extend MarkerView) and specify the layout
                        // to use for it
                        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
                        mv.setChartView(barchart1); // For bounds control
                        barchart1.setMarker(mv); // Set the marker to the chart


                        XAxis xAxis = barchart1.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        xAxis.setTypeface(tfLight);
                        xAxis.setGranularity(1f);
                        xAxis.setTextSize(10f);

                        xAxis.setDrawGridLines(false);
                        xAxis.setCenterAxisLabels(true);

                        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(utilityFunctions.getXAxisLabelsLine(resultList)));
//                        xAxis.setValueFormatter(new ValueFormatter() {
//                            @Override
//                            public String getFormattedValue(float value) {
//                                return String.valueOf((int) value);
//                            }
//                        });

                        YAxis leftAxis = barchart1.getAxisLeft();
                        leftAxis.setTypeface(tfLight);
                        leftAxis.setValueFormatter(new LargeValueFormatter());
                        leftAxis.setDrawGridLines(false);
                        leftAxis.setSpaceTop(35f);
                        leftAxis.setAxisMaximum(105f);
                        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

                        barchart1.getAxisRight().setEnabled(false);

                        //DATA

                        int groupCount = termYearDatesKey.size();//seekBarX.getProgress() + 1

                        float groupSpace = 0.27f;
                        float barSpace = 0.02f; // x4 DataSet
                        float barWidth = 0.13f; //x4 DataSet

                        if(groupCount <= 2) {
                             groupSpace = 0.7f;
                             barSpace   = 0.02f;
                             barWidth   =  0.13f;
                        }

                        if(groupCount == 3) {
                            groupSpace = 0.5f;
                            barSpace   = 0.02f;
                            barWidth   =  0.13f;
                        }

                        // (0.2 + 0.03) * 4 + 0.08 = 1.00 -> interval per "group"




                        List<ArrayList<BarEntry>> valuesArrayListList = new ArrayList<>();//5
                        for(int i=0; i<groupCount; i++ ){
                            ArrayList<BarEntry> values = new ArrayList<>();
                            valuesArrayListList.add(values);
                        }

                        float randomMultiplier =  100f;//seekBarY.getProgress() * 100000f;

                        for(int i= 0; i< subjectGroups.size(); i++){

                            List<PortalStudentVisualizationResponse> visualRes = subjectGroups.get(i);

                            for(int j =0; j < valuesArrayListList.size(); j++ ){

                                ArrayList<BarEntry> values = valuesArrayListList.get(j);

                                for(int k=j; k< visualRes.size(); k++){
                                    PortalStudentVisualizationResponse resp = visualRes.get(k);
                                   values.add(new BarEntry(i, Float.valueOf(resp.getAverageScore())/*(float) (Math.random() * randomMultiplier))*/));
                                    //values.add(new BarEntry(i, Float.valueOf(/*resp.getAverageScore())*/(float) (Math.random() * randomMultiplier))));
                                    break;
                                }

                            }
                        }


                        //2 subjects//loop by the number of years
//                        for(int i =0; i< subjectGroups.size(); i++){
//
//                            List<PortalStudentVisualizationResponse> responseList1 = subjectGroups.get(i);
//
//                            for(int j=0; j< valuesArrayListList.size(); j++){
//
//                                ArrayList<BarEntry> values = valuesArrayListList.get(j);
//
//                                for(int k=0; k<responseList1.size(); k++) {//5
//                                    PortalStudentVisualizationResponse visualResponse = responseList1.get(k);
//                                    values.add(new BarEntry(i, Float.valueOf(visualResponse.getAverageScore()) ));
//
//                                }
//                            }
//
//                        }
                         //Hence going for all years, groups supplying data
                        //TODO: HAD TO REMAIN COZ THIS IS GENIUS, IT WILL HAVE THE SAME YEAR BUT DIFF Y VALUES
//                        for (int i = startYear; i < endYear; i++) {
//                            values1.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                            values2.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                            values3.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                            values4.add(new BarEntry(i, (float) (Math.random() * randomMultiplier)));
//                        }



                        if (barchart1.getData() != null && barchart1.getData().getDataSetCount() > 0) {



                            for(int i=0; i< valuesArrayListList.size(); i++){
                                BarDataSet set = (BarDataSet) barchart1.getData().getDataSetByIndex(i);
                                set.setValues(valuesArrayListList.get(i));
                            }

                            barchart1.getData().notifyDataChanged();
                            barchart1.notifyDataSetChanged();

                        } else {
                            // create 4 DataSets



                            IBarDataSet[] setArrayList = new IBarDataSet[groupCount];
                            //BarDataSet[] setArrayList = new BarDataSet[valuesList.size()];
                            int[] colorArray =
                                            {
//                                                    Color.rgb(26, 79, 249),
//                                            Color.rgb(27, 71, 210),
//                                            Color.rgb(22, 59, 175),
//                                            Color.rgb(19, 49, 146),
//                                            Color.rgb(15, 39, 116),
//                                            Color.rgb(12, 30, 87),
//                                            Color.rgb(108, 140, 247),
//                                            Color.rgb(83, 113, 209),
//                                            Color.rgb(70, 95, 173),
//                                            Color.rgb(57, 76, 137),
//                                            Color.rgb(139, 165, 249),
//                                            Color.rgb(114, 135, 202),
//                                            Color.rgb(91, 107, 162),

                                                    Color.rgb(207, 54, 126),
                                                    Color.rgb(135, 21, 211),
                                                    Color.rgb(207, 58, 54 ),
                                                    Color.rgb(21, 102, 211),

                                                    Color.rgb(207, 135, 54),
                                                    Color.rgb(126, 207, 54),
                                                    Color.rgb(21, 211, 35 ),
                                                    Color.rgb(207, 148, 54),

                                                    Color.rgb(54, 207, 59 ),
                                                    Color.rgb(202, 207, 54),
                                                    Color.rgb(207, 54, 190),
                                                    Color.rgb(43, 132, 112),


                                                    Color.rgb(244, 122, 66),
                                                    Color.rgb(234, 202, 42),
                                                    Color.rgb(181, 229, 71),
                                                    Color.rgb(64, 193, 34),

                                                    Color.rgb(7, 84, 2),
                                                    Color.rgb(9, 193, 138),
                                                    Color.rgb(9, 127, 196),
                                                    Color.rgb(9, 41, 226),

                                                    Color.rgb(166, 8, 224),
                                                    Color.rgb(242, 36, 197),
                                                    Color.rgb(29, 73, 37),
                                                    Color.rgb(204, 12, 50)
                                            };
                                                       //set key
                            //THIS IS AS LONG AS valueList and termYearDatesKey have a similar count




                            for(int i=0; i< valuesArrayListList.size(); i++){
                                ArrayList<BarEntry> values = valuesArrayListList.get(i);

                                BarDataSet set  = new BarDataSet(values, termYearDatesKey.get(i));
                                set.setColor(colorArray[i]);
                                setArrayList[i] = set;
                            }


//                            set1 = new BarDataSet(values1, "Company A");
//                            //set1.setColor(Color.rgb(104, 241, 175));
//                            set2 = new BarDataSet(values2, "Company B");
//                            //set2.setColor(Color.rgb(164, 228, 251));
//                            set3 = new BarDataSet(values3, "Company C");
//                            //set3.setColor(Color.rgb(242, 247, 158));
//                            set4 = new BarDataSet(values4, "Company D");
//                            // set4.setColor(Color.rgb(255, 102, 0));
//
//
//
//                            set1.setColor(Color.rgb(26, 79, 249));
//                            set2.setColor(Color.rgb(27, 71, 210));
//                            set3.setColor(Color.rgb(22, 59, 175));
//                            set4.setColor(Color.rgb(19, 49, 146));

                            com.github.mikephil.charting.data.BarData data = new com.github.mikephil.charting.data.BarData(setArrayList);

                            data.setValueFormatter(new LargeValueFormatter());
                            data.setValueTypeface(tfLight);

                            barchart1.setData(data);
                        }

                        // specify the width each bar should have
                        barchart1.getBarData().setBarWidth(barWidth);

                        // restrict the x-axis range
                        xAxis.setAxisMaximum(subjectGroups.size());
                        xAxis.setAxisMinimum(0f);
                        // barData.getGroupWith(...) is a helper that calculates the width each group needs based on the provided parameters
                       //barchart1.getXAxis().setAxisMaximum( 0 +  * groupCount );

                        barchart1.groupBars(0, groupSpace, barSpace);
                        barchart1.invalidate();


                        Legend l = barchart1.getLegend();
                        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
                        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                        l.setDrawInside(true);
                        l.setTypeface(tfLight);
                        l.setYOffset(0f);
                        l.setXOffset(25f);
                        l.setYEntrySpace(0f);
                        l.setTextSize(8f);

                        barchart1.getData().setHighlightEnabled(barchart1.getData().isHighlightEnabled());
                        barchart1.setPinchZoom(true);
                        barchart1.setAutoScaleMinMaxEnabled(barchart1.isAutoScaleMinMaxEnabled());
                        barchart1.notifyDataSetChanged();
                        barchart1.animateXY(2000, 2000);
                        barchart1.invalidate();

                    }
                }

                super.onPostExecute(o);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                return db.getPortalStudentVisualizationResponseDAO().getPortalStudentVisualizationResponse(Integer.valueOf(studentid));
            }
        };
        asyncTask.execute();



    }




    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null) {
            return;
        }
            Log.i("Activity", "Selected: " + e.toString() + ", dataSet: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() { }
}
