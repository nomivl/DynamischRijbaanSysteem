package com.dynamischrijbaansysteem;

public class Lane {
    private int laneId;
    private String location;
    private int density;
    private LaneStatus laneStatus;
    public Lane(int laneId, String location) {
        this.laneId = laneId;
        this.location = location;
        //this.density = density;
        //this.laneStatus = laneStatus;
    }

    public int getLaneId() {
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

    public int getDensity() {
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
