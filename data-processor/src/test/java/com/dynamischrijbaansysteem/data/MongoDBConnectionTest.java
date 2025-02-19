package com.dynamischrijbaansysteem.data;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MongoDBConnectionTest {
    @Test
    public void testInsertAndRetrieveTrafficData() {
       TrafficDensityService trafficDensityService = new TrafficDensityService();
       // voeg testdata toe
        trafficDensityService.insertTrafficDensity(1, 85);
        //haal data op en toon deze
        trafficDensityService.getTrafficData();
    }

    @Test
    public void testInsertAndRetrieveLanes(){
       LaneService laneService = new LaneService();
       laneService.insertLane(1,"A1 Highway", true);
        laneService.insertLane(2,"A2 Highway", true);
        laneService.insertLane(3,"A3 Highway", true);
        //haal data op en toon deze
       laneService.getLanes();
    }

}
