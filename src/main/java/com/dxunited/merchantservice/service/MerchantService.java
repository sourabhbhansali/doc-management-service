package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MerchantService {
    @Autowired
    private MerchantEventPublisher merchantEventPublisher;
    @Autowired
    private MerchantDBConnector merchantDBConnector;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private ObjectMapper mapper;

    @SneakyThrows
    public void createMerchantEvent(String value) {
         merchantEventPublisher.sendMessage(KafkaConstant.CREATE_MERCHANT, value);
    }

    @SneakyThrows
    public void saveMerchant(String createMerchant) {
        Map<String, String> merchantMap = mapper.readValue(createMerchant, HashMap.class);
        Document merchantDocument = new Document();
        merchantMap.entrySet().forEach(entry ->
                merchantDocument.append(entry.getKey(), entry.getValue()));
        MongoCollection<Document> merchantCollection = merchantDBConnector.getMerchantCollection();
        merchantRepository.insertMerchant(merchantCollection, merchantDocument);
    }
}
