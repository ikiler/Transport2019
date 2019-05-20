package com.example.ikiler.transport2019.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ikiler on 19-5-12.
 */

public class LogSearchBean {
    private String tvCo2;
    private String tvHum;
    private String tvPm254;
    private String tvLight;
    private String tvTemp;
    private String tvTime;

    public LogSearchBean(String tvCo2, String tvHum, String tvPm254, String tvLight, String tvTemp, String tvTime) {
        this.tvCo2 = tvCo2;
        this.tvHum = tvHum;
        this.tvPm254 = tvPm254;
        this.tvLight = tvLight;
        this.tvTemp = tvTemp;
        this.tvTime = tvTime;
    }

    public String getTvCo2() {
        return tvCo2;
    }

    public void setTvCo2(String tvCo2) {
        this.tvCo2 = tvCo2;
    }

    public String getTvHum() {
        return tvHum;
    }

    public void setTvHum(String tvHum) {
        this.tvHum = tvHum;
    }

    public String getTvPm254() {
        return tvPm254;
    }

    public void setTvPm254(String tvPm254) {
        this.tvPm254 = tvPm254;
    }

    public String getTvLight() {
        return tvLight;
    }

    public void setTvLight(String tvLight) {
        this.tvLight = tvLight;
    }

    public String getTvTemp() {
        return tvTemp;
    }

    public void setTvTemp(String tvTemp) {
        this.tvTemp = tvTemp;
    }

    public String getTvTime() {
        return tvTime;
    }

    public void setTvTime(String tvTime) {
        this.tvTime = tvTime;
    }
}
