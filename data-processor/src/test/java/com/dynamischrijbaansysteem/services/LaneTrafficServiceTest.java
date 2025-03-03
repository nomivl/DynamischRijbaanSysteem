package com.dynamischrijbaansysteem.services;

import com.dynamischrijbaansysteem.models.LaneTraffic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LaneTrafficServiceTest {
    LaneTrafficService laneTrafficService = new LaneTrafficService();
    @Test
    public void testInsertTrafficData(){
        LaneTraffic laneTraffic = new LaneTraffic(1,10,System.currentTimeMillis());
        laneTrafficService.insertLaneTraffic(laneTraffic);
        assertTrue(laneTrafficService.getLatestLaneTrafficById(1).toString().equals(laneTraffic.toString()),"Data komt niet overheen");

    }
}
