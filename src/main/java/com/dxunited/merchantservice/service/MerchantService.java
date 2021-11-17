package com.dxunited.merchantservice.service;

import com.dxunited.merchantservice.adaptor.EventAdaptor;
import com.dxunited.merchantservice.response.EventResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MerchantService {
    @Autowired
    EventAdaptor eventAdaptor;

    public EventResponse createMerchantEvent(String topic, String value) {
         return eventAdaptor.sendMessage(topic, value);
    }
}
