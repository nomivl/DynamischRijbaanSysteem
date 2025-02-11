package com.dynamischrijbaansysteem;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Lane {
    private int laneId;
    private String location;
    private int density;
    private Timestamp timestamp;
    private LaneStatus laneStatus;
    private List<Map<String,Object>> history;
    public Lane(int laneId, String location) {
        this.laneId = laneId;
        this.location = location;
    }

    public Lane(int laneId, String location, int density, LaneStatus laneStatus) {
        this.laneId = laneId;
        this.location = location;
        this.density = density;
        this.laneStatus = laneStatus;
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

    public Integer getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public LaneStatus getLaneStatus() {
        return laneStatus;
    }

    public void setLaneStatus(LaneStatus laneStatus) {
        this.laneStatus = laneStatus;
    }
    public Timestamp getTimestamp(){
        return this.timestamp;
    }
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }
    public List<Map<String, Object>> getHistory() {
        return history;
    }

    public void setHistory(List<Map<String, Object>> history) {
        this.history = history;
    }
    @Override
    public String toString() {
        return "Lane{" +
                "laneId=" + laneId +
                ", location='" + location + '\'' +
                ", density=" + density +
                ", laneStatus=" + laneStatus +
                '}';
    }
}
