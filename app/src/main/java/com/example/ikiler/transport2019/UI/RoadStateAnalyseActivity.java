package com.example.ikiler.transport2019.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ikiler.transport2019.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class RoadStateAnalyseActivity extends AppCompatActivity {

    private BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBarChart();
        setContentView(barChart);
    }
    private void initBarChart(){
        barChart = new BarChart(getApplicationContext());
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.setData(getData());
    }
    private BarData getData(){
        List<BarEntry> list1 = new ArrayList<>();
        list1.add(new BarEntry(10,0));
        list1.add(new BarEntry(20,1));
        list1.add(new BarEntry(30,2));
        list1.add(new BarEntry(40,3));
        BarDataSet dataSet1 = new BarDataSet(list1,"Week1");

        List<BarEntry> list2 = new ArrayList<>();
        list1.add(new BarEntry(10,0));
        list1.add(new BarEntry(20,1));
        list1.add(new BarEntry(30,2));
        list1.add(new BarEntry(40,3));
        BarDataSet dataSet2 = new BarDataSet(list1,"Week2");

        List<BarEntry> list3 = new ArrayList<>();
        list1.add(new BarEntry(10,0));
        list1.add(new BarEntry(20,1));
        list1.add(new BarEntry(30,2));
        list1.add(new BarEntry(40,3));
        BarDataSet dataSet3 = new BarDataSet(list1,"Week3");

        String[] xVals = {"Week1s","Week2s","Week3s"};
        List<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);

        BarData barData = new BarData(xVals,dataSets);
        return barData;
    }
}
