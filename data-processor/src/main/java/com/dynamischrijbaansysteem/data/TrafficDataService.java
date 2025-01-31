package com.dynamischrijbaansysteem.data;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class TrafficDataService {

    private final MongoCollection<Document> collection;

    public TrafficDataService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.collection = database.getCollection("traffic");
    }

    public void insertTrafficData(String location, int density) {
        Document document = new Document()
                .append("location", location)
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
