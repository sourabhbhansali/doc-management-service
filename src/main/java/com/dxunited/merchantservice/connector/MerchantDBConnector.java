package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.exception.ValidationException;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        merchantMap.put("status", "Active");
        insertMerchant(merchantCollection, merchantMap);
    }


    public void updateMerchant(Map<String, Object> merchantMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        merchantCollection.deleteOne(Filters.eq("merchantId", merchantMap.get("merchantId")));
        merchantMap.remove("_id");
        insertMerchant(merchantCollection, merchantMap);
    }

    public void checkMerchantRankUnique(Integer merchantRank) {
        MongoCollection<Document> merchantCollection = this.getMerchantCollection();
        convertDocumentToMerchants(merchantCollection.find(Filters.in("merchantRank", merchantRank)));
    }

    private void convertDocumentToMerchants(FindIterable<Document> documents) {
        StreamSupport.stream(documents.spliterator(), false).forEach(document -> {
            String merchantString = document.toJson();
            if (StringUtils.isNotEmpty(merchantString)) {
                throw new ValidationException(
                        "Merchant Rank should be unique, given rank already exist for other merchant");
            }
        });
    }

    private void insertMerchant(MongoCollection<Document> merchantCollection, Map<String, Object> merchantMap) {
        Document merchantDocument = new Document();
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        merchantRepository.insertMerchant(merchantCollection, merchantDocument);
    }

    public Integer getNextSequence(String name) {
        MongoDatabase db = getDataBase();
        MongoCollection<Document> collection = db.getCollection("sequenceGenerator");
        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
        Document document = collection.findOneAndUpdate(find, update);
        Integer seq = (Integer) document.get("seq");
        return seq;
    }
}
