package com.example.ikiler.transport2019.bean;

import java.util.List;

/**
 * Created by ikiler on 19-5-9.
 */

public class RoadStat {

    /**
     * id : 5
     * roads : [{"time":"Week0","state":5},{"time":"Week1","state":2},{"time":"Week2","state":1},{"time":"Week3","state":5},{"time":"Week4","state":3},{"time":"Week5","state":4},{"time":"Week6","state":1}]
     */

    private int id;
    private List<RoadsBean> roads;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RoadsBean> getRoads() {
        return roads;
    }

    public void setRoads(List<RoadsBean> roads) {
        this.roads = roads;
    }

    public static class RoadsBean {
        /**
         * time : Week0
         * state : 5
         */

        private String time;
        private int state;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
