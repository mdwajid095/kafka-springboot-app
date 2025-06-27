package com.kafka.admin.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Profile;

@Profile("admin")
@Service
public class KafkaAdminService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaAdminService.class);

    private final KafkaAdmin kafkaAdmin;

    @Value("${spring.kafka.admin.topic-name}")
    private String topicName;

    @Value("${spring.kafka.admin.partitions:3}")
    private int partitions;

    @Value("${spring.kafka.admin.replication-factor:3}")
    private short replicationFactor;

    KafkaAdminService(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    public void createTopic() {
        try {
            NewTopic topic = new NewTopic(topicName, partitions, replicationFactor);
            kafkaAdmin.createOrModifyTopics(topic);
            logger.info("Requested creation of topic '{}', partitions={}, replicationFactor={}", topicName, partitions, replicationFactor);
        } catch (Exception ex) {
            logger.error("Failed to create topic '{}': {}", topicName, ex.getMessage(), ex);
        }
    }
}