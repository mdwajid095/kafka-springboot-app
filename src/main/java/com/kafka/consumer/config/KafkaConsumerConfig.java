package com.kafka.consumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.slf4j.LoggerFactory;

@Configuration
@Profile("consumer")
class KafkaConsumerConfig {

    @Value("${spring.kafka.listener.concurrency:1}")
    int concurrency;

    @Value("${spring.kafka.listener.retry.max-attempts:9}")
    int maxAttempts;

    @Value("${spring.kafka.listener.retry.initial-interval:1000}")
    long initialInterval;

    @Value("${spring.kafka.listener.retry.multiplier:2.0}")
    double multiplier;

    @Value("${spring.kafka.listener.retry.max-interval:10000}")
    long maxInterval;

    @Value("${spring.kafka.listener.ack-mode:BATCH}")
    String ackMode;

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(concurrency);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            (record, ex) -> {
                LoggerFactory.getLogger(KafkaConsumerConfig.class)
                    .error("Error in process with record: topic={}, partition={}, offset={}, value={}, exception={}",
                        record.topic(),
                        record.partition(),
                        record.offset(),
                        record.value(),
                        ex.getMessage(),
                        ex
                    );
                // Throwing an exception will stop the container - "fail fast" approach
                // throw new org.springframework.kafka.KafkaException("Stopping container due to persistent error", ex);
            },
            new ExponentialBackOffWithMaxRetries(maxAttempts) {{
                setInitialInterval(initialInterval);
                setMultiplier(multiplier);
                setMaxInterval(maxInterval);
            }}
        );

        errorHandler.setRetryListeners((record, ex, deliveryAttempt) -> {
        LoggerFactory.getLogger(KafkaConsumerConfig.class)
            .warn("Retry attempt {} for record: topic={}, partition={}, offset={}, value={}, exception={}",
                deliveryAttempt,
                record.topic(),
                record.partition(),
                record.offset(),
                record.value(),
                ex.getMessage()
            );
        });

        factory.setCommonErrorHandler(errorHandler);
        factory.getContainerProperties().setAckMode(
            org.springframework.kafka.listener.ContainerProperties.AckMode.valueOf(ackMode.toUpperCase())
            );
        return factory;
    }
}
