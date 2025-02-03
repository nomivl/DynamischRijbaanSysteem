package com.dynamischrijbaansysteem.data;
import com.dynamischrijbaansysteem.LaneStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LaneStatusServiceTest {
    LaneStatusService laneStatusService = new LaneStatusService();
    @Test
    public void testOpenExtraLane() {
        assertEquals(LaneStatus.OPEN_EXTRA_LANE, laneStatusService.determineLaneStatus(85),"Bij een verkeersdichtheid van 85% moet een extra rijbaan worden geopend.");
    }

    @Test
    public void testCloseExtraLane() {
        assertEquals(LaneStatus.CLOSE_EXTRA_LANE, laneStatusService.determineLaneStatus(25), "Bij een verkeersdichtheid van 25% moet een extra rijbaan worden gesloten.");
    }

    @Test
    public void testKeepLaneSame() {
        assertEquals(LaneStatus.KEEP_LANES_SAME,laneStatusService.determineLaneStatus(50),"Bij een verkeersdichtheid van 50% moeten de rijbanen ongewijzigd blijven");
    }

    @Test
    public void testDetermineLaneStatus(){

        LaneStatus laneStatus = laneStatusService.determineLaneStatus(80);
        assertNotNull(laneStatus, "Lane status mag niet null zijn.");
        System.out.println("🚦 LaneManager beslissing op basis van MongoDB-data: " + laneStatus);
    }
}
