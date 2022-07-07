package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantWorkflowDBConnector;
import com.dxunited.merchantservice.exception.ValidationException;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class MerchantWorkflowService {

    @Autowired
    private MerchantEventPublisher merchantEventPublisher;
    @Autowired
    private MerchantWorkflowDBConnector merchantWorkflowDBConnector;

    @SneakyThrows
    public void createMerchantWorkflow(Map<String, Object> merchantMap) {
        merchantWorkflowDBConnector.saveMerchantWorkflow(merchantMap);
    }

    public Map<String, Object> getMerchantWfCollection(Map<String, Object> merchantMap) {
        return merchantWorkflowDBConnector.getMerchantWfCollection(merchantMap);
    }

    public void deleteFromMerchantWorkflow( Map<String, Object> workFlowMap) {
        merchantWorkflowDBConnector.deleteFromMerchantWorkflow(workFlowMap);
    }
}
