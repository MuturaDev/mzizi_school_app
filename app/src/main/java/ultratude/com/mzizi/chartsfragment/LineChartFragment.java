package ultratude.com.mzizi.chartsfragment;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ultratude.com.mzizi.ChartsCustom.MyMarkerView;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationAverageResponse;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;

public class LineChartFragment extends Fragment implements OnChartValueSelectedListener {

    private LineChart lineChart, lineChart1;
    private TextView y_axis_title, x_axis_title;

    //CHART
    private Typeface tfLight,tfRegular;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.linechart_fragment_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        y_axis_title = view.findViewById(R.id.y_axis_title);
        y_axis_title.setTypeface(tfLight);
        x_axis_title = view.findViewById(R.id.x_axis_title);
        x_axis_title.setTypeface(tfLight);
       LineChart();


        super.onViewCreated(view, savedInstanceState);
    }




    private List<Entry> getIncomeEntriesExam(List<PortalStudentVisualizationAverageResponse> responseList){
        ArrayList<Entry> incomeEntries = new ArrayList<>();

        int count = 0;
        for(PortalStudentVisualizationAverageResponse response : responseList){
            incomeEntries.add(new Entry(count, Float.valueOf(response.getAverageScore())));
            count++;
        }


        return incomeEntries.subList(0, responseList.size());

    }

    private void setDataLine(List<PortalStudentVisualizationAverageResponse> responseList) {

        if(responseList != null){
            if(responseList.size() > 0){


                List<Entry> values = getIncomeEntriesExam(responseList);


                com.github.mikephil.charting.data.LineDataSet set1;

                if (lineChart.getData() != null &&
                                lineChart.getData().getDataSetCount() > 0) {
                    set1 = (com.github.mikephil.charting.data.LineDataSet) lineChart.getData().getDataSetByIndex(0);
                    set1.setValues(values);
                    set1.setColor(R.color.graph_color);
                    set1.setCircleColor(R.color.colorAccent1);
                    set1.notifyDataSetChanged();
                    lineChart.getData().notifyDataChanged();
                    lineChart.notifyDataSetChanged();
                } else {
                    // create a dataset and give it a type
                    //TODO: SHOW THE SUBJECT SELECTED, OR ALL SUBJECTS
                    set1 = new com.github.mikephil.charting.data.LineDataSet(values, "AVERAGE MARKS PER TERM");

                    set1.setDrawIcons(true);

                    // draw dashed line
                    //set1.enableDashedLine(10f, 5f, 0f);


                    // black lines and points
                    //not working
//                    set1.setColor(R.color.graph_color);
//                    set1.setCircleColor(R.color.colorAccent1);



                    // line thickness and point size
                    set1.setLineWidth(2.5f);
                    set1.setCircleRadius(4f);

                    // draw points as solid circles
                    set1.setDrawCircleHole(false);

                    // customize legend entry
                    //TODO: JUST REMOVED FOR TESTING, STARTED HERE
//            set1.setFormLineWidth(1f);
//            //set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);

                    // text size of values
                    set1.setValueTextSize(9f);
                    //TODO: JUST ADDED FOR TESTING
                    set1.setColor(Color.rgb(0, 24, 168));
                  // set1.setValueTextColor(Color.rgb(80, 80, 152));
                    set1.setCircleColor(Color.rgb(0, 24, 168));


                    // draw selection line as dashed
                    // set1.enableDashedHighlightLine(10f, 5f, 0f);

                    // set the filled area
                    //TODO: SET FALSE;
//                    set1.setDrawFilled(false);
//                    set1.setFillFormatter(new IFillFormatter() {
//                        @Override
//                        public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
//                            return lineChart.getAxisLeft().getAxisMinimum();
//                        }
//                    });

                    // set color of filled area
//            if (Utils.getSDKInt() >= 18) {
//                // drawables only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }

                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(set1); // add the data sets

                    // create a data object with the data sets
                    com.github.mikephil.charting.data.LineData data = new com.github.mikephil.charting.data.LineData(dataSets);

                    // set data
                    lineChart.setData(data);
                }
            }

        }




    }

    private void LineChart(){
        AsyncTask asyncTask = new AsyncTask() {



            @Override
            protected void onPostExecute(Object o) {

                List<PortalStudentVisualizationAverageResponse> responseList = (List<PortalStudentVisualizationAverageResponse>)o;

                if(responseList != null){
                    if(responseList.size() > 0){
                        //setDataLine2(45,180);
                        // add data


                        {
                            // // Chart Style // //
                            lineChart = getView().findViewById(R.id.chart);

                            // background color
                            lineChart.setBackgroundColor(Color.WHITE);

                            // disable description text
                            lineChart.getDescription().setEnabled(false);
//                            lineChart.setDescription();
                            // enable touch gestures
                            lineChart.setTouchEnabled(true);

                            // set listeners
                            lineChart.setOnChartValueSelectedListener(LineChartFragment.this);
                            lineChart.setDrawGridBackground(true);

                            // create marker to display box when values are selected
                            MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

                            // Set the marker to the chart
                            mv.setChartView(lineChart);
                            lineChart.setMarker(mv);

                            // enable scaling and dragging
                            lineChart.setDragEnabled(true);
                            lineChart.setScaleEnabled(true);
                            // chart.setScaleXEnabled(true);
                            // chart.setScaleYEnabled(true);

                            //to hide background lines
//                            lineChart.getXAxis().setDrawGridLines(false);
//                            lineChart.getAxisLeft().setDrawGridLines(false);
//                            lineChart.getAxisRight().setDrawGridLines(false);



                            // force pinch zoom along both axis
                            lineChart.setPinchZoom(true);

                            setDataLine(responseList);
                        }



                        XAxis xAxis;
                        {   // // X-Axis Style // //
                            xAxis = lineChart.getXAxis();

                            //xAxis.setAxisLineColor(Color.rgb(0, 24, 168));
                            xAxis.setAxisLineWidth(1f);
                           // xAxis.setTextColor(Color.rgb(80,80,152));
                            //xAxis.setCenterAxisLabels(true);
                            xAxis.setGranularity(1f);
                            xAxis.setAxisMaximum(responseList.size());
                            xAxis.setAxisMinimum(0);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            List<String> xAxisValues = new ArrayList<>();

                            for(int i =0; i<responseList.size(); i++){
                                xAxisValues.add(responseList.get(i).getPeriod());
                            }
                            xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
                            // vertical grid lines
                            //xAxis.enableGridDashedLine(10f, 10f, 0f);



                        }

                        YAxis yAxis;
                        {   // // Y-Axis Style // //
                            yAxis = lineChart.getAxisLeft();
                            yAxis.setAxisLineWidth(1f);
                           // yAxis.setAxisLineColor(Color.rgb(0, 24, 168));
                            //yAxis.setTextColor(Color.rgb(80,80,152));
                            // disable dual axis (only use LEFT axis)
                            lineChart.getAxisRight().setEnabled(false);

                            // horizontal grid lines
                            yAxis.enableGridDashedLine(10f, 10f, 0f);

                            // axis range
                            //TODO: NOTE, MUST HAVE THIS
                            yAxis.setAxisMaximum(100f);
                            yAxis.setAxisMinimum(0f);
                        }

                        //to hide right Y and top X border
//                        YAxis rightYAxis = lineChart.getAxisRight();
//                        rightYAxis.setEnabled(false);
//                        YAxis leftYAxis = lineChart.getAxisLeft();
//                        leftYAxis.setEnabled(false);
//                        XAxis topXAxis = lineChart.getXAxis();
//                        topXAxis.setEnabled(false);

                        {   // // Create Limit Lines // //
//                            LimitLine llXAxis = new LimitLine(9f, "Index 10");
//                            llXAxis.setLineWidth(4f);
//                            llXAxis.enableDashedLine(10f, 10f, 0f);
//                            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//                            llXAxis.setTextSize(10f);
//                            llXAxis.setTypeface(tfRegular);

                            //TODO: THIS IS REMOVED
//                            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//                            ll1.setLineWidth(4f);
//                            ll1.enableDashedLine(10f, 10f, 0f);
//                            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//                            ll1.setTextSize(10f);
//                            ll1.setTypeface(tfRegular);
//
//                            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//                            ll2.setLineWidth(4f);
//                            ll2.enableDashedLine(10f, 10f, 0f);
//                            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//                            ll2.setTextSize(10f);
//                            ll2.setTypeface(tfRegular);

                            // draw limit lines behind data instead of on top
//                            yAxis.setDrawLimitLinesBehindData(true);
//                            xAxis.setDrawLimitLinesBehindData(true);
//
//                            // add limit lines
//                            yAxis.addLimitLine(ll1);
//                            yAxis.addLimitLine(ll2);
                            //xAxis.addLimitLine(llXAxis);
                        }

                    //        //add data


                        // draw points over time
                        lineChart.animateXY(1000, 1500);

                        // get the legend (only possible after setting data)
                        Legend l = lineChart.getLegend();

                        // draw legend entries as lines
                        l.setForm(Legend.LegendForm.LINE);
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
                return db.getPortalStudentVisualizationAverageResponseDAO().getPortalStudentVisualizationAverageResponse(Integer.valueOf(studentid));
            }
        };
        asyncTask.execute();



    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null) {
            return;
        }


            Log.i("Entry selected", e.toString());
            Log.i("LOW HIGH", "low: " + lineChart.getLowestVisibleX() + ", high: " + lineChart.getHighestVisibleX());
            Log.i("MIN MAX", "xMin: " + lineChart.getXChartMin() + ", xMax: " + lineChart.getXChartMax() + ", yMin: " + lineChart.getYChartMin() + ", yMax: " + lineChart.getYChartMax());


    }

    @Override
    public void onNothingSelected() { }

}
