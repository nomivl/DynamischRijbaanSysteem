package com.dynamischrijbaansysteem.data;

import com.dynamischrijbaansysteem.Lane;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficDensityService {

    private final MongoCollection<Document> collection;

    public TrafficDensityService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("traffic_data");
    }

   public Lane getLaneLatestTrafficData(Lane lane){
        Document latestData = collection.find(new Document("laneId", lane.getLaneId()))
                .sort(new Document("timestamp", -1))
                .first();
        if (latestData != null) {
            lane.setDensity(latestData.getInteger("density",0));
            lane.setTimestamp(new Timestamp(latestData.getLong("timestamp")));
            return lane;
        }
        return null;
   }

   public List<Map<String, Object>> getHistoryFromDB(int id) {
       List<Map<String, Object>> history = new ArrayList<>();
       try (MongoCursor<Document> cursor = collection.find(new Document("laneId", id)).sort(new Document("timestamp",-1)).iterator()) {
           while (cursor.hasNext()) {
               Document doc = cursor.next();
               Map<String, Object> entry = new HashMap<>();
               entry.put("density", doc.getInteger("density"));
               entry.put("timestamp", new Timestamp(doc.getLong("timestamp")));
               history.add(entry);
           }
       }
       return history;
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
