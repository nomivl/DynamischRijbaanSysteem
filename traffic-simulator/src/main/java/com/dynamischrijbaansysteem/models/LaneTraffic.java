package com.dynamischrijbaansysteem.models;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class LaneTraffic implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer laneId;
    private Integer density;
    private Long timestamp;

    public LaneTraffic(Integer laneId, Integer density, Long timestamp) {
        this.laneId = laneId;
        this.density = density;
        this.timestamp = timestamp;
    }

    public Integer getLaneId() {
        return laneId;
    }

    public void setLaneId(Integer laneId) {
        this.laneId = laneId;
    }

    public Integer getDensity() {
        return density;
    }

    public void setDensity(Integer density) {
        this.density = density;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return "Lane " + this.laneId + " has a " + this.density + "% " + "density " +  "at " + new Time(this.timestamp) + new Date(this.timestamp);
    }
}
