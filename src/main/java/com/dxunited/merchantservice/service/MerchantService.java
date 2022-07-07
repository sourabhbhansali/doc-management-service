package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.connector.MerchantWorkflowDBConnector;
import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.constants.MerchantConstant;
import com.dxunited.merchantservice.exception.ValidationException;
import com.dxunited.merchantservice.model.MerchantRequest;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.dxunited.merchantservice.utils.MerchantConversion;
import com.dxunited.merchantservice.utils.MerchantUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private MerchantWorkflowDBConnector merchantWorkflowDBConnector;

    @Autowired
    private MerchantWorkflowService merchantWorkflowService;

    @Autowired
    private MerchantConversion merchantConversion;

    public void createMerchantEvent(MerchantRequest merchantRequest) throws Exception {
        if (merchantRequest.getMerchantRank() != null) {
            merchantDBConnector.checkMerchantRankUnique(merchantRequest.getMerchantRank());
        }
        String merchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        merchantEventPublisher.sendMessage(KafkaConstant.CREATE_MERCHANT, merchant);
    }

    public void updateMerchantEvent(MerchantRequest merchantRequest) throws Exception {
        boolean isAlreadyInReview = merchantWorkflowDBConnector
                .isMerchantAlreadyInReview(merchantRequest.getMerchantId(), merchantRequest.getClientId());
        if (isAlreadyInReview) {
            throw new ValidationException(
                    HttpStatus.CONFLICT.value(),
                    "Merchant is already in pending for review");
        }
        String updateMerchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        checkIfMerchantExistForModify(updateMerchant);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_MERCHANT, updateMerchant);
    }

    public void merchantStatusAction(MerchantRequest merchantRequest) throws Exception {
        String updateMerchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_STATUS_IN_MERCHANT, updateMerchant);
    }

    public void updateMerchantStatus(MerchantRequest merchantRequest) throws Exception {
        String updateMerchant = merchantConversion.convertObjectToJsonString(merchantRequest);
        checkIfMerchantExist(updateMerchant);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_STATUS, updateMerchant);
    }

    @SneakyThrows
    public void saveMerchant(String createMerchant) {
        MerchantRequest merchantRequest = (MerchantRequest) merchantConversion.convertStringToClass(
                createMerchant, MerchantRequest.class);
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(createMerchant);
        merchantMap.put("merchantId", String.valueOf(merchantDBConnector.getNextSequence("merchantId")));
        merchantMap.put("merchantRank", merchantDBConnector.getNextSequence("merchantRank"));
        if (StringUtils.isNotEmpty(merchantRequest.getClientId())) {
            merchantWorkflowService.createMerchantWorkflow(merchantMap);
        } else {
            merchantDBConnector.saveMerchant(merchantMap);
        }
    }

    public void updateMerchantStatus(String updateMerchant) {
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(updateMerchant);
        String status = (String)merchantMap.get("status");
        if (MerchantUtil.isReviewApproved(status)) {
            Map<String, Object> workFlowMap = merchantWorkflowService.getMerchantWfCollection(merchantMap);
            Double merchantRank = (Double) workFlowMap.get("merchantRank");
            merchantDBConnector.checkMerchantRankUnique(merchantRank.intValue());
            workFlowMap.put("status", "Enabled");
            workFlowMap.put("modifiedDate", MerchantUtil.getCurrentDate());
            merchantDBConnector.updateMerchant(workFlowMap);
            updateMerchantDetailsInProduct(workFlowMap);
        }
        merchantWorkflowService.deleteFromMerchantWorkflow(merchantMap);
    }

    private void updateMerchantDetailsInProduct(Map<String, Object> merchantMap) {
        String updateValue = merchantConversion.buildMerchantForClientProduct(merchantMap);
        merchantEventPublisher.sendMessage(KafkaConstant.UPDATE_MERCHANT_IN_PRODUCT, updateValue);
    }

    public void saveMerchantStatus(String updateMerchant) {
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(updateMerchant);
        String status = (String)merchantMap.get("status");
        if (MerchantConstant.APPROVED.equalsIgnoreCase(status)) {
            Map<String, Object> merchantWorkflowData = merchantWorkflowService.getMerchantWfCollection(merchantMap);
            Double merchantRank = (Double) merchantWorkflowData.get("merchantRank");
            merchantDBConnector.checkMerchantRankUnique(merchantRank.intValue());
            merchantWorkflowData.put("status", MerchantConstant.CREATED);
            merchantWorkflowData.put("modifiedDate", MerchantUtil.getCurrentDate());
            merchantDBConnector.updateMerchant(merchantWorkflowData);
            updateMerchantDetailsInProduct(merchantWorkflowData);
            merchantWorkflowService.deleteFromMerchantWorkflow(merchantMap);
        } else if (MerchantConstant.REJECTED.equalsIgnoreCase(status)) {
            merchantWorkflowService.deleteFromMerchantWorkflow(merchantMap);
        } else if (MerchantConstant.ENABLED.equalsIgnoreCase(status)
                    || MerchantConstant.DISABLED.equalsIgnoreCase(status)) {
            Map<String, Object> merchantData = merchantDBConnector.getMerchantCollection(merchantMap);
            merchantData.put("status", status);
            merchantData.put("modifiedDate", MerchantUtil.getCurrentDate());
            merchantDBConnector.updateMerchant(merchantData);
            updateMerchantDetailsInProduct(merchantData);
        } else {
            throw new ValidationException(
                    HttpStatus.BAD_REQUEST.value(), "Invalid Merchant Status");
        }
    }

    private void checkIfMerchantExist(String merchant) {
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(merchant);
        String status = (String)merchantMap.get("status");
        if (MerchantConstant.APPROVED.equalsIgnoreCase(status)) {
            Map<String, Object> merchantWorkflowData = merchantWorkflowDBConnector.getMerchantWfCollection(merchantMap);
            if (MapUtils.isEmpty(merchantWorkflowData)) {
                throw new ValidationException(HttpStatus.NOT_FOUND.value(), "Invalid Merchant Id, No merchant found with given id");
            }
        } else if (MerchantConstant.ENABLED.equalsIgnoreCase(status)
                || MerchantConstant.DISABLED.equalsIgnoreCase(status)) {
            Map<String, Object> merchantData = merchantDBConnector.getMerchantCollection(merchantMap);
            if (MapUtils.isEmpty(merchantData)) {
                throw new ValidationException(HttpStatus.NOT_FOUND.value(), "Invalid Merchant Id, No merchant found with given id");
            }
        }
    }

    private void checkIfMerchantExistForModify(String merchant) {
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(merchant);
        Map<String, Object> merchantData = merchantDBConnector.getMerchantCollection(merchantMap);
        if (MapUtils.isEmpty(merchantData)) {
            throw new ValidationException(
                    HttpStatus.NOT_FOUND.value(), "Invalid Merchant Id, No merchant found with given id");
        }
    }
}
