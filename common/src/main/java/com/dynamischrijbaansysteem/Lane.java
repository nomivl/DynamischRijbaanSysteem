package com.dynamischrijbaansysteem;

import java.sql.Time;
import java.sql.Timestamp;

public class Lane {
    private int laneId;
    private String location;
    private int density;
    private Timestamp timestamp;
    private LaneStatus laneStatus;

    public Lane(int laneId, String location) {
        this.laneId = laneId;
        this.location = location;
        //this.density = density;
        //this.laneStatus = laneStatus;
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
