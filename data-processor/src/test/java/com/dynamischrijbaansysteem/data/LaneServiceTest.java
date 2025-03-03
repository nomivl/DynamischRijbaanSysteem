package com.dynamischrijbaansysteem.data;

import com.dynamischrijbaansysteem.models.Lane;
import com.dynamischrijbaansysteem.services.LaneService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LaneServiceTest {
    private LaneService laneService = new LaneService();
    @Test
    public void updateDynamicLaneControlTest() {
        this.laneService.insertLane(0, "Test Lane", true);
        this.laneService.updateDynamicLaneControl(0,false);
        Lane lane = this.laneService.getLaneById(0);
        assertEquals(false,lane.getDynamicLaneControl(),"DynamicLaneControl moet False zijn");
    }


}
