package com.example.ikiler.transport2019.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.example.ikiler.transport2019.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VlanalyseActivity extends AppCompatActivity {

    @BindView(R.id.from)
    TextView from;
    @BindView(R.id.to)
    TextView to;
    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.calendar)
    ImageView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlanalyse);
        ButterKnife.bind(this);
        initChart();
    }

    private void initChart() {
        LineData data;
        String[] names = {"学院路", "联想路", "医院路", "幸福路", "环城快速路", "环城高速", "停车场"};
        List<Entry> list = new ArrayList<>();
        list.add(new Entry((float) (11 + 10 * Math.random()), 0));
        list.add(new Entry((float) (10 + 10 * Math.random()), 1));
        list.add(new Entry((float) (12 + 10 * Math.random()), 2));
        list.add(new Entry((float) (9 + 10 * Math.random()), 3));
        list.add(new Entry((float) (11 + 10 * Math.random()), 4));
        list.add(new Entry((float) (11 + 10 * Math.random()), 5));
        list.add(new Entry((float) (11 + 10 * Math.random()), 6));
        LineDataSet dataSet = new LineDataSet(list, "aaa" + 5);
        dataSet.setColor(Color.RED);
        data = new LineData(names, dataSet);

        chart.setData(data);
        chart.invalidate();
    }


    @OnClick({R.id.from, R.id.to, R.id.calendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.from:
                TimePickerBuilder builder = new TimePickerBuilder(VlanalyseActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        from.setText(format.format(date));
                        initChart();
                    }
                });
                builder.setCancelText("取消");
                builder.setSubmitText("确定");
                builder.build().show();
                break;
            case R.id.to:
                TimePickerBuilder builder2 = new TimePickerBuilder(VlanalyseActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        to.setText(format.format(date));
                        initChart();
                    }
                });
                builder2.setCancelText("取消");
                builder2.setSubmitText("确定");
                builder2.build().show();
                break;
            case R.id.calendar:

                break;
        }
    }


}
