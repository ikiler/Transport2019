package com.example.ikiler.transport2019.UI;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.ikiler.transport2019.R;
import com.example.ikiler.transport2019.bean.RoadStat;
import com.example.ikiler.transport2019.http.API;
import com.example.ikiler.transport2019.http.NetConnection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoadStateAnalyseActivity extends AppCompatActivity {

    private BarChart barChart;
    private boolean isRun = true;
    private List<Integer> ignore = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBarChart();
        setContentView(barChart);
    }

    private void initBarChart() {
        final String[] staNams = {"", "畅通", "缓行", "一般拥堵", "中度拥堵", "严重拥堵"};
        barChart = new BarChart(getApplicationContext());
        barChart.getAxisLeft().setLabelCount(5, true);
        barChart.getAxisLeft().setAxisMaxValue(5);
        barChart.getAxisLeft().setAxisMinValue(1);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisLeft().setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, YAxis yAxis) {
                String state = staNams[(int) v];
                return state;
            }
        });
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                ignore.add(i);
                getData();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        getData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    getData();
                    try {
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void getData() {

        List<String> urls = new ArrayList<>();
        List<String> jsons = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            urls.add(API.baseUrl + "getRoadStat");
            jsons.add("{\"id\": " + i + "}");
        }

        NetConnection.post(urls, jsons, new NetConnection.CallBack() {
            @Override
            public void success(List<String> results) {
                if (!isRun) return;
                int n = 0;
                BarData barData = new BarData();
                barData.addXValue("周1" );
                barData.addXValue("周2");
                barData.addXValue("周3");
                barData.addXValue("周4");
                barData.addXValue("周5");
                barData.addXValue("周6");
                barData.addXValue("周7" );

                int[] color = {0xff0000ff, 0xff00ff00, 0xffff0000, 0xff778899, 0xff0f0f0f, 0xfff00fff, 0xff112233};
                List<String> xValues = new ArrayList<>();
                String[] names = {"学院路", "联想路", "医院路", "幸福路", "环城快速路", "环城高速", "停车场"};
                List<BarDataSet> dataSets = new ArrayList<>();
                for (String json : results) {
                    Gson gson = new Gson();
                    RoadStat roadStat = gson.fromJson(json, RoadStat.class);
                    List<BarEntry> list = new ArrayList<>();
//                    if (ignore.contains(roadStat.getId()))
//                        continue;
                    for (int j = 0; j < roadStat.getRoads().size(); j++) {
                        if (ignore.contains(roadStat.getId()))
                            list.add(new BarEntry(0, j));
                        else
                            list.add(new BarEntry(roadStat.getRoads().get(j).getState(), j));
                    }
                    BarDataSet dataSet = new BarDataSet(list, names[roadStat.getId()]);
                    dataSet.setColor(color[n]);
                    n++;
                    xValues.add("周" + n);
                    dataSets.add(dataSet);
                    barData.addDataSet(dataSet);
//                    barData.addXValue("周" + n);
                }
                barChart.setData(barData);
                barChart.invalidate();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRun = false;
    }
}
