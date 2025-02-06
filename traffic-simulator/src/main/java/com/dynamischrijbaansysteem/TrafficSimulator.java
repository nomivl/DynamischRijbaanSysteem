package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.data.LaneService;
import com.dynamischrijbaansysteem.data.TrafficDensityService;

import java.util.List;
import java.util.Random;
public class TrafficSimulator {

    private final LaneService laneService;
    private final TrafficDensityService trafficDensityService;
    private final Random random;

    public TrafficSimulator (LaneService laneService, TrafficDensityService trafficDensityService) {
        this.trafficDensityService = trafficDensityService;
        this.laneService = laneService;
        this.random = new Random();
    }

    /**
     * Simuleert veranderende verkeersdrukte en slaat deze op in de database.
     */
    public void generateTrafficData() {
        List<Lane> lanes = laneService.getLanes();
        for (Lane lane: lanes){
            int density = random.nextInt(101);
            trafficDensityService.insertTrafficDensity(lane.getLaneId(), density);
            System.out.println("🚦 Nieuwe verkeersdata gegenereerd: " + lane.getLocation() + " -> " + density + "%");
        }
    }
    /*
    public static void main(String args[])throws InterruptedException {
        TrafficSimulator simulator = new TrafficSimulator();

        // Simuleer verkeersdata elke 5 seconden
        while (true) {
            simulator.generateTrafficData();
            Thread.sleep(5000);
        }
    }
    */

}
