package com.dynamischrijbaansysteem.data;
import com.dynamischrijbaansysteem.Lane;
import com.dynamischrijbaansysteem.LaneStatus;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LaneService {
    private final MongoCollection<Document> laneCollection;
    public LaneService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.laneCollection = database.getCollection("lanes");
    }
    public void insertLane(int laneId, String location, Boolean dynamicLaneControl){
        Document document = new Document()
                .append("laneId", laneId)
                .append("location", location)
                .append("dynamicLaneControl", dynamicLaneControl)
                .append("created", System.currentTimeMillis());
        laneCollection.insertOne(document);
    }

    public List<Lane> getLanes(){
        List<Lane> lanes = new ArrayList<>();
        for (Document doc: laneCollection.find()){
            System.out.println(doc.toJson());

            int laneId = doc.getInteger("laneId");
            String location = doc.getString("location");
            Boolean dynamicLaneControl = doc.getBoolean("dynamicLaneControl");
            lanes.add(new Lane(laneId, location, dynamicLaneControl));
        }
        return lanes;
    }

    public Lane getLaneById(int laneId) {
        Document laneDoc = laneCollection.find(new Document("laneId", laneId)).first();

        if(laneDoc != null){
            String location = laneDoc.getString("location");
            Boolean dynamicLaneControl = laneDoc.getBoolean("dynamicLaneControl");
            return new Lane(laneId,location,dynamicLaneControl);
        } else {
            throw new IllegalArgumentException("Lane with ID " + laneId + " not found.");
        }
    }

    public void updateDynamicLaneControl (Integer laneId, Boolean dynamicLaneControl) {
        laneCollection.updateOne(eq("laneId", laneId), new Document("$set", new Document("dynamicLaneControl", dynamicLaneControl)));
        System.out.print("DynamicLaneControl updated for "+ laneId.toString() + ":" + dynamicLaneControl.toString());
    }

}
