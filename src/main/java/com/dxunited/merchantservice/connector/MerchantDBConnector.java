package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MerchantDBConnector {
    @Autowired
    MerchantDBConfig merchantDBConfig;
    static MongoCollection<Document> merchantCollection = null;

    public MongoCollection<Document> getMerchantCollection() {
        if (merchantCollection == null)
            merchantCollection = getMerchantCollectionFromDb();
        return merchantCollection;
    }
    public MongoDatabase getDataBase() {
        String merchantHost = merchantDBConfig.getHost();
        int merchantPort = merchantDBConfig.getPort();
        String merchantDatabase = merchantDBConfig.getDatabase();
        MongoClient mongoClient = new MongoClient( merchantHost , merchantPort);
        return mongoClient.getDatabase(merchantDatabase);
    }

    private MongoCollection<Document> getMerchantCollectionFromDb() {
        MongoDatabase dataBase = getDataBase();
        String mongoCollection = merchantDBConfig.getMerchantCollection();
        return dataBase.getCollection(mongoCollection);
    }

}
