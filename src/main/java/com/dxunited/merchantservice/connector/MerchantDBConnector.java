package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
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

    static MongoCollection<Document> merchantCollection = null;

    public MongoCollection<Document> getMerchantCollection() {
        if (merchantCollection == null)
            merchantCollection = getMerchantCollectionFromDb();
        return merchantCollection;
    }

    public MongoDatabase getDataBase(String host, int port, String database) {
        MongoClient mongoClient = new MongoClient(host, port);
        return mongoClient.getDatabase(database);
    }

    public MongoCollection<Document> getMerchantCollectionFromDb() {
        MongoDatabase dataBase = getDataBase(merchantDBConfig.getHost(), merchantDBConfig.getPort(), merchantDBConfig.getDatabase());
        String mongoCollection = merchantDBConfig.getMerchantCollection();
        return dataBase.getCollection(mongoCollection);
    }

    @SneakyThrows
    public void saveMerchant(String createMerchant) {
        Map<String, String> merchantMap = new ObjectMapper().readValue(createMerchant, HashMap.class);
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        insertMerchant(merchantCollection, merchantMap);
    }


    @SneakyThrows
    public void updateMerchant(String updateMerchant) {
        Map<String, String> merchantMap = new ObjectMapper().readValue(updateMerchant, HashMap.class);
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        merchantCollection.deleteOne(Filters.eq("merchantId", merchantMap.get("merchantId")));
        insertMerchant(merchantCollection, merchantMap);

    }

    private void insertMerchant(MongoCollection<Document> merchantCollection, Map<String, String> merchantMap) {
        Document merchantDocument = new Document();
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        merchantRepository.insertMerchant(merchantCollection, merchantDocument);
    }
}
