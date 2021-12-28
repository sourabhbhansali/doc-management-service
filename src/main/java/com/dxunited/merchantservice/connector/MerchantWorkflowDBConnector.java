package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.config.MerchantDBConfig;
import com.dxunited.merchantservice.exception.ValidationException;
import com.dxunited.merchantservice.repository.MerchantWorkflowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Slf4j
@Component
public class MerchantWorkflowDBConnector {

    @Autowired
    MerchantDBConfig merchantDBConfig;
    @Autowired
    private MerchantWorkflowRepository merchantWorkflowRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private Gson gson;
    static MongoCollection<Document> merchantWorkflowCollection = null;

    public MongoCollection<Document> getMerchantWorkflowCollection() {
        if (merchantWorkflowCollection == null)
            merchantWorkflowCollection = getMerchantCollectionFromDb();
        return merchantWorkflowCollection;
    }

    public MongoDatabase getDataBase(String host, int port, String database) {
        MongoClient mongoClient = new MongoClient(host, port);
        return mongoClient.getDatabase(database);
    }

    public MongoCollection<Document> getMerchantCollectionFromDb() {
        MongoDatabase dataBase = getDataBase(merchantDBConfig.getHost(), merchantDBConfig.getPort(), merchantDBConfig.getDatabase());
        String mongoCollection = merchantDBConfig.getMerchantWorkflowCollection();
        return dataBase.getCollection(mongoCollection);
    }

    public void validateMerchantStatus(Map<String, String> merchantMap) {
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject();
        whereQuery.put("merchantId", merchantMap.get("merchantId"));
        whereQuery.put("provider", "manual");
        whereQuery.put("tenantId", merchantMap.get("tenantId"));
        // the fields to be returned from the query-only loginId, and remove _id
        fields.put("status", 1);
       List<Map<String,String>> merchantWorkflowData=executeQuery(whereQuery, fields);
        if (CollectionUtils.isNotEmpty(merchantWorkflowData)) {
            throw new ValidationException("There is already Ready for review state status for given merchant.");
        }
    }

    public List<Map<String, String>> executeQuery(BasicDBObject whereQuery, BasicDBObject fields) {
        MongoCollection<Document> merchantWorkflowCollection = this.getMerchantWorkflowCollection();
        FindIterable<Document> documents = merchantWorkflowCollection.find(whereQuery)
                .projection(fields).sort(new BasicDBObject("timeStamp", -1));
        return StreamSupport.stream(documents.spliterator(), false).map(document -> {
            return (Map<String, String>) gson.fromJson(document.toJson(), Map.class);
        }).collect(Collectors.toList());
    }

    public void saveMerchantWorkflow(Map<String, String> merchantMap) {

        MongoCollection<Document> merchantCollection = this.getMerchantWorkflowCollection();
        Document merchantDocument = new Document();
        merchantMap.put("status", "Ready for Review");
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        merchantWorkflowRepository.insertMerchant(merchantCollection, merchantDocument);
    }

    public List<Map<String, String>> getMerchantWfCollection(Map<String, String> merchantMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantWorkflowCollection();
        FindIterable<Document> documents = merchantCollection.find(Filters.eq("merchantId", merchantMap.get("merchantId")));
        List<Map<String, String>> workflowMap= StreamSupport.stream(documents.spliterator(), false).map(document -> {
            return (Map<String, String>) gson.fromJson(document.toJson(), Map.class);
        }).collect(Collectors.toList());

        if(CollectionUtils.isNotEmpty(workflowMap))
            return workflowMap;
        else
            throw new ValidationException("No record found for update status");
    }

    public void deleteFromMerchantWorkflow( Map<String, String> workFlowMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantWorkflowCollection();
        merchantCollection.deleteOne(Filters.eq("merchantId",workFlowMap.get("merchantId")));
    }

    public void saveMerchant(Map<String, String> merchantMap) {
    }
}
