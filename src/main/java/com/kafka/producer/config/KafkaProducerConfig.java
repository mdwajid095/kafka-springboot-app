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

import com.kafka.avro.EmployeeDetails;

@Profile("producer")
@Configuration
class KafkaProducerConfig {

    @Bean
    ProducerListener<Object, EmployeeDetails> producerListener() {
        return new ProducerListener<>() {
            @Override
            public void onSuccess(ProducerRecord<Object, EmployeeDetails> record, RecordMetadata metadata) {
                LoggerFactory.getLogger(KafkaProducerConfig.class)
                    .info("ProducerListener SUCCESS: topic={}, partition={}, offset={}, value={}",
                        record.topic(), metadata.partition(), metadata.offset(), record.value());
            }

            @Override
            public void onError(ProducerRecord<Object, EmployeeDetails> record, @org.springframework.lang.Nullable RecordMetadata metadata, Exception exception) {
                LoggerFactory.getLogger(KafkaProducerConfig.class)
                    .error("ProducerListener ERROR: topic={}, value={}, exception={}",
                        record.topic(), record.value(), exception.getMessage(), exception);
            }
        };
    }

    @Bean
    KafkaTemplate<Object, EmployeeDetails> kafkaTemplate(
            ProducerFactory<Object, EmployeeDetails> producerFactory,
            ProducerListener<Object, EmployeeDetails> producerListener) {
        KafkaTemplate<Object, EmployeeDetails> template = new KafkaTemplate<>(producerFactory);
        template.setProducerListener(producerListener);
        return template;
    }
}
