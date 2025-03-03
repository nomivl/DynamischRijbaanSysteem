package com.dynamischrijbaansysteem;

import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.models.LaneTraffic;
import com.dynamischrijbaansysteem.services.LaneService;
import com.dynamischrijbaansysteem.services.LaneTrafficService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LaneManager {
    private final LaneService laneService;
    private final LaneTrafficService laneTrafficService;

    public LaneManager (LaneService laneService, LaneTrafficService laneTrafficService) {
        this.laneService = laneService;
        this.laneTrafficService = laneTrafficService;
    }

    public List<Lane> getLaneData(Boolean includeHistory){
        List<Lane> lanes = laneService.getLanes();
        for (Lane lane: lanes) {
           LaneTraffic laneTraffic = laneTrafficService.getLatestLaneTrafficById(lane.getLaneId());
           lane.setLaneTraffic(laneTraffic);
           if (includeHistory) {
               lane.setHistory(laneTrafficService.getHistoryFromDB(lane.getLaneId()));
           }

        }
        return lanes;
    }
    public Lane getLaneDetails(Lane lane){
        lane.setHistory(laneTrafficService.getHistoryFromDB(lane.getLaneId()));
        return lane;
    }

}
