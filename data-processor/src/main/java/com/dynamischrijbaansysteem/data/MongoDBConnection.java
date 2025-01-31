package com.dynamischrijbaansysteem.data;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class MongoDBConnection {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "traffic_data";
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private static final Logger logger = LoggerFactory.getLogger(MongoDBConnection.class);

    public static MongoDatabase getDatabase() {
        if(database == null){
            mongoClient = MongoClients.create(CONNECTION_STRING);
            database = mongoClient.getDatabase(DATABASE_NAME);
        }
        return database;
    }

    public static void closeConnection() {
        if(mongoClient != null) {
            mongoClient.close();
        }
    }

    public void connectToDatabase(){
        logger.info("verbinding maken met de database...");
        try {
            Thread.sleep(1000);
            logger.info("Succesvol verbonden met de database.");
        } catch (InterruptedException e){
            logger.error("Fout tijdens verbinden met de database", e);
        }
    }

    public static void main(String[] args){
        MongoDBConnection connection = new MongoDBConnection();
        connection.connectToDatabase();
    }

}
