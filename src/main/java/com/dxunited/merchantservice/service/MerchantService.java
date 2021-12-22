package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.dxunited.merchantservice.repository.MerchantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MerchantService {
    @Autowired
    private MerchantEventPublisher merchantEventPublisher;
    @Autowired
    private MerchantDBConnector merchantDBConnector;


    @SneakyThrows
    public void createMerchantEvent(String value) {
         merchantEventPublisher.sendMessage(KafkaConstant.CREATE_MERCHANT, value);
    }

    public void saveMerchant(String createMerchant) {
        merchantDBConnector.saveMerchant(createMerchant);
    }

    public void updateMerchantEvent(String value, Long tenantId, Long siteId) {
        ListenableFuture<SendResult<String, String>> ack =  merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT, value);

        ack.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Merchant creation failed", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.debug("Merchant created", result.getRecordMetadata().toString());
                String updateValue  = getRequiredDetails(value, tenantId, siteId);
                merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_PRODUCT, updateValue);
            }
        });
    }
@SneakyThrows
    private String getRequiredDetails(String value, Long tenantId, Long siteId) {
        Map<String, String> merchantMap = new ObjectMapper().readValue(value, HashMap.class);
    Map<String, String> tenantMerchantUpdateMap = new HashMap<>();
    tenantMerchantUpdateMap.put("merchantId",merchantMap.get("merchantId"));
    tenantMerchantUpdateMap.put("status",merchantMap.get("status"));
    tenantMerchantUpdateMap.put("siteId",siteId+"");
    tenantMerchantUpdateMap.put("tenantId",tenantId+"");
    tenantMerchantUpdateMap.put("modifiedDate",merchantMap.get("modifiedDate"));
    tenantMerchantUpdateMap.put("modifiedBy",merchantMap.get("modifiedBy"));
    Gson gson= new Gson();
    return gson.toJson(tenantMerchantUpdateMap);


}

    public void updateMerchant(String updateMerchant) {
        merchantDBConnector.updateMerchant(updateMerchant);
    }
}
