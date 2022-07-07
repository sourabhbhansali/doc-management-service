package com.dxunited.merchantservice.connector;

import com.dxunited.merchantservice.constants.MerchantConstant;
import com.dxunited.merchantservice.exception.ValidationException;
import com.dxunited.merchantservice.repository.MerchantWorkflowRepository;
import com.dxunited.merchantservice.utils.MerchantUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Component
public class MerchantWorkflowDBConnector {

    @Value("${mongo.database}")
    private String database;

    @Value("${mongo.merchant_workflow_collection}")
    private String merchantWorkflow;

    @Autowired
    private MongoClient mongoClient;
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

    public MongoDatabase getDataBase() {
        return mongoClient.getDatabase(database);
    }

    public MongoCollection<Document> getMerchantCollectionFromDb() {
        MongoDatabase dataBase = getDataBase();
        return dataBase.getCollection(merchantWorkflow);
    }

    public boolean isMerchantAlreadyInReview(String merchantId, String clientId) {
        BasicDBObject whereQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject();
        whereQuery.put("merchantId", merchantId);
        whereQuery.put("clientId", clientId);
        List<Map<String, String>> merchantWorkflowData = executeQuery(whereQuery, fields);
        if (CollectionUtils.isNotEmpty(merchantWorkflowData)) {
            return true;
        }
        return false;
    }

    public List<Map<String, String>> executeQuery(BasicDBObject whereQuery, BasicDBObject fields) {
        MongoCollection<Document> merchantWorkflowCollection = this.getMerchantWorkflowCollection();
        FindIterable<Document> documents = merchantWorkflowCollection.find(whereQuery)
                .projection(fields).sort(new BasicDBObject("timeStamp", -1));
        return StreamSupport.stream(documents.spliterator(), false).map(document -> {
            return (Map<String, String>) gson.fromJson(document.toJson(), Map.class);
        }).collect(Collectors.toList());
    }

    public void saveMerchantWorkflow(Map<String, Object> merchantMap) {

        MongoCollection<Document> merchantCollection = this.getMerchantWorkflowCollection();
        Document merchantDocument = new Document();
        merchantMap.put("status", MerchantConstant.CREATED);
        merchantMap.put("createdDate", MerchantUtil.getCurrentDate());
        merchantMap.put("modifiedDate", MerchantUtil.getCurrentDate());
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        merchantWorkflowRepository.insertMerchant(merchantCollection, merchantDocument);
    }

    public Map<String, Object> getMerchantWfCollection(Map<String, Object> merchantMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantWorkflowCollection();
        FindIterable<Document> documents = merchantCollection.find(Filters.eq(
                "merchantId", merchantMap.get("merchantId")));
        List<Map<String, Object>> workflowMap = StreamSupport.stream(documents.spliterator(), false).map(document -> {
            return (Map<String, Object>) gson.fromJson(document.toJson(), Map.class);
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(workflowMap))
            return workflowMap.get(0);
        else
            throw new ValidationException("No record found for update status");
    }

    public void deleteFromMerchantWorkflow(Map<String, Object> workFlowMap) {
        MongoCollection<Document> merchantCollection = this.getMerchantWorkflowCollection();
        merchantCollection.deleteOne(Filters.eq("merchantId", workFlowMap.get("merchantId")));
    }
}
