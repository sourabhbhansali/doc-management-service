package com.dxunited.merchantservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class KafkaProducerConfig {
    @Value(value = "${kafka.producer.bootstrap-servers}")
    private String bootStrapServers;
    @Value(value = "${kafka.producer.key-serializer}")
    private String keyDeserializer;
    @Value(value = "${kafka.producer.value-serializer}")
    private String valueDeserializer;
}
