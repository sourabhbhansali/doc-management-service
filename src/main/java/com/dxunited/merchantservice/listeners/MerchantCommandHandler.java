package com.dxunited.merchantservice.listeners;

import com.dxunited.merchantservice.adaptor.MerchantAdaptor;
import com.dxunited.merchantservice.builders.*;
import com.dxunited.merchantservice.model.EventModel;
import com.dxunited.merchantservice.response.request.ErrorResponse;
import com.dxunited.merchantservice.response.request.IRequestResponse;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.dxunited.merchantservice.constants.Constants.*;

@Data
@Builder
@Slf4j
@Service
@Component
public class MerchantCommandHandler {
    @Autowired
    Gson gson;

    @Tolerate
    MerchantCommandHandler() {
    }

    @Autowired
    EventModel eventModel;
    @Autowired
    MerchantAdaptor merchantAdaptor;

    @KafkaListener(topics = {CREATE_MERCHANT})
    public IRequestResponse consumeMerchantEvents(ConsumerRecord<String, Object> event) {
        eventModel = EventModel
                .builder()
                .key(event.key())
                .value((String) event.value())
                .build();


        HashMap<String, Object> processors = new HashMap<>();
        processors.put(CREATE_MERCHANT, new MerchantCreateService(eventModel.getValue()));

        IRequestResponse response = null;
        try {
            log.info("Initiate consumeMerchantEvent in MerchantCommandHandler" + " - CorrelationId: " + event);
            MerchantService service = (MerchantService) processors.get(event.topic());
            response = service.build(merchantAdaptor);
        } catch (Exception e) {
            response = ErrorResponse
                    .builder()
                    .errorBody(e.getMessage())
                    .build();
        }
        return response;
    }
}
