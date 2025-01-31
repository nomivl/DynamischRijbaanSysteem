package com.dynamischrijbaansysteem;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LaneManagerTest {

    private final LaneManager laneManager = new LaneManager();
    @Test
    public void testDetermineLaneStatusWithRealData() {
        LaneManager.LaneStatus status = laneManager.determineStatusFromDatabase();
        assertNotNull(status, "Lane status mag niet null zijn.");
        System.out.println("🚦 LaneManager beslissing op basis van MongoDB-data: " + status);
    }
}
