package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.connector.MerchantWorkflowDBConnector;
import com.dxunited.merchantservice.domain.Merchant;
import com.dxunited.merchantservice.publisher.MerchantEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MerchantWorkflowService {

    @Autowired
    private MerchantEventPublisher merchantEventPublisher;
    @Autowired
    private MerchantWorkflowDBConnector merchantWorkflowDBConnector;
    @Autowired
    private Merchant merchant;

    @SneakyThrows
    public void createMerchantWorkflow(String updateMerchant) {
        Map<String, String> merchantMap = merchant.convertStringToMap(updateMerchant);
        merchantWorkflowDBConnector.validateMerchantStatus(merchantMap);
        merchantWorkflowDBConnector.saveMerchantWorkflow(merchantMap);
    }

}
