package com.dxunited.merchantservice.repository;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.utils.MerchantConversion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@Service
@Repository
@Data
@Builder
public class MerchantRepository {
    @Autowired
    MerchantDBConnector merchantDBConnector;
    @Autowired
    Gson gson;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MerchantConversion merchantConversion;

    public String insertMerchant(MongoCollection<Document> collection, Document doc) {
        collection.insertOne(doc);
        return doc.toJson();
    }
}
