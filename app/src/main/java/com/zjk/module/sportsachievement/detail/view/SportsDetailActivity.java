package com.zjk.module.sportsachievement.detail.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zjk.common.ui.BaseChartActivity;
import com.zjk.model.SportsData;
import com.zjk.run_help.R;
import com.zjk.util.DebugUtil;

import java.util.ArrayList;

public class SportsDetailActivity extends BaseChartActivity implements OnChartValueSelectedListener {

    private static final String TAG = "SportsDetailActivity";

    private static final String KEY_SPORTS_DATA = "key_sports_data";

    private Toolbar mToolbar;
    private LineChart mChart;

    private SportsData sportsData;

    public static void start(Context context, SportsData sportsData) {
        Intent intent = new Intent(context, SportsDetailActivity.class);
        intent.putExtra(KEY_SPORTS_DATA, sportsData);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_detail);

        findWidget();
        setListener();
        init();
    }

    @Override
    protected void findWidget() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mChart = (LineChart) findViewById(R.id.chart);
    }

    @Override
    protected void setListener() {
        mChart.setOnChartValueSelectedListener(this);
    }

    @Override
    protected void init() {
        setupActionBar(mToolbar);
        sportsData = (SportsData) getIntent().getSerializableExtra(KEY_SPORTS_DATA);
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
//        mChart.setBackgroundColor(Color.LTGRAY);

        // add data
        initData();

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        // l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void initData() {
        ArrayList<Entry> list = new ArrayList<>();
        if (sportsData != null && sportsData.getrGDList() != null) {
            for (int i = 0; i < sportsData.getrGDList().size(); i++) {
                list.add(new Entry(i, (float) sportsData.getrGDList().get(i).getSpeed()));
            }
        }

        LineDataSet set;
        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set.setValues(list);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set = new LineDataSet(list, getString(R.string.instantaneous_speed));

            set.setAxisDependency(AxisDependency.LEFT);
            set.setColor(ContextCompat.getColor(this, R.color.color_list_divider));
            set.setCircleColor(ContextCompat.getColor(this, R.color.colorAccent));
            set.setLineWidth(2f);
            set.setCircleRadius(3f);
            set.setFillAlpha(65);
            set.setFillColor(ColorTemplate.getHoloBlue());
            set.setHighLightColor(Color.rgb(244, 117, 117));
            set.setDrawCircleHole(false);
            //set.setFillFormatter(new MyFillFormatter(0f));
            //set.setDrawHorizontalHighlightIndicator(false);
            //set.setVisible(false);
            //set.setCircleHoleColor(Color.WHITE);

            // create a data object with the datasets
            LineData data = new LineData(set);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            mChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        DebugUtil.info("Entry selected", e.toString());
        mChart.centerViewToAnimated(e.getX(), e.getY(), mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
    }

    @Override
    public void onNothingSelected() {
        DebugUtil.info("Nothing selected", "Nothing selected.");
    }

    @Override
    public void onClick(View v) {

    }
}
