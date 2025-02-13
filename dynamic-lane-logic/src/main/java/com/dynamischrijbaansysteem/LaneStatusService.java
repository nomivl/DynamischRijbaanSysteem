package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;

import java.util.List;
import java.util.stream.Collectors;

public class LaneStatusService {
    private final LaneService laneService;
    private final TrafficDensityService trafficDensityService;
    public LaneStatusService(LaneService laneservice, TrafficDensityService trafficDensityService) {
        this.laneService = laneservice;
        this.trafficDensityService = trafficDensityService;
    }

    /**
     * Bepaalt op basis van verkeersdichtheid of rijbanen geopend of gesloten moeten worden.
     *
     * @param trafficDensity De verkeersdichtheid (0-100%).
     * @return De aanbevolen actie: extra rijbaan openen, sluiten of niets doen.
     */
    public LaneStatus determineExtraLaneStatus(int trafficDensity){
        if (trafficDensity > 70) {
            return LaneStatus.OPEN_EXTRA_LANE;
        } else {
            return LaneStatus.CLOSE_EXTRA_LANE;
        }
    }
    // TO DO History via aparte method ophalen of niet?
    public List<Lane> getUpdatedLanes(){
        List<Lane> lanes = laneService.getLanes();
        return lanes.stream().map(trafficDensityService::getLaneLatestTrafficData).peek(lane -> {
            lane.setLaneStatus(determineExtraLaneStatus(lane.getDensity()));
            lane.setHistory(trafficDensityService.getHistoryFromDB(lane.getLaneId()));
        }).collect(Collectors.toList());
    }

    public Lane getUpdatedLane(Integer laneId) {
        Lane lane = trafficDensityService.getLaneLatestTrafficData(laneService.getLaneById(laneId));
        lane.setLaneStatus(determineExtraLaneStatus(lane.getDensity()));
        lane.setHistory(trafficDensityService.getHistoryFromDB(laneId));
        return lane;

    }

    public LaneStatus getLaneStatus(int laneId) {
        Lane lane  = laneService.getLaneById(laneId);
        int density = trafficDensityService.getTrafficDensity(laneId);
        return determineExtraLaneStatus(density);

    }

}
