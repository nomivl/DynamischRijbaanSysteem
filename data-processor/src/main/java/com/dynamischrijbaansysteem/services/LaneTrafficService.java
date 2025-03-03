package com.dynamischrijbaansysteem.services;
import com.dynamischrijbaansysteem.LaneStatus;
import com.dynamischrijbaansysteem.data.MongoDBConnection;
import com.dynamischrijbaansysteem.models.LaneTraffic;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .append("lanestatus", laneTraffic.getLaneStatus())
                .append("timestamp", laneTraffic.getTimestamp()));
        System.out.println("Insert lane traffic into DB: "+ laneTraffic.toString());
    }

    public LaneTraffic getLatestLaneTrafficById(Integer laneId) {
        Document document = collection.find(new Document("laneId", laneId))
                .sort(new Document("timestamp", -1))
                .first();
        if (document == null) {
            return null;
        }
        LaneTraffic laneTraffic = new LaneTraffic(
                document.getInteger("laneId"),
                document.getInteger("density"),
                document.getLong("timestamp"),
                LaneStatus.fromString(document.getString("lanestatus")),
                document.getString("comment"));
        return laneTraffic;
    }

    public List<LaneTraffic> getHistoryFromDB(Integer laneId) {
        List<LaneTraffic> history = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find(new Document("laneId", laneId)).sort(new Document("timestamp",-1)).cursor();

        while(cursor.hasNext()) {
            Document document = cursor.next();
            LaneTraffic laneTraffic = new LaneTraffic(
                    document.getInteger("laneId"),
                    document.getInteger("density"),
                    document.getLong("timestamp"),
                    LaneStatus.fromString(document.getString("lanestatus")),
                    document.getString("comment"));
            history.add(laneTraffic);
        }
        return history;
    }



}



