package com.dxunited.merchantservice.listeners;

import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.service.MerchantService;
import com.dxunited.merchantservice.service.MerchantWorkflowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MerchantEventListener {
    private static final String GROUP_ID = "tenant_product_service";

    @Autowired
    private MerchantService merchantService;
    @Autowired
    private MerchantWorkflowService merchantWorkflowService;

    @KafkaListener(topics = KafkaConstant.CREATE_MERCHANT, containerFactory = "kafkaListenerContainerFactory")
    public void listenCreateMerchantEvent(@Payload String createMerchant, Acknowledgment acknowledgment) {
        log.info("consumed create RDS DB event");
        merchantService.saveMerchant(createMerchant);
        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = KafkaConstant.UPDATE_MERCHANT_IN_MERCHANT, containerFactory = "kafkaListenerContainerFactory")
    public void listenUpdateMerchantEvent(@Payload String updateMerchant, Acknowledgment acknowledgment) {
        log.info("consumed create mongo DB event");
        merchantWorkflowService.createMerchantWorkflow(updateMerchant);
        acknowledgment.acknowledge();
    }


    @KafkaListener(topics = KafkaConstant.UPDATE_MERCHANT_STATUS_IN_MERCHANT, containerFactory = "kafkaListenerContainerFactory")
    public void listenUpdateMerchantStatusEvent(@Payload String updateMerchant, Acknowledgment acknowledgment) {
        log.info("consumed create mongo DB event");
        merchantService.updateMerchantStatus(updateMerchant);
        acknowledgment.acknowledge();
    }
}
