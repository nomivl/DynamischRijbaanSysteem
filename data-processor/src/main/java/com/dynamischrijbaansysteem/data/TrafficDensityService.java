package com.dynamischrijbaansysteem.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class TrafficDensityService {

    private final MongoCollection<Document> collection;

    public TrafficDensityService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("traffic_data");
    }

   public int getTrafficDensity(int laneId){
        Document latestData = collection.find(new Document("laneId", laneId))
                .sort(new Document("timestamp", -1))
                .first();
        if (latestData != null) {
            return latestData.getInteger("density",0);
        }
        return 0;
   }

   public void insertTrafficDensity(int laneId, int density){
        Document document = new Document()
                .append("laneId", laneId)
                .append("density", density)
                .append("timestamp", System.currentTimeMillis());
        collection.insertOne(document);
   }

    public void getTrafficData() {
        for (Document doc : collection.find()){
            System.out.println(doc.toJson());
        }
    }
}
