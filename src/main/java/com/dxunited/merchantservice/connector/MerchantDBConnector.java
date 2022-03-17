package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
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
import org.springframework.stereotype.Component;

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
    @Autowired
    private Gson gson;
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
/*
    public List<Map<String, String>> getMerchantVersion(Map<String, String> merchantMap) {
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject();
        whereQuery.put("merchantId", merchantMap.get("merchantId"));
        whereQuery.put("provider", "manual");
        whereQuery.put("clientId", merchantMap.get("clientId"));
        whereQuery.put("status", "Inprogress");
        fields.put("version", 1);
        return executeQueryByFilter(whereQuery, fields);
    }

    public List<Map<String, String>> executeQueryByFilter(BasicDBObject whereQuery, BasicDBObject fields) {
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        FindIterable<Document> documents = merchantCollection.find(whereQuery)
                .projection(fields).sort(new BasicDBObject("timeStamp", -1));
        return StreamSupport.stream(documents.spliterator(), false).map(document -> {
            return (Map<String, String>) gson.fromJson(document.toJson(), Map.class);
        }).collect(Collectors.toList());
    }*/
}
