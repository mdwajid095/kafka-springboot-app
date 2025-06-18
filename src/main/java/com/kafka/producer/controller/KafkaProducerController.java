package com.kafka.producer.controller;

import com.kafka.producer.service.KafkaProducerService;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Profile("producer")
@RestController
@RequestMapping("/kafka/v1")
class KafkaProducerController {

    private final KafkaProducerService KafkaProducerService;

    KafkaProducerController(KafkaProducerService KafkaProducerService) {
        this.KafkaProducerService = KafkaProducerService;
    }

    // Endpoint to test the service - curl -X GET http://localhost:8080/kafka/v1/hi
    @GetMapping("/hi")
    public String sayHi() {
        return "Hello.. You are in Kafka Producer Service!";
    }

    // Endpoint to publish event - curl -X POST http://localhost:8080/kafka/v1/send -H "Content-Type: application/json" -d {"ordertime":1000004222380,"orderid":23,"itemid":"Item_101","address":{"city":"Kolkata","state":"WB","zipcode":700046}}
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Message must not be empty.");
        }
        try {
            KafkaProducerService.send(message);
            return ResponseEntity.ok("Message sent to Kafka topic.");
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body("Failed to send message: " + ex.getMessage());
        }
    }
}
