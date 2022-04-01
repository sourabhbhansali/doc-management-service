package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class MerchantDBConnector {

    @Value("${mongo.database}")
    private String database;

    @Value("${mongo.merchant_collection}")
    private String merchant;

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Gson gson;

    static MongoCollection<Document> merchantCollection = null;

    public MongoCollection<Document> getMerchantCollection() {
        if (merchantCollection == null)
            merchantCollection = getMerchantCollectionFromDb();
        return merchantCollection;
    }

    public MongoDatabase getDataBase() {
        return mongoClient.getDatabase(database);
    }

    public MongoCollection<Document> getMerchantCollectionFromDb() {
        MongoDatabase dataBase = getDataBase();
        return dataBase.getCollection(merchant);
    }

    @SneakyThrows
    public void saveMerchant(Map<String, Object> merchantMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        insertMerchant(merchantCollection, merchantMap);
    }


    public void updateMerchant(Map<String, Object> merchantMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        merchantCollection.deleteOne(Filters.eq("merchantId",merchantMap.get("merchantId")));
        merchantMap.remove("_id");
        insertMerchant(merchantCollection, merchantMap);
    }

    private void insertMerchant(MongoCollection<Document> merchantCollection, Map<String, Object> merchantMap) {
        Document merchantDocument = new Document();
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        merchantRepository.insertMerchant(merchantCollection, merchantDocument);
    }
}
