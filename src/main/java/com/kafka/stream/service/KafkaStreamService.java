package com.kafka.stream.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.apache.kafka.streams.kstream.KStream;

@Profile("stream")
@Service
public class KafkaStreamService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaStreamService.class);

    public KStream<String, String> processStream(KStream<String, String> stream) {
 
    return stream
        .peek((key, value) -> logger.info("Received message - Key: {}, Value: {}", key, value))
        .mapValues(value -> {
            try {
                return value.toUpperCase();
            } catch (Exception e) {
                logger.error("Error processing value: {}", value, e);
                return "ERROR";
            }
        })
        .peek((key, value) -> logger.info("Transformed message - Key: {}, Value: {}", key, value));
    }
}

