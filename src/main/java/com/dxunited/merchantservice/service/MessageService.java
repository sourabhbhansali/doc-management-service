package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.response.EventResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class MessageService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public EventResponse sendMessage(String topic, String message) {
        EventResponse eventResponse = new EventResponse();
        log.info(String.format("Producing message -> %s", message));
        ListenableFuture<SendResult<String, String>> ack = this.kafkaTemplate.send(topic, message);
        ack.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                eventResponse.setEventPushMsg("Failure.");
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                eventResponse.setEventPushMsg("Success => " + result.getRecordMetadata().toString());
            }
        });
        return eventResponse;
    }

}
