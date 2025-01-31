package com.dynamischrijbaansysteem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LaneManagerTest {
    private final LaneManager laneManager = new LaneManager();

    @Test
    public void testOpenExtraLane() {
        assertEquals(LaneManager.LaneStatus.OPEN_EXTRA_LANE, laneManager.determineLaneStatus(85),"Bij een verkeersdichtheid van 85% moet een extra rijbaan worden geopend.");
    }

    @Test
    public void testCloseExtraLane() {
        assertEquals(LaneManager.LaneStatus.CLOSE_EXTRA_LANE, laneManager.determineLaneStatus(25), "Bij een verkeersdichtheid van 25% moet een extra rijbaan worden gesloten.");
    }

    @Test
    public void testKeepLaneSame() {
        assertEquals(LaneManager.LaneStatus.KEEP_LANES_SAME,laneManager.determineLaneStatus(50),"Bij een verkeersdichtheid van 50% moeten de rijbanen ongewijzigd blijven");
    }

    @Test
    public void testDetermineLaneStatusFromDatabase() {
        // roep de methode aan die data uit MongoDB haalt en de rijbaanstatus bepaalt
        LaneManager.LaneStatus status = laneManager.determineStatusFromDatabase();

        //Controleer of de waarde geldig is
        assertNotNull(status, "Lane status mag niet null zijn.");
    }
}
