package com.dxunited.merchantservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Configuration
public class EventModel {
    private String key;
    private String value;

    @Bean
    EventModel getEventModel() {
        return new EventModel();
    }
}
