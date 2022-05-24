package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.connector.MerchantWorkflowDBConnector;
import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.model.MerchantRequest;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.dxunited.merchantservice.utils.MerchantConversion;
import com.dxunited.merchantservice.utils.MerchantUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MerchantService {

    @Autowired
    private MerchantEventPublisher merchantEventPublisher;

    @Autowired
    private MerchantDBConnector merchantDBConnector;

    @Autowired
    private MerchantWorkflowService merchantWorkflowService;

    @Autowired
    private MerchantConversion merchantConversion;

    public void createMerchantEvent(MerchantRequest merchantRequest) throws Exception {
        String merchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        merchantEventPublisher.sendMessage(KafkaConstant.CREATE_MERCHANT, merchant);
    }

    public void updateMerchantEvent(MerchantRequest merchantRequest) throws Exception {
        String updateMerchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_MERCHANT, updateMerchant);
    }

    public void merchantStatusAction(MerchantRequest merchantRequest) throws Exception {
        String updateMerchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_STATUS_IN_MERCHANT, updateMerchant);
    }

    @SneakyThrows
    public void saveMerchant(String createMerchant) {
        MerchantRequest merchantRequest = (MerchantRequest) merchantConversion.convertStringToClass(
                createMerchant, MerchantRequest.class);
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(createMerchant);
        if (StringUtils.isNotEmpty(merchantRequest.getClientId())) {
            merchantWorkflowService.createMerchantWorkflow(merchantMap);
        } else{
            merchantDBConnector.saveMerchant(merchantMap);
        }
    }

    public void updateMerchantStatus(String updateMerchant) {
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(updateMerchant);
        String status = (String)merchantMap.get("status");
        if (MerchantUtil.isReviewApproved(status) ) {
            Map<String, Object> workFlowMap  = merchantWorkflowService.getMerchantWfCollection(merchantMap);
            workFlowMap.put("status", status);
            merchantDBConnector.updateMerchant(workFlowMap);
            updateMerchantDetailsInProduct(workFlowMap);
        }
        merchantWorkflowService.deleteFromMerchantWorkflow(merchantMap);
    }

    private void updateMerchantDetailsInProduct(Map<String, Object> merchantMap) {
        String updateValue = merchantConversion.buildMerchantForClientProduct(merchantMap);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_PRODUCT, updateValue);
    }
}
