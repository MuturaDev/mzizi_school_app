package ultratude.com.mzizi.chartsfragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.ChartsCustom.MyMarkerView;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.Utils.UtilityFunctions;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentVisualizationResponse;

public class LineChartMultipleFragment extends Fragment implements OnChartGestureListener, OnChartValueSelectedListener {

    private LineChart chart;
    private TextView y_axis_title, x_axis_title;

    //CHART
    private Typeface tfLight,tfRegular;

    public LineChartMultipleFragment(){

    }

    private UtilityFunctions utilityFunctions;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.linechart_multiple_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        utilityFunctions = new UtilityFunctions(getActivity());

        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
        y_axis_title = view.findViewById(R.id.y_axis_title);
        y_axis_title.setTypeface(tfLight);
        x_axis_title = view.findViewById(R.id.x_axis_title);
        x_axis_title.setTypeface(tfLight);

        AsyncTask asyncTask = new AsyncTask() {

            @Override
            protected void onPostExecute(Object o) {

                List<PortalStudentVisualizationResponse> resultList = (List<PortalStudentVisualizationResponse>)o;
                Bundle unit = getArguments();
                String subject = "";
                if(unit != null){
                    subject = unit.getString("UNIT");
                }
               // Log.d(getActivity().getPackageName().toUpperCase(), "LINE SUBJECT: " + subject);
                //List<PortalStudentVisualizationResponse> resultList = utilityFunctions.getListForThisSubject(subject,utilityFunctions.portalStudentVisualizationResponsesDummyData() );



                if(resultList != null) {
                    if (resultList.size() > 0) {
                        LineChartMultiple(resultList);
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



        super.onViewCreated(view, savedInstanceState);
    }

    private void setDataLineMultiple(List<PortalStudentVisualizationResponse> resultList){
        chart.resetTracking();

        List<String> termYearDatesKey = utilityFunctions.getUniqueTermYearDates(resultList);
        List<List<PortalStudentVisualizationResponse>> subjectGroups = utilityFunctions.getUniqueSubjects(resultList);


//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//
//        for (int z = 0; z < 3; z++) {
//
//            ArrayList<Entry> values = new ArrayList<>();
//
//            for(int i=0; i< 3; i++){
//                double val = (Math.random()) * 3 + 3;
//                values.add(new Entry(i, (float) val));
//            }
//
//            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
//            d.setLineWidth(2.5f);
//            d.setCircleRadius(4f);
//
//            int color = colors[z % colors.length];
//            d.setColor(color);
//            d.setCircleColor(color);
//            dataSets.add(d);
//        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        float randomMultiplier =  100f;

        List<ArrayList<Entry>> valuesArrayListList = new ArrayList<>();
        for(int i=0; i<termYearDatesKey.size(); i++){
            ArrayList<Entry> values = new ArrayList<>();
            valuesArrayListList.add(values);
        }

        for (int i= 0; i< subjectGroups.size(); i++) {
            List<PortalStudentVisualizationResponse> visualRes = subjectGroups.get(i);

            for(int j =0; j < valuesArrayListList.size(); j++ ){
                ArrayList<Entry> values = valuesArrayListList.get(j);

                for(int k=j; k< visualRes.size(); k++){
                    PortalStudentVisualizationResponse resp = visualRes.get(k);
                    values.add(new Entry(i, Float.valueOf(resp.getAverageScore())/*(float) (Math.random() * randomMultiplier))*/));
                  // values.add(new Entry(i, Float.valueOf(/*resp.getAverageScore())*/(float) (Math.random() * randomMultiplier))));
                    break;
                }

            }

        }

        for(int i=0; i<valuesArrayListList.size(); i++){
            ArrayList<Entry> values = valuesArrayListList.get(i);

            LineDataSet d = new LineDataSet(values, termYearDatesKey.get(i));
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);
            d.setDrawCircleHole(false);
            d.setColor(colors[i]);
            d.setCircleColor(colors[i]);
            dataSets.add(d);
        }



        // make the first DataSet dashed
//        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
//        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
//        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private void LineChartMultiple(List<PortalStudentVisualizationResponse> responseList){


        chart = getView().findViewById(R.id.chart);
        chart.setBackgroundColor(Color.WHITE);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(true);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(true);

        chart.setOnChartValueSelectedListener(LineChartMultipleFragment.this);

        // create marker to display box when values are selected
        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);

        // Set the marker to the chart
        mv.setChartView(chart);
        chart.setMarker(mv);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        // if disabled, scaling can be done on x- and y-axis separately
        // chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        chart.setPinchZoom(true);

        XAxis xAxis;
        {
            xAxis = chart.getXAxis();
            xAxis.setEnabled(true);
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(true);
            xAxis.setAxisLineWidth(1f);
            xAxis.setGranularity(1f);
            xAxis.setAxisMaximum(utilityFunctions.getUniqueSubjects(responseList).size());
            xAxis.setAxisMinimum(0f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(utilityFunctions.getXAxisLabelsLine(responseList)));
        }


        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            yAxis.setEnabled(true);
            yAxis.setDrawAxisLine(true);
            yAxis.setDrawGridLines(true);
            yAxis.setAxisLineWidth(1f);
            yAxis.setAxisMaximum(105f);
            yAxis.setAxisMinimum(0f);

            // yAxis.setAxisLineColor(Color.rgb(0, 24, 168));
            //yAxis.setTextColor(Color.rgb(80,80,152));
            // disable dual axis (only use LEFT axis)

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            //TODO: NOTE, MUST HAVE THIS
//            yAxis.setAxisMaximum(100f);
//            yAxis.setAxisMinimum(0f);
        }


        //add data
        setDataLineMultiple(responseList);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(25f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
        l.setDrawInside(true);

        chart.getAxisRight().setEnabled(false);



//        chart.setPinchZoom(true);
//        chart.setAutoScaleMinMaxEnabled(true);
//        chart.notifyDataSetChanged();
//        chart.getData().setHighlightEnabled(true);



        chart.animateXY(1500, 2000);

        chart.invalidate();
    }

    private final int[] colors = new int[] {
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

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            chart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart fling. VelocityX: " + velocityX + ", VelocityY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {}



    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart long pressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }
}
