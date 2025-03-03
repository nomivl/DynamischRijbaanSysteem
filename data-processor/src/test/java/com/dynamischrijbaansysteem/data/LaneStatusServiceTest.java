package com.dynamischrijbaansysteem.data;
import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.services.LaneStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LaneStatusServiceTest {
    LaneStatusService laneStatusService = new LaneStatusService();

    @Test
    public void testOpenExtraLane() {
        Assertions.assertEquals(LaneStatus.OPEN_EXTRA_LANE, laneStatusService.determineExtraLaneStatus(85),"Bij een verkeersdichtheid van 85% moet een extra rijbaan worden geopend.");
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

}
