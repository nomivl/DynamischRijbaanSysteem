package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.data.TrafficDensityService;
import java.util.Random;
public class TrafficSimulator {

    private final TrafficDensityService trafficDensityService;
    private final Random random;

    public TrafficSimulator () {
        this.trafficDensityService = new TrafficDensityService();
        this.random = new Random();
    }

    /**
     * Simuleert veranderende verkeersdrukte en slaat deze op in de database.
     */
    public void generateTrafficData() {
        String location = "A1 Highway";
        int density = random.nextInt(101);
        int laneId = random.nextInt(3);

        // TO DO misschien uitbreiden dat density van lanes tegelijk veranderen ipv 1 per keer
        trafficDensityService.insertTrafficDensity(laneId,density);

        System.out.println("🚦 Nieuwe verkeersdata gegenereerd: " + location + " -> " + density + "%");
    }

    public static void main(String args[])throws InterruptedException {
        TrafficSimulator simulator = new TrafficSimulator();

        // Simuleer verkeersdata elke 5 seconden
        while (true) {
            simulator.generateTrafficData();
            Thread.sleep(5000);
        }
    }
}
