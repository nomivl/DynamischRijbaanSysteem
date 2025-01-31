package com.dynamischrijbaansysteem.data;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MongoDBConnectionTest {
    @Test
    public void testInsertAndRetrieve() {
       TrafficDataService service = new TrafficDataService();

       // voeg testdata toe
        service.insertTrafficData("A1 Highway", 85);

        //haal data op en toon deze
        service.getTrafficData();
    }

}
