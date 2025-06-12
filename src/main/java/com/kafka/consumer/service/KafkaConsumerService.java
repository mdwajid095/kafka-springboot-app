package com.kafka.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Profile("consumer")
@Service
class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(
        topics = "${spring.kafka.topic}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    void listen(ConsumerRecord<?, ?> record) {
        logger.info(
            "Received event from topic: {}-{}, offset: {}, value: {}",
            record.topic(),
            record.partition(),
            record.offset(),
            record.value()
        );
        // processing logic here
    }
}
