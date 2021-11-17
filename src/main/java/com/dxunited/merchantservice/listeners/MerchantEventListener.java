package com.dxunited.merchantservice.listeners;

import com.dxunited.merchantservice.constants.KafkaConstant;
import com.dxunited.merchantservice.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MerchantEventListener {
    private static final String GROUP_ID = "merchant_service";

    @Autowired
    private MerchantService merchantService;

    @KafkaListener(topics = KafkaConstant.CREATE_MERCHANT,
            groupId = GROUP_ID, containerFactory = "kafkaListenerContainerFactory")
    public void listenCreateMerchantEvent(@Payload String createMerchant, Acknowledgment acknowledgment) {
        log.info("consumed create RDS DB event");
        merchantService.saveMerchant(createMerchant);
        acknowledgment.acknowledge();
    }
}
