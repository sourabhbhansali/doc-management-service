package com.dxunited.merchantservice.adaptor;

import com.dxunited.merchantservice.response.EventResponse;
import com.dxunited.merchantservice.service.MessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventAdaptor {
    @Autowired
    MessageService messageService;
    @SneakyThrows
    public EventResponse sendMessage(String topic, String value) {
        return messageService.sendMessage(topic, value);
    }
}