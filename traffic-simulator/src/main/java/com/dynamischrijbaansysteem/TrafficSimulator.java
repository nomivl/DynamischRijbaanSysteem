package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.data.TrafficDataService;
import java.util.Random;
public class TrafficSimulator {

    private final TrafficDataService trafficDataService;
    private final Random random;

    public TrafficSimulator () {
        this.trafficDataService = new TrafficDataService();
        this.random = new Random();
    }

    /**
     * Simuleert veranderende verkeersdrukte en slaat deze op in de database.
     */
    public void generateTrafficData() {
        String location = "A1 Highway";
        int density = random.nextInt(101);
        trafficDataService.insertTrafficData(location,density);

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
