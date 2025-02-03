package com.dynamischrijbaansysteem.data;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class TrafficDensityServiceTest {
    @Test
    public void testInsertandRetrieveTrafficDensity(){
        TrafficDensityService service = new TrafficDensityService();
        service.insertTrafficDensity(1,75);
        int density = service.getTrafficDensity(1);
        System.out.println("Opgehaalde verkeersdichtheid: " + density);
        assertEquals(75,density,"Er wordt een verkeersdichtheid van 75 verwacht");
    }
}
