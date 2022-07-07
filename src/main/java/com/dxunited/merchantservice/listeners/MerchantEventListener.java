package com.dxunited.merchantservice.listeners;

import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.service.MerchantService;
import com.dxunited.merchantservice.service.MerchantWorkflowService;
import com.dxunited.merchantservice.utils.MerchantConversion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component
public class MerchantEventListener {
    private static final String GROUP_ID = "client_product_service";

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantWorkflowService merchantWorkflowService;

    @Autowired
    private MerchantConversion merchantConversion;

    @KafkaListener(topics = KafkaConstant.CREATE_MERCHANT, containerFactory = "kafkaListenerContainerFactory")
    public void listenCreateMerchantEvent(@Payload String createMerchant, Acknowledgment acknowledgment) {
        log.info("consumed create merchant event");
        merchantService.saveMerchant(createMerchant);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = KafkaConstant.UPDATE_MERCHANT_IN_MERCHANT, containerFactory = "kafkaListenerContainerFactory")
    public void listenUpdateMerchantEvent(@Payload String updateMerchant, Acknowledgment acknowledgment) {
        log.info("consumed update merchant event");
        Map<String, Object> merchantMap = merchantConversion.convertStringToMap(updateMerchant);
        merchantWorkflowService.createMerchantWorkflow(merchantMap);
        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = KafkaConstant.UPDATE_MERCHANT_STATUS_IN_MERCHANT, containerFactory = "kafkaListenerContainerFactory")
    public void listenPublishMerchantStatusEvent(@Payload String updateMerchant, Acknowledgment acknowledgment) {
        log.info("consumed publish merchant event");
        merchantService.updateMerchantStatus(updateMerchant);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = KafkaConstant.UPDATE_MERCHANT_STATUS, containerFactory = "kafkaListenerContainerFactory")
    public void listenUpdateMerchantStatusEvent(@Payload String updateMerchant, Acknowledgment acknowledgment) {
        log.info("consumed update merchant status event");
        merchantService.saveMerchantStatus(updateMerchant);
        acknowledgment.acknowledge();
    }
}
