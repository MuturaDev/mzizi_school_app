package ultratude.com.mzizi.chartsfragment;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.ChartsCustom.DayAxisValueFormatter;
import ultratude.com.mzizi.ChartsCustom.MyMarkerView;
import ultratude.com.mzizi.ChartsCustom.MyValueFormatter;
import ultratude.com.mzizi.ChartsCustom.XYMarkerView;
import ultratude.com.mzizi.R;
import ultratude.com.mzizi.uiactivities.HomeFrag;

public class BarChartFragment extends Fragment implements OnChartValueSelectedListener {

    private BarChart barchart;
    private TextView y_axis_title, x_axis_title;

    //CHART
    private Typeface tfLight,tfRegular;


    public BarChartFragment(){

    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");



        y_axis_title = view.findViewById(R.id.y_axis_title);
        y_axis_title.setTypeface(tfLight);
        x_axis_title = view.findViewById(R.id.x_axis_title);
        x_axis_title.setTypeface(tfLight);

        BarChart();



        super.onViewCreated(view, savedInstanceState);
    }



    private void setData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (barchart.getData() != null &&
                barchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barchart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barchart.getData().notifyDataChanged();
            barchart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "Term 3 Overall Exam 2019");

            set1.setDrawIcons(false);

//            set1.setColors(ColorTemplate.MATERIAL_COLORS);

            /*int startColor = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
            set1.setGradientColor(startColor, endColor);*/

            int startColor1 = ContextCompat.getColor(getActivity(), R.color.graph_color);
//            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
//            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
//            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
//            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
//            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
//            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
//            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
//            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
//            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, startColor1));
//            gradientColors.add(new GradientColor(startColor2, endColor2));
//            gradientColors.add(new GradientColor(startColor3, endColor3));
//            gradientColors.add(new GradientColor(startColor4, endColor4));
//            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);

            ArrayList<com.github.mikephil.charting.interfaces.datasets.IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            com.github.mikephil.charting.data.BarData data = new com.github.mikephil.charting.data.BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            barchart.setData(data);
        }
    }

    private final RectF onValueSelectedRectF = new RectF();


    //MPAndroidChart
    private void BarChart(){
        barchart = getView().findViewById(R.id.chart);
        barchart.setOnChartValueSelectedListener(BarChartFragment.this);
        barchart.setDrawBarShadow(false);
        barchart.setDrawValueAboveBar(true);

        //If more than 60 entries are displayed in the chart, no values wil be drawn
        barchart.setMaxVisibleValueCount(60);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart);
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);


        ValueFormatter custom = new MyValueFormatter("%");

        YAxis leftAxis = barchart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)



        YAxis rightAxis = barchart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)


        Legend l = barchart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);


        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(barchart); // For bounds control
        barchart.setMarker(mv); // Set the marker to the chart

        setData(20, 20);
        barchart.invalidate();
        barchart.animateXY(2000, 2000);
    }



    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null) {
            return;
        }

            RectF bounds = onValueSelectedRectF;
            barchart.getBarBounds((BarEntry) e, bounds);
            MPPointF position = barchart.getPosition(e, YAxis.AxisDependency.RIGHT);

            Log.i(getActivity().getPackageName().toUpperCase(), bounds.toString());
            Log.i(getActivity().getPackageName().toUpperCase(), position.toString());

            Log.i(getActivity().getPackageName().toUpperCase(),
                    "low: " + barchart.getLowestVisibleX() + ", high: "
                            + barchart.getHighestVisibleX());

            MPPointF.recycleInstance(position);

    }

    @Override
    public void onNothingSelected() { }


}
