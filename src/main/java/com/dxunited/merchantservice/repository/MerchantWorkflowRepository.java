package com.dxunited.merchantservice.repository;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class MerchantWorkflowRepository {
    public void insertMerchant(MongoCollection<Document> collection, Document doc) {
        collection.insertOne(doc);
    }
}