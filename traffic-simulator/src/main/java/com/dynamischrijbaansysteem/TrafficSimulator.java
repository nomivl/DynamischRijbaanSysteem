package com.dynamischrijbaansysteem;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
@Component(immediate = true)
public class TrafficSimulator {

    /**
     * Simuleert veranderende verkeersdrukte en slaat deze op in de database.
     */
    @Activate
    public void start() {
        System.out.println("🚦 Traffic Simulator gestart!");
    }


}
