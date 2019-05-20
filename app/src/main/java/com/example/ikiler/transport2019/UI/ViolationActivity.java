package com.example.ikiler.transport2019.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ikiler.transport2019.R;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViolationActivity extends AppCompatActivity {


    @BindView(R.id.radar)
    RadarChart radar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        RadarData data = new RadarData();
        int[] color = {Color.RED,Color.BLUE,Color.GRAY,Color.GREEN,Color.YELLOW};
        for (int i = 0; i < 5; i++) {
            List<Entry> list = new ArrayList<>();
            list.add(new Entry((float) (i + 10*Math.random()), i));
            list.add(new Entry((float) (i + 10*Math.random()), i));
            list.add(new Entry((float) (i + 10*Math.random()), i));
            list.add(new Entry((float) (i + 10*Math.random()), i));
            RadarDataSet dataSet = new RadarDataSet(list, "aaa" + i);
//            dataSet.setColors(color);
            data.addDataSet(dataSet);
            dataSet.setColor(color[i]);
            dataSet.setFillColor(color[i]);
            dataSet.setDrawFilled(true);
            data.addXValue("BBB" + i);
        }
        radar.setData(data);
        radar.invalidate();
    }
}
