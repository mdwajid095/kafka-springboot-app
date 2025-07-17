package com.kafka.admin.controller;

import com.kafka.admin.service.KafkaAdminService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("admin")
@RestController
@RequestMapping("/kafka/v1")
class KafkaAdminController {

    private final KafkaAdminService kafkaAdminService;

    KafkaAdminController(KafkaAdminService kafkaAdminService) {
        this.kafkaAdminService = kafkaAdminService;
    }

    // Endpoint to test the service - curl -X GET http://localhost:8080/kafka/v1/hi
    @GetMapping("/hi")
    String sayHi() {
        return "Hello.. You are in Kafka Admin Service!";
    }

    // Endpoint to create a topic - curl -X GET http://localhost:8080/kafka/v1/create-topic
    @GetMapping("/create-topic")
    String createTopic() {
        try {
            kafkaAdminService.createTopic();
            return "Topic creation requested successfully!";
        } catch (Exception ex) {
            return "Failed to create topic: " + ex.getMessage();
        }
    }
}
