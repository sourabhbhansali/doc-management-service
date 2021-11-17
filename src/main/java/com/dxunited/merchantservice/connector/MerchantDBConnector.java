package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MerchantDBConnector {
    @Autowired
    MerchantDBConfig merchantDBConfig;

    public MongoDatabase getDataBase() {
        String merchantHost = merchantDBConfig.getHost();
        int merchantPort = merchantDBConfig.getPort();
        String merchantDatabase = merchantDBConfig.getDatabase();
        MongoClient mongoClient = new MongoClient( merchantHost , merchantPort);
        return mongoClient.getDatabase(merchantDatabase);
    }

    public MongoCollection<Document> getMerchantCollection() {
        MongoDatabase dataBase = getDataBase();
        String mongoCollection = merchantDBConfig.getMerchantCollection();
        return dataBase.getCollection(mongoCollection);
    }

    public MongoCollection<Document> getReferenceCollection() {
        MongoDatabase dataBase = getDataBase();
        String mongoCollection = merchantDBConfig.getReferenceCollection();
        return dataBase.getCollection(mongoCollection);
    }

}
