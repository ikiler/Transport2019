package com.example.ikiler.transport2019.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ikiler on 19-5-13.
 */
@Entity
public class Sense {
    /**
     * pm25 : 8
     * co2 : 5919
     * LightIntensity : 1711
     * humidity : 44
     * temperature : 28
     */

    @Id(autoincrement = true)
    private Long id;
    private int pm25;
    private int co2;
    private int LightIntensity;
    private int humidity;
    private int temperature;
    private String time;

    @Generated(hash = 1084828967)
    public Sense(Long id, int pm25, int co2, int LightIntensity, int humidity,
            int temperature, String time) {
        this.id = id;
        this.pm25 = pm25;
        this.co2 = co2;
        this.LightIntensity = LightIntensity;
        this.humidity = humidity;
        this.temperature = temperature;
        this.time = time;
    }

    @Generated(hash = 705303031)
    public Sense() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getLightIntensity() {
        return LightIntensity;
    }

    public void setLightIntensity(int LightIntensity) {
        this.LightIntensity = LightIntensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
