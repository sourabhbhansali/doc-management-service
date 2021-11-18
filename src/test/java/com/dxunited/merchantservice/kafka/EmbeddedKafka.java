package com.dxunited.merchantservice.kafka;

import com.dxunited.merchantservice.adaptor.MerchantAdaptor;
import com.dxunited.merchantservice.config.KafkaProducerConfig;
import com.dxunited.merchantservice.connector.MerchantDBConnector;
import com.dxunited.merchantservice.response.MerchantBaseResponse;
import com.dxunited.merchantservice.service.MerchantService;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://127.0.0.1:9092", "port=9092"})
class EmbeddedKafkaIntegrationTest {

    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

    @MockBean
    MerchantService merchantService;
    @MockBean
    MerchantBaseResponse merchantBaseResponse;
    @MockBean
    MerchantDBConnector merchantDBConnector;
    @MockBean
    MerchantAdaptor merchantAdaptor;
    @MockBean
    KafkaProducerConfig kafkaProducerConfig;
    @MockBean
    KafkaProducer kafkaProducer;
    @MockBean
    KafkaConsumer consumer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void sendAndReceive() throws Exception {
        kafkaProducer.send(new ProducerRecord(topic, "Hello"));
        consumer.subscribe(Collections.singletonList(topic));
        int noMessageFound = 0;
        ConsumerRecords consumerRecords = consumer.poll(1000);
        if (consumerRecords.count() == 0) {
            noMessageFound++;
        }
        consumerRecords.forEach(record -> {
            System.out.println("Hello");
        });
        consumer.commitAsync();

    }
}