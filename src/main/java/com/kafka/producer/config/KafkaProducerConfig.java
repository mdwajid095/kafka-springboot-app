package com.kafka.producer.config;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

@Profile("producer")
@Configuration
class KafkaProducerConfig {

    @Bean
    ProducerListener<String, String> producerListener() {
        return new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<String, String> record, RecordMetadata metadata) {
                LoggerFactory.getLogger(KafkaProducerConfig.class)
                    .info("ProducerListener SUCCESS: topic={}, partition={}, offset={}, value={}",
                        record.topic(), metadata.partition(), metadata.offset(), record.value());
            }

            @Override
            public void onError(ProducerRecord<String, String> record, @org.springframework.lang.Nullable RecordMetadata metadata, Exception exception) {
                LoggerFactory.getLogger(KafkaProducerConfig.class)
                    .error("ProducerListener ERROR: topic={}, value={}, exception={}",
                        record.topic(), record.value(), exception.getMessage(), exception);
            }
        };
    }

    @Bean
    KafkaTemplate<String, String> kafkaTemplate(
            ProducerFactory<String, String> producerFactory,
            ProducerListener<String, String> producerListener) {
        KafkaTemplate<String, String> template = new KafkaTemplate<>(producerFactory);
        template.setProducerListener(producerListener);
        return template;
    }
}
