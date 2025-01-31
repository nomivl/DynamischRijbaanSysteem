package com.dynamischrijbaansysteem;
import com.dynamischrijbaansysteem.data.MongoDBConnection;
import com.dynamischrijbaansysteem.data.TrafficDataService;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;


public class LaneManager {
    private final TrafficDataService trafficDataService;
    public LaneManager(){
        this.trafficDataService = new TrafficDataService();
    }
    /**
     * Haalt de laatste verkeersdichtheid op en bepaalt de rijbaanstatus.
     *
     * @return De aanbevolen actie: extra rijbaan openen, sluiten of niets doen.
     */
    public int getLatestTrafficDensity () {
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("traffic");
        try (MongoCursor<Document> cursor = collection.find().sort(new Document("timestamp",-1)).limit(1).iterator()){
            if (cursor.hasNext()) {
                Document latestTraffic = cursor.next();
                return latestTraffic.getInteger("density");
            }
        }
        return 50; //standaardwaarde als er geen data is.
    }
    /**
     * Ophalen van de nieuwste verkeersdichtheid uit de database.
     *
     * @return De meest recente verkeersdichtheid (0-100%).
     */
    public LaneStatus determineStatusFromDatabase(){
        int latestTrafficDensity = getLatestTrafficDensity();
        return determineLaneStatus(latestTrafficDensity);
    }

    public enum LaneStatus {
        OPEN_EXTRA_LANE,
        CLOSE_EXTRA_LANE,
        KEEP_LANES_SAME
    }

    /**
     * Bepaalt op basis van verkeersdichtheid of rijbanen geopend of gesloten moeten worden.
     *
     * @param trafficDensity De verkeersdichtheid (0-100%).
     * @return De aanbevolen actie: extra rijbaan openen, sluiten of niets doen.
     */
    public LaneStatus determineLaneStatus(int trafficDensity){
        if (trafficDensity > 80) {
            return LaneStatus.OPEN_EXTRA_LANE;
        } else if (trafficDensity < 30) {
            return LaneStatus.CLOSE_EXTRA_LANE;
        } else {
            return LaneStatus.KEEP_LANES_SAME;
        }
    }
}
