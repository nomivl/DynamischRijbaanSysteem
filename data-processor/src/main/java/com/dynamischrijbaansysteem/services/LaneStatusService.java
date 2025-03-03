package com.dynamischrijbaansysteem.services;
import com.dynamischrijbaansysteem.LaneStatus;

public class LaneStatusService {
    public LaneStatusService() {

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

}
