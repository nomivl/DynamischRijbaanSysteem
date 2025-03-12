package com.dynamischrijbaansysteem.models;

import com.dynamischrijbaansysteem.models.LaneTraffic;
import javafx.beans.property.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Lane {
    private int laneId;
    private String location;
    private ObjectProperty<LaneTraffic> laneTraffic = new SimpleObjectProperty<>();
    private BooleanProperty dynamicLaneControl = new SimpleBooleanProperty();
    private List<LaneTraffic> history;
    public Lane(int laneId, String location) {
        this.laneId = laneId;
        this.location = location;
        this.dynamicLaneControl.set(true);
    }
    public Lane(int laneId, String location, Boolean dynamicLaneControl) {
        this.laneId = laneId;
        this.location = location;
        this.dynamicLaneControl.set(dynamicLaneControl);
    }

    public Lane(int laneId, String location, LaneTraffic laneTraffic) {
        this.laneId = laneId;
        this.location = location;
        this.laneTraffic.set(laneTraffic);
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
        this.location = location;;
    }

    public Boolean getDynamicLaneControl(){
        return dynamicLaneControl.get();
    }
    public void setDynamicLaneControl(Boolean dynamicLaneControl){
        this.dynamicLaneControl.set(dynamicLaneControl);
    }
    public void setHistory(List<LaneTraffic> history) {
        this.history = history;
    }

    public List<LaneTraffic> getHistory() {
        return history;
    }

    public LaneTraffic getLaneTraffic() {
        return laneTraffic.get();
    }

    public void setLaneTraffic(LaneTraffic laneTraffic) {
        this.laneTraffic.set(laneTraffic);
    }


    public BooleanProperty dynamicLaneControlProperty(){
        return dynamicLaneControl;
    }

    public ObjectProperty laneTrafficProperty(){
        return laneTraffic;
    }
    @Override
    public String toString() {
        return "Lane{" +
                "laneId=" + laneId +
                ", location='" + location + '\'' +
                ", density=" + laneTraffic.get().getDensity() +
                ", laneStatus=" + laneTraffic.get().getLaneStatus() +
                '}';
    }
}
