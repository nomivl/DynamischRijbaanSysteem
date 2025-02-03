package com.dynamischrijbaansysteem.data;
import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatus;

import java.util.ArrayList;
import java.util.List;

public class LaneStatusService {
    private final LaneService laneService;
    private final TrafficDensityService trafficDensityService;
    public LaneStatusService() {
        this.laneService = new LaneService();
        this.trafficDensityService = new TrafficDensityService();
    }

    /**
     * Bepaalt op basis van verkeersdichtheid of rijbanen geopend of gesloten moeten worden.
     *
     * @param trafficDensity De verkeersdichtheid (0-100%).
     * @return De aanbevolen actie: extra rijbaan openen, sluiten of niets doen.
     */
    public LaneStatus determineLaneStatus(int trafficDensity){
        if (trafficDensity > 80) {
            return LaneStatus.OPEN_EXTRA_LANE;
        } else if (trafficDensity < 30) {
            return LaneStatus.CLOSE_EXTRA_LANE;
        } else {
            return LaneStatus.KEEP_LANES_SAME;
        }
    }

    public List<Lane> getUpdatedLanes(){
        List<Lane> lanes = laneService.getLanes();
        for (Lane lane : lanes) {
            int density = trafficDensityService.getTrafficDensity(lane.getLaneId());
            lane.setDensity(density);
            lane.setLaneStatus(determineLaneStatus(density));
        }
        return lanes;
    }
}
