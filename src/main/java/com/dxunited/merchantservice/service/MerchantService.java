package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.connector.MerchantWorkflowDBConnector;
import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.domain.Merchant;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MerchantService {
    @Autowired
    private MerchantEventPublisher merchantEventPublisher;
    @Autowired
    private MerchantDBConnector merchantDBConnector;
    @Autowired
    private MerchantWorkflowDBConnector merchantWorkflowDBConnector;
    @Autowired
    private Merchant merchant;
    @Autowired
    private MerchantWorkflowService merchantWorkflowService;

    public void createMerchantEvent(String value, Long tenantId, Long siteId) {
        merchantEventPublisher.sendMessage(KafkaConstant.CREATE_MERCHANT, value);
    }

    public void updateMerchantEvent(String value, Long tenantId, Long siteId) {
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_MERCHANT, value);
    }

    public void merchantStatusAction(String value, Long tenantId, Long siteId) {
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_STATUS_IN_MERCHANT, value);
    }

    @SneakyThrows
    public void saveMerchant(String createMerchant) {
        Map<String, String> merchantMap = new ObjectMapper().readValue(createMerchant, HashMap.class);
       if (merchantMap.get("tenantId") != null) {
         merchantWorkflowService.createMerchantWorkflow(createMerchant);
        }else{
           merchantDBConnector.saveMerchant(merchantMap);
       }
    }

    public void updateMerchantStatus(String updateMerchant) {
        Map<String, String> merchantMap = merchant.convertStringToMap(updateMerchant);
        String status = merchantMap.get("status");
        if (StringUtils.isNotEmpty(status) && "Approve".equalsIgnoreCase(status)) {
            List<Map<String, String>> merchantData = merchantWorkflowDBConnector.getMerchantWfCollection(merchantMap);
            Map<String, String> workFlowMap = merchantData.get(0);
            workFlowMap.put("status", "Active");
            merchantDBConnector.updateMerchant(workFlowMap);
            updateMerchantDetailsInRDX(workFlowMap);
        }
        merchantWorkflowDBConnector.deleteFromMerchantWorkflow(merchantMap);
    }

    private void updateMerchantDetailsInRDX(Map<String, String> merchantMap) {
        String updateValue = merchant.buildMerchant(merchantMap);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_PRODUCT, updateValue);
    }
}
