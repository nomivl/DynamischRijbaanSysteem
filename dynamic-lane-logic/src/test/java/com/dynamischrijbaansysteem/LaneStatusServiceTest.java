package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LaneStatusServiceTest {
    TrafficDensityService trafficDensityService = new TrafficDensityService();
    LaneService laneService = new LaneService();
    LaneStatusService laneStatusService = new LaneStatusService(laneService, trafficDensityService);

    @Test
    public void testOpenExtraLane() {
        assertEquals(LaneStatus.OPEN_EXTRA_LANE, laneStatusService.determineExtraLaneStatus(85),"Bij een verkeersdichtheid van 85% moet een extra rijbaan worden geopend.");
    }

    @Test
    public void testCloseExtraLane() {
        assertEquals(LaneStatus.CLOSE_EXTRA_LANE, laneStatusService.determineExtraLaneStatus(25), "Bij een verkeersdichtheid van 25% moet een extra rijbaan worden gesloten.");
    }
    @Test
    public void testDetermineLaneStatus(){

        LaneStatus laneStatus = laneStatusService.determineExtraLaneStatus(80);
        assertNotNull(laneStatus, "Lane status mag niet null zijn.");
        System.out.println("🚦 LaneManager beslissing op basis van MongoDB-data: " + laneStatus);
    }

    @Test
    public void testGetLaneStatus() {
        laneService.insertLane(10 , "Test Lane", true);
        System.out.println(laneStatusService.getLaneStatus(10));

    }
}
