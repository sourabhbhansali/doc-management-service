package com.dxunited.merchantservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class KafkaProducerConfig {
    @Value(value = "${kafka.bootstrap}")
    private String bootStrapServers;
    @Value(value = "${kafka.key-serializer}")
    private String keyDeserializer;
    @Value(value = "${kafka.value-serializer}")
    private String valueDeserializer;
}
