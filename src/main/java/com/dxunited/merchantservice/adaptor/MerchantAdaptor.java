package com.dxunited.merchantservice.adaptor;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.dxunited.merchantservice.utils.MerchantConversion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@Component
public class MerchantAdaptor {
    @Autowired
    MerchantDBConnector merchantDBConnector;
    @Autowired
    MerchantConversion merchantConversion;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    ObjectMapper mapper;
    static MongoCollection<Document> merchantCollection = null;
    static MongoCollection<Document> referenceCollection = null;

    private MongoCollection<Document> getMerchantCollection() {
        if (merchantCollection == null)
            merchantCollection = merchantDBConnector.getMerchantCollection();
        return merchantCollection;
    }

    private MongoCollection<Document> getReferenceCollection() {
        if (referenceCollection == null)
            referenceCollection = merchantDBConnector.getReferenceCollection();
        return referenceCollection;
    }

    @SneakyThrows
    public Map<String, String> insertMerchant(String merchantString) {
        HashMap<String, String> merchantMap = mapper.readValue(merchantString, HashMap.class);
        merchantMap.remove("_id");
        String randomUniqueId = UUID.randomUUID().toString();
        Document doc = new Document();
        doc.append("_id", randomUniqueId);
        merchantMap.entrySet().forEach(keySet ->
                doc.append(keySet.getKey(), keySet.getValue()));
        MongoCollection<Document> merchantCollection = getMerchantCollection();
        HashMap<String, String> merchantDetails = new HashMap<>();
        String merchant = merchantRepository.insertMerchant(merchantCollection, doc);
        merchantDetails.put("id", randomUniqueId);
        merchantDetails.put("merchant", merchant);
        return merchantDetails;
    }

    @SneakyThrows
    public String insertMerchantReference(String id, String merchantString) {
        HashMap<String, String> merchantMap = mapper.readValue(merchantString, HashMap.class);
        Document doc = new Document();
        doc.append("_id", id);
        merchantMap.entrySet().forEach(keySet ->
                doc.append(keySet.getKey(), keySet.getValue()));
        MongoCollection<Document> documentReferenceCollection = getReferenceCollection();
        return merchantRepository.insertMerchant(documentReferenceCollection, doc);
    }


}
