package com.dynamischrijbaansysteem.services;
import com.dynamischrijbaansysteem.data.MongoDBConnection;
import com.dynamischrijbaansysteem.models.LaneTraffic;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
public class LaneTrafficService {
    private final MongoCollection<Document> collection;
    public LaneTrafficService() {
        MongoDatabase database =  MongoDBConnection.getDatabase();
        this.collection = database.getCollection("traffic_data");
    }
    public void insertLaneTraffic (LaneTraffic laneTraffic) {
        collection.insertOne(new Document()
                .append("laneId", laneTraffic.getLaneId())
                .append("density", laneTraffic.getDensity())
                .append("timestamp", laneTraffic.getTimestamp()));
        System.out.println("Insert lane traffic into DB: "+ laneTraffic.toString());
    }

    public LaneTraffic getLaneTrafficById(Integer laneId) {
        Document document = collection.find(new Document("laneId", laneId))
                .sort(new Document("timestamp", -1))
                .first();
        LaneTraffic laneTraffic = new LaneTraffic(
                document.getInteger("laneId"),
                document.getInteger("density"),
                document.getLong("timestamp"));
        return laneTraffic;
    }
}



