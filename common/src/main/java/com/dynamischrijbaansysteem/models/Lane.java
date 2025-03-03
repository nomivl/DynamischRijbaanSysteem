package com.dynamischrijbaansysteem.models;

import com.dynamischrijbaansysteem.models.LaneTraffic;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Lane {
    private int laneId;
    private String location;
    private LaneTraffic laneTraffic;
    private Boolean dynamicLaneControl;
    private List<Map<String,LaneTraffic>> history;
    public Lane(int laneId, String location) {
        this.laneId = laneId;
        this.location = location;
        this.dynamicLaneControl = true;
    }
    public Lane(int laneId, String location, Boolean dynamicLaneControl) {
        this.laneId = laneId;
        this.location = location;
        this.dynamicLaneControl = dynamicLaneControl;
    }

    public Lane(int laneId, String location, LaneTraffic laneTraffic) {
        this.laneId = laneId;
        this.location = location;
        this.laneTraffic = laneTraffic;
    }

    public Integer getLaneId() {
        return laneId;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getDynamicLaneControl(){
        return dynamicLaneControl;
    }
    public void setDynamicLaneControl(Boolean dynamicLaneControl){
        this.dynamicLaneControl = dynamicLaneControl;
    }
    public void setHistory(List<Map<String, LaneTraffic>> history) {
        this.history = history;
    }

    public List<Map<String, LaneTraffic>> getHistory() {
        return history;
    }

    public LaneTraffic getLaneTraffic() {
        return laneTraffic;
    }

    public void setLaneTraffic(LaneTraffic laneTraffic) {
        this.laneTraffic = laneTraffic;
    }

    @Override
    public String toString() {
        return "Lane{" +
                "laneId=" + laneId +
                ", location='" + location + '\'' +
                ", density=" + laneTraffic.getDensity() +
                ", laneStatus=" + laneTraffic.getLaneStatus() +
                '}';
    }
}
