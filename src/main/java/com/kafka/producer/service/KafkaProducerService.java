package com.kafka.producer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.kafka.avro.EmployeeDetails;

@Service
public class KafkaProducerService {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<Object, EmployeeDetails> kafkaTemplate;

    KafkaProducerService(KafkaTemplate<Object, EmployeeDetails> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(EmployeeDetails event) {
        kafkaTemplate.send(topic, event);
    }
}
