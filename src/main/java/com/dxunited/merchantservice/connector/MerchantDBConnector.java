package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MerchantDBConnector {
    @Autowired
    MerchantDBConfig merchantDBConfig;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private ObjectMapper mapper;

    public static MongoCollection<Document> merchantCollection = null;

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

    public MongoCollection<Document> getMerchantCollectionFromDb() {
        MongoDatabase dataBase = getDataBase();
        String mongoCollection = merchantDBConfig.getMerchantCollection();
        return dataBase.getCollection(mongoCollection);
    }
    @SneakyThrows
    public void saveMerchant(String createMerchant) {
        Map<String, String> merchantMap = mapper.readValue(createMerchant, HashMap.class);
        Document merchantDocument = new Document();
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        MongoCollection<Document> merchantCollection = getMerchantCollection();
        merchantRepository.insertMerchant(merchantCollection, merchantDocument);
    }
}
