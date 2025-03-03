package com.dynamischrijbaansysteem.models;

import com.dynamischrijbaansysteem.LaneStatus;

import java.io.Serializable;
import java.sql.Time;

public class LaneTraffic implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer laneId;
    private Integer density;
    private Long timestamp;
    private LaneStatus laneStatus;
    private String comment;

    // Lege constructor voor JSON-serializer
    public LaneTraffic () {

    }
    public LaneTraffic(Integer laneId, Integer density, Long timestamp, LaneStatus laneStatus, String comment) {
        this.laneId = laneId;
        this.density = density;
        this.timestamp = timestamp;
        this.laneStatus = laneStatus;
        this.comment = comment;
    }

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
    public LaneStatus getLaneStatus() {
        return laneStatus;
    }

    public void setLaneStatus(LaneStatus laneStatus) {
        this.laneStatus = laneStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString(){
        return "Lane " + this.laneId + " has a " + this.density + "% " + "density " +  "at " + new Time(this.timestamp);
    }
}
